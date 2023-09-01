package com.google.eclipse.mechanic.internal;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.eclipse.mechanic.plugin.core.MechanicLog;

public class CustomTemplateMerger {

  private static final MechanicLog log = MechanicLog.getDefault();

  public static final String INSTANCE_ORG_ECLIPSE_JDT_UI = "/instance/org.eclipse.jdt.ui";
  public static final String CUSTOM_TEMPLATES_PREFERENCE_KEY = "org.eclipse.jdt.ui.text.custom_templates";
  private Document oldXml;
  private Document newXml;
  private Preferences mergingNode;
  private String newXmlString;

  private class CustomTemplate {

    private Node node;
    private String name;
    private String context;
    private String autoinsert;
    private String deleted;
    private String enabled;
    private String description;
    private String value;
    private boolean deepEquals;

    public CustomTemplate(Node node, boolean deepEquals) {
      this.node = node;
      this.deepEquals = deepEquals;
      name = node.getAttributes().getNamedItem("name").getNodeValue();
      context = node.getAttributes().getNamedItem("context").getNodeValue();
      autoinsert = node.getAttributes().getNamedItem("autoinsert")
          .getNodeValue();
      deleted = node.getAttributes().getNamedItem("deleted").getNodeValue();
      enabled = node.getAttributes().getNamedItem("enabled").getNodeValue();
      description = node.getAttributes().getNamedItem("description")
          .getNodeValue();
      value = node.getTextContent();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof CustomTemplate) {
        return ((CustomTemplate) obj).name.equals(name)
            && ((CustomTemplate) obj).context.equals(context)
            && (!deepEquals
                || ((CustomTemplate) obj).autoinsert.equals(autoinsert)
                    && ((CustomTemplate) obj).deleted.equals(deleted)
                    && ((CustomTemplate) obj).description.equals(description)
                    && ((CustomTemplate) obj).enabled.equals(enabled)
                    && ((CustomTemplate) obj).value.equals(value));
      }
      return super.equals(obj);
    }

    @Override
    public int hashCode() {
      if (deepEquals) {
        return name.hashCode() + context.hashCode() + autoinsert.hashCode()
            + deleted.hashCode() + enabled.hashCode() + description.hashCode()
            + value.hashCode();
      }
      return name.hashCode() + context.hashCode();
    }

  }

  public CustomTemplateMerger(Preferences newPrefs, Preferences oldPrefs) {
    try {
      if (newPrefs.nodeExists(INSTANCE_ORG_ECLIPSE_JDT_UI)) {
        mergingNode = newPrefs.node(INSTANCE_ORG_ECLIPSE_JDT_UI);
        String newPref = mergingNode.get(CUSTOM_TEMPLATES_PREFERENCE_KEY, null);
        if (oldPrefs.nodeExists(INSTANCE_ORG_ECLIPSE_JDT_UI)) {
          Preferences oldNode = oldPrefs.node(INSTANCE_ORG_ECLIPSE_JDT_UI);
          String oldPref = oldNode.get(CUSTOM_TEMPLATES_PREFERENCE_KEY, null);
          DocumentBuilder newXmlDocumentBuilder = DocumentBuilderFactory
              .newInstance().newDocumentBuilder();
          DocumentBuilder oldXmlDocumentBuilder = DocumentBuilderFactory
              .newInstance().newDocumentBuilder();

          newXml = newXmlDocumentBuilder
              .parse(new InputSource(new StringReader(newPref)));

          oldXml = oldXmlDocumentBuilder
              .parse(new InputSource(new StringReader(oldPref)));

        }
      }
    } catch (BackingStoreException e) {
      log.logError(e);
    } catch (SAXException e) {
      log.logError(e);
    } catch (IOException e) {
      log.logError(e);
    } catch (ParserConfigurationException e) {
      log.logError(e);
    }
  }

  public CustomTemplateMerger(String newPref, String oldPref) {
    try {
      DocumentBuilder newXmlDocumentBuilder = DocumentBuilderFactory
          .newInstance().newDocumentBuilder();
      DocumentBuilder oldXmlDocumentBuilder = DocumentBuilderFactory
          .newInstance().newDocumentBuilder();

      newXml = newXmlDocumentBuilder
          .parse(new InputSource(new StringReader(newPref)));

      oldXml = oldXmlDocumentBuilder
          .parse(new InputSource(new StringReader(oldPref)));
    } catch (SAXException e) {
      log.logError(e);
    } catch (IOException e) {
      log.logError(e);
    } catch (ParserConfigurationException e) {
      log.logError(e);
    }
  }

  /**
   * merges the old custom templates (oldPrefs) to the new custom templates
   * (newPrefs), if an template is on both (according to
   * {@link CustomTemplate#equals(Object)})then the one in newPrefs will be
   * applied
   * 
   * @param newPrefs
   * @param oldPrefs
   */
  public String mergeCustomTemplates() {
    mergeOrCheckCustomTemplates(true);
    if (mergingNode != null) {
      mergingNode.put(CUSTOM_TEMPLATES_PREFERENCE_KEY, newXmlString);
    }
    return newXmlString;
  }

  private boolean mergeOrCheckCustomTemplates(boolean merge)
      throws TransformerFactoryConfigurationError {
    try {
      Set<CustomTemplate> newCustomTemplates = extractTemplates(newXml, !merge);
      Set<CustomTemplate> oldCustomTemplates = extractTemplates(oldXml, !merge);

      NodeList elementsByTagName = newXml.getElementsByTagName("templates");
      Node templatesNode = elementsByTagName.item(0);
      Iterator<CustomTemplate> iterator = oldCustomTemplates.iterator();
      boolean atLeastOneMergeWasMade = false;
      while (iterator.hasNext()) {
        CustomTemplate next = iterator.next();
        if (!newCustomTemplates.contains(next)) {
          if (merge) {
            templatesNode.appendChild(newXml.importNode(next.node, true));
          }
          atLeastOneMergeWasMade = true;
        }
      }
      if (!merge) {
        return atLeastOneMergeWasMade;
      }
      TransformerFactory factory = TransformerFactory.newInstance();
      factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      Transformer transformer = factory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      StringWriter writer = new StringWriter();

      transformer.transform(new DOMSource(newXml), new StreamResult(writer));
      newXmlString = writer.getBuffer().toString();
    } catch (TransformerException e) {
      log.logError(e);
    }
    return true;
  }

  public boolean isSomethingToMerge() {
    return mergeOrCheckCustomTemplates(false);
  }

  private Set<CustomTemplate> extractTemplates(Document newXml,
      boolean deepEquals) {
    NodeList newTemplateNodes = newXml.getElementsByTagName("template");
    Set<CustomTemplate> newCustomTemplates = new HashSet<CustomTemplate>();
    for (int i = 0; i < newTemplateNodes.getLength(); i++) {
      Node item = newTemplateNodes.item(i);
      newCustomTemplates
          .add(new CustomTemplate(item.cloneNode(true), deepEquals));
    }
    return newCustomTemplates;
  }
}

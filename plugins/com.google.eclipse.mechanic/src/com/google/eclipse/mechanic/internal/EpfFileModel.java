/*******************************************************************************
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.google.eclipse.mechanic.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

/**
 * A model of an EPF file.
 * 
 * @author brianchin@google.com (Brian Chin)
 */
public class EpfFileModel {
  private final String title;
  private final String description;
  private final TaskType taskType;
  private final XMLMode xmlMode;
  private final Map<String, String> preferences = new HashMap<String, String>();
  
  /**
   * Creates an EPF file model with the given properties.
   * @param title the title of this task. Listed in the @title field.
   * @param description the description of this task. Listed in the 
   *     @description field.
   * @param taskType the type of task to create.
   *  @param xmlMode the xml mode to use
   */
  public EpfFileModel(
      String title, 
      String description, 
      TaskType taskType, 
      XMLMode xmlMode) {
    this.title = Preconditions.checkNotNull(title);
    this.description = Preconditions.checkNotNull(description);
    this.taskType = Preconditions.checkNotNull(taskType);
    this.xmlMode = Preconditions.checkNotNull(xmlMode);
  }
  
  /**
   * Adds a preference entry to this file.
   * @param key the preference's key
   * @param value the preference's value
   * @return this.
   */
  public EpfFileModel addElement(String key, String value) {
    preferences.put(key, value);
    // TODO(brianchin): Currently, this outputs the scope used for each modification as well
    // as part of the key. I assume this needs to be changed.
    return this;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public TaskType getTaskType() {
    return taskType;
  }

  public XMLMode getXmlMode() {
    return xmlMode;
  }

  public Map<String, String> getPreferences() {
    // TODO(konigsberg): Use ImmutableMap.
    return Collections.unmodifiableMap(preferences);
  }

}

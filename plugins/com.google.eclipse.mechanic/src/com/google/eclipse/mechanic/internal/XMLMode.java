package com.google.eclipse.mechanic.internal;

/**
 * An enum that defines how XML files should be handled.
 */
public enum XMLMode {

  /**
   * the current xml will be overwritten by the one from preference file
   */
  OVERWRITE,

  /**
   * the current xml should be merged with the one from preference file new
   * entries will be added, not existing will stay, existing will be handled
   * according to the {@link TaskType}
   */
  MERGE;
}

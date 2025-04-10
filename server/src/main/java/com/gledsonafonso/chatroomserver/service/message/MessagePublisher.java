package com.gledsonafonso.chatroomserver.service.message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class MessagePublisher {
  private final static PropertyChangeSupport support = new PropertyChangeSupport(MessagePublisher.class);

  public static void addSubscriber(PropertyChangeListener propertyChangeListener) {
    support.addPropertyChangeListener(propertyChangeListener);
  }

  public static void removeSubscriber(PropertyChangeListener propertyChangeListener) {
    support.removePropertyChangeListener(propertyChangeListener);
  }

  public static void publishMessage(String newMessage) {
    support.firePropertyChange("message", "", newMessage);
  }
}

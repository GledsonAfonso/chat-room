package com.gledsonafonso.chatroomserver;

import com.gledsonafonso.chatroomserver.service.CommunicationService;

public class Application {
  public static void main(String[] args) {
    var communicationService = new CommunicationService();

    try {
      communicationService.start(6666);
    } catch (Exception exception) {
      System.out.println("Error" + exception.getMessage());
    } finally {
      communicationService.stop();
    }
  }
}

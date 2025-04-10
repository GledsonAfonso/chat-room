package com.gledsonafonso.chatroomserver;

import com.gledsonafonso.chatroomserver.configuration.Environment;
import com.gledsonafonso.chatroomserver.service.connection.CommunicationService;

public class Application {
  public static void main(String[] args) {
    var environment = new Environment();
    var port = Integer.parseInt(environment.getProperty("port"));
    
    try(var communicationService = new CommunicationService()) {
      communicationService.start(port);
    } catch (Exception exception) {
      System.out.println("Error" + exception.getMessage());
    }
  }
}

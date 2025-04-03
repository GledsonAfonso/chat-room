package com.gledsonafonso.chatroomclient;

import com.gledsonafonso.chatroomclient.service.ConnectionService;

public class Application {
  public static void main(String[] args) {
    try {
      var connectionService = new ConnectionService();
      connectionService.start("127.0.0.1", 6666);
      
      var serverResponse = connectionService.sendMessage("hello server");
      System.out.println("Server says: " + serverResponse);
    } catch (Exception exception) {
      System.out.println("Error: " + exception.getMessage());
    }
  }
}

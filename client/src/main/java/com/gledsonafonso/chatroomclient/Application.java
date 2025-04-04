package com.gledsonafonso.chatroomclient;

import com.gledsonafonso.chatroomclient.configuration.Environment;
import com.gledsonafonso.chatroomclient.service.ConnectionService;

public class Application {
  public static void main(String[] args) {
    var environment = new Environment();
    var ip = environment.getProperty("ip");
    var port = Integer.parseInt(environment.getProperty("port"));
    
    try(var connectionService = new ConnectionService()) {
      connectionService.start(ip, port);

      var serverResponse = connectionService.sendMessage("hello server");
      System.out.println("Server says: " + serverResponse);
    } catch (Exception exception) {
      System.out.println("Error: " + exception.getMessage());
    }
  }
}

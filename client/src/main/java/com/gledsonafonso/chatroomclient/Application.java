package com.gledsonafonso.chatroomclient;

import com.gledsonafonso.chatroomclient.configuration.Environment;
import com.gledsonafonso.chatroomclient.service.connection.ServerConnection;
import com.gledsonafonso.chatroomclient.service.io.InputHandler;
import com.gledsonafonso.chatroomclient.service.io.OutputHandler;

public class Application {
  public static void main(String[] args) {
    try {
      var environment = new Environment();
      var ip = environment.getProperty("ip");
      var port = Integer.parseInt(environment.getProperty("port"));
      var serverConnection = new ServerConnection(ip, port);
      var inputHandler = new InputHandler(serverConnection.getWriter());
      var outpuHandler = new OutputHandler(serverConnection.getClientId(), serverConnection.getReader());

      outpuHandler.start();
      inputHandler.start();

      // Forcing the main thread to wait for both i/o threads to finish first
      outpuHandler.join();
      inputHandler.join();

      serverConnection.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}

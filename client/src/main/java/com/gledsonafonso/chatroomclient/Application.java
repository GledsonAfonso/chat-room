package com.gledsonafonso.chatroomclient;

import java.util.Scanner;

import com.gledsonafonso.chatroomclient.configuration.Environment;
import com.gledsonafonso.chatroomclient.service.ConnectionService;

public class Application {
  public static void main(String[] args) {
    var environment = new Environment();
    var ip = environment.getProperty("ip");
    var port = Integer.parseInt(environment.getProperty("port"));
    
    try(
      var connectionService = new ConnectionService();
      var inputScanner = new Scanner(System.in);
    ) {
      connectionService.start(ip, port);

      String input;
      while(inputScanner.hasNext() && !(input = inputScanner.nextLine()).equals("exit")) {
        var serverResponse = connectionService.sendMessage(input);
        System.out.println(serverResponse);
      }

      System.out.println(connectionService.sendMessage("exit"));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}

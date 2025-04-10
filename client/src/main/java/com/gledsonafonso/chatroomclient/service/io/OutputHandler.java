package com.gledsonafonso.chatroomclient.service.io;

import java.io.BufferedReader;
import java.io.IOException;

public class OutputHandler extends Thread {
  private String clientId;
  private BufferedReader reader;

  public OutputHandler(String clientId, BufferedReader reader) throws IOException {
    this.reader = reader;
    this.clientId = clientId;
  }

  @Override
  public void run() {
    // receiving response from server
    String message;
    while((message = this.readLine()) != null) {
      // don't show if messages are from the this client
      if (!message.contains(this.clientId)) {
        System.out.println(message);
      }
    }
  }

  private String readLine() {
    try {
      return this.reader.readLine();
    } catch (IOException exception) {
      if(exception.getMessage().contains("Stream closed")) {
        return null;
      }
  
      throw new RuntimeException(exception);
    }
  }
}

package com.gledsonafonso.chatroomserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.gledsonafonso.chatroomserver.service.message.MessagePublisher;

public abstract class MessageUtils {
  public static String REMOTE_ID_PREFIX = "remote-id--";
  public static String EXIT_MESSAGE = "--exit--";

  public static String readLine(BufferedReader reader) {
    try {
      return reader.readLine();
    } catch (IOException exception) {
      if(exception.getMessage().contains("Stream closed")) {
        return null;
      }
  
      throw new RuntimeException(exception);
    }
  }

  public static void sendMessage(PrintWriter writer, String message) {
    writer.println(message);
  }

  public static void sendRemoteIdToClient(BufferedReader reader, PrintWriter writer, String clientId) throws RuntimeException {
    // validate if client is requesting the remote id, throws error if not
    var clientRemoteIdMessageRequest = MessageUtils.readLine(reader);
    if (clientRemoteIdMessageRequest == null) {
      throw new RuntimeException("Client " + clientId + " did not requested the remote id.");
    }
    
    // sends remote id to client
    writer.println(REMOTE_ID_PREFIX + clientId);
  }

  public static void processMessage(BufferedReader reader, PrintWriter writer, String clientId) {
    String clientMessage;
    while((clientMessage = MessageUtils.readLine(reader)) != null) {
      if (clientMessage.equals(EXIT_MESSAGE)) {
        break;
      }

      MessagePublisher.publishMessage(clientId + ": " + clientMessage);
    }
  }
}

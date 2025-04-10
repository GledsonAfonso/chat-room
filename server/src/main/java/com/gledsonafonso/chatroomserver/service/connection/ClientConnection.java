package com.gledsonafonso.chatroomserver.service.connection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.gledsonafonso.chatroomserver.service.message.MessagePublisher;
import com.gledsonafonso.chatroomserver.utils.MessageUtils;

public class ClientConnection extends Thread implements PropertyChangeListener {
  private String clientId;
  private BufferedReader reader;
  private PrintWriter writer;
  
  public ClientConnection(Socket socket) throws IOException {
    this.clientId = "user-" + socket.getRemoteSocketAddress().toString();
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new PrintWriter(socket.getOutputStream(), true);

    MessageUtils.sendRemoteIdToClient(this.reader, this.writer, this.clientId);
  
    System.out.println(this.clientId + " logged in.");
  }

  @Override
  public void run() {
    try {
      MessageUtils.processMessage(this.reader, this.writer, this.clientId);
      this.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    var message = (String) evt.getNewValue();
    MessageUtils.sendMessage(this.writer, message);
  }

  public void close() throws IOException {
    this.reader.close();
    this.writer.close();
    
    MessagePublisher.removeSubscriber(this);

    System.out.println("Client " + this.clientId + " disconnected.");
  }  
}

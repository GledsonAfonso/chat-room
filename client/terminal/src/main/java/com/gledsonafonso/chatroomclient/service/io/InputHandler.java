package com.gledsonafonso.chatroomclient.service.io;

import java.io.PrintWriter;
import java.util.Scanner;

import com.gledsonafonso.chatroomclient.utils.MessageUtils;

public class InputHandler extends Thread {
  private PrintWriter writer;
  
  public InputHandler(PrintWriter writer) {
    this.writer = writer;
  }

  @Override
  public void start() {
    try(var inputScanner = new Scanner(System.in)) {
      String input;
      while(inputScanner.hasNext() && !(input = inputScanner.nextLine()).equals(MessageUtils.EXIT_MESSAGE)) {
        this.writer.println(input);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}

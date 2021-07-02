package com.google;

import java.util.Arrays;
import java.util.Scanner;

public class Run {
  public static void main(String[] args){
    System.out.println("Hello and welcome to YouTube, what would you like to do? "
        + "Enter HELP for list of available commands or EXIT to terminate.");
    var videoPlayer = new VideoPlayer();
    var parser = new CommandParser(videoPlayer);
    var scanner = new Scanner(System.in);
    while (true) {
      System.out.print("YT> ");
      var input = scanner.nextLine();
      if (input.equalsIgnoreCase("exit")) {
        System.out.println("YouTube has now terminated its execution. " +
            "Thank you and goodbye!");
        return;
      }
      parser.executeCommand(Arrays.asList(input.split("\\s+")));
    }
  }
}

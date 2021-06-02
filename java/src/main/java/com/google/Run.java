package com.google;

import java.util.Arrays;
import java.util.Scanner;

public class Run {
  public static void main(String[] args){
    System.out.println("Hello and welcome to YouTube, what would you like to do? "
        + "Enter HELP for list of available commands or EXIT to terminate.");
    var parser = new CommandParser();
    var scanner = new Scanner(System.in);
    while (true) {
      var input = scanner.nextLine();
      parser.executeCommand(Arrays.asList(input.split("\\s+")));
    }
  }
}

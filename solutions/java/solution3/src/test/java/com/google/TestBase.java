package com.google;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class TestBase {
    protected final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    protected VideoPlayer videoPlayer;
    private InputStream stdin;

    @BeforeEach
    public void setUp() {
      System.setOut(new PrintStream(outputStream));
      videoPlayer = new VideoPlayer();
      stdin = System.in;
    }

    @AfterEach
    public void tearDown() {
        System.setIn(stdin);
    }

    String[] getOutputLines() {
        return outputStream.toString().split("\\r?\\n");
    }

    void setInput(String str) {
      System.setIn(new ByteArrayInputStream((str + "\r\n").getBytes()));
    }
}

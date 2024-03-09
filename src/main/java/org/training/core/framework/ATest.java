package org.training.core.framework;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;

public class ATest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;
  private static final PrintStream originalErr = System.err;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  public List<String> getOutContent() {
    return Arrays.asList(outContent.toString().split("\\R"));
  }

  public boolean outContentIsEmpty() {
    return outContent.toString().isBlank();
  }

  public List<String> getErrContent() {
    return Arrays.asList(errContent.toString().split("\\R"));
  }

  public boolean errContentIsEmpty() {
    return errContent.toString().isBlank();
  }

  public void clearOutContent() {
    outContent.reset();
  }

  public void clearErrContent() {
    errContent.reset();
  }

  public void clearAllContent() {
    clearOutContent();
    clearErrContent();
  }


}

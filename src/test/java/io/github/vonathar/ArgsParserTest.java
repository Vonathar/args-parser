package io.github.vonathar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ArgsParserTest {

  @Test
  public void parse_ArgumentWithoutFlag_ShouldThrowException() {
    String[] args = new String[] {"value"};

    assertThrows(IllegalArgumentException.class, () -> new ArgsParser(args));
  }

  @Test
  public void get_SingleArgument_SingleHyphen_ShouldReturnArgumentAsString() {
    String[] args = new String[] {"-flag", "value"};
    ArgsParser argsParser = new ArgsParser(args);

    String expected = "value";
    String actual = argsParser.get("flag");

    assertEquals(expected, actual);
  }

  @Test
  public void get_MultipleArguments_SingleHyphen_ShouldReturnArgumentsAsString() {
    String[] args = new String[] {"-one", "first", "--two", "second", "-three", "third"};
    ArgsParser argsParser = new ArgsParser(args);

    String expected1 = "first";
    String actual1 = argsParser.get("one");
    String expected2 = "second";
    String actual2 = argsParser.get("two");
    String expected3 = "third";
    String actual3 = argsParser.get("three");

    assertEquals(expected1, actual1);
    assertEquals(expected2, actual2);
    assertEquals(expected3, actual3);
  }

  @Test
  public void get_SingleArgument_DoubleHyphen_ShouldReturnArgumentAsString() {
    String[] args = new String[] {"--flag", "value"};
    ArgsParser argsParser = new ArgsParser(args);

    String expected = "value";
    String actual = argsParser.get("flag");

    assertEquals(expected, actual);
  }

  @Test
  public void get_MultipleArguments_DoubleHyphen_ShouldReturnArgumentsAsString() {
    String[] args = new String[] {"--one", "value", "--two", "another"};
    ArgsParser argsParser = new ArgsParser(args);

    String expected1 = "value";
    String actual1 = argsParser.get("one");
    String expected2 = "another";
    String actual2 = argsParser.get("two");

    assertEquals(expected1, actual1);
    assertEquals(expected2, actual2);
  }

  @Test
  public void get_MultipleArguments_MixedHyphens_ShouldReturnArgumentsAsString() {
    String[] args = new String[] {"--one", "value", "-two", "another"};
    ArgsParser argsParser = new ArgsParser(args);

    String expected1 = "value";
    String actual1 = argsParser.get("one");
    String expected2 = "another";
    String actual2 = argsParser.get("two");

    assertEquals(expected1, actual1);
    assertEquals(expected2, actual2);
  }

  @Test
  public void get_NonExistingArgument_ShouldThrowException() {
    String[] args = new String[] {};
    ArgsParser argsParser = new ArgsParser(args);

    assertThrows(IllegalArgumentException.class, () -> argsParser.get("one"));
  }

  @Test
  public void has_ExistingFlag_ShouldReturnTrue() {
    String[] args = new String[] {"--one"};
    ArgsParser argsParser = new ArgsParser(args);

    assertTrue(argsParser.has("one"));
  }

  @Test
  public void has_NonExistingFlag_ShouldReturnFalse() {
    String[] args = new String[] {};
    ArgsParser argsParser = new ArgsParser(args);

    assertFalse(argsParser.has("one"));
  }
}

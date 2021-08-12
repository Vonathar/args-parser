package io.github.vonathar;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
  public void getAll_SingleArgument_ShouldReturnArgumentAsStringArray() {
    ArgsParser argsParser1 = new ArgsParser(new String[] {"-one", "first"});
    ArgsParser argsParser2 = new ArgsParser(new String[] {"--one", "first"});

    assertEquals("first", argsParser1.getAll("one")[0]);
    assertEquals("first", argsParser2.getAll("one")[0]);
  }

  @Test
  public void getAll_MultipleArguments_ShouldReturnArgumentsAsStringArray() {
    ArgsParser argsParser = new ArgsParser(new String[] {"--one", "first", "second", "third"});

    assertArrayEquals(new String[] {"first", "second", "third"}, argsParser.getAll("one"));
  }

  @Test
  public void getSingle_SingleArgument_ShouldReturnArgumentAsString() {
    ArgsParser argsParser1 = new ArgsParser(new String[] {"-one", "first"});
    ArgsParser argsParser2 = new ArgsParser(new String[] {"--one", "first"});

    assertEquals("first", argsParser1.getFirst("one"));
    assertEquals("first", argsParser2.getFirst("one"));
  }

  @Test
  public void getSingle_MultipleArguments_MixedHyphens_ShouldReturnArgumentsAsString() {
    ArgsParser argsParser =
        new ArgsParser(new String[] {"--one", "first", "-two", "second", "--three", "third"});

    assertEquals("first", argsParser.getFirst("one"));
    assertEquals("second", argsParser.getFirst("two"));
    assertEquals("third", argsParser.getFirst("three"));
  }

  @Test
  public void getSingle_NonExistingArgument_ShouldThrowException() {
    String[] args = new String[] {};
    ArgsParser argsParser = new ArgsParser(args);

    assertThrows(IllegalArgumentException.class, () -> argsParser.getFirst("one"));
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

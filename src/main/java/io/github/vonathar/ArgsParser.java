package io.github.vonathar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArgsParser {

  private final Map<String, String[]> arguments = new HashMap<>();
  private final Set<String> options = new HashSet<>();

  public ArgsParser(String[] args) {
    parse(args);
  }

  private void parse(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (i + 1 == args.length) {
        if (!isFlag(args[i])) {
          throw new IllegalArgumentException("Argument without flag found: " + args[i]);
        }
        options.add(args[i].replaceAll("-", ""));
      }
      if (isFlag(args[i])) {
        String flag = args[i].replaceAll("-", "");
        if (i + 1 < args.length) {
          if (isFlag(args[i + 1])) {
            options.add(flag);
          }
          String value = args[i + 1];
          arguments.put(flag, new String[] {value});
          i++;
        }
      }
    }
  }

  private boolean isFlag(String arg) {
    return arg.startsWith("-") || arg.startsWith("--");
  }

  public String getFirst(String flag) {
    if (!arguments.containsKey(flag)) {
      throw new IllegalArgumentException(String.format("Argument does not exist: %s", flag));
    }
    return arguments.get(flag)[0];
  }

  public String[] getAll(String flag) {
    if (!arguments.containsKey(flag)) {
      throw new IllegalArgumentException(String.format("Argument does not exist: %s", flag));
    }
    return arguments.get(flag);
  }

  public boolean has(String flag) {
    return options.contains(flag);
  }
}

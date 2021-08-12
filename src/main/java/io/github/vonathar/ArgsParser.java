package io.github.vonathar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArgsParser {

  private final Map<String, String> arguments = new HashMap<>();
  private final Set<String> options = new HashSet<>();

  public ArgsParser(String[] args) {
    parse(args);
  }

  private void parse(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (i + 1 == args.length) {
        if (!args[i].startsWith("-") || !args[i].startsWith("--")) {
          throw new IllegalArgumentException("Argument without flag found: " + args[i]);
        }
        options.add(args[i].replaceAll("-", ""));
      }
      if (args[i].startsWith("--") || args[i].startsWith("-")) {
        String flag = args[i].replaceAll("-", "");
        if (i + 1 < args.length) {
          if (args[i + 1].startsWith("--") || args[i + 1].startsWith("-")) {
            options.add(flag);
          }
          String value = args[i + 1];
          arguments.put(flag, value);
          i++;
        }
      }
    }
  }

  public String get(String flag) {
    if (!arguments.containsKey(flag)) {
      throw new IllegalArgumentException(String.format("Argument does not exist: %s", flag));
    }
    return arguments.get(flag);
  }

  public boolean has(String flag) {
    return options.contains(flag);
  }
}

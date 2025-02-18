package com.example.author_finder.util;

import java.util.UUID;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class OpenLibraryIdGenerator implements IdentifierGenerator {
  private static final String AUTHOR_PREFIX = "OL";
  private static final String AUTHOR_SUFFIX = "A";
  private static final String WORK_PREFIX = "OL";
  private static final String WORK_SUFFIX = "W";

  @Override
  public Object generate(SharedSessionContractImplementor session, Object object) {
    String prefix = AUTHOR_PREFIX;
    String suffix = AUTHOR_SUFFIX;

    if (object.getClass().getSimpleName().toLowerCase().contains("work")) {
      prefix = WORK_PREFIX;
      suffix = WORK_SUFFIX;
    }

    // Generate a random number between 10000000 and 99999999
    long randomNum = Math.abs(UUID.randomUUID().getLeastSignificantBits() % 90000000) + 10000000;
    return String.format("%s%d%s", prefix, randomNum, suffix);
  }

  public static String extractId(String key, boolean isWork) {
    if (key == null) {
      return null;
    }
    return key.replace(isWork ? "/works/" : "/authors/", "");
  }
} 
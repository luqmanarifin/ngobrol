package com.ngobrol.server.common;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by luqmanarifin on 02/11/16.
 */

public final class SessionIdentifierGenerator {
  private static SecureRandom random = new SecureRandom();

  public static String nextSessionId() {
    return new BigInteger(130, random).toString(32);
  }
}
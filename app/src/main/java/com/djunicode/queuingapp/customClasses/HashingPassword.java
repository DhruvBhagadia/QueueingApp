package com.djunicode.queuingapp.customClasses;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by DELL_PC on 23-01-2018.
 */

public class HashingPassword {

  private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  public String generate_salt() {
    int count = 16;
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
      builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }
    return builder.toString();
  }

  public String hash_the_password(String input) {
    StringBuilder hash = new StringBuilder();

    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      byte[] hashedBytes = sha.digest(input.getBytes());
      char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
          'a', 'b', 'c', 'd', 'e', 'f' };
      for (int idx = 0; idx < hashedBytes.length; ++idx) {
        byte b = hashedBytes[idx];
        hash.append(digits[(b & 0xf0) >> 4]);
        hash.append(digits[b & 0x0f]);
      }
    } catch (NoSuchAlgorithmException e) {
      // handle error here.
    }

    return hash.toString();
  }

  public boolean isExpectedPassword(String password, String salt, String expectedHash) {
    String fina = password + salt;
    String s = hash_the_password(fina);
    if(s.equals(expectedHash))
      return true;
    else
      return false;
  }

}

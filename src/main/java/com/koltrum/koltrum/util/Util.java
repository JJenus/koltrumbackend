package com.koltrum.koltrum.util;

import java.security.SecureRandom;

public class Util {
    public static long generateToken(int numDigits) {
        SecureRandom secureRandom = new SecureRandom();
        long min = (long) Math.pow(10, numDigits); // Minimum number with specified digits
//        long max = (long) Math.pow(10, numDigits) - 1; // Maximum number with specified digits
//        4747504016105446848
        return Math.abs(secureRandom.nextLong() % (1 + min));
    }

}

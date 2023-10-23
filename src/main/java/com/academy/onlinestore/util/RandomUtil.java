package com.academy.onlinestore.util;

import java.util.Random;

public class RandomUtil {

    // Generate 6 digits random Number.
    public static String generateCode() {
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}

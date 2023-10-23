package com.academy.onlinestore.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

public class GeneratorUtil {
    public static String generateUniqueFileName() {
        String filename = "";
        long millis = System.currentTimeMillis();
        String datetime = new Date().toGMTString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        String randomChars = RandomStringUtils.randomAlphanumeric(16);
        filename = randomChars + "_" + datetime + "_" + millis;
        return filename;
    }
}

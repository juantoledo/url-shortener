package io.houmlab.urlshortener.utils;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class UrlUtils {

    private static final int CODE_LENGTH = 8;
    private static final int RANDOM_CHARS_NUMBER = 200;

    public static String generateCode() {

        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        StringBuffer generatedBuffer = new StringBuffer();

        RandomStringUtils.randomAlphanumeric(10);

        sb.append(UUID.randomUUID().toString());
        sb.append(String.valueOf(System.currentTimeMillis()));
        sb.append(RandomStringUtils.randomAlphanumeric(RANDOM_CHARS_NUMBER));

        for (int i = 0; i < CODE_LENGTH; i++) {
            generatedBuffer.append(sb.toString().charAt(random.nextInt(sb.length())));
        }

        return generatedBuffer.toString();

    }

}
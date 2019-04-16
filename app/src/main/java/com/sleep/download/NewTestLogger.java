package com.sleep.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewTestLogger {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(NewTestLogger.class);
        logger.info("Hello World");
    }
}
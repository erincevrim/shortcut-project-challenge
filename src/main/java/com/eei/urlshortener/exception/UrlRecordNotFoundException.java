package com.eei.urlshortener.exception;

public class UrlRecordNotFoundException extends Exception {
    public UrlRecordNotFoundException(String key) {
        super("Incorrect url:" + key);
    }
}

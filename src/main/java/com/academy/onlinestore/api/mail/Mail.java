package com.academy.onlinestore.api.mail;

import lombok.Data;

@Data
public class Mail<T> {
    private String sender;
    private String receiver;
    private String username;
    private String subject;
    private String template;
    private T metadata;
}

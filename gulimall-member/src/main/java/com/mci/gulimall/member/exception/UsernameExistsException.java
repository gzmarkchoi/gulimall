package com.mci.gulimall.member.exception;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super("User name already exist!");
    }
}

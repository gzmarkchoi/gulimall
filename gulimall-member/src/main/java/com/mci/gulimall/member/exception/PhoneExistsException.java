package com.mci.gulimall.member.exception;

public class PhoneExistsException extends RuntimeException {
    public PhoneExistsException() {
        super("Phone number already exist!");
    }
}

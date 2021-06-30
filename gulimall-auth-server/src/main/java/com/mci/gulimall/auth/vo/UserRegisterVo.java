package com.mci.gulimall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterVo {
    @NotEmpty(message = "User name must not be empty")
    @Length(min = 6, max = 18, message = "User name must be between 6-18 characters")
    private String userName;

    @NotEmpty(message = "Password must not be empty")
    @Length(min = 6, max = 18, message = "Password must be between 6-18 characters")
    private String password;

    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "Phone number format not correct")
    private String phone;

    @NotEmpty(message = "Verification code must not be empty")
    private String code;
}

package com.oauth2.securityoauth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$",message = "Tên đăng nhập không đúng định dạng") // ký tự đầu tien la chu; ky tu theo sau la chu thuong, in, _; do dai ky tu theo sau ky tu dau la 7-29
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^(.+)@(.+)$",message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", message = "Mật khẩu không đúng định dạng") // it nhất 1 ký tự thường, hoa, chữ só,ky tu dăc biet, do dai toi thieu 8
    private String password;

    Set<Long> roles;
}

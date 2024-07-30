package com.oauth2.securityoauth.dto.request;

import com.oauth2.securityoauth.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;
@Data
public class RegisterRequest {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$",message = "Tên đăng nhập không đúng định dạng") // ký tự đầu tien la chu; ky tu theo sau la chu thuong, in, _; do dai ky tu theo sau ky tu dau la 7-29
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^(.+)@(.+)$",message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$", message = "Mật khẩu không đúng định dạng") // ít nhất 1 chữ thường, chữ hoa, chữ số, ký tự đặc biệt, độ dài tối thiểu 8 ký tự
    private String password;
}

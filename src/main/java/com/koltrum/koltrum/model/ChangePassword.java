package com.koltrum.koltrum.model;

import lombok.Data;

@Data
public class ChangePassword {
    private Long userId;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}

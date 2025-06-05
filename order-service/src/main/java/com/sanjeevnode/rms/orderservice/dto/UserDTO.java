package com.sanjeevnode.rms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String role;
    private String createdAt;
    private String updatedAt;
}

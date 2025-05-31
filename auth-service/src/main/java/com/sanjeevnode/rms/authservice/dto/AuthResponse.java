package com.sanjeevnode.rms.authservice.dto;

import lombok.Data;

public record AuthResponse(
        String username,
        String role,
        String token
) {
}

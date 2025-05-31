package com.sanjeevnode.rms.authservice.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;

    public ResponseEntity<ApiResponse> buildResponse() {
        return  ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .status(status)
                        .message(message)
                        .data(data)
                        .build()
        );
    }
}

package com.example.studentmanager.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;
}
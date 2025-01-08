package com.Thomas.ChattingWeb.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

    private String error;
    private String message;
    private LocalDateTime timestamp;


}
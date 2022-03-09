package com.example.microservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CustomException extends Exception{

    private String message;
    private String description;



}

package com.gpronoitis.todomanagment.exception;

import org.springframework.http.HttpStatus;

public class TodoAPIException extends RuntimeException{

    private HttpStatus status;


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }


    public TodoAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;

    }
}

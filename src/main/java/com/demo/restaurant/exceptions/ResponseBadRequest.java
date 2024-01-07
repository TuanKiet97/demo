package com.demo.restaurant.exceptions;

public class ResponseBadRequest extends RuntimeException {
    public ResponseBadRequest(String message) {
	super(message);
    }
}

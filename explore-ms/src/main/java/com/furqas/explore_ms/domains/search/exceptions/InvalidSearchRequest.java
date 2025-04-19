package com.furqas.explore_ms.domains.search.exceptions;

public class InvalidSearchRequest extends RuntimeException {
    public InvalidSearchRequest(String message) {
        super(message);
    }
}

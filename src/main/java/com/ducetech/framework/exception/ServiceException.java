package com.ducetech.framework.exception;

public class ServiceException extends BaseRuntimeException{

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

package com.dev.exception;

/**
 * Application exception defined for indicating failure of different robot operations.
 * 
 * @author Piyush.Baheti
 *
 */
public class RobotOperateException extends RuntimeException {

    private static final long serialVersionUID = 2616819182010216982L;

    private String message;

    public RobotOperateException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

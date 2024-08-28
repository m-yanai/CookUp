package com.example.rhizome.exception;

/**
 * 権限不足の例外を表現するクラスです。
 */
public class NoSessionException extends RuntimeException {

    public NoSessionException(String message) {
        super(message);
    }

}

package com.acoes.bolsa.exception;


public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Usuario já existe");
    }
}

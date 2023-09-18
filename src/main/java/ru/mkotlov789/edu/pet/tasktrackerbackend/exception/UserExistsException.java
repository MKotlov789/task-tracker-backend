package ru.mkotlov789.edu.pet.tasktrackerbackend.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException(){
        super();
    }
    public UserExistsException(String msg) {
        super(msg);
    }
}

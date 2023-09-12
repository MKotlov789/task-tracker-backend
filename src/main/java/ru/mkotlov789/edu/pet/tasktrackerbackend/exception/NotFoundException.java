package ru.mkotlov789.edu.pet.tasktrackerbackend.exception;


public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super();
    }
    public NotFoundException(String msg) {
        super(msg);
    }
}
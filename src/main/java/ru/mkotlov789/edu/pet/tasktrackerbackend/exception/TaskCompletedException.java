package ru.mkotlov789.edu.pet.tasktrackerbackend.exception;

public class TaskCompletedException extends RuntimeException{

    public TaskCompletedException(){
        super();
    }
    public TaskCompletedException(String msg) {
        super(msg);
    }
}

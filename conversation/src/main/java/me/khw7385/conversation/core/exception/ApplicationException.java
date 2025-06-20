package me.khw7385.conversation.core.exception;

public class ApplicationException extends RuntimeException{
    public ApplicationException(String message) {
        super(message);
    }

    public String getMessage(){
        return super.getMessage();
    }
}

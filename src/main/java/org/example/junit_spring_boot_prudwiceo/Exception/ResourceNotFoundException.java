package org.example.junit_spring_boot_prudwiceo.Exception;

public class ResourceNotFoundException  extends RuntimeException{

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }

    public ResourceNotFoundException(String msg,Throwable reason)
    {
        super(msg,reason);
    }
}

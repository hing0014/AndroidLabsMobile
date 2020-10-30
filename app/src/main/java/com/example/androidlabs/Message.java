package com.example.androidlabs;

public class Message
{
    protected String message, sender;
    protected long id;

    public Message(String mess, String sndr, long i)
    {
        this.message = mess;
        this.sender = sndr;
        this.id = i;
    }

    public String getMessage() {
        return message;
    }
    public String getSender() {
        return sender;
    }
    public long getId() {
        return id;
    }
}

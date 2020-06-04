package com.example.myapplication1;

import java.util.Calendar;

public class Mail {
    long dateInMS;
    String title, sender, message, recipient;
    boolean checked;

    public Mail(){
    }

    public Mail(String title, String message, String sender, String recipient) {
        this.dateInMS = Calendar.getInstance().getTimeInMillis();
        this.title = title;
        this.sender = sender;
        this.message = message;
        this.checked = false;
        this.recipient = recipient;
    }

    public long getDateInMS() {return dateInMS;}
    public String getTitle() {return title;}
    public String getSender() {return sender; }
    public String getMessage() {return message;}
    public boolean isChecked() {return checked;}
    public String getRecipient() {return recipient;}

}

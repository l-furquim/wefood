package com.lucas.mailsender_ms.domains.mail.enums;

public enum MailType {

    PAYMENT(1),
    ORDER(2),
    PROFILE(3);

    private int value;

    MailType(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}

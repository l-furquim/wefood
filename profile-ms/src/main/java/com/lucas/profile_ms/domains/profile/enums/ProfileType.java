package com.lucas.profile_ms.domains.profile.enums;

public enum ProfileType {
    COMUM(1),
    WAITING_CONFIRMATION(2),
    RESTAURANT(3);

    private int value;

    ProfileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}

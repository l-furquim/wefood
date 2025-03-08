package com.lucas.profile_ms.domains.profile.enums;

public enum ProfileType {
    COMUM(1),
    RESTAURANT(2);

    private int value;

    ProfileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}

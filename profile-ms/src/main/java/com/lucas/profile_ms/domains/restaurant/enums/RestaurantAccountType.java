package com.lucas.profile_ms.domains.restaurant.enums;

public enum RestaurantAccountType {
    MAIN(1),
    BRANCH(2),
    WAITING_CONFIRMATION(3);

    private int value;

    RestaurantAccountType(int value){
        this.value = value;
    }

     public Integer getValue(){
        return this.value;
    }
}

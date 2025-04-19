package com.furqas.explore_ms.domains.search.enums;

public enum SearchCategory {

    NONE(0),
    RESTAURANT(2),
    CONVENIENCE(3);

    private Integer code;

    SearchCategory(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

package com.DesafioAttornatus.entity.enums;

public enum Principal {
    Sim("Sim"),
    Nao("Não");

    private String code;

    Principal(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

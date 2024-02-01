package com.gabrielazevedo.apiserasa.enums;

import lombok.Getter;

@Getter
public enum PersonStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    private String status;

    PersonStatus(String status) {
        this.status = status;
    }

}

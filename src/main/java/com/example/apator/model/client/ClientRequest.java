package com.example.apator.model.client;

import lombok.Data;

@Data
public class ClientRequest {
    private String name;
    private Boolean isVIP;
    private Boolean isUrgent;
    private String pin;
}

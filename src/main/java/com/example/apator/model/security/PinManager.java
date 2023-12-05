package com.example.apator.model.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PinManager {
    @Value("${app.pinVip}")
    private String pinVip;

    @Value("${app.pinUrgent}")
    private String pinUrgent;
}

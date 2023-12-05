package com.example.apator.model.client;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@RequiredArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientNumber;
    private String name;
    @Column(name = "is_vip")
    private Boolean isVIP;
    private Boolean isUrgent;
    private LocalDateTime processingStartTime;
    private LocalDateTime timeAdded;

}

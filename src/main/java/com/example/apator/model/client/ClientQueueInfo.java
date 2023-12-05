package com.example.apator.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientQueueInfo {
    private List<ClientDTO> listClientDTO;
    private int timeToWait;
}

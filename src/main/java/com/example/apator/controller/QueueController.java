package com.example.apator.controller;

import com.example.apator.model.client.ClientDTO;
import com.example.apator.model.client.ClientQueueInfo;
import com.example.apator.model.client.ClientRequest;
import com.example.apator.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/clients")
    public List<ClientDTO> getClient(){
      return queueService.getClientDTO();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/clientToMonitor")
    public ClientQueueInfo clientToMonitor(@RequestParam String clientInfo) {
        return queueService.getClientListBasedOnClientNumber(clientInfo);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody ClientRequest clientRequest) {
        return queueService.addClientToDB(clientRequest);
    }

}

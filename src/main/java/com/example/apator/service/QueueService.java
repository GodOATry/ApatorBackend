package com.example.apator.service;


import com.example.apator.model.client.Client;
import com.example.apator.model.client.ClientDTO;
import com.example.apator.model.client.ClientQueueInfo;
import com.example.apator.model.client.ClientRequest;
import com.example.apator.model.enums.ModificationTypeDB;
import com.example.apator.model.security.PinManager;
import com.example.apator.model.comparators.ClientComparator;
import com.example.apator.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private final PinManager pinManager;
    private final Lock lockForScheduled = new ReentrantLock();
    private final Object lockForModificationDB = new Object();
    private volatile boolean isExecuting = false;


    public List<Client> getClient(){
        LinkedList<Client> clientList = new LinkedList<>(queueRepository.findAll());
        clientList.sort(new ClientComparator());
        return clientList;
    }

    public List<ClientDTO> getClientDTO(){
        List<Client> clientList = getClient();
        return clientList.stream().map(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setClientNumber(client.getClientNumber());
            dto.setName(client.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public boolean isValidVipPin(String password) {
        return  pinManager.getPinVip().equals(password);
    }
    public boolean isValidUrgentPin(String password) {
        return  pinManager.getPinUrgent().equals(password);
    }
    public ResponseEntity<String> addClientToDB(ClientRequest clientRequest) {
        Client client = new Client();
        client.setName(clientRequest.getName());
        client.setIsVIP(clientRequest.getIsVIP());
        client.setIsUrgent(clientRequest.getIsUrgent());
        client.setProcessingStartTime(null);
        client.setTimeAdded(LocalDateTime.now());
        client.setClientNumber(System.currentTimeMillis());
        String pin = clientRequest.getPin();

        if (clientRequest.getIsVIP() && !isValidVipPin(pin)){
            return ResponseEntity.badRequest().body("Invalid pin for VIP");
        }
        else if (clientRequest.getIsUrgent() && !isValidUrgentPin(pin)){
            return ResponseEntity.badRequest().body("Invalid pin for Urgent");
        }
        try {
           queueRepository.save(client);
           return ResponseEntity.ok("Client added to the queue successfully!");
        }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding client to the queue.");
        }
    }

    public ClientQueueInfo getClientListBasedOnClientNumber(String clientInfo) {
        List<Client> clientList = getClient();
        List<Client> clientListToSend = new ArrayList<>();
        int timeOfWaiting =0;
        for (Client client : clientList) {
            clientListToSend.add(client);
            timeOfWaiting += client.getIsUrgent() ? 60 : 20;
            if (client.getName().equals(clientInfo) || client.getClientNumber().toString().equals(clientInfo)) {
                ClientQueueInfo clientQueueInfo = new ClientQueueInfo();
                clientQueueInfo.setListClientDTO(clientListToSend.stream().map(client_1 -> {
                    ClientDTO dto = new ClientDTO();
                    dto.setClientNumber(client_1.getClientNumber());
                    dto.setName(client_1.getName());
                    return dto;
                }).collect(Collectors.toList()));
                clientQueueInfo.setTimeToWait(timeOfWaiting);


                return clientQueueInfo;
            }

        }
        return new ClientQueueInfo(Collections.emptyList(),0);

    }


    public void modifyClientInDB(Client client, ModificationTypeDB modificationTypeDB){
        synchronized (lockForModificationDB){
            switch (modificationTypeDB) {
                case UPDATE:
                    queueRepository.save(client);
                    break;
                case DELETE:
                    queueRepository.delete(client);
                    break;
            }
        }
    }

    @Scheduled(fixedRate = 1000)
    public void simulatePostOffice(){
        if (!isExecuting) {
            try {
                isExecuting = true;
                lockForScheduled.lock();
                LinkedList<Client> clientList = new LinkedList<>(queueRepository.findAll());
                clientList.sort(new ClientComparator());
                System.out.println("Scheduled method executed at: " + System.currentTimeMillis());
                if(!clientList.isEmpty()){
                    if(clientList.getFirst().getProcessingStartTime() != null){
                        if(clientList.getFirst().getIsUrgent()){
                            if(Duration.between(clientList.getFirst().getProcessingStartTime(),LocalDateTime.now()).getSeconds()>=60){
                                modifyClientInDB(clientList.getFirst(),ModificationTypeDB.DELETE);
                            }
                        }else {
                            if(Duration.between(clientList.getFirst().getProcessingStartTime(),LocalDateTime.now()).getSeconds()>=20){
                                modifyClientInDB(clientList.getFirst(),ModificationTypeDB.DELETE);
                            }
                        }
                    }else {
                        clientList.getFirst().setProcessingStartTime(LocalDateTime.now());
                        modifyClientInDB(clientList.getFirst(),ModificationTypeDB.UPDATE);
                    }
                }

            } finally {
                isExecuting = false;
                lockForScheduled.unlock();
            }
        } else {
            System.out.println("Previous execution still in progress. Skipping this scheduled run.");
        }
    }

}

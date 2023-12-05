package com.example.apator.model.comparators;
import com.example.apator.model.client.Client;
import java.util.Comparator;

public class ClientComparator implements Comparator<Client>{

    @Override

    public int compare(Client client1, Client client2) {
        if (client1.getProcessingStartTime() != null && client2.getProcessingStartTime() == null) {
            return -1;
        } else if (client1.getProcessingStartTime() == null && client2.getProcessingStartTime() != null) {
            return 1;
        }
        int urgentComparison = Boolean.compare(client2.getIsUrgent(), client1.getIsUrgent());
        if (urgentComparison != 0) {
            return urgentComparison;
        }

        int vipComparison = Boolean.compare(client2.getIsVIP(), client1.getIsVIP());
        if (vipComparison != 0) {
            return vipComparison;
        }

        return client1.getTimeAdded().compareTo(client2.getTimeAdded());
    }

}

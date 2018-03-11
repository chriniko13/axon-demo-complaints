package com.example.democomplaints.event_processor;

import com.example.event.ComplaintFiledEvent;
import com.example.democomplaints.query.ComplaintQueryObject;
import com.example.democomplaints.query.ComplaintQueryObjectRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class ComplaintQueryObjectUpdater {

    private final ComplaintQueryObjectRepository complaintQueryObjectRepository;

    public ComplaintQueryObjectUpdater(ComplaintQueryObjectRepository complaintQueryObjectRepository) {
        this.complaintQueryObjectRepository = complaintQueryObjectRepository;
    }

    @EventHandler
    public void on(ComplaintFiledEvent complaintFiledEvent) {
        complaintQueryObjectRepository.save(
                new ComplaintQueryObject(
                        complaintFiledEvent.getId(),
                        complaintFiledEvent.getCompany(),
                        complaintFiledEvent.getDescription()
                )
        );
    }

}
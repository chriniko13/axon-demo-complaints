package com.example.democomplaints.command.handler;


import com.example.democomplaints.command.FileComplaintCommand;
import com.example.event.ComplaintFiledEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

@Aggregate
public class Complaint {

    @AggregateIdentifier
    private String complaintId;

    public Complaint() {
    }

    @CommandHandler
    public Complaint(FileComplaintCommand fileComplaintCommand) {

        Assert.hasLength(fileComplaintCommand.getCompany());

        AggregateLifecycle.apply(
                new ComplaintFiledEvent(fileComplaintCommand.getId(),
                        fileComplaintCommand.getCompany(),
                        fileComplaintCommand.getDescription()
                )
        );
    }

    @EventSourcingHandler
    public void on(ComplaintFiledEvent complaintFiledEvent) {
        this.complaintId = complaintFiledEvent.getId();
    }

}

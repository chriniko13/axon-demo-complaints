package com.example.democomplaints.controller;

import com.example.democomplaints.command.FileComplaintCommand;
import com.example.democomplaints.query.ComplaintQueryObject;
import com.example.democomplaints.query.ComplaintQueryObjectRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class ComplaintsController {

    private final ComplaintQueryObjectRepository complaintQueryObjectRepository;
    private final CommandGateway commandGateway;


    public ComplaintsController(ComplaintQueryObjectRepository complaintQueryObjectRepository, CommandGateway commandGateway) {
        this.complaintQueryObjectRepository = complaintQueryObjectRepository;
        this.commandGateway = commandGateway;
    }

    // --- START: queries ---
    @GetMapping("/complaints")
    public List<ComplaintQueryObject> findAll() {
        return complaintQueryObjectRepository.findAll();
    }

    @GetMapping("/complaints/{id}")
    public ComplaintQueryObject find(@PathVariable("id") String id) {
        Optional<ComplaintQueryObject> result = complaintQueryObjectRepository.findById(id);
        return result.orElse(null);
    }
    // --- END: queries ---


    // --- START: commands ---

    @PostMapping
    public CompletableFuture<String> fileComplaint(@RequestBody Map<String, String> request) {
        String id = UUID.randomUUID().toString();
        return commandGateway.send(new FileComplaintCommand(id, request.get("company"), request.get("description")));
    }
    // --- END: commands ---


}

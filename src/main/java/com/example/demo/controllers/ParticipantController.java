package com.example.demo.controllers;

import com.example.demo.dto.ParticipantRequest;
import com.example.demo.dto.ParticipantResponse;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.Participant;
import com.example.demo.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllParticipants() {
        List<ParticipantResponse> participantResponses = participantService.getAllParticipants();
        ApiResponse response = new ApiResponse(participantService.getStatusMessage(), participantService.getResponseMessage(), participantResponses);

        if (!participantResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getParticipantById(@PathVariable("id") Long id) {
        ParticipantResponse participant = participantService.getParticipantById(id);
        ApiResponse response = new ApiResponse(participantService.getStatusMessage(), participantService.getResponseMessage(), participant);

        if (participant != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addParticipant(@RequestBody ParticipantRequest participantRequest) {
        Participant participant = participantService.addParticipant(participantRequest);

        if (participant != null) {
            ParticipantResponse participantResponse = new ParticipantResponse(participant.getName(), participant.getAddress(), participant.getPhone(), participant.getActive());
            ApiResponse response = new ApiResponse(participantService.getStatusMessage(), participantService.getResponseMessage(), participantResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            ApiResponse response = new ApiResponse(participantService.getStatusMessage(), participantService.getResponseMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateParticipant(@PathVariable("id") Long id, @RequestBody ParticipantRequest participantRequest) {
        Participant participant = participantService.updateParticipant(id, participantRequest);

        if (participant != null) {
            ParticipantResponse participantResponse = new ParticipantResponse(participant.getName(), participant.getAddress(), participant.getPhone(), participant.getActive());
            ApiResponse response = new ApiResponse(participantService.getStatusMessage(), participantService.getResponseMessage(), participantResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            ApiResponse response = new ApiResponse(participantService.getStatusMessage(), participantService.getResponseMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteParticipant(@PathVariable("id") Long id) {
        Participant participants = participantService.deleteParticipant(id);
        ApiResponse response = new ApiResponse(participantService.getStatusMessage(), participantService.getResponseMessage(), null);

        if (participants != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
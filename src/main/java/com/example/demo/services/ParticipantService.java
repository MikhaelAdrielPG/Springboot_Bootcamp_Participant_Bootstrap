package com.example.demo.services;

import com.example.demo.dto.ParticipantRequest;
import com.example.demo.dto.ParticipantResponse;
import com.example.demo.model.Participant;
import com.example.demo.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private String statusMessage;
    private String responseMessage;
    public String getStatusMessage() {
        return statusMessage;
    }
    public String getResponseMessage() {
        return responseMessage;
    }

    public List<ParticipantResponse> getAllParticipants() {
        List<Participant> participants = participantRepository.findAllByDeletedAtIsNull();
        List<ParticipantResponse> participantResponses = new ArrayList<>();

        if (participants.isEmpty()) {
            statusMessage = FAILED;
            responseMessage = "Data doesn't exist, please insert new participant data.";
        } else {
            for (Participant participant : participants) {
                ParticipantResponse participantResponse = new ParticipantResponse(participant.getName(), participant.getAddress(), participant.getPhone(), participant.getActive());
                participantResponses.add(participantResponse);
            }
            statusMessage = SUCCESS;
            responseMessage = "Data successfully loaded.";
        }
        return participantResponses;
    }


    public ParticipantResponse getParticipantById(Long id) {
        Optional<Participant> participantOptional = participantRepository.findByIdAndDeletedAtIsNull(id);

        if (!participantOptional.isPresent()) {
            statusMessage = FAILED;
            responseMessage = "Sorry, participant id not found.";
            return null;
        } else {
            Participant participant = participantOptional.get();
            ParticipantResponse participantResponse = new ParticipantResponse(participant.getName(), participant.getAddress(), participant.getPhone(), participant.getActive());
            statusMessage = SUCCESS;
            responseMessage = "Data Successfully loaded.";
            return participantResponse;
        }
    }

    @Transactional
    public Participant addParticipant(ParticipantRequest request) {
        if (!isValidName(request.getName())) {
            statusMessage = FAILED;
            responseMessage = "Name cannot contain special characters or digits.";
        } else if (!isValidPhoneNumber(request.getPhone())) {
            statusMessage = SUCCESS;
            responseMessage = "Phone number must be between 10 and 13 characters long.";
        } else if (!isValidAddress(request.getAddress())) {
            statusMessage = FAILED;
            responseMessage = "Address cannot be empty.";
        } else {
            Participant participant = new Participant();
            participant.setName(request.getName());
            participant.setAddress(request.getAddress());
            participant.setPhone(request.getPhone());
            participant.setActive(request.getActive());

            statusMessage = SUCCESS;
            responseMessage = "Data Successfully Added!";
            return participantRepository.save(participant);
        }
        return null;
    }

    @Transactional
    public Participant updateParticipant(Long id, ParticipantRequest request) {
        Optional<Participant> participantOptional = participantRepository.findByIdAndDeletedAtIsNull(id);

        if (!isValidName(request.getName())) {
            statusMessage = FAILED;
            responseMessage = "Name cannot contain special characters or digits.";
        } else if (!isValidPhoneNumber(request.getPhone())) {
            statusMessage = FAILED;
            responseMessage = "Phone number must be between 10 and 13 characters long.";
        } else if (!isValidAddress(request.getAddress())) {
            statusMessage = FAILED;
            responseMessage = "Address cannot be empty.";
        } else {
            if (participantOptional.isPresent()) {
                Participant participant = participantOptional.get();
                participant.setName(request.getName());
                participant.setAddress(request.getAddress());
                participant.setPhone(request.getPhone());
                participant.setActive(request.getActive());

                statusMessage = SUCCESS;
                responseMessage = "Data Successfully Updated!";
                return participantRepository.save(participant);
            } else {
                statusMessage = FAILED;
                responseMessage = "Failed to Update Data.";
            }
        }
        return null;
    }

    public Participant deleteParticipant(Long id) {
        Optional<Participant> existingParticipantOptional = participantRepository.findByIdAndDeletedAtIsNull(id);

        if (existingParticipantOptional.isPresent()) {
            Participant existingParticipant = existingParticipantOptional.get();
            existingParticipant.setDeletedAt(new Date());
            statusMessage = SUCCESS;
            responseMessage = "Data Successfully Removed.";
            return participantRepository.save(existingParticipant);
        } else {
            statusMessage = FAILED;
            responseMessage = "Failed to Remove Data";
            return null;
        }
    }

    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z ]+$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() >= 10 && phoneNumber.length() <= 13 && phoneNumber.matches("\\d+");
    }

    private boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }
}
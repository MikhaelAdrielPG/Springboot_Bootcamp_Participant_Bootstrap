package com.example.demo.repositories;

import com.example.demo.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findAllByDeletedAtIsNull();
    Optional<Participant> findByIdAndDeletedAtIsNull(Long id);
}
package com.ericbeaubrun.presencesys.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
    Optional<StudentEntity> findByCardId(String cardId);
}
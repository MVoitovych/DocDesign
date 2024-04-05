package org.example.dental.dao;

import org.example.dental.models.AdditionalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalInformationRepository extends JpaRepository<AdditionalInformation, Integer> {
}

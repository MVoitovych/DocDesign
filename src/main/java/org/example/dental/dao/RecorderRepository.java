package org.example.dental.dao;

import org.example.dental.models.Recorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecorderRepository extends JpaRepository<Recorder, Integer> {
}

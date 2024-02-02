package com.fundy.jpa.devnote.repository;

import com.fundy.jpa.devnote.model.DevNoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface DevNoteRepository extends JpaRepository<DevNoteModel, Long>, JpaSpecificationExecutor<DevNoteModel> {
    boolean existsById(Long id);


}

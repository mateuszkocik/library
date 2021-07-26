package com.matkoc.library.reader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long>{

    @Query("SELECT r FROM Reader r WHERE r.contactInformation.email = ?1")
    Reader findByEmail(String email);
}

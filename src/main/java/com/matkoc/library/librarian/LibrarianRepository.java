package com.matkoc.library.librarian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long> {

    Librarian getLibrarianByContactInformation_Email(String email);
}

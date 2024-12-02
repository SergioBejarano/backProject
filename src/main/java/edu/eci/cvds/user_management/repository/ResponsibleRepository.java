package edu.eci.cvds.user_management.repository;

import edu.eci.cvds.user_management.model.Responsible;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * ResponsibleRepository is a data access class that manages database interactions for
 * the Responsible entity. It allows saving a new Responsible record and retrieving a
 * Responsible by document type and number.
 */
@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, String> {


    /**
     * Find a Responsible by typeDocument and document.
     *
     * @param document     the document number.
     * @return an Optional containing the Responsible if found, otherwise empty.
     */
    Optional<Responsible> findByDocument(String document);


    /**
     * Finds all responsibles with pagination.
     *
     * @param pageable The pagination information.
     * @return A paginated list of responsibles.
     */
    Page<Responsible> findAll(Pageable pageable);

    /**
     * Deletes a Responsible by document.
     *
     * @param document the document number of the Responsible to delete.
     */
    void deleteByDocument(String document);

}



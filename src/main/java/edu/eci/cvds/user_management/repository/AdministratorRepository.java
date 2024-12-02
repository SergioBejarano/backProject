package edu.eci.cvds.user_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.eci.cvds.user_management.model.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {

}


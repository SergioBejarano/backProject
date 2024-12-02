package edu.eci.cvds.userManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.eci.cvds.userManagement.model.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {

}


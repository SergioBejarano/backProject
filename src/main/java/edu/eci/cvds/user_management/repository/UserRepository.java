package edu.eci.cvds.user_management.repository;


import edu.eci.cvds.user_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

package com.sanjeevnode.rms.authservice.repository;

import com.sanjeevnode.rms.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
}

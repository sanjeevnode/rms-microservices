package com.sanjeevnode.rms.authservice.repository;

import com.sanjeevnode.rms.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}

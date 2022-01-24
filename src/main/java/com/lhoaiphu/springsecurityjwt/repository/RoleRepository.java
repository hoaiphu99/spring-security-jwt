package com.lhoaiphu.springsecurityjwt.repository;

import com.lhoaiphu.springsecurityjwt.models.ERole;
import com.lhoaiphu.springsecurityjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}

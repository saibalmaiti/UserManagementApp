package com.cts.UserManagementApp.repository;

import com.cts.UserManagementApp.model.ExJWT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ExJWTRepo extends JpaRepository<ExJWT, String> {
}

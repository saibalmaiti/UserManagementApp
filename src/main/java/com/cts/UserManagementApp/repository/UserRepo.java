package com.cts.UserManagementApp.repository;

import com.cts.UserManagementApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("select u from User u where u.empName= :empName and u.password= :password")
    public User validateUser(String empName, String password);
}

package com.example.Repository;
import com.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public boolean existsByEmail(String email);

    User findByEmail(String email);
}

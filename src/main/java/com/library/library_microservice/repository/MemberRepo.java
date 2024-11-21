package com.library.library_microservice.repository;

import com.library.library_microservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
}

package com.library.library_microservice.respository;

import com.library.library_microservice.entity.Member;
import com.library.library_microservice.repository.MemberRepo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class MemberRepoTest {
    @Autowired
    private MemberRepo memberRepo;

    @DisplayName("get member by email")
    @Test
    public void givenMemberEmail_whenfindMemberByEmail_thenReturnsMember() {
        //given -precondition or setup
        Member member = Member.builder()
                .name("sarojkhatiwada")
                .email("sarojkhatiwada1999@gmail.com")
                .build();
        memberRepo.save(member);
        //        when- action or a behavioue that we are going to test

        Member memberEntity = memberRepo.findByEmail(member.getEmail()).get();
        //        then- verify the output
        assertThat(memberEntity).isNotNull();


    }
}

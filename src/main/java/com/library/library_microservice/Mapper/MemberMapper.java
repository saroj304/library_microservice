package com.library.library_microservice.Mapper;

import com.library.library_microservice.dto.MemberDto;
import com.library.library_microservice.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toMember(MemberDto memberDto){
      return   Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .build();
    }
}

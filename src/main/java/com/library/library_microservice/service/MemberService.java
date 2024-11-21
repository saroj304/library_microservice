package com.library.library_microservice.service;

import com.library.library_microservice.dto.MemberDto;
import com.library.library_microservice.entity.Member;

public interface MemberService {
    Member createMember(MemberDto memberDto);
}

package com.library.library_microservice.service.impl;

import com.library.library_microservice.Mapper.MemberMapper;
import com.library.library_microservice.dto.MemberDto;
import com.library.library_microservice.entity.Member;
import com.library.library_microservice.exception.AlreadyExistsException;
import com.library.library_microservice.repository.MemberRepo;
import com.library.library_microservice.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepo memberRepo;

    @Override
    public Member createMember(MemberDto memberDto) {
        Member member = memberMapper.toMember(memberDto);
        Optional<Member> existingMember = memberRepo.findByEmail(member.getEmail());
        if (existingMember.isPresent()) {
            throw new AlreadyExistsException("member with the email " + member.getEmail() + " already exists");
        }
        return memberRepo.save(member);

    }
}

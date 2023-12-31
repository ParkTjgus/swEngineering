package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.domain.entity.MemberEntity;
import com.example.se_attendance.domain.entity.NoticeEntity;
import com.example.se_attendance.domain.entity.RecordEntity;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.repository.MemberRepository;
import com.example.se_attendance.repository.RecordRepository;
import com.example.se_attendance.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.secret}")
    private String secretKey;

    // 회원가입 (app)
    @Transactional
    public void signUp(MemberDTO.MemberSignUpDto userDto) throws Exception {
        if (memberRepository.findByMemberId(userDto.getMemberId()).isPresent()) {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, "이미 존재하는 아이디입니다.");
        }

        MemberEntity user = MemberEntity.builder()
                .memberId(userDto.getMemberId())
                .memberPw(encoder.encode(userDto.getMemberPw()))
                .memberName(userDto.getMemberName())
                .memberMajor(userDto.getMemberMajor())
                .memberState(userDto.getMemberState())
                .memberBirth(userDto.getMemberBirth())
                .build();

        memberRepository.save(user);
    }

    // 로그인 (app)
    public String login(MemberDTO.MemberLoginDto memberLoginDto) {
        Long expireTimeMs = 1000 * 60 * 60l;

        MemberEntity user = memberRepository.findByMemberId(memberLoginDto.getMemberId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "가입되지 않은 회원입니다."));
        if (!encoder.matches(memberLoginDto.getMemberPw(), user.getMemberPw())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "잘못된 비밀번호입니다.");
        }
        return JwtUtil.createToken(user.getMemberId(), secretKey, expireTimeMs);
    }

    // 회원 정보 조회 (app)
    public MemberDTO.Memberdto findUser(String memberId) {
        MemberEntity user = memberRepository.findByMemberId(memberId).orElseThrow(() -> new AppException(ErrorCode.INVALID_MEMBER, "회원 정보를 찾을 수 없습니다."));
        return MemberDTO.Memberdto.builder()
                .memberId(user.getMemberId())
                .memberName(user.getMemberName())
                .memberMajor(user.getMemberMajor())
                .memberState(user.getMemberState())
                .memberBirth(user.getMemberBirth())
                .build();
    }

    // 회원 정보 수정 (app)
    @Transactional
    public void updateMyInfo(String memberId, MemberDTO.Memberdto user) {
        MemberEntity persistance = memberRepository.findByMemberId(memberId).orElseThrow(() -> new AppException(ErrorCode.INVALID_MEMBER, "회원 정보를 찾을 수 없습니다."));
        persistance.setMemberPw(encoder.encode(user.getMemberPw()));
        persistance.setMemberName(user.getMemberName());
        persistance.setMemberMajor(user.getMemberMajor());
        persistance.setMemberState(user.getMemberState());
        persistance.setMemberBirth(user.getMemberBirth());
    }

    public List<MemberDTO.rankMember> getAllMember(){
        List<Object[]> result = memberRepository.findMembers();

        List<MemberDTO.rankMember> mappedResult = new ArrayList<>();
        for (Object[] row : result) {

            String memberId = (String) row[0];
            String memberName = (String) row[1];
            String memberMajor = (String) row[2];
            int recordTime = ((Number) row[3]).intValue();

            MemberDTO.rankMember member = new MemberDTO.rankMember(memberId, memberName, memberMajor, recordTime);
            mappedResult.add(member);
        }

        return mappedResult;
    }

    public MemberDTO.Memberdto findById(String memberId){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.Memberdto.builder()
                    .memberId(memberEntity.getMemberId())
                    .memberName(memberEntity.getMemberName())
                    .memberMajor(memberEntity.getMemberMajor())
                    .memberState(memberEntity.getMemberState())
                    .memberBirth(memberEntity.getMemberBirth())
                    .createTime(memberEntity.getCreateTime())
                    .build();
        } else{
            return null;
        }
    }

    public void deleteById(String memberId) {
        System.out.println("-------------------------------------------------------------");
        MemberEntity member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 학생이 존재하지 않습니다."));
        System.out.println("MemberEntity: "+member.getMemberId());
        List<RecordEntity> memberRecord = recordRepository.findByMemberEntity(member);
        if (memberRecord != null) {
            for (RecordEntity record : memberRecord) {
                System.out.println("DELETE Record : "+record.getId());
                recordRepository.delete(record);
            }
        }
        memberRepository.deleteByMemberId(memberId);
    }

    public void update(MemberDTO.Memberdto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDto.getMemberId());
        memberEntity.setMemberBirth(memberDto.getMemberBirth());
        memberEntity.setMemberMajor(memberDto.getMemberMajor());
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setMemberState(memberDto.getMemberState());
        memberRepository.save(memberEntity);
    }
}

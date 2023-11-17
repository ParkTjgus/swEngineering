package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.domain.entity.MemberEntity;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.repository.MemberRepository;
import com.example.se_attendance.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.secret}")
    private String secretKey;

    // 회원가입
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

    // 로그인
    public String login(MemberDTO.MemberLoginDto memberLoginDto) {
        Long expireTimeMs = 1000 * 60 * 60l;

        MemberEntity user = memberRepository.findByMemberId(memberLoginDto.getMemberId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "가입되지 않은 회원입니다."));
        if (!encoder.matches(memberLoginDto.getMemberPw(), user.getMemberPw())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "잘못된 비밀번호입니다.");
        }
        return JwtUtil.createToken(user.getMemberId(), secretKey, expireTimeMs);
    }

    // 회원 정보 조회
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

    // 회원 정보 수정
    @Transactional
    public void updateMyInfo(MemberDTO.Memberdto user) {
        MemberEntity persistance = memberRepository.findByMemberId(user.getMemberId()).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        persistance.setMemberPw(encoder.encode(user.getMemberPw()));
        persistance.setMemberName(user.getMemberName());
        persistance.setMemberMajor(user.getMemberMajor());
        persistance.setMemberState(user.getMemberState());
        persistance.setMemberBirth(user.getMemberBirth());
        //더티체킹. 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌(db에)
    }
    public List<MemberDTO.MemberName> getAllMember(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        //Controller에는 dto로 변환해서 줘야함으로 entity->dto 변환하는 부분
        List<MemberDTO.MemberName> memberNameList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList){
            MemberDTO.MemberName memberdto = MemberDTO.MemberName.builder()
                    .memberId(memberEntity.getMemberId())
                    .memberName(memberEntity.getMemberName())
                    .build();
            memberNameList.add(memberdto);
        }
        return memberNameList;
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

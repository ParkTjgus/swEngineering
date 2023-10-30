package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.domain.entity.MemberEntity;
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
@Transactional(readOnly = true) //롤백 기능 어노테이션. readonly쓰면 성능면에서 좋다네
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional
    public void signUp(MemberDTO.MemberSignUpDto userDto) throws Exception {
        if (memberRepository.findByMemberId(userDto.getMemberId()).isPresent()) {
            throw new Exception("이미 존재하는 아이디입니다.");
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

    public String login(MemberDTO.MemberLoginDto memberLoginDto) {
        Long expireTimeMs = 1000 * 60 * 60l;

        MemberEntity user = memberRepository.findByMemberId(memberLoginDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원 입니다."));
        if (!encoder.matches(memberLoginDto.getMemberPw(), user.getMemberPw())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return JwtUtil.createToken(user.getMemberId(), secretKey, expireTimeMs);
    }

    public MemberDTO.Memberdto findUser() {
        String memberId = JwtUtil.getUserIdFromToken();
        MemberEntity user = memberRepository.findByMemberId(memberId).orElseThrow(() -> new BadCredentialsException("회원 정보를 찾을 수 없습니다."));
        return MemberDTO.Memberdto.builder()
                .memberId(user.getMemberId())
                .memberName(user.getMemberName())
                .memberMajor(user.getMemberMajor())
                .memberState(user.getMemberState())
                .memberBirth(user.getMemberBirth())
                .build();
    }

    @Transactional
    public void updateMyInfo(MemberDTO.Memberdto user) {
        MemberEntity persistance = memberRepository.findByMemberId(user.getMemberId()).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        //persistance.setMemberPw(passwordEncoder.encode(user.getMemberPw()));
        //persistance.setMemberName(user.getMemberName());
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

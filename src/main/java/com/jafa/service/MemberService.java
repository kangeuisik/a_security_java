package com.jafa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jafa.domain.AuthVO;
import com.jafa.domain.MemberType;
import com.jafa.domain.MemberVO;
import com.jafa.repository.AuthRepository;
import com.jafa.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	AuthRepository authRepository; 
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public void join(MemberVO vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword())); // 비밀번호 암호화 
		memberRepository.save(vo);
		AuthVO authVO = AuthVO.builder()
				.memberId(vo.getMemberId())
				.memberType(MemberType.ROLE_ASSOCIATE_MEMBER)
				.ordinal(MemberType.ROLE_ASSOCIATE_MEMBER.ordinal())
				.build();
		authRepository.save(authVO);
		
	}
	
	// 회원 등급변경 
	@Transactional
	public void updateMemberType(AuthVO authVO) {
		authRepository.remove(authVO.getMemberId()); // 모든등급삭제 
		MemberType memberType = authVO.getMemberType(); // 변경할 회원등급 
		MemberType[] types = MemberType.values(); // 전체 회원등급
		for(int i=memberType.ordinal(); i<types.length;i++) {
			AuthVO vo = AuthVO.builder()
				.memberId(authVO.getMemberId())
				.memberType(types[i])
				.ordinal(types[i].ordinal())
				.build();
			authRepository.save(vo);
		}
	}

	public List<MemberVO> memberList() {
		return memberRepository.memberList();
	}
	
}

package com.jafa.repository;

import com.jafa.domain.AuthVO;

public interface AuthRepository {
	// 회원등급
	void save(AuthVO vo);
	//모든회원등급삭제
	void remove(String memberId);

}

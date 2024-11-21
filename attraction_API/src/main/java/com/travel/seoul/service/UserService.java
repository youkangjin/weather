package com.travel.seoul.service;

import java.util.List;

import com.travel.seoul.vo.UserVO;

public interface UserService {
	void insert(UserVO vo);
	List<UserVO> list();
	int delete(Long userSerial);
	void userdelete(String userID);
	int update(UserVO vo);
	UserVO selectID(String id);	//중복 Id 검색
	UserVO userByIdAndPassword(UserVO vo);	//중복 Id 검색

	// 아이디 찾기
	UserVO find_ID(UserVO vo);		
	
	// 비밀번호 찾기
	UserVO find_PW(UserVO vo);		
}

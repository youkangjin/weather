package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.UserVO;

public interface UserMapper {
	void insert(UserVO vo);
	List<UserVO> list();
	int delete(Long userSerial);
	void userdelete(String userID);
	int update(UserVO vo);
	UserVO selectID(String id);	//중복 Id 검색
	UserVO userByIdAndPassword(UserVO vo);
	//ID 찾기
	
	UserVO find_ID(UserVO vo);
	
	//PW 찾기
	UserVO find_PW(UserVO vo);
	
}

package com.travel.seoul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.seoul.mapper.UserMapper;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Service
public class UserServiceImpl implements UserService{
	
	@Setter(onMethod_ = @Autowired)
	private UserMapper mapper;
	
	@Override
	public void insert(UserVO vo) {
		System.out.println("서비스에서 로그인 : " + vo);
		mapper.insert(vo);
		
	}

	@Override
	public List<UserVO> list() {
		System.out.println("서비스에서 회원가입");
		return mapper.list();
	}

	@Override
	public int delete(Long userSerial) {
		System.out.println("서비스에서 삭제 : "+userSerial);
		return mapper.delete(userSerial);
	}

	@Override
	public int update(UserVO vo) {
		System.out.println("서비스에서 수정 : "+vo);
		return mapper.update(vo);
	}
	@Override
	public UserVO selectID(String id) {
		System.out.println("서비스 아이디 중복 확인 : "+id);
		return mapper.selectID(id);
	}

	@Override
	public UserVO find_ID(UserVO vo) {
		System.out.println("서비스 아이디 찾기 : "+vo);
		return mapper.find_ID(vo);
	}

	@Override
	public UserVO find_PW(UserVO vo) {
		System.out.println("서비스 비밀번호 찾기 : "+vo);
		return mapper.find_PW(vo);
	
	}

	@Override
	public UserVO userByIdAndPassword(UserVO vo) {
		System.out.println("서비스 로그인 확인 : " +vo);
		UserVO result = mapper.userByIdAndPassword(vo);
	    System.out.println("서비스 로그인 결과 : " + result);
	    return result;
	}

	@Override
	public void userdelete(String userID) {
		mapper.userdelete(userID);
	}



}

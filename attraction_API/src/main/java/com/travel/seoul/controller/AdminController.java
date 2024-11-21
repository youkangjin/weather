package com.travel.seoul.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.seoul.mapper.AdminMapper;
import com.travel.seoul.mapper.BoardMapper;
import com.travel.seoul.mapper.CommentMapper;
import com.travel.seoul.mapper.UserMapper;
import com.travel.seoul.service.UserService;
import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Setter(onMethod_=@Autowired) 
	private UserService service;
	
	
	@Autowired
    private UserMapper userMapper;
	@Autowired
    private AdminMapper adminMapper;
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private CommentMapper commentMapper;

	
	@GetMapping("/memberList")
	public String getMemberList(Model model,HttpServletRequest request) {
		List<UserVO> list = service.list();
		// 덮어씌워지는 것을 방지하기 위해 list에 값 저장
		List<String> roles = new ArrayList<>();
		HttpSession session = request.getSession();
	      UserVO user = (UserVO) session.getAttribute("loginMember");
	      model.addAttribute("user",user);
	      
		for (UserVO uservo : list) {
			System.out.println("UserID: "+uservo.getUserID());
			AdminVO role = adminMapper.getAdminID(uservo.getUserID());
			System.out.println("role: "+role);
			
			// 관리자 DB에 userID가 없을 경우 일반회원, 있을 경우 관리자
			if (role==null){
				roles.add("일반회원");
			}
			else {
				roles.add("관리자");
			}
		}
		model.addAttribute("roles", roles);
		model.addAttribute("list", list);
		return "admin/memberList";
	}
	
	// memberList.jsp에서 userID와 userPW 받아옴(관리자 권한 부여)
	@GetMapping("/adminupdate")
	public String adminupdate(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) {
		// 객체 생성 후 값 저장
	    AdminVO admin = new AdminVO();
	    admin.setAdmin_title(null);
	    admin.setAdmin_content(null);
	    admin.setAdmin_id(user_id);
	    admin.setAdmin_pw(user_pw);
	    
	    //관리자 DB에 값 추가
	    adminMapper.adminIDInsert(admin);
	    
	    System.out.println(user_id + " 가 관리자로 승급하였습니다");

	    return "redirect:/admin/memberList";
	}
	
	// memberList.jsp에서 userID 받아옴(관리자 권한 제거)
	@GetMapping("/admindelete")
	public String admindelete(@RequestParam("user_id") String user_id) {
	    //관리자 DB에 값 삭제
	    adminMapper.adminIDDelete(user_id);
	    
	    System.out.println(user_id + " 가 일반회원이 되었습니다");
	    
	    return "redirect:/admin/memberList";
	}
	
	// memberList.jsp에서 userID 받아옴(회원탈퇴)
	@GetMapping("/userdelete")
	public String userdelete(@RequestParam("user_id") String user_id) {
		System.out.println("userdelete : "+user_id);
		// 회원 DB와 연결된 데이터 모두 삭제
	    boardMapper.userdelete(user_id);
		System.out.println("boardMapper 삭제 후");
		commentMapper.userdeleteComment(user_id);
		System.out.println("commentMapper 삭제 후");
		userMapper.userdelete(user_id);
		System.out.println("userMapper 삭제 후");
		
	    System.out.println("회원탈퇴");

	    return "redirect:/admin/memberList";
	}
	
	// memberList.jsp에서 topic,keyword 받아옴(검색)
	@GetMapping("/search")
	public String search(String topic, String keyword, HttpSession session) {
		List<UserVO> list = service.list();
		
		// 키워드가 해당 주제에 포함되어있으면 List에 추가
		List<UserVO> searchList = list.stream()
	            .filter(user -> {
	                switch (topic) {
	                    case "id":
	                        return keyword.equals(user.getUserID());
	                    case "name":
	                        return keyword.equals(user.getUserName());
	                    case "sex":
	                        return keyword.equals(user.getUserSex());
	                    case "age":
	                        return keyword.equals(user.getUserAge());
	                    case "area":
	                        return keyword.equals(user.getUserArea1()) || keyword.equals(user.getUserArea2()) || keyword.equals(user.getUserArea3());
	                    default:
	                        return false;
	                }
	            })
	            .collect(Collectors.toList());
		
		session.setAttribute("user_searchList", searchList);
		System.out.println("User List Size: " + searchList.size());
		
		return "admin/memberList";
	}
}

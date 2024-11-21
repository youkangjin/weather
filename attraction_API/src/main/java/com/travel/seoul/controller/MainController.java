package com.travel.seoul.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.seoul.mapper.AdminMapper;
import com.travel.seoul.mapper.NewsMapper;
import com.travel.seoul.service.UserService;
import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.NewsVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
public class MainController {
	@Setter(onMethod_=@Autowired)
	private UserService service;
	
	@Autowired
    private AdminMapper adminMapper;
	@Autowired
    private NewsMapper newsMapper;
	
	@GetMapping("/")
	public String main(HttpServletRequest request, UserVO vo, Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("loginMember");
		
		if(user != null) {
			model.addAttribute("user",user);
		}
		System.out.println("user / : "+user);
		return "/Main";
	}
	
	@GetMapping("/main")
	public String mainSession(HttpServletRequest request, HttpSession session, UserVO vo, Model model) {
		UserVO user = (UserVO) session.getAttribute("loginMember");
		model.addAttribute("user",user);
		System.out.println("user : "+user);
		
		AdminVO admindbID = isAdminFromDatabase(user.getUserID());
		
		List<NewsVO> imageList = newsMapper.NewsList();
	    session.setAttribute("imageList", imageList);
		
		// user와 admindbID가 null이 아니고 userID와 adminID가 동일할 때 adminID 세션에 저장
		if (user != null && admindbID != null && user.getUserID().equals(admindbID.admin_id)) {
			System.out.println("이상없음");
	        session.setAttribute("admindbID", admindbID.admin_id);
	        
	        System.out.println("로그인 되었어요"+user);
	        return "admin/Main_admin";
		} else {
			System.out.println("일반회원");
			user= service.selectID(vo.getUserID());
			System.out.println("로그인 되었어요"+user);
			return "redirect:/";
		}

	}
	
	// adminMapper에서 adminID 불러옴
	private AdminVO isAdminFromDatabase(String userID) {
		return adminMapper.getAdminID(userID);
    }
	private static final String UPLOAD_DIR = "C://upload//";
    @GetMapping("/display/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> displayImage(@PathVariable String fileName) {
        System.out.println("Requested file name: " + fileName);
        File file = new File(UPLOAD_DIR + fileName);

        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));

            Resource resource = new FileSystemResource(file);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@GetMapping("/face")
	public ResponseEntity<UserVO> faceSession(@RequestParam("userID") String userID,HttpServletRequest request) {
	    System.out.println("1번 출력: " + userID);
	    UserVO vo = service.selectID(userID);
	    System.out.println("2번 출력: " + vo);
	    HttpSession session = request.getSession();
	    session.setAttribute("loginMember", vo);
	    return new ResponseEntity<UserVO>(vo, HttpStatus.OK);
	}

	
  	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session, UserVO vo, Model model) {
		session.removeAttribute("loginMember");
		System.out.println("로그아웃 했습니다.");
		return "redirect:/";
	}
	

}

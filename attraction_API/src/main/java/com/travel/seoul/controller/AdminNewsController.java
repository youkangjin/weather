package com.travel.seoul.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel.seoul.mapper.NewsMapper;
import com.travel.seoul.vo.NewsVO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/*")
public class AdminNewsController {
	@Autowired
    private NewsMapper newsMapper;
	
	@GetMapping("/")
	public String admin() {
		return "admin/Main_admin";
	}
	
	// Main_admin.jsp에서 ID와 URL 받아옴(URL 수정)
    @GetMapping("/editlink")
    public String editImage(Integer id, String url, HttpSession session) {
        System.out.println("수정된 id: "+id);
    	System.out.println("수정된 url: "+url);
        
        List<NewsVO> imageList = (List<NewsVO>) session.getAttribute("imageList");

        // DB에 있는 번호와 선택한 뉴스 id가 같다면 newsVO 객체에 추가 
        if (imageList != null) {
            for (NewsVO img : imageList) {
                if (img.getNews_num() == id) {
                	NewsVO newsVO = new NewsVO();
                    newsVO.setNews_num(id);
                    newsVO.setNews_link(url);
                    
                    // DB에 추가
                    newsMapper.NewsUpdateLink(newsVO);
                    
                    img.setNews_link(url);
                }
            }
            session.setAttribute("imageList", imageList);
        } else {
            System.out.println("null");
        }
        
        return "admin/Main_admin";
    } 
    
    // 이미지를 저장할 기본 경로
    private static final String UPLOAD_DIR = "C://upload"; 
    // Main_admin.jsp에서 ID와 이미지정보 받아옴(이미지 수정)
    @PostMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam Integer id, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes, HttpSession session) {
        if (imageFile.isEmpty()) {
            return "파일이 선택되지 않았습니다.";
        }

        try {
            System.out.println(id);
            System.out.println("파일 이름: " + imageFile.getOriginalFilename());
            System.out.println("파일 크기: " + imageFile.getSize());

            // UUID로 고유한 파일 이름 생성
            String uniqueFileName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일 저장
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            //displayImage 함수와 연결하기 위함
            String relativePath = "/display/" + uniqueFileName;
            
            List<NewsVO> imageList = (List<NewsVO>) session.getAttribute("imageList");
            System.out.println(imageList);
            if (imageList != null) {
                for (NewsVO img : imageList) {
                    if (img.getNews_num() == id) {
                    	NewsVO newsVO = new NewsVO();
                        newsVO.setNews_num(id);
                        newsVO.setNews_path(relativePath);

                        newsMapper.NewsUpdatePath(newsVO);
                    }
                }
                session.setAttribute("imageList", imageList);
            } else {
                System.out.println("null");
            }

            return "admin/Main_admin";
        } catch (Exception e) {
            e.printStackTrace();
            return "파일 업로드 중 오류가 발생했습니다.";
        }
    }
    
    // Main_admin.jsp에서 URL과 이미지정보 받아옴(뉴스 추가)
    @PostMapping("/insertNews")
    public String insertNews(MultipartFile imageFile, String url, HttpSession session) {
        try {
            if (imageFile.isEmpty()) {
                return "admin/Main_admin";
            }

            String uniqueFileName = imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            List<NewsVO> imageList = (List<NewsVO>) session.getAttribute("imageList");
            NewsVO newsVO = new NewsVO();
            newsVO.setNews_alt("image");
            newsVO.setNews_link(url);
            newsVO.setNews_path("/display/"+imageFile.getOriginalFilename());  
            
            newsMapper.Newsinsert(newsVO);     
            imageList.add(newsVO);    
            session.setAttribute("imageList", imageList);
            
            return "admin/Main_admin";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/Main_admin";
        }
    }
    
    // 외부경로의 이미지 화면에 띄우는 용도
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
            System.out.println(resource);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Main_admin.jsp에서 ID 받아옴(뉴스 삭제)
    @GetMapping("/deleteNews")
    public String deleteNews(Integer id, HttpSession session) {
    	System.out.println("뉴스삭제");
    	List<NewsVO> imageList = (List<NewsVO>) session.getAttribute("imageList");

        newsMapper.NewsDelete(id);

        session.setAttribute("imageList", imageList);
        return "admin/Main_admin";
    }
}
	
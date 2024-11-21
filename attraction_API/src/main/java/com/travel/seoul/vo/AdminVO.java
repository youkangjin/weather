
 package com.travel.seoul.vo;
  
  import java.util.List;

import lombok.Data;
  
@Data 
public class AdminVO { 
	  private long admin_num; 
	  private String admin_title; 
	  private String admin_content; 
	  public String admin_id; 
	  private String admin_pw; 
	  private String admin_postdate; 
	  private long admin_visitcount; 
	  
	  private List<BoardAttachVO> attachList;
	  
	  @Override
	    public String toString() {
	        return "UserVO(admin_id=" + admin_id+')';
	    }
}
 
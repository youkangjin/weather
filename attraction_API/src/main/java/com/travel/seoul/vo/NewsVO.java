package com.travel.seoul.vo;

import lombok.Data;
  
  @Data 
  public class NewsVO { 
	  private long news_num; 
	  private String news_path; 
	  private String news_alt; 
	  public String news_link; 
  }
 
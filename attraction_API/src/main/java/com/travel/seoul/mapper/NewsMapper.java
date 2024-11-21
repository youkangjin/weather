package com.travel.seoul.mapper;
 
import java.util.List;

import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.NewsVO;

public interface NewsMapper {
	public List<NewsVO> NewsList(); 
	public void Newsinsert(NewsVO NewsVO);
	public void NewsUpdateLink(NewsVO NewsVO);
	public void NewsUpdatePath(NewsVO NewsVO);
	public int NewsDelete(int news_num);
	}

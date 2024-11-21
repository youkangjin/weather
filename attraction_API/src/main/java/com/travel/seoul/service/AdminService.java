package com.travel.seoul.service;
 
import java.util.List;
 
import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.BoardAttachVO;
 
public interface AdminService { 
	public List<AdminVO> getAdminList(); 
	public void adminInsert(AdminVO admin); 
	public void adminIDInsert(AdminVO admin);
	public int adminUpdate(AdminVO admin);
	public int adminDelete(long admin_num); 
	public void adminIDDelete(String admin_id);
	public int adminVisitcount(long admin_num); 
	public AdminVO getAdminByNum(long admin_num);
	public int getNextNum();
	
	public void adminWrite(AdminVO admin);
	public List<BoardAttachVO> getAdminAttachList(long num);
	public boolean adminEdit(AdminVO admin);
	}

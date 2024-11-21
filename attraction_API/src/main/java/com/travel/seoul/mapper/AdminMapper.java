
package com.travel.seoul.mapper;
 
import java.util.List;

import com.travel.seoul.vo.AdminVO;

public interface AdminMapper {
	public List<AdminVO> getAdminList(); 
	public void adminInsert(AdminVO admin); 
	public void adminIDInsert(AdminVO admin);
	public int adminUpdate(AdminVO admin);
	public int adminDelete(long admin_num); 
	public void adminIDDelete(String admin_id);
	public int adminVisitcount(long admin_num);
	public AdminVO getAdminByNum(long admin_num);
	public AdminVO getAdminID(String admin_id);
	public int getNextNum();
	
	public void adminFileInsert(AdminVO admin);
	public void adminFileSelect(AdminVO admin);
	public void adminInsertSelectKey(AdminVO admin);
}

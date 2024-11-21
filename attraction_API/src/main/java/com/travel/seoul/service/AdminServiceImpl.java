
 package com.travel.seoul.service;
 
 import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.seoul.mapper.AdminMapper;
import com.travel.seoul.mapper.BoardAttachMapper;
import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.BoardAttachVO;

import lombok.Setter;

@Service
public class AdminServiceImpl implements AdminService {
	 
	 @Setter(onMethod_ = @Autowired)
	 AdminMapper amapper;
	 
	 @Setter(onMethod_ = @Autowired)
		BoardAttachMapper attachMapper;
	 
	@Override
	public List<AdminVO> getAdminList() {
		return amapper.getAdminList();
	}

	@Override
	public void adminInsert(AdminVO admin) {
		amapper.adminInsert(admin);
	}

	@Override
	public int adminUpdate(AdminVO admin) {
		return amapper.adminUpdate(admin);
	}

	@Override
	public int adminDelete(long admin_num) {
		return amapper.adminDelete(admin_num);
	}

	@Override
	public int adminVisitcount(long admin_num) {
		return amapper.adminVisitcount(admin_num);
	}

	@Override
	public AdminVO getAdminByNum(long admin_num) {
		return amapper.getAdminByNum(admin_num);
	}

	@Override
	public int getNextNum() {
		return amapper.getNextNum();
	}
	@Transactional
	@Override
	public void adminWrite(AdminVO admin) {
		int adminNum = amapper.getNextNum();
		amapper.adminInsertSelectKey(admin);
	    System.out.println("admin write..." + admin);
	    // 첨부파일이 없을 경우, 중지
	    if (admin.getAttachList() == null || admin.getAttachList().size() <= 0) {
	        return;
	    }
	    
	    admin.getAttachList().forEach(attach -> {
	    	attach.setAdmin_num(adminNum);
	        attachMapper.insert(attach);
	    });
	}

	@Override
	public List<BoardAttachVO> getAdminAttachList(long num) {
		System.out.println("getAttachList admin num 서비스"+num);
		return attachMapper.findByAdmin_num(num);
	}
	
	@Transactional
	@Override
	public boolean adminEdit(AdminVO admin) {
		   System.out.println("admin edit......" + admin);
		   long num = admin.getAdmin_num();
		   
		   System.out.println("admin 서비스에서 파일추가번호"+num);
		   
		   attachMapper.deleteAllAdmin_num(num);
		   boolean editResult = amapper.adminUpdate(admin) == 1;
		   System.out.println("서비스 edit 리스트 : "+admin.getAttachList());
		   if (editResult && admin.getAttachList() != null && admin.getAttachList().size() >0) {
			   admin.getAttachList().forEach(attach -> {
		    	 System.out.println("서비스 attach edit : "+attach);
		         attach.setAdmin_num(num);
		         attachMapper.insert(attach);
		      });
		   }
		   
		   return editResult;
		}

	@Override
	public void adminIDInsert(AdminVO admin) {
		amapper.adminIDInsert(admin);
		
	}

	@Override
	public void adminIDDelete(String admin_id) {
		amapper.adminIDDelete(admin_id);
		
	}

 }

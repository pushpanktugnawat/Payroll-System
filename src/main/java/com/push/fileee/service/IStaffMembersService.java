package com.push.fileee.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.push.fileee.model.StaffMembers;

public interface IStaffMembersService {

	List<StaffMembers> getAllStaffMembers();
	
	ResponseEntity<String> addStaffMembers(StaffMembers staffMembers);
	
	ResponseEntity<String> updateStaffMembers(StaffMembers staffMembers);
	
	ResponseEntity<String> removeStaffMembers(int staffMemberId);

	List<StaffMembers> getAllStaffMembersByBubbleSort();
}

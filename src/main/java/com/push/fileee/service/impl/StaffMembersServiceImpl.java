/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffMembersServiceImpl.java
 */
package com.push.fileee.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.push.fileee.dao.IStaffMembersDao;
import com.push.fileee.model.StaffMembers;
import com.push.fileee.service.IStaffMembersService;
import com.push.fileee.utils.UtilityClass;

/**
 * The Class StaffMembersServiceImpl.
 * 
 * this class aims for business Logic of the current implementation
 */
@Service
public class StaffMembersServiceImpl implements IStaffMembersService {

	@Autowired
	private IStaffMembersDao staffMembersDao;

	private Logger logger = LoggerFactory.getLogger(StaffMembersServiceImpl.class);

	/**
	 * this function is used to retrieve all the staff members available in DB
	 */
	@Override
	public List<StaffMembers> getAllStaffMembers() 
	{
		logger.info("@method getAllStaffMembers");

		return (List<StaffMembers>) staffMembersDao.findAll();
	}

	/**
	 * this function is used to add a new staff member in DB
	 * */
	@Override
	public ResponseEntity<String> addStaffMembers(StaffMembers staffMembers) {

		logger.info("@method addStaffMembers ");

		if(staffMembers!=null)
		{
			staffMembers.setCreatedTime(LocalDateTime.now());
			staffMembers.setModifiedTime(LocalDateTime.now());
			StaffMembers members=staffMembersDao.save(staffMembers);
			if(members.getStaffId()>0)
			{
				members=null;
				return ResponseEntity.ok(UtilityClass.STAFF_MEMBER_ADDED_SUCCESSFULLY);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilityClass.SOMETHING_WENT_WRONG);
		}
		return ResponseEntity.badRequest().body(UtilityClass.STAFF_MEMBER_CAN_NOT_BE_NULL);
	}

	/**
	 * this function is used to UPDATE a  staff member data in DB
	 *  */
	@Override
	public ResponseEntity<String> updateStaffMembers(StaffMembers staffMembers) 
	{
		logger.info("@method updateStaffMembers ");

		logger.debug("@method updateStaffMembers "+staffMembers.getStaffId());
		if(staffMembers!=null && staffMembers.getStaffId()>0)
		{
			 return staffMembersDao.findById(staffMembers.getStaffId())
		                .map(optionalMembers -> {
		                	staffMembers.setCreatedTime(optionalMembers.getCreatedTime());
		                	staffMembers.setModifiedTime(LocalDateTime.now());
		                	staffMembersDao.save(staffMembers);
		    				
		                    return ResponseEntity.ok(UtilityClass.STAFF_MEMBER_UPDATED_SUCCESSFULLY);
		                })
		                .orElseGet(() -> ResponseEntity.badRequest().body(UtilityClass.STAFF_MEMBER_DOES_NOT_EXIST));
			
		}
		return ResponseEntity.badRequest().body(UtilityClass.STAFF_MEMBER_CAN_NOT_BE_NULL);
	}

	/**
	 * this function is used to remove a  staff member data in DB
	 *  */
	@Override
	public ResponseEntity<String> removeStaffMembers(int staffMemberId) {

		logger.info("@method removeStaffMembers ");

		logger.debug("@method removeStaffMembers "+staffMemberId);
		
		if(staffMemberId>0)
		{

			return staffMembersDao.findById(staffMemberId)
	                .map(optionalMembers -> {
	                	staffMembersDao.deleteById(staffMemberId);
	                    return ResponseEntity.ok(UtilityClass.STAFF_MEMBER_DELETED_SUCCESSFULLY);
	                }).orElseGet(() -> ResponseEntity.badRequest().body(UtilityClass.STAFF_MEMBER_DOES_NOT_EXIST));
			
		}
		return ResponseEntity.badRequest().body(UtilityClass.STAFF_MEMBER_CAN_NOT_BE_NULL);
	}

	/**
	 * this API to allow returning sorted results (alphabetical order of the staff members
	 * name) using Bubble Sort
	 * */
	@Override
	public List<StaffMembers> getAllStaffMembersByBubbleSort()
	{

		logger.info("@method getAllStaffMembersByBubbleSort");

		List<StaffMembers> staffMembers= (List<StaffMembers>) staffMembersDao.findAll();
		
		if(!staffMembers.isEmpty() && staffMembers.size()>1)
		{
			logger.info("@method getAllStaffMembersByBubbleSort size of available staffmembers data "+staffMembers.size());
			return sortByBubbleSort(staffMembers);
		}
		logger.info("@method getAllStaffMembersByBubbleSort no data available for Staff Members  ");
		return staffMembers;
	}

	/**
	 * Sort by bubble sort.
	 *
	 * @param staffMembers the staff members
	 * @return the list
	 */
	List<StaffMembers> sortByBubbleSort(List<StaffMembers> staffMembers) {
		
		logger.info("@method sortByBubbleSort ");

		logger.debug("@method sortByBubbleSort "+staffMembers.size());

		StaffMembers temp;
		for (int j = 0; j < staffMembers.size() - 1; j++) 
        { 
            for (int i = j + 1; i < staffMembers.size(); i++)  
            { 
                if (staffMembers.get(j).getName().compareTo(staffMembers.get(i).getName()) > 0) 
                { 
                    temp = staffMembers.get(j); 
                    staffMembers.set(j, staffMembers.get(i));
                    staffMembers.set(i, temp);
                } 
            } 
        } 
		return staffMembers;
	}

}

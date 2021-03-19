/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffMembersRestImpl.java
 */
package com.push.fileee.rest.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.push.fileee.model.StaffMembers;
import com.push.fileee.service.IStaffMembersService;

/**
 * The Class StaffMembersRestImpl.
 */
@RestController
@RequestMapping("/api/staffMembers")
public class StaffMembersRestImpl {

	@Autowired
	private IStaffMembersService staffMembersService;


	Logger logger = LoggerFactory.getLogger(StaffMembersRestImpl.class);

	/**
	 * Gets the all staff members.
	 *
	 * @param bubbleSort the bubble sort
	 * @return the all staff members
	 */
	@GetMapping
	public List<StaffMembers> getAllStaffMembers(@RequestParam(required = false,name="bubbleSort") boolean bubbleSort)
	{
		logger.info("@method getAllStaffMembers "+bubbleSort);

		if(bubbleSort)
		{
			return staffMembersService.getAllStaffMembersByBubbleSort();
		}else
		{
			return staffMembersService.getAllStaffMembers();
		}
	}

	/**
	 * Adds the staff members.
	 *
	 * @param staffMembers the staff members
	 * @return the string
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addStaffMembers(@RequestBody StaffMembers staffMembers)
	{
		logger.info("@method addStaffMembers ");

		logger.debug("@method addStaffMembers "+staffMembers.getName());

		return staffMembersService.addStaffMembers(staffMembers);
	}


	/**
	 * Update staff members.
	 *
	 * @param staffMembers the staff members
	 * @return the string
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateStaffMembers(@RequestBody StaffMembers staffMembers)
	{
		logger.info("@method updateStaffMembers ");

		logger.debug("@method updateStaffMembers "+staffMembers.getName());

		return staffMembersService.updateStaffMembers(staffMembers);
	}


	/**
	 * Removes the staff members.
	 *
	 * @param staffMemberId the staff member id
	 * @return the string
	 */
	@DeleteMapping("/{staffMemberId}")
	public ResponseEntity<String> removeStaffMembers(@PathVariable int staffMemberId)
	{
		logger.info("@method removeStaffMembers ");

		logger.debug("@method staffMemberId "+staffMemberId);

		return staffMembersService.removeStaffMembers(staffMemberId);
	}


}

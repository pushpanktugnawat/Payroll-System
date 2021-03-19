/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffMembersServiceImplTest.java
 */
package com.push.fileee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.push.fileee.dao.IStaffMembersDao;
import com.push.fileee.model.StaffMembers;
import com.push.fileee.model.StaffMembers.PayRollType;
import com.push.fileee.utils.UtilityClass;

/**
 * The Class StaffMembersServiceImplTest.
 *
 * @author pintu
 */
@ExtendWith(MockitoExtension.class)
public class StaffMembersServiceImplTest {

	/** The staff members dao. */
	@Mock
	private IStaffMembersDao staffMembersDao;

	/** The staff members service impl. */
	@InjectMocks
	private StaffMembersServiceImpl staffMembersServiceImpl;


	/** The staff members. */
	private List<StaffMembers> staffMembers;       

	/** The staffmember. */
	private StaffMembers staffmember;
	
	
	/**
	 * Sets the up.
	 */
	@BeforeEach                           
	void setUp() {                               

		this.staffMembers = new ArrayList<>();    
		this.staffMembers.add(new StaffMembers(1, "Alex", PayRollType.FIXED));
		this.staffMembers.add(new StaffMembers(2, "Peter", PayRollType.HOURLY));

		this.staffmember=new StaffMembers(3, "David", PayRollType.FIXED);
	}

	/**
	 * Test find all staff members success.
	 */
	@Test
	public void testFindAllStaffMembersSuccess() {

		Mockito.when(staffMembersDao.findAll()).thenReturn(this.staffMembers);

		List<StaffMembers> expected = staffMembersServiceImpl.getAllStaffMembers();

		Assertions.assertEquals(expected, this.staffMembers);
	}
	
	@Test
	public void testFindAllStaffMembersWithBubbleSortSuccess() {

		Mockito.when(staffMembersDao.findAll()).thenReturn(this.staffMembers);
		
		List<StaffMembers> expected = staffMembersServiceImpl.getAllStaffMembersByBubbleSort();

		Assertions.assertEquals(expected, this.staffMembers);
	}
	
	@Test
	public void testFindAllStaffMembersSortingAlgoSuccess() {

		
		List<StaffMembers> expected = staffMembersServiceImpl.sortByBubbleSort(this.staffMembers);

		Assertions.assertEquals(expected, this.staffMembers);
	}


	/**
	 * Test find all staff members no result.
	 */
	@Test
	public void testFindAllStaffMembersNoResult() {

		Mockito.when(staffMembersDao.findAll()).thenReturn(null);

		List<StaffMembers> expected = staffMembersServiceImpl.getAllStaffMembers();

		this.staffMembers=null;

		Assertions.assertEquals(expected, this.staffMembers);
	}

	/**
	 * Test find all staff members failure.
	 */
	@Test
	public void testFindAllStaffMembersFailure() {

		Mockito.when(staffMembersDao.findAll()).thenReturn(null);

		List<StaffMembers> expected = staffMembersServiceImpl.getAllStaffMembers();
		Assertions.assertNotEquals(expected, this.staffMembers);
	}

	/**
	 * Test add staff members success.
	 */
	@Test
	public void testAddStaffMembersSuccess() {

		Mockito.when(staffMembersDao.save(this.staffmember)).thenReturn(this.staffmember);


		String message = staffMembersServiceImpl.addStaffMembers(this.staffmember).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_ADDED_SUCCESSFULLY);
	}

	/**
	 * Test add staff members when id is zero.
	 */
	@Test
	public void testAddStaffMembersWhenIdIsZero() {

		this.staffmember.setStaffId(0);
		Mockito.when(staffMembersDao.save(this.staffmember)).thenReturn(this.staffmember);


		String message = staffMembersServiceImpl.addStaffMembers(this.staffmember).getBody();

		Assertions.assertEquals(message, UtilityClass.SOMETHING_WENT_WRONG);
	}

	/**
	 * Test add staff members when staff member is null.
	 */
	@Test
	public void testAddStaffMembersWhenStaffMemberIsNull() {

		String message = staffMembersServiceImpl.addStaffMembers(null).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_CAN_NOT_BE_NULL);
	}

	/**
	 * Test update staff members success.
	 */
	@Test
	public void testUpdateStaffMembersSuccess() {

		Optional<StaffMembers> staffMemberOpt=Optional.of(staffmember);

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);

		Mockito.when(staffMembersDao.save(this.staffmember)).thenReturn(this.staffmember);


		String message = staffMembersServiceImpl.updateStaffMembers(this.staffmember).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_UPDATED_SUCCESSFULLY);
	}

	/**
	 * Test update staff members not exist.
	 */
	@Test()
	public void testUpdateStaffMembersNotExist() {

		Optional<StaffMembers> staffMemberOpt=Optional.empty();

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);


		String message = staffMembersServiceImpl.updateStaffMembers(this.staffmember).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_DOES_NOT_EXIST);
	}

	/**
	 * Test update staff members when id is zero.
	 */
	@Test
	public void testUpdateStaffMembersWhenIdIsZero() {

		this.staffmember.setStaffId(0);
		String message = staffMembersServiceImpl.updateStaffMembers(this.staffmember).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_CAN_NOT_BE_NULL);
	}

	/**
	 * Test update staff members when staff member is null.
	 */
	@Test
	public void testUpdateStaffMembersWhenStaffMemberIsNull() {

		String message = staffMembersServiceImpl.addStaffMembers(null).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_CAN_NOT_BE_NULL);
	}
	
	/**
	 * Test remove staff members success.
	 */
	@Test
	public void testRemoveStaffMembersSuccess() {

		Optional<StaffMembers> staffMemberOpt=Optional.of(staffmember);

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);


		String message = staffMembersServiceImpl.removeStaffMembers(this.staffmember.getStaffId()).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_DELETED_SUCCESSFULLY);
	}
	
	/**
	 * Test remove staff members not exist.
	 */
	@Test()
	public void testRemoveStaffMembersNotExist() {

		Optional<StaffMembers> staffMemberOpt=Optional.empty();

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);


		String message = staffMembersServiceImpl.removeStaffMembers(this.staffmember.getStaffId()).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_DOES_NOT_EXIST);
	}

	/**
	 * Test remove staff members when id is zero.
	 */
	@Test
	public void testRemoveStaffMembersWhenIdIsZero() {

		String message = staffMembersServiceImpl.removeStaffMembers(0).getBody();

		Assertions.assertEquals(message, UtilityClass.STAFF_MEMBER_CAN_NOT_BE_NULL);
	}

	
	


}

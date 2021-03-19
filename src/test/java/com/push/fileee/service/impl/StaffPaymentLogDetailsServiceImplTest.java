/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffPaymentLogDetailsServiceImplTest.java
 */
package com.push.fileee.service.impl;

import java.math.BigInteger;
import java.time.LocalDate;
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
import com.push.fileee.dao.IStaffPaymentLogDetailsDao;
import com.push.fileee.model.StaffMembers;
import com.push.fileee.model.StaffMembers.PayRollType;
import com.push.fileee.model.StaffPaymentLogDetails;
import com.push.fileee.utils.UtilityClass;

@ExtendWith(MockitoExtension.class)
public class StaffPaymentLogDetailsServiceImplTest {

	/** The staff members dao. */
	@Mock
	private IStaffMembersDao staffMembersDao;
	
	@Mock
	private IStaffPaymentLogDetailsDao staffPaymentLogDetailsDao;

	/** The staff members service impl. */
	@InjectMocks
	private StaffPaymentLogDetailsServiceImpl staffPaymentLogDetailsServiceImpl;
	
	/** The staff payment log details. */
	private StaffPaymentLogDetails staffPaymentLogDetails;
	
	/** The staffmember. */
	private StaffMembers staffmember;
	
	
	
	/**
	 * Setup.
	 */
	@BeforeEach
	public void setup()
	{
		this.staffmember=new StaffMembers(3, "David", PayRollType.FIXED);
		this.staffPaymentLogDetails=new StaffPaymentLogDetails(1, "Worked on Create task", 4.5, 60.5, this.staffmember, LocalDate.now());
		
	}
	
	@Test
	void testAddWorkLogSuccessForFixed()
	{
	
		Optional<StaffMembers> staffMemberOpt=Optional.of(this.staffmember);

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);


		Mockito.when(staffPaymentLogDetailsDao.findPaymentLogDetailsBasedOnStaffAndMonth
				(this.staffmember.getStaffId(),this.staffPaymentLogDetails.getEntryDate().getMonthValue())).thenReturn(new BigInteger("0"));

		
		Mockito.when(staffPaymentLogDetailsDao.save(this.staffPaymentLogDetails)).thenReturn(this.staffPaymentLogDetails);
		
		

		String message = staffPaymentLogDetailsServiceImpl.addWorkLog(staffPaymentLogDetails).getBody();

		Assertions.assertEquals(message, UtilityClass.WORK_LOG_ADDED_SUCCESSFULLY);
		
	}
	
	@Test
	void testAddWorkLogFailureForFixed()
	{
	
		Optional<StaffMembers> staffMemberOpt=Optional.of(this.staffmember);

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);


		Mockito.when(staffPaymentLogDetailsDao.findPaymentLogDetailsBasedOnStaffAndMonth
				(this.staffmember.getStaffId(),this.staffPaymentLogDetails.getEntryDate().getMonthValue())).thenReturn(new BigInteger("0"));

		this.staffPaymentLogDetails.setId(0);
		
		Mockito.when(staffPaymentLogDetailsDao.save(this.staffPaymentLogDetails)).thenReturn(this.staffPaymentLogDetails);
		
		

		String message = staffPaymentLogDetailsServiceImpl.addWorkLog(staffPaymentLogDetails).getBody();

		Assertions.assertEquals(message, UtilityClass.SOMETHING_WENT_WRONG);
		
	}
	
	@Test
	void testAddWorkLogSuccessForHourly()
	{
		this.staffmember.setPayrollType(PayRollType.HOURLY);
	
		Optional<StaffMembers> staffMemberOpt=Optional.of(this.staffmember);

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);


		Mockito.when(staffPaymentLogDetailsDao.findPaymentLogDetailsBasedOnStaffAndDay
				(this.staffmember.getStaffId(),this.staffPaymentLogDetails.getEntryDate())).thenReturn(0.0);

		
		Mockito.when(staffPaymentLogDetailsDao.save(this.staffPaymentLogDetails)).thenReturn(this.staffPaymentLogDetails);
		
		

		String message = staffPaymentLogDetailsServiceImpl.addWorkLog(staffPaymentLogDetails).getBody();

		Assertions.assertEquals(message, UtilityClass.WORK_LOG_ADDED_SUCCESSFULLY);
		
	}

	
	@Test
	void testAddWorkLogFailureForHourly()
	{
		this.staffmember.setPayrollType(PayRollType.HOURLY);
	
		Optional<StaffMembers> staffMemberOpt=Optional.of(this.staffmember);

		Mockito.when(staffMembersDao.findById(this.staffmember.getStaffId())).thenReturn(staffMemberOpt);


		Mockito.when(staffPaymentLogDetailsDao.findPaymentLogDetailsBasedOnStaffAndDay
				(this.staffmember.getStaffId(),this.staffPaymentLogDetails.getEntryDate())).thenReturn(0.0);

		this.staffPaymentLogDetails.setId(0);
		Mockito.when(staffPaymentLogDetailsDao.save(this.staffPaymentLogDetails)).thenReturn(this.staffPaymentLogDetails);
		
		

		String message = staffPaymentLogDetailsServiceImpl.addWorkLog(staffPaymentLogDetails).getBody();

		Assertions.assertEquals(message, UtilityClass.SOMETHING_WENT_WRONG);
		
	}
	
	@Test
	void testFindPayRollForAStaffMmbrCertainPeriod()
	{
		List<StaffPaymentLogDetails> paymentLogDetailsList=new ArrayList<>();
		paymentLogDetailsList.add(staffPaymentLogDetails);
		
		LocalDate startDate=LocalDate.of(2020, 05, 13);
		LocalDate endDate=LocalDate.of(2021, 05, 13);
		Mockito.when(staffPaymentLogDetailsDao.findPayrollOfAStaffMembersForCertainPeriod(this.staffmember.getStaffId(), startDate
				, endDate)
				).thenReturn(paymentLogDetailsList);
		

		List<StaffPaymentLogDetails> expected = staffPaymentLogDetailsServiceImpl.findPayrollOfAStaffMembersForCertainPeriod(this.staffmember.getStaffId(), startDate, endDate).getBody();

		Assertions.assertEquals(expected, paymentLogDetailsList);
		
	}
	
	@Test
	void testFindPayRollForAllStaffMmbrCertainPeriod()
	{
		List<Object[]> paymentLogDetailsList=new ArrayList<>();
		
		paymentLogDetailsList.add(new Object[3]);
		
		LocalDate startDate=LocalDate.of(2020, 05, 13);
		LocalDate endDate=LocalDate.of(2021, 05, 13);
		
		Mockito.when(staffPaymentLogDetailsDao.findPayrollOfAllStaffMembersForCertainPeriod(startDate, endDate)
				).thenReturn(paymentLogDetailsList);
		

		List expected = staffPaymentLogDetailsServiceImpl.findPayrollOfAllStaffMembersForCertainPeriod( startDate, endDate).getBody();

		Assertions.assertEquals(expected, paymentLogDetailsList);
		
	}
}

/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffPaymentLogDetailsServiceImpl.java
 */
package com.push.fileee.service.impl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.push.fileee.dao.IStaffMembersDao;
import com.push.fileee.dao.IStaffPaymentLogDetailsDao;
import com.push.fileee.model.StaffMembers;
import com.push.fileee.model.StaffMembers.PayRollType;
import com.push.fileee.model.StaffPaymentLogDetails;
import com.push.fileee.service.IStaffPaymentLogDetailsService;
import com.push.fileee.utils.UtilityClass;

/**
 * @author pintu
 *
 */
@Service
public class StaffPaymentLogDetailsServiceImpl implements IStaffPaymentLogDetailsService {

	/** The staff members dao. */
	@Autowired
	private IStaffMembersDao staffMembersDao;

	/** The staff payment log details dao. */
	@Autowired
	private IStaffPaymentLogDetailsDao staffPaymentLogDetailsDao;

	private Logger logger=LoggerFactory.getLogger(StaffPaymentLogDetailsServiceImpl.class);

	@Override
	public ResponseEntity<String> addWorkLog(StaffPaymentLogDetails staffPaymentLogDetails) {

		logger.info("@method addWorkLog ");
		if(staffPaymentLogDetails!=null && staffPaymentLogDetails.getStaffMembers()!=null)
		{
			int staffMemberId=staffPaymentLogDetails.getStaffMembers().getStaffId();
			logger.info("@method addWorkLog staffMemberId "+staffMemberId);


			if(staffMemberId>0)
			{
				Optional<StaffMembers> optionalMembers=staffMembersDao.findById(staffMemberId);

				if(optionalMembers.isPresent())
				{
					logger.info("@method addWorkLog Staff Member exist");
					return checkScenariosAndCreate(staffPaymentLogDetails,optionalMembers.get().getPayrollType(),staffMemberId);
				}else
				{
					logger.error("@method addWorkLog Staff Member doesn't exist");

					return ResponseEntity.badRequest().body(UtilityClass.STAFF_MEMBER_DOES_NOT_EXIST);
				}

			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilityClass.SOMETHING_WENT_WRONG);

	}

	/**
	 * Check scenarios.
	 *
	 * @param logDetails the log details
	 * @param payRoll the pay roll
	 * @param staffMemberId the staff member id
	 * @return the string
	 */
	private ResponseEntity<String> checkScenariosAndCreate(StaffPaymentLogDetails logDetails, PayRollType payRoll,int staffMemberId) {


		logger.info("@method checkScenariosAndCreate with payroll"+payRoll.name());
		if(payRoll.equals(PayRollType.FIXED))
		{
			BigInteger count=staffPaymentLogDetailsDao.findPaymentLogDetailsBasedOnStaffAndMonth
					(staffMemberId,logDetails.getEntryDate().getMonthValue());

			logger.debug("@method addWorkLog result of findPaymentLogDetailsBasedOnStaffAndMonth "+count.intValue());

			if(count.intValue()==0)
			{
				logDetails.setPerHourWage(0.0);
				logDetails.setTotalHours(0.0);

				return createWorkLog(logDetails);

			}else{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilityClass.WORK_LOG_ALREADY_ADDED_FOR_THIS_MONTH);
			}
		}else if(payRoll.equals(PayRollType.HOURLY) && UtilityClass.isNotNull(logDetails.getPerHourWage()) 
				&& UtilityClass.isNotNull(logDetails.getTotalHours()))
		{

			Double sumOfWorkingHours=staffPaymentLogDetailsDao.findPaymentLogDetailsBasedOnStaffAndDay(staffMemberId, logDetails.getEntryDate());

			logger.debug("@method addWorkLog result of findPaymentLogDetailsBasedOnStaffAndDay "+logDetails.getEntryDate()+" "
					+ "sumOfWorkingHours "+sumOfWorkingHours+" logDetails.getTotalHours() "+logDetails.getTotalHours());
			if(sumOfWorkingHours==null)
			{
				sumOfWorkingHours=0.0;
			}

			if(sumOfWorkingHours>=0.0 && (sumOfWorkingHours+=logDetails.getTotalHours())<24)
			{
				logDetails.setNetPay(logDetails.getPerHourWage()*logDetails.getTotalHours());

				return createWorkLog(logDetails);
			}else{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilityClass.YOU_HAVE_ENTERED_MORE_THAN_24_HRS_FOR_GIVEN_ENTRY_DATE);
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilityClass.SOMETHING_WENT_WRONG);
	}

	/**
	 * Creates the work log.
	 *
	 * @return the string
	 */
	private ResponseEntity<String> createWorkLog(StaffPaymentLogDetails logDetails) 
	{
		logger.info("@method createWorkLog ");

		logDetails.setCreateDate(LocalDate.now());
		logDetails=staffPaymentLogDetailsDao.save(logDetails);

		if(logDetails.getId()>0)
		{
			return ResponseEntity.ok().body(UtilityClass.WORK_LOG_ADDED_SUCCESSFULLY);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UtilityClass.SOMETHING_WENT_WRONG);
	}

	/**
	 * Find payroll of A staff members for certain period.
	 * 
	 * the system should allow getting payrolls for an employee for a certain period
	 *
	 * @param staffMemberId the staff member id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Override
	public ResponseEntity<List<StaffPaymentLogDetails>> findPayrollOfAStaffMembersForCertainPeriod(int staffMemberId,LocalDate startDate, LocalDate endDate) {

		logger.info("@method findPayrollOfAStaffMembersForCertainPeriod ");

		logger.debug("@method findPayrollOfAStaffMembersForCertainPeriod @param staffMemberId "+staffMemberId+" "
				+ "@param startDate "+startDate+" @param endDate "+endDate);
		if(staffPaymentLogDetailsDao.findById(staffMemberId)!=null)
		{
			List<StaffPaymentLogDetails> paymentLogDetails=staffPaymentLogDetailsDao.findPayrollOfAStaffMembersForCertainPeriod(staffMemberId, startDate, endDate);

			if(!CollectionUtils.isEmpty(paymentLogDetails))
			{
				logger.info("@method findPayrollOfAStaffMembersForCertainPeriod data found");
				return ResponseEntity.ok(paymentLogDetails);
			}else
			{
				logger.info("@method findPayrollOfAStaffMembersForCertainPeriod data not found");
				return ResponseEntity.noContent().build();
			}
		}else{
			logger.info("@method findPayrollOfAStaffMembersForCertainPeriod Entity not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}


	}


	/**
	 * Find payroll of all staff members for certain period.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Override
	public ResponseEntity<List<Object[]>> findPayrollOfAllStaffMembersForCertainPeriod(LocalDate startDate, LocalDate endDate) {

		logger.info("@method findPayrollOfAllStaffMembersForCertainPeriod ");

		logger.debug("@method findPayrollOfAllStaffMembersForCertainPeriod @param "
				+ "@param startDate "+startDate+" @param endDate "+endDate);

		List<Object[]> obj=staffPaymentLogDetailsDao.findPayrollOfAllStaffMembersForCertainPeriod(startDate, endDate);
		if(!CollectionUtils.isEmpty(obj))
		{
			logger.info("@method findPayrollOfAllStaffMembersForCertainPeriod data found");
			return ResponseEntity.ok().body(obj);
		}else
		{
			logger.info("@method findPayrollOfAllStaffMembersForCertainPeriod data not found");
			return ResponseEntity.noContent().build();
		}

	}

}



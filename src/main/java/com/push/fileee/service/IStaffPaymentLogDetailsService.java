/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: IStaffPaymentLogDetailsService.java
 */
package com.push.fileee.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.push.fileee.model.StaffPaymentLogDetails;

/**
 * The Interface IStaffPaymentLogDetailsService.
 */
public interface IStaffPaymentLogDetailsService {
	
	/**
	 * Adds the work log.
	 *
	 * @param staffPaymentLogDetails the staff payment log details
	 * @return the string
	 */
	ResponseEntity<String> addWorkLog(StaffPaymentLogDetails staffPaymentLogDetails);
	
	/**
	 * Find payroll of A staff members for certain period.
	 *
	 * @param staffMemberId the staff member id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	ResponseEntity<List<StaffPaymentLogDetails>> findPayrollOfAStaffMembersForCertainPeriod(int staffMemberId, LocalDate startDate,
			LocalDate endDate);
	
	/**
	 * Find payroll of all staff members for certain period.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	ResponseEntity<List<Object[]>> findPayrollOfAllStaffMembersForCertainPeriod(LocalDate startDate, LocalDate endDate);

}

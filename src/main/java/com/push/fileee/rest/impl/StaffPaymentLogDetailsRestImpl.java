/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffPaymentLogDetailsRestImpl.java
 */
package com.push.fileee.rest.impl;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.push.fileee.model.StaffPaymentLogDetails;
import com.push.fileee.service.IStaffPaymentLogDetailsService;
import com.push.fileee.utils.UtilityClass;

@RestController
@RequestMapping("/api/staffMembers")
public class StaffPaymentLogDetailsRestImpl {

	@Autowired
	private IStaffPaymentLogDetailsService logDetailsService;
	
	private Logger logger=LoggerFactory.getLogger(StaffPaymentLogDetailsRestImpl.class);
	
	/**
	 * Adds the staff members.
	 *
	 * @param staffMembers the staff members
	 * @return the string
	 */
	@PostMapping("/addWorkLog")
	public ResponseEntity<String> addWorkLog(@RequestBody StaffPaymentLogDetails staffPaymentLogDetails)
	{
		logger.info("@method addWorkLog ");

		return logDetailsService.addWorkLog(staffPaymentLogDetails);
	}
	
	
	/**
	 * Payroll for certain period.
	 *
	 * @param staffMemberId the staff member id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the string
	 */
	@GetMapping(value="/payrollForCertainPeriod/{staffMemberId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StaffPaymentLogDetails>> payrollForCertainPeriod(@PathVariable int staffMemberId,@RequestParam(required=true,name="startDate") @DateTimeFormat (pattern=UtilityClass
	.YYYY_MM_DD )LocalDate startDate,@RequestParam(required=true,name="endDate") @DateTimeFormat (pattern=UtilityClass
	.YYYY_MM_DD ) LocalDate endDate)
	{
		logger.info("@method payrollForCertainPeriod @param "+staffMemberId);

		if(startDate.isBefore(endDate))
		{
			return logDetailsService.findPayrollOfAStaffMembersForCertainPeriod(staffMemberId, startDate, endDate);
		}else{
			logger.info("@method payrollForCertainPeriod START DATE must be before End Date");
			
			return ResponseEntity
	                .badRequest()
	                .build();
		}
	}
	
	/**
	 * Payroll for all staff certain period.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@GetMapping(value="/payrollForAllStaffCertainPeriod",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object[]>> payrollForAllStaffCertainPeriod(@RequestParam(required=true,name="startDate") @DateTimeFormat (pattern=UtilityClass
	.YYYY_MM_DD )LocalDate startDate,@RequestParam(required=true,name="endDate") @DateTimeFormat (pattern=UtilityClass
	.YYYY_MM_DD ) LocalDate endDate)
	{
		logger.info("@method payrollForAllStaffCertainPeriod ");

		if(startDate.isBefore(endDate))
		{
			return logDetailsService.findPayrollOfAllStaffMembersForCertainPeriod(startDate, endDate);
		}else{
		
			logger.info("@method payrollForCertainPeriod START DATE must be before End Date");
			return ResponseEntity
	                .badRequest()
	                .build();
		}
	}
	
	@GetMapping(value="/exportData",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePayRollInformation(@RequestParam(required=true,name="startDate") @DateTimeFormat (pattern=UtilityClass
    		.YYYY_MM_DD )LocalDate startDate,@RequestParam(required=true,name="endDate") @DateTimeFormat (pattern=UtilityClass
    		.YYYY_MM_DD ) LocalDate endDate) {

		if(startDate.isBefore(endDate))
		{
	        ByteArrayInputStream bis = UtilityClass.generatePdf(logDetailsService.findPayrollOfAllStaffMembersForCertainPeriod(startDate, endDate).getBody());
	
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=STAFF_MEMBER_PAYMENT.pdf");
	
	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
		}else{
			logger.info("@method payrollForCertainPeriod START DATE must be before End Date");
			return ResponseEntity
	                .badRequest()
	                .build();
		}
    }
	
}

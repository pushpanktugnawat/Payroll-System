/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: IStaffPaymentLogDetailsDao.java
 */
package com.push.fileee.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.push.fileee.model.StaffPaymentLogDetails;

/**
 * The Interface IStaffPaymentLogDetailsDao.
 *
 * @author pintu
 */
public interface IStaffPaymentLogDetailsDao extends CrudRepository<StaffPaymentLogDetails, Integer> {

	/**
	 * Find payment log details based on staff and month.
	 *
	 * @param staffId the staff id
	 * @param month the month
	 * @return the big integer
	 */
	@Query("SELECT count(*) from StaffPaymentLogDetails where staffMembers.staffId=:staffId and MONTH(entryDate)=:month")
	public BigInteger findPaymentLogDetailsBasedOnStaffAndMonth(@Param("staffId") int staffId,@Param("month") int month);

	/**
	 * Find payment log details based on staff and day.
	 *
	 * @param staffId the staff id
	 * @param entryDate the entry date
	 * @return the double
	 */
	@Query("SELECT sum(totalHours) from StaffPaymentLogDetails where staffMembers.staffId=:staffId and entryDate=:entryDate group by entryDate having sum(totalHours)<24")
	public Double findPaymentLogDetailsBasedOnStaffAndDay(@Param("staffId") int staffId,@Param("entryDate") LocalDate entryDate);

	/**
	 * Find payroll for A staff members for certain period.
	 *
	 * @param staffId the staff id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list of StaffPayment Details
	 */
	@Query("SELECT s from StaffPaymentLogDetails s where s.staffMembers.staffId=:staffId and s.entryDate BETWEEN :startDate AND :endDate")
	public List<StaffPaymentLogDetails> findPayrollOfAStaffMembersForCertainPeriod(@Param("staffId") int staffId,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);


	/**
	 *  API to retrieve a json object with information about the money
	 * 		that should be paid out to an employee in a certain period.
	 *
	 * @param staffId the staff id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Query("SELECT sum(s.netPay) as TotalPayout ,e.name as Name,e.payrollType as PayrollType from StaffPaymentLogDetails s inner join "
			+ "StaffMembers e on s.staffMembers.staffId=e.staffId where s.entryDate BETWEEN :startDate AND :endDate group by s.staffMembers")
	public List<Object[]> findPayrollOfAllStaffMembersForCertainPeriod(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);



}

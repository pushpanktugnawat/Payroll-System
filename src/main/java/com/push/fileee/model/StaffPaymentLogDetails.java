/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffPaymentLogDetails.java
 */
package com.push.fileee.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author pintu
 *
 */
/**
 * @author pintu
 *
 */
@Entity
@Table(name="staff_payroll_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StaffPaymentLogDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/** The work log. */
	@Column(name="work_log")
	private String workLog;
	
	/** The total hours. */
	@Column(name="total_hours")
	private Double totalHours;
	
	/** The per hour wage. */
	@Column(name="per_hour_wage")
	private Double perHourWage;
	
	/** The net pay. */
	@Column(name="net_pay")
	private Double netPay;
	
	
	/** The staff members. */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="staff_members_id")
	private StaffMembers staffMembers;
	
	/** The entry date. 
	 * this column specify users log entry as they may log work for some other date as well
	 * 
	 * */
	@Column(name="entry_date",columnDefinition = "DATE")
	private LocalDate entryDate;
	
	/** The create date. */
	@Column(name="create_date",columnDefinition = "TIMESTAMP")
	private LocalDate createDate;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWorkLog() {
		return workLog;
	}

	public void setWorkLog(String workLog) {
		this.workLog = workLog;
	}

	public Double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	public Double getPerHourWage() {
		return perHourWage;
	}

	public void setPerHourWage(Double perHourWage) {
		this.perHourWage = perHourWage;
	}

	public Double getNetPay() {
		return netPay;
	}

	public void setNetPay(Double netPay) {
		this.netPay = netPay;
	}

	public StaffMembers getStaffMembers() {
		return staffMembers;
	}

	public void setStaffMembers(StaffMembers staffMembers) {
		this.staffMembers = staffMembers;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	/** The entry date. 
	 * this column specify users log entry as they may log work for some other date as well
	 * 
	 * */
	public LocalDate getEntryDate() {
		return entryDate;
	}

	/** The entry date. 
	 * this column specify users log entry as they may log work for some other date as well
	 * 
	 * */
	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * Instantiates a new staff payment log details.
	 *
	 * @param id the id
	 * @param workLog the work log
	 * @param totalHours the total hours
	 * @param perHourWage the per hour wage
	 * @param netPay the net pay
	 * @param staffMembers the staff members
	 * @param entryDate the entry date
	 */
	public StaffPaymentLogDetails(int id, String workLog, Double totalHours, Double perHourWage, 
			StaffMembers staffMembers, LocalDate entryDate) {
		super();
		this.id = id;
		this.workLog = workLog;
		this.totalHours = totalHours;
		this.perHourWage = perHourWage;
		this.staffMembers = staffMembers;
		this.entryDate = entryDate;
	}
	
	

	/**
	 * Instantiates a new staff payment log details.
	 */
	public StaffPaymentLogDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((entryDate == null) ? 0 : entryDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((netPay == null) ? 0 : netPay.hashCode());
		result = prime * result + ((perHourWage == null) ? 0 : perHourWage.hashCode());
		result = prime * result + ((staffMembers == null) ? 0 : staffMembers.hashCode());
		result = prime * result + ((totalHours == null) ? 0 : totalHours.hashCode());
		result = prime * result + ((workLog == null) ? 0 : workLog.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StaffPaymentLogDetails other = (StaffPaymentLogDetails) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (entryDate == null) {
			if (other.entryDate != null)
				return false;
		} else if (!entryDate.equals(other.entryDate))
			return false;
		if (id != other.id)
			return false;
		if (netPay == null) {
			if (other.netPay != null)
				return false;
		} else if (!netPay.equals(other.netPay))
			return false;
		if (perHourWage == null) {
			if (other.perHourWage != null)
				return false;
		} else if (!perHourWage.equals(other.perHourWage))
			return false;
		if (staffMembers == null) {
			if (other.staffMembers != null)
				return false;
		} else if (!staffMembers.equals(other.staffMembers))
			return false;
		if (totalHours == null) {
			if (other.totalHours != null)
				return false;
		} else if (!totalHours.equals(other.totalHours))
			return false;
		if (workLog == null) {
			if (other.workLog != null)
				return false;
		} else if (!workLog.equals(other.workLog))
			return false;
		return true;
	}
	
	
	
	
	
	
}

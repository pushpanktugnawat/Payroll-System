/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffMembers.java
 */
package com.push.fileee.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author pintu
 * this class is used to store information for Staff Members
 *
 */
@Table(name="staff_members")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StaffMembers implements Serializable{

	/** The staff id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="staff_id")
	private int staffId;
	
	/** The name. */
	@Column(nullable=false)
	private String name;
	
	/** The payroll type. */
	@Enumerated(EnumType.STRING)
	@Column(length = 8,name="payroll_type",nullable=false)
	private PayRollType payrollType;
	
	/**
	 * The Enum PayRollType.
	 */
	public enum PayRollType{HOURLY,FIXED}
	
	@Column(name="created_time")
	private LocalDateTime createdTime;
	
	@Column(name="modifiedTime")
	private LocalDateTime modifiedTime;
	

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PayRollType getPayrollType() {
		return payrollType;
	}

	public void setPayrollType(PayRollType payrollType) {
		this.payrollType = payrollType;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	/**
	 * Instantiates a new staff members.
	 *
	 * @param staffId the staff id
	 * @param name the name
	 * @param payrollType the payroll type
	 */
	public StaffMembers(int staffId, String name, PayRollType payrollType) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.payrollType = payrollType;
	}
	
	

	/**
	 * Instantiates a new staff members.
	 */
	public StaffMembers() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdTime == null) ? 0 : createdTime.hashCode());
		result = prime * result + ((modifiedTime == null) ? 0 : modifiedTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((payrollType == null) ? 0 : payrollType.hashCode());
		result = prime * result + staffId;
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
		StaffMembers other = (StaffMembers) obj;
		if (createdTime == null) {
			if (other.createdTime != null)
				return false;
		} else if (!createdTime.equals(other.createdTime))
			return false;
		if (modifiedTime == null) {
			if (other.modifiedTime != null)
				return false;
		} else if (!modifiedTime.equals(other.modifiedTime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (payrollType != other.payrollType)
			return false;
		if (staffId != other.staffId)
			return false;
		return true;
	};
	
	
	
	
	
	
	
}

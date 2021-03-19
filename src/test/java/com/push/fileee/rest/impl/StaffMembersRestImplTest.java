/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: StaffMembersRestImplTest.java
 */
package com.push.fileee.rest.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.push.fileee.model.StaffMembers;
import com.push.fileee.model.StaffMembers.PayRollType;
import com.push.fileee.service.IStaffMembersService;
import com.push.fileee.utils.UtilityClass;

/**
 * The Class StaffMembersRestImplTest.
 *
 * @author pintu
 */
@WebMvcTest(controllers = StaffMembersRestImpl.class)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public class StaffMembersRestImplTest {

	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStaffMembersService iStaffMembersService;

   
    /** The staff members. */
	private List<StaffMembers> staffMembers;       

	/** The staffmember. */
	private StaffMembers staffmember;
	

    @Autowired
    private ObjectMapper objectMapper;
	
	
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

    @Test
    @Tag("testGetAllStaffMembers")
    void testGetAllStaffMembers() throws Exception {

    	Mockito.when(iStaffMembersService.getAllStaffMembers()).thenReturn(this.staffMembers);
    	mockMvc.perform(get("/api/staffMembers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(this.staffMembers.size()));
    }
    
    @Test
    @Tag("testGetAllStaffMembersWithBubbleSortSuccess")
    void testGetAllStaffMembersWithBubbleSortSuccess() throws Exception {

    	Mockito.when(iStaffMembersService.getAllStaffMembersByBubbleSort()).thenReturn(this.staffMembers);
    	mockMvc.perform(get("/api/staffMembers?bubbleSort=true")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(this.staffMembers.size()));
    }
    
    @Test
    @Tag("testGetAllStaffMembersWithNoResult")
    void testGetAllStaffMembersWithNoResult() throws Exception {

    	Mockito.when(iStaffMembersService.getAllStaffMembers()).thenReturn(null);
    	mockMvc.perform(get("/api/staffMembers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    @Tag("testAddStaffMembersSuccess")
    void testAddStaffMembersSuccess() throws Exception {

    	Mockito.when(iStaffMembersService.addStaffMembers(staffmember)).thenReturn(ResponseEntity.ok(UtilityClass.STAFF_MEMBER_ADDED_SUCCESSFULLY));
    	mockMvc.perform(post("/api/staffMembers").contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(this.staffmember))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    @Tag("testUpdateStaffMembersSuccess")
    void testUpdateStaffMembersSuccess() throws Exception {

    	Mockito.when(iStaffMembersService.updateStaffMembers(staffmember)).thenReturn(ResponseEntity.ok(UtilityClass.STAFF_MEMBER_UPDATED_SUCCESSFULLY));
    	mockMvc.perform(put("/api/staffMembers").contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(this.staffmember))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    @Tag("testRemoveStaffMembersSuccess")
    void testRemoveStaffMembersSuccess() throws Exception {

    	Mockito.when(iStaffMembersService.removeStaffMembers(1)).thenReturn(ResponseEntity.ok(UtilityClass.STAFF_MEMBER_DELETED_SUCCESSFULLY));
    	mockMvc.perform(delete("/api/staffMembers/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
}

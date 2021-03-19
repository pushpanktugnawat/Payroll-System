package com.push.fileee.rest.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.push.fileee.model.StaffPaymentLogDetails;
import com.push.fileee.service.IStaffPaymentLogDetailsService;
import com.push.fileee.utils.UtilityClass;

@WebMvcTest(controllers = StaffPaymentLogDetailsRestImpl.class)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public class StaffPaymentLogDetailsRestImplTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStaffPaymentLogDetailsService staffPaymentLogDetailsService;
    
    
	/** The staffmember. */
	private StaffMembers staffmember;
	

    @Autowired
    private ObjectMapper objectMapper;

	private StaffPaymentLogDetails staffPaymentLogDetails;
	

	@BeforeEach
	public void setup()
	{
		this.staffmember=new StaffMembers(3, "David", PayRollType.FIXED);
		this.staffPaymentLogDetails=new StaffPaymentLogDetails(1, "Worked on Create task", 4.5, 60.5, this.staffmember, LocalDate.now());
		
	}
    
    @Test
    @Tag("testAddWorkLogSuccess")
    void testAddWorkLogSuccess() throws Exception {

    	Mockito.when(staffPaymentLogDetailsService.addWorkLog(this.staffPaymentLogDetails)).
    	thenReturn(ResponseEntity.ok().body(UtilityClass.WORK_LOG_ADDED_SUCCESSFULLY));
    	mockMvc.perform(post("/api/staffMembers/addWorkLog").contentType(MediaType.APPLICATION_JSON).
    			content(objectMapper.writeValueAsString(this.staffPaymentLogDetails))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    @Tag("testFindPayRollForAStaffMmbrCertainPeriodSuccess")
    void testFindPayRollForAStaffMmbrCertainPeriodSuccess() throws Exception {

    	LocalDate startDate=LocalDate.of(2020, 05, 13);
		LocalDate endDate=LocalDate.of(2021, 05, 13);
		
		List<StaffPaymentLogDetails> paymentLogDetailsList=new ArrayList<>();
		paymentLogDetailsList.add(staffPaymentLogDetails);
		
    	Mockito.when(staffPaymentLogDetailsService.findPayrollOfAStaffMembersForCertainPeriod(3, startDate, endDate)).
    	thenReturn(ResponseEntity.ok(paymentLogDetailsList));
    	
    	mockMvc.perform(get("/api/staffMembers/payrollForCertainPeriod/{staffMemberId}",3).param("startDate", "2020-05-13").param("endDate","2021-05-13").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    
    @Test
    @Tag("testFindPayRollForAllStaffMmbrCertainPeriodSuccess")
    void testFindPayRollForAllStaffMmbrCertainPeriodSuccess() throws Exception {

    	LocalDate startDate=LocalDate.of(2020, 05, 13);
		LocalDate endDate=LocalDate.of(2021, 05, 13);
		
		List<Object[]> paymentLogDetailsList=new ArrayList<>();
		
    	Mockito.when(staffPaymentLogDetailsService.findPayrollOfAllStaffMembersForCertainPeriod(startDate, endDate)).
    	thenReturn(ResponseEntity.ok().body(paymentLogDetailsList));
    	
    	mockMvc.perform(get("/api/staffMembers/payrollForAllStaffCertainPeriod/").param("startDate", "2020-05-13").param("endDate","2021-05-13").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
}

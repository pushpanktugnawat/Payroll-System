/*
 *  Copyright (c) 2020 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: UtilityClass.java
 */
package com.push.fileee.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * The Class UtilityClass.
 */
public class UtilityClass {


	private static final String SUCCESS = "Success";

	private static final String FAILURE = "Failure";

	/** The Constant STAFF_MEMBER_ADDED_SUCCESSFULLY. */
	public static final String STAFF_MEMBER_ADDED_SUCCESSFULLY = convertJsonObjectToString("Staff Member added successfully",SUCCESS);

	/** The Constant SOMETHING_WENT_WRONG. */
	public static final String SOMETHING_WENT_WRONG = convertJsonObjectToString("Something Went Wrong",FAILURE);

	/** The Constant STAFF_MEMBER_CAN_NOT_BE_NULL. */
	public static final String STAFF_MEMBER_CAN_NOT_BE_NULL = convertJsonObjectToString("Staff Member Can't be Null",FAILURE);

	/** The Constant STAFF_MEMBER_UPDATED_SUCCESSFULLY. */
	public static final String STAFF_MEMBER_UPDATED_SUCCESSFULLY = convertJsonObjectToString("Staff Member updated successfully",SUCCESS);

	/** The Constant STAFF_MEMBER_DOES_NOT_EXIST. */
	public static final String STAFF_MEMBER_DOES_NOT_EXIST = convertJsonObjectToString("Staff Member doesn't exist",FAILURE);

	/** The Constant STAFF_MEMBER_DELETED_SUCCESSFULLY. */
	public static final String STAFF_MEMBER_DELETED_SUCCESSFULLY = convertJsonObjectToString("Staff Member removed successfully",SUCCESS);

	/** The Constant WORK_LOG_ADDED_SUCCESSFULLY. */
	public static final String WORK_LOG_ADDED_SUCCESSFULLY =convertJsonObjectToString("Work Log Added Successfully",SUCCESS);

	/** The Constant WORK_LOG_ALREADY_ADDED_FOR_THIS_MONTH. */
	public static final String WORK_LOG_ALREADY_ADDED_FOR_THIS_MONTH =convertJsonObjectToString("Work log already added for this month",FAILURE);

	/** The Constant YOU_HAVE_ENTERED_MORE_THAN_24_HRS_FOR_GIVEN_ENTRY_DATE. */
	public static final String YOU_HAVE_ENTERED_MORE_THAN_24_HRS_FOR_GIVEN_ENTRY_DATE = convertJsonObjectToString("You don't have enough hours remaining for given day",FAILURE);

	/** The Constant YYYY_MM_DD. */
	public static final String YYYY_MM_DD="yyyy-MM-dd";

	public static final Object START_DATE_CANT_BE_GREATER_THAN_EQUAL_TO_END_DATE = convertJsonObjectToString("Start Date Can't be Greater Than equals to End Date", FAILURE);

	
	/**
	 * Checks if is not null.
	 *
	 * @return true, if is not null
	 */
	public static boolean isNotNull(Double dbl) {
		// TODO Auto-generated method stub
		if(dbl!=null && dbl.doubleValue()>0.0)
		{
			return true;
		}
		return false;
	}

	 
	 

	private static String convertJsonObjectToString(String message,String status) {
		
		ObjectMapper objectMapper=new ObjectMapper();
		CustomResponse customResponse=new CustomResponse();
		
		try 
		{
			customResponse.setMessage(message);
			customResponse.setStatus(status);
			return objectMapper.writeValueAsString(customResponse);

		} catch ( JsonProcessingException e) {
			// TODO Auto-generated catch block
			objectMapper=null;
			customResponse.setStatus(FAILURE);
		}
		return customResponse.getStatus();

	}

	public static ByteArrayInputStream generatePdf(
			List<Object[]> payrollOfAllStaffMembersForCertainPeriod) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(90);
			table.setWidths(new int[]{5, 5, 5});

			Font headFont = FontFactory.getFont(FontFactory.COURIER_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Payroll Type", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Amount", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			if(!CollectionUtils.isEmpty(payrollOfAllStaffMembersForCertainPeriod) )
			{
				for (Object[] obj : payrollOfAllStaffMembersForCertainPeriod) {

					PdfPCell cell;

					cell = new PdfPCell(new Phrase(obj[1].toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new com.lowagie.text.Phrase(obj[2].toString()));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(obj[0].toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
			}else{


				PdfPCell cell = new PdfPCell(new Phrase("NO DATA FOUND"));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(3);
				table.addCell(cell);
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);

			document.close();

		} catch (DocumentException ex) {


		}

		return new ByteArrayInputStream(out.toByteArray());

	}


}

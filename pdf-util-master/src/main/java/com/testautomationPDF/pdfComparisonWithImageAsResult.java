package com.testautomationPDF;

import java.io.IOException;

import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;

public class pdfComparisonWithImageAsResult {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
String folderLocation= "C:\\Users\\916926\\OneDrive - Cognizant\\Documents\\Image";
String actual="C:\\Users\\916926\\Downloads\\MRS-RCVD-PD.pdf";
String expected="C:\\Users\\916926\\Downloads\\USEN--Pall_Qty_Diff.pdf";

PDFUtil pdfUtil = new PDFUtil();
pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
pdfUtil.highlightPdfDifference(true);
pdfUtil.setImageDestinationPath(folderLocation);
pdfUtil.compare(expected,actual);

	}

}

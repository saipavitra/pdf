package com.testautomationguru;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.TestException;

public final class Main {
	
	public static void main(String[] args) throws IOException {
		String File1="C:\\Users\\916926\\Downloads\\MRS-RCVD-PD.pdf";
		String File2="C:\\Users\\916926\\Downloads\\USEN--Pall_Qty_Diff.pdf";
		
		URL pdfURL = new URL(File1);
		InputStream is = pdfURL.openStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		PDDocument pdf1 = PDDocument.load(bis);
		
		URL pdfURL1 = new URL(File2);
		InputStream is1 = pdfURL.openStream();
		BufferedInputStream bis1 = new BufferedInputStream(is1);
		PDDocument pdf2 = PDDocument.load(bis1);
		
//			PDFTextStripper strip = new PDFTextStripper();
//			String stripText= strip.getText(doc);
//			System.out.println(stripText);
			
			File pdfFile1 = new File(File1);
			File pdfFile2 = new File(File2);
			pdfFile1.deleteOnExit();
			pdfFile2.deleteOnExit();
			FileUtils.copyURLToFile(pdfURL, pdfFile1);
			FileUtils.copyURLToFile(pdfURL1, pdfFile2);
			
//			PDFTextStripper strip1 = new PDFTextStripper();
//			String stripText1= strip1.getText(doc1);
			
//			PDDocument pdf1 = PDDocument.load(pdfFile1);
//			PDDocument pdf2 = PDDocument.load(pdfFile2);
			PDPageTree pdf1pages = pdf1.getDocumentCatalog().getPages();
			 PDPageTree pdf2pages = pdf2.getDocumentCatalog().getPages(); 

			 try { 
			if (pdf1pages.getCount() != pdf2pages.getCount()) { 
			String message = "Number of pages in the files ("+pdfFile1+","+pdfFile2+") do not match. pdfFile1 has "+pdf1pages.getCount()+" no pages, while pdf2pages has "+pdf2pages.getCount()+" no of pages";
			System.out.println(message);
//			throw new TestException(message);
			 } 
			PDFTextStripper pdfStripper = new PDFTextStripper(); 
			System.out.println("pdfStripper is :- " + pdfStripper); 
			System.out.println("pdf1pages.size() is :- " + pdf1pages.getCount());
			for (int i = 0; i < pdf1pages.getCount(); i++) {
			 pdfStripper.setStartPage(i + 1); 
			pdfStripper.setEndPage(i + 1); 
			String pdf1PageText = pdfStripper.getText(pdf1);
			 String pdf2PageText = pdfStripper.getText(pdf2); 
			if (!pdf1PageText.equals(pdf2PageText))
			 { 
			String message = "Contents of the files ("+pdfFile1+","+pdfFile2+") do not match on Page no: " + (i + 1)+" pdf1PageText is : "+pdf1PageText+" , while pdf2PageText is : "+pdf2PageText; 
			System.out.println(message); 
			System.out.println("fff");
			System.out.println("pdf1PageText is " + pdf1PageText);
			System.out.println("pdf2PageText is " + pdf2PageText); 
			String difference = StringUtils.difference(pdf1PageText, pdf2PageText);
//			 LOG.debug("difference is "+difference); 
//			throw new TestException(message+" [[ Difference is ]] "+difference);
			 }
			 }
			System.out.println("Returning True , as PDF Files ("+pdfFile1+","+pdfFile2+") get matched"); 
			}
			 finally { 
				 pdf1.close();
				 pdf2.close();

			 }
			 }

			//pdfutil.compare(file1,file2,1,1);
			
			//pdfutil.setCompareMode(CompareMode.VISUAL_MODE);
			
//			if(args.length>2){
//				pdfutil.highlightPdfDifference(true);
//				pdfutil.setImageDestinationPath(args[2]);				
//			}

			//pdfutil.compare(args[0], args[1]);
//			PDFUtil pdfutil = new PDFUtil();
//			boolean flag =pdfutil.compare(doc,doc1,1,1);
//			if()
//			System.out.println(flag);
//		}
		
//	}

//	private static void showUsage(){
//		System.out.println("Usage: java -jar pdf-util.jar file1.pdf file2.pdf [Optional:image-destination-path]");
//	}
}
		
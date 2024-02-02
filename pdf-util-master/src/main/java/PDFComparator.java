import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;


public class PDFComparator {
	public static void main(String[] args) throws Exception {

		int mismatchValidationFlag = 0;
		int minimumPdfLength = 0;
		int subtractionOfPdfLengths;
		int maximumPdfLength=0;
		ExtentReports extent;
		String sourcePdf="MRS-RCVD-PD.pdf";
		//String targetPdf="USEN--Pall_Qty_Diff.pdf";
		String targetPdf="MRS-RCVD-PD.pdf";
		String imageResult="imageResult";
		
		//to read the source and target file
	    File sourceFile = new File(sourcePdf);
	    File targetFile = new File(targetPdf);
	    File imageFile = new File(imageResult);
	    
	    //to get the path of the source and target files
	    String sourcePath=sourceFile.getAbsolutePath();
	    String targetPath=targetFile.getAbsolutePath();
	    String imagePath=imageFile.getAbsolutePath();

	    System.out.println(sourcePath);
        System.out.println(targetPath);
	    
		// loaded an existing PDF document
		PDDocument sourceDocument = PDDocument.load(sourceFile);
		PDDocument targetDocument = PDDocument.load(targetFile);

		// stripped out all of the text from the PDF document
		PDFTextStripper sourceStripper = new PDFTextStripper();
		String sourcePdfText = sourceStripper.getText(sourceDocument);

		PDFTextStripper targetStripper = new PDFTextStripper();
		String targetPdfText = targetStripper.getText(targetDocument);

		// Splitted the pdf words by using the delimiter as "multiple space"
		String[] sourcePdfWords = sourcePdfText.split("\\s+");
		String[] targetPdfWords = targetPdfText.split("\\s+");

		//to get the length of the pdf
		int sourcePdfWordsLength = sourcePdfWords.length;
		int targetPdfWordsLength = targetPdfWords.length;

		System.out.println("count of pdf 1 words : " + sourcePdfWordsLength);
		System.out.println("count of pdf 2 words : " + targetPdfWordsLength);

		// create the htmlReporter object
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");

		// create ExtentReports and attach reporter(s)
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// creates a toggle for the given test, add all log events under it
		ExtentTest extentTestObj = extent.createTest("comparison of two pdf",
				"Compare the two PDF texts and print mismatched words");

		// comparison of two pdf's word count
		// store the subtracted value and declared in for loop
		if (sourcePdfWords.length <= targetPdfWords.length) {
			subtractionOfPdfLengths = targetPdfWords.length - sourcePdfWords.length;
			minimumPdfLength = targetPdfWordsLength - subtractionOfPdfLengths;
		} else {
			subtractionOfPdfLengths = sourcePdfWords.length - targetPdfWords.length;   
			minimumPdfLength = sourcePdfWordsLength - subtractionOfPdfLengths;
		}
		
		//store the maximum length
		if (sourcePdfWords.length < targetPdfWords.length) {
			maximumPdfLength=targetPdfWords.length;
		} else {
			maximumPdfLength=sourcePdfWords.length;
		}
		
         int passCount=0;
         int failCount=0;
		// Compare the two PDF texts and print mismatched words
		for (int i = 0; i < minimumPdfLength; i++) {
			if (!sourcePdfWords[i].equals(targetPdfWords[i])) {

				extentTestObj.fail("Mismatch at word " + i + ": " + sourcePdf +": " + sourcePdfWords[i] + " and " + targetPdf +": "+ targetPdfWords[i]);
				mismatchValidationFlag = 1;
				failCount++;
			}
			else if(sourcePdfWords[i].equals(targetPdfWords[i]))
			{   extentTestObj.pass("Matched word " + i + ": " + sourcePdf +": " + sourcePdfWords[i] + " and " +targetPdf +": "+ targetPdfWords[i]);
				passCount++;
				System.out.println("Matched words " + i + ": " + sourcePdf +": " + sourcePdfWords[i] + " and "+targetPdf +": "+ targetPdfWords[i]);
			}
		}
		
		//print the words which is missed 
		System.out.println(minimumPdfLength + " "+ maximumPdfLength);
		if (sourcePdfWords.length > targetPdfWords.length)
		{
		for (int i = minimumPdfLength;i<maximumPdfLength; i++) {
			
			extentTestObj.skip("Missed word " + i + ": " + targetPdf+ " : " + sourcePdfWords[i]);
			System.out.println("Missed word " + i + ": " + targetPdf+ " : " + sourcePdfWords[i]);
			}
		}
		else
		{
			for (int i = minimumPdfLength;i<maximumPdfLength; i++) {
				
				extentTestObj.skip("Missed word " + i + ": " + sourcePdf + " : " + targetPdfWords[i]);
				System.out.println("Missed word " + i + ": " + sourcePdf + " : " + targetPdfWords[i]);
				}
		}
		
		
		System.out.println("Number of word matched in both the pdfs : "+passCount);
		System.out.println("Number of word mismatched in both the pdfs : "+failCount);

		// if there is no mismatch of words between two PDFs , print the success message in extent report
		if (mismatchValidationFlag == 0) {
			extentTestObj.pass("No mismatch of words found when comparing PDF 1 and 2");
		}

		//PDF Image validation 
//		PDFUtil pdfUtil = new PDFUtil();
//		pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
//		pdfUtil.highlightPdfDifference(true);
//		pdfUtil.setImageDestinationPath(imagePath);
//		pdfUtil.compareAllPages(true);
//		pdfUtil.compare(sourcePath,targetPath);
		
		//Note -> uncomment the below line to use "compare both pdfs with specific page" functionality
		//pdfUtil.compare(pdf1Path,pdf2Path,1,1);
		
		
		//writes the report into the file
		extent.flush();
	}
}
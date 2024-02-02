import java.io.File;
import java.awt.AWTException;	
import java.awt.Robot;	
import java.awt.event.KeyEvent;	
import java.io.IOException;
import java.sql.DriverAction;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PDFContentsCheck {

	public static String url = "https://id.mm100nonprod.mastermindtms.com/auth/realms/dev.mm100.mastermindtms.com/protocol/openid-connect/auth?client_id=https%3A%2F%2Fdev.mm100.mastermindtms.com%2F&redirect_uri=https%3A%2F%2Fdev.mm100.mastermindtms.com%2Fcustomers%2Fsearch%3FcustomerLevelType%3D%257B%2522id%2522%253A%2522_not_selected%2522%252C%2522label%2522%253A%2522---%2522%257D%26customerOpportunityType%3D%257B%2522id%2522%253A%2522_not_selected%2522%252C%2522label%2522%253A%2522---%2522%257D%26customerStatusType%3D%257B%2522id%2522%253A%2522_not_selected%2522%252C%2522label%2522%253A%2522---%2522%257D%26employee%3D%257B%2522id%2522%253A%2522%2522%252C%2522label%2522%253A%2522%2522%257D%26identifierType%3D%257B%2522id%2522%253A%2522_not_selected%2522%252C%2522label%2522%253A%2522---%2522%257D%26name%3Dtest%26ts%3D1683021236954%26login-init%3Dtrue&state=ab231c99-fa6b-488c-bbb8-ef3d7a3a83bb&response_mode=fragment&response_type=code&scope=openid&nonce=83e22f0b-aaee-43f4-a4be-4e332ead1804";
	public static WebDriver driver;
	public static void main(String[] args) throws InvalidPasswordException, IOException, InterruptedException, AWTException {
		// TODO Auto-generated method stub
//		String routeNum = "5000319621";
//		String routeNum = "5001662389";
		
		String routeNum = "5001664050";
		String browserType = "chrome";
		//WebDriver driver = null;
		if (browserType.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserType.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserType.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.get(url);
		System.out.println("Application Launched successfully");
		driver.findElement(By.xpath("//*[@id='show-email']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("KeycloakSuperUser@mastery.net");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("*D6whzhTHX*t");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='kc-login']")).click();
		Thread.sleep(15000);
		driver.findElement(By.xpath("//*[contains(text(),'Loads')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@data-testid='nav-link-load-search']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='routeCode']")).sendKeys(routeNum);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@data-testid='load-search-submit']")).click();
		Thread.sleep(10000);
		String Mode = driver.findElement(By.xpath("(//*[@data-cellheader='Mode'])[1]")).getText();
		Thread.sleep(3000);
		String Size = driver.findElement(By.xpath("(//*[@data-cellheader='Size'])[1]")).getText();
		Thread.sleep(3000);
		String Equipment = driver.findElement(By.xpath("(//*[@data-cellheader='Equip'])[1]")).getText();
		Equipment = Equipment.replaceAll(" ", "");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@data-cellheader='Load #'])[1]")).click();
		Thread.sleep(15000);
		String parentWindow = driver.getWindowHandle();
		Set<String> childWindow = driver.getWindowHandles();
		for (String allWindow : childWindow) {
			if (allWindow != parentWindow) {
				driver.switchTo().window(allWindow);
				System.out.println("switched to next window");
			}
		}
	
		Thread.sleep(70000);
		driver.manage().window().maximize();
		Thread.sleep(10000);

		driver.findElement(By.xpath("//div[@data-table-title='load-orders']")).click();
		Thread.sleep(5000);
		Actions act = new Actions(driver);
		for (int i = 0; i < 12; i++) {
			act.sendKeys(Keys.PAGE_DOWN).build().perform();
			Thread.sleep(3000);
		}
		// Page Down
		System.out.println("Scroll down perfomed");
		String RouteNum = driver.findElement(By.xpath("//*[@data-cellheader='Route #' and @title='" + routeNum + "']"))
				.getText();
		Thread.sleep(3000);
		String Stops = driver.findElement(By.xpath("//*[@data-cellheader='Route #' and @title='" + routeNum
				+ "']//following-sibling::div[@data-cellheader='Stops']")).getText();
		Thread.sleep(3000);
		String Equipment1 = driver.findElement(By.xpath("//*[@data-cellheader='Route #' and @title='" + routeNum
				+ "']//following-sibling::div[@data-cellheader='Equipment']")).getText();
		Equipment1 = Equipment1.replaceAll("[a-zA-Z,]", "");
		Equipment1 = Equipment1.replaceAll(" ", "");
		//System.out.println("Web" + Equipment1);

		String newEquipment = Equipment.concat(Equipment1);
		//System.out.println("Mastery" + newEquipment);

		Thread.sleep(5000);
		driver.findElement(By.xpath("(//*[@data-cellheader='Route #' and @title='" + routeNum
				+ "']//preceding-sibling::div//following-sibling::div)[1]")).click();
		for (int i = 0; i < 5; i++) {
			act.sendKeys(Keys.PAGE_DOWN).build().perform();
			Thread.sleep(3000);
		}
		String RouteType = driver.findElement(By.xpath("//*[contains(text(),'Route Type')]//following-sibling::span"))
				.getText();
		Thread.sleep(5000);
		//System.out.println(RouteType);
		System.out.println("Fetched all the datas from Mastery application");
		Thread.sleep(5000);

		// document section click
		WebElement element = driver.findElement(By.xpath("//*[@data-testid='Documents-Panel']"));
		int locationX = element.getLocation().getX();
		int locationY = element.getLocation().getY();
		//System.out.println(locationX + "   " + locationY);
		Actions action = new Actions(driver);
		Thread.sleep(5000);
		action.moveByOffset(locationX, locationY + 5).click().build().perform();
		Thread.sleep(5000);

		driver.findElement(By.xpath("(//div[@title='RateConf_" + routeNum + ".pdf'])[1]//preceding-sibling::div[4]"))
				.click();
		Thread.sleep(10000);

		//driver.findElement(By.xpath("(//div[@data-valuetext='Preview'])[1]")).click();
		driver.findElement(By.xpath("(//div[@data-valuetext='Download'])[1]")).click();
		
		Thread.sleep(10000);
		
		String parentWindow1 = driver.getWindowHandle();
		Set<String> childWindow1 = driver.getWindowHandles();
		for (String allWindow : childWindow1) {
			if (allWindow != parentWindow1) {
				driver.switchTo().window(allWindow);
				System.out.println("switched to next window");
			}
		}
		
	      
		Thread.sleep(5000);
		Robot robot = new Robot();  // Robot class throws AWT Exception      
        Thread.sleep(5000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        Thread.sleep(3000);
        robot.keyPress(KeyEvent.VK_S);
        Thread.sleep(5000);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(5000);
        robot.keyRelease(KeyEvent.VK_S);
        Thread.sleep(5000);
        robot.keyPress(KeyEvent.VK_ENTER);
        
        
		ExtentReports extent;
//		String sourcePdf = "1685036317986-RateConf_" + RouteNum + ".pdf";
//
//		String TermAndConditions = "This confirmation is subject to the terms of the Broker-Carrier Contract agreement and this document constitutes an amendment to the Contract. If the carrier has not signed the contract, then the rate shown above is the agreed individually negotiated rate and no other rate shall apply including and carrier tariff rate or terms. This load shall not be DOUBLE BROKERED. No additional charges not listed above may be added by the carrier. Any additional charges must appear on a revised confirmation. Carrier must include signed copy of the shipper’s bill of lading and proof of delivery with invoice to Broker. Rates, except as specifically designated above, are inclusive of any fuel surcharge. Any Lumper Fee’s must be reported to broker within 24 hours of delivery. OS&D must be reported while at receiver. Broker must be notified 30 minutes prior to starting detention. Carrier hereby confirms that it maintains applicable and valid insurance without exclusions that would prevent coverage for the items listed above. Carrier has atleast $1,000,000 in automotive liability coverage and $100,000 in cargo insurance. Carrier agrees to comply with all U.S. DOT regulation applicable to is operations while transporting said shipment.d ALL LOADS ARE SUBJECT TO ELECTRONIC MONITORING";
//		String[] targetPdfWords = TermAndConditions.split("\\s+");

		String home = System.getProperty("user.home");

		// to read the source and target file
		//File sourceFile = new File(home + "\\Downloads\\" + sourcePdf);
		
		File sourceFile = new File(home + "\\Downloads\\");
		File[] arr=sourceFile.listFiles();
        System.out.println(sourceFile.getName());
		Thread.sleep(5000);
		String sourcePath;
		String sourceFile1;
		int filesLen=arr.length-1;
		System.out.println(filesLen);
		for (int f=0;f<=filesLen;f++)
		{
			sourcePath=arr[f].toString();
           // System.out.println(sourcePath);
			sourceFile1 = arr[f].getName();
			//System.out.println(sourceFile1);
			sourceFile = new File(home + "\\Downloads\\"+ sourceFile1);
			if(sourcePath.contains("-RateConf_" + routeNum + ".pdf"))
			{
				System.out.println(sourcePath);
				System.out.println(sourceFile1);
				break;
				
			}
		}

		// to get the path of the source and target files
//		String sourcePath = sourceFile.getAbsolutePath();
//
//		System.out.println(sourcePath);
//
		Thread.sleep(5000);
		// loaded an existing PDF document
		PDDocument sourceDocument = PDDocument.load(sourceFile);

		// stripped out all of the text from the PDF document
		PDFTextStripper sourceStripper = new PDFTextStripper();
		String sourcePdfText = sourceStripper.getText(sourceDocument);

		// Splitted the pdf words by using the delimiter as "multiple space"
		String[] sourcePdfWords = sourcePdfText.split("\\s+");

		// to get the length of the pdf
		int sourcePdfWordsLength = sourcePdfWords.length;

		System.out.println("count of pdf words : " + sourcePdfWordsLength);

		// create the htmlReporter object
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");

		// create ExtentReports and attach reporter(s)
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// creates a toggle for the given test, add all log events under it
		ExtentTest extentTestObj = extent.createTest("verifying the PDF document and UI application",
				"verifying the PDF document and Mastery application data");

		for (int i = 0; i < sourcePdfWordsLength; i++) {
			if (sourcePdfWords[i].equals("Route") && sourcePdfWords[i + 1].equals("#")) {
				if (sourcePdfWords[i + 2].equals(RouteNum)) {
					extentTestObj.pass("Route number - " + sourcePdfWords[i + 2]
							+ " present in the document is same as in Mastery application's data");
					Assert.assertTrue("Route number present in the document is same as in Mastery application's data",
							true);
				} else {
					// Assert.fail();
					extentTestObj.fail("Failed to match Route number - " + sourcePdfWords[i + 2]
							+ " present in the document and in Mastery application's data - "+RouteNum);
				}
			}
			if (sourcePdfWords[i].equals("Mode:")) {
				if (sourcePdfWords[i + 1].equals(Mode)) {
					extentTestObj.pass("Mode - " + sourcePdfWords[i + 1]
							+ " present in the document is same as in Mastery application's data");
				} else {
					extentTestObj.fail("Failed to match Mode - " + sourcePdfWords[i + 1]
							+ " present in the document and in mastery application's data -"+Mode);
					//Assert.fail();
				}
			}

			if (sourcePdfWords[i].equals("Size:")) {
				if (sourcePdfWords[i + 1].equals(Size)) {
					extentTestObj.pass("Size - " + sourcePdfWords[i + 1]
							+ " present in the document is same as in Mastery application's data");
				} else {
					extentTestObj.fail("Failed to match Size - " + sourcePdfWords[i + 1]
							+ " present in the document and in Mastery application's data - "+Size);
					//Assert.fail();
				}
			}
			if (sourcePdfWords[i].equals("Route") && sourcePdfWords[i + 1].equals("Type:")) {
				if (sourcePdfWords[i + 2].equals(RouteType)) {
					extentTestObj.pass("Route Type - " + sourcePdfWords[i + 2]
							+ " present in the document is same as in Mastery application's data");
				} else {
					extentTestObj.fail("Failed to match the Route Type - " + sourcePdfWords[i + 2]
							+ " present in the document and in Mastery application's data - "+RouteType);
					//Assert.fail();
				}
			}
			if (sourcePdfWords[i].equals("Equipment:")) {

					int interator = i + 1;
					String txtLastEquipWord = "Expected";
					int h = 0;
					String oldEquip = new String();
					String newEquip = null;
					String arrEquip[] = new String[10];

					for (int a = interator; a < sourcePdfWordsLength; a++) {
						if (sourcePdfWords[a].equals(txtLastEquipWord)) {
							break;
						} else {
							arrEquip[h] = sourcePdfWords[a];
							oldEquip = oldEquip.concat(arrEquip[h]);
							h++;
						}
					}
					//System.out.println(oldEquip);
					newEquip = oldEquip.replaceAll(" ", "");
					//System.out.println("Pdf" + newEquip);
					if (newEquip.equals(newEquipment)) {
						extentTestObj.pass("Equipment - " + newEquip
								+ " present in the document is same as in Mastery application's data");
					} else {
						extentTestObj.fail("Failed to match the Equipment - " + newEquip
								+ "  present in the document and in the mastery application data - "+newEquipment);
						//Assert.fail();
					}
			}
			if (sourcePdfWords[i].equals("Stops:")) {
				if (sourcePdfWords[i + 1].equals(Stops)) {
					extentTestObj.pass("Stops -" + sourcePdfWords[i + 1]
							+ " present in the document is same as in Mastery application's data");
				} else {
					extentTestObj.fail("Failed to match the Stops - " + sourcePdfWords[i + 1]
							+ " present in the document and in the mastery application data - "+Stops);
					//Assert.fail();
				}
			}
		}
		System.out.println("Driver closed");
		// writes the report into the file
		extent.flush();
	}

}
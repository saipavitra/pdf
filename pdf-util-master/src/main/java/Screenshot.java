import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentTest;

public class Screenshot extends PDFContentsCheck {
	public static ExtentTest extentTest;
	/**
	 * To take screenshot
	 */
	public static void takeScreenshot() {
		try {
			String imgData = "<img src='data:image/png;base64, " + getBase64Image()
					+ "' alt='Screenshot' width='1000' height ='350'/>";
			extentTest.info(imgData);
		} catch (Throwable e) {
			extentTest.fail("Fail to take the screenshot");
		}
	}

	public static String getBase64Image() {
		return ((TakesScreenshot) PDFContentsCheck.driver).getScreenshotAs(OutputType.BASE64);

	}

}

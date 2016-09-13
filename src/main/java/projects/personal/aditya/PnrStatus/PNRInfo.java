package projects.personal.aditya.PnrStatus;

import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


import projects.personal.aditya.emailbuilder.EmailBuilder;

public class PNRInfo extends EmailBuilder {
	
	private String pnrNo;
	private URI url;
	private String emailBody;
	
	private String getPnrNo()
	{
		return this.pnrNo;
	}
	
	private WebDriver createDriver() {
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
	
	private void setPnrNo(String pnr)
	{
		this.pnrNo = pnr;
	}
	
	
	private void setURL(String url)
	{
		try {
			this.url = new URI(url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getURL()
	{
		return this.url.toString();
	}
	
	private void createHtmlForEmail()
	{
		this.setFileNameOfHTML("email");
		this.setTitleOfHtml("PNRStatus");
		this.setBodyContent();
		this.createEntireHtml();
	}

	@Override
	public String setBodyContent() {
		
		return "<h3>For Bhopal to Ujjain </h3><br/><p>"+this.emailBody+"</p>";
	}
	
	private void setSomeTimeOut(long timeToSleep) throws InterruptedException
	{
		Thread.sleep(timeToSleep);
	}
	
	private void getPnrStatus()
	{
		WebDriver driver =createDriver();
		driver.get(getURL());
		try {
			setSomeTimeOut(20*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement pnrText = driver.findElement(By.id("element"));
		pnrText.sendKeys(getPnrNo());
		WebElement submitElement = driver.findElement(By.name("submit"));
		submitElement.click();
		try{
			setSomeTimeOut(20*1000);
		}catch(InterruptedException e)
		{}
		WebElement tableElement = driver.findElement(By.id("center_table"));
		this.emailBody=tableElement.getText();
		if(driver!=null)
		{
			driver.quit();
		}
	}
	
	
	public static void main(String args[])
	{
		PNRInfo info = new PNRInfo();
		info.setURL("http://www.indianrail.gov.in/pnr_Enq.html");
		info.setPnrNo("6254560624");
		info.getPnrStatus();
		info.createHtmlForEmail();
	}
}

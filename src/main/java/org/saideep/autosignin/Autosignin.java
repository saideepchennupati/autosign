package org.saideep.autosignin;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

import java.net.UnknownHostException;



public class Autosignin {
	static int a = 1;
	// checks whether it is reachable or not
	public void checkconnection() {
		int timeout = 2000;
		InetAddress[] addresses = null ;
		         try {
	                 addresses = InetAddress.getAllByName("mail.google.com");
		               } catch (UnknownHostException e1) {
                           System.out.printf(" Network issue kindly check");			
			               System.exit(0);
	 	                                     }
	  for (InetAddress address : addresses) {
		         try {
				     if (address.isReachable(timeout)) {
				        System.out.printf("%s is reachable%n", address);
				            a= 1;
				         }
				     else {
				         System.out.printf("%s could not be contacted%n", address);
				         a = 0;
				          }
			             } catch (IOException e) {
				                 System.out.printf(" Network issue kindly check");				
				                  System.exit(0);
			                                      }
		                } 
	                }

	public static void main(String[] args) throws InterruptedException, IOException  {
		 String FILENAME = "C:\\\\eclipse-workspace\\\\autosignin\\\\src\\\\test\\\\config.txt";        // path to the config file
		BufferedReader br = null;
		FileReader fr = null;
		String sCurrentLine;
		String [] arrOfStr ;
		String em = null;
		String pass = null;
	    String ver;
		String per = new String("Sign in");
		String currenturl ;
		String tempurl="dummyurl";
		try{
			fr = new FileReader(FILENAME);
			br =  new BufferedReader(fr);
			sCurrentLine = br.readLine();                                                             // reading from first line
			arrOfStr = sCurrentLine.split(":", 2);
			em = arrOfStr[1];                                                                         //getting email id from config file
			sCurrentLine = br.readLine();
			arrOfStr = sCurrentLine.split(":", 2);
			pass = arrOfStr[1];                                                                       //getting password from config file
			br.close();
			fr.close();
			
		}
		catch (IOException e) {

			e.printStackTrace();

		}
		WebDriver gm = new ChromeDriver();                                                          // initialising web driver
		
		 Autosignin check = new Autosignin();
		 check.checkconnection();
		 if(a==1) {
			 gm.get("https://mail.google.com");
				String sam = gm.getTitle();
				System.out.println(sam);
		  // boolean status = elem.isDisplayed();
		  // System.out.println(elem);
			gm.findElement(By.xpath(".//*[@id='identifierId']")).sendKeys(Keys.CONTROL + "a");
			gm.findElement(By.xpath(".//*[@id='identifierId']")).sendKeys(Keys.DELETE);              //for clearing the keys in the search box if exists
			gm.findElement(By.xpath(".//*[@id='identifierId']")).sendKeys(em);                       //writing email id on to the box
			gm.findElement(By.xpath(".//*[@id='identifierNext']/content/span")).click();             // clicking the button
            Thread.sleep(2000);                                                                      //allowing the next page to load 
			ver = gm.findElement(By.xpath(".//h1[@id='headingText']")).getText();                    //getting heading text from Gmail (ie signin , welcome etc)
			currenturl = gm.getCurrentUrl();                                                         // getting the current url address
			                                                                                         //System.out.println(ver); //used for testing
      if(ver.equals(per)) {
    	  System.out.println("Entered email doesnot exist");
			
        }
      else {
    	    gm.findElement(By.xpath(".//*[@id='password']/div[1]/div/div[1]/input")).sendKeys(Keys.CONTROL + "a");
			gm.findElement(By.xpath(".//*[@id='password']/div[1]/div/div[1]/input")).sendKeys(Keys.DELETE);          //for clearing the keys in the search box if exists				     
		    gm.findElement(By.xpath(".//*[@id='password']/div[1]/div/div[1]/input")).sendKeys(pass);                 // sending keys to password bax
		    gm.findElement(By.xpath(".//*[@id='passwordNext']/content/span")).click();                               // clicking the next button
		    Thread.sleep(2000);
		    tempurl = gm.getCurrentUrl();                                                                            //getting the tempurl because if the password is incorrect it stays in the same url else it goes to next step
			 
			
			 if( currenturl.equals(tempurl)) {
			     System.out.println("enterd wrong password in config file");
			   			    }
			else {
				check.checkconnection();
				if(a==1) {
				System.out.println("successfully logged in");
				           }
				 else {
					 System.out.println("network issue");
				 }
			 }
			 }
		     }
				
		 else {
			 System.out.println("network issue");
		    }
			
      
         }	    
	}



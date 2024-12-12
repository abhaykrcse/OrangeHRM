import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.swing.*;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class OrangeHRM {
    WebDriver driver; // Declare WebDriver at class level

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    }

    @Test(priority = 2, enabled = false)
    public void loginTest() throws InterruptedException {
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String pageTitle = driver.getTitle();

//        if (pageTitle.equals("OrangeHRM")) {
//            System.out.println("Login successful!");
//        }else{
//            System.out.println("Login failed!");
//        }
        logout();
        Assert.assertEquals(pageTitle, "OrangeHRM", "Page title mismatch!");
    }

    @Test(priority = 1, enabled = false)
    public void doLoginWithInvalidCredential() throws InterruptedException {
        driver.findElement(By.name("username")).sendKeys("Admin");

        driver.findElement(By.name("password")).sendKeys("a123"); //wrong password
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String message_expected = "Invalid credentials";
        String message_actual = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();

        Assert.assertEquals(message_expected, message_actual);
        Thread.sleep(1500);
    }



    @Test(priority = 3, enabled = false)
    public void addEmployee() throws InterruptedException, IOException {
        LogIn();
        driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[normalize-space()='Add Employee']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Abhay");
        driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("Kumar");

        //////////////////////Add Image////////////////////////////////////////////

        //add image
        driver.findElement(By.xpath("//button[@class='oxd-icon-button oxd-icon-button--solid-main employee-image-action']")).click();


        Thread.sleep(5000);//pause of 5 seconds

        Runtime.getRuntime().exec("C://intelliJ Project//OrangeHRM//addemployeeProfilepicUpload.exe");


        Thread.sleep(5000);


        /////////////////////////////////////////////////////////////////////////////

//        WebElement empIdField = driver.findElement(By.xpath("//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']"));
//        empIdField.click();
//        empIdField.sendKeys(Keys.CONTROL + "a");
//        empIdField.sendKeys(Keys.DELETE);
//        empIdField.sendKeys("0312");
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();

        String confirmationMessage = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();

        if (confirmationMessage.contains("Personal Details")) {
            System.out.println("Employee added successfully");
        } else {
            System.out.println("Failed to add employee!");
        }
        logout();
        Assert.assertEquals("Personal Details", confirmationMessage);
    }


    @Test(priority = 4, enabled = false)
    public void searchEmployeeByName() throws InterruptedException {
        LogIn();
        driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
        driver.findElements(By.tagName("input")).get(1).sendKeys("Abhay");

        Thread.sleep(500);
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

        List<WebElement> element = driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));

        String expected_message = "Record Found";
        String message_actual = element.get(0).getText();
        System.out.println((message_actual));
//        for (int i=0; i<element.size(); i++)
//        {
//            System.out.println("At index "+ i + "text is :"+ element.get(i).getText());
//        }
        logout();
        Assert.assertTrue(message_actual.contains(expected_message));
    }

    @Test(priority = 5, enabled = false)
    public void searchEmployeeById() throws InterruptedException {
        String empId = "0372";
        String message_actual = "";
        LogIn();

        driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
        driver.findElements(By.tagName("input")).get(2).sendKeys(empId);
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
        Thread.sleep(2000);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0," + 500 + ")");
        Thread.sleep(2000);

        List<WebElement> rows = driver.findElements(By.xpath("(//div[@role='row'])"));
        if (rows.size() > 1) {
            message_actual = driver.findElement(By.xpath("((//div[@role='row'])[2]/div[@role='cell'])[2]")).getText();
        }

        logout();
        Assert.assertEquals(empId, message_actual);
    }

    @Test(priority=6, enabled = true)
    public void fileUpload() throws IOException, InterruptedException
    {
        LogIn();

        //find PIM Menu and click on PIM Menu
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        //click on configuration button
        driver.findElement(By.xpath("//span[@class='oxd-topbar-body-nav-tab-item']")).click();


        //click on Data import
        driver.findElement(By.partialLinkText("Data ")).click();

        //click on browse button
        driver.findElement(By.xpath("//div[@class='oxd-file-button']")).click();


        Thread.sleep(5000);//pause of 5 seconds

        Runtime.getRuntime().exec("C://intelliJ Project//OrangeHRM//FileUploadOrangeHRM.exe");

        Thread.sleep(5000);

        //click on upload button
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

        logout();


    }


    public void LogIn() {
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public void logout() throws InterruptedException {
        // Open user dropdown menu
        driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
//        driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();

        // Find logout link and validate
        List<WebElement> elementList = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));

        if (elementList.isEmpty()) {
            System.out.println("Logout option not found!");
            return;
        }

        for (int i = 0; i < elementList.size(); i++) {
            Thread.sleep(2000); // Add delay for demonstration (avoid in production)
            System.out.println(i + ": " + elementList.get(i).getText());
        }

        // Click the logout option (assuming the first match)
        elementList.get(3).click();
    }


    @Test(priority=7, enabled=false)
    public void deleteEmployee() throws InterruptedException
    {
        LogIn();

        //find PIM Menu and click on PIM Menu
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        //Select Employee List
        driver.findElement(By.xpath("//a[text()='Employee List']")).click();

        //enter employee name
        driver.findElements(By.tagName("input")).get(1).sendKeys("Odis");

        //driver.findElement(By.tagName("input")).get(1).sendKeys("Nesta");


        //Click the search button.
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();


        Thread.sleep(3000);
        ///////////////////Delete/////////////////////////

        //click on delete icon of the record
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();


        //click on yes, delete messaage button
        driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']")).click();

        //check for message "No Record Found"
        String msg = driver.findElement(By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]")).getText();

        Assert.assertEquals(msg, "No Records Found");

        Thread.sleep(5000);
        logout();

    }

    @Test(priority=8, enabled=false)
    public void ListEmployees() throws InterruptedException
    {
        LogIn();
        //find PIM Menu and click on PIM Menu
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        //Select Employee List
        driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
        Thread.sleep(3000);

        //find total links
        List<WebElement> totalLinksElements = driver.findElements(By.xpath("//ul[@class='oxd-pagination__ul']/li"));

        int totalLinks = totalLinksElements.size();

        for (int i=0; i<totalLinks; i++ )//0,1,2,3,
        {

            try
            {
                String currentLinkText = totalLinksElements.get(i).getText();


                int page = Integer.parseInt(currentLinkText);
                System.out.println("Page: " + page);

                totalLinksElements.get(i).click();

                Thread.sleep(2000);

                List <WebElement> emp_list = driver.findElements(By.xpath("//div[@class='oxd-table-card']/div /div[4]"));

                for(int j=0; j<emp_list.size();j++)
                {
                    //print last name of each row
                    String lastName = emp_list.get(j).getText();
                    System.out.println(lastName);
                }
            }
            catch(Exception e)
            {
                System.out.println("Not a number.");
            }


        }

        Thread.sleep(5000);
        logout();
    }




    @Test(priority=9, enabled=false)
    public void applyLeave() throws InterruptedException
    {
        //find username and enter username "Admin"
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");

        //find password and enter password admin123
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");

        //login button click
        driver.findElement(By.xpath("//button[@type='submit']")).submit();


        //click on leave menu
        driver.findElement(By.linkText("Leave")).click();

        //click on Apply menu
        driver.findElement(By.linkText("Apply")).click();

        //click on leave type drop down
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")).click();

        //select CAN-FMLA option from leave type dropdown
        driver.findElement(By.xpath("//*[contains(text(),'CAN')]")).click();

        //enter from date
        driver.findElement(By.xpath("//div[@class='oxd-date-input']/input")).sendKeys("2024-08-04");


        //enter comment
        driver.findElement(By.tagName("textarea")).sendKeys("This is my personal leave");
        Thread.sleep(3000);


        //click on Apply button
        driver.findElement(By.xpath("//button[@type='submit']")).click();


        Thread.sleep(5000);
        logout();

    }


    @AfterTest
    public void tearDown() throws InterruptedException {
//        Thread.sleep(1000);
//        logout(); // Perform logout before quitting
        Thread.sleep(1000); // Wait for 5 seconds before quitting

        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}

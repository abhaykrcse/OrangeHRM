This document provides a set of automated test cases for the OrangeHRM application, accessible at: [OrangeHRM Login](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login). The test cases cover essential functionality such as logging in with valid and invalid credentials, adding new employees, and searching for employees by name and ID.

### Test Cases Overview:
1. **Login with Valid Credentials**: Ensures successful login and verifies the dashboard page title, followed by user logout.
2. **Invalid Login**: Verifies that appropriate error messages are displayed when login is attempted with invalid credentials.
3. **Add Employee**: Automates adding a new employee, including uploading a profile picture using AutoIt, and verifies the addition by checking the employee list for personal details.
4. **Search Employee by Name**: Ensures that employees can be searched by name and verifies the search results.
5. **Search Employee by ID**: Ensures that employees can be searched by ID and verifies the search results.

### File Upload Using AutoIt:
The document also includes instructions for using AutoIt to automate file uploads. It involves setting up AutoIt, writing a script to handle file uploads, compiling the script into an executable, and integrating it into the Selenium code.

#### Key Steps:
1. **Set Focus on the File Upload Window**: Using `ControlFocus`.
2. **Set the File Path**: Using `ControlSetText`.
3. **Click the Open Button**: Using `ControlClick`.

By following these guidelines and utilizing the provided resources, users can effectively automate and verify key functionalities of the OrangeHRM application.

# MyAutomationE2EProject
### Table of Contents
- [Description](#Description)
- [Technology Used](#TechnologyUsed)
- [How to Use?](#HowtoUse)
- [Author's Info](#AuthorDetails)

#### Description
This is mini project which compares the temperature value of the UI(NDTV) and API(Open Weather), compares them on variance logic, and return True or False. If the value of calculated variance is less than 3, then the returned value should be true, otherwise false.
#### Technologies Used
- Core Java
- Selenium WebDriver
- Rest Assured
- TestNG, Maven, Extent Reports
#### How To Use?
##### Installation
Make sure that your system has JDK, Maven, and Eclipse installed with respective path configured.
##### Import the Project
Download the whole project and import it into Eclipse's workspace to work on it.
##### Change City if required
Go to "/CodingAssignmentProject/resources/testData/testData.properties" and change the name of the city as per your convenience (Make sure the City is present in the list with the correct spelling)
##### Ways to Execute the Script
1. The test class' path is in the testng.xml file, so directly right click on testng.xml file (in Eclipse) and select Run as "TestNG Suite" option
2. Right Click on Pom.xml file and select "Run as Maven test" option
3. Provide the project path(where pom.xml stored) in command prompt and provide "mvn test" command to run it from command prompt directly

#### Author's Info
- Linkedin - [@AbhishekJain](https://www.linkedin.com/in/abhishek-jain-849a0656/)






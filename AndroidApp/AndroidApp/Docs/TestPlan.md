
# Test Plan

**Author**: Team 48

## 1 Testing Strategy

### 1.1 Overall strategy

#### Assumptions
Automated test is not compatible with API 28.

#### Unit Testing
Unit tests will run automatically whenever we compile the code.
Build will be rejected if there is some unit test failure.

#### Regression Testing
We will have a suite of regression tests documenting:
 - Core functionality
 - Features which have broken in the past
 - Fragile Area of code
 With every build, we will run these regression tests using automated tests or manually if
 we couldn't automate or automated test failed. We will update regression test suite as per
 new features and changes in the existing features.


### 1.2 Test Selection

#### Unit Testing:
We will use Test Driven Development
Automated White Box Testing using TestNG

#### System Testing:
- Manual Test Cases (Black Box Testing )
- Automated UI Test Cases (Black Box Testing)

#### Regression Testing:
- Manual Test Cases (Black Box Testing )
- Automated UI Test Cases (Black Box Testing)

<b>Note:</b> We will also do Code Review (Inspection) to increase the quality

### 1.3 Adequacy Criterion

- Unit Tests, we will target to achieve 80% code coverage


### 1.4 Bug Tracking

We will track bugs and enhancement requests using excel format.
- All bugs will be given an id and there will be description. if it is a bug it will be associated with requirements / use-case
If it is enhancement, it will be given new requirement id or use-case id

### 1.5 Technology

- For Unit Testing, we will be using TestNG
- For Automated UI Testing, we will be using Espresso or an other Android Automated Testing technology.

## 2 Test Cases
Steps will be added later as they can change slightly based on the UI changes

|R Id| Test Case Id     | Purpose   | Steps |  Result | Status | Notes |
|:---|:------ |:------------------------|:------ |:---------------|:--------|:-------|
|1| 1| Registration: User is able to register with unique username |Run ableToRegisterSuccessfully() method in UITest class（manual test)  <br>Step1: Input is username, major and email; Output is the username of the registered user.  <br>Step2: The input username should be the same as the registered user. |Passed |
|1| 2| Registration: User is not able to register with same username again |Run userIsNotAbleToRegisterWithSameUserName() in UITest class.  <br>Step1: Randomly choose a user in the testing system, and stored its username as a local variable.  <br>Step2:Use registerUser() method to register with the user name, major and email input.  <br>Step3: Check if the registered user is same as the user we selected  <br>Step4: Go back to the previous screen, use the perform(click()) method to click on the register button and then register with the same username, major and email input.  Check if the screen shows error message as we designed in the app, which is user + "is already registered!"|Passed |
|1| 3| Registration: sername, email, major and seniority level are required |<br>Step1:Click register on the first page of the app.  <br>Step2: Check if username, email, major and seniority level are required on the second screen by filling in the form.  <br>Step3: Run step 1 and 2 for multiple times to check if "username, email, major and seniority levels" are required.    |Passed |
|1| 4| Login: User is able to login     |Run ableToLoginWithValidUserAndStudentDashboard() method in UITest class  <br>Step1:Select a random username from the name list we created, then registered the user using registerUser() method.  <br>Step2:Use loginUser(username) method to log in to the system, by checking if all the UI buttons matches as designed, we can conclude that the user is able to login.|Passed |
|1| 5| Login: User info matches with the information provided at time of registration.     |<br>Step 1: Register for an account and log in  <br>Step 2: Click the view quiz score button, and choose on a quiz and click check score button.  <br>Step3: student name will be shown on the screen so we can check if the user info matches with the registration info.    |Passed |
|3| 6| Add Quiz: User is able to add a quiz with unique name     |Run studentIsAbleToAddQuizFromDashboard() method in UITest class  <br>Step1: Register in the system with a random user name, login to the system.  <br>Step2: Click on the "add quiz" button and add a guiz with a random selected quizname and description.  If it run successfully, it means that we can add a quiz with unique name.|Passed |
|3| 7| Add Quiz: User is not able to add a quiz with same name again    |Run notAbleToAddQuizWithSameName() method in UITest class  <br>Step1: Get a random Username from the namelist, register in the system with the "user name, a major and email".  <br>Step2: log into the system, go to add quiz screen, add a quiz to the system with a quizname.  <br>Step3: use the same username to add a quiz again, the screen shows error message as "There is another quiz with the same name"  If it shows this error message, it means user is not able to add a quiz with same name again. |Passed |
|3| 8| Add Quiz: There should be minimum 1 question in a quiz    |Step 1: Register for an account, log in to the system, then click on the "practice a quiz" button.  <br>Step2: Select a quiz and click on the button to start the quiz.  <br>Step3: When we go through the quiz, we can see how many questions we have answered.  <br>Step4:We should do this multiple times so that we are sure there is at least one question for each quiz.|Passed |
|3| 9| Add Quiz: User should not be able to add more than 10 questions in a quiz   |Step1: Register for an account, log in to the system, then click on the "practice a quiz" button.  <br>Step2: Select a quiz and click on the button to start the quiz.  <br>Step3: When we go through the quiz, we can see how many questions we have answered.  <br>Step4:We have done this multiple times so that we are sure there is at most ten questions for each quiz. |Passed |
|4| 10| Remove Quiz: User is able to remove the quiz (s)he created      |Step1: Run userIsAbleToRemoveHisQuizzesFromDashboard() method in UITest class  <br>Step1: Use the registerUser()method to register for a random selected user from our name list. <br>Step2:Use loginUser(registerUser) method to login to the system with the current user's username.  <br>Step3:From goToAddQuizScreenFromStudentDashboard() method, we can go to add quiz screen, use the addQuiz(quizName) method to add a quiz, then go to remove quiz screen from Dashboard, then select the quiz and remove.  <br>Step4:Check on the screen and check if it shows with designed text " You don't have quizzes to remove" as we had removed the quiz. If it works, then the user is able to remove his quiz. |Passed |
|4| 11| Remove Quiz: User is not able to remove the quiz (s)he didn't create      |Step1:Run userIsNotAbleToRemoveQuizzesNotCreatedByHim() method in UITest class.  <br>Step2:Select a user to register into the system and then add a quiz to the system.  <br>Step3:After logging out, we selected another user to register for an account and ask the user to remove a quiz.  <br>Step4:The screen will show "You don't have quizzes to remove", so that user is not able to remove quizzes not created by him. |Passed |
|4| 12| Remove Quiz: If we remove quiz, it removes the quiz statistics as well      |Step1: Register for an account and log into the system.  <br>Step2: Click on "remove a quiz" on the dashboard, and select a quiz to remove.  <br>Step3: Click on either "practice a quiz" or "check quiz score" button on the dashboard, and then check if the removed quiz is still in the quiz list.      |Passed |
|5| 13| Practice Quiz: User can not practice the quiz created by her      |Run userCanNotPracticeQuizzesCreatedByHim() method in UITest class  <br>Step1: Get a random user name from the name list.  <br>Step2: register with the username and login to the system  <br>Step3: Add a quiz with a quizname.  <br>Step4: Practice quiz and check if the previous quizname is in the spinner.  The result is no,  which proves the user can not practice the quiz created by her.|Passed |
|5| 14| Practice Quiz: User can practice any quiz not created by him      |Run userCanPracticeQuizzesNotCreatedByHim() in UITest class  <br>Step1: get a random user name from the name list.  <br>Step2: register with the username and login to the system.  <br>Step3: Add a quiz with a quizname.  <br>Step4: Practice quiz and check if the previous quizname is in the spinner.  The result is not, it proves that user can not practice quizzes created by him.|Passed |
|6| 15| Quiz Screen: One question appear only once in a quiz      |Step 1: Register for an account, log in to the system, then click on the "practice a quiz" button.  <br>Step2: Select a quiz and click on the button to start the quiz.  <br>Step3: When we go through the quiz, we can check if there are duplicated questions. <br>Step4:We have done this multiple times so that we are sure one question appear only once in a quiz.     |Passed |
|6| 16| Quiz Screen: Application shows correct message if answer is correct.      |Step1: Register with a username and login to the system  <br>Step2: Click practice quiz and start quiz.  <br>Step3: Go through the quiz and click on the answers, it will show correct message if you choose the right answer.      |Passed |
|6| 17| Quiz Screen: Application shows incorrect message if answer is not correct|Step1: Register with a username and login to the system.  <br>Step2: Click practice quiz and start quiz.  Step3: go through the quiz and click on the answers, it will show incorrect message if you choose the wrong answer.        |Passed |
|6| 18| Quiz Screen: User gets 100% score if he answers all question correctly      |Step1: Register with a username and login to the system.  <br>Step2: Click practice quiz and start quiz.  Step3: Go through the quiz and click on the right answer, it will show 100% after you submit the quiz.      |Passed |
|7| 19| Dashboard: First Screen: All quizzes include quizzes played by student      |Step1: Register with a username and login to the system.  <br>Step2: Click "practice quiz button" and start quiz  <br>Step3: Go through the quiz and submit.  <br>Step4:Return to the dashboard and click view score.  <br>Step5: Check if the quiz list includes the quiz the user practiced.  If it is in the list, all quizzes include quizzes played by student   |Passed |
|7| 20| Dashboard: First Screen: All quizzes included quizzes not played but created by student      |Step1: Register with a username and login to the system.  <br>Step2: Click "add quiz button" and add a quiz.  <br>Step3:Return to the dashboard and click view score  <br>Step4: Check if the quiz list includes the quiz the user just added.  If it is in the list, all quizzes include quizzes not played but created by student.      |Passed |
|7| 21| Dashboard: First Screen: Quizzes played by student appear first before not played      |Step1: Register with a username and login to the system.  <br>Step2: Click "practice quiz button" and start quiz.  <br>Step3: Go through the quiz and submit.  <br>Step4:Return to the dashboard and click view score.  <br>Step5: Check if the quiz just practiced by the student appear first before not played.|Passed |
|7| 22| Dashboard: Second Screen: For played quiz, we show the first score of Student and when it was achieved     |Step1: Register with a username and login to the system.  <br>Step2: Click "practice quiz button" and start quiz.  <br>Step3: Go through the quiz and submit, remember the score and the time you practiced the test.  <br>Step4: Click "practice quiz button" and take the quiz again, remember the score and time when you finished the test.  <br>Step5:return to the dashboard and click view score for this quiz.  <br>Step6: The first score of the student practiced the test showd up.   |Passed |
|7| 23| Dashboard: Second Screen: For played quiz, we show the highest score of student and when it was achieved      |Step1: Register with a username and login to the system.  <br>Step2: Click "practice quiz button" and start quiz.  <br>Step3: Go through the quiz and submit, remember the score and time you practiced the test.  <br>Step4: Click "practice quiz button" and take the quiz again, remember the score and time when you finished the test.  <br>Step5:return to the dashboard and click view score for this quiz.  <br>Step6: The highest score of the student practiced the test showd up.   |Passed |
|7| 24| Dashboard: Second Screen: For played quiz, we show the names of the first 3 students sorted alphabetically to score 100% on quiz      |Step1: Register with a username and login to the system.  <br>Step2: Click "practice quiz button" and select a quiz to start.  <br>Step3: Go through the quiz and submit, practice several times until you get full score. remember the score and time and also the username.  <br>Step4: Register for another two accounts and repeat Step1 through Step3.  <br>Step5: return to the dashboard and click view score for the same quiz, it will show the first 3 students sorted alphabetically to score 100% one the quiz.  |Passed |
|7| 25| Dashboard: Second Screen: For not-played quiz, we show only the names of the first 3 students sorted alphabetically to score 100% on quiz      |Step1: Register with a username and login to the system.  <br>Step2: Click "practice quiz button" and select a quiz to start.  <br>Step3: Go through the quiz and submit, practice several times until you get full score. remember the score and time and also the username.  <br>Step4: Register for another two accounts and repeat Step1 through Step3.  <br>Step5: Register for a fourth account and log in, return to the dashboard and click view score for the same quiz, it will show the first 3 students sorted alphabetically to score 100% one the quiz, and will not show other information.    |Passed |
|7| 26| Dashboard: Second Screen: For not-played quiz, There is no first score or best score for the Student      |Step1: Register with a username and login to the system.  <br>Step2: Click view score and select a quiz to show score.  <br>Step3: There is no first score or best score for the student on the second screen as a result. Step4: Go back to the previous screen, use the perform(click()) method to click on the register button and then register with the same username, major and email input.  Check if the screen shows error message as we designed in the app, which is user + "is already registered!"|Passed |

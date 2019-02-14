# Idividual Designs

### Design 1

![Design 1](./image/design1.png)

- Cons:
  - It’s not possible to find the correct definition for the words as words and definitions are in two different list instead of having a list of word linked to a proper definition and an incorrect definition list. So we think word and definition should be combined to a single class, which is shown in our final design.
  - NewStudent and RegisteredStudent shouldn’t be sub classes of student.

- Pros:
  - Other than the cons mentioned above the diagram is the best base we found among the four designs to improve. It is concise and well structured. Overall, we used Student, Quiz, Session and the combined class “Word” class in our final design, which is derived from this design.


### Design 2

![Design 2](./image/design2.png)

- Cons:
  - We found that having two sub classes for quizzes wasn’t the approach we wanted to follow for this particular design. It does not make the model lighter or easier.
  - The diagram need a QuizScore, and practice Session class and some other subclasses to make the model lighter and more efficient to use.

- Pros:
  - It give the team a different perspective on how to approach the user and quiz class and we used this ideas for the final design.
  - Quiz and User class were taken in final design with slight modifications


### Design 3

![Design 3](./image/design3.png)

- Cons:
  - We can not track all the answers given in a particular practice session.
  - System class felt un-necessary. because rest all classes is the System collectively

- Pros:
  - This diagram is concise and has the best use of UML relations which we used in the final design.
  - The overall structure of the design is put together very well.

### Design 4

![Design 4](./image/design4.png)

- Cons:
  - It has some redundant classes that could be simplified and meet the requirements.
  - The use of the UML relations could be improved as they not clearly show the intent.
- Pros:
  - The classes in this design are very detailed and after an explanation of their intent we used them in the final implementation.


# Team Design
![Design Team](./image/design-team.png)

### Commonalities

Quiz and Student/User class were presented in all 4 designs and the final design in a very similar way. Under the “Student” class, both the final design and the individual ones have similar variables to identify the class, like “username”, “major”, “email”, “seniority levels”. We also have similar methods like “log in” , “create an account”, “add a quiz”, “practice a quiz” and “remove a quiz”. Under the “Quiz” class, there are similar variables like “Quizname”, “createby”, “quizDescription”, “word”. “Quizscore”. These are the commonalities for this design and the individual ones. 

### Differences

The main difference of this design and the individual ones is the final design is more organized and it used all the pros and avoids all the cons from each design. It is a more structured design which will make the model more efficient. The four individual ones all have some shortcomings either in terms of structure or function(fail to meet certain requirements). The problems for each individual design is already discussed above on the pros and cons section, but the final design meet all the requirements. 

### Justification

We have six classes in our final design. “Student”, “Quiz” and “Session” are the three main classes that will meet most of design requirements. “QuizScore” is a subclass of the “Quiz”. It can meet “requirements 7” that will show the quiz score statistics for a student S. “Word” is a subclass of the “Quiz” class, which has the text and a definition of each word question. “Answer” class is composed of “Word” class, which contains the correct word and the provided answer.”Session” has a variable of type “Answer” class which will help the session class to calculate score for each quiz session. 

For the three main classes, a student can login and register under the “Student” class, and after log in , a student can “add a quiz”, “remove a quiz” or “practice a quiz”. When the student adds a quiz, a quiz object will be created and the student has to fill in the quiz attributes quizName, createdBy, quizDescription, incorrectDefinitions and words.

A student can call the “Quiz” class to practice a quiz, and Quiz class will call the Session class to create a session for the quiz. After the session is finished, “quizScore” can be calculated with the help of the “Answer” and “Word” class. Then the quizScore is stored under the Quiz class. At the same time, the score, dateTime, and student name will be stored under the “quizScore” attribute so that the system can check the quiz score statistics for a student. 

We are confident that this design can meet all the requirements and also compute and save information efficiently.

# Summary

In the process of discussing the designs, we first analyzed the pros and cons, then we compared the four designs and agreed on which design had the best structure to build upon and we used this as a base for the final design. 

We studied a lot from each other’s individual designs, and we kept all the best features of each design and constructed a new one. It took about 2 hours to conduct a team session that allowed us to build together the new design and we are quite satisfied with our output. We tried our best to make the design more structured and also that satisfied all the requirements of the assignment. The UML diagram has helped us think about the relationships of each class and the overall functionality of the system. 

### Lessons Learned:
- The team discussion was very helpful to identify the flaws in each design 
- It also helped highlighting good design patterns which ultimately contributed to the final design
- The UML diagram helps better identify major classes and their relationships
- We duplicated efforts sometimes, moving forward we will assign tasks better to avoid this problem
- It will be a better use of our time to review the material created by the other team members before meeting
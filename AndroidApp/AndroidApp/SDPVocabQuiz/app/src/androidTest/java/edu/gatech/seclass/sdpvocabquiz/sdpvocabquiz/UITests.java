package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class UITests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loginAndRegisterButtonsAreAvailableOnMainScreen() {
        ViewInteraction button = onView(withId(R.id.login_button));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(withId(R.id.register_button));
        button2.check(matches(isDisplayed()));
    }

    // test id : 1
    @Test
    public void ableToRegisterSuccessfully() {
        String user = getRandomUserName();
        String registerUser = registerUser(user, "Senior", "s@oms.com");
        Assert.assertEquals(registerUser, user);

    }

    // test id : 2
    @Test
    public void userIsNotAbleToRegisterWithSameUserName() {
        String user = getRandomUserName();
        String registerUser = registerUser(user, "Senior", "s@oms.com");
        Assert.assertEquals(registerUser, user);

        goBackToPreviousScreen();
        ViewInteraction registerButtonOnMainScreen = onView(
                allOf(withId(R.id.register_button), withText("REGISTER"),
                        isDisplayed()));
        registerButtonOnMainScreen.perform(click());

        fillRegistrationScreen(user, "Senior", "s@oms.com");
        // error message validation
        String errorMessage = user +  " is already registered!";
        onView(withText(errorMessage)).check(matches(isDisplayed()));
    }


    // test id : 4
    @Test
    public void ableToLoginWithValidUserAndStudentDashboard() {
        String userName = getRandomUserName();
        registerUser(userName, "Senior", "j@oms.com");

        loginUser(userName);

        ViewInteraction button = onView(withId(R.id.add_quiz));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(withId(R.id.remove_quiz));
        button2.check(matches(isDisplayed()));

        ViewInteraction button6 = onView(withId(R.id.practice_quiz));
        button6.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(withId(R.id.view_score));
        button4.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(withId(R.id.view_score));
        button5.check(matches(isDisplayed()));

    }

    // test id : 6
    @Test
    public void studentIsAbleToAddQuizFromDashboard() {
        String user = getRandomUserName();
        registerUser(user, "Computers", "serg@oms.com");
        loginUser(user);

        goToAddQuizScreenFromStudentDashboard();

        String quizName = getRandomQuizNameForStudent(user);
        addQuiz(quizName, "description");

    }

    // test id : 7
    @Test
    public void notAbleToAddQuizWithSameName() {
        String user = getRandomUserName();
        registerUser(user, "Super Major", "superUser@super.com");
        loginUser(user);

        goToAddQuizScreenFromStudentDashboard();

        String quizName = getRandomQuizNameForStudent(user);
        addQuiz(quizName, "Quiz Description");


        goToAddQuizScreenFromStudentDashboard();

        // try to add the quiz again

        ViewInteraction quizNameInputFieldOnAddQuizScreen = onView(
                allOf(withId(R.id.quiz_name),
                        isDisplayed()));
        quizNameInputFieldOnAddQuizScreen.perform(click());

        quizNameInputFieldOnAddQuizScreen.perform(replaceText(quizName), closeSoftKeyboard());

        ViewInteraction quizDescriptionInputOnAddQuizScreen = onView(
                allOf(withId(R.id.description),
                        isDisplayed()));
        quizDescriptionInputOnAddQuizScreen.perform(replaceText("A Description"), closeSoftKeyboard());


        ViewInteraction nextButtonOnAddQuizScreen = onView(
                allOf(withId(R.id.next_button), withText("NEXT"),
                        isDisplayed()));
        nextButtonOnAddQuizScreen.perform(click());

        String errorMessage = "There is another quiz with the same name!";
        onView(withText(errorMessage)).check(matches(isDisplayed()));
    }



    // test id : 12
    @Test
    public void userIsAbleToRemoveHisQuizzesFromDashboard() {
        String user = getRandomUserName();
        String registerUser = registerUser(user, "CS", "s@oms.com");
        loginUser(registerUser);

        goToAddQuizScreenFromStudentDashboard();
        String quizName = getRandomQuizNameForStudent(user);
        addQuiz(quizName, "Quiz Description");


        goToRemoveQuizScreenFromDashboard();

        ViewInteraction textView4 = onView(withId(R.id.select_quiz_title));
        textView4.check(matches(isDisplayed()));

        ViewInteraction spinner = onView(withId(R.id.get_quiz));
        spinner.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(withId(R.id.remove_button));
        button5.check(matches(isDisplayed()));
        button5.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.no_quiz_text),
                        isDisplayed()));
        textView5.check(matches(withText("You don't have quizzes to remove")));

        // go to student dashboard
        goBackToPreviousScreen();
    }

    // test id : 13
    @Test
    public void userIsNotAbleToRemoveQuizzesNotCreatedByHim() {
        String user1 = getRandomUserName();

        String registerUser = registerUser(user1, "CS", "s@oms.com");
        loginUser(registerUser);

        goToAddQuizScreenFromStudentDashboard();

        String quizName = getRandomQuizNameForStudent(user1);
        addQuiz(quizName, "Quiz Description");

        // logout
        logout();

        // Main screen
        goBackToPreviousScreen();

        String user2 = getRandomUserName();
        registerUser(user2, "CS", "adf@afd.com");
        // login as second user
        loginUser(user2);
        goToRemoveQuizScreenFromDashboard();

        // remove quizzes should be empty
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.no_quiz_text),
                        isDisplayed()));
        textView5.check(matches(withText("You don't have quizzes to remove")));

    }


    // test id : extra
    @Test
    public void noQuizzesAvailableToDeleteForUserWhoDidNotCreateAnyQuizzesYest() {
        String user = getRandomUserName();

        String registerUser = registerUser(user, "CS", "s@oms.com");
        loginUser(registerUser);
        goToRemoveQuizScreenFromDashboard();
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.no_quiz_text),
                        isDisplayed()));
        textView5.check(matches(withText("You don't have quizzes to remove")));
    }

    // test id : 15
    @Test
    public void userCanNotPracticeQuizzesCreatedByHim() {
        String user = getRandomUserName();

        registerUser(user, "major1", "major@oms.com");
        loginUser(user);
        goToAddQuizScreenFromStudentDashboard();
        String quizName = getRandomQuizNameForStudent(user);
        addQuiz(quizName, "description");

        goToPracticeQuizScreenFromStudentDashboard();
        ViewInteraction spinner1 = onView(withId(R.id.get_quiz_practice)).check(matches(quizNotListed(quizName)));

    }

    private static Matcher<View> quizNotListed(final String quizName) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                Spinner quizzesSpinner = (Spinner)item;
                SpinnerAdapter quizArrayAdapter = quizzesSpinner.getAdapter();
                List<String> quizzes = new ArrayList<>();

                int count = quizArrayAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    Quiz quiz = (Quiz) quizzesSpinner.getItemAtPosition(i);
                    quizzes.add(quiz.getName());
                }

                return !quizzes.contains(quizName);

            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    private static Matcher<View> quizListed(final String quizName) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                Spinner quizzesSpinner = (Spinner)item;
                SpinnerAdapter quizArrayAdapter = quizzesSpinner.getAdapter();
                List<String> quizzes = new ArrayList<>();

                int count = quizArrayAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    Quiz quiz = (Quiz) quizzesSpinner.getItemAtPosition(i);
                    quizzes.add(quiz.getName());
                }

                return quizzes.contains(quizName);

            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }



    // test id : 16
    @Test
    public void userCanPracticeQuizzesNotCreatedByHim() {
        String user1 = getRandomUserName();

        String registerUser = registerUser(user1, "CS", "s@oms.com");
        loginUser(registerUser);

        goToAddQuizScreenFromStudentDashboard();

        String quizName = getRandomQuizNameForStudent(user1);
        addQuiz(quizName, "Quiz Description");

        // logout
        logout();

        // Main screen
        goBackToPreviousScreen();

        String user2 = getRandomUserName();
        registerUser(user2, "CS", "adf@afd.com");
        // login as second user
        loginUser(user2);
        goToPracticeQuizScreenFromStudentDashboard();
        onView(withId(R.id.get_quiz_practice)).check(matches(quizListed(quizName)));

    }

    private void goToPracticeQuizScreenFromStudentDashboard() {
        onView(withId(R.id.practice_quiz)).perform(click());
    }


    private void goToRemoveQuizScreenFromDashboard() {
        ViewInteraction removeQuizButtonOnStudentDashboard = onView(withId(R.id.remove_quiz));
        removeQuizButtonOnStudentDashboard.check(matches(isDisplayed()));

        removeQuizButtonOnStudentDashboard.perform(click());
    }


    private void goToAddQuizScreenFromStudentDashboard() {
        ViewInteraction addQuizButtonOnStudentDashboard = onView(
                allOf(withId(R.id.add_quiz),
                        isDisplayed()));
        addQuizButtonOnStudentDashboard.perform(click());
    }

    private void goBackToPreviousScreen() {
        ViewInteraction previousArrow = onView(
                allOf(withContentDescription("Navigate up"),
                        isDisplayed()));
        previousArrow.perform(click());
    }

    private void loginUser(String userName) {
        ViewInteraction userNameInputOnLoginScreen = onView(
                allOf(withId(R.id.login_activity_username_input),
                        isDisplayed()));
        userNameInputOnLoginScreen.perform(replaceText(userName), closeSoftKeyboard());

        userNameInputOnLoginScreen.perform(pressImeActionButton());

        ViewInteraction loginButtonOnLoginScreen = onView(
                allOf(withId(R.id.user_login), withText("LOGIN"),
                        isDisplayed()));
        loginButtonOnLoginScreen.perform(click());
    }

    private void addQuiz(String quizName, String description) {
        ViewInteraction quizNameInputFieldOnAddQuizScreen = onView(
                allOf(withId(R.id.quiz_name),
                        isDisplayed()));
        quizNameInputFieldOnAddQuizScreen.perform(click());

        quizNameInputFieldOnAddQuizScreen.perform(replaceText(quizName), closeSoftKeyboard());

        ViewInteraction quizDescriptionInputOnAddQuizScreen = onView(
                allOf(withId(R.id.description),
                        isDisplayed()));
        quizDescriptionInputOnAddQuizScreen.perform(replaceText(description), closeSoftKeyboard());


        ViewInteraction nextButtonOnAddQuizScreen = onView(
                allOf(withId(R.id.next_button), withText("NEXT"),
                        isDisplayed()));
        nextButtonOnAddQuizScreen.perform(click());


        ViewInteraction wordInputFieldOnAddWordScreen = onView(
                allOf(withId(R.id.input_word),
                        isDisplayed()));
        wordInputFieldOnAddWordScreen.perform(click());
        wordInputFieldOnAddWordScreen.perform(replaceText("Word1"), closeSoftKeyboard());

        ViewInteraction definitionInputFieldOnAddWordScreen = onView(
                allOf(withId(R.id.input_definition),
                        isDisplayed()));
        definitionInputFieldOnAddWordScreen.perform(click());
        definitionInputFieldOnAddWordScreen.perform(replaceText("Definition1"), closeSoftKeyboard());

        ViewInteraction nextButtonOnAddWordScreen = onView(
                allOf(withId(R.id.next_button), withText("NEXT"),
                        isDisplayed()));
        nextButtonOnAddWordScreen.perform(click());

        ViewInteraction def1InputFieldOnAddDefinitionsScreen = onView(
                allOf(withId(R.id.input_def1),
                        isDisplayed()));
        def1InputFieldOnAddDefinitionsScreen.perform(click());
        def1InputFieldOnAddDefinitionsScreen.perform(replaceText("Definition1"), closeSoftKeyboard());

        ViewInteraction def2InputFieldOnAddDefinitionsScreen = onView(
                allOf(withId(R.id.input_def2),
                        isDisplayed()));
        def2InputFieldOnAddDefinitionsScreen.perform(click());
        def2InputFieldOnAddDefinitionsScreen.perform(replaceText("Definition2"), closeSoftKeyboard());

        ViewInteraction def3InputFieldOnAddDefinitionsScreen = onView(
                allOf(withId(R.id.input_def3),
                        isDisplayed()));
        def3InputFieldOnAddDefinitionsScreen.perform(click());
        def3InputFieldOnAddDefinitionsScreen.perform(replaceText("Definition3"), closeSoftKeyboard());

        ViewInteraction createButtonOnAddDefinitionScreen = onView(
                allOf(withId(R.id.next_button), withText("CREATE"),
                        isDisplayed()));
        createButtonOnAddDefinitionScreen.perform(click());
        String successMessage = "Congratulations " + quizName + " has been created!";
        onView(withText(successMessage)).check(matches(isDisplayed()));

        onView(withText(android.R.string.ok)).perform(click());

    }

    private String registerUser(String userName, String major, String email) {
        ViewInteraction registerButtonOnMainScreen = onView(
                allOf(withId(R.id.register_button), withText("REGISTER"),
                        isDisplayed()));
        registerButtonOnMainScreen.perform(click());

        fillRegistrationScreen(userName, major, email);

        // click on alert
        onView(withId(android.R.id.button1)).perform(click());

        // assert login screen
        ViewInteraction loginButtonOnLoginScreen = onView(
                allOf(withId(R.id.user_login),
                        isDisplayed()));
        loginButtonOnLoginScreen.check(matches(isDisplayed()));

        return userName;
    }

    private void logout() {
        ViewInteraction logoutButtonOnStudentDashboard = onView(
                allOf(withId(R.id.logout),
                        isDisplayed()));
        logoutButtonOnStudentDashboard.perform(click());
    }

    private void fillRegistrationScreen(String userName, String major, String email) {
        ViewInteraction userNameLabelOnRegistrationScreen = onView(
                allOf(withId(R.id.username_label), withText("Username:"),
                        isDisplayed()));
        userNameLabelOnRegistrationScreen.check(matches(isDisplayed()));

        ViewInteraction userNameInputOnRegistrationScreen = onView(
                allOf(withId(R.id.username_input),
                        isDisplayed()));
        userNameInputOnRegistrationScreen.perform(replaceText(userName), closeSoftKeyboard());

        ViewInteraction majorInputFieldOnRegistrationScreen = onView(
                allOf(withId(R.id.major_input),
                        isDisplayed()));
        majorInputFieldOnRegistrationScreen.perform(replaceText(major), closeSoftKeyboard());

        ViewInteraction emailInputFieldOnRegistrationScreen = onView(
                allOf(withId(R.id.email_input),
                        isDisplayed()));
        emailInputFieldOnRegistrationScreen.perform(replaceText(email), closeSoftKeyboard());

        emailInputFieldOnRegistrationScreen.perform(pressImeActionButton());

        ViewInteraction registerButtonOnRegistrationScreen = onView(
                allOf(withId(R.id.register_button), withText("REGISTER"),
                        isDisplayed()));
        registerButtonOnRegistrationScreen.perform(click());
    }

    private String getRandomUserName() {
        List<String> randomUserNames = Arrays.asList("Sunny", "Serg", "Jack", "Satish");
        int randomIndex = (int)(Math.random() * (randomUserNames.size() - 1));
        String randomUserName = randomUserNames.get(randomIndex);

        int random3DigitNumber = (int)(Math.random() * 1000);
        return randomUserName + random3DigitNumber;
    }

    private String getRandomQuizNameForStudent(String student) {

        int random3DigitNumber = (int)(Math.random() * 1000);
        return student + "-" + "quiz" + random3DigitNumber;

    }
}

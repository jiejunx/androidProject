package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;

class AddQuizController {
    private static final AddQuizController instance = new AddQuizController();

    protected Quiz quiz = null;

    private AddQuizController() {}

    static AddQuizController getInstance() {
        return instance;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void cleanController() {
        quiz = null;
    }
}

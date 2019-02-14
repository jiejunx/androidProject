package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizSession;

class PracticeQuizController {
    private static final PracticeQuizController ourInstance = new PracticeQuizController();

    protected QuizSession quizSession = null;

    private PracticeQuizController() {}

    static PracticeQuizController getInstance() {
        return ourInstance;
    }


    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void cleanController() {
        quizSession = null;
    }
}

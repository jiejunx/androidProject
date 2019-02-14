package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models;

import android.arch.persistence.room.Ignore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QuizSession {
    private Quiz baseQuiz = null;

    @Ignore
    private List<Answer> answers = null;


    public void setBaseQuiz(Quiz baseQuiz) {
        this.baseQuiz = baseQuiz;
    }

    public Quiz getBaseQuiz() {
        return baseQuiz;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<String> getRandomDefinitionsForWord() {
        List<String> incorrectDefinitions = baseQuiz.getIncorrectDefinitions();

        List<String> copy = new LinkedList(incorrectDefinitions);
        Collections.shuffle(copy);
        List<String> randomList = copy.subList(0, 3);
        return randomList;
    }

    public Word getWordByIndex(Integer index) {
        List<Word> quizWords = baseQuiz.getWordList();
        Word word = quizWords.get(index);
        return word;
    }


    public Integer calculateScore() {
        if (answers == null) {
            return 0;
        }

        Integer nWords = getBaseQuiz().getNumberOfWords();
        Double answerValue = 100.0 / nWords;
        Double result = 0.0;

        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            if (answer.isCorrect()) {
                result += answerValue;
            }
        }

        return (int) Math.round(result);
    }

}

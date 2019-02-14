package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models;

public class Answer {
    private Word word;
    private String userAnswer;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Boolean isCorrect() {
        return word.getDefinition() == userAnswer;
    }
}

package com.dainv.parrotjapanese.data;

import java.util.List;

/**
 * Created by dainv on 17/08/04.
 */

public class Question {
    private List<LearnItem> answers;    /* list of questions */
    private int question_type;          /* type of question */
    private int correct_index;          /* correct answer position */

    public Question() {
    }

    public List<LearnItem> getAnswers() {
        return answers;
    }

    public void setAnswers(List<LearnItem> answers) {
        this.answers = answers;
    }

    public LearnItem getQuestion() {
        LearnItem item = null;
        if ((correct_index < answers.size()) &&
            (correct_index >= 0)) {
            item = answers.get(correct_index);
        }
        return item;
    }

    public void setCorrectAnswerIndex(int index) {
        correct_index = index;
    }

    public boolean isCorrect(int index) {
        boolean ret = false;
        if (index == correct_index)
            ret = true;
        return ret;
    }

    public boolean isCorrect(LearnItem item) {
        return answers.contains(item);
    }
}

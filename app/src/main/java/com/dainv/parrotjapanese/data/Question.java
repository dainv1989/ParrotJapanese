package com.dainv.parrotjapanese.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dainv on 17/08/04.
 */

public class Question {
    private List<LearnItem> answers;    /* list of questions */
    private int question_type;          /* type of question */
    private int correct_index;          /* correct answer position */

    public Question() {
        answers = new ArrayList<>(AppData.NUMBER_OF_ANSWERS);
    }

    public List<LearnItem> getAnswers() {
        return answers;
    }

    public LearnItem getQuestion() {
        LearnItem item = null;
        if ((correct_index < answers.size()) &&
            (correct_index >= 0)) {
            item = answers.get(correct_index);
        }
        return item;
    }

    public boolean isCorrect(int index) {
        boolean ret = false;
        if (index == correct_index)
            ret = true;
        return ret;
    }
}

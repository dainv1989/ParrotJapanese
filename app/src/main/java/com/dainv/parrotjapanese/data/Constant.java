package com.dainv.parrotjapanese.data;

/**
 * Created by dainv on 11/9/2015.
 */
public final class Constant {
    /**
     * make constructor as private so
     * this class cannot be instantiated anywhere
     */
    private Constant() {}

    public static final String DEVELOPER_KEY = "AlzaSyc5lanwybSDZnEtlvM0sZdNDR8jk8hCqcM";

    /**
     * language constant
     */
    public static final String LANG_VIETNAMESE = "vi";
    public static final String LANG_ENGLISH = "en";

    /**
     * Preference keys
     */
    public static final String KEY_PREF_LANG = "pref_lang";
    public static final String KEY_PREF_QA_NUMBERS = "pref_number_qa";
    public static final String KEY_PREF_EXER_BY_SUBJECT = "pref_exercise_by_subject";

    public static final String EXTRA_EXER_KEY = "exercise";
    public static final String EXTRA_EXER_HIRAGANA = "hiragana";
    public static final String EXTRA_EXER_KATAKANA = "katakana";
    public static final String EXTRA_EXER_PRONUN = "pronunciation";
    public static final String EXTRA_EXER_MEANING = "meaning";

    public final static int NUMBER_OF_ANSWERS = 4;
}

package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // Correct options for the radio buttons
    @BindView(R.id.radio_question_1_option3)
    RadioButton question1Answer;
    @BindView(R.id.radio_question_2_option2)
    RadioButton question2Answer;
    @BindView(R.id.radio_question_3_option2)
    RadioButton question3Answer;

    // Checkboxes
    @BindView(R.id.check_question_4_option1)
    CheckBox question4checkbox1;   // Correct
    @BindView(R.id.check_question_4_option2)
    CheckBox question4checkbox2;   // Correct
    @BindView(R.id.check_question_4_option3)
    CheckBox question4checkbox3;   // Correct

    @BindView(R.id.check_question_5_option1)
    CheckBox question5checkbox1;   // Correct
    @BindView(R.id.check_question_5_option2)
    CheckBox question5checkbox2;   // Incorrect
    @BindView(R.id.check_question_5_option3)
    CheckBox question5checkbox3;   // Correct

    @BindView(R.id.check_question_6_option1)
    CheckBox question6checkbox1;   // Incorrect
    @BindView(R.id.check_question_6_option2)
    CheckBox question6checkbox2;   // Correct
    @BindView(R.id.check_question_6_option3)
    CheckBox question6checkbox3;   // Correct

    // Edittexts for the last questions
    @BindView(R.id.text_question_7)
    EditText question7answer;
    @BindView(R.id.text_question_8)
    EditText question8answer;
    @BindView(R.id.text_question_9)
    EditText question9answer;

    // Number of correct answers
    int totalCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * Checks all the answers and build a result text with the grading and all the questions that
     * had the correct or incorrect options selected or answered.
     *
     * @param view the view that triggered this method
     */
    public void checkAnswer(View view) {
        totalCorrect = 0;

        String results = "Results:";

        results += "\n" + questionRadioButtonResult(1, question1Answer);
        results += "\n" + questionRadioButtonResult(2, question2Answer);
        results += "\n" + questionRadioButtonResult(3, question3Answer);

        results += "\n" + questionCheckBoxResult(4, new CheckBox[]{question4checkbox1, question4checkbox2, question4checkbox3}, new CheckBox[]{});
        results += "\n" + questionCheckBoxResult(5, new CheckBox[]{question5checkbox1, question5checkbox3}, new CheckBox[]{question5checkbox2});
        results += "\n" + questionCheckBoxResult(6, new CheckBox[]{question6checkbox2, question6checkbox3}, new CheckBox[]{question6checkbox1});

        results += "\n" + questionEditTextResult(7, getString(R.string.question_7_answer), question7answer);
        results += "\n" + questionEditTextResult(8, getString(R.string.question_8_answer), question8answer);
        results += "\n" + questionEditTextResult(9, getString(R.string.question_9_answer), question9answer);

        results += "\n\n" + String.format(Locale.getDefault(), "Total %d/9", totalCorrect);

        Toast.makeText(this, results, Toast.LENGTH_LONG).show();
    }

    /**
     * Checks if the correct radio button is checked, and build the result line accordingly
     *
     * @param questionNumber the number of the question being checked
     * @param questionAnswer the correct radio button that has to be checked
     * @return The resulting line according to the answer
     */
    private String questionRadioButtonResult(int questionNumber, RadioButton questionAnswer) {
        return questionResult(questionNumber, questionAnswer.isChecked());
    }

    /**
     * Checks if the correct check boxes were checked and the incorrect ones were unchecked.
     * Then build the resulting line accordingly.
     *
     * @param questionNumber  the number of the question being checked
     * @param correctOptions  an array with the checkboxes that must be checked
     * @param incorretOptions an array with the checkboxes that must be unchecked
     * @return The resulting line according to the options that the user checked
     */
    private String questionCheckBoxResult(int questionNumber, CheckBox[] correctOptions, CheckBox[] incorretOptions) {
        boolean correctResult = true;
        for (CheckBox correctCheckBox : correctOptions) {
            correctResult = correctResult && correctCheckBox.isChecked();
        }

        boolean incorrectResult = false;
        for (CheckBox incorrectCheckBox : incorretOptions) {
            incorrectResult = incorrectResult || incorrectCheckBox.isChecked();
        }

        return questionResult(questionNumber, correctResult && !incorrectResult);
    }

    /**
     * Checks if a EditText has the correct answer. Then buils the resulting line accordingly.
     *
     * @param questionNumber the number of the question being checked
     * @param expectedText   the correct answer
     * @param editText       the editText containing the actual answer from the user
     * @return The resulting line according to the user answer
     */
    private String questionEditTextResult(int questionNumber, String expectedText, EditText editText) {
        return questionResult(questionNumber, expectedText.equalsIgnoreCase(editText.getText().toString()));
    }

    /**
     * Build the resulting line according to the result parameter
     *
     * @param questionNumber the number of the question
     * @param result         the result parameter
     * @return the resulting line
     */
    private String questionResult(int questionNumber, boolean result) {
        if (result) {
            totalCorrect++;
            return formatCorrectResult(questionNumber);
        } else {
            return formatIncorrectResult(questionNumber);
        }
    }

    /**
     * Builds a correct result line
     *
     * @param questionNumber the question number
     * @return the result line based on the questionResultMask
     */
    private String formatCorrectResult(int questionNumber) {
        return String.format(Locale.getDefault(), getString(R.string.question_result_mask), questionNumber, getString(R.string.result_correct));
    }

    /**
     * Builds a incorrect result line
     *
     * @param questionNumber the question number
     * @return the result line based on the questionResultMask
     */
    private String formatIncorrectResult(int questionNumber) {
        return String.format(Locale.getDefault(), getString(R.string.question_result_mask), questionNumber, getString(R.string.result_incorrect));
    }
}

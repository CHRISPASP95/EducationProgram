package com.example.christospaspalieris.educationprogram;

import com.google.firebase.storage.StorageReference;

/**
 * Created by Christos Paspalieris on 28-May-17.
 */

public class Questions {

    private String questionText;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String correctAnswer;
    private boolean creditAlreadyGiven;


    public Questions()
    {

    }
    public Questions(String questionText, String choiceA, String choiceB, String choiceC, String choiceD, String correctAnswer) {
        this.questionText = questionText;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.correctAnswer = correctAnswer;


    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isCreditAlreadyGiven() {
        return creditAlreadyGiven;
    }

    public void setCreditAlreadyGiven(boolean creditAlreadyGiven) {
        this.creditAlreadyGiven = creditAlreadyGiven;
    }



/*
    public String mQuestions[] = {
            "1 + 1 = ?",
            "6 + 4 = ?",
            "1 + 7 = ?",
            "2 + 7 = ?",
            "0 + 8 = ?",
            "9 + 9 = ?",
            "8 + 0 = ?",
            "7 + 3 = ?",
            "5 + 2 = ?",
            "6 + 1 = ?",
            "17 + 11 = ?",
            "11 + 12 = ?",
            "12 + 13 = ?",
            "13 + 14 = ?",
            "19 + 15 = ?",
            "13 + 16 = ?",
            "18 + 17 = ?",
            "15 + 18 = ?",
            "171 + 112 = ?",
            "112 + 123 = ?",
            "123 + 134 = ?",
            "134 + 145 = ?",
            "195 + 156 = ?",
            "136 + 167 = ?",
            "187 + 178 = ?",
            "158 + 189 = ?"
    };

    private String mChoices[][] = {
            {"2","0","4","7"},
            {"1","10","3","9"},
            {"6","3","8","10"},
            {"7","5","0","9"},
            {"1","8","2","5"},
            {"10","21","16","18"},
            {"2","9","32","8"},
            {"10","12","7","3"},
            {"5","2","3","7"},
            {"6","1","5","7"},
            {"28","19","50","25"},
            {"29","30","23","39"},
            {"25","15","35","75"},
            {"11","19","25","27"},
            {"4","9","34","20"},
            {"3","18","21","29"},
            {"25","19","35","11"},
            {"33","3","23","43"},
            {"283","253","263","293"},
            {"265","225","235","275"},
            {"297","277","267","257"},
            {"179","379","279","259"},
            {"251","351","451","151"},
            {"303","203","403","433"},
            {"385","355","365","345"},
            {"377","397","347","347"}
    };

    private String mCorrectAnswers[] = {"2","10","8","9","8","18","8","10","7","7","28","23","25","27","34","29","35","33","283","235","257","279","351","303","365","347"};

    public String getQuestion(int q)
    {
        String question = mQuestions[q];
        return question;
    }

    public String getChoice1(int q)
    {
        String choice1 = mChoices[q][0];
        return choice1;
    }
    public String getChoice2(int q)
    {
        String choice2 = mChoices[q][1];
        return choice2;
    }
    public String getChoice3(int q)
    {
        String choice3 = mChoices[q][2];
        return choice3;
    }
    public String getChoice4(int q)
    {
        String choice4 = mChoices[q][3];
        return choice4;
    }

    public String getCorrectAnswer(int q)
    {
        String answer = mCorrectAnswers[q];
        return answer;
    }
*/

}


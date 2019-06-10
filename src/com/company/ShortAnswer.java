package com.company;

import java.util.Vector;
import static com.company.Main.isTest;

public class ShortAnswer extends Essay implements java.io.Serializable {

    public Vector<String> createShortAnswer(int questionNumber) {
        setPrompt(questionNumber);
        EssayQuestion.add(this.prompt);
        Output.outputString("Please input a word limit\n");
        String Wordlimit = Input.inputString();
        EssayQuestion.add("Word limit");
        EssayQuestion.add(Wordlimit);
        EssayQuestion.add("ShortAnswer");
        Output.outputString("you have created the following Short Answer question\n" + this.prompt + "Word limit = " + Wordlimit +"\n");
        if(isTest==true) {
            Essay();
            AntiOverrideCorrectAnswer();
        }
        Output.outputString("\nnew displaying the question menu\n");
        return EssayQuestion;
    }
}

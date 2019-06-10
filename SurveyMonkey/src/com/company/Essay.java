package com.company;

import java.util.Vector;
import static com.company.Main.isTest;

public class Essay extends Question implements java.io.Serializable {

    Vector EssayQuestion = new Vector();

    public Vector<String> createEssay(int questionNumber) {
        setPrompt(questionNumber);
        EssayQuestion.add(this.prompt);
        EssayQuestion.add("Essay");
        Output.outputString("you have created the following Essay question\n" + this.prompt +"\n");
        if(isTest==true) {
            Essay();
            AntiOverrideCorrectAnswer();
        }
        Output.outputString("\nnew displaying the question menu\n");
        return EssayQuestion;
    }

}

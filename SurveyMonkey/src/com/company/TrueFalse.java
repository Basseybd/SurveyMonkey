package com.company;

import java.util.Vector;
import static com.company.Main.isTest;

public class TrueFalse extends MultipleChoice implements java.io.Serializable {


    public Vector<String> createTrueFalse(int questionNumber) {
        setPrompt(questionNumber);
        MultipleChoice.add(this.prompt);
        MultipleChoice.add("True / False?");
        MultipleChoice.add("TrueFalse");
        if(isTest==true) {
            TrueFalse();
            AntiOverrideCorrectAnswer();
        }
        Output.outputString("you have created the following T/F question\n" + this.prompt +"\n");
        for(int i = 1; i < MultipleChoice.size()-1; i++){
            Output.outputString( MultipleChoice.get(i) + "\n");
        }
        Output.outputString("\nnew displaying the question menu\n");
        return MultipleChoice;

    }
}

package com.company;


import java.util.Vector;
import static com.company.Main.isTest;


public class MultipleChoice extends Question implements java.io.Serializable{

    public String option;
    public String[] options;
    static Vector MultipleChoiceAnswerOptions = new Vector();
    Vector MultipleChoice = new Vector();

    public Vector<String> createMultipleChoice(int questionNumber) {
        setPrompt(questionNumber);
        Output.outputString("Please input the options for the  with a '//' between each option" +
                " where the first value is a second is b and so on.\n");
        option =  Input.inputString();
        options = option.split("//");
        MultipleChoice.add(this.prompt);
        for(int i = 0; i < options.length; i++){
            MultipleChoice.add((char) ('a' + i) +") " + options[i]);
            MultipleChoiceAnswerOptions.add((char) ('a' + i) +") " + options[i]);
        }
        MultipleChoice.add("MultipleChoice");
        if(isTest==true) {
            RankOrMultipleChoiceAnswer();
            AntiOverrideCorrectAnswer();
            MultipleChoiceAnswerOptions.clear();
        }
        Output.outputString("you have created the following Multiple Choice question\n" + this.prompt +"\n");
        for(int i = 1; i < MultipleChoice.size()-1; i++){
            Output.outputString(MultipleChoice.get(i) + "\n");
        }
        Output.outputString("\nnow displaying the question menu\n");
        return MultipleChoice;
    }
}


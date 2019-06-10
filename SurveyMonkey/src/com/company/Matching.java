package com.company;

import java.util.Vector;
import static com.company.Main.isTest;


public class Matching extends Question implements java.io.Serializable{

    public String rightOption;
    public String[] rightOptions;
    public String leftOption;
    public String[] leftOptions;
    static Vector leftMatching = new Vector();
    static Vector RightMatching = new Vector();
    Vector Matching = new Vector();

    public Vector<String> createMatching(int questionNumber) {
        setPrompt(questionNumber);
        Output.outputString("Please input the left options for the matching question with a '//' between each option " +
                "the first value will be 'a' and the second 'b', it will progress in a linear fashion\n");
        leftOption = Input.inputString();
        leftOptions = leftOption.split("//");
        Output.outputString("Please input the right options for the matching question with a '//' between each option " +
                "the first value will be 'A' and the second 'B', it will progress in a linear fashion\n");
        rightOption = Input.inputString();
        rightOptions = rightOption.split("//");

        Matching.add(this.prompt);
        if(rightOptions.length==leftOptions.length) {
            for (int i = 0; i < rightOptions.length; i++) {
                String left = (char) ('a' + i) + ") " + leftOptions[i];
                String right = (char) ('A' + i) + ") " + rightOptions[i];
                leftMatching.add(left);
                RightMatching.add(right);
                Matching.add(String.format("%-20s %s", left, right));
            }
            Matching.add("Matching");
            if(isTest==true) {
                MatchingAnswer();
                AntiOverrideCorrectAnswer();
                leftMatching.clear();
                RightMatching.clear();
            }
            Output.outputString("you have created the following Matching question\n" + this.prompt + "\n");
            for (int i = 1; i < Matching.size()-1; i++) {
                Output.outputString( Matching.get(i) + "\n");
            }
            Output.outputString("\nnow displaying the question menu\n");
            return Matching;
        }
        else {
            Output.outputString("you have created options that aren't equal on both sides please try again later please delete this file and start over\n");
        }
        return null;
    }
}

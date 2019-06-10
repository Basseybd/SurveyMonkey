package com.company;

import java.io.*;
import java.util.Vector;

import static java.lang.String.*;

public class Test extends Survey implements Serializable {

    String correctAnswerString;
    String[] correctAnswersArray;
    Vector correctAnswersVector=new Vector();

    public void Grade(){
        load();
        int earnedPoints=0;
        int totalPoints;
        if(currentVector!=null){
            currentVector.clear();
        }
        ReadVectorFromFile("Responses/" + currentSurvey.surveyName + ".ser");
        final Vector<Vector> ResponseToGrade = currentVector;
        try {
            ReadVectorFromFile("Answers/" + currentSurvey.surveyName + ".ser");
            final Vector<Vector> Answers = currentVector;
            for(int j=0;j<ResponseToGrade.size();j++){
                totalPoints=Answers.size();
                Vector<Vector> individualResponseToGrade=ResponseToGrade.elementAt(j);
                if(!(Answers.size()==individualResponseToGrade.size())){
                    Output.outputString("please review the correct answers");
                    Main.startingMenu();
                }
                for(int i=0;i<Answers.size();i++){
                    if(Answers.elementAt(i).equals(individualResponseToGrade.elementAt(i))){
                        earnedPoints = earnedPoints +1;
                    }
                }
                for(int i=0;i<Answers.size();i++) {
                    String innerElement = String.valueOf(Answers.elementAt(i).elementAt(0));
                    if (innerElement.equals("Essay") || innerElement.equals("ShortAnswer")) {
                        totalPoints = totalPoints - 1;
                    }
                }
                Output.outputString("Response " + (j + 1) + " got " + earnedPoints + "/" + totalPoints + " on the test\n");
                earnedPoints = 0;
            }

        } catch (Exception ex) {
            Output.outputString("this is not a test please try again\n");
            Main.startingMenu();
        }

    }

    public void Essay(){
        correctAnswersVector.add("Essay");
    }


    public Vector TrueFalse(){                                           //adds the correct answer to none multiple answer questions
        Output.outputString("Please input the correct answer 'True' or 'False'\n");
        correctAnswerString = Input.inputString();
        while(!(correctAnswerString.equals("True") || correctAnswerString.equals("False"))) {
            Output.outputString("Invalid Input, please input the correct answer 'True' or 'False'\n");
            correctAnswerString = Input.inputString();
        }

        correctAnswersVector.add(correctAnswerString);
        return correctAnswersVector;
    }

    public Vector RankOrMultipleChoiceAnswer(){                              //adds the correct answer to multiple answer questions
        Output.outputString("Please input the correct answer(s) for the  with a '//' between each answer" +
                " please only enter one number per answer where 1=a, 2=b and so one;\n");
        correctAnswerString = Input.inputString();
        correctAnswersArray = correctAnswerString.split("//");
        for (int i = 0; i < correctAnswersArray.length; i++) {
            try
            {
                int integerValue =  Integer.parseInt(correctAnswersArray[i])-1;
                correctAnswersVector.add(MultipleChoice.MultipleChoiceAnswerOptions.elementAt(integerValue));
            }
            catch (NumberFormatException e)
            {
                Output.outputString("Please read the instructions and try again.");
                RankOrMultipleChoiceAnswer();
            }

        }
        return correctAnswersVector;
    }

    public Vector  MatchingAnswer(){                              //adds the correct answer to multiple answer questions
        Output.outputString("Please input the correct answer(s) for the  with a '//' between each answer;" +
                "and a ' ' between each correct pair please only enter one number per answer 1=a, 2=b " +
                "and so one and after the space 1=A 2=B and so one. please start with the first " +
                "1 and x where is it the correct pair then process to the 2 and so on\n");
        correctAnswerString =  Input.inputString();
        correctAnswersArray = correctAnswerString.split("//");
        for (int i = 0; i < correctAnswersArray.length; i++) {
            try
            {
                String[] matchingOptions = correctAnswersArray[i].split(" ");
                int integerValueLeft =  Integer.parseInt(matchingOptions[0])-1;
                int integerValueRight =  Integer.parseInt(matchingOptions[1])-1;
                correctAnswersVector.add(format("%-20s %s" , Matching.leftMatching.elementAt(integerValueLeft),
                        Matching.RightMatching.elementAt(integerValueRight)));
            }
            catch (NumberFormatException e)
            {
                Output.outputString("Please read the instructions and try again.");
                MatchingAnswer();
            }
        }
        return correctAnswersVector;
    }

    public void AntiOverrideCorrectAnswer(){
        Vector<Vector> helperVector = new Vector<>();
        if(currentVector!=null){
            currentVector.clear();
        }
        ReadVectorFromFile("Answers/" + currentSurvey.surveyName + ".ser");
        if(currentVector==null){
            helperVector.add(correctAnswersVector);
            currentVector=helperVector;
        }
        else{
            currentVector.add(correctAnswersVector);
        }
        WriteVectorToFile(currentVector, "Answers/" + currentSurvey.surveyName + ".ser");
    }
}

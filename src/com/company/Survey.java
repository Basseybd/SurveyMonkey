package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Vector;

public class Survey implements Serializable {

    Vector<Vector> questions = new Vector();
    Vector<Vector> responseVector = new Vector();
    public String surveyName;
    public static Vector<Vector>  currentVector=null;
    public static Survey currentSurvey=null;

    public void create() {                                                //creates a survey or test
        Output.outputString("Please name your file ");
        this.surveyName =  Input.inputString();
        currentSurvey = this;
        try {
            Output.outputString("\nYou have created a file named " +   this.surveyName +"\n");
            WriteObjectToFile(this, "Survey&Test/" +   this.surveyName + ".ser");
            questionMenu();
            int questionMenuOption = Input.inputInt();
            questionOperation(questionMenuOption);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void display(){                                                //displays current survey or test
        if (currentSurvey== null){
            Output.outputString("There is no current survey or test please load or create a survey or test\n");
        }
        else {
            String element;
            for (int i = 0; i < currentSurvey.questions.size(); i++){
                Vector inner = currentSurvey.questions.elementAt(i);
                for (int j = 0; j < inner.size()-1; j++) {
                    element = (String)inner.elementAt(j);
                    Output.outputString(element + "\n");
                }
                Output.outputString("\n");
            }
            Output.outputString("\n");
        }
    }

    public void load(){                                                       //loads a test or survey
        File folder = new File("Survey&Test/");
        File[] listOfFiles = folder.listFiles();
        Output.outputString("Below are the surveys and tests\n");
        for (int i = 0; i < listOfFiles.length; i++) {
            int order = i+1;
            Output.outputString(order + ") "+ listOfFiles[i].getName() + "\n");
            }
        Output.outputString("Please type out the name of the survey or test you wish to load\n");
        try {
            this.surveyName = Input.inputString();
            ReadObjectFromFile("Survey&Test/" + this.surveyName);
        }
        catch(Exception exception) {
            Input.Invalidinput();
        }
    }

    public void save(){
        if (currentSurvey== null){
            Output.outputString("There is no current survey  or test please load or create a survey or test \n");
        }
        else {
            WriteObjectToFile(this, "Survey&Test/" + Survey.currentSurvey.surveyName + ".ser");
        }
    }

    public void Modify() {
        load();
        display();
        Output.outputString("Do you wish to 'delete', 'add' or 'modify', a question?\n");
        String deleteAddOrModify = Input.inputString();
        switch(deleteAddOrModify) {
            case "delete":
                Output.outputString("What question do you wish to delete?\n");
                int questionIndexDelete = Input.inputInt();
                currentSurvey.questions.remove(questionIndexDelete - 1);
                WriteObjectToFile(currentSurvey, "Survey&Test/" + currentSurvey.surveyName + ".ser");
                break;
            case "add":
                returnQuestionMenu();
                break;
            case "modify":
                Output.outputString("What question do you wish to modify?\n");
                int questionIndexModify = Input.inputInt();
                Output.outputString("what do you wish to modify 'prompt' or 'option' (if applicable)?\n");
                String stringToModify = Input.inputString();
                Vector questionToModify = currentSurvey.questions.elementAt(questionIndexModify - 1);
                switch (stringToModify) {
                    case "prompt":
                        Output.outputString("current prompt " + questionToModify.elementAt(0) + "\n");
                        Output.outputString("what is your replacement prompt?\n");
                        String newPrompt = Input.inputString();
                        questionToModify.set(0, newPrompt);
                        break;
                    case "option":
                        if (Survey.currentSurvey.questions.size() > 2) {
                            Output.outputString("no options to modify\n");
                        } else {
                            for (int options = 1; options < questionToModify.size(); options++) {
                                String OptionsRank = Integer.toString(options);
                                Output.outputString(OptionsRank + questionToModify.elementAt(options) + "\n");
                            }
                            Output.outputString("which option do you want to modify(Select the preceding number)?\n");
                            int oldOption = Input.inputInt();
                            Output.outputString("what is your replacement option?\n");
                            String newOption = Input.inputString();
                            questionToModify.set(oldOption, newOption);
                        }
                        break;
                }
                WriteObjectToFile(currentSurvey, "Survey&Test/" + currentSurvey.surveyName + ".ser");
                break;
                default:
                    Input.Invalidinput();
        }
    }

    public void Take() {
        String element;
        Vector<Vector> helperVector = new Vector<>();
        load();
        if(currentVector!=null){
            currentVector.clear();
        }
        ReadVectorFromFile("Responses/" + currentSurvey.surveyName + ".ser");
        Output.outputString("\nPlease input the answer(s) for the with a '//' between each answer;\n" +
                "and a ' ' between each correct pair if needed please only enter one number per answer 1=a, 2=b \n" +
                "and so one and after the space 1=A 2=B and so one. please start with the first \n" +
                "1 and x where is it the correct pair then process to the 2 and so on");
        for (int i = 0; i < currentSurvey.questions.size(); i++) {
            Vector inner = currentSurvey.questions.elementAt(i);
            for (int j = 0; j < inner.size()-1; j++) {
                element = (String) inner.elementAt(j);
                Output.outputString(element + "\n");
            }
            ResponseType(inner.lastElement(),inner);
            Output.outputString("\n");

        }
        if(currentVector==null){
            helperVector.add(responseVector);
            currentVector=helperVector;
        }
        else{
            currentVector.add(responseVector);
        }
        Output.outputString("\n");
        WriteVectorToFile(currentVector, "Responses/" + currentSurvey.surveyName + ".ser");
    }

    public void Tabulate(){
        String element;
        Vector<Vector> individualResponseVector = new Vector<>();
        Vector<Vector> helperVector = new Vector();
        HashMap<Vector,Integer> tabulatedResponse = new HashMap<Vector, Integer>();
        load();
        if(currentVector!=null){
            currentVector.clear();
        }
        ReadVectorFromFile("Responses/" + currentSurvey.surveyName + ".ser");
        if(currentVector==null){
            Main.startingMenu();
        }
        else {
            for (int i = 0; i < currentSurvey.questions.size(); i++) {
                Vector inner = currentSurvey.questions.elementAt(i);
                for (int j = 0; j < inner.size() - 1; j++) {
                    element = (String) inner.elementAt(j);
                    Output.outputString(element + "\n");
                }
            }
            Output.outputString("\nThese are the responses for the above question question\n");
            Output.outputString("Responses; every answer is seperated by a ',' \n");
            for(int j=0;j<currentSurvey.questions.size();j++){
                for(int i=0;i<currentVector.size();i++)
                {
                    individualResponseVector = currentVector.elementAt(i);
                    helperVector.add(individualResponseVector.elementAt(j));
                    if(tabulatedResponse.containsKey(helperVector.elementAt(i))){
                        tabulatedResponse.put(helperVector.elementAt(i),tabulatedResponse.get(helperVector.elementAt(i))+1);
                    }
                    else {
                        tabulatedResponse.put(helperVector.elementAt(i),1);
                    }
                }
                Output.outputString("\nQuestion "+ (j+1) +"\n");
                tabulatedResponse.forEach((key, value) -> Output.outputHashMap(key, " : " + value +"\n"));
                helperVector.clear();
                tabulatedResponse.clear();
            }
        }
    }

    public void WriteVectorToFile(Vector Input,String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(Input);
            objectOut.close();
            fileOut.close();
            Output.outputString("The questions was successfully written to " + filepath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void ReadVectorFromFile(String filepath) {
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Output.outputString("\nThe vector is being read from the file\n");
            currentVector  = (Vector) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            Output.outputString("No responses yet!");
        }
    }

    public void WriteObjectToFile(Survey currentSurvey,String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(currentSurvey);
            objectOut.close();
            fileOut.close();
            Output.outputString("The questions was successfully written to " + filepath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void ReadObjectFromFile(String filepath) {
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Output.outputString("The questions are being read from the file");
            currentSurvey  = (Survey) ois.readObject();
            ois.close();
        } catch (FileNotFoundException ex){
            Output.outputString("Could find file please try again\n");
            Main.startingMenu();

        } catch (Exception ex) {
            Output.outputString("Could find file please try again\n");
            Main.startingMenu();
        }
    }

    public void questionOperation(int Option) {                                 //creates the question for either test or survey
        switch(Option){
            case 1:
                TrueFalse trueFalseQuestion = new TrueFalse();
                currentSurvey.questions.add  ( trueFalseQuestion.createTrueFalse(currentSurvey.questions.size()+1) );
                returnQuestionMenu();
                break;
            case 2:
                MultipleChoice multipleChoiceQuestion = new MultipleChoice();
                currentSurvey.questions.add ( multipleChoiceQuestion.createMultipleChoice(currentSurvey.questions.size()+1));
                returnQuestionMenu();
                break;
            case 3:
                ShortAnswer shortAnswerQuestion = new ShortAnswer();
                currentSurvey.questions.add  ( shortAnswerQuestion.createShortAnswer(currentSurvey.questions.size()+1));
                returnQuestionMenu();
                break;
            case 4:
                Essay essayQuestion = new Essay();
                currentSurvey.questions.add  ( essayQuestion.createEssay(currentSurvey.questions.size()+1));
                returnQuestionMenu();
                break;
            case 5:
                Ranking rankingQuestion = new Ranking();
                currentSurvey.questions.add  ( rankingQuestion.createRanking(currentSurvey.questions.size()+1));
                returnQuestionMenu();
                break;
            case 6:
                Matching matchingQuestion = new Matching();
                currentSurvey.questions.add (  matchingQuestion.createMatching(currentSurvey.questions.size()+1));
                returnQuestionMenu();
                break;
            case 7:
                WriteObjectToFile(currentSurvey, "Survey&Test/" +   currentSurvey.surveyName + ".ser");
                Main.startingMenu();
                break;
            default:
                Input.Invalidinput();

        }
    }

    public void returnQuestionMenu() {
        questionMenu ( );
        int questionMenuOption = Input.inputInt();
        questionOperation(questionMenuOption);
    }

    public static void questionMenu() {
        Output.outputString("\nPlease select a number from 1-8 \n1) Add a new T/F question\n2) Add a new multiple choice question\n" +
                "3) Add a new short answer question\n4) Add a new essay question\n" +
                "5) Add a new ranking question\n6) Add a new matching question\n7) Save and go back to main menu\n8) Quit\n");
        Output.outputString("Enter a number: \n");
    }

    public void ResponseType(Object QuestionType, Vector QuestionOptions){

        if (QuestionType.equals("TrueFalse")) {
            TrueFalseResponse();
        }
        else if (QuestionType.equals("ShortAnswer")) {
            ShortAnswerResponse(QuestionOptions);
        }
        else if (QuestionType.equals("Essay")) {
            EssayResponse();
        }
        else if (QuestionType.equals("MultipleChoice")) {
            MultipleChoiceResponse(QuestionOptions);
        }
        else if (QuestionType.equals("Ranking")) {
            RankingResponse(QuestionOptions);
        }
        else if (QuestionType.equals("Matching")) {
            MatchingResponse(QuestionOptions);
        }

    }

    public void TrueFalseResponse(){
        String response;
        Vector helperVector = new Vector();
        Output.outputString("Please input either 'True' or 'False'\n");
        response = Input.inputString();
        while(!(response.equals("True") || response.equals("False"))) {
            Output.outputString("Invalid Input, please input the correct answer 'True' or 'False'\n");
            response = Input.inputString();
        }
        helperVector.add(response);
        responseVector.add(helperVector);
    }

    public void ShortAnswerResponse(Vector QuestionOptions) {
        String response;
        String[] responseArray;
        Vector helperVector = new Vector();
        Output.outputString("if it requires two or more answers please separate them with a '//'\n");
        response = Input.inputString();
        responseArray = response.split("//");
        String WordLimitArgument = String.valueOf(QuestionOptions.elementAt(QuestionOptions.size()-2));
        for(int i=0; i < responseArray.length;i++) {
            String[] WordCount = responseArray[i].split(" ");
            int maxWords = Integer.parseInt(WordLimitArgument);
            if (WordCount.length > maxWords) {
                Output.outputString("Over the max word limit please re-enter your response");
                helperVector.clear();
                ShortAnswerResponse(QuestionOptions);
            }
            helperVector.add(responseArray[i]);
        }
        responseVector.add(helperVector);
    }

    public void EssayResponse(){
        String response;
        String[] responseArray;
        Vector helperVector = new Vector();
        Output.outputString("if it requires two or more answers please separate them with a //\n");
        response = Input.inputString();
        responseArray = response.split("//");
        for(int i=0; i < responseArray.length;i++) {
            helperVector.add(responseArray[i]);
        }
        responseVector.add(helperVector);
    }

    public void MultipleChoiceResponse(Vector QuestionOptions){
        String response;
        String[] responseArray;
        Vector helperVector = new Vector();
        Output.outputString("Please input the correct answer(s) for the  with a '//' between each answer" +
                " please only enter one number per answer where 1=a, 2=b and so one;\n");
        response = Input.inputString();
        responseArray = response.split("//");
        for (int i = 0; i < responseArray.length; i++) {
            try {
                int integerValue = Integer.parseInt(responseArray[i]);
                if(integerValue>QuestionOptions.size()-2){
                    MultipleChoiceResponse(QuestionOptions);
                }
                helperVector.add(QuestionOptions.elementAt(integerValue));
            } catch (NumberFormatException |ArrayIndexOutOfBoundsException  e) {
                Output.outputString("Please read the instructions and try again.");
                helperVector.clear();
                MultipleChoiceResponse(QuestionOptions);
            }
        }
        responseVector.add(helperVector);
    }

    public void RankingResponse(Vector QuestionOptions){
        String response;
        String[] responseArray;Vector helperVector = new Vector();
        Output.outputString("Please input the correct answer(s) for the  with a '//' between each answer" +
                " please only enter one number per answer where 1=a, 2=b and so one;\n");
        response = Input.inputString();
        responseArray = response.split("//");
        for (int i = 0; i < responseArray.length; i++) {
            try {
                int integerValue = Integer.parseInt(responseArray[i]);
                if(integerValue>QuestionOptions.size()-2){
                    RankingResponse(QuestionOptions);
                }
                helperVector.add(QuestionOptions.elementAt(integerValue));
            }
            catch (NumberFormatException |ArrayIndexOutOfBoundsException e) {
                Output.outputString("Please read the instructions and try again. ");
                helperVector.clear();
                RankingResponse(QuestionOptions);
            }
        }
        responseVector.add(helperVector);
        if(!((QuestionOptions.size()-2)==(helperVector.size()))){
            Output.outputString("you didn't rank all the options please try again\n");
            helperVector.clear();
            RankingResponse(QuestionOptions);
        }
    }

    public void MatchingResponse(Vector QuestionOptions){
        String response;
        String[] responseArray;
        Vector leftOptions= new Vector();
        Vector rightOptions= new Vector();
        Vector helperVector = new Vector();
        Output.outputString("Please input the correct answer(s) for the  with a '//' between each answer;\n" +
                "and a ' ' between each correct pair please only enter one number per answer 1=a, 2=b \n" +
                "and so one and after the space 1=A 2=B and so one. please start with the first \n" +
                "1 and x where is it the correct pair then process to the 2 and so on\n" +
                "for example you can type '1 3//2 1//3 2'\n");
        response =  Input.inputString();
        responseArray = response.split("//");
        try {
            for (int j = 1; j < QuestionOptions.size() - 1; j++) {
                String[] Options = String.valueOf(QuestionOptions.elementAt(j)).split("[ ]{2,}");
                leftOptions.add(Options[0]);
                rightOptions.add(Options[1]);
            }
            for (int i = 0; i < responseArray.length; i++) {
                String[] matchingOptions = responseArray[i].split(" ");
                int integerValueLeft = Integer.parseInt(matchingOptions[0]) - 1;
                int integerValueRight = Integer.parseInt(matchingOptions[1]) - 1;
                if(integerValueLeft>QuestionOptions.size()-3||integerValueRight>QuestionOptions.size()-3){
                    leftOptions.clear();
                    rightOptions.clear();
                    MatchingResponse(QuestionOptions);
                }
                helperVector.add(String.format("%-20s %s", leftOptions.get(integerValueLeft),
                        rightOptions.get(integerValueRight)));
            }
        }
        catch (NumberFormatException |ArrayIndexOutOfBoundsException e)
        {
            Output.outputString("bad input, read the instructions and try again. \n");
            helperVector.clear();
            leftOptions.clear();
            rightOptions.clear();
            MatchingResponse(QuestionOptions);
        }
        if(!((QuestionOptions.size()-2)==(helperVector.size()))){
            Output.outputString("you didn't matches all the options please try again \n");
            helperVector.clear();
            leftOptions.clear();
            rightOptions.clear();
            MatchingResponse(QuestionOptions);
        }
        responseVector.add(helperVector);
    }

}


package com.company;

import java.util.InputMismatchException;


public class Main {

    public static void main(String[] args) {
        startingMenu();
    }
    public static boolean isTest=false;

    public static void startingMenu(){                                                  //Displaying the first menu
        try
        {
            Output.outputString("Please select a number from 1-3 \n1) Survey\n2) Test\n3) Quit\n");
            Output.outputString("Enter a number: \n");
            int option = Input.inputInt(); // Scans the next token of the input as an int.
            whichMenu(option);
        }
        catch(InputMismatchException exception)
        {
            Input.Invalidinput();
        }
    }

    public static void whichMenu(int option) {                                          //Displaying the survey/test menu
        switch(option){
            case 1:
                try {
                    Output.outputString("Please select a number from 1-6 \n1) Create a new Survey\n2) Display a Survey\n" +
                            "3) Load a Survey\n4) Save current survey\n5) Modify an Existing Survey\n6) Take a Survey\n" +
                            "7) Tabulate a Survey\n8) Back to main menu\n9) Quit\n");
                    Output.outputString("Enter a number: \n");
                    int surveyOption = Input.inputInt();                            // Scans the next token of the input as an int.
                    surveyOperation(surveyOption);
                }
                catch(InputMismatchException exception) {
                    Input.Invalidinput();
                }
                break;
            case 2:
                try {
                    Output.outputString("Please select a number from 1-6 \n1) Create a new Test\n2) Display a Test\n" +
                            "3) Load a Test\n4) Save current test \n5) Modify an Existing Test\n6) Take a Test\n" +
                            "7) Tabulate a Test\n8) Grade a Test\n9) Back to main menu\n10) Quit\n");
                    Output.outputString("Enter a number: \n");
                    int testOption = Input.inputInt();                                // Scans the next token of the input as an int.
                    testOperation(testOption);
                }
                catch(InputMismatchException exception) {
                    Input.Invalidinput();
                }
                break;
            case 3:
                //Quits
                break;
            default:
                Input.Invalidinput();
                startingMenu();
        }

    }

    public static void surveyOperation(int surveyOption) {                              //Acting on the choice from the survey menu
        isTest= false;
        switch(surveyOption) {
            case 1:
                Survey survey = new Survey();
                survey.create();
               break;
            case 2:
                Survey surveyDisplay = new Survey();
                surveyDisplay.display();
                startingMenu();
                break;
            case 3:
                Survey surveyLoad = new Survey();
                surveyLoad.load();
                startingMenu();
                break;
            case 4:
                Survey surveySave = new Survey();
                surveySave.save();
                startingMenu();
                break;
            case 5:
                Survey surveyModify = new Survey();
                surveyModify.Modify();
                startingMenu();
                break;
            case 6:
                Survey surveyTake = new Survey();
                surveyTake.Take();
                startingMenu();
                break;
            case 7:
                Survey surveyTabulate = new Survey();
                surveyTabulate.Tabulate();
                startingMenu();
                break;
            case 8:
                startingMenu();
                break;
            case 9:
                break;
            default:
                Input.Invalidinput();
                startingMenu();
        }
    }

    public static void testOperation(int testOption) {                                 //Acting on the choice from the test menu
        isTest= true;
        switch(testOption){
            case 1:
                Test test = new Test();
                test.create();
                break;
            case 2:
                Test testDisplay = new Test();
                testDisplay.display();
                startingMenu();
                break;
            case 3:
                Test testLoad = new Test();
                testLoad.load();
                startingMenu();
                break;
            case 4:
                Test testSave = new Test();
                testSave.save();
                startingMenu();
                break;
            case 5:
                Test testModify = new Test();
                testModify.Modify();
                startingMenu();
                break;
            case 6:
                Test testTake = new Test();
                testTake.Take();
                startingMenu();
                break;
            case 7:
                Test testTabulate = new Test();
                testTabulate.Tabulate();
                startingMenu();
                break;
            case 8:
                Test testGrade = new Test();
                testGrade.Grade();
                startingMenu();
                break;
            case 9:
                startingMenu();
                break;
            case 10:
                break;
            default:
                Input.Invalidinput();
                startingMenu();
        }
    }

}

package com.company;

import java.io.Serializable;

public class Question extends Test implements Serializable {

    public String prompt;

    public void setPrompt(int questionNumber) {                                   //sets the question prompt
        Output.outputString("Please input the question prompt\n");
        this.prompt = "\nQuestion " + questionNumber +") " + Input.inputString();
    }
}

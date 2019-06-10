package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Input {
    transient static Scanner input = new Scanner(System.in);

    public static String inputString(){
        try
        {
            return input.nextLine();
        }
        catch(InputMismatchException exception)
        {
            Invalidinput();
            return null;
        }
    }

    public static int inputInt() {
        try
        {
            return Integer.parseInt(input.nextLine());
        }
        catch(NumberFormatException exception)
        {
            return 0;
        }
    }

    public static void Invalidinput()
    {
        Output.outputString("Invalid input, please an integer from the above next time\n");
    }
}


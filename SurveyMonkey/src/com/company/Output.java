package com.company;
import java.util.Vector;

public class Output {
    public static void outputString(String output){
        System.out.printf(output);
    }

    public static void outputHashMap(Vector output, String stringOutput) {
        for (int i = 0; i < output.size(); i++) {
            System.out.print(output.elementAt(i)+", ");
        }
        outputString(stringOutput);
    }
}

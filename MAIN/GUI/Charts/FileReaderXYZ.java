package MAIN.GUI.Charts;

import java.io.*;
import java.util.Scanner;

public class FileReaderXYZ {

    public double[] getCord(String cord) {
        double[] x = new double[366];
        try {
            //scanner.next() is Input of our user
            File starting = new File(System.getProperty("user.dir"));
            String fileStr = "/DataNasa/" + cord + ".txt";
            File file = new File(starting, fileStr);
            Scanner scan = new Scanner(file);
            //scan.hasNextLine() reads whole file by one line
            int point = 0;
            while (scan.hasNextLine()) {//Gives a line to String
                String line = scan.nextLine();
                x[point++] = Double.parseDouble(line);
            }
            scan.close();

        } catch (FileNotFoundException e) { //Catch, if program can not find a file
            System.out.println("Sorry, file not found");
        }
        return x;
    }
}

package GUI.Charts;

import java.io.*;
import java.util.Scanner;

/**
 * File reader for NASA coordinates
 */
public class FileReaderXYZ {

    /**
     * Gets coordinates from the txt file
     * @param cord Name of the file
     * @return array of coordinates
     */
    public double[] getCord(String cord) {
        double[] x = new double[367];
        try {
            //scanner.next() is Input of our user
            File starting = new File(System.getProperty("user.dir"));
            String fileStr = "src/main/resources/DataNasa/" + cord + ".txt";
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

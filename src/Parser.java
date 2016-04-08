/**
 * Created by RatchyZanko on 4/8/16.
 */

import java.io.*;

public class Parser {
    public static void main(String[] args) {
        String fileName = "problems/problems.txt";
        String outputName = "output/leetcode-problems.json";
        String line;
        String jsonObject = "";
        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                int i = 0;
                String numberString = "";
                while (line.charAt(i) != ' ') {
                    numberString = numberString + line.charAt(i);
                    i++;
                }
                while (line.charAt(i) == ' ') {
                    i++;
                }
                int number = Integer.parseInt(numberString);
                String title = line.substring(i, line.indexOf('%') - 4).trim();
                String difficulty = line.substring(line.indexOf('%') + 1, line.length()).trim();
                jsonObject = jsonObject +
                        "{ " +
                        "\"number\":" + "\"" + number + "\"" + "," +
                        "\"title\":" + "\"" + title + "\"" + "," +
                        "\"difficulty\":" + "\"" + difficulty + "\""
                        + "},";

            }
            jsonObject = jsonObject.substring(0, jsonObject.length() - 1);
            jsonObject = "{" + "\"leetcode-problems\": [" + jsonObject + "]}";
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }

        try {
            FileWriter fileWriter = new FileWriter(outputName);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
            
            bufferedWriter.write(jsonObject);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

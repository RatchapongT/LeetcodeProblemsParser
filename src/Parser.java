/**
 * Created by RatchyZanko on 4/8/16.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class Parser {
    public static void main(String[] args) {

        Document doc = null;
        try {
            doc = Jsoup.connect("https://leetcode.com/problemset/algorithms/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements problemsTable = doc.select("#problemList");
        Elements tableBody = problemsTable.select("tbody");
        JSONObject leetcodeProblem = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Element row : tableBody.select("tr")) {
            Elements data = row.select("td");
            int number = Integer.parseInt(data.get(1).text());
            String title = data.get(2).text();
            double rate = Double.parseDouble(data.get(3).text().substring(0, data.get(3).text().length() - 1));
            String link = data.get(2).select("a").first().attr("abs:href");
            boolean lock = data.get(2).select("i").first() != null;
            String difficulty = data.get(5).text();

            JSONObject obj = new JSONObject();
            obj.put("number", number);
            obj.put("title", title);
            obj.put("rate", rate);
            System.out.println(link);
            obj.put("link", link);
            obj.put("lock", lock);
            obj.put("difficulty", difficulty);
            jsonArray.add(obj);


        }
        leetcodeProblem.put("leetcode-problems", jsonArray);
        String outputName = "output/leetcode-problems.json";

        try {
            FileWriter file = new FileWriter(outputName);
            file.write(leetcodeProblem.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

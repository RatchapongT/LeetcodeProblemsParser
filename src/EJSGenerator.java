import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by RatchyZanko on 4/9/16.
 */
public class EJSGenerator {
    public static void main(String[] args) {

        Document doc = null;
        try {
            doc = Jsoup.connect("https://leetcode.com/problemset/algorithms/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements problemsTable = doc.select("#problemList");
        Elements tableBody = problemsTable.select("tbody");

        for (Element row : tableBody.select("tr")) {
            Elements data = row.select("td");
            String link = data.get(2).select("a").first().attr("abs:href");

            String problemName = link.substring(30, link.length() - 1);

            System.out.println();
            String outputName = "leetcode-problems-list/" + problemName + ".ejs";

            try {
                FileWriter file = new FileWriter(outputName);
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

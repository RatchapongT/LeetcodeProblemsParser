import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
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
            String title = data.get(2).text();

            //templatesGenerator(problemName, title);
            problemsGenerator(problemName, link);
            explanationsGenerator(problemName);
            solutionsGenerator(problemName);
            javaGenerator(problemName);
        }

    }

    public static void templatesGenerator(String problemName, String title) {
        File theDir = new File("leetcode-templates");

        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {

            }
            if (result) {
                System.out.println("leetcode-templates created");
            }
        }
        String templates = "leetcode-templates/template-" + problemName + ".ejs";
        String template = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <%- include('../../partials/page/head.ejs', {title: '" + title + "'}); %>\n" +
                "    <body class=\"fixed-header\">\n" +
                "        <%- include('../../partials/page/header.ejs'); %>\n" +
                "        <%- include('../../partials/page/search.ejs'); %>\n" +
                "        <div id=\"wrapper\">\n" +
                "\n" +
                "            <section class=\"elements\">\n" +
                "                <div class=\"container\">\n" +
                "                    <h2>" + title + "</h2>\n" +
                "\n" +
                "                    <h3><b>Question</b></h3>\n" +
                "                    <div class=\"problem-question\">\n" +
                "                        <%- include('../leetcode-problems/problem-" + problemName + ".ejs'); %>\n" +
                "                    </div>\n" +
                "\n" +
                "                    <h3><b>Solution</b></h3>\n" +
                "                    <div class=\"problem-solution\">\n" +
                "                        <pre class=\"language-java\">\n" +
                "                            <code class=\"language-java\">\n" +
                "                                <%= include('../leetcode-solutions/solution-" + problemName + ".java'); %>\n" +
                "                            </code>\n" +
                "                        </pre>\n" +
                "                    </div>\n" +
                "\n" +
                "                    <h3><b>Explanation</b></h3>\n" +
                "                    <div class=\"problem-explanation\">\n" +
                "                        <%- include('../leetcode-explanations/explanation-" + problemName + ".ejs'); %>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </section>\n" +
                "        </div>\n" +
                "        <%- include('../../partials/page/footer.ejs'); %>\n" +
                "        <%- include('../../partials/page/script.ejs'); %>\n" +
                "    </body>\n" +
                "</html>";
        try {
            FileWriter file = new FileWriter(templates);
            file.write(template);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void problemsGenerator(String problemName, String link) {
        File theDir = new File("leetcode-problems");

        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {

            }
            if (result) {
                System.out.println("leetcode-problems created");
            }
        }
        String problems = "leetcode-problems/problem-" + problemName + ".ejs";

        Document subDoc = null;
        try {
            subDoc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements problemContent = subDoc.select(".question-content");

        try {
            FileWriter file = new FileWriter(problems);
            file.write(problemContent.html());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void explanationsGenerator(String problemName) {
        File theDir = new File("leetcode-explanations");

        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {

            }
            if (result) {
                System.out.println("leetcode-explanations created");
            }
        }
        String explanations = "leetcode-explanations/explanation-" + problemName + ".ejs";
        try {
            FileWriter file = new FileWriter(explanations);
            file.write("<p>Explanation goes here...</p>");
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void solutionsGenerator(String problemName) {
        File theDir = new File("leetcode-solutions");

        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {

            }
            if (result) {
                System.out.println("leetcode-solutions created");
            }
        }
        String solution = "leetcode-solutions/solution-" + problemName + ".ejs";
        String template = "<h3><b>Solution</b></h3>\n" +
                "                    <div class=\"problem-solution\">\n" +
                "                        <pre class=\"language-java\">\n" +
                "                            <code class=\"language-java\">\n" +
                "                                <%= include('../leetcode-java/java-" + problemName + ".java'); %>\n" +
                "                            </code>\n" +
                "                        </pre>\n" +
                "                    </div>\n" +
                "\n" +
                "                    <h3><b>Explanation</b></h3>\n" +
                "                    <div class=\"problem-explanation\">\n" +
                "                        <%- include('../leetcode-explanations/explanation-" + problemName + ".ejs'); %>\n" +
                "                    </div>";
        try {
            FileWriter file = new FileWriter(solution);
            file.write(template);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void javaGenerator(String problemName) {
        File theDir = new File("leetcode-java");

        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {

            }
            if (result) {
                System.out.println("leetcode-java created");
            }
        }

        String java = "leetcode-java/java-" + problemName + ".java";
        try {
            FileWriter file = new FileWriter(java);
            file.write("public class Solution {}");
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

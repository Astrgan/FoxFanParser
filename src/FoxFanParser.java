import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class FoxFanParser {
    Document doc = null;

    public FoxFanParser(String URL) {

        String id = "ram";
        String season = URL.substring(URL.length() - 1);

        Properties connInfo = new Properties();
        connInfo.put("user", "alex");
        connInfo.put("password", "angel");
        connInfo.put("useUnicode","true"); // (1)
        connInfo.put("charSet", "UTF8"); // (2)




        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/MultGo?", connInfo);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO cartoons (name_cartoon, name_cartoon_rus, season, episode, name_episode, description, index_cartoon, id_cartoon) VALUE (?, ?, ?, ?, ?, ?, ?, ?)")
        ){
            doc = Jsoup
                    .connect(URL)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 5000)
                    .get();


            statement.setString(1, "Rick and Morty");
            statement.setString(2, "Рик и Морти");
            statement.setInt(3, Integer.parseInt(season));


            Element table = doc.select("table").get(2); //select the first table.
            Elements rows = table.select("tr");

            int ep = 1;
            for (int i = 0; i < rows.size(); i++) {

                try {
                    System.out.println(rows.get(i).select("a").get(1).text());
                    statement.setString(5, rows.get(i).select("a").get(1).text());
                }catch (Exception e){
                    System.out.println(rows.get(i).select("td").first().text());
                    statement.setString(7, id+"_s"+season+"e"+ep);
                    statement.setInt(8, (10000*1)+(100*Integer.parseInt(season))+ep);
                    statement.setInt(4, ep++);
                    statement.setString(6, rows.get(i).select("td").first().text());
                    System.out.println(statement);
                    statement.executeUpdate();
                }


            }


/*
            Elements smallSeason = doc.getElementsByClass("smallSeason");



            for (Element e: smallSeason) {

                Elements em = e.getElementsByTag("em");

                //System.out.println(em.select("h2").text());

                em.select("h2").remove();
                System.out.println(em.text());


                //System.out.println(e.getElementsByTag("em"));

            }*/



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class FoxFanParser {
    Document doc = null;
    String id = "ram";
    URL preURL;
    String cartoonName, rusCartoonName;
    int intCode;
    String link_name;

    public FoxFanParser(String seasonsURL, int season) {

        try {

            preURL = new URL(seasonsURL+season);
            setCartoonCode(preURL.getHost());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        Properties connInfo = new Properties();
        connInfo.put("user", "alex");
        connInfo.put("password", "angel");
        connInfo.put("useUnicode","true"); // (1)
        connInfo.put("charSet", "UTF8"); // (2)




        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/MultGo?", connInfo);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO cartoons (name_cartoon, name_cartoon_rus, season, episode, name_episode, description, index_cartoon, id_cartoon, shot_name, link_name) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
        ){
            doc = Jsoup
                    .connect(seasonsURL+season)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 5000)
                    .get();


            statement.setString(1, cartoonName);
            statement.setString(2, rusCartoonName);
            statement.setInt(3, season);


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
                    statement.setInt(8, (intCode*1)+(100*season)+ep);
                    statement.setString(9, this.id);
                    statement.setString(10, this.link_name);
                    statement.setInt(4, ep);
                    statement.setString(6, rows.get(i).select("td").first().text());
                    System.out.println(statement);
                    statement.executeUpdate();

                    getImage(preURL, ep+(100*season));

                    ep++;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//constructor

    void getImage (URL preURL, int episode){
        try {


            URL URLimage = new URL("http://"+preURL.getHost()+"/seasons/" + episode + "_big.jpg");

            InputStream in = URLimage.openStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream("img/"+this.id+"/"+episode+".jpg"));
            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }
            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setCartoonCode(String str){
        System.out.println(str);
        switch (str) {
            case "rickandmorty.cn-fan.ru":
                this.id = "ram";
                cartoonName = "Rick and Morty";
                rusCartoonName = "Рик и Морти";
                intCode = 10000;
                link_name = "rickandmorty";
                break;
            case "southpark.cc-fan.tv":
                this.id  = "sp";
                cartoonName = "South park";
                rusCartoonName = "Южный парк";
                intCode = 20000;
                link_name = "southpark";
                break;
            case "paradise.nf-fan.tv":
                this.id  = "ppd";
                cartoonName = "Paradise PD";
                rusCartoonName = "полиция парадайз";
                intCode = 30000;
                link_name = "paradisepd";
                break;

        }

    }
}

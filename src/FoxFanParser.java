
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.EnumSet;
import java.util.Properties;

public class FoxFanParser {
    Document doc = null;
    String id = "ram";
    URL preURL;
    String cartoonName, rusCartoonName;
    int intCode;
    String link_name;
    String chanel;

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
        connInfo.put("useSSL", "false");




        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/MultGo2?", connInfo);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO cartoons (name_cartoon, name_cartoon_rus, season, episode, name_episode, description, index_cartoon, id_cartoon, shot_name, link_name, channel) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
        ){
            doc = Jsoup
                    .connect(seasonsURL+season)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 5000)
                    .get();


            statement.setString(1, cartoonName);
            statement.setString(2, rusCartoonName);
            statement.setInt(3, season);


            Element table = doc.select("table").get(1); //select the first table.
            Elements rows = table.select("tr");

            int ep = 1;
            for (int i = 0; i < rows.size(); i++) {

                try {
//                    System.out.println(rows.get(i).select("a").get(1).text());
                    statement.setString(5, rows.get(i).select("a").get(1).text());
                }catch (Exception e){
//                    System.out.println(rows.get(i).select("td").first().text());
                    statement.setString(7, id+"_s"+season+"e"+ep);
                    statement.setInt(8, (intCode*1)+(100*season)+ep);
                    statement.setString(9, this.id);
                    statement.setString(10, this.link_name);
                    statement.setInt(4, ep);
                    statement.setString(6, rows.get(i).select("td").first().text());
                    statement.setString(11, chanel);
//                    System.out.println(statement);
//                    statement.executeUpdate();

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
            System.out.println(URLimage);
//            InputStream in = URLimage.openStream();
//            OutputStream out = new BufferedOutputStream(new FileOutputStream("img/"+this.id+"/"+episode+".jpg"));
//            for (int b; (b = in.read()) != -1; ) {
//                out.write(b);
//            }
//            out.close();
//            in.close();
//            ReadableByteChannel readableByteChannel = Channels.newChannel(URLimage.openStream());
//            FileOutputStream fileOutputStream = new FileOutputStream("img/"+this.id+"/"+episode+".jpg");
//            FileChannel fileChannel = fileOutputStream.getChannel();
//            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//            fileOutputStream.close();

            download(URLimage.toURI(), "img/"+this.id+"/"+episode+".jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File download(URI uri, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        long totalBytesRead = 0L;
        HttpURLConnection con = (HttpURLConnection) uri.resolve(fileName).toURL().openConnection();
        con.setReadTimeout(10000);
        con.setConnectTimeout(10000);
        try (ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
             FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));) {
            totalBytesRead = fileChannel.transferFrom(rbc, 0, 1 << 22); // download file with max size 4MB
            System.out.println("totalBytesRead = " + totalBytesRead);
            fileChannel.close();
            rbc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return path.toFile();
    }


    void setCartoonCode(String str){
        System.out.println(str);
        switch (str) {
            case "rickandmorty.cn-fan.tv":
                this.id = "ram";
                cartoonName = "Rick and Morty";
                rusCartoonName = "Рик и Морти";
                intCode = 10000;
                link_name = "rickandmorty";
                chanel = "Cartoon network";
                break;
            case "southpark.cc-fan.tv":
                this.id  = "sp";
                cartoonName = "South park";
                rusCartoonName = "Южный парк";
                intCode = 20000;
                link_name = "southpark";
                chanel = "Comedy central";
                break;
            case "paradise.nf-fan.tv":
                this.id  = "ppd";
                cartoonName = "Paradise PD";
                rusCartoonName = "полиция парадайз";
                intCode = 30000;
                link_name = "paradisepd";
                chanel = "Netflix";
                break;
            case "simpsons.fox-fan.tv":
                this.id  = "ts";
                cartoonName = "The Simpsons";
                rusCartoonName = "Симпсоны";
                intCode = 40000;
                link_name = "thesimpsons";
                chanel = "FOX";
                break;
            case "familyguy.fox-fan.tv":
                this.id  = "fg";
                cartoonName = "Family Guy";
                rusCartoonName = "Гриффины";
                intCode = 50000;
                link_name = "familyguy";
                chanel = "FOX";
                break;
            case "americandad.fox-fan.tv":
                this.id  = "ad";
                cartoonName = "American dad!";
                rusCartoonName = "Американский папаша!";
                intCode = 60000;
                link_name = "americandad";
                chanel = "FOX";
                break;
            case "clevelandshow.fox-fan.tv":
                this.id  = "ch";
                cartoonName = "Cleveland show";
                rusCartoonName = "Шоу Кливленда";
                intCode = 70000;
                link_name = "clevelandshow";
                chanel = "FOX";
                break;
            case "futurama.fox-fan.tv":
                this.id  = "fut";
                cartoonName = "Futurama";
                rusCartoonName = "Футурама";
                intCode = 80000;
                link_name = "futurama";
                chanel = "FOX";
                break;
            case "duncanville.fox-fan.tv":
                this.id  = "dun";
                cartoonName = "Duncanville";
                rusCartoonName = "Данканвилл";
                intCode = 90000;
                link_name = "duncanville";
                chanel = "FOX";
                break;
            case "blesstheharts.fox-fan.tv":
                this.id  = "bth";
                cartoonName = "Bless the Harts";
                rusCartoonName = "Благословите Хартов";
                intCode = 100000;
                link_name = "BlesstheHarts";
                chanel = "FOX";
                break;
            case "bordertown.fox-fan.tv":
                this.id  = "bort";
                cartoonName = "Bordertown";
                rusCartoonName = "Приграничный городок";
                intCode = 110000;
                link_name = "bordertown";
                chanel = "FOX";
                break;
            case "bobsburgers.fox-fan.tv":
                this.id  = "bb";
                cartoonName = "Bob's burgers";
                rusCartoonName = "Бургеры Боба";
                intCode = 120000;
                link_name = "Bobsburgers";
                chanel = "FOX";
                break;
            case "kingofthehill.fox-fan.tv":
                this.id  = "koth";
                cartoonName = "King of the Hill";
                rusCartoonName = "Царь горы";
                intCode = 130000;
                link_name = "kingofthehill";
                chanel = "FOX";
                break;
            case "ldr.nf-fan.tv":
                this.id  = "ldar";
                cartoonName = "Love, Death & Robots";
                rusCartoonName = "Любовь, смерть и роботы";
                intCode = 140000;
                link_name = "lovedeathandrobots";
                chanel = "Netflix";
                break;
            case "family.nf-fan.tv":
                this.id  = "fiff";
                cartoonName = "F Is for Family";
                rusCartoonName = "С значит Семья";
                intCode = 150000;
                link_name = "fisforfamily";
                chanel = "Netflix";
                break;
            case "adventuretime.cn-fan.tv":
                this.id  = "at";
                cartoonName = "Adventure time";
                rusCartoonName = "Время приключений";
                intCode = 160000;
                link_name = "adventuretime";
                chanel = "Cartoon network";
                break;
            case "mrpickles.cn-fan.tv":
                this.id  = "mp";
                cartoonName = "Mr. Pickles";
                rusCartoonName = "Мистер пиклз";
                intCode = 170000;
                link_name = "mrpickles";
                chanel = "Cartoon network";
                break;
        }

    }
}


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.ChangeNotifyingArrayList;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicReference;

public class FoxFanVideoDownloader {
    String redLink;
    Document doc = null;
    String id = "ram";
    URL preURL;
    String cartoonName, rusCartoonName;
    int intCode;
    String link_name;
    String chanel;
    ArrayList<Thread> arrThreads = new ArrayList<>();
    int threads = 0;
    int ep;

    public FoxFanVideoDownloader(String seasonsURL, int season) {

        try {
            preURL = new URL(seasonsURL+season);
            setCartoonCode(preURL.getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try{
            doc = getDoc(preURL.toString());

            Element table = doc.select("table").get(2); //select the first table.
            Elements rows = table.select("tr");



            for (ep = 0; ep < rows.size(); ep++) {

                try {
                    String name = rows.get(ep).select("a").get(1).text();
                    String link = "https://" + preURL.getHost() + '/' + rows.get(ep).select("a").get(1).attr("href");
                    System.out.println(name + " -> " + link);
                    Document docEp = getDoc(link);
//                    Document renderDoc = PhantomJsUtils.renderPage(docEp);
//                    String videLink = docEp.select("video").get(1).attr("src");
                    String str = null;
                    String html[] = docEp.html().split("\\r?\\n");

                    for(String s:  html){
                        if (s.contains("player.api(\"play\","))
                            str = s;
                    };
                    String videoLink = str.substring(str.lastIndexOf(",\"")+2, str.lastIndexOf("\""));
                    Connection.Response response = Jsoup.connect(link).followRedirects(true).execute();
                    redLink = response.url().toString();
                    System.out.println("rederec url");

                    File cartoons = new File("cartoons/" + link_name);
                    if (!cartoons.exists()) cartoons.mkdir();
                    File seasonFolder = new File(cartoons.getPath() + "/season" + season);
                    if (!seasonFolder.exists()) seasonFolder.mkdir();

                    File coockie = new File("cookie"+threads+".txt");

                    String command = String.format("curl -b %s -c %s %s", coockie.getAbsolutePath(), coockie.getAbsolutePath(), redLink);
                    Process p = Runtime.getRuntime().exec(command);
                    System.out.println(command);
                    int exitVal = p.waitFor();

                    command = String.format("curl -c %s %s --output %s", coockie.getAbsolutePath(), videoLink, seasonFolder.getAbsolutePath() + "/" +  (ep+1) + ".mp4");
                    System.out.println(command);
                    p = Runtime.getRuntime().exec(command);
                    exitVal = p.waitFor();



                  /*  Thread T1 = new Thread(new Thread(() -> {
                        try {
                            File cartoons = new File("cartoons/" + link_name);
                            if (!cartoons.exists()) cartoons.mkdir();
                            File seasonFolder = new File(cartoons.getPath() + "/season" + season);
                            if (!seasonFolder.exists()) seasonFolder.mkdir();

                            File coockie = new File("cookie"+threads+".txt");

                            String command = String.format("curl -b %s -c %s %s", coockie.getAbsolutePath(), coockie.getAbsolutePath(), redLink);
                            Process p = Runtime.getRuntime().exec(command);
                            System.out.println(command);
                            int exitVal = p.waitFor();

                            command = String.format("curl -c %s %s --output %s", coockie.getAbsolutePath(), videoLink, seasonFolder.getAbsolutePath() + "/" +  (ep+1) + ".mp4");
                            System.out.println(command);
                            p = Runtime.getRuntime().exec(command);
                            exitVal = p.waitFor();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }));

                    T1.start();
                    arrThreads.add(T1);
                    threads++;
                    T1.join();
                    if(threads < 5) {
                        for (Thread thread: arrThreads)
                            thread.join();
                        threads = 0;
                    }
*/
                }catch (Exception e){

                }
                break;
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }//constructor

    private Document getDoc(String link) throws IOException {
        return Jsoup
                .connect(link)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 5000)
                .get();
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
            case "brickleberry.cc-fan.tv":
                this.id  = "bric";
                cartoonName = "Brickleberry";
                rusCartoonName = "Бриклберри";
                intCode = 180000;
                link_name = "brickleberry";
                chanel = "Comedy central";
                break;
        }

    }
}

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.FileWriter;
import java.io.IOException;

class PhantomJsUtils {
    private static String filePath = ".";

    public static Document renderPage(String filePath) {
        System.setProperty("./phantomjs", "libs/phantomjs"); // path to bin file. NOTE: platform dependent
        WebDriver ghostDriver = new PhantomJSDriver();
        try {
            ghostDriver.get(filePath);
            String source = ghostDriver.getPageSource();
            return Jsoup.parse(ghostDriver.getPageSource());
        } finally {
            ghostDriver.quit();
        }
    }

    public static Document renderPage(Document doc) {
        String tmpFileName = "page.html";
        try {
            FileWriter myWriter;
            myWriter = new FileWriter(tmpFileName);
            myWriter.write(doc.toString());
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderPage(tmpFileName);
    }
}
import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        File cartoons = new File("cartoons");

        if (cartoons.exists()) {
            cartoons.mkdir();
        }
/*
        for(int i=0; i<4; i++){
            new FoxFanVideoDownloader("http://rickandmorty.cn-fan.tv/season.php?id=",(i+1));
        }


        for(int i=0; i<22; i++){
            new FoxFanVideoDownloader("http://southpark.cc-fan.tv/season.php?id=",(i+1));
        }

        new FoxFanVideoDownloader("http://paradise.nf-fan.tv/season.php?id=",1);


        for(int i=0; i<16; i++){
            new FoxFanVideoDownloader("http://americandad.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<16; i++){
            new FoxFanVideoDownloader("http://familyguy.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<31; i++){
            new FoxFanVideoDownloader("http://simpsons.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<4; i++){
            new FoxFanVideoDownloader("https://clevelandshow.fox-fan.tv/season.php?id=",(i+1));
        }
        for(int i=0; i<7; i++){
            new FoxFanVideoDownloader("https://futurama.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<1; i++){
            new FoxFanVideoDownloader("https://duncanville.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<1; i++){
            new FoxFanVideoDownloader("https://blesstheharts.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<1; i++){
            new FoxFanVideoDownloader("https://bordertown.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<10; i++){
            new FoxFanVideoDownloader("https://bobsburgers.fox-fan.tv/season.php?id=",(i+1));
        }
*/
        for(int i=0; i<13; i++){
            new FoxFanVideoDownloader("https://kingofthehill.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<1; i++){
            new FoxFanVideoDownloader("https://ldr.nf-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<4; i++){
            new FoxFanVideoDownloader("https://mrpickles.cn-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<3; i++){
            new FoxFanVideoDownloader("https://family.nf-fan.tv/season.php?id=",(i+1));
        }
/*
        for(int i=0; i<10; i++){
            new FoxFanVideoDownloader("https://adventuretime.cn-fan.tv/season.php?id=",(i+1));
        }
*/
//        for(int i=0; i<3; i++){
//            new FoxFanVideoDownloader("https://brickleberry.cc-fan.tv/season.php?id=",(i+1));
//        }

    }
}

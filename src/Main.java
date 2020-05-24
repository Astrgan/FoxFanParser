import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        File cartoons = new File("cartoons");

        if (cartoons.exists()) {
            cartoons.mkdir();
        }

        /*for(int i=0; i<3; i++){
            new FoxFanParser("http://rickandmorty.cn-fan.tv/season.php?id=",(i+1));
        }


        for(int i=0; i<22; i++){
            new FoxFanParser("http://southpark.cc-fan.tv/season.php?id=",(i+1));
        }

        new FoxFanParser("http://paradise.nf-fan.tv/season.php?id=",1);


        for(int i=0; i<16; i++){
            new FoxFanParser("http://americandad.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<16; i++){
            new FoxFanParser("http://familyguy.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<30; i++){
            new FoxFanParser("http://simpsons.fox-fan.tv/season.php?id=",(i+1));
        }

        for(int i=0; i<4; i++){
            new FoxFanParser("https://clevelandshow.fox-fan.tv/season.php?id=",(i+1));
        }
*/
//        for(int i=0; i<7; i++){
//            new FoxFanParser("https://futurama.fox-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<1; i++){
//            new FoxFanParser("https://duncanville.fox-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<1; i++){
//            new FoxFanParser("https://blesstheharts.fox-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<1; i++){
//            new FoxFanParser("https://bordertown.fox-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<10; i++){
//            new FoxFanParser("https://bobsburgers.fox-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<13; i++){
//            new FoxFanParser("https://kingofthehill.fox-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<1; i++){
//            new FoxFanParser("https://ldr.nf-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<4; i++){
//            new FoxFanParser("https://mrpickles.cn-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<3; i++){
//            new FoxFanParser("https://family.nf-fan.tv/season.php?id=",(i+1));
//        }
//
//        for(int i=0; i<10; i++){
//            new FoxFanParser("https://adventuretime.cn-fan.tv/season.php?id=",(i+1));
//        }

        for(int i=0; i<1; i++){
            new FoxFanVideoDownloader("https://brickleberry.cc-fan.tv/season.php?id=",(i+1));
        }

    }
}

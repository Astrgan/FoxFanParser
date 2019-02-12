public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        for(int i=0; i<3; i++){
            new FoxFanParser("http://rickandmorty.cn-fan.ru/season.php?id="+(i+1));
        }

    }
}

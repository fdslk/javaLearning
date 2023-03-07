import org.zqf.Animal;

public class Application {
    public static void main(String[] args) {
        Animal builder = new Animal.Builder("blue").nose("high").mouth("big").build();
        System.out.println(builder);
    }
}

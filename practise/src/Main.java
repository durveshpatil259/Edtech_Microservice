import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        double num = 12324.134;

        NumberFormat u = NumberFormat.getCurrencyInstance(Locale.US);
       String m =  u.format(num);
        System.out.println(m);
    }
}

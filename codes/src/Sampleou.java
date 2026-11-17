import java.math.BigDecimal;
import java.util.Arrays;

public class Sampleou {
    static int sum(int... nums) {
        int total = 0;
        for (int n : nums)
            total += n;
        return total;
    }

    static void show(String label, int... nums)
    {
        System.out.println(label + " -> " + nums.length);
    }


    static void over(int... a)
    { System.out.println("varargs"); }

    static void show() {
        System.out.println(sum());
        System.out.println(sum(1, 2, 3));
        System.out.println(sum(new int[]{4, 5}));
        show("none");
        show("three", 1, 2, 3);
        over(1, 2);
        over(1, 2, 3);
        over(1);
    }
    static void nullVarargs(String... s)
    { System.out.println(s == null ? "null array" : "len " + s.length);
    }
    static void va2() {
        nullVarargs();
        nullVarargs((String) null);
        nullVarargs((String[]) null);
    }


    static void main() {
       show();
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sample {
/*
    // ---------- 1. Data types, variables, casting ----------

   static void dt2() {
        byte b = (byte) 130;
        System.out.println(b);
        int i = (int) 3.99;
        System.out.println(i);
        System.out.println((int) -3.99);
        System.out.println((char) 65);
        System.out.println('A' + 1);
        System.out.println((char) ('A' + 1));
    }

     //Overflow happens before widening



         static void dt4() {
        System.out.println(0.1 + 0.2);
        System.out.println(0.1 + 0.2 == 0.3);
        System.out.println(0.1f + 0.2f);
        System.out.println(10 / 0.0);
        System.out.println(0.0 / 0.0);
        // System.out.println(10 / 0); // ArithmeticException
             BigDecimal a = new BigDecimal("0.1");
        BigDecimal b = new BigDecimal("0.2");
        BigDecimal c = new BigDecimal("0.3");

        System.out.println(a.add(b));
        System.out.println(a.add(b).compareTo(c) == 0);
    }

    static void dt5() {
        byte x = 10;
        // x = x + 1;      // compile error: int -> byte

        x++;
        System.out.println(x);
        int s = 10;
        char c = 'a';
        // short t = s + c; // compile error
        var v = s + c;     // what type is v?
        System.out.println(v + " " + ((Object) v).getClass().getSimpleName());
    }


    static void dt6() {
        Integer a = 12, b = 12;
        Integer c = 125, d = 125;
        System.out.println(a == b);
        System.out.println(c == d);
        System.out.println(c.equals(d));
    }



    // ---------- 2. Operators & control flow ----------
    static void op1() {
        int i = 5;
        int j = i++ + ++i;
        System.out.println(i + " " + j);
    }
    static void op2() {
        int x = 10;
        x = x++;
        System.out.println(x);
    }

    static void op4() {
        System.out.println(1 + 2 + "3" + 4 + 5);
        System.out.println("" + 'a' + 'b');
        System.out.println('a' + 'b' + "");
    }

    static void op5() {
        System.out.println(7 % 3);
        System.out.println(-7 % 3);
        System.out.println(7 % -3);
        System.out.println(-8 >> 1);
        System.out.println(-8 >>> 28);
        System.out.println(5 & 3);
        System.out.println(5 | 3);
        System.out.println(5 ^ 3);
    }

    static void cf1() {
        int day = 2;
        switch (day) {
            case 1: System.out.println("Mon");
            case 2: System.out.println("Tue");
            case 3: System.out.println("Wed");
            default: System.out.println("Other");
        }
    }

    static void cf3() {
        boolean flag = false;
        if (flag = true) System.out.println("Inside");
        else System.out.println("Else");
    }
    static void cf4() {
        outer:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (j == 2) continue outer;
                if (i == 3) break outer;
                System.out.println(i + "," + j);
            }
        }
    }
    static void cf5() {
        int i = 10;
        do {
            System.out.println("do " + i);
        } while (i < 5);
        while (i < 5) System.out.println("while " + i);
        System.out.println("end");
    }
    static void cf6() {
        int count = 0;
        for (int i = 0; i < 10; i++) ;
        {
            count++;
        }
        System.out.println(count);
    }


    // ---------- 3. Strings ----------
    static void st1() {
        String a = "java";
        String b = "java";
        String c = new String("java");
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a.equals(c));
    }



    static void st2() {
        String s = "hello";
        s.toUpperCase();
        s.concat(" world");
        System.out.println(s);
        s = s.toUpperCase().concat(" WORLD");
        System.out.println(s);
    }


    static void st3() {
        String a = "ja";
        String b = a + "va";
        final String f = "ja";
        String c = f + "va";
        System.out.println(b == "java");
        System.out.println(c == "java");
    }
    static void st4() {
        String s = "  Java Refresher  ";
        System.out.println("[" + s.trim() + "]");
        System.out.println(s.length());
        System.out.println(s.trim().substring(5));
        System.out.println(s.trim().substring(0, 4));
        System.out.println(s.indexOf('a'));
        System.out.println("a,b,,c,".split(",").length);
        System.out.println(String.join("-", "x", "y", "z"));
        System.out.println("ab".repeat(3));
        System.out.println("   ".isBlank() + " " + "   ".isEmpty());
    }
    static void st5() {
        StringBuilder sb = new StringBuilder("abc");
        sb.append("def").insert(0, "X").reverse();
        System.out.println(sb);
        sb.setLength(3);
        System.out.println(sb);
        sb.deleteCharAt(0).replace(0, 1, "ZZ");
        System.out.println(sb);
    }
    static void st6() {
        StringBuilder a = new StringBuilder("hi");
        StringBuilder b = new StringBuilder("hi");
        System.out.println(a.equals(b));
        System.out.println(a.toString().equals(b.toString()));
        System.out.println(a.compareTo(b) == 0);
    }
    static void st7() {
        StringBuilder sb = new StringBuilder("x");
        change(sb);
        System.out.println(sb);
        String s = "x";
        change(s);
        System.out.println(s);
    }
    static void change(StringBuilder sb) { sb.append("y"); sb = new StringBuilder("z"); }
    static void change(String s) { s = s + "y"; }




    // ---------- 4. Arrays & varargs ----------
    static void ar1() {
        int[] a = new int[3];
        String[] s = new String[2];
        boolean[] f = new boolean[1];
        System.out.println(a[0] + " " + s[0] + " " + f[0]);
        System.out.println(a);
        System.out.println(Arrays.toString(a));
    }


    static void ar4() {
        int[] arr = {5, 3, 8};
        for (int x : arr) x = x * 2;
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) arr[i] *= 2;
        System.out.println(Arrays.toString(arr));
    }

    static void ar5() {
        int[] arr = {4, 1, 3};
        try {
            for (int i = 0; i <= arr.length; i++) System.out.println(arr[i]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
    static void ar6() {
        int[] arr = {9, 2, 7, 4};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.binarySearch(arr, 7));
        int[] copy = Arrays.copyOf(arr, 6);
        System.out.println(Arrays.toString(copy));
        int[] filled = new int[4];
        Arrays.fill(filled, 5);
        System.out.println(Arrays.toString(filled));
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
        Integer[] boxed = list.toArray(new Integer[0]);
        System.out.println(boxed.length);
    }
    static int sum(int... nums) {
        int total = 0;
        for (int n : nums) total += n;
        return total;
    }
    static void show(String label, int... nums)
    { System.out.println(label + " -> " + nums.length);
     }
    static void over(int a, int b)
    { System.out.println("fixed");
     }
    static void over(int... a)
    { System.out.println("varargs"); }
    static void va1() {
        System.out.println(sum());
        System.out.println(sum(1, 2, 3));
        System.out.println(sum(new int[]{4, 5}));
        show("none");
        show("three", 1, 2, 3);
        over(1, 2);
        over(1, 2, 3);
        over(1);
    }
    static void nullVarargs(String... s) { System.out.println(s == null ? "null array" : "len " + s.length); }
    static void va2() {
        nullVarargs();
        nullVarargs((String) null);
        nullVarargs((String[]) null);
    }

     */
static void op5() {
    System.out.println(7 % 3);
    System.out.println(-7 % 3);
    System.out.println(7 % -3);
    System.out.println(-8 >> 1);
    System.out.println(-8 >>> 28);
    System.out.println(5 & 3);
    System.out.println(5 | 3);
    System.out.println(5 ^ 3);
}



    public static void main(String[] args) throws Exception {
     op5();
     }
}



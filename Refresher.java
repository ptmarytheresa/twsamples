import java.util.*;

public class Refresher {
    static void p(String label) { System.out.println("\n=== " + label + " ==="); }

    // ---------- 1. Data types, variables, casting ----------
    static void dt1() {
        int a = 10, b = 4;
        System.out.println(a / b);
        System.out.println(a / (double) b);
        System.out.println((double) (a / b));
    }
   /* static void dt2() {
        byte b = (byte) 130;
        System.out.println(b);
        int i = (int) 3.99;
        System.out.println(i);
        System.out.println((int) -3.99);
        System.out.println((char) 65);
        System.out.println('A' + 1);
        System.out.println((char) ('A' + 1));
    }
    static void dt3() {
        int max = Integer.MAX_VALUE;
        System.out.println(max + 1);
        long big = max + 1;
        System.out.println(big);
        long fixed = max + 1L;
        System.out.println(fixed);
    }
    static void dt4() {
        System.out.println(0.1 + 0.2);
        System.out.println(0.1 + 0.2 == 0.3);
        System.out.println(0.1f + 0.2f);
        System.out.println(10 / 0.0);
        System.out.println(0.0 / 0.0);
        // System.out.println(10 / 0); // ArithmeticException
    }
    static void dt5() {
        byte x = 10;
        // x = x + 1;      // compile error: int -> byte
        x += 1;            // OK: implicit cast
        x++;
        System.out.println(x);
        short s = 10;
        char c = 'a';
        // short t = s + c; // compile error
        var v = s + c;     // what type is v?
        System.out.println(v + " " + ((Object) v).getClass().getSimpleName());
    }
    static void dt6() {
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        System.out.println(a == b);
        System.out.println(c == d);
        System.out.println(c.equals(d));
    }
    static void dt7() {
        System.out.println(010);
        System.out.println(0x1F);
        System.out.println(0b101);
        System.out.println(1_000_000);
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
    static void op3() {
        int a = 0;
        if (a != 0 && 10 / a > 1) System.out.println("A");
        else System.out.println("B");
        // replace && with &  -> ArithmeticException
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
    static void cf2() {
        int day = 2;
        String name = switch (day) {
            case 1, 7 -> "Weekend";
            case 2, 3, 4, 5, 6 -> {
                String t = "Week";
                yield t + "day";
            }
            default -> "Invalid";
        };
        System.out.println(name);
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
    static void cf7() {
        Object o = 42;
        String r = switch (o) {
            case Integer n when n > 40 -> "big int " + n;
            case Integer n -> "int " + n;
            case String s -> "string " + s;
            default -> "other";
        };
        System.out.println(r);
    }

    // ---------- 3. Strings ----------
    static void st1() {
        String a = "java";
        String b = "java";
        String c = new String("java");
        String d = c.intern();
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a.equals(c));
        System.out.println(a == d);
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

    static void st8() throws Exception {
        int n = 100_000;
        long t = System.nanoTime();
        String s = "";
        for (int i = 0; i < 20_000; i++) s += "a";
        System.out.println("String +=  : " + (System.nanoTime() - t) / 1_000_000 + " ms");
        t = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append("a");
        System.out.println("StringBuilder: " + (System.nanoTime() - t) / 1_000_000 + " ms");
        t = System.nanoTime();
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < n; i++) sf.append("a");
        System.out.println("StringBuffer : " + (System.nanoTime() - t) / 1_000_000 + " ms");
    }
    static void st9() throws Exception {
        StringBuilder sb = new StringBuilder();
        StringBuffer sf = new StringBuffer();
        Runnable r = () -> {
            for (int i = 0; i < 100_000; i++) {
                try { sb.append('a'); } catch (Exception e) { }   // builder can even throw
                sf.append('a');
            }
        };
        Thread t1 = new Thread(r), t2 = new Thread(r);
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("Builder: " + sb.length());
        System.out.println("Buffer : " + sf.length());
    }

    // ---------- 4. Arrays & varargs ----------
    static void ar1() {
        int[] a = new int[3];
        String[] s = new String[2];
        boolean[] f = new boolean[1];
        System.out.println(a[0] + " " + s[0] + " " + f[0]);
        System.out.println(a);
        System.out.println(Arrays.toString(a));
    }
    static void ar2() {
        int[] a = {1, 2, 3};
        int[] b = a;
        b[0] = 99;
        int[] c = a.clone();
        c[1] = 77;
        System.out.println(Arrays.toString(a));
        System.out.println(a == b);
        System.out.println(Arrays.equals(a, new int[]{99, 2, 3}));
        System.out.println(a.equals(new int[]{99, 2, 3}));
    }
    static void ar3() {
        int[][] jag = new int[3][];
        jag[0] = new int[]{1};
        jag[1] = new int[]{1, 2};
        jag[2] = new int[]{1, 2, 3};
        for (int[] row : jag) System.out.println(Arrays.toString(row));
        System.out.println(jag.length + " " + jag[2].length);
        int[][] grid = {{1, 2}, {3, 4}};
        System.out.println(Arrays.deepToString(grid));
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
    static void show(String label, int... nums) { System.out.println(label + " -> " + nums.length); }
    static void over(int a, int b) { System.out.println("fixed"); }
    static void over(int... a) { System.out.println("varargs"); }
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
    } */

    public static void main(String[] args) throws Exception {
        p("dt1"); dt1();
    }
}

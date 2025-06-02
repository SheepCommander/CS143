public class Main {
        // System.out.println(mystery5(5, 7));
        // System.out.println(mystery5(12, 9));
        // System.out.println(mystery5(-7, 4));
        // System.out.println(mystery5(-23, -48));
        // System.out.println(mystery5(128, 343));
        // System.out.println(mystery6(7, 1));
        // System.out.println(mystery6(4, 2));
        // System.out.println(mystery6(4, 3));
        // System.out.println(mystery6(5, 3));
        // System.out.println(mystery6(5, 4));
        // System.out.println();
        // recursionMystery7("abcd");
        // System.out.println();
        // recursionMystery7("leonard");
        // System.out.println();
        // recursionMystery7("breakfast");
        // System.out.println();
        // RecursionMyster8 was manageable without programming!

        // System.out.println(recursionMystery9(12, 49));
        // System.out.println(recursionMystery9(73, -8));
        // System.out.println(recursionMystery9(-248, -3795));
        //travel(2, 1);
    public static String speakAndSpell(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int count = 1;
        char current = input.charAt(0);

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == current) {
                count++;
            } else {
                result.append(count).append(current);
                current = input.charAt(i);
                count = 1;
            }
        }
        result.append(count).append(current);

        return result.toString();
    }

    public static void main(String[] args) {
        String n = speakAndSpell("5");
        System.out.println(n);    // Output: 11
        for (int i=1; i<18; i++) {
            n = speakAndSpell(n);
            System.out.println(n);
        }
    }

    public static void travel(int x, int y) {
        // floor
        if (x == 1 && y == 0) {
            System.out.println("E");
            return;
        }
        if (y == 1 && x == 0) {
            System.out.println("N");
            return;
        }
        if (y == 0 && x == 0) {
            System.out.println();
            return;
        }
        // ceiling
        if (x > 1 && y == 0) {
            System.out.print("E ");
            travel(x - 1, 0);
            return;
        }
        if (y > 1 && x == 0) {
            System.out.print("N ");
            travel(0, y - 1);
            return;
        }
        // first try East
        System.out.print("E ");
        travel(x - 1, y);
        // then try North
        System.out.print("N ");
        travel(x, y - 1);
        // then try NorthEast
        System.out.print("NE ");
        travel(x - 1, y - 1);
    }

    public static int recursionMystery9(int x, int y) {
        if (x < 0) {
            return -recursionMystery9(-x, y);
        } else if (y < 0) {
            return -recursionMystery9(x, -y);
        } else if (x == 0 && y == 0) {
            return 0;
        } else {
            return 100 * recursionMystery9(x / 10, y / 10) + 10 * (x % 10) + y % 10;
        }
    }

    public static int mystery5(int x, int y) {
        if (x < 0) {
            return -mystery5(-x, y);
        } else if (y < 0) {
            return -mystery5(x, -y);
        } else if (x == 0 && y == 0) {
            return 0;
        } else {
            return 100 * mystery5(x / 10, y / 10) + 10 * (x % 10) + y % 10;
        }
    }

    public static int mystery6(int n, int k) {
        if (k == 0 || k == n) {
            return 1;
        } else if (k > n) {
            return 0;
        } else {
            return mystery6(n - 1, k - 1) + mystery6(n - 1, k);
        }
    }

    public static void recursionMystery7(String s) {
        if (s.length() <= 1) {
            System.out.print(s);
        } else {
            String first = s.substring(0, 1);
            String last = s.substring(s.length() - 1, s.length());
            String mid = s.substring(1, s.length() - 1);
            if (first.compareTo(last) < 0) {
                recursionMystery7(mid);
                System.out.print(last + first.toUpperCase());
            } else {
                System.out.print("[" + first + "]");
                recursionMystery7(mid);
                System.out.print(last);
            }
        }
    }
}
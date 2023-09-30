package ru.sasha.test;

import java.util.*;

public class Main {

    static Map<String, Integer> romanDigits = new TreeMap<>();
    {
        romanDigits.put("Z",2000);
        romanDigits.put("M",1000);
        romanDigits.put("CM",900);
        romanDigits.put("D",500);
        romanDigits.put("CD",400);
        romanDigits.put("C",100);
        romanDigits.put("XC",90);
        romanDigits.put("L",50);
        romanDigits.put("XL",40);
        romanDigits.put("X",10);
        romanDigits.put("IX",9);
        romanDigits.put("VIII",8);
        romanDigits.put("VII",7);
        romanDigits.put("VI",6);
        romanDigits.put("V",5);
        romanDigits.put("IV",4);
        romanDigits.put("III",3);
        romanDigits.put("II",2);
        romanDigits.put("I",1);
    }

    public static String intToRom(int input) throws IllegalStateException {
        if (input < 1 || input > 3999)
            throw new IllegalStateException();
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;
        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }

    public static int romanToArabic(String roman) {
        int[] intervals={0, 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        String[] numerals={"", "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int result = 0;
        for (int i = intervals.length-1; i >= 0; i-- ) {
            while (roman.indexOf(numerals[i]) == 0 && numerals[i].length() > 0) {
                result += intervals[i];
                roman = roman.substring(numerals[i].length());
            }
        }
        return result;
    }

    public static String calc(String input) throws IllegalStateException {
        input = input.replaceAll("\\s+","");
        String[] vars = input.split("[+*/-]");
        int x=0;
        int y=0;
        String a = vars[0];
        String b = vars[1];
        //System.out.println(x);System.out.println(y);System.out.println(a);System.out.println(b);

        boolean isRim;
        String op;
        if (input.matches("\\d+\\s*[+*/-]\\s*\\d+")) {
            isRim = false;
            x = Integer.parseInt(vars[0]);
            y = Integer.parseInt(vars[1]);
            if (x < 1 || x > 10 || y < 1 || y > 10)
                throw new IllegalStateException();
            op = input.replaceAll("\\d+","");
        } else if (input.matches("[IVXLCDMZ]+\\s*[+*/-]\\s*[IVXLCDMZ]+")) {
            isRim = true;
            op = input.replaceAll("[IVXLCDMZ]+","");
        } else
            throw new IllegalStateException();

        int res;

        if (isRim) {
            x=romanToArabic(a);
            y=romanToArabic(b);
            if (x > 10 || y > 10)
                throw new IllegalStateException();
        }

        res = switch (op) {
            case "+":
                yield x + y;
            case "-":
                yield x - y;
            case "*":
                yield x * y;
            case "/":
                yield x / y;
            default:
                throw new IllegalStateException("Unexpected value: " + op);
        };

        String resRom;
        if (isRim) {
            resRom=intToRom(res);
            return resRom;
        }

        return Integer.toString(res);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        String res = Main.calc(str);

        System.out.println(res);
    }

}

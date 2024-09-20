package utils;

public class Experiment {

    public static void main(String[] args) {
        String result = method("str1","str2");
    }

    public static <T> T method(String... per) {

        return (T) per[per.length-1];
    }
}

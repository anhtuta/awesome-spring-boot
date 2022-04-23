package hello.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class StrUtils {

    private static final String[] RANDOM_PRD_FIRST_NAME = {"Harry", "Ross", "Bruce", "Cook",
            "Carolyn",
            "Morgan", "Albert", "Walker", "Randy", "Reed", "Larry", "Barnes", "Lois", "Wilson",
            "Jesse", "Campbell", "Ernest", "Rogers", "Theresa", "Patterson", "Henry", "Simmons",
            "Michelle", "Perry", "Frank", "Butler", "Shirley", "Brooks", "Rachel", "Edwards",
            "Christopher", "Perez", "Thomas", "Baker", "Sara", "Moore", "Chris", "Bailey", "Roger",
            "Johnson", "Marilyn", "Thompson", "Anthony", "Evans", "Julie", "Hall", "Paula",
            "Phillips", "Annie", "Hernandez", "Dorothy", "Murphy", "Alice", "Howard"};
    private static final String[] RANDOM_PRD_LAST_NAME = {"Ruth", "Jackson", "Debra", "Allen",
            "Gerald",
            "Harris", "Raymond", "Carter", "Jacqueline", "Torres", "Joseph", "Nelson", "Carlos",
            "Sanchez", "Ralph", "Clark", "Jean", "Alexander", "Stephen", "Roberts", "Eric", "Long",
            "Amanda", "Scott", "Teresa", "Diaz", "Wanda", "Thomas"};
    private static final String[] LOREM_WORDS = {"lorem", "ipsum", "dolor", "sit", "amet",
            "consectetur",
            "adipiscing", "elit", "curabitur", "vel", "hendrerit", "libero", "eleifend", "blandit",
            "nunc", "ornare", "odio", "ut", "orci", "gravida", "imperdiet", "nullam", "purus",
            "lacinia", "a", "pretium", "quis", "congue", "praesent", "sagittis", "laoreet",
            "auctor", "mauris", "non", "velit", "eros", "dictum", "proin", "accumsan", "sapien",
            "nec", "massa", "volutpat", "venenatis", "sed", "eu", "molestie", "lacus", "quisque",
            "porttitor", "ligula", "dui", "mollis", "tempus", "at", "magna", "vestibulum", "turpis",
            "ac", "diam", "tincidunt", "id", "condimentum", "enim", "sodales", "in", "hac",
            "habitasse", "platea", "dictumst", "aenean", "neque", "fusce", "augue", "leo", "eget",
            "semper", "mattis", "tortor", "scelerisque", "nulla", "interdum", "tellus", "malesuada",
            "rhoncus", "porta", "sem", "aliquet", "et", "nam", "suspendisse", "potenti", "vivamus",
            "luctus", "fringilla", "erat", "donec", "justo", "vehicula", "ultricies", "varius",
            "ante", "primis", "faucibus", "ultrices", "posuere", "cubilia", "curae", "etiam",
            "cursus", "aliquam", "quam", "dapibus", "nisl", "feugiat", "egestas", "class", "aptent",
            "taciti", "sociosqu", "ad", "litora", "torquent", "per", "conubia", "nostra",
            "inceptos", "himenaeos", "phasellus", "nibh", "pulvinar", "vitae", "urna", "iaculis",
            "lobortis", "nisi", "viverra", "arcu", "morbi", "pellentesque", "metus", "commodo",
            "ut", "facilisis", "felis", "tristique", "ullamcorper", "placerat", "aenean",
            "convallis", "sollicitudin", "integer", "rutrum", "duis", "est", "etiam", "bibendum",
            "donec", "pharetra", "vulputate", "maecenas", "mi", "fermentum", "consequat",
            "suscipit", "aliquam", "habitant", "senectus", "netus", "fames", "quisque", "euismod",
            "curabitur", "lectus", "elementum", "tempor", "risus", "cras"};

    public static String getRandomInArray(String[] arr) {
        return arr[NumberUtils.getRandomInt(0, arr.length - 1)];
    }

    public static String getRandomLorem(int length) {
        List<String> strs = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            strs.add(getRandomInArray(LOREM_WORDS));
        }
        return StringUtils.capitalize(String.join(" ", strs));
    }

    public static String getRandomFullname() {
        return getRandomInArray(RANDOM_PRD_FIRST_NAME) + " "
                + getRandomInArray(RANDOM_PRD_LAST_NAME);
    }

}

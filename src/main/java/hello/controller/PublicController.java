package hello.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.common.Result;
import hello.utils.NumberUtils;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/public-api")
public class PublicController {

    static final String[] RANDOM_PRD_FIRST_NAME = {"Harry", "Ross", "Bruce", "Cook", "Carolyn",
            "Morgan", "Albert", "Walker", "Randy", "Reed", "Larry", "Barnes", "Lois", "Wilson",
            "Jesse", "Campbell", "Ernest", "Rogers", "Theresa", "Patterson", "Henry", "Simmons",
            "Michelle", "Perry", "Frank", "Butler", "Shirley", "Brooks", "Rachel", "Edwards",
            "Christopher", "Perez", "Thomas", "Baker", "Sara", "Moore", "Chris", "Bailey", "Roger",
            "Johnson", "Marilyn", "Thompson", "Anthony", "Evans", "Julie", "Hall", "Paula",
            "Phillips", "Annie", "Hernandez", "Dorothy", "Murphy", "Alice", "Howard"};
    static final String[] RANDOM_PRD_LAST_NAME = {"Ruth", "Jackson", "Debra", "Allen", "Gerald",
            "Harris", "Raymond", "Carter", "Jacqueline", "Torres", "Joseph", "Nelson", "Carlos",
            "Sanchez", "Ralph", "Clark", "Jean", "Alexander", "Stephen", "Roberts", "Eric", "Long",
            "Amanda", "Scott", "Teresa", "Diaz", "Wanda", "Thomas"};
    static final String[] LOREM_WORDS = {"lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
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
    static final String[] COLORS =
            {"Light Blue", "Black", "White", "Yellow", "Viole", "Green", "Red"};
    static final String IMAGE_FOLDER = "https://ducdongyyen.com/nmvn/images/test/";
    static final int TOTAL_IMAGES = 30;
    static final int[] SALE_PERCENTAGES = {5, 10, 15, 20, 30, 50};


    @GetMapping("hello-world")
    public Result helloWorld() {
        return new Result().successRes("Hello world!");
    }

    @GetMapping("/nmvn/product/all")
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>(1000);
        Set<String> nameSet = new HashSet<>(1000);
        Product p;
        String name;
        int originalPrice, salePc; // percentage

        for (int i = 105; i <= 1000; i++) {
            p = new Product();
            p.setId(i);
            while (true) {
                name = getRandomInArray(RANDOM_PRD_FIRST_NAME) + " "
                        + getRandomInArray(RANDOM_PRD_LAST_NAME);
                if (!nameSet.contains(name)) {
                    nameSet.add(name);
                    break;
                }
            }
            p.setName(name);
            p.setDescription(getRandomLorem(NumberUtils.getRandomInt(32, 45)));
            p.setCategoryId(NumberUtils.getRandomInt(1, 5));
            p.setSlug(p.getName().toLowerCase().replaceAll(" ", "-"));
            p.setSku(p.getSlug());
            p.setHeroImg(IMAGE_FOLDER + NumberUtils.getRandomInt(1, TOTAL_IMAGES) + ".jpg");
            p.setImages(getRandomImageArray(NumberUtils.getRandomInt(4, 10)));
            p.setPrice(NumberUtils.getRandomInt(1000, 20000) * 1000);
            p.setOriginalPrice(p.getPrice());   // 30% sản phẩm sẽ ko được sale
            if (NumberUtils.getRandomInt(1, 100) > 70) {
                // Chỉ có 70% sản phẩm được sale
                salePc = getRandomInArray(SALE_PERCENTAGES);
                System.out.println("i = " + i + ", salePc = " + salePc);
                originalPrice = (int) ((p.getPrice() / 1000) / (1 - salePc * 1.0 / 100)) * 1000;
                System.out.println("Sale off " + (100 - p.getPrice() * 100 / originalPrice) + "%");
                p.setOriginalPrice(originalPrice);
            }
            p.setQuantity(NumberUtils.getRandomInt(10, 100));
            p.setColor(getRandomInArray(COLORS));
            p.setMeta(getRandomLorem(NumberUtils.getRandomInt(12, 20)));

            products.add(p);
        }

        return products;
    }

    private static String getRandomInArray(String[] arr) {
        return arr[NumberUtils.getRandomInt(0, arr.length - 1)];
    }

    private static int getRandomInArray(int[] arr) {
        return arr[NumberUtils.getRandomInt(0, arr.length - 1)];
    }

    private static String getRandomLorem(int length) {
        List<String> strs = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            strs.add(getRandomInArray(LOREM_WORDS));
        }
        return StringUtils.capitalize(String.join(" ", strs));
    }

    private static String[] getRandomImageArray(int length) {
        String[] strs = new String[length];
        boolean[] isExist = new boolean[TOTAL_IMAGES + 1];
        int loopCnt = 0;

        for (int i = 0; i < length; i++) {
            while (true) {
                loopCnt++;
                int ran = NumberUtils.getRandomInt(1, TOTAL_IMAGES);
                // check trùng nhau tối đa 10000 lần thôi! Nếu sau 10000 lần gen số ngẫu nhiên
                // mà vẫn gen phải số có rồi thì đành chọn nó :v
                if (!isExist[ran] || loopCnt == 10000) {
                    isExist[ran] = true;
                    strs[i] = IMAGE_FOLDER + ran + ".jpg";
                    break;
                }
            }

        }
        return strs;
    }

    /**
     * Inner class: Product of Nen Mong Viet Nam
     */
    @Getter
    @Setter
    private class Product {
        private int id;
        private String name;
        private String description;
        private int categoryId;
        private String slug;
        private String sku;
        private String heroImg;
        private String[] images;
        private long price;
        private long originalPrice;
        private int quantity;
        private String color;
        private Meta meta;

        public void setMeta(String description) {
            this.meta = new Meta(description);
        }

        @Getter
        @Setter
        private class Meta {
            private String description;

            public Meta(String description) {
                this.description = description;
            }
        }
    }
}

package hello.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.common.Result;
import hello.config.aop.RequireNonsense;
import hello.utils.NumUtils;
import hello.utils.StrUtils;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/public-api")
public class PublicController {

    static final String[] COLORS =
            {"Light Blue", "Black", "White", "Yellow", "Viole", "Green", "Red"};
    static final String IMAGE_FOLDER = "https://ducdongyyen.com/nmvn/images/test/";
    static final int TOTAL_IMAGES = 30;
    static final int[] SALE_PERCENTAGES = {5, 10, 15, 20, 30, 50};

    @GetMapping("hello-world")
    public Result helloWorld() {
        return new Result().successRes("Hello world!");
    }

    @GetMapping("goodbye-world")
    @RequireNonsense(prefix = "demo_prefix")
    public Result goodbyeWorld() {
        return new Result().successRes("Goodbye world!");
    }

    @GetMapping("say-haha")
    @RequireNonsense
    public Result sayHaha() {
        return new Result().successRes("Hahaha haha ha!!!");
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
                name = StrUtils.getRandomFullname();
                if (!nameSet.contains(name)) {
                    nameSet.add(name);
                    break;
                }
            }
            p.setName(name);
            p.setDescription(StrUtils.getRandomLorem(NumUtils.getRandomInt(32, 45)));
            p.setCategoryId(NumUtils.getRandomInt(1, 5));
            p.setSlug(p.getName().toLowerCase().replaceAll(" ", "-"));
            p.setSku(p.getSlug());
            p.setHeroImg(IMAGE_FOLDER + NumUtils.getRandomInt(1, TOTAL_IMAGES) + ".jpg");
            p.setImages(getRandomImageArray(NumUtils.getRandomInt(4, 10)));
            p.setPrice(NumUtils.getRandomInt(1000, 20000) * 1000);
            p.setOriginalPrice(p.getPrice());   // 30% sản phẩm sẽ ko được sale
            if (NumUtils.getRandomInt(1, 100) > 70) {
                // Chỉ có 70% sản phẩm được sale
                salePc = NumUtils.getRandomInArray(SALE_PERCENTAGES);
                System.out.println("i = " + i + ", salePc = " + salePc);
                originalPrice = (int) ((p.getPrice() / 1000) / (1 - salePc * 1.0 / 100)) * 1000;
                System.out.println("Sale off " + (100 - p.getPrice() * 100 / originalPrice) + "%");
                p.setOriginalPrice(originalPrice);
            }
            p.setQuantity(NumUtils.getRandomInt(10, 100));
            p.setColor(StrUtils.getRandomInArray(COLORS));
            p.setMeta(StrUtils.getRandomLorem(NumUtils.getRandomInt(12, 20)));

            products.add(p);
        }

        return products;
    }

    private static String[] getRandomImageArray(int length) {
        String[] strs = new String[length];
        boolean[] isExist = new boolean[TOTAL_IMAGES + 1];
        int loopCnt = 0;

        for (int i = 0; i < length; i++) {
            while (true) {
                loopCnt++;
                int ran = NumUtils.getRandomInt(1, TOTAL_IMAGES);
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

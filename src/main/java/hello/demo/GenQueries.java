package hello.demo;

import java.util.Random;

public class GenQueries {

    public static void main(String[] args) {
        Random rd = new Random();
        for (int i = 1; i <= 500; i++) {
            System.out.println("UPDATE `springboot_tech`.`staff` SET `is_alive` = '" + rd.nextInt(2)
                    + "' WHERE (`id` = '" + i + "');");
        }
    }
}

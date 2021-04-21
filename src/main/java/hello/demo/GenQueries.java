package hello.demo;

public class GenQueries {

    public static void main(String[] args) {
        java.util.Random rd = new java.util.Random();
        for (int i = 501; i <= 600; i++) {
            System.out.println("UPDATE `springboot_tech`.`staff` SET `is_alive` = '" + rd.nextInt(2)
                    + "' WHERE (`id` = '" + i + "');");
        }
    }
}

package hello.genquery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import hello.utils.NumberUtils;
import hello.utils.StrUtils;

/**
 * Not done yet!
 * 
 * @author anhtu
 */
public class GenService {

    public static void genQuery(String db, String table, List<String> columns, List<Object> values,
            int initID,
            String fileOutPath)
            throws IOException {

        final int TOTAL_ROWS = 1000000;
        final int CHUNK = 1000; // number of rows to insert each query

        String insertQuery =
                String.format("INSERT INTO `%s`.`%s` (", db, table);
        for (String col : columns) {
            insertQuery += String.format("`%s`", col);
        }

        // KHÔNG thể dùng forEach để cộng chuỗi, bởi vì biến dùng bên trong forEach phải là final,
        // tức là bên trong forEach ko thay đổi giá trị của biến bên ngoài được.
        // Cũng dễ hiểu, vì nó là stream, nên nếu 1 đứa thay đổi thì đứa khác sao đọc được
        // columns.stream().forEach(item -> {
        // insertQuery += String.format("`%s`", item);
        // });
        insertQuery += columns.stream().reduce((a, b) -> {
            return "`" + a + "`, `" + b + "`";
        }).get();
        insertQuery += ") VALUES ";

        String sql;
        File fout = new File(fileOutPath);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String createdDate = sdf.format(cal.getTime());

        int totalQuery = (int) Math.ceil(TOTAL_ROWS * 1.0 / CHUNK);
        for (int i = 0; i < totalQuery; i++) {
            sql = insertQuery;
            bw.write(sql);
            bw.newLine();

            int start = i * CHUNK;
            int end = i < totalQuery - 1 ? (i + 1) * CHUNK : TOTAL_ROWS;

            // System.out.printf("i=%d, start=%d, end=%d\n", i, start, end);
            for (int j = start; j < end; j++) {
                // How to handle these dynamic data? Using @FunctionalInterface?
                String title = StrUtils.getRandomLorem(NumberUtils.getRandomInt(2, 7));
                String author = StrUtils.getRandomFullname();
                int price = NumberUtils.getRandomInt(20, 500) * 1000;
                sql = String.format("('%s', '%s', '%s', '%s', '%s', '%s')",
                        initID + j, title, author, price, createdDate, createdDate);
                if (j < end - 1)
                    sql += ",";
                else
                    sql += ";";
                bw.write(sql);
                bw.newLine();
            }
        }


        bw.close();
        fos.close();
        System.out.println("Generate book query done!");
    }

}

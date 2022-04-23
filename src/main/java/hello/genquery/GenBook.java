package hello.genquery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import hello.utils.NumberUtils;
import hello.utils.StrUtils;

/*
 * Gen query to insert testing data for `book` table in database test
 * Note: must use database `sbt_test`.
 * 
 * After running this class, a SQL file will be generated, go to that folder
 * (ex: D:/ProgramData/sbt/sql/book.sql), open terminal, then run the following cmd:
 * mysql -u root -p sbt_test < book.sql
 * 
 * Enter password to generate data.
 * Note: if it returns the following error:
 * ERROR 2006 (HY000) at line 1: MySQL server has gone away
 * 
 * Then you should change the config max_allowed_packet > file size, ref:
 * https://stackoverflow.com/a/12792977/7688028
 */
public class GenBook {

    public static void main(String[] args) throws IOException {
        final int INIT_ID = 123;
        final int TOTAL_ROWS = 100;// 1000000;
        final int CHUNK = 10;// 1000; // number of rows to insert each query

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String createdDate = sdf.format(cal.getTime());

        String insertQuery =
                "INSERT INTO `sbt_test`.`book` (`id`, `title`, `author`, `price`, `created_date`, `modified_date`) VALUES ";
        String sql;
        File fout = new File("D:/ProgramData/sbt/sql/book.sql");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        int totalQuery = (int) Math.ceil(TOTAL_ROWS * 1.0 / CHUNK);
        for (int i = 0; i < totalQuery; i++) {
            sql = insertQuery;
            bw.write(sql);
            bw.newLine();

            int start = i * CHUNK;
            int end = i < totalQuery - 1 ? (i + 1) * CHUNK : TOTAL_ROWS;

            // System.out.printf("i=%d, start=%d, end=%d\n", i, start, end);
            for (int j = start; j < end; j++) {
                String title = StrUtils.getRandomLorem(NumberUtils.getRandomInt(2, 7));
                String author = StrUtils.getRandomFullname();
                int price = NumberUtils.getRandomInt(20, 500) * 1000;
                sql = String.format("('%s', '%s', '%s', '%s', '%s', '%s')",
                        INIT_ID + j, title, author, price, createdDate, createdDate);
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

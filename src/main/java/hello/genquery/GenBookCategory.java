package hello.genquery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;
import hello.utils.NumberUtils;

/*
 * Note: book_id phải tồn tại, nếu ko sẽ lỗi FK. Do đó phải gen data cho table book trước, rồi mới
 * tới table này. ID của table book phải liên tiếp thì mới ko lỗi (do bên này cũng gen book_id là
 * các số tự nhiên liên tiếp)
 * 
 * Sau khi run có chút nhận xét sau:
 * - Do bảng này có 2 FK (tham chiếu tới 2 bảng book và category) nêu việc insert data chậm hơn
 * nhiều so với bảng book
 * - Có thể chỉ cần dùng 1 câu query (CHUNK = TOTAL_ROWS) với nhiều VALUES, nhưng nếu sao 1 VALUES
 * thì toàn bộ data trước đó cũng ko được insert, mà trong trường hợp này cần insert bao nhiêu cũng
 * được, sai thì bỏ record sai thôi
 * - CHUNK càng to thì run càng nhanh, nếu CHUNK = 1 (mỗi 1 câu query chỉ insert 1 row) thì việc run
 * sẽ rất chậm
 */
public class GenBookCategory {

    public static void main(String[] args) throws IOException {
        final int INIT_BOOK_ID = 114;
        final int TOTAL_ROWS = 1000000;
        final int CHUNK = 1000; // number of rows to insert each query

        String insertQuery =
                "INSERT INTO `sbt_test`.`book_category` (`book_id`, `category_id`, `vote_count`) VALUES ";
        String sql;
        File fout = new File("D:/ProgramData/sbt/sql/book_category.sql");
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
                Set<Integer> cateSet =
                        NumberUtils.getRandomSetInt(NumberUtils.getRandomInt(1, 4), 1, 14);
                sql = "";
                int cnt = 0;
                for (Integer cate : cateSet) {
                    sql += String.format("('%s', '%s', '%s')",
                            INIT_BOOK_ID + j, cate, NumberUtils.getRandomInt(100, 1000));
                    cnt++;
                    if (cnt < cateSet.size())
                        sql += ",";
                }

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
        System.out.println("Generate book_category query done!");
    }

}

package hello.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadTextFromFile {

    public static void writeExcel(List<String> headerList, List<List<String>> contentList)
            throws IOException {
        if (headerList.size() != 5) {
            System.out.println("Header size must be 5");
            return;
        }

        System.out.println("Creating excel file...");
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("Persons");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            /****** Header ******/
            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            // headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            // headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            // font.setFontHeightInPoints((short) 14);
            font.setBold(true);
            headerStyle.setFont(font);

            // Header content
            for (int i = 0; i < headerList.size(); i++) {
                Cell headerCell = header.createCell(i);
                headerCell.setCellValue(headerList.get(i));
                headerCell.setCellStyle(headerStyle);
            }

            /****** Body ******/
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            for (int i = 0; i < contentList.get(0).size(); i++) {
                Row row = sheet.createRow(i + 1);

                for (int j = 0; j < headerList.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(contentList.get(j).get(i));
                    cell.setCellStyle(style);
                }
            }

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "kwfinder-do-dong.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

        System.out.println("Done!");
    }

    public static void main(String[] args) {
        try {
            File myObj = new File(System.getProperty("user.dir") + "/text/kwfinder-do-dong.txt");
            Scanner sc = new Scanner(myObj);
            List<String> headerList = Arrays.asList("Từ khóa", "Lượt search/tháng", "CPC", "PPC", "Độ khó SEO");
            List<String> kwList = new ArrayList<>();
            List<String> searchList = new ArrayList<>();
            List<String> cpcList = new ArrayList<>();
            List<String> ppcList = new ArrayList<>();
            List<String> kdList = new ArrayList<>();
            List<List<String>> contentList = new ArrayList<>();

            int lineIndex = 1;
            int remainder;
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                // System.out.println(lineIndex + ": " + data);
                remainder = lineIndex % 7; // Mỗi block có 7 dòng
                switch (remainder) {
                    case 1:
                        kwList.add(data);
                        break;
                    case 3:
                        searchList.add(data);
                        break;
                    case 4:
                        cpcList.add(data);
                        break;
                    case 5:
                        ppcList.add(data);
                        break;
                    case 6:
                        kdList.add(data);
                        break;
                    default:
                        break;
                }

                lineIndex++;
            }

            contentList.add(kwList);
            contentList.add(searchList);
            contentList.add(cpcList);
            contentList.add(ppcList);
            contentList.add(kdList);

            writeExcel(headerList, contentList);

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

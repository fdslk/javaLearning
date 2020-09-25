package com.fang.common;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: Files
 * @Package
 * @Description: TODO
 * @date
 */
public class Files {
    /**
     * 读取excel
     * @param dir
     * @param file
     * @param sheetName
     * @return
     */
    public static ArrayList<HashMap<String, String>> readExcel(String dir, String file, String sheetName) throws Exception {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        if(file.endsWith(".xlsx")){
                XSSFWorkbook book = new XSSFWorkbook(new File(dir + File.separator + file));
                XSSFSheet sheet = book.getSheet(sheetName);
                HashMap<String, String> hm;
                Row titles = sheet.getRow(0);
                if(sheet.getLastRowNum() != 0){
                    Row row;
                    for (int i = 1; i < sheet.getLastRowNum() + 1; i ++) {
                        hm = new HashMap<String, String>();
                        row = sheet.getRow(i);
                        for(int j = 0; j < titles.getLastCellNum(); j++){
                            hm.put(titles.getCell(j).getStringCellValue(),
                                    row.getCell(j).getStringCellValue());
                        }
                        result.add(hm);
                    }
                }else{
                    hm = new HashMap<String, String>();
                    for(int i = 0; i < titles.getLastCellNum(); i ++){
                        hm.put(titles.getCell(i).getStringCellValue(), "");
                    }
                    result.add(hm);
                }
                book.close();
        }else{
            HSSFWorkbook book = new HSSFWorkbook(new POIFSFileSystem((new File(dir + File.separator +file))));
            HSSFSheet sheet = book.getSheet(sheetName);
            HashMap<String, String> hm;
            Row titles = sheet.getRow(0);
            if(sheet.getLastRowNum() != 0){
                Row row;
                for(int i = 0; i < titles.getLastCellNum() + 1; i ++){
                    hm = new HashMap<String, String>();
                    row = sheet.getRow(i);
                    for(int j = 0; j < titles.getLastCellNum(); j ++){
                        hm.put(titles.getCell(j).getStringCellValue(),
                                row.getCell(j).getStringCellValue());
                    }
                    result.add(hm);
                }
            }else{
                hm = new HashMap<String, String>();
                for(int i = 0; i < titles.getLastCellNum(); i ++){
                    hm.put(titles.getCell(i).getStringCellValue(), "");
                }
                result.add(hm);
            }
            book.close();
        }
        return result;
    }
}

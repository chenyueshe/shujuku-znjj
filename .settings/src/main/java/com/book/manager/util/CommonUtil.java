package com.book.manager.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * @author jy
 * @Date 2019/3/24 12:20
 */
public class CommonUtil {
	private static String s36bit_const = "0123456789abcdefghijklmnopqrstuvwxyz";
    
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
			'Z', '+', '-', };

	/***
     * @author wsl
     * @date 2019/4/26 17:40
     * @param inputStream
     * @return java.util.List<java.lang.Object>
     * @desc   使用poi从流中读取excel数据
     */
    public static List<Object> readChannelExcel2(InputStream inputStream) {
        List<Object> result = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        try {
            XSSFWorkbook hssfWorkBook = new XSSFWorkbook(inputStream);
            Sheet sheet = hssfWorkBook.getSheetAt(0);//first sheet
            int rowSize = sheet.getPhysicalNumberOfRows();
            int clumSize = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int r = 0; r < rowSize; r++) {
                Row row = sheet.getRow(r);
                List<Object> rowList = new ArrayList<>();
                for (int i = 0; i < clumSize; i++) {
                    Cell cell = row.getCell(i);
//                    HSSFDateUtil.isCellDateFormatted(cell);
//                    cell.getDateCellValue();
                    rowList.add(formatter.formatCellValue(cell));
                }
                result.add(rowList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static String RandomChar(String str,int many) {
    	char [] chs=str.toCharArray();
    	String resultString="";
    	for(int i=0;i<many;i++) {
    		Random random=new Random();
    		resultString+=chs[random.nextInt(str.length())];
    	}
    	return resultString.toUpperCase();
    }
    public static String removeAllBlank(String s){
        String result = "";
        if(null!=s && !"".equals(s)){
            result = s.replaceAll("[　*| *| *|//s*]*", "");
        }
        return result;
    }
    private static String to32String(BigInteger i) {
        BigDecimal divide = new BigDecimal(32);
        BigDecimal decimal = new BigDecimal(i);
        String res = "";
        while (BigDecimal.ZERO.compareTo(decimal) != 0){
            BigDecimal[] divRes = decimal.divideAndRemainder(divide);
            decimal = divRes[0];
            res =  s36bit_const.charAt(divRes[1].intValue()) + res;
        }
        return res;
    }
    private static String CompressNumber(long number) {
		char[] buf = new char[64];
		int charPos = 64;
		int radix = 1 << 6;
		long mask = radix - 1;
		do {
			buf[--charPos] = digits[(int) (number & mask)];
			number >>>= 6;
		} while (number != 0);
		return new String(buf, charPos, (64 - charPos));
	}
    
    public static String covertCityName(String cityId) {
    	Map<String,String>cityMap=new HashMap<String, String>();
    	cityMap.put("399", "全区");
    	cityMap.put("470", "呼伦贝尔市");
    	cityMap.put("471", "呼和浩特市");
    	cityMap.put("472", "包头市");
    	cityMap.put("473", "乌海市");
    	cityMap.put("474", "乌兰察布市");
    	cityMap.put("475", "通辽市");
    	cityMap.put("476", "赤峰市");
    	cityMap.put("477", "鄂尔多斯市");
    	cityMap.put("478", "巴彦淖尔市");
    	cityMap.put("479", "锡林郭勒盟");
    	cityMap.put("482", "兴安盟");
    	cityMap.put("483", "阿拉善盟");
    	return cityMap.get(cityId);
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
          int number=random.nextInt(62);
          sb.append(str.charAt(number));
        }
        return sb.toString();
    }
   public static void main(String[]args) throws ParseException {
	   
   }
}



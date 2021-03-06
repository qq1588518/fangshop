package com.fang.fangshop.core.common;
import java.io.File;  
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;  
import java.net.URL;  
import java.util.ArrayList;  
import java.util.Calendar;  
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.CellType;  
import jxl.Workbook;  
import jxl.format.Alignment;  
import jxl.format.Border;  
import jxl.format.BorderLineStyle;  
import jxl.format.Colour;  
import jxl.format.ScriptStyle;  
import jxl.format.UnderlineStyle;  
import jxl.format.VerticalAlignment;  
import jxl.read.biff.BiffException;  
import jxl.write.Blank;  
import jxl.write.DateFormat;  
import jxl.write.DateFormats;  
import jxl.write.DateTime;  
import jxl.write.Formula;  
import jxl.write.Label;  
import jxl.write.Number;  
import jxl.write.NumberFormat;  
import jxl.write.WritableCell;  
import jxl.write.WritableCellFeatures;  
import jxl.write.WritableCellFormat;  
import jxl.write.WritableFont;  
import jxl.write.WritableHyperlink;  
import jxl.write.WritableImage;  
import jxl.write.WritableSheet;  
import jxl.write.WritableWorkbook;  
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException; 

public class ExportExcelUtil {
	public final static String exportExcel(HttpServletResponse response, String fileName, String[] Title,
			List<Object> listContent) {
		String result = "系统提示：Excel文件导出成功！";
		// 以下开始输出到EXCEL
		try {
			
			response.reset();// 清空输出流
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/vnd.ms-excel;charset=utf-8");// 定义输出类型
			OutputStream os = response.getOutputStream();// 取得输出流
			// 定义输出流，以便打开保存对话框_______________________end
			
			File file1 = new File("E:\\订单.xls");

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL第一行列标题********************* */
			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
			}
			/** ***************以下是EXCEL正文数据********************* */
			Field[] fields = null;
			int i = 1;
			for (Object obj : listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					sheet.addCell(new Label(j, i, va.toString(), wcf_left));
					j++;
				}
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();
		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) throws BiffException, IOException, RowsExceededException, WriteException {
		    
	}

}

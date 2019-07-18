package com.mch.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiUtil {
	protected static final Log logger = LogFactory.getLog(PoiUtil.class);

	public static void main(String[] args) throws IOException {
		List<PoiModel> poiModels = new ArrayList<PoiModel>();
		for (int x = 0; x < 30; x++) {
			PoiModel poiModel = new PoiModel();
			poiModel.setNeedBackGroundColor(true);
//			poiModel.setTitle("管家测试" + x);
			poiModel.setColumnTitles(new String[] { "领队名称", "商品名称", "商品编号", "商品数量" });
			poiModel.setColumnWidths(new int[] { 10, 11, 5, 3 });

			List<String[]> columnModels = new ArrayList<String[]>();
			for (int i = 0; i < 100; i++) {
				columnModels.add(new String[] { "哈哈", "哈呵呵哈", "好啊好啊", "你哈" });
			}

			poiModel.setColumns(columnModels);

			columnModels = new ArrayList<String[]>();
			for (int i = 0; i < 3; i++) {
				columnModels.add(new String[] { "", "", "", "今好" });
			}

			poiModel.setFootColumns(columnModels);
			poiModels.add(poiModel);
		}

		// Workbook wb = xlsx ? new XSSFWorkbook() : new HSSFWorkbook();

		// Workbook wb = xlsx ? new XSSFWorkbook() : new HSSFWorkbook();

		writeExcel(poiModels, "test", new XSSFWorkbook());
	}

	public static void writeExcel(List<PoiModel> poiModels, String filePath, Workbook wb) throws IOException {
		buildExcel(poiModels, wb);
		// Write the output to a file
		String file = filePath + ".xls";
		if (wb instanceof XSSFWorkbook) {
			file += "x";
		}
		System.out.println("gen excel：" + new File(file).getAbsolutePath());
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

	public static void buildExcel(List<PoiModel> poiModels, Workbook wb) {
		Map<String, CellStyle> styles = createStyles(wb);
		for (int i = 0, size = poiModels.size(); i < size; i++) {
			PoiModel poiModel = poiModels.get(i);
			if (!poiModel.check()) {
				throw new RuntimeException("出错了" + poiModel.getTitle());
			}
			
			String sheetName = poiModel.getTitle();
			Sheet sheet = wb.createSheet(null != sheetName ? sheetName:"sheet" + i);
			// turn off gridlines
			sheet.setDisplayGridlines(true);
			sheet.setPrintGridlines(true);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);

			// the following three statements are required only for HSSF
			sheet.setAutobreaks(true);
			printSetup.setFitHeight((short) 1);
			printSetup.setFitWidth((short) 1);

			int columnCount = poiModel.getColumnTitles().length;

			int rowIndex = 0;
			if (!StringUtils.isEmpty(poiModel.getTitle())) {
				Row headerRow = sheet.createRow(rowIndex);
				headerRow.setHeightInPoints(28);
				// 标题合并单元格
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));
				Cell titleCell = headerRow.createCell(0);
				titleCell.setCellValue(poiModel.getTitle());
				titleCell.setCellStyle(styles.get("title"));
				
				rowIndex++;
			}
			
			Row columnTitleRow = sheet.createRow(rowIndex);
			rowIndex++;
			for (int j = 0; j < columnCount; j++) {
				sheet.setColumnWidth(j, poiModel.getColumnWidths()[j] * 512);
				Cell columnTitleCell = columnTitleRow.createCell(j);
				columnTitleCell.setCellValue(poiModel.getColumnTitles()[j]);
				columnTitleCell.setCellStyle(styles.get("titlecell"));
			}

			List<String[]> columns = poiModel.getColumns();
			int rowCount = columns.size();
			for (int m = 0; m < rowCount; m++) {
				Row cellRow = sheet.createRow(m + rowIndex);
				String[] values = columns.get(m);
				for (int n = 0; n < columnCount; n++) {
					Cell columnTitleCell = cellRow.createCell(n);
					if (poiModel.getColumnWidths()[n] < 8) {
						if (m % 2 == 0 && poiModel.isNeedBackGroundColor()) {
							columnTitleCell.setCellStyle(styles.get("titlecell_bg"));
						} else {
							columnTitleCell.setCellStyle(styles.get("titlecell"));
						}
					} else {
						if (m % 2 == 0 && poiModel.isNeedBackGroundColor()) {
							columnTitleCell.setCellStyle(styles.get("cell_bg"));
						} else {
							columnTitleCell.setCellStyle(styles.get("cell"));
						}
					}
					columnTitleCell.setCellValue(values[n]);
				}
			}

			int footRowCount = 0;
			List<String[]> footColumns = poiModel.getFootColumns();
			if (footColumns != null && !footColumns.isEmpty()) {
				footRowCount = footColumns.size();
				for (int m = 0; m < footRowCount; m++) {
					Row cellRow = sheet.createRow(m + rowCount + rowIndex);
					String[] values = footColumns.get(m);
					for (int n = 0; n < columnCount; n++) {
						Cell columnTitleCell = cellRow.createCell(n);
						if (poiModel.getColumnWidths()[n] < 8) {
							columnTitleCell.setCellStyle(styles.get("titlecell"));
						} else {
							columnTitleCell.setCellStyle(styles.get("cell"));
						}
						columnTitleCell.setCellValue(values[n]);
					}
				}
			}

			try {
				// 增加边框
				CellRangeAddress region = new CellRangeAddress(0, rowIndex + rowCount + footRowCount - 1, 0, columnCount - 1);
				RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, region, sheet, wb);
				RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, region, sheet, wb);
				RegionUtil.setBorderRight(CellStyle.BORDER_THIN, region, sheet, wb);
				RegionUtil.setBorderTop(CellStyle.BORDER_THIN, region, sheet, wb);
			} catch (Exception e) {
				logger.error("加边框失败:" + e.getMessage());
			}
		}
	}

	private static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		Font cellFont = wb.createFont();
		
		cellFont.setFontHeightInPoints((short) 12);

		style = wb.createCellStyle();
		style.setFont(cellFont);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("titlecell", style);

		style = wb.createCellStyle();
		style.setFont(cellFont);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styles.put("titlecell_bg", style);
		
		Font font = wb.createFont();
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font);
		styles.put("cell", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(cellFont);
		styles.put("cell_bg", style);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return styles;
	}
}

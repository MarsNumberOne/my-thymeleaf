package com.mch.poi;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PoiModel {
	private String title;
	protected final Log logger = LogFactory.getLog(PoiModel.class);
	private String[] columnTitles;
	private int[] columnWidths;
	private List<String[]> columns;
	private List<String[]> footColumns;
	private boolean needBackGroundColor;
	
	public boolean check() {
		int size = columnTitles.length;
		if (columnWidths.length != size) {
			logger.error("columnTitles长度:"+columnTitles.length+" ,columnWidths.length:"+columnWidths.length);
			return false;
		}
		for (int i = 0, c = columns.size(); i < c; i++) {
			String[] v = columns.get(i);
			if (v.length != size) {
				logger.error("column"+v.length+",columnTitles长度="+size);
				return false;
			}
		}
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getColumnTitles() {
		return columnTitles;
	}

	public void setColumnTitles(String[] columnTitles) {
		this.columnTitles = columnTitles;
	}

	public int[] getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(int[] columnWidths) {
		this.columnWidths = columnWidths;
	}

	public List<String[]> getColumns() {
		return columns;
	}

	public void setColumns(List<String[]> columns) {
		this.columns = columns;
	}

	public List<String[]> getFootColumns() {
		return footColumns;
	}

	public void setFootColumns(List<String[]> footColumns) {
		this.footColumns = footColumns;
	}

	public boolean isNeedBackGroundColor() {
		return needBackGroundColor;
	}

	public void setNeedBackGroundColor(boolean needBackGroundColor) {
		this.needBackGroundColor = needBackGroundColor;
	}

}

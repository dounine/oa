package net.yasion.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.apache.struts.upload.FormFile;

public class ExcelUtil {
	private String extName = null;

	private Workbook innerWorkbook = null;

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public Workbook getInnerWorkbook() {
		return innerWorkbook;
	}

	public void setInnerWorkbook(Workbook innerWorkbook) {
		this.innerWorkbook = innerWorkbook;
	}

	public boolean createWorkBook() {
		innerWorkbook = new HSSFWorkbook();
		extName = ".xls";
		return innerWorkbook != null ? true : false;
	}

	public boolean createExtendWorkBook() {
		try {
			innerWorkbook = new XSSFWorkbook();
			extName = ".xlsx";
		} catch (Exception e) {
			extName = ".xls";
			innerWorkbook = new HSSFWorkbook();
		}
		return innerWorkbook != null ? true : false;
	}

	public boolean createSheet(String sheetName) {
		if (innerWorkbook != null) {
			Sheet sheet = null;

			if (innerWorkbook instanceof HSSFWorkbook) {
				sheet = ((HSSFWorkbook) innerWorkbook).createSheet(sheetName);
			} else if (innerWorkbook instanceof XSSFWorkbook) {
				sheet = ((XSSFWorkbook) innerWorkbook).createSheet(sheetName);
			} else {
				sheet = ((SXSSFWorkbook) innerWorkbook).createSheet(sheetName);
			}

			return sheet != null ? true : false;
		}

		return false;
	}

	public boolean createRow(int sheetIndex, int rowIndex) {
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount) && (rowIndex >= 0 && rowIndex <= 65535)) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				Row row = null;

				if (sheet instanceof HSSFSheet) {
					row = ((HSSFSheet) sheet).createRow(rowIndex);
				} else if (sheet instanceof XSSFSheet) {
					row = ((XSSFSheet) sheet).createRow(rowIndex);
				} else {
					row = sheet.createRow(rowIndex);
				}

				return row != null ? true : false;
			}
		}

		return false;
	}

	public boolean createCell(int sheetIndex, int rowIndex, int cellIndex) {
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount) && (rowIndex >= 0 && rowIndex <= 65535)) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				int rowCount = sheet.getLastRowNum();

				if (rowCount > 0 && (rowIndex >= 0 && rowIndex < rowCount) && (cellIndex >= 0 && cellIndex <= 65535)) {
					Row row = sheet.getRow(rowIndex);
					Cell cell = null;

					if (row instanceof HSSFRow) {
						cell = ((HSSFRow) row).createCell(rowIndex);
					} else if (row instanceof XSSFRow) {
						cell = ((XSSFRow) row).createCell(rowIndex);
					} else {
						cell = row.createCell(rowIndex);
					}

					return cell != null ? true : false;
				}
			}
		}

		return false;
	}

	public boolean addRowData(Object[] data, int sheetIndex, int rowIndex, boolean autoWidth, boolean autoBorder) {
		if (innerWorkbook != null && innerWorkbook.getNumberOfSheets() > 0) {
			CellStyle style = innerWorkbook.createCellStyle();
			style.setBorderBottom((short) 1);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);
			if (sheetIndex >= 0 && sheetIndex < innerWorkbook.getNumberOfSheets()) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);

				if (rowIndex >= 0 && rowIndex <= 65535) {
					if (sheet instanceof HSSFSheet) {
						sheet.createRow(rowIndex);
						for (int i = 0; data != null && i < data.length; i++) {
							appendCellData(data[i], sheetIndex, rowIndex, autoWidth, autoBorder);
						}

						return true;
					} else if (sheet instanceof XSSFSheet) {
						sheet.createRow(rowIndex);
						for (int i = 0; data != null && i < data.length; i++) {
							appendCellData(data[i], sheetIndex, rowIndex, autoWidth, autoBorder);
						}

						return true;
					} else {
						sheet.createRow(rowIndex);
						for (int i = 0; data != null && i < data.length; i++) {
							appendCellData(data[i], sheetIndex, rowIndex, autoWidth, autoBorder);
						}

						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean appendRowData(Object[] data, int sheetIndex, boolean autoWidth, boolean autoBorder) {
		if (innerWorkbook != null && innerWorkbook.getNumberOfSheets() > 0) {
			if (sheetIndex >= 0 && sheetIndex < innerWorkbook.getNumberOfSheets()) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				int rowCount = sheet.getPhysicalNumberOfRows();

				return this.addRowData(data, sheetIndex, rowCount, autoWidth, autoBorder);
			}
		}

		return false;
	}

	public boolean addCellData(Object data, int sheetIndex, int rowIndex, int columnIndex, boolean autoWidth, boolean autoBorder) {
		if (innerWorkbook != null && innerWorkbook.getNumberOfSheets() > 0) {
			CellStyle style = innerWorkbook.createCellStyle();
			style.setBorderBottom((short) 1);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);
			if (sheetIndex >= 0 && sheetIndex < innerWorkbook.getNumberOfSheets()) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);

				if (rowIndex >= 0 && (rowIndex <= sheet.getLastRowNum())) {
					if (sheet instanceof HSSFSheet) {
						HSSFRow row = ((HSSFSheet) sheet).getRow(rowIndex);

						if (columnIndex >= 0 && columnIndex <= 65535) {

							HSSFCell cell = row.createCell(columnIndex);

							if (autoWidth && data != null) {
								int width = this.getStringWidthWithColumn(data.toString());
								int nowWidth = sheet.getColumnWidth(columnIndex);

								if (nowWidth < width) {
									sheet.setColumnWidth(columnIndex, width);
								}
							}

							this.setCellData(cell, data);

							if (autoBorder) {
								cell.setCellStyle(style);
							}

							return true;
						}

					} else if (sheet instanceof XSSFSheet) {
						XSSFRow row = ((XSSFSheet) sheet).getRow(rowIndex);

						if (columnIndex >= 0 && columnIndex <= 65535) {
							XSSFCell cell = row.createCell(columnIndex);
							if (autoWidth) {
								int width = this.getStringWidthWithColumn(data.toString());
								int nowWidth = sheet.getColumnWidth(columnIndex);

								if (nowWidth < width) {
									sheet.setColumnWidth(columnIndex, width);
								}
							}

							this.setCellData(cell, data);

							if (autoBorder) {
								cell.setCellStyle(style);
							}

							return true;
						}
					} else {
						Row row = sheet.getRow(rowIndex);

						if (columnIndex >= 0 && columnIndex <= 65535) {
							Cell cell = row.createCell(columnIndex);

							if (autoWidth) {
								int width = this.getStringWidthWithColumn(data.toString());
								int nowWidth = sheet.getColumnWidth(columnIndex);

								if (nowWidth < width) {
									sheet.setColumnWidth(columnIndex, width);
								}
							}

							this.setCellData(cell, data);

							if (autoBorder) {
								cell.setCellStyle(style);
							}

							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean appendCellData(Object data, int sheetIndex, int rowIndex, boolean autoWidth, boolean autoBorder) {
		if (innerWorkbook != null && innerWorkbook.getNumberOfSheets() > 0) {
			if (sheetIndex >= 0 && sheetIndex < innerWorkbook.getNumberOfSheets()) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				if (rowIndex >= 0 && rowIndex <= sheet.getLastRowNum()) {
					Row row = sheet.getRow(rowIndex);
					int cellCount = row.getPhysicalNumberOfCells();

					return this.addCellData(data, sheetIndex, rowIndex, cellCount, autoWidth, autoBorder);
				}
			}
		}

		return false;
	}

	public boolean setMergedRegion(int sheetIndex, CellRangeAddress region) {
		if (innerWorkbook != null && innerWorkbook.getNumberOfSheets() > 0) {
			if (sheetIndex >= 0 && sheetIndex < innerWorkbook.getNumberOfSheets()) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				sheet.addMergedRegion(region);

				return true;
			}
		}

		return false;
	}

	public CellStyle createCellStyle() {
		return this.innerWorkbook.createCellStyle();
	}

	public Font createFont() {
		return this.innerWorkbook.createFont();
	}

	public boolean setCellStyle(CellStyle style, int sheetIndex, int rowIndex, int cellIndex) {
		if (innerWorkbook != null && innerWorkbook.getNumberOfSheets() > 0) {
			if (sheetIndex >= 0 && sheetIndex < innerWorkbook.getNumberOfSheets()) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				int rowCount = sheet.getPhysicalNumberOfRows();

				if (rowCount > 0 && (rowIndex >= 0 && rowIndex <= 65535)) {
					Row row = sheet.getRow(rowIndex);
					int cellCount = row.getPhysicalNumberOfCells();

					if (cellCount > 0 && (cellIndex >= 0 && cellIndex <= 65535)) {
						Cell cell = row.getCell(cellIndex);
						cell.setCellStyle(style);

						return true;
					}
				}
			}
		}

		return false;
	}

	public void setCellData(Cell cell, Object data) {
		if (data != null) {
			if (data instanceof Integer) {
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.valueOf(data.toString()));
			} else if (data instanceof Double) {
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Double.valueOf(data.toString()));
			} else if (data instanceof Boolean) {
				cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
				cell.setCellValue(Boolean.valueOf(data.toString()));
			} else if (data instanceof Date) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue((Date) data);
			} else if (data instanceof Calendar) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue((Calendar) data);
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(String.valueOf(data));
			}
		} else {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(String.valueOf(""));
		}
	}

	public void addRegionData(int sheetIndex, CellRangeAddress region, CellStyle cellStyle, int cellType, Object data) {
		this.addRegionData(sheetIndex, region, cellStyle, cellType, data, false);
	}

	public void addRegionData(int sheetIndex, CellRangeAddress region, CellStyle cellStyle, int cellType, Object data, boolean autoWidth) {
		if (innerWorkbook != null && innerWorkbook.getNumberOfSheets() > 0) {
			if (sheetIndex >= 0 && sheetIndex < innerWorkbook.getNumberOfSheets()) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						row = sheet.createRow(i);
					}
					for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							cell = row.createCell(j);
						}

						if (autoWidth && data != null) {
							int width = this.getStringWidthWithColumn(data.toString());
							int nowWidth = sheet.getColumnWidth(region.getFirstColumn());

							if (nowWidth < width) {
								sheet.setColumnWidth(region.getFirstColumn(), width);
							}
						}

						if (i == region.getFirstRow() && j == region.getFirstColumn()) {
							this.setCellData(cell, data);
						}
						cell.setCellStyle(cellStyle);
					}
				}

			}
		}
	}

	public boolean innerWorkBookWrite(String path) throws IOException {
		if (innerWorkbook != null && path != null) {
			File file = new File(path);
			if (!file.exists()) {
				String[] paths = path.split("\\\\");
				String newPath = path.substring(0, path.length() - paths[paths.length - 1].length());
				File fileFolder = new File(newPath);
				fileFolder.mkdirs();
				file.createNewFile();
			}
			FileOutputStream output = new FileOutputStream(path);
			innerWorkbook.write(output);
			output.close();

			return true;
		}
		return false;
	}

	public void innerWorkBookColse() {
		innerWorkbook = null;
	}

	public int getStringWidthWithColumn(String str) {
		/*
		 * in units of 1/256th of a character width
		 */
		return str != null ? str.getBytes().length * 256 : 0;
	}

	public CellRangeAddress getCellRangeAddress(int rowStart, int rowEnd, int colStart, int colEnd) {
		return new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
	}

	// /**
	// * 从FormFile中获取excel数据
	// *
	// * @param formfile
	// * 表单文件域对象
	// * @param startline
	// * 开始读取行数
	// * @return 返回所有excel数据，返回实例ArrayList-ArrayList-Object[]
	// * @throws IOException
	// */
	// public List<?> getFormFileExcelData(FormFile formfile, int startline)
	// throws Exception {
	// List<?> dataList = new ArrayList<Object>();
	// InputStream inputStream = null;
	//
	// if (formfile != null) {
	// try {
	// inputStream = formfile.getInputStream();
	// // 先用excel2003的方法 读取数据
	// dataList = this.readExcelData(inputStream, startline);
	// } catch (Exception e) {
	// if (inputStream != null) {
	// inputStream.close();
	// }
	//
	// inputStream = formfile.getInputStream();
	// // 如果出现异常， 则用excel2007-2010的方法 读取数据
	// dataList = this.readExtendExcelData(inputStream, startline);
	// } finally {
	// if (inputStream != null) {
	// inputStream.close();
	// }
	// }
	// }
	// return dataList;
	// }

	/**
	 * 从指定路径中获取excel数据
	 * 
	 * @param inputStream
	 *            inputStream
	 * @param startline
	 *            开始读取行数
	 * @return 返回所有excel数据，返回实例ArrayList-ArrayList-Object[]
	 * @throws IOException
	 */
	public List<?> getInputStreamExcelData(InputStream inputStream, int startline) throws Exception {
		List<?> dataList = new ArrayList<Object>();
		if (inputStream != null) {
			byte[] data = getDataByInputStream(inputStream);
			try {
				inputStream = new ByteArrayInputStream(data);
				// 先用excel2003的方法 读取数据
				dataList = this.readExcelData(inputStream, startline);
			} catch (Exception e) {
				inputStream = new ByteArrayInputStream(data);

				// 如果出现异常， 则用excel2007-2010的方法 读取数据
				dataList = this.readExtendExcelData(inputStream, startline);
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return dataList;
	}

	protected byte[] getDataByInputStream(InputStream inputStream) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] bs = new byte[1024];
			int i = 0;
			while ((i = inputStream.read(bs)) != -1) {
				bos.write(bs, 0, i);
			}
			byte[] data = bos.toByteArray();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从指定路径中获取excel数据
	 * 
	 * @param filePath
	 *            excel文件路径
	 * @param startline
	 *            开始读取行数
	 * @return 返回所有excel数据，返回实例ArrayList-ArrayList-Object[]
	 * @throws IOException
	 */
	public List<?> getFileExcelData(String filePath, int startline) throws Exception {
		List<?> dataList = new ArrayList<Object>();
		InputStream inputStream = null;

		if (filePath != null) {
			try {
				inputStream = new FileInputStream(filePath);
				// 先用excel2003的方法 读取数据
				dataList = this.readExcelData(inputStream, startline);
			} catch (Exception e) {
				if (inputStream != null) {
					inputStream.close();
				}

				inputStream = new FileInputStream(filePath);
				// 如果出现异常， 则用excel2007-2010的方法 读取数据
				dataList = this.readExtendExcelData(inputStream, startline);
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}

		return dataList;
	}

	/**
	 * 读取excel数据 适用excel2007-2010
	 * 
	 * @param workBook
	 *            指定的工作簿对象
	 * @param startLine
	 *            开始读取行数
	 * @return 返回所有excel数据，返回实例ArrayList-ArrayList-Object[]
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	protected List<?> readExtendExcelData(InputStream inputStream, int startLine) throws IOException, InvalidFormatException {
		XSSFWorkbook workBook = new XSSFWorkbook(inputStream); // excel2007要用XSSF读取
		this.setInnerWorkbook(workBook);
		ArrayList<ArrayList<Object[]>> dataList = new ArrayList<ArrayList<Object[]>>();
		startLine = (startLine >= 0 ? startLine : 0);

		for (int sheetIndex = 0; sheetIndex < workBook.getNumberOfSheets(); sheetIndex++) // 遍历工作簿中所有的Sheet
		{
			ArrayList<Object[]> sheetData = new ArrayList<Object[]>();
			XSSFSheet sheet = (XSSFSheet) workBook.getSheetAt(sheetIndex);

			for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) // 遍历Sheet中所有有数据的Row
			{
				if (rowIndex >= startLine) {
					XSSFRow row = sheet.getRow(rowIndex);

					if (row != null) {
						short cellNum = row.getLastCellNum();

						if (cellNum >= 0) {
							Object[] rowData = new Object[cellNum];

							for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {// 遍历Row中所有的Cell
								XSSFCell cell = row.getCell((short) cellIndex);
								rowData[cellIndex] = this.getCellData(cell);
							}
							sheetData.add(rowData);
						}
					}
				}
			}
			dataList.add(sheetData);
		}
		return dataList;
	}

	/**
	 * 读取excel数据 适用excel2003
	 * 
	 * @param inputstream
	 *            数据输入流
	 * @param startLine
	 *            开始读取行数
	 * @return 返回所有excel数据，返回实例ArrayList-ArrayList-Object[]
	 * @throws IOException
	 */
	protected List<?> readExcelData(InputStream inputstream, int startLine) throws IOException {
		ArrayList<ArrayList<Object[]>> dataList = new ArrayList<ArrayList<Object[]>>();
		HSSFWorkbook workBook = new HSSFWorkbook(inputstream); // excel2003要用HSSF读取
		this.setInnerWorkbook(workBook);
		startLine = (startLine >= 0 ? startLine : 0);

		for (int sheetIndex = 0; sheetIndex < workBook.getNumberOfSheets(); sheetIndex++) {
			ArrayList<Object[]> sheetData = new ArrayList<Object[]>();
			HSSFSheet sheet = workBook.getSheetAt(sheetIndex);

			for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				if (rowIndex >= startLine) {
					HSSFRow row = sheet.getRow(rowIndex);

					if (row != null) {
						short cellNum = row.getLastCellNum();

						if (cellNum >= 0) {
							Object[] rowData = new Object[cellNum];

							for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
								HSSFCell cell = row.getCell(cellIndex);
								rowData[cellIndex] = this.getCellData(cell);
							}
							sheetData.add(rowData);
						}
					}
				}
			}
			dataList.add(sheetData);
		}
		return dataList;
	}

	public Object getCellData(Cell cell) {
		Object output = new Object();
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: {// CELL_TYPE_NUMERIC 数值型 0
				Double doubleValue = new Double(cell.getNumericCellValue());

				if (DateUtil.isCellDateFormatted(cell)) {
					output = new java.sql.Date(DateUtil.getJavaDate(doubleValue).getTime());
					return output;
				}

				Integer intValue = new Integer(doubleValue.intValue());

				if (intValue.doubleValue() == doubleValue.doubleValue()) {
					output = intValue;
					return output;
				}

				output = doubleValue;
				break;
			}
			case Cell.CELL_TYPE_STRING: {// CELL_TYPE_STRING 字符串型 1
				output = cell.getRichStringCellValue().getString();
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {// CELL_TYPE_FORMULA 公式型 2
				output = cell.getCellFormula();
				break;
			}
			case Cell.CELL_TYPE_BLANK: {// CELL_TYPE_BLANK 空值 3
				output = null;
				break;
			}
			case Cell.CELL_TYPE_BOOLEAN: { // CELL_TYPE_BOOLEAN 布尔型 4
				output = new Boolean(cell.getBooleanCellValue());
				break;
			}
			case Cell.CELL_TYPE_ERROR: {// CELL_TYPE_ERROR 错误 5
				output = new Byte(cell.getErrorCellValue());
				break;
			}
			default: {
				output = null;
				break;
			}
			}
		} else {
			output = null;
		}
		return output;
	}

	public boolean isExistSheet(int sheetIndex) {
		boolean result = false;
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount)) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				result = (sheet != null ? true : false);
			}
		}
		return result;
	}

	public Sheet getSheet(int sheetIndex) {
		Sheet result = null;
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount)) {
				result = innerWorkbook.getSheetAt(sheetIndex);
			}
		}
		return result;
	}

	public boolean isExistRow(int sheetIndex, int rowIndex) {
		boolean result = false;
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount) && (rowIndex >= 0 && rowIndex <= 65535)) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				int rowCount = sheet.getLastRowNum();
				if (rowCount > 0 && (rowIndex >= 0 && rowIndex <= rowCount)) {
					Row row = sheet.getRow(rowIndex);
					result = (row != null ? true : false);
				}
			}
		}
		return result;
	}

	public Row getRow(int sheetIndex, int rowIndex) {
		Row result = null;
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount) && (rowIndex >= 0 && rowIndex <= 65535)) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				int rowCount = sheet.getLastRowNum();
				if (rowCount > 0 && (rowIndex >= 0 && rowIndex <= rowCount)) {
					result = sheet.getRow(rowIndex);
				}
			}
		}
		return result;
	}

	public boolean isExistCell(int sheetIndex, int rowIndex, int cellIndex) {
		boolean result = false;
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount) && (rowIndex >= 0 && rowIndex <= 65535)) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				int rowCount = sheet.getLastRowNum();
				if (rowCount > 0 && (rowIndex >= 0 && rowIndex <= rowCount) && (cellIndex >= 0 && cellIndex <= 65535)) {
					Row row = sheet.getRow(rowIndex);
					int cellCount = row.getLastCellNum();
					if (cellCount > 0 && (cellIndex >= 0 && cellIndex <= cellCount)) {
						Cell cell = row.getCell(cellIndex);
						result = (cell != null ? true : false);
					}
				}
			}
		}
		return result;
	}

	public Cell getCell(int sheetIndex, int rowIndex, int cellIndex) {
		Cell result = null;
		if (innerWorkbook != null) {
			int sheetCount = innerWorkbook.getNumberOfSheets();
			if (sheetCount > 0 && (sheetIndex >= 0 && sheetIndex < sheetCount) && (rowIndex >= 0 && rowIndex <= 65535)) {
				Sheet sheet = innerWorkbook.getSheetAt(sheetIndex);
				int rowCount = sheet.getLastRowNum();
				if (rowCount > 0 && (rowIndex >= 0 && rowIndex <= rowCount) && (cellIndex >= 0 && cellIndex <= 65535)) {
					Row row = sheet.getRow(rowIndex);
					int cellCount = row.getLastCellNum();
					if (cellCount > 0 && (cellIndex >= 0 && cellIndex <= cellCount)) {
						result = row.getCell(cellIndex);
					}
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// Set<TbWorkProject> workProjectList = new HashSet<TbWorkProject>();
		// TbWorkProject workProject = new TbWorkProject();
		// Set<TbWorkTask> workTaskList = new HashSet<TbWorkTask>();
		// TbWorkTask workTask = new TbWorkTask();
		// Set<TbWorkAbility> workAbilityList = new HashSet<TbWorkAbility>();
		// TbWorkAbility workAbility = new TbWorkAbility();
		try {
			ExcelUtil excelutil = new ExcelUtil();
			List<?> list = excelutil.getFileExcelData(args[0], 4);
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				if (obj instanceof ArrayList) {
					ArrayList<?> sheet = (ArrayList<?>) (obj);
					for (int j = 0; j < sheet.size(); j++) {
						Object obj2 = sheet.get(j);
						if (obj2 instanceof Object[]) {
							Object[] objs = (Object[]) obj2;
							System.out.println(Arrays.toString(objs));
							// if (objs.length > 5
							// || (objs[4] != null && objs[5] != null)) {// 判断这行数据是否有效
							// // 判断行的工作项目单元数据是否有效 有效则添加记录数据
							// if (objs[0] != null && objs[1] != null) {
							// workProject = new TbWorkProject();
							// workProject.setCode((String) objs[0]);
							// workProject.setName((String) objs[1]);
							// workProjectList.add(workProject);
							// if (workTaskList.size() > 0) {
							// workProject
							// .setTbWorkTasks(workTaskList);
							// workTaskList.clear();
							// }
							// }
							// // 判断行的工作任务单元数据是否有效 有效则添加记录数据
							// if (objs[2] != null && objs[3] != null) {
							// workTask = new TbWorkTask();
							// workTask.setCode((String) objs[2]);
							// workTask.setName((String) objs[3]);
							// workTaskList.add(workTask);
							// if (workAbilityList.size() > 0) {
							// workTask.setTbWorkAbilitys(workAbilityList);
							// workAbilityList.clear();
							// }
							// }
							// // 记录技能并添加到列表
							// TbWorkAbility workAbility = new TbWorkAbility();
							// workAbility.setCode((String) objs[4]);
							// workAbility.setName((String) objs[5]);
							// workAbilityList.add(workAbility);
							// }
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}
}
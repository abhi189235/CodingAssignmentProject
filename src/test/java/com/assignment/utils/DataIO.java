package com.assignment.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataIO {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	// This is used to read a value of specific property from specified file
	public String getValuePropertiesFile(String fileName, String property) {

		String pathDataFolder = System.getProperty("user.dir") + "\\resources\\testData\\" + fileName + ".properties";
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(pathDataFolder);
			prop.load(fis);
			String valueOfGivenProperty = prop.getProperty(property);
			return valueOfGivenProperty;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// This is used to load the excel file from the given path
	public void loadExcelFile(String folderName, String fileName) {
		String excelPath = System.getProperty("user.dir") + "\\resources\\templates\\" + folderName + "\\" + fileName
				+ ".xlsx";
		this.path = excelPath;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * This method will fetch the value from the given column and row from the excel
	 * sheet
	 */
	public String getCellDataExcel(String sheetName, String colName, int rowNum) {

		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";

			if (cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();

			else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType().BLANK != null)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
		
	}

	
	/*
	 * This method will set the value in the given column and row of the excel sheet
	 * with the given data, and will return true in return else false
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String typeData) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			if (typeData.equalsIgnoreCase("string")) {
				cell.setCellType(CellType.STRING);
				cell.setCellValue(data);
			}

			if (typeData.equalsIgnoreCase("double") || typeData.equalsIgnoreCase("float")) {
				cell.setCellType(CellType.NUMERIC);
				cell.setCellValue(Float.parseFloat(data));
			}

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();
			

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// This is to handle the Page Object Excel sheet
	public ArrayList<String> getPageObjectFromExcel(String fileName, String objectName) throws Exception {
		loadExcelFile("PageObjects", fileName);
		ArrayList<String> elements = new ArrayList<String>();

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			if (row.getCell(0).toString().equalsIgnoreCase(objectName)) {
				elements.add(row.getCell(0).toString());
				elements.add(row.getCell(1).toString());
				elements.add(row.getCell(2).toString());
				break;
			}
		}
		workbook.close();
		return elements;
	}

	// This method will re-evaluate all the formulas and update the values in excel
	public void reEvaluateExcelFormulas() {

		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		for (Sheet sheet : workbook) {
			for (Row r : sheet) {
				for (Cell c : r) {
					if (c.getCellType() == CellType.FORMULA) {
						evaluator.evaluateFormulaCell(c);
					}
				}
			}
		}

	}
}

package parseFiletoExcel;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FileUtils;

public class AssessmentQ1 {
	static List<String> testDataLines = new ArrayList<String>();
	static String[] transactionDetails = null;
	static List<String> keyValuePairs = new ArrayList<String>();
	static Map<String, String> transactionRecords = new LinkedHashMap<String, String>();

	public static String fileToString() throws IOException {
		File file = new File("C:\\Discount Central\\Automation\\TestDatatoExcel\\testData\\q1.test_data");
		String finalcontent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		return finalcontent;

	}

	public static List<String> fetchKeyValuePairsasList(List<String> testData) {

		for (String str : testData) {
			if (str.contains("\"")) {
				keyValuePairs.add(str);
			}
		}

		return keyValuePairs;
	}

	public static List<String> convertStringtoList(String testData) {

		List<String> eachTransaction = new ArrayList<String>(Arrays.asList(testData.split("\n")));
		return eachTransaction;
	}

	public static void ExcelUtils(Map<String, String> transacMap, int transactionNumber) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("Transaction");
		String fileSuffix = new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date());
		Row header = spreadsheet.createRow(0);
		header.createCell(0).setCellValue("Key");
		header.createCell(1).setCellValue("Value");
		int row = 1;
		for (String key : transacMap.keySet()) {
			Row newRow = spreadsheet.createRow(row);
			String value = transacMap.get(key);
			newRow.createCell(0).setCellValue(key);
			newRow.createCell(1).setCellValue(value);
			row++;

		}
		FileOutputStream out = new FileOutputStream(
				new File("C:\\Discount Central\\Automation\\TestDatatoExcel\\generatedExcelFiles\\TransactionDetails-"
						+ (transactionNumber + 1) + " - " + fileSuffix + ".xlsx"));
		workbook.write(out);
		out.close();
		System.out.println("File Created Successfully !! ");
		System.out.println(
				"Please check on following  path : C:\\Discount Central\\Automation\\TestDatatoExcel\\generatedExcelFiles");

	}

	public static void parseTransactionDetails(String[] testData) throws IOException {
		List<String> eachTransaction;
		List<String> keyValuePairsIdentify;
		Map<String, String> transactionRecordsMap;
		for (int i = 0; i < testData.length; i++) {
			eachTransaction = convertStringtoList(testData[i]);
			keyValuePairsIdentify = fetchKeyValuePairsasList(eachTransaction);
			transactionRecordsMap = convertListtoMap(keyValuePairsIdentify);
			System.out.println(transactionRecordsMap);
			ExcelUtils(transactionRecordsMap, i);

		}

	}

	public static Map<String, String> convertListtoMap(List<String> keyValuePairs) {

		for (String str : keyValuePairs) {
			str = str.trim();
			transactionRecords.put(str.split("\"")[0].trim(), str.split("\"")[1]);
		}
		return transactionRecords;
	}

	

	public static String[] getTransactionDetails(String fileContent) {
		
		String[] transactions = fileContent.split("]]SZ");
		System.out.println("Transaction Size: " + transactions.length);
		return transactions;

	}
   
	public static void main(String args[]) throws IOException{
		transactionDetails = getTransactionDetails(fileToString());
		parseTransactionDetails(transactionDetails);

	}
}

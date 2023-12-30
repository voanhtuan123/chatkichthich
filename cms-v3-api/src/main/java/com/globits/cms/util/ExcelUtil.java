package com.globits.cms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.globits.cms.dto.CmsArticleDto;
@Component
public class ExcelUtil {
	public static void main(String[] Args) {
		
	}
	
	public static List<CmsArticleDto> importCmsArticle(InputStream is) throws IOException {
		List<CmsArticleDto> ret = new ArrayList<CmsArticleDto>();
		 

		/* try { */
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(is);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		int rowIndex = 1;
		int falseIndex = 1;
		int num = datatypeSheet.getLastRowNum();
		while (rowIndex <= num) {
			System.out.println(rowIndex);
			Row currentRow = datatypeSheet.getRow(rowIndex);
			Cell currentCell = null;
			if (currentRow != null) {
				CmsArticleDto dto = new CmsArticleDto();
				//title
				Integer index = 0;
				if (index != null) {
					currentCell = currentRow.getCell(index);
//					currentCell.equals("#REF!");
					if(currentCell != null) {
						String code = currentCell.toString();
						if(code.equals("#REF!")) {
							rowIndex++;
							continue;
						}
					}
					String title="";
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						title = String.valueOf(currentCell.getNumericCellValue());
						
					} else if (currentCell != null && currentCell.getStringCellValue() != null 
							&& currentCell.getCellTypeEnum() == CellType.STRING) {
						title = currentCell.getStringCellValue().trim();
					} else {
						rowIndex++;
						continue;
					}
					if(title!=null && title.length()>0) {
						dto.setTitle(title);
					}
					else {
						rowIndex++;
						continue;
					}					
				}
				//content
				index = 1;
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if(currentCell != null) {
						String content = currentCell.toString();
						if(content.equals("#REF!")) {
							rowIndex++;
							continue;
						}
					}
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String content = String.valueOf(currentCell.getNumericCellValue());
						dto.setContent(content);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String content = currentCell.getStringCellValue().trim();
						dto.setContent(content);
					}
				}
				//titleImageUrl
				index = 4;
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if(currentCell != null) {
						String titleImageUrl = currentCell.toString();
						if(titleImageUrl.equals("#REF!")) {
							rowIndex++;
							continue;
						}
					}
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String titleImageUrl = String.valueOf(currentCell.getNumericCellValue());
						dto.setTitleImageUrl(titleImageUrl);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String titleImageUrl = currentCell.getStringCellValue().trim();
						dto.setTitleImageUrl(titleImageUrl);
					}
				}
				//language
				index = 3;
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if(currentCell != null) {
						String language = currentCell.toString();
						if(language.equals("#REF!")) {
							rowIndex++;
							continue;
						}
					}
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String language = String.valueOf(currentCell.getNumericCellValue());
						dto.setLanguage(language);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String language = currentCell.getStringCellValue().trim();
						dto.setLanguage(language);
					}
				}
				//summary
				index = 2;
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if(currentCell != null) {
						String summary = currentCell.toString();
						if(summary.equals("#REF!")) {
							rowIndex++;
							continue;
						}
					}
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String summary = String.valueOf(currentCell.getNumericCellValue());
						dto.setSummary(summary);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String summary = currentCell.getStringCellValue().trim();
						dto.setSummary(summary);
					}
				}
				//primaryCategoryCode
				index = 5;
				if (index != null) {
					currentCell = currentRow.getCell(index);
					if(currentCell != null) {
						String primaryCategoryCode = currentCell.toString();
						if(primaryCategoryCode.equals("#REF!")) {
							rowIndex++;
							continue;
						}
					}
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						String primaryCategoryCode = String.valueOf(currentCell.getNumericCellValue());
						dto.setPrimaryCategoryCode(primaryCategoryCode);
					} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING
							&& currentCell.getStringCellValue() != null) {
						String primaryCategoryCode = currentCell.getStringCellValue().trim();
						dto.setPrimaryCategoryCode(primaryCategoryCode);
					}
				}
				
				ret.add(dto);
			}
			rowIndex++;
		}
		return ret;
		/*
		 * } catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); } return null;
		 */
	}
	
	
}

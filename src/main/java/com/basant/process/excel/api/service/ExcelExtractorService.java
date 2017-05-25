package com.basant.process.excel.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basant.process.excel.api.dao.EmployeeDao;
import com.basant.process.excel.api.pojo.Employee;
import com.basant.process.excel.api.pojo.EmployeeModel;

@Service
public class ExcelExtractorService {
	@Autowired
	private EmployeeDao employeeDao;
	
	@SuppressWarnings("resource")
	public List<Employee> excelToJavaBean(String xlxsFilePath)
			throws IOException {
		List<Employee> employees = new ArrayList<>();
		Employee employee = null;
		XSSFWorkbook workBook = new XSSFWorkbook(xlxsFilePath);
		XSSFSheet sheet = workBook.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("Total Row find :" + totalRows);
		Row row;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = (Row) sheet.getRow(i);

			int id = (int) row.getCell(0).getNumericCellValue();
			String name = row.getCell(1).getStringCellValue();
			String dept = row.getCell(2).getStringCellValue();
			double salary = row.getCell(3).getNumericCellValue();
			employee = new Employee(id, name, dept, salary);
			employees.add(employee);
		}
		//here i am mapping DTO to Model class
		List<EmployeeModel> model=dtoMapToModel(employees);
		//Now i will iterate and save by calling persistence layer
		//Before save please add a check to avoid duplicate
		employeeDao.save(model);
		
		return  employees.stream().sorted(new Comparator<Employee>() {

			@Override
			public int compare(Employee e1, Employee e2) {
				return e1.getId()-e2.getId();
			}
		}).collect(Collectors.toList());
	}

	// call this method inside above at end then iterate one by one object and
	// save it to DB by calling your DAO
	private List<EmployeeModel> dtoMapToModel(List<Employee> employees) {
		List<EmployeeModel> employeeModels = new ArrayList<>();
		employees.stream().forEach(
				employee -> employeeModels.add(new EmployeeModel(employee
						.getId(), employee.getName(), employee.getDept(),
						employee.getSalary())));
		return employeeModels;
	}

}

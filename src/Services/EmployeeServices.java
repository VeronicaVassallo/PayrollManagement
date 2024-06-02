package Services;

import java.util.ArrayList;
import java.util.HashMap;

import models.Employee;
import models.Employee.JobTitle;

public class EmployeeServices {
    private static 	JobTitle convertStringToEnum(String jbTitle)
    {
        if(jbTitle.equals("JUNIOR")){
            return Employee.JobTitle.JUNIOR;
        }else if(jbTitle.equals("MIDDLE")){
            return Employee.JobTitle.MIDDLE;
        }else{
            return Employee.JobTitle.SENIOR;
        }
    }

    //Function returns me the list of all employees
    public static HashMap<String, Employee> populateListEmployees()
    {
        ArrayList<String[]> data = CommonServices.getData("C:\\Users\\danid\\eclipse-workspace\\Payroll_Management\\src\\Data\\Employees.csv");
		//employee list
		HashMap<String, Employee> listEmployees = new HashMap<>();
		for (String[] record : data) {
			Employee newEmployee = new Employee(record[0], 
            record[1], 
            record[2], 
            EmployeeServices.convertStringToEnum(record[3]), 
            CommonServices.convertStringToDate(record[4], "dd/MM/yyyy") ,
            CommonServices.convertStringToDate(record[5], "dd/MM/yyyy"),
            CommonServices.convertStringToDate(record[6], "dd/MM/yyyy"),
            CommonServices.convertStringToDate(record[7], "dd/MM/yyyy"));
			listEmployees.put(newEmployee.getIDCode(), newEmployee);
		}
		   
		return listEmployees;
    }

    // Function that returns me a specific Employee based on the ID code
    public static Employee getEmployer(HashMap<String, Employee> listEmployee, String IDCode){
        Employee specificEmployee;
        specificEmployee = listEmployee.get(IDCode);
        return specificEmployee;
    }
   
}


package PayrollManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Services.ClockingServices;
import Services.EmployeeServices;
import Services.PaySlipServices;
import models.Clocking;
import models.Employee;

public class Main {
    public static HashMap<String, Employee> listEmployee = EmployeeServices.populateListEmployees();
    public static ArrayList<Clocking> listClocking = ClockingServices.populateListClockings();

	// Percentages based on the type of day(if a Saturday, Sunday, etc..)
	private static double ORDINARY_PAY = 0;
    private static double SATURDAY_PAY = 0.33;
    private static double SUNDAY_PAY = 0.50;
    private static double OVERTIME_PAY = 0.20;
    private static double HOLIDAY_PAY = 0.50;

    public static void main(String[] args) {
        for (Map.Entry<String, Employee> entry : listEmployee.entrySet()) {
            Employee employee = entry.getValue();
            ArrayList<Clocking> employeeClockings = ClockingServices.getListClockingEmployee(listClocking, employee.getIDCode());
			//Hours work
            long totalOrdinaryHours = sumHours(ClockingServices.getDayWorkingOrdinaryHours(employeeClockings));
            long totalSaturdaysHours = sumHours(ClockingServices.getSaturdayHours(employeeClockings));
            long totalSundayHours = sumHours(ClockingServices.getSundayHours(employeeClockings));
            long totalVacationDayHours = sumHours(ClockingServices.getListVacationDay(employeeClockings));
            long totalSickDayHours = sumHours(ClockingServices.getListSickDay(employeeClockings));
            long totalPublicHolidaysHours = sumHours(ClockingServices.getListHoursPublicHolidays(employeeClockings));
            long totalOvertimeHours = sumHours(ClockingServices.getListOvertimeHours(employeeClockings, employee));
			//Total payment for the differt Hours work
            double totalOrdinaryPay = PaySlipServices.calculatePay(totalOrdinaryHours, employee.getJobTitle(), ORDINARY_PAY);
            double totalSaturdayPay = PaySlipServices.calculatePay(totalSaturdaysHours, employee.getJobTitle(), SATURDAY_PAY);
            double totalSundayPay = PaySlipServices.calculatePay(totalSundayHours, employee.getJobTitle(), SUNDAY_PAY);
            double totalVacationDayPay = PaySlipServices.calculatePay(totalVacationDayHours, employee.getJobTitle(), ORDINARY_PAY);
            double totalSickDayPay = PaySlipServices.calculatePay(totalSickDayHours, employee.getJobTitle(), ORDINARY_PAY);
            double totalPublicHolidaysPay = PaySlipServices.calculatePay(totalPublicHolidaysHours, employee.getJobTitle(), HOLIDAY_PAY);
            double totalOvertimePay = PaySlipServices.calculatePay(totalOvertimeHours, employee.getJobTitle(), OVERTIME_PAY);
			//Final Total payment
            double finalTotal = totalOrdinaryPay + totalSaturdayPay + totalSundayPay + totalVacationDayPay + totalSickDayPay + totalPublicHolidaysPay + totalOvertimePay;

            PaySlipServices.createCsv(
                employee.getIDCode(), 
                employee.getNameEmployee(), 
                employee.getSurnameEmployee(), 
                totalOrdinaryHours, 
                totalSaturdaysHours, 
                totalSundayHours, 
                totalVacationDayHours, 
                totalSickDayHours, 
                totalPublicHolidaysHours, 
                totalOvertimeHours, 
                totalOrdinaryPay, 
                totalSaturdayPay, 
                totalSundayPay, 
                totalVacationDayPay, 
                totalSickDayPay, 
                totalPublicHolidaysPay, 
                totalOvertimePay, 
                finalTotal
            );
        }
    }

    private static long sumHours(ArrayList<Long> hoursList) {
        return ClockingServices.sumTotalHours(hoursList);
    }
}

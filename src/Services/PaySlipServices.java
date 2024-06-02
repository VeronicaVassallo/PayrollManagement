package Services;
import models.PaySlip;
import models.Employee.JobTitle;

import java.io.FileWriter;
import java.io.IOException;

public class PaySlipServices {

    // Function that calculates pay
    public static double calculatePay(long totalHoursEmployee, JobTitle seniority, double basisValue){
     double totalHours = (double) totalHoursEmployee;
     double total = 0;
     if(seniority == JobTitle.SENIOR){
          double basis = totalHours * 25;
          total = basis + (basis * basisValue);
     }else if(seniority == JobTitle.MIDDLE){
          double basis = totalHours * 20;
          total = basis + (basis * basisValue);
     }else{
          double basis = totalHours * 15;
          total = basis + (basis * basisValue);
     }
     return total;
    }

  
    // Function to create a single CSV file with employee data
    public static void createCsv(
            String IDCode,
            String nameEmployee,
            String surnameEmployee,
            long totalOrdinaryHours,
            long totalSaturdaysHours,
            long totalSundayHours,
            long totalVacationDayHours,
            long totalSickDayHours,
            long totalPublicHolidaysHours,
            long totalOvertimeHours,
            double totalOrdinaryPay,
            double totalSaturdayPay,
            double totalSundayPay,
            double totalVacationDayPay,
            double totalSickDayPay,
            double totalPublicHolidaysPay,
            double totalOvertimePay,
            double finalTotal) {

        String csvPath = "C:\\Users\\danid\\eclipse-workspace\\PayrollManagement\\src\\Data\\" + IDCode + ".csv";

        try (FileWriter writer = new FileWriter(csvPath)) {
            // Header of PaySlip
            PaySlip headerPaySlip = new PaySlip(
                    "ID code",
                    "Name",
                    "Surname",
                    "Total ordinary hours",
                    "Total Saturdays hours",
                    "Total Sundays hours",
                    "Total vacation hours",
                    "Total sick hours",
                    "Total public holidays hours",
                    "Total overtime hours",
                    "Total ordinary pay",
                    "Total Saturdays pay",
                    "Total Sundays pay",
                    "Total vacation pay",
                    "Total sick pay: ",
                    "Total public holidays pay",
                    "Total overtime pay",
                    "Final total");

            // New PaySlip
            PaySlip newPaySlip = new PaySlip(
                    IDCode,
                    nameEmployee,
                    surnameEmployee,
                    Long.toString(totalOrdinaryHours),
                    Long.toString(totalSaturdaysHours),
                    Long.toString(totalSundayHours),
                    Long.toString(totalVacationDayHours),
                    Long.toString(totalSickDayHours),
                    Long.toString(totalPublicHolidaysHours),
                    Long.toString(totalOvertimeHours),
                    Double.toString(totalOrdinaryPay),
                    Double.toString(totalSaturdayPay),
                    Double.toString(totalSundayPay),
                    Double.toString(totalVacationDayPay),
                    Double.toString(totalSickDayPay),
                    Double.toString(totalPublicHolidaysPay),
                    Double.toString(totalOvertimePay),
                    Double.toString(finalTotal));

            writer.append(headerPaySlip.toCSVString());
            writer.append(newPaySlip.toCSVString());
            System.out.println("CSV file created successfully");

        } catch (IOException e) {
            System.out.println("Error durring creating CSV file: " + e.getMessage());
        }
    }

}


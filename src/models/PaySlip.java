package models;

public class PaySlip {
    String IDCode;
    String nameEmployee;
    String surnameEmployee;
    String totalOrdinaryHours;
    String totalSaturdaysHours;
    String totalSundayHours;
    String totalVacationDayHours;
    String totalSickDayHours;
    String totalPublicHolidaysHours;
    String totalOvertimeHours;
    String totalOrdinaryPay;
    String totalSaturdayPay;
    String totalSundayPay;
    String totalVacationDayPay;
    String totalSickDayPay;
    String totalPublicHolidaysPay;
    String totalOvertimePay;
    String finalTotal;
 
     

    public PaySlip
    (
        String IDCode,
        String nameEmployee,
        String surnameEmployee,
        String totalOrdinaryHours,
        String totalSaturdaysHours,
        String totalSundayHours,
        String totalVacationDayHours,
        String totalSickDayHours,
        String totalPublicHolidaysHours,
        String totalOvertimeHours,
        String totalOrdinaryPay,
        String totalSaturdayPay,
        String totalSundayPay,
        String totalVacationDayPay,
        String totalSickDayPay,
        String totalPublicHolidaysPay,
        String totalOvertimePay,
        String finalTotal
    ){
        this.IDCode = IDCode;
        this.nameEmployee = nameEmployee;
        this.surnameEmployee = surnameEmployee;
        this.totalOrdinaryHours = totalOrdinaryHours;
        this.totalSaturdaysHours = totalSaturdaysHours;
        this.totalSundayHours = totalSundayHours;
        this.totalVacationDayHours = totalVacationDayHours;
        this.totalSickDayHours = totalSickDayHours;
        this.totalPublicHolidaysHours = totalPublicHolidaysHours;
        this.totalOvertimeHours = totalOvertimeHours;
        this.totalOrdinaryPay = totalOrdinaryPay;
        this.totalSaturdayPay = totalSaturdayPay;
        this.totalSundayPay = totalSundayPay;
        this.totalVacationDayPay = totalVacationDayPay;
        this.totalSickDayPay = totalSickDayPay;
        this.totalPublicHolidaysPay = totalPublicHolidaysPay;
        this.totalOvertimePay = totalOvertimePay;
        this.finalTotal = finalTotal;

    }

    public String toCSVString() {
        return String.join(";", 
            IDCode,
            nameEmployee,
            surnameEmployee,
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
        ) + "\n";
    }

    

}


package models;

import java.util.Date;

public class Employee {
	private String nameEmployee;
	private String surnameEmployee;
	private String IDCode;
	private JobTitle jobTitle; 
	private Date assumptionDate;
	private Date workEndDate;
	private Date overtimeStartDate;
	private Date overtimeEndDate;

	
	public enum JobTitle {
		SENIOR,
		MIDDLE,
		JUNIOR
	}

	public Employee(String nameEmployee, String surnameEmployee, String IDCode, JobTitle jobTitle, Date assumptionDate, Date workEndDate, Date overtimeStartDate, Date overtimeEndDate) {
		this.nameEmployee = nameEmployee;
		this.surnameEmployee = surnameEmployee;
		this.IDCode = IDCode;
		this.jobTitle = jobTitle;
		this.assumptionDate = assumptionDate;
		this.workEndDate = workEndDate;
		this.overtimeStartDate = overtimeStartDate;
		this.overtimeEndDate = overtimeEndDate;
	}

	public String getNameEmployee() {
		return this.nameEmployee;
	}
	public String getSurnameEmployee() {
		return this.surnameEmployee;
	}
	public String getIDCode() {
		return this.IDCode;
	}
	public JobTitle getJobTitle() {
        return this.jobTitle;
    }

    public Date getAssumptionDate() {
        return this.assumptionDate;
    }

    public Date getWorkEndDate() {
        return this.workEndDate;
    }

    public Date getOvertimeStartDate() {
        return this.overtimeStartDate;
    }

    public Date getOvertimeEndDate() {
        return this.overtimeEndDate;
    }
}


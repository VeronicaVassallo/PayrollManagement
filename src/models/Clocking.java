package models;

import java.util.Date;

public class Clocking {
  private Employee idCodeEmployee;
  private Date clockingIn;
  private Date clockingOut;
  private boolean sickDay;
  private boolean vacationDay;
  private boolean overtimeDay;
  
  public Clocking(Employee idCodeEmployee, Date clockingIn,  Date clockingOut, boolean sickDay, boolean vacationDay, boolean overtimeDay ) {
	  this.idCodeEmployee = idCodeEmployee;
	  this.clockingIn = clockingIn;
	  this.clockingOut = clockingOut;
	  this.sickDay = sickDay;
	  this.vacationDay = vacationDay;
    this.overtimeDay = overtimeDay;
  }
  
  public Employee getidCodeEmployee() {
	  return this.idCodeEmployee;
  }
  
  public Date getClockingIn() {
	  return this.clockingIn;
  }
  
  public Date getClockingOut() {
	  return this.clockingOut;
  }
  
  public boolean getSickDay() {
	  return this.sickDay;
  }
  
  public boolean getVacationDay() {
	  return this.vacationDay;
  }

  public boolean getOvertimeDay(){
    return this.overtimeDay;
  }
}

package Services;

import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import PayrollManagement.Main;
import models.Clocking;
import models.Employee;

public class ClockingServices {

	private static final Set<LocalDate> HOLIDAYS = Set.of(
        LocalDate.of(0, 1, 1),  // New Year's Day
        LocalDate.of(0, 1, 6),  // Epiphany
        LocalDate.of(0, 4, 25), // Liberation Day
        LocalDate.of(0, 5, 1),  // Workers Day
        LocalDate.of(0, 6, 2),  // Republic Day
        LocalDate.of(0, 8, 15), // Assumption Day
        LocalDate.of(0, 11, 1), // All Saints' Day
        LocalDate.of(0, 12, 8), // Immaculate Conception
        LocalDate.of(0, 12, 25),// Christmas Day
        LocalDate.of(0, 12, 26) // St Stephen's Day
    );

     // Function that returns the list of all the Clockings
    public static ArrayList<Clocking> populateListClockings()
    {
        ArrayList<String[]> data = CommonServices.getData("C:\\Users\\danid\\eclipse-workspace\\Payroll_Management\\src\\Data\\Clockings.csv");
		// Clockings list
		ArrayList<Clocking> listClockings = new ArrayList<>();
		for (String[] record : data) {
			Clocking newClocking = new Clocking(EmployeeServices.getEmployer( Main.listEmployee ,
			record[0]), 
			CommonServices.convertStringToDate(record[1], "dd/MM/yyyy HH:mm"), 
			CommonServices.convertStringToDate(record[2], "dd/MM/yyyy HH:mm"),
			CommonServices.convertStringToBoolean(record[3]), 
			CommonServices.convertStringToBoolean(record[4]),
			CommonServices.convertStringToBoolean(record[5]));
			listClockings.add(newClocking);
		}
		   
		return listClockings;
    }

	// Function to check if the date is a public Holidays day
	private static boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(LocalDate.of(0, date.getMonth(), date.getDayOfMonth()));
    }

	// Function to remove lunch hours
	private static ArrayList<Long> removeLunchHours(LocalTime localTimeIn,LocalTime localTimeOut, long diff ){

		ArrayList<Long> durations = new ArrayList<>();
		if (localTimeIn.isBefore(LocalTime.of(13, 0)) && localTimeOut.isAfter(LocalTime.of(14, 0))) {
			durations.add(diff - 1);
		} else {
			durations.add(diff);
		}

		return durations;
	}
	// Function to convert from a type Date to LocalDate
	private static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	// Function to convert from a Date to a localTime
	private static LocalTime toLocalTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}
	// Function to round hours work (specific time in)
	private static LocalTime roundTimeIn(LocalTime timeIn) {
		return timeIn.isAfter(LocalTime.of(8, 30)) ? LocalTime.of(9, 0) : timeIn;
	}
	// Function to round hours work (specific time out)
	private static LocalTime roundTimeOut(LocalTime timeOut) {
		return timeOut.isBefore(LocalTime.of(18, 30)) ? LocalTime.of(18, 0) : timeOut;
	}

	// Function to check if a clocking is a Special day like (Sick day, vacation day or overtime day)
	private static boolean isSpecialDay(Clocking clocking) {
		return clocking.getSickDay() || clocking.getVacationDay() || clocking.getOvertimeDay();
	}
	// Function returns duration(dateOut - dateIn)
	private static long getDuration(Date dateIn, Date dateOut) {
        return TimeUnit.HOURS.convert(dateOut.getTime() - dateIn.getTime(), TimeUnit.MILLISECONDS);
    }

	// Function that returns the clockings of a specific employee 
	public static ArrayList<Clocking> getListClockingEmployee(ArrayList<Clocking> listClocking, String IDCode)
	{
		ArrayList<Clocking> ListClockingEmployee = new ArrayList<>();
        for (Clocking clocking : listClocking) {
            if (clocking.getidCodeEmployee() != null && clocking.getidCodeEmployee().getIDCode() != null && clocking.getidCodeEmployee().getIDCode().equals(IDCode)) {
                ListClockingEmployee.add(clocking);
            }
        }
        return ListClockingEmployee;
	}

	/* Function that calculate total hours ordinary job for single day (from Monday to Wensday) 
	 * (without sickDay , vacationDay, lunch break, overtime day, public holidays
	 * (like Christmas Day or New Year's Day ) )
	 */
	public static ArrayList<Long> getDayWorkingOrdinaryHours(ArrayList<Clocking> listClockingEmployee){
        // List of durations between clocking and clocking dates for a single day
		ArrayList<Long> durations = new ArrayList<>();

		for (Clocking clocking : listClockingEmployee) 
		{
			LocalDate localDateIn = toLocalDate(clocking.getClockingIn());
         	LocalTime localTimeIn = toLocalTime(clocking.getClockingIn());
        	LocalTime localTimeOut = toLocalTime(clocking.getClockingOut());

        	 DayOfWeek dayOfWeek = localDateIn.getDayOfWeek();

			// If the date falls on a Sunday or Saturday or a public Holiday, skip it
			// If the hours are leave or sick, then sickDay or vacationDay are true , skip them
			if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || isHoliday(localDateIn) || isSpecialDay(clocking)) {
				continue;
			}
			
			// check that I round off the entry or exit times to the nearest half hour due to shortfall or excess
			localTimeIn = roundTimeIn(localTimeIn);
        	localTimeOut = roundTimeOut(localTimeOut);

			// Calculate the difference between the two dates(dateIn,dateOut) and convert it to hours
			Long diffInHours = getDuration(clocking.getClockingIn(), clocking.getClockingOut());

			 ArrayList<Long> result = new ArrayList<>();
			 result = removeLunchHours(localTimeIn, localTimeOut, diffInHours );
			durations.addAll(result); 
		}
		return durations;
	}

	// Function that calculate Hours of job on Saturday (without lunch break) 
	public static ArrayList<Long> getSaturdayHours(ArrayList<Clocking> listClockingEmployee){
		// List of durations between clock-in and clock-out dates for a single day
		ArrayList<Long> durations = new ArrayList<>();
		
		for (Clocking clocking : listClockingEmployee) {
			LocalDate localDateIn = toLocalDate(clocking.getClockingIn());
			LocalTime localTimeIn = toLocalTime(clocking.getClockingIn());
			LocalTime localTimeOut = toLocalTime(clocking.getClockingOut());

			DayOfWeek dayOfWeek = localDateIn.getDayOfWeek();

			// If the date is not on a Saturday or is a public Holiday, skip it
			//If the hours are leave or sick, then sickDay or vacationDay are true , skip them
			if (dayOfWeek != DayOfWeek.SATURDAY || isHoliday(localDateIn) || isSpecialDay(clocking)) {
				continue;
			}

			localTimeIn = roundTimeIn(localTimeIn);
        	localTimeOut = roundTimeOut(localTimeOut);

			// Calculate the difference between the two dates(dateIn, dateOut) and convert it to hours
			Long diffInHours = getDuration(clocking.getClockingIn(),clocking.getClockingOut());

			// If the startDate is before 13:00 and the endDate is after 14:00 you remove me an hour
			ArrayList<Long> result = new ArrayList<>();
			result = removeLunchHours(localTimeIn, localTimeOut, diffInHours );
		   	durations.addAll(result);
		}
    	return durations;
		
		
	}

	// Function that calculate Hours of job on Sunday (without lunch break) 
	public static ArrayList<Long> getSundayHours(ArrayList<Clocking> listClockingEmployee){
		
		ArrayList<Long> durations = new ArrayList<>();
		
		for (Clocking clocking : listClockingEmployee) {
			LocalDate localDateIn = toLocalDate(clocking.getClockingIn());
			LocalTime localTimeIn = toLocalTime(clocking.getClockingIn());
			LocalTime localTimeOut = toLocalTime(clocking.getClockingOut());
	
			DayOfWeek dayOfWeek = localDateIn.getDayOfWeek();

			// If the date is not on a Sunday and it's a public holiday, skip it
			// If the hours are leave or sick, then sickDay or vacationDay are true , skip them
			if (dayOfWeek != DayOfWeek.SUNDAY || isHoliday(localDateIn) || isSpecialDay(clocking)) {
				continue;
			}
			// round the time
			localTimeIn = roundTimeIn(localTimeIn);
            localTimeOut = roundTimeOut(localTimeOut);
			// Calculate the difference between the two dates(dateIn, dateOut) and convert it to hours
			Long diffInHours = getDuration(clocking.getClockingIn(), clocking.getClockingOut());

			// If the startDate is before 13:00 and the endDate is after 14:00 you remove me an hour
			ArrayList<Long> result = new ArrayList<>();
			result = removeLunchHours(localTimeIn, localTimeOut, diffInHours );
		    durations.addAll(result);
		}
    	return durations;
		
		
	}

	// Function  returns list of vacationDay
	public static ArrayList<Long> getListVacationDay(ArrayList<Clocking> listClockingEmployee){
		ArrayList<Long> durations = new ArrayList<>();
		
		for (Clocking clocking : listClockingEmployee) {
			LocalTime localTimeIn = toLocalTime(clocking.getClockingIn());
			LocalTime localTimeOut = toLocalTime(clocking.getClockingOut());
			
			//Calculate the difference between the two dates(dateIn, dateOut) and convert it to hours
			Long diffInHours = getDuration(clocking.getClockingIn(), clocking.getClockingOut());
			
			if(clocking.getVacationDay() == false){
				continue;
			 }
			 
			// If the startDate is before 13:00 and the endDate is after 14:00 you remove me an hour
			ArrayList<Long> result = new ArrayList<>();
			result = removeLunchHours(localTimeIn, localTimeOut, diffInHours );
		    durations.addAll(result);
		}

		return durations;
	}

		//Function returnss list of SickDay
		public static ArrayList<Long> getListSickDay(ArrayList<Clocking> listClockingEmployee){
			ArrayList<Long> durations = new ArrayList<>();
			
			for (Clocking clocking : listClockingEmployee) {
				LocalTime localTimeIn = toLocalTime(clocking.getClockingIn());
				LocalTime localTimeOut = toLocalTime(clocking.getClockingOut());
	
				//Calculate the difference between the two dates(dateIn, dateOut) and convert it to hours
				Long diffInHours = getDuration(clocking.getClockingIn(), clocking.getClockingOut());
	
				// Se le ore sono di malattia sono a false  le salti, perche a me servono soltanto quelle che sono a true
				if(clocking.getSickDay() == false){
					continue;
				 }
				 
				ArrayList<Long> result = new ArrayList<>();
				result = removeLunchHours(localTimeIn, localTimeOut, diffInHours );
			    durations.addAll(result);
			}
	
			return durations;
		}

	//Function gets extra-job hours that are confirmed by Menager, check if these hours are confirmed or not
	public static ArrayList<Long> getListOvertimeHours(ArrayList<Clocking> listClockingEmployee, Employee employee){
		ArrayList<Long> durations = new ArrayList<>();

		for (Clocking clocking : listClockingEmployee) {
			LocalTime localTimeIn = toLocalTime(clocking.getClockingIn());
			LocalTime localTimeOut = toLocalTime(clocking.getClockingOut());

			//Calculate the difference between the two dates(dateIn, dateOut) and convert it to hours
			Long diffInHours = getDuration(clocking.getClockingIn(), clocking.getClockingOut());
			//if Overtime day is false, skip it
			if(clocking.getOvertimeDay() == false){
				continue;
			 }
			// round the time
			localTimeIn = roundTimeIn(localTimeIn);
			localTimeOut = roundTimeOut(localTimeOut);

			if(employee.getOvertimeStartDate().before(clocking.getClockingIn()) && employee.getOvertimeEndDate().after(clocking.getClockingOut())){

				if (localTimeIn.isBefore(LocalTime.of(13, 0)) && localTimeOut.isAfter(LocalTime.of(14, 0))) {
					durations.add(diffInHours - 1);
				} else {
					durations.add(diffInHours);
				}
			}else{
				System.out.print("Errore : il dipendente " + employee.getNameEmployee() + " " + employee.getSurnameEmployee() + " ha inserito un giorno di straordinario non consentito dal menager");
				break;
			}
		}
		return durations;
	}

	//Function that calculate the job hours of public holidays
	public static ArrayList<Long> getListHoursPublicHolidays(ArrayList<Clocking> listClockingEmployee){
		ArrayList<Long> durations = new ArrayList<>();

		for (Clocking clocking : listClockingEmployee) {
			LocalDate localDateIn = toLocalDate(clocking.getClockingIn());
			LocalTime localTimeIn = toLocalTime(clocking.getClockingIn());
			LocalTime localTimeOut = toLocalTime(clocking.getClockingOut());
	

			//Calculate the difference between the two dates(dateIn, dateOut) and convert it to hours
			Long diffInHours = getDuration(clocking.getClockingIn(), clocking.getClockingOut());

			// round the time
			localTimeIn = roundTimeIn(localTimeIn);
			localTimeOut = roundTimeOut(localTimeOut);
			
			if (!isHoliday(localDateIn)) {
                continue;
            }
			ArrayList<Long> result = new ArrayList<>();
			result = removeLunchHours(localTimeIn, localTimeOut, diffInHours );
		    durations.addAll(result);
		}
		return durations;
	}

	// Function that adds all the hours of the individual days together
	public static long sumTotalHours(ArrayList<Long> listTotalHoursSingleDay){

		long count = 0;
		for(long HoursDay : listTotalHoursSingleDay){
			count = count + HoursDay;
		}

		return count;
	}

	
}


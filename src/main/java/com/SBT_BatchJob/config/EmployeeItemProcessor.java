package com.SBT_BatchJob.config;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.batch.item.ItemProcessor;
import com.SBT_BatchJob.entity.Employee;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

	

	@Override
     	public Employee process(Employee employee) throws Exception {
  
	      	
		BigDecimal currentExp =  new BigDecimal(employee.getExperience());	
		
		 currentExp=currentExp.add(new BigDecimal("0.01"));
	     	
		if(currentExp.toString().endsWith(Integer.toString(12)))
			currentExp=new BigDecimal(currentExp.toBigInteger().add(new BigInteger("1")).toString());
		
		employee.setExperience(currentExp.toString());
		return employee;
		
	

		
//		 double currentExp = employee.getExperience(); // Get the current experience of the employee
//		    LocalDate startDate = LocalDate.parse(employee.getDateOfJoining(), DateTimeFormatter.ofPattern("M/d/yyyy")); // Parse the date from the "Date of Joining" field
//		    LocalDate currentDate = LocalDate.now();
//		    long monthsBetween = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), currentDate.withDayOfMonth(1)); // Calculate the duration between the two dates in months
//		    double experience = currentExp + (monthsBetween / 12.0); // Add the current experience to the calculated experience
//		    DecimalFormat df = new DecimalFormat("#.#");
//		    employee.setExperience(Double.valueOf(df.format(experience)));
//		    return employee;
	}
	
}
     


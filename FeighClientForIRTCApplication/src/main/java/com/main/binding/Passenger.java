package com.main.binding;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

	
	private String passengerName;
	private String fromLoc;
	private String toLoc;
	private LocalDate journeyDate;
	private String email;
	private Long trainNum;

}

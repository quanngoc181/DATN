package com.hust.datn.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtilities {
	public static LocalDate parseDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate localDate = LocalDate.parse(date, formatter);
        
        return localDate;
	}
}

package com.fmourabrasil.booknow.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Auxiliares {

	static String FORMATO_YMD = "YMD";
	static String FORMATO_DMY = "DMY";
	
	public static DateTimeFormatter retornaFormatoData(String formato) {
		if ( formato ==  FORMATO_DMY) {
			return DateTimeFormatter.ofPattern("dd/MM/yyyy");	
		} else {
			return DateTimeFormatter.ofPattern("yyyy-MM-dd");			
		}		
	}
	
	public static LocalDate converteDataStringParaLocalDate(String dataString) {
		return LocalDate.parse(dataString, retornaFormatoData(FORMATO_YMD) );
	}
	
	public static String converteLocalDataParaString(LocalDate data, String formato) {
		if ( formato == null ) {
			formato = FORMATO_YMD;
		}		
		return data.format(retornaFormatoData(formato));
	}
	
	public static String converteLocalDataParaString(LocalDate data) {
		return converteLocalDataParaString(data, FORMATO_YMD);
	}	
}

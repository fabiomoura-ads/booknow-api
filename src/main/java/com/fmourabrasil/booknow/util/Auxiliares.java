package com.fmourabrasil.booknow.util;

import java.time.format.DateTimeFormatter;

public class Auxiliares {

	public static DateTimeFormatter formatoData() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}
}

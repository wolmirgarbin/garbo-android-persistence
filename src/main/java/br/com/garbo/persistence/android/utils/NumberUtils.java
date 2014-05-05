package br.com.garbo.persistence.android.utils;

public class NumberUtils {

	/**
	 * Retorna a string no tipo double ou retorna o valor default passado
	 * 
	 * @param text
	 * @param defaultCaseNull
	 * @return
	 */
	public static Double toDoubleOrDefault(String text, Double defaultCaseNull) {
		if( isNotNumber(text) )
			return defaultCaseNull;
		
		return Double.valueOf(text);
	}

	/**
	 * Retorna apenas os numeros presentes no text
	 * 
	 * @param text
	 * @return
	 */
	public static String onlyNumbers(String text) {
		if( text == null )
			return null;
		
		final StringBuffer toReturn = new StringBuffer();
		for(final char c : text.toCharArray()) {
			if( c >= '0' && c <= '9' ) {
				toReturn.append(c);
			}
		}
		return toReturn.toString();
	}
	
	/**
	 * Valida se é numero ou não
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNumber(String text) {
		if( text == null )
			return false;
		
		boolean toReturn = false;
		for(final char c : text.toCharArray()) {
			if( c >= '0' && c <= '9' ) {
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	/**
	 * Return true case is not number
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNotNumber(String text) {
		return !isNumber(text);
	}
}

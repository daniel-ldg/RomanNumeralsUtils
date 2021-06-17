package com.github.danielldg.romannumerals;

public class RomanUtils {

	private static final String[] THOUSANDS = {
			"", // Empty value for index 0
			"M", "MM", "MMM"
	};
	private static final String[] HUNDREDS = {
			"", // Empty value for index 0
			"C","CC","CCC","CD","D","DC","DCC","DCCC","CM"
	};
	private static final String[] TENS = {
			"", // Empty value for index 0
			"X","XX","XXX","XL","L","LX","LXX","LXXX","XC"
	};
	private static final String[] UNITS = {
			"", // Empty value for index 0
			"I","II","III","IV","V","VI","VII","VIII","IX"
	};

	public static String toRoman(int arabic) throws InvalidOrUnexpectedNumber {

		if (arabic < RomanConstants.minValue) {
			String msg = String.format("Value %d is lower than minimum value: %d", arabic, RomanConstants.minValue);
			throw new InvalidOrUnexpectedNumber(msg);
		}

		if (arabic > RomanConstants.maxValue) {
			String msg = String.format("Value %d is higher than maximum value: %d", arabic, RomanConstants.maxValue);
			throw new InvalidOrUnexpectedNumber(msg);
		}

		char[] input = String.format("%04d",arabic).toCharArray();

		String thousandOutput = THOUSANDS[Character.getNumericValue(input[0])];
		String hundredOutput = HUNDREDS[Character.getNumericValue(input[1])];
		String teenOutput = TENS[Character.getNumericValue(input[2])];
		String unitOutput = UNITS[Character.getNumericValue(input[3])];

		return thousandOutput.concat(hundredOutput).concat(teenOutput).concat(unitOutput);

	}

	public static int toArabic(String s) throws InvalidOrUnexpectedNumber {

		if (s == null || s.isEmpty()) {
			throw new InvalidOrUnexpectedNumber("Unexpected null or empty string");
		}

		String romanInput = s.toUpperCase();
		char[] arabicOutput = {'0','0','0','0'};

		for (int i = THOUSANDS.length - 1; i > 0; i--) {
			String romanValue = THOUSANDS[i];
			if (romanInput.startsWith(romanValue)) {
				arabicOutput[0] = Character.forDigit(i, 10);
				romanInput = romanInput.substring(romanValue.length());
				break;
			}
		}

		for (int i = HUNDREDS.length - 1; i > 0; i--) {
			String romanValue = HUNDREDS[i];
			if (romanInput.startsWith(romanValue)) {
				arabicOutput[1] = Character.forDigit(i, 10);
				romanInput = romanInput.substring(romanValue.length());
				break;
			}
		}

		for (int i = TENS.length - 1; i > 0; i--) {
			String romanValue = TENS[i];
			if (romanInput.startsWith(romanValue)) {
				arabicOutput[2] = Character.forDigit(i, 10);
				romanInput = romanInput.substring(romanValue.length());
				break;
			}
		}

		for (int i = UNITS.length - 1; i > 0; i--) {
			String romanValue = UNITS[i];
			if (romanInput.startsWith(romanValue)) {
				arabicOutput[3] = Character.forDigit(i, 10);
				romanInput = romanInput.substring(romanValue.length());
				break;
			}
		}

		if (!romanInput.isEmpty()) {
			String msg = String.format("Value %s is not a valid roman numeral", s);
			throw new InvalidOrUnexpectedNumber(msg);
		}

		return Integer.parseInt(new String(arabicOutput));

	}
}

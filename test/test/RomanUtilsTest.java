package test;

import com.github.danielldg.romannumerals.InvalidOrUnexpectedNumber;
import com.github.danielldg.romannumerals.RomanUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RomanUtilsTest {

	@Nested
	@DisplayName("Not valid values arabic to roman")
	class toRomanInvalidValues {

		@DisplayName("There are no negative roman numerals")
		@ParameterizedTest(name = "Value {0} can not be represented as a roman numeral")
		@ValueSource(ints = {-1, -2, -30})
		void toRomanNegative(int arabicInput) {
			assertThrows(InvalidOrUnexpectedNumber.class, () -> RomanUtils.toRoman(arabicInput));
		}

		@Test
		@DisplayName("There is not a symbol to represent 0")
		void toRomanZero() {
			assertThrows(InvalidOrUnexpectedNumber.class, () -> RomanUtils.toRoman(0));
		}

		@DisplayName("Numbers higher than 3999 can not be represented as a roman numeral")
		@ParameterizedTest(name = "Value {0} can not be represented as a roman numeral")
		@ValueSource(ints = {4000, 4001, 10000})
		void toRomanOverTheMaxValue(int arabicInput) {
			assertThrows(InvalidOrUnexpectedNumber.class, () -> RomanUtils.toRoman(arabicInput));
		}

	}

	@Nested
	@DisplayName("Valid values arabic to roman")
	class toRomanValidValues {

		@Test
		@DisplayName("Minimum value 1")
		void toRomanMinValue() throws InvalidOrUnexpectedNumber {
			assertEquals("I", RomanUtils.toRoman(1));
		}

		@Test
		@DisplayName("Maximum value 3999")
		void toRomanMaxValue() throws InvalidOrUnexpectedNumber {
			assertEquals("MMMCMXCIX", RomanUtils.toRoman(3999));
		}

		@DisplayName("Numbers containing zeros")
		@ParameterizedTest(name = "Value {0} is {1}")
		@CsvSource({"60,LX","101,CI","901,CMI","1001,MI","1010,MX"})
		void toRomanContainingZeros(String arabicInput, String expectedRoman) throws InvalidOrUnexpectedNumber {
			String actualRoman = RomanUtils.toRoman(Integer.parseInt(arabicInput));
			assertEquals(expectedRoman, actualRoman);
		}

		@DisplayName("Large numbers are seen in the form of year numbers")
		@ParameterizedTest(name = "Value {0} is {1}")
		@CsvSource({"1776,MDCCLXXVI","1918,MCMXVIII","1954,MCMLIV","2014,MMXIV","2021,MMXXI"})
		void toRomanLargeNumbers(String arabicInput, String expectedRoman) throws InvalidOrUnexpectedNumber {
			String actualRoman = RomanUtils.toRoman(Integer.parseInt(arabicInput));
			assertEquals(expectedRoman, actualRoman);
		}

	}

	@Nested
	@DisplayName("Not valid values roman to arabic")
	class toArabicInvalidValues {

		@DisplayName("Only I V X L C D M are valid characters")
		@ParameterizedTest(name = "{0} contains not valid characters")
		@ValueSource(strings = {"A","ZZZ","OIIV", "MDCCLEXXVI","MMXIVB", "12", "C.M", "I_V", "C D", "-I"})
		void toArabicNotValidCharacters(String romanInput) {
			assertThrows(InvalidOrUnexpectedNumber.class, () -> RomanUtils.toArabic(romanInput));
		}

		@DisplayName("The same symbol can not be used more than tree times")
		@ParameterizedTest(name = "{0} is not a valid roman numeral")
		@ValueSource(strings = {"IIII", "IIIIV", "MCMXVIIII", "XXXXIII"})
		void toArabicSameSymbolMoreThanTreeTimes(String romanInput) {
			assertThrows(InvalidOrUnexpectedNumber.class, () -> RomanUtils.toArabic(romanInput));
		}

		@DisplayName("Only one symbol can subtract")
		@ParameterizedTest(name = "{0} is not a valid roman numeral")
		@ValueSource(strings = {"IIV", "CCCD", "XXLIII", "MCCM", "CCCM"})
		void toArabicSubtractMoreThanOneSymbol(String romanInput) {
			assertThrows(InvalidOrUnexpectedNumber.class, () -> RomanUtils.toArabic(romanInput));
		}

		@DisplayName("Symbols should be in order thousands, hundreds, teens, and units")
		@ParameterizedTest(name = "{0} is not a valid roman numeral")
		@ValueSource(strings = {"IIM", "XD", "CDXXIMM", "XCL", "MDVICCLXX", "MMMCMXCIXI"})
		void toArabicSymbolsWrongOrder(String romanInput) {
			assertThrows(InvalidOrUnexpectedNumber.class, () -> RomanUtils.toArabic(romanInput));
		}

	}

	@Nested
	@DisplayName("Valid values roman to arabic")
	class toArabicValidValues {

		@Test
		@DisplayName("Minimum value 1")
		void toArabicMinValue() throws InvalidOrUnexpectedNumber {
			assertEquals(1, RomanUtils.toArabic("I"));
		}

		@Test
		@DisplayName("Maximum value 3999")
		void toArabicMaxValue() throws InvalidOrUnexpectedNumber {
			assertEquals(3999, RomanUtils.toArabic("MMMCMXCIX"));
		}

		@DisplayName("Numbers containing zeros")
		@ParameterizedTest(name = "Value {0} is {1}")
		@CsvSource({"LX,60","CI,101","CMI,901","MI,1001","MX,1010"})
		void toArabicContainingZeros(String romanInput, String expectedArabic) throws InvalidOrUnexpectedNumber {
			int actualArabic = RomanUtils.toArabic(romanInput);
			assertEquals(Integer.parseInt(expectedArabic), actualArabic);
		}

		@DisplayName("Large numbers are seen in the form of year numbers")
		@ParameterizedTest(name = "Value {0} is {1}")
		@CsvSource({"MDCCLXXVI,1776","MCMXVIII,1918","MCMLIV,1954","MMXIV,2014","MMXXI,2021"})
		void toRomanLargeNumbers(String romanInput, String expectedArabic) throws InvalidOrUnexpectedNumber {
			int actualArabic = RomanUtils.toArabic(romanInput);
			assertEquals(Integer.parseInt(expectedArabic), actualArabic);
		}

		@DisplayName("Lower case roman are valid values")
		@ParameterizedTest(name = "Value {0} is {1}")
		@CsvSource({"MdCcLxXvI,1776","mCmXvIiI,1918","mCmLiV,1954","mMxIv,2014","MmXxI,2021"})
		void toRomanLowerCase(String romanInput, String expectedArabic) throws InvalidOrUnexpectedNumber {
			int actualArabic = RomanUtils.toArabic(romanInput);
			assertEquals(Integer.parseInt(expectedArabic), actualArabic);
		}

	}

}
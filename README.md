# RomanNumeralsUtils

Roman numerals conversion and manipulation Java library

## Usage

```
import com.github.danielldg.romannumerals.*;

class Scratch {
	public static void main(String[] args) {

		String romanInput = "MMXXI";

		int arabicOutput = 0;
		try {
			arabicOutput = RomanUtils.toArabic(romanInput);
		} catch (InvalidOrUnexpectedNumber e) {
			e.printStackTrace(); // handle invalid romanInput (e.g. invalid characters)
		}

		System.out.println(arabicOutput);

		int arabicInput = 1949;

		String romanOutput = "";
		try {
			romanOutput = RomanUtils.toRoman(arabicInput);
		} catch (InvalidOrUnexpectedNumber e) {
			e.printStackTrace(); // handle invalid arabicInput (e.g. negative value)
		}

		System.out.println(romanOutput);

	}
}
```

Output

```
2021
MCMXLIX
```
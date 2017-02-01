package de.veihelmann.master.processors;

/**
 * Normalizes input lines by applying string replacements. This makes the
 * detection of (very) similar comments more robust. For example, comments like
 * 'jQuery 1.0.0 2013' should be identified as similar to 'jQuery 1.0.1 2014'.
 */
public class CommentLineNormalizer {

	private final static String EMAIL_REGEX = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}";

	private final static String URL_REGEX = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	public final static String NORM_YEAR = "YEAR";

	public final static String NORM_EMAIL = "EMAIL";

	public final static String NORM_URL = "URL";

	public final static String NORM_VERSION = "VERSION";

	public final static String NORM_NUMBER = "NUMBER";

	private static final String EMPTY_STRING = "";

	public static String normalizeLine(String line) {
		if (line == null || line.isEmpty()) {
			return line;
		}
		return removePuntuation( //
				removeWhitespace( //
						normalizeEmail( //
								normalizeUrls( //
										removeCopyright( //
												normalizeVersions( //
														normalizeNumbers( //
																normalizeYears(line.toLowerCase()))))))));
	}

	private static String normalizeEmail(String line) {
		return line.replaceAll(EMAIL_REGEX, NORM_EMAIL);
	}

	private static String removePuntuation(String line) {
		return line.replaceAll("(\\//)?[.!?\\(\\)\\]\\[\\{\\}\\:\\,\\;\"'\\\\\\*]", EMPTY_STRING)
				.replaceAll("\\/(\\/)+", EMPTY_STRING).replaceAll("==*", "=").replaceAll("--*", "-");
	}

	private static String normalizeUrls(String line) {
		return line.replaceAll(URL_REGEX, NORM_URL);
	}

	private static String removeWhitespace(String line) {
		return line.replaceAll("\\s+", EMPTY_STRING);
	}

	private static String removeCopyright(String line) {
		return line.replaceAll("(\\((C|c)\\)|[cC][oO][pP][yY][rR][iI][gG][hH][tT])", EMPTY_STRING);
	}

	private static String normalizeNumbers(String line) {
		line = line.replaceAll("(\\d\\.)+\\.?", NORM_VERSION);
		return line.replaceAll("(\\d)+", "0").replaceAll("0(\\.|\\,)*", NORM_NUMBER)
				.replaceAll("(" + NORM_NUMBER + ")+", NORM_NUMBER);
	}

	private static String normalizeVersions(String line) {
		line = line.replaceAll("(" + NORM_NUMBER + "\\.)+\\.?", NORM_VERSION);
		return line.replaceAll("(\\s|\\))v(\\.|ersion|" + NORM_NUMBER + ")", NORM_VERSION)
				.replaceAll("v?" + NORM_VERSION + NORM_NUMBER, NORM_VERSION);
	}

	private static String normalizeYears(String line) {
		return line.replaceAll("(19\\d\\d|20(0|1)\\d)", NORM_YEAR).replaceAll(NORM_YEAR + "(\\-|\\_|\\w)" + NORM_YEAR,
				NORM_YEAR);
	}
}

package org.warnotte.obj2gui2;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.text.DefaultFormatter;

/**
 * A regular expression based implementation of AbstractFormatter.
 */
public class RegexFormatter extends DefaultFormatter
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5747089766673675266L;

	private Pattern pattern;

	private Matcher matcher;

	public RegexFormatter()
	{
		super();
	}

	/**
	 * Creates a regular expression based AbstractFormatter. pattern specifies
	 * the regular expression that will be used to determine if a value is
	 * legal.
	 */
	public RegexFormatter(String pattern) throws PatternSyntaxException
	{
		this();
		setPattern(Pattern.compile(pattern));
		//  setAllowsInvalid(false);
		setOverwriteMode(false);
	}

	/**
	 * Creates a regular expression based AbstractFormatter. pattern specifies
	 * the regular expression that will be used to determine if a value is
	 * legal.
	 */
	public RegexFormatter(Pattern pattern)
	{
		this();
		setPattern(pattern);
	}

	/**
	 * Sets the pattern that will be used to determine if a value is legal.
	 */
	public void setPattern(Pattern pattern)
	{
		this.pattern = pattern;
	}

	/**
	 * Returns the Pattern used to determine if a value is legal.
	 */
	public Pattern getPattern()
	{
		return pattern;
	}

	/**
	 * Sets the Matcher used in the most recent test if a value is legal.
	 */
	protected void setMatcher(Matcher matcher)
	{
		this.matcher = matcher;
	}

	/**
	 * Returns the Matcher from the most test.
	 */
	protected Matcher getMatcher()
	{
		return matcher;
	}

	/**
	 * Parses text returning an arbitrary Object. Some formatters may return
	 * null. If a Pattern has been specified and the text completely matches the
	 * regular expression this will invoke setMatcher.
	 * 
	 * @throws ParseException
	 *             if there is an error in the conversion
	 * @param text
	 *            String to convert
	 * @return Object representation of text
	 */
	@Override
	public Object stringToValue(String text) throws ParseException
	{

		System.err.println("Received text : " + text);
		Pattern pattern = getPattern();

		if (pattern != null)
		{
			Matcher matcher = pattern.matcher(text);

			if (matcher.matches())
			{
				setMatcher(matcher);
				System.err.println("Pattern match :)");
				return super.stringToValue(text);
			}
			System.err.println("Pattern did not match :(");
			//return text;
			throw new ParseException("Pattern did not match", 0);

		}
		return text;
	}
}
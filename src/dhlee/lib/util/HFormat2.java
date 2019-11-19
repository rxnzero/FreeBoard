package dhlee.lib.util;


/**
 * <pre>
 * Filename : HFormat2.java		<br>
 * Class    : HFormat2			<br>
 * Function : utility			<br>
 * Comment  : a collection of methods that are useful in converting strings.	<br>
 * </pre>
 *
 * @version	1.0.0
 * @author	rxnzero.
 */
public final class HFormat2
{
	/**
	 * Don't let anyone instantiate this class.
	 */
	private HFormat2()
	{
	}


	/*------------------- ################################ -------------------*/
	/*------------------- ##### Methods for servlets ##### -------------------*/
	/*------------------- ################################ -------------------*/

	/**
	 * Returns the string Converted to a valid string at quotation of javascript.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string converted to a valid string at quotation of javascript.
	 *
	 * @see		atsoft.util.HFormat2#toJSQuoteInTag(java.lang.String)
	 */
	public static String toJSQuote(String source)
	{
		char [] oldChar = {'\\', '\'', '\"'};
		String [] newString = {"\\\\", "\\\'", "\\\""};

		return replace(source, oldChar, newString);
	}


	/**
	 * Returns the string Converted to a valid string at double-quotation of HTML Tag.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string converted to a valid string at double-quotation of HTML Tag.
	 *
	 * @see		atsoft.util.HFormat2#toJSQuoteInTag(java.lang.String)
	 */
	public static String toTagQuote(String source)
	{
		return replace(source, "\"", "&quot;");
	}


	/**
	 * Returns the string Converted to a valid string at quotation of javascript in double-quotation of HTML Tag.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string converted to a valid string at quotation of javascript in double-quotation of HTML Tag.
	 *
	 * @see		atsoft.util.HFormat2#toJSQuote(java.lang.String)
	 * @see		atsoft.util.HFormat2#toTagQuote(java.lang.String)
	 */
	public static String toJSQuoteInTag(String source)
	{
		char [] oldChar = {'\\', '\'', '\"'};
		String [] newString = {"\\\\", "\\\'", "\\&quot;"};

		return replace(source, oldChar, newString);
	}


	/**
	 * Returns the string Converted to a valid string between &lt;textarea&gt; and &lt;/textarea&gt;.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string Converted to a valid string between &lt;textarea&gt; and &lt;/textarea&gt;.
	 *
	 * @see		atsoft.util.HFormat2#toPlainText(java.lang.String)
	 */
	public static String toTextareaText(String source)
	{
		char [] oldChar = {'<', '>', '&'};
		String [] newString = {"&lt;", "&gt;", "&amp;"};

		return replace(source, oldChar, newString);
	}


	/**
	 * Returns the string Converted to the string showed as a plain text on a browser.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string Converted to the string showed as a plain text on a browser.
	 *
	 * @see		atsoft.util.HFormat2#toTextareaText(java.lang.String)
	 */
	public static String toPlainText(String source)
	{
		char [] oldChar = {'<', '>', '&', '\n', '\r'};
		String [] newString = {"&lt;", "&gt;", "&amp;", "<br>\r\n", ""};

		return replace(source, oldChar, newString);
	}


	/**
	 * Returns the string added &lt;br&gt; tags to ends of lines.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string added &lt;br&gt; tags to ends of lines.
	 */
	public static String addBRTagToNewLine(String source)
	{
		char [] oldChar = {'\n', '\r'};
		String [] newString = {"<br>\r\n", ""};

		return replace(source, oldChar, newString);
	}


	/*------------------- ################################ -------------------*/
	/*------------------- #####   Methods for sql    ##### -------------------*/
	/*------------------- ################################ -------------------*/

	/**
	 * Returns the string Converted to a valid string at quotation of text literal.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string Converted to a valid string at quotation of text literal.
	 */
	public static String toLiteralQuote(String source)
	{
		return replace(source, "\'", "\'\'");
	}


	/**
	 * Returns the string Converted to a valid string at quotation of LIKE-clause with the difault escape caracter; which is <code>'/'</code>.
	 *
	 * @param	source		a source string.
	 *
	 * @return	the string Converted to a valid string at quotation of LIKE-clause with the difault escape caracter; which is <code>'/'.
	 *
	 * @see		atsoft.util.HFormat2#toLikeQuote(java.lang.String, char)
	 */
	public static String toLikeQuote(String source)
	{
		return toLikeQuote(source, '/');
	}


	/**
	 * Returns the string Converted to a valid string at quotation of LIKE-clause with <code>escapeChar</code> as an escape caracter.
	 *
	 * @param	source			a source string.
	 * @param	escapeChar		an escape character.
	 *
	 * @return	the string Converted to a valid string at quotation of LIKE-clause with <code>escapeChar</code> as an escape caracter.
	 *
	 * @see		atsoft.util.HFormat2#toLikeQuote(java.lang.String)
	 */
	public static String toLikeQuote(String source, char escapeChar)
	{
		if (escapeChar != '%' && escapeChar != '_')
		{
			char [] oldChar = null;
			String [] newString = null;
			if (escapeChar != '\'')
			{
				String escapeString = String.valueOf(escapeChar);
				// for Oracle
				oldChar = new char [] {'\'', escapeChar, '%', '_'};
				newString = new String [] {"\'\'", escapeString + escapeString, escapeString + "%", escapeString + "_"};
				// for MS SQL
				// oldChar = new char [] {'\'', escapeChar, '%', '_', '[', ']', '^'};
				// newString = new String [] {"\'\'", escapeString + escapeString, escapeString + "%", escapeString + "_", escapeString + "[", escapeString + "]", escapeString + "^"};
			}
			else
			{
				// for Oracle
				oldChar = new char [] {'\'', '%', '_'};
				newString = new String [] {"\'\'\'\'", "\'\'%", "\'\'_"};
				// for MS SQL
				// oldChar = new char [] {'\'', '%', '_', '[', ']', '^'};
				// newString = new String [] {"\'\'\'\'", "\'\'%", "\'\'_", "\'\'[", "\'\']", "\'\'^"};
			}
			return replace(source, oldChar, newString);
		}
		else
			throw new IllegalArgumentException();
	}


	/*------------------- ################################ -------------------*/
	/*------------------- #####    Common methods    ##### -------------------*/
	/*------------------- ################################ -------------------*/

	/**
	 * Returns a new string resulting from replacing all occurrences of <code>oldString</code> in this string with <code>newString</code>.
	 *
	 * @param	source		a source string.
	 * @param	oldString	the old string.
	 * @param	newString	the new string.
	 *
	 * @return	a string derived from this string by replacing every occurrence of <code>oldString</code> with <code>newString</code>.
	 *
	 * @see		hessie.util.HFormat#replaceIgnoreCase(java.lang.String, java.lang.String, java.lang.String)
	 */
	public static String replace(String source, String oldString, String newString)
	{
		if (source != null && oldString != null && newString != null)
			if (!oldString.equals(newString))
				if (oldString.length() > 0)
				{
					int beginIndex = 0;
					int endIndex = source.indexOf(oldString);
					if (endIndex >= 0)
					{
						StringBuffer buffer = new StringBuffer();
						while (endIndex >= 0)
						{
							buffer.append(source.substring(beginIndex, endIndex));
							buffer.append(newString);
							beginIndex = endIndex + oldString.length();
							endIndex = source.indexOf(oldString, beginIndex);
						}
						buffer.append(source.substring(beginIndex));
						return buffer.toString();
					}
					else
						return source;
				}
				else
					if (source.length() > 0)
						return source;
					else
						return newString;
			else
				return source;
		else
			throw new NullPointerException();
	}


	/**
	 * Returns a new string resulting from replacing all occurrences of array <code>oldChar</code> in this string with array <code>newString</code>.
	 *
	 * @param	source		a source string.
	 * @param	oldChar		the old character array.
	 * @param	newString	the new string array.
	 *
	 * @return	a string derived from this string by replacing every occurrence of array <code>oldChar</code> with array <code>newString</code>.
	 *
	 * @exception	ArrayIndexOutOfBoundsException	If <code>length</code>s of <code>oldChar</code> and <code>newString</code> are different.
	 *
	 * @see		hessie.util.HFormat#replaceIgnoreCase(java.lang.String, char [], java.lang.String [])
	 */
	public static String replace(String source, char [] oldChar, String [] newString)
	{
		if (source != null)
			if (oldChar.length == newString.length)
			{
				for (int j = 0; j < newString.length; j++)
					if (newString[j] == null)
						throw new NullPointerException();

				StringBuffer buffer = new StringBuffer();
				String oldChars = String.valueOf(oldChar);
				for (int i = 0; i < source.length(); i++)
				{
					char subchar = source.charAt(i);
					int index = oldChars.indexOf(subchar);
					if (index >= 0)
						buffer.append(newString[index]);
					else
						buffer.append(subchar);
				}
				return buffer.toString();
			}
			else
				throw new ArrayIndexOutOfBoundsException();
		else
			throw new NullPointerException();
	}


}
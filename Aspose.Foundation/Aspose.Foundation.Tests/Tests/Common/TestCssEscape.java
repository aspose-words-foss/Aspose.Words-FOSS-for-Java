// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.CssEscape;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests methods of the <see cref="CssEscape"/> class.
/// </summary>
@Test
public class TestCssEscape
{
    @Test
    public void testIdentifierNull()
    {
        checkIdentifiers(null, null);
    }

    @Test
    public void testIdentifierEmpty()
    {
        checkIdentifiers("", "");
    }

    @Test
    public void testIdentifierLetter()
    {
        checkIdentifiers("a", "a");
    }

    @Test
    public void testIdentifierLetters()
    {
        checkIdentifiers("abc", "abc");
    }

    @Test
    public void testIdentifierDigit()
    {
        checkIdentifiers("1", "\\31");
    }

    @Test
    public void testIdentifierDigits()
    {
        checkIdentifiers("123", "\\31 23");
    }

    @Test
    public void testIdentifierDigitAndLetter()
    {
        checkIdentifiers("1a", "\\31 a");
    }

    @Test
    public void testIdentifierDigitAndLetterNoSpace()
    {
        checkIdentifiers("1x", "\\31x");
    }

    @Test
    public void testIdentifierStartsWithDash()
    {
        checkIdentifiers("-a", "-a");
    }

    @Test
    public void testIdentifierStartsWithDashDigit()
    {
        checkIdentifiers("-1", "-\\31");
    }

    @Test
    public void testIdentifierStartsWithDashNonAscii()
    {
        checkIdentifiers("-§", "-§");
    }

    @Test
    public void testIdentifierDash()
    {
        checkIdentifiers("-", "\\-");
    }

    @Test
    public void testIdentifierDoubleDash()
    {
        checkIdentifiers("--", "-\\-");
    }

    @Test
    public void testIdentifierUnderscore()
    {
        checkIdentifiers("_", "_");
    }

    @Test
    public void testIdentifierDashUnderscore()
    {
        checkIdentifiers("-_", "-_");
    }

    @Test
    public void testIdentifierPunctuation()
    {
        checkIdentifiers("{a}", "\\{a\\}");
    }

    @Test
    public void testIdentifierWhitespace()
    {
        checkIdentifiers("1 a", "\\31\\ a");
    }

    @Test
    public void testIdentifierNonAscii()
    {
        checkIdentifiers("§§§", "§§§");
    }

    @Test
    public void testIdentifierComplex()
    {
        checkIdentifiers("-12(a b)§", "-\\31 2\\(a\\ b\\)§");
    }

    @Test
    public void testStringEmpty()
    {
        checkString("", "");
    }

    @Test
    public void testStringWhitespace()
    {
        checkString(" ", " ");
    }

    @Test
    public void testStringCrLf()
    {
        checkString("\r\n", "\\D\\A");
        checkString("\r\nAdorable", "\\D\\A Adorable");
        checkString("\r\nIndifference", "\\D\\AIndifference");
    }

    @Test
    public void testStringCr()
    {
        checkString("\r", "\\D");
        checkString("\r1", "\\D 1");
        checkString("\rX", "\\DX");
    }

    @Test
    public void testStringLineFeed()
    {
        checkString("\n", "\\A");
        checkString("\n2", "\\A 2");
        checkString("\nG", "\\AG");
    }

    @Test
    public void testStringFormFeed()
    {
        checkString("\f", "\\C");
        checkString("\fC", "\\C C");
        checkString("\fR", "\\CR");
    }

    @Test
    public void testStringQuote()
    {
        checkDoubleQuotedString("\"", "\\\"");
        checkSingleQuotedString("'", "\\'");
    }

    @Test
    public void testStringBackslash()
    {
        checkString("\\", "\\\\");
    }

    @Test
    public void testStringComplex()
    {
        final String ORIGINAL_STRING_VALUE = "public class foo\r\n{\r\nprivate int x;\r\n\r\nfoo(int x) {\r\n  this.x = x;\r\n}\r\n}";
        final String ESCAPED_STRING_VALUE = "public class foo\\D\\A{\\D\\Aprivate int x;\\D\\A\\D\\A foo(int x) {\\D\\A   this.x = x;\\D\\A}\\D\\A}";
        checkString(ORIGINAL_STRING_VALUE, ESCAPED_STRING_VALUE);
    }

    @Test (dataProvider = "testIsValidIdentifierDataProvider")
    public void testIsValidIdentifier(String identifier)
    {
        msAssert.isTrue(CssEscape.isValidIdentifier(identifier));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsValidIdentifierDataProvider")
	public static Object[][] testIsValidIdentifierDataProvider()
	{
		return new Object[][]
		{
			{"identifier"},
			{"-identifier"},
			{"_identifier"},
			{"а3"},
			{"-а3"},
			{"_3"},
			{"宋体"},
		};
	}

    @Test (dataProvider = "testIsInvalidIdentifierDataProvider")
    public void testIsInvalidIdentifier(String identifier)
    {
        msAssert.isFalse(CssEscape.isValidIdentifier(identifier));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsInvalidIdentifierDataProvider")
	public static Object[][] testIsInvalidIdentifierDataProvider()
	{
		return new Object[][]
		{
			{"font name"},
			{"(parameter)"},
			{"'quoted_text'"},
			{"\"quoted_text\""},
			{"100"},
			{"3pt"},
			{"-3pt"},
			{"one,two"},
		};
	}

    private static void checkIdentifiers(String originalUnescaped, String expectedEscaped)
    {
        String actualEscaped = CssEscape.escapeIdentifier(originalUnescaped);
        msAssert.areEqual(expectedEscaped, actualEscaped);
    }

    private static void checkSingleQuotedString(String originalUnescaped, String expectedEscaped)
    {
        String actualEscaped = CssEscape.escapeSingleQuotedString(originalUnescaped);
        msAssert.areEqual(expectedEscaped, actualEscaped);
    }

    private static void checkDoubleQuotedString(String originalUnescaped, String expectedEscaped)
    {
        String actualEscaped = CssEscape.escapeDoubleQuotedString(originalUnescaped);
        msAssert.areEqual(expectedEscaped, actualEscaped);
    }

    private static void checkString(String originalUnescaped, String expectedEscaped)
    {
        checkSingleQuotedString(originalUnescaped, expectedEscaped);
        checkDoubleQuotedString(originalUnescaped, expectedEscaped);
    }
}


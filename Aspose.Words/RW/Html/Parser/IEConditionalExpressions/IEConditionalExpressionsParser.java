// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.IO.StringReader;
import com.aspose.StringUtil;


/// <summary>
/// A parser for IE conditional expressions.
/// </summary>
class IEConditionalExpressionsParser
{
    /// <summary>
    /// Parses a text into a conditional expression.
    /// </summary>
    /// <param name="text">A text to parse.</param>
    /// <returns>Either the parsed expression or <c>null</c> to indicate an error.</returns>
    ConditionalExpression parse(String text) throws Exception
    {
        Debug.assertTrue(text != null);
        Debug.assertTrue(mReader == null);

        ConditionalExpression expression;
        StringReader stringReaderResource = mReader = new StringReader(text);
        try /*JAVA: was using*/
        {
            advance();
            expression = parseDisjunction();

            // It is a top-level expression and it must end with an end-of-text token.
            if (mCurrentToken.getTokenType() != TokenType.END_OF_TEXT)
            {
                // Error. Unexpected token.
                expression = null;
            }
        }
        finally { if (stringReaderResource != null) stringReaderResource.close(); }
        mReader = null;

        return expression;
    }

    private ConditionalExpression parseDisjunction() throws Exception
    {
        // First part of an expression.
        ConditionalExpression expression = parseConjunction();
        if (expression == null)
        {
            // Error. Cannot parse the left part.
            return null;
        }

        // An optional sequence of additional parts separated by 'or' operators.
        while (mCurrentToken.getTokenType() == TokenType.OR)
        {
            advance();
            ConditionalExpression rightPart = parseConjunction();
            if (rightPart == null)
            {
                // Error. Cannot parse the right part.
                return null;
            }
            expression = new Or(expression, rightPart);
        }

        // This must be either top-level expression (which ends with an end-of-text token) or a subexpression
        // (which ends with a closing parenthesis).
        if ((mCurrentToken.getTokenType() != TokenType.END_OF_TEXT) &&
            (mCurrentToken.getTokenType() != TokenType.CLOSE_PARENTHESIS))
        {
            // Error. Unexpected token.
            return null;
        }

        Debug.assertTrue(expression != null);
        return expression;
    }

    private ConditionalExpression parseConjunction() throws Exception
    {
        // First part of an expression.
        ConditionalExpression expression = parsePart();
        if (expression == null)
        {
            // Error. Cannot parse the first part.
            return null;
        }

        // An optional sequence of additional parts separated by 'and' operators.
        while (mCurrentToken.getTokenType() == TokenType.AND)
        {
            advance();
            ConditionalExpression rightPart = parsePart();
            if (rightPart == null)
            {
                // Error. Cannot parse one of additional parts.
                return null;
            }
            expression = new And(expression, rightPart);
        }

        // An expression can be one of the following:
        //  - a top-level expression, which ends with an end-of-text token;
        //  - a subexpression, which ends with a closing parenthesis;
        //  - a conjunction inside a disjunction sequence, which ends with an 'or' operator.
        if ((mCurrentToken.getTokenType() != TokenType.END_OF_TEXT) &&
            (mCurrentToken.getTokenType() != TokenType.CLOSE_PARENTHESIS) &&
            (mCurrentToken.getTokenType() != TokenType.OR))
        {
            // Error. Unexpected token.
            return null;
        }

        Debug.assertTrue(expression != null);
        return expression;
    }

    private ConditionalExpression parsePart() throws Exception
    {
        skipWhitespace();

        switch (mCurrentToken.getTokenType())
        {
            case TokenType.OPEN_PARENTHESIS:
            {
                return parseSubexpression();
            }
            case TokenType.NOT:
            {
                return parseNegation();
            }
            case TokenType.IDENTIFIER:
            {
                return parseComparison();
            }
            default:
            {
                // Error. Unexpected token.
                return null;
            }
        }
    }

    private ConditionalExpression parseSubexpression() throws Exception
    {
        Debug.assertTrue(mCurrentToken.getTokenType() == TokenType.OPEN_PARENTHESIS);

        advance();
        ConditionalExpression part = parseDisjunction();
        if ((part == null) ||
            (mCurrentToken.getTokenType() != TokenType.CLOSE_PARENTHESIS))
        {
            // Error. Cannot parse the part or found an unmatched open parenthesis.
            return null;
        }
        advance();
        skipWhitespace();

        Debug.assertTrue(part != null);
        return part;
    }

    private ConditionalExpression parseNegation() throws Exception
    {
        advance();
        skipWhitespace();
        ConditionalExpression negatedExpression;
        switch (mCurrentToken.getTokenType())
        {
            case TokenType.OPEN_PARENTHESIS:
            {
                negatedExpression = parseSubexpression();
                break;
            }
            case TokenType.IDENTIFIER:
            {
                negatedExpression = parseComparison();
                break;
            }
            default:
            {
                // Error. Unexpected token.
                negatedExpression = null;
                break;
            }
        }
        if (negatedExpression == null)
        {
            // Error. Cannot parse the negated expression.
            return null;
        }
        return new Not(negatedExpression);
    }

    private ConditionalExpression parseComparison() throws Exception
    {
        Debug.assertTrue(mCurrentToken.getTokenType() == TokenType.IDENTIFIER);

        if (("true".equals(mCurrentToken.getText())) || ("false".equals(mCurrentToken.getText())))
        {
            boolean value = "true".equals(mCurrentToken.getText());
            advance();
            skipWhitespace();
            return new BoolConstant(value);
        }

        // Parse operation. An operation keyword is optional.
        /*ComparisonOperation*/int operation;
        switch (gStringSwitchMap.of(mCurrentToken.getText()))
        {
            case /*"gt"*/0:
                operation = ComparisonOperation.GREATER;
                break;
            case /*"gte"*/1:
                operation = ComparisonOperation.GREATER_OR_EQUAL;
                break;
            case /*"lt"*/2:
                operation = ComparisonOperation.LESS;
                break;
            case /*"lte"*/3:
                operation = ComparisonOperation.LESS_OR_EQUAL;
                break;
            default:
                operation = ComparisonOperation.EQUAL;
                break;
        }
        // Equality comparison operation has no dedicated keyword.
        if (operation != ComparisonOperation.EQUAL)
        {
            advance();
            skipWhitespace();
        }

        // Parse feature. A feature is mandatory.
        if (mCurrentToken.getTokenType() != TokenType.IDENTIFIER)
        {
            // Error. Unexpected token.
            return null;
        }
        String feature = mCurrentToken.getText();
        advance();

        // Parse version. A version is optional for equality comparison but it is mandatory for other operations.
        skipWhitespace();
        VersionVector version;
        if (mCurrentToken.getTokenType() == TokenType.VERSION)
        {
            version = VersionVector.parse(mCurrentToken.getText());
            advance();
        }
        else
        {
            if (operation != ComparisonOperation.EQUAL)
            {
                // Error. Unexpected token. Version is mandatory if an operation token is present.
                return null;
            }
            version = null;
        }

        skipWhitespace();

        return new Comparison(operation, feature, version);
    }

    /// <summary>
    /// Advances the parser one token forward.
    /// </summary>
    private void advance() throws Exception
    {
        mCurrentToken = parseNextToken();
    }

    /// <summary>
    /// Skips the current token if it is a whitespace.
    /// </summary>
    private void skipWhitespace() throws Exception
    {
        if (mCurrentToken.getTokenType() == TokenType.WHITESPACE)
        {
            advance();
        }
        Debug.assertTrue(mCurrentToken.getTokenType() != TokenType.WHITESPACE);
    }

    private Token parseNextToken() throws Exception
    {
        int character = mReader.read();
        switch (character)
        {
            case -1:
                return new Token(TokenType.END_OF_TEXT, "");
            case '(':
                return new Token(TokenType.OPEN_PARENTHESIS, "(");
            case ')':
                return new Token(TokenType.CLOSE_PARENTHESIS, ")");
            case '!':
                return new Token(TokenType.NOT, "!");
            case '&':
                return new Token(TokenType.AND, "&");
            case '|':
                return new Token(TokenType.OR, "|");
            default:
                break;
        }
        if (StringUtil.isWhiteSpace(character))
        {
            return new Token(TokenType.WHITESPACE, readWhitespace((char)character));
        }
        if (StringUtil.isLetter(character))
        {
            return new Token(TokenType.IDENTIFIER, readIdentifier((char)character));
        }
        if (StringUtil.isDigit(character))
        {
            return new Token(TokenType.VERSION, readVersion((char)character));
        }
        return new Token(TokenType.ERROR, "");
    }

    private String readWhitespace(char firstCharacter) throws Exception
    {
        StringBuilder result = new StringBuilder();
        result.append(firstCharacter);
        while (true)
        {
            int character = mReader.peek();
            if (!StringUtil.isWhiteSpace(character))
            {
                break;
            }
            result.append((char)character);
            mReader.read();
        }
        return result.toString();
    }

    private String readIdentifier(char firstCharacter) throws Exception
    {
        StringBuilder result = new StringBuilder();
        result.append(asciiLowerCase(firstCharacter));
        while (true)
        {
            int character = mReader.peek();
            if (!StringUtil.isLetterOrDigit(character))
            {
                break;
            }
            result.append(asciiLowerCase((char)character));
            mReader.read();
        }
        return result.toString();
    }

    /// <summary>
    /// Converts the character to lowercase if it is an ASCII uppercase letter, otherwise leaves it unchanged.
    /// </summary>
    private static char asciiLowerCase(char c)
    {
        final int DIFF = 'a' - 'A';
        return ((c >= 'A') && (c <= 'Z'))
            ? (char)(c + DIFF)
            : c;
    }

    private String readVersion(char firstCharacter) throws Exception
    {
        StringBuilder result = new StringBuilder();
        result.append(firstCharacter);
        while (true)
        {
            int character = mReader.peek();
            if ((!StringUtil.isLetterOrDigit(character)) && (character != '.'))
            {
                break;
            }
            result.append((char)character);
            mReader.read();
        }
        return result.toString();
    }

    private StringReader mReader;
    private Token mCurrentToken;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"gt",
		"gte",
		"lt",
		"lte"
	);

}


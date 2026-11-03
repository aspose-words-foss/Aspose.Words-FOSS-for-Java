// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;


/// <summary>
/// Converts a list of tokens that represents a CSS selector into a new selector instance.
/// </summary>
class CssSelectorParser
{
    private CssSelectorParser(
        ArrayList<CssToken> tokens,
        CssNamespacePrefixes declaredCssNamespaces)
    {
        Debug.assertTrue(declaredCssNamespaces != null);

        mTokens = tokens;
        mDeclaredCssNamespaces = declaredCssNamespaces;
    }

    /// <summary>
    /// Parses the tokens into a CSS selector.
    /// </summary>
    /// <param name="tokens">A list of tokens that represents a CSS selector.</param>
    /// <param name="declaredCssNamespaces">CSS namespace declarations met so far.</param>
    /// <returns>
    /// The CSS selector, if the tokens were parsed successfully.
    /// Otherwise, <c>null</c> is returned.
    /// </returns>
    static CssSelector parse(
        ArrayList<CssToken> tokens,
        CssNamespacePrefixes declaredCssNamespaces)
    {
        CssSelectorParser parser = new CssSelectorParser(tokens, declaredCssNamespaces);
        return parser.parseSelector();
    }

    /// <summary>
    /// Parses the sequence "CombinableSelector[Combinator CombinableSelector][Combinator CombinableSelector][...]".
    /// </summary>
    /// <returns>
    /// The parsed selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSelector parseSelector()
    {
        mTokenIndex = 0;
        if ((mTokens == null) || (mTokenIndex >= mTokens.size()))
        {
            // Parsing error. The list of tokens is empty.
            return null;
        }

        // Pre-fetch tokens.
        mCurrentToken = (mTokenIndex < mTokens.size())
            ? mTokens.get(mTokenIndex)
            : CssToken.EOF;
        int nextTokenIndex = mTokenIndex + 1;
        mNextToken = (nextTokenIndex < mTokens.size())
            ? mTokens.get(nextTokenIndex)
            : CssToken.EOF;

        CssCombinableSelector head = parseCombinableSelector();
        if (head == null)
        {
            // Error. Cannot parse the head selector.
            return null;
        }

        // A selector can contain zero or more combinators.
        ArrayList<CssCombinator> combinators = parseCombinators();
        if (combinators == null)
        {
            // Error while parsing combinators.
            return null;
        }

        // If a selector contains one or more combinators, it is a complex selector.
        if (combinators.size() > 0)
        {
            // Only the last selector in a combination is allowed to select a pseudo-element.
            if (head.selectsPseudoElement())
            {
                // Error. The head selector is invalid.
                return null;
            }

            for (int i = 0; i < combinators.size() - 1; i++)
            {
                CssCombinator combinator = combinators.get(i);
                if (combinator.getSelector().selectsPseudoElement())
                {
                    // Error. One of combinators is invalid.
                    return null;
                }
            }

            return new CssComplexSelector(head, combinators);
        }
        // Otherwise, it is a simple selector.
        return head;
    }

    /// <summary>
    /// Tries to parse a combinator and a selector: "[Combinator CombinableSelector]".
    /// </summary>
    /// <returns>
    /// A list of parsed combinators (instances of <see cref="CssCombinator"/>).
    /// Items of the list are never <c>null</c>.
    /// The list is empty if there are no combinators.
    /// If a parsing error occurs, <c>null</c> is returned.
    /// </returns>
    private ArrayList<CssCombinator> parseCombinators()
    {
        ArrayList<CssCombinator> combinators = new ArrayList<CssCombinator>();
        while (!mCurrentToken.isEof())
        {
            if (mCurrentToken.isWhitespace() &&
                (mNextToken.isDelim('+') || mNextToken.isDelim('>') || mNextToken.isDelim('~')))
            {
                skipWhitespace();
            }

            CssCombinator combinator;
            CssToken currentToken = mCurrentToken;
            if (currentToken.isDelim('+'))
            {
                combinator = parseAdjacentSiblingCombinator();
            }
            else if (currentToken.isDelim('>'))
            {
                combinator = parseChildCombinator();
            }
            else if (currentToken.isDelim('~'))
            {
                combinator = parseGeneralSiblingCombinator();
            }
            else if (currentToken.isWhitespace())
            {
                combinator = parseDescendantCombinator();
            }
            else
            {
                // Parsing error. Unexpected token.
                return null;
            }

            if (combinator == null)
            {
                // Error. Cannot parse the combinator.
                return null;
            }
            msArrayList.add(combinators, combinator);
        }

        return combinators;
    }

    /// <summary>
    /// Parses the right-hand side of a general sibling combinator (the sequence "~ Selector").
    /// </summary>
    /// <returns>The parsed selector or <c>null</c> in case of an error.</returns>
    private CssGeneralSiblingCombinator parseGeneralSiblingCombinator()
    {
        // Read a combinator token.
        CssToken combinatorToken = readTokenAndAdvance(CssTokenType.DELIM);
        if ((combinatorToken == null) || (((CssDelimToken)combinatorToken).getValue() != '~'))
        {
            // Error. Unexpected token.
            return null;
        }

        skipWhitespace();

        // Read the right-hand selector.
        CssCombinableSelector right = parseCombinableSelector();
        if (right == null)
        {
            // Error. Cannot parse the right-hand selector.
            return null;
        }

        return new CssGeneralSiblingCombinator(right);
    }

    /// <summary>
    /// Parses the right-hand side of a child element combinator (the sequence "&gt; Selector").
    /// </summary>
    /// <returns>The parsed selector or <c>null</c> in case of an error.</returns>
    private CssChildCombinator parseChildCombinator()
    {
        // Read a combinator token.
        CssToken combinatorToken = readTokenAndAdvance(CssTokenType.DELIM);
        if ((combinatorToken == null) || (((CssDelimToken)combinatorToken).getValue() != '>'))
        {
            // Error. Unexpected token.
            return null;
        }

        skipWhitespace();

        // Read the right-hand selector.
        CssCombinableSelector right = parseCombinableSelector();
        if (right == null)
        {
            // Error. Cannot parse the right-hand selector.
            return null;
        }

        return new CssChildCombinator(right);
    }

    /// <summary>
    /// Parses the right-hand side of an adjacent sibling combinator (the sequence "+ Selector").
    /// </summary>
    /// <returns>The parsed selector or <c>null</c> in case of an error.</returns>
    private CssAdjacentSiblingCombinator parseAdjacentSiblingCombinator()
    {
        // Read a combinator token.
        CssToken combinatorToken = readTokenAndAdvance(CssTokenType.DELIM);
        if ((combinatorToken == null) || (((CssDelimToken)combinatorToken).getValue() != '+'))
        {
            // Error. Unexpected token.
            return null;
        }

        skipWhitespace();

        // Read the right-hand selector.
        CssCombinableSelector right = parseCombinableSelector();
        if (right == null)
        {
            // Error. Cannot parse the right-hand selector.
            return null;
        }

        return new CssAdjacentSiblingCombinator(right);
    }

    /// <summary>
    /// Parses the right-hand side of a descendant combinator (the sequence "'space' Selector").
    /// </summary>
    /// <returns>The parsed selector or <c>null</c> in case of an error.</returns>
    private CssDescendantCombinator parseDescendantCombinator()
    {
        // Read whitespace combinator.
        if (readTokenAndAdvance(CssTokenType.WHITESPACE) == null)
        {
            // Error. Unexpected token.
            return null;
        }

        // Read the right-hand selector.
        CssCombinableSelector right = parseCombinableSelector();
        if (right == null)
        {
            // Error. Cannot parse the right-hand selector.
            return null;
        }

        return new CssDescendantCombinator(right);
    }

    /// <summary>
    /// Parses a combinable selector, which is either a simple selector, or a sequence of simple selectors
    /// (a compound selector).
    /// </summary>
    /// <returns>
    /// The parsed selector or <c>null</c> in case of an error.
    /// </returns>
    /// <remarks>
    /// For example, "div", "div[align=left]", and "div.class[align=left]:not(#id)" are combinable selectors.
    /// </remarks>
    private CssCombinableSelector parseCombinableSelector()
    {
        CssSimpleSelector head = parseSimpleSelector();
        if (head == null)
        {
            // Error. Cannot parse the head selector.
            return null;
        }

        ArrayList<CssSimpleSelector> compoundParts = parseCompoundSelectorParts();
        if (compoundParts == null)
        {
            // An error has occurred while parsing compound selector parts.
            return null;
        }

        // It might be just a simple selector, without compound parts.
        if (compoundParts.size() == 0)
        {
            return head;
        }

        // It is a compound selector consisting of more than one part. Let's reconstruct it.
        compoundParts.add(0, head);
        CssCombinableSelector compoundSelector = compoundParts.get(compoundParts.size() - 1);
        for (int i = compoundParts.size() - 2; i >= 0; i--)
        {
            CssSimpleSelector part = compoundParts.get(i);
            if (part instanceof CssPseudoElementSelector)
            {
                // No more than one pseudo-element selector is allowed per compound selector.
                // The pseudo-element selector must be the last item of a compound selector.
                return null;
            }
            compoundSelector = new CssCompoundSelector(part, compoundSelector);
        }
        return compoundSelector;
    }

    /// <summary>
    /// Parses a sequence of simple selectors (a compound selector), which can be empty.
    /// </summary>
    /// <returns>
    /// A list of parsed simple selectors (instances of <see cref="CssSimpleSelector"/>) that constitute
    /// a compound selector.
    /// Items of the list are never <c>null</c>.
    /// The list is empty if there are no compound parts and the current selector is simple.
    /// In case of a parsing error <c>null</c> is returned.
    /// </returns>
    private ArrayList<CssSimpleSelector> parseCompoundSelectorParts()
    {
        // Collect compound selector parts.
        ArrayList<CssSimpleSelector> selectorParts = new ArrayList<CssSimpleSelector>();
        while (!mCurrentToken.isDelim('+') &&
            !mCurrentToken.isDelim('>') &&
            !mCurrentToken.isDelim('~') &&
            !mCurrentToken.isWhitespace() &&
            !mCurrentToken.isEof())
        {
            if (currentTokenStartsAClass() ||
                (mCurrentToken.getType() == CssTokenType.HASH) ||
                (mCurrentToken.getType() == CssTokenType.SQUARE_BRACKET_LEFT) ||
                (mCurrentToken.getType() == CssTokenType.COLON))
            {
                CssSimpleSelector part = parseCompoundSelectorPart();
                if (part == null)
                {
                    // Error. Cannot parse a part of a compound selector.
                    return null;
                }
                msArrayList.add(selectorParts, part);
            }
            else
            {

                // Parsing error. Unexpected token.
                return null;
            }
        }
        return selectorParts;
    }

    /// <summary>
    /// Parses second and later parts of a sequence of simple selectors.
    /// This can be any simple selector except the universal selector and the type selector.
    /// </summary>
    /// <returns>
    /// The parsed simple selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parseCompoundSelectorPart()
    {
        if (currentTokenStartsAClass())
        {
            return parseClassSelector();
        }

        switch (mCurrentToken.getType())
        {
            case CssTokenType.HASH:
                return parseIdSelector();
            case CssTokenType.SQUARE_BRACKET_LEFT:
                return parseAttributeSelector();
            case CssTokenType.COLON:
                return parsePseudoSelector();
            default:
                // Parsing error. Unexpected token.
                return null;
        }
    }

    /// <summary>
    /// Parses a simple selector.
    /// </summary>
    /// <returns>
    /// The parsed simple selector or <c>null</c> in case of an error.
    /// </returns>
    /// <remarks>
    /// The definition of the simple selector can be found here: http://www.w3.org/TR/css3-selectors/#selector-syntax
    /// </remarks>
    private CssSimpleSelector parseSimpleSelector()
    {
        if ((mCurrentToken.getType() == CssTokenType.IDENT) ||
            mCurrentToken.isDelim('*') ||
            mCurrentToken.isDelim('|'))
        {
            return parseTypeOrUniversalSelector();
        }
        else
        {
            return parseCompoundSelectorPart();
        }
    }

    /// <summary>
    /// Parses a :not() pseudo-class selector.
    /// </summary>
    /// <returns>
    /// The parsed selector or <c>null</c> in case of an error.
    /// </returns>
    private CssNotSelector parseNotSelector()
    {
        if (readTokenAndAdvance(CssTokenType.FUNCTION) == null)
        {
            // Error. Unexpected token.
            return null;
        }
        skipWhitespace();
        CssSimpleSelector argument = parseNotSelectorArgument();
        if (argument == null)
        {
            // Error. Cannot parse the argument.
            return null;
        }
        if (argument instanceof CssPseudoElementSelector)
        {
            // A pseudo-element cannot be an argument of the :not pseudo-class.
            return null;
        }
        if (argument instanceof CssNotSelector)
        {
            // :not pseudo-classes cannot be nested.
            return null;
        }
        skipWhitespace();
        if (readTokenAndAdvance(CssTokenType.ROUND_BRACKET_RIGHT) == null)
        {
            // Error. Unexpected token.
            return null;
        }
        return new CssNotSelector(argument);
    }

    /// <summary>
    /// Parses an argument of a :not() pseudo-class selector. The argument must be a simple selector except
    /// the :not() pseudo-class itself.
    /// </summary>
    /// <returns>
    /// The parsed selector, or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parseNotSelectorArgument()
    {
        if (currentTokenStartsAClass())
        {
            return parseClassSelector();
        }

        if (mCurrentToken.isDelim('*') || mCurrentToken.isDelim('|'))
        {
            return parseTypeOrUniversalSelector();
        }

        switch (mCurrentToken.getType())
        {
            case CssTokenType.IDENT:
                return parseTypeOrUniversalSelector();
            case CssTokenType.HASH:
                return parseIdSelector();
            case CssTokenType.SQUARE_BRACKET_LEFT:
                return parseAttributeSelector();
            case CssTokenType.COLON:
                return parsePseudoSelector();
            default:
            {
                // Parsing error. Unexpected token.
                return null;
            }
        }
    }

    /// <summary>
    /// Parses either a pseudo-class or a pseudo-element selector.
    /// </summary>
    /// <returns>
    /// The parsed selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parsePseudoSelector()
    {
        if (readTokenAndAdvance(CssTokenType.COLON) == null)
        {
            // Error. Unexpected token.
            return null;
        }

        switch (mCurrentToken.getType())
        {
            case CssTokenType.COLON:
            {
                advance();
                return parsePseudoElement();
            }
            case CssTokenType.IDENT:
            {
                switch (gStringSwitchMap.of(StringUtil.asciiLowerCase(((CssTextToken)mCurrentToken).getText())))
                {
                    case /*"before"*/0:
                    case /*"after"*/1:
                    case /*"first-line"*/2:
                    case /*"first-letter"*/3:
                        // For historical reasons, these pseudo-elements can be written either with one or with two
                        // leading colons.
                        return parsePseudoElement();
                    default:
                        return parsePseudoClassWithoutArguments();
                }
            }
            case CssTokenType.FUNCTION:
            {
                if ("not".equals(StringUtil.asciiLowerCase(((CssTextToken)mCurrentToken).getText())))
                {
                    return parseNotSelector();
                }
                else
                {
                    return parsePseudoClassWithArguments();
                }
            }
            default:
            {
                // Parsing error. Unexpected token.
                return null;
            }
        }
    }

    /// <summary>
    /// Parses a pseudo-class selector that has an argument.
    /// </summary>
    /// <returns>
    /// The parsed pseudo-class selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parsePseudoClassWithArguments()
    {
        CssTextToken functionToken = (CssTextToken)readTokenAndAdvance(CssTokenType.FUNCTION);
        if (functionToken == null)
        {
            // Error. Unexpected token.
            return null;
        }

        skipWhitespace();

        // Read tokens of the pseudo-class argument.
        ArrayList<CssToken> argumentTokens = new ArrayList<CssToken>();
        boolean whitespaceIsExpected = false;
        while ((mCurrentToken.getType() != CssTokenType.ROUND_BRACKET_RIGHT) && !mCurrentToken.isEof())
        {
            if (mCurrentToken.isWhitespace())
            {
                if (whitespaceIsExpected)
                {
                    // Argument tokens can be delimeted by whitespace. The CSS parser combines adjacent whitespace
                    // characters into one whitespace token, so adjacent whitespace tokens are not expected.
                    msArrayList.add(argumentTokens, mCurrentToken);
                    advance();
                    whitespaceIsExpected = false;
                }
                else
                {
                    // Parsing error. Unexpected token.
                    return null;
                }
            }
            else
            {
                msArrayList.add(argumentTokens, mCurrentToken);
                advance();
                whitespaceIsExpected = true;
            }
        }
        if (readTokenAndAdvance(CssTokenType.ROUND_BRACKET_RIGHT) == null)
        {
            // Error. Unexpected token.
            return null;
        }

        return createPseudoClassWithArguments(functionToken.getText(), argumentTokens);
    }

    /// <summary>
    /// The pseudo-class selector factory. Creates a pseudo-class selector by its name and argument tokens.
    /// </summary>
    /// <returns>
    /// The created pseudo-class selector or <c>null</c> in case of an error.
    /// </returns>
    private static CssSimpleSelector createPseudoClassWithArguments(String name, ArrayList<CssToken> argumentTokens)
    {
        switch (gStringSwitchMap.of(StringUtil.asciiLowerCase(name)))
        {
            case /*"lang"*/4:
            {
                String language = CssLangArgumentParser.parse(argumentTokens);
                return (language != null)
                    ? new CssLangSelector(language)
                    : null;
            }
            case /*"nth-child"*/5:
            {
                CssIndexArgument argument = CssIndexArgumentParser.parse(argumentTokens);
                return (argument != null)
                    ? new CssNthChildSelector(argument)
                    : null;
            }
            case /*"nth-last-child"*/6:
            {
                CssIndexArgument argument = CssIndexArgumentParser.parse(argumentTokens);
                return (argument != null)
                    ? new CssNthLastChildSelector(argument)
                    : null;
            }
            case /*"nth-of-type"*/7:
            {
                CssIndexArgument argument = CssIndexArgumentParser.parse(argumentTokens);
                return (argument != null)
                    ? new CssNthOfTypeSelector(argument)
                    : null;
            }
            case /*"nth-last-of-type"*/8:
            {
                CssIndexArgument argument = CssIndexArgumentParser.parse(argumentTokens);
                return (argument != null)
                    ? new CssNthLastOfTypeSelector(argument)
                    : null;
            }
            default:
            {
                // If a pseudo-class is unsupported, the selector must be considered invalid.
                return null;
            }
        }
    }

    /// <summary>
    /// Parses a pseudo-class selector that does not have an argument.
    /// </summary>
    /// <returns>
    /// The parsed pseudo-class selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parsePseudoClassWithoutArguments()
    {
        CssTextToken nameToken = (CssTextToken)readTokenAndAdvance(CssTokenType.IDENT);
        if (nameToken == null)
        {
            // Error. Unexpected token.
            return null;
        }
        String name = StringUtil.asciiLowerCase(nameToken.getText());
        switch (gStringSwitchMap.of(name))
        {
            case /*"root"*/9:
                return new CssRootSelector();
            case /*"empty"*/10:
                return new CssEmptySelector();
            case /*"first-child"*/11:
                // :first-child is same as :nth-child(1)
                return new CssNthChildSelector(new CssIndexArgument(0, 1));
            case /*"last-child"*/12:
                // :last-child is same as :nth-last-child(1)
                return new CssNthLastChildSelector(new CssIndexArgument(0, 1));
            case /*"first-of-type"*/13:
                // :first-of-type is same as :nth-of-type(1)
                return new CssNthOfTypeSelector(new CssIndexArgument(0, 1));
            case /*"last-of-type"*/14:
                // :last-of-type is same as :nth-last-of-type(1)
                return new CssNthLastOfTypeSelector(new CssIndexArgument(0, 1));
            case /*"only-child"*/15:
                return new CssOnlyChildSelector();
            case /*"only-of-type"*/16:
                return new CssOnlyOfTypeSelector();
            case /*"link"*/17:
                return new CssLinkSelector();
            case /*"visited"*/18:
                return new CssVisitedSelector();
            case /*"active"*/19:
                return new CssActiveSelector();
            case /*"hover"*/20:
                return new CssHoverSelector();
            default:
                // If a pseudo-class is unsupported, the selector must be considered invalid.
                return null;
        }
    }

    /// <summary>
    /// Parses a pseudo-element selector.
    /// </summary>
    /// <returns>
    /// The parsed pseudo-element selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parsePseudoElement()
    {
        CssTextToken nameToken = (CssTextToken)readTokenAndAdvance(CssTokenType.IDENT);
        if (nameToken == null)
        {
            // Error. Unexpected token.
            return null;
        }
        String name = nameToken.getText();
        /*HtmlElementPart*/int part;
        switch (gStringSwitchMap.of(StringUtil.asciiLowerCase(name)))
        {
            case /*"before"*/0:
                part = HtmlElementPart.BEFORE;
                break;
            case /*"after"*/1:
                part = HtmlElementPart.AFTER;
                break;
            case /*"first-letter"*/3:
                part = HtmlElementPart.FIRST_LETTER;
                break;
            case /*"first-line"*/2:
                part = HtmlElementPart.FIRST_LINE;
                break;
            default:
                // If a pseudo-element is unsupported, the selector must be considered invalid.
                return null;
        }
        // WORDSJAVA-2911 use lower case for the name.
        return new CssPseudoElementSelector(part, StringUtil.asciiLowerCase(name));
    }

    /// <summary>
    /// Parses an attribute selector that has the form "'['name[filter]']'" (the filter expression is optional;
    /// it determines the type of the attribute selector).
    /// </summary>
    /// <returns>
    /// The parsed attribute selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parseAttributeSelector()
    {
        // Read a left square bracket character.
        if (readTokenAndAdvance(CssTokenType.SQUARE_BRACKET_LEFT) == null)
        {
            // Error. Unexpected token.
            return null;
        }

        skipWhitespace();

        // Read a namespace.
        // By default, attributes have no namespace. A default namespace, when declared in CSS, applies only
        // to type selectors.
        CssNamespace parsedNamespace = parseNamespace(CssNamespace.EMPTY);
        if (parsedNamespace == null)
        {
            // Error. Cannot parse the namespace.
            return null;
        }

        // Read an attribute name.
        CssTextToken attributeNameToken = (CssTextToken)readTokenAndAdvance(CssTokenType.IDENT);
        if (attributeNameToken == null)
        {
            // Error. Unexpected token.
            return null;
        }

        skipWhitespace();

        // Attribute names are case-insensitive in HTML. The HTML parser converts them to lowercase,
        // and the CSS parser should do the same.
        String attributeName = StringUtil.asciiLowerCase(attributeNameToken.getText());

        CssSimpleSelector result = parseAttributeFilter(parsedNamespace, attributeName);

        // Read a right square bracket character.
        if (readTokenAndAdvance(CssTokenType.SQUARE_BRACKET_RIGHT) == null)
        {
            // Error. Unexpected token.
            return null;
        }

        return result;
    }

    /// <summary>
    /// Parses a filter expression of an attribute selector and creates an attribute selector corresponding to the parsed
    /// filter expression.
    /// </summary>
    /// <returns>
    /// The parsed attribute selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parseAttributeFilter(CssNamespace attributeNamespace, String attributeLocalName)
    {
        // Remember the equality operator char to choose the right selector type later.
        char operatorChar;

        if (mCurrentToken.getType() == CssTokenType.DELIM)
        {
            operatorChar = ((CssDelimToken)mCurrentToken).getValue();
            if ((operatorChar == '^') ||
                (operatorChar == '$') ||
                (operatorChar == '*') ||
                (operatorChar == '~') ||
                (operatorChar == '|'))
            {
                advance(); // Prefix of complex match symbols like "^=" or "$=".
                if (!mCurrentToken.isDelim('='))
                {
                    // Parsing error. Unexpected token.
                    return null;
                }
            }
            advance(); // '='
        }
        else if (mCurrentToken.getType() == CssTokenType.SQUARE_BRACKET_RIGHT)
        {
            // No filter, just an attribute name.
            return new CssAttributeNameSelector(attributeNamespace, attributeLocalName);
        }
        else
        {
            // Parsing error. Unexpected token.
            return null;
        }

        skipWhitespace();

        String value = parseAttributeFilterValue();
        if (value == null)
        {
            // Parsing error. Unexpected token type.
            return null;
        }

        skipWhitespace();

        // Create a selector of required type.
        switch (operatorChar)
        {
            case '^':
                return new CssAttributePrefixSelector(attributeNamespace, attributeLocalName, value);
            case '$':
                return new CssAttributeSuffixSelector(attributeNamespace, attributeLocalName, value);
            case '*':
                return new CssAttributeSubstringSelector(attributeNamespace, attributeLocalName, value);
            case '=':
                return new CssAttributeValueSelector(attributeNamespace, attributeLocalName, value);
            case '~':
                return new CssAttributeWordSelector(attributeNamespace, attributeLocalName, value);
            case '|':
                return new CssAttributeDashPrefixSelector(attributeNamespace, attributeLocalName, value);
            default:
                // Parsing error. Unexpected token.
                return null;
        }
    }

    /// <summary>
    /// Parses a value of an attribute filter expression. The value is optionally enclosed in single or double quotes.
    /// </summary>
    /// <returns>
    /// The parsed filter value or <c>null</c> in case of an error.
    /// </returns>
    private String parseAttributeFilterValue()
    {
        CssToken currentToken = mCurrentToken;
        switch (currentToken.getType())
        {
            case CssTokenType.IDENT:
            case CssTokenType.STRING:
                advance();
                return ((CssTextToken)currentToken).getText();
            default:
                // Parsing error. Unexpected token.
                return null;
        }
    }

    /// <summary>
    /// Parses a class selector.
    /// </summary>
    /// <returns>
    /// The parsed class selector or <c>null</c> in case of an error.
    /// </returns>
    private CssClassSelector parseClassSelector()
    {
        advance(); // Dot character.
        CssTextToken classNameToken = (CssTextToken)readTokenAndAdvance(CssTokenType.IDENT);
        if (classNameToken == null)
        {
            // Error. Unexpected token.
            return null;
        }
        return new CssClassSelector(classNameToken.getText());
    }

    /// <summary>
    /// Parses an ID selector.
    /// </summary>
    /// <returns>
    /// The parsed selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parseIdSelector()
    {
        // Read the identifier.
        CssHashToken idToken = (CssHashToken)readTokenAndAdvance(CssTokenType.HASH);
        if ((idToken == null) || !idToken.isIdentifier())
        {
            // Error. Unexpected token or the ID selector doesn't specify a valid identifier.
            return null;
        }
        return new CssIdSelector(idToken.getText());
    }

    /// <summary>
    /// Parses either a type selector or a universal selector.
    /// </summary>
    /// <returns>
    /// The parsed selector or <c>null</c> in case of an error.
    /// </returns>
    private CssSimpleSelector parseTypeOrUniversalSelector()
    {
        CssNamespace parsedNamespace = parseNamespace(mDeclaredCssNamespaces.getDefaultNamespaceForElements());
        if (parsedNamespace == null)
        {
            // Error. Cannot parse the namespace.
            return null;
        }

        if (mCurrentToken.isDelim('*'))
        {
            advance();
            return new CssUniversalSelector(parsedNamespace);
        }

        if (mCurrentToken.getType() == CssTokenType.IDENT)
        {
            CssTextToken elementNameToken = (CssTextToken)mCurrentToken;
            advance();

            // Element names are case-insensitive in HTML. The HTML parser converts them to lowercase.
            // The CSS parser should do the same.
            String elementName = StringUtil.asciiLowerCase(elementNameToken.getText());

            return new CssTypeSelector(parsedNamespace, elementName);
        }
        else
        {
            // Parsing error. Unexpected token.
            return null;
        }
    }

    /// <summary>
    /// Parses sequences "tag", "|tag", and "namespace|tag". Extracts the namespace part of the sequences.
    /// </summary>
    /// <param name="defaultNamespace">The namespace that is returned if the sequence contains no namespace.</param>
    /// <returns>
    /// The extracted namespace part of the sequence. If the sequence contains no namespace, the specified default namespace
    /// is returned. In case of a parsing error, <c>null</c> is returned.
    /// </returns>
    private CssNamespace parseNamespace(CssNamespace defaultNamespace)
    {
        if (mCurrentToken.isDelim('*') || (mCurrentToken.getType() == CssTokenType.IDENT))
        {
            // Choose between two possible token sequences: "namespace|tag" and just "tag".
            // Have to look ahead one token to disambiguate the input.
            // We need to disambiguate between the scenarios where a dash character is a namespace delimeter
            // like in "[ns|tag='abc']" and where it is a part of the "dash-match" operator ("|="), like in "[tag|='abc']".
            boolean nextTokenIsNamespaceDelimeter = mNextToken.isDelim('|') && !tokenAfterNextIsDelim('=');
            if (nextTokenIsNamespaceDelimeter)
            {
                // namespace|tag
                CssNamespace result;
                if (mCurrentToken.getType() == CssTokenType.IDENT)
                {
                    String prefix = ((CssTextToken)mCurrentToken).getText();
                    CssNamespace declaredNamespace = mDeclaredCssNamespaces.getNamespace(prefix);
                    if (declaredNamespace == null)
                    {
                        // Parsing error. The referenced namespace is not declared.
                        return null;
                    }
                    else
                    {
                        result = declaredNamespace;
                    }
                }
                else
                {
                    // The current token is an asterisk.
                    result = CssNamespace.ANY;
                }
                advance(); // namespace token
                advance(); // '|'
                return result;
            }
            else
            {
                // Just tag.
                return defaultNamespace;
            }
        }
        else if (mCurrentToken.isDelim('|'))
        {
            advance(); // '|'
            return CssNamespace.EMPTY;
        }
        else
        {
            // Parsing error. Unexpected token.
            return null;
        }
    }

    /// <summary>
    /// Advances the parser by one token.
    /// </summary>
    private void advance()
    {
        if (mTokenIndex < mTokens.size())
        {
            ++mTokenIndex;
            mCurrentToken = mNextToken;
            int nextTokenIndex = mTokenIndex + 1;
            mNextToken = (nextTokenIndex < mTokens.size())
                ? mTokens.get(nextTokenIndex)
                : CssToken.EOF;
        }
        else
        {
            Debug.fail("Attempt to read past the end of a token list.");
        }
    }

    /// <summary>
    /// Verifies that the current token has the specified type, returns the current token, and advances the parser
    /// by one token.
    /// </summary>
    /// <returns>
    /// The token of the specified type or <c>null</c> if the type of the current token is not equal to the specified type.
    /// </returns>
    private CssToken readTokenAndAdvance(/*CssTokenType*/int tokenType)
    {
        CssToken result = mCurrentToken;
        if (result.getType() != tokenType)
        {
            return null;
        }
        advance();
        return result;
    }

    /// <summary>
    /// Advances the parser while the current token is the whitespace token.
    /// </summary>
    private void skipWhitespace()
    {
        if (mCurrentToken.isWhitespace())
        {
            advance();
        }
    }

    /// <summary>
    /// Checks if the current token and the next token form a valid ".class" selector.
    /// </summary>
    private boolean currentTokenStartsAClass()
    {
        // '.' delimeter followed by an identifier: ".class"
        return mCurrentToken.isDelim('.') && (mNextToken.getType() == CssTokenType.IDENT);
    }

    /// <summary>
    /// Checks if the token after the next token is the specified delimeter.
    /// </summary>
    /// <remarks>
    /// We need two token look ahead to disambiguate between "ns|tag=..." and "tag|=...", where "|=" is a match operator.
    /// </remarks>
    private boolean tokenAfterNextIsDelim(char delimChar)
    {
        int nextTokenIndex = mTokenIndex + 2;
        if (nextTokenIndex >= mTokens.size())
        {
            return false;
        }
        CssToken nextToken = mTokens.get(nextTokenIndex);
        return nextToken.isDelim(delimChar);
    }

    /// <summary>
    /// List of tokens representing the selector.
    /// </summary>
    private /*final*/ ArrayList<CssToken> mTokens;

    /// <summary>
    /// The index of the token being parsed currently.
    /// </summary>
    private int mTokenIndex;

    /// <summary>
    /// The token currently being parsed.
    /// </summary>
    private CssToken mCurrentToken;

    /// <summary>
    /// The next token to parse.
    /// </summary>
    private CssToken mNextToken;

    /// <summary>
    /// CSS namespace declarations known at the moment.
    /// </summary>
    private /*final*/ CssNamespacePrefixes mDeclaredCssNamespaces;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"before",
		"after",
		"first-line",
		"first-letter",
		"lang",
		"nth-child",
		"nth-last-child",
		"nth-of-type",
		"nth-last-of-type",
		"root",
		"empty",
		"first-child",
		"last-child",
		"first-of-type",
		"last-of-type",
		"only-child",
		"only-of-type",
		"link",
		"visited",
		"active",
		"hover"
	);

}


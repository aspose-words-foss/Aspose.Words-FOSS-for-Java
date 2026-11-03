// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.UriUtil;
import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;


/// <summary>
/// Parses CSS: stylesheets, declarations, or values.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/css-syntax-3/
/// </remarks>
class CssParser
{
    private CssParser(
        /*CssDocumentMode*/int documentMode,
        String stylesheetUri,
        CssNamespacePrefixResolver multipleStylesheetNamespacePrefixResolver)
    {
        if (stylesheetUri != null)
        {
            String stylesheetBaseUri = UriUtil.getDirectoryHref(stylesheetUri);
            if (StringUtil.hasChars(stylesheetBaseUri) && (!".".equals(stylesheetBaseUri)))
            {
                mStylesheetBaseUri = stylesheetBaseUri;
            }
        }

        mIsInQuirksMode = documentMode == CssDocumentMode.QUIRKS;
        mMultipleStylesheetNamespacePrefixResover = multipleStylesheetNamespacePrefixResolver;
    }

    /// <summary>
    /// Parses a CSS stylesheet.
    /// </summary>
    static ArrayList<CssRule> parseStandaloneStylesheet(
        String css,
        /*CssDocumentMode*/int documentMode)
    {
        CssParser parser = new CssParser(
            documentMode,
            "",
            new CssNamespacePrefixResolver());
        return parser.parseRuleList(new CssTokenizerTokenSource(css));
    }

    /// <summary>
    /// Parses a CSS stylesheet. Makes sure namespaces of this stylesheet don't re-use prefixes of namespaces imported by
    /// other stylesheets.
    /// </summary>
    static ArrayList<CssRule> parseStylesheet(
        String css,
        /*CssDocumentMode*/int documentMode,
        String stylesheetUri,
        CssNamespacePrefixResolver multipleStylesheetNamespacePrefixResolver)
    {
        CssParser parser = new CssParser(
            documentMode,
            stylesheetUri,
            multipleStylesheetNamespacePrefixResolver);
        return parser.parseRuleList(new CssTokenizerTokenSource(css));
    }

    /// <summary>
    /// Parses a CSS declaration list. For example, content of a HTML "style" attribute.
    /// </summary>
    static CssDeclarationCollection parseDeclarations(String css, /*CssDocumentMode*/int documentMode)
    {
        // CSS namespaces are used in CSS selectors only. Since CSS declarations cannot contain selectors, it is okay that
        // here we don't pass any CSS namespace prefix collector to the CSS parser.
        CssParser parser = new CssParser(
            documentMode,
            "",
            null);

        // Note that if at-rules are expected, they are allowed to end with {}-blocks and have no semicolon character
        // at the end. Most modern browsers, however, don't follow the CSS specification and require all components
        // of a declaration list to be separated by semicolon characters.
        // According to the CSS specification, the following declaration list:
        //   "@media screen { color: blue } color: red"
        // must be equivalent to:
        //   "color: red"
        // However, modern browsers don't follow the specification and consider such a declaration list totally invalid.
        // We mimic the browsers' behavior.
        return parser.consumeDeclarationList(new CssTokenizerTokenSource(css), false);
    }

    /// <summary>
    /// Parses an individual CSS value.
    /// </summary>
    static CssValue parseValue(String css)
    {
        CssTokenSource tokenSource = new CssTokenizerTokenSource(css);
        tokenSource.consumeOptionalWhitespace();
        CssValue value = parseValue(tokenSource, "");

        if ((value == null) ||
            (value.getValueType() == CssValueType.COMMA) ||
            (value.getValueType() == CssValueType.SOLIDUS))
        {
            return null;
        }

        return tokenSource.consumeWhitespaceAndToken().isEof()
            ? value
            : null;
    }

    /// <summary>
    /// Parses a space-separated list of CSS values.
    /// </summary>
    static CssValueList parseValueList(String css)
    {
        CssTokenizerTokenSource tokenSource = new CssTokenizerTokenSource(css);
        CssValueList result = parseSpaceSeparatedValueList(tokenSource, "");
        return result;
    }

    /// <summary>
    /// Parses a media query string.
    /// </summary>
    static CssMediaQueryList parseMediaQueryList(String mediaQuery)
    {
        return consumeMediaQueryList(new CssTokenizerTokenSource(mediaQuery));
    }

    /// <summary>
    /// Parses a stylesheet (list of rules).
    /// </summary>
    private ArrayList<CssRule> parseRuleList(CssTokenSource tokenSource)
    {
        while (tokenSource.hasMoreTokens())
        {
            CssToken token = tokenSource.consume();

            if (mRuleLists.isTopLevel() && ((token.getType() == CssTokenType.CDC) || (token.getType() == CssTokenType.CDO)))
            {
                // HTML comment tokens '<--' and '-->' are ignored at the top level of stylesheets.
                continue;
            }

            if (!mRuleLists.isTopLevel() && (token.getType() == CssTokenType.BLOCK_BRACKET_RIGHT))
            {
                // Closing block brackets end nested rule lists if they appear at a nested level (not at the top level).
                endNestedMediaRule();
                continue;
            }

            switch (token.getType())
            {
                case CssTokenType.WHITESPACE:
                {
                    // Skip whitespace.
                    break;
                }
                case CssTokenType.AT_KEYWORD:
                {
                    // at-rule names are ASCII case-insensitive.
                    String name = ((CssTextToken)token).getText();
                    name = StringUtil.asciiLowerCase(name);

                    // WORDSNET-24021 @media rules are processed in a special way, because they contain nested rule
                    // lists. In order to avoid stack overflow exceptions we cannot parse those nested rule lists
                    // recursively and we have to use an iterative approach.
                    if ("media".equals(name))
                    {
                        ArrayList<CssToken> prelude = consumeAtRulePrelude(tokenSource);
                        CssToken blockStartToken = tokenSource.consume(); // ';', '{', or EOF.

                        // Semicolon and EOF tokens end a @media rule right away. Such a rule is malformed since it has
                        // no block, so we skip it. We process only @media rules that do have a block.
                        if (blockStartToken.getType() == CssTokenType.BLOCK_BRACKET_LEFT)
                        {
                            beginNestedMediaRule(prelude);
                        }
                    }
                    else
                    {
                        tokenSource.reconsume();
                        CssRule atRule = consumeAtRule(tokenSource, mRuleLists.isTopLevel());
                        addRuleToCurrentList(atRule);
                    }
                    break;
                }
                default:
                {
                    tokenSource.reconsume();
                    CssStyleRule styleRule = consumeStyleRule(tokenSource);
                    addRuleToCurrentList(styleRule);
                    break;
                }
            }
        }

        // At EOF, CSS parsers silently close all nested rule lists that are not closed correctly and don't have closing
        // '}' brackets.
        while (!mRuleLists.isTopLevel())
        {
            endNestedMediaRule();
        }

        return mRuleLists.getCurrentRuleList();
    }

    private void beginNestedMediaRule(ArrayList<CssToken> prelude)
    {
        CssMediaQueryList mediaQueryList = consumeMediaQueryList(new CssListTokenSource(prelude));
        mRuleLists.beginNestedMediaRule(mediaQueryList);

        // @import and @namespace rules must precede any well-formed @media rules.
        mCanProcessMoreImportRules = false;
        mCanProcessMoreNamespaceRules = false;
    }

    private void endNestedMediaRule()
    {
        CssMediaRule mediaRule = buildMediaRule(mRuleLists.getCurrentMediaQueryList(), mRuleLists.getCurrentRuleList());
        mRuleLists.endNestedMediaRule();
        addRuleToCurrentList(mediaRule);
    }

    private void addRuleToCurrentList(CssRule rule)
    {
        if (rule == null)
        {
            return;
        }

        msArrayList.add(mRuleLists.getCurrentRuleList(), rule);

        // Note that we don't check for @charset here, because our parser doesn't support @charset rules
        // and always ignores them.
        if (mRuleLists.isTopLevel() && !(rule instanceof CssImportRule))
        {
            // @import rules must precede other well-formed rules except @charset and other @import rules.
            mCanProcessMoreImportRules = false;
            // @namespace rules must precede other well-formed rules except @charset, @import, and other @namespace rules.
            mCanProcessMoreNamespaceRules = false;
        }
    }

    private CssStyleRule consumeStyleRule(CssTokenSource tokenSource)
    {
        ArrayList<CssToken> selectorTokens = new ArrayList<CssToken>();
        boolean reachedEof = false;
        do
        {
            CssToken token = tokenSource.consume();
            switch (token.getType())
            {
                case CssTokenType.EOF:
                    tokenSource.reconsume();
                    reachedEof = true;
                    break;
                case CssTokenType.BLOCK_BRACKET_LEFT:
                    ArrayList<CssToken> declarationTokens = new ArrayList<CssToken>();
                    consumeBlock(tokenSource, declarationTokens);
                    return buildStyleRule(selectorTokens, declarationTokens);
                default:
                    tokenSource.reconsume();
                    consumeComponent(tokenSource, selectorTokens);
                    break;
            }
        } while (!reachedEof);

        // Reached end of file.
        return null;
    }

    private CssRule consumeAtRule(CssTokenSource tokenSource, boolean isTopLevel)
    {
        // at-rule names are ASCII case-insensitive.
        String name = ((CssTextToken)tokenSource.consume()).getText();
        name = StringUtil.asciiLowerCase(name);

        ArrayList<CssToken> prelude = consumeAtRulePrelude(tokenSource);
        CssToken blockStartToken = tokenSource.consume(); // ';', '}', or EOF.

        // Consume the block if there is any.
        ArrayList<CssToken> block = null;
        if (blockStartToken.getType() == CssTokenType.BLOCK_BRACKET_LEFT)
        {
            block = new ArrayList<CssToken>();
            consumeBlock(tokenSource, block);
        }

        CssRule atRule = parseAtRule(name, prelude, block, isTopLevel);
        return atRule;
    }

    private static ArrayList<CssToken> consumeAtRulePrelude(CssTokenSource tokenSource)
    {
        ArrayList<CssToken> prelude = new ArrayList<CssToken>();
        CssToken token = tokenSource.consume();
        while ((token.getType() != CssTokenType.EOF) &&
            (token.getType() != CssTokenType.SEMICOLON) &&
            (token.getType() != CssTokenType.BLOCK_BRACKET_LEFT))
        {
            tokenSource.reconsume();
            consumeComponent(tokenSource, prelude);
            token = tokenSource.consume();
        }
        tokenSource.reconsume();
        return prelude;
    }

    private static void skipAtRule(CssTokenSource tokenSource)
    {
        // Any at-rule starts with an at-token followed by a possibly empty prelude (sequence of components) and is ended
        // with a semicolon, EOF, or a '{'-block.
        boolean atRuleEndReached = false;
        while (!atRuleEndReached)
        {
            CssToken token = tokenSource.consume();
            switch (token.getType())
            {
                case CssTokenType.SEMICOLON:
                case CssTokenType.EOF:
                    // This at-rule ends with a semicolon or EOF.
                    atRuleEndReached = true;
                    break;
                case CssTokenType.BLOCK_BRACKET_LEFT:
                    // This at-rule ends with a block. Skip it too.
                    consumeBlock(tokenSource, null);
                    atRuleEndReached = true;
                    break;
                default:
                    // Skip prelude components.
                    tokenSource.reconsume();
                    consumeComponent(tokenSource, null);
                    break;
            }
        }
    }

    private CssDeclarationCollection consumeDeclarationList(CssTokenSource tokenSource, boolean expectNestedAtRules)
    {
        CssDeclarationCollectionBuilder result = new CssDeclarationCollectionBuilder();
        while (true)
        {
            CssToken token = tokenSource.consumeWhitespaceAndToken();
            if (token.isEof())
            {
                break;
            }

            if (expectNestedAtRules && (token.getType() == CssTokenType.AT_KEYWORD))
            {
                // This might be, for example, a margin at-rule (like @top-left) nested in a @page rule.
                // We don't support at-rules in declaration lists so we skip the rule.
                skipAtRule(tokenSource);
            }
            else
            {
                switch (token.getType())
                {
                    case CssTokenType.SEMICOLON:
                    {
                        // Empty declaration. Skip it.
                        break;
                    }
                    case CssTokenType.IDENT:
                    {
                        CssDeclarationCollection declarations = consumeAndParseDeclaration(
                            tokenSource,
                            ((CssTextToken)token).getText());
                        if (declarations != null)
                        {
                            result.addOrReplaceIfMoreOrEquallyImportant(declarations);
                        }
                        break;
                    }
                    default:
                    {
                        tokenSource.reconsume();
                        while (true)
                        {
                            CssToken nextToken = tokenSource.consume();
                            if ((nextToken.getType() == CssTokenType.SEMICOLON) || nextToken.isEof())
                            {
                                break;
                            }
                            tokenSource.reconsume();
                            consumeComponent(tokenSource, null);
                        }
                        break;
                    }
                }
            }
        }

        return result.getDeclarations();
    }

    private CssDeclarationCollection consumeAndParseDeclaration(CssTokenSource tokenSource, String name)
    {
        Debug.assertTrue(StringUtil.hasChars(name));

        // A colon character between the name and the value of a property.
        CssToken token = tokenSource.consumeWhitespaceAndToken();
        if (token.getType() != CssTokenType.COLON)
        {
            if ((token.getType() != CssTokenType.SEMICOLON) && !token.isEof())
            {
                tokenSource.reconsume();
            }
            return null;
        }

        // Tokens that constitute the value of the property.
        ArrayList<CssToken> value = new ArrayList<CssToken>();
        consumeComponentSequence(tokenSource, CssTokenType.SEMICOLON, value);
        tokenSource.consume(); // ';' or EOF

        // Check if the declaration is important. Look for trailing "!important" tokens with optional whitespace and remove
        // them if found.
        boolean isImportant = parseAndRemoveImportantKeyword(value);

        return parseDeclaration(name, value, isImportant);
    }

    private static boolean parseAndRemoveImportantKeyword(ArrayList<CssToken> valueTokens)
    {
        // Search backward for the "important" keyword.
        int i = valueTokens.size() - 1;
        for (; i >= 0; i--)
        {
            CssToken valueToken = valueTokens.get(i);
            if (valueToken.getType() == CssTokenType.IDENT)
            {
                String asciiCaseInsensitiveText = StringUtil.asciiLowerCase(((CssTextToken)valueToken).getText());
                if ("important".equals(asciiCaseInsensitiveText))
                {
                    break;
                }
            }
            if (!valueToken.isWhitespace())
            {
                // Stop parsing if we meet anything but "important" or whitespace.
                return false;
            }
        }

        // Return if the value contains only the "important" identifier followed by whitespace.
        i--;
        if (i < 0)
        {
            return false;
        }

        // Search backward for the "!" character.
        for (; i >= 0; i--)
        {
            CssToken valueToken = valueTokens.get(i);
            if (valueToken.isDelim('!'))
            {
                // Remove the "!important" part from the value.
                msArrayList.removeRange(valueTokens, i, valueTokens.size() - i);
                return true;
            }
            if (!valueToken.isWhitespace())
            {
                // Stop parsing if we meet anything but "!" or whitespace.
                return false;
            }
        }

        return false;
    }

    private CssRule parseAtRule(
        String name,
        ArrayList<CssToken> preludeTokens,
        ArrayList<CssToken> blockTokens,
        boolean isTopLevel)
    {
        // at-rule names are ASCII case-insensitive.
        Debug.assertTrue(StringUtil.isAsciiLowerCase(name));

        switch (gStringSwitchMap.of(name))
        {
            case /*"import"*/0:
            {
                if (mCanProcessMoreImportRules)
                {
                    return parseImportRule(preludeTokens, blockTokens, mStylesheetBaseUri);
                }
                // @import rules are ignored if they come after other rules.
                return null;
            }
            case /*"namespace"*/1:
            {
                // Note that @namespace rules are processed internally by the parser in order to make declared
                // namespaces available to parsing of style rules.
                // @namespace rules cannot be nested inside other at-rules and thus they are ignored if we're parsing
                // a nested (not top-level) stylesheet.
                if (mCanProcessMoreNamespaceRules && isTopLevel)
                {
                    parseAndProcessNamespaceRule(preludeTokens, blockTokens);
                }
                // Note that we always return "null" here even if we successfully process the @namespace rule. That's because
                // @namespace rules are fully processed inside the parser and don't become visible to the calling code.
                return null;
            }
            case /*"page"*/2:
            {
                return parsePageRule(preludeTokens, blockTokens);
            }
            case /*"font-face"*/3:
            {
                return parseFontFaceRule(blockTokens);
            }
            case /*"list"*/4:
            {
                return parseListRule(preludeTokens, blockTokens);
            }
            default:
            {
                // Unsupported at-rule.
                return null;
            }
        }
    }

    private void parseAndProcessNamespaceRule(ArrayList<CssToken> preludeTokens, ArrayList<CssToken> blockTokens)
    {
        // @namespace rules cannot have a block.
        if (blockTokens != null)
        {
            return;
        }

        CssTokenSource stream = new CssListTokenSource(preludeTokens);
        CssToken token = stream.consumeWhitespaceAndToken();

        // Optional prefix.
        String prefix = "";
        if (token.getType() == CssTokenType.IDENT)
        {
            // Namespace prefixes are case-sensitive.
            prefix = ((CssTextToken)token).getText();

            token = stream.consumeWhitespaceAndToken();
        }

        // Namespace name.
        if ((token.getType() != CssTokenType.STRING) && (token.getType() != CssTokenType.URL))
        {
            return;
        }
        String name = ((CssTextToken)token).getText();

        token = stream.consumeWhitespaceAndToken();
        if (!token.isEof())
        {
            return;
        }

        // Get a prefix that will be unique among all parsed stylesheets.
        // Individual stylesheets may use same namespace prefixes for different namespaces. If we're going to combine
        // rules imported from different stylesheets, we need to make sure all namespace names use unique prefixes.
        String uniquePrefix;
        CssNamespace redefinedNamespace = StringUtil.hasChars(prefix)
            ? mDeclaredNamespaces.getNamespace(prefix)
            : mDeclaredNamespaces.getDefaultNamespaceForElements();
        if ((redefinedNamespace != null) && redefinedNamespace.isSpecific())
        {
            // If we have already processed a namespace with the same prefix in this stylesheet, re-use the unique prefix
            // that we've computed for that existing namespace.
            uniquePrefix = (mMultipleStylesheetNamespacePrefixResover != null)
                ? mMultipleStylesheetNamespacePrefixResover.redefinePrefix(redefinedNamespace.getName(), name)
                : redefinedNamespace.getPrefix();
        }
        else
        {
            // We haven't yet processed a namespace with this prefix in this stylesheet but we might have met the same
            // prefix in other stylesheets. Make sure different namespaces don't have the same prefix in parsed rules.
            uniquePrefix = (mMultipleStylesheetNamespacePrefixResover != null)
                ? mMultipleStylesheetNamespacePrefixResover.getPrefix(name, prefix)
                : prefix;
        }

        // If the namespace rule is well-formed, remember the namespace.
        // Note that "prefix" is unique to this stylesheet only and "uniquePrefix" is unique among all stylesheets processed
        // by this instance of the CSS parser.
        mDeclaredNamespaces.setNamespace(prefix, new CssNamespace(uniquePrefix, name));

        // @import rules must precede other well-formed rules except @charset and other @import rules.
        // We've just parsed and processed a well-formed @namespace rule, so we cannot accept @import rules any more.
        mCanProcessMoreImportRules = false;
    }

    private static CssImportRule parseImportRule(
        ArrayList<CssToken> preludeTokens,
        ArrayList<CssToken> blockTokens,
        String baseUri)
    {
        // @import rules cannot have a block.
        if (blockTokens != null)
        {
            return null;
        }

        CssTokenSource tokenSource = new CssListTokenSource(preludeTokens);

        // URL of the stylesheet to import.
        CssToken token = tokenSource.consumeWhitespaceAndToken();
        if ((token.getType() != CssTokenType.STRING) && (token.getType() != CssTokenType.URL))
        {
            return null;
        }
        String url = resolveRelativeUri(baseUri, ((CssTextToken)token).getText());

        // If we don't support the media query, the @import rule is still considered well-formed and it must not be ignored.
        CssMediaQueryList mediaQueryList = consumeMediaQueryList(tokenSource);
        return new CssImportRule(url, mediaQueryList);
    }

    private CssPageRule parsePageRule(ArrayList<CssToken> preludeTokens, ArrayList<CssToken> blockTokens)
    {
        CssTokenSource preludeTokenSource = new CssListTokenSource(preludeTokens);
        CssPageSelector selector = parsePageSelector(preludeTokenSource);
        if (selector == null)
        {
            // Parsing error.
            return null;
        }

        // Declarations of @page rules may contain nested margin at-rules. We don't support them but we must parse them.
        CssDeclarationCollection declarations = consumeDeclarationList(new CssListTokenSource(blockTokens), true);

        return new CssPageRule(selector, declarations);
    }

    private CssFontFaceRule parseFontFaceRule(ArrayList<CssToken> blockTokens)
    {
        CssDeclarationCollection declarations = consumeDeclarationList(new CssListTokenSource(blockTokens), false);
        return new CssFontFaceRule(declarations);
    }

    private CssListRule parseListRule(ArrayList<CssToken> preludeTokens, ArrayList<CssToken> blockTokens)
    {
        CssTokenSource preludeTokenSource = new CssListTokenSource(preludeTokens);

        CssToken token = preludeTokenSource.consumeWhitespaceAndToken();
        if ((token.getType() != CssTokenType.IDENT) || (token.getType() == CssTokenType.EOF))
        {
            return null;
        }

        Debug.assertTrue(token.getType() == CssTokenType.IDENT);
        String listDefName = ((CssTextToken)token).getText();

        String listLevelName = "";
        token = preludeTokenSource.consumeWhitespaceAndToken();
        if (token.getType() == CssTokenType.COLON)
        {
            token = preludeTokenSource.consumeWhitespaceAndToken();
            listLevelName = (token.getType() == CssTokenType.IDENT)
                ? ((CssTextToken)token).getText()
                : "";

            token = preludeTokenSource.consumeWhitespaceAndToken();
        }

        String lfoName = (token.getType() == CssTokenType.IDENT)
            ? ((CssTextToken)token).getText()
            : "";

        token = preludeTokenSource.consumeWhitespaceAndToken();
        if (token.getType() != CssTokenType.EOF)
        {
            return null;
        }

        CssListRulePrelude listRulePrelude = new CssListRulePrelude(listDefName, listLevelName, lfoName);
        CssDeclarationCollection declarations = consumeDeclarationList(new CssListTokenSource(blockTokens), false);
        return new CssListRule(listRulePrelude, declarations);
    }

    private static CssPageSelector parsePageSelector(CssTokenSource tokenSource)
    {
        CssToken token = tokenSource.consumeWhitespaceAndToken();
        if (token.isEof())
        {
            // Empty selector list. The rule is supported and it matches any page.
            return new CssPageSelector(null);
        }
        tokenSource.reconsume();

        ArrayList<String> pageNames = new ArrayList<String>();
        String currentPageName = null;
        while (true)
        {
            token = tokenSource.consumeWhitespaceAndToken();

            switch (token.getType())
            {
                case CssTokenType.COMMA:
                case CssTokenType.EOF:
                {
                    // A list item has ended. If it's a supported page name, add it to the result.
                    if (currentPageName == null)
                    {
                        // A list item has not been recognized. The list is malformed.
                        return null;
                    }

                    // A page name will be empty if it is recognized but not supported.
                    if (!"".equals(currentPageName))
                    {
                        msArrayList.add(pageNames, currentPageName);
                    }
                    currentPageName = null;

                    // If the all list has been parsed and it is well-formed, return it. Note that the list of pages may
                    // be empty if we recognize but don't support any of page selectors.
                    if (token.isEof())
                    {
                        return new CssPageSelector(msArrayList.toArray(pageNames, new String[0]));
                    }
                    break;
                }
                case CssTokenType.IDENT:
                case CssTokenType.COLON:
                {
                    // Try to parse a page name.
                    if (currentPageName == null)
                    {
                        tokenSource.reconsume();
                        currentPageName = parsePageName(tokenSource);
                    }
                    else
                    {
                        // Parsing error. We have already parsed this list item as a page name. The list is malformed.
                        return null;
                    }
                    break;
                }
                default:
                {
                    // Unexpected token. Malformed list.
                    return null;
                }
            }
        }
    }

    private static String parsePageName(CssTokenSource tokenSource)
    {
        CssToken token = tokenSource.consume();
        String pageName = null;

        if (token.getType() == CssTokenType.IDENT)
        {
            // Page names are case sensitive.
            pageName = ((CssTextToken)token).getText();

            // According to the specification, the "auto" keyword (case-insensitive) is a valid page name that, however,
            // matches no pages. The reason is that "auto" is the default value of the "page" CSS property. In other words,
            // "auto" is the default page name in CSS.
            if ("auto".equals(StringUtil.asciiLowerCase(pageName)))
            {
                // Mark the page name as "recognized but not supported".
                pageName = "";
            }

            token = tokenSource.consume();
        }

        // There may be more than one pseudo-class. Parse them all.
        while (token.getType() == CssTokenType.COLON)
        {
            // Currently, we don't support @page pseudo-classes. That's why we mark the page name
            // as "recognized but not supported".
            pageName = "";

            // Parse the name of the pseudo-class.
            token = tokenSource.consume();
            if (token.getType() != CssTokenType.IDENT)
            {
                // Parsing error. Unexpected token.
                return null;
            }

            // Check if the pseudo-class is recognized.
            String pseudoClassName = StringUtil.asciiLowerCase(((CssTextToken)token).getText());
            boolean pseudoClassIsRecognized =
                ("first".equals(pseudoClassName)) ||
                ("left".equals(pseudoClassName)) ||
                ("right".equals(pseudoClassName)) ||
                ("blank".equals(pseudoClassName));
            if (!pseudoClassIsRecognized)
            {
                // If a pseudo-class is not recognized, the whole page name list is considered invalid.
                return null;
            }

            token = tokenSource.consume();
        }

        tokenSource.reconsume();
        return pageName;
    }

    private static CssMediaQueryList consumeMediaQueryList(CssTokenSource tokenSource)
    {
        CssToken token = tokenSource.consumeWhitespaceAndToken();
        if (token.isEof())
        {
            // Empty media query list is valid and is equivalent to the "all" media type.
            return new CssMediaQueryList(true);
        }
        tokenSource.reconsume();

        ArrayList<CssToken> mediaTypeTokens = new ArrayList<CssToken>();

        String mediaType = null;
        boolean foundSupportedMediaType = false;
        while (true)
        {
            token = tokenSource.consumeWhitespaceAndToken();

            if (token.isEof() || (token.getType() == CssTokenType.COMMA))
            {
                if (("all".equals(mediaType)) || ("screen".equals(mediaType)))
                {
                    foundSupportedMediaType = true;
                }

                if (token.isEof())
                {
                    // The list is well-formed and contains only supported media types.
                    return new CssMediaQueryList(foundSupportedMediaType);
                }

                // Comma separator. Start parsing a new list item.
                mediaType = null;
            }
            else
            {
                tokenSource.reconsume();
                mediaTypeTokens.clear();
                consumeComponent(tokenSource, mediaTypeTokens);

                if ((mediaType == null) &&
                    (mediaTypeTokens.size() == 1) &&
                    (mediaTypeTokens.get(0).getType() == CssTokenType.IDENT))
                {
                    // Media types are ASCII case-insensitive.
                    mediaType = StringUtil.asciiLowerCase(((CssTextToken)token).getText());
                }
                else
                {
                    // Unexpected token. The current list item contains anything other than one indent and optional
                    // whitespace. This media query is not supported.
                    mediaType = "";
                }
            }
        }
    }

    /// <summary>
    /// Consumes a component.
    /// </summary>
    /// <remarks>
    /// A component is a token, a function with all its parameters, or a sequence of tokens in brackets.
    /// </remarks>
    private static void consumeComponent(CssTokenSource tokenSource, ArrayList<CssToken> result)
    {
        CssToken token = tokenSource.consume();
        // The calling code must make sure there is a token to consume.
        Debug.assertTrue(!token.isEof());

        // If the result is null, the calling code wants to skip the component and is not interested in its value.
        if (result != null)
        {
            msArrayList.add(result, token);
        }

        /*CssTokenType*/int stopTokenType = getComponentStopTokenType(token.getType());
        if (stopTokenType != CssTokenType.EOF)
        {
            consumeComponentSequence(tokenSource, stopTokenType, result);
            if (tokenSource.hasMoreTokens())
            {
                CssToken endingToken = tokenSource.consume();
                if (result != null)
                {
                    msArrayList.add(result, endingToken);
                }
            }
        }
    }

    /// <summary>
    /// Consumes components until the stop token is encountered.
    /// </summary>
    /// <remarks>
    /// A component is a token, a function with all its parameters, or a sequence of tokens in brackets
    /// </remarks>
    private static void consumeComponentSequence(
        CssTokenSource tokenSource,
        /*CssTokenType*/int stopTokenType,
        ArrayList<CssToken> result)
    {
        // Since components may be nested, we maintain a stack of stop tokens for each nesting level.
        // MEMORY. Nested components are not common so we allocate the stack lazily.
        CssTokenTypeStack nestedStopTokens = null;

        while (true)
        {
            CssToken token = tokenSource.consume();
            if (token.isEof())
            {
                // Reached the EOF. Stop reading.
                break;
            }

            if ((nestedStopTokens != null) && !nestedStopTokens.isEmpty())
            {
                // Finished reading a nested component value. Forget its stop token.
                if (token.getType() == nestedStopTokens.peek())
                {
                    nestedStopTokens.pop();
                }
            }
            else if (token.getType() == stopTokenType)
            {
                // Encountered the stop token of the whole sequence. Stop reading and leave the stop token uncounsumed.
                tokenSource.reconsume();
                break;
            }

            // If the result is null, the calling code wants to skip the component and is not interested in its value.
            if (result != null)
            {
                msArrayList.add(result, token);
            }

            /*CssTokenType*/int nestedStopTokenType = getComponentStopTokenType(token.getType());
            if (nestedStopTokenType != CssTokenType.EOF)
            {
                // Encountered a nested component value. Allocate the nested stop tokens stack if needed and remember
                // the stop token.
                if (nestedStopTokens == null)
                {
                    nestedStopTokens = new CssTokenTypeStack();
                }
                nestedStopTokens.push(nestedStopTokenType);
            }
        }
    }

    private static /*CssTokenType*/int getComponentStopTokenType(/*CssTokenType*/int startTokenType)
    {
        switch (startTokenType)
        {
            case CssTokenType.BLOCK_BRACKET_LEFT:
                return CssTokenType.BLOCK_BRACKET_RIGHT;
            case CssTokenType.SQUARE_BRACKET_LEFT:
                return CssTokenType.SQUARE_BRACKET_RIGHT;
            case CssTokenType.ROUND_BRACKET_LEFT:
            case CssTokenType.FUNCTION:
                return CssTokenType.ROUND_BRACKET_RIGHT;
            default:
                return CssTokenType.EOF;
        }
    }

    private static void consumeBlock(
        CssTokenSource tokenSource,
        ArrayList<CssToken> result)
    {
        consumeComponentSequence(tokenSource, CssTokenType.BLOCK_BRACKET_RIGHT, result);
        tokenSource.consume(); // '}' or EOF
    }

    private static CssMediaRule buildMediaRule(
        CssMediaQueryList mediaQueryList,
        ArrayList<CssRule> nestedRules)
    {
        // Inside @media rules, we allow only style, @page, and other @media rules. Remove rules of other types.
        int filteredOutRuleCount = 0;
        for (CssRule innerRule : nestedRules)
        {
            if (!isAllowedInsideMediaRule(innerRule.getType()))
            {
                ++filteredOutRuleCount;
            }
        }

        // Place filtered rules into an array.
        CssRule[] nestedRulesArray = new CssRule[nestedRules.size() - filteredOutRuleCount];
        int ruleIndex = 0;
        for (CssRule innerRule : nestedRules)
        {
            if (isAllowedInsideMediaRule(innerRule.getType()))
            {
                nestedRulesArray[ruleIndex] = innerRule;
                ++ruleIndex;
            }
        }

        return new CssMediaRule(mediaQueryList, nestedRulesArray);
    }

    private static boolean isAllowedInsideMediaRule(/*CssRuleType*/int ruleType)
    {
        return (ruleType == CssRuleType.STYLE) ||
            (ruleType == CssRuleType.MEDIA) ||
            (ruleType == CssRuleType.PAGE);
    }

    private CssStyleRule buildStyleRule(
        ArrayList<CssToken> selectorTokens,
        ArrayList<CssToken> declarationTokens)
    {
        CssSelector[] selectors = parseSelectors(selectorTokens);
        if (selectors.length == 0)
        {
            return null;
        }
        CssDeclarationCollection declarations = consumeDeclarationList(new CssListTokenSource(declarationTokens), false);
        return new CssStyleRule(selectors, declarations);
    }

    private CssSelector[] parseSelectors(ArrayList<CssToken> tokens)
    {
        ArrayList<CssSelector> selectors = new ArrayList<CssSelector>();
        ArrayList<CssToken> selectorTokens = new ArrayList<CssToken>();
        // Here we make one step past the end of token lists. This allows to make the loop simpler, because the last token
        // is processed inside the loop.
        for (int i = 0; i <= tokens.size(); i++)
        {
            // If the current token is a selector separator (comma or EOF), parse selector tokens that we've collected.
            if ((i >= tokens.size()) || (tokens.get(i).getType() == CssTokenType.COMMA))
            {
                // Remove trailing whitespace.
                if ((selectorTokens.size() > 0) && selectorTokens.get(selectorTokens.size() - 1).isWhitespace())
                {
                    selectorTokens.remove(selectorTokens.size() - 1);
                }

                CssSelector selector = CssSelectorParser.parse(selectorTokens, mDeclaredNamespaces);
                if (selector != null)
                {
                    msArrayList.add(selectors, selector);
                }
                else
                {
                    // Parsing error. If any selector cannot be parsed, the whole selector list is considered malformed,
                    // and the whole rule must be ignored.
                    return new CssSelector[0];
                }

                selectorTokens.clear();
            }
            // Collect tokens of the current selector. Remove leading whitespace.
            else if ((selectorTokens.size() > 0) || !tokens.get(i).isWhitespace())
            {
                msArrayList.add(selectorTokens, tokens.get(i));
            }
        }
        return msArrayList.toArray(selectors, new CssSelector[0]);
    }

    private CssDeclarationCollection parseDeclaration(
        String propertyName,
        ArrayList<CssToken> value,
        boolean isImportant)
    {
        // CSS property names contain only ASCII characters and are case-insensitive.
        CssPropertyDef propertyDef = CssPropertyDefFactory.getPropertyDef(StringUtil.asciiLowerCase(propertyName));

        CssValueList cssValues = parseSpaceSeparatedValueList(new CssListTokenSource(value), mStylesheetBaseUri);
        return (cssValues.getCount() != 0)
            ? propertyDef.createDeclarations(cssValues, isImportant, mIsInQuirksMode)
            : null;
    }

    private static CssValueList parseSpaceSeparatedValueList(
        CssTokenSource tokenSource,
        String stylesheetBaseUri)
    {
        ArrayList<CssValue> result = new ArrayList<CssValue>();

        tokenSource.consumeOptionalWhitespace();

        while (tokenSource.hasMoreTokens())
        {
            CssValue value = parseValue(tokenSource, stylesheetBaseUri);
            if (value == null)
            {
                // Parsing error.
                return new CssValueList();
            }
            msArrayList.add(result, value);

            tokenSource.consumeOptionalWhitespace();
        }

        return new CssValueList(msArrayList.toArray(result, new CssValue[0]));
    }

    private static CssValue parseValue(
        CssTokenSource tokenSource,
        String stylesheetBaseUri)
    {
        CssToken token = tokenSource.consume();

        // Is used as a value in the "font" shorthand property.
        if (token.isDelim('/'))
        {
            return CssValue.Solidus;
        }

        switch (token.getType())
        {
            case CssTokenType.STRING:
            {
                return new CssStringValue(((CssTextToken)token).getText());
            }
            case CssTokenType.COMMA:
            {
                return CssValue.Comma;
            }
            case CssTokenType.NUMBER:
            {
                return new CssNumberValue(((CssNumberToken)token).getValue());
            }
            case CssTokenType.PERCENTAGE:
            {
                return new CssPercentageValue(((CssNumberToken)token).getValue());
            }
            case CssTokenType.DIMENSION:
            {
                CssDimensionToken dimensionToken = (CssDimensionToken)token;

                if ("deg".equals(StringUtil.asciiLowerCase(dimensionToken.getUnit())))
                {
                    return new CssDegreeValue(dimensionToken.getValue());
                }

                /*CssUnit*/int unit = parseUnit(dimensionToken.getUnit());
                return (unit != CssUnit.NONE)
                    ? new CssLengthValue(dimensionToken.getValue(), unit)
                    : null;
            }
            case CssTokenType.FUNCTION:
            {
                String name = ((CssTextToken)token).getText();
                // CSS function names are ASCII case-insensitive.
                name = StringUtil.asciiLowerCase(name);

                ArrayList<CssValue> parameters = new ArrayList<CssValue>();
                while (true)
                {
                    CssToken parameterToken = tokenSource.consumeWhitespaceAndToken();
                    if (parameterToken.isEof())
                    {
                        return null;
                    }
                    if (parameterToken.getType() == CssTokenType.ROUND_BRACKET_RIGHT)
                    {
                        return new CssFunctionValue(name, new CssValueList(msArrayList.toArray(parameters, new CssValue[0])));
                    }
                    tokenSource.reconsume();
                    // Note that the parameter might be a function too.
                    CssValue parameter = parseValue(tokenSource, stylesheetBaseUri);
                    if (parameter == null)
                    {
                        return null;
                    }
                    msArrayList.add(parameters, parameter);
                }
            }
            case CssTokenType.IDENT:
            {
                return new CssIdentifierValue(((CssTextToken)token).getText());
            }
            case CssTokenType.HASH:
            {
                return new CssHashValue(((CssTextToken)token).getText());
            }
            case CssTokenType.URL:
            {
                String uri = resolveRelativeUri(stylesheetBaseUri, ((CssTextToken)token).getText());
                return new CssUriValue(uri);
            }
            default:
            {
                // Parsing error. Unexpected token.
                return null;
            }
        }
    }

    private static String resolveRelativeUri(String stylesheetBaseUri, String uri)
    {
        if ((stylesheetBaseUri != null) && !UriUtil.isAbsoluteHref(uri))
        {
            uri = UriUtil.constructAbsoluteUri(stylesheetBaseUri, uri);
        }
        return uri;
    }

    private static /*CssUnit*/int parseUnit(String value)
    {
        switch (gStringSwitchMap.of(StringUtil.asciiLowerCase(value)))
        {
            case /*"in"*/5:
                return CssUnit.IN;
            case /*"cm"*/6:
                return CssUnit.CM;
            case /*"mm"*/7:
                return CssUnit.MM;
            case /*"pt"*/8:
                return CssUnit.PT;
            case /*"pc"*/9:
                return CssUnit.PC;
            case /*"px"*/10:
                return CssUnit.PX;
            case /*"em"*/11:
                return CssUnit.EM;
            case /*"ex"*/12:
                return CssUnit.EX;
            case /*"rem"*/13:
                return CssUnit.REM;
            default:
                return CssUnit.NONE;
        }
    }

    /// <summary>
    /// Indicates whether the CSS document is being processed in the Quirks mode, as opposed to the Standards mode.
    /// </summary>
    private /*final*/ boolean mIsInQuirksMode;

    /// <summary>
    /// Base URI (directory) of the stylesheet that is being parsed.
    /// </summary>
    /// <remarks>
    /// This base URI is prepended to all parsed relative URI values in order to resolve them relative to this stylesheet's
    /// location. This makes it easier to later combine and resolve URIs in CSS declarations loaded from different
    /// stylesheets.
    /// </remarks>
    private /*final*/ String mStylesheetBaseUri;

    /// <summary>
    /// Stack of CSS rule lists processed by the parser. Allows to parse nested rule lists.
    /// </summary>
    private /*final*/ CssRuleListStack mRuleLists = new CssRuleListStack();

    /// <summary>
    /// CSS namespaces declared in the stylesheet that is being parsed.
    /// </summary>
    private /*final*/ CssNamespacePrefixes mDeclaredNamespaces = new CssNamespacePrefixes();

    /// <summary>
    /// When the stylesheet which is currently being parsed is loaded together with other stylesheets, this class helps
    /// to ensure that in parsed rules different namespaces will have different prefixes.
    /// </summary>
    private /*final*/ CssNamespacePrefixResolver mMultipleStylesheetNamespacePrefixResover;

    /// <summary>
    /// Indicates whether a @namespace rule is allowed at the current position in CSS.
    /// </summary>
    /// <remarks>
    /// @namespace rules must be ignored if anything has already been processed except @charset or @import rules.
    /// </remarks>
    private boolean mCanProcessMoreNamespaceRules = true;

    /// <summary>
    /// Indicates whether a @import rule is allowed at the current position in CSS.
    /// </summary>
    /// <remarks>
    /// @import rules must be ignored if anything has already been processed except @charset rules.
    /// </remarks>
    private boolean mCanProcessMoreImportRules = true;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"import",
		"namespace",
		"page",
		"font-face",
		"list",
		"in",
		"cm",
		"mm",
		"pt",
		"pc",
		"px",
		"em",
		"ex",
		"rem"
	);

}


// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/02/2025 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ArrayUtil;


/// <summary>
/// Represents a CSS selector as seen by MS Word. Allows to parse selectors into parts and the check the parts
/// against specified patterns.
/// </summary>
class MsoHtmlSelector
{
    MsoHtmlSelector(
        String parent,
        String tag,
        String id,
        String className,
        String pseudo)
    {
        mParent = parent;
        mTag = tag;
        mId = id;
        mClassName = className;
        mPseudo = pseudo;
    }

    /// <summary>
    /// Parses a CSS selector into parts as MS Word does.
    /// </summary>
    /// <remarks>
    /// <para>
    /// Note that MS Word supports only a subset of CSS selectors supported by Aspose.Words.
    /// </para>
    /// <para>
    /// <paramref name="selectorCss"/> must be a valid CSS selector text and it must not contain whitespace (except for
    /// one space character per descendant combinator).
    /// </para>
    /// </remarks>
    /// <param name="selectorCss">Selector's CSS text.</param>
    /// <returns>A parsed selector. If CSS cannot be parsed by MS Word, an empty selector is returned.</returns>
    static MsoHtmlSelector parse(String selectorCss)
    {
        int stop = selectorCss.length();

        String parent = "";

        // Extract the last part (innermost selector) of a descendant selector. For example, the selector "div p span" will
        // be split into two parts: "div p" and "span". The innermost part is parsed further.
        int lastSpaceIndex = selectorCss.lastIndexOf(' ');
        if (lastSpaceIndex > 0)
        {
            if (msString.indexOfAny(selectorCss, G_COMBINATORS, 0, lastSpaceIndex) >= 0)
            {
                return new MsoHtmlSelector("", "", "", "", "");
            }

            parent = selectorCss.substring((0), (0) + (lastSpaceIndex));
            selectorCss = selectorCss.substring(lastSpaceIndex + 1);
            stop = selectorCss.length();
        }

        // From the MS Word's perspective, a CSS selector looks like this:
        // tag#id.class:pseudo
        // Any of the parts can be missing. For example, "tag", "tag#id", "#id.class", ":pseudo", "tag:pseudo" selectors
        // are all recognized.
        // CSS is eagerly parsed from right to left, one part at a time.

        // Extract the pseudo part of the selector (either pseudo-class or pseudo-element).
        // For example, "hover" from "tag#id.class:hover".
        String pseudo = parseLastPart(selectorCss, stop, ':');
        if (pseudo.length() > 0)
        {
            stop -= pseudo.length() + 1;
        }

        // Extract the class part of the selector. For example, "class" from "tag#id.class".
        // Note that the pseudo part has been extracted already.
        String className = parseLastPart(selectorCss, stop, '.');
        if (className.length() > 0)
        {
            stop -= className.length() + 1;
        }

        // Extract the id part of the selector. For example, "id" from "tag#id".
        String id = parseLastPart(selectorCss, stop, '#');
        if (id.length() > 0)
        {
            stop -= id.length() + 1;
        }

        // The remaining part (if any) is the tag.
        String tag = "";
        if (stop >= 0)
        {
            tag = selectorCss.substring((0), (0) + (stop));
        }

        // MS Word converts class names to lower case.
        className = msString.toLowerInvariant(className);

        return new MsoHtmlSelector(parent, tag, id, className, pseudo);
    }

    /// <summary>
    /// Returns a value indicating whether this selector is supported by MS Word in HTML documents.
    /// </summary>
    boolean isSupportedByMsWord()
    {
        // Empty selectors (or selectors that cannot be parsed by MS Word) are not supported.
        if (isEmpty())
        {
            return false;
        }

        // MS Word supports only a limited list of element names in selectors.
        if (StringUtil.hasChars(getTag()) && !MsoHtmlUtil.isElementSupportedByMsWord(getTag()))
        {
            return false;
        }

        // MS Word doesn't support multi-ID selectors like "#id1#id2".
        if (StringUtil.hasChars(getId()) && (getId().indexOf('#') >= 0))
        {
            return false;
        }

        // MS Word doesn't support multi-class selectors like ".class1.class2". It also doesn't support mixed class and ID
        // selectors like ".class#id".
        if (StringUtil.hasChars(getClassName()) &&
            ((getClassName().indexOf('.') >= 0) || (getClassName().indexOf('#') >= 0)))
        {
            return false;
        }

        if (StringUtil.hasChars(getPseudo()))
        {
            // MS Word supports a limited list of pseudo-elements and pseudo-classes selectors.
            if (ArrayUtil.binarySearch(G_PSEUDO_SUPPORTED_BY_MS_WORD, getPseudo()) < 0)
            {
                return false;
            }

            // Bare ":active" selectors are not supported. There must be a tag, an id, or a class specified: "em:active",
            // "#id:active", or ".class:active".
            if (("active".equals(getPseudo())) &&
                (StringUtil.hasChars(getTag()) || StringUtil.hasChars(getId()) || StringUtil.hasChars(getClassName())))
            {
                return false;
            }

            // For "link" and "visited" pseudo-classes, only the following two cases are supported:
            // - bare pseudo-class, like ":link";
            // - an anchor element with a pseudo-class, like "a:link".
            if ((("link".equals(getPseudo())) || ("visited".equals(getPseudo()))) && StringUtil.hasChars(getTag()) && (!"a".equals(getTag())))
            {
                return false;
            }
        }

        // MS Word doesn't support selectors that have both ID and class specified. For example, "#id.class".
        if (StringUtil.hasChars(getId()) && StringUtil.hasChars(getClassName()))
        {
            return false;
        }

        return true;
    }

    boolean isEmpty()
    {
        return hasParts(false, false, false, false);
    }

    boolean isTagOnly(String tag)
    {
        return hasParts(true, false, false, false) && (msString.equals(getTag(), tag));
    }

    boolean isTagAndClassAndAnyPseudo(
        String tag,
        String className)
    {
        return hasParts(true, false, true, true) && (msString.equals(getTag(), tag)) && (msString.equals(getClassName(), className));
    }

    boolean isClassOnly(String className)
    {
        return hasParts(false, false, true, false) && (msString.equals(getClassName(), className));
    }

    boolean isClassAndAnyPseudo(String className)
    {
        return hasParts(false, false, true, true) && (msString.equals(getClassName(), className));
    }

    boolean isPseudoOnly(String pseudo)
    {
        return hasParts(false, false, false, true) && (msString.equals(getPseudo(), pseudo));
    }

    boolean isTagAndClass(
        String tag,
        String className)
    {
        return hasParts(true, false, true, false) &&
            (msString.equals(getTag(), tag)) &&
            (msString.equals(getClassName(), className));
    }

    boolean isTagAndAnyClass(String tag)
    {
        return hasParts(true, false, true, false) && (msString.equals(getTag(), tag));
    }

    boolean isTagAndPseudo(
        String tag,
        String pseudo)
    {
        return hasParts(true, false, false, true) && (msString.equals(getTag(), tag)) && (msString.equals(getPseudo(), pseudo));
    }

    boolean isTagAndAnything(
        String tag,
        boolean allowClass)
    {
        if (!allowClass && hasPart(getClassName(), true))
        {
            return false;
        }
        return (msString.equals(getTag(), tag)) &&
            (hasPart(getId(), true) || hasPart(getClassName(), true) || hasPart(getPseudo(), true));
    }

    String getParent() { return mParent; };

    private String mParent;

    String getTag() { return mTag; };

    private String mTag;

    String getId() { return mId; };

    private String mId;

    String getClassName() { return mClassName; };

    private String mClassName;

    String getPseudo() { return mPseudo; };

    private String mPseudo;

    /// <summary>
    /// Eagerly parses the rightmost part of the selector.
    /// </summary>
    /// <remarks>
    /// The parsed part extends from the leftmost occurence of the specified <paramref name="starter"/> character
    /// up to the specified <paramref name="stop"/> position.
    /// If no <paramref name="starter"/> character is found, an empty string is returned.
    /// </remarks>
    private static String parseLastPart(
        String selectorCss,
        int stop,
        char starter)
    {
        int position = selectorCss.indexOf(starter);
        if ((position >= 0) && (position < stop))
        {
            return selectorCss.substring((position + 1), (position + 1) + (stop - position - 1));
        }
        return "";
    }

    private boolean hasParts(
        boolean hasTag,
        boolean hasId,
        boolean hasClass,
        boolean hasPseudo)
    {
        return hasPart(getTag(), hasTag) &&
            hasPart(getId(), hasId) &&
            hasPart(getClassName(), hasClass) &&
            hasPart(getPseudo(), hasPseudo);
    }

    private static boolean hasPart(
        String partValue,
        boolean isExpected)
    {
        return StringUtil.hasChars(partValue) == isExpected;
    }

    /// <summary>
    /// List of pseudo-element and pseudo-class names supported by MS Word.
    /// </summary>
    /// <remarks>
    /// The list is sorted for binary search to work.
    /// </remarks>
    private static /*final*/ String[] G_PSEUDO_SUPPORTED_BY_MS_WORD = new String[]
    {
        "active", "first-letter", "first-line", "focus", "hover", "link", "visited"
    };

    /// <summary>
    /// Selector combinators that are supported by Aspose.Words but not supported by MS Word.
    /// </summary>
    private static /*final*/ char[] G_COMBINATORS = new char[] { '>', '+', '~' };
}


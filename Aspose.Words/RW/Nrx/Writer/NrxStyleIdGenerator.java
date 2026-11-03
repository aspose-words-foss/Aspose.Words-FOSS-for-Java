// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.IntToObjDictionary;
import com.aspose.CaseInsensitiveHashSet;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ArgumentUtil;
import com.aspose.ISetGeneric;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Common code for HTML, WordML and DOCX that deals with writing style names to the output file.
/// Original code by VA, extracted from WmlContext.
/// </summary>
class NrxStyleIdGenerator
{
    NrxStyleIdGenerator(StyleCollection styles)
    {
        Debug.assertTrue(styles != null);

        int stylesCount = styles.getCount();
        mStyleIdByIstdTable = new IntToObjDictionary<String>(stylesCount);
        mStyleAliasesIdsByIstdTable = new IntToObjDictionary<String[]>();
        mStyleIdTable = new CaseInsensitiveHashSet();

        for (int i = 0; i < stylesCount; ++i)
            addStyleId(styles.get(i));
    }

    /// <summary>
    /// Generates unique WordML-compliant style identifier for the specified style and adds it to styleId-by-istd table.
    /// </summary>
    private void addStyleId(Style style)
    {
        String styleName;

        if (style.getBuiltIn())
        {
            // This gives a locale-independent DOCX name of a built-in style, suitable for most cases.
            styleName = StyleConvertUtil.styleIdentifierToXml(style.getStyleIdentifier(), style.getName());
            // However, some DOCX names, such as "annotation reference" are not what we need.
            // We do a trick of converting a DOCX name to a DOC/model name such as "Comment Reference".
            styleName = StyleConvertUtil.xmlToStyleName(styleName);
        }
        else
        {
            styleName = style.getName();
        }

        // If the resulting style name is an empty string, then "a" is taken as a name.
        int styleIstd = style.getIstd();
        String styleId = generateStyleIdByName("a", styleName);
        mStyleIdByIstdTable.add(styleIstd, styleId);

        // Style aliases if any present. They are comma separated.
        String aliases = style.getStyles().getAliases(style, false);
        if (StringUtil.hasChars(aliases))
        {
            String[] aliasesArray = msString.split(aliases, ',');
            for (int i = aliasesArray.length; --i >= 0; )
                aliasesArray[i] = generateStyleIdByName(styleId, aliasesArray[i]);

            mStyleAliasesIdsByIstdTable.add(styleIstd, aliasesArray);
        }
    }

    /// <summary>
    /// Removes prohibited characters from the name, ensures it is not empty and unique.
    /// Empty names are filled with defaultPrefix and then uniqueness is assured as usual.
    /// </summary>
    private String generateStyleIdByName(String defaultPrefix, String styleName)
    {
        // Remove all characters from the style name that are not -, A-Z, a-z, 0-9.
        String styleId = G_REGEX_MAKE_STYLE_ID.replace(styleName, "");

        if (msString.equals(styleId, ""))
            styleId = defaultPrefix;

        styleId = ensureUniqueStyleId(styleId);
        mStyleIdTable.add(styleId);
        return styleId;
    }

    /// <summary>
    /// If the styleid already exists in the document, try to add a number to it,
    /// starting from 0, until the resulting name is unique.
    /// </summary>
    private String ensureUniqueStyleId(String styleId)
    {
        ArgumentUtil.checkHasChars(styleId, "styleId");

        String newStyleId = styleId;

        int k = 0;
        while (mStyleIdTable.contains(newStyleId))
        {
            newStyleId = styleId + k;
            k++;
        }

        return newStyleId;
    }

    /// <summary>
    /// Gets style id by the style's istd.
    /// </summary>
    String getStyleId(int istd)
    {
        return mStyleIdByIstdTable.get(istd);
    }

    /// <summary>
    /// Gets style aliases ids by the style's istd.
    /// Maybe null if the style has no aliases.
    /// </summary>
    String[] getStyleAliasesIds(int istd)
    {
        return mStyleAliasesIdsByIstdTable.get(istd);
    }

    /// <summary>
    /// The key is style istd, the value is a string WordML-compliant style identifier.
    /// </summary>
    private /*final*/ IntToObjDictionary<String> mStyleIdByIstdTable;
    /// <summary>
    /// The key is style istd, the value is the array of aliases in WordML-compliant form.
    /// </summary>
    private /*final*/ IntToObjDictionary<String[]> mStyleAliasesIdsByIstdTable;
    /// <summary>
    /// This case-insensitive hashset is used to ensure case-insensitivity of WordML style id generation.
    /// For example, 'name' and 'Name' are considered to be the same style id.
    /// Contains strings which are WordML-compliant style identifiers.
    /// </summary>
    private /*final*/ ISetGeneric<String> mStyleIdTable;

    private static /*final*/ Regex G_REGEX_MAKE_STYLE_ID = new Regex("[^-a-zA-Z0-9]*", RegexOptions.SINGLELINE | RegexOptions.COMPILED);
}


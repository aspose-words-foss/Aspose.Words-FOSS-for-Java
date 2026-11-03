// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.UriUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.words.ref.Ref;


/// <summary>
/// Text paragraph's structure
/// </summary>
class TxtParagraph
{
    //>>>>>>>> #region  Constuctors

    TxtParagraph()
    {
        mStringBuilder = new StringBuilder();
        mLinesCount = 0;
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Properties

    int getLeftIndent() { return mLeftIndent; }
    void setLeftIndent(int value) { mLeftIndent = value; }

    int getFirstLineIndent() { return mFirstLineIndent; }
    void setFirstLineIndent(int value) { mFirstLineIndent = value; }

    int getLinesCount() { return mLinesCount; }

    /// <summary>
    /// Numbering of paragraph or null otherwise
    /// </summary>
    TxtNumbering getNumbering() { return mNumbering; }
    void setNumbering(TxtNumbering value) { mNumbering = value; }

    /// <summary>
    /// Returns true if FormFeed (0xC) character is terminating this line, meaning that we'll
    /// need to insert new Section after this line.
    /// </summary>
    boolean isNewSectionRequested() { return mIsFormFeed; }
    void isNewSectionRequested(boolean value) { mIsFormFeed = value; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Methods

    String getText()
    {
        return mStringBuilder.toString();
    }

    void appendText(String text)
    {
        if (mStringBuilder.length() != 0)
            msStringBuilder.append(mStringBuilder, " ");
        msStringBuilder.append(mStringBuilder, text);
        mLinesCount++;
    }

    /// <summary>
    /// Writes paragraph into a specified builder.
    /// </summary>
    void write(DocumentBuilder builder, TxtLoadOptions options)
    {
        String text = mStringBuilder.toString();

        int i = 0;
        // WORDSNET-25529 Check hyperlink text to store it in model as HyperlinkField.
        if (options.getDetectHyperlinks())
        {
            String uri = null;
            Ref<String> referenceToUri = new Ref<String>(uri);
            int uriIndex = UriUtil.findUri(text, /*out*/ referenceToUri);
            uri = referenceToUri.get();
            while (uriIndex != -1)
            {
                // Write text before hyperlink.
                if (i < uriIndex)
                {
                    String plainText = text.substring((i), (i) + (uriIndex - i));
                    builder.write(plainText);
                }

                String schemeName = null;
                Ref<String> referenceToSchemeName = new Ref<String>(schemeName);
                UriUtil.findUriScheme(uri, 0, /*out*/ referenceToSchemeName);
                schemeName = referenceToSchemeName.get();
                schemeName = msString.toLower(schemeName, msCultureInfo.getInvariantCulture());
                // Consider only the following scheme names as hyperlinks.
                if ("http".equals(schemeName) || "https".equals(schemeName) || "ftp".equals(schemeName))
                {
                    builder.pushFont();
                    builder.getFont().setColor(options.getHyperlinksColor());
                    builder.getFont().setUnderline(options.getHyperlinksUnderline());
                    builder.insertHyperlink(uri, uri, false);
                    builder.popFont();
                }
                else
                {
                    builder.write(uri);
                }

                i = uriIndex + uri.length();
                referenceToUri.set(uri);
                uriIndex = UriUtil.findUri(text, i, /*out*/ referenceToUri);
                uri = referenceToUri.get();
            }
        }

        // Write the rest of remained text.
        if (i < text.length())
        {
            String plainText = (i == 0) ? text : text.substring((i), (i) + (text.length() - i));
            builder.write(plainText);
        }

        builder.writeln();
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Fields

    private /*final*/ StringBuilder mStringBuilder;
    private int mLeftIndent;
    private int mFirstLineIndent;
    private int mLinesCount;
    private TxtNumbering mNumbering;
    private boolean mIsFormFeed;

    //<<<<<<<< #endregion 
}


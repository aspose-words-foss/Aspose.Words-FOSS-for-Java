// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Can be used to specify additional options when saving a document into the <see cref="Words.SaveFormat.Text"/> format.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-save-options/">Specify Save Options</a> documentation article.</para>
/// </summary>
public class TxtSaveOptions extends TxtSaveOptionsBase
{
    /// <summary>
    /// Specifies the format in which the document will be saved if this save options object is used.
    /// Can only be <see cref="Words.SaveFormat.Text"/>.
    /// </summary>
    public /*override*/ /*SaveFormat*/int getSaveFormat() { return SaveFormat.TEXT; }
    public /*override*/ void setSaveFormat(/*SaveFormat*/int value)
    {
        if (value != SaveFormat.TEXT)
            throw new IllegalArgumentException("An invalid SaveFormat for this options type was chosen.");
    }

    /// <summary>
    /// <para>Specifies whether the program should simplify list labels in case of
    /// complex label formatting not being adequately represented by plain text.</para>
    /// <para>If set to <c>true</c>, numbered list labels are written in simple numeric format
    /// and itemized list labels as simple ASCII characters. The default value is <c>false</c>.</para>
    /// </summary>
    public boolean getSimplifyListLabels() { return mSimplifyListLabels; }; public void setSimplifyListLabels(boolean value) { mSimplifyListLabels = value; };

    private boolean mSimplifyListLabels;

    /// <summary>
    /// <para>Specifies whether to add bi-directional marks before each BiDi run when exporting in plain text format.</para>
    /// <para>The default value is <c>false</c>.</para>
    /// </summary>
    public boolean getAddBidiMarks() { return mAddBidiMarks; }; public void setAddBidiMarks(boolean value) { mAddBidiMarks = value; };

    private boolean mAddBidiMarks;

    /// <summary>
    /// Gets a <see cref="TxtListIndentation"/> object that specifies how many and which character to use for indentation of list levels.
    /// By default, it is zero count of character '\0', that means no indentation.
    /// </summary>
    public TxtListIndentation getListIndentation() { return mListIndentation; }

    /// <summary>
    /// Specifies whether the program should attempt to preserve layout of tables when saving in the plain text format.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getPreserveTableLayout() { return mPreserveTableLayout; }; public void setPreserveTableLayout(boolean value) { mPreserveTableLayout = value; };

    private boolean mPreserveTableLayout;

    /// <summary>
    /// Gets or sets an integer value that specifies the maximum number of characters per one line.
    /// The default value is 0, that means no limit.
    /// </summary>
    public int getMaxCharactersPerLine() { return mMaxCharactersPerLine; }; public void setMaxCharactersPerLine(int value) { mMaxCharactersPerLine = value; };

    private int mMaxCharactersPerLine;

    /// <summary>
    /// Specifies how OfficeMath will be written to the output file.
    /// Default value is <see cref="TxtOfficeMathExportMode.Text"/>.
    /// </summary>
    public /*TxtOfficeMathExportMode*/int getOfficeMathExportMode() { return mOfficeMathExportMode; }; public void setOfficeMathExportMode(/*TxtOfficeMathExportMode*/int value) { mOfficeMathExportMode = value; };

    private /*TxtOfficeMathExportMode*/int mOfficeMathExportMode;

    private /*final*/ TxtListIndentation mListIndentation = new TxtListIndentation();
}


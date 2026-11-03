// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToIntBidirectionalMap;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.IntToIntDictionary;
import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Stores data during import of elements from one document into another.
/// </summary>
class ImportContext
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ImportContext(StyleCollection srcStyles, StyleCollection dstStyles)
    {
    	this(srcStyles.getDocument(), dstStyles.getDocument(), ImportFormatMode.KEEP_SOURCE_FORMATTING);
	
    }

    /// <summary>
    /// Creates ImportContext object with a specified ImportFormatMode.
    /// </summary>
    ImportContext(DocumentBase srcDoc, DocumentBase dstDoc, /*ImportFormatMode*/int importFormatMode)
    {
    	this (srcDoc, dstDoc, importFormatMode, null);
	
    }

    /// <summary>
    /// Creates ImportContext object with a specified ImportFormatMode and ImportFormatOptions.
    /// </summary>
    ImportContext(DocumentBase srcDoc, DocumentBase dstDoc, /*ImportFormatMode*/int importFormatMode,
        ImportFormatOptions importFormatOptions)
    {
    	this (srcDoc, dstDoc, importFormatMode, importFormatOptions, null);
	
    }

    ImportContext(DocumentBase srcDoc, DocumentBase dstDoc, /*ImportFormatMode*/int importFormatMode,
        ImportFormatOptions importFormatOptions, IntToIntBidirectionalMap importedIstds)
    {
        Debug.assertTrue(srcDoc != null);
        Debug.assertTrue(dstDoc != null);

        mSrcDoc = srcDoc;
        mDstDoc = dstDoc;
        mImportFormatMode = importFormatMode;

        mImportFormatOptions = importFormatOptions;
        if (mImportFormatOptions == null)
        {
            mImportFormatOptions = new ImportFormatOptions();
            {
                // WORDSNET-24169 Use "UseExistingLists" by default to avoid creating new lists and list definitions.
                mImportFormatOptions.setUseExistingLists(true);
            }
        }

        mImportedIstds = (importedIstds != null) ? importedIstds : new IntToIntBidirectionalMap();

        // It seems, Word relies on the original state of themes,
        // so it should be called here before any changes to themes were made.

        // AM. I think we should not compensate theme difference if node is imported from glossary.
        if (srcDoc instanceof Document || dstDoc instanceof Document)
            calculateThemeDifference(srcDoc.getThemeInternal(), dstDoc.getThemeInternal());

        mIsThemeColorsDifferent = !Theme.themeColorsEquals(srcDoc.getThemeInternal(), dstDoc.getThemeInternal());
    }

    /// <summary>
    /// Applies theme difference if needed.
    /// </summary>
    void applyThemeDifference(RunPr runPr)
    {
        if (isThemeFontsDifferent())
        {
            for (int key : RunPr.FONT_NAME_ATTRIBUTES)
            {
                if (runPr.contains(key))
                {
                    ComplexFontName officeFont = (ComplexFontName)runPr.get(key);

                    if (officeFont.isThemeFont() && mThemeDifference.containsKey(officeFont.getThemeFontCore()))
                        runPr.setAttr(key, ComplexFontName.fromName(mThemeDifference.get(officeFont.getThemeFontCore())));
                }
            }
        }

        if (isThemeColorsDifferent())
        {
            // In common both Color and ThemeColor are present, so we may need to just delete ThemeColor.
            // But it will be incorrect if only ThemeColor specified. We need to correctly resolve this color.

            if (!runPr.contains(FontAttr.COLOR))
                ThemeColorUpdater.update(runPr, getDstDoc().getThemeInternal());

            runPr.remove(FontAttr.THEME_COLOR);
        }
    }

    /// <summary>
    /// Returns true if style was already imported in this context.
    /// </summary>
    boolean isImported(Style style)
    {
        return (getImportedIstds().get(style.getIstd()) != Integer.MIN_VALUE);            
    }

    /// <summary>
    /// Returns true if the specified <paramref name="listDefId"/> can be reused from the destination collection.
    /// </summary>
    boolean canReuseListDefId(int listDefId)
    {
        // WORDSNET-17534 The new ImportFormatOptions.KeepSourceNumbering option is introduced.
        return (!mImportFormatOptions.getKeepSourceNumbering() && (mNonReusableListDefId != listDefId));
    }

    /// <summary>
    /// Sets list definition identifier that cannot be reused.
    /// </summary>
    void setNonReusableListDefId(long nonReusableListDefId)
    {
         mNonReusableListDefId = nonReusableListDefId;
    }

    /// <summary>
    /// Calculates difference between document themes which has to be applied during import.
    /// </summary>
    private void calculateThemeDifference(Theme srcTheme, Theme dstTheme)
    {
        // The same document, nothing to do.
        if (msObject.referenceEquals(srcTheme, dstTheme))
            return;

        for (/*ThemeFontCore*/int themeFont : RunPr.ThemeFontAttrs)
        {
            ComplexFontName officeFontA = ComplexFontName.fromTheme(themeFont);
            ComplexFontName officeFontB = ComplexFontName.fromTheme(themeFont);

            // Fonts are different, save resolved value.
            if (!msString.equals(officeFontA.resolve(srcTheme), officeFontB.resolve(dstTheme)))
                mThemeDifference.set(themeFont, officeFontA.resolve(srcTheme));
        }
    }

    /// <summary>
    /// The document from which the nodes are being imported.
    /// </summary>
    DocumentBase getSrcDoc() { return mSrcDoc; }

    /// <summary>
    /// The document into which the nodes are being imported.
    /// </summary>
    DocumentBase getDstDoc() { return mDstDoc; }

    StyleCollection getSrcStyles() { return mSrcDoc.getStyles(); }

    StyleCollection getDstStyles() { return mDstDoc.getStyles(); }

    ListCollection getSrcLists() { return mSrcDoc.getLists(); }

    ListCollection getDstLists() { return mDstDoc.getLists(); }

    /// <summary>
    /// Specifies how the current import operation behaves.
    /// </summary>
    /*ImportFormatMode*/int getImportFormatMode() { return mImportFormatMode; }
    void setImportFormatMode(/*ImportFormatMode*/int value) { mImportFormatMode = value; }

    /// <summary>
    /// Cache that maps style istds of the source document into istds in the destination document.
    /// </summary>
    IntToIntBidirectionalMap getImportedIstds() { return mImportedIstds; }

    /// <summary>
    /// Cache that maps list id valid in the source document into valid list id in the destination document.
    /// </summary>
    IntToIntDictionary getImportedListIds() { return mImportedListIds; }

    /// <summary>
    /// Cache that maps list definition id valid in the source document into valid list definition id in the destination document.
    /// </summary>
    IntToIntDictionary getImportedListDefIds() { return mImportedListDefIds; }

    /// <summary>
    /// Key is a picture bullet id in the source document, value is a picture bullet id in the destination document.
    /// </summary>
    IntToIntDictionary getImportedPictureBulletIds() { return mImportedPictureBulletIds; }

    /// <summary>
    /// Key is a comment id in the source document. Value is a comment id in the destination document.
    /// </summary>
    IntToIntDictionary getImportedCommentIds() { return mImportedCommentIds; }

    /// <summary>
    /// Key is SDT Id in the source document. Value is SDT Id in the destination document.
    /// </summary>
    IntToIntDictionary getImportedSdtIds() { return mImportedSdtIds; }

    /// <summary>
    /// Indicates that fonts defined by theme are different in source and destination document.
    /// </summary>
    boolean isThemeFontsDifferent() { return mThemeDifference.getCount() > 0; }

    /// <summary>
    /// Indicates that colors defined by theme are different in source and destination document.
    /// </summary>
    boolean isThemeColorsDifferent() { return mIsThemeColorsDifferent; }

    /// <summary>
    /// The topmost importing node.
    /// </summary>
    Node getTopmostNode() { return mTopmostNode; }
    void setTopmostNode(Node value) { mTopmostNode = value; }

    /// <summary>
    /// Specifies additional import options.
    /// </summary>
    ImportFormatOptions getImportFormatOptions() { return mImportFormatOptions; }

    private /*final*/ DocumentBase mSrcDoc;
    private /*final*/ DocumentBase mDstDoc;

    private /*ImportFormatMode*/int mImportFormatMode;
    private /*final*/ ImportFormatOptions mImportFormatOptions;

    private /*final*/ IntToIntBidirectionalMap mImportedIstds;
    private /*final*/ IntToIntDictionary mImportedListIds = new IntToIntDictionary();
    private /*final*/ IntToIntDictionary mImportedListDefIds = new IntToIntDictionary();
    private /*final*/ IntToIntDictionary mImportedPictureBulletIds = new IntToIntDictionary();
    private /*final*/ IntToIntDictionary mImportedCommentIds = new IntToIntDictionary();
    private /*final*/ IntToIntDictionary mImportedSdtIds = new IntToIntDictionary();

    private /*final*/ boolean mIsThemeColorsDifferent;
    
    private long mNonReusableListDefId = Long.MAX_VALUE;

    private Node mTopmostNode;

    private /*final*/ SortedList<Integer, String> mThemeDifference = new SortedList<Integer, String>();
}


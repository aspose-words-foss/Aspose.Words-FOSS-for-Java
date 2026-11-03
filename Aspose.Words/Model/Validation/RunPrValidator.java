// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2009 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ISetGeneric;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msObject;


/// <summary>
/// <para>Visits all nodes of a document and can perform the following tasks:</para>
/// <para>1. Collect names of fonts used throughout the document. It helps to <see cref="DocumentValidator"/>
/// to update <see cref="FontInfoCollection"/> of whole document.</para>
/// <para>2. Expand theme font names into normal formatting.</para>
/// </summary>
/// <remarks>
/// This is in a separate document visitor to declutter the main document validator. But it requires
/// an extra loop through a document. If that proves slow we should hook this class to the document validator methods.
/// </remarks>
class RunPrValidator extends RunPrCollectorBase
{
    /// <summary>
    /// <para>Visits all nodes of a document and can collect of used font names and update theme colors.</para>
    /// </summary>
    /// <param name="doc">Visited document.</param>
    /// <param name="usedFontNames">Found font names are added here. If it's <c>null</c>, font names shall not be collected.</param>
    void execute(DocumentBase doc, ISetGeneric<String> usedFontNames) throws Exception
    {
        mTheme = doc.getThemeInternal();
        mUsedFontNames = usedFontNames;

        doc.accept(this);

        if (mUsedFontNames != null)
            addDefaultsToUsedFontNames();
    }

    protected /*override*/ void doHandleRunPr(RunPr runPr)
    {
        if (runPr == null)
            return;

        if (mUsedFontNames != null)
        {
            collectFontNames(runPr);

            // WORDSNET-9373 Collect font names also inside format revisions.
            if (runPr.hasFormatRevision())
                collectFontNames((RunPr)runPr.getFormatRevision().getRevPr());
        }

        ThemeColorUpdater.update(runPr, mTheme);
    }

    /// <summary>
    /// <para>Marks theme fonts as used. Some of them can be unused actually but we add unused style fonts
    /// to FontInfos, so it's consistent to add theme fonts as used too. MS Word does it too.</para>
    /// <para>Also adds Times New Roman. It looks like MS Word always does it.</para>
    /// </summary>
    private void addDefaultsToUsedFontNames()
    {
        Debug.assertTrue(mUsedFontNames != null);

        if (mTheme != null)
        {
            for (/*ThemeFontCore*/int themeFont : RunPr.ThemeFontAttrs)
            {
                String fontName = ((IThemeProvider)mTheme).getFontName(themeFont);
                if (StringUtil.hasChars(fontName))
                    mUsedFontNames.add(fontName);
            }
        }

        mUsedFontNames.add(RunPr.getDefaultNameAscii());

        // AS It's good to add them too but currently they are the same as NameAscii and they will unlikely be changed.
        Debug.assertTrue(msObject.equals(RunPr.fetchDefaultAttr(FontAttr.NAME_BI), RunPr.fetchDefaultAttr(FontAttr.NAME_ASCII)));
        Debug.assertTrue(msObject.equals(RunPr.fetchDefaultAttr(FontAttr.NAME_FAR_EAST), RunPr.fetchDefaultAttr(FontAttr.NAME_ASCII)));
        Debug.assertTrue(msObject.equals(RunPr.fetchDefaultAttr(FontAttr.NAME_OTHER), RunPr.fetchDefaultAttr(FontAttr.NAME_ASCII)));
    }

    private void collectFontNames(RunPr runPr)
    {
        Debug.assertTrue(mUsedFontNames != null);

        for (int fontAttr : RunPr.FONT_NAME_ATTRIBUTES)
        {
            ComplexFontName fontName = (ComplexFontName)runPr.getDirectAttr(fontAttr);
            if (fontName != null)
                mUsedFontNames.add(ComplexFontName.resolve(fontName, mTheme));
        }
    }

    private Theme mTheme;

    private ISetGeneric<String> mUsedFontNames;

}


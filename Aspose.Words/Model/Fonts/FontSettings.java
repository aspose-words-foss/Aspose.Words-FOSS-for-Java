// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.TTFont;
import com.aspose.FontUtil;


/// <summary>
/// Specifies font settings for a document.
/// </summary>
/// <remarks>
/// <para>FOSS: font resolution, substitution, fallback and external/system font sources were removed
/// together with the layout and rendering engine. This type is retained because it is exposed via
/// <see cref="Document.FontSettings"/> and <see cref="Loading.LoadOptions.FontSettings"/>, but it no
/// longer carries any configurable font sources or substitution rules. Fonts embedded into a document
/// are still honored; every other font resolves to a built-in last-resort font.</para>
/// </remarks>
public class FontSettings
{
    public FontSettings()
    {
        mSyncRoot = new Object();
    }

    /// <summary>
    /// Static default font settings.
    /// </summary>
    /// <remarks>
    /// This instance is used by default in a document unless <see cref="Document.FontSettings"/> is specified.
    /// </remarks>
    public static FontSettings getDefaultInstance() { return G_DEFAULT_INSTANCE; }

    /// <summary>
    /// Gets the built-in last-resort font. This is the only font <see cref="FontSettings"/> can resolve
    /// now that external/system font sources have been removed.
    /// </summary>
    TTFont getAnyFont()
    {
        synchronized (mSyncRoot)
        {
            // WORDSNET-26980 Use font embedded into assembly resource as the last-resort font.
            if (mLastResortFont == null)
            {
                mLastResortFont = FontUtil.loadLastResortFontFromResources();
            }
            return mLastResortFont;
        }
    }

    /// <summary>
    /// FOSS: no external/system font sources to preload.
    /// </summary>
    void preloadFontsInBackground()
    {
    }

    private static /*final*/ FontSettings G_DEFAULT_INSTANCE = new FontSettings();
    private /*final*/ Object mSyncRoot;

    /// <summary>
    /// Last-resort font, loaded on demand - see the <see cref="GetAnyFont()"/> method.
    /// </summary>
    private TTFont mLastResortFont;
}


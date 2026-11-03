// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This is an interface to resolve a "style id" that is specified in the file
/// into an istd (style index) in the model.
/// 
/// Style id is string in DOCX and WordML, but int in RTF. Therefore it is an object here.
/// </summary>
interface INrxStyleIdToIstd
{
    /// <summary>
    /// Resolves a style id that is stored on file into a model istd.
    /// If the speicifed style is not found returns the specified default istd.
    /// </summary>
    public int resolveStyleIdToIstd(Object styleId, int defaultIstd);

    /// <summary>
    /// Adds a mapping between a style id and an istd. 
    /// It is safe to add multiple mappings for same styleId. Latest styleId overrides all previous.
    /// </summary>
    public void addStyleIdToIstdMapping(Object styleId, int istd);
}


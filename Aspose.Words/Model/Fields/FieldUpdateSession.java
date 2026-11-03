// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a common state shared between separated fields update invocations.
/// </summary>
/// <remarks>
/// During fields updating in headers/footers session is used to hold data providers and
/// to prevent from multiple performing of external actions.
/// </remarks>
class FieldUpdateSession
{
    FieldUpdateSession(Document document)
    {
        mExternalActions = new ExternalActionCollection();
        mDataProviderCollection = new FieldUpdateDataProviderCollection();
        mHiddenAttributeCache = new HiddenAttributeCache();
        mImportedStylesIstdsCollector = new ImportedStylesIstdsCollector();
        mAutoTextEntryExtractor = new AutoTextEntryExtractor(document);
        mFieldParagraphFinderCache = new FieldParagraphFinderCache();
        hasNumPagesCoverPageOffset(false);
    }

    /// <summary>
    /// Returns a collection of external actions.
    /// </summary>
    ExternalActionCollection getExternalActions() { return mExternalActions; };

    private ExternalActionCollection mExternalActions;

    /// <summary>
    /// Returns a collection of <see cref="IFieldUpdateDataProvider"/>.
    /// </summary>
    FieldUpdateDataProviderCollection getDataProviderCollection() { return mDataProviderCollection; };

    private FieldUpdateDataProviderCollection mDataProviderCollection;

    HiddenAttributeCache getHiddenAttributeCache() { return mHiddenAttributeCache; };

    private HiddenAttributeCache mHiddenAttributeCache;

    ImportedStylesIstdsCollector getImportedStylesIstdsCollector() { return mImportedStylesIstdsCollector; };

    private ImportedStylesIstdsCollector mImportedStylesIstdsCollector;

    AutoTextEntryExtractor getAutoTextEntryExtractor() { return mAutoTextEntryExtractor; };

    private AutoTextEntryExtractor mAutoTextEntryExtractor;

    FieldParagraphFinderCache getFieldParagraphFinderCache() { return mFieldParagraphFinderCache; };

    private FieldParagraphFinderCache mFieldParagraphFinderCache;

    int getNumPagesCoverPageOffset() { return mNumPagesCoverPageOffset; }
    void setNumPagesCoverPageOffset(int value)
    {
        mNumPagesCoverPageOffset = value;
        hasNumPagesCoverPageOffset(true);
    }

    private int mNumPagesCoverPageOffset;

    boolean hasNumPagesCoverPageOffset() { return mHasNumPagesCoverPageOffset; }; void hasNumPagesCoverPageOffset(boolean value) { mHasNumPagesCoverPageOffset = value; };

    private boolean mHasNumPagesCoverPageOffset;
}


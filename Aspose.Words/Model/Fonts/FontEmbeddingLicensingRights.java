// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2024 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FontFsType;


/// <summary>
/// Represents embedding licensing rights for the font.
/// </summary>
/// <remarks>
/// To learn more, visit the
/// <a href="https://learn.microsoft.com/en-us/typography/opentype/spec/os2#fstype">OpenType specification section</a>
/// on Microsoft Typography portal.
/// </remarks>
public class FontEmbeddingLicensingRights
{
    FontEmbeddingLicensingRights(
        /*FontEmbeddingUsagePermissions*/int embeddingUsagePermissions,
        boolean noSubsetting,
        boolean bitmapEmbeddingOnly)
    {
        mEmbeddingUsagePermissions = embeddingUsagePermissions;
        mNoSubsetting = noSubsetting;
        mBitmapEmbeddingOnly = bitmapEmbeddingOnly;
    }

    static FontEmbeddingLicensingRights fromFsType(FontFsType fsType)
    {
        return new FontEmbeddingLicensingRights(
            EnumConverter.fontEmbeddingUsagePermissionsFromCore(fsType.getPermissions()),
            fsType.getNoSubsetting(),
            fsType.getBitmapEmbeddingOnly());
    }

    /// <summary>
    /// Usage permissions.
    /// </summary>
    public /*FontEmbeddingUsagePermissions*/int getEmbeddingUsagePermissions() { return mEmbeddingUsagePermissions; };

    private /*FontEmbeddingUsagePermissions*/int mEmbeddingUsagePermissions;

    /// <summary>
    /// Indicates the "No subsetting" restriction.
    /// </summary>
    /// <remarks>
    /// When this flag is set, the font must not be subsetted prior to embedding. Other embedding restrictions
    /// also apply.
    /// </remarks>
    public boolean getNoSubsetting() { return mNoSubsetting; };

    private  boolean mNoSubsetting;

    /// <summary>
    /// Indicates the "Bitmap embedding only" restriction.
    /// </summary>
    /// <remarks>
    /// When this bit is set, only bitmaps contained in the font may be embedded. No outline data may be embedded.
    /// If there are no bitmaps available in the font, then the font is considered unembeddable and the embedding
    /// services will fail. Other embedding restrictions also apply.
    /// </remarks>
    public boolean getBitmapEmbeddingOnly() { return mBitmapEmbeddingOnly; };

    private  boolean mBitmapEmbeddingOnly;
}


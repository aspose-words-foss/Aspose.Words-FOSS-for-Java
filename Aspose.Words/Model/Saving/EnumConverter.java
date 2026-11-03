// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FontFsTypePermissions;


class EnumConverter
{
    static /*FontEmbeddingUsagePermissions*/int fontEmbeddingUsagePermissionsFromCore(
        /*FontFsTypePermissions*/int value)
    {
        switch (value)
        {
            case FontFsTypePermissions.EDITABLE:
                return FontEmbeddingUsagePermissions.EDITABLE;
            case FontFsTypePermissions.INSTALLABLE:
                return FontEmbeddingUsagePermissions.INSTALLABLE;
            case FontFsTypePermissions.RESTRICTED_LICENSE:
                return FontEmbeddingUsagePermissions.RESTRICTED_LICENSE;
            case FontFsTypePermissions.PRINT_AND_PREVIEW:
                return FontEmbeddingUsagePermissions.PRINT_AND_PREVIEW;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        }
    }
}


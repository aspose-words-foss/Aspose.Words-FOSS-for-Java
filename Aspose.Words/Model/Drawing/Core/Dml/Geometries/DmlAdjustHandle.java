// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;

abstract class DmlAdjustHandle
{
    /// <summary>
    /// Returns adjust type if it is used in this handle and <see cref="DmlAdjustType.Unknown"/> otherwise.
    /// </summary>
    public abstract /*DmlAdjustType*/int getAdjustType(String adjustName);

    /// <summary>
    /// Clones this instance of adjust handle.
    /// </summary>
    abstract DmlAdjustHandle deepClone();

    protected static boolean compareAdjustNames(String adjustName, String guideReference)
    {
        if (!StringUtil.hasChars(adjustName))
            return false;

        if (!StringUtil.hasChars(guideReference))
            return false;

        return msString.equals(adjustName, guideReference);
    }
}


// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IWarningCallbackCore;
import com.aspose.WarningTypeCore;
import com.aspose.WarningSourceCore;
import com.aspose.ms.System.msString;


/// <summary>
/// Adapts IWarningCallbackCore interface to IWarningCallback interface.
/// </summary>
class WarningCallbackCoreAdapter implements IWarningCallbackCore
{
    WarningCallbackCoreAdapter(IWarningCallback warningCallback)
    {
        mWarningCallback = warningCallback;
    }

    public void /*IWarningCallbackCore.*/warn(/*WarningTypeCore*/int warningType, /*WarningSourceCore*/int source, String description)
    {
        if (mWarningCallback == null)
            return;

        /*WarningType*/int type = WarningInfo.convertWarningTypeCoreToWarningType(warningType);
        /*WarningSource*/int src = WarningInfo.convertWarningSourceCoreToWarningSource(source);
        mWarningCallback.warning(new WarningInfo(type, src, description));
    }

    public void /*IWarningCallbackCore.*/warn(/*WarningTypeCore*/int warningType, /*WarningSourceCore*/int source, String format, Object formatArgument)
    {
        if (mWarningCallback == null)
            return;

        String description = msString.format(format, formatArgument);
        ((IWarningCallbackCore)this).warn(warningType, source, description);
    }

    public void warn(/*WarningTypeCore*/int warningType, /*WarningSourceCore*/int source, String format, Object arg0, Object arg1)
    {
        if (mWarningCallback == null)
            return;

        String description = msString.format(format, arg0, arg1);
        ((IWarningCallbackCore)this).warn(warningType, source, description);
    }

    private /*final*/ IWarningCallback mWarningCallback;
}


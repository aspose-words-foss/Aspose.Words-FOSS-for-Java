// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;


/// <summary>
/// Set of properties of <see cref="DmlPropertySet"/>.
/// </summary>
class DmlPropertySetPr
{
    void setProperty(/*DmlPropertySetAttr*/int attr, Object value)
    {
        mPrSet.set((int)attr, value);
    }

    Object getProperty(/*DmlPropertySetAttr*/int attr)
    {
        return mPrSet.get((int)attr);
    }

    DmlModelId getPresAssocId()
    {
        if(!mPrSet.containsKey((int)DmlPropertySetAttr.PRES_ASSOC_ID))
            return null;

        return (DmlModelId)mPrSet.get((int) DmlPropertySetAttr.PRES_ASSOC_ID);
    }

    String getPresName()
    {
        if (!mPrSet.containsKey((int)DmlPropertySetAttr.PRES_NAME))
            return null;

        return (String)mPrSet.get((int)DmlPropertySetAttr.PRES_NAME);
    }

    private /*final*/ IntToObjDictionary<Object> mPrSet = new IntToObjDictionary<Object>();
}


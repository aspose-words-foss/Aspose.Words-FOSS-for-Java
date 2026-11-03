// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/26/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import java.util.HashMap;


class DmlContentPart extends DmlNode implements IDmlCommonShapePrSource
{
    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        DmlContentPart lhs = (DmlContentPart) super.deepClone(isCloneChildren, cloningListener);
        lhs.setContentPart(getContentPart().deepClone());
        return lhs;
    }

    //>>>>>>>> #region  IDmlCommonShapePrSource implementation

    // At the moment we preserve content parts unparsed, please see ContentPart property.
    // That is why currently return new empty DmlStyleFill.
    public DmlFill getFill()
    {
        return new DmlStyleFill();
    }
    public void setFill(DmlFill value)
    {
        // Do nothing.
    }

    // At the moment we preserve content parts unparsed, please see ContentPart property.
    // That is why currently return new empty DmlOutline.
    public DmlOutline getOutline()
    {
        return new DmlOutline();
    }
    public void setOutline(DmlOutline value)
    {
        // Do nothing.
    }

    // At the moment we preserve content parts unparsed, please see ContentPart property.
    // That is why currently return null.
    public DmlShapeStyle getStyle()
    {
        return null;
    }
    public void setStyle(DmlShapeStyle value)
    {
        // Do nothing.
    }

    //<<<<<<<< #endregion 

    /*BWMode*/int getBWMode() { return mBwMode; }
    void setBWMode(/*BWMode*/int value) { mBwMode = value; }

    /// <summary>
    /// The content parts's main XML part. At the moment we preserve it unparsed.
    /// </summary>
    OpcPackagePart getContentPart() { return mContentPart; }
    void setContentPart(OpcPackagePart value) { mContentPart = value; }

    /*override*/ /*DmlNodeType*/int getDmlNodeType() { return DmlNodeType.CONTENT_PART; }

    HashMap<String, OpcPackagePart> getRelatedParts() { return mRelatedParts; }

    private /*BWMode*/int mBwMode;
    private OpcPackagePart mContentPart;
    private /*final*/ HashMap<String, OpcPackagePart> mRelatedParts = new HashMap<String, OpcPackagePart>();
}


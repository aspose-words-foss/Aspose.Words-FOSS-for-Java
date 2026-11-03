// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import java.util.HashMap;


/// <summary>
/// 21.4.3.5 pt (Point)
/// </summary>
class DmlDiagramPoint extends DmlExtensionListSource
{
    DmlModelId getModelId() { return mModelId; }
    void setModelId(DmlModelId value) { mModelId = value; }

    /*DmlPointType*/int getType() { return mType; }
    void setType(/*DmlPointType*/int value) { mType = value; }

    DmlModelId getConnectionId() { return mConnectionId; }
    void setConnectionId(DmlModelId value) { mConnectionId = value; }

    DmlTextBody getTextBody() { return mTextBody; }
    void setTextBody(DmlTextBody value) { mTextBody = value; }

    DmlShapeProperties getShapeProperties() { return mShapeProperties; }
    void setShapeProperties(DmlShapeProperties value) { mShapeProperties = value; }

    DmlPropertySet getPropertySet() { return mPropertySet; }
    void setPropertySet(DmlPropertySet value) { mPropertySet = value; }

    DmlDiagramPoint getAlternateContentFallbackPoint() { return mFallbackPoint; }
    void setAlternateContentFallbackPoint(DmlDiagramPoint value) { mFallbackPoint = value; }

    Map<String, String> getAlternateContentChoiceAttributes()
    {
        if(mChoiceAttributes == null)
            mChoiceAttributes = new HashMap<String, String>();
        return mChoiceAttributes;
    }

    Map<String, String> getAlternateContentAttributes()
    {
        if (mAlternateContentAttributes == null)
            mAlternateContentAttributes = new HashMap<String, String>();
        return mAlternateContentAttributes;
    }

    private DmlModelId mModelId;
    private /*DmlPointType*/int mType = DmlPointType.NODE;
    private DmlModelId mConnectionId;
    private DmlTextBody mTextBody;
    private DmlShapeProperties mShapeProperties;
    private DmlPropertySet mPropertySet;
    private DmlDiagramPoint mFallbackPoint;
    private HashMap<String, String> mChoiceAttributes;
    private HashMap<String, String> mAlternateContentAttributes;
}


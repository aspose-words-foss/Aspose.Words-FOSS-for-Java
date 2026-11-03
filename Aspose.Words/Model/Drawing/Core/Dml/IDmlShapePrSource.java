// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


interface IDmlShapePrSource
{
    public DmlTransform getTransform(); public void setTransform(DmlTransform value);
    public /*BWMode*/int getBWMode(); public void setBWMode(/*BWMode*/int value);
    public DmlGeometry getGeometry(); public void setGeometry(DmlGeometry value);
    public DmlFill getFill(); public void setFill(DmlFill value);
    public DmlOutline getOutline(); public void setOutline(DmlOutline value);
    public DmlShapeStyle getStyle(); public void setStyle(DmlShapeStyle value);
    public DmlShapeEffectsCollection getEffects(); public void setEffects(DmlShapeEffectsCollection value);
    public DmlScene3DProperties getScene3DProperties(); public void setScene3DProperties(DmlScene3DProperties value);
    public DmlShape3DProperties getShape3DProperties(); public void setShape3DProperties(DmlShape3DProperties value);
    public StringToObjDictionary<DmlExtension> getSpPrExtensions(); public void setSpPrExtensions(StringToObjDictionary<DmlExtension> value);
}


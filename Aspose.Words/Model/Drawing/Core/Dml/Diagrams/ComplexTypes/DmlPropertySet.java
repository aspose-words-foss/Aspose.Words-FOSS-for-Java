// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.3.4 prSet (Property Set)
/// This element holds properties and customizations which are used throughout certain elements in DiagramML.
/// </summary>
class DmlPropertySet
{
    DmlLayoutVariablePropertySet getLayoutVariablePropertySet() { return mLayoutVariablePropertySet; }
    void setLayoutVariablePropertySet(DmlLayoutVariablePropertySet value) { mLayoutVariablePropertySet = value; }

    DmlShapeStyle getStyle() { return mStyle; }
    void setStyle(DmlShapeStyle value) { mStyle = value; }

    DmlPropertySetPr getPrSet() { return mPrSet; }

    private DmlLayoutVariablePropertySet mLayoutVariablePropertySet;
    private DmlShapeStyle mStyle;
    private /*final*/ DmlPropertySetPr mPrSet = new DmlPropertySetPr();
}


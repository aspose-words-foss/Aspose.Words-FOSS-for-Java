// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.5.12 txPr (Text Properties)
/// This element defines special text formatting that can be applied to text through a style label.
/// </summary>
class DmlDiagramTextPr
{
    DmlFlatText getFlatText() { return mFlatText; }
    void setFlatText(DmlFlatText value) { mFlatText = value; }

    DmlShape3DProperties getShape3DProperties() { return mShape3DProperties; }
    void setShape3DProperties(DmlShape3DProperties value) { mShape3DProperties = value; }

    private DmlFlatText mFlatText;
    private DmlShape3DProperties mShape3DProperties;
}


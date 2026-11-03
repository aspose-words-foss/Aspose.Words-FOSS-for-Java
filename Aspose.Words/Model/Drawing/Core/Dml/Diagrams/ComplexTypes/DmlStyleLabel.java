// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.5.10 styleLbl (Style Label)
/// </summary>
class DmlStyleLabel extends DmlExtensionListSource
{
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    DmlScene3DProperties getScene3D() { return mScene3D; }
    void setScene3D(DmlScene3DProperties value) { mScene3D = value; }

    DmlShape3DProperties getShape3D() { return mShape3D; }
    void setShape3D(DmlShape3DProperties value) { mShape3D = value; }

    DmlDiagramTextPr getTextProperties() { return mTextProperties; }
    void setTextProperties(DmlDiagramTextPr value) { mTextProperties = value; }

    DmlShapeStyle getStyle() { return mStyle; }
    void setStyle(DmlShapeStyle value) { mStyle = value; }

    private String mName;
    private DmlScene3DProperties mScene3D;
    private DmlShape3DProperties mShape3D;
    private DmlDiagramTextPr mTextProperties;
    private DmlShapeStyle mStyle;
}


// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.4.10 styleLbl (Style Label)
/// </summary>
class DmlColorTransformationStyleLabel extends DmlExtensionListSource
{
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    DmlDiagramColors getFillColorList() { return mFillColorList; }
    void setFillColorList(DmlDiagramColors value) { mFillColorList = value; }

    DmlDiagramColors getLineColorList() { return mLineColorList; }
    void setLineColorList(DmlDiagramColors value) { mLineColorList = value; }

    DmlDiagramColors getEffectColorList() { return mEffectColorList; }
    void setEffectColorList(DmlDiagramColors value) { mEffectColorList = value; }

    DmlDiagramColors getTextLineColorList() { return mTextLineColorList; }
    void setTextLineColorList(DmlDiagramColors value) { mTextLineColorList = value; }

    DmlDiagramColors getTextFillColorList() { return mTextFillColorList; }
    void setTextFillColorList(DmlDiagramColors value) { mTextFillColorList = value; }

    DmlDiagramColors getTextEffectColorList() { return mTextEffectColorList; }
    void setTextEffectColorList(DmlDiagramColors value) { mTextEffectColorList = value; }

    private String mName;
    private DmlDiagramColors mFillColorList;
    private DmlDiagramColors mLineColorList;
    private DmlDiagramColors mEffectColorList;
    private DmlDiagramColors mTextLineColorList;
    private DmlDiagramColors mTextFillColorList;
    private DmlDiagramColors mTextEffectColorList;
}


// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.6 Layout Definition
/// </summary>
class DmlDiagramLayout extends DmlExtensionListSource
{
    DmlDiagramLayout(String relId)
    {
        mRelId = relId;
    }

    String getRelId() { return mRelId; }

    String getUniqueId() { return mUniqueId; }
    void setUniqueId(String value) { mUniqueId = value; }

    String getMinVersion() { return mMinVersion; }
    void setMinVersion(String value) { mMinVersion = value; }

    String getDefaultStyle() { return mDefaultStyle; }
    void setDefaultStyle(String value) { mDefaultStyle = value; }

    DmlDiagramLayoutNode getLayoutNode() { return mLayoutNode; }
    void setLayoutNode(DmlDiagramLayoutNode value) { mLayoutNode = value; }

    DmlColorTransformCategory[] getCategories() { return mCategories; }
    void setCategories(DmlColorTransformCategory[] value) { mCategories = value; }

    DmlDiagramString[] getTitles() { return mTitles; }
    void setTitles(DmlDiagramString[] value) { mTitles = value; }

    DmlDiagramString[] getDescriptions() { return mDescriptions; }
    void setDescriptions(DmlDiagramString[] value) { mDescriptions = value; }

    DmlDiagramDataModel getSampleData() { return mSampleData; }
    void setSampleData(DmlDiagramDataModel value) { mSampleData = value; }

    DmlDiagramDataModel getColorTransformSampleData() { return mColorTransformSampleData; }
    void setColorTransformSampleData(DmlDiagramDataModel value) { mColorTransformSampleData = value; }

    DmlDiagramDataModel getStyleData() { return mStyleData; }
    void setStyleData(DmlDiagramDataModel value) { mStyleData = value; }

    private DmlDiagramDataModel mSampleData;
    private DmlDiagramDataModel mColorTransformSampleData;
    private DmlDiagramDataModel mStyleData;
    private DmlDiagramString[] mTitles;
    private DmlDiagramString[] mDescriptions;
    private DmlColorTransformCategory[] mCategories;
    private String mUniqueId;
    private String mMinVersion;
    private String mDefaultStyle;
    private DmlDiagramLayoutNode mLayoutNode;
    private /*final*/ String mRelId;
}


// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// 21.4.5.7 styleDef (Style Definition)
/// </summary>
class DmlDiagramStyleDefinition extends DmlExtensionListSource
{
    String getUniqueId() { return mUniqueId; }
    void setUniqueId(String value) { mUniqueId = value; }

    String getMinVersion() { return mMinVersion; }
    void setMinVersion(String value) { mMinVersion = value; }

    DmlScene3DProperties getScene3D() { return mScene3D; }
    void setScene3D(DmlScene3DProperties value) { mScene3D = value; }

    DmlStyleLabel[] getStyleLabels() { return mStyleLabels; }
    void setStyleLabels(DmlStyleLabel[] value) { mStyleLabels = value; }

    DmlColorTransformCategory[] getCategories() { return mCategories; }
    void setCategories(DmlColorTransformCategory[] value) { mCategories = value; }

    DmlDiagramString[] getTitles() { return mTitles; }
    void setTitles(DmlDiagramString[] value) { mTitles = value; }

    DmlDiagramString[] getDescriptions() { return mDescriptions; }
    void setDescriptions(DmlDiagramString[] value) { mDescriptions = value; }

    public DmlStyleLabel getStyleLabel(String labelName)
    {
        if (!StringUtil.hasChars(labelName))
            return null;

        for (DmlStyleLabel label : mStyleLabels)
            if (msString.equals(label.getName(), labelName))
                return label;

        return null;
    }

    private DmlDiagramString[] mTitles;
    private DmlDiagramString[] mDescriptions;
    private DmlColorTransformCategory[] mCategories;
    private String mUniqueId;
    private String mMinVersion;
    private DmlScene3DProperties mScene3D;
    private DmlStyleLabel[] mStyleLabels;
}


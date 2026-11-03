// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// 21.4.4.3 colorsDef (Color Transform Definitions)
/// </summary>
class DmlDiagramColorTransform extends DmlExtensionListSource
{
    String getUniqueId() { return mUniqueId; }
    void setUniqueId(String value) { mUniqueId = value; }

    String getMinVersion() { return mMinVersion; }
    void setMinVersion(String value) { mMinVersion = value; }

    DmlColorTransformationStyleLabel[] getStyleLabels() { return mStyleLabels; }
    void setStyleLabels(DmlColorTransformationStyleLabel[] value) { mStyleLabels = value; }

    DmlColorTransformCategory[] getCategories() { return mCategories; }
    void setCategories(DmlColorTransformCategory[] value) { mCategories = value; }

    DmlDiagramString[] getTitles() { return mTitles; }
    void setTitles(DmlDiagramString[] value) { mTitles = value; }

    DmlDiagramString[] getDescriptions() { return mDescriptions; }
    void setDescriptions(DmlDiagramString[] value) { mDescriptions = value; }

    public DmlColorTransformationStyleLabel getStyleLabel(String labelName)
    {
        if (!StringUtil.hasChars(labelName))
            return null;

        for (DmlColorTransformationStyleLabel label : mStyleLabels)
            if (msString.equals(label.getName(), labelName))
                return label;

        return null;
    }

    private DmlDiagramString[] mTitles;
    private DmlDiagramString[] mDescriptions;
    private String mUniqueId;
    private String mMinVersion;
    private DmlColorTransformationStyleLabel[] mStyleLabels;
    private DmlColorTransformCategory[] mCategories;
}


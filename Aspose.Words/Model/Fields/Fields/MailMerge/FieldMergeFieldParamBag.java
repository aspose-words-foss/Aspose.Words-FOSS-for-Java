// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2014 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an result of the MERGEFIELD field code parsing.
/// </summary>
class FieldMergeFieldParamBag
{
    String getPrefix() { return mPrefix; }; void setPrefix(String value) { mPrefix = value; };

    private String mPrefix;

    String getFieldName() { return mFieldName; }; void setFieldName(String value) { mFieldName = value; };

    private String mFieldName;

    /*MergeFieldType*/int getMergeFieldType() { return mMergeFieldType; }; void setMergeFieldType(/*MergeFieldType*/int value) { mMergeFieldType = value; };

    private /*MergeFieldType*/int mMergeFieldType;

    MergeFieldImageDimension getImageWidth() { return mImageWidth; }; void setImageWidth(MergeFieldImageDimension value) { mImageWidth = value; };

    private MergeFieldImageDimension mImageWidth;

    MergeFieldImageDimension getImageHeight() { return mImageHeight; }; void setImageHeight(MergeFieldImageDimension value) { mImageHeight = value; };

    private MergeFieldImageDimension mImageHeight;

    String getFieldNameWithPrefix()
    {
        return FieldMergeField.getMergeFieldName(getPrefix(), getFieldName());
    }
}


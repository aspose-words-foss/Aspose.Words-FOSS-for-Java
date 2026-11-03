// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/10/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Parses and composes the INCLUDEPICTURE field code.
/// </summary>
class FieldCodeIncludePicture
{
    /// <summary>
    /// Parses an INCLUDEPICTURE field code into parts.
    /// </summary>
    static FieldCodeIncludePicture parse(FieldBundle fieldBundle) throws Exception
    {
        Debug.assertTrue(fieldBundle.getFieldType() == FieldType.FIELD_INCLUDE_PICTURE || fieldBundle.getFieldType() == FieldType.FIELD_IMPORT);

        IFieldIncludePictureCode field = (IFieldIncludePictureCode)fieldBundle.getField();
        FieldCodeIncludePicture result = new FieldCodeIncludePicture();

        result.setSourceFullName(field.getSourceFullName());
        result.isLinkOnly(field.isLinked());

        return result;
    }

    /// <summary>
    /// Full path to the linked picture. Never null.
    /// </summary>
    String getSourceFullName() { return mSourceFullName; }
    private void setSourceFullName(String value) { mSourceFullName = (value == null) ? "" : value; }

    /// <summary>
    /// True when the image is not stored in the document.
    /// </summary>
    boolean isLinkOnly() { return mIsLinkOnly; }; private void isLinkOnly(boolean value) { mIsLinkOnly = value; };

    private boolean mIsLinkOnly;

    private String mSourceFullName = "";
}


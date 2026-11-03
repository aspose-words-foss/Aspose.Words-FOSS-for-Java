// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.msNumber;


/// <summary>
/// Represents 5.7.2.122 numFmt (Number Format) element.
/// This element specifies number formatting for the parent element.
/// </summary>
class DmlChartNumFormat implements Cloneable
{
    DmlChartNumFormat deepClone()
    {
        return (DmlChartNumFormat)memberwiseClone();
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlChartNumFormat value = (DmlChartNumFormat)obj;

        return
            (ArgumentUtil.bothAreNull(getFormatCode(), value.getFormatCode()) ||
                ((getFormatCode() != null) && msString.equals(getFormatCode(), value.getFormatCode(), StringComparison.ORDINAL))) &&
            (ArgumentUtil.bothAreNull(getSourceFormatCode(), value.getSourceFormatCode()) ||
                ((getSourceFormatCode() != null) && msString.equals(getSourceFormatCode(), value.getSourceFormatCode(), StringComparison.ORDINAL))) &&
            (getSourceLinked() == value.getSourceLinked());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        if (getFormatCode() != null)
            hash ^= getFormatCode().hashCode();
        if (getSourceFormatCode() != null)
            hash ^= getSourceFormatCode().hashCode();
        hash ^= msNumber.booleanHashCode(getSourceLinked());

        return hash;
    }

    /// <summary>
    /// Format code validated for rendering.
    /// </summary>
    String getFormatCode() { return mFormatCode; }
    void setFormatCode(String value)
    {
        mSourceFormatCode = value;
        mFormatCode = DmlChartFormatCodeValidator.validateFormatCode(value);
    }

    boolean getSourceLinked() { return mSourceLinked; }
    void setSourceLinked(boolean value) { mSourceLinked = value; }

    /// <summary>
    /// Returns format code value read from the document.
    /// Used to preserve format code upon writing chart back to DOCX document form the model.
    /// </summary>
    String getSourceFormatCode() { return mSourceFormatCode; }

    private String mSourceFormatCode;
    private String mFormatCode;
    private boolean mSourceLinked = true;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}


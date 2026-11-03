// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.msString;


/// <summary>
/// Base class for color modifiers with the percentage property.
/// </summary>
abstract class DmlPercentageBasedColorModifier extends DmlColorModifier
{
    public /*override*/ IDmlColorModifier deepClone()
    {
        DmlPercentageBasedColorModifier newColorModifier = createEmptyObject();
        newColorModifier.setValue(getValue());
        return newColorModifier;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlPercentageBasedColorModifier value = (DmlPercentageBasedColorModifier)obj;

        return (MathUtil.areEqual(value.getValue(), getValue()));
    }

    public /*override*/ int hashCode()
    {
        int hash = /*enum.hashCode()=(int)value*/getModifierType();
        hash ^= msNumber.doubleHashCode(getValue());
        return hash;
    }

    protected static void writeCore(String tagName, String prefix, double value, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        String tagNameWithPrefix = msString.format("{0}:{1}", prefix, tagName);
        builder.startElement(tagNameWithPrefix);
        builder.writeAttribute(DmlNamespaceUtil.getAttrName(prefix, "val"),
            DmlPercentageUtil.toPercentOrDmlPercent(value, isIsoStrict));
        builder.endElement(tagNameWithPrefix);
    }

    protected abstract DmlPercentageBasedColorModifier createEmptyObject();

    /// <summary>
    /// Value is in fraction representation.
    /// </summary>
    double getValue() { return mValue; }
    void setValue(double value) { mValue = value; }

    private double mValue;
}


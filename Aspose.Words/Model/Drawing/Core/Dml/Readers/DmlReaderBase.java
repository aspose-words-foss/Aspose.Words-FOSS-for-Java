// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/10/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;


/// <summary>
/// Base class for reading DML shapes.
/// </summary>
class DmlReaderBase
{
    /// <summary>
    /// Warns current reader element is not supported and ignores it.
    /// </summary>
    protected static void warnNotSupportedAndIgnoreElement(NrxXmlReader reader)
    {
        warnNotSupported(reader);
        reader.ignoreElement();
    }

    /// <summary>
    /// Warns current reader element is unexpected and ignores it.
    /// </summary>
    protected static void warnUnexpectedAndIgnoreElement(NrxXmlReader reader)
    {
        warnUnexpected(reader);
        reader.ignoreElement();
    }

    /// <summary>
    /// Warns current reader element is not supported.
    /// </summary>
    private static void warnNotSupported(NrxXmlReader reader)
    {
        reader.warn(
            WarningType.DATA_LOSS,
            WarningSource.DRAWING_ML,
            msString.format(WarningStrings.NOT_SUPPORTED_TAG, reader.getLocalName()));
    }

    /// <summary>
    /// Warns current reader element is unexpected.
    /// </summary>
    protected static void warnUnexpected(NrxXmlReader reader)
    {
        reader.warn(
            WarningType.UNEXPECTED_CONTENT,
            WarningSource.DRAWING_ML,
            msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, reader.getLocalName()));
    }

    /// <summary>
    /// Warns shape is ignored.
    /// </summary>
    protected static void warnShapeIgnored(NrxXmlReader reader, String details)
    {
        reader.warn(
            WarningType.UNEXPECTED_CONTENT,
            WarningSource.DRAWING_ML,
            msString.format(WarningStrings.SHAPE_IGNORED, details));
    }

    /// <summary>
    /// Returns shape's type by <paramref name="tagName"/>.
    /// </summary>
    protected static /*DmlNodeType*/int getShapeType(String tagName)
    {
        switch (gStringSwitchMap.of(tagName))
        {
            case /*"sp"*/0:
                return DmlNodeType.SHAPE;
            case /*"wsp"*/1:
                return DmlNodeType.WORDPROCESSING_SHAPE;
            case /*"cxnSp"*/2:
                return DmlNodeType.CONNECTOR_SHAPE;
            case /*"lockedCanvas"*/3:
                return DmlNodeType.LOCKED_CANVAS;
            case /*"wpc"*/4:
                return DmlNodeType.WORDPROCESSING_CANVAS;
            case /*"grpSp"*/5:
                return DmlNodeType.GROUP_SHAPE;
            case /*"wgp"*/6:
                return DmlNodeType.WORDPROCESSING_GROUP_SHAPE;
            case /*"spTree"*/7:
                return DmlNodeType.SP_TREE;
            case /*"chart"*/8:
                return DmlNodeType.CHART;
            case /*"relIds"*/9:
                return DmlNodeType.DIAGRAM;
            case /*"pic"*/10:
                return DmlNodeType.PICTURE;
            case /*"graphicFrame"*/11:
                return DmlNodeType.GRAPHIC_FRAME;
            default:
                throw new IllegalArgumentException("Unexpected tag name");
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"sp",
		"wsp",
		"cxnSp",
		"lockedCanvas",
		"wpc",
		"grpSp",
		"wgp",
		"spTree",
		"chart",
		"relIds",
		"pic",
		"graphicFrame"
	);

}


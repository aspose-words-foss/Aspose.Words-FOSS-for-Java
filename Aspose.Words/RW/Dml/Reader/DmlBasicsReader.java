// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MathUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.System.Drawing.Rectangle;
import com.aspose.Debug;


/// <summary>
/// Reads common DrawingML elements from the 5.1.2 Basics section of the OOXML specification.
/// </summary>
/*static*/ class DmlBasicsReader
{
	/* Simulation of static class by using private constructor */
	private DmlBasicsReader()
	{}

    /// <summary>
    /// Reads an element that contains two attributes x and y with integer values.
    /// </summary>
    static /*Point*/long readXY(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        int x = 0;
        int y = 0;

        // Has attributes only.
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"x"*/0:
                    x = MathUtil.doubleToInt(reader.getValueAsEmus(complianceInfo));
                    break;
                case /*"y"*/1:
                    y = MathUtil.doubleToInt(reader.getValueAsEmus(complianceInfo));
                    break;
                default:
                    reader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, reader.getLocalName()));
                    break;
            }
        }

        return msPoint.ctor(x, y);
    }

    /// <summary>
    /// Reads an element that contains two attributes cx and cy with integer values.
    /// </summary>
    static /*Size*/long readCxCy(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        int cx = 0;
        int cy = 0;

        // Has attributes only.
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"cx"*/2:
                    cx = MathUtil.doubleToInt(reader.getValueAsEmus(complianceInfo));
                    break;
                case /*"cy"*/3:
                    cy = MathUtil.doubleToInt(reader.getValueAsEmus(complianceInfo));
                    break;
                default:
                    reader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, reader.getLocalName()));
                    break;
            }
        }

        return msSize.ctor(cx, cy);
    }

    static Rectangle readRect(DocxXmlReader reader)
    {
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"l"*/4:
                    left = NrxXmlReader.xmlToPercent(reader.getValue(), reader.getComplianceInfo());
                    break;
                case /*"t"*/5:
                    top = NrxXmlReader.xmlToPercent(reader.getValue(), reader.getComplianceInfo());
                    break;
                case /*"r"*/6:
                    right = NrxXmlReader.xmlToPercent(reader.getValue(), reader.getComplianceInfo());
                    break;
                case /*"b"*/7:
                    bottom = NrxXmlReader.xmlToPercent(reader.getValue(), reader.getComplianceInfo());
                    break;
                default:
                    reader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, reader.getLocalName()));
                    break;
            }
        }
        return Rectangle.fromLTRB(left, top, right, bottom);
    }

    /// <summary>
    /// Reads 5.1.2.1.19 graphicFrameLocks (Graphic Frame Locks).
    /// Reads 5.1.2.1.31 picLocks (Picture Locks).
    /// 
    /// This element specifies all locking properties for a graphic frame. These properties 
    /// inform the generating application about specific properties that have been previously 
    /// locked and thus should not be changed.
    /// </summary>
    static void readLocks(NrxXmlReader reader, ShapePr shapePr)
    {
        // Read attributes.
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"noAdjustHandles"*/8:
                    // picLocks
                    shapePr.setAttr(ShapeAttr.LOCK_ADJUST_HANDLES, reader.getValueAsBool());
                    break;
                case /*"noChangeArrowheads"*/9:
                    // picLocks
                    // Not in VML.
                    shapePr.setAttr(ShapeAttr.DML_LOCK_ARROW_HEADS, reader.getValueAsBool());
                    break;
                case /*"noChangeAspect"*/10:
                    // picLocks
                    // graphicFrameLocks
                    shapePr.setAttr(ShapeAttr.LOCK_ASPECT_RATIO, reader.getValueAsBool());
                    break;
                case /*"noDrilldown"*/11:
                    // graphicFrameLocks
                    // Not in VML.
                    shapePr.setAttr(ShapeAttr.DML_LOCK_DRILL_DOWN, reader.getValueAsBool());
                    break;
                case /*"noChangeShapeType"*/12:
                    // picLocks
                    shapePr.setAttr(ShapeAttr.LOCK_SHAPE_TYPE, reader.getValueAsBool());
                    break;
                case /*"noCrop"*/13:
                    // picLocks
                    shapePr.setAttr(ShapeAttr.LOCK_CROPPING, reader.getValueAsBool());
                    break;
                case /*"noEditPoints"*/14:
                    // picLocks
                    shapePr.setAttr(ShapeAttr.LOCK_VERTICES, reader.getValueAsBool());
                    break;
                case /*"noGrp"*/15:
                    // picLocks
                    // graphicFrameLocks
                    shapePr.setAttr(ShapeAttr.LOCK_AGAINST_GROUPING, reader.getValueAsBool());
                    break;
                case /*"noMove"*/16:
                    // picLocks
                    // graphicFrameLocks
                    shapePr.setAttr(ShapeAttr.LOCK_POSITION, reader.getValueAsBool());
                    break;
                case /*"noResize"*/17:
                    // picLocks
                    // graphicFrameLocks
                    // Not in VML.
                    shapePr.setAttr(ShapeAttr.DML_LOCK_RESIZE, reader.getValueAsBool());
                    break;
                case /*"noRot"*/18:
                    // picLocks
                    shapePr.setAttr(ShapeAttr.LOCK_ROTATION, reader.getValueAsBool());
                    break;
                case /*"noSelect"*/19:
                    // picLocks
                    // graphicFrameLocks
                    shapePr.setAttr(ShapeAttr.LOCK_AGAINST_SELECT, reader.getValueAsBool());
                    break;
                case /*"a"*/20:
                    // This is xmlns:a attribute, ignore.
                    break;
                default:
                    reader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DRAWING_ML, msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, reader.getLocalName()));
                    break;
            }
        }

        // Read elements.
        reader.moveToElement();
        String tagName = reader.getLocalName();
        while (reader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"extLst"*/21:
                    // Not supported.
                    break;
                default:
                    Debug.fail(reader.getLocalName());
                    reader.ignoreElement();
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"x",
		"y",
		"cx",
		"cy",
		"l",
		"t",
		"r",
		"b",
		"noAdjustHandles",
		"noChangeArrowheads",
		"noChangeAspect",
		"noDrilldown",
		"noChangeShapeType",
		"noCrop",
		"noEditPoints",
		"noGrp",
		"noMove",
		"noResize",
		"noRot",
		"noSelect",
		"a",
		"extLst"
	);

}


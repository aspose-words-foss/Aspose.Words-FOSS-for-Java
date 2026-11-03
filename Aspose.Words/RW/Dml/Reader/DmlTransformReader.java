// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


class DmlTransformReader extends DmlReaderBase
{
    private DmlTransformReader(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        mReader = reader;
        mComplianceInfo = complianceInfo;
    }

    /// <summary>
    /// 20.1.7.5 xfrm (2D Transform for Grouped Objects)
    /// This element is nearly identical to the representation of 2-D transforms for ordinary shapes (20.1.7.6).
    /// The only addition is a member to represent the Child offset and the Child extents.
    /// </summary>
    static DmlGroupTransform readGroupTransform(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        if (!"xfrm".equals(reader.getLocalName()))
            return null;

        DmlTransformReader transformReader = new DmlTransformReader(reader, complianceInfo);
        DmlGroupTransform transform = new DmlGroupTransform();
        transformReader.read2DTransformForGroupedObjects(transform);
        return transform;
    }

    /// <summary>
    /// 20.1.7.6 xfrm (2D Transform for Individual Objects)
    /// This element represents 2-D transforms for ordinary shapes.
    /// </summary>
    static DmlTransform readTransform(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        if (!"xfrm".equals(reader.getLocalName()) && !"txXfrm".equals(reader.getLocalName()))
            return null;

        DmlTransformReader transformReader = new DmlTransformReader(reader, complianceInfo);

        DmlTransform transform = new DmlTransform();
        transformReader.read2DTransformForIndividualObjects(transform);
        return transform;
    }

    private void read2DTransformForGroupedObjects(DmlGroupTransform transform)
    {
        readAttributes(transform);
        while (mReader.readChild("xfrm"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"ext"*/0:
                    readExtents(transform);
                    break;
                case /*"off"*/1:
                    readOffset(transform);
                    break;
                case /*"chExt"*/2:
                    transform.setChildWidth(mReader.readDoubleAttribute("cx", 0.0));
                    transform.setChildHeight(mReader.readDoubleAttribute("cy", 0.0));
                    break;
                case /*"chOff"*/3:
                    transform.setChildX(mReader.readAttributeAsEmus("x", 0.0, mComplianceInfo));
                    transform.setChildY(mReader.readAttributeAsEmus("y", 0.0, mComplianceInfo));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
    }

    private void read2DTransformForIndividualObjects(DmlTransform transform)
    {
        String elementName = mReader.getLocalName();
        readAttributes(transform);
        while (mReader.readChild(elementName))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"ext"*/0:
                    readExtents(transform);
                    break;
                case /*"off"*/1:
                    readOffset(transform);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
    }

    private void readAttributes(DmlTransform transform)
    {
        boolean flipH = mReader.readBoolAttribute("flipH", false);
        boolean flipV = mReader.readBoolAttribute("flipV", false);

        if (flipH)
            transform.setFlipOrientation(FlipOrientation.HORIZONTAL);

        if (flipV)
            transform.setFlipOrientation(FlipOrientation.VERTICAL);

        if (flipH && flipV)
            transform.setFlipOrientation(FlipOrientation.BOTH);

        transform.setRotation(DmlAngle.createWithNormalization(mReader.readDoubleAttribute("rot", 0.0)));
    }

    private void readOffset(DmlTransform transform)
    {
        transform.setX(mReader.readAttributeAsEmus("x", 0.0, mComplianceInfo));
        transform.setY(mReader.readAttributeAsEmus("y", 0.0, mComplianceInfo));
    }

    private void readExtents(DmlTransform transform)
    {
        transform.setWidth(mReader.readAttributeAsEmus("cx", 0.0, mComplianceInfo));
        transform.setHeight(mReader.readAttributeAsEmus("cy", 0.0, mComplianceInfo));
    }

    private /*final*/ NrxXmlReader mReader;
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ext",
		"off",
		"chExt",
		"chOff"
	);

}


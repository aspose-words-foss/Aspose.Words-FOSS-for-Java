// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Represents a class building DrawingML path from XML.
/// </summary>
class DmlPathReader extends DmlReaderBase
{
    private DmlPathReader(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        mReader = reader;
        mComplianceInfo = complianceInfo;
        mPath = new DmlPath();
    }

    /// <summary>
    /// Builds a path using specified XML reader.
    /// </summary>
    /// <param name="reader">The reader.</param>
    /// <param name="complianceInfo"></param>
    /// <returns>Null if the current node in the reader isn't a path node.</returns>
    static DmlPath read(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        if (!"path".equals(reader.getLocalName()))
            return null;

        DmlPathReader pathReader = new DmlPathReader(reader, complianceInfo);
        pathReader.readPath();
        return pathReader.mPath;
    }

    /// <summary>
    /// Reads the path.
    /// </summary>
    private void readPath()
    {
        mPath.setFillMode(readFillMode());

        while (mReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"h"*/0:
                    mPath.setHeight(mReader.getValueAsDouble());
                    break;
                case /*"w"*/1:
                    mPath.setWidth(mReader.getValueAsDouble());
                    break;
                case /*"stroke"*/2:
                    mPath.setStroke(mReader.getValueAsBool());
                    break;
                case /*"extrusionOk"*/3:
                    mPath.setExtrusionOk(mReader.getValueAsBool());
                    break;
                default:
                    warnUnexpected(mReader);
                    break;
            }
        }

        while (mReader.readChild("path"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"close"*/4:
                    mPath.addPathPart(readClose());
                    break;
                case /*"lnTo"*/5:
                    mPath.addPathPart(readLineTo());
                    break;
                case /*"moveTo"*/6:
                    mPath.addPathPart(readMoveTo());
                    break;
                case /*"arcTo"*/7:
                    mPath.addPathPart(readArcTo());
                    break;
                case /*"cubicBezTo"*/8:
                    mPath.addPathPart(readCubicBezTo());
                    break;
                case /*"quadBezTo"*/9:
                    mPath.addPathPart(readQuadBezTo());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
    }

    private /*DmlPathFillMode*/int readFillMode()
    {
        String value = mReader.readAttribute("fill", "");
        return DmlEnum.dmlToPathFillMode(value);
    }

    /// <summary>
    /// Reads the quadBezTo tag.
    /// </summary>
    private IDmlPathPart readQuadBezTo()
    {
        DmlAdjustablePoint[] points = new DmlAdjustablePoint[2];
        int index = 0;
        while (mReader.readChild("quadBezTo"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"pt"*/10:
                {
                    if (index < points.length)
                    {
                        points[index] = readPoint();
                        index++;
                    }
                    else
                        warnUnexpectedAndIgnoreElement(mReader);
                    break;
                }
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }

        return new DmlQuadraticBezierTo(points);
    }

    /// <summary>
    /// Reads the cubicBezTo tag.
    /// </summary>
    private IDmlPathPart readCubicBezTo()
    {
        DmlAdjustablePoint[] points = new DmlAdjustablePoint[3];
        int index = 0;
        while (mReader.readChild("cubicBezTo"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"pt"*/10:
                {
                    if (index < points.length)
                    {
                        points[index] = readPoint();
                        index++;
                    }
                    else
                        warnUnexpectedAndIgnoreElement(mReader);
                    break;
                }
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }

        return new DmlCubicBezierTo(points);
    }

    /// <summary>
    /// Reads the arcTo.
    /// </summary>
    private IDmlPathPart readArcTo()
    {
        DmlArcTo arcTo = new DmlArcTo();
        String hR = mReader.readAttribute("hR", "");
        checkForIsoTransitional(hR);
        arcTo.setHeightRadius(new DmlAdjustableCoordinate(hR));
        String wR = mReader.readAttribute("wR", "");
        checkForIsoTransitional(wR);
        arcTo.setWidthRadius(new DmlAdjustableCoordinate(wR));
        arcTo.setStartAngle(new DmlAdjustableAngle(mReader.readAttribute("stAng", "")));
        arcTo.setSwingAngle(new DmlAdjustableAngle(mReader.readAttribute("swAng", "")));
        return arcTo;
    }

    /// <summary>
    /// Checks if the input value is in format that is introduced by ISO/IEC 29500.
    /// Sets the ISO Transitional flag in this case.
    /// </summary>
    private void checkForIsoTransitional(String value)
    {
        if ((mComplianceInfo != null) && (NrxXmlReader.isUniversalMeasure(value)))
            mComplianceInfo.markAsIsoTransitional();
    }

    /// <summary>
    /// Reads the close.
    /// </summary>
    private static IDmlPathPart readClose()
    {
        return new DmlClose();
    }

    /// <summary>
    /// Reads the line to.
    /// </summary>
    private IDmlPathPart readLineTo()
    {
        DmlLineTo result = new DmlLineTo();
        while (mReader.readChild("lnTo"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"pt"*/10:
                    result.setPoint(readPoint());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
        return result;
    }

    /// <summary>
    /// Reads the point.
    /// </summary>
    private DmlAdjustablePoint readPoint()
    {
        if (!"pt".equals(mReader.getLocalName()))
            throw new IllegalStateException("Cannot read a point from the '{0}' tag. The 'pt' tag is required.");
        String x = mReader.readAttribute("x", "");
        checkForIsoTransitional(x);
        String y = mReader.readAttribute("y", "");
        checkForIsoTransitional(y);
        return new DmlAdjustablePoint(x, y);
    }

    /// <summary>
    /// Reads the move to.
    /// </summary>
    private IDmlPathPart readMoveTo()
    {
        DmlMoveTo moveTo = new DmlMoveTo();
        while (mReader.readChild("moveTo"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"pt"*/10:
                    moveTo.setPoint(readPoint());
                    return moveTo;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
        return moveTo;
    }

    private /*final*/ DmlPath mPath;
    private /*final*/ NrxXmlReader mReader;
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"h",
		"w",
		"stroke",
		"extrusionOk",
		"close",
		"lnTo",
		"moveTo",
		"arcTo",
		"cubicBezTo",
		"quadBezTo",
		"pt"
	);

}


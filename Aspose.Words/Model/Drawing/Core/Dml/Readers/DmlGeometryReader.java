// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents a class building DrawingML shape by xml
/// </summary>
class DmlGeometryReader extends DmlReaderBase
{
    private DmlGeometryReader(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        mReader = reader;
        mComplianceInfo = complianceInfo;
    }

    static DmlGeometry read(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        DmlGeometryReader geometryReader = new DmlGeometryReader(reader, complianceInfo);
        return geometryReader.readCore();
    }

    private DmlGeometry readCore()
    {
        switch (gStringSwitchMap.of(mReader.getLocalName()))
        {
            case /*"custGeom"*/0:
                DmlGeometry geometry = new DmlGeometry();
                readGeometry("custGeom", false, geometry);
                return geometry;
            case /*"prstGeom"*/1:
                String presetName = mReader.readAttribute("prst", "");
                DmlGeometry presetGeometry = getPresetGeometry(presetName);

                if (presetGeometry == null)
                    return new DmlGeometry();

                readGeometry("prstGeom", false, presetGeometry);
                return presetGeometry;
            default:
                warnUnexpectedAndIgnoreElement(mReader);
                return new DmlGeometry();
        }
    }

    static DmlGeometry getPresetGeometry(String presetName)
    {
        IPresetGeometryXmlRepository repository = new PresetGeometryXmlRepository();
        String presetXml = repository.getPresetGeometryXml(presetName);
        if (!StringUtil.hasChars(presetXml))
            return null;

        String actualPresetName = presetName;
        if ("rect".equals(presetName))
        {
            // We have to rewrite xml because XmlReader
            // doesn't handle properly following xml:
            // <rect>... <rect/>...</rect>
            presetXml = "<rect1" + presetXml.substring((5), (5) + (presetXml.length() - 10)) + "rect1>";
            actualPresetName = "rect1";
        }

        NrxXmlReader xmlReader = new NrxXmlReader(presetXml, null);
        DmlGeometryReader geometryReader = new DmlGeometryReader(xmlReader, null);

        DmlGeometry geometry = new DmlGeometry();
        geometry.setPresetName(presetName);
        geometryReader.readGeometry(actualPresetName, true, geometry);

        return geometry;
    }

    /// <summary>
    /// Reads the geometry.
    /// </summary>
    /// <remarks>
    /// 20.1.9.8 custGeom (Custom Geometry)
    /// This element specifies the existence of a custom geometric shape. This shape
    /// consists of a series of lines and curves described within a creation path.
    /// In addition to this there can also be adjust values, guides, adjust handles,
    /// connection sites and an inscribed rectangle specified for this custom geometric shape.
    /// </remarks>
    private void readGeometry(String tag, boolean isPreset, DmlGeometry geometry)
    {
        while (mReader.readChild(tag))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"pathLst"*/2:
                    readPathList(geometry);
                    break;
                case /*"avLst"*/3:
                    readAdjustableValues(geometry.getGuides(), isPreset, mReader, mGuidesNames);
                    break;
                case /*"gdLst"*/4:
                    readGuides(geometry, isPreset);
                    break;
                case /*"rect"*/5:
                    geometry.setDmlTextboxRect(readTextboxRect());
                    break;
                case /*"ahLst"*/6:
                    readAdjustHandleList(geometry);
                    break;
                case /*"cxnLst"*/7:
                    readConnectionSites(geometry);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }

        validateConnectionSites(geometry);
    }

    /// <summary>
    /// Reads list of shape connection sites.
    /// </summary>
    /// <remarks>
    /// 20.1.9.10 cxnLst (List of Shape Connection Sites)
    /// This element specifies all the connection sites that are used for this shape. A connection site is
    /// specified by defining a point within the shape bounding box that can have a cxnSp element attached
    /// to it. These connection sites are specified using the shape coordinate system that is specified
    /// within the ext transform element.
    /// </remarks>
    private void readConnectionSites(DmlGeometry geometry)
    {
        Debug.assertTrue(geometry != null);

        while (mReader.readChild("cxnLst"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"cxn"*/8:
                    readConnectionSite(geometry);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
    }

    /// <summary>
    ///  Reads shape connection site element.
    /// </summary>
    /// <param name="geometry">Object with geometry properties.</param>
    private void readConnectionSite(DmlGeometry geometry)
    {
        Debug.assertTrue(geometry != null);

        String angle = mReader.readAttribute("ang", "");

        // Document can not be opened by MSW, when attribute "angle" are missing or empty.
        // For resilience purposes just skip current connection site.
        if (!checkAttribute(angle, true))
            return;

        DmlConnectionSite site = new DmlConnectionSite(angle);

        // Read "pos" child element.
        while (mReader.readChild("cxn"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"pos"*/9:
                    readShapePosition(site);
                    break;
                default:
                    // Skip any another elements.
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }

        // Document can not be opened by MSW, when "pos" element is missing.
        // For resilience purposes just skip current connection site.
        if (site.getCoordinates() == null)
            WarningUtil.warn(mReader.getWarningCallback(), WarningType.UNEXPECTED_CONTENT,
              WarningSource.DRAWING_ML, WarningStrings.UNACCEPTABLE_MARKUP);
        else
            geometry.getConnectionSites().add(site);
    }

    /// <summary>
    /// Reads a position coordinate within the shape bounding box.
    /// </summary>
    /// <param name="site">Connection site which holds position element.</param>
    private void readShapePosition(DmlConnectionSite site)
    {
        Debug.assertTrue(site != null);

        String xCoord = "";
        String yCoord = "";

        while (mReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"x"*/10:
                    xCoord = mReader.getValue();
                    break;
                case /*"y"*/11:
                    yCoord = mReader.getValue();
                    break;
                default:
                    break;
            }
        }

        // Document can not be opened by MSW, when coordinates attributes are missing or empty.
        // For resilience purposes just skip current connection site.
        if (checkAttribute(xCoord, false) && checkAttribute(yCoord, false))
            site.setCoordinates(new DmlAdjustablePoint(xCoord, yCoord));
    }

    /// <summary>
    /// Checks that connection site attributes have valid values and
    /// if values are not correct then skip such sites.
    /// </summary>
    /// <param name="geometry">Object with geometry properties.</param>
    private void validateConnectionSites(DmlGeometry geometry)
    {
        Debug.assertTrue(geometry != null);

        if (geometry.getConnectionSites().size() == 0)
            return;

        // This method was added for resilience purposes.
        ArrayList<DmlConnectionSite> validSites = new ArrayList<DmlConnectionSite>();

        for(DmlConnectionSite site : geometry.getConnectionSites())
        {
            String ang = site.getAngle().getString();

            DmlAdjustablePoint coord = site.getCoordinates();
            String xCoord = coord.getX().getString();
            String yCoord = coord.getY().getString();

            double dValX= FormatterPal.tryParseDoubleInvariant(xCoord);
            double dValY= FormatterPal.tryParseDoubleInvariant(yCoord);
            double dValAng = FormatterPal.tryParseDoubleInvariant(ang);

            // Position of the connection site can store as an absolute coordinate position
            // or a reference to a geometry guide. If reference is invalid then skip this site.
            // MSW does not open such documents.
            if ((Double.isNaN(dValX) && !mGuidesNames.contains(xCoord)) ||
                (Double.isNaN(dValY) && !mGuidesNames.contains(yCoord)) ||
                (Double.isNaN(dValAng) && !mGuidesNames.contains(ang)))
                WarningUtil.warn(mReader.getWarningCallback(), WarningType.UNEXPECTED_CONTENT,
                     WarningSource.DRAWING_ML, WarningStrings.UNACCEPTABLE_MARKUP);
            else
                msArrayList.add(validSites, site);
        }

        // Replace collection with valid connection sites.
        geometry.setConnectionSites(validSites);
    }

    /// <summary>
    /// Checks that attribute value is not empty, otherwise register warning
    /// and skip current element if it is requested.
    /// </summary>
    /// <param name="val">Attribute value for check.</param>
    /// <param name="registerWarn">When true and value is empty, then register warning and skip element.</param>
    /// <returns>True when attribute is not empty.</returns>
    private boolean checkAttribute(String val, boolean registerWarn)
    {
        boolean hasChars = StringUtil.hasChars(val);

        if (!hasChars && registerWarn)
        {
            WarningUtil.warn(mReader.getWarningCallback(), WarningType.UNEXPECTED_CONTENT,
                WarningSource.DRAWING_ML, WarningStrings.UNACCEPTABLE_MARKUP);
            mReader.ignoreElementNoWarn();
        }

        return hasChars;
    }

    private DmlTextBoxRect readTextboxRect()
    {
        String l = "";
        String t = "";
        String r = "";
        String b = "";

        // Read attributes.
        while (mReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"l"*/12:
                    l = mReader.getValue();
                    checkForIsoTransitional(l);
                    break;
                case /*"t"*/13:
                    t = mReader.getValue();
                    checkForIsoTransitional(t);
                    break;
                case /*"r"*/14:
                    r = mReader.getValue();
                    checkForIsoTransitional(r);
                    break;
                case /*"b"*/15:
                    b = mReader.getValue();
                    checkForIsoTransitional(b);
                    break;
                default:
                    break;
            }
        }

        // There must be no children.
        while (mReader.readChild("rect"))
            warnUnexpectedAndIgnoreElement(mReader);

        return new DmlTextBoxRect(l, t, r, b);
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
    /// Reads guides
    /// </summary>
    /// <remarks>
    /// 20.1.9.12 gdLst (List of Shape Guides)
    /// This element specifies all the guides that are used for this shape.
    /// A guide is specified by the gd element and defines a calculated value
    /// that can be used for the construction of the corresponding shape.
    /// </remarks>
    private void readGuides(DmlGeometry geometry, boolean isPreset)
    {
        while (mReader.readChild("gdLst"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"gd"*/16:
                    readGuide(false, geometry.getGuides(), isPreset, mReader, mGuidesNames);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads guide and collects guide name when collection to store is specified.
    /// </summary>
    /// <param name="isAdjustableValue">True when adjustable value is used.</param>
    /// <param name="guides">Guide collection.</param>
    /// <param name="isPreset">True when preset geometric shape is used.</param>
    /// <param name="reader">Input data reader.</param>
    /// <param name="guidesNames">Dictionary to collect guides names.</param>
    private static void readGuide(boolean isAdjustableValue, DmlGuides guides, boolean isPreset, NrxXmlReader reader,
        HashSetGeneric<String> guidesNames)
    {
        String guideName = reader.readAttribute("name", "");
        String guideFormula = reader.readAttribute("fmla", "");
        if ((msString.equals(guideFormula, "")) || (msString.equals(guideName, "")))
            return;

        if (isAdjustableValue)
            guides.addAdjustableValue(guideName, guideFormula, isPreset);
        else
            guides.addGuide(guideName, guideFormula, isPreset);

        if (guidesNames != null)
            guidesNames.add(guideName);
    }

    static void readGuide(boolean isAdjustableValue, DmlGuides guides, boolean isPreset, NrxXmlReader reader)
    {
        readGuide(isAdjustableValue, guides, isPreset, reader, null);
    }

    /// <summary>
    /// Reads adjustable values
    /// </summary>
    /// <remarks>
    /// 20.1.9.5 avLst (List of Shape Adjust Values)
    /// This element specifies
    /// the adjust values that are applied to the specified shape.
    /// An adjust value is simply a guide that has a value based formula
    /// specified. That is, no calculation takes place for an adjust value guide.
    /// Instead, this guide specifies a parameter value that is used for calculations within the shape guides.
    /// </remarks>
    static void readAdjustableValues(DmlGuides guides, boolean isPreset, NrxXmlReader reader)
    {
        readAdjustableValues(guides, isPreset, reader, null);
    }

    /// <summary>
    /// Reads adjustable value and collects guide name when collection to store is specified.
    /// </summary>
    /// <param name="guides">Guide collection.</param>
    /// <param name="isPreset">True when preset geometric shape is used.</param>
    /// <param name="reader">Input data reader.</param>
    /// <param name="guidesNames">Dictionary to collect guides names.</param>
    private static void readAdjustableValues(DmlGuides guides, boolean isPreset, NrxXmlReader reader,
        HashSetGeneric<String> guidesNames)
    {
          while (reader.readChild("avLst"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"gd"*/16:
                    readGuide(true, guides, isPreset, reader, guidesNames);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the path list.
    /// </summary>
    /// <remarks>
    /// 20.1.9.16 pathLst (List of Shape Paths)
    /// This element specifies the entire path that is to make up a single geometric shape. The pathLst can consist
    /// of many individual paths within it.
    /// </remarks>
    private void readPathList(DmlGeometry geometry)
    {
        while (mReader.readChild("pathLst"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"path"*/17:
                    DmlPath path = DmlPathReader.read(mReader, mComplianceInfo);
                    geometry.addPath(path);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
    }

    private void readAdjustHandleList(DmlGeometry geometry)
    {
        // Currently adjust handles are only required to determine adjust value type for diagrams.
        // So read only guide reference attributes.
        while (mReader.readChild("ahLst"))
        {
            switch (gStringSwitchMap.of(mReader.getLocalName()))
            {
                case /*"ahPolar"*/18:
                    DmlAdjustHandlePolar polar = new DmlAdjustHandlePolar();
                    polar.setGdRefAng(mReader.readAttribute("gdRefAng", ""));
                    polar.setGdRefR(mReader.readAttribute("gdRefR", ""));
                    geometry.getAdjustHandles().add(polar);
                    break;
                case /*"ahXY"*/19:
                    DmlAdjustHandleXY xy = new DmlAdjustHandleXY();
                    xy.setGdRefX(mReader.readAttribute("gdRefX", ""));
                    xy.setGdRefY(mReader.readAttribute("gdRefY", ""));
                    geometry.getAdjustHandles().add(xy);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mReader);
                    break;
            }
        }
    }

    private /*final*/ NrxXmlReader mReader;
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;

    /// <summary>
    /// Dictionary which holds names of the guides.
    /// </summary>
    private /*final*/ HashSetGeneric<String> mGuidesNames = new HashSetGeneric<String>();

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"custGeom",
		"prstGeom",
		"pathLst",
		"avLst",
		"gdLst",
		"rect",
		"ahLst",
		"cxnLst",
		"cxn",
		"pos",
		"x",
		"y",
		"l",
		"t",
		"r",
		"b",
		"gd",
		"path",
		"ahPolar",
		"ahXY"
	);

}


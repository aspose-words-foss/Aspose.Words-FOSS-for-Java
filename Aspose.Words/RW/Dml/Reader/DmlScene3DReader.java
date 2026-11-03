// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


class DmlScene3DReader extends DmlReaderBase
{
    private DmlScene3DReader(DocxDocumentReaderBase reader, OoxmlComplianceInfo complianceInfo)
    {
        mDocumentReader = reader;
        mXmlReader = reader.getXmlReader();
        mComplianceInfo = complianceInfo;
    }

    static DmlScene3DProperties readScene3DProperties(DocxDocumentReaderBase reader, OoxmlComplianceInfo complianceInfo) throws Exception
    {
        return readScene3DProperties(reader, false, complianceInfo);
    }

    static DmlScene3DProperties readScene3DProperties(DocxDocumentReaderBase reader, boolean isTheme,
        OoxmlComplianceInfo complianceInfo) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlScene3DReader scene3DReader = new DmlScene3DReader(reader, complianceInfo);

        DmlScene3DProperties scene3D = new DmlScene3DProperties(isTheme);

        while (xmlReader.readChild("scene3d"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"backdrop"*/0:
                    scene3D.setBackdropPlane(scene3DReader.readBackdropPlane());
                    break;
                case /*"camera"*/1:
                    scene3DReader.readCameraProperties(scene3D.getCamera());
                    break;
                case /*"lightRig"*/2:
                    scene3DReader.readLightRigProperties(scene3D.getLightRig());
                    break;
                case /*"extLst"*/3:
                    scene3D.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return scene3D;
    }

    static DmlShape3DProperties readShape3DProperties(DocxDocumentReaderBase reader, OoxmlComplianceInfo complianceInfo) throws Exception
    {
        return readShape3DProperties(reader, false, complianceInfo);
    }

    static DmlShape3DProperties readShape3DProperties(DocxDocumentReaderBase reader, boolean isTheme,
        OoxmlComplianceInfo complianceInfo) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        DmlScene3DReader shape3DReader = new DmlScene3DReader(reader, complianceInfo);
        String tagName = xmlReader.getLocalName();

        DmlShape3DProperties shape3DProperties = new DmlShape3DProperties(isTheme);

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"z"*/4:
                    shape3DProperties.setZ(xmlReader.getValueAsEmus(complianceInfo));
                    break;
                case /*"extrusionH"*/5:
                    shape3DProperties.setExtrusionHeight(xmlReader.getValueAsDouble());
                    break;
                case /*"contourW"*/6:
                    shape3DProperties.setContourWidth(xmlReader.getValueAsDouble());
                    break;
                case /*"prstMaterial"*/7:
                    shape3DProperties.setPresetMaterial(DmlEnum.dmlToPresetMaterial(xmlReader.getValue()));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        xmlReader.moveToElement();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"bevelT"*/8:
                    shape3DProperties.setBevelTop(shape3DReader.readBevel());
                    break;
                case /*"bevelB"*/9:
                    shape3DProperties.setBevelBottom(shape3DReader.readBevel());
                    break;
                case /*"extrusionClr"*/10:
                {
                    while (xmlReader.readChild("extrusionClr"))
                    {
                        DmlColor color = DmlColorReader.read(xmlReader, complianceInfo);
                        if ((shape3DProperties.getExtrusionColor() == null) && (color != null))
                            shape3DProperties.setExtrusionColor(color);
                    }
                    break;
                }
                case /*"contourClr"*/11:
                {
                    while (xmlReader.readChild("contourClr"))
                    {
                        DmlColor color = DmlColorReader.read(xmlReader, complianceInfo);
                        if ((shape3DProperties.getContourColor() == null) && (color != null))
                            shape3DProperties.setContourColor(color);
                    }
                    break;
                }
                case /*"extLst"*/3:
                    shape3DProperties.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return shape3DProperties;
    }

    private void readLightRigProperties(DmlLightRig lightRig)
    {
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"dir"*/12:
                    lightRig.setDirection(DmlEnum.dmlToLightRigDirection(mXmlReader.getValue()));
                    break;
                case /*"rig"*/13:
                    lightRig.setLightRigType(DmlEnum.dmlToLightRigType(mXmlReader.getValue()));
                    break;
                default:
                    warnUnexpected(mXmlReader);
                    break;
            }
        }

        mXmlReader.moveToElement();
        while (mXmlReader.readChild("lightRig"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"rot"*/14:
                    lightRig.setRotation(readRotation());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }
    }

    private void readCameraProperties(DmlCamera camera)
    {
        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"fov"*/15:
                    camera.setFovAngle(new DmlAngle(mXmlReader.getValueAsDouble()));
                    break;
                case /*"prst"*/16:
                    camera.setPresetCameraType(DmlEnum.dmlToPresetCameraType(mXmlReader.getValue()));
                    break;
                case /*"zoom"*/17:
                    camera.setZoom(DmlPercentageUtil.fromPercentOrDmlPercent(mXmlReader.getValue(), mComplianceInfo));
                    break;
                default:
                    warnUnexpected(mXmlReader);
                    break;
            }
        }

        mXmlReader.moveToElement();
        while (mXmlReader.readChild("camera"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"rot"*/14:
                    camera.setRotation(readRotation());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }
    }

    private DmlBackdropPlane readBackdropPlane() throws Exception
    {
        DmlBackdropPlane backdrop = new DmlBackdropPlane();

        while (mXmlReader.readChild("backdrop"))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"anchor"*/18:
                    backdrop.setAnchor(readPoint3D());
                    break;
                case /*"norm"*/19:
                    backdrop.setNormalVector(readVector3D());
                    break;
                case /*"up"*/20:
                    backdrop.setUpVector(readVector3D());
                    break;
                case /*"extLst"*/3:
                    backdrop.setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return backdrop;
    }

    private DmlBevel readBevel()
    {
        DmlBevel bevel = new DmlBevel();

        bevel.setWidth(mXmlReader.readDoubleAttribute("w", 76200d));
        bevel.setHeight(mXmlReader.readDoubleAttribute("h", 76200d));
        bevel.setBevelPresetType(DmlEnum.dmlToBevelPresetType(mXmlReader.readAttribute("prst", "")));

        return bevel;
    }

    private DmlRotation3D readRotation()
    {
        DmlRotation3D rot = new DmlRotation3D();
        rot.setLatitude(new DmlAngle(mXmlReader.readDoubleAttribute("lat", 0.0d)));
        rot.setLongitude(new DmlAngle(mXmlReader.readDoubleAttribute("lon", 0.0d)));
        rot.setRevolution(new DmlAngle(mXmlReader.readDoubleAttribute("rev", 0.0d)));
        return rot;
    }

    private DmlPoint3D readPoint3D()
    {
        DmlPoint3D point = new DmlPoint3D();
        point.setX(mXmlReader.readAttributeAsEmus("x", 0.0d, mComplianceInfo));
        point.setY(mXmlReader.readAttributeAsEmus("y", 0.0d, mComplianceInfo));
        point.setZ(mXmlReader.readAttributeAsEmus("z", 0.0d, mComplianceInfo));
        return point;
    }

    private DmlPoint3D readVector3D()
    {
        DmlPoint3D point = new DmlPoint3D();
        point.setX(mXmlReader.readAttributeAsEmus("dx", 0.0d, mComplianceInfo));
        point.setY(mXmlReader.readAttributeAsEmus("dy", 0.0d, mComplianceInfo));
        point.setZ(mXmlReader.readAttributeAsEmus("dz", 0.0d, mComplianceInfo));
        return point;
    }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;
    private /*final*/ NrxXmlReader mXmlReader;
    private /*final*/ OoxmlComplianceInfo mComplianceInfo;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"backdrop",
		"camera",
		"lightRig",
		"extLst",
		"z",
		"extrusionH",
		"contourW",
		"prstMaterial",
		"bevelT",
		"bevelB",
		"extrusionClr",
		"contourClr",
		"dir",
		"rig",
		"rot",
		"fov",
		"prst",
		"zoom",
		"anchor",
		"norm",
		"up"
	);

}


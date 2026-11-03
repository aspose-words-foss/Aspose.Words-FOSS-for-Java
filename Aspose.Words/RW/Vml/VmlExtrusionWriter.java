// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.FormatterPal;



/// <summary>
/// Writes 'v:extrusion' sub-element of v:shape to WordML.
/// </summary>
class VmlExtrusionWriter
{
    VmlExtrusionWriter(NrxXmlBuilder builder, IVmlShapeWriterContext context)
    {
        mBuilder = builder;
        mContext = context;
    }

    /// <summary>
    /// Add 'v:extrusion' related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        // Determine if the added attribute is written inside 'v:extrusion' element.
        // This is done to avoid writing of the empty 'v:extrusion' declaration.
        if (value instanceof /*boolean*/Boolean)
            switch (key)
            {
                // these values are true by default
                case ShapeAttr.TD_PARALLEL:
                case ShapeAttr.TD_LIGHT_FACE:
                case ShapeAttr.TD_CONSTRAIN_ROTATION:
                case ShapeAttr.TD_KEY_HARSH:
                    if (!(/*boolean*/Boolean)value)
                        mAttrCount++;
                    break;
                // other possible values are false by default
                default:
                    if ((/*boolean*/Boolean)value)
                        mAttrCount++;
                    break;
            }
        else
            mAttrCount++;

        switch (key)
        {
            case ShapeAttr.TD_AMBIENT_INTENSITY:
                mTDAmbientIntensity = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_CONSTRAIN_ROTATION:
                mTDConstrainRotation = VmlUtil.boolToVml(value, true);
                break;
            case ShapeAttr.TD_DIFFUSE_AMOUNT:
                mTDDiffuseAmount = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_EDGE_THICKNESS:
                mTDEdgeThickness = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_EXTRUDE_BACKWARD:
                mTDExtrudeBackward = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_EXTRUDE_FORWARD:
                mTDExtrudeForward = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_EXTRUDE_PLANE:
                mTDExtrudePlane = VmlEnum.planeTypeToVml((/*PlaneType*/Integer)value);
                break;
            case ShapeAttr.TD_EXTRUSION_COLOR:
                mTDExtrusionColor = mContext.colorToVml((DrColor)value);
                break;
            case ShapeAttr.TD_EXTRUSION_COLOR_EXT:
                // RK Why nothing?
                break;
            case ShapeAttr.TD_EXTRUSION_COLOR_EXT_CMY:
                // RK Why nothing?
                break;
            case ShapeAttr.TD_EXTRUSION_COLOR_EXT_K:
                // RK Why nothing?
                break;
            case ShapeAttr.TD_EXTRUSION_COLOR_EXT_MOD:
                // RK Why nothing?
                break;
            case ShapeAttr.TD_FILL_HARSH:
                mTDFillHarsh = VmlUtil.boolToVml(value, false);
                break;
            case ShapeAttr.TD_FILL_INTENSITY:
                mTDFillIntensity = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_FILL_X:
                mTDFillX = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_FILL_Y:
                mTDFillY = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_FILL_Z:
                mTDFillZ = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_KEY_HARSH:
                mTDKeyHarsh = VmlUtil.boolToVml(value, true);
                break;
            case ShapeAttr.TD_KEY_INTENSITY:
                mTDKeyIntensity = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_KEY_X:
                mTDKeyX = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_KEY_Y:
                mTDKeyY = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_KEY_Z:
                mTDKeyZ = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_LIGHT_FACE:
                mTDLightFace = VmlUtil.boolToVml(value, true);
                break;
            case ShapeAttr.TD_METALLIC:
                mTDMetallic = VmlUtil.boolToVml(value, false);
                break;
            case ShapeAttr.TD_ON:
                mTDOn = VmlUtil.boolToVml(value, false);
                break;
            case ShapeAttr.TD_ORIGIN_X:
                mTDOriginX = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_ORIGIN_Y:
                mTDOriginY = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_PARALLEL:
                if (!(/*boolean*/Boolean)value)
                    mTDParallel = "perspective";
                break;
            case ShapeAttr.TD_RENDER_MODE:
                mTDRenderMode = VmlEnum.threeDRenderModeToVml((/*ThreeDRenderMode*/Integer)value);
                break;
            case ShapeAttr.TD_ROTATION_ANGLE:
                mTDRotationAngle = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_ROTATION_ANGLE_X:
                mTDRotationAngleX = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_ROTATION_ANGLE_Y:
                mTDRotationAngleY = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_ROTATION_AXIS_X:
                mTDRotationAxisX = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_ROTATION_AXIS_Y:
                mTDRotationAxisY = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_ROTATION_AXIS_Z:
                mTDRotationAxisZ = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_ROTATION_CENTER_AUTO:
                mTDRotationCenterAuto = VmlUtil.boolToVml(value, false);
                break;
            case ShapeAttr.TD_ROTATION_CENTER_X:
                mTDRotationCenterX = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_ROTATION_CENTER_Y:
                mTDRotationCenterY = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_ROTATION_CENTER_Z:
                mTDRotationCenterZ = VmlUtil.emuToVmlMillimeters((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_SHININESS:
                mTDShininess = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_SKEW_AMOUNT:
                mTDSkewAmount = VmlUtil.percentsToVml((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_SKEW_ANGLE:
                mTDSkewAngle = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_SPECULAR_AMOUNT:
                mTDSpecularAmount = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_TOLERANCE:
                mTDTolerance = VmlUtil.fixedToVml(value);
                break;
            case ShapeAttr.TD_USE_EXTRUSION_COLOR:
                // Ignored.
                break;
            case ShapeAttr.TD_VIEWPOINT_X:
                mTDViewpointX = VmlUtil.emuToVmlMillimeters((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_VIEWPOINT_Y:
                mTDViewpointY = VmlUtil.emuToVmlMillimeters((/*int*/Integer)value);
                break;
            case ShapeAttr.TD_VIEWPOINT_Z:
                mTDViewpointZ = VmlUtil.emuToVmlMillimeters((/*int*/Integer)value);
                break;
            default:
                return;
        }
    }

    /// <summary>
    /// Write 'v:extrusion' element.
    /// </summary>
    void write()
    {
        if (mAttrCount <= 0)
            return;

        mBuilder.startElement("o:extrusion");

        mBuilder.writeAttribute("v:ext", "view");
        mBuilder.writeAttribute("specularity", mTDSpecularAmount);
        mBuilder.writeAttribute("diffusity", mTDDiffuseAmount);
        mBuilder.writeAttribute("shininess", mTDShininess);
        mBuilder.writeAttribute("edge", mTDEdgeThickness);
        mBuilder.writeAttribute("foredepth", mTDExtrudeForward);
        mBuilder.writeAttribute("backdepth", mTDExtrudeBackward);
        mBuilder.writeAttribute("plane", mTDExtrudePlane);
        mBuilder.writeAttribute("color", mTDExtrusionColor);
        mBuilder.writeAttribute("on", mTDOn);
        mBuilder.writeAttribute("metal", mTDMetallic);
        mBuilder.writeAttribute("lightface", mTDLightFace);
        mBuilder.writeSet("rotationangle", mTDRotationAngleX, mTDRotationAngleY);
        mBuilder.writeSet("orientation", mTDRotationAxisX, mTDRotationAxisY, mTDRotationAxisZ);
        mBuilder.writeAttribute("orientationangle", mTDRotationAngle);
        mBuilder.writeSet("rotationcenter", mTDRotationCenterX, mTDRotationCenterY, mTDRotationCenterZ);
        mBuilder.writeAttribute("render", mTDRenderMode);
        mBuilder.writeAttribute("facet", mTDTolerance);
        mBuilder.writeSet("viewpoint", mTDViewpointX, mTDViewpointY, mTDViewpointZ);
        mBuilder.writeSet("viewpointorigin", mTDOriginX, mTDOriginY);
        mBuilder.writeAttribute("skewangle", mTDSkewAngle);
        mBuilder.writeAttribute("skewamt", mTDSkewAmount);
        mBuilder.writeAttribute("brightness", mTDAmbientIntensity);
        mBuilder.writeSet("lightposition", mTDKeyX, mTDKeyY, mTDKeyZ);
        mBuilder.writeAttribute("lightlevel", mTDKeyIntensity);
        mBuilder.writeSet("lightposition2", mTDFillX, mTDFillY, mTDFillZ);
        mBuilder.writeAttribute("lightlevel2", mTDFillIntensity);
        // colormode - ignored in WordML
        mBuilder.writeAttribute("lockrotationcenter", mTDConstrainRotation);
        mBuilder.writeAttribute("autorotationcenter", mTDRotationCenterAuto);
        mBuilder.writeAttribute("type", mTDParallel);
        mBuilder.writeAttribute("lightharsh", mTDKeyHarsh);
        mBuilder.writeAttribute("lightharsh2", mTDFillHarsh);

        mBuilder.endElement(); //o:extrusion
    }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ IVmlShapeWriterContext mContext;

    private int mAttrCount = 0;

    private String mTDSpecularAmount = null;
    private String mTDDiffuseAmount = null;
    private String mTDShininess = null;
    private String mTDEdgeThickness = null;
    private String mTDExtrudeForward = null;
    private String mTDExtrudeBackward = null;
    private String mTDExtrudePlane = null;
    private String mTDExtrusionColor = null;
    private String mTDOn = null;
    private String mTDMetallic = null;
    private String mTDLightFace = null;
    private String mTDRotationAngleY = null;
    private String mTDRotationAngleX = null;
    private String mTDRotationAxisX = null;
    private String mTDRotationAxisY = null;
    private String mTDRotationAxisZ = null;
    private String mTDRotationAngle = null;
    private String mTDRotationCenterX = null;
    private String mTDRotationCenterY = null;
    private String mTDRotationCenterZ = null;
    private String mTDRenderMode = null;
    private String mTDTolerance = null;
    private String mTDViewpointX = null;
    private String mTDViewpointY = null;
    private String mTDViewpointZ = null;
    private String mTDOriginX = null;
    private String mTDOriginY = null;
    private String mTDSkewAngle = null;
    private String mTDSkewAmount = null;
    private String mTDAmbientIntensity = null;
    private String mTDKeyX = null;
    private String mTDKeyY = null;
    private String mTDKeyZ = null;
    private String mTDKeyIntensity = null;
    private String mTDFillX = null;
    private String mTDFillY = null;
    private String mTDFillZ = null;
    private String mTDFillIntensity = null;
    private String mTDConstrainRotation = null;
    private String mTDRotationCenterAuto = null;
    private String mTDParallel = null;
    private String mTDKeyHarsh = null;
    private String mTDFillHarsh = null;

    //v:ext    Yes    
    //specularity    Yes    
    //diffusity    Yes    
    //shininess    Yes    
    //edge    Yes    
    //foredepth    Yes    
    //backdepth    Yes    
    //plane    Yes    
    //color    Yes    
    //on    Yes    
    //metal    Yes    
    //lightface    Yes    
    //rotationangle    Yes    
    //orientation    Yes    
    //orientationangle    Yes    
    //rotationcenter    Yes    
    //render    Yes    
    //facet    Yes    
    //viewpoint    Yes    
    //viewpointorigin    Yes    
    //skewangle    Yes    
    //skewamt    Yes    
    //brightness    Yes    
    //lightposition    Yes    
    //lightlevel    Yes    
    //lightposition2    Yes    
    //lightlevel2    Yes    
    //colormode    No need    Ignored in WordML.
    //lockrotationcenter    Yes    
    //autorotationcenter    Yes    
    //type    Yes    
    //lightharsh    Yes    
    //lightharsh2    Yes    
}


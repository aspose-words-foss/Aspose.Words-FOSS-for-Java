// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********




/// <summary>
/// Writes 'o:callout' sub-element of v:shape to WordML.
/// </summary>
class VmlCalloutWriter
{
    VmlCalloutWriter(NrxXmlBuilder builder)
    {
        mBuilder = builder;
    }

    /// <summary>
    /// Add 'o:callout' related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        // Determine if the added attribute is written inside 'o:callout' element.
        // This is done to avoid writing of the empty 'o:callout' declaration.
        if (value instanceof /*boolean*/Boolean)
            switch (key)
            {
                // these values are true by default
                case ShapeAttr.CALLOUT_ON:
                case ShapeAttr.CALLOUT_TEXT_BORDER:
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
            case ShapeAttr.CALLOUT_ACCENT_BAR:
                {
                    mCalloutAccentBar = VmlUtil.boolToVml(value, false);
                    break;
                }
            case ShapeAttr.CALLOUT_ANGLE:
                {
                    mCalloutAngle = VmlEnum.calloutAngleToVml((/*CalloutAngle*/Integer)value);
                    break;
                }
            case ShapeAttr.CALLOUT_DROP_AUTO:
                {
                    mCalloutDropAuto = VmlUtil.boolToVml(value, false);
                    break;
                }
            case ShapeAttr.CALLOUT_DROP_DISTANCE:
                {
                    mCalloutDropDistance = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                    break;
                }
            case ShapeAttr.CALLOUT_DROP_TYPE:
                {
                    mCalloutDropType = VmlEnum.calloutDropTypeToVml((/*CalloutDropType*/Integer)value);
                    break;
                }
            case ShapeAttr.CALLOUT_GAP:
                {
                    mCalloutGap = VmlUtil.emuToVmlPoints((/*int*/Integer)value);
                    break;
                }
            case ShapeAttr.CALLOUT_LENGTH:
                {
                    mCalloutLength = VmlUtil.emuToVmlMillimeters((/*int*/Integer)value);
                    break;
                }
            case ShapeAttr.CALLOUT_LENGTH_SPECIFIED:
                {
                    mCalloutLengthSpecified = VmlUtil.boolToVml(value, false);
                    break;
                }
            case ShapeAttr.CALLOUT_MINUS_X:
                {
                    mCalloutMinusX = VmlUtil.boolToVml(value, false);
                    break;
                }
            case ShapeAttr.CALLOUT_MINUS_Y:
                {
                    mCalloutMinusY = VmlUtil.boolToVml(value, false);
                    break;
                }
            case ShapeAttr.CALLOUT_ON:
                {
                    mCalloutOn = VmlUtil.boolToVml(value, true);
                    break;
                }
            case ShapeAttr.CALLOUT_TEXT_BORDER:
                {
                    mCalloutTextBorder = VmlUtil.boolToVml(value, true);
                    break;
                }
            case ShapeAttr.CALLOUT_TYPE:
                {
                    mCalloutType = VmlEnum.calloutTypeToVml((/*CalloutType*/Integer)value);
                    break;
                }
            default:
                return;
        }
    }

    /// <summary>
    /// Write 'o:callout' element.
    /// </summary>
    void write()
    {
        if (mAttrCount <= 0)
            return;

        mBuilder.startElement("o:callout");

        mBuilder.writeAttribute("v:ext", "edit");
        mBuilder.writeAttribute("type", mCalloutType);
        mBuilder.writeAttribute("gap", mCalloutGap);
        mBuilder.writeAttribute("angle", mCalloutAngle);
        mBuilder.writeAttribute("drop", mCalloutDropType);
        mBuilder.writeAttribute("distance", mCalloutDropDistance);
        mBuilder.writeAttribute("length", mCalloutLength);
        mBuilder.writeAttribute("on", mCalloutOn);
        mBuilder.writeAttribute("accentbar", mCalloutAccentBar);
        mBuilder.writeAttribute("textborder", mCalloutTextBorder);
        mBuilder.writeAttribute("minusx", mCalloutMinusX);
        mBuilder.writeAttribute("minusy", mCalloutMinusY);
        mBuilder.writeAttribute("dropauto", mCalloutDropAuto);
        mBuilder.writeAttribute("lengthspecified", mCalloutLengthSpecified);

        mBuilder.endElement(); //o:callout
    }

    private /*final*/ NrxXmlBuilder mBuilder;

    private int mAttrCount = 0;

    private String mCalloutType = null;
    private String mCalloutGap = null;
    private String mCalloutAngle = null;
    private String mCalloutDropType = null;
    private String mCalloutDropDistance = null;
    private String mCalloutLength = null;
    private String mCalloutOn = null;
    private String mCalloutAccentBar = null;
    private String mCalloutTextBorder = null;
    private String mCalloutMinusX = null;
    private String mCalloutMinusY = null;
    private String mCalloutDropAuto = null;
    private String mCalloutLengthSpecified = null;


    //ext        Yes
    //type        Yes
    //gap        Yes
    //angle        Yes
    //drop        Yes
    //distance    Yes
    //length    Yes
    //on        Yes
    //accentbar    Yes
    //textborder    Yes
    //minusx    Yes
    //minusy    Yes
    //dropauto    Yes
    //lengthspecified    Yes
}


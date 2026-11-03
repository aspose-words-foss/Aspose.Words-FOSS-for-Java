// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Writes 'o:lock' sub-element of v:shape to WordML.
/// </summary>
class VmlLockWriter
{
    VmlLockWriter(NrxXmlBuilder builder)
    {
        mBuilder = builder;
    }

    /// <summary>
    /// Add 'o:lock' related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        switch (key)
        {
            case ShapeAttr.LOCK_ADJUST_HANDLES:
            {
                // Ignored.
                break;
            }
            case ShapeAttr.LOCK_AGAINST_GROUPING:
            {
                // Ignored.
                break;
            }
            case ShapeAttr.LOCK_AGAINST_SELECT:
            {
                // Ignored.
                break;
            }
            case ShapeAttr.LOCK_ASPECT_RATIO:
            {
                mLockAspectRatio = VmlUtil.boolToVml(value);
                mAttrCount++;
                break;
            }
            case ShapeAttr.LOCK_CROPPING:
            {
                // Ignored.
                break;
            }
            case ShapeAttr.LOCK_POSITION:
            {
                // Ignored.
                break;
            }
            case ShapeAttr.LOCK_ROTATION:
            {
                // Ignored.
                break;
            }
            case ShapeAttr.LOCK_SHAPE_TYPE:
            {
                if ((/*boolean*/Boolean)value)
                {
                    mLockShapeType = VmlUtil.boolToVml(value, false);
                    mAttrCount++;
                }
                break;
            }
            case ShapeAttr.LOCK_TEXT:
            {
                if ((/*boolean*/Boolean)value)
                {
                    mLockText = VmlUtil.boolToVml(value, false);
                    mAttrCount++;
                }
                break;
            }
            case ShapeAttr.LOCK_VERTICES:
            {
                if ((/*boolean*/Boolean)value)
                {
                    mLockVertices = VmlUtil.boolToVml(value, false);
                    mAttrCount++;
                }
                break;
            }
            default:
                return;
        }
    }

    /// <summary>
    /// Write 'o:lock' element.
    /// </summary>
    void write()
    {
        if (mAttrCount <= 0)
            return;

        mBuilder.startElement("o:lock");

        mBuilder.writeAttribute("v:ext", "edit");
        mBuilder.writeAttribute("aspectratio", mLockAspectRatio);
        mBuilder.writeAttribute("verticies", mLockVertices);
        mBuilder.writeAttribute("text", mLockText);
        mBuilder.writeAttribute("shapetype", mLockShapeType);

        mBuilder.endElement(); //o:lock
    }

    private /*final*/ NrxXmlBuilder mBuilder;

    private int mAttrCount;

    private String mLockAspectRatio;
    private String mLockVertices;
    private String mLockText;
    private String mLockShapeType;

    //v:ext            Yes    
    //aspectratio    Yes    
    //vertices        Yes    
    //text            Yes    
    //shapetype        Yes    
    //adjusthandles    No need    Ignored in WordML.
    //cropping        No need    Ignored in WordML.
    //grouping        No need    Ignored in WordML.
    //position        No need    Ignored in WordML.
    //rotation        No need    Ignored in WordML.
    //selection        No need    Ignored in WordML.
}


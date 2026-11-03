// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;



/// <summary>
/// Writes diagram related elements and attributes to WordML.
/// </summary>
class VmlDiagramWriter
{
    VmlDiagramWriter(IVmlShapeWriterContext context, NrxXmlBuilder builder)
    {
        mBuilder = builder;
        mContext = context;
    }

    /// <summary>
    /// Add diagram related attribute.
    /// </summary>
    /// <param name="key">Attribute key.</param>
    /// <param name="value">Attribute value.</param>
    void addAttribute(int key, Object value)
    {
        mAttrCount++;

        switch (key)
        {
            case ShapeAttr.PSEUDO_INLINE:
                // PseudoInline property is supported for DOC/RTF only.
                mAttrCount--;
                break;
            case ShapeAttr.EDIT_AS:
                mEditAs = VmlEnum.editAsToVml((/*EditAs*/Integer)value);
                mAttrCount--;
                break;
            case ShapeAttr.DIAGRAM_STYLE:
                mStyle = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.DIAGRAM_SCALE_X:
                mScaleX = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.DIAGRAM_SCALE_Y:
                mScaleY = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.DIAGRAM_FONT_SIZE:
                mFontSize = FormatterPal.intToXml((/*int*/Integer)value);
                break;
            case ShapeAttr.DIAGRAM_CONSTRAIN_BOUNDS:
                mConstrainBounds = NrxXmlUtil.intArrayToXml((int[])value);
                break;
            case ShapeAttr.DIAGRAM_AUTO_FORMAT:
                mAutoformat = VmlUtil.boolToVml(value);
                break;
            case ShapeAttr.DIAGRAM_REVERSE:
                mReverse = VmlUtil.boolToVml(value);
                break;
            case ShapeAttr.DIAGRAM_RELATIONS_TABLE:
                mRelations = (DiagramNodeRelation[])value;
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// Write 'o:diagram' element.
    /// </summary>
    void writeDiagramElement()
    {
        // Example:
        // <o:diagram v:ext="edit" dgmstyle="12" dgmscalex="31402" dgmscaley="25941" dgmfontsize="4" constrainbounds="2374,2279,9718,9623" autoformat="t" reverse="t">
        // <o:relationtable v:ext="edit">
        //        <o:rel v:ext="edit" idsrc="#_s1036" iddest="#_s1036" />
        // </o:relationtable>

        if (mAttrCount > 0)
        {
            mBuilder.startElement("o:diagram");

            mBuilder.writeAttribute("v:ext", "edit");
            mBuilder.writeAttribute("dgmstyle", mStyle);
            mBuilder.writeAttribute("dgmscalex", mScaleX);
            mBuilder.writeAttribute("dgmscaley", mScaleY);
            mBuilder.writeAttribute("dgmfontsize", mFontSize);
            mBuilder.writeAttribute("constrainbounds", mConstrainBounds);
            mBuilder.writeAttribute("autoformat", mAutoformat);
            mBuilder.writeAttribute("reverse", mReverse);

            if (mRelations != null && mRelations.length > 0)
            {
                mBuilder.startElement("o:relationtable");
                mBuilder.writeAttribute("v:ext", "edit");

                for (DiagramNodeRelation relation : mRelations)
                {
                    mBuilder.startElement("o:rel");
                    mBuilder.writeAttribute("v:ext", "edit");
                    mBuilder.writeAttribute("idsrc", mContext.getDiagramMemberName(relation.A));
                    mBuilder.writeAttribute("iddest", mContext.getDiagramMemberName(relation.B));
                    mBuilder.writeAttribute("idcntr", mContext.getDiagramMemberName(relation.C));
                    mBuilder.endElement(); //o:rel
                }

                mBuilder.endElement(); //o:relationtable
            }

            mBuilder.endElement(); //o:diagram
        }
    }

    String getEditAs() { return mEditAs; }

    private /*final*/ NrxXmlBuilder mBuilder;
    private /*final*/ IVmlShapeWriterContext mContext;

    private String mEditAs = null;

    private int mAttrCount = 0;

    private String mStyle;
    private String mScaleX;
    private String mScaleY;
    private String mFontSize;
    private String mConstrainBounds;
    private String mAutoformat;
    private String mReverse;

    private DiagramNodeRelation[] mRelations;
}


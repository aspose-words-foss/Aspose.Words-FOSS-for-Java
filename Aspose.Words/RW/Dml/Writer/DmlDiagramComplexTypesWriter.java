// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/*static*/ class DmlDiagramComplexTypesWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramComplexTypesWriter()
	{}

    static void writeLayoutVariables(String tagName, DmlLayoutVariablePropertySet layoutPrSet, DocxDocumentWriterBase writer)
    {
        if (layoutPrSet == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();
        String prefix = msString.split(tagName, ':')[0];
        
        builder.startElement(tagName);

        writeLayoutVariableAttribute(DmlLayoutVariableAttr.ORG_CHART, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.CHILD_MAX, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.CHILD_PREF, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.BULLET_ENABLED, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.DIRECTION, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.HIER_BRANCH_STYLE, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.ANIM_ONE, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.ANIM_LEVEL, layoutPrSet, builder, prefix);
        writeLayoutVariableAttribute(DmlLayoutVariableAttr.RESIZE_HANDLES, layoutPrSet, builder, prefix);

        builder.endElement(tagName);
    }

    private static void writeLayoutVariableAttribute(
       /*DmlLayoutVariableAttr*/int id,
       DmlLayoutVariablePropertySet props,
       DocxBuilder builder, 
       String prefix)
    {
        Object val = props.getDirectProperty(id);
        if (val == null)
            return;

        switch (id)
        {
            case DmlLayoutVariableAttr.ORG_CHART:
                builder.writeElementWithAttributes(msString.format("{0}:orgChart", prefix), "val", val);
                break;
            case DmlLayoutVariableAttr.CHILD_MAX:
                builder.writeElementWithAttributes(msString.format("{0}:chMax", prefix), "val",
                                                   ((DmlDiagramNodeCount)val).getValue());
                break;
            case DmlLayoutVariableAttr.CHILD_PREF:
                builder.writeElementWithAttributes(msString.format("{0}:chPref", prefix), "val",
                                                   ((DmlDiagramNodeCount)val).getValue());
                break;
            case DmlLayoutVariableAttr.BULLET_ENABLED:
                builder.writeElementWithAttributes(msString.format("{0}:bulletEnabled", prefix), "val", val);
                break;
            case DmlLayoutVariableAttr.DIRECTION:
                builder.writeElementWithAttributes(msString.format("{0}:dir", prefix), "val",
                                                   DmlDiagramEnum.directionToDml((/*DmlDiagramDirection*/Integer)val));
                break;
            case DmlLayoutVariableAttr.HIER_BRANCH_STYLE:
                builder.writeElementWithAttributes(msString.format("{0}:hierBranch", prefix), "val",
                                                   DmlDiagramEnum.hierBranchStyleToDml((/*DmlHierBranchStyle*/Integer)val));
                break;
            case DmlLayoutVariableAttr.ANIM_ONE:
                builder.writeElementWithAttributes(msString.format("{0}:animOne", prefix), "val",
                                                   DmlDiagramEnum.animOneToDml((/*DmlAnimOne*/Integer)val));
                break;
            case DmlLayoutVariableAttr.ANIM_LEVEL:
                builder.writeElementWithAttributes(msString.format("{0}:animLvl", prefix), "val",
                                                   DmlDiagramEnum.animLevelToDml((/*DmlAnimLevel*/Integer)val));
                break;
            case DmlLayoutVariableAttr.RESIZE_HANDLES:
                builder.writeElementWithAttributes(msString.format("{0}:resizeHandles", prefix), "val",
                                                   DmlDiagramEnum.resizeHandlesToDml((/*DmlResizeHandles*/Integer)val));
                break;
            default:
                break;
        }
    }

    static void writeStrings(String tagName, DmlDiagramString[] dmlDiagramStrings, DocxDocumentWriterBase writer)
    {
        if (dmlDiagramStrings == null || dmlDiagramStrings.length == 0)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();
        for (DmlDiagramString s : dmlDiagramStrings)
        {
            builder.startElement(tagName);
            builder.writeAttribute("lang", s.getLanguage());
            builder.writeAttributeString("val", s.getValue());
            builder.endElement(tagName);
        }
    }

    static void writeCategoryList(DmlColorTransformCategory[] categories, DocxDocumentWriterBase writer)
    {
        if (categories == null || categories.length == 0)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();
        builder.startElement("dgm:catLst");

        for (DmlColorTransformCategory category : categories)
            builder.writeElementWithAttributes("dgm:cat", "type", category.getCategoryType(), "pri", category.getPriority());

        builder.endElement("dgm:catLst");
    }
}


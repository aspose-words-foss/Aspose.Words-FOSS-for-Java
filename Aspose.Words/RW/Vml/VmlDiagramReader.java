// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


class VmlDiagramReader extends VmlShapeReaderBase
{
    static void readDiagram(ShapeBase shape, NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"ext"*/0: // v:ext
                    // Always "edit", ignore.
                    break;
                case /*"dgmstyle"*/1:
                    shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_STYLE, reader.getValueAsInt());
                    break;
                case /*"dgmscalex"*/2:
                    shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_SCALE_X, reader.getValueAsInt());
                    break;
                case /*"dgmscaley"*/3:
                    shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_SCALE_Y, reader.getValueAsInt());
                    break;
                case /*"dgmfontsize"*/4:
                    shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_FONT_SIZE, reader.getValueAsInt());
                    break;
                case /*"constrainbounds"*/5:
                    shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_CONSTRAIN_BOUNDS, VmlUtil.vmlToIntArray(reader.getValue()));
                    break;
                case /*"autoformat"*/6:
                    setBoolAttribute(shape, ShapeAttr.DIAGRAM_AUTO_FORMAT, reader.getValue());
                    break;
                case /*"reverse"*/7:
                    setBoolAttribute(shape, ShapeAttr.DIAGRAM_REVERSE, reader.getValue());
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }

        while (reader.readChild("diagram"))
        {
            if ("relationtable".equals(reader.getLocalName())) // o:relationtable
                readDiagramRelationTable(shape, reader);
        }
    }

    private static void readDiagramRelationTable(ShapeBase shape, NrxXmlReader reader)
    {
        // Attribute "v:ext" is always "edit", ignore.

        // Read "o:rel" child elements.

        ArrayList<DiagramNodeRelation> relationsList = new ArrayList<DiagramNodeRelation>();

        while (reader.readChild("relationtable"))
        {
            if ("rel".equals(reader.getLocalName())) // o:rel
                msArrayList.add(relationsList, readDiagramNodeRelation(reader));
        }

        DiagramNodeRelation[] relations = msArrayList.toArray(relationsList, new DiagramNodeRelation[0]);
        shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_RELATIONS_TABLE, relations);
    }

    private static DiagramNodeRelation readDiagramNodeRelation(NrxXmlReader reader)
    {
        DiagramNodeRelation relation = new DiagramNodeRelation();

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"ext"*/0:
                    // Always "edit", ignore.
                    break;
                case /*"idsrc"*/8:
                    relation.A = VmlUtil.calculateDiagramHash(value);
                    break;
                case /*"iddest"*/9:
                    relation.B = VmlUtil.calculateDiagramHash(value);
                    break;
                case /*"idcntr"*/10:
                    relation.C = VmlUtil.calculateDiagramHash(value);
                    break;
                default:
                    // Ignore other attributes.
                    break;
            }
        }

        return relation;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ext",
		"dgmstyle",
		"dgmscalex",
		"dgmscaley",
		"dgmfontsize",
		"constrainbounds",
		"autoformat",
		"reverse",
		"idsrc",
		"iddest",
		"idcntr"
	);

}


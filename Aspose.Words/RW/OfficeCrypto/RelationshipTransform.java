// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/03/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.Comparator;
import com.aspose.OpcRelationship;
import com.aspose.StringOrdinalComparer;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import java.util.Collections;
import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.Collections.msArrayList;


class RelationshipTransform extends Transform implements Comparator<OpcRelationship>
{
    public int /*IComparer<OpcRelationship>.*/compare(OpcRelationship x, OpcRelationship y)
    {
        return StringOrdinalComparer.DEFAULT.compare(x.getId(), y.getId());
    }

    protected /*override*/ void read(AnyXmlReader reader)
    {
        while (reader.readChild("Transform"))
        {
            if ("RelationshipReference".equals(reader.getLocalName()))
            {
                // Collect allowed relationship references.
                addRelationshipReference(reader.readAttribute("SourceId", null));
            }
            else if("RelationshipsGroupReference".equals(reader.getLocalName()))
            {
                // Collect allowed relationship types.
                addRelationshipsGroupReference(reader.readAttribute("SourceType", null));
            }
        }
    }

    /// <summary>
    /// Applies RelationshipTransform. See ISO29500-2 13.2.4.24 Relationships Transform Algorithm.
    /// </summary>
    /// <remarks>
    /// 1) Sort elements by Id attribute.
    /// 2) Add "TargetMode" attribute with default "Internal" value if it's absent.
    /// 3) Filter elements those are not in mRelationshipReferences list.
    /// </remarks>
    /*override*/ MemoryStream apply(Stream stream) throws Exception
    {
        AnyXmlReader reader = new AnyXmlReader(stream);
        ArrayList<OpcRelationship> relationships = new ArrayList<OpcRelationship>();

        while (reader.readChild("Relationships"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Relationship"*/0:
                    readRelationship(reader, relationships);
                    break;
                default:
                    throw new IllegalStateException(msString.format("Unexpected element '{0}'.", reader.getLocalName()));
            }
        }

        // Sort relationship by Id.
        Collections.sort(relationships, this);

        MemoryStream output = new MemoryStream();
        AnyXmlBuilder xmlBuilder = new AnyXmlBuilder(output, false);

        xmlBuilder.startDocument("Relationships");
        xmlBuilder.writeAttributeString("xmlns", "http://schemas.openxmlformats.org/package/2006/relationships");
        for (OpcRelationship rel : relationships)
        {
            // Output relationship only if it has allowed Id or allowed Type.
            if (mRelationshipReferences.contains(rel.getId()) || mRelationshipsGroupReferences.contains(rel.getType()))
            {
                xmlBuilder.startElement("Relationship");

                xmlBuilder.writeAttributeString("Id", rel.getId());
                xmlBuilder.writeAttributeString("Type", rel.getType());
                xmlBuilder.writeAttributeString("Target", rel.getTarget());
                xmlBuilder.writeAttributeString("TargetMode", rel.isExternal() ? "External" : "Internal");

                xmlBuilder.endElement("Relationship");
            }
        }

        xmlBuilder.endDocument();
        return output;
    }

    /*override*/ void write(AnyXmlBuilder writer)
    {
        writer.startElement("Transform");
        writer.writeAttributeString("Algorithm", ALGORITHM);

        for (String rel : mRelationshipReferences)
        {
            writer.startElement("mdssi:RelationshipReference");
            writer.writeAttributeString("SourceId", rel);
            writer.endElement("mdssi:RelationshipReference");
        }

        for(String relGroup : mRelationshipsGroupReferences)
        {
            writer.startElement("opc:RelationshipsGroupReference");
            writer.writeAttributeString("SourceType", relGroup);
            writer.endElement("opc:RelationshipsGroupReference");
        }

        writer.endElement("Transform");
    }

    void addRelationshipReference(String id)
    {
        msArrayList.add(mRelationshipReferences, id);
    }

    void addRelationshipsGroupReference(String type)
    {
        if (!mRelationshipsGroupReferences.contains(type))
            msArrayList.add(mRelationshipsGroupReferences, type);
    }

    /// <summary>
    /// Reads and collects relationships.
    /// </summary>
    private static void readRelationship(AnyXmlReader reader, ArrayList<OpcRelationship> relationships)
    {
        String id = "";
        String type = "";
        String target = "";
        boolean isExternal = false;

        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Id"*/1:
                    id = reader.getValue();
                    break;
                case /*"Type"*/2:
                    type = reader.getValue();
                    break;
                case /*"Target"*/3:
                    target = reader.getValue();
                    break;
                case /*"TargetMode"*/4:
                    isExternal = ("External".equals(reader.getValue()));
                    break;
                default:
                    // Ignore any other attributes might occur.
                    break;
            }
        }

        msArrayList.add(relationships, new OpcRelationship(id, type, target, isExternal));
    }

    static final String ALGORITHM = "http://schemas.openxmlformats.org/package/2006/RelationshipTransform";

    /// <summary>
    /// Stores allowed relationship ids.
    /// </summary>
    private /*final*/ ArrayList<String> mRelationshipReferences = new ArrayList<String>();

    /// <summary>
    /// Stores allowed relationship types.
    /// </summary>
    private /*final*/ ArrayList<String> mRelationshipsGroupReferences = new ArrayList<String>();

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"Relationship",
		"Id",
		"Type",
		"Target",
		"TargetMode"
	);

}


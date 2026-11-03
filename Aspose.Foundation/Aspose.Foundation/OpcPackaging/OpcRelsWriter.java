// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Provides static method for writing relationships package part
/// </summary>
public /*static*/ class OpcRelsWriter
{
	/* Simulation of static class by using private constructor */
	private OpcRelsWriter()
	{}

    /// <summary>
    /// Creates and writes a relationship part for the specified package.
    /// </summary>
    public static void write(OpcPackageBase _package, boolean isPrettyFormat) throws Exception
    {
        writeCore(_package, _package.getRels(), "", isPrettyFormat);

        ArrayList<OpcPackagePart> partsWithRels = new ArrayList<OpcPackagePart>();

        for (OpcPackagePart part : _package.getParts())
        {
            if (part.getRels().getCount() > 0)
                msArrayList.add(partsWithRels, part);
        }

        for (OpcPackagePart part : partsWithRels)
            writeCore(_package, part.getRels(), part.getName(), isPrettyFormat);
    }

    /// <summary>
    /// Builds URI for relationships.
    /// </summary>
    public static String getRelsUri(String uri)
    {
        // Build URI for relationships.
        int lastSlashIndex = uri.lastIndexOf('/');
        String path = uri.substring((0), (0) + (lastSlashIndex + 1));
        String name = uri.substring(lastSlashIndex + 1);
        // AN: Relationship part names should start with a slash. If there is no slash at the beginning of the part name,
        // we should add it. This is needed to write Flat Opc packages correctly.
        String relsUri = msString.format(
            "{0}{1}_rels/{2}.rels",
            msString.startsWith(path, "/", StringComparison.ORDINAL) ? "" : "/",
            path,
            name);

        return relsUri;
    }

    /// <summary>
    /// Writes relationships for the specified package part.
    /// </summary>
    private static void writeCore(OpcPackageBase _package, OpcRelationshipCollection rels, String uri, boolean isPrettyFormat) throws Exception
    {
        OpcPackagePart part = new OpcPackagePart(getRelsUri(uri), OpcContentType.RELATIONSHIPS);
        AnyXmlBuilder builder = new AnyXmlBuilder(part.getStream(), isPrettyFormat);

        builder.startDocument("Relationships");
        builder.writeAttributeString("xmlns", "http://schemas.openxmlformats.org/package/2006/relationships");

        for (OpcRelationship rel : rels)
        {
            builder.startElement("Relationship");

            builder.writeAttributeString("Id", rel.getId());
            builder.writeAttributeString("Type", rel.getType());
            builder.writeAttributeString("Target", rel.getTarget());

            if (rel.isExternal())
                builder.writeAttributeString("TargetMode", "External");

            builder.endElement();
        }

        builder.endDocument();

        _package.getParts().add(part);
    }
}


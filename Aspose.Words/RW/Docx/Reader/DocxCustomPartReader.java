// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcRelationshipCollection;
import com.aspose.OpcPackageBase;
import com.aspose.OpcRelationship;
import com.aspose.OpcPackagePart;
import com.aspose.StreamUtil;
import com.aspose.HashSetGeneric;
import com.aspose.OpcRelationshipType;


/// <summary>
/// Reads custom parts. Custom parts is a set of related parts or external targets that have
/// relationship types that are not specified in the ISO29500 standard. Microsoft Word preserves
/// such parts during open/save cycles and so do we.
/// </summary>
/*static*/ class DocxCustomPartReader
{
	/* Simulation of static class by using private constructor */
	private DocxCustomPartReader()
	{}

    /// <summary>
    /// Reads custom parts or external targets from the specified collection of relationships.
    /// </summary>
    /// <param name="rels">The relationships to child parts to enumerate. We do not pass the parent part itself here
    /// because the parent can actually be the package itself. It is enough to pass just the relationship collection.</param>
    /// <param name="package">The whole package. We need it to get the related parts.</param>
    /// <param name="customParts">Where the data is read to.</param>
    static void read(
        OpcRelationshipCollection rels,
        OpcPackageBase _package,
        CustomPartCollection customParts)
    {
        for (OpcRelationship rel : rels)
        {
            // Any part can have unknown relationships, but at the moment we load only custom parts related to the package. It is enough.
            if (G_STANDARD_PACKAGE_REL_TYPES.contains(rel.getType()))
                continue;

            CustomPart part = readCustomPart(rel, rels.getPartName(), _package, customParts);
            if (part != null)
                customParts.add(part);
        }
    }

    private static CustomPart readCustomPart(OpcRelationship rel, String parentPartName, OpcPackageBase _package, CustomPartCollection customParts)
    {
        CustomPart customPart = new CustomPart();

        customPart.setOriginalId(rel.getId());
        customPart.setRelationshipType(rel.getType());
        customPart.isExternal(rel.isExternal());            

        if (rel.isExternal())
        {
            customPart.setName(rel.getTarget());
        }
        else
        {
            OpcPackagePart opcPart = _package.getPartByName(OpcPackageBase.makeAbsolute(parentPartName, rel.getTarget()));
            // If we cannot find the related part, ignore this relationship.
            if (opcPart == null)
                return null;

            customPart.setName(opcPart.getName());
            customPart.setContentType(opcPart.getContentType());
            customPart.setData(StreamUtil.copyStreamToByteArray(opcPart.getStream()));

            // WORDSNET-10389 Custom parts can has relationships with another parts recursively.
            if (opcPart.getRels() != null)
                for (OpcRelationship childRel : opcPart.getRels())
                {
                    CustomPart childPart = readCustomPart(childRel, customPart.getName(), _package, customParts);
                    if (childPart != null)
                        customParts.add(childPart);
                }
        }

        customPart.setParentPartName(parentPartName);

        return customPart;
    }

    /// <summary>
    /// Contains relationship types that the standard defines for an OOXML package.
    /// </summary>
    private static /*final*/ HashSetGeneric<String> G_STANDARD_PACKAGE_REL_TYPES = new HashSetGeneric<String>();

    static/* DocxCustomPartReader()*/
    {
        // Standard relationship types defined for an transition OOXML package.
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.OFFICE_DOCUMENT, false));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.CORE_PROPERTIES, false));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.CUSTOM_PROPERTIES, false));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.EXTENDED_PROPERTIES, false));
        G_STANDARD_PACKAGE_REL_TYPES.add(OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.THUMBNAIL, false));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.WEB_EXTENSION_TASK_PANES, false));

        // Standard relationship types defined for an strict OOXML package.
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.OFFICE_DOCUMENT, true));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.CORE_PROPERTIES, true));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.CUSTOM_PROPERTIES, true));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.EXTENDED_PROPERTIES, true));
        G_STANDARD_PACKAGE_REL_TYPES.add(OpcRelationshipType.DIGITAL_SIGNATURE_ORIGIN);
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.THUMBNAIL, true));
        G_STANDARD_PACKAGE_REL_TYPES.add(DocxRelationshipTypes.getType(DocxRelationshipType.WEB_EXTENSION_TASK_PANES, true));
    }
}


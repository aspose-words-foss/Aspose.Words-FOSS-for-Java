// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackage;
import com.aspose.OpcPackagePart;
import com.aspose.OpcRelsWriter;
import com.aspose.OpcContentType;
import com.aspose.OpcRelationshipCollection;
import com.aspose.OpcRelationship;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Implements signing OpcPackage using XmlDsig method. Used for DOCX document.
/// </summary>
class OpcSignatureWriterDocx extends OpcSignatureWriterBase
{
    OpcSignatureWriterDocx(OpcPackage _package)
    {
    	super(_package, true);
	
    }

    /// <summary>
    /// Adds reference to part relationships.
    /// </summary>
    protected /*override*/ void addRelationshipReference(OpcPackage _package, OpcPackagePart part, ReferenceCollection references)
    {
        String relsUri = OpcRelsWriter.getRelsUri((part == null) ? "" : part.getName());

        Reference r = new Reference(relsUri, OpcContentType.RELATIONSHIPS);
        references.add(r);

        // Apply RelationshipTransform.
        RelationshipTransform transform = (RelationshipTransform)Transform.create(RelationshipTransform.ALGORITHM);
        r.getTransformCollection().add(transform);

        // Loop thought package.Rels or part.Rels
        OpcRelationshipCollection rels = (part == null) ? _package.getRels() : part.getRels();
        for (OpcRelationship rel : rels)
        {
            // Filter external relationships.
            if (rel.isExternal())
            {
                // WORDSNET-27977 Word adds all references to transform, even it is external.
                transform.addRelationshipReference(rel.getId());
            }
            else
            {
                OpcPackagePart relatedPart = (part == null)
                    ? _package.getPartByName(msString.format("/{0}", rel.getTarget()))
                    : _package.getPartByName(part.getRelatedPartName(rel));

                // Filter excluded parts.
                if (!isExcludedPart(relatedPart))
                    transform.addRelationshipReference(rel.getId());
            }
        }

        // Apply C14nTransform.
        r.getTransformCollection().add(Transform.create(C14Transform.ALGORITHM));
    }

    protected /*override*/ String getSignatureOriginPartName()
    {
        return "/_xmlsignatures/origin.sigs";
    }

    protected /*override*/ String getSignaturePartName(OpcPackage _package)
    {
        // Do this in straight way.
        int i = 1;
        while (_package.getPartByName(msString.format("/_xmlsignatures/sig{0}.xml", i)) != null)
            i++;

        return msString.format("sig{0}.xml", i);
    }

    protected /*override*/ MemoryStream getSignatureStream(ReferenceCollection refs, DigitalSignature signature) throws Exception
    {
        XmlDsigSignerOoxml xmlDsigWriter = new XmlDsigSignerOoxml();
        return xmlDsigWriter.getSignature(refs, signature);
    }

    /// <summary>
    /// Indicates that given part must be excluded from signing.
    /// </summary>
    protected /*override*/ boolean isExcludedPart(OpcPackagePart part)
    {
        if (G_EXCLUDE_NAMES.contains(part.getName()))
            return true;

        if (part.getName().contains("/[trash]"))
            return true;

        if ((OpcContentType.DIGITAL_SIGNATURE.equals(part.getContentType())) ||
            (OpcContentType.DIGITAL_SIGNATURE_ORIGIN.equals(part.getContentType())))
            return true;

        return false;
    }

    protected /*override*/ String getCertificatePartName(OpcPackage _package)
    {
        throw new UnsupportedOperationException("Should not be called.");
    }

    private static /*final*/ ArrayList<String> G_EXCLUDE_NAMES;

    static/* OpcSignatureWriterDocx()*/
    {
        G_EXCLUDE_NAMES = new ArrayList<String>();
        msArrayList.add(G_EXCLUDE_NAMES, OpcPackage.CONTENT_TYPES_PART_NAME);
        msArrayList.add(G_EXCLUDE_NAMES, "/docProps/app.xml");
        msArrayList.add(G_EXCLUDE_NAMES, "/docProps/core.xml");
    }
}


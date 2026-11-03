// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/09/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.AnyXmlBuilder;


/// <summary>
/// Base class for XML transformation objects.
/// </summary>
abstract class Transform
{
    /// <summary>
    /// Factory method used to create Transform object of given type.
    /// </summary>
    static Transform create(String alg)
    {
        switch (gStringSwitchMap.of(alg))
        {
            case/*C14Transform.ALGORITHM*/0:
                return new C14Transform();

            case/*RelationshipTransform.ALGORITHM*/1:
                return new RelationshipTransform();

            default:
                throw new IllegalStateException(msString.format("Unknown transform algorithm '{0}'", alg));
        }
    }

    /// <summary>
    /// Factory method used to create appropriate Transform object from XML element. 
    /// </summary>
    /// <remarks>
    /// Reader should be positioned at Transform XML element start.
    /// </remarks>
    static Transform create(AnyXmlReader reader)
    {
        Transform transform = create(reader.readAttribute("Algorithm", null));
        transform.read(reader);
        return transform;
    }

    /// <summary>
    /// Apply current transform to given XML.
    /// </summary>
    abstract MemoryStream apply(Stream xml) throws Exception;

    /// <summary>
    /// Writes Transform object in XML form.
    /// </summary>
    abstract void write(AnyXmlBuilder writer);

    /// <summary>
    /// Reads object from Xml element.
    /// </summary>
    /// <param name="reader"></param>
    protected abstract void read(AnyXmlReader reader);

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
		"http://schemas.openxmlformats.org/package/2006/RelationshipTransform"
	);

}


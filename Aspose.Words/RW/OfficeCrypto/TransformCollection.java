// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/03/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.AnyXmlReader;
import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.IO.MemoryStream;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Implements collection for <see cref="Transform"/> objects.
/// </summary>
class TransformCollection implements Iterable<Transform>
{
    // Appends Transform object to collection.
    void add(Transform transform)
    {
        msArrayList.add(mItems, transform);
    }

    /// <summary>
    /// Reads transform collection from XML.
    /// </summary>
    /// <param name="reader"></param>
    void read(AnyXmlReader reader)
    {
        while (reader.readChild("Transforms"))
            if ("Transform".equals(reader.getLocalName()))
                add(Transform.create(reader));
            else
                throw new IllegalStateException("");
    }

    /// <summary>
    /// Writes TransformCollection in XML form.
    /// </summary>
    void write(AnyXmlBuilder writer)
    {
        if (mItems.size() == 0)
            return;

        writer.startElement("Transforms");

        for (Transform transform : mItems)
            transform.write(writer);

        writer.endElement("Transforms");
    }

    /// <summary>
    /// Applies transformation to stream and return transformed data.
    /// </summary>
    MemoryStream apply(MemoryStream data) throws Exception
    {
        for (Transform transform : mItems)
            data = transform.apply(data);

        return data;
    }

    public Iterator<Transform> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ ArrayList<Transform> mItems = new ArrayList<Transform>();
}


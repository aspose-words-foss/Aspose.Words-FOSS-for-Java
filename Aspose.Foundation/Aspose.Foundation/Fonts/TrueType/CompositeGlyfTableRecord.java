// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Represents composite record in glyf table in OpenType file.
/// </summary>
class CompositeGlyfTableRecord extends GlyfTableRecord
{
    public CompositeGlyfTableRecord()
    {
        mComponents = new ArrayList<ComponentGlyphRecord>();
    }

    /// <summary>
    /// Reads composite record from binary reader.
    /// </summary>
    public static CompositeGlyfTableRecord readCompositeRecord(BigEndianBinaryReader reader) throws Exception
    {
        CompositeGlyfTableRecord result = new CompositeGlyfTableRecord();
        result.readHeader(reader);
        result.readComponents(reader);

        if (result.getWeHaveInstructions())
        {
            /*ushort*/short numInstructions = reader.readUInt16();
            result.setInstructions(reader.readBytes((numInstructions & 0xFFFF)));
        }

        return result;
    }

    public void readComponents(BigEndianBinaryReader reader) throws Exception
    {
        while (true)
        {
            ComponentGlyphRecord component = ComponentGlyphRecord.read(reader);
            msArrayList.add(getComponents(), component);
            if (!component.getMoreComponents())
                break;
        }
    }

    public /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writeNumContoursAndBoundingBox(writer);

        for (ComponentGlyphRecord component : getComponents())
            component.write(writer);

        if (getWeHaveInstructions())
        {
            writer.writeUInt16(getInstructions().length);
            writer.writeBytes(getInstructions(), 0, getInstructions().length);
        }
    }

    public byte[] buildComponentsData() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BigEndianBinaryWriter writer = new BigEndianBinaryWriter(stream);
            for (ComponentGlyphRecord component : getComponents())
                component.write(writer);
            return stream.toArray();
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// TrueType instructions.
    /// </summary>
    public byte[] getInstructions() { return mInstructions; }; public void setInstructions(byte[] value) { mInstructions = value; };

    private byte[] mInstructions;

    /// <summary>
    /// True when WeHaveInstructions flag is set.
    /// </summary>
    public boolean getWeHaveInstructions() { return getComponents().size() > 0 && getComponents().get(getComponents().size() - 1).getWeHaveInstructions(); }

    public ArrayList<ComponentGlyphRecord> getComponents() { return mComponents; };

    private ArrayList<ComponentGlyphRecord> mComponents;
}


// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements HTML OLE control.
/// </summary>
class HtmlOleControl extends OleControl
{
    /// <summary>
    /// Creates instance of the <see cref="HtmlOleControl"/> class with specified name, type and HTML string.
    /// </summary>
    HtmlOleControl(String name, /*HtmlOleControlType*/int type, String html)
    {
    	super(name);
	
        setType(type);
        setHtml(html);
    }

    /// <summary>
    /// Creates instance of the <see cref="HtmlOleControl"/> class with a specified name.
    /// </summary>
    HtmlOleControl(String name)
    {
    	super(name);
	
    }

    /// <summary>
    /// Reads the control from a storage.
    /// </summary>
    /*override*/ void read(MemoryStorage storage) throws Exception
    {
        MemoryStream dataStream = storage.fetchStream(Ole2StreamBase.OCX_DATA_STREAM_NAME);
        BinaryReader reader = new BinaryReader(dataStream);

        Guid clsid = new Guid(reader.readBytes(16));
        setType(clsidToType(clsid.toString()));

        byte[] htmlBytes = reader.readBytes((int)(reader.getBaseStream().getLength() - reader.getBaseStream().getPosition()));
        setHtml(Encoding.getUnicode().getString(htmlBytes));
    }

    /// <summary>
    /// Writes the control to a binary writer.
    /// </summary>
    /*override*/ /*uint*/int write(BinaryWriter writer) throws Exception
    {
        long originalPosition = writer.getBaseStream().getPosition();

        Guid clsid = new Guid(getClsidVirtual());
        writer.writeBytes(clsid.toByteArray());
        writer.writeBytes(Encoding.getUnicode().getBytes(getHtml()));

        long writtenSize = writer.getBaseStream().getPosition() - originalPosition;
        return (/*uint*/int)(writtenSize);
    }

    /// <summary>
    /// Writes the control to a storage.
    /// </summary>
    /*override*/ void write(MemoryStorage storage) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        write(new BinaryWriter(stream));

        OcxDataStream ocxData = new OcxDataStream();
        ocxData.setData(stream.toArray());
        ocxData.write(storage);

        // As we write HtmlOleControl to the OcxData stream, we must update ObjInfo stream flags accordingly.
        // See OdtPersist1 for details.
        ObjInfoStream objInfoStream = ObjInfoStream.read(storage);
        objInfoStream.setFlags1(objInfoStream.getFlags1() | OdtPersist1.STREAM);
        objInfoStream.write(storage);
    }

    /// <summary>
    /// Gets a Clsid of the control.
    /// </summary>
    protected /*override*/ String getClsidVirtual()
    {
        switch (getType())
        {
            case HtmlOleControlType.SELECT:
                return HTML_SELECT_CLSID;
            case HtmlOleControlType.OPTION:
                return HTML_OPTION_CLSID;
            case HtmlOleControlType.SUBMIT_BUTTON:
                return HTML_SUBMIT_BUTTON_CLSID;
            case HtmlOleControlType.HIDDEN:
                return HTML_HIDDEN_CLSID;
            case HtmlOleControlType.TEXT:
                return HTML_TEXT_CLSID;
            default:
                return Guid.Empty.toString();
        }
    }

    /// <summary>
    /// Converts a Clsid of the control to a type.
    /// </summary>
    private static /*HtmlOleControlType*/int clsidToType(String clsid)
    {
        switch (gStringSwitchMap.of(clsid))
        {
            case/*HTML_SELECT_CLSID*/0: return HtmlOleControlType.SELECT;
            case/*HTML_OPTION_CLSID*/1: return HtmlOleControlType.OPTION;
            case/*HTML_SUBMIT_BUTTON_CLSID*/2: return HtmlOleControlType.SUBMIT_BUTTON;
            case/*HTML_HIDDEN_CLSID*/3: return HtmlOleControlType.HIDDEN;
            case/*HTML_TEXT_CLSID*/4: return HtmlOleControlType.TEXT;
            default:
                return HtmlOleControlType.UNKNOWN;
        }
    }

    /// <summary>
    /// Gets a type of the control.
    /// </summary>
    /*HtmlOleControlType*/int getType() { return mType; }; private void setType(/*HtmlOleControlType*/int value) { mType = value; };

    private /*HtmlOleControlType*/int mType;

    /// <summary>
    /// Gets HTML string of the control.
    /// </summary>
    String getHtml() { return mHtml; }; private void setHtml(String value) { mHtml = value; };

    private String mHtml;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"5512d122-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d118-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d110-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d11c-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d11a-5cc6-11cf-8d67-00aa00bdce1d"
	);

}


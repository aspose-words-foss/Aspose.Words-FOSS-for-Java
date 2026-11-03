// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/04/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Guid;
import com.aspose.Debug;
import com.aspose.ms.System.IO.Path;
import com.aspose.StreamUtil;


/// <summary>
/// Contains data of an OOXML package embedded in a document.
/// </summary>
class OoxmlObject implements IEmbeddedObject
{
    /// <summary>
    /// Creates instance of the <see cref="OoxmlObject"/> object from a stream.
    /// </summary>
    OoxmlObject(Stream stream, String contentType, String name, Guid clsid)
    {
    	this(stream, contentType, "");
	
        mName = name;
        mClsid = clsid;
    }

    /// <summary>
    /// Creates instance of the <see cref="OoxmlObject"/> object from a stream.
    /// </summary>
    OoxmlObject(Stream stream, String contentType, String partName)
    {
        Debug.assertTrue(partName != null);

        mId = UniqueIdManager.generateInteger();

        mStream = stream;
        mContentType = contentType;
        mClsid = Guid.Empty;
        mPartName = partName;
    }

    /// <summary>
    /// Returns a name of the embedded object.
    /// </summary>
    public String /*IEmbeddedObject.*/getName()
    {
        return mName;
    }

    /// <summary>
    /// Returns instance of the <see cref="OleObject"/> object.
    /// </summary>
    public OleObject /*IEmbeddedObject.*/getOleObject()
    {
        return null;
    }

    /// <summary>
    /// Returns the extension (with the leading dot).
    /// The extension must be for the inner (e.g. unwrapped) data that can be saved directly to a file.
    /// </summary>
    public String /*IEmbeddedObject.*/getExtensionForUser(String progId)
    {
        return DocxEnum.contentTypeToExtension(getContentType());
    }

    /// <summary>
    /// Returns the file name.
    /// </summary>
    public String /*IEmbeddedObject.*/getFileNameForUser() throws Exception
    {
        // WORDSNET-24632 Return file name from the package part.
        return Path.getFileNameWithoutExtension(mPartName);
    }

    /// <summary>
    /// Saves the embedded object data in a way that makes it a valid standalone file.
    /// </summary>
    public void /*IEmbeddedObject.*/saveForUser(Stream dstStream, IShapeAttrSource attrSource) throws Exception
    {
        boolean needsUnhide =
            (DocxContentType.XLSX.equals(getContentType())) ||
            (DocxContentType.XLSM.equals(getContentType())) ||
            (DocxContentType.XLTX.equals(getContentType())) ||
            (DocxContentType.XLTM.equals(getContentType()));

        // WORDSNET-14899 Skip unhiding of a workbook when "DrawAspect" attribute has "Content" value.
        needsUnhide &= (/*boolean*/Boolean)attrSource.fetchShapeAttr(ShapeAttr.OLE_ICON);

        // Unhide workbook if needed and copy to destination stream.
        if (needsUnhide)
        {
            OleUtil.unhideOoxmlExcelWorkbook(getStream(), dstStream);
        }
        else
        {
            getStream().setPosition(0);
            StreamUtil.copyStream(getStream(), dstStream);
        }
    }

    /// <summary>
    /// Gets a boolean value, indicating either the embedded object is Forms2Ole control.
    /// </summary>
    public boolean /*IEmbeddedObject.*/isForms2OleControlInternal() { return OleControl.isForms2OleClsid(((IEmbeddedObject)this).getClsidInternal()); }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    public String /*IEmbeddedObject.*/getClsidInternal() { return mClsid.toString(); }

    /// <summary>
    /// Gets or sets Id of the embedded object.
    /// </summary>
    public int /*IEmbeddedObject.*/getId() { return mId; }
    public void /*IEmbeddedObject.*/setId(int value) { mId = value; }

    public boolean /*IEmbeddedObject.*/isEmpty() throws Exception { return getStream().getLength() == 0; }

    /// <summary>
    /// The Id of the embedded object.
    /// </summary>
    /// <remarks>
    /// Each embedded object, when stored in a file, has a unique id.
    /// Looks like RTF does not use this.
    /// </remarks>
    private int mId;

    /// <summary>
    /// Gets a stream of the OOXML object.
    /// </summary>
    Stream getStream() { return mStream; };

    private Stream mStream;

    /// <summary>
    /// Gets a type of the content of the OOXML object.
    /// </summary>
    String getContentType() { return mContentType; };

    private String mContentType;

    private /*final*/ String mName;
    private /*final*/ Guid mClsid;
    private /*final*/ String mPartName;
}


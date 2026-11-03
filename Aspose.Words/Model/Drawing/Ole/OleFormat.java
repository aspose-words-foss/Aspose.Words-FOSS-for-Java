// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2006 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.FileSystem;
import com.aspose.StreamUtil;
import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.BinaryReader;


/// <summary>
/// Provides access to the data of an OLE object or ActiveX control.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-ole-objects/">Working with Ole Objects</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Use the <see cref="Shape.OleFormat"/> property to access the data of an OLE object.
/// You do not create instances of the <see cref="OleFormat"/> class directly.</p>
///
/// <seealso cref="Shape.OleFormat"/>
/// </remarks>
public class OleFormat
{
    OleFormat(IShapeAttrSource parent)
    {
        Debug.assertTrue(parent != null);
        mParent = parent;
    }

    /// <summary>
    /// Saves the data of the embedded object into the specified stream.
    /// </summary>
    /// <remarks>
    /// <p>It is the responsibility of the caller to dispose the stream.</p>
    /// </remarks>
    /// <exception cref="InvalidOperationException">Throws if you attempt to save a linked object.</exception>
    /// <param name="stream">Where to save the object data.</param>
    /// <javaName>void save(java.io.OutputStream stream)</javaName>
    //JAVA-added public wrapper for internalized member
    public void save(java.io.OutputStream stream) throws Exception
    {
        MemoryStream tempStream = new MemoryStream();
        save(tempStream);
        tempStream.setPosition(0);
        com.aspose.ms.java.IO.JavaOnlyStreamUtil.copyStream(tempStream, stream);
    }
    void save( Stream stream) throws Exception
    {
        if (stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        if (isLink())
            throw new IllegalStateException("This OLE object is linked, and cannot be saved. Only embedded OLE objects can be saved.");

        IEmbeddedObject embeddedObject = getEmbeddedObject();
        if (embeddedObject == null)
            throw new IllegalStateException("There is no OLE or OOXML object for saving.");

        embeddedObject.saveForUser(stream, mParent);
    }

    /// <summary>
    /// Saves the data of the embedded object into a file with the specified name.
    /// </summary>
    /// <exception cref="InvalidOperationException">Throws if you attempt to save a linked object.</exception>
    /// <param name="fileName">Name of the file to save the OLE object data.</param>
    public void save(String fileName) throws Exception
    {
        Stream stream = File.create(fileName);
        try /*JAVA: was using*/
    	{
            save(stream);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Gets OLE object data entry.
    /// </summary>
    /// <param name="oleEntryName">Case-sensitive name of the OLE data stream.</param>
    /// <returns>An OLE data <ms>stream</ms><java>byte array</java><cpp>stream</cpp> or <c>null</c>.</returns>
    /// <javaName>byte[] getOleEntry(java.lang.String oleEntryName)</javaName>
    //JAVA: method is internalized
	MemoryStream getOleEntryInternal(String oleEntryName)
    {
        if (getOleObject() != null)
            return getOleObject().getData().getStreamSafe(oleEntryName);
        else
            return null;
    }

	//JAVA-added public wrapper for internalized method
	public byte[] getOleEntry(String oleEntryName)
	{
		return getOleEntryInternal(oleEntryName).toArray();
	}

    /// <summary>
    /// Gets OLE object raw data.
    /// </summary>
    public byte[] getRawData() throws Exception
    {
        if (getOleObject() != null)
        {
            FileSystem fs = new FileSystem(getOleObject().getData());
            MemoryStream raw = new MemoryStream();
            fs.save(raw);

            return raw.toArray();
        }

        if (getOoxmlObject() != null)
        {
            MemoryStream raw = new MemoryStream((int)getOoxmlObject().getStream().getLength());
            StreamUtil.copyStream(getOoxmlObject().getStream(), raw);

            return raw.toArray();
        }

        return null;
    }


    /// <summary>
    /// Sets the draw aspect of the OLE object to a specified value.
    /// </summary>
    /// <remarks>
    /// This is made internal because I don't want it public, see comment in <see cref="OleIcon"/>.
    /// </remarks>
    void setOleIcon(boolean isIcon)
    {
        setAttrIfNotDefault(ShapeAttr.OLE_ICON, isIcon);
    }

    private void updateDrawAspectFromOleObject()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (getOleObject() != null)
        	{
            	ObjInfoStream objInfoStream = ObjInfoStream.read(getOleObject().getData());

            	if (objInfoStream != null)
                	setOleIcon(objInfoStream.isIcon());
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private Object fetchAttr(int key)
    {
        return mParent.fetchShapeAttr(key);
    }

    private void setAttrIfNotDefault(int key, Object value)
    {
        if (value != null && !value.equals(ShapePr.fetchDefaultAttr(key)))
            mParent.setShapeAttr(key, value);
        else
            mParent.removeShapeAttr(key);
    }

    private void setAttr(int key, Object value)
    {
        mParent.setShapeAttr(key, value);
    }

    /// <summary>
    /// Gets icon caption of OLE object.
    /// <para>In case if the OLE object does not have an icon or a caption cannot be retrieved, returns an empty
    /// string.</para>
    /// </summary>
    public String getIconCaption() throws Exception
    {
        // FOSS simplified.

        if (getOleIcon())
        {
            // AM. Have seen JPEG image inserted as OLE object so we can not parse icon label from it.
            // Try to get name from Object Packager data if it's possible.
            OleObject oleObject = ms.as(getEmbeddedObject(), OleObject.class);
            if ((oleObject != null) && oleObject.isOle1())
                return oleObject.getOriginalFileNameFromOle1();
        }

        return "";
    }

    /// <summary>
    /// Gets the file extension suggested for the current embedded object if you want to save it into a file.
    /// </summary>
    public String getSuggestedExtension() throws Exception
    {
        IEmbeddedObject embeddedObject = getEmbeddedObject();
        return (embeddedObject != null) ? embeddedObject.getExtensionForUser(getProgId()) : "";
    }

    /// <summary>
    /// Gets the file name suggested for the current embedded object if you want to save it into a file.
    /// </summary>
    public String getSuggestedFileName() throws Exception
    {
        IEmbeddedObject embeddedObject = getEmbeddedObject();
        return (embeddedObject != null) ? embeddedObject.getFileNameForUser() : "";
    }

    /// <summary>
    /// Gets or sets the ProgID of the OLE object.
    /// </summary>
    /// <remarks>
    /// <para>The ProgID property is not always present in Microsoft Word documents and cannot be relied upon.</para>
    /// <para>Cannot be <c>null</c>.</para>
    /// <p>The default value is an empty string.</p>
    /// </remarks>
    public String getProgId()
    {
        IEmbeddedObject embeddedObject = getEmbeddedObject();
        if ((embeddedObject != null) && embeddedObject.isForms2OleControlInternal())
            return Forms2OleControl.getProgId(embeddedObject.getClsidInternal());

        return (String)fetchAttr(ShapeAttr.OLE_PROG_ID);
    }
    public void setProgId(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(ShapeAttr.OLE_PROG_ID, value);
    }

    /// <summary>
    /// Returns <c>true</c> if the OLE object is linked (when <see cref="SourceFullName"/> is specified).
    /// </summary>
    public boolean isLink() throws Exception { return StringUtil.hasChars(getSourceFullName()); }

    /// <summary>
    /// Gets or sets the path and name of the source file for the linked OLE object.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    ///
    /// <p>If <see cref="SourceFullName"/> is not an empty string, the OLE object is linked.</p>
    /// </remarks>
    public String getSourceFullName() throws Exception
    {
        String sourceFullName = (String)fetchAttr(ShapeAttr.OLE_SOURCE_FULL_NAME);

        if (!StringUtil.hasChars(sourceFullName) && (getOlePackage() != null) && getOlePackage().isLink())
            sourceFullName = getOlePackage().getFileName();

        return sourceFullName;
    }
    public void setSourceFullName(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(ShapeAttr.OLE_SOURCE_FULL_NAME, value);
    }

    /// <summary>
    /// Gets or sets a string that is used to identify the portion of the source file that is being linked.
    /// </summary>
    /// <remarks>
    /// <p>The default value is an empty string.</p>
    ///
    /// <p>For example, if the source file is a Microsoft Excel workbook, the <see cref="SourceItem"/>
    /// property might return "Workbook1!R3C1:R4C2" if the OLE object contains only a few cells from
    /// the worksheet.</p>
    /// </remarks>
    public String getSourceItem() { return (String)fetchAttr(ShapeAttr.OLE_SOURCE_ITEM); }
    public void setSourceItem(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        setAttr(ShapeAttr.OLE_SOURCE_ITEM, value);
    }

    /// <summary>
    /// Specifies whether the link to the OLE object is automatically updated or not in Microsoft Word.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean getAutoUpdate() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.OLE_AUTO_UPDATE); }
    public void setAutoUpdate(boolean value) { setAttr(ShapeAttr.OLE_AUTO_UPDATE, value); }

    /// <summary>
    /// Gets the draw aspect of the OLE object. When <c>true</c>, the OLE object is displayed as an icon.
    /// When <c>false</c>, the OLE object is displayed as content.
    /// </summary>
    /// <remarks>
    /// <para>Aspose.Words does not allow to set this property to avoid confusion. If you were able to change
    /// the draw aspect in Aspose.Words, Microsoft Word would still display the OLE object in its original
    /// draw aspect until you edit or update the OLE object in Microsoft Word.</para>
    /// </remarks>
    public boolean getOleIcon() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.OLE_ICON); }

    /// <summary>
    /// Specifies whether the link to the OLE object is locked from updates.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <c>false</c>.</p>
    /// </remarks>
    public boolean isLocked() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.OLE_LOCKED); }
    public void isLocked(boolean value) { setAttr(ShapeAttr.OLE_LOCKED, value); }

    /// <summary>
    /// Gets the CLSID of the OLE object.
    /// </summary>
    //JAVA: property accessor is internalized
	Guid getClsidInternal()
    {
        IEmbeddedObject embeddedObject = getEmbeddedObject();
        return (embeddedObject != null) ? new Guid(embeddedObject.getClsidInternal()) : Guid.Empty;
    }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.UUID getClsid() { return Guid.toJava(getClsidInternal()); }

    /// <summary>
    /// Provide access to <see cref="Drawing.OlePackage" /> if OLE object is an OLE Package.
    /// Returns <c>null</c> otherwise.
    /// </summary>
    /// <remarks>
    /// OLE Package is a legacy technology that allows to wrap any file format not present in the OLE registry of
    /// a Windows system into a generic package allowing to embed almost anything into a document.
    /// See <see cref="Drawing.OlePackage"/> type for more info.
    /// </remarks>
    /// <dev>
    ///  Has meaning only if ProgId equal to "Package" otherwise is null.
    /// </dev>
    public OlePackage getOlePackage() throws Exception
    {
        if (mOlePackageCache == null)
        {
            // WORDSNET-15758 System.InvalidCastException is thrown while loading DOC file.
            // Here EmbeddedObject may be OoxmlObject if OLE object isn't structured.
            if ((msString.equals(getProgId(), OleRegistryInfo.getOlePackage().ProgId)) && (getEmbeddedObject() instanceof OleObject))
            {
                OleObject oleObject = (OleObject)getEmbeddedObject();
                mOlePackageCache = new OlePackage(oleObject);
                Ole10NativeStream ole10NativeStream = Ole10NativeStream.read(oleObject.getData());
                if (ole10NativeStream != null)
                {
                    Encoding encoding = Encoding.getEncoding(OlePackage.DEFAULT_CODE_PAGE);
                    MemoryStream memoryStream = new MemoryStream(ole10NativeStream.getNativeData());
                    try /*JAVA: was using*/
                	{
                    BinaryReader reader = new BinaryReader(memoryStream, encoding);
                    try /*JAVA: was using*/
                    {
                        mOlePackageCache.read(reader);
                    }
                    finally { if (reader != null) reader.close(); }
                	}
                    finally { if (memoryStream != null) memoryStream.close(); }
                }
            }
        }
        return mOlePackageCache;
    }

    /// <summary>
    /// Gets <see cref="OleControl"/> objects if this OLE object is an ActiveX control. Otherwise this property is null.
    /// </summary>
    /// <dev>
    /// If value is binary OleObject and it contains Forms2OleControl, then it means we have unparsed
    /// Forms2OleControl in ShapePr[ShapeAttr.OleObject]. In this case we should parse it and replace
    /// with new parsed OleControl created from this unparsed OleObject.
    /// </dev>
    public OleControl getOleControl() throws Exception
    {
        IEmbeddedObject embeddedObject = getEmbeddedObject();
        OleControl oleControl = ms.as(embeddedObject, OleControl.class);
        if (oleControl != null)
            return oleControl;

        // Ole control can be unparsed OleObject. Try to parse it here.
        OleObject oleObject = ms.as(embeddedObject, OleObject.class);
        if (oleObject != null)
        {
            oleControl = OleControl.create(oleObject.getData());

            // If OleObject was successfully parsed, then we need to update shape with the new parsed control.
            if (oleControl != null)
                setAttr(ShapeAttr.OLE_OBJECT, oleControl);

            return oleControl;
        }

        return null;
    }

    /// <summary>
    /// Provides access to the raw data of the embedded (and sometimes linked) OLE or OOXML object.
    ///
    /// Unfortunately, linked objects in DOC also have data, but linked objects in WML do not have it.
    /// So this property will not be set (return <c>null</c>) for linked objects loaded from WML, but not null for linked
    /// objects loaded from DOC. Same applies to RTF.
    /// </summary>
    IEmbeddedObject getEmbeddedObject() { return (IEmbeddedObject)fetchAttr(ShapeAttr.OLE_OBJECT); }
    void setEmbeddedObject(IEmbeddedObject value)
    {
        setAttrIfNotDefault(ShapeAttr.OLE_OBJECT, value);

        // WORDSNET-15616 Need to rebuild OlePackage after changing EmbeddedObject.
        mOlePackageCache = null;

        // We need to update the boolean OleIcon (DrawAspect) by getting data from the OLE object if possible
        // because it seems to be the most reliable source of this information.
        updateDrawAspectFromOleObject();
    }

    /// <summary>
    /// Safely gets the embedded object as an OLE object. If there is no embedded object or it is an OOXML object, returns <c>null</c>.
    /// </summary>
    OleObject getOleObject() { return ms.as(getEmbeddedObject(), OleObject.class); }

    /// <summary>
    /// Safely gets the embedded object as an OOXML package. If there is no embedded object or it is an OLE object, returns <c>null</c>.
    /// </summary>
    OoxmlObject getOoxmlObject() { return ms.as(getEmbeddedObject(), OoxmlObject.class); }

    /// <summary>
    /// It will be good to refactor this to remove at some time in the future.
    /// </summary>
    int getOleTxid() { return (/*int*/Integer)fetchAttr(ShapeAttr.OLE_TXID); }
    void setOleTxid(int value) { setAttr(ShapeAttr.OLE_TXID, value); }

    /// <summary>
    /// Gets or sets the type of the link for the OLE object.
    /// </summary>
    /*OleLinkType*/int getOleLinkType() { return (/*OleLinkType*/Integer)fetchAttr(ShapeAttr.OLE_LINK_TYPE); }
    void setOleLinkType(/*OleLinkType*/int value) { setAttr(ShapeAttr.OLE_LINK_TYPE, value); }

    /// <summary>
    /// Returns true if this OLE is linked and has no embedded data.
    /// </summary>
    boolean isLinkNoData() throws Exception { return ((isLink()) && (getEmbeddedObject() == null)); }

    /// <summary>
    /// Returns true if the OLE object is physically stored as an embedded document part.
    /// </summary>
    boolean getNeedEmbeddedPart() throws Exception { return !isLink() || (getOlePackage() != null); }

    /// <summary>
    /// This is a number used in the \f field switch.
    /// The following values are documented at this stage:
    /// 0 Maintain the formatting of the source file.
    /// 1 Not supported
    /// 2 Match the formatting of the destination document.
    /// 3 Not supported
    /// 4 Maintain the formatting of the source file, if the source file is an Excel workbook.
    /// 5 Match the formatting of the destination document if the source is an Excel workbook.
    ///
    /// Lets keep it integer for now. Don't see a need for enum yet.
    /// </summary>
    int getFormatUpdateType() { return (/*int*/Integer)fetchAttr(ShapeAttr.OLE_FORMAT_UPDATE_TYPE); }
    void setFormatUpdateType(int value) { setAttr(ShapeAttr.OLE_FORMAT_UPDATE_TYPE, value); }

    private /*final*/ IShapeAttrSource mParent;
    private OlePackage mOlePackageCache;
}


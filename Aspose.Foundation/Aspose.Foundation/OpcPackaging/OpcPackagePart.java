// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2007 by Vladimir Averkin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.DateTime;


public class OpcPackagePart implements Cloneable
{
    public OpcPackagePart(String partName, String contentType) throws Exception
    {
        ArgumentUtil.checkHasChars(partName, "partName");

        mName = partName;
        mContentType = contentType;
        mStream = new MemoryStream();

        mRels = new OpcRelationshipCollection(partName);
    }

    /// <summary>
    /// Retrieves the target for the specified relationship id.
    /// If the target is external, removes the file:/// prefix.
    /// If the target is internal, returns the absolute part name.
    /// </summary>
    public String getRelationshipTarget(String relId)
    {
        if (!StringUtil.hasChars(relId))
            return "";

        OpcRelationship rel = mRels.getById(relId);
        if (rel == null)
            return "";

        if (rel.isExternal())
        {
            String target = rel.getTarget();
            // RK File paths are stored with the protocol prefix in DOCX, but without it
            // in the model and other formats, therefore it is best to remove it.
            if (UriUtil.hasFileScheme(target))
            {
                target = UriUtil.removeFileSchemePrefix(target);
                target = UriUtil.unescapeHref(target);
            }
            return target;
        }
        else
        {
            // An internal relationship.
            if (UriUtil.isSubAddressOnly(rel.getTarget()))
            {
                // I think this is used when target is a bookmark. In this case just return the name as is.
                return rel.getTarget();
            }
            else
            {
                // The target is another part, return its absolute name.
                return getRelatedPartName(rel);
            }
        }
    }

    /// <summary>
    /// Gets the absolute name of the related (child) part from the specified relationship.
    /// The relationship must be from the relationship collection of this part.
    /// The relationship must be internal.
    /// </summary>
    public String getRelatedPartName(OpcRelationship rel)
    {
        if (rel.isExternal())
            throw new IllegalStateException("An internal target is expected here.");

        return OpcPackageBase.makeAbsolute(mName, rel.getTarget());
    }

    /// <summary>
    /// Creates a deep copy of the part and copies its content data stream.
    /// </summary>
    public OpcPackagePart deepClone()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	OpcPackagePart lhs = (OpcPackagePart)memberwiseClone();

        	lhs.mRels = mRels.deepClone();

        	// andrnosk: WORDSNET-9372 The same stream is used upon cloning in multiple threads,
        	// this leads incorrect Theme part writing. Using lock we avoid this.
        	synchronized (mLocker)
        	{
            	lhs.setStream(new MemoryStream());
            	getStream().setPosition(0);
            	StreamUtil.copyStream(getStream(), lhs.getStream());

            	lhs.mLocker = new Object();

            	return lhs;
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// The name of the part (with name path).
    ///
    /// This seems to corresponds to "part name" as per the OPC specification,
    /// that is "/segment1/segment2" (starts with "/").
    /// </summary>
    public String getName() { return mName; }

    /// <summary>
    /// The extension of the part name (without the dot).
    /// </summary>
    public String getExtension() throws Exception { return msString.trimStart(Path.getExtension(mName), '.'); }

    /// <summary>
    /// The MIME type of the content stream.
    /// </summary>
    public String getContentType() { return mContentType; }
    public void setContentType(String value) { mContentType = value; }

    /// <summary>
    /// The content data stream of the part.
    ///
    /// If the package is loaded into memory (normal method), then this is a MemoryStream.
    /// But if the package is loaded using the memory-optimized method, then this is a FileStream of a temporary file.
    /// </summary>
    public Stream getStream() { return mStream; }
    public void setStream(Stream value) { mStream = value; }

    /// <summary>
    /// This is part of the memory-optimized method. Allows to get the part as a memory stream.
    /// </summary>
    public MemoryStream getAsMemoryStream() throws Exception
    {
        mStream.setPosition(0);

        Stream stream = CompressedData.getStream(mStream);

        return StreamUtil.copyStreamIfNotMemory(stream);
    }

    /// <summary>
    /// Relationships for this package part.
    /// </summary>
    public OpcRelationshipCollection getRels() { return mRels; }

    /// <summary>
    /// LastModified property of the associated entry.
    /// </summary>
    DateTime getZipEntryLastModified() { return mZipEntryLastModified; }
    void setZipEntryLastModified(DateTime value) { mZipEntryLastModified = value; }

    private Object mLocker = new Object();
    private /*final*/ String mName;
    private String mContentType;
    private Stream mStream;
    private OpcRelationshipCollection mRels;
    private DateTime mZipEntryLastModified;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}



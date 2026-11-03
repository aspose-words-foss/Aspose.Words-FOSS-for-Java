// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2006 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Stream;
import com.aspose.StreamUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.FileSystem;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Contains data of an OLE object embedded in a document.
/// </summary>
class OleObject implements IEmbeddedObject
{
    /// <summary>
    /// Creates instance of <see cref="OleObject"/> object from a storage.
    /// </summary>
    OleObject(MemoryStorage data)
    {
    	this(UniqueIdManager.generateInteger(), data);
	
    }

    /// <summary>
    /// Creates instance of <see cref="OleObject"/> object from a storage.
    /// </summary>
    OleObject(int id, MemoryStorage data)
    {
        Debug.assertTrue(data != null);
        mData = data;

        mId = id;
    }

    /// <summary>
    /// Returns a file name from OLE1 package.
    /// </summary>
    String getOriginalFileNameFromOle1() throws Exception
    {
        if (!isOle1())
            return "";

        BinaryReader reader = createOle10NativeReader();
        return OleUtil.getOriginalFileNameFromOle1(reader);
    }

    /// <summary>
    /// Office documents can be attached in an old "97-2003" format with the progId of a greater Office version.
    /// As we save them as is it would be better to correct the extension.
    /// </summary>
    private String correctOfficeExtension(String fileExt)
    {
        if (isOfficeExt(fileExt) && getData() != null)
        {
            if (getData().containsKey("Workbook"))
                return ".xls";
            if (getData().containsKey("WordDocument"))
                return ".doc";
            if (getData().containsKey("PowerPoint Document"))
                return ".ppt";
            if (getData().containsKey("VisioDocument"))
                return ".vsd";
        }

        return fileExt;
    }

    /// <summary>
    /// Returns true, if a specified string is one of the Office extensions.
    /// </summary>
    private static boolean isOfficeExt(String fileExt)
    {
        if (".xlsx".equals(fileExt) || ".docx".equals(fileExt) || ".pptx".equals(fileExt) || ".vsdx".equals(fileExt))
            return true;

        return false;
    }

    /// <summary>
    /// Gets a file extension for Outlook by a specified progId.
    /// </summary>
    private String getExtensionForOutlookAttachment(String progId) throws Exception
    {
        if (msString.startsWith(progId, "MailMsgAtt", StringComparison.ORDINAL) ||
            msString.startsWith(progId, "Outlook.MsgAttach", StringComparison.ORDINAL))
            return ".msg"; // maybe

        if (msString.startsWith(progId, "Outlook.FileAttach", StringComparison.ORDINAL))
        {
            AttachDescStream attachDescStream = AttachDescStream.read(getData());
            if ((attachDescStream != null) && StringUtil.hasChars(attachDescStream.getExtension()))
                return attachDescStream.getExtension();
        }

        return "";
    }

    /// <summary>
    /// Returns a name of the embedded object.
    /// </summary>
    public String /*IEmbeddedObject.*/getName() throws Exception
    {
        Debug.assertTrue(mData != null);

        OcxNameStream ocxNameStream = OcxNameStream.read(mData);
        if (ocxNameStream == null)
            return null;

        return ocxNameStream.getValue();
    }

    /// <summary>
    /// Returns instance of <see cref="OleObject"/> object.
    /// </summary>
    public OleObject /*IEmbeddedObject.*/getOleObject()
    {
        return this;
    }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    public String /*IEmbeddedObject.*/getClsidInternal() { return mData.getClsid().toString(); }

    /// <summary>
    /// Gets or sets Id of the embedded object.
    /// </summary>
    public int /*IEmbeddedObject.*/getId() { return mId; }
    public void /*IEmbeddedObject.*/setId(int value) { mId = value; }

    public boolean /*IEmbeddedObject.*/isEmpty() { return false; }

    /// <summary>
    /// Returns the extension (with the leading dot).
    /// The extension must be for the inner (e.g. unwrapped) data that can be saved directly to a file.
    /// </summary>
    public String /*IEmbeddedObject.*/getExtensionForUser(String progId) throws Exception
    {
        if (!msString.isNullOrEmpty(progId))
        {
            // Try to look for ProgId in Registry database.
            OleRegistryInfo oleInfo = OleRegistryInfo.getByProgId(progId);
            String extension = correctOfficeExtension(oleInfo.FileExt);
            if (StringUtil.hasChars(extension))
                return extension;

            // Now try to get the extension of the Outlook attachment.
            extension = getExtensionForOutlookAttachment(progId);
            if (!msString.isNullOrEmpty(extension))
                return extension;
        }

        // Try to get the extension from OLE10 native data.
        String originalFileName = getOriginalFileNameFromOle1();
        if (!msString.isNullOrEmpty(originalFileName))
            return Path.getExtension(originalFileName).toLowerCase();

        // Try to get the extension by clsID.
        return OleRegistryInfo.getExtensionByClsId(getData().getClsid());
    }

    /// <summary>
    /// Returns the file name.
    /// </summary>
    public String /*IEmbeddedObject.*/getFileNameForUser() throws Exception
    {
        AttachDescStream attachDesc = AttachDescStream.read(mData);

        if (attachDesc != null)
            return attachDesc.getFileName();

        return Path.getFileNameWithoutExtension(getOriginalFileNameFromOle1());
    }


    /// <summary>
    /// Saves the embedded object data in a way that makes it a valid standalone file.
    /// </summary>
    public void /*IEmbeddedObject.*/saveForUser(Stream stream, IShapeAttrSource attrSource) throws Exception
    {
        String progId = (String)attrSource.fetchShapeAttr(ShapeAttr.OLE_PROG_ID);

        if (isOle1())
            saveOle1Unwrap(stream, progId);
        else
            saveOle2Unwrap(stream, progId);
    }

    /// <summary>
    /// Gets a boolean value, indicating either the embedded object is Forms2Ole control.
    /// </summary>
    public boolean /*IEmbeddedObject.*/isForms2OleControlInternal() { return OleControl.isForms2OleClsid(((IEmbeddedObject)this).getClsidInternal()); }

    /// <summary>
    /// Tries to extract the native data without OLE headers and saves it to the stream.
    /// </summary>
    private void saveOle1Unwrap(Stream dstStream, String progId) throws Exception
    {
        if (StringUtil.startsWithOrdinalIgnoreCase(progId, MOL_PROG_ID) &&
            unwrapMol(dstStream))
            return;

        BinaryReader reader = createOle10NativeReader();
        if (reader == null)
            return;

        Ole10Header header = new Ole10Header(reader);
        // WORDSNET-3576 “System.OutOfMemoryException” occurs during extracting OLE10 object from document.
        // RK This file has OLE10 data in some other format, probably corrupted as MS Word does not like
        // it much either. MS Word even crashes sometimes.
        if (header.Header1 == 0x0002)
        {
            switch (header.Header2)
            {
                case 0x0001:
                {
                    // WORDSNET-10087 Ole10NativeStream ends with filename2 if header2 is 0x0001, so there is no content.
                    break;
                }
                case 0x0003:
                {
                    // Read content.
                    byte[] content = new byte[header.ContentLength];
                    reader.read(content, 0, content.length);
                    dstStream.write(content, 0, content.length);
                    break;
                }
                default:
                {
                    // Unknown value, stop reading and write nothing into the output stream.
                    break;
                }
            }
        }
        else
        {
            // We don't know how to parse this OLE data, simply save it as is, but without the length header.
            getOle10NativeStream().setPosition(4);
            StreamUtil.copyStream(getOle10NativeStream(), dstStream);
        }
    }

    /// <summary>
    /// Returns OLE1 reader or null if there is nothing to read.
    /// </summary>
    private BinaryReader createOle10NativeReader() throws Exception
    {
        final int CODE_PAGE = 1251;

        Debug.assertTrue(isOle1());

        // WORDSNET-10249 The problem occurred because there are Ole10NativeStream with zero length in the document.
        // Write nothing into the output stream in such case.
        if (getOle10NativeStream().getLength() == 0)
            return null;

        // WORDSNET-10248 The problem occurred because in the document there are two OLE objects, which refer to the same Ole10NativeStream.
        // So to resolve the issue set stream position to 0 before reading it.
        getOle10NativeStream().setPosition(0);

        return new BinaryReader(getOle10NativeStream(), Encoding.getEncoding(CODE_PAGE));
    }

    /// <summary>
    /// Saves OLE2 embedded data. If progId is known to be one of the types we can unwrap,
    /// unwraps it to save only document data.
    /// </summary>
    /// <remarks>
    /// AM. I think we should refactor all this extraction using information from OleRegistryInfo class.
    /// Too busy now, postpone for a while.
    /// </remarks>
    private void saveOle2Unwrap(Stream stream, String progId) throws Exception
    {
        Debug.assertTrue(!isOle1());

        // If data doesn't exist, then there is nothing to save.
        if (mData == null)
            return;

        OleRegistryInfo oleInfo = OleRegistryInfo.getByProgId(progId);
        // ProgId data may be empty or invalid. In this case, we determine OleRegistryInfo by CLSID.
        if (StringUtil.equalsOrdinalIgnoreCase(oleInfo.ClsId, OleRegistryInfo.PACKAGE_CLS_ID))
            oleInfo = OleRegistryInfo.getByClsId(mData.getClsid());

        MemoryStream contentsStream = null;
        MemoryStorage contentsStorage = null;
        if (msString.startsWith(oleInfo.ProgId, "Excel.Sheet.8", StringComparison.ORDINAL))
        {
            Stream workbookStream = mData.getStreamSafe("Workbook");

            // WORDSNET-9995 The XLS file appears hidden in Excel after extracting. We have to unhide it before saving.
            OleUtil.unhideExcel97Workbook(workbookStream);
        }
        else if (isExcel12(oleInfo.ProgId))
        {
            // WORDSNET-8477 Perform these checks to keep contentsStream equal to null,
            // if mData do not have proper stream.
            if (mData.containsKey("Package"))
            {
                if ("Excel.SheetBinaryMacroEnabled.12".equals(oleInfo.ProgId))
                {
                    // AM. Do not "unhide" Excel Binary Workbook because output is corrupted.
                    // Will investigate it later.
                    contentsStream = mData.getStreamSafe("Package");
                }
                else
                {
                    MemoryStream _package = mData.getStreamSafe("Package");
                    _package.setPosition(0);

                    // WORDSNET-26316 Detect OLE object embedded in nested package.
                    if (FileSystem.isStructuredStorage(_package))
                        _package = new FileSystem(_package).getRoot().getStreamSafe("package");

                    contentsStream = new MemoryStream();

                    // andrnosk: WORDSNET-6032 The XLSX file appears hidden in Excel after extracting.
                    // We have to unhide it before saving.
                    OleUtil.unhideOoxmlExcelWorkbook(_package, contentsStream);
                }
            }
            else if (mData.containsKey("Workbook"))
            {
                // WORDSNET-8477 We should rely on mData content to be able to unwrap Excel97Workbook.
                OleUtil.unhideExcel97Workbook(mData.getStreamSafe("Workbook"));
            }
        }
        else if ((oleInfo.EmbeddingType == OleEmbeddingType.OLE_2_STREAM_PACKAGE))
        {
            // WORDSNET-12589 The XLSX file is wrapped into a structured storage. We need to extract it.
            contentsStream = mData.getStreamSafe("Package");
        }
        else if ((oleInfo.EmbeddingType == OleEmbeddingType.OLE_2_STREAM_EMBEDDED_ODF))
        {
            contentsStream = mData.getStreamSafe("EmbeddedOdf");
        }
        else if (msString.startsWith(progId, "Outlook.FileAttach", StringComparison.ORDINAL))
        {
            // WORDSNET-3983 The extracted embedded OLE object is corrupted.
            // RK The object is Outlook.FileAttach that contains the actual data (another OLE object)
            // inside it. The customers want to extract the inner object that is inside the attachment.
            // So I think it is fair to automatically unwrap Outlook attachment objects.
            contentsStream = mData.getStreamSafe("AttachContents");
        }
        else if ((msString.startsWith(oleInfo.ProgId, "AcroExch.Document", StringComparison.ORDINAL) ||
                  msString.startsWith(oleInfo.ProgId, "Acrobat.Document", StringComparison.ORDINAL)))
        {
            // WORDSNET-5238 Extracted PDF document can’t be opened by Adobe PDF Reader 7.
            contentsStream = mData.getStreamSafe("CONTENTS");
        }
        else if ((msString.startsWith(progId, "MailMsgAtt", StringComparison.ORDINAL) ||
            msString.startsWith(progId, "Outlook.MsgAttach", StringComparison.ORDINAL)))
        {
            // WORDSNET-10314 Unwrap MailMsgAtt OLE objects, so the extracted file can be opened by outlook.
            // WORDSNET-4542 The same problem with Outlook.MsgAttach OLE objects. Fixed by adding one more condition.
            contentsStorage = mData.getStorageSafe("MAPIMessage");
        }
        else if (msString.startsWith(progId, "ISISServer", StringComparison.ORDINAL))
        {
            // MOL - Chemical table file
            if (unwrapMol(stream))
                return;
        }
        else if ((oleInfo.EmbeddingType == OleEmbeddingType.OLE_1_PACKAGE))
        {
            // WORDSNET-27408 Extract OLE content from nested streams.
            if ("PowerPoint.Slide.12".equals(oleInfo.ProgId))
                contentsStream = mData.getStreamSafe("Package");
            else if("WordPad.Document.1".equals(oleInfo.ProgId))
                contentsStream = mData.getStreamSafe("Contents");
        }

        if (contentsStream != null)
        {
            // We have an "unwrapped contents" stream, write it out.
            contentsStream.setPosition(0);
            StreamUtil.copyStream(contentsStream, stream);
        }
        else
        {
            saveOle2NoUnwrap(stream, contentsStorage != null ? contentsStorage : mData);
        }
    }

    /// <summary>
    /// Extracts data of a Chemical table file (MOL) stored in this OLE object into the stream if possible.
    /// </summary>
    private boolean unwrapMol(Stream stream) throws Exception
    {
        MemoryStream contentsStream = mData.getStreamSafe("CONTENTS");
        if (contentsStream == null)
            return false;

        byte[] buffer = contentsStream.getBuffer();
        Encoding encoding = Encoding.getUTF8();

        // Skip the first 4 bytes of contents.
        final int OFFSET = 4;
        String contents = encoding.getString(buffer, OFFSET, (int)contentsStream.getLength() - OFFSET);

        Match match = G_MOL_END_REGEX.match(contents);
        if (!match.getSuccess())
            return false;

        int length = match.getIndex() + match.getLength();
        // Include \n
        if (length < contents.length())
            length++;
        if ((contents.charAt(length - 1) == '\r') && (length < contents.length()) && (contents.charAt(length) == '\n'))
            length++;

        String molContents = contents.substring((0), (0) + (length));

        byte[] data = encoding.getBytes(molContents);
        stream.write(data, 0, data.length);

        return true;
    }

    /// <summary>
    /// Returns true if progId is MS Excel OOXML document.
    /// </summary>
    private static boolean isExcel12(String progId)
    {
        return (msString.startsWith(progId, "Excel.Sheet.12", StringComparison.ORDINAL) ||
                msString.startsWith(progId, "Excel.SheetBinaryMacroEnabled.12", StringComparison.ORDINAL) ||
                msString.startsWith(progId, "Excel.SheetMacroEnabled.12", StringComparison.ORDINAL) ||
                msString.startsWith(progId, "Excel.Template", StringComparison.ORDINAL) ||
                msString.startsWith(progId, "Excel.Template.MacroEnabled", StringComparison.ORDINAL)

                );
    }

    /// <summary>
    /// Save the OLE2 object as is without unwrapping.
    /// </summary>
    private static void saveOle2NoUnwrap(Stream stream, MemoryStorage contentsStorage) throws Exception
    {
        FileSystem fs = new FileSystem(contentsStorage);
        fs.save(stream);
    }

    /// <summary>
    /// Gets the META stream that is often present in OLE10 objects.
    /// </summary>
    MemoryStream getMetaStream() { return mData.getStreamSafe(Ole2StreamBase.META_STREAM_NAME); }

    /// <summary>
    /// Gets the PIC stream that is often present in OLE10 objects.
    /// </summary>
    MemoryStream getPicStream() { return mData.getStreamSafe(Ole2StreamBase.PIC_STREAM_NAME); }

    /// <summary>
    /// The object can be either in the OLE10 or OLE20 format.
    /// Returns true if this is OLE10 object (contains Ole10Native stream).
    /// When false, this is an OLE20 object.
    /// </summary>
    boolean isOle1() { return getOle10NativeStream() != null; }

    /// <summary>
    /// Gets or sets the data of the OLE object in the Structured Storage format.
    /// </summary>
    MemoryStorage getData() { return mData; }
    void setData(MemoryStorage value)
    {
        Debug.assertTrue(value != null);
        mData = value;
    }

    /// <summary>
    /// The Id of the embedded object.
    /// </summary>
    /// <remarks>
    /// Each embedded object, when stored in a file, has a unique id.
    /// Looks like RTF does not use this.
    /// </remarks>
    private int mId;

    /// <summary>
    /// Gets the Ole10Native stream that is present in OLE10 objects.
    /// </summary>
    private MemoryStream getOle10NativeStream() { return mData.getStreamSafe(Ole2StreamBase.OLE_10_NATIVE_STREAM_NAME); }

    private MemoryStorage mData;

    private static /*final*/ Regex G_MOL_END_REGEX =
        new Regex("^M  END$", RegexOptions.COMPILED | RegexOptions.IGNORE_CASE | RegexOptions.MULTILINE);

    private static final String MOL_PROG_ID = "ISISServer";
}


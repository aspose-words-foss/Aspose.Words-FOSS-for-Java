// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/10/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.StringUtil;
import com.aspose.OpcPackage;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.StreamUtil;
import com.aspose.BitUtil;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.NullableBool;
import com.aspose.FileSystem;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Provides utility methods for manipulation with OLE objects.
/// </summary>
/*static*/ class OleUtil
{
	/* Simulation of static class by using private constructor */
	private OleUtil()
	{}

    /// <summary>
    /// Wraps application data as "Ole1Native" stream within structured storage.
    /// </summary>
    static MemoryStorage wrapOle10NativeData(String progId, byte[] nativeData) throws Exception
    {
        String clsId = OleRegistryInfo.getClsId(progId);

        MemoryStorage oleData = asOle1Native(new MemoryStream(nativeData), clsId);
        return oleData;
    }

    /// <summary>
    /// Wraps application data as "Package" stream within structured storage.
    /// </summary>
    static MemoryStorage wrapOoxmlPackage(String progId, OoxmlObject ooxml, boolean isIcon) throws Exception
    {
        return wrapPackage(progId, ooxml.getStream(), "Package", isIcon);
    }

    /// <summary>
    /// Wraps application data as "EmbeddedOdf" stream within structured storage.
    /// </summary>
    static MemoryStorage wrapOdtPackage(String progId, Stream stream, boolean isIcon) throws Exception
    {
        stream.setPosition(0);

        return wrapPackage(progId, stream, "EmbeddedOdf", isIcon);
    }

    /// <summary>
    /// Converts OOXML/ODT embedded object into OLE object which can be written to DOC/WML/RTF.
    /// </summary>
    /// <remarks>
    /// AM. Although this code looks like duplicate of CreateOleObject() but it has major difference in unknown progId processing.
    /// CreateOleObject() chooses Ole1Package method if progId is unknown while this method always uses Ole2NamedStream.
    /// </remarks>
    private static MemoryStorage wrapPackage(String progId, Stream stream, String streamName, boolean isIcon) throws Exception
    {
        String clsId = OleRegistryInfo.getClsId(progId);

        MemoryStorage oleData = asNamedStream(stream, streamName, clsId);
        addObjInfoStream(oleData, isIcon, false);
        addCompObjStream(oleData, clsId, progId, "");

        return oleData;
    }

    /// <summary>
    /// Reads [MS-OLEDS] 2.1.4 LengthPrefixedAnsiString.
    /// </summary>
    static String readLengthPrefixedAnsiString(BinaryReader reader) throws Exception
    {
        return readLengthPrefixedAnsiString(reader, reader.readInt32());
    }

    /// <summary>
    /// Reads [MS-OLEDS] 2.1.4 LengthPrefixedAnsiString with length already read.
    /// </summary>
    static String readLengthPrefixedAnsiString(BinaryReader reader, int length) throws Exception
    {
        String ret = "";

        if (length > 0)
        {
            byte[] bytes = reader.readBytes(length - 1);
            reader.readByte();
            ret = Encoding.getEncoding(1251).getString(bytes);
        }

        return ret;
    }

    /// <summary>
    /// Writes [MS-OLEDS] 2.1.4 LengthPrefixedAnsiString. Writes empty string as terminating zero byte.
    /// </summary>
    /// <remarks>
    /// AM. The reason that function exist that ClassName of Ole1Object can not be written as zero length string.
    /// Instead it should be written as 1-char string where char is terminating zero char.
    /// </remarks>
    static void writeLengthPrefixedAnsiStringCore(BinaryWriter writer, String text) throws Exception
    {
        text = (text == null) ? "" : text;

        writer.writeInt32(text.length() + 1);
        writer.writeBytes(Encoding.getEncoding(1251).getBytes(text));
        writer.writeByte((byte)0x00);
    }

    /// <summary>
    /// Writes [MS-OLEDS] 2.1.4 LengthPrefixedAnsiString. Writes empty string as zero length string.
    /// </summary>
    static void writeLengthPrefixedAnsiString(BinaryWriter writer, String text) throws Exception
    {
        if (StringUtil.hasChars(text))
        {
            writeLengthPrefixedAnsiStringCore(writer, text);
        }
        else
            writer.writeInt32(0x00);
    }

    /// <summary>
    /// When XLSX is embedded in a Word document, it has the Workbook Hidden flag set for some reason.
    /// When we are saving such XLSX to an external file we must unhide it, otherwise the users report it as invalid XLSX.
    /// </summary>
    static void unhideOoxmlExcelWorkbook(Stream stream, Stream dstStream) throws Exception
    {
        if (stream == null)
            return;

        stream.setPosition(0);
        OpcPackage _package = new OpcPackage(stream);

        // First try to get workbookPart by transitional DocxRelationshipType then by strict.
        OpcPackagePart workbookPart = _package.getPartByRelationshipType(null, DocxRelationshipTypes.getType(DocxRelationshipType.OFFICE_DOCUMENT, false));

        if (workbookPart == null)
            // Try to get workbookPart by strict DocxRelationshipType.
            workbookPart = _package.getPartByRelationshipType(null, DocxRelationshipTypes.getType(DocxRelationshipType.OFFICE_DOCUMENT, true));

        StreamReader reader = new StreamReader(workbookPart.getStream());
        String prevXml = reader.readToEnd();
        String xml = prevXml.replace("visibility=\"hidden\"", "");

        // WORDSNET-17333 Skip package recreating, when content was not changed.
        if (prevXml.length() > xml.length())
        {
            // It is better to create a new stream than dealing with truncating the old one.
            workbookPart.setStream(new MemoryStream());
            StreamWriter writer = new StreamWriter(workbookPart.getStream(), reader.getCurrentEncoding());
            writer.write(xml);
            writer.flush();

            _package.save(dstStream);
        }
        else
        {
            stream.setPosition(0);
            StreamUtil.copyStream(stream, dstStream);
        }
    }

    /// <summary>
    /// When XLS is embedded in a Word document, it has the Workbook Hidden flag set for some reason.
    /// When we are saving such XLS to an external file we must unhide it, otherwise the users report it as invalid XLS.
    /// </summary>
    static void unhideExcel97Workbook(Stream stream) throws Exception
    {
        if (stream == null)
            return;

        stream.setPosition(0);
        BinaryReader reader = new BinaryReader(stream);

        while (stream.getPosition() < stream.getLength())
        {
            int type = reader.readInt16();
            int size = reader.readInt16();
            if (type == 61) // Looking for Window1 record
            {
                reader.readInt16();     // xWn
                reader.readInt16();     // yWn
                reader.readInt16();     // dxWn
                reader.readInt16();     // dyWn
                int flags = (reader.readByte() & 0xFF);
                flags = BitUtil.setBit(flags, 0x0001, false);
                stream.setPosition(stream.getPosition() - 1);
                stream.writeByte((byte)flags);
                break;
            }
            else
            {
                stream.setPosition(stream.getPosition() + size);
            }
        }
    }

    /// <summary>
    /// Creates embedded OLE object from given stream.
    /// </summary>
    static OleObject createOleObject(Stream stream, String progId, boolean asIcon) throws Exception
    {
        return createEmbeddedOleObject(stream, progId, null, asIcon);
    }

    /// <summary>
    /// Creates either embedded or linked OLE object from given file.
    /// </summary>
    static OleObject createOleObject(String fileName, boolean isLinked, boolean asIcon, OleRegistryInfo oleInfo) throws Exception
    {
        return isLinked
            ? createLinkedOleObject(fileName, asIcon, oleInfo)
            : createEmbeddedOleObject(fileName, asIcon, oleInfo);
    }

    private static OleObject createLinkedOleObject(String fileName, boolean asIcon, OleRegistryInfo oleInfo) throws Exception
    {
        if (oleInfo == null)
        {
            String fileExt = Path.getExtension(fileName);
            oleInfo = OleRegistryInfo.getByFileExt(fileExt);
        }

        // WORDSNET-15431 If the OLE info is Package, embed a package that contains a link to the specified file.
        if (msString.equals(oleInfo.ClsId, OleRegistryInfo.PACKAGE_CLS_ID, StringComparison.ORDINAL_IGNORE_CASE))
        {
            MemoryStorage oleData = asOle1Package(null, fileName);
            addDefaultStreams(oleData, oleInfo.ClsId, oleInfo.ProgId, oleInfo.UserType, asIcon);
            return new OleObject(oleData);
        }
        else
        {
            return createAsLink(fileName, asIcon, oleInfo);
        }
    }

    /// <summary>
    /// Creates Ole object as link to specified filename.
    /// </summary>
    private static OleObject createAsLink(String fileName, boolean asIcon, OleRegistryInfo oleInfo) throws Exception
    {
         MemoryStorage oleData = new MemoryStorage(CLSID_STD_LINK);

        addObjInfoStream(oleData, asIcon, true);

        LinkInfoStream linkInfoStream = new LinkInfoStream("", fileName);
        linkInfoStream.write(oleData);

        OleStream oleStream = new OleStream(OleObjectType.LINKED);
        oleStream.setPath(fileName);
        oleStream.setClsId(new Guid(oleInfo.ClsId));

        if (gTestMode)
        {
            oleStream.setLocalCheckUpdateTime(G_TEST_DATE_TIME);
            oleStream.setLocalUpdateTime(G_TEST_DATE_TIME);
            oleStream.setRemoteUpdateTime(G_TEST_DATE_TIME);
        }
        else
        {
            oleStream.setLocalCheckUpdateTime(DateTime.getNow());
            oleStream.setLocalUpdateTime(DateTime.getNow());
            oleStream.setRemoteUpdateTime(DateTime.getNow());
        }

        oleStream.write(oleData);

        return new OleObject(oleData);
    }

    private static OleObject createEmbeddedOleObject(String fileName, boolean asIcon, OleRegistryInfo oleInfo) throws Exception
    {
        if (oleInfo == null)
        {
            String fileExt = Path.getExtension(fileName);
            oleInfo = OleRegistryInfo.getByFileExt(fileExt);
        }

        FileStream fileStream = File.open(fileName, FileMode.OPEN);
        try /*JAVA: was using*/
        {
            return createEmbeddedOleObject(fileStream, oleInfo.ProgId, fileName, asIcon);
        }
        finally { if (fileStream != null) fileStream.close(); }
    }

    private static OleObject createEmbeddedOleObject(Stream stream, String progId, String fileName, boolean asIcon) throws Exception
    {
        OleRegistryInfo oleInfo = OleRegistryInfo.getByProgId(progId);

        MemoryStorage oleData;
        switch (oleInfo.EmbeddingType)
        {
            case OleEmbeddingType.OLE_1_NATIVE_STREAM:
                oleData = asOle1Native(stream, oleInfo.ClsId);
                break;
            case OleEmbeddingType.OLE_2_STREAM_CONTENTS:
                oleData = asNamedStream(stream, "CONTENTS", oleInfo.ClsId);
                break;
            case OleEmbeddingType.OLE_2_STREAM_PACKAGE:
                oleData = asNamedStream(stream, "Package", oleInfo.ClsId);
                break;
            case OleEmbeddingType.OLE_2_STREAM_EMBEDDED_ODF:
                oleData = asNamedStream(stream, "EmbeddedOdf", oleInfo.ClsId);
                break;
            case OleEmbeddingType.OLE_2_STREAM_EQUATION_NATIVE:
                oleData = asNamedStream(stream, "Equation Native", oleInfo.ClsId);
                break;
            case OleEmbeddingType.OLE_2_STORAGE:
                oleData = asOle2Storage(stream);
                break;
            case OleEmbeddingType.OLE_1_PACKAGE:
                oleData = asOle1Package(stream, fileName);
                break;
            default:
                throw new IllegalStateException("Unexpected OleEmbeddingType.");
        }

        addDefaultStreams(oleData, oleInfo.ClsId, progId, oleInfo.UserType, asIcon);

        return new OleObject(oleData);
    }

    /// <summary>
    /// Adds default streams into the specified OLE data object.
    /// </summary>
    private static void addDefaultStreams(MemoryStorage oleData, String clsId, String progId,
        String userType, boolean asIcon) throws Exception
    {
        if (oleData.getClsid().equals(Guid.Empty))
            oleData.setClsid(new Guid(clsId));

        addObjInfoStream(oleData, asIcon, false);

        if (!oleData.containsKey(Ole2StreamBase.COMP_OBJ_STREAM_NAME))
            addCompObjStream(oleData, clsId, progId, userType);
    }

    static String getOriginalFileNameFromOle1(BinaryReader reader) throws Exception
    {
        if (reader != null)
        {
            OlePackage _package = new OlePackage();

            // Skip dataLength.
            reader.readInt32();
            _package.read(reader);

            return _package.getDisplayName();
        }
        return "";
    }

    /// <summary>
    /// Converts a string to a <see cref="NullableBool"/>.
    /// </summary>
    static /*NullableBool*/byte toNullableBool(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"0"*/0:
                return NullableBool.FALSE;
            case /*"1"*/1:
                return NullableBool.TRUE;
            default:
                return NullableBool.NOT_DEFINED;
        }
    }

    /// <summary>
    /// Converts a <see cref="NullableBool"/> to a string.
    /// </summary>
    static String toString(/*NullableBool*/byte value)
    {
        switch (value)
        {
            case NullableBool.FALSE:
                return "0";
            case NullableBool.TRUE:
                return "1";
            default:
                return "";
        }
    }

    /// <summary>
    /// Converts a type of the Forms 2.0 control to a string.
    /// </summary>
    static String toString(/*Forms2OleControlType*/int type)
    {
        switch (type)
        {
            case Forms2OleControlType.OPTION_BUTTON:
                return "OptionButton";

            case Forms2OleControlType.LABEL:
                return "Label";

            case Forms2OleControlType.TEXTBOX:
                return "TextBox";

            case Forms2OleControlType.CHECK_BOX:
                return "CheckBox";

            case Forms2OleControlType.TOGGLE_BUTTON:
                return "ToggleButton";

            case Forms2OleControlType.SPIN_BUTTON:
                return "SpinButton";

            case Forms2OleControlType.COMBO_BOX:
                return "ComboBox";

            case Forms2OleControlType.FRAME:
                return "Frame";

            case Forms2OleControlType.MULTI_PAGE:
                return "MultiPage";

            case Forms2OleControlType.COMMAND_BUTTON:
                return "CommandButton";

            case Forms2OleControlType.IMAGE:
                return "Image";

            case Forms2OleControlType.SCROLL_BAR:
                return "ScrollBar";

            case Forms2OleControlType.TAB_STRIP:
                return "TabStrip";

            case Forms2OleControlType.LIST_BOX:
                return "ListBox";

            case Forms2OleControlType.FORM:
                return "Form";

            default:
                throw new IllegalStateException(msString.format("Unexpected Forms2 OLE control type: {0}", type));
        }
    }

    /// <summary>
    /// Creates embedded OLE object with application data stored in Ole1Native stream.
    /// </summary>
    private static MemoryStorage asOle1Native(Stream stream, String clsId) throws Exception
    {
        MemoryStorage oleData = new MemoryStorage(new Guid(clsId));

        OleStream oleStream = new OleStream(OleObjectType.EMBEDDED);
        oleStream.write(oleData);

        Ole10NativeStream ole10NativeStream = new Ole10NativeStream();
        int length = (int) stream.getLength();
        byte[] nativeData = new byte[length];
        StreamUtil.read(stream, nativeData, 0, length);
        ole10NativeStream.setNativeData(nativeData);
        ole10NativeStream.write(oleData);

        return oleData;
    }

    /// <summary>
    /// Creates embedded OLE object with application data stored in stream with given name.
    /// </summary>
    /// <remarks>
    /// Position of the passed stream is set to 0 before copying to the memory storage.
    /// </remarks>
    private static MemoryStorage asNamedStream(Stream stream, String streamName, String clsId) throws Exception
    {
        MemoryStorage oleData = new MemoryStorage(new Guid(clsId));

        OleStream oleStream = new OleStream(OleObjectType.EMBEDDED);
        oleStream.write(oleData);

        MemoryStream dstStream = new MemoryStream();
        stream.setPosition(0);
        StreamUtil.copyStream(stream, dstStream);
        oleData.add(streamName, dstStream);

        return oleData;
    }

    /// <summary>
    /// Creates embedded OLE object with application data stored as structured storage.
    /// </summary>
    private static MemoryStorage asOle2Storage(Stream stream) throws Exception
    {
        FileSystem fs = new FileSystem(stream);
        MemoryStorage oleData = fs.getRoot();

        return oleData;
    }

    /// <summary>
    /// Creates embedded OLE object where application data stored within OLE Package object.
    /// </summary>
    private static MemoryStorage asOle1Package(Stream stream, String fileName) throws Exception
    {
        Guid clsId = new Guid(OleRegistryInfo.getOlePackage().ClsId);

        MemoryStorage oleData = new MemoryStorage(clsId);

        // We can't use empty name here, Word is failed to open such embedding.
        fileName = StringUtil.hasChars(fileName) ? fileName : "Unknown";

        OlePackage _package = new OlePackage();
        _package.setDisplayName(Path.getFileName(fileName));
        _package.setFileName(fileName);
        _package.TempFileName = fileName;
        if (stream != null)
            _package.Data = StreamUtil.copyStreamToByteArray(stream);

        // Make OLE package object.
        MemoryStream packagerStream = new MemoryStream();
        BinaryWriter packagerWriter = new BinaryWriter(packagerStream);
        _package.write(packagerWriter);

        // And write it as Ole10NativeStream.
        Ole10NativeStream ole10NativeStream = new Ole10NativeStream();
        ole10NativeStream.setNativeData(packagerStream.toArray());
        ole10NativeStream.write(oleData);

        return oleData;
    }

    /// <summary>
    /// Adds ObjInfo stream to OLE data storage.
    /// </summary>
    private static void addObjInfoStream(MemoryStorage oleData, boolean isIcon, boolean isLink) throws Exception
    {
        ObjInfoStream objInfoStream = ObjInfoStream.defaultObject(isIcon, isLink);
        objInfoStream.write(oleData);
    }

    /// <summary>
    /// Adds CompObj stream to OLE data storage.
    /// </summary>
    private static void addCompObjStream(MemoryStorage oleData, String clsId, String progId, String userType) throws Exception
    {
        CompObjStream compObjStream = new CompObjStream();
        compObjStream.setProgId(progId);
        compObjStream.setClsid(new Guid(clsId));
        compObjStream.setUserType(userType);
        compObjStream.write(oleData);
    }

    /// <summary>
    /// Predefined library of various images.
    /// </summary>
    static StringToObjDictionary<byte[]> getImageLibrary()
    {
        if (gImageLibraryCache == null)
        {
            synchronized (G_IMAGE_LIBRARY_SYNC_ROOT)
            {
                if (gImageLibraryCache == null)
                {
                    gImageLibraryCache = new StringToObjDictionary<byte[]>();

                    // Icons for HTML OLE radio button control.
                    addImage("RadioChecked", "Aspose.Words.Resources.OleIcons.RadioChecked.wmf");
                    addImage("RadioUnchecked", "Aspose.Words.Resources.OleIcons.RadioUnchecked.wmf");
                }
            }
        }

        return gImageLibraryCache;
    }

    /// <summary>
    /// Sets Test flag. This flag is used to make behavior of some methods deterministic for test purposes.
    /// Call this method before running any tests which can be connected to methods of this class.
    /// </summary>
    public static void setTestMode()
    {
        gTestMode = true;
    }

    private static void addImage(String name, String resource)
    {
        gImageLibraryCache.add(name, StreamUtil.copyStreamToByteArray(ResourceUtil.fetchResourceStream(resource)));
    }

    /// <summary>
    /// Gets the content type by progId valid for saving into an OOXML document.
    /// </summary>
    static String getContentType(String progId)
    {
        // RK Word 2007 seems to write specific content types for OLE objects created using Office applications.
        // Without writing a specific content type it will not be possible to edit the object in Word 2007.
        if (msString.startsWith(progId, "Excel.Sheet.8", StringComparison.ORDINAL))
            return DocxContentType.XLS;
        else if (msString.startsWith(progId, "Word.Document.8", StringComparison.ORDINAL))
            return DocxContentType.DOC;
        else if (msString.startsWith(progId, "PowerPoint.Show.8", StringComparison.ORDINAL))
            return DocxContentType.PPT;
        else if (msString.startsWith(progId, "Visio.Drawing.11", StringComparison.ORDINAL))
            return DocxContentType.VSD;
        else
            return DocxContentType.OLE_OBJECT;
    }

    /// <summary>
    /// Gets the embedded object id, formatted the "Microsoft Word way", that is "_xyz".
    /// </summary>
    /// <remarks>
    /// Note that in Word it could be with leading zeroes "_0000000xyz" sometimes.
    /// But we DO NOT prefix with zeroes, otherwise OLE10 objects will not work in MS Word!
    /// </remarks>
    static String getMsWordId(IEmbeddedObject embeddedObject)
    {
        return msString.format("_{0}", embeddedObject.getId());
    }

    /// <summary>
    /// This static member is used to use gTestDateTime instead of DateTime.Now in test mode.
    ///
    /// All tests which can be connected to methods of this class must call TestUtil.SetTestMode/>
    /// to set this flag to <c>true</c>.
    /// </summary>
    private static boolean gTestMode = false;

    /// <summary>
    /// Commonly used OLE1.0 OleVersion constant.
    /// </summary>
    /// AM. Although spec says that this value is arbitrary Word always writes this one.
    static final int OLE_VERSION = 0x0501;
    static final String PACKAGER = "packager";

    /// <summary>
    /// A Clsid of StdOleLink as specified in [MS-OLEDS], 2.3.3 OLEStream.
    /// </summary>
    static /*final*/ Guid CLSID_STD_LINK = new Guid("00000300-0000-0000-c000-000000000046");

    private static /*final*/ DateTime G_TEST_DATE_TIME = new DateTime(2013, 5, 3, 12, 23, 0);

    private static StringToObjDictionary<byte[]> gImageLibraryCache;
    private static /*final*/ Object G_IMAGE_LIBRARY_SYNC_ROOT = new Object();

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"0",
		"1"
	);

}


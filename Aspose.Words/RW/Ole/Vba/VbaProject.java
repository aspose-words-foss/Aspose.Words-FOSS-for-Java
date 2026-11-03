// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2019 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringSplitOptions;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.StringUtil;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Provides access to VBA project information.
/// A VBA project inside the document is defined as a collection of VBA modules.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-vba-macros/">Working with VBA Macros</a> documentation article.</para>
/// </summary>
public class VbaProject implements Cloneable
{
    /// <summary>
    /// Creates a blank <see cref="VbaProject"/>.
    /// </summary>
    public VbaProject()
    {
        mName = "Project";
        mSysKind = 1;
        mCodePage = 1251;
        mLcid = 0x00000409;
        mLcidInvoke = 0x00000409;
        mVersionMajor = 1;
        mVersionMinor = (short) 1;
        mDescription = "";
        mVbaStorage = null;

        VbaModule module = new VbaModule("ThisDocument");
        module.setType(VbaModuleType.DOCUMENT_MODULE);
        getModules().add(module);
    }

    /// <summary>
    /// Creates a <see cref="VbaProject"/> from the <paramref name="vbaStorage"/>.
    /// </summary>
    VbaProject(MemoryStorage vbaStorage) throws Exception
    {
        mVbaStorage = vbaStorage;

        try
        {
            MemoryStream compressedDir = vbaStorage.fetchStorage(VbaRecord.VBA_STORAGE_NAME).fetchStream(VbaRecord.DIR_STREAM_NAME);
            MemoryStream dir = new MemoryStream();

            RleCompressor.decompress(compressedDir, dir);
            dir.setPosition(0);

            mReader = new VbaRecordReader(new BinaryReader(dir));

            // PROJECTINFORMATION
            mSysKind = mReader.readInt32Record((VbaRecord.SYS_KIND & 0xFFFF));           // PROJECTSYSKIND
            mLcid = mReader.readInt32Record((VbaRecord.LCID & 0xFFFF));              // PROJECTLCID
            mLcidInvoke = mReader.readInt32Record((VbaRecord.LCID_INVOKE & 0xFFFF));        // PROJECTLCIDINVOKE
            mCodePage = mReader.readInt16Record((VbaRecord.CODE_PAGE & 0xFFFF));          // PROJECTCODEPAGE
            mReader.setEncoding(Encoding.getEncoding(mCodePage));

            mName = mReader.readStringRecord((VbaRecord.NAME & 0xFFFF));             // PROJECTNAME

            // PROJECTDOCSTRING
            mDescription = mReader.readStringRecords((VbaRecord.DESCRIPTION & 0xFFFF), (VbaRecord.DESCRIPTION_UNICODE & 0xFFFF));
            // <PROJECTHELPFILEPATH> must be defined in PROJECT Stream, if SizeOfHelpFile1 > 0.
            mHelpFilePath1 = mReader.readStringRecord((VbaRecord.HELP_FILE_PATH_1 & 0xFFFF));

            // [MS-OVBA] 2009 was using ID 0x49 (VbaRecord.HelpFilePath2Obsolete).
            // [MS-OVBA] 2019 is using ID 0x3d (VbaRecord.HelpFilePath2).
            // Let's make optional read here to be able to import old documents as well.
            mHelpFilePath2 = (mReader.peekId() == (VbaRecord.HELP_FILE_PATH_2_OBSOLETE & 0xFFFF)) ?
                mReader.readStringRecord((VbaRecord.HELP_FILE_PATH_2_OBSOLETE & 0xFFFF)) :
                mReader.readStringRecord((VbaRecord.HELP_FILE_PATH_2 & 0xFFFF));

            mHelpContext = mReader.readInt32Record((VbaRecord.PROJECT_HELP_CONTEXT & 0xFFFF));       // PROJECTHELPCONTEXT
            mLibFlags = mReader.readInt32Record((VbaRecord.LIB_FLAGS & 0xFFFF));          // PROJECTLIBFLAGS

            // PROJECTVERSION
            mReader.readVersionRecord((VbaRecord.PROJECT_VERSION & 0xFFFF));
            mVersionMajor = mReader.readUInt32();    // major
            mVersionMinor = mReader.readUInt16();    // minor

            // PROJECTCONSTANTS
            if (mReader.peekId() == (VbaRecord.CONSTANTS & 0xFFFF))
                mConstants = mReader.readStringRecords((VbaRecord.CONSTANTS & 0xFFFF), (VbaRecord.CONSTANTS_UNICODE & 0xFFFF));

            // PROJECTREFERENCES
            while (true)
            {
                if (mReader.peekId() == (VbaRecord.MODULES_COUNT & 0xFFFF))
                {
                    // Start of PROJECTMODULES
                    break;
                }

                getReferences().add(VbaReference.read(mReader));
            }

            int modulesCount = mReader.readInt16Record((VbaRecord.MODULES_COUNT & 0xFFFF));
            mReader.readBytes(8);           // PROJECTCOOKIE

            // Array of Module record.
            for (int i = 0; i < modulesCount; i++)
            {
                VbaModule module = VbaModule.read(mReader, vbaStorage);
                if (module != null)
                    getModules().addInternal(module);
            }

            parseProjectStream();
        }
        catch(Exception e)
        {
            Debug.assertTrue(false, "Unexpected VbaProject content");
        }
    }

    /// <summary>
    /// Gets or sets VBA project name.
    /// </summary>
    public String getName() { return mName; }
    public void setName(String value)
    {
        mName = value;
        markModified();
    }

    /// <summary>
    /// Returns collection of VBA project modules.
    /// </summary>
    public VbaModuleCollection getModules()
    {
        if (mModulesCollection == null)
            mModulesCollection = new VbaModuleCollection(this);
        return mModulesCollection;
    }

    /// <summary>
    /// Gets or sets the VBA project’s code page.
    /// </summary>
    /// <remarks>
    /// Please note that VBA is pre-Unicode feature and you have to explicitly set appropriate code page
    /// to preserve regional character sets.
    /// </remarks>
    public int getCodePage() { return mCodePage; }
    public void setCodePage(int value) { mCodePage = (short)value; }

    /// <summary>
    /// Performs a copy of the <see cref="VbaProject"/>.
    /// </summary>
    /// <returns>The cloned <see cref="VbaProject"/>.</returns>
    public VbaProject deepClone()
    {
        VbaProject lhs = (VbaProject)memberwiseClone();

        lhs.mModulesCollection = mModulesCollection.deepClone(lhs);
        lhs.mReferences = getReferences().deepClone();

        if (mMacroNames != null)
        {
            lhs.mMacroNames = new ArrayList<String>();
            for (String macroName : mMacroNames)
                lhs.addMacroName(macroName);
        }

        return lhs;
    }

    /// <summary>
    /// Shows whether the <see cref="VbaProject"/> is signed or not.
    /// </summary>
    public boolean isSigned() { return mSignature != null; }

    /// <summary>
    /// Shows whether the <see cref="VbaProject"/> is password protected.
    /// </summary>
    /// <dev>
    /// Actually we need to parse ProtectionState property but according to spec
    /// protected project ID MUST be {00000000-0000-0000-0000-000000000000}.
    /// Lets do this simple way first.
    /// </dev>
    public boolean isProtected() { return "{00000000-0000-0000-0000-000000000000}".equals(mId); }

    /// <summary>
    /// Gets a collection of VBA project references.
    /// </summary>
    public VbaReferenceCollection getReferences()
    {
        if (mReferences == null)
            mReferences = new VbaReferenceCollection(this);

        return mReferences;
    }

    /// <summary>
    /// Rebuilds macro names list.
    /// </summary>
    void rebuildMacroNames()
    {
        if(mMacroNames != null)
            mMacroNames.clear();

        for (VbaModule module : getModules())
        {
            if (!msString.isNullOrEmpty(module.getSourceCode()))
            {
                String[] lines =
                    msString.split(module.getSourceCode(), new String[] { "\r\n" }, StringSplitOptions.REMOVE_EMPTY_ENTRIES);

                // Do naive scan for subroutines names.
                for (String line : lines)
                {
                    Regex regex = new Regex("Sub\\s*([a-z, A-Z, 0-9]*)\\(\\)");
                    Match match = regex.match(line);

                    if (!msString.isNullOrEmpty(match.getValue()))
                        addMacroName(msString.format("{0}.{1}.{2}", getName(), module.getName(), match.getGroups().get(1).getValue()));
                }
            }
        }
    }

    /// <summary>
    /// Vba signature of the project.
    /// </summary>
    byte[] getSignature() { return mSignature; }
    void setSignature(byte[] value) { mSignature = value; }

    /// <summary>
    /// Contains strings that are macro names.
    /// Can be null.
    /// </summary>
    ArrayList<String> getMacroNames() { return mMacroNames; }
    void setMacroNames(ArrayList<String> value) { mMacroNames = value; }

    /// <summary>
    /// Adds a macro name into the <see cref="MacroNames"/> collection.
    /// </summary>
    void addMacroName(String macroName)
    {
        if (mMacroNames == null)
            mMacroNames = new ArrayList<String>();

        mMacroNames.add(macroName);
    }

    /// <summary>
    /// Specifies the platform for which the VBA project is created.
    /// </summary>
    int getSysKind() { return mSysKind; }

    /// <summary>
    /// Language code identifier
    /// </summary>
    int getLcid() { return mLcid; }

    /// <summary>
    /// Specifies an LCID value used for Invoke calls on an Automation server as specified in [MS-OAUT] section 3.1.4.4.
    /// </summary>
    int getLcidInvoke() { return mLcidInvoke; }

    /// <summary>
    /// Specifies the description for the VBA project.(2.3.4.2.1.6 PROJECTDOCSTRING Record)
    /// </summary>
    String getDescription() { return mDescription; }

    /// <summary>
    /// Specifies the path to the Help file for the VBA project.
    /// </summary>
    String getHelpFilePath1() { return mHelpFilePath1; }

    /// <summary>
    /// Specifies the path to the Help file for the VBA project.
    /// </summary>
    String getHelpFilePath2() { return mHelpFilePath2; }

    /// <summary>
    /// Specifies the Help topic identifier for the VBA project.
    /// </summary>
    int getHelpContext() { return mHelpContext; }

    /// <summary>
    /// Specifies the LIBFLAGS for the VBA project’s Automation type library as specified in [MS-OAUT] section 2.2.20.
    /// </summary>
    int getLibFlags() { return mLibFlags; }

    /// <summary>
    /// Specifies the major version of the VBA project.
    /// </summary>
    /*uint*/int getVersionMajor() { return mVersionMajor; }

    /// <summary>
    /// Specifies the minor version of the VBA project.
    /// </summary>
    /*ushort*/short getVersionMinor() { return mVersionMinor; }

    /// <summary>
    /// Specifies the compilation constants for the VBA project.
    /// </summary>
    String getConstants() { return mConstants; }

    void markModified()
    {
        mVbaStorage = null;
        mSignature = null;

        rebuildMacroNames();
    }

    /// <summary>
    /// Returns VBA storage for a project.
    /// </summary>
    MemoryStorage getStorage() throws Exception
    {
        // While not modified return original VBA storage.
        if (mVbaStorage != null)
            return mVbaStorage;

        mVbaStorage = new MemoryStorage();

        mVbaStorage.add(VbaRecord.VBA_STORAGE_NAME, new MemoryStorage());
        mVbaStorage.add(VbaRecord.PROJECT_STREAM_NAME, streamPROJECT());

        MemoryStorage vbaStorage = mVbaStorage.fetchStorage(VbaRecord.VBA_STORAGE_NAME);

        MemoryStream dir = new MemoryStream();
        writeDir(dir);
        dir.setPosition(0);
        vbaStorage.add(VbaRecord.DIR_STREAM_NAME, dir);

        vbaStorage.add(VbaRecord.CACHE_STREAM_NAME, stream_VBA_PROJECT());

        // Write module source code streams.
        for(VbaModule module : getModules())
        {
            // Add designer form data to VBA project.
            if(module.getFormMemoryStorage() != null)
                mVbaStorage.add(module.getName(), module.getFormMemoryStorage());

            MemoryStream newModuleStream = new MemoryStream();
            MemoryStream inputStream = StringUtil.hasChars(module.getSourceCode())?
                new MemoryStream(Encoding.getEncoding(mCodePage).getBytes(module.getSourceCode())):
                new MemoryStream();
            RleCompressor.compress(inputStream, newModuleStream);

            vbaStorage.add(module.getName(), newModuleStream);
        }

        return mVbaStorage;
    }

    /// <summary>
    /// Parses a PROJECT stream.
    /// </summary>
    private void parseProjectStream() throws Exception
    {
        MemoryStream stream = mVbaStorage.fetchStream(VbaRecord.PROJECT_STREAM_NAME);

        String stringProj = mReader.getEncoding().getString(stream.toArray());

        mId = getParamValueByName(stringProj, VbaRecord.ID);
        mProtectionState = getParamValueByName(stringProj, VbaRecord.PROTECTION_STATE);
        mPassword = getParamValueByName(stringProj, VbaRecord.PASSWORD);
        mVisibilityState = getParamValueByName(stringProj, VbaRecord.VISIBILITY_STATE);
    }

    /// <summary>
    /// Gets param value enclosed into quotes from a string.
    /// </summary>
    private static String getParamValueByName(String stringProj, String paramName)
    {
        // Concat '="' to avoid finding paramName in other places.
        String param = msString.concat(paramName, "=\"");

        int i = msString.indexOf(stringProj, param, StringComparison.ORDINAL);
        if (i == -1)
            return "";

        int first = stringProj.indexOf('"', i) + 1;

        int last = stringProj.indexOf('"', first);

        if ((first > 0) && (last > 0))
            return stringProj.substring((first), (first) + (last - first));

        return "";
    }

    /// <summary>
    /// Creates a PROJECT stream for a VbaMacros.
    /// </summary>
    private MemoryStream streamPROJECT() throws Exception
    {
        // This list of properties makes the output document readable for MSW.
        // This list is just a part of all properties, feel free to add properties.

        StringBuilder stringProj = new StringBuilder();
        writeParam(stringProj, VbaRecord.ID, mId, true);

        for (VbaModule module : getModules())
            writeParam(stringProj, VbaModule.moduleTypeToString(module.getType()), module.getName(), false);

        writeParam(stringProj, "Name", mName, true);
        writeParam(stringProj, "VersionCompatible32", "393222000", true);

        writeParam(stringProj, VbaRecord.PROTECTION_STATE, mProtectionState, true);
        writeParam(stringProj, VbaRecord.PASSWORD, mPassword, true);
        writeParam(stringProj, VbaRecord.VISIBILITY_STATE, mVisibilityState, true);

        byte[] bytes = Encoding.getEncoding(mCodePage).getBytes(stringProj.toString());
        return new MemoryStream(bytes);
    }

    /// <summary>
    /// Writes a parameter in a form paramName="paramValue".
    /// </summary>
    private static void writeParam(StringBuilder sb, String paramName, String paramValue, boolean useQuotes)
    {
        if (!StringUtil.hasChars(paramValue))
            return;

        msStringBuilder.append(sb, paramName);
        sb.append('=');
        if (useQuotes)
            sb.append('"');
        msStringBuilder.append(sb, paramValue);
        if (useQuotes)
            sb.append('"');
        msStringBuilder.append(sb, "\r\n");
    }

    /// <summary>
    /// Creates a cached stream without the performance cache.
    /// </summary>
    private static MemoryStream stream_VBA_PROJECT() throws Exception
    {
        MemoryStream newCacheStream = new MemoryStream();
        newCacheStream.writeByte((byte) 0xcc);
        newCacheStream.writeByte((byte) 0x61);
        newCacheStream.writeByte((byte) 0xff);
        newCacheStream.writeByte((byte) 0xff);
        newCacheStream.writeByte((byte) 0x00);
        newCacheStream.writeByte((byte) 0x00);
        newCacheStream.writeByte((byte) 0x00);
        return newCacheStream;
    }

    /// <summary>
    /// Writes "dir" stream.
    /// </summary>
    private void writeDir(MemoryStream compressedDir) throws Exception
    {
        MemoryStream dir = new MemoryStream();
        VbaRecordWriter writer = new VbaRecordWriter(new BinaryWriter(dir), Encoding.getEncoding(mCodePage));

        // PROJECTINFORMATION
        writer.writeInt32Record(VbaRecord.SYS_KIND, mSysKind);           // PROJECTSYSKIND
        writer.writeInt32Record(VbaRecord.LCID, mLcid);              // PROJECTLCID
        writer.writeInt32Record(VbaRecord.LCID_INVOKE, mLcidInvoke);              // PROJECTLCIDINVOKE
        writer.writeInt16Record(VbaRecord.CODE_PAGE, mCodePage);               // PROJECTCODEPAGE

        writer.writeStringRecord(VbaRecord.NAME, mName);

        // PROJECTDOCSTRING
        writer.writeStringRecord(VbaRecord.DESCRIPTION, mDescription);
        writer.writeStringRecord(VbaRecord.DESCRIPTION_UNICODE, Encoding.getUnicode(), mDescription);

        // <PROJECTHELPFILEPATH> must be defined in PROJECT Stream, if SizeOfHelpFile1 > 0.
        writer.writeStringRecord(VbaRecord.HELP_FILE_PATH_1, mHelpFilePath1);

        // According to specs: size of mHelpFilePath2 must be equal to size of SizeOfHelpFile1.
        // TODO check MSW behavior for the case, when sizes are not equal.
        writer.writeStringRecord(VbaRecord.HELP_FILE_PATH_2, mHelpFilePath2);

        writer.writeInt32Record(VbaRecord.PROJECT_HELP_CONTEXT, mHelpContext);
        writer.writeInt32Record(VbaRecord.LIB_FLAGS, 0);

        // PROJECTVERSION
        writer.writeUInt16(VbaRecord.PROJECT_VERSION);
        // Write size of int32.
        writer.writeInt32(4);
        writer.writeUInt32(mVersionMajor);
        writer.writeUInt16(mVersionMinor);

        // PROJECTCONSTANTS
        writer.writeStringRecord(VbaRecord.CONSTANTS, mConstants);
        writer.writeStringRecord(VbaRecord.CONSTANTS_UNICODE, Encoding.getUnicode(), mConstants);

        // PROJECTREFERENCES
        for (VbaReference reference : getReferences())
            reference.write(writer);

        writer.writeInt16Record(VbaRecord.MODULES_COUNT, (short)getModules().getCount());

        // PROJECTCOOKIE
        writer.writeUInt16(VbaRecord.PROJECT_COOKIE_RECORD_ID);
        // Write size of ushort.
        writer.writeInt32(2);
        writer.writeUInt16(VbaRecord.COOKIE_RECORD);

        // Array of Module record.
        for (VbaModule module : getModules())
            module.write(writer);

        // The end of dir stream
        writer.writeUInt16(VbaRecord.DIR_TERMINATOR);
        writer.writeInt32(0);

        dir.setPosition(0);
        RleCompressor.compress(dir, compressedDir);
    }

    private /*final*/ VbaRecordReader mReader;

    private short mCodePage;
    private String mName;
    private VbaModuleCollection mModulesCollection;
    private VbaReferenceCollection mReferences;

    private /*final*/ int mSysKind;
    private /*final*/ int mLcid;
    private /*final*/ int mLcidInvoke;

    private /*final*/ String mDescription;
    private /*final*/ String mHelpFilePath1;
    private /*final*/ String mHelpFilePath2;

    private /*final*/ int mHelpContext;
    private /*final*/ int mLibFlags;

    private /*final*/ /*uint*/int mVersionMajor;
    private /*final*/ /*ushort*/short mVersionMinor;

    private /*final*/ String mConstants;

    private MemoryStorage mVbaStorage;
    private byte[] mSignature;

    private ArrayList<String> mMacroNames;

    private String mProtectionState;
    private String mPassword;
    private String mVisibilityState;
    private String mId;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}


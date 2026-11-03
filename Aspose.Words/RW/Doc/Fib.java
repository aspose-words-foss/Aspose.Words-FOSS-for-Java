// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2003 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.Debug;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Responsible for reading and wring Word FIB structure.
/// The FIB starts at the beginning of the file.
/// </summary>
class Fib
{
    // ReSharper disable InconsistentNaming

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.2 FibBase
    //////////////////////////////////////////////////////////////////////////////////////

    /// <summary>
    /// Specifies that this is a Word Binary File. This value MUST be 0xA5EC.
    /// </summary>
    private /*ushort*/short wIdent;              // 0x00

    /// <summary>
    /// Specifies the version number of the file format used. Superseded by FibRgCswNew.nFibNew if it is present.
    /// </summary>
    /// <remarks>
    /// This value SHOULD be 0x00C1.
    ///
    /// Special empty document is installed with Word 97, Word 2000, Word 2002, and Office Word 2003 to allow "Create New Word Document"
    /// from the operating system. This document has an nFib of 0x00C0. In addition the BiDi build of Word 97 differentiates its documents
    /// by saving 0x00C2 as the nFib. In both cases treat them as if they were 0x00C1.
    /// </remarks>
    /*NFibValue*/int nFib;            // 0x02

    /// <summary>
    /// Product version.
    /// SPEC unused (2 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private /*ushort*/short nProduct;            // 0x04

    /// <summary>
    /// Specifies the install language of the application that is producing the document.
    /// </summary>
    /// <remarks>
    /// If nFib is 0x00D9 or greater, then any East Asian install lid or any install lid with a base language of Spanish,
    /// German or French MUST be recorded as lidAmerican. If the nFib is 0x0101 or greater,
    /// then any install lid with a base language of Vietnamese, Thai, or Hindi MUST be recorded as lidAmerican.
    /// </remarks>
    short lid;                  // 0x06

    /// <summary>
    /// Specifies the offset in the WordDocument stream of the FIB for the document which contains all the AutoText items.
    /// </summary>
    /// <remarks>
    /// If this value is 0, there are no AutoText items attached. Otherwise the FIB is found at file location pnNext×512.
    /// If fGlsy is 1 or fDot is 0, this value MUST be 0. If pnNext is not 0, each FIB MUST share the same values for FibRgFcLcb97.fcPlcBteChpx,
    /// FibRgFcLcb97.lcbPlcBteChpx, FibRgFcLcb97.fcPlcBtePapx, FibRgFcLcb97.lcbPlcBtePapx, and FibRgLw97.cbMac.
    /// </remarks>
    short pnNext;               // 0x08

    /// <summary>
    /// Specifies whether this is a document template.
    /// </summary>
    boolean fDot;                 // 0x0a.0001

    /// <summary>
    /// Specifies whether this is a document that contains only AutoText items.
    /// </summary>
    /// <remarks>
    /// See FibRgFcLcb97.fcSttbfGlsy, FibRgFcLcb97.fcPlcfGlsy and FibRgFcLcb97.fcSttbGlsyStyle
    /// </remarks>
    boolean fGlsy;                 // 0x0a.0002

    /// <summary>
    /// Specifies that file is in complex, fast-saved format.
    /// SPEC: Specifies that the last save operation that was performed on this document was an incremental save operation.
    /// </summary>
    boolean fComplex;             // 0x0a.0004

    /// <summary>
    /// If set file contains 1 or more pictures.
    /// </summary>
    boolean fHasPic;              // 0x0a.0008

    /// <summary>
    /// 4bit count of times file was quick saved.
    /// </summary>
    /// <remarks>
    /// If nFib is less than 0x00D9, then cQuickSaves specifies the number of consecutive times this document was incrementally saved.
    /// If nFib is 0x00D9 or greater, then cQuickSaves MUST be 0xF.
    /// </remarks>
    int cQuickSaves;           // 0x0a.00f0

    /// <summary>
    /// Specifies whether the document is encrypted or obfuscated.
    /// </summary>
    boolean fEncrypted;           // 0x0a.0100

    /// <summary>
    /// When 0, this fib refers to the table stream named "0Table", when 1, this fib refers to the table stream named "1Table".
    /// Normally, a file will have only one table stream, but under unusual circumstances a file may have table streams with both names.
    /// In that case, this flag must be used to decide which table stream is valid.
    /// </summary>
    boolean fWhichTblStm;         // 0x0a.0200

    /// <summary>
    /// Specifies whether the document author recommended that the document be opened in read-only mode.
    /// </summary>
    boolean fReadOnlyRecommended; // 0x0a.0400

    /// <summary>
    /// Specifies whether the document has a write-reservation password.
    /// </summary>
    boolean fWriteReservation;    // 0x0a.0800

    /// <summary>
    /// If true use extended character set in file. This value MUST be 1.
    /// </summary>
    private boolean fExtChar;              // 0x0a.1000

    /// <summary>
    /// Specifies whether to override the language information and font that are specified in the paragraph style at istd 0
    /// (the normal style) with the defaults that are appropriate for the installation language of the application.
    /// </summary>
    private boolean fLoadOverride;         // 0x0a.2000

    /// <summary>
    /// Specifies whether the installation language of the application that created the document was an East Asian language.
    /// </summary>
    private boolean fFarEast;              // 0x0a.4000

    /// <summary>
    /// If fEncrypted is 1, specifies whether the document is obfuscated using XOR obfuscation.
    /// otherwise this bit MUST be ignored.
    /// </summary>
    boolean fObfuscation;         // 0x0a.8000

    /// <summary>
    /// This value SHOULD be 0x00BF. This value MUST be 0x00BF or 0x00C1.
    /// </summary>
    /*ushort*/short nFibBack;           // 0x0c

    /// <summary>
    /// If fEncryption is 1 and fObfuscation is 1, specifies the XOR obfuscation password verifier.
    /// If fEncryption is 1 and fObfuscation is 0, specifies the size of the EncryptionHeader stored
    /// at the start of the Table stream as described in Encryption and Obfuscation.
    /// Otherwise, MUST be 0.
    /// </summary>
    int lKey;                // 0x0e

    /// <summary>
    /// Byte environment in which file was created: 0 created by Win Word / 1 created by Mac Word.
    /// SPEC: This value MUST be 0, and MUST be ignored.
    /// </summary>
    private byte envr;                  // 0x12

    /// <summary>
    /// If 1 file was last saved in the Mac environment.
    /// SPEC: This value MUST be 0, and MUST be ignored.
    /// </summary>
    private boolean fMac;                  // 0x13

    /// <summary>
    /// This value SHOULD be 0 and SHOULD be ignored.
    /// </summary>
    /// <remarks>
    /// Word 97, Word 2000, Word 2002, and Office Word 2003 install a minimal .doc file for use with the New-Microsoft Word Document of the shell.
    /// This minimal .doc file has fEmptySpecial set to 1.
    /// Word uses this flag to identify a document that was created by using the New – Microsoft Word Document of the operating system shell.
    /// </remarks>
    private boolean fEmptySpecial;

    /// <summary>
    /// Specifies whether to override the section properties for page size, orientation, and margins
    /// with the defaults that are appropriate for the installation language of the application.
    /// </summary>
    private boolean fLoadOverridePage;

    /// <summary>
    /// This value is undefined and MUST be ignored.
    /// </summary>
    private boolean fFutureSavedUndo;

    /// <summary>
    /// This value is undefined and MUST be ignored.
    /// </summary>
    private boolean fWord97Saved;

    /// <summary>
    /// Default extended character set id for text in document stream. (overridden by chp.chse). 0 = ANSI  / 256 Macintosh character set.
    /// SPEC: reserved3 (2 bytes): This value MUST be 0 and MUST be ignored.
    /// </summary>
    private /*ushort*/short chse;                // 0x14

    /// <summary>
    /// Default extended character set id for text: 0 = ANSI, 256 = Macintosh.
    /// SPEC: reserved4 (2 bytes): This value MUST be 0 and MUST be ignored.
    /// </summary>
    private /*ushort*/short chseTables;          // 0x16

    /// <summary>
    /// File offset of first character of text.
    /// SPEC: reserved5 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    int fcMin;               // 0x18

    /// <summary>
    /// File offset of last character of text + 1
    /// SPEC: reserved6 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    int fcMac;               // 0x1c

    /// <summary>
    /// Specifies the count of 16-bit values corresponding to fibRgW that follow. MUST be 0x000E.
    /// </summary>
    private /*ushort*/short csw;

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.3 FibRgW97.
    //////////////////////////////////////////////////////////////////////////////////////

    /// <summary>
    /// Unique number Identifying the File's creator. 0x6A62 is the creator ID for Word and is reserved.
    /// Other creators should choose a different value.
    /// </summary>
    private /*ushort*/short wMagicCreated;

    /// <summary>
    /// Identifies the File's last modifier.
    /// </summary>
    private /*ushort*/short wMagicRevised;

    /// <summary>
    /// 0x26. Private data.
    /// </summary>
    private /*ushort*/short wMagicCreatedPrivate;

    /// <summary>
    /// 0x28. Private data.
    /// </summary>
    private /*ushort*/short wMagicRevisedPrivate;

    /*
        INT16  pnFbpChpFirst_W6;// not used
        INT16  pnChpFirst_W6;   // not used
        INT16  cpnBteChp_W6;    // not used
        INT16  pnFbpPapFirst_W6;// not used
        INT16  pnPapFirst_W6;   // not used
        INT16  cpnBtePap_W6;    // not used
        INT16  pnFbpLvcFirst_W6;// not used
        INT16  pnLvcFirst_W6;   // not used
        INT16  cpnBteLvc_W6;    // not used
        */

    /// <summary>
    /// A LID whose meaning depends on the nFib value.
    /// </summary>
    /// <remarks>
    /// 0x00C1 - If FibBase.fFarEast is "true", this is the LID of the stored style names. Otherwise it MUST be ignored.
    /// 0x00D9, 0x0101, 0x010C, 0x0112 - The LID of the stored style names (STD.xstzName).
    /// </remarks>
    private short  lidFE;

    /// <summary>
    /// Specifies the count of 32-bit values corresponding to fibRgLw that follow. MUST be 0x0016.
    /// </summary>
    private /*ushort*/short cslw;            // 0x3e

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.4 FibRgLw97.
    //////////////////////////////////////////////////////////////////////////////////////

    int cbMac;           // 0x40 file offset of last byte written to file + 1.

    /// <summary>
    /// The build date of the creator. 10695 indicates the creator program was compiled on Jan 6, 1995
    /// SPEC: reserved1 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int lProductCreated;  // 0x44

    /// <summary>
    /// The build date of the File's last modifier.
    /// SPEC: reserved2 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int lProductRevised;  // 0x48

    /// <summary>
    /// Length of main document text stream.
    /// </summary>
    int ccpText;         // 0x4c

    /// <summary>
    /// Length of footnote subdocument text stream.
    /// </summary>
    int ccpFtn;          // 0x50

    /// <summary>
    /// length of header subdocument text stream.
    /// </summary>
    int ccpHdr;          // 0x54

    /// <summary>
    /// Length of macro subdocument text stream.
    /// SPEC: reserved3 (4 bytes): This value MUST be zero and MUST be ignored.
    /// </summary>
    private int ccpMcr;           // 0x58

    /// <summary>
    /// Length of annotation subdocument text stream.
    /// </summary>
    int ccpAtn;          // 0x5c

    /// <summary>
    /// Length of endnote subdocument text stream.
    /// </summary>
    int ccpEdn;          // 0x60

    /// <summary>
    /// Length of textbox subdocument text stream.
    /// </summary>
    int ccpTxbx;         // 0x64

    /// <summary>
    /// Length of header textbox subdocument text stream.
    /// </summary>
    int ccpHdrTxbx;      // 0x68

    /// <summary>
    /// When there was insufficient memory for Word to expand the PLCFbte at save time,
    /// the PLCFbte is written to the file in a linked list of 512-byte pieces starting with this pn.
    /// SPEC: reserved4 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int  pnFbpChpFirst;   // 0x6c

    /// <summary>
    /// The page number of the lowest numbered page in the 0x74 document that records CHPX FKP information.
    /// reserved5 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int  pnChpFirst;      // 0x70

    /// <summary>
    /// Count of CHPX FKPs recorded in file. In non-complex files if the number of entries in the PLCFbteChpx
    /// is less than this, the PLCFbteChpx is incomplete.
    /// </summary>
    /// <remarks>
    /// SPEC: This value MUST be equal or less than the number of data elements in PlcBteChpx, as specified
    /// by FibRgFcLcb97.fcPlcfBteChpx and FibRgFcLcb97.lcbPlcfBteChpx. This value MUST be ignored.
    /// </remarks>
    private int  cpnBteChp;       // 0x74

    /// <summary>
    /// When there was insufficient memory for Word to expand the PLCFbte at save time, the PLCFbte is written to
    /// the file in a linked list of 512-byte pieces starting with this pn.
    /// SPEC: reserved7 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int  pnFbpPapFirst;   // 0x78

    /// <summary>
    /// The page number of the lowest numbered page in the document that records PAPX FKP information.
    /// SPEC. reserved8 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int  pnPapFirst;      // 0x7c

    /// <summary>
    /// Count of PAPX FKPs recorded in file. In non-complex files if the number of entries in the PLCFbtePapx is
    /// less than this, the PLCFbtePapx is incomplete.
    /// </summary>
    /// <remarks>
    /// SPEC: reserved9 (4 bytes): This value MUST be less than or equal to the number of data elements
    /// in PlcBtePapx, as specified by FibRgFcLcb97.fcPlcfBtePapx and FibRgFcLcb97.lcbPlcfBtePapx. This value MUST be ignored.
    /// </remarks>
    private int  cpnBtePap;       // 0x80

    /// <summary>
    /// When there was insufficient memory for Word to expand the PLCFbte at save time, the PLCFbte is written to
    /// the file in a linked list of 512-byte pieces starting with this pn.
    /// SPEC: reserved10 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int  pnFbpLvcFirst;   // 0x84

    /// <summary>
    /// The page number of the lowest numbered page in the document that records LVC FKP information.
    /// SPEC: reserved11 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private int  pnLvcFirst;      // 0x88

    /// <summary>
    /// Count of LVC FKPs recorded in file. In non-complex files if the number of entries in the PLCFbtePapx is
    /// less than this, the PLCFbtePapx is incomplete.
    /// </summary>
    /// <remarks>
    /// SPEC: reserved12 (4 bytes): This value SHOULD be zero, and MUST be ignored. Word 97, Word 2000, Word 2002,
    /// and Office Word 2003 write a nonzero value here when saving a document template with changes
    /// that require the saving of an AutoText document.
    /// </remarks>
    private int cpnBteLvc;        // 0x8C

    /// <summary>
    /// SPEC: reserved13 (4 bytes): This value MUST be zero and MUST be ignored.
    /// </summary>
    private int fcIslandFirst;   // 0x90

    /// <summary>
    /// SPEC: reserved14 (4 bytes): This value MUST be zero and MUST be ignored.
    /// </summary>
    private int fcIslandLim;     // 0x94

    /// <summary>
    /// Specifies the count of 64-bit values corresponding to fibRgFcLcb that follow.
    /// </summary>
    /// <remarks>
    /// This MUST be one of the following values, depending on the value of nFib.
    /// 0x00C1 - 0x005D
    /// 0x00D9 - 0x006C
    /// 0x0101 - 0x0088
    /// 0x010C - 0x00A4
    /// 0x0112 - 0x00B7
    /// </remarks>
    private /*ushort*/short cbRgFcLcb;       // 0x98

    // JAVA: all former structs below are intitialized for java.

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.5 FibRgFcLcb
    //////////////////////////////////////////////////////////////////////////////////////
    RgFcLcb RgFcLcb = new RgFcLcb();           // 93 plcf pairs.

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.7 FibRgFcLcb2000
    //////////////////////////////////////////////////////////////////////////////////////
    RgFcLcb2000 RgFcLcb2000 = new RgFcLcb2000();   // 108 plcf pairs.

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.8 FibRgFcLcb2002
    //////////////////////////////////////////////////////////////////////////////////////
    RgFcLcb2002 RgFcLcb2002 = new RgFcLcb2002();   // 136 plcf pairs.

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.9 FibRgFcLcb2003
    //////////////////////////////////////////////////////////////////////////////////////
    RgFcLcb2003 RgFcLcb2003 = new RgFcLcb2003();   // 164 plcf pairs.

    //////////////////////////////////////////////////////////////////////////////////////
    // 2.5.10 FibRgFcLcb2007
    //////////////////////////////////////////////////////////////////////////////////////
    RgFcLcb2007 RgFcLcb2007 = new RgFcLcb2007();   // 183 plcf pairs.

    /// <summary>
    /// I found that in an encrypted document, some bytes at the start are not encrypted.
    /// </summary>
    static final int NOT_ENCRYPTED_HEADER_LENGTH = 68;

    /// <summary>
    /// Word stores various plcfs and tables with the stream named either "0Table" or "1Table". Ordinarily a file will contain
    /// only one table stream. However, in some unusual circumstances (e.g. crash during file save) a file might have two table
    /// streams. In that case the bit field fWhichTblStm in the FIB should be used to determine which table stream to read.
    /// If fWhichTblStm is 0, then the FIB refers to the stream named "0Table", and if fWhichTblStm is 1, then the FIB refers
    /// to the stream name "1Table".
    /// </summary>
    String getTableStreamName() { return (fWhichTblStm) ? "1Table" : "0Table"; }

    /// <summary>
    /// Initializes a FIB.
    /// </summary>
    Fib(/*NFibValue*/int targetNFib)
    {
        wIdent = (short) 0xa5ec;

        // RK If modifying this, check that TestDefect4237, TestTableNested and TestTableNested2000
        // still look correct in MS Word.
        if (targetNFib == NFibValue.WORD_2007)
        {
            nFib = NFibValue.WORD_2007;
            cbRgFcLcb = FC_LCB_COUNT_WORD_2007;
        }
        else
        {
            nFib = NFibValue.WORD_2003;
            cbRgFcLcb = FC_LCB_COUNT_WORD_2003;
        }

        // RK I'm not aware of this field having any effects.
        // 0x4035 is what Word 2002 writes.
        // 0x6021 is what Word 2003 writes.
        // 0x6071 is written by Word 2003 build 11.5604.5606
        nProduct = (short) 0x6021;
        lid = 1033;
        fComplex = true;
        fExtChar = true;
        nFibBack = (short) 191;
        fWord97Saved = true;
        csw = (short) 14;
        // 0x6A62 is what Word 2003 writes.
        // MS Recommends a unique value for the creator.
        // AW - means Aspose.Words
        wMagicCreated = (short) 0x5741;
        // N  - means .NET
        // For Java this should be 'J' - 0x4a
        // For C++ this should be 'C' - 0x43
        wMagicRevised = (short) 0x004e;

        // 0x32CF is written by Word 2002.
        // 0xAD5C is what Word 2003 writes.
        // 0x5071 is written by Word 2003 build 11.5604.5606
        // Seems can be any value. Lets write unique.
        // We write version here. It can contain 4 symbols minimum (e.g. "17.4" min and 17.12.9 max)
        wMagicCreatedPrivate = (/*ushort*/short)(AssemblyConstants.VERSION.charAt(0) | (AssemblyConstants.VERSION.charAt(1) << 8));
        wMagicRevisedPrivate = (/*ushort*/short)(AssemblyConstants.VERSION.charAt(2) | (AssemblyConstants.VERSION.charAt(3) << 8));

        lidFE = 1033;
        cslw = (short) 22;
        pnFbpChpFirst = pnFbpPapFirst = pnFbpLvcFirst = 0x000fffff;

        // 0x58AD is written by Word 2002.
        // 0xC73E is what Word 2003 writes.
        // 0x13A13 is written by Word 2003 build 11.5604.5606
        // Seems can be any value. Lets write unique.
        lProductCreated = 0xC73E;
        lProductRevised = 0xC73E;
    }

    /// <summary>
    /// NOTE this reads only the small part of the FIB that is guaranteed to be not encrypted.
    /// </summary>
    Fib(BinaryReader reader, IWarningCallback warningCallback, int offset) throws Exception
    {
        mWarningCallback = warningCallback;

        readPart1(reader, offset);
    }

    boolean isPreWord60() { return isPreWord60Value(nFib); }

    boolean isWord60() { return isWord60Value(nFib); }

    /// <summary>
    /// Reads the first 68 bytes of the FIB that are never encrypted even in an encrypted document.
    /// </summary>
    private void readPart1(BinaryReader reader, int offset) throws Exception
    {
        reader.getBaseStream().setPosition(offset);

        // 2.5.2 FibBase
        wIdent = reader.readUInt16();
        nFib = (/*NFibValue*/int)(reader.readUInt16() & 0xFFFF);

        nProduct = reader.readUInt16();
        lid = reader.readInt16();
        pnNext = reader.readInt16();

        int flags = (reader.readByte() & 0xFF);
        fDot = (flags & 0x01) != 0;
        fGlsy = (flags & 0x02) != 0;

        fComplex = (flags & 0x04) != 0;
        fHasPic = (flags & 0x08) != 0;
        cQuickSaves = (flags & 0xf0) >> 4;

        flags = (reader.readByte() & 0xFF);
        fEncrypted  = (flags & 0x01) != 0;
        fWhichTblStm= (flags & 0x02) != 0;
        fReadOnlyRecommended = (flags & 0x04) != 0;
        fWriteReservation = (flags & 0x08) != 0;
        fExtChar = (flags & 0x10) != 0;
        fLoadOverride = (flags & 0x20) != 0;
        fFarEast = (flags & 0x40) != 0;
        fObfuscation = (flags & 0x80) != 0;

        nFibBack = reader.readUInt16();
        lKey = reader.readInt32();
        envr = (byte)reader.readByte();//JAVA: casting needed since ReadByte() returns short in java.

        flags = (reader.readByte() & 0xFF);
        fMac = (flags & 0x01) != 0;
        fEmptySpecial = (flags & 0x02) != 0;
        fLoadOverridePage = (flags & 0x04) != 0;
        fFutureSavedUndo = (flags & 0x08) != 0;
        fWord97Saved = (flags & 0x10) != 0;

        chse = reader.readUInt16();
        chseTables = reader.readUInt16();
        fcMin = reader.readInt32();
        fcMac = reader.readInt32();

        if (isWord60())
        {
            cbMac = reader.readInt32();

            // Ignore spare0.
            reader.readInt32();
            // Ignore spare1.
            reader.readInt32();
            // Ignore spare2.
            reader.readInt32();
            // Ignore spare3.
            reader.readInt32();

            ccpText = reader.readInt32();
            ccpFtn = reader.readInt32();
            ccpHdr = reader.readInt32();
            ccpMcr = reader.readInt32();
            ccpAtn = reader.readInt32();
            ccpEdn = reader.readInt32();
            ccpTxbx = reader.readInt32();
            ccpHdrTxbx = reader.readInt32();

            reader.readInt32(); // ccpSpare2
        }
        else
        {
            // 2.5.3 FibRgW97
            csw = reader.readUInt16();

            wMagicCreated = reader.readUInt16();
            wMagicRevised = reader.readUInt16();
            wMagicCreatedPrivate = reader.readUInt16();
            wMagicRevisedPrivate = reader.readUInt16();
            //Skip 9 * Int16 not used data.
            reader.getBaseStream().seek(9*2, SeekOrigin.CURRENT);
            lidFE = reader.readInt16();

            // 2.5.4 FibRgLw97
            cslw = reader.readUInt16();

            cbMac = reader.readInt32();
        }
    }

    /// <summary>
    /// Reads the remaining data of the FIB (after the initial 68 bytes).
    /// To read successfully, the data must already be decrypted.
    /// </summary>
    void readPart2(BinaryReader reader, int offset) throws Exception
    {
        if (isWord60())
        {
            reader.getBaseStream().setPosition(offset + 88);

            RgFcLcb.readWord60Part1(reader);

            reader.readInt16(); // wSpare4Fib

            pnChpFirst = reader.readInt16();
            pnPapFirst = reader.readInt16();
            cpnBteChp = reader.readInt16();
            cpnBtePap = reader.readInt16();

            RgFcLcb.readWord60Part2(reader);

            // Warnings.
            warnings();
            return;
        }

        reader.getBaseStream().setPosition(offset + NOT_ENCRYPTED_HEADER_LENGTH);

        lProductCreated = reader.readInt32();
        lProductRevised = reader.readInt32();

        ccpText = reader.readInt32();
        ccpFtn = reader.readInt32();
        ccpHdr = reader.readInt32();
        ccpMcr = reader.readInt32();
        ccpAtn = reader.readInt32();
        ccpEdn = reader.readInt32();
        ccpTxbx = reader.readInt32();
        ccpHdrTxbx = reader.readInt32();

        pnFbpChpFirst = reader.readInt32();
        pnChpFirst = reader.readInt32();
        cpnBteChp = reader.readInt32();
        pnFbpPapFirst = reader.readInt32();
        pnPapFirst = reader.readInt32();
        cpnBtePap = reader.readInt32();
        pnFbpLvcFirst = reader.readInt32();
        pnLvcFirst = reader.readInt32();
        cpnBteLvc = reader.readInt32();
        fcIslandFirst = reader.readInt32();
        fcIslandLim = reader.readInt32();

        // This is the count of pairs of longs.
        // The offset at this point is 154 (0x9a).
        cbRgFcLcb = reader.readUInt16();
        int fcLcbPos = (int)reader.getBaseStream().getPosition();

        // Beginning of array of FC/LCB pairs.
        RgFcLcb.read(reader);

        // End of table as documented per Word 97 specification.
        // Offset here is 0x382, meaning only 93 Fc/Lcb pairs have been read so far.
        // All tables from here are for versions greater than Word 97.
        Debug.assertTrue(reader.getBaseStream().getPosition() == offset + FIB_LENGTH_WORD_97);

        // This block of data is for Word 2000 and later. 108 Fc/Lcb pairs.
        if ((cbRgFcLcb & 0xFFFF) >= FC_LCB_COUNT_WORD_2000)
            RgFcLcb2000.read(reader);

        // This block of data is for Word 2002 and later. 136 Fc/Lcb pairs.
        if ((cbRgFcLcb & 0xFFFF) >= FC_LCB_COUNT_WORD_2002)
            RgFcLcb2002.read(reader);

        // This block of data is for Word 2003 and later. 164 Fc/Lcb pairs.
        if ((cbRgFcLcb & 0xFFFF) >= FC_LCB_COUNT_WORD_2003)
            RgFcLcb2003.read(reader);

        // This block of data is for Word 2007 and later. 183 Fc/Lcb pairs.
        if ((cbRgFcLcb & 0xFFFF) >= FC_LCB_COUNT_WORD_2007)
            RgFcLcb2007.read(reader);

        // This is one more block of properties. I think it was added in Word 2000.
        // We detect it by looking at the number of cfclcb pairs.
        if ((cbRgFcLcb & 0xFFFF) >= FC_LCB_COUNT_WORD_2000)
        {
            reader.getBaseStream().setPosition(fcLcbPos + (cbRgFcLcb & 0xFFFF) * 8);

            int cswNew = (reader.readUInt16() & 0xFFFF);

            // These are entries I know so far.
            if (cswNew >= 2)
            {
                RgCswNew = new FcLcb((int)reader.getBaseStream().getPosition(), cswNew * 2);

                // New FIB value is stored here.
                nFib = (/*NFibValue*/int)(reader.readUInt16() & 0xFFFF);
                // Force NFib to be at least NFibValue.Word2000.
                if (nFib < NFibValue.WORD_2000)
                    nFib = NFibValue.WORD_2000;
                cQuickSaves = (reader.readUInt16() & 0xFFFF);
            }
        }

        // WORDSNET-11471 Ensure that ccpText is valid.
        int totalCcp = ccpText + ccpFtn + ccpHdr + ccpMcr + ccpAtn + ccpEdn + ccpTxbx + ccpHdrTxbx + 1;
        if (fExtChar)
            totalCcp *= 2;

        int minCcp = fcMac - 2048;
        if ((nFib == NFibValue.WORD_97) && (totalCcp < minCcp))
        {
            // Adjust main subdoc character count and zero all other subdocs.
            ccpText = minCcp;
            ccpFtn = 0;
            ccpHdr = 0;
            ccpMcr = 0;
            ccpAtn = 0;
            ccpEdn = 0;
            ccpTxbx = 0;
            ccpHdrTxbx = 0;
        }

        // Warnings.
        warnings();
    }

    /// <summary>
    /// Saves FIB in the Word 2003 format to the stream object.
    /// </summary>
    void write(Stream stream) throws Exception
    {
        BinaryWriter writer = new BinaryWriter(stream, Encoding.getUnicode());

        writer.writeUInt16(wIdent);
        // RK This is what Word 2003 writes. It is an "old" value superseded by nFib at the end of the FIB.
        writer.writeUInt16((/*ushort*/short)NFibValue.WORD_97);
        writer.writeUInt16(nProduct);
        writer.writeInt16(lid);
        writer.writeInt16(pnNext);

        /*ushort*/short nBits16 = (short) 0;
        if (fDot)
            nBits16 |= 0x0001;
        if (fGlsy)
            nBits16 |= 0x0002;
        if (fComplex)
            nBits16 |= 0x0004;
        if (fHasPic)
            nBits16 |= 0x0008;
        nBits16 |= (/*ushort*/short)(0xf0 & (cQuickSaves << 4));
        if (fEncrypted)
            nBits16 |= 0x0100;
        if (fWhichTblStm)
            nBits16 |= 0x0200;
        if (fReadOnlyRecommended)
            nBits16 |= 0x0400;
        if (fWriteReservation)
            nBits16 |= 0x0800;
        if (fExtChar)
            nBits16 |= 0x1000;
        writer.writeUInt16(nBits16);

        writer.writeUInt16(nFibBack);
        writer.writeInt32(lKey);
        writer.writeByte(envr);

        byte nBits8 = (byte) 0;
        if (fMac)
            nBits8 |= 0x0001;
        if (fEmptySpecial)
            nBits8 |= 0x0002;
        if (fLoadOverridePage)
            nBits8 |= 0x0004;
        if (fFutureSavedUndo)
            nBits8 |= 0x0008;
        if (fWord97Saved)
            nBits8 |= 0x0010;
        writer.writeByte(nBits8);

        writer.writeUInt16(chse);
        writer.writeUInt16(chseTables);
        writer.writeInt32(fcMin);
        writer.writeInt32(fcMac);

        //Beginning of the array of shorts "rgsw".
        writer.writeUInt16(csw);
        writer.writeUInt16(wMagicCreated);
        writer.writeUInt16(wMagicRevised);
        writer.writeUInt16(wMagicCreatedPrivate);
        writer.writeUInt16(wMagicRevisedPrivate);
        //Skip 9 * Int16 unused data.
        writer.seek(9 * 2, SeekOrigin.CURRENT);
        writer.writeInt16(lidFE);
        writer.writeUInt16(cslw);

        //Beginning of the array of longs "rclw".
        writer.writeInt32(cbMac);

        writer.writeInt32(lProductCreated);
        writer.writeInt32(lProductRevised);

        writer.writeInt32(ccpText);
        writer.writeInt32(ccpFtn);
        writer.writeInt32(ccpHdr);
        writer.writeInt32(ccpMcr);
        writer.writeInt32(ccpAtn);
        writer.writeInt32(ccpEdn);
        writer.writeInt32(ccpTxbx);
        writer.writeInt32(ccpHdrTxbx);

        writer.writeInt32(pnFbpChpFirst);
        writer.writeInt32(pnChpFirst);
        writer.writeInt32(cpnBteChp);
        writer.writeInt32(pnFbpPapFirst);
        writer.writeInt32(pnPapFirst);
        writer.writeInt32(cpnBtePap);
        writer.writeInt32(pnFbpLvcFirst);
        writer.writeInt32(pnLvcFirst);
        writer.writeInt32(cpnBteLvc);
        writer.writeInt32(fcIslandFirst);
        writer.writeInt32(fcIslandLim);
        writer.writeUInt16(cbRgFcLcb);

        // Beginning of array of FC/LCB pairs "rgfclcb".
        int fcLcbStartPos = (int)writer.getBaseStream().getPosition();
        // 0
        RgFcLcb.write(writer);
        // *** End of Word 97 PLCF pairs.

        RgFcLcb2000.write(writer);
        // 0x3fa. End of Word 2000 data. 108 Fc/Lcb pairs.
        Debug.assertTrue(writer.getBaseStream().getPosition() == 0x3fa);

        // Word 2002 PLCF pairs start here.
        RgFcLcb2002.write(writer);
        // 0x4da. End of Word 2002 data. 136 plcf pairs.
        Debug.assertTrue(writer.getBaseStream().getPosition() == 0x4da);

        // Some of the Word 2003 PLCF pairs here.
        RgFcLcb2003.write(writer, RgFcLcb.Clx.Fc);

        // 0x5ba. End of Word 2003 data here. 164 plcf pairs.
        Debug.assertTrue(writer.getBaseStream().getPosition() == 0x5ba);

        if (nFib == NFibValue.WORD_2007)
            RgFcLcb2007.write(writer);

        // Verify that all FcLcb pairs are written.
        int fcLcbPairsWritten = ((int)writer.getBaseStream().getPosition() - fcLcbStartPos) / 8; // 8 bytes each pair.
        int dummyPlcfPairsToWrite = (cbRgFcLcb & 0xFFFF) - fcLcbPairsWritten;
        Debug.assertTrue(dummyPlcfPairsToWrite == 0);

        // New array of shorts since Word 2000.
        // Number of the following entries.
        writer.writeUInt16((/*ushort*/short)2);
        // The actual FIB version.
        writer.writeUInt16((/*ushort*/short)nFib);
        // New quick saves value.
        writer.writeUInt16((/*ushort*/short)0);
    }

    // public for java.
    public /*new*/ String toString()
    {
        return StringUtil.objectToLogString(this, "FIB");
    }

    /// <summary>
    /// Converts document CP into CP relative to the subdocument where this CP belongs.
    /// Copied from Werner's code. It's a but rough, I'd prefer something more elegant.
    /// </summary>
    /// <param name="globalCP"></param>
    /// <returns></returns>
    int toLocalCP(int globalCP)
    {
        int localCP = globalCP;

        if (localCP < ccpText)
            return localCP;
        localCP -= ccpText;

        if (localCP < ccpFtn)
            return localCP;
        localCP -= ccpFtn;

        if (localCP < ccpHdr)
            return localCP;
        localCP -= ccpHdr;

        if (localCP < ccpMcr)
            return localCP;
        localCP -= ccpMcr;

        if (localCP < ccpAtn)
            return localCP;
        localCP -= ccpAtn;

        if (localCP < ccpEdn)
            return localCP;
        localCP -= ccpEdn;

        if (localCP < ccpTxbx)
            return localCP;
        localCP -= ccpTxbx;

        if (localCP < ccpHdrTxbx)
            return localCP;

        throw new IllegalStateException(msString.format(
            "Attempted to aim {0} characters past the end of the text. ",
            localCP));
    }

    /// <summary>
    /// Converts a local CP of the specified subdocument into a global CP.
    /// </summary>
    int toGlobalCP(/*SubDocType*/int subDocType, int localCP)
    {
        int globalCP = localCP;

        if (subDocType == SubDocType.MAIN)
            return globalCP;
        globalCP += ccpText;

        if (subDocType == SubDocType.FOOTNOTE)
            return globalCP;
        globalCP += ccpFtn;

        if (subDocType == SubDocType.HEADER)
            return globalCP;
        globalCP += ccpHdr;

        globalCP += ccpMcr;

        if (subDocType == SubDocType.COMMENT)
            return globalCP;
        globalCP += ccpAtn;

        if (subDocType == SubDocType.ENDNOTE)
            return globalCP;
        globalCP += ccpEdn;

        if (subDocType == SubDocType.TEXTBOX)
            return globalCP;
        globalCP += ccpTxbx;

        if (subDocType == SubDocType.HEADER_TEXT_BOX)
            return globalCP;

        throw new IllegalStateException("Unknown subdocument type specified.");
    }

    /// <summary>
    /// Indicates that document uses extended Word60 character encoding.
    /// </summary>
    boolean isWord60ExtChar()
    {
        return isWord60() && fExtChar;
    }

    static boolean isWord60Value(/*NFibValue*/int nFib)
    {
        return (NFibValue.WORD_60_FOR_WIN <= nFib) && (nFib < NFibValue.VALUE_106);
    }

    /// <summary>
    /// Indicates that Fib is valid.
    /// </summary>
    /// <remarks>
    /// Used to detect document embedded into WordDocument stream, see WORDSNET-26415.
    /// </remarks>
    boolean isValid()
    {
        if (cbMac < 0)
            return false;

        switch (nFib)
        {
            case NFibValue.WORD_60_FOR_WIN:
            case NFibValue.WORD_60_FOR_MAC:
            case NFibValue.VALUE_106:
            case NFibValue.VALUE_191:
            case NFibValue.WORD_95:
            case NFibValue.WORD_97:
            case NFibValue.WORD_97_EMPTY:
            case NFibValue.WORD_97_BI_DI:
            case NFibValue.WORD_2000:
            case NFibValue.WORD_2002:
            case NFibValue.WORD_2003:
            case NFibValue.WORD_2007:
                return true;

            default:
                return false;
        }
    }

    private static boolean isPreWord60Value(/*NFibValue*/int nFib)
    {
        return nFib < NFibValue.WORD_60_FOR_WIN;
    }

    FcLcb RgCswNew = new FcLcb();

    /// <summary>
    /// This is the number of FC/LCB pairs written by Word 97.
    /// </summary>
    static final int FC_LCB_COUNT_WORD_97 = 93;

    /// <summary>
    /// RK 108 is my number of FC/LCB pairs written by Word 2000.
    /// But according to the spec, this is 110.
    /// </summary>
    static final int FC_LCB_COUNT_WORD_2000 = 108;

    /// <summary>
    /// This is the number of FC/LCB pairs written by Word 2002.
    /// </summary>
    static final int FC_LCB_COUNT_WORD_2002 = 136;

    /// <summary>
    /// This is the number of FC/LCB pairs written by Word 2003.
    /// </summary>
    static final int FC_LCB_COUNT_WORD_2003 = 164;

    /// <summary>
    /// This is the number of FC/LCB pairs written by Word 2007.
    /// </summary>
    static final int FC_LCB_COUNT_WORD_2007 = 183;

    /// <summary>
    /// The length of the Fib in Word97.
    /// </summary>
    private static final int FIB_LENGTH_WORD_97 = 0x382;

    private /*final*/ IWarningCallback mWarningCallback;

    static boolean logSwitch = false;

    // ReSharper restore InconsistentNaming

    /// <summary>
    /// Issues warnings based on FIB information.
    /// </summary>
    private void warnings()
    {
        // General Fib specific warnings.
        warnIf(fLoadOverride, WarningStrings.LOAD_OVERRIDE);
        warnIf(fFarEast, WarningStrings.FAR_EAST_INSTALLATION_LID);
        warnIf(isWord60(), WarningStrings.WORD_60_DOCUMENT);

        // RgFcLcb specific warnings.
        warnIf(RgFcLcb.hasPrinterDriverData(), WarningStrings.PRINTER_DRIVER_DATA);
        warnIf(RgFcLcb.hasLastSelectionData(), WarningStrings.LAST_SELECTION_DATA);
        warnIf((fDot && RgFcLcb.hasAutoCaptions()), WarningStrings.AUTO_CAPTIONS);
        warnIf(RgFcLcb.hasSpellCheckerData(), WarningStrings.SPELL_CHECKER);
        warnIf(RgFcLcb.hasGrammarCheckerData(), WarningStrings.GRAMMAR_CHECKER);
        warnIf(RgFcLcb.hasRouteSlip(), WarningStrings.ROUTE_SLIP);
        warnIf(RgFcLcb.hasSaveHistory(), WarningStrings.SAVE_HISTORY);
        warnIf(RgFcLcb.hasVersioning(), WarningStrings.DOCUMENT_VERSIONING);
        warnIf(RgFcLcb.hasDeprecatedOcxData(), WarningStrings.DEPRECATED_OCX_DATA);
        warnIf(RgFcLcb.hasAutoSummaryPriorities(), WarningStrings.AUTO_SUMMARY_PRIORITIES);

        // RgLcb2000 specific warnings.
        warnIf(RgFcLcb2000.hasThreadingData(), WarningStrings.THREADING_DATA);
        warnIf(RgFcLcb2000.hasHybridListFormats(), WarningStrings.HYBRID_LISTS_FORMATS);
        warnIf(RgFcLcb2000.hasEnvelopeData(), WarningStrings.MSO_ENVELOPE);
        warnIf(RgFcLcb2000.hasLanguageAutoDetectData(), WarningStrings.LANGUAGE_AUTO_DETECT_DATA);
        warnIf(RgFcLcb2000.hasGrammarCheckerOptions(), WarningStrings.GRAMMAR_CHECKER_OPTIONS);

        // RgLcb2002 specific warnings.
        warnIf(RgFcLcb2002.hasGrammarCheckerCookies(), WarningStrings.GRAMMAR_CHECKER_COOKIES);
        warnIf(RgFcLcb2002.hasTextFrameworkData(), WarningStrings.TEXT_FRAMEWORK_DATA);
        warnIf(RgFcLcb2002.hasSmartTagRecognizerData(), WarningStrings.SMART_TAG_RECOGNIZER_DATA);
        warnIf(RgFcLcb2002.hasRepairBookmarks(), WarningStrings.REPAIR_BOOKMARKS);
        warnIf(RgFcLcb2002.hasConsistencyCheckerBookmarks(), WarningStrings.CONSISTENCY_CHECKER_BOOKMARKS);

        // RgLcb2003 specific warnings.
        warnIf(RgFcLcb2003.hasRevisionsAuthorSelection(), WarningStrings.REVISIONS_AUTHOR_SELECTION);
    }

    /// <summary>
    /// Conditionally logs a warning to the user-provided warning callback.
    /// </summary>
    private void warnIf(boolean condition, String description)
    {
        if (condition)
            WarningUtil.warnUnexpected(mWarningCallback, WarningSource.DOC, description);
    }
}


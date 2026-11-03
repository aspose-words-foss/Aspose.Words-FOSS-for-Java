// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements [MS-DOC] 2.5.6 FibRgFcLcb97 structure.
/// </summary>
class RgFcLcb
{
    // ReSharper disable InconsistentNaming

    /// <summary>
    /// Fc/Lcb of STSH.
    /// </summary>
    /// <remarks>
    /// During fast save Word will attempt to reuse this allocation if STSH is small enough to fit.
    /// This value is undefined and MUST be ignored.
    /// </remarks>
    FcLcb StshfOrig = new FcLcb();     // 0x9a

    /// <summary>
    /// Fc/Lcb of STSH.
    /// </summary>
    FcLcb Stshf = new FcLcb();         // 0xa2

    /// <summary>
    /// Fc/Lcb of footnote reference PLCF.
    /// </summary>
    FcLcb PlcffndRef = new FcLcb();    // 0xaa

    /// <summary>
    /// Fc/Lcb of footnote text PLCF.
    /// </summary>
    FcLcb PlcffndTxt = new FcLcb();    // 0xb2

    /// <summary>
    /// Fc/Lcb of annotation reference PLCF.
    /// </summary>
    FcLcb PlcfAndRef = new FcLcb();    // 0xba

    /// <summary>
    /// Fc/Lcb of annotation text PLCF.
    /// </summary>
    FcLcb PlcfAndTxt = new FcLcb();    // 0xc2

    /// <summary>
    /// Fc/Lcb of PlcfSed in the Table Stream which specifies the locations of property lists for each section in the Main Document.
    /// </summary>
    FcLcb PlcfSed = new FcLcb();         // 0xca

    /// <summary>
    /// Fc/Lcb of paragraph descriptor PLCF.
    /// SPEC: This value is undefined and MUST be ignored.
    /// </summary>
    FcLcb PlcPad = new FcLcb();         // 0xd2

    /// <summary>
    /// Fc/Lcb of PLCF which specifies version-specific information about paragraph height.
    /// SPEC: This Plc SHOULD NOT be emitted and SHOULD be ignored.
    /// </summary>
    /// <remarks>
    /// Word 97, Word 2000, and Word 2002 emit this information when performing an incremental save. 
    /// Office Word 2003, Office Word 2007,and Word 2010 do not emit this information.
    /// Word 97 reads this information if FibBase.nFib is 193. Word 2000 reads this information if FibRgCswNew.nFibNew is 217. 
    /// Word 2002 reads this information if FibRgCswNew.nFibNew is 257. 
    /// 
    /// Office Word 2003, Office Word 2007, and Word 2010 do not read this information.
    /// </remarks>
    FcLcb PlcfPhe = new FcLcb();          // 0xda

    /// <summary>
    /// Fc/Lcb of glossary string table.
    /// If FibBase.fGlsy of the Fib that contains this FibRgFcLcb97 is zero, this value MUST be zero.
    /// </summary>
    FcLcb SttbfGlsy = new FcLcb();        // 0xe2

    /// <summary>
    /// Fc/Lcb of glossary PLCF.
    /// If FibBase.fGlsy of the Fib that contains this FibRgFcLcb97 is zero, this value MUST be zero.
    /// </summary>
    FcLcb PlcfGlsy = new FcLcb();         // 0xea

    /// <summary>
    /// Fc/Lcb of header PLCF.
    /// SPEC: A PlcfHdd MUST exist if FibRgLw97.ccpHdd indicates that there are characters in the Header Document 
    /// (that is, if FibRgLw97.ccpHdd is greater than 0). Otherwise, the Plcfhdd MUST NOT exist.
    /// </summary>
    FcLcb PlcfHdd = new FcLcb();       // 0xf2

    /// <summary>
    /// Fc/Lcb of character property bin table PLCF.
    /// </summary>
    FcLcb PlcfBteChpx = new FcLcb();   // 0xfa

    /// <summary>
    /// Fc/Lcb of paragraph property bin table PLCF.
    /// </summary>
    FcLcb PlcfBtePapx = new FcLcb();   // 0x102

    /// <summary>
    /// Fc/Lcb of PLCF reserved for private use. The SEA is 6 bytes long.
    /// SPEC: This value is undefined and MUST be ignored.
    /// </summary>
    FcLcb PlcfSea = new FcLcb();       // 0x10a

    /// <summary>
    /// Fc/Lcb of font information STTBF. See the FFN file structure definition.
    /// </summary>
    FcLcb SttbfFfn = new FcLcb();      // 0x112

    /// <summary>
    /// Fc/Lcb of the PLCF of field positions in the main document.
    /// </summary>
    FcLcb PlcfFldMom = new FcLcb();    // 0x11a

    /// <summary>
    /// Fc/Lcb of the PLCF of field positions in the header subdocument.
    /// </summary>
    FcLcb PlcfFldHdr = new FcLcb();    // 0x122

    /// <summary>
    /// Fc/Lcb of the PLCF of field positions in the footnote subdocument.
    /// </summary>
    FcLcb PlcfFldFtn = new FcLcb();    // 0x12a

    /// <summary>
    /// Fc/Lcb of the PLCF of field positions in the annotation subdocument.
    /// </summary>
    FcLcb PlcfFldAtn = new FcLcb();    // 0x132

    /// <summary>
    /// Fc/Lcb of the PLCF of field positions in the macro subdocument.
    /// SPEC: This value is undefined and MUST be ignored.
    /// </summary>
    FcLcb PlcfFldMcr = new FcLcb();     // 0x13a

    /// <summary>
    /// Fc/Lcb in the Table Stream of the STTBF that records bookmark names in the main document.
    /// </summary>
    FcLcb SttbfBkmk = new FcLcb();     // 0x142 

    /// <summary>
    /// Fc/Lcb in the Table Stream of the PLCF that records the beginning CP offsets of bookmarks in the main document.
    /// </summary>
    FcLcb PlcfBkf = new FcLcb();       // 0x14a

    /// <summary>
    /// Fc/Lcb in the Table Stream of the PLCF that records the ending CP offsets of bookmarks recorded in the main document. 
    /// </summary>
    FcLcb PlcfBkl = new FcLcb();       // 0x152

    /// <summary>
    /// Fc/Lcb in the Table Stream of a Tcg that specifies command-related customizations. 
    /// </summary>
    FcLcb Cmds = new FcLcb();          // 0x15a

    /// <summary>
    /// SPEC: fcUnused1 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private /*final*/ FcLcb PlcfMcr = new FcLcb();        // 0x162

    /// <summary>
    /// SPEC: fcSttbfMcr (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    FcLcb SttbfMcr = new FcLcb();       // 0x16a

    /// <summary>
    /// Fc/Lcb in the Table Stream of the printer driver information (names of drivers, port etc...).
    /// </summary>
    FcLcb PrDrvr = new FcLcb();         // 0x172

    /// <summary>
    /// Fc/Lcb in the Table Stream of the print environment in portrait mode.
    /// </summary>
    FcLcb PrEnvPort = new FcLcb();      // 0x17a

    /// <summary>
    /// Fc/Lcb in the Table Stream of the print environment in landscape mode.
    /// </summary>
    FcLcb PrEnvLand = new FcLcb();      // 0x182

    /// <summary>
    /// Fc/Lcb in the Table Stream of Selsf which specifies the last selection that was made in the Main Document.
    /// </summary>
    FcLcb Wss = new FcLcb();            // 0x18a 

    /// <summary>
    /// Fc/Lcb of document property data structure.
    /// </summary>
    /// <remarks>
    /// Lcb is 84 when nFib less than 103.
    /// </remarks>
    FcLcb Dop = new FcLcb();           // 0x192

    /// <summary>
    /// Fc/Lcb of STTBF of associated strings. See STTBFASSOC.
    /// The value of lcbSttbfAssoc MUST NOT be zero.
    /// </summary>
    FcLcb SttbfAssoc = new FcLcb();    // 0x19a

    /// <summary>
    /// Fc/Lcb of beginning of information for complex files.
    /// SPEC: The value of lcbClx MUST be greater than zero.
    /// </summary>
    FcLcb Clx = new FcLcb();           // 0x1a2

    /// <summary>
    /// Fc/Lcb of page descriptor PLCF for footnote subdocument.
    /// </summary>
    /// <remarks>
    /// fcPlcfPgdFtn (4 bytes): This value is undefined and MUST be ignored.
    /// </remarks>
    FcLcb PlcfPgdFtn = new FcLcb();     // 0x1aa

    // 35

    /// <summary>
    /// Fc/Lcb of the name of the original file.
    /// SPEC: fcAutosaveSource (4 bytes): This value is undefined and MUST be ignored.
    /// SPEC: lcbAutosaveSource (4 bytes): This value MUST be zero and MUST be ignored.
    /// </summary>
    FcLcb AutosaveSource = new FcLcb();     // 0x1b2

    /// <summary>
    /// Fc/Lcb of strings array recording the names of the owners of annotations.
    /// </summary>
    FcLcb GrpXstAtnOwners = new FcLcb();   // 0x1ba

    /// <summary>
    /// Fc/Lcb of Sttbf in Table Stream that records names of bookmarks in the annotation subdocument.
    /// </summary>
    FcLcb SttbfAtnBkmk = new FcLcb();      // 0x1c2

    /// <summary>
    /// Fc/Lcb of the FDOA (drawn object) PLCF for main document. The length of the FDOA is 6 bytes.
    /// SPEC: fcUnused2 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    FcLcb PlcfdoaMom = new FcLcb();         // 0x1ca

    /// <summary>
    /// Fc/Lcb of the FDOA (drawn object) PLCF for the header document. The length of the FDOA is 6 bytes.
    /// SPEC: fcUnused3 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    FcLcb PlcfdoaHdr = new FcLcb();         // 0x1d2

    // 40

    /// <summary>
    /// Fc/Lcb of the FSPA PLCF for main document.
    /// If there are no shapes in the Main Document, lcbPlcfSpaMom and fcPlcfSpaMom MUST be zero and MUST be ignored.
    /// </summary>
    FcLcb PlcfSpaMom = new FcLcb();        // 0x1da

    /// <summary>
    /// Fc/Lcb of the FSPA PLCF for header document.
    /// If there are no shapes in the Header Document, lcbPlcSpaHdr and fcPlcSpaHdr MUST both be zero and MUST be ignored.
    /// </summary>
    FcLcb PlcfSpaHdr = new FcLcb();      // 0x1e2

    /// <summary>
    /// BKF (bookmark first) PLCF of the annotation subdocument.
    /// </summary>
    FcLcb PlcfAtnBkf = new FcLcb();      // 0x1ea

    /// <summary>
    /// BKL (bookmark last) PLCF of the annotation subdocument.
    /// </summary>
    FcLcb PlcfAtnBkl = new FcLcb();      // 0x1f2

    /// <summary>
    /// PMS (Print Merge State) information block.
    /// </summary>
    FcLcb Pms = new FcLcb();           // 0x1fa

    // 45

    /// <summary>
    /// Fc/Lcb of form field Sttbf which contains strings used in form field dropdown controls.
    /// </summary>
    /// <remarks>
    /// SPEC: fcFormFldSttbs (4 bytes): This value is undefined and MUST be ignored.
    /// SPEC: lcbFormFldSttbs (4 bytes): This value MUST be zero, and MUST be ignored.
    /// </remarks>
    FcLcb FormFldSttbs = new FcLcb();    // 0x202

    /// <summary>
    /// Fc/Lcb of PLCFendRef which points to endnote references in the main document stream.
    /// </summary>
    FcLcb PlcfendRef = new FcLcb();      // 0x20a

    /// <summary>
    /// Fc/Lcb of PLCFendRef which points to endnote text in the endnote document stream which corresponds with the PLCFendRef.
    /// </summary>
    /// <remarks>
    /// lcbPlcfendTxt MUST be zero if FibRgLw97.ccpEdn is zero, and MUST be nonzero if FibRgLw97.ccpEdn is nonzero.
    /// </remarks>
    FcLcb PlcfendTxt = new FcLcb();      // 0x212

    /// <summary>
    /// Fc/Lcb to PLCF of field positions in the endnote subdocument.
    /// </summary>
    FcLcb PlcfFldEdn = new FcLcb();      // 0x21a

    /// <summary>
    /// Fc/Lcb to PLCF of page boundaries in the endnote subdocument.
    /// </summary>
    /// <remarks>
    /// [MS-DOC] fcUnused4 (4 bytes): This value is undefined and MUST be ignored.
    /// </remarks>
    FcLcb PlcfpgdEdn = new FcLcb();       // 0x222

    // 50

    /// <summary>
    /// Fc/Lcb in the Table Stream of the office art object table data.
    /// </summary>
    FcLcb DggInfo = new FcLcb();         // 0x22a

    /// <summary>
    /// Fc/Lcb to STTBF that contains the names of authors who have added revision marks or comments to the document.
    /// </summary>
    FcLcb SttbfRMark = new FcLcb();      // 0x232

    /// <summary>
    /// Fc/Lcb of the SttbfCaption that contains information about the captions.
    /// </summary>
    /// <remarks>
    /// If this document is not the Normal template, this value MUST be ignored.
    /// 
    /// If FibBase.fDot of the Fib that contains this FibRgFcLcb97 is zero, Lcb value MUST be zero.
    /// 
    /// Each string in this STTB structure is the label of a caption, and MUST have less than or equal to 40 characters. 
    /// The extra data appended to each string is a CAPI structure that specifies addition information about the caption.
    /// </remarks>
    FcLcb SttbfCaption = new FcLcb();     // 0x23a

    /// <summary>
    /// Fc/Lcb of the SttbfAutoCaption that contains information about the AutoCaption strings.
    /// </summary>
    /// <remarks>
    /// If base.fDot of the Fib that contains this FibRgFcLcb97 is zero, Lcb value MUST be zero.
    /// 
    /// Each string in this STTB is the ProgID of an OLE object that, when inserted into the document, 
    /// automatically has a caption inserted with it. 
    /// The extra data which is appended to each string is an unsigned 16-bit integer that specifies a zero-based index into SttbfCaption.
    /// </remarks>
    FcLcb SttbfAutoCaption = new FcLcb(); // 0x242

    /// <summary>
    /// Fc/Lcb of PLCF that contains information about all master documents and subdocuments. See <see cref="PlcfWkb"/>.
    /// </summary>
    FcLcb PlcfWkb = new FcLcb();         // 0x24a

    // 55

    /// <summary>
    /// Fc/Lcb in Table Stream of PLCF (of SPLS structures) that records spell check state.
    /// </summary>
    FcLcb PlcfSpl = new FcLcb();          // 0x252

    /// <summary>
    /// Fc/Lcb in the Table Stream of PlcftxbxTxt which specifies which ranges of text are contained in which textboxes.
    /// lcbPlcftxbxTxt MUST be zero if FibRgLw97.ccpTxbx is zero, and MUST be nonzero if FibRgLw97.ccpTxbx is nonzero.
    /// </summary>
    FcLcb PlcftxbxTxt = new FcLcb();     // 0x25a

    /// <summary>
    /// Fc/Lcb of PLCF of field boundaries recorded in the textbox subdocument.
    /// </summary>
    FcLcb PlcfFldTxbx = new FcLcb();     // 0x262

    /// <summary>
    /// Fc/Lcb of PLCF of beginning CP in the header text box subdocument.
    /// </summary>
    FcLcb PlcfHdrtxbxTxt = new FcLcb();  // 0x26a

    /// <summary>
    /// Fc/Lcb of PLCF of field boundaries recorded in the header textbox subdocument.
    /// </summary>
    FcLcb PlcffldHdrTxbx = new FcLcb();  // 0x272

    // 60

    /// <summary>
    /// Fc/Lcb in the Table Stream of StwUser that specifies the user-defined variables and VBA digital signature (2).
    /// </summary>
    FcLcb StwUser = new FcLcb();         // 0x27a

    /// <summary>
    /// Fc/Lcb in table stream of embedded true type font data.
    /// </summary>
    FcLcb SttbTtmbd = new FcLcb();       // 0x282

    /// <summary>
    /// Fc/Lcb of array of Cdb structures which are grammar checker cookies.
    /// </summary>
    /// <remarks>
    /// fcCookieData MAY be ignored. Office Word 2007 and Word 2010 ignore this data.
    /// </remarks>
    FcLcb CookieData = new FcLcb();       // 0x28a

    /// <summary>
    /// Deprecated document page layout cache. 
    /// </summary>
    /// <remarks>
    /// Information SHOULD NOT be emitted at this offset and SHOULD be ignored.
    /// Word 97 emits information at offset fcPgdMotherOldOld.
    /// Word 97 reads this information. Word 2000, Word 2002, Office Word 2003, Office Word 2007, and Word 2010 ignore this information.
    /// </remarks>
    FcLcb PgdMotherOldOld = new FcLcb();  // 0x292

    /// <summary>
    /// Fc/Lcb in the Table Stream of deprecated document text flow break cache.
    /// </summary>
    FcLcb BkdMotherOldOld = new FcLcb();  // 0x29a

    // 65

    /// <summary>
    /// Fc/Lcb in the Table Stream of deprecated footnote layout cache.
    /// </summary>
    FcLcb PgdFtnOldOld = new FcLcb();     // 0x2a2

    /// <summary>
    /// Fc/Lcb in the Table Stream of deprecated footnote text flow break cache begins at this offset.
    /// </summary>
    FcLcb BkdFtnOldOld = new FcLcb();     // 0x2aa

    /// <summary>
    /// Fc/Lcb in the Table Stream of deprecated endnote layout cache begins at this offset.
    /// </summary>
    FcLcb PgdEdnOldOld = new FcLcb();     // 0x2b2

    /// <summary>
    /// Fc/Lcb in the Table Stream of deprecated endnote text flow break cache.
    /// </summary>
    FcLcb BkdEdnOldOld = new FcLcb();     // 0x2ba

    /// <summary>
    /// Fc/Lcb of the STTBF containing field keywords. This is only used in a small number of the international versions of word.
    /// </summary>
    /// <remarks>
    /// This field is no longer written to the file for nFib >= 167.
    /// fcSttbfIntlFld (4 bytes): This value is undefined and MUST be ignored.
    /// </remarks>
    FcLcb SttbfIntlFld = new FcLcb();     // 0x2c2

    // 70

    /// <summary>
    /// Fc/Lcb of the RouteSlip that specifies the route slip for this document.
    /// </summary>
    FcLcb RouteSlip = new FcLcb();       // 0x2ca

    /// <summary>
    /// Fc/Lcb of STTBF recording the names of the users who have saved this document alternating with the save locations.
    /// </summary>
    /// <remarks>
    /// SttbSavedBy is only saved and read by Word 97 and Word 2000.
    /// </remarks>
    FcLcb SttbSavedBy = new FcLcb();      // 0x2d2

    /// <summary>
    /// Fc/Lcb in the Table Stream of SttbFnm that contains information about the external files that are referenced by this document.
    /// </summary>
    FcLcb SttbFnm = new FcLcb();         // 0x2da

    /// <summary>
    /// Fc/Lcb in the Table Stream of PlfLst that contains list formatting information.
    /// </summary>
    FcLcb PlcfLst = new FcLcb();         // 0x2e2

    /// <summary>
    /// Fc/Lcb in the Table Stream of PlfLfo that contains list formatting override information.
    /// </summary>
    FcLcb PlfLfo = new FcLcb();          // 0x2ea

    // 75

    /// <summary>
    /// Fc/Lcb in the Table Stream of PlcftxbxBkd that specifies which ranges of text go inside which textboxes.
    /// </summary>
    FcLcb PlcfTxbxBkd = new FcLcb();     // 0x2f2

    /// <summary>
    /// Fc/Lcb in the Table Stream of PlcfTxbxHdrBkd that specifies which ranges of text are contained inside which header textboxes.
    /// lcbPlcfTxbxHdrBkd MUST be zero if FibRgLw97.ccpHdrTxbx is zero, and MUST be nonzero if FibRgLw97.ccpHdrTxbx is nonzero.
    /// </summary>
    FcLcb PlcfTxbxHdrBkd = new FcLcb();  // 0x2fa

    /// <summary>
    /// Fc/Lcb in the WordDocument Stream of version-specific undo information.
    /// </summary>
    /// <remarks>
    /// This information SHOULD NOT be emitted and SHOULD be ignored.
    /// Word 97 and Word 2000 write this information when the user chooses to save versions in the document.
    /// Word 97, Word 2000, Word 2002, and Office Word 2003 read this information. Office Word 2007 and Word 2010 ignore it.
    /// </remarks>
    FcLcb DocUndoWord9 = new FcLcb();     // 0x302

    /// <summary>
    /// Fc/Lcb in the WordDocument Stream of version-specific undo information.
    /// </summary>
    FcLcb Rgbuse = new FcLcb();           // 0x30a

    /// <summary>
    /// Fc/Lcb in the WordDocument Stream of version-specific undo information.
    /// </summary>
    FcLcb Usp = new FcLcb();              // 0x312

    // 80 //

    /// <summary>
    /// Fc/Lcb in the Table Stream of version-specific undo information.
    /// </summary>
    FcLcb Uskf = new FcLcb();             // 0x31a

    /// <summary>
    /// Fc/Lcb in the Table Stream of Plc that contains version-specific undo information.
    /// </summary>
    FcLcb PlcupcRgbUse = new FcLcb();     // 0x322

    /// <summary>
    /// Fc/Lcb in the Table Stream of the Plc that contains version-specific undo information.
    /// </summary>
    FcLcb PlcupcUsp = new FcLcb();        // 0x32a

    /// <summary>
    /// Fc/Lcb in the Table Stream of string table of style names for glossary entries.
    /// </summary>
    /// <remarks>
    /// If base.fGlsy of the Fib that contains this FibRgFcLcb97 is zero, this value MUST be zero.
    /// </remarks>
    FcLcb SttbGlsyStyle = new FcLcb();    // 0x332

    /// <summary>
    /// Fc/Lcb in the Table Stream of the PlfGosl that contains option set for a grammar checker.
    /// </summary>
    FcLcb Plgosl = new FcLcb();           // 0x33a

    // 85

    /// <summary>
    /// Fc/Lcb of the RgxOcxInfo that specifies information about the OLE controls.
    /// </summary>
    /// <remarks>
    /// If there are any OLE controls in the document, fcPlcocx MUST point to a valid RgxOcxInfo.
    ///
    /// The data that is contained in OcxInfo structures SHOULD be ignored.
    /// 
    /// Office Word 2003, Office Word 2007, and Word 2010 ignore the values in the OcxInfo structure but, for backward compatibility, 
    /// emit values based on the OLE controls in the document. The values are populated by finding all the control FLDs in the document and 
    /// saving the values for the corresponding OLE controls. 
    /// 
    /// Previous versions of Word expect that the values in OcxInfo structures and the values of the controls all match. 
    /// The description of OcxInfo fields specifies the values that are written.
    /// </remarks>
    FcLcb Plcocx = new FcLcb();           // 0x342

    /// <summary>
    /// Fc/Lcb in the Table Stream of deprecated numbering field cache.
    /// </summary>
    FcLcb PlcfBteLvc = new FcLcb();       // 0x034a

    /// <summary>
    /// The low-order part of a FILETIME structure, as specified by [MS-DTYP], that specifies when the document was last saved.
    /// </summary>
    private int dwLowDateTime;      // 0x352
    /// <summary>
    /// The high-order part of a FILETIME structure, as specified by [MS-DTYP], that specifies when the document was last saved.
    /// </summary>
    private int dwHighDateTime;     // 0x356

    /// <summary>
    ///  Fc/Lcb in the Table Stream of the deprecated list level cache.
    /// </summary>
    FcLcb PlcfLvcPre10 = new FcLcb();     // 0x35a

    /// <summary>
    /// Fc/Lcb of PlcfAsumy whose data elements contain information about priority of a text range for AutoSummary.
    /// </summary>
    FcLcb PlcfAsumy = new FcLcb();        // 0x362

    // 90

    /// <summary>
    /// Fc/Lcb of a Plcfgram, which specifies the state of the grammar checker for each text range.
    /// </summary>
    FcLcb PlcfGram = new FcLcb();         // 0x36a

    /// <summary>
    /// Fc/Lcb in the Table Stream of SttbListNames, which specifies the LISTNUM field names of the lists.
    /// </summary>
    FcLcb SttbListNames = new FcLcb();   // 0x0372

    /// <summary>
    /// Fc/Lcb of the deprecated, version-specific undo information.
    /// </summary>
    /// <remarks>
    /// Some sort of a string table that contains version comments mangled with timestamps probably.
    /// </remarks>
    FcLcb SttbfUssr = new FcLcb();       // 0x37a

    // ReSharper enable InconsistentNaming

    boolean hasAutoCaptions() { return (SttbfCaption.Lcb > 0) || (SttbfAutoCaption.Lcb > 0); }

    boolean hasVersioning() 
    { 
        return (DocUndoWord9.Lcb > 0) || (Rgbuse.Lcb > 0) || (Usp.Lcb > 0) || (Uskf.Lcb > 0) || 
        (PlcupcRgbUse.Lcb > 0) || (PlcupcUsp.Lcb > 0) || (SttbfUssr.Lcb > 0); 
    }

    boolean hasPrinterDriverData() { return (PrDrvr.Lcb > 0) || (PrEnvPort.Lcb > 0) || (PrEnvLand.Lcb > 0); }
    
    boolean hasGrammarCheckerData() { return (CookieData.Lcb > 0) || (Plgosl.Lcb > 0) || (PlcfGram.Lcb > 0); }
    
    boolean hasLastSelectionData() { return Wss.Lcb > 0; }

    boolean hasSpellCheckerData() { return PlcfSpl.Lcb > 0; }

    boolean hasRouteSlip() { return RouteSlip.Lcb > 0; }

    boolean hasAutoSummaryPriorities() { return PlcfAsumy.Lcb > 0; }

    boolean hasDeprecatedOcxData() { return Plcocx.Lcb > 0; }

    boolean hasSaveHistory() { return SttbSavedBy.Lcb > 0; }

    void read(BinaryReader reader) throws Exception
    {
        // 0
        StshfOrig.read(reader);
        Stshf.read(reader);
        PlcffndRef.read(reader);
        PlcffndTxt.read(reader);
        PlcfAndRef.read(reader);
        PlcfAndTxt.read(reader);
        PlcfSed.read(reader);
        PlcPad.read(reader);
        PlcfPhe.read(reader);
        SttbfGlsy.read(reader);
        // 10
        PlcfGlsy.read(reader);
        PlcfHdd.read(reader);
        PlcfBteChpx.read(reader);
        PlcfBtePapx.read(reader);
        PlcfSea.read(reader);
        SttbfFfn.read(reader);
        PlcfFldMom.read(reader);
        PlcfFldHdr.read(reader);
        PlcfFldFtn.read(reader);
        PlcfFldAtn.read(reader);
        // 20
        PlcfFldMcr.read(reader);
        SttbfBkmk.read(reader);
        PlcfBkf.read(reader);
        PlcfBkl.read(reader);
        Cmds.read(reader);
        PlcfMcr.read(reader);
        SttbfMcr.read(reader);
        PrDrvr.read(reader);
        PrEnvPort.read(reader);
        PrEnvLand.read(reader);
        // 30
        Wss.read(reader);
        Dop.read(reader);
        SttbfAssoc.read(reader);
        Clx.read(reader);
        PlcfPgdFtn.read(reader);
        AutosaveSource.read(reader);
        GrpXstAtnOwners.read(reader);
        SttbfAtnBkmk.read(reader);
        PlcfdoaMom.read(reader);
        PlcfdoaHdr.read(reader);
        // 40
        PlcfSpaMom.read(reader);
        PlcfSpaHdr.read(reader);
        PlcfAtnBkf.read(reader);
        PlcfAtnBkl.read(reader);
        Pms.read(reader);
        FormFldSttbs.read(reader);
        PlcfendRef.read(reader);
        PlcfendTxt.read(reader); 
        PlcfFldEdn.read(reader);
        PlcfpgdEdn.read(reader);
        // 50
        DggInfo.read(reader);
        SttbfRMark.read(reader);
        SttbfCaption.read(reader);
        SttbfAutoCaption.read(reader);
        PlcfWkb.read(reader);
        // 55
        PlcfSpl.read(reader);
        PlcftxbxTxt.read(reader);
        PlcfFldTxbx.read(reader);
        PlcfHdrtxbxTxt.read(reader);
        PlcffldHdrTxbx.read(reader);
        // 60
        StwUser.read(reader);
        SttbTtmbd.read(reader);
        CookieData.read(reader);
        PgdMotherOldOld.read(reader);
        BkdMotherOldOld.read(reader);
        // 65
        PgdFtnOldOld.read(reader);
        BkdFtnOldOld.read(reader);
        PgdEdnOldOld.read(reader);
        BkdEdnOldOld.read(reader);
        SttbfIntlFld.read(reader);
        // 70
        RouteSlip.read(reader);
        SttbSavedBy.read(reader);
        SttbFnm.read(reader);
        PlcfLst.read(reader);
        PlfLfo.read(reader);
        // 75
        PlcfTxbxBkd.read(reader);
        PlcfTxbxHdrBkd.read(reader);
        DocUndoWord9.read(reader);
        Rgbuse.read(reader);
        Usp.read(reader);
        // 80
        Uskf.read(reader);
        PlcupcRgbUse.read(reader);
        PlcupcUsp.read(reader);
        SttbGlsyStyle.read(reader);
        Plgosl.read(reader);
        // 85
        Plcocx.read(reader);
        PlcfBteLvc.read(reader);
        dwLowDateTime = reader.readInt32();
        dwHighDateTime = reader.readInt32();
        PlcfLvcPre10.read(reader);
        PlcfAsumy.read(reader);
        // 90
        PlcfGram.read(reader);
        SttbListNames.read(reader);
        SttbfUssr.read(reader);
    }

    void write(BinaryWriter writer) throws Exception
    {
        StshfOrig.write(writer);
        Stshf.write(writer);
        PlcffndRef.write(writer);
        PlcffndTxt.write(writer);
        PlcfAndRef.write(writer);
        // 5
        PlcfAndTxt.write(writer);
        PlcfSed.write(writer);
        PlcPad.write(writer);
        PlcfPhe.write(writer);
        SttbfGlsy.write(writer);
        // 10
        PlcfGlsy.write(writer);
        PlcfHdd.write(writer);
        PlcfBteChpx.write(writer);
        PlcfBtePapx.write(writer);
        PlcfSea.write(writer);
        // 15
        SttbfFfn.write(writer);
        PlcfFldMom.write(writer);
        PlcfFldHdr.write(writer);
        PlcfFldFtn.write(writer);
        PlcfFldAtn.write(writer);
        // 20
        PlcfFldMcr.write(writer);
        SttbfBkmk.write(writer);
        PlcfBkf.write(writer);
        PlcfBkl.write(writer);
        Cmds.write(writer);
        // 25
        PlcfMcr.write(writer);
        SttbfMcr.write(writer);
        PrDrvr.write(writer);
        PrEnvPort.write(writer);
        PrEnvLand.write(writer);
        // 30
        Wss.write(writer);
        Dop.write(writer);
        SttbfAssoc.write(writer);
        Clx.write(writer);
        PlcfPgdFtn.write(writer);
        // 35
        AutosaveSource.write(writer);
        GrpXstAtnOwners.write(writer);
        SttbfAtnBkmk.write(writer);
        PlcfdoaMom.write(writer);
        PlcfdoaHdr.write(writer);
        // 40
        PlcfSpaMom.write(writer);
        PlcfSpaHdr.write(writer);
        PlcfAtnBkf.write(writer);
        PlcfAtnBkl.write(writer);
        Pms.write(writer);
        // 45
        FormFldSttbs.write(writer);
        PlcfendRef.write(writer);
        PlcfendTxt.write(writer);
        PlcfFldEdn.write(writer);
        PlcfpgdEdn.write(writer);
        // 50
        DggInfo.write(writer);
        SttbfRMark.write(writer);
        SttbfCaption.write(writer);
        SttbfAutoCaption.write(writer);
        PlcfWkb.write(writer);
        // 55
        PlcfSpl.write(writer);
        PlcftxbxTxt.write(writer);
        PlcfFldTxbx.write(writer);
        PlcfHdrtxbxTxt.write(writer);
        PlcffldHdrTxbx.write(writer);
        // 60
        StwUser.write(writer);
        SttbTtmbd.write(writer);
        CookieData.write(writer);
        PgdMotherOldOld.write(writer);
        BkdMotherOldOld.write(writer);
        // 65
        PgdFtnOldOld.write(writer);
        BkdFtnOldOld.write(writer);
        PgdEdnOldOld.write(writer);
        BkdEdnOldOld.write(writer);
        SttbfIntlFld.write(writer);
        // 70
        RouteSlip.write(writer);
        SttbSavedBy.write(writer);
        SttbFnm.write(writer);
        PlcfLst.write(writer);
        PlfLfo.write(writer);
        // 75
        PlcfTxbxBkd.write(writer);
        PlcfTxbxHdrBkd.write(writer);
        DocUndoWord9.write(writer);
        Rgbuse.write(writer);
        Usp.write(writer);
        // 80
        Uskf.write(writer);
        PlcupcRgbUse.write(writer);
        PlcupcUsp.write(writer);
        SttbGlsyStyle.write(writer);
        Plgosl.write(writer);
        // 85
        Plcocx.write(writer);
        PlcfBteLvc.write(writer);
        writer.writeInt32(dwLowDateTime);
        writer.writeInt32(dwHighDateTime);
        PlcfLvcPre10.write(writer);
        PlcfAsumy.write(writer);
        // 90
        PlcfGram.write(writer);
        SttbListNames.write(writer);
        SttbfUssr.write(writer);
    }

    void readWord60Part1(BinaryReader reader) throws Exception
    {
        StshfOrig.read(reader);
        Stshf.read(reader);
        PlcffndRef.read(reader);
        PlcffndTxt.read(reader);
        PlcfAndRef.read(reader);
        PlcfAndTxt.read(reader);
        PlcfSed.read(reader);
        PlcPad.read(reader);
        PlcfPhe.read(reader);
        SttbfGlsy.read(reader);
        // 10
        PlcfGlsy.read(reader);
        PlcfHdd.read(reader);
        PlcfBteChpx.read(reader);
        PlcfBtePapx.read(reader);
        PlcfSea.read(reader);
        SttbfFfn.read(reader);
        PlcfFldMom.read(reader);
        PlcfFldHdr.read(reader);
        PlcfFldFtn.read(reader);
        PlcfFldAtn.read(reader);
        // 20
        PlcfFldMcr.read(reader);
        SttbfBkmk.read(reader);
        PlcfBkf.read(reader);
        PlcfBkl.read(reader);
        Cmds.read(reader);
        PlcfMcr.read(reader);
        SttbfMcr.read(reader);
        PrDrvr.read(reader);
        PrEnvPort.read(reader);
        PrEnvLand.read(reader);
        // 30
        Wss.read(reader);
        Dop.read(reader);
        SttbfAssoc.read(reader);
        Clx.read(reader);
        PlcfPgdFtn.read(reader);
        AutosaveSource.read(reader);
        GrpXstAtnOwners.read(reader);
        SttbfAtnBkmk.read(reader);
    }

    void readWord60Part2(BinaryReader reader) throws Exception
    {
        PlcfdoaMom.read(reader);
        PlcfdoaHdr.read(reader);
        // 40
        new FcLcb().read(reader);       // unused1
        new FcLcb().read(reader);       // unused2

        PlcfAtnBkf.read(reader);
        PlcfAtnBkl.read(reader);
        Pms.read(reader);
        FormFldSttbs.read(reader);
        PlcfendRef.read(reader);
        PlcfendTxt.read(reader);
        PlcfFldEdn.read(reader);
        PlcfpgdEdn.read(reader);
        // 50
        new FcLcb().read(reader);       // unused3
        SttbfRMark.read(reader);
        SttbfCaption.read(reader);
        SttbfAutoCaption.read(reader);
        PlcfWkb.read(reader);
        // 55
        new FcLcb().read(reader);       // unused4
        PlcftxbxTxt.read(reader);
        PlcfFldTxbx.read(reader);
        PlcfHdrtxbxTxt.read(reader);
        PlcffldHdrTxbx.read(reader);
        // 60
        StwUser.read(reader);
        SttbTtmbd.read(reader);
        new FcLcb().read(reader);       // unused5
        PgdMotherOldOld.read(reader);
        BkdMotherOldOld.read(reader);
        // 65
        PgdFtnOldOld.read(reader);
        BkdFtnOldOld.read(reader);
        PgdEdnOldOld.read(reader);
        BkdEdnOldOld.read(reader);
        SttbfIntlFld.read(reader);
        // 70
        RouteSlip.read(reader);
        SttbSavedBy.read(reader);
        SttbFnm.read(reader);
    }

    /// <summary>
    /// Writes dummy Fc for few Fc/Lcb pairs although these structures are undefined.
    /// </summary>
    void setDummyFc(int position)
    {
        PlcfPhe.Fc = position;
        PlcfSea.Fc = position;
        SttbfGlsy.Fc = position;
        PlcfGlsy.Fc = position;
    }
}


// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/08/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements [MS-DOC] 2.5.8 FibRgFcLcb2002 structure.
/// </summary>
class RgFcLcb2002
{
    // ReSharper disable InconsistentNaming

    /// <summary>
    /// Fc/Lcb to some PLCF that specifies how nested tables are structured. 
    /// SPEC: fcUnused1 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    private /*final*/ FcLcb Unused2 = new FcLcb();       // 0x3fa

    /// <summary>
    /// Fc/Lcb in the Table Stream of PGPArray which describes the border and margin properties 
    /// that can be applied to a contiguous range of paragraphs.
    /// </summary>
    FcLcb PlcfPgp = new FcLcb();       // 0x402

    /// <summary>
    /// Fc/Lcb in the Table Stream of the Plcfuim which contains data that was provided 
    /// by the Microsoft Windows Text Services Framework, a service provided by Microsoft Windows that 
    /// enables the application to receive input from different input sources, such as handwriting.
    /// </summary>
    /*final*/ FcLcb Plcfuim = new FcLcb();          // 0x40a

    /// <summary>
    /// Fc/Lcb in the Table Stream of the PlfguidUim.
    /// </summary>
    /*final*/ FcLcb PlfguidUim = new FcLcb();       // 0x412

    /// <summary>
    /// Fc/Lcb in the Table Stream of ATRDPost10 structures.
    /// </summary>
    FcLcb AtrdExtra = new FcLcb();       // 0x41a

    /// <summary>
    /// Fc/Lcb in the Table Stream of a PLRSID which is array of revision-save identifiers.
    /// </summary>
    FcLcb Plrsid = new FcLcb();           // 0x422

    /// <summary>
    /// Fc/Lcb of smart tag bookmark STTB.
    /// The SttbfBkmkFactoid is parallel to the PlcfBkfd at offset fcPlcfBkfFactoid.
    /// </summary>
    FcLcb SttbfBkmkFactoid = new FcLcb(); // 0x42a

    /// <summary>
    /// Fc/Lcb of smart tag bookmark Plc of cpFirsts. PLCF of smart tag starts.
    /// </summary>
    FcLcb PlcfBkfFactoid = new FcLcb();   // 0x432

    /// <summary>
    /// Fc/Lcb of a PlcfcookieOffset which contains information about a grammar checker cookie. 
    /// The grammar checker cookie itself is contained within the data that corresponds to the fcCookieData member of FibRgFcLcb97.
    /// </summary>
    /*final*/ FcLcb Plcfcookie = new FcLcb();           // 0x43a

    /// <summary>
    /// Fc/Lcb of smart tag bookmark Plc of cpLims. PLCF of smart tag ends.
    /// </summary>
    FcLcb PlcfBklFactoid = new FcLcb();      // 0x442

    /// <summary>
    /// Fc/Lcb of smart tag data.
    /// </summary>
    FcLcb FactoidData = new FcLcb();         // 0x44a

    /// <summary>
    /// Fc/Lcb in the WordDocument Stream of version-specific undo information.
    /// </summary>
    /*final*/ FcLcb DocUndo = new FcLcb();          // 0x452

    /// <summary>
    /// Fc/Lcb of SttbfBkmkFcc that contains information about the format consistency-checker bookmarks.
    /// </summary>
    /*final*/ FcLcb SttbfBkmkFcc = new FcLcb();      // 0x45a

    /// <summary>
    /// Fc/Lcb of PlcfBkfFcc that contains information about the format consistency-checker bookmarks.
    /// </summary>
    /*final*/ FcLcb PlcfBkfFcc = new FcLcb();        // 0x462

    /// <summary>
    /// Fc/Lcb of PlcfBklFcc that contains information about the format consistency-checker bookmarks.
    /// </summary>
    private /*final*/ FcLcb PlcfBklFcc = new FcLcb();        // 0x46a

    /// <summary>
    /// Fc/Lcb of an SttbfBkmkBPRepairs that contains information about the repair bookmarks.
    /// </summary>
    /*final*/ FcLcb SttbfbkmkBPRepairs = new FcLcb();   // 0x472

    /// <summary>
    /// Fc/Lcb of an PlcfBkf that contains information about the repair bookmarks.
    /// </summary>
    /*final*/ FcLcb PlcfbkfBPRepairs = new FcLcb();     // 0x47a

    /// <summary>
    /// Fc/Lcb of an PlcfBkl that contains information about the repair bookmarks.
    /// </summary>
    /*final*/ FcLcb PlcfbklBPRepairs = new FcLcb();  // 0x482

    /// <summary>
    /// Fc/Lcb of new Pms, which contains the current state of a print merge operation.
    /// </summary>
    FcLcb PmsNew = new FcLcb();            // 0x48a

    /// <summary>
    /// Fc/Lcb of Office Data Source Object (ODSO) data used to perform mail merge begins at this offset.
    /// </summary>
    FcLcb ODSO = new FcLcb();              // 0x492

    /// <summary>
    /// Fc/Lcb of the deprecated paragraph mark information cache used by Word2002.
    /// Information SHOULD NOT be emitted at this offset and SHOULD be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfpmiOldXP = new FcLcb();      // 0x49a

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated paragraph mark information cache used by Word 2002.
    /// Information SHOULD NOT be emitted at this offset and SHOULD be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfpmiNewXP = new FcLcb();     // 0x4a2

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated paragraph mark information cache used by Word 2002.
    /// Information SHOULD NOT be emitted at this offset and SHOULD be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfpmiMixedXP = new FcLcb();   // 0x4aa

    /// <summary>
    /// Fc/Lcb in Table Stream of encryption properties.
    /// SPEC: fcUnused2 (4 bytes): This value is undefined and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb EncryptedProps = new FcLcb();    // 0x4b2

    /// <summary>
    /// Fc/Lcb in the Table Stream of a Plcffactoid, which specifies the smart tag recognizer state of each text range.
    /// </summary>
    /*final*/ FcLcb Plcffactoid = new FcLcb();       // 0x4ba

    /// <summary>
    /// Fc/Lcb in Table Stream of LVC PLC (Old View) for Word 2002.
    /// This is an internal information cache used by Word.
    /// </summary>
    /*final*/ FcLcb PlcflvcOldXP = new FcLcb();      // 0x4c2

    /// <summary>
    /// Fc/Lcb in Table Stream of LVC PLC (New View) for Word 2002.
    /// This is an internal information cache used by Word.
    /// </summary>
    /*final*/ FcLcb PlcflvcNewXP = new FcLcb();      // 0x4ca

    /// <summary>
    /// Fc/Lcb in Table Stream of LVC PLC (Mixed View) for Word 2002.
    /// This is an internal information cache used by Word.
    /// </summary>
    /*final*/ FcLcb PlcflvcMixedXP = new FcLcb();    // 0x4d2

    // ReSharper enable InconsistentNaming

    /// <summary>
    /// Indicates that document has grammar checker cookies.
    /// </summary>
    boolean hasGrammarCheckerCookies() { return Plcfcookie.Lcb > 0; }

    /// <summary>
    /// Indicates that document has Microsoft Windows Text Services Framework data.
    /// </summary>
    boolean hasTextFrameworkData() { return (PlfguidUim.Lcb > 0) || (Plcfuim.Lcb > 0); }

    /// <summary>
    /// Indicates that document has smart tag recognizer state data.
    /// </summary>
    boolean hasSmartTagRecognizerData() { return Plcffactoid.Lcb > 0; }

    /// <summary>
    /// Indicates that document has repair bookmarks.
    /// </summary>
    boolean hasRepairBookmarks() { return (SttbfbkmkBPRepairs.Lcb > 0) || (PlcfbkfBPRepairs.Lcb > 0) || (PlcfbklBPRepairs.Lcb > 0); }

    boolean hasConsistencyCheckerBookmarks() { return (SttbfBkmkFcc.Lcb > 0) || (PlcfBkfFcc.Lcb > 0) || (PlcfBklFcc.Lcb > 0); }

    void read(BinaryReader reader) throws Exception
    {
        // 108
        Unused2.read(reader);           // 0x3fa
        PlcfPgp.read(reader);           // 0x402

        // RK According to the spec, end of Word 2000 data is here.

        Plcfuim.read(reader);           // 0x40a
        // 110
        PlfguidUim.read(reader);        // 0x412
        AtrdExtra.read(reader);         // 0x41a
        Plrsid.read(reader);            // 0x422
        SttbfBkmkFactoid.read(reader);  // 0x42a
        PlcfBkfFactoid.read(reader);    // 0x432
        // 115
        Plcfcookie.read(reader);        // 0x43a
        PlcfBklFactoid.read(reader);    // 0x442
        FactoidData.read(reader);       // 0x44a
        DocUndo.read(reader);           // 0x452
        SttbfBkmkFcc.read(reader);      // 0x45a
        // 120
        PlcfBkfFcc.read(reader);        // 0x462
        PlcfBklFcc.read(reader);        // 0x46a
        SttbfbkmkBPRepairs.read(reader);// 0x472
        PlcfbkfBPRepairs.read(reader);  // 0x47a
        PlcfbklBPRepairs.read(reader);  // 0x482
        // 125
        PmsNew.read(reader);            // 0x48a
        ODSO.read(reader);              // 0x492
        PlcfpmiOldXP.read(reader);      // 0x49a
        PlcfpmiNewXP.read(reader);      // 0x4a2
        PlcfpmiMixedXP.read(reader);    // 0x4aa
        // 130
        EncryptedProps.read(reader);    // 0x4b2
        Plcffactoid.read(reader);       // 0x4ba
        PlcflvcOldXP.read(reader);      // 0x4c2
        PlcflvcNewXP.read(reader);      // 0x4ca
        PlcflvcMixedXP.read(reader);    // 0x4d2

    }

    void write(BinaryWriter writer) throws Exception
    {
        // 108
        Unused2.write(writer);     // 0x3fa
        PlcfPgp.write(writer);     // 0x402

        // RK According to the spec, end of Word 2000 data is here.

        Plcfuim.write(writer);       // 0x40a
        // 110
        PlfguidUim.write(writer);    // 0x412
        AtrdExtra.write(writer);     // 0x41a
        Plrsid.write(writer);        // 0x422
        SttbfBkmkFactoid.write(writer);  // 0x42a
        PlcfBkfFactoid.write(writer);    // 0x432
        // 115
        Plcfcookie.write(writer);        // 0x43a
        PlcfBklFactoid.write(writer);    // 0x442
        FactoidData.write(writer);       // 0x44a
        DocUndo.write(writer);          // 0x452
        SttbfBkmkFcc.write(writer);      // 0x45a
        // 120
        PlcfBkfFcc.write(writer);        // 0x462
        PlcfBklFcc.write(writer);        // 0x46a
        SttbfbkmkBPRepairs.write(writer);// 0x472
        PlcfbkfBPRepairs.write(writer);  // 0x47a
        PlcfbklBPRepairs.write(writer);  // 0x482
        // 125
        PmsNew.write(writer);            // 0x48a
        ODSO.write(writer);              // 0x492
        PlcfpmiOldXP.write(writer);      // 0x49a
        PlcfpmiNewXP.write(writer);      // 0x4a2
        PlcfpmiMixedXP.write(writer);    // 0x4aa
        // 130
        EncryptedProps.write(writer);    // 0x4b2
        Plcffactoid.write(writer);       // 0x4ba
        PlcflvcOldXP.write(writer);      // 0x4c2
        PlcflvcNewXP.write(writer);      // 0x4ca
        PlcflvcMixedXP.write(writer);    // 0x4d2
    }
}


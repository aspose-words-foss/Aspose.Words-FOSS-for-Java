// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// RgFcLcb2003 part of FIB which contains Word2003 specific Fc/Lcb pairs.
/// </summary>
class RgFcLcb2003
{
    // ReSharper disable InconsistentNaming

    /// <summary>
    /// Fc/Lcb of the Hplxsdr that contains specifies information about XML schema definition references.
    /// </summary>
    FcLcb Hplxsdr = new FcLcb();

    /// <summary>
    /// Fc/Lcb of SttbfBkmkSdt that contains information about the structured document tag bookmarks.
    /// </summary>
    FcLcb SttbfBkmkSdt = new FcLcb();

    /// <summary>
    /// Fc/Lcb of the PlcBkfd that contains information about the structured document tag bookmarks.
    /// </summary>
    FcLcb PlcfBkfSdt = new FcLcb();

    /// <summary>
    /// Fc/Lcb of the PlcBkld that contains information about the structured document tag bookmarks.
    /// </summary>
    FcLcb PlcfBklSdt = new FcLcb();

    /// <summary>
    /// Fc/Lcb of array of 16-bit Unicode characters, which specifies the full path and file name of the XML Stylesheet 
    /// to apply when saving this document in XML format.
    /// </summary>
    /// <remarks>
    /// lcbCustomXForm MUST be less than or equal to 4168 and MUST be evenly divisible by two.
    /// </remarks>
    /*final*/ FcLcb CustomXForm = new FcLcb();

    /// <summary>
    /// Fc/Lcb of the SttbfBkmkProt that contains information about range-level protection bookmarks.
    /// </summary>
    /*final*/ FcLcb SttbfBkmkProt = new FcLcb();

    /// <summary>
    /// Fc/Lcb of the PlcBkf that contains information about range-level protection bookmarks.
    /// </summary>
    /*final*/ FcLcb PlcfBkfProt = new FcLcb();

    /// <summary>
    /// Fc/Lcb in the Table Stream of the PlcBkl that contains information about range-level protection bookmarks.
    /// </summary>
    /*final*/ FcLcb PlcfBklProt = new FcLcb();

    /// <summary>
    /// Fc/Lcb in the Table Stream of the SttbProtUser that specifies the usernames that are used for range-level protection.
    /// </summary>
    /*final*/ FcLcb SttbProtUser = new FcLcb();

    /// <summary>
    /// Fc value MUST be zero, and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /// <remarks>
    /// Made internal for unit testing.
    /// </remarks>
    /*final*/ FcLcb Unused = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated paragraph mark information cache used by Word 2003. 
    /// </summary>
    /*final*/ FcLcb PlcfpmiOld = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated paragraph mark information cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PlcfpmiOldInline = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated paragraph mark information cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PlcfpmiNew = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated paragraph mark information cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PlcfpmiNewInline = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated listnum field cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PlcflvcOld = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated listnum field cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PlcflvcOldInline = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated listnum field cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PlcflvcNew = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated listnum field cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PlcflvcNewInline = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated document page layout cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PgdMother = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated document text flow break cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb BkdMother = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated document author filter cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb AfdMother = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated footnote layout cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PgdFtn = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated footnote text flow break cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb BkdFtn = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated footnote author filter cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb AfdFtn = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated endnote layout cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb PgdEdn = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated endnote text flow break cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb BkdEdn = new FcLcb();

    /// <summary>
    /// Fc/Lcb of deprecated endnote author filter cache used by Word 2003 only.
    /// </summary>
    /*final*/ FcLcb AfdEdn = new FcLcb();

    /// <summary>
    /// Deprecated AFD structure that specifies whose revisions and comments were being hidden 
    /// when this document was last saved used by Word2003 only.
    /// </summary>
    /// <remarks>
    /// See [MS-DOC] 2.9.2 Afd.
    /// </remarks>
    /*final*/ FcLcb Afd = new FcLcb();

    // ReSharper enable InconsistentNaming
    boolean hasRevisionsAuthorSelection() { return (Afd.Lcb > 0); }

    void read(BinaryReader reader) throws Exception
    {
        Hplxsdr.read(reader);
        SttbfBkmkSdt.read(reader);
        PlcfBkfSdt.read(reader);
        PlcfBklSdt.read(reader);
        CustomXForm.read(reader);
        SttbfBkmkProt.read(reader);
        PlcfBkfProt.read(reader);
        PlcfBklProt.read(reader);
        SttbProtUser.read(reader);

        // Below fields are ignored and ain't written but read them for test reason.
        Unused.read(reader);
        PlcfpmiOld.read(reader);
        PlcfpmiOldInline.read(reader);
        PlcfpmiNew.read(reader);
        PlcfpmiNewInline.read(reader);
        PlcflvcOld.read(reader);
        PlcflvcOldInline.read(reader);
        PlcflvcNew.read(reader);
        PlcflvcNewInline.read(reader);
        PgdMother.read(reader);
        BkdMother.read(reader);
        AfdMother.read(reader);
        PgdFtn.read(reader);
        BkdFtn.read(reader);
        AfdFtn.read(reader);
        PgdEdn.read(reader);
        BkdEdn.read(reader);
        AfdEdn.read(reader);
        Afd.read(reader);
    }

    /// <summary>
    /// Writes RgFcLcb2003 structure to given writer.
    /// </summary>
    /// <remarks>
    /// AM. Previous implementation wrote some dummy value to each Fc we ignore. 
    /// Trying to do the same to avoid any possible problems.
    /// </remarks>
    void write(BinaryWriter writer, int dummyFc) throws Exception
    {
        Hplxsdr.write(writer);
        SttbfBkmkSdt.write(writer);
        PlcfBkfSdt.write(writer);
        PlcfBklSdt.write(writer);
        CustomXForm.write(writer);

        SttbfBkmkProt.write(writer);
        PlcfBkfProt.write(writer);
        PlcfBklProt.write(writer);
        SttbProtUser.write(writer);
        
        // This MUST be zero per spec. Otherwise BffValidator fails.
        Unused.Fc = 0;
        Unused.Lcb = 0;

        PlcfpmiOld.Fc = dummyFc;
        PlcfpmiOldInline.Fc = dummyFc;
        PlcfpmiNew.Fc = dummyFc;
        PlcfpmiNewInline.Fc = dummyFc;
        PlcflvcOld.Fc = dummyFc;
        PlcflvcOldInline.Fc = dummyFc;
        PlcflvcNew.Fc = dummyFc;
        PlcflvcNewInline.Fc = dummyFc;
        PgdMother.Fc = dummyFc;
        BkdMother.Fc = dummyFc;
        AfdMother.Fc = dummyFc;
        PgdFtn.Fc = dummyFc;
        BkdFtn.Fc = dummyFc;
        AfdFtn.Fc = dummyFc;
        PgdEdn.Fc = dummyFc;
        BkdEdn.Fc = dummyFc;
        AfdEdn.Fc = dummyFc;
        Afd.Fc = dummyFc;

        Unused.write(writer);
        PlcfpmiOld.write(writer);
        PlcfpmiOldInline.write(writer);
        PlcfpmiNew.write(writer);
        PlcfpmiNewInline.write(writer);
        PlcflvcOld.write(writer);
        PlcflvcOldInline.write(writer);
        PlcflvcNew.write(writer);
        PlcflvcNewInline.write(writer);
        PgdMother.write(writer);
        BkdMother.write(writer);
        AfdMother.write(writer);
        PgdFtn.write(writer);
        BkdFtn.write(writer);
        AfdFtn.write(writer);
        PgdEdn.write(writer);
        BkdEdn.write(writer);
        AfdEdn.write(writer);
        Afd.write(writer);
    }
}


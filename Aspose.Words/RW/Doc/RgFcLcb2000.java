// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/08/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Represents 2.5.7 FibRgFcLcb2000 structure.
/// </summary>
class RgFcLcb2000
{
    // ReSharper disable InconsistentNaming

    /// <summary>
    /// F/Lcb in the Table Stream of a PlcfTch which specifies a cache of table characters.
    /// </summary>
    /*final*/ FcLcb PlcfTch = new FcLcb();           // 0x382

    /// <summary>
    /// Fc/Lcb in the Table Stream of RmdThreading that specifies the data concerning the e-mail messages and their authors.
    /// </summary>
    /*final*/ FcLcb RmdfThreading = new FcLcb();    // 0x38a

    // 95

    /// <summary>
    /// Fc/Lcb in the Table Stream of a double-byte character Unicode string that specifies the message identifier.
    /// This value MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Mid = new FcLcb();               // 0x392

    /// <summary>
    /// Fc/Lcb in the Table Stream of a SttbRgtplc that specifies the bullet/numbering formats for a hybrid bulleted/numbered multi-level list.
    /// </summary>
    /*final*/ FcLcb SttbRgtplc = new FcLcb();        // 0x39a

    /// <summary>
    /// Fc/Lcb in the Table Stream of an MsoEnvelopeCLSID, which specifies the envelope data as specified by [MS-OSHARED] section 2.3.8.1.
    /// </summary>
    /*final*/ FcLcb MsoEnvelope = new FcLcb();       // 0x3a2

    /// <summary>
    /// Fc/Lcb in the Table Stream of a Plcflad that specifies the language auto-detect state of each text range.
    /// </summary>
    /*final*/ FcLcb PlcfLad = new FcLcb();           // 0x3aa

    /// <summary>
    /// Fc/Lcb in the Table Stream of a variable-length array with elements of type Dofrh which are records that support the frame set and list style features.
    /// </summary>
    FcLcb RgDofr = new FcLcb();                    // 0x3b2

    /// <summary>
    /// Fc/Lcb in the Table Stream of a PlfCosl which specifies the option set to use for a grammar checker.
    /// </summary>
    /*final*/ FcLcb Plcosl = new FcLcb();            // 0x3ba

    /// <summary>
    /// Fc/Lcb in the Table Stream of a PlcfcookieOld.
    /// </summary>
    /// <remarks>
    /// Word 2002, Office Word 2003, Office Word 2007, Word 2010, and Word 15 Technical Preview ignore this value.
    /// </remarks>
    /*final*/ FcLcb PlcfCookieOld = new FcLcb();     // 0x3c2

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated document page layout cache.
    /// </summary>
    /*final*/ FcLcb PgdMotherOld = new FcLcb();      // 0x3ca

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated document text flow break cache.
    /// </summary>
    /*final*/ FcLcb BkdMotherOld = new FcLcb();      // 0x3d2

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated footnote layout cache.
    /// </summary>
    /*final*/ FcLcb PgdFtnOld = new FcLcb();         // 0x3da

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated footnote text flow break cache.
    /// </summary>
    /*final*/ FcLcb BkdFtnOld = new FcLcb();         // 0x3e2

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated endnote layout cache.
    /// </summary>
    /*final*/ FcLcb PgdEdnOld = new FcLcb();         // 0x3ea

    /// <summary>
    /// Fc/Lcb in the Table Stream of the deprecated endnote text flow break cache.
    /// </summary>
    /*final*/ FcLcb BkdEdnOld = new FcLcb();         // 0x3f2

    // ReSharper restore InconsistentNaming

    /// <summary>
    /// Indicates that document has information concerning the e-mail messages and their authors.
    /// </summary>
    boolean hasThreadingData() { return RmdfThreading.Lcb > 0; }

    /// <summary>
    /// Indicates that document has bullet/numbering formats for a hybrid bulleted/numbered multi-level list
    /// </summary>
    boolean hasHybridListFormats() { return SttbRgtplc.Lcb > 0; }

    /// <summary>
    /// Indicates that document has MSO Envelope information.
    /// </summary>
    boolean hasEnvelopeData() { return MsoEnvelope.Lcb > 0; }

    boolean hasLanguageAutoDetectData() { return PlcfLad.Lcb > 0; }

    boolean hasGrammarCheckerOptions() { return Plcosl.Lcb > 0; }

    void read(BinaryReader reader) throws Exception
    {
        PlcfTch.read(reader);
        RmdfThreading.read(reader);
        // 95
        Mid.read(reader);
        SttbRgtplc.read(reader);
        MsoEnvelope.read(reader);
        PlcfLad.read(reader);
        RgDofr.read(reader);
        // 100
        Plcosl.read(reader);
        PlcfCookieOld.read(reader);
        PgdMotherOld.read(reader);
        BkdMotherOld.read(reader);
        PgdFtnOld.read(reader);
        // 105
        BkdFtnOld.read(reader);
        PgdEdnOld.read(reader);
        BkdEdnOld.read(reader);
    }

    void write(BinaryWriter writer) throws Exception
    {
        PlcfTch.write(writer);
        RmdfThreading.write(writer);
        // 95
        Mid.write(writer);
        SttbRgtplc.write(writer);
        MsoEnvelope.write(writer);
        PlcfLad.write(writer);
        RgDofr.write(writer);
        // 100
        Plcosl.write(writer);
        PlcfCookieOld.write(writer);
        PgdMotherOld.write(writer);
        BkdMotherOld.write(writer);
        PgdFtnOld.write(writer);
        // 105
        BkdFtnOld.write(writer);
        PgdEdnOld.write(writer);
        BkdEdnOld.write(writer);
    }

    /// <summary>
    /// Sets given Fc for certain RgFcLcb fields.
    /// </summary>
    void setDummyFc(int fc)
    {
        PlcfTch.Fc = fc; 
    }
}


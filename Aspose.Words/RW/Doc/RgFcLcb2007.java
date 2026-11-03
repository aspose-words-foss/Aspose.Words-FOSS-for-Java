// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements [MS-DOC] 2.5.10 FibRgFcLcb2007 structure.
/// </summary>
class RgFcLcb2007
{

    // ReSharper disable InconsistentNaming

    // Currently unused, hide in Release.

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Plcfmthd = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /// <remarks>
    /// Seems to be reserved for new kind of revisions "Move revision" introduced in DOCX.
    /// But it also seems that MS decided to reject all new features in DOC. At least Move revisions are not
    /// supported in DOC - Word converts them into Insert/Delete pair. Maybe it's need to be investigated.
    /// </remarks>
    /*final*/ FcLcb SttbfBkmkMoveFrom = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfBkfMoveFrom = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfBklMoveFrom = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb SttbfBkmkMoveTo = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfBkfMoveTo = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfBklMoveTo = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Unused1 = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Unused2 = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Unused3 = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb SttbfBkmkArto = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfBkfArto = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb PlcfBklArto = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb ArtoData = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Unused4 = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Unused5 = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value MUST be zero, and MUST be ignored.
    /// </summary>
    /*final*/ FcLcb Unused6 = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value SHOULD be zero, and MUST be ignored.
    /// </summary>
    /// <remarks>
    /// Neither Office Word 2007, Word 2010, nor Word 15 Technical Preview write 0 here, 
    /// but all three ignore this value when loading files.
    /// 
    /// Undocumented Table, Slot:181, Start:2F22, Length:C09
    /// This is a ZIP (an OPC Package) file that contains the theme for the document.
    /// 50 4B 03 04 14 00 06 00 08 00 00 00 21 00 82 8A BC 13 FA ...
    /// </remarks>
    FcLcb OssTheme = new FcLcb();

    /// <summary>
    /// Fc value is undefined and MUST be ignored.
    /// Lcb value SHOULD be zero, and MUST be ignored.
    /// </summary>
    /// <remarks>
    /// Neither Office Word 2007, Word 2010, nor Word 15 Technical Preview write 0 here, 
    /// but all three ignore this value when loading files.
    /// </remarks>
    FcLcb ColorSchemeMapping = new FcLcb();

    // ReSharper enable InconsistentNaming

    void read(BinaryReader reader) throws Exception
    {
        // Read this only for testing purposes.
        Plcfmthd.read(reader);
        SttbfBkmkMoveFrom.read(reader);
        PlcfBkfMoveFrom.read(reader);
        PlcfBklMoveFrom.read(reader);
        SttbfBkmkMoveTo.read(reader);
        PlcfBkfMoveTo.read(reader);
        PlcfBklMoveTo.read(reader);
        Unused1.read(reader);
        Unused2.read(reader);
        Unused3.read(reader);
        SttbfBkmkArto.read(reader);
        PlcfBkfArto.read(reader);
        PlcfBklArto.read(reader);
        ArtoData.read(reader);
        Unused4.read(reader);
        Unused5.read(reader);
        Unused6.read(reader);
        OssTheme.read(reader);
        ColorSchemeMapping.read(reader);
    }

    void write(BinaryWriter writer) throws Exception
    {
        Plcfmthd.write(writer);
        SttbfBkmkMoveFrom.write(writer);
        PlcfBkfMoveFrom.write(writer);
        PlcfBklMoveFrom.write(writer);
        SttbfBkmkMoveTo.write(writer);
        PlcfBkfMoveTo.write(writer);
        PlcfBklMoveTo.write(writer);
        Unused1.write(writer);
        Unused2.write(writer);
        Unused3.write(writer);
        SttbfBkmkArto.write(writer);
        PlcfBkfArto.write(writer);
        PlcfBklArto.write(writer);
        ArtoData.write(writer);
        Unused4.write(writer);
        Unused5.write(writer);
        Unused6.write(writer);
        OssTheme.write(writer);
        ColorSchemeMapping.write(writer);
    }
}


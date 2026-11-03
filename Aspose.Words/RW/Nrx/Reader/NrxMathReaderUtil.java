// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/16/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;


/// <summary>
/// Utility class contains common methods for Dml and Docx math readers.
/// In both Docx and Dml the same math syntax is used, but run properties and content are represented differently.
/// Reading of run properties and content must be implemented in derived classes.
/// </summary>
/*static*/ class NrxMathReaderUtil
{
	/* Simulation of static class by using private constructor */
	private NrxMathReaderUtil()
	{}

    /// <summary>
    /// Read root-level math object m:oMathPara
    /// </summary>
    static void readOMathPara(INrxMathReader reader) throws Exception
    {
        MathObjectOMathPara mathParent = new MathObjectOMathPara();
        reader.addAndPushMathContainer(mathParent);

        // Read elements.
        NrxXmlReader xmlReader = reader.getXmlReader();
        xmlReader.moveToElement();
        while (xmlReader.readChild("oMathPara"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"oMath"*/0:
                    readOMath(reader);
                    break;
                case /*"oMathParaPr"*/1:
                    readOMathParaPr(xmlReader, mathParent);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }
    
    /// <summary>
    /// Read root-level math object m:oMath
    /// </summary>
    static void readOMath(INrxMathReader reader) throws Exception
    {
        readArgument(reader, new MathObjectOMath(), "oMath");
    }
    
    private static void readOMathParaPr(NrxXmlReader xmlReader, MathObjectOMathPara oMathPara)
    {
        while (xmlReader.readChild("oMathParaPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"jc"*/2:
                    oMathPara.setJustification(DocxEnum.docxToMathJustification(xmlReader.readVal()));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// "default" argument for most math objects is "m:e", so create a special function for reading it.
    /// </summary>
    private static void readDefaultArgument(INrxMathReader reader) throws Exception
    {
        readArgument(reader, new MathObjectArgumentBase(MathObjectType.ARGUMENT), "e");
    }

    private static void readArgument(INrxMathReader reader, MathObject mathParent, String tagName) throws Exception
    {
        readArgument(reader, mathParent, tagName, true);    
    }
    
    /// <summary>
    /// Reads a collection of math objects that can act as arguments for current parent.
    /// </summary>
    private static void readArgument(INrxMathReader reader, MathObject mathParent, String tagName, boolean createParent) throws Exception
    {
        IMathRunPr rPr = reader.createRunPr();
        if (createParent)
            reader.addAndPushMathContainer(mathParent, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"acc"*/3: // Accent §22.1.2.
                    readMathObjectDefault(reader, new MathObjectAccent(), "acc");
                    break;
                case /*"bar"*/4: // (Bar) §22.1.2.7
                    readMathObjectDefault(reader, new MathObjectBar(), "bar");
                    break;
                case /*"borderBox"*/5: // Border-Box Object §22.1.2.11
                    readMathObjectDefault(reader, new MathObjectBorderBox(), "borderBox");
                    break;
                case /*"box"*/6: // Box Object §22.1.2.13
                    readMathObjectDefault(reader, new MathObjectBox(), "box");
                    break;
                case /*"d"*/7: // Delimiter Object §22.1.2.24
                    readMathObjectDefault(reader, new MathObjectDelimiter(), "d");
                    break;
                case /*"eqArr"*/8: // Array Object §22.1.2.34
                    readMathObjectDefault(reader, new MathObjectArray(), "eqArr");
                    break;
                case /*"f"*/9: // Fraction Object §22.1.2.36
                    readFraction(reader);
                    break;
                case /*"func"*/10: // Function Apply Object §22.1.2.39
                    readFunction(reader);
                    break;
                case /*"groupChr"*/11: // Group-Character Object §22.1.2.41
                    readMathObjectDefault(reader, new MathObjectGroupCharacter(), "groupChr");
                    break;
                case /*"limLow"*/12: // Lower-Limit Object §22.1.2.54
                    readLimUppLow(reader, new MathObjectLowerLimit(), "limLow");
                    break;
                case /*"limUpp"*/13: // Upper-Limit Object §22.1.2.56
                    readLimUppLow(reader, new MathObjectUpperLimit(), "limUpp");
                    break;
                case /*"m"*/14: // Matrix Object §22.1.2.60
                    readMatrix(reader);
                    break;
                case /*"nary"*/15: // n-ary Operator Object §22.1.2.70
                    readNAry(reader);
                    break;
                case /*"oMath"*/0: // Office Math §22.1.2.77
                case /*"oMathPara"*/16: // Office Math Paragraph §22.1.2.78
                    // resiliency                
                    // MS-OI29500, 22.1.2.77. (b) Word fails to open a file with an oMath element inside a math object argument 
                    // MS-OI29500, 22.1.2.78. (b) Word fails to open a file with oMathPara as a descendant of any math element.
                    readArgument(reader, mathParent, tagName, false);
                    break;
                case /*"phant"*/17: // (Phantom Object) §22.1.2.81
                    readMathObjectDefault(reader, new MathObjectPhantom(), "phant");
                    break;
                case /*"rad"*/18: // Radical Object §22.1.2.88
                    readRadical(reader);
                    break;
                case /*"sPre"*/19: // Pre-Sub-Superscript Object §22.1.2.99
                    readSubSupObjects(reader, new MathObjectPreSubSuperscript(), "sPre");
                    break;
                case /*"sSub"*/20: // Subscript Object §22.1.2.101
                    readSubSupObjects(reader, new MathObjectSubscript(), "sSub");
                    break;
                case /*"sSubSup"*/21: // Sub-Superscript Object §22.1.2.103
                    readSubSupObjects(reader, new MathObjectSubSuperscript(), "sSubSup");
                    break;
                case /*"sSup"*/22: // Superscript Object §22.1.2.105
                    readSubSupObjects(reader, new MathObjectSuperscript(), "sSup");
                    break;
                case /*"argPr"*/23: // Argument Properties §22.1.2.5
                    readArgPr(reader, mathParent);
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                {
                    // inline level elements can appear inside math, so we delegate reading for all unknown tags.
                    reader.readInlineChildren();
 
                    // WORDSNET-13720 Try decoding text of recently read run.
                    Run run = ms.as(reader.getDocumentReader().getCurContainer().getLastChild(), Run.class);
                    if (run != null)
                        tryDetectEncoding(run.getText(), reader.getDocumentReader());

                    break;
                }
            }
        }

        if (createParent) 
            reader.popMathContainer();
    }

    /// <summary>
    /// Read Math objects by "default". A lot of math object have similar structure and differ only in properties.
    /// So we read those by this default method delegating specific pr reads to specialized reading methods throught
    /// cases defined in <see cref="ReadMathObjectPr"/>
    /// </summary>
    private static void readMathObjectDefault(INrxMathReader reader, MathObject obj, String tagName) throws Exception
    {
        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(obj, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild(tagName))
        {
            switch(gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"e"*/25:
                    readDefaultArgument(reader);
                    break;
                default:
                    readMathObjectPr(reader, obj, rPr);
                    break;
            }
        }

        reader.popMathContainer();        
    }
    
    /// <summary>
    /// Reads properties of a given math object. Provides resiliency against wrong objPr tags.
    /// </summary>
    private static void readMathObjectPr(INrxMathReader reader, MathObject obj, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        if ((obj.getMathObjectType() == MathObjectType.ACCENT) && ("accPr".equals(xmlReader.getLocalName())))
        {
            readAccentPr(reader, (MathObjectAccent)obj, rPr);
        }
        else if ((obj.getMathObjectType() == MathObjectType.BAR) && ("barPr".equals(xmlReader.getLocalName())))
        {
            readBarPr(reader, (MathObjectBar)obj, rPr);
        }
        else if ((obj.getMathObjectType() == MathObjectType.BORDER_BOX) && ("borderBoxPr".equals(xmlReader.getLocalName())))
        {
            readBorderBoxPr(reader, (MathObjectBorderBox)obj, rPr);
        }
        else if ((obj.getMathObjectType() == MathObjectType.BOX) && ("boxPr".equals(xmlReader.getLocalName())))
        {
            readBoxPr(reader, (MathObjectBox)obj, rPr);
        }
        else if ((obj.getMathObjectType() == MathObjectType.DELIMITER) && ("dPr".equals(xmlReader.getLocalName())))
        {
            readDelimiterPr(reader, (MathObjectDelimiter)obj, rPr);
        }
        else if ((obj.getMathObjectType() == MathObjectType.ARRAY) && ("eqArrPr".equals(xmlReader.getLocalName())))
        {
            readArrayPr(reader, (MathObjectArray)obj, rPr);
        }
        else if ((obj.getMathObjectType() == MathObjectType.GROUP_CHARACTER) && ("groupChrPr".equals(xmlReader.getLocalName())))
        {
            readGroupChrPr(reader, (MathObjectGroupCharacter)obj, rPr);
        }
        else if ((obj.getMathObjectType() == MathObjectType.PHANTOM) && ("phantPr".equals(xmlReader.getLocalName())))
        {
            readPhantomPr(reader, (MathObjectPhantom)obj, rPr);
        }
        else
        {
            // objPr tag does not correspond to obj type, so skip this objPr tag for resiliency
            xmlReader.ignoreElement();
        }
    }

    private static void readFraction(INrxMathReader reader) throws Exception
    {
        MathObjectFraction fraction = new MathObjectFraction();

        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(fraction, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("f"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fPr"*/26:
                    readFractionPr(reader, fraction, rPr);
                    break;
                case /*"den"*/27:
                    readArgument(reader, new MathObjectArgumentBase(MathObjectType.DENOMINATOR), "den");
                    break;
                case /*"num"*/28:
                    readArgument(reader, new MathObjectArgumentBase(MathObjectType.NUMERATOR), "num");
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }

    private static void readFractionPr(INrxMathReader reader, MathObjectFraction f, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("fPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"type"*/29:
                    f.setFractionType(DocxEnum.docxToMathFractionType(xmlReader.readVal()));
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readFunction(INrxMathReader reader) throws Exception
    {
        MathObjectFunction function = new MathObjectFunction();
        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(function, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("func"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"funcPr"*/30: // func properties
                    readDefaultPr(reader, rPr, "funcPr");
                    break;
                case /*"fName"*/31:
                    readArgument(reader, new MathObjectArgumentBase(MathObjectType.FUNCTION_NAME), "fName");
                    break;
                case /*"e"*/25:
                    readDefaultArgument(reader);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }
    
    /// <summary>
    /// Common reader function for <see cref="MathObjectLowerLimit"/> and <see cref="MathObjectUpperLimit"/>.
    /// </summary>
    private static void readLimUppLow(INrxMathReader reader, MathObject obj, String objTag) throws Exception
    {
        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(obj, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild(objTag))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"lim"*/32:
                    readArgument(reader, new MathObjectArgumentBase(MathObjectType.LIMIT), "lim");
                    break;
                case /*"e"*/25:
                    readDefaultArgument(reader);
                    break;
                case /*"limUppPr"*/33:
                    readDefaultPr(reader, rPr, "limUppPr");
                    break;
                case /*"limLowPr"*/34:
                    readDefaultPr(reader, rPr, "limLowPr");
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }

    private static void readMatrix(INrxMathReader reader) throws Exception
    {
        MathObjectMatrix m = new MathObjectMatrix();
        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(m, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("m"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"mr"*/35:
                    readMatrixRow(reader, m.getColumnPrCollection());
                    break;
                case /*"mPr"*/36:
                    readMatrixPr(reader, m, rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }

    private static void readMatrixRow(INrxMathReader reader, MathMatrixColumnPrCollection columns) throws Exception
    {
        reader.addAndPushMathContainer(new MathObjectMatrixRow());
        NrxXmlReader xmlReader = reader.getXmlReader();
        int actualColumnCount = 0;
        while (xmlReader.readChild("mr"))
        {
            ++actualColumnCount;
            if ("e".equals(xmlReader.getLocalName()))
            {
                readDefaultArgument(reader);
            }
            else
                xmlReader.ignoreElement();
        }
        // WORDSNET-17185 Correct number of columns in case it is specified incorrectly in matrix properties
        // (or is not specified at all).
        if (actualColumnCount > columns.getCount())
        {
            columns.add(new MathMatrixColumnPr(), actualColumnCount - columns.getCount());
        }

        reader.popMathContainer();
    }

    private static void readMatrixPr(INrxMathReader reader, MathObjectMatrix m, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("mPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"baseJc"*/37:
                    m.setBaseJustification(readBaseJustification(xmlReader, reader.getDocumentReader().getComplianceInfo()));
                    break;
                case /*"cGp"*/38:
                    m.setColumnGap(xmlReader.readIntVal());
                    break;
                case /*"cGpRule"*/39:
                    m.setColumnSpacingRule(readGapSpacingRule(xmlReader));
                    break;
                case /*"cSp"*/40:
                    m.setMinimumColumnWidth(xmlReader.readIntVal());
                    break;
                case /*"mcs"*/41:
                    readMatrixColumns(reader, m.getColumnPrCollection());
                    break;
                case /*"plcHide"*/42:
                    m.isHidePlaceholders(xmlReader.readBoolVal());
                    break;
                case /*"rSp"*/43:
                    m.setRowSpacing(xmlReader.readIntVal());
                    break;
                case /*"rSpRule"*/44:
                    m.setRowSpacingRule(readGapSpacingRule(xmlReader));
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readMatrixColumns(INrxMathReader reader, MathMatrixColumnPrCollection columns)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("mcs"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"mc"*/45:
                    readMatrixColumn(reader, columns);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readMatrixColumn(INrxMathReader reader, MathMatrixColumnPrCollection columns)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("mc"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"mcPr"*/46:
                    readMatrixColumnPr(reader, columns);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readMatrixColumnPr(INrxMathReader reader, MathMatrixColumnPrCollection columns)
    {
        int nColumns = 1;
        MathMatrixColumnPr mcPr = new MathMatrixColumnPr();

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("mcPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"count"*/47:
                    nColumns = readInt255(xmlReader);
                    break;
                case /*"mcJc"*/48:
                    mcPr.setHorizontalAlignment(StyleConvertUtil.xmlToHorizontalAlignment(xmlReader.readVal()));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
        
        columns.add(mcPr, nColumns);
    }

    private static void readNAry(INrxMathReader reader) throws Exception
    {
        MathObjectNAry nary = new MathObjectNAry();
        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(nary, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("nary"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"e"*/25:
                    readDefaultArgument(reader);
                    break;
                case /*"naryPr"*/49:
                    readNAryPr(reader, nary, rPr);
                    break;
                case /*"sub"*/50:
                    readArgument(reader, new MathObjectArgumentBase(MathObjectType.SUBSCRIPT_PART), "sub");
                    break;
                case /*"sup"*/51:
                    readArgument(reader, new MathObjectArgumentBase(MathObjectType.SUPERSCRIPT_PART), "sup");
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }

    private static void readNAryPr(INrxMathReader reader, MathObjectNAry nary, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("naryPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"chr"*/52:
                    nary.setCharacter(readChar(reader));
                    break;
                case /*"grow"*/53:
                    nary.setGrowToMatchOperand(xmlReader.readBoolVal());
                    break;
                case /*"limLoc"*/54:
                    nary.setLimitLocation(DocxEnum.docxToMathLimitLocation(xmlReader.readVal()));
                    break;
                case /*"subHide"*/55:
                    nary.isHideSubscript(xmlReader.readBoolVal());
                    break;
                case /*"supHide"*/56:
                    nary.isHideSuperscript(xmlReader.readBoolVal());
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readRadical(INrxMathReader reader) throws Exception
    {
        MathObjectRadical rad = new MathObjectRadical();
        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(rad, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("rad"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"e"*/25:
                    readDefaultArgument(reader);
                    break;
                case /*"deg"*/57:
                    readArgument(reader, new MathObjectArgumentBase(MathObjectType.DEGREE), "deg");
                    break;
                case /*"radPr"*/58:
                    readRadicalPr(reader, rad, rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }

    private static void readRadicalPr(INrxMathReader reader, MathObjectRadical rad, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("radPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"degHide"*/59:
                    rad.isHideDegree(xmlReader.readBoolVal());
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }
    
    /// <summary>
    /// Common reader function for the following quasi-similar objects.
    /// "sPre": (Pre-Sub-Superscript Object) §22.1.2.99
    /// "sSub": (Subscript Object) §22.1.2.101
    /// "sSubSup": (Sub-Superscript Object) §22.1.2.103       
    /// "sSup": (Superscript Object) §22.1.2.105
    /// </summary>
    private static void readSubSupObjects(INrxMathReader reader, MathObject obj, String objTag) throws Exception
    {
        IMathRunPr rPr = reader.createRunPr();
        reader.addAndPushMathContainer(obj, rPr);

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild(objTag))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"e"*/25:
                    readDefaultArgument(reader);
                    break;
                case /*"sup"*/51:
                    {
                        if (obj.getMathObjectType() == MathObjectType.SUBSCRIPT) // subscripts shouldn't have "sup" objTag inside
                            xmlReader.ignoreElement();
                        else
                            readArgument(reader, new MathObjectArgumentBase(MathObjectType.SUPERSCRIPT_PART), "sup");
                        break;
                    }
                case /*"sub"*/50:
                    {
                        if (obj.getMathObjectType() == MathObjectType.SUPERSCRIPT) // superscripts shouldn't have "sub" objTag inside
                            xmlReader.ignoreElement();
                        else
                            readArgument(reader, new MathObjectArgumentBase(MathObjectType.SUBSCRIPT_PART), "sub");
                        break;
                    }
                case /*"sPrePr"*/60:
                    readDefaultPr(reader, rPr, "sPrePr");
                    break;
                case /*"sSubPr"*/61:
                    readDefaultPr(reader, rPr, "sSubPr");
                    break;
                case /*"sSubSupPr"*/62:
                    {
                        if (obj.getMathObjectType() == MathObjectType.SUB_SUPERSCRIPT) // SubSuperscript is the only obj that has non-default pr.
                            readSubSupPr(reader, (MathObjectSubSuperscript)obj, rPr);
                        else
                            xmlReader.ignoreElement();
                        break;
                    }
                case /*"sSupPr"*/63:
                    readDefaultPr(reader, rPr, "sSupPr");
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.popMathContainer();
    }

    private static void readSubSupPr(INrxMathReader reader, MathObjectSubSuperscript subSuperscript, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("sSubSupPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"alnScr"*/64:
                    subSuperscript.isAlignScripts(xmlReader.readBoolVal());
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readArgPr(INrxMathReader reader, MathObject mathParent)
    {
        int argSz = MathObjectArgumentBase.DEFAULT_ARGUMENT_SIZE;
        
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("argPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"argSz"*/65:
                    argSz = xmlReader.readIntVal();
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        if ((argSz != MathObjectArgumentBase.DEFAULT_ARGUMENT_SIZE)  && (mathParent instanceof MathObjectArgumentBase))
            ((MathObjectArgumentBase)mathParent).setArgumentSize(argSz);
    }
    
    /// <summary>
    /// Reads a default "almost-empty" MathObject pr that should contain only one child "m:ctrlPr"
    /// </summary>
    private static void readDefaultPr(INrxMathReader reader, IMathRunPr rPr, String prTag) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild(prTag))
        {
            if ("ctrlPr".equals(xmlReader.getLocalName()))
                reader.readCtrlPr(rPr);
            else
                xmlReader.ignoreElement();
        }

    }

    private static void readAccentPr(INrxMathReader reader, MathObjectAccent acc, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("accPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"chr"*/52:
                    acc.setCharacter(readChar(reader, true));
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readBarPr(INrxMathReader reader, MathObjectBar bar, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        // WORDSNET-6085 There was a mistake in the code, instead of 'barPr' method looked for 'accPr'.
        while (xmlReader.readChild("barPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"pos"*/66:
                    bar.setPosition(readPositionVal(xmlReader));
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readBorderBoxPr(INrxMathReader reader, MathObjectBorderBox bbox, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("borderBoxPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"hideBot"*/67:
                    bbox.setHideBottomEdge(xmlReader.readBoolVal());
                    break;
                case /*"hideLeft"*/68:
                    bbox.setHideLeftEdge(xmlReader.readBoolVal());
                    break;
                case /*"hideRight"*/69:
                    bbox.setHideRightEdge(xmlReader.readBoolVal());
                    break;
                case /*"hideTop"*/70:
                    bbox.setHideTopEdge(xmlReader.readBoolVal());
                    break;
                case /*"strikeBLTR"*/71:
                    bbox.setStrikeBLTR(xmlReader.readBoolVal());
                    break;
                case /*"strikeH"*/72:
                    bbox.setStrikeH(xmlReader.readBoolVal());
                    break;
                case /*"strikeTLBR"*/73:
                    bbox.setStrikeTLBR(xmlReader.readBoolVal());
                    break;
                case /*"strikeV"*/74:
                    bbox.setStrikeV(xmlReader.readBoolVal());
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readBoxPr(INrxMathReader reader, MathObjectBox box, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("boxPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"aln"*/75:
                    box.isAlignmentPoint(xmlReader.readBoolVal());
                    break;
                case /*"brk"*/76:
                    box.setLineBreak(readMathLineBreak(xmlReader));
                    break;
                case /*"diff"*/77:
                    box.isDifferential(xmlReader.readBoolVal());
                    break;
                case /*"noBreak"*/78:
                    box.isUnbreakable(xmlReader.readBoolVal());
                    break;
                case /*"opEmu"*/79:
                    box.isOperatorEmulation(xmlReader.readBoolVal());
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readDelimiterPr(INrxMathReader reader, MathObjectDelimiter delimiter, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("dPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"begChr"*/80:
                    delimiter.setBeginningCharacter(readChar(reader));
                    break;
                case /*"endChr"*/81:
                    delimiter.setEndingCharacter(readChar(reader));
                    break;
                case /*"grow"*/53:
                    delimiter.setGrowToMatchOperand(xmlReader.readBoolVal());
                    break;
                case /*"sepChr"*/82:
                    delimiter.setSeparatorCharacter(readChar(reader));
                    break;
                case /*"shp"*/83:
                    delimiter.setDelimiterShape(DocxEnum.docxToMathDelimiterShape(xmlReader.readVal()));
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readArrayPr(INrxMathReader reader, MathObjectArray array, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("eqArrPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"baseJc"*/37:
                    array.setBaseJustification(readBaseJustification(xmlReader, 
                        reader.getDocumentReader().getComplianceInfo()));
                    break;
                case /*"maxDist"*/84:
                    array.isMaximumDistribution(xmlReader.readBoolVal());
                    break;
                case /*"objDist"*/85:
                    array.isObjectDistribution(xmlReader.readBoolVal());
                    break;
                case /*"rSp"*/43:
                    array.setRowSpacing(xmlReader.readIntVal());
                    break;
                case /*"rSpRule"*/44:
                    array.setRowSpacingRule(readGapSpacingRule(xmlReader));
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readGroupChrPr(INrxMathReader reader, MathObjectGroupCharacter groupChar, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("groupChrPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"chr"*/52:
                    groupChar.setCharacter(readChar(reader));
                    break;
                case /*"pos"*/66:
                    groupChar.setPosition(readPositionVal(xmlReader));
                    break;
                case /*"vertJc"*/86:
                    groupChar.setVerticalJustification(DocxEnum.docxToMathVerticalJustificationType(xmlReader.readVal()));
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readPhantomPr(INrxMathReader reader, MathObjectPhantom phantom, IMathRunPr rPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("phantPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"show"*/87:
                    phantom.isShown(xmlReader.readBoolVal());
                    break;
                case /*"transp"*/88:
                    phantom.isTransparent(xmlReader.readBoolVal());
                    break;
                case /*"zeroAsc"*/89:
                    phantom.isZeroAscent(xmlReader.readBoolVal());
                    break;
                case /*"zeroDesc"*/90:
                    phantom.isZeroDescent(xmlReader.readBoolVal());
                    break;
                case /*"zeroWid"*/91:
                    phantom.isZeroWidth(xmlReader.readBoolVal());
                    break;
                case /*"ctrlPr"*/24:
                    reader.readCtrlPr(rPr);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Read math specific type brk (Break), see. OOXML ISO29500 ch. 22.1.2.15
    /// </summary>
    /// <remarks>
    /// Semantically this function is better suited as member <see cref="NrxXmlReader"/>,
    /// but it is only used for math reading, so we hide it here to reduce complexity.
    /// </remarks>
    static MathLineBreak readMathLineBreak(NrxXmlReader reader)
    {
        MathLineBreak result = new MathLineBreak();

        String val = reader.readAttribute("alnAt", null);
        result.setAlignment((StringUtil.hasChars(val)) ? FormatterPal.xmlToInt(val) : 0);

        return result;
    }
    
    /// <summary>
    /// Read val value of ST_Integer255 type.
    /// </summary>
    private static int readInt255(NrxXmlReader reader)
    {
        int val = reader.readIntVal();
        return (val < 1) ? 1 : (val > 255) ? 255 : val;
    }

    /// <summary>
    /// Read m:pos val contents. (ST_TopBot OOXML type)
    /// </summary>
    /// <remarks>
    /// Semantically this function is better suited as member <see cref="NrxXmlReader"/>,
    /// but it is only used for math reading, so we hide it here to reduce complexity.
    /// </remarks>
    private static /*MathPosition*/int readPositionVal(NrxXmlReader xmlReader)
    {
        return DocxEnum.docxToMathPositionType(xmlReader.readVal());
    }
    
    /// <summary>
    /// Read value of (CT_SpacingRule) Office Math type.
    /// </summary>
    /// <remarks>
    /// Semantically this function might better suited as member <see cref="NrxXmlReader"/>,
    /// but it is only used for math reading, so we hide it here to reduce complexity.
    /// </remarks>
    private static /*MathSpacingRule*/int readGapSpacingRule(NrxXmlReader xmlReader)
    {
        int val = xmlReader.readIntVal();
        return (/*MathSpacingRule*/int)((val < 0)? 0 : (val > 4)? 4 : val);
    }
    
    /// <summary>
    /// Read value of (CT_YAlign) type.
    /// </summary>
    private static /*MathBaseJustification*/int readBaseJustification(NrxXmlReader xmlReader, 
        OoxmlComplianceInfo complianceInfo)
    {
        return DocxEnum.docxToMathBaseJustification(xmlReader.readVal(), complianceInfo);
    }

    /// <summary>
    /// Reads 'val' attribute of character type. If there is more than 1 char, then tries to decode it.
    /// MS Word encodes equation XML to UTF-8 twice. So, we may need additional decoding to single byte encoding
    /// that was default in Windows on document creation and then to UTF-8.
    /// </summary>
    private static char readChar(INrxMathReader reader)
    {
        return readChar(reader, false);
    }

    /// <summary>
    /// Reads 'val' attribute of character type. If there is more than 1 char, then tries decoding it.
    /// MS Word encodes equation XML to UTF-8 twice. So, we may need additional decoding to single byte encoding
    /// that was default in Windows on document creation and then to UTF-8.
    /// For accent we do more reliable check of result.
    /// </summary>
    private static char readChar(INrxMathReader reader, boolean isAccent)
    {
        String result = reader.getXmlReader().readVal();

        if (result.length() > 1)
            result = tryDecodingThroughAscii(result, isAccent, reader.getDocumentReader());

        return (result.length() > 0) ? result.charAt(0) : MathObject.EMPTY_CHARACTER;
    }

    /// <summary>
    /// Tries to detect encoding of text.
    /// </summary>
    private static void tryDetectEncoding(String val, NrxDocumentReaderBase docReader)
    {
        // WORDSNET-14155 Currently process only the first two characters from the string.
        // It is not good fix, see comment for OfficeMathToShapeConverter.EquationXmlToOfficeMath method.
        if (val.length() < 2)
            return;

        tryDecodingThroughAscii(val, false, docReader);
    }

    /// <summary>
    /// Tries to perform additional decoding of read string through converting to single byte encoding 
    /// and then to UTF-8. Does it for all Windows encodings until correct result is got.
    /// </summary>
    private static String tryDecodingThroughAscii(String charValue, boolean isAccent, NrxDocumentReaderBase docReader)
    {
        ArrayList<Integer> codePageOrder = new ArrayList<Integer>();
        msArrayList.addRange(codePageOrder, new int[] { 1252, 1250, 1251, 1253, 1254, 1255, 1256, 1257, 1258, 874 });

        // Different computers can use different encodings as the default, and the default encoding can even change on 
        // a single computer. In addition, the encoding returned by the Default property uses 
        // best fit fallback to map unsupported characters to characters supported by the code page. For these two reasons, 
        // using the default encoding is generally not recommended.
        // Let's try UTF-8 (Encoding.UTF8) which supports all of the characters that the framework can handle.
        moveToFirst(codePageOrder, Encoding.getUTF8().getCodePage());
        if (docReader.getDetectedEncoding() != null)
            moveToFirst(codePageOrder, docReader.getDetectedEncoding().getCodePage());

        for (int codePage : codePageOrder)
        {
            String result = decodeThroughAscii(charValue, codePage);
            if (!msString.isNullOrEmpty(result))
            {
                // For accent we do more checks and get more reliable result, so overwrite the encoding.
                if (docReader.getDetectedEncoding() == null || (isAccent && isValidAccent(result)))
                {
                    docReader.setDetectedEncoding(Encoding.getEncoding(codePage));
                }

                return result;
            }
        }

        return charValue;
    }

    /// <summary>
    /// Moves the specified value of array list that contains integer values to the index 0.
    /// </summary>
    private static void moveToFirst(ArrayList<Integer> listOfInt, int value)
    {
        int i = listOfInt.indexOf(value);
        if (i > 0)
        {
            listOfInt.remove(i);
            listOfInt.add(0, value);
        }
    }

    /// <summary>
    /// Decodes string by converting it to single byte encoding and then to UTF-8 like MS Word does
    /// for equation XML.
    /// </summary>
    private static String decodeThroughAscii(String charValue, int codePage)
    {
        try
        {
            DecoderErrorFallBack decoderErrorFallback = new DecoderErrorFallBack();
            EncoderErrorFallBack encoderErrorFallback = new EncoderErrorFallBack();
            Encoding encoding = Encoding.getEncoding(codePage, encoderErrorFallback, decoderErrorFallback);
            byte[] asciiBytes = encoding.getBytes(charValue);

            Encoding utf8Encoding = Encoding.getEncoding(65001, encoderErrorFallback, decoderErrorFallback);
            String result = utf8Encoding.getString(asciiBytes);
            return (!decoderErrorFallback.isError() && !encoderErrorFallback.isError())? result : null;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /// <summary>
    /// Returns <c>true</c> if the specified value presents a valid accent.
    /// </summary>
    private static boolean isValidAccent(String value)
    {
        return ((value != null) && MathObjectAccent.isValidAccent(value.charAt(0)));
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"oMath",
		"oMathParaPr",
		"jc",
		"acc",
		"bar",
		"borderBox",
		"box",
		"d",
		"eqArr",
		"f",
		"func",
		"groupChr",
		"limLow",
		"limUpp",
		"m",
		"nary",
		"oMathPara",
		"phant",
		"rad",
		"sPre",
		"sSub",
		"sSubSup",
		"sSup",
		"argPr",
		"ctrlPr",
		"e",
		"fPr",
		"den",
		"num",
		"type",
		"funcPr",
		"fName",
		"lim",
		"limUppPr",
		"limLowPr",
		"mr",
		"mPr",
		"baseJc",
		"cGp",
		"cGpRule",
		"cSp",
		"mcs",
		"plcHide",
		"rSp",
		"rSpRule",
		"mc",
		"mcPr",
		"count",
		"mcJc",
		"naryPr",
		"sub",
		"sup",
		"chr",
		"grow",
		"limLoc",
		"subHide",
		"supHide",
		"deg",
		"radPr",
		"degHide",
		"sPrePr",
		"sSubPr",
		"sSubSupPr",
		"sSupPr",
		"alnScr",
		"argSz",
		"pos",
		"hideBot",
		"hideLeft",
		"hideRight",
		"hideTop",
		"strikeBLTR",
		"strikeH",
		"strikeTLBR",
		"strikeV",
		"aln",
		"brk",
		"diff",
		"noBreak",
		"opEmu",
		"begChr",
		"endChr",
		"sepChr",
		"shp",
		"maxDist",
		"objDist",
		"vertJc",
		"show",
		"transp",
		"zeroAsc",
		"zeroDesc",
		"zeroWid"
	);

}


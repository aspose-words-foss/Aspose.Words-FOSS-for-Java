// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Base class, helps to read m:oMath and m:oMathPara objects and all possible children into a math tree.
/// </summary>
abstract class NrxMathReaderBase implements INrxMathReader
{
    protected NrxMathReaderBase(NrxDocumentReaderBase reader,
        NrxRunPrReaderBase runPrReader,
        NrxInlineReaderBase inlineReader)
    {
        Debug.assertTrue(reader != null);
        Debug.assertTrue(runPrReader != null);
        Debug.assertTrue(inlineReader != null);
        mReader = reader;
        mRunPrReader = runPrReader;
        mInlineReader = inlineReader;
    }

    public void addAndPushMathContainer(MathObject mathObject)
    {
        getDocumentReader().addAndPushContainer(new OfficeMath(getDocumentReader().getDocument(), mathObject));
    }

    public void addAndPushMathContainer(MathObject mathObject, IMathRunPr rPr)
    {
        RunPr runPr = ms.as(rPr, RunPr.class);
        if (runPr != null)
        {
            OfficeMath node = new OfficeMath(getDocumentReader().getDocument(), mathObject, runPr);
            getDocumentReader().addAndPushContainer(node);
            getDocumentReader().StoryRevisionStack.apply(runPr, node);
        }
    }

    public void popMathContainer()
    {
        getDocumentReader().popContainer(NodeType.OFFICE_MATH);
    }

    public IMathRunPr createRunPr()
    {
        return new RunPr();
    }

    public void readInlineChildren() throws Exception
    {
        mInlineReader.readChild(getDocumentReader());
    }

    public void readCtrlPr(IMathRunPr rPr) throws Exception
    {
        while (getXmlReader().readChild("ctrlPr"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"rPr"*/0:
                    mRunPrReader.read(getDocumentReader(), (RunPr)rPr);
                    break;
                case /*"annotation"*/1:
                case /*"content"*/2:
                   // WORDSNET-12224 Ignore 'annotation' elements while read EquationXML.
                    break;
                default:
                    if (!readFormatSpecificAttribute(getXmlReader().getLocalName(), rPr))
                        getXmlReader().ignoreElement();
                    break;
            }
        }
    }

    protected abstract boolean readFormatSpecificAttribute(String attrName, IMathRunPr rPr) throws Exception;

    public NrxDocumentReaderBase getDocumentReader() { return mReader; }

    public NrxXmlReader getXmlReader() { return mReader.getXmlReader(); }

    private /*final*/ NrxDocumentReaderBase mReader;
    private /*final*/ NrxRunPrReaderBase mRunPrReader;
    private /*final*/ NrxInlineReaderBase mInlineReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"rPr",
		"annotation",
		"content"
	);

}


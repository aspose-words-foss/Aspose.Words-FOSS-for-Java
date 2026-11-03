// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/16/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;


class DmlTextMathReader extends DmlReaderBase implements INrxMathReader
{
    private DmlTextMathReader(NrxDocumentReaderBase reader)
    {
        mReader = reader;
    }

    static DmlTextMath read(NrxDocumentReaderBase reader) throws Exception
    {
        DmlTextMathReader mathReader = new DmlTextMathReader(reader);
        mathReader.readCore();
        // Return empty MathObjectOMathPara if 'm' tag is empty.
        DmlTextMath rootElement = (mathReader.mRootMath == null)
            ? new DmlTextMath(new MathObjectOMathPara())
            : mathReader.mRootMath;

        rootElement.isRootElement(true);

        return rootElement;
    }

    private void readCore() throws Exception
    {
        while (getXmlReader().readChild("m"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"oMath"*/0:
                    NrxMathReaderUtil.readOMath(this);
                    break;
                case /*"oMathPara"*/1:
                    NrxMathReaderUtil.readOMathPara(this);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }


    public void addAndPushMathContainer(MathObject mathObject)
    {
        addAndPushMathContainer(mathObject, new DmlRunProperties());
    }

    public void addAndPushMathContainer(MathObject mathObject, IMathRunPr rPr)
    {
        DmlTextMath math = new DmlTextMath(mathObject, (DmlRunProperties)rPr);

        if (getCurrentMath() == null)
            mRootMath = math;
        else
            getCurrentMath().addElement(math);

        mMathStack.push(math);
    }

    public void popMathContainer()
    {
        mMathStack.pop();
    }

    public IMathRunPr createRunPr()
    {
        return new DmlRunProperties();
    }

    public void readInlineChildren() throws Exception
    {
        DmlParagraphTextElementBase inline = DmlTextShapeReader.readInline((DocxDocumentReaderBase)getDocumentReader());
        if (inline != null)
            getCurrentMath().addElement(inline);
    }

    public void readCtrlPr(IMathRunPr rPr) throws Exception
    {
        while (getXmlReader().readChild("ctrlPr"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"rPr"*/2:
                    DmlTextShapeReader.readRunProperties((DmlRunProperties)rPr, (DocxDocumentReaderBase)getDocumentReader());
                    break;
                default:
                    getXmlReader().ignoreElement();
                    break;
            }
        }
    }

    public NrxDocumentReaderBase getDocumentReader() { return mReader; }

    public NrxXmlReader getXmlReader() { return mReader.getXmlReader(); }

    private DmlTextMath getCurrentMath() { return StackExtension.top(mMathStack); }

    private /*final*/ NrxDocumentReaderBase mReader;
    private /*final*/ msStack<DmlTextMath> mMathStack = new msStack<DmlTextMath>();
    private DmlTextMath mRootMath;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"oMath",
		"oMathPara",
		"rPr"
	);

}


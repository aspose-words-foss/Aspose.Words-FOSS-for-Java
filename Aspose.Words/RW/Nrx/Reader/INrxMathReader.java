// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/16/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



interface INrxMathReader
{
    public void addAndPushMathContainer(MathObject mathObject);
    public void addAndPushMathContainer(MathObject mathObject, IMathRunPr rPr);
    public void popMathContainer() throws Exception;
    public IMathRunPr createRunPr();
    public void readInlineChildren() throws Exception;
    public void readCtrlPr(IMathRunPr rPr) throws Exception;

    public NrxDocumentReaderBase getDocumentReader();
    public NrxXmlReader getXmlReader();
}


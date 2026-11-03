// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2016 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Interface for visiting parts of pseudo-elements' content.
/// </summary>
interface IPseudoElementContentPartVisitor
{
    public void visitAttr(String attributeName);
    public void visitCounter(String counterName, /*NumberStyle*/int counterStyle);
    public void visitCounters(String counterName, String separator, /*NumberStyle*/int counterStyle);
    public void visitText(String text);
}


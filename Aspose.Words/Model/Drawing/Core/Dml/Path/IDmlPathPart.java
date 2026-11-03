// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



interface IDmlPathPart
{
    public /*DmlPathPartType*/int getPathPartType();

    public IDmlPathPart deepClone();

    public void write(NrxXmlBuilder builder);
}


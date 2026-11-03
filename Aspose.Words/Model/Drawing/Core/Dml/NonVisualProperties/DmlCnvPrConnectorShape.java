// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.2.2.4 cNvCxnSpPr (Non-Visual Connector Shape Drawing Properties)
/// This element specifies the non-visual drawing properties for a connector shape.
/// </summary>
class DmlCnvPrConnectorShape extends DmlCnvPrBase
{
    /*override*/ /*DmlNvHolder*/int getHolder() { return DmlNvHolder.CONNECTION_SHAPE; }

    DmlConnection getConnectionEnd() { return mConnectionEnd; }
    void setConnectionEnd(DmlConnection value) { mConnectionEnd = value; }

    DmlConnection getConnectionStart() { return mConnectionStart; }
    void setConnectionStart(DmlConnection value) { mConnectionStart = value; }

    private DmlConnection mConnectionEnd;
    private DmlConnection mConnectionStart;
}


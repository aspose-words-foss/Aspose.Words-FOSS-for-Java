// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a close path part.
/// </summary>
/// <remarks>
/// 20.1.9.6 close (Close Shape Path)
/// This element specifies the ending of a series of lines and curves in the creation path of a
/// custom geometric shape. When this element is encountered, the generating application should
/// consider the corresponding path closed. That is, any further lines or curves that follow this
/// element should be ignored.
/// </remarks>
class DmlClose implements IDmlPathPart, Cloneable
{
    public /*DmlPathPartType*/int getPathPartType() { return DmlPathPartType.CLOSE; }

    /// <summary>
    /// Clones this instance of <see cref="DmlClose"/>.
    /// </summary>
    public IDmlPathPart deepClone()
    {
        return (DmlClose)memberwiseClone();
    }

    public void write(NrxXmlBuilder builder)
    {
        builder.writeEmptyElement("a:close");
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}


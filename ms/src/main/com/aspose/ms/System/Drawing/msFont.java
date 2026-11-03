package com.aspose.ms.System.Drawing;

import java.awt.Font;

/**
 * Static class with extensions methods to emulate .Net Font .ctors in Java using float size.
 */
public final class msFont
{
    private msFont()
    {
    }

    public static Font newFont(String requestedName, float sizeInPoints, /*FontStyle*/int requestedStyle)
    {
        Font requestedFont = new Font(requestedName, requestedStyle, 1);
        return requestedFont.deriveFont(sizeInPoints);
    }

    public static Font newFont(String requestedName, float sizeInPoints)
    {
        Font requestedFont = new Font(requestedName, Font.PLAIN, 1);
        return requestedFont.deriveFont(sizeInPoints);
    }
}


package com.aspose.ms.System.Drawing;

public final class FontStyle
{
	public static final int REGULAR = 0;
    public static final int BOLD = 1;
    public static final int ITALIC = 2;
	public static final int UNDERLINE = 4;
    public static final int STRIKEOUT = 8;

    public static String getName(int fontStyle)
    {
        switch (fontStyle)
        {
            case 0: return "Regular";
            case 1: return "Bold";
            case 2: return "Italic";
            case 4: return "Underline";
            case 8: return "Strikeout";
            case 3: return "BoldItalic";
            case 12: return "UnderlineStrikeout";
        }
        return "";
    }
}

package com.aspose.ms.System.Drawing;

import java.awt.*;

/**
 * Extensions methods that are available on .net Color, but not available on Java Color.
 */
public class msColor
{
    // We convert System.Drawing.Color.Empty into this value during autoporting.
	public static final Color Empty = new Color(0, 0, 0, 0);

    public static Color ctor()
    {
        return Empty;
    }

    public static Color fromName(String name)
    {
        KnownColor knownColor = KnownColor.get(name);
        int argb = knownColor.value();
        return new Color(argb, true);
    }

    public static boolean isEmpty(Color value)
    {
        return (value.getRGB() == 0);
    }

    public static Color getAqua()
    {
        return new Color(KnownColor.Aqua.value());
    }
    public static Color getAliceBlue()
    {
        return new Color(KnownColor.AliceBlue.value());
    }
    public static Color getAzure()
    {
        return new Color(KnownColor.Azure.value());
    }

    public static Color getBeige()
    {
        return new Color(KnownColor.Beige.value());
    }
    public static Color getBlueViolet()
    {
        return new Color(KnownColor.BlueViolet.value());
    }
    public static Color getBrown()
    {
        return new Color(KnownColor.Brown.value());
    }
    public static Color getBisque()
    {
        return new Color(KnownColor.Bisque.value());
    }

    public static Color getChocolate()
    {
        return new Color(KnownColor.Chocolate.value());
    }
    public static Color getChartreuse()
    {
        return new Color(KnownColor.Chartreuse.value());
    }
    public static Color getCrimson()
    {
        return new Color(KnownColor.Crimson.value());
    }

    public static Color getDarkBlue()
    {
        return new Color(KnownColor.DarkBlue.value());
    }
    public static Color getDarkGreen()
    {
        return new Color(KnownColor.DarkGreen.value());
    }
    public static Color getDarkGray()
    {
        return new Color(KnownColor.DarkGray.value());
    }
    public static Color getDarkMagenta()
    {
        return new Color(KnownColor.DarkMagenta.value());
    }
    public static Color getDarkOrange()
    {
        return new Color(KnownColor.DarkOrange.value());
    }
    public static Color getDarkOrchid()
    {
        return new Color(KnownColor.DarkOrchid.value());
    }
    public static Color getDarkRed()
    {
        return new Color(KnownColor.DarkRed.value());
    }
    public static Color getDeepPink()
    {
        return new Color(KnownColor.DeepPink.value());
    }

    public static Color getGold()
    {
        return new Color(KnownColor.Gold.value());
    }
    public static Color getGreen()
    {
        return new Color(KnownColor.Green.value());
    }
    public static Color getGreenYellow()
    {
        return new Color(KnownColor.GreenYellow.value());
    }
    public static Color getGray()
    {
        return new Color(KnownColor.Gray.value());
    }

    public static Color getYellowGreen()
    {
        return new Color(KnownColor.YellowGreen.value());
    }

    public static Color getLightCoral()
    {
        return new Color(KnownColor.LightCoral.value());
    }
    public static Color getLightGray()
    {
        return new Color(KnownColor.LightGray.value());
    }
    public static Color getLightGreen()
    {
        return new Color(KnownColor.LightGreen.value());
    }
    public static Color getLightPink()
    {
        return new Color(KnownColor.LightPink.value());
    }
    public static Color getLightSkyBlue()
    {
        return new Color(KnownColor.LightSkyBlue.value());
    }
    public static Color getLightBlue()
    {
        return new Color(KnownColor.LightBlue.value());
    }
    public static Color getLime()
    {
        return new Color(KnownColor.Lime.value());
    }

    public static Color getOrange()
    {
        return new Color(KnownColor.Orange.value());
    }
    public static Color getOrangeRed()
    {
        return new Color(KnownColor.OrangeRed.value());
    }

    public static Color getPink()
    {
        return new Color(KnownColor.Pink.value());
    }
    public static Color getPeru()
    {
        return new Color(KnownColor.Peru.value());
    }


    public static Color getPowderBlue()
    {
        return new Color(KnownColor.PowderBlue.value());
    }

    public static Color getSilver()
    {
        return new Color(KnownColor.Silver.value());
    }
    public static Color getSkyBlue()
    {
        return new Color(KnownColor.SkyBlue.value());
    }
    public static Color getSalmon()
    {
        return new Color(KnownColor.Salmon.value());
    }

    public static Color getWheat()
    {
        return new Color(KnownColor.Wheat.value());
    }

    public static Color getTransparent()
    {
        return new Color(KnownColor.Transparent.value(), true);
    }
}

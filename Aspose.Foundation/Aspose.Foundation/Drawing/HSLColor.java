// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/03/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// HSL values are in the range of 0 to 1.
/// Code taken from http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c
/// </summary>
public class HSLColor
{
    public HSLColor(double hue, double sat, double lum)
    {
        setHue(hue);
        setSat(sat);
        setLum(lum);
    }

    /// <summary>
    /// Given an RGB color in range of 0-255 constructs H,S,L in range of 0-1
    /// </summary>
    public HSLColor(DrColor rgb)
    {
        double r = rgb.getR() / 255.0;
        double g = rgb.getG() / 255.0;
        double b = rgb.getB() / 255.0;

        double max = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);

        double mid = (max + min) / 2.0;
        mLum = mid;
        mHue = mid;
        mSat = mid;

        if (max == min)
        {
            mHue = 0.0; // achromatic
            mSat = 0.0;
        }
        else
        {
            double d = max - min;
            mSat = (mLum > 0.5) ? d / (2.0 - max - min) : d / (max + min);

            if (r == max)
                mHue = (g - b) / d + (g < b ? 6 : 0);
            else if (g == max)
                mHue = (b - r) / d + 2.0;
            else if (b == max)
                mHue = (r - g) / d + 4.0;
        }
        mHue /= 6.0;
    }

    /// <summary>
    /// Converts RGB color to HSL color.
    /// </summary>
    /// <remarks>
    /// AM. I found this algorithm at http://support.microsoft.com/kb/29240 and it seems Office uses it. 
    /// It gives very close result with minor difference caused by fraction loss.
    /// For a while I don't want this algorithm to be common.
    /// </remarks>
    public static HSLColor officeFromDrColor(DrColor rgbColor)
    {
        int h, s, l;

        // intermediate value: % of spread from max
        int r = rgbColor.getR();
        int g = rgbColor.getG();
        int b = rgbColor.getB();

        // calculate lightness
        // max and min RGB values
        int cMax = Math.max(Math.max(r, g), b);
        int cMin = Math.min(Math.min(r, g), b);

        l = (((cMax + cMin) * HSL_MAX) + RGB_MAX) / (2 * RGB_MAX);

        if (cMax == cMin)
        {
            // r=g=b --> achromatic case

            s = 0;                     // saturation
            h = UNDEFINED_HUE;          // hue
        }
        else
        {
            // chromatic case

            // saturation
            if (l <= (HSL_MAX / 2))
                s = (((cMax - cMin) * HSL_MAX) + ((cMax + cMin) / 2)) / (cMax + cMin);
            else
                s = (((cMax - cMin) * HSL_MAX) + ((2 * RGB_MAX - cMax - cMin) / 2)) / (2 * RGB_MAX - cMax - cMin);

            // hue
            int deltaR = (((cMax - r) * (HSL_MAX / 6)) + ((cMax - cMin) / 2)) / (cMax - cMin);
            int deltaG = (((cMax - g) * (HSL_MAX / 6)) + ((cMax - cMin) / 2)) / (cMax - cMin);
            int deltaB = (((cMax - b) * (HSL_MAX / 6)) + ((cMax - cMin) / 2)) / (cMax - cMin);

            if (r == cMax)
                h = deltaB - deltaG;
            else if (g == cMax)
                h = (HSL_MAX / 3) + deltaR - deltaB;
            else // B == cMax
                h = ((2 * HSL_MAX) / 3) + deltaG - deltaR;

            if (h < 0)
                h += HSL_MAX;

            if (h > HSL_MAX)
                h -= HSL_MAX;
        }

        return new HSLColor(h / (double)HSL_MAX, s / (double)HSL_MAX, l / (double)HSL_MAX);
    }

    public DrColor toDrColor()
    {
        return toDrColor(getHue(), getSat(), getLum());
    }

    /// <summary>
    /// Creates <see cref="DrColor"/> based on specified hue, saturation and lightness.
    /// </summary>
    /// <param name="hue">The specified hue</param>
    /// <param name="sat">The specified saturation</param>
    /// <param name="lum">The specified lightness</param>
    /// <returns><see cref="DrColor"/></returns>
    public static DrColor toDrColor(double hue, double sat, double lum)
    {
        double r;
        double g;
        double b;

        if (lum == 0)
        {
            r = 0.0;
            g = 0.0;
            b = 0.0;
        }
        else
        {
            if (sat == 0)
            {
                r = lum;
                g = lum;
                b = lum;
            }
            else
            {
                double q = (lum < 0.5) ? lum * (1.0 + sat) : lum + sat - lum * sat;
                double p = 2.0 * lum - q;
                r = hueToRgb(p, q, hue + 1.0 / 3.0);
                g = hueToRgb(p, q, hue);
                b = hueToRgb(p, q, hue - 1.0 / 3.0);
            }
        }

        return DrColor.fromArgb((int)(255.0 * r), (int)(255.0 * g), (int)(255.0 * b));
    }

    /// <summary>
    /// HSL based color difference. 
    /// </summary>
    /// <remarks>
    /// AM. After some experiments I found that HSL model gives more exactly color matching results. 
    /// </remarks>
    public static double colorDiff(int colorA, int colorB)
    {
        int ar = (colorA & 0x000000FF);
        int ag = ((colorA & 0x0000FF00) >> 8);
        int ab = ((colorA & 0x00FF0000) >> 16);

        int br = (colorB & 0x000000FF);
        int bg = ((colorB & 0x0000FF00) >> 8);
        int bb = ((colorB & 0x00FF0000) >> 16);

        HSLColor hsla = new HSLColor(DrColor.fromArgb(ar, ag, ab));
        HSLColor hslb = new HSLColor(DrColor.fromArgb(br, bg, bb));

        double diff = Math.abs(hsla.getSat() - hslb.getSat()) + Math.abs(hsla.getLum() - hslb.getLum());

        // Hue is undefined for achromatic color so skip component from calculation.
        if (!(isAchromatic(ar, ag, ab) || isAchromatic(br, bg, bb)))
            diff += Math.abs(hsla.getHue() - hslb.getHue());

        return diff;
    }

    private static boolean isAchromatic(int r, int g, int b)
    {
        return (r == g) && (g == b);
    }

    /// <summary>
    /// Converts HSL color to RGB color.
    /// </summary>
    /// <remarks>
    /// AM. I found this algorithm at http://support.microsoft.com/kb/29240 and it seems Office uses it. 
    /// It gives very close result with minor difference caused by fraction loss.
    /// For a while I don't want this algorithm to be common.
    /// </remarks>
    public DrColor officeToDrColor()
    {
        int hue = (int)(getHue() * HSL_MAX);
        int lum = (int)(getLum() * HSL_MAX);
        int sat = (int)(getSat() * HSL_MAX);

        int r, g, b;

        if (sat == 0)
        {
            // achromatic case
            r = g = b = (lum * RGB_MAX) / HSL_MAX;
            if (hue != UNDEFINED_HUE)
            {
                // ERROR
            }
        }
        else
        {
            // chromatic case

            // set up magic numbers
            int magic2;
            if (lum <= (HSL_MAX / 2))
                magic2 = (lum * (HSL_MAX + sat) + (HSL_MAX / 2)) / HSL_MAX;
            else
                magic2 = lum + sat - ((lum * sat) + (HSL_MAX / 2)) / HSL_MAX;
            int magic1 = 2 * lum - magic2;

            // get RGB, change units from HLSMAX to RGBMAX
            r = (officeHueToRgb(magic1, magic2, hue + (HSL_MAX / 3)) * RGB_MAX + (HSL_MAX / 2)) / HSL_MAX;
            g = (officeHueToRgb(magic1, magic2, hue) * RGB_MAX + (HSL_MAX / 2)) / HSL_MAX;
            b = (officeHueToRgb(magic1, magic2, hue - (HSL_MAX / 3)) * RGB_MAX + (HSL_MAX / 2)) / HSL_MAX;
        }

        return DrColor.fromArgb(r, g, b);
    }

    private static int officeHueToRgb(int n1, int n2, int hue)
    {
        // Range check: note values passed add/subtract thirds of range
        if (hue < 0)
            hue += HSL_MAX;

        if (hue > HSL_MAX)
            hue -= HSL_MAX;

        // Return r,g, or b value from this tridrant
        if (hue < (HSL_MAX / 6))
            return (n1 + (((n2 - n1) * hue + (HSL_MAX / 12)) / (HSL_MAX / 6)));
        if (hue < (HSL_MAX / 2))
            return (n2);
        if (hue < ((HSL_MAX * 2) / 3))
            return (n1 + (((n2 - n1) * (((HSL_MAX * 2) / 3) - hue) + (HSL_MAX / 12)) / (HSL_MAX / 6))
      );
        else
            return (n1);
    }

    private static double hueToRgb(double p, double q, double t)
    {
        if (t < 0.0)
            t += 1.0;

        if (t > 1.0)
            t -= 1.0;

        if (t < 1.0 / 6.0)
            return p + (q - p) * 6.0 * t;

        if (t < 1.0 / 2.0)
            return q;

        if (t < 2.0 / 3.0)
            return p + (q - p) * (2.0 / 3.0 - t) * 6.0;

        return p;
    }

    public double getHue() { return mHue; }
    public void setHue(double value) { mHue = MathUtil.fitToRange(value, 0.0, 1.0); }

    public double getSat() { return mSat; }
    public void setSat(double value) { mSat = MathUtil.fitToRange(value, 0.0, 1.0); }

    public double getLum() { return mLum; }
    public void setLum(double value) { mLum = MathUtil.fitToRange(value, 0.0, 1.0); }

    private double mHue;
    private double mSat;
    private double mLum;

    private static final int HSL_MAX = 240;
    private static final int RGB_MAX = 255;
    private static final int UNDEFINED_HUE = (HSL_MAX * 2 / 3);
}


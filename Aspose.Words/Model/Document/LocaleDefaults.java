// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/11/2015 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Contains methods that returns information dependent on system locale during MS Word installation
/// (i.e. dependent on system locale when the Normal template was generated).
/// </summary>
/*static*/ class LocaleDefaults
{
	/* Simulation of static class by using private constructor */
	private LocaleDefaults()
	{}

    /// <summary>
    /// Returns MS Word default page margins that depends on system locale during generation of the Normal template.
    /// </summary>
    /// <param name="lcid">Culture identifier.</param>
    /// <returns>Default page margins in twips.</returns>
    static Margins getPageMargins(int lcid)
    {
        // Most of the values below are taken from the screenshots in the Office documentation for corresponding
        // language. The page for USA is: 
        // https://support.office.com/en-us/article/Change-page-margins-acf0317f-86ab-4bd7-a6a3-75df6234fac3#
        // Other pages can be got by changing language code in the URL. For some languages, margins are mistakenly 
        // displayed as 1''.
        // Values for several popular languages are checked in MS Word.

        switch (lcid)
        {
            case 0x1009: // English - Canada
            case 0x0409: // English - United States
            case 0x0809: // English - United Kingdom
            case 0x0C09: // English - Australia
            case 0x1409: // English - New Zealand
                return new Margins(MARGIN_1_INCH, MARGIN_1_INCH, MARGIN_1_INCH, MARGIN_1_INCH);

            case 0x0804: // Chinese - China
            case 0x0408: // Greek - Greece
            case 0x040D: // Hebrew - Israel
                return new Margins(MARGIN_1_25INCH, MARGIN_1_25INCH, MARGIN_1_INCH, MARGIN_1_INCH);

            case 0x0411: // Japanese - Japan
                return new Margins(MARGIN_30_MM, MARGIN_30_MM, MARGIN_35_MM, MARGIN_30_MM);

            case 0x0402: // Bulgarian - Bulgaria
            case 0x041A: // Croatian - Croatia
            case 0x0405: // Czech - Czech Republic
            case 0x0813: // Dutch - Belgium
            case 0x0413: // Dutch - The Netherlands
            case 0x080C: // French - Belgium
            case 0x040C: // French - France
            case 0x100C: // French - Switzerland
            case 0x0414: // Norwegian (Bokmål) - Norway
            case 0x0415: // Polish - Poland
            case 0x041D: // Swedish - Sweden
            case 0x041F: // Turkish - Turkey
                return new Margins(MARGIN_25_MM, MARGIN_25_MM, MARGIN_25_MM, MARGIN_25_MM);

            case 0x0C07: // German - Austria        
            case 0x0407: // German - Germany
            case 0x1407: // German - Liechtenstein  
            case 0x1007: // German - Luxembourg     
            case 0x0807: // German - Switzerland    
                return new Margins(MARGIN_25_MM, MARGIN_25_MM, MARGIN_25_MM, MARGIN_20_MM);

            case 0x2C0A: // Spanish - Argentina
            case 0x340A: // Spanish - Chile
            case 0x080A: // Spanish - Mexico
            case 0x0C0A: // Spanish - Spain
            case 0x0416: // Portuguese - Brazil
            case 0x0816: // Portuguese - Portugal
                return new Margins(MARGIN_30_MM, MARGIN_30_MM, MARGIN_25_MM, MARGIN_25_MM);

            case 0x0406: // Danish - Denmark
                return new Margins(MARGIN_20_MM, MARGIN_20_MM, MARGIN_30_MM, MARGIN_30_MM);

            case 0x0419: // Russian - Russia
            case 0x0444: // Tatar - Russia
                return new Margins(MARGIN_30_MM, MARGIN_15_MM, MARGIN_20_MM, MARGIN_20_MM);

            case 0x0410: // Italian - Italy
            case 0x0810: // Italian - Switzerland   
                return new Margins(MARGIN_20_MM, MARGIN_20_MM, MARGIN_25_MM, MARGIN_20_MM);

            case 0x0422: // Ukrainian - Ukraine
                return new Margins(MARGIN_25_MM, MARGIN_15_MM, MARGIN_15_MM, MARGIN_15_MM);

            case 0x0427: // Lithuanian - Lithuania
                return new Margins(MARGIN_30_MM, MARGIN_10_MM, MARGIN_30_MM, MARGIN_20_MM);

            default:
                if ((lcid & 0xFF) == 0x09) // English - other, made same as United States
                    return new Margins(MARGIN_1_INCH, MARGIN_1_INCH, MARGIN_1_INCH, MARGIN_1_INCH);

                if ((lcid & 0xFF) == 0x07) // German - other, made same as Germany
                    return new Margins(MARGIN_25_MM, MARGIN_25_MM, MARGIN_25_MM, MARGIN_20_MM);

                if ((lcid & 0xFF) == 0x0A) // Spanish - other, made same as Spain
                    return new Margins(MARGIN_30_MM, MARGIN_30_MM, MARGIN_25_MM, MARGIN_25_MM);

                return new Margins(MARGIN_25_MM, MARGIN_25_MM, MARGIN_25_MM, MARGIN_25_MM);
        }
    }

    /// <summary>
    /// Returns MS Word defaults for header/footer distance from the top/bottom edge of the page. 
    /// The values depend on system locale during generation of the Normal template.
    /// </summary>
    /// <param name="lcid">Culture identifier.</param>
    /// <returns>Default distance from the edge of the page in twips.</returns>
    static int getHeaderFooterDistance(int lcid)
    {
        // Checked for United States, United Kingdom, Australia, Germany, France, Russia, Lithuania, China, Japan,
        // Hebrew (Israel), Greece and other.
        switch (lcid)
        {
            case 0x0409: // English - United States
                return MARGIN_0_5INCH;
            default:
                return MARGIN_12_5MM;
        }
    }

    /// <summary>
    /// Returns MS Word defaults for space between columns. 
    /// The value depends on system locale during generation of the Normal template.
    /// </summary>
    /// <param name="lcid">Culture identifier.</param>
    /// <returns>Default space between columns in twips.</returns>
    static int getSpaceBetweenColumns(int lcid)
    {
        return getHeaderFooterDistance(lcid); // Has same value as header/footer distance for checked languages
    }

    /// <summary>
    /// Returns MS Word defaults for page width and height. 
    /// The values depend on system locale during generation of the Normal template.
    /// </summary>
    /// <param name="lcid">Culture identifier.</param>
    /// <returns>Default page size in twips.</returns>
    static /*Size*/long getPageSize(int lcid)
    {
        /*PaperSize*/int paperSize = getPaperSize(lcid);
        return WordUtil.paperSizeToSize(paperSize);
    }

    private static /*PaperSize*/int getPaperSize(int lcid)
    {
        // Note that this wasn't thoroughly checked and there may be other locale/size combinations used by MS Word.
        switch (lcid)
        {
            case 0x0419: // Russian - Russia
            case 0x1409: // English - New Zealand
            case 0x0411: // Japanese - Japan
            case 0x0407: // German - Germany
                return PaperSize.A4;
            default:
                return PaperSize.LETTER;
        }
    }

    private static final int MARGIN_0_5INCH = 720;
    private static final int MARGIN_1_INCH = 1440;
    private static final int MARGIN_1_25INCH = 1800;
    private static final int MARGIN_10_MM = 567;
    private static final int MARGIN_12_5MM = 708; // In defaults of headers and columns MS Word uses value rounded to lesser
    private static final int MARGIN_15_MM = 850;
    private static final int MARGIN_20_MM = 1134;
    private static final int MARGIN_25_MM = 1417;
    private static final int MARGIN_30_MM = 1701;
    private static final int MARGIN_35_MM = 1985; // In defaults of margins MS Word uses value rounded to greater

    static class Margins
    {
        Margins(int left, int right, int top, int bottom)
        {
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
        }

        int getLeft() { return mLeft; };

        private int mLeft;
        int getRight() { return mRight; };

        private int mRight;
        int getTop() { return mTop; };

        private int mTop;
        int getBottom() { return mBottom; };

        private int mBottom;
    }
}


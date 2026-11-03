/**
 * User: sk Date: Apr 25, 2007
 */

package com.aspose.ms.System.Text.RegularExpressions;

import java.util.regex.Pattern;

/**
 * Copycat of .Net System.Text.RegularExpressions.RegexOptions.
 *
 * Used by aspose's Regex's ctor, backed by java Pattern.* constants.
 */
public final class RegexOptions //@todo 2 sk test unit
{
    //JAVA: single Pattern.CASE_INSENSITIVE works only with US-ASCII
    public static final int IGNORE_CASE = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

    public static final int MULTILINE = Pattern.MULTILINE;

    public static final int IGNORE_PATTERN_WHITESPACE = Pattern.COMMENTS;

    // This option is not available on Java and it is safe to ignore it.
    public static final int COMPILED = 0;

    public static final int NONE = 0;

    // In Java we seem to have a special value for MULTILINE, I suppose then Java's value for SINGLELINE is zero.
    public static final int SINGLELINE = 0;

    // Probably java regex is culture invariant by default.
    public static final int CULTURE_INVARIANT = 0;

    //Not ported values:
    //ExplicitCapture, RightToLeft
}

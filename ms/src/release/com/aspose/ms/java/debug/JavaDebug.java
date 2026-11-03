//Konstantin Sidorenko 22/06/12

package com.aspose.ms.java.debug;

/**
 * Used for simplified emulation of .Net '#if TEST' and '#if DEBUG' preprocessor conditions.
 *
 * Example:
 *      if (JavaDebug.ON)
 *          e.printStackTrace();
 *
 * It is java analog of conditional compilation. See Java Language Specification (JLS) 14.21
 * (very last page): http://docs.oracle.com/javase/specs/jls/se7/html/jls-14.html#jls-14.21.
 *
 * Moreover, Java compiler optimizes non reachable code - it deletes the whole statement (including if)
 * from production code since its argument is always false and underlying body is not reachable.
 *
 * There are two equally-named JavaDebug.java files to automatize constant value swapping.
 *
 * 1. The file for test environment (this file): ..\src\debug\com\aspose\ms\java\debug\JavaDebug.java.
 *    ON is always true in it.
 * 2. The file for production: ..\src\release\com\aspose\ms\java\debug\JavaDebug.java.
 *    ON is always false in it.
 *
 * The first file is defined for compilation in IDE. The second file is used by build script to
 * compile production code.
 */
public class JavaDebug
{
	public static final boolean ON = false;
}

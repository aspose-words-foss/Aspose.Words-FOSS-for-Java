// ported 26/08/2008 by Konstantin Sidorenko
package com.aspose.ms.System.IO;

// contains constants for specifying the access you want for a file.
// You can have Read, Write or ReadWrite access.
//
public final class FileAccess //JAVA was: enum
{
    // Specifies read access to the file. Data can be read from the file and
    // the file pointer can be moved. Combine with WRITE for read-write access.
    public static final int READ = 1;

    // Specifies write access to the file. Data can be written to the file and
    // the file pointer can be moved. Combine with READ for read-write access.
    public static final int WRITE = 2;

    // Specifies read and write access to the file. Data can be written to the
    // file and the file pointer can be moved. Data can also be read from the
    // file.
    public static final int READ_WRITE = 3;
}

// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2007 by Konstantin Sidorenko
// 14/01/2016 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Path;


@Test
public class TestPath
{
    @Test
    public void testPathGetFileName() throws Exception
    {
        msAssert.areEqual(Path.getFileName("C:\\tmp\\"), "");
        msAssert.areEqual(Path.getFileName("C:\\"), "");
        msAssert.areEqual(Path.getFileName("C:"), "");
        msAssert.areEqual(Path.getFileName(""), "");
        msAssert.areEqual(Path.getFileName(null), null);
        msAssert.areEqual(Path.getFileName("myfile"), "myfile");
        msAssert.areEqual(Path.getFileName("C:\\tmp\\simple.txt"), "simple.txt");
        msAssert.areEqual(Path.getFileName("very_simple.txt"), "very_simple.txt");
        msAssert.areEqual(Path.getFileName("C:/tmp/simple_forvard_slash.txt"), "simple_forvard_slash.txt");
        msAssert.areEqual(Path.getFileName("C:\\tmp\\simple.with.dot.txt"), "simple.with.dot.txt");
        msAssert.areEqual(Path.getFileName("C:\\tmp\\simple_without_dot"), "simple_without_dot");
        msAssert.areEqual(Path.getFileName("C:\\tmp.dot\\simple_with_dot_in_path.txt"), "simple_with_dot_in_path.txt");
        msAssert.areEqual(Path.getFileName("C:\\tmp.dot\\simple.with.dot.evryvere.txt"), "simple.with.dot.evryvere.txt");
    }

    @Test
    public void testPathGetFileNameWithoutExtension() throws Exception
    {
        msAssert.areEqual(Path.getFileNameWithoutExtension(""), "");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:\\tmp\\"), "");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:"), "");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:\\tmp\\"), "");
        msAssert.areEqual(Path.getFileNameWithoutExtension(""), "");
        msAssert.areEqual(Path.getFileNameWithoutExtension(null), null);
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:\\tmp\\simple.txt"), "simple");
        msAssert.areEqual(Path.getFileNameWithoutExtension("very_simple.txt"), "very_simple");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:/tmp/simple_forvard_slash.txt"), "simple_forvard_slash");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:\\tmp\\simple.with.dot.txt"), "simple.with.dot");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:\\tmp\\simple_without_dot"), "simple_without_dot");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:\\tmp.dot\\simple_with_dot_in_path.txt"), "simple_with_dot_in_path");
        msAssert.areEqual(Path.getFileNameWithoutExtension("C:\\tmp.dot\\simple.with.dot.evryvere.txt"), "simple.with.dot.evryvere");
    }

    @Test
    public void testPathGetExtension() throws Exception
    {
        msAssert.areEqual(Path.getExtension("C:\\tmp\\simple"), "");
        msAssert.areEqual(Path.getExtension("C:\\tmp\\"), "");
        msAssert.areEqual(Path.getExtension("C:\\"), "");
        msAssert.areEqual(Path.getExtension("C:"), "");
        msAssert.areEqual(Path.getExtension(""), "");
        msAssert.areEqual(Path.getExtension(null), null);
        msAssert.areEqual(Path.getExtension("C:\\tmp\\simple.txt"), ".txt");
        msAssert.areEqual(Path.getExtension("very_simple.txt"), ".txt");
        msAssert.areEqual(Path.getExtension("C:/tmp/simple_forvard_slash.txt"), ".txt");
        msAssert.areEqual(Path.getExtension("C:\\tmp\\simple.with.dot.txt"), ".txt");
        msAssert.areEqual(Path.getExtension("C:\\tmp\\simple_without_dot"), "");
        msAssert.areEqual(Path.getExtension("C:\\tmp.dot\\simple_with_dot_in_path.txt"), ".txt");
        msAssert.areEqual(Path.getExtension("C:\\tmp.dot\\simple.with.dot.evryvere.txt"), ".txt");
    }

    @Test
    public void testPathGetDirectoryName() throws Exception
    {
        msAssert.areEqual(Path.getDirectoryName("C:\\tmp\\simple.txt"), "C:\\tmp");
        msAssert.areEqual(Path.getDirectoryName("C:\\tmp\\"), "C:\\tmp");
        msAssert.areEqual(Path.getDirectoryName("C:"), null);
        msAssert.areEqual(Path.getDirectoryName("C:\\"), null);
        msAssert.areEqual(Path.getDirectoryName("very_simple.txt"), "");
        msAssert.areEqual(Path.getDirectoryName("tmp\\simple.with.dot.txt"), "tmp");
        // .Net throws on Path.GetDirectoryName(""), Java - doesn't.
        msAssert.areEqual(Path.getDirectoryName(null), null);
        msAssert.areEqual(Path.getDirectoryName("C:/tmp/simple_forvard_slash.txt"), "C:\\tmp");
        msAssert.areEqual(Path.getDirectoryName("C:/tmp\\simple_forvard_slash.txt"), "C:\\tmp");
        msAssert.areEqual(Path.getDirectoryName("C:\\tmp\\simple.with.dot.txt"), "C:\\tmp");
        msAssert.areEqual(Path.getDirectoryName("C:\\tmp\\simple_without_dot"), "C:\\tmp");
        msAssert.areEqual(Path.getDirectoryName("C:\\tmp.dot\\simple_with_dot_in_path.txt"), "C:\\tmp.dot");
        msAssert.areEqual(Path.getDirectoryName("C:\\tmp.dot\\simple.with.dot.evryvere.txt"), "C:\\tmp.dot");
    }

    @Test
    public void testPathCombine() throws Exception
    {
        msAssert.areEqual(Path.combine("C:\\ignored.parent", "C:\\tmp\\simple.txt"), "C:\\tmp\\simple.txt");
        msAssert.areEqual(Path.combine("", "very_simple.txt"), "very_simple.txt");
        msAssert.areEqual(Path.combine("C:\\tmp", ""), "C:\\tmp");
        msAssert.areEqual(Path.combine("C:\\tmp", "subdir\\file.txt"), "C:\\tmp\\subdir\\file.txt");
        msAssert.areEqual(Path.combine("C:\\tmp.txt", "subdir\\file.txt"), "C:\\tmp.txt\\subdir\\file.txt");
        //.Net doesn't normalize slash '/' to back slash '\\', but Java does. It is acceptable difference, imho.
        msAssert.areEqual(Path.combine("C:/tmp", "simple_forvard_slash.txt"), "C:\\tmp\\simple_forvard_slash.txt");
        msAssert.areEqual(Path.combine("C:/tmp/", "simple_forvard_slash.txt"), "C:\\tmp\\simple_forvard_slash.txt");
        msAssert.areEqual(Path.combine("C:\\tmp/other", "different_slashes.txt"), "C:\\tmp\\other\\different_slashes.txt");
        //
        msAssert.areEqual(Path.combine("C:\\tmp", "simple.with.dot.txt"), "C:\\tmp\\simple.with.dot.txt");
        msAssert.areEqual(Path.combine("C:\\tmp\\", "simple.with.dot.txt"), "C:\\tmp\\simple.with.dot.txt");

        msAssert.areEqual(Path.combine("C:\\tmp\\", "\\simple.with.dot.txt"), "\\simple.with.dot.txt");

        msAssert.areEqual(Path.combine("C:\\tmp\\", "simple_without_dot"), "C:\\tmp\\simple_without_dot");
        msAssert.areEqual(Path.combine("C:\\tmp.dot", "simple_with_dot_in_path.txt"), "C:\\tmp.dot\\simple_with_dot_in_path.txt");
        msAssert.areEqual(Path.combine("C:\\tmp.dot", "simple.with.dot.evryvere.txt"), "C:\\tmp.dot\\simple.with.dot.evryvere.txt");

        msAssert.areEqual(Path.combine("C:\\parent", "..\\child\\file.txt"), "C:\\parent\\..\\child\\file.txt");
    }

    @Test
    public void testPathIsPathRooted() throws Exception
    {
        msAssert.areEqual(Path.isPathRooted(""), false);

        msAssert.areEqual(Path.isPathRooted("C:"), true);

        msAssert.areEqual(Path.isPathRooted("C:\\"), true);
        msAssert.areEqual(Path.isPathRooted("C:\\tmp\\simple.txt"), true);
        msAssert.areEqual(Path.isPathRooted("very_simple.txt"), false);
        msAssert.areEqual(Path.isPathRooted("C:/tmp/simple_forvard_slash.txt"), true);
        msAssert.areEqual(Path.isPathRooted("C:\\tmp/simple.with.different.slashes.txt"), true);

        msAssert.areEqual(Path.isPathRooted("\\\\tmp\\simple_without_drive"), true);
        msAssert.areEqual(Path.isPathRooted("\\tmp\\simple_without_drive"), true);

        msAssert.areEqual(Path.isPathRooted("//tmp/simple_without_drive"), true);
        msAssert.areEqual(Path.isPathRooted("/tmp/simple_without_drive"), true);

        msAssert.areEqual(Path.isPathRooted("tmp\\simple_without_root.txt"), false);
        msAssert.areEqual(Path.isPathRooted("tmp/simple_without_root.txt"), false);
    }
}


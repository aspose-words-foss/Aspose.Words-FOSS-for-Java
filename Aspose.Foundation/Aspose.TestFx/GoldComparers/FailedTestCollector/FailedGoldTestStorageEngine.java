// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 02/01/2013 by Alexey Butalov

package com.aspose;

import java.io.*;
import java.nio.file.Files;

/**
 * Created by Romik on 27.04.2018.
 */
public class FailedGoldTestStorageEngine
{
    public FailedGoldTestStorageEngine()
    {
    }

    public void saveFailedTest(FailedGoldTestInfo testInfo) throws Exception
    {
        File path = new File(testInfo.getFileNameOut()
                .replaceAll("(?i)x:\\\\(words-java|awnet)", G_GOLD_DIR)
        ).getParentFile();
        path.mkdirs();

        copyFile(path, testInfo.getFileNameOut());
        copyFile(path, testInfo.getFileNameGold());
    }

    private void copyFile(File path, String file) throws Exception
    {
        File sourceFile = new File(file);
        File destFile = new File(path.getAbsoluteFile() + "\\" + new File(file).getName());

        if (destFile.exists())
        {
            if (Files.isSameFile(sourceFile.toPath(), destFile.toPath()))
                return;
            else
                destFile.delete();
        }

        copyFiles(sourceFile, destFile);
    }

    private void copyFiles(File source, File dest) throws Exception
    {
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest))
        {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0)
            {
                os.write(buffer, 0, length);
            }
        }
    }

    private void WriteConfigFile(FailedGoldTestInfo testInfo)
    {
    }

    private static final String G_GOLD_DIR = "x:\\\\words-java.golds";
}

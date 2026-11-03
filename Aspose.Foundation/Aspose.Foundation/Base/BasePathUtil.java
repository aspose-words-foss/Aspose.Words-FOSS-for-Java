// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/08/2016 by Konstantin Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.Random;
import com.aspose.ms.System.Environment;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IDisposable;


public /*static*/ class BasePathUtil
{
	/* Simulation of static class by using private constructor */
	private BasePathUtil()
	{}

    /// <summary>
    /// Returns path to a temporary folder on disk
    /// </summary>
    /// <returns></returns>
    public static String getTempFolderPath() throws Exception
    {
        char[] folder = new char[8];
        String temp = Path.getTempPath();
        for (int a = 0; a <= 10; a++)    // 10 retries
        {
            Random r = new Random(Environment.getTickCount());
            for (int i = 0; i < folder.length; i++)
                folder[i] = (char)r.next('a', 'z' + 1);
            String target = Path.combine(temp, new String(folder));
            if (File.exists(target) || Directory.exists(target))
                continue;
            // Here some other process can create target, and we will happilly return it anyway
            Directory.createDirectory(target);
            return target;
        }
        throw new Exception("failed to create temporary folder");
    }

    public static DeletePathOnDispose useTempFolderPath() throws Exception
    {
        return new DeletePathOnDispose(getTempFolderPath());
    }

    public static class DeletePathOnDispose implements IDisposable
    {
        public DeletePathOnDispose(String path)
        {
            _Path = path;
        }

        public String getTmpPath() { return _Path; }

        public void dispose() throws Exception
        {
            if (_Path != null)
            {
                Directory.delete(_Path, true);
                _Path = null;
            }
        }

        private String _Path;
    }
}


//Konstantin Sidorenko 12/01/12

package com.aspose.ms;

import com.aspose.TestEnvironment;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IO.File;

public class CopyDir
{
	public static void main(String[] args) throws Exception
	{
		if (!Directory.exists(TestEnvironment.getDirWordsJava() + "classes\\test\\META-INF\\services"))
			Directory.createDirectory(TestEnvironment.getDirWordsJava() + "classes\\test\\META-INF\\services");

		File.copy(TestEnvironment.getDirWordsJava() + "classes\\production\\META-INF\\services\\java.nio.charset.spi.CharsetProvider",
				TestEnvironment.getDirWordsJava() + "classes\\test\\META-INF\\services\\java.nio.charset.spi.CharsetProvider", true);
	}
}

Here is implemented UTF-7 charset that not supported by core java.

At the moment UTF7Charset instantiated manually inside Encoding.toJava().
This done because standard java ServiceLoader doesn't work in some tangled environments.

Below is the old comment how to register the charset so ServiceLoader can automatically load it.
You can use the comment to restore automatic loading of the charset:

A new charset should fulfil java.nio.charset.Charset.CharsetProvider contract so JVM will be able to
load the new charset as an extension charset:
1. A charset provider identifies itself with a provider-configuration file named
   java.nio.charset.spi.CharsetProvider in the resource directory META-INF/services.
2. The file should contain a list of fully-qualified concrete charset-provider class names,
   one per line. (com.aspose.ms.java.utf7charset.CharsetProvider in our case).
3. com.aspose.ms.java.utf7charset.CharsetProvider should implement java.nio.charset.spi.CharsetProvider:
   init and return all added charsets (see com.aspose.ms.java.utf7charset.CharsetProvider).

NOTE! Developer should take care during obfuscation of this package since it contains classes referenced from JVM
(external non-aspose calls).
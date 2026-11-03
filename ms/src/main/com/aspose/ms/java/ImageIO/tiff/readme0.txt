These classes let us write (and read) some TIFFs with nothing but the JDK.

The code is taken from https://github.com/jai-imageio/jai-imageio-core
License is BSD 3-clause License w/nuclear disclaimer: see jai-imageio-core-LICENSE.txt in this folder, a copy of
https://github.com/jai-imageio/jai-imageio-core/blob/master/LICENSE.txt. Every source file here carries the notice of the original.

Not all the compressors/decompressors are supported.
Unused code was removed.
Used code was simplified as much as possible.

The main classes are TIFFImageWriter, TiffDecoder, TiffMetadata.
TIFFImageWriter and TiffMetadata are used by SimpleJaiTiffWriterPal (Aspose.Foundation), which SimplifiedJaiTiffWriter and TiffWriterPal use.
TiffDecoder has no user left in Aspose.Words FOSS: its reader, SimplifiedJaiTiffReader, was removed together with JAI.

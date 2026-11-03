/*
 * This file is derived from jai-imageio-core (https://github.com/jai-imageio/jai-imageio-core),
 * reduced and adapted by Aspose. The notice of the original follows.
 *
 * Copyright (c) 2005 Sun Microsystems, Inc. All  Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this  list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for
 * use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */
// Copyright (c) 2001-2017 Aspose Pty Ltd. All Rights Reserved.
// August 2017 by Slava Durin
package com.aspose.ms.java.ImageIO.tiff.decoder;

/**
 * https://github.com/jai-imageio/jai-imageio-core/blob/master/src/main/java/com/github/jaiimageio/plugins/tiff/BaselineTIFFTagSet.java
 * <p>
 * https://www.loc.gov/preservation/digital/formats/content/tiff_tags.shtml
 */
public class BaselineTIFFTags extends TiffTagValue
{

    public static String getName(int tag)
    {
        switch (tag)
        {
            case TAG_NEW_SUBFILE_TYPE:
                return "TAG_NEW_SUBFILE_TYPE";
            case TAG_SUBFILE_TYPE:
                return "TAG_SUBFILE_TYPE";
            case TAG_IMAGE_WIDTH:
                return "TAG_IMAGE_WIDTH";
            case TAG_IMAGE_LENGTH:
                return "TAG_IMAGE_LENGTH";
            case TAG_BITS_PER_SAMPLE:
                return "TAG_BITS_PER_SAMPLE";
            case TAG_COMPRESSION:
                return "TAG_COMPRESSION";
            case TAG_PHOTOMETRIC_INTERPRETATION:
                return "TAG_PHOTOMETRIC_INTERPRETATION";
            case TAG_THRESHHOLDING:
                return "TAG_THRESHHOLDING";
            case TAG_CELL_WIDTH:
                return "TAG_CELL_WIDTH";
            case TAG_CELL_LENGTH:
                return "TAG_CELL_LENGTH";
            case TAG_FILL_ORDER:
                return "TAG_FILL_ORDER";
            case TAG_DOCUMENT_NAME:
                return "TAG_DOCUMENT_NAME";
            case TAG_IMAGE_DESCRIPTION:
                return "TAG_IMAGE_DESCRIPTION";
            case TAG_MAKE:
                return "TAG_MAKE";
            case TAG_MODEL:
                return "TAG_MODEL";
            case TAG_STRIP_OFFSETS:
                return "TAG_STRIP_OFFSETS";
            case TAG_ORIENTATION:
                return "TAG_ORIENTATION";
            case TAG_SAMPLES_PER_PIXEL:
                return "TAG_SAMPLES_PER_PIXEL";
            case TAG_ROWS_PER_STRIP:
                return "TAG_ROWS_PER_STRIP";
            case TAG_STRIP_BYTE_COUNTS:
                return "TAG_STRIP_BYTE_COUNTS";
            case TAG_MIN_SAMPLE_VALUE:
                return "TAG_MIN_SAMPLE_VALUE";
            case TAG_MAX_SAMPLE_VALUE:
                return "TAG_MAX_SAMPLE_VALUE";
            case TAG_X_RESOLUTION:
                return "TAG_X_RESOLUTION";
            case TAG_Y_RESOLUTION:
                return "TAG_Y_RESOLUTION";
            case TAG_PLANAR_CONFIGURATION:
                return "TAG_PLANAR_CONFIGURATION";
            case TAG_PAGE_NAME:
                return "TAG_PAGE_NAME";
            case TAG_X_POSITION:
                return "TAG_X_POSITION";
            case TAG_Y_POSITION:
                return "TAG_Y_POSITION";
            case TAG_FREE_OFFSETS:
                return "TAG_FREE_OFFSETS";
            case TAG_FREE_BYTE_COUNTS:
                return "TAG_FREE_BYTE_COUNTS";
            case TAG_GRAY_RESPONSE_UNIT:
                return "TAG_GRAY_RESPONSE_UNIT";
            case TAG_GRAY_RESPONSE_CURVE:
                return "TAG_GRAY_RESPONSE_CURVE";
            case TAG_T4_OPTIONS:
                return "TAG_T4_OPTIONS";
            case TAG_T6_OPTIONS:
                return "TAG_T6_OPTIONS";
            case TAG_RESOLUTION_UNIT:
                return "TAG_RESOLUTION_UNIT";
            case TAG_PAGE_NUMBER:
                return "TAG_PAGE_NUMBER";
            case TAG_TRANSFER_FUNCTION:
                return "TAG_TRANSFER_FUNCTION";
            case TAG_SOFTWARE:
                return "TAG_SOFTWARE";
            case TAG_DATE_TIME:
                return "TAG_DATE_TIME";
            case TAG_ARTIST:
                return "TAG_ARTIST";
            case TAG_HOST_COMPUTER:
                return "TAG_HOST_COMPUTER";
            case TAG_PREDICTOR:
                return "TAG_PREDICTOR";
            case TAG_EXTRA_SAMPLES:
                return "TAG_EXTRA_SAMPLES";
            default:
                return tag + ": See the class";
        }
    }

    /**
     * Constant specifying the "NewSubfileType" tag.
     *
     * @see #NEW_SUBFILE_TYPE_REDUCED_RESOLUTION
     * @see #NEW_SUBFILE_TYPE_SINGLE_PAGE
     * @see #NEW_SUBFILE_TYPE_TRANSPARENCY
     */
    public static final int TAG_NEW_SUBFILE_TYPE = 254;

    /**
     * A mask to be used with the "NewSubfileType" tag.
     *
     * @see #TAG_NEW_SUBFILE_TYPE
     */
    public static final int NEW_SUBFILE_TYPE_REDUCED_RESOLUTION = 1;

    /**
     * A mask to be used with the "NewSubfileType" tag.
     *
     * @see #TAG_NEW_SUBFILE_TYPE
     */
    public static final int NEW_SUBFILE_TYPE_SINGLE_PAGE = 2;

    /**
     * A mask to be used with the "NewSubfileType" tag.
     *
     * @see #TAG_NEW_SUBFILE_TYPE
     */
    public static final int NEW_SUBFILE_TYPE_TRANSPARENCY = 4;

    /**
     * Constant specifying the "SubfileType" tag.
     *
     * @see #SUBFILE_TYPE_FULL_RESOLUTION
     * @see #SUBFILE_TYPE_REDUCED_RESOLUTION
     * @see #SUBFILE_TYPE_SINGLE_PAGE
     */
    public static final int TAG_SUBFILE_TYPE = 255;

    /**
     * A value to be used with the "SubfileType" tag.
     *
     * @see #TAG_SUBFILE_TYPE
     */
    public static final int SUBFILE_TYPE_FULL_RESOLUTION = 1;

    /**
     * A value to be used with the "SubfileType" tag.
     *
     * @see #TAG_SUBFILE_TYPE
     */
    public static final int SUBFILE_TYPE_REDUCED_RESOLUTION = 2;

    /**
     * A value to be used with the "SubfileType" tag.
     *
     * @see #TAG_SUBFILE_TYPE
     */
    public static final int SUBFILE_TYPE_SINGLE_PAGE = 3;

    /**
     * Constant specifying the "ImageWidth" tag.
     */
    public static final int TAG_IMAGE_WIDTH = 256;

    /**
     * Constant specifying the "ImageLength" tag.
     */
    public static final int TAG_IMAGE_LENGTH = 257;

    /**
     * Constant specifying the "BitsPerSample" tag.
     */
    public static final int TAG_BITS_PER_SAMPLE = 258;

    /**
     * Constant specifying the "Compression" tag.
     *
     * @see #COMPRESSION_NONE
     * @see #COMPRESSION_CCITT_RLE
     * @see #COMPRESSION_CCITT_T_4
     * @see #COMPRESSION_CCITT_T_6
     * @see #COMPRESSION_LZW
     * @see #COMPRESSION_OLD_JPEG
     * @see #COMPRESSION_JPEG
     * @see #COMPRESSION_ZLIB
     * @see #COMPRESSION_PACKBITS
     * @see #COMPRESSION_DEFLATE
     */
    public static final int TAG_COMPRESSION = 259;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     */
    public static final int COMPRESSION_NONE = 1;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     */
    public static final int COMPRESSION_CCITT_RLE = 2;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     */
    public static final int COMPRESSION_CCITT_T_4 = 3;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     */
    public static final int COMPRESSION_CCITT_T_6 = 4;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     */
    public static final int COMPRESSION_LZW = 5;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     */
    public static final int COMPRESSION_OLD_JPEG = 6;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     * @see <a href="http://partners.adobe.com/public/developer/en/tiff/TIFFphotoshop.pdf">TIFF Specification Supplement 2</a>
     */
    public static final int COMPRESSION_JPEG = 7;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     * @see <a href="http://partners.adobe.com/public/developer/en/tiff/TIFFphotoshop.pdf"> TIFF Specification Supplement 2</a>
     */
    public static final int COMPRESSION_ZLIB = 8;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     */
    public static final int COMPRESSION_PACKBITS = 32773;

    /**
     * A value to be used with the "Compression" tag.
     *
     * @see #TAG_COMPRESSION
     * @see <a href="http://www.isi.edu/in-notes/rfc1951.txt">DEFLATE specification</a>
     * @see <a href="http://partners.adobe.com/public/developer/en/tiff/TIFFphotoshop.pdf"> TIFF Specification Supplement 2</a>
     */
    public static final int COMPRESSION_DEFLATE = 32946;

    /**
     * Constant specifying the "PhotometricInterpretation" tag.
     *
     * @see #PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO
     * @see #PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO
     * @see #PHOTOMETRIC_INTERPRETATION_RGB
     * @see #PHOTOMETRIC_INTERPRETATION_PALETTE_COLOR
     * @see #PHOTOMETRIC_INTERPRETATION_TRANSPARENCY_MASK
     * @see #PHOTOMETRIC_INTERPRETATION_Y_CB_CR
     * @see #PHOTOMETRIC_INTERPRETATION_CIELAB
     * @see #PHOTOMETRIC_INTERPRETATION_ICCLAB
     */
    public static final int TAG_PHOTOMETRIC_INTERPRETATION = 262;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_PALETTE_COLOR = 3;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_TRANSPARENCY_MASK = 4;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_CMYK = 5;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_Y_CB_CR = 6;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     */
    public static final int PHOTOMETRIC_INTERPRETATION_CIELAB = 8;

    /**
     * A value to be used with the "PhotometricInterpretation" tag.
     *
     * @see #TAG_PHOTOMETRIC_INTERPRETATION
     * @see <a href="http://partners.adobe.com/public/developer/en/tiff/TIFFPM6.pdf">TIFF Specification Supplement 1</a>
     */
    public static final int PHOTOMETRIC_INTERPRETATION_ICCLAB = 9;

    /**
     * Constant specifying the "Threshholding" tag.
     *
     * @see #THRESHHOLDING_NONE
     * @see #THRESHHOLDING_ORDERED_DITHER
     * @see #THRESHHOLDING_RANDOMIZED_DITHER
     */
    public static final int TAG_THRESHHOLDING = 263;

    /**
     * A value to be used with the "Thresholding" tag.
     *
     * @see #TAG_THRESHHOLDING
     */
    public static final int THRESHHOLDING_NONE = 1;

    /**
     * A value to be used with the "Thresholding" tag.
     *
     * @see #TAG_THRESHHOLDING
     */
    public static final int THRESHHOLDING_ORDERED_DITHER = 2;

    /**
     * A value to be used with the "Thresholding" tag.
     *
     * @see #TAG_THRESHHOLDING
     */
    public static final int THRESHHOLDING_RANDOMIZED_DITHER = 3;

    /**
     * Constant specifying the "Cell_Width" tag.
     */
    public static final int TAG_CELL_WIDTH = 264;

    /**
     * Constant specifying the "cell_length" tag.
     */
    public static final int TAG_CELL_LENGTH = 265;

    /**
     * Constant specifying the "fill_order" tag.
     *
     * @see #FILL_ORDER_LEFT_TO_RIGHT
     * @see #FILL_ORDER_RIGHT_TO_LEFT
     */
    public static final int TAG_FILL_ORDER = 266;

    /**
     * A value to be used with the "FillOrder" tag.
     *
     * @see #TAG_FILL_ORDER
     */
    public static final int FILL_ORDER_LEFT_TO_RIGHT = 1;

    /**
     * A value to be used with the "FillOrder" tag.
     *
     * @see #TAG_FILL_ORDER
     */
    public static final int FILL_ORDER_RIGHT_TO_LEFT = 2;

    /**
     * Constant specifying the "document_name" tag.
     */
    public static final int TAG_DOCUMENT_NAME = 269;

    /**
     * Constant specifying the "Image_description" tag.
     */
    public static final int TAG_IMAGE_DESCRIPTION = 270;

    /**
     * Constant specifying the "Make" tag.
     */
    public static final int TAG_MAKE = 271;

    /**
     * Constant specifying the "Model" tag.
     */
    public static final int TAG_MODEL = 272;

    /**
     * Constant specifying the "Strip_offsets" tag.
     */
    public static final int TAG_STRIP_OFFSETS = 273;

    /**
     * Constant specifying the "Orientation" tag.
     *
     * @see #ORIENTATION_ROW_0_TOP_COLUMN_0_LEFT
     * @see #ORIENTATION_ROW_0_TOP_COLUMN_0_RIGHT
     * @see #ORIENTATION_ROW_0_BOTTOM_COLUMN_0_RIGHT
     * @see #ORIENTATION_ROW_0_BOTTOM_COLUMN_0_LEFT
     * @see #ORIENTATION_ROW_0_LEFT_COLUMN_0_TOP
     * @see #ORIENTATION_ROW_0_RIGHT_COLUMN_0_TOP
     * @see #ORIENTATION_ROW_0_RIGHT_COLUMN_0_BOTTOM
     * @see #ORIENTATION_ROW_0_LEFT_COLUMN_0_BOTTOM
     */
    public static final int TAG_ORIENTATION = 274;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_TOP_COLUMN_0_LEFT = 1;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_TOP_COLUMN_0_RIGHT = 2;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_BOTTOM_COLUMN_0_RIGHT = 3;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_BOTTOM_COLUMN_0_LEFT = 4;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_LEFT_COLUMN_0_TOP = 5;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_RIGHT_COLUMN_0_TOP = 6;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_RIGHT_COLUMN_0_BOTTOM = 7;

    /**
     * A value to be used with the "Orientation" tag.
     *
     * @see #TAG_ORIENTATION
     */
    public static final int ORIENTATION_ROW_0_LEFT_COLUMN_0_BOTTOM = 8;

    /**
     * Constant specifying the "Samples_per_pixel" tag.
     */
    public static final int TAG_SAMPLES_PER_PIXEL = 277;

    /**
     * Constant specifying the "Rows_per_strip" tag.
     */
    public static final int TAG_ROWS_PER_STRIP = 278;

    /**
     * Constant specifying the "Strip_byte_counts" tag.
     */
    public static final int TAG_STRIP_BYTE_COUNTS = 279;

    /**
     * Constant specifying the "Min_sample_value" tag.
     */
    public static final int TAG_MIN_SAMPLE_VALUE = 280;

    /**
     * Constant specifying the "Max_sample_value" tag.
     */
    public static final int TAG_MAX_SAMPLE_VALUE = 281;

    /**
     * Constant specifying the "XResolution" tag.
     */
    public static final int TAG_X_RESOLUTION = 282;

    /**
     * Constant specifying the "YResolution" tag.
     */
    public static final int TAG_Y_RESOLUTION = 283;

    /**
     * Constant specifying the "PlanarConfiguration" tag.
     *
     * @see #PLANAR_CONFIGURATION_CHUNKY
     * @see #PLANAR_CONFIGURATION_PLANAR
     */
    public static final int TAG_PLANAR_CONFIGURATION = 284;

    /**
     * A value to be used with the "PlanarConfiguration" tag.
     *
     * @see #TAG_PLANAR_CONFIGURATION
     */
    public static final int PLANAR_CONFIGURATION_CHUNKY = 1;

    /**
     * A value to be used with the "PlanarConfiguration" tag.
     *
     * @see #TAG_PLANAR_CONFIGURATION
     */
    public static final int PLANAR_CONFIGURATION_PLANAR = 2;

    /**
     * Constant specifying the "PageName" tag.
     */
    public static final int TAG_PAGE_NAME = 285;

    /**
     * Constant specifying the "XPosition" tag.
     */
    public static final int TAG_X_POSITION = 286;

    /**
     * Constant specifying the "YPosition" tag.
     */
    public static final int TAG_Y_POSITION = 287;

    /**
     * Constant specifying the "FreeOffsets" tag.
     */
    public static final int TAG_FREE_OFFSETS = 288;

    /**
     * Constant specifying the "FreeByteCounts" tag.
     */
    public static final int TAG_FREE_BYTE_COUNTS = 289;

    /**
     * Constant specifying the "GrayResponseUnit" tag.
     *
     * @see #GRAY_RESPONSE_UNIT_TENTHS
     * @see #GRAY_RESPONSE_UNIT_HUNDREDTHS
     * @see #GRAY_RESPONSE_UNIT_THOUSANDTHS
     * @see #GRAY_RESPONSE_UNIT_TEN_THOUSANDTHS
     * @see #GRAY_RESPONSE_UNIT_HUNDRED_THOUSANDTHS
     */
    public static final int TAG_GRAY_RESPONSE_UNIT = 290;

    /**
     * A value to be used with the "GrayResponseUnit" tag.
     *
     * @see #TAG_GRAY_RESPONSE_UNIT
     */
    public static final int GRAY_RESPONSE_UNIT_TENTHS = 1;

    /**
     * A value to be used with the "GrayResponseUnit" tag.
     *
     * @see #TAG_GRAY_RESPONSE_UNIT
     */
    public static final int GRAY_RESPONSE_UNIT_HUNDREDTHS = 2;

    /**
     * A value to be used with the "GrayResponseUnit" tag.
     *
     * @see #TAG_GRAY_RESPONSE_UNIT
     */
    public static final int GRAY_RESPONSE_UNIT_THOUSANDTHS = 3;

    /**
     * A value to be used with the "GrayResponseUnit" tag.
     *
     * @see #TAG_GRAY_RESPONSE_UNIT
     */
    public static final int GRAY_RESPONSE_UNIT_TEN_THOUSANDTHS = 4;

    /**
     * A value to be used with the "GrayResponseUnit" tag.
     *
     * @see #TAG_GRAY_RESPONSE_UNIT
     */
    public static final int GRAY_RESPONSE_UNIT_HUNDRED_THOUSANDTHS = 5;

    /**
     * Constant specifying the "GrayResponseCurve" tag.
     */
    public static final int TAG_GRAY_RESPONSE_CURVE = 291;

    /**
     * Constant specifying the "T4Options" tag.
     *
     * @see #T4_OPTIONS_2D_CODING
     * @see #T4_OPTIONS_UNCOMPRESSED
     * @see #T4_OPTIONS_EOL_BYTE_ALIGNED
     */
    public static final int TAG_T4_OPTIONS = 292;

    /**
     * A mask to be used with the "T4Options" tag.
     *
     * @see #TAG_T4_OPTIONS
     */
    public static final int T4_OPTIONS_2D_CODING = 1;

    /**
     * A mask to be used with the "T4Options" tag.
     *
     * @see #TAG_T4_OPTIONS
     */
    public static final int T4_OPTIONS_UNCOMPRESSED = 2;

    /**
     * A mask to be used with the "T4Options" tag.
     *
     * @see #TAG_T4_OPTIONS
     */
    public static final int T4_OPTIONS_EOL_BYTE_ALIGNED = 4;

    /**
     * Constant specifying the "T6Options" tag.
     *
     * @see #T6_OPTIONS_UNCOMPRESSED
     */
    public static final int TAG_T6_OPTIONS = 293;

    /**
     * A mask to be used with the "T6Options" tag.
     *
     * @see #TAG_T6_OPTIONS
     */
    public static final int T6_OPTIONS_UNCOMPRESSED = 2;

    /**
     * Constant specifying the "ResolutionUnit" tag.
     *
     * @see #RESOLUTION_UNIT_NONE
     * @see #RESOLUTION_UNIT_INCH
     * @see #RESOLUTION_UNIT_CENTIMETER
     */
    public static final int TAG_RESOLUTION_UNIT = 296;

    /**
     * A value to be used with the "ResolutionUnit" tag.
     *
     * @see #TAG_RESOLUTION_UNIT
     */
    public static final int RESOLUTION_UNIT_NONE = 1;

    /**
     * A value to be used with the "ResolutionUnit" tag.
     *
     * @see #TAG_RESOLUTION_UNIT
     */
    public static final int RESOLUTION_UNIT_INCH = 2;

    /**
     * A value to be used with the "ResolutionUnit" tag.
     *
     * @see #TAG_RESOLUTION_UNIT
     */
    public static final int RESOLUTION_UNIT_CENTIMETER = 3;


    /**
     * Constant specifying the "PageNumber" tag.
     */
    public static final int TAG_PAGE_NUMBER = 297;

    /**
     * Constant specifying the "TransferFunction" tag.
     */
    public static final int TAG_TRANSFER_FUNCTION = 301;

    /**
     * Constant specifying the "Software" tag.
     */
    public static final int TAG_SOFTWARE = 305;

    /**
     * Constant specifying the "DateTime" tag.
     */
    public static final int TAG_DATE_TIME = 306;

    /**
     * Constant specifying the "Artist" tag.
     */
    public static final int TAG_ARTIST = 315;

    /**
     * Constant specifying the "HostComputer" tag.
     */
    public static final int TAG_HOST_COMPUTER = 316;

    /**
     * Constant specifying the "Predictor" tag.
     *
     * @see #TAG_WHITE_POINT
     * @see #TAG_PRIMARY_CHROMATICITES
     * @see #TAG_COLOR_MAP
     * @see #TAG_HALFTONE_HINTS
     * @see #TAG_TILE_WIDTH
     * @see #TAG_TILE_LENGTH
     * @see #TAG_TILE_OFFSETS
     * @see #TAG_TILE_BYTE_COUNTS
     */
    public static final int TAG_PREDICTOR = 317;

    /**
     * A value to be used with the "Predictor" tag.
     *
     * @see #TAG_PREDICTOR
     */
    public static final int PREDICTOR_NONE = 1;

    /**
     * A value to be used with the "Predictor" tag.
     *
     * @see #TAG_PREDICTOR
     */
    public static final int PREDICTOR_HORIZONTAL_DIFFERENCING = 2;

    /**
     * Constant specifying the "WhitePoint" tag.
     */
    public static final int TAG_WHITE_POINT = 318;

    /**
     * Constant specifying the "PrimaryChromaticites" tag.
     */
    public static final int TAG_PRIMARY_CHROMATICITES = 319;

    /**
     * Constant specifying the "ColorMap" tag.
     */
    public static final int TAG_COLOR_MAP = 320;

    /**
     * Constant specifying the "HalftoneHints" tag.
     */
    public static final int TAG_HALFTONE_HINTS = 321;

    /**
     * Constant specifying the "TileWidth" tag.
     */
    public static final int TAG_TILE_WIDTH = 322;

    /**
     * Constant specifying the "TileLength" tag.
     */
    public static final int TAG_TILE_LENGTH = 323;

    /**
     * Constant specifying the "TileOffsets" tag.
     */
    public static final int TAG_TILE_OFFSETS = 324;

    /**
     * Constant specifying the "TileByteCounts" tag.
     */
    public static final int TAG_TILE_BYTE_COUNTS = 325;

    /**
     * Constant specifying the "InkSet" tag.
     *
     * @see #INK_SET_CMYK
     * @see #INK_SET_NOT_CMYK
     */
    public static final int TAG_INK_SET = 332;

    /**
     * A value to be used with the "InkSet" tag.
     *
     * @see #TAG_INK_SET
     */
    public static final int INK_SET_CMYK = 1;

    /**
     * A value to be used with the "InkSet" tag.
     *
     * @see #TAG_INK_SET
     */
    public static final int INK_SET_NOT_CMYK = 2;

    /**
     * Constant specifying the "InkNames" tag.
     */
    public static final int TAG_INK_NAMES = 333;

    /**
     * Constant specifying the "NumberOfInks" tag.
     */
    public static final int TAG_NUMBER_OF_INKS = 334;

    /**
     * Constant specifying the "DotRange" tag.
     */
    public static final int TAG_DOT_RANGE = 336;

    /**
     * Constant specifying the "TargetPrinter" tag.
     */
    public static final int TAG_TARGET_PRINTER = 337;

    /**
     * Constant specifying the "ExtraSamples" tag.
     *
     * @see #EXTRA_SAMPLES_UNSPECIFIED
     * @see #EXTRA_SAMPLES_ASSOCIATED_ALPHA
     * @see #EXTRA_SAMPLES_UNASSOCIATED_ALPHA
     */
    public static final int TAG_EXTRA_SAMPLES = 338;

    /**
     * A value to be used with the "ExtraSamples" tag.
     *
     * @see #TAG_EXTRA_SAMPLES
     */
    public static final int EXTRA_SAMPLES_UNSPECIFIED = 0;

    /**
     * A value to be used with the "ExtraSamples" tag.
     *
     * @see #TAG_EXTRA_SAMPLES
     */
    public static final int EXTRA_SAMPLES_ASSOCIATED_ALPHA = 1;

    /**
     * A value to be used with the "ExtraSamples" tag.
     *
     * @see #TAG_EXTRA_SAMPLES
     */
    public static final int EXTRA_SAMPLES_UNASSOCIATED_ALPHA = 2;

    /**
     * Constant specifying the "SampleFormat" tag.
     *
     * @see #SAMPLE_FORMAT_UNSIGNED_INTEGER
     * @see #SAMPLE_FORMAT_SIGNED_INTEGER
     * @see #SAMPLE_FORMAT_FLOATING_POINT
     * @see #SAMPLE_FORMAT_UNDEFINED
     */
    public static final int TAG_SAMPLE_FORMAT = 339;

    /**
     * A value to be used with the "SampleFormat" tag.
     *
     * @see #TAG_SAMPLE_FORMAT
     */
    public static final int SAMPLE_FORMAT_UNSIGNED_INTEGER = 1;

    /**
     * A value to be used with the "SampleFormat" tag.
     *
     * @see #TAG_SAMPLE_FORMAT
     */
    public static final int SAMPLE_FORMAT_SIGNED_INTEGER = 2;

    /**
     * A value to be used with the "SampleFormat" tag.
     *
     * @see #TAG_SAMPLE_FORMAT
     */
    public static final int SAMPLE_FORMAT_FLOATING_POINT = 3;

    /**
     * A value to be used with the "SampleFormat" tag.
     *
     * @see #TAG_SAMPLE_FORMAT
     */
    public static final int SAMPLE_FORMAT_UNDEFINED = 4;

    /**
     * Constant specifying the "SMinSampleValue" tag.
     */
    public static final int TAG_S_MIN_SAMPLE_VALUE = 340;

    /**
     * Constant specifying the "SMaxSampleValue" tag.
     */
    public static final int TAG_S_MAX_SAMPLE_VALUE = 341;

    /**
     * Constant specifying the "TransferRange" tag.
     */
    public static final int TAG_TRANSFER_RANGE = 342;

    /**
     * Constant specifying the "JPEGTables" tag.
     *
     * @see <a href="http://partners.adobe.com/public/developer/en/tiff/TIFFphotoshop.pdf">TIFF Specification Supplement 2</a>
     * @see <a href="ftp://ftp.sgi.com/graphics/tiff/TTN2.draft.txt">JPEG-in-TIFF compression</a>
     */
    public static final int TAG_JPEG_TABLES = 347;

    /**
     * Constant specifying the "JPEGProc" tag.
     */
    public static final int TAG_JPEG_PROC = 512;

    /**
     * A value to be used with the "JPEGProc" tag.
     *
     * @see #TAG_JPEG_PROC
     */
    public static final int JPEG_PROC_BASELINE = 1;

    /**
     * A value to be used with the "JPEGProc" tag.
     *
     * @see #TAG_JPEG_PROC
     */
    public static final int JPEG_PROC_LOSSLESS = 14;

    /**
     * Constant specifying the "JPEGInterchangeFormat" tag.
     */
    public static final int TAG_JPEG_INTERCHANGE_FORMAT = 513;

    /**
     * Constant specifying the "JPEGInterchangeFormatLength" tag.
     */
    public static final int TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = 514;

    /**
     * Constant specifying the "JPEGRestartInterval" tag.
     */
    public static final int TAG_JPEG_RESTART_INTERVAL = 515;

    /**
     * Constant specifying the "JPEGLosslessPredictors" tag.
     */
    public static final int TAG_JPEG_LOSSLESS_PREDICTORS = 517;

    /**
     * Constant specifying the "JPEGPointTransforms" tag.
     */
    public static final int TAG_JPEG_POINT_TRANSFORMS = 518;

    /**
     * Constant specifying the "JPEGQTables" tag.
     */
    public static final int TAG_JPEG_Q_TABLES = 519;

    /**
     * Constant specifying the "JPEGDCTables" tag.
     */
    public static final int TAG_JPEG_DC_TABLES = 520;

    /**
     * Constant specifying the "JPEGACTables" tag.
     */
    public static final int TAG_JPEG_AC_TABLES = 521;

    /**
     * Constant specifying the "YCbCrCoefficients" tag.
     */
    public static final int TAG_Y_CB_CR_COEFFICIENTS = 529;

    /**
     * Constant specifying the "YCbCrSubsampling" tag.
     */
    public static final int TAG_Y_CB_CR_SUBSAMPLING = 530;

    /**
     * Constant specifying the "YCbCrPositioning" tag.
     *
     * @see #Y_CB_CR_POSITIONING_CENTERED
     * @see #Y_CB_CR_POSITIONING_COSITED
     */
    public static final int TAG_Y_CB_CR_POSITIONING = 531;

    /**
     * A value to be used with the "YCbCrPositioning" tag.
     *
     * @see #TAG_Y_CB_CR_POSITIONING
     */
    public static final int Y_CB_CR_POSITIONING_CENTERED = 1;

    /**
     * A value to be used with the "YCbCrPositioning" tag.
     *
     * @see #TAG_Y_CB_CR_POSITIONING
     */
    public static final int Y_CB_CR_POSITIONING_COSITED = 2;

    /**
     * Constant specifying the "ReferenceBlackWhite" tag.
     */
    public static final int TAG_REFERENCE_BLACK_WHITE = 532;

    /**
     * Constant specifying the "Copyright" tag.
     */
    public static final int TAG_COPYRIGHT = 33432;

    // Common non-baseline tags

    // ICC profiles (Spec ICC 1:2001-04, Appendix B)

    // 34675 - Embedded ICC Profile               (UNDEFINED/any)

    /**
     * Constant specifying the "ICC Profile" tag.
     *
     * @see <a href="http://www.color.org/ICC1V42.pdf">ICC Specification, section B.4: Embedding ICC profiles in TIFF files</a>
     */
    public static final int TAG_ICC_PROFILE = 34675;
}

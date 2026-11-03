// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/09/2019 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ExifOrientation;


class ExifOrientationUtils
{
    static double getRotationAngle(/*ExifOrientation*/int exifOrientation)
    {
        double rotationAng = 0.0;
        switch (exifOrientation)
        {
            case ExifOrientation.ROTATE_270:
            // Word saves this orientation as mirrored vertical and turned to 270 degrees.
            case ExifOrientation.MIRROR_HORIZONTAL_AND_ROTATE_90:
                rotationAng = 270.0;
                break;
            case ExifOrientation.ROTATE_90:
            // Word saves this orientation as mirrored vertical and turned to 90 degrees.
            case ExifOrientation.MIRROR_HORIZONTAL_AND_ROTATE_270:
                rotationAng = 90.0;
                break;
            case ExifOrientation.ROTATE:
                rotationAng = 180.0;
                break;
        }

        return rotationAng;
    }

    static /*FlipOrientation*/int getFlipOrientation(/*ExifOrientation*/int exifOrientation)
    {
        /*FlipOrientation*/int flip = FlipOrientation.NONE;
        switch (exifOrientation)
        {
            case ExifOrientation.MIRROR:
            case ExifOrientation.MIRROR_HORIZONTAL_AND_ROTATE_90:
            case ExifOrientation.MIRROR_HORIZONTAL_AND_ROTATE_270:
                flip = FlipOrientation.VERTICAL;
                break;
            case ExifOrientation.MIRROR_HORIZONTAL:
                flip = FlipOrientation.HORIZONTAL;
                break;
        }

        return flip;
    }
}


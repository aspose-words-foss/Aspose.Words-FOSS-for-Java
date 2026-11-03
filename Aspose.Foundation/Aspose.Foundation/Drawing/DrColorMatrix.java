// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2011 by Alexey Kachalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a color transformation matrix. This is a 5x5 matrix that contains
/// the coordinates for the RGBAW space. Color transformation matrices are used for
/// applying effects to pictures. Objects of this class are used by DrImageAttributes
/// class objects.
/// </summary>
public class DrColorMatrix
{
    /// <summary>
    /// Initializes a new instance of the class with identity matrix by default.
    /// </summary>
    public DrColorMatrix()
    {
    	this(true);
	
    }

    /// <summary>
    /// Create a color matrix from the given array. Used in java Pal classes.
    /// </summary>
    public DrColorMatrix(float[][] colorMatrix)
    {
        for (int row = 0; row < COLOR_MATRIX_SIZE; row++)
            for (int col = 0; col < COLOR_MATRIX_SIZE; col++)
                mColorMatrix[row][col] = colorMatrix[row][col];
    }

    private DrColorMatrix(boolean initializeWithIdentityMatrix)
    {
        if (initializeWithIdentityMatrix)
        {
            // Set diagonal values with ones.
            for (int i = 0; i < COLOR_MATRIX_SIZE; ++i)
            {
                mColorMatrix[i][i] = 1.0f;
            }
        }
    }

    /// <summary>
    /// Calculates a product of matrices multiplication for pointed color transformation matrices.
    /// </summary>
    public static DrColorMatrix multiply(DrColorMatrix leftMatrix, DrColorMatrix rightMatrix)
    {
        if (leftMatrix == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "leftMatrix");
        if (rightMatrix == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "rightMatrix");

        DrColorMatrix productMatrix = new DrColorMatrix(false);

        for (int i = 0; i < COLOR_MATRIX_SIZE; ++i)
            for (int j = 0; j < COLOR_MATRIX_SIZE; ++j)
                for (int k = 0; k < COLOR_MATRIX_SIZE; ++k)
                    productMatrix.set(i, j, productMatrix.get(i, j) + leftMatrix.get(i, k) * rightMatrix.get(k, j));

        return productMatrix;
    }

    /// <summary>
    /// Gets a value indicating whether this color transformation matrix is identity matrix.
    /// </summary>
    public boolean isIdentityMatrix()
    {
        for (int i = 0; i < COLOR_MATRIX_SIZE; ++i)
        {
            for (int j = 0; j < COLOR_MATRIX_SIZE; ++j)
            {
                float elementValue = mColorMatrix[i][j];
                if (i == j && elementValue != 1.0f || i != j && elementValue != 0.0f)
                    return false;
            }
        }
        return true;
    }

    /// <summary>
    /// Gets or sets a value of this color matrix element by specified row and column indices.
    /// </summary>
    
    public float get(int rowIndex, int columnIndex)
    {
        return mColorMatrix[rowIndex][columnIndex];
    }
    
    public void set(int rowIndex, int columnIndex, float value)
    {
        if (rowIndex > COLOR_MATRIX_SIZE - 1 || columnIndex > COLOR_MATRIX_SIZE - 1)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "DrColorMatrix has size 5x5.");

        mColorMatrix[rowIndex][columnIndex] = value;
    }


    /// <summary>
    /// The size of color matrix.
    /// </summary>
    public static final int COLOR_MATRIX_SIZE = 5;

    private /*final*/ float[][] mColorMatrix = new float[COLOR_MATRIX_SIZE][COLOR_MATRIX_SIZE];
}


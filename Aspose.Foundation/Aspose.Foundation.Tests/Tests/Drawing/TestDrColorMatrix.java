// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2011 by Alexey Kachalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDrColorMatrix
{
    @Test
    public void constructor_IdentityMatrix5x5IsCreated()
    {
        // Arrange, Act.
        DrColorMatrix identityColorMatrix = new DrColorMatrix();

        // Assert.
        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                float expected = i == j ? 1.0f : 0.0f;
                msAssert.areEqual(expected, identityColorMatrix.get(i, j));
            }
        }
    }

    @Test
    public void isIdentityMatrix_DefaultConstructorIsCalled_TrueIsReturned()
    {
        // Arrange.
        DrColorMatrix identityColorMatrix = new DrColorMatrix();

        // Act.
        boolean isIdentityMatrix = identityColorMatrix.isIdentityMatrix();

        // Assert.
        msAssert.isTrue(isIdentityMatrix);
    }

    @Test
    public void isIdentityMatrix_MatrixIsNotIdentity_FalseIsReturned()
    {
        // AAA1.
        DrColorMatrix colorMatrix = new DrColorMatrix();
        colorMatrix.set(0, 0, 2.0f);
        msAssert.isFalse(colorMatrix.isIdentityMatrix());
        // AAA2.
        colorMatrix = new DrColorMatrix();
        colorMatrix.set(1, 2, 1.0f);
        msAssert.isFalse(colorMatrix.isIdentityMatrix());
    }

    @Test
    public void multiply_TwoColorMatricesArePassed_ProductMatrix5x5IsCorrect()
    {
        // Arrange.
        DrColorMatrix a = new DrColorMatrix();
        fillColumn(a, 0, 1.0f);
        fillColumn(a, 1, 2.0f);
        fillColumn(a, 2, 3.0f);
        fillColumn(a, 3, 4.0f);
        fillColumn(a, 4, 5.0f);

        DrColorMatrix b = new DrColorMatrix();
        fillRow(b, 0, 6.0f);
        fillRow(b, 1, 7.0f);
        fillRow(b, 2, 8.0f);
        fillRow(b, 3, 9.0f);
        fillRow(b, 4, 10.0f);

        // Act.
        DrColorMatrix product = DrColorMatrix.multiply(a, b);

        // Assert.
        // JAVA-changed to exclude autoporter warnings about float rounding.
        final float EXPECTED = (float) (1.0*6.0 + 2.0*7.0 + 3.0*8.0 + 4.0*9.0 + 5.0*10.0);

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                msAssert.areEqual(EXPECTED, product.get(i, j));
            }
        }
    }

    private void fillColumn(DrColorMatrix a, int column, float value)
    {
        for (int i = 0; i < DrColorMatrix.COLOR_MATRIX_SIZE; i++)
            a.set(i, column, value);
    }

    private void fillRow(DrColorMatrix a, int row, float value)
    {
        for (int i = 0; i < DrColorMatrix.COLOR_MATRIX_SIZE; i++)
            a.set(row, i, value);
    }
}


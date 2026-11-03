// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2018 by Anatoly Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.Drawing.msSize;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.words.ref.RefSizeF;
import com.aspose.words.ref.RefSize;
import com.aspose.words.ref.RefPoint;
import com.aspose.words.ref.RefPointF;


@Test
public class TestOutRefStruct
{
    @Test
    public void testStructOutRefCtor()
    {
        /*Point*/long p = msPoint.ctor(1, 2);
        /*PointF*/long pf = msPointF.ctor(1.0f, 2.0f);
        /*SizeF*/long sf = msSizeF.Empty;
        /*Size*/long s = msSize.Empty;

        RefSizeF referenceToSf = new RefSizeF(sf);
        RefSize referenceToS = new RefSize(s);
        RefPoint referenceToP = new RefPoint(p);
        RefPointF referenceToPf = new RefPointF(pf);
        methodRef(/*out*/ referenceToSf, /*out*/ referenceToS, /*ref*/ referenceToP, /*ref*/ referenceToPf);
        sf = referenceToSf.get();
        s = referenceToS.get();
        p = referenceToP.get();
        pf = referenceToPf.get();

        msAssert.areEqual(msSizeF.getHeight(sf), 2.0f);
        msAssert.areEqual(msSizeF.getWidth(sf), 1.0f);

        msAssert.areEqual(msSize.getHeight(s), 2);
        msAssert.areEqual(msSize.getWidth(s), 1);

        msAssert.areEqual(msPointF.getX(pf), 2.0f);
        msAssert.areEqual(msPointF.getY(pf), 1.0f);

        msAssert.areEqual(msPoint.getX(p), 2);
        msAssert.areEqual(msPoint.getY(p), 1);
    }

    @Test
    public void testStructOutRefProperty()
    {
        /*Point*/long p = msPoint.ctor(1, 2);
        /*PointF*/long pf = msPointF.ctor(3.0f, 2.0f);
        /*SizeF*/long sf = msSizeF.ctor(0.1f, 0.2f);
        /*Size*/long s = msSize.ctor(4, 5);

        RefSizeF referenceToSf = new RefSizeF(sf);
        RefSize referenceToS = new RefSize(s);
        RefPoint referenceToP = new RefPoint(p);
        RefPointF referenceToPf = new RefPointF(pf);
        methodRef2(p, /*ref*/ referenceToSf, /*ref*/ referenceToS, /*ref*/ referenceToP, /*ref*/ referenceToPf);
        sf = referenceToSf.get();
        s = referenceToS.get();
        p = referenceToP.get();
        pf = referenceToPf.get();

        msAssert.areEqual(msSizeF.getHeight(sf), 2.0f);
        msAssert.areEqual(msSizeF.getWidth(sf), 1.0f);

        msAssert.areEqual(msSize.getHeight(s), 2);
        msAssert.areEqual(msSize.getWidth(s), 1);

        msAssert.areEqual(msPointF.getX(pf), 1.0f);
        msAssert.areEqual(msPointF.getY(pf), 2.0f);

        msAssert.areEqual(msPoint.getX(p), 2);
        msAssert.areEqual(msPoint.getY(p), 1);
    }

    private void methodRef(/*out*/RefSizeF outSizeF, /*out*/RefSize outSize, /*ref*/RefPoint refPoint, /*ref*/RefPointF refPointf)
    {
        /*PointF*/long pfj = refPointf.get();
        refPointf.set(msPointF.ctor(msPointF.getY(pfj), msPointF.getX(pfj)));
        /*Point*/long pj = refPoint.get();
        refPoint.set(msPoint.ctor(msPoint.getY(pj), msPoint.getX(pj)));
        outSizeF.set(new /*SizeF*/Long(pfj));
        outSize.set(new /*Size*/Long(pj));
    }

    private void methodRef2(/*Point*/long point, /*ref*/RefSizeF refSizeF, /*ref*/RefSize refSize, /*ref*/RefPoint refPoint, /*ref*/RefPointF refPointf)
    {
        refPoint.set(msPoint.setX(refPoint.get(), msPoint.getY(point)));
        refPoint.set(msPoint.setY(refPoint.get(), msPoint.getX(point)));
        refPointf.set(msPointF.setX(refPointf.get(), msPoint.getX(point)));
        refPointf.set(msPointF.setY(refPointf.get(), msPoint.getY(point)));
        refSize.set(msSize.setWidth(refSize.get(), msPoint.getX(point)));
        refSize.set(msSize.setHeight(refSize.get(), msPoint.getY(point)));
        refSizeF.set(msSizeF.setWidth(refSizeF.get(), msPoint.getX(point)));
        refSizeF.set(msSizeF.setHeight(refSizeF.get(), msPoint.getY(point)));
    }
}


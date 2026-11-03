// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/08/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


public abstract class PackageComparisonInfo extends FileComparisonInfo
{
    public PackageComparisonInfo(
        String filenameSrc,
        String filenameOut,
        String filenameGold,
        String filenameMS)
    {
    	super(
              filenameSrc,
              filenameOut,
              filenameGold,
              filenameMS);
	
        mStreamProvider = new FileComparisonStreamProvider(filenameOut, filenameGold, null, filenameMS);
    }

    public PackageComparisonInfo(
        String filenameSrc,
        String filenameOut,
        String filenameGold,
        String filenameOriginalGold,
        String filenameMS)
    {
    	super(
              filenameSrc,
              filenameOut,
              filenameGold,
              filenameOriginalGold,
              filenameMS);
	
        mStreamProvider = new FileComparisonStreamProvider(filenameOut, filenameGold, filenameOriginalGold, filenameMS);
    }

    public PackageComparisonInfo(IComparisonStreamProvider streamProvider)
    {
    	super(
              null,
              null,
              null,
              null,
              null);
	
        mStreamProvider = streamProvider;
    }

    public /*override*/ void updateStatus() throws Exception
    {
        updateStatus(null);
    }

    /// <summary>
    /// Call this to update the comparison info status if using a custom package part comparer.
    /// </summary>
    public void updateStatus(IPackagePartComparer partComparer) throws Exception
    {
        mPartComparisonInfos = new PackagePartComparisonInfoCollection();

        resetStatus();

        // We need to compare all parts that occur in all three packages: Out, Gold and MS. 
        // For this we load and create a list of all parts found in all three packages.
        // But Gold and/or MS file can be missing and this is allowed.
        Stream streamOut = null;
        Stream streamGold = null;
        Stream streamOriginalGold = null;
        Stream streamMS = null;

        ComparisonPackage packageOut = null;
        ComparisonPackage packageGold = null;
        ComparisonPackage packageOriginalGold = null;
        ComparisonPackage packageMS = null;

        try
        {
            streamOut = mStreamProvider.getStreamOut();
            if (streamOut != null)
                packageOut = createPackage(streamOut);

            streamGold = mStreamProvider.getStreamGold();
            if (streamGold != null)
                packageGold = createPackage(streamGold);

            streamOriginalGold = mStreamProvider.getStreamOriginalGold();
            if (streamOriginalGold != null)
                packageOriginalGold = createPackage(streamOriginalGold);

            streamMS = mStreamProvider.getStreamMS();
            if (streamMS != null)
                packageMS = createPackage(streamMS);

            // Collect all part comparison infos.
            collectParts(packageOut, PackageWhatFile.OUT);
            collectParts(packageGold, PackageWhatFile.GOLD);
            collectParts(packageOriginalGold, PackageWhatFile.ORIGINAL_GOLD);
            collectParts(packageMS, PackageWhatFile.MS);

            // Update comparison status for all parts.
            for (PackagePartComparisonInfo partInfo : mPartComparisonInfos)
                partInfo.updateStatus(partComparer);

            if (packageOut != null)
            {
                mStatusOut = ComparisonStatus.OK;

                // Make the overall status Different for the Gold package if there is a problem with at least one part.
                if (packageGold != null)
                {
                    mStatusGold = ComparisonStatus.OK;
                    for (PackagePartComparisonInfo partInfo : mPartComparisonInfos)
                    {
                        if (shouldInvalidatePackage(partInfo.getGoldStatus(), partInfo.getOutStatus()))
                        {
                            mStatusGold = ComparisonStatus.DIFFERENT;
                            break;
                        }
                    }
                }
                else
                {
                    mStatusGold = ComparisonStatus.MISSING;
                }

                // Make the overall status Different for the 'Original .Net Gold' package if there is a problem with at least one part.
                if (packageOriginalGold != null)
                {
                    mStatusOriginalGold = ComparisonStatus.OK;
                    for (PackagePartComparisonInfo partInfo : mPartComparisonInfos)
                    {
                        if (shouldInvalidatePackage(partInfo.getOriginalGoldStatus(), partInfo.getOutStatus()))
                        {
                            mStatusOriginalGold = ComparisonStatus.DIFFERENT;
                            break;
                        }
                    }
                }
                else
                {
                    mStatusOriginalGold = ComparisonStatus.MISSING;
                }


                // Make the overall status Different for the MS package if there is a problem with at least one part.
                if (packageMS != null)
                {
                    mStatusMS = ComparisonStatus.OK;
                    for (PackagePartComparisonInfo partInfo : mPartComparisonInfos)
                    {
                        if (shouldInvalidatePackage(partInfo.getMSStatus(), partInfo.getOutStatus()))
                        {
                            mStatusMS = ComparisonStatus.DIFFERENT;
                            break;
                        }
                    }
                }
                else
                {
                    mStatusMS = ComparisonStatus.MISSING;
                }
            }
            else
            {
                mStatusOut = ComparisonStatus.MISSING;
                mStatusGold = (packageGold != null) ? ComparisonStatus.DIFFERENT : ComparisonStatus.MISSING;
                mStatusOriginalGold = (packageOriginalGold != null) ? ComparisonStatus.DIFFERENT : ComparisonStatus.MISSING;
                mStatusMS = (packageMS != null) ? ComparisonStatus.DIFFERENT : ComparisonStatus.MISSING;
            }
        }
        finally
        {
            if (streamOut != null)
                streamOut.close();
            if (streamGold != null)
                streamGold.close();
            if (streamOriginalGold != null)
                streamOriginalGold.close();
            if (streamMS != null)
                streamMS.close();
        }
    }

    protected abstract ComparisonPackage createPackage(Stream stream) throws Exception;

    private static boolean shouldInvalidatePackage(/*ComparisonStatus*/int otherStatus, /*ComparisonStatus*/int outStatus)
    {
        if (otherStatus != ComparisonStatus.OK)
        {
            boolean bothMissing = ((otherStatus == ComparisonStatus.MISSING) && (outStatus == ComparisonStatus.MISSING));
            // When both Out and Other parts are missing, it is okay and should not invalidate the whole package.
            // This happens when for example both Out and Gold do not have some part because we don't support it, but MS has it.
            return !bothMissing;
        }
        return false;
    }

    private void collectParts(ComparisonPackage _package, /*PackageWhatFile*/int whatFile)
    {
        if (_package == null)
            return;

        while (_package.moveToNextEntry())
        {
            String partName = _package.getEntryFileName();

            PackagePartComparisonInfo partComparisonInfo = mPartComparisonInfos.get(partName);
            if (partComparisonInfo == null)
            {
                partComparisonInfo = new PackagePartComparisonInfo(partName);
                mPartComparisonInfos.add(partComparisonInfo);
            }

            switch (whatFile)
            {
                case PackageWhatFile.OUT:
                    partComparisonInfo.setBufferOut(_package.loadEntryToByteArray());
                    break;
                case PackageWhatFile.GOLD:
                    partComparisonInfo.setBufferGold(_package.loadEntryToByteArray());
                    break;
                case PackageWhatFile.ORIGINAL_GOLD:
                    partComparisonInfo.setBufferOriginalGold(_package.loadEntryToByteArray());
                    break;
                case PackageWhatFile.MS:
                    partComparisonInfo.setBufferMS(_package.loadEntryToByteArray());
                    break;
                default:
                    throw new IllegalStateException("Unexpected enum value.");
            }
        }
    }

    public PackagePartComparisonInfoCollection getPartComparisonInfos() { return mPartComparisonInfos; }

    private PackagePartComparisonInfoCollection mPartComparisonInfos;
    private /*final*/ IComparisonStreamProvider mStreamProvider;
}


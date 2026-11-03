// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2010 by Roman Korchagin
package com.aspose;

import com.aspose.ms.System.IO.*;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;
import org.custommonkey.xmlunit.*;
import org.w3c.dom.Node;

import java.io.ByteArrayInputStream;


/// <summary>
/// This class is to be ported manually. I am not making it part of the PAL because it is used for tests only.
/// </summary>
public class XmlComparer
{
    public static boolean compareXmlStrings(String expectedXml, String actualXml) throws Exception
    {
        byte[] expectedBytes = Encoding.getUTF8().getBytes(expectedXml);
        byte[] actualBytes = Encoding.getUTF8().getBytes(actualXml);

        return compareXmlBuffers(expectedBytes, actualBytes);
    }

    public static boolean compareXmlStreams(Stream stream1, Stream stream2) throws Exception
    {
        XMLUnit.setIgnoreWhitespace(true);
        //.net XmlTextWriter changes 0x0-0x1F chars to xml character entities, but java one (based on Sun's StAX) - doesn't.
        //See for example TestVarsAndFrame - in .net all '\n' inside 'w:val' attribute of 'w:docVar' node changed to
        //'&#xA;', but in java stayed untouched.
        //This parameter instructs to normalize all whitespaces inside text nodes and attributes.
        XMLUnit.setNormalizeWhitespace(true);

        // RK At the moment we ignore comments because our HTML writers some MS Word-like special comments
        // such as [if !supportMisalignedColumns] and that will require too many JavaGolds.
        XMLUnit.setIgnoreComments(true);

        //SPEED: strip DOCTYPE line from the documents: The current version of the XERX parser will always
        //read the DTD if the DOCTYPE line is specified even when validation feature is set to false.
        String goldString = getStringDoctypeStripped(stream1);
        String userString = getStringDoctypeStripped(stream2);

        Diff diff = new Diff(goldString, userString);
        diff.overrideDifferenceListener(new MyXmlDifferenceListener());

        // todo 0 rk this seems to relax comparison somewhat, can I delete this?
        diff.overrideElementQualifier(new ElementNameAndAttributeQualifier());

        return diff.identical();
    }

    static boolean compareXmlBuffers(byte[] buffer1, byte[] buffer2) throws Exception
    {
        MemoryStream stream1 = new MemoryStream(buffer1);
        MemoryStream stream2 = new MemoryStream(buffer2);
        boolean result = compareXmlStreams(stream1, stream2);
        stream1.close();
        stream2.close();
        return result;
    }

    /**
     * Strips DOCTYPE line from the document.
     *
     * The current version of the XERX parser will always read the DTD if the DOCTYPE
     * line is specified even when validation feature is set to false. This seriously
     * slows document comparison since the parsers loads external DTDs from the internet.
     */
    private static String getStringDoctypeStripped(Stream stream) throws Exception
    {
        stream.setPosition(0);
        StreamReader reader = new StreamReader(stream);

        char[] firstChars = new char[200];
        int firstCharsRead = reader.read(firstChars, 0, firstChars.length);
        String first = new String(firstChars, 0, firstCharsRead);
        int docTypeStart = first.indexOf("<!DOCTYPE");

        //the document doesn't contain DOCTYPE line.
        if (docTypeStart == -1)
        {
            String remainder = reader.readToEnd();
            return first + remainder;
        }

        int docTypeEnd = first.indexOf('>', docTypeStart);

        first = new String(firstChars, 0, docTypeStart);
        String second = new String(firstChars, docTypeEnd + 1, firstCharsRead - docTypeEnd - 1);
        String remainder = reader.readToEnd();

        stream.close();
        return first + second + remainder;
    }

    /// <summary>
    /// We put all the custom logic for XML compare into this class.
    /// At the moment this class allows for small differences in double values in XPS that occur due to platform differences.
    /// </summary>
    static class MyXmlDifferenceListener implements DifferenceListener
    {
        // This is the maximum allowed difference in values we allow.
        static final double MaxAllowedDelta = 0.000002;

        public int differenceFound(Difference difference)
        {
	        int result = DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            if (difference.getId() == DifferenceConstants.ATTR_VALUE_ID)
            {
                Node controlNode = difference.getControlNodeDetail().getNode();
                Node testNode = difference.getTestNodeDetail().getNode();

                String attrName = controlNode.getLocalName();
// todo 0 rk enable as required.                
//                if ("OriginX".equals(attrName) ||
//                    "OriginY".equals(attrName))
//                {
//                    // This is an XPS difference.
//	                result = CompareDoublesWithDelta(controlNode.getNodeValue(), testNode.getNodeValue());
//                }
//                else if ("Transform".equals(attrName) ||
//                         "Viewport".equals(attrName) ||
//                         "Viewbox".equals(attrName))
//                {
//                    // This is an XPS difference.
//                    String[] values1 = m$String.split(controlNode.getNodeValue(), ',');
//                    String[] values2 = m$String.split(testNode.getNodeValue(), ',');
//                    result = CompareDoublesWithDelta(values1, values2);
//                }
               /*else*/ if (/*"RenderTransform".equals(attrName) ||*/
                         "Data".equals(attrName))
                {
                    // RK There is no point in comparing Canvas.Clip because .NET and Java returns different points actually, although visually same.

                    // RK Path.Data can have differences due to use of Math.Sqrt to calculate some points. And Math.Sqrt gives different results.

                    // This is an XPS difference.
                    final char[] pathDataSeparators = new char[] {' ', ','};
                    String[] values1 = msString.split(controlNode.getNodeValue(), pathDataSeparators);
                    String[] values2 = msString.split(testNode.getNodeValue(), pathDataSeparators);
                    trimPathDataCommands(values1);
                    trimPathDataCommands(values2);
                    result = CompareDoublesWithDelta(values1, values2);
                }
            }

	        //sk: visual file comparer draws too big difference picture.
	        //Uncomment this if you want to see exact place of the first difference.
	        //if (result == DifferenceListener.RETURN_ACCEPT_DIFFERENCE)
	        //    System.out.println("difference: " + difference);

            return result;
        }

        private static int CompareDoublesWithDelta(String s1, String s2)
        {
            if ((s1.length() == 0) && (s2.length() == 0))
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;

			double d1 = Double.parseDouble(s1);
			double d2 = Double.parseDouble(s2);

			if (Math.abs(d1 - d2) > MaxAllowedDelta)
				return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
			else
				return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
        }

        private static int CompareDoublesWithDelta(String[] values1, String[] values2)
        {
            if (values1.length != values2.length)
                return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;

            for (int i = 0; i < values1.length; i++)
            {
                if (CompareDoublesWithDelta(values1[i], values2[i]) == DifferenceListener.RETURN_ACCEPT_DIFFERENCE)
                    return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            }

            return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
        }

        private static void trimPathDataCommands(String[] values)
        {
            final char[] pathDataCommands = new char[] { 'M', 'L', 'C', 'Z' };
            for (int i = 0; i < values.length; i++)
                values[i] = msString.trimStart(values[i], pathDataCommands);
        }

        public void skippedComparison(Node node, Node node1)
        {
            //do nothing.
        }
    }
}



//Konstantin Sidorenko 27/12/2010

package com.aspose.ms.System.Xml;

import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ms.java.util.JavaUtil;
import com.aspose.ms.java.xml.DotNetEscapingWriter;
import com.ctc.wstx.sr.ValidatingStreamReader;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Helper java-only class to implement XmlTextReader.ReadOuterXml().
 * Reads one xml element. Recursively calls new XmlElementReader if
 * meets child element.
 *
 * Reads outer xml of given element almost like .Net XmlTextReader.ReadOuterXml() does.
 *
 * StAX parser hides some original xml text during parsing:
 * 1) StAX counts namespace declarations ('xmlns' attributes) separately from ordinary attributes.
 * 2) character entities converted to characters.
 * - the class restores this text during reading.
 *
 * .Net XmlTextReader adds namespace declaration for current element if the declaration is stripped out
 * (i.e. defined above given outer xml piece). - the class adds the declaration too.
 *
 */
class XmlElementReader implements XMLStreamConstants
{
	/**
	 * Init XmlElementReader to read outer xml like .Net XmlTextReader.ReadOuterXml() does.
	 * The reader should be positioned on START_ELEMENT. All read text added to given StringBuilder.
	 */
	XmlElementReader(ValidatingStreamReader reader, StringBuilder builder)
	{
		this.mParent = null;
		this.mReader = reader;
		this.mBuilder = builder;

		//sk: i don't like this, but StAX throws out all information about already read and converted
		//character entities... so we can restore entities only using brute force: just reprint a text
		//using DotNetEscapingWriter to get escaped text.
		mCharWriter = new CharArrayWriter();
		mEscapingWriter = new DotNetEscapingWriter(mCharWriter, false);
		mEscapingAttributeWriter = new DotNetEscapingWriter(mCharWriter, true);
	}

	/**
	 * Used to recursively call itself.
	 */
	private XmlElementReader(XmlElementReader parent)
	{
		this.mParent = parent;
		this.mReader = parent.mReader;
		this.mBuilder = parent.mBuilder;

		//reuse all writers used to restore character entities.
		mCharWriter = parent.mCharWriter;
		mEscapingWriter = parent.mEscapingWriter;
		mEscapingAttributeWriter = parent.mEscapingAttributeWriter;
	}

	/**
	 * Reads outer xml into StringBuilder. Returns mCurrentEvent number
	 * because can change the number internally.
	 */
	public int readOuterXml() throws XMLStreamException, IOException
	{
		mCurrentEvent = mReader.getEventType();
		assert mCurrentEvent == START_ELEMENT;
		readStartElement();

        if (mReader.isEmptyElement())
        {
            // Go to the fictive END_ELEMENT.
            // This element exists only in Woodstox model but not in the real document.
            mCurrentEvent = mReader.next();
            return mCurrentEvent;
        }

		do
		{
			mCurrentEvent = mReader.next();
			switch (mCurrentEvent)
			{
				case START_ELEMENT:
				{
					//calls himself recursively
					XmlElementReader childElementReader = new XmlElementReader(this);
					mCurrentEvent = childElementReader.readOuterXml();
					break;
				}
				case END_ELEMENT:
				{
					mBuilder.append("</").append(mReader.getPrefixedName()).append(">");
					return mCurrentEvent;
				}
				case CHARACTERS:
				{
					// sk: again, i don't like this, but i didn't find another way to restore character entities.
					String unescaped = mReader.getText();
					mEscapingWriter.write(unescaped);
					mEscapingWriter.flush();
					String escaped = mCharWriter.toString();
					mCharWriter.reset();
					mBuilder.append(escaped);
					break;
				}
				case PROCESSING_INSTRUCTION:
				case COMMENT:
				{
					//do nothing
					break;
				}
				default:
					throw new IllegalStateException("Unexpected XML event type: " + XmlNodeType.toString(mCurrentEvent));

			}
		} while (true);
	}

	private void readStartElement() throws XMLStreamException, IOException
	{
        mBuilder.append("<");
        mBuilder.append(mReader.getPrefixedName());

        // mDefinedPrefixes contains namespace declarations sorted by prefixes
        // ordinary attributes
        SortedList<String, String> attributes = new SortedList<String, String>();

        // StAX counts namespace declarations ('xmlns' attributes) separately from ordinary attributes.
        // Local defined namespaces. Overridden prefixes is not supported at the moment.
        for (int i = 0; i < mReader.getNamespaceCount(); i++)
            definePrefix(mReader.getNamespacePrefix(i), mReader.getNamespaceURI(i));

        // check prefix of element itself, if it is defined
        definePrefix(mReader.getPrefix(), mReader.getNamespaceURI());

        for (int i = 0; i < mReader.getAttributeCount(); i++)
        {
            String prefix = mReader.getAttributePrefix(i);
            String localName = mReader.getAttributeLocalName(i);

            if ("xmlns".equals(prefix)) // define a local namespace
                continue;

            // attribute with namespace prefix: append namespace declaration if it is not defined earlier.
            if (JavaUtil.hasChars(prefix))
            {
                definePrefix(prefix, mReader.getNamespaceURI(prefix));
                attributes.add(prefix + ":" + localName, mReader.getAttributeValue(i));
            }
            else
            {
                attributes.add(localName, mReader.getAttributeValue(i));
            }
        }

        if (attributes.getCount() > 0)
        {
            for (Map.Entry<String, String> e : attributes)
            {
                mBuilder.append(" ");
                mBuilder.append(e.getKey());
                mBuilder.append("=\"");
                mBuilder.append( escape(e.getValue()) );
                mBuilder.append("\"");
            }
        }

        // Append namespace declaration for current node
        if (mDefinedPrefixes.getCount() > 0)
        {
            for (Map.Entry<String, String> e : mDefinedPrefixes)
            {
                if (JavaUtil.hasChars(e.getKey()))
                {
                    mBuilder.append(" xmlns:");
                    mBuilder.append(e.getKey());
                    mBuilder.append("=\"");
                    mBuilder.append( escape(e.getValue()) );
                    mBuilder.append("\"");
                }
                else
                {
                    // default namespace
                    mBuilder.append(" xmlns=\"");
                    mBuilder.append( escape(e.getValue()) );
                    mBuilder.append("\"");
                }
            }
        }

		if (mReader.isEmptyElement())
            mBuilder.append(" />");
        else
            mBuilder.append(">");
	}

    private String escape(String s) throws IOException
    {
        // sk: again, I don't like this, but I didn't find another way to restore character entities.
        mEscapingAttributeWriter.write(s);
        mEscapingWriter.flush();
        String escaped = mCharWriter.toString();
        mCharWriter.reset();
        return escaped;
    }

    private void definePrefix(String prefix, String namespaceUri)
    {
        if (!isPrefixDefined(prefix) && JavaUtil.hasChars(namespaceUri))
            mDefinedPrefixes.add(prefix, namespaceUri);
    }

	private boolean isPrefixDefined(String prefix)
	{
		if (mDefinedPrefixes.containsKey(prefix))
			return true;

		if (mParent != null)
			return mParent.isPrefixDefined(prefix);

		return false;
	}

	//backing StAX parser
	private ValidatingStreamReader mReader;
	private StringBuilder mBuilder;
	private XmlElementReader mParent;

	// Used to restore namespace declarations ('xmlns' attributes)
    // Key is a string prefix. Value is a string namespace uri.
	SortedList<String,String> mDefinedPrefixes = new SortedList<String, String>();
	private int mCurrentEvent;
	//used to escape character entities (unescaped during reading)
	CharArrayWriter mCharWriter;
	private DotNetEscapingWriter mEscapingWriter;
	private DotNetEscapingWriter mEscapingAttributeWriter;
}

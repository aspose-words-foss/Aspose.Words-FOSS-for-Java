// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/io/textwriter.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.
// ported 28/07/2008 by Konstantin Sidorenko
/*============================================================
**
** Class:  TextWriter
**
**
** Purpose: Abstract base class for Text-only Writers.
** Subclasses will include StreamWriter & StringWriter.
**
**
===========================================================*/

package com.aspose.ms.System.IO;

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;

// This abstract base class represents a writer that can write a sequential
// stream of characters. A subclass must minimally implement the
// Write(char) method.
//
// This class is intended for character output, not bytes.
// There are methods on the Stream class for writing bytes.
public abstract class TextWriter //: MarshalByRefObject, IDisposable
{
    public static final TextWriter Null = new NullTextWriter();

//JAVA-changed from:
    // This should be initialized to Environment.NewLine, but
    // to avoid loading Environment unnecessarily so I've duplicated
    // the value here.
//#if !PLATFORM_UNIX
//    private const String InitialNewLine = "\r\n";
//
//    protected char[] CoreNewLine = new char[] { '\r', '\n' };
//#else
//    private const String InitialNewLine = "\n";
//
//    protected char[] CoreNewLine = new char[] {'\n'};
//
//#endif // !PLATFORM_UNIX
    //private static final String InitialNewLine = System.getProperty("line.separator");
	//JAVA: should be static as InitialNewLine if we whant to get proper initialization.
    //protected static char[] CoreNewLine = InitialNewLine.toCharArray();

    //JAVA: @nixspirit.
    // see WORDSJAVA-682, WORDSJAVA-683 issues.
    // Users reported that MHTML files are rendered incorrectly in Linux environment.
    // The reason why IE can't open files properly was different new line sequence.
    // Nevertheless either generated in Linux or Windows mhtml files can be opened in Linux with e.g. Fifrefox.
    // So we decided to set the same new line sequence for all platforms.
    // It shouldn't affect any windows functionality and probably make Linux output look better.
    private static final String InitialNewLine = "\r\n";
    protected static char[] CoreNewLine = new char[] { '\r', '\n' };


	//JAVA-deleted
    // Can be null - if so, ask for the Thread's CurrentCulture every time.
    //private IFormatProvider InternalFormatProvider;

    protected TextWriter()
    {
	    //JAVA-deleted
        //InternalFormatProvider = null;  // Ask for CurrentCulture all the time.
    }

	//JAVA-deleted //@todo 2 sk TextWriter for different Locales or port IFormatProvider
//    protected TextWriter(IFormatProvider formatProvider)
//    {
//        InternalFormatProvider = formatProvider;
//    }
//
//    public IFormatProvider getFormatProvider()
//    {
//		if (InternalFormatProvider == null)
//			return Thread.CurrentThread.CurrentCulture;
//		else
//			return InternalFormatProvider;
//    }

    // Closes this TextWriter and releases any system resources associated with the
    // TextWriter. Following a call to Close, any operations on the TextWriter
    // may raise exceptions. This default method is empty, but descendant
    // classes can override the method to provide the appropriate
    // functionality.
    public void close() throws Exception
    {
        dispose(true);
        //GC.SuppressFinalize(this); //JAVA-deleted: not applicable to java.
    }

    protected void dispose(boolean disposing) throws Exception
    {
    }


    public void dispose() throws Exception
    {
        dispose(true);
        //GC.SuppressFinalize(this); //JAVA-deleted: not applicable to java.
    }

    // Clears all buffers for this TextWriter and causes any buffered data to be
    // written to the underlying device. This default method is empty, but
    // descendant classes can override the method to provide the appropriate
    // functionality.
    public void flush() throws Exception
    {
    }

    public abstract Encoding getEncoding();

    // Returns the line terminator string used by this TextWriter. The default line
    // terminator string is a carriage return followed by a line feed ("\r\n").
    //
    // Sets the line terminator string for this TextWriter. The line terminator
    // string is written to the text stream whenever one of the
    // WriteLine methods are called. In order for text written by
    // the TextWriter to be readable by a TextReader, only one of the following line
    // terminator strings should be used: "\r", "\n", or "\r\n".
    //
    public String getNewLine()
    {
        return new String(CoreNewLine);
    }

	public void setNewLine(String value)
	{
		if (value == null)
			value = InitialNewLine;
		CoreNewLine = value.toCharArray();
    }


	//JAVA-deleted
//    public static TextWriter Synchronized(TextWriter writer)
//    {
//        if (writer == null)
//            throw new NullPointerException("writer");
//        if (writer instanceof SyncTextWriter)
//            return writer;
//
//        return new SyncTextWriter(writer);
//    }

    // Writes a character to the text stream. This default method is empty,
    // but descendant classes can override the method to provide the
    // appropriate functionality.
    //
    public void write(char value) throws Exception
    {
    }

    // Writes a character array to the text stream. This default method calls
    // Write(char) for each of the characters in the character array.
    // If the character array is null, nothing is written.
    //
    public void write(char[] buffer) throws Exception
    {
        if (buffer != null) write(buffer, 0, buffer.length);
    }

    // Writes a range of a character array to the text stream. This method will
    // write count characters of data into this TextWriter from the
    // buffer character array starting at position index.
    //
    public void write(char[] buffer, int index, int count) throws Exception
    {
        if (buffer == null)
            throw new NullPointerException("buffer");
        if (index < 0)
            throw new IndexOutOfBoundsException("index");
        if (count < 0)
            throw new IndexOutOfBoundsException("count");
        if (buffer.length - index < count)
            throw new IllegalArgumentException("(buffer.length - index) should be less than count");

        for (int i = 0; i < count; i++) write(buffer[index + i]);
    }

    // Writes the text representation of a boolean to the text stream. This
    // method outputs either Boolean.TrueString or Boolean.FalseString.
    //
    public void write(boolean value) throws Exception
    {
	    //JAVA-changed from:
        //Write(value ? Boolean.TrueLiteral : Boolean.FalseLiteral);
        write(value ? "True" : "False");
    }

    // Writes the text representation of an integer to the text stream. The
    // text representation of the given value is produced by calling the
    // Int32.ToString() method.
    //
    public void write(int value) throws Exception
    {
        write(Integer.toString(value));//JAVA was: (value.ToString(FormatProvider));
    }

    // Writes the text representation of an integer to the text stream. The
    // text representation of the given value is produced by calling the
    // UInt32.ToString() method.
    //
    public void write(long value) throws Exception//JAVA was: uint
    {
        write(Long.toString(value));//JAVA was: (value.ToString(FormatProvider));
    }

	//JAVA-deleted: clashes with former 'Write(uint)', see above.
    // Writes the text representation of a long to the text stream. The
    // text representation of the given value is produced by calling the
    // Int64.ToString() method.
    //
//    public void Write(long value)
//    {
//        Write(value.ToString(FormatProvider));
//    }

	//JAVA-deleted: java hasn't analog for ulong.
    // Writes the text representation of an unsigned long to the text
    // stream. The text representation of the given value is produced
    // by calling the UInt64.ToString() method.
    //
//    public virtual void Write(ulong value)
//    {
//        Write(value.ToString(FormatProvider));
//    }

    // Writes the text representation of a float to the text stream. The
    // text representation of the given value is produced by calling the
    // Float.toString(float) method.
    //
    public void write(float value) throws Exception
    {
        write(Float.toString(value));//JAVA was: (value.ToString(FormatProvider));
    }

    // Writes the text representation of a double to the text stream. The
    // text representation of the given value is produced by calling the
    // Double.toString(double) method.
    //
    public void write(double value) throws Exception
    {
        write(Double.toString(value));//JAVA was: (value.ToString(FormatProvider));
    }

	//JAVA-deleted
//    public void Write(BigDecimal value)
//    {
//        Write(BigDecimal.);//JAVA was: (value.ToString(FormatProvider));
//    }

    // Writes a string to the text stream. If the given string is null, nothing
    // is written to the text stream.
    //
    public void write(String value) throws Exception
    {
        if (value != null)
	        write(value.toCharArray());
    }

    // Writes the text representation of an object to the text stream. If the
    // given object is null, nothing is written to the text stream.
    // Otherwise, the object's ToString method is called to produce the
    // string representation, and the resulting string is then written to the
    // output stream.
    //
    public void write(Object value) throws Exception
    {
        if (value != null)
        {
	        //JAVA-changed from:
//            IFormattable f = value as IFormattable;
//            if (f != null)
//                Write(f.ToString(null, FormatProvider));
//            else
//                Write(value.ToString());
	        write(value.toString());
        }
    }



    // Writes out a formatted string.  Uses the same semantics as
    // String.Format.
    //
    public void write(String format, Object ... args) throws Exception
    {
        write(msString.format(format, args));
    }

	//JAVA-deleted: Write(String format, Object ... args) (see above) does the same.
//    // Writes out a formatted string.  Uses the same semantics as
//    // String.Format.
//    //
//    public virtual void Write(String format, Object arg0, Object arg1)
//    {
//        Write(String.Format(FormatProvider, format, arg0, arg1));
//    }
//
//    // Writes out a formatted string.  Uses the same semantics as
//    // String.Format.
//    //
//    public virtual void Write(String format, Object arg0, Object arg1, Object arg2)
//    {
//        Write(String.Format(FormatProvider, format, arg0, arg1, arg2));
//    }
//
//    // Writes out a formatted string.  Uses the same semantics as
//    // String.Format.
//    //
//    public virtual void Write(String format, params Object[] arg)
//    {
//        Write(String.Format(FormatProvider, format, arg));
//    }


    // Writes a line terminator to the text stream. The default line terminator
    // is a carriage return followed by a line feed ("\r\n"), but this value
    // can be changed by setting the NewLine property.
    //
    public void writeLine() throws Exception
    {
        write(CoreNewLine);
    }

    // Writes a character followed by a line terminator to the text stream.
    //
    public void writeLine(char value) throws Exception
    {
        write(value);
        writeLine();
    }

    // Writes an array of characters followed by a line terminator to the text
    // stream.
    //
    public void writeLine(char[] buffer) throws Exception
    {
        write(buffer);
        writeLine();
    }

    // Writes a range of a character array followed by a line terminator to the
    // text stream.
    //
    public void writeLine(char[] buffer, int index, int count) throws Exception
    {
        write(buffer, index, count);
        writeLine();
    }

    // Writes the text representation of a boolean followed by a line
    // terminator to the text stream.
    //
    public void writeLine(boolean value) throws Exception
    {
        write(value);
        writeLine();
    }

    // Writes the text representation of an integer followed by a line
    // terminator to the text stream.
    //
    public void writeLine(int value) throws Exception
    {
        write(value);
        writeLine();
    }

	//JAVA-deleted: WriteLine(long value) does the same
//    // Writes the text representation of an unsigned integer followed by
//    // a line terminator to the text stream.
//    //
//    public virtual void WriteLine(uint value)
//    {
//        Write(value);
//        WriteLine();
//    }

    // Writes the text representation of a long followed by a line terminator
    // to the text stream.
    //
    public void writeLine(long value) throws Exception
    {
        write(value);
        writeLine();
    }

	//JAVA-deleted: ulong not applicable for java
//    // Writes the text representation of an unsigned long followed by
//    // a line terminator to the text stream.
//    //
//    [CLSCompliant(false)]
//    public virtual void WriteLine(ulong value)
//    {
//        Write(value);
//        WriteLine();
//    }

    // Writes the text representation of a float followed by a line terminator
    // to the text stream.
    //
    public void writeLine(float value) throws Exception
    {
        write(value);
        writeLine();
    }

    // Writes the text representation of a double followed by a line terminator
    // to the text stream.
    //
    public void writeLine(double value) throws Exception
    {
        write(value);
        writeLine();
    }

	//JAVA-deleted
//    public virtual void WriteLine(decimal value)
//    {
//        Write(value);
//        WriteLine();
//    }

    // Writes a string followed by a line terminator to the text stream.
    //
    public void writeLine(String value) throws Exception
    {

        if (value == null)
        {
            writeLine();
        }
        else
        {
            // We'd ideally like WriteLine to be atomic, in that one call
            // to WriteLine equals one call to the OS (ie, so writing to
            // console while simultaneously calling printf will guarantee we
            // write out a string and new line chars, without any interference).
            // Additionally, we need to call ToCharArray on Strings anyways,
            // so allocating a char[] here isn't any worse than what we were
            // doing anyways.  We do reduce the number of calls to the
            // backing store this way, potentially.
            int vLen = value.length();
            int nlLen = CoreNewLine.length;
            char[] chars = new char[vLen + nlLen];
	        //JAVA-changed from:
            //value.CopyTo(0, chars, 0, vLen);
	        msString.copyTo(value, 0, chars, 0, vLen);
            // CoreNewLine will almost always be 2 chars, and possibly 1.
            if (nlLen == 2)
            {
                chars[vLen] = CoreNewLine[0];
                chars[vLen + 1] = CoreNewLine[1];
            }
            else if (nlLen == 1)
            {
                chars[vLen] = CoreNewLine[0];
            }
            else
            {
	            //JAVA-changed from:
                //Buffer.InternalBlockCopy(CoreNewLine, 0, chars, vLen * 2, nlLen * 2);
	            System.arraycopy(CoreNewLine, 0, chars, vLen * 2, nlLen * 2);
            }
            write(chars, 0, vLen + nlLen);
        }
        /*
        Write(value);  // We could call Write(String) on StreamWriter...
        WriteLine();
        */
    }

    // Writes the text representation of an object followed by a line
    // terminator to the text stream.
    //
    public void writeLine(Object value) throws Exception
    {
        if (value == null)
        {
            writeLine();
        }
        else
        {
	        //JAVA-changed from:
//            // Call WriteLine(value.ToString), not Write(Object), WriteLine().
//            // This makes calls to WriteLine(Object) atomic.
//            IFormattable f = value as IFormattable;
//            if (f != null)
//                WriteLine(f.ToString(null, FormatProvider));
//            else
//                WriteLine(value.ToString());
			writeLine(value.toString());
        }
    }


	// Writes out a formatted string and a new line.  Uses the same
	// semantics as String.Format.
	//
	public void writeLine(String format, Object arg0) throws Exception
	{
		writeLine(msString.format(format, arg0));
	}

	//JAVA-added instead of three methods (see below).
	public void writeLine(String format, Object... args) throws Exception
	{
		writeLine(msString.format(format, args));
	}

	//JAVA-deleted: WriteLine(String format, Object ... args) does the same, see above.
//    // Writes out a formatted string and a new line.  Uses the same
//    // semantics as String.Format.
//    //
//    public virtual void WriteLine(String format, Object arg0, Object arg1)
//    {
//        WriteLine(String.Format(FormatProvider, format, arg0, arg1));
//    }
//
//    // Writes out a formatted string and a new line.  Uses the same
//    // semantics as String.Format.
//    //
//    public virtual void WriteLine(String format, Object arg0, Object arg1, Object arg2)
//    {
//        WriteLine(String.Format(FormatProvider, format, arg0, arg1, arg2));
//    }
//
//    // Writes out a formatted string and a new line.  Uses the same
//    // semantics as String.Format.
//    //
//    public virtual void WriteLine(String format, params Object[] arg)
//    {
//        WriteLine(String.Format(FormatProvider, format, arg));
//    }

    private static final class NullTextWriter extends TextWriter
    {
        NullTextWriter()
        {
	        super();//JAVA was: CultureInfo.INVARIANT_CULTURE
        }

        public Encoding getEncoding()
        {
            return Encoding.getDefault();
        }

        public void write(char[] buffer, int index, int count)
        {
        }

        public void write(String value)
        {
        }

        // Not strictly necessary, but for perf reasons
        public void writeLine()
        {
        }

        // Not strictly necessary, but for perf reasons
        public void writeLine(String value)
        {
        }

        public void writeLine(Object value)
        {
        }
    }

	//JAVA-deleted
//    final class SyncTextWriter extends TextWriter//, IDisposable
//    {
//        private TextWriter _out;
//
//        SyncTextWriter(TextWriter t)
//        {
//	        super(t.FormatProvider)
//	        _out = t;
//        }
//
//        public Encoding getEncoding()
//        {
//            return _out.getEncoding();
//        }
//
//	    //JAVA-deleted
////        public override IFormatProvider FormatProvider
////        {
////            get { return _out.FormatProvider; }
////        }
//
//        public override String NewLine
//        {
//            [MethodImplAttribute(MethodImplOptions.Synchronized)]
//            get { return _out.NewLine; }
//            [MethodImplAttribute(MethodImplOptions.Synchronized)]
//            set { _out.NewLine = value; }
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Close()
//        {
//            // So that any overriden Close() gets run
//            _out.Close();
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        protected override void Dispose(bool disposing)
//        {
//            // Explicitly pick up a potentially methodimpl'ed Dispose
//            if (disposing)
//                ((IDisposable)_out).Dispose();
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Flush()
//        {
//            _out.Flush();
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(char value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(char[] buffer)
//        {
//            _out.Write(buffer);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(char[] buffer, int index, int count)
//        {
//            _out.Write(buffer, index, count);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(bool value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(int value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(uint value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(long value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(ulong value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(float value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(double value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(Decimal value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(String value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(Object value)
//        {
//            _out.Write(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(String format, Object arg0)
//        {
//            _out.Write(format, arg0);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(String format, Object arg0, Object arg1)
//        {
//            _out.Write(format, arg0, arg1);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(String format, Object arg0, Object arg1, Object arg2)
//        {
//            _out.Write(format, arg0, arg1, arg2);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void Write(String format, Object[] arg)
//        {
//            _out.Write(format, arg);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine()
//        {
//            _out.WriteLine();
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(char value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(decimal value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(char[] buffer)
//        {
//            _out.WriteLine(buffer);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(char[] buffer, int index, int count)
//        {
//            _out.WriteLine(buffer, index, count);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(bool value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(int value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(uint value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(long value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(ulong value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(float value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(double value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(String value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(Object value)
//        {
//            _out.WriteLine(value);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(String format, Object arg0)
//        {
//            _out.WriteLine(format, arg0);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(String format, Object arg0, Object arg1)
//        {
//            _out.WriteLine(format, arg0, arg1);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(String format, Object arg0, Object arg1, Object arg2)
//        {
//            _out.WriteLine(format, arg0, arg1, arg2);
//        }
//
//        [MethodImplAttribute(MethodImplOptions.Synchronized)]
//        public override void WriteLine(String format, Object[] arg)
//        {
//            _out.WriteLine(format, arg);
//        }
//    }
}

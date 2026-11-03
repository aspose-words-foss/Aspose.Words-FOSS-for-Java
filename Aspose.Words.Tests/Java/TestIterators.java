//Konstantin Sidorenko Sep 14, 2006

package com.aspose.words;

import com.aspose.DrColor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import static org.testng.Assert.*;

//JAVA-added

/**
 * Defect 1186 fixed, see http://www.aspose.com/Community/forums/thread/55340.aspx
 *
 * Java Iterator worked well in foreach loops but don't complied to a java Iterator contract.
 * All iterators reworked to comply the java Iterator contract.
 *
 * Later:
 * WORDSJAVA-25810 - Generic collections does not work as expected in 10th version.
 * http://gemini.aspose.com:8042/Default.aspx?p=17&i=25810
 */
@Test
public class TestIterators
{
    //NodeCollection children

	@Test
	public void TestSections() throws Exception
	{
		Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
		Node[] nodes = new Node[3];
        for (int i = 0; i < 3; i++)
        {
            Section section = new Section(doc);
            Body body = new Body(doc);
            body.appendChild(new Paragraph(doc));
            section.appendChild(body);
            nodes[i] = doc.appendChild(section);
        }

		//foreach loop
		int i = 0;
		for (Section section : doc.getSections())
		{
			Assert.assertEquals(nodes[i++], section);
            Assert.assertNotNull(section.getBody());
            for (Node node : section.getBody())
            {
                //iterate through Body's children
                assertTrue(node instanceof Paragraph);
            }
		}

		//classic iterator loop
		i = 0;
		Iterator<Section> it = doc.getSections().iterator();
		while (it.hasNext())
		{
			Assert.assertEquals(nodes[i++], it.next());
		}
	}

	@Test
    public void TestBuildingBlocks() throws Exception
    {
        GlossaryDocument glossary = new GlossaryDocument();

        Node[] nodes = new Node[3];
        for (int i = 0; i < 3; i++)
        {
            BuildingBlock block = new BuildingBlock(glossary);
            nodes[i] = glossary.appendChild(block);

            Section section = new Section(glossary);
            block.appendChild(section);

            Body b = new Body(glossary);
            section.appendChild(b);
        }

        //foreach loop
        int i = 0;
        for (BuildingBlock block : glossary.getBuildingBlocks())
        {
            Assert.assertEquals(nodes[i++], block);
            for (Node node : block)
            {
            }
        }

        //classic iterator loop
        i = 0;
        Iterator<BuildingBlock> it = glossary.getBuildingBlocks().iterator();
        while (it.hasNext())
        {
            BuildingBlock block = it.next();

            Iterator<Node> sectionIterator = block.iterator();
            while (sectionIterator.hasNext()) {
                Section section = (Section) sectionIterator.next();
            }
        }
    }

    @Test
	public void TestCells() throws Exception
	{
		int n;
		String[] cellTexts = new String[4];
		Iterator<Cell> it;

		Document doc = new Document();
		DocumentBuilder builder = new DocumentBuilder(doc);

		//build 4 cells in one row
		for (int i = 0; i < 4; i++)
		{
			builder.insertCell();
			String text = MessageFormat.format("X{0}", i);
			builder.write(text);
			cellTexts[i] = text + "\u0007";
		}
		builder.endRow();
		builder.endTable();

		Row row = (Row)doc.selectSingleNode("//Row[1]");
		CellCollection cells = row.getCells();

		//foreach loop
		n = 0;
		for (Cell cell : cells)
		{
			assertEquals(cellTexts[n], cell.getText());
            for (Node node : cell)
            {
                //iterate through Cell's children
                assertTrue(node instanceof Paragraph);
            }
			n++;
		}
		assertEquals(4, n);

		//classic iterator loop
		n = 0;
		it = cells.iterator();
		while (it.hasNext())
		{
			assertEquals(cellTexts[n], it.next().getText());
			n++;
		}
		assertEquals(4, n);

		//@todo 2 sk //loop that doesn't stop after the end
		boolean hasNext = true;
		it = cells.iterator();
		for (n = 0; n < 7; n++)
		{
			try
			{
				hasNext = it.hasNext();
				Cell cell = it.next();//have to throw when cross iterator's boundaries

				//it is all okay inside iterator's boundaries
				assertTrue(n < 4);
				assertTrue(hasNext);
				assertEquals(cellTexts[n], cell.getText());
			}
			catch (IllegalStateException ex)
			{
				assertTrue(n > 3);
				assertFalse(hasNext);
				assertEquals("Invalid position of the enumerator.", ex.getMessage());
				//after next hasNext() the iterator will restart iteration.
				break;
			}
		}
	}

	//CompositeNode childes

	@Test
	public void TestChildNodeEnumerator() throws Exception
	{
		int n;
		Iterator<Node> it;

		//only 3 child nodes initialized
		Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
		Node sect1 = doc.appendChild(new Section(doc));
		Node sect2 = doc.appendChild(new Section(doc));
		Node sect3 = doc.appendChild(new Section(doc));

		//@todo 2 sk foreach for Document Node iterator (ChildNodeEnumerator)
		//foreach loop
		n = 0;
		for (Node node : doc)
		{
			assertEquals(NodeType.SECTION, node.getNodeType());
			n++;
		}
		assertEquals(3, n);

		//classic iterator loop
		n = 0;
		it = doc.iterator();
		while (it.hasNext())
		{
			assertEquals(NodeType.SECTION, it.next().getNodeType());
			n++;
		}
		assertEquals(3, n);

		//loop that doesn't stop after the end
		boolean hasNext = true;
		it = doc.iterator();
		for (n = 0; n < 7; n++)
		{
			try
			{
				hasNext = it.hasNext();
				int nodeType = it.next().getNodeType();//have to throw when cross iterator's boundaries

				//it is all okay inside iterator's boundaries
				assertTrue(n < 3);
				assertTrue(hasNext);
				assertEquals(NodeType.SECTION, nodeType);
			}
			catch (IllegalStateException ex)
			{
				assertTrue(n > 2);
				assertFalse(hasNext);
				assertEquals("Invalid position of the enumerator.", ex.getMessage());
			}
		}
	}

	private void TestChildNodeCollection(Table collection)
	{
		for (Row entry : collection)
        {
        }
	}

	private void TestChildNodeCollection(Row collection)
	{
		for (Node entry : collection)
        {
        }
	}

	//own iterator

	@Test
	public void TestBorders() throws Exception
	{
		int n;
		Document doc = new Document();
		BorderCollection borders = doc.getFirstSection().getPageSetup().getBorders();
		Iterator<Border> it;

		//foreach loop
		n = 0;
		for (Border border : borders)
		{
			assertEquals(LineStyle.NONE, border.getLineStyle());
			assertEquals(DrColor.EMPTY.toNativeColor(), border.getColor());

			n++;
		}
		assertEquals(4, n);

		//classic iterator loop
		n = 0;
		it = borders.iterator();
		while (it.hasNext())
		{
			Border border = it.next();
			assertEquals(LineStyle.NONE, border.getLineStyle());
			assertEquals(DrColor.EMPTY.toNativeColor(), border.getColor());
			n++;
		}
		assertEquals(4, n);

		//@todo 2 sk foreach for Document Node iterator (ChildNodeEnumerator)
		//loop that doesn't stop after the end
//		boolean hasNext = true;
//		it = borders.iterator();
//		for (n = 0; n < 7; n++)
//		{
//			try
//			{
//				hasNext = it.hasNext();
//				Border border = it.next();//have to throw when cross iterator's boundaries
//
//				//it is all okay inside iterator's boundaries
//				assertTrue(n < 4);
//				assertTrue(hasNext);
//				assertEquals(LineStyle.NONE, border.getLineStyle());
//				assertEquals(DrColor.EMPTY.toNativeColor(), border.getColor());
//			}
//			catch (NoSuchElementException ex)
//			{
//				assertTrue(n > 3);
//				assertFalse(hasNext);
//				assertEquals("Iteration has no more elements.", ex.getMessage());
//			}
//		}
	}

	//Compilation tests: just check that java foreach does not cause syntax errors.

	private void TestCollection(BookmarkCollection collection)
	{
		for (Bookmark entry : collection)
        {
        }
	}

	private void TestCollection(BorderCollection collection)
	{
		for (Border entry : collection)
        {
        }
	}

	private void TestCollection(VariableCollection variableCollection)
	{
		for (Map.Entry entry : variableCollection)
        {
        }
	}

	private void TestCollection(DropDownItemCollection dropDownItemCollection)
	{
		for (String item : dropDownItemCollection)
        {
        }
	}

	private void TestCollection(FormFieldCollection formFieldCollection)
	{
		for (FormField item : formFieldCollection)
        {
        }
	}

	private void TestCollection(FontInfoCollection fontInfoCollection)
	{
		for (FontInfo item : fontInfoCollection)
        {
        }
	}

	private void TestCollection(ListCollection listCollection)
	{
		for (List item : listCollection)
        {
        }
	}

	private void TestCollection(ListLevelCollection listLevelCollection)
	{
		for (ListLevel item : listLevelCollection)
        {
        }
	}

	private void TestCollection(CustomXmlPropertyCollection collection)
	{
		for (CustomXmlProperty item : collection)
        {
        }
	}

	private void TestCollection(DocumentPropertyCollection collection)
	{
		for (DocumentProperty item : collection)
        {
        }
	}

	private void TestCollection(OdsoFieldMapDataCollection collection)
	{
		for (OdsoFieldMapData item : collection)
        {
        }
	}

	private void TestCollection(OdsoRecipientDataCollection collection)
	{
		for (OdsoRecipientData item : collection)
        {
        }
	}

	private void TestCollection(StyleCollection collection)
	{
		for (Style item : collection)
        {
        }
    }

    private void TestCollection(StructuredDocumentTag collection)
    {
        for (Node item : collection)
        {
        }
    }

    private void TestCollection(ShapeBase collection)
    {
        for (Node node : collection)
        {
        }

        if (collection instanceof Shape)
        {
            Shape shape = (Shape) collection;
            for (Node node : shape)
            {
            }
        }
        if (collection instanceof GroupShape)
        {
            GroupShape g = (GroupShape) collection;
            for (Node node : g)
            {
            }
        }
    }

    private void TestCollection(Section collection)
    {
        for (Node node : collection)
        {
        }
    }

    private void TestCollection(OfficeMath collection)
    {
        for (Node node : collection)
        {
        }
    }

    private void TestCollection(Story collection)
    {
        for (Node node : collection)
        {
        }

        if (collection instanceof HeaderFooter)
        {
            HeaderFooter headerFooter = (HeaderFooter) collection;
            for (Node node : headerFooter)
            {
            }
        }
        if (collection instanceof Body)
        {
            Body body = (Body) collection;
            for (Node node : body)
            {
            }
        }
    }

    private void TestCollection(InlineStory collection)
    {
        for (Node node : collection)
        {
        }

        if (collection instanceof Footnote)
        {
            Footnote footnote = (Footnote) collection;
            for (Node node : footnote)
            {
            }
        }
        if (collection instanceof Comment)
        {
            Comment comment = (Comment) collection;
            for (Node node : comment)
            {
            }
        }
    }

    private void TestCollection(Paragraph collection)
    {
        for (Node node : collection)
        {
        }
    }

    private void TestCollection(SmartTag collection)
    {
        for (Node node : collection)
        {
        }
    }

    private void TestCollection(Row collection)
    {
        for (Node node : collection)
        {
        }
    }

    private void TestCollection(Table collection)
    {
        for (Node node : collection)
        {
        }
    }

    private void TestCollection(FootnoteSeparator collection)
    {
        for (Node node : collection)
        {
        }
    }

	//@todo 2 sk XPath?
	//Java added: run over non-CompositNode element with JaxenConstants.EMPTY_ITERATOR (nedeed for XPath)
//	@Test
//	public void TestJaxenEmptyIterator() throws Exception
//	{
//		Document doc = new Document();
//		Run run = new Run(doc);
//		Node node = null;
//
//		int i = 0;
//		for(Node n : run)
//		{
//			i++;
//			node = n;
//		}
//		Assert.assertEquals(i, 0);
//		Assert.assertEquals(node, null);
//	}

}

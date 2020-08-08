package za.ac.sun.cs.coastal.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Class for Markdown generation.
 */
public class MdWriter extends PrintWriter {

	/**
	 * Directory separator for the current operating system.
	 */
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * Produce a {@link Writer} that writes to a file.
	 *
	 * @param fullFilename filename for the destination
	 * @return {@link Writer} that writes to the file
	 * @throws IOException if the file cannot be created
	 */
	private static Writer genWriter(String fullFilename) throws IOException {
		FileOutputStream fos = new FileOutputStream(fullFilename);
		return new OutputStreamWriter(fos, "UTF-8");
	}

	/**
	 * Create a Markdown writer for a new document called "{@code index.md}" in the
	 * given path and with the given title.
	 * 
	 * @param path  path for the new document
	 * @param title title for the new document
	 * @throws IOException if the file cannot be created
	 */
	public MdWriter(String path, String title) throws IOException {
		super(genWriter(path + FILE_SEPARATOR + "index.md"));
		writeFrontMatter(title, "/api/");
	}

	/**
	 * Create a Markdown writer for a new document called "{@code index.md}" in the
	 * given path and with the given title and with an optional table of contents.
	 * 
	 * @param path  path for the new document
	 * @param title title for the new document
	 * @param toc   whether or not a table of contents is generated
	 * @throws IOException if the file cannot be created
	 */
	public MdWriter(String path, String title, boolean toc) throws IOException {
		super(genWriter(path + FILE_SEPARATOR + "index.md"));
		writeFrontMatter(title, "/api/", new Object[][] { { "toc", toc } });
	}

	/**
	 * Create a Markdown writer for a new document with the given name in the given
	 * path and with the given title. The document name should not end in ".md"; it
	 * is added by this constructor.
	 * 
	 * @param path     path for the new document
	 * @param filename name for the new document
	 * @param title    title for the new document
	 * @throws IOException if the file cannot be created
	 */
	public MdWriter(String path, String filename, String title) throws IOException {
		super(genWriter(path + FILE_SEPARATOR + filename + ".md"));
		writeFrontMatter(title, String.format("/api/%s/", filename));
	}

	/**
	 * Create a Markdown writer for a new document with the given name in the given
	 * path and with the given title and with an optional table of contents. The
	 * document name should not end in ".md"; it is added by this constructor.
	 * 
	 * @param path     path for the new document
	 * @param filename name for the new document
	 * @param title    title for the new document
	 * @param toc      whether or not a table of contents is generated
	 * @throws IOException if the file cannot be created
	 */
	public MdWriter(String path, String filename, String title, boolean toc) throws IOException {
		super(genWriter(path + FILE_SEPARATOR + filename + ".md"));
		writeFrontMatter(title, String.format("/api/%s/", filename), new Object[][] { { "toc", toc } });
	}

	/**
	 * Write the Markdown frontmatter. This contains at least two fields: the title
	 * and a permalink. Additional fields may be provided.
	 *
	 * @param title     title of the page
	 * @param permalink canonical link for the page
	 * @param extras    additional fields to include
	 */
	private void writeFrontMatter(String title, String permalink, Object[]... extras) {
		println("---");
		printf("title: %s\n", title);
		printf("permalink: %s\n", permalink);
		for (Object[] extra : extras) {
			printf("%s: %s\n", extra[0].toString(), extra[1].toString());
		}
		println("---\n");
	}

//	/**
//	 * Process a sequence of tags up to a certain point. If {@code before} is -1 or
//	 * less, all tags up to the first <code>@after</code> tag are output; if
//	 * {@code before} is 1 or more, all tags after the first <code>@after</code>
//	 * tags are output; if "{@code before}" is 0, all tags are output.
//	 *
//	 * @param tags   array of tags
//	 * @param before flag for treatment of "{@code @after}" tag
//	 * @return reference to this object
//	 */
//	private MdWriter tags(Tag[] tags, int before) {
//		int i = 0;
//		if (before > 0) {
//			while ((i < tags.length) && !tags[i].name().equals("@after")) {
//				i++;
//			}
//		}
//		while (i < tags.length) {
//			switch (tags[i].name()) {
//			case "@after":
//				if (before < 0) {
//					return this;
//				}
//				break;
//			case "Text":
//				cdata(tags[i].text());
//				break;
//			case "@prejava":
//				prejava().cdata(tags[i].text()).prejavaEnd();
//				break;
//			case "@link":
//				code().az(tags[i].holder(), null, tags[i].text(), tags[i].text()).codeEnd();
//				break;
//			case "@code":
//				code().cdata(tags[i].text()).codeEnd();
//				break;
//			default:
//				cdata("TAG(" + tags[i].name() + ", " + tags[i].text() + ")");
//				break;
//			}
//			i++;
//		}
//		return this;
//	}
//
//	/**
//	 * Write an array of tags one by one.
//	 *
//	 * @param tags array of tags
//	 * @return reference to this object
//	 */
//	private MdWriter tags(Tag[] tags) {
//		return tags(tags, 0);
//	}

	/**
	 * Write an A element with the given link and text.
	 *
	 * @param href link for the element
	 * @param text text for the element
	 * @return reference to this object
	 */
	public MdWriter a(String href, String text) {
		printf("<a href=\"%s\">%s</a>", href(href), text);
		return this;
	}

	/**
	 * Write an A element with the given link, text, and class.
	 *
	 * @param clas CSS class of span
	 * @param href link for the element
	 * @param text text for the element
	 * @return reference to this object
	 */
	public MdWriter a(String clas, String href, String text) {
		printf("<a class=\"%s\" href=\"%s\">%s</a>\n", clas, href(href), text);
		return this;
	}

	/**
	 * Write an A element with the given link, text, and class for an internal link.
	 * Markdown syntax is used to turn the link into a relative link.
	 *
	 * @param clas CSS class of span
	 * @param href link for the element
	 * @param text text for the element
	 * @return reference to this object
	 */
	public MdWriter ax(String clas, String href, String text) {
		printf("<a class=\"%s\" href=\"{{ '%s' | relative_url }}\">%s</a>\n", clas, href, text);
		return this;
	}

	/**
	 * Write an A element with the given link, text, and class for a link that is
	 * relative to the given document. If the link starts with "{@code #}", it is
	 * assumed to be an anchor inside the document. Otherwise, the document is
	 * ignored, and the method behaves just as {@link #a(String, String, String)}.
	 *
	 * @param doc  document for relative links
	 * @param clas CSS class of span
	 * @param href link for the element
	 * @param text text for the element
	 * @return reference to this object
	 */
	public MdWriter az(String base, String clas, String href, String text) {
		String classSpec = "";
		if (clas != null) {
			classSpec = String.format(" class=\"%s\"", clas);
		}
		if (href.startsWith("#")) {
			printf("<a%s href=\"%s\">%s</a>\n", classSpec, href(base + href), text.substring(1));
			return this;
		} else {
			printf("<a%s href=\"%s\">%s</a>\n", classSpec, href(href), text);
			return this;
		}
	}

	/**
	 * Write an HREF attribute for an HTML A element.
	 *
	 * @param href link to include in attribute
	 * @return reference to this object
	 */
	private String href(String href) {
		int index = href.indexOf('#');
		if (index == -1) {
			return String.format("{{ '/api/%s/' | relative_url }}", href);
		} else {
			String anchor = href.substring(index);
			String base = href.substring(0, index);
			return String.format("{{ '/api/%s/' | relative_url }}%s", base, anchor);
		}
	}

	/**
	 * Write raw text.
	 *
	 * @param data text to output
	 * @return reference to this object
	 */
	public MdWriter cdata(String data) {
		print(data);
		return this;
	}

	/**
	 * Write the opening Markdown tags for a piece of Java quote.
	 *
	 * @return reference to this object
	 */
	public MdWriter prejava() {
		println("<div markdown=\"1\">");
		println("~~~java");
		return this;
	}

	/**
	 * Write the closing Markdown tags for a piece of Java quote.
	 *
	 * @return reference to this object
	 */
	public MdWriter prejavaEnd() {
		println("~~~");
		println("</div>");
		return this;
	}

	/**
	 * Write the opening tag of a paragraph.
	 *
	 * @return reference to this object
	 */
	public MdWriter p() {
		return generic("p");
	}

	/**
	 * Write the opening tag of a paragraph with a given class.
	 *
	 * @param clas CSS class of paragraph
	 * @return reference to this object
	 */
	public MdWriter p(String clas) {
		return generic(clas, "p");
	}

	/**
	 * Write the closing tag of a paragraph.
	 *
	 * @return reference to this object
	 */
	public MdWriter pEnd() {
		return genericEnd("p");
	}

	/**
	 * Write the opening tag of a span of text.
	 *
	 * @return reference to this object
	 */
	public MdWriter span() {
		return generic("span", false);
	}

	/**
	 * Write the opening tag of a span of text with a given class.
	 *
	 * @param clas CSS class of span
	 * @return reference to this object
	 */
	public MdWriter span(String clas) {
		return generic(clas, "span", false);
	}

	/**
	 * Write the closing tag of a span of text.
	 *
	 * @return reference to this object
	 */
	public MdWriter spanEnd() {
		return genericEnd("span", false);
	}

	/**
	 * Write the opening tag of a literal span of text.
	 *
	 * @return reference to this object
	 */
	public MdWriter code() {
		return generic("code", false);
	}

	/**
	 * Write the opening tag of a literal span of text with a given class.
	 *
	 * @param clas CSS class of span
	 * @return reference to this object
	 */
	public MdWriter code(String clas) {
		return generic(clas, "code", false);
	}

	/**
	 * Write the closing tag of a literal span of text.
	 *
	 * @return reference to this object
	 */
	public MdWriter codeEnd() {
		return genericEnd("code", false);
	}

	/**
	 * Write the opening tag of an HTML element.
	 *
	 * @param element element name
	 * @return reference to this object
	 */
	private MdWriter generic(String element) {
		return generic(element, true);
	}

	/**
	 * Write the opening tag of an HTML element with a given class.
	 *
	 * @param clas    CSS class of element
	 * @param element element name
	 * @return reference to this object
	 */
	private MdWriter generic(String clas, String element) {
		return generic(clas, element, true);
	}

	/**
	 * Write the closing tag of a literal span of text.
	 *
	 * @param element element name
	 * @return reference to this object
	 */
	private MdWriter genericEnd(String element) {
		return genericEnd(element, true);
	}

	/**
	 * Write the opening tag of an HTML element followed by an optional newline.
	 *
	 * @param element element name
	 * @param newline whether or not to write a newline
	 * @return reference to this object
	 */
	private MdWriter generic(String element, boolean newline) {
		printf("<%s>", element);
		if (newline) {
			printf("\n");
		}
		return this;
	}

	/**
	 * Write the opening tag of an HTML element with a given class followed by an
	 * optional newline.
	 *
	 * @param clas    CSS class of element
	 * @param element element name
	 * @param newline whether or not to write a newline
	 * @return reference to this object
	 */
	private MdWriter generic(String clas, String element, boolean newline) {
		if (clas == null) {
			printf("<%s>", element);
		} else {
			printf("<%s class=\"%s\">", element, clas);
		}
		if (newline) {
			printf("\n");
		}
		return this;
	}

	/**
	 * Write the closing tag of a literal span of text followed by an optional
	 * newline.
	 *
	 * @param element element name
	 * @param newline whether or not to write a newline
	 * @return reference to this object
	 */
	private MdWriter genericEnd(String element, boolean newline) {
		printf("</%s>", element);
		if (newline) {
			printf("\n");
		}
		return this;
	}

	// ---------- HEADINGS ----------------------------------------

	/**
	 * Write an H1 tag with the given text.
	 *
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h1(String heading) {
		return hn("h1", heading);
	}

	/**
	 * Write an H1 tag with the given text and class.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h1(String clas, String heading) {
		return hn("h1", clas, heading);
	}

	/**
	 * Write an H1 tag with the given text, class, and name attribute.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @param name    name attribute
	 * @return reference to this object
	 * @return
	 */
	public MdWriter h1(String clas, String heading, String name) {
		return hn("h1", clas, heading, name);
	}

	/**
	 * Write an H2 tag with the given text.
	 *
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h2(String heading) {
		return hn("h2", heading);
	}

	/**
	 * Write an H2 tag with the given text and class.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h2(String clas, String heading) {
		return hn("h2", clas, heading);
	}

	/**
	 * Write an H2 tag with the given text, class, and name attribute.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @param name    name attribute
	 * @return reference to this object
	 * @return
	 */
	public MdWriter h2(String clas, String heading, String name) {
		return hn("h2", clas, heading, name);
	}

	/**
	 * Write an H3 tag with the given text.
	 *
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h3(String heading) {
		return hn("h3", heading);
	}

	/**
	 * Write an H3 tag with the given text and class.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h3(String clas, String heading) {
		return hn("h3", clas, heading);
	}

	/**
	 * Write an H3 tag with the given text, class, and name attribute.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @param name    name attribute
	 * @return reference to this object
	 * @return
	 */
	public MdWriter h3(String clas, String heading, String name) {
		return hn("h3", clas, heading, name);
	}

	/**
	 * Write an H4 tag with the given text.
	 *
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h4(String heading) {
		return hn("h4", heading);
	}

	/**
	 * Write an H4 tag with the given text and class.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	public MdWriter h4(String clas, String heading) {
		return hn("h4", clas, heading);
	}

	/**
	 * Write an H4 tag with the given text, class, and name attribute.
	 *
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @param name    name attribute
	 * @return reference to this object
	 * @return
	 */
	public MdWriter h4(String clas, String heading, String name) {
		return hn("h4", clas, heading, name);
	}

	/**
	 * Write an Hx tag with the given text.
	 *
	 * @param hn      heading element name
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	private MdWriter hn(String hn, String heading) {
		printf("<%s>%s</%s>\n", hn, heading, hn);
		return this;
	}

	/**
	 * Write an Hx tag with the given text and class.
	 *
	 * @param hn      heading element name
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @return reference to this object
	 */
	private MdWriter hn(String hn, String clas, String heading) {
		if (clas == null) {
			printf("<%s>%s</%s>\n", hn, heading, hn);
		} else {
			printf("<%s class=\"%s\">%s</%s>\n", hn, clas, heading, hn);
		}
		return this;
	}

	/**
	 * Write an Hx tag with the given text, class, and name attribute.
	 *
	 * @param hn      heading element name
	 * @param clas    CSS class of heading
	 * @param heading text for the heading
	 * @param name    name attribute
	 * @return reference to this object
	 */
	private MdWriter hn(String hn, String clas, String heading, String name) {
		if (clas == null) {
			printf("<%s><a class=\"anchor\" name=\"%s\"></a>%s</%s>\n", hn, name, heading, hn);
		} else {
			printf("<%s class=\"%s\"><a class=\"anchor\" name=\"%s\"></a>%s</%s>\n", hn, clas, name, heading, hn);
		}
		return this;
	}

	// ---------- LISTS ----------------------------------------

	/**
	 * Write the opening tag of an HTML unnumbered list.
	 *
	 * @return reference to this object
	 */
	public MdWriter ul() {
		return generic("ul");
	}

	/**
	 * Write the opening tag of an HTML unnumbered list with the given class.
	 *
	 * @param clas CSS class of list
	 * @return reference to this object
	 */
	public MdWriter ul(String clas) {
		return generic(clas, "ul");
	}

	/**
	 * Write the closing tag of an HTML unnumbered list.
	 *
	 * @return reference to this object
	 */
	public MdWriter ulEnd() {
		return genericEnd("ul");
	}

	/**
	 * Write the opening tag of an HTML list item.
	 *
	 * @return reference to this object
	 */
	public MdWriter li() {
		return generic("li");
	}

	/**
	 * Write the opening tag of an HTML list item with the given class.
	 *
	 * @param clas CSS class of list item
	 * @return reference to this object
	 */
	public MdWriter li(String clas) {
		return generic(clas, "li");
	}

	/**
	 * Write the closing tag of an HTML list item.
	 *
	 * @return reference to this object
	 */
	public MdWriter liEnd() {
		return genericEnd("li");
	}

	// ---------- SECTION ----------------------------------------

	/**
	 * Write the opening tag of an HTML section.
	 *
	 * @return reference to this object
	 */
	public MdWriter section() {
		return generic("section");
	}

	/**
	 * Write the opening tag of an HTML section with the given class.
	 *
	 * @param clas CSS class of section
	 * @return reference to this object
	 */
	public MdWriter section(String clas) {
		return generic(clas, "section");
	}

	/**
	 * Write the closing tag of an HTML section.
	 *
	 * @return reference to this object
	 */
	public MdWriter sectionEnd() {
		return genericEnd("section");
	}

	// ---------- TABLES ----------------------------------------

	/**
	 * Write the opening tag of an HTML table.
	 *
	 * @return reference to this object
	 */
	public MdWriter table() {
		return generic("table");
	}

	/**
	 * Write the opening tag of an HTML table with the given class.
	 *
	 * @param clas CSS class of table
	 * @return reference to this object
	 */
	public MdWriter table(String clas) {
		return generic(clas, "table");
	}

	/**
	 * Write the closing tag of an HTML table.
	 *
	 * @return reference to this object
	 */
	public MdWriter tableEnd() {
		return genericEnd("table");
	}

	/**
	 * Write the opening tag of an HTML table heading.
	 *
	 * @return reference to this object
	 */
	public MdWriter thead() {
		return generic("thead");
	}

	/**
	 * Write the opening tag of an HTML table heading with the given class.
	 *
	 * @param clas CSS class of table heading
	 * @return reference to this object
	 */
	public MdWriter thead(String clas) {
		return generic(clas, "thead");
	}

	/**
	 * Write the closing tag of an HTML table heading.
	 *
	 * @return reference to this object
	 */
	public MdWriter theadEnd() {
		return genericEnd("thead");
	}

	/**
	 * Write the opening tag of an HTML table body.
	 *
	 * @return reference to this object
	 */
	public MdWriter tbody() {
		return generic("tbody");
	}

	/**
	 * Write the opening tag of an HTML table body with the given class.
	 *
	 * @param clas CSS class of body
	 * @return reference to this object
	 */
	public MdWriter tbody(String clas) {
		return generic(clas, "tbody");
	}

	/**
	 * Write the closing tag of an HTML table body.
	 *
	 * @return reference to this object
	 */
	public MdWriter tbodyEnd() {
		return genericEnd("tbody");
	}

	/**
	 * Write the opening tag of an HTML table row.
	 *
	 * @return reference to this object
	 */
	public MdWriter tr() {
		return generic("tr");
	}

	/**
	 * Write the opening tag of an HTML table row with the given class.
	 *
	 * @param clas CSS class of row
	 * @return reference to this object
	 */
	public MdWriter tr(String clas) {
		return generic(clas, "tr");
	}

	/**
	 * Write the closing tag of an HTML table row.
	 *
	 * @return reference to this object
	 */
	public MdWriter trEnd() {
		return genericEnd("tr");
	}

	/**
	 * Write the opening tag of an HTML table heading cell.
	 *
	 * @return reference to this object
	 */
	public MdWriter th() {
		return generic("th");
	}

	/**
	 * Write the opening tag of an HTML table heading cell with the given class.
	 *
	 * @param clas CSS class of cell
	 * @return reference to this object
	 */
	public MdWriter th(String clas) {
		return generic(clas, "th");
	}

	/**
	 * Write the closing tag of an HTML table heading cell.
	 *
	 * @return reference to this object
	 */
	public MdWriter thEnd() {
		return genericEnd("th");
	}

	/**
	 * Write the opening tag of an HTML table data cell.
	 *
	 * @return reference to this object
	 */
	public MdWriter td() {
		return generic("td");
	}

	/**
	 * Write the opening tag of an HTML table data cell with the given class.
	 *
	 * @param clas CSS class of cell
	 * @return reference to this object
	 */
	public MdWriter td(String clas) {
		return generic(clas, "td");
	}

	/**
	 * Write the closing tag of an HTML table data cell.
	 *
	 * @return reference to this object
	 */
	public MdWriter tdEnd() {
		return genericEnd("td");
	}

}

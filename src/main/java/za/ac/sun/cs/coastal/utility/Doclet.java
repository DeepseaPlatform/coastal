package za.ac.sun.cs.coastal.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

public class Doclet {

	private static Doclet doclet;

	private static Doclet getDoclet() {
		if (doclet == null) {
			doclet = new Doclet();
		}
		return doclet;
	}

	public static boolean start(RootDoc root) {
		return getDoclet().startDoc(root);
	}

	public static int optionLength(String option) {
		return getDoclet().getOptionLength(option);
	}

	public static boolean validOptions(String[][] options, DocErrorReporter reporter) {
		return getDoclet().areValidOptions(options, reporter);
	}

	public static LanguageVersion languageVersion() {
		return getDoclet().getLanguageVersion();
	}

	// ======================================================================
	//
	// DOCLET CLASS
	//
	// ======================================================================

	private String destination = ".";

	private PackageDoc[] coastalPackages;
	
	private Set<ClassDoc> coastalClasses = new HashSet<>();
	
	private PackageDoc[] examplePackages;

	private Set<ClassDoc> exampleClasses = new HashSet<>();
	
	public Doclet() {
	}

	private boolean startDoc(RootDoc root) {
		try {
			setOptions(root.options());
			initArrays(root);
			generatePackageSummary();
		} catch (Fault f) {
			root.printError(f.getMessage());
			return false;
		} catch (IOException e) {
			root.printError(e.getMessage());
			return false;
		}
		root.printNotice("Done");
		return true;
	}

	private int getOptionLength(String option) {
		option = option.toLowerCase();
		if (option.equals("-author") || option.equals("-docfilessubdirs") || option.equals("-javafx")
				|| option.equals("-keywords") || option.equals("-linksource") || option.equals("-nocomment")
				|| option.equals("-nodeprecated") || option.equals("-nodeprecatedlist") || option.equals("-nohelp")
				|| option.equals("-noindex") || option.equals("-nonavbar") || option.equals("-nosince")
				|| option.equals("-notimestamp") || option.equals("-notree") || option.equals("-quiet")
				|| option.equals("-serialwarn") || option.equals("-splitindex") || option.equals("-use")
				|| option.equals("-version") || option.equals("-xnodate")) {
			return 1;
		} else if (option.equals("-charset") || option.equals("-bottom") || option.equals("-d")
				|| option.equals("-docencoding") || option.equals("-doctitle") || option.equals("-encoding")
				|| option.equals("-excludedocfilessubdir") || option.equals("-footer") || option.equals("-header")
				|| option.equals("-helpfile") || option.equals("-link") || option.equals("-noqualifier")
				|| option.equals("-output") || option.equals("-sourcepath") || option.equals("-sourcetab")
				|| option.equals("-stylesheetfile") || option.equals("-tag") || option.equals("-taglet")
				|| option.equals("-tagletpath") || option.equals("-top") || option.equals("-windowtitle")
				|| option.equals("-xprofilespath")) {
			return 2;
		} else if (option.equals("-group") || option.equals("-linkoffline")) {
			return 3;
		} else {
			return -1; // indicate we don't know about it
		}
	}

	private boolean areValidOptions(String[][] options, DocErrorReporter reporter) {
		return true;
	}

	private LanguageVersion getLanguageVersion() {
		return LanguageVersion.JAVA_1_5;
	}

	private void setOptions(String[][] options) throws Fault {
		for (int i = 0, n = options.length; i < n; i++) {
			String option = options[i][0];
			if (option.equals("-d")) {
				destination = options[i][1];
				new File(destination).mkdirs();
			}
		}
	}

	private void initArrays(RootDoc root) {
		Set<PackageDoc> coastalSet = new HashSet<>();
		Set<PackageDoc> exampleSet = new HashSet<>();
		for (ClassDoc classDoc : root.classes()) {
			PackageDoc packag = classDoc.containingPackage();
			if (packag.name().startsWith("za.ac.sun.cs.coastal")) {
				coastalSet.add(packag);
				coastalClasses.add(classDoc);
			} else if (packag.name().startsWith("examples.")) {
				exampleSet.add(packag);
				exampleClasses.add(classDoc);
			}
		}
		coastalPackages = sortList(coastalSet, new PackageDoc[] {});
		examplePackages = sortList(exampleSet, new PackageDoc[] {});
	}
	
	private void generatePackageSummary() throws IOException {
		MdWriter writer = new MdWriter(destination, "COASTAL", false);
		writer.section("sidebar").sectionEnd();
		writer.section("main").h1("{{ page.title | escape }}");
		writer.table("packages").thead().tr();
		writer.th().cdata("Package").thEnd();
		writer.th().cdata("Description").thEnd();
		writer.trEnd().theadEnd().tbody();
		for (PackageDoc packageDoc : coastalPackages) {
			writer.tr();
			writer.td().a(packageDoc.name(), packageDoc.name()).tdEnd();
			writer.td().tags(packageDoc.firstSentenceTags()).tdEnd();
			writer.trEnd();
			generatePackage(packageDoc);
		}
		writer.tbodyEnd().tableEnd();
		writer.sectionEnd();
		writer.close();
	}

	private void generatePackage(PackageDoc packageDoc) throws IOException {
		MdWriter writer = new MdWriter(destination, packageDoc.name(), packageDoc.name());
		generatePackageSidebar(writer);
		writer.section("main").h1("{{ page.title | escape }}");
		writer.tags(packageDoc.inlineTags());
		writer.table("classes").thead().tr();
		writer.th().cdata("Class").thEnd();
		writer.th().cdata("Description").thEnd();
		writer.trEnd().theadEnd().tbody();
		ClassDoc[] classes = sortList(packageDoc.allClasses(), new ClassDoc[] {});
		for (ClassDoc classDoc : classes) {
			writer.tr();
			writer.td().a(classDoc.name(), classDoc.name()).tdEnd();
			writer.td().tags(classDoc.firstSentenceTags()).tdEnd();
			writer.trEnd();
			generateClass(classDoc);
		}
		writer.tbodyEnd().tableEnd();
		writer.sectionEnd().close();
	}

	private void generateClass(ClassDoc classDoc) throws IOException {
		MdWriter writer = new MdWriter(destination, classDoc.name(), classDoc.name(), true);
		generatePackageSidebar(writer);
		writer.section("main").h1("{{ page.title | escape }}");
		writer.tags(classDoc.inlineTags());
		writer.sectionEnd();
		generateClassSidebar(writer, classDoc);
		writer.close();
	}

/*
	
          <section class="toc">
            <ul class="section-nav">
              <li class="toc-entry toc-h2"><a href="#branches-choices-and-paths">Branches, choices, and paths</a>
                <ul>
                  <li class="toc-entry toc-h3"><a href="#brances">Brances</a></li>
                  <li class="toc-entry toc-h3"><a href="#choices">Choices</a></li>
                  <li class="toc-entry toc-h3"><a href="#paths">Paths</a></li>
                  <li class="toc-entry toc-h3"><a href="#example">Example</a></li>
                </ul>
              </li>
              <li class="toc-entry toc-h2"><a href="#executions-and-inputs">Executions and inputs</a>
                <ul>
                  <li class="toc-entry toc-h3"><a href="#executions">Executions</a></li>
                </ul>
              </li>
              <li class="toc-entry toc-h2"><a href="#the-pathtree-and-pathtreenode">The PathTree and PathTreeNode</a></li>
            </ul>
          </section>
	
	
*/
	private void generateClassSidebar(MdWriter writer, ClassDoc classDoc) throws IOException {
		writer.section("toc").ul("section-nav");
		writer.ulEnd().sectionEnd();
	}

	private void generatePackageSidebar(MdWriter writer) throws IOException {
		writer.section("sidebar");
		writer.ax("top", "/api/", "API home");
		writer.h3("COASTAL").ul();
		for (PackageDoc packageDoc : coastalPackages) {
			writer.li().a(packageDoc.name(), packageDoc.name()).liEnd();
		}
		writer.ulEnd().h3("Examples").ul();
		for (PackageDoc packageDoc : examplePackages) {
			writer.li().a(packageDoc.name(), packageDoc.name()).liEnd();
		}
		writer.ulEnd().sectionEnd();
	}
	
	private <T> T[] sortList(Collection<T> collection, T[] prototype) {
		SortedSet<T> sortedSet = new TreeSet<>(collection);
		return sortedSet.toArray(prototype);
	}

	private <T> T[] sortList(T[] collection, T[] prototype) {
		SortedSet<T> sortedSet = new TreeSet<>();
		for (T t : collection) {
			sortedSet.add(t);
		}
		return sortedSet.toArray(prototype);
	}
	
	// ======================================================================
	//
	// FAULT CLASS
	//
	// ======================================================================

	private static class Fault extends Exception {

		private static final long serialVersionUID = 0;

		Fault(String msg) {
			super(msg);
		}

		Fault(String msg, Exception cause) {
			super(msg, cause);
		}
	}

	// ======================================================================
	//
	// MDWRITER CLASS
	//
	// ======================================================================

	private static class MdWriter extends PrintWriter {

		private static final String fileseparator = System.getProperty("file.separator");

		public MdWriter(String path, String title) throws IOException {
			super(genWriter(path + fileseparator + "index.md"));
			writeFrontMatter(title, "/api/");
		}
		
		public MdWriter(String path, String title, boolean toc) throws IOException {
			super(genWriter(path + fileseparator + "index.md"));
			writeFrontMatter(title, "/api/", new Object[][] { { "toc", toc } });
		}
		
		public MdWriter(String path, String filename, String title) throws IOException {
			super(genWriter(path + fileseparator + filename + ".md"));
			writeFrontMatter(title, String.format("/api/%s/", filename));
		}

		public MdWriter(String path, String filename, String title, boolean toc) throws IOException {
			super(genWriter(path + fileseparator + filename + ".md"));
			writeFrontMatter(title, String.format("/api/%s/", filename), new Object[][] { { "toc", toc } });
		}
		
		private void writeFrontMatter(String title, String permalink, Object[]... extras) {
			println("---");
			printf("title: %s\n", title);
			printf("permalink: %s\n", permalink);
			for (Object[] extra : extras) {
				printf("%s: %s\n", extra[0].toString(), extra[1].toString());
			}
			println("---\n");
		}

		private static Writer genWriter(String fullFilename) throws IOException {
			FileOutputStream fos = new FileOutputStream(fullFilename);
			return new OutputStreamWriter(fos, "UTF-8");
		}

		private MdWriter tags(Tag[] tags) {
			for (Tag tag : tags) {
				switch (tag.name()) {
				case "Text":
					cdata(tag.text());
					break;
				case "@prejava":
					cdata("<div markdown=\"1\">\n~~~java\n" + tag.text() + "~~~\n</div>\n");
					break;
				default:
					cdata("TAG(" + tag.kind() + ", " + tag.text() + ")");
					break;
				}
			}
			return this;
		}

		public MdWriter a(String href, String text) {
			printf("<a href=\"{{ '/api/%s/' | relative_url }}\">%s</a>", href, text);
			return this;
		}
		
		public MdWriter a(String clas, String href, String text) {
			printf("<a class=\"%s\" href=\"{{ '/api/%s/' | relative_url }}\">%s</a>", clas, href, text);
			return this;
		}
		
		public MdWriter ax(String clas, String href, String text) {
			printf("<a class=\"%s\" href=\"{{ '%s' | relative_url }}\">%s</a>", clas, href, text);
			return this;
		}
		
		public MdWriter cdata(String data) {
			print(data);
			return this;
		}
		
		// ---------- HEADINGS ----------------------------------------
				
		public MdWriter h1(String heading) {
			printf("<h1>%s</h1>\n", heading);
			return this;
		}
		
		public MdWriter h1(String heading, String clas) {
			printf("<h1 class=\"%s\">%s</h1>\n", clas, heading);
			return this;
		}
		
		public MdWriter h3(String heading) {
			printf("<h3>%s</h3>\n", heading);
			return this;
		}
		
		public MdWriter h3(String heading, String clas) {
			printf("<h3 class=\"%s\">%s</h3>\n", clas, heading);
			return this;
		}
		
		// ---------- LISTS ----------------------------------------
		
		public MdWriter ul() {
			println("<ul>");
			return this;
		}

		public MdWriter ul(String clas) {
			printf("<ul class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter ulEnd() {
			println("</ul>");
			return this;
		}

		public MdWriter li() {
			println("<li>");
			return this;
		}

		public MdWriter li(String clas) {
			printf("<li class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter liEnd() {
			println("</li>");
			return this;
		}
		
		// ---------- SECTION ----------------------------------------
		
		public MdWriter section() {
			println("<section>");
			return this;
		}

		public MdWriter section(String clas) {
			printf("<section class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter sectionEnd() {
			println("</section>");
			return this;
		}

		// ---------- TABLES ----------------------------------------
		
		public MdWriter table() {
			println("<table>");
			return this;
		}

		public MdWriter table(String clas) {
			printf("<table class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter tableEnd() {
			println("</table>");
			return this;
		}

		public MdWriter thead() {
			println("<thead>");
			return this;
		}
		
		public MdWriter thead(String clas) {
			printf("<thead class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter theadEnd() {
			println("</thead>");
			return this;
		}
		
		public MdWriter tbody() {
			println("<tbody>");
			return this;
		}
		
		public MdWriter tbody(String clas) {
			printf("<tbody class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter tbodyEnd() {
			println("</tbody>");
			return this;
		}
		
		public MdWriter tr() {
			println("<tr>");
			return this;
		}

		public MdWriter tr(String clas) {
			printf("<tr class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter trEnd() {
			println("</tr>");
			return this;
		}

		public MdWriter th() {
			println("<th>");
			return this;
		}
		
		public MdWriter th(String clas) {
			printf("<th class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter thEnd() {
			println("</th>");
			return this;
		}
		
		public MdWriter td() {
			println("<td>");
			return this;
		}

		public MdWriter td(String clas) {
			printf("<td class=\"%s\">\n", clas);
			return this;
		}
		
		public MdWriter tdEnd() {
			println("</td>");
			return this;
		}

	}

}

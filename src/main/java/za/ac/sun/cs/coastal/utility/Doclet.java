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
import java.util.StringTokenizer;
import java.util.TreeSet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
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

	private final Set<String> excludedQualifiers = new HashSet<>();

	private PackageDoc[] coastalPackages;

	private final Set<ClassDoc> coastalClasses = new HashSet<>();

	private PackageDoc[] examplePackages;

	private final Set<ClassDoc> exampleClasses = new HashSet<>();

	public Doclet() {
	}

	private boolean startDoc(RootDoc root) {
		try {
			setOptions(root.options());
			initArrays(root);
			generatePackageSummary();
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

	private void setOptions(String[][] options) {
		for (int i = 0, n = options.length; i < n; i++) {
			String option = options[i][0];
			if (option.equals("-d")) {
				destination = options[i][1];
				new File(destination).mkdirs();
			} else if (option.equals("-noqualifier")) {
				addToSet(excludedQualifiers, options[i][1]);
			}
		}
	}

	private void addToSet(Set<String> s, String str) {
		StringTokenizer st = new StringTokenizer(str, ":");
		String current;
		while (st.hasMoreTokens()) {
			current = st.nextToken();
			s.add(current);
		}
	}

	private String excludeQualifier(String qualifier) {
		for (String excludedQualifier : excludedQualifiers) {
			if (excludedQualifier.endsWith("*")) {
				if (qualifier.startsWith(excludedQualifier.substring(0, excludedQualifier.length() - 1))) {
					int index = qualifier.lastIndexOf(".", qualifier.length() + 1 - 1);
					return qualifier.substring(index + 1);
				}
			}
		}
		int index = qualifier.length() + 1;
		while ((index = qualifier.lastIndexOf(".", index - 1)) != -1) {
			String prefix = qualifier.substring(0, index + 1);
			if (excludedQualifiers.contains(prefix)) {
				return qualifier.substring(index + 1);
			}
		}
		return qualifier;
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
		writer.section("main package").h1("{{ page.title | escape }}");
		writer.tags(packageDoc.inlineTags(), -1);
		int interfaceCount = 0;
		ClassDoc[] interfaces = sortList(packageDoc.interfaces(), new ClassDoc[] {});
		if (interfaces.length > 0) {
			writer.h2("Interfaces").table("classes").tbody();
			for (ClassDoc interfac : interfaces) {
				writer.tr();
				writer.td().a(interfac.name(), interfac.name()).tdEnd();
				writer.td().tags(interfac.firstSentenceTags()).tdEnd();
				writer.trEnd();
				generateClass(interfac);
				interfaceCount++;
			}
			writer.tbodyEnd().tableEnd();
		}
		if (interfaceCount > 0) {
			ClassDoc[] classes = sortList(packageDoc.allClasses(), new ClassDoc[] {});
			writer.h2("Classes").table("classes").tbody();
			for (ClassDoc classDoc : classes) {
				if (classDoc.isInterface()) {
					continue;
				}
				writer.tr();
				writer.td().a(classDoc.name(), classDoc.name()).tdEnd();
				writer.td().tags(classDoc.firstSentenceTags()).tdEnd();
				writer.trEnd();
				generateClass(classDoc);
			}
			writer.tbodyEnd().tableEnd();
		}
//		if (packageDoc.tags("@after").length > 0) {
		if (hasAfterTag(packageDoc.inlineTags())) {
			writer.h2("Description").tags(packageDoc.inlineTags(), 1);
		}
//		writer.table("classes").thead().tr();
//		writer.th().cdata("Class").thEnd();
//		writer.th().cdata("Description").thEnd();
//		writer.trEnd().theadEnd().tbody();
//		ClassDoc[] classes = sortList(packageDoc.allClasses(), new ClassDoc[] {});
//		for (ClassDoc classDoc : classes) {
//			writer.tr();
//			writer.td().a(classDoc.name(), classDoc.name()).tdEnd();
//			writer.td().tags(classDoc.firstSentenceTags()).tdEnd();
//			writer.trEnd();
//			generateClass(classDoc);
//		}
//		writer.tbodyEnd().tableEnd();
		writer.sectionEnd().close();
	}

	private boolean hasAfterTag(Tag[] inlineTags) {
		for (Tag tag : inlineTags) {
			if (tag.name().equals("@after")) {
				return true;
			}
		}
		return false;
	}

	private void generateClass(ClassDoc classDoc) throws IOException {
		MdWriter writer = new MdWriter(destination, classDoc.name(), classDoc.name(), true);
		generatePackageSidebar(writer);
		writer.section("main class").h1("{{ page.title | escape }}");
		writer.tags(classDoc.inlineTags());
		// FIELDS
		FieldDoc[] fields = classDoc.fields();
		if (fields.length > 0) {
			int numberOfConstants = 0;
			for (FieldDoc field : fields) {
				if (field.isStatic() && field.isFinal()) {
					numberOfConstants++;
				}
			}
			if (numberOfConstants > 0) {
				// writer.h2("Constants");
				for (FieldDoc field : fields) {
					if (field.isStatic() && field.isFinal()) {
						writer.h2(null, field.name(), field.name());
						writer.prejava();
						writer.cdata(field.modifiers() + " ");
						writer.cdata(excludeQualifier(field.type().qualifiedTypeName()) + " ");
						writer.cdata(field.name());
						String value = field.constantValueExpression();
						if (value != null) {
							writer.cdata(" = " + value);
						}
						writer.cdata("\n").prejavaEnd();
						writer.p().tags(field.inlineTags()).pEnd();
					}
				}
			}
			if (numberOfConstants < fields.length) {
				// writer.h2("Fields");
				for (FieldDoc field : fields) {
					if (!field.isStatic() || !field.isFinal()) {
						writer.h2(null, field.name(), field.name());
						writer.prejava();
						writer.cdata(field.modifiers() + " ");
						writer.cdata(excludeQualifier(field.type().qualifiedTypeName()) + " ");
						writer.cdata(field.name());
						writer.cdata("\n").prejavaEnd();
						writer.p().tags(field.inlineTags()).pEnd();
					}
				}
			}
		}
		// CONSTRUCTORS
		ConstructorDoc[] constructors = classDoc.constructors();
		if (constructors.length > 0) {
			// writer.h2("Constructors");
			for (ConstructorDoc constructor : constructors) {
				writer.h2(null, constructor.name(), constructor.name() + constructor.flatSignature());
				writer.prejava();
				writer.cdata(constructor.modifiers() + " ");
				generateMethodHead(writer, constructor);
				writer.prejavaEnd();
				generateMethod(writer, constructor);
			}
		}
		// METHODS
		MethodDoc[] methods = classDoc.methods();
		if (methods.length > 0) {
			// writer.h2("Methods");
			for (MethodDoc method : methods) {
				writer.h2(null, method.name(), method.name() + method.flatSignature());
				writer.prejava();
				writer.cdata(method.modifiers() + " ");
				writer.cdata(excludeQualifier(method.returnType().qualifiedTypeName()) + " ");
				generateMethodHead(writer, method);
				writer.prejavaEnd();
				generateMethod(writer, method);
			}
		}
		writer.sectionEnd();
		generateClassSidebar(writer, classDoc);
		writer.close();
	}

	private void generateMethodHead(MdWriter writer, ExecutableMemberDoc executable) {
		writer.cdata(executable.name() + "(");
		Parameter[] params = executable.parameters();
		if (params.length > 0) {
			boolean isFirst = true;
			for (Parameter param : params) {
				if (isFirst) {
					isFirst = false;
				} else {
					writer.cdata(", ");
				}
				writer.cdata(excludeQualifier(param.type().qualifiedTypeName()) + " ");
				writer.cdata(param.name());
			}
		}
		writer.cdata(")\n");
	}

	private void generateMethod(MdWriter writer, ExecutableMemberDoc executable) {
		writer.tags(executable.inlineTags());
		Parameter[] params = executable.parameters();
		ParamTag[] paramTags = executable.paramTags();
		if (params.length > 0) {
			writer.h4("Parameters").table("parameters").tbody();
			for (int i = 0, n = params.length; i < n; i++) {
				writer.tr();
				writer.td().cdata(params[i].name()).cdata("<br/>");
				writer.span("paramtype").cdata(excludeQualifier(params[i].type().qualifiedTypeName())).spanEnd();
				writer.tdEnd();
				if (i < paramTags.length) {
					writer.td().tags(paramTags[i].inlineTags()).tdEnd();
				} else {
					writer.td().tdEnd();
				}
				writer.trEnd();
			}
			writer.tbodyEnd().tableEnd();
		}
		Tag[] returnTags = executable.tags("@return");
		if (returnTags.length > 0) {
			writer.h4("Returns");
			writer.p().tags(returnTags[0].inlineTags()).pEnd();
		}
		// writer.h4("Throws");
	}

	private void generateClassSidebar(MdWriter writer, ClassDoc classDoc) throws IOException {
		writer.section("apitoc").ul("section-nav");
		FieldDoc[] fields = classDoc.fields();
		if (fields.length > 0) {
			fields = sortList(fields, new FieldDoc[] {});
			int numberOfConstants = 0;
			for (FieldDoc field : fields) {
				if (field.isStatic() && field.isFinal()) {
					numberOfConstants++;
				}
			}
			if (numberOfConstants > 0) {
				writer.li("toc-entry toc-h2").cdata("Constants").ul();
				for (FieldDoc field : fields) {
					if (field.isStatic() && field.isFinal()) {
						writer.li("toc-entry toc-h3").a(classDoc.name() + "#" + field.name(), field.name()).liEnd();
					}
				}
				writer.ulEnd().liEnd();
			}
			if (numberOfConstants < fields.length) {
				writer.li("toc-entry toc-h2").cdata("Fields").ul();
				for (FieldDoc field : fields) {
					if (!field.isStatic() || !field.isFinal()) {
						writer.li("toc-entry toc-h3").a(classDoc.name() + "#" + field.name(), field.name()).liEnd();
					}
				}
				writer.ulEnd().liEnd();
			}
		}
		ConstructorDoc[] constructors = classDoc.constructors();
		if (constructors.length > 0) {
			constructors = sortList(constructors, new ConstructorDoc[] {});
			writer.li("toc-entry toc-h2").cdata("Constructors").ul();
			for (ConstructorDoc constructor : constructors) {
				String text = constructor.name() + constructor.flatSignature();
				String anchor = classDoc.name() + "#" + text;
				writer.li("toc-entry toc-h3");
				writer.a(anchor, text);
				writer.liEnd();
			}
			writer.ulEnd().liEnd();
		}
		MethodDoc[] methods = classDoc.methods();
		if (methods.length > 0) {
			methods = sortList(methods, new MethodDoc[] {});
			writer.li("toc-entry toc-h2").cdata("Methods").ul();
			for (MethodDoc method : methods) {
				String text = method.name() + method.flatSignature();
				String anchor = classDoc.name() + "#" + text;
				writer.li("toc-entry toc-h3");
				writer.a(anchor, text);
				writer.liEnd();
			}
			writer.ulEnd().liEnd();
		}
		writer.cdata("\n").ulEnd().sectionEnd();
	}

	private void generatePackageSidebar(MdWriter writer) throws IOException {
		writer.section("sidetoc").ul("section-nav");
		writer.li("toc-entry toc-h2").ax("top", "/api/", "API home").liEnd();
		writer.li("toc-entry toc-h2").cdata("COASTAL").ul();
		for (PackageDoc packageDoc : coastalPackages) {
			writer.li("toc-entry toc-h3").a(packageDoc.name(), packageDoc.name()).liEnd();
		}
		writer.ulEnd().liEnd().li("toc-entry toc-h2").cdata("Examples").ul();
		for (PackageDoc packageDoc : examplePackages) {
			writer.li("toc-entry toc-h3").a(packageDoc.name(), packageDoc.name()).liEnd();
		}
		writer.ulEnd().liEnd().ulEnd().sectionEnd();
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
	// MDWRITER CLASS
	//
	// ======================================================================

	private static final String FILE_SEPARATOR = System.getProperty("file.separator");

	private static Writer genWriter(String fullFilename) throws IOException {
		FileOutputStream fos = new FileOutputStream(fullFilename);
		return new OutputStreamWriter(fos, "UTF-8");
	}

	public class MdWriter extends PrintWriter {

		public MdWriter(String path, String title) throws IOException {
			super(genWriter(path + FILE_SEPARATOR + "index.md"));
			writeFrontMatter(title, "/api/");
		}

		public MdWriter(String path, String title, boolean toc) throws IOException {
			super(genWriter(path + FILE_SEPARATOR + "index.md"));
			writeFrontMatter(title, "/api/", new Object[][] { { "toc", toc } });
		}

		public MdWriter(String path, String filename, String title) throws IOException {
			super(genWriter(path + FILE_SEPARATOR + filename + ".md"));
			writeFrontMatter(title, String.format("/api/%s/", filename));
		}

		public MdWriter(String path, String filename, String title, boolean toc) throws IOException {
			super(genWriter(path + FILE_SEPARATOR + filename + ".md"));
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

		private MdWriter tags(Tag[] tags, int before) {
			int i = 0;
			if (before > 0) {
				while ((i < tags.length) && !tags[i].name().equals("@after")) {
					i++;
				}
			}
			while (i < tags.length) {
				switch (tags[i].name()) {
				case "@after":
					if (before < 0) {
						return this;
					}
					break;
				case "Text":
					cdata(tags[i].text());
					break;
				case "@prejava":
					prejava().cdata(tags[i].text()).prejavaEnd();
					break;
				case "@link":
					code().az(tags[i].holder(), null, tags[i].text(), tags[i].text()).codeEnd();
					break;
				case "@code":
					code().cdata(tags[i].text()).codeEnd();
					break;
				default:
					cdata("TAG(" + tags[i].name() + ", " + tags[i].text() + ")");
					break;
				}
				i++;
			}
			return this;
		}

		private MdWriter tags(Tag[] tags) {
			return tags(tags, 0);
		}

		public MdWriter a(String href, String text) {
			printf("<a href=\"%s\">%s</a>", href(href), text);
			return this;
		}

		public MdWriter a(String clas, String href, String text) {
			printf("<a class=\"%s\" href=\"%s\">%s</a>\n", clas, href(href), text);
			return this;
		}

		public MdWriter ax(String clas, String href, String text) {
			printf("<a class=\"%s\" href=\"{{ '%s' | relative_url }}\">%s</a>\n", clas, href, text);
			return this;
		}

		public MdWriter az(Doc doc, String clas, String href, String text) {
			String classSpec = "";
			if (clas != null) {
				classSpec = String.format(" class=\"%s\"", clas);
			}
			if (href.startsWith("#")) {
				printf("<a%s href=\"%s\">%s</a>\n", classSpec, href(doc.name() + href), text.substring(1));
				return this;
			} else {
				printf("<a%s href=\"%s\">%s</a>\n", classSpec, href(href), text);
				return this;
			}
		}

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

		public MdWriter cdata(String data) {
			print(data);
			return this;
		}

		public MdWriter prejava() {
			println("<div markdown=\"1\">");
			println("~~~java");
			return this;
		}

		public MdWriter prejavaEnd() {
			println("~~~");
			println("</div>");
			return this;
		}

		public MdWriter p() {
			return generic("p");
		}

		public MdWriter p(String clas) {
			return generic(clas, "p");
		}

		public MdWriter pEnd() {
			return genericEnd("p");
		}

		public MdWriter span() {
			return generic("span", false);
		}

		public MdWriter span(String clas) {
			return generic(clas, "span", false);
		}

		public MdWriter spanEnd() {
			return genericEnd("span", false);
		}

		public MdWriter code() {
			return generic("code", false);
		}

		public MdWriter code(String clas) {
			return generic(clas, "code", false);
		}

		public MdWriter codeEnd() {
			return genericEnd("code", false);
		}

		private MdWriter generic(String element) {
			return generic(element, true);
		}

		private MdWriter generic(String clas, String element) {
			return generic(clas, element, true);
		}

		private MdWriter genericEnd(String element) {
			return genericEnd(element, true);
		}

		private MdWriter generic(String element, boolean newline) {
			printf("<%s>", element);
			if (newline) {
				printf("\n");
			}
			return this;
		}

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

		private MdWriter genericEnd(String element, boolean newline) {
			printf("</%s>", element);
			if (newline) {
				printf("\n");
			}
			return this;
		}

		// ---------- HEADINGS ----------------------------------------

		public MdWriter h1(String heading) {
			return hn("h1", heading);
		}

		public MdWriter h1(String clas, String heading) {
			return hn("h1", clas, heading);
		}

		public MdWriter h1(String clas, String heading, String name) {
			return hn("h1", clas, heading, name);
		}

		public MdWriter h2(String heading) {
			return hn("h2", heading);
		}

		public MdWriter h2(String clas, String heading) {
			return hn("h2", clas, heading);
		}

		public MdWriter h2(String clas, String heading, String name) {
			return hn("h2", clas, heading, name);
		}

		public MdWriter h3(String heading) {
			return hn("h3", heading);
		}

		public MdWriter h3(String clas, String heading) {
			return hn("h3", clas, heading);
		}

		public MdWriter h3(String clas, String heading, String name) {
			return hn("h3", clas, heading, name);
		}

		public MdWriter h4(String heading) {
			return hn("h4", heading);
		}

		public MdWriter h4(String clas, String heading) {
			return hn("h4", clas, heading);
		}

		public MdWriter h4(String clas, String heading, String name) {
			return hn("h4", clas, heading, name);
		}

		private MdWriter hn(String hn, String heading) {
			printf("<%s>%s</%s>\n", hn, heading, hn);
			return this;
		}

		private MdWriter hn(String hn, String clas, String heading) {
			if (clas == null) {
				printf("<%s>%s</%s>\n", hn, heading, hn);
			} else {
				printf("<%s class=\"%s\">%s</%s>\n", hn, clas, heading, hn);
			}
			return this;
		}

		private MdWriter hn(String hn, String clas, String heading, String name) {
			if (clas == null) {
				printf("<%s><a class=\"anchor\" name=\"%s\"></a>%s</%s>\n", hn, name, heading, hn);
			} else {
				printf("<%s class=\"%s\"><a class=\"anchor\" name=\"%s\"></a>%s</%s>\n", hn, clas, name, heading, hn);
			}
			return this;
		}

		// ---------- LISTS ----------------------------------------

		public MdWriter ul() {
			return generic("ul");
		}

		public MdWriter ul(String clas) {
			return generic(clas, "ul");
		}

		public MdWriter ulEnd() {
			return genericEnd("ul");
		}

		public MdWriter li() {
			return generic("li");
		}

		public MdWriter li(String clas) {
			return generic(clas, "li");
		}

		public MdWriter liEnd() {
			return genericEnd("li");
		}

		// ---------- SECTION ----------------------------------------

		public MdWriter section() {
			return generic("section");
		}

		public MdWriter section(String clas) {
			return generic(clas, "section");
		}

		public MdWriter sectionEnd() {
			return genericEnd("section");
		}

		// ---------- TABLES ----------------------------------------

		public MdWriter table() {
			return generic("table");
		}

		public MdWriter table(String clas) {
			return generic(clas, "table");
		}

		public MdWriter tableEnd() {
			return genericEnd("table");
		}

		public MdWriter thead() {
			return generic("thead");
		}

		public MdWriter thead(String clas) {
			return generic(clas, "thead");
		}

		public MdWriter theadEnd() {
			return genericEnd("thead");
		}

		public MdWriter tbody() {
			return generic("tbody");
		}

		public MdWriter tbody(String clas) {
			return generic(clas, "tbody");
		}

		public MdWriter tbodyEnd() {
			return genericEnd("tbody");
		}

		public MdWriter tr() {
			return generic("tr");
		}

		public MdWriter tr(String clas) {
			return generic(clas, "tr");
		}

		public MdWriter trEnd() {
			return genericEnd("tr");
		}

		public MdWriter th() {
			return generic("th");
		}

		public MdWriter th(String clas) {
			return generic(clas, "th");
		}

		public MdWriter thEnd() {
			return genericEnd("th");
		}

		public MdWriter td() {
			return generic("td");
		}

		public MdWriter td(String clas) {
			return generic(clas, "td");
		}

		public MdWriter tdEnd() {
			return genericEnd("td");
		}

	}

}

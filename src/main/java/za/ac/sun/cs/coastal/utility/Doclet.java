/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
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

/**
 * Generate javadoc that is suitable for inclusion in the COASTAL documentation
 * system.
 */
public class Doclet {

	/**
	 * Singleton instance.
	 */
	private static Doclet doclet;

	/**
	 * Return the singleton instance of {@link Doclet}. If it does not exist, it is
	 * created.
	 *
	 * @return singleton instance
	 */
	private static Doclet getDoclet() {
		if (doclet == null) {
			doclet = new Doclet();
		}
		return doclet;
	}

	/**
	 * Generate the documentation.
	 *
	 * @param root
	 *             root document
	 * @return true on success
	 */
	public static boolean start(RootDoc root) {
		return getDoclet().startDoc(root);
	}

	/**
	 * Check for doclet-added options. Returns the number of arguments you must
	 * specify on the command line for the given option. For example,
	 * "{@code -d docs}" would return 2.
	 *
	 * @param option
	 *               a given doclet option
	 * @return number of arguments on the command line for the option including the
	 *         option name itself. Zero return means option not known. Negative
	 *         value means error occurred.
	 */
	public static int optionLength(String option) {
		return getDoclet().getOptionLength(option);
	}

	/**
	 * Check that options have the correct arguments.
	 *
	 * @param options
	 *                 doclet options split into array
	 * @param reporter
	 *                 {@link DocErrorReporter} for printing error messages
	 * @return true if the options are valid
	 */
	public static boolean validOptions(String[][] options, DocErrorReporter reporter) {
		return getDoclet().areValidOptions(options, reporter);
	}

	/**
	 * Return the version of the Java Programming Language supported by this doclet.
	 *
	 * @return the language version supported by this doclet
	 */
	public static LanguageVersion languageVersion() {
		return getDoclet().getLanguageVersion();
	}

	// ======================================================================
	//
	// DOCLET CLASS
	//
	// ======================================================================

	/**
	 * Destination directoy for generated documentation.
	 */
	private String destination = ".";

	/**
	 * Set of qualifiers that are never displayed.
	 */
	private final Set<String> excludedQualifiers = new HashSet<>();

	/**
	 * The COASTAL packages detected.
	 */
	private PackageDoc[] coastalPackages;

	/**
	 * Set of COASTAL classes to document.
	 */
	private final Set<ClassDoc> coastalClasses = new HashSet<>();

	/**
	 * Construct a document generator.
	 */
	public Doclet() {
	}

	/**
	 * Generate the documentation.
	 *
	 * @param root
	 *             root document
	 * @return true on success
	 */
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

	/**
	 * Check for doclet-added options. Returns the number of arguments you must
	 * specify on the command line for the given option. For example,
	 * "{@code -d docs}" would return 2.
	 *
	 * @param option
	 *               a given doclet option
	 * @return number of arguments on the command line for the option including the
	 *         option name itself. Zero return means option not known. Negative
	 *         value means error occurred.
	 */
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

	/**
	 * Check that options have the correct arguments.
	 *
	 * @param options
	 *                 doclet options split into array
	 * @param reporter
	 *                 {@link DocErrorReporter} for printing error messages
	 * @return true if the options are valid
	 */
	private boolean areValidOptions(String[][] options, DocErrorReporter reporter) {
		return true;
	}

	/**
	 * Return the version of the Java Programming Language supported by this doclet.
	 *
	 * @return the language version supported by this doclet
	 */
	private LanguageVersion getLanguageVersion() {
		return LanguageVersion.JAVA_1_5;
	}

	/**
	 * Process an array of options, picking up the options that are of interest to
	 * this class.
	 *
	 * @param options
	 *                array of options
	 */
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

	/**
	 * Add a set of tokens (colon-separated substrings of a given string) to a set.
	 *
	 * @param s
	 *            resulting set of strings
	 * @param str
	 *            string of tokens
	 */
	private void addToSet(Set<String> s, String str) {
		StringTokenizer st = new StringTokenizer(str, ":");
		String current;
		while (st.hasMoreTokens()) {
			current = st.nextToken();
			s.add(current);
		}
	}

	/**
	 * Reduce a name by removing any registered qualifiers.
	 *
	 * @param name
	 *             name to process
	 * @return resulting name possibly with its qualifier excluded
	 */
	private String excludeQualifier(String name) {
		for (String excludedQualifier : excludedQualifiers) {
			if (excludedQualifier.endsWith("*")) {
				if (name.startsWith(excludedQualifier.substring(0, excludedQualifier.length() - 1))) {
					int index = name.lastIndexOf(".", name.length() + 1 - 1);
					return name.substring(index + 1);
				}
			}
		}
		int index = name.length() + 1;
		while ((index = name.lastIndexOf(".", index - 1)) != -1) {
			String prefix = name.substring(0, index + 1);
			if (excludedQualifiers.contains(prefix)) {
				return name.substring(index + 1);
			}
		}
		return name;
	}

	/**
	 * Pick out the COASTAL classes from a root document.
	 *
	 * @param root
	 *             root document
	 */
	private void initArrays(RootDoc root) {
		Set<PackageDoc> coastalSet = new HashSet<>();
		for (ClassDoc classDoc : root.classes()) {
			PackageDoc packag = classDoc.containingPackage();
			if (packag.name().startsWith("za.ac.sun.cs.coastal")) {
				coastalSet.add(packag);
				coastalClasses.add(classDoc);
			}
		}
		coastalPackages = sortList(coastalSet, new PackageDoc[] {});
	}

	/**
	 * Generate a sidebar with a list of COASTAL packages.
	 *
	 * @throws IOException
	 *                     if file can not be written to
	 */
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

	/**
	 * Generate a page for a given package.
	 *
	 * @param packageDoc
	 *                   package documentation
	 * @throws IOException
	 *                     if file can not be written to
	 */
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
		if (hasAfterTag(packageDoc.inlineTags())) {
			writer.h2("Description").tags(packageDoc.inlineTags(), 1);
		}
		writer.sectionEnd().close();
	}

	/**
	 * Check if a sequence of tags includes an <code>@after</code> tag.
	 *
	 * @param inlineTags
	 *                   sequence of tags
	 * @return true if and only if the sequence includes
	 */
	private boolean hasAfterTag(Tag[] inlineTags) {
		for (Tag tag : inlineTags) {
			if (tag.name().equals("@after")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generate a page for a given class.
	 *
	 * @param classDoc
	 *                 class documentation
	 * @throws IOException
	 *                     if file can not be written to
	 */
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
						writer.cdata(
								excludeQualifier(field.type().qualifiedTypeName()) + field.type().dimension() + " ");
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

	/**
	 * Generate the method head include its name, return type, and parameters.
	 *
	 * @param writer
	 *                   markdown writer
	 * @param executable
	 *                   method information
	 */
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
				writer.cdata(excludeQualifier(param.type().qualifiedTypeName()) + param.type().dimension() + " ");
				writer.cdata(param.name());
			}
		}
		writer.cdata(")\n");
	}

	/**
	 * Generate the description for a method.
	 *
	 * @param writer
	 *                   markdown writer
	 * @param executable
	 *                   method information
	 */
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

	/**
	 * Generate the right-hand sidebar that lists all methods and fields.
	 *
	 * @param writer
	 *                 markdown writer
	 * @param classDoc
	 *                 class to generate the sidebar for
	 * @throws IOException
	 *                     if file can not be written to
	 */
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

	/**
	 * Generate the left-hand sidebar that lists all packages.
	 *
	 * @param writer
	 *               markdown writer
	 * @throws IOException
	 *                     if file can not be written to
	 */
	private void generatePackageSidebar(MdWriter writer) throws IOException {
		writer.section("sidetoc").ul("section-nav");
		writer.li("toc-entry toc-h2").ax("top", "/api/", "API home").liEnd();
		writer.li("toc-entry toc-h2").cdata("COASTAL").ul();
		for (PackageDoc packageDoc : coastalPackages) {
			writer.li("toc-entry toc-h3").a(packageDoc.name(), packageDoc.name()).liEnd();
		}
		writer.ulEnd().liEnd().ulEnd().sectionEnd();
	}

	/**
	 * Sort a collection of items of type {@code T} according to their natural
	 * ordering and return the sorted result as an array.
	 *
	 * @param <T>
	 *                   type of items
	 * @param collection
	 *                   collection of items
	 * @param prototype
	 *                   array type
	 * @return sorted array of items
	 */
	private <T> T[] sortList(Collection<T> collection, T[] prototype) {
		SortedSet<T> sortedSet = new TreeSet<>(collection);
		return sortedSet.toArray(prototype);
	}

	/**
	 * Sort an array of items of type {@code T} according to their natural ordering
	 * and return the sorted result as a new array.
	 *
	 * @param <T>
	 *                   type of items
	 * @param collection
	 *                   array of items
	 * @param prototype
	 *                   array type
	 * @return sorted array of items
	 */
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

	/**
	 * Directory separator for the current operating system.
	 */
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * Produce a {@link Writer} that writes to a file.
	 *
	 * @param fullFilename
	 *                     filename for the destination
	 * @return {@link Writer} that writes to the file
	 * @throws IOException
	 *                     if the file cannot be created
	 */
	private static Writer genWriter(String fullFilename) throws IOException {
		FileOutputStream fos = new FileOutputStream(fullFilename);
		return new OutputStreamWriter(fos, "UTF-8");
	}

	/**
	 * Class for Markdown generation.
	 */
	public class MdWriter extends PrintWriter {

		/**
		 * Create a Markdown writer for a new document called "{@code index.md}" in the
		 * given path and with the given title.
		 * 
		 * @param path
		 *              path for the new document
		 * @param title
		 *              title for the new document
		 * @throws IOException
		 *                     if the file cannot be created
		 */
		public MdWriter(String path, String title) throws IOException {
			super(genWriter(path + FILE_SEPARATOR + "index.md"));
			writeFrontMatter(title, "/api/");
		}

		/**
		 * Create a Markdown writer for a new document called "{@code index.md}" in the
		 * given path and with the given title and with an optional table of contents.
		 * 
		 * @param path
		 *              path for the new document
		 * @param title
		 *              title for the new document
		 * @param toc
		 *              whether or not a table of contents is generated
		 * @throws IOException
		 *                     if the file cannot be created
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
		 * @param path
		 *                 path for the new document
		 * @param filename
		 *                 name for the new document
		 * @param title
		 *                 title for the new document
		 * @throws IOException
		 *                     if the file cannot be created
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
		 * @param path
		 *                 path for the new document
		 * @param filename
		 *                 name for the new document
		 * @param title
		 *                 title for the new document
		 * @param toc
		 *                 whether or not a table of contents is generated
		 * @throws IOException
		 *                     if the file cannot be created
		 */
		public MdWriter(String path, String filename, String title, boolean toc) throws IOException {
			super(genWriter(path + FILE_SEPARATOR + filename + ".md"));
			writeFrontMatter(title, String.format("/api/%s/", filename), new Object[][] { { "toc", toc } });
		}

		/**
		 * Write the Markdown frontmatter. This contains at least two fields: the title
		 * and a permalink. Additional fields may be provided.
		 *
		 * @param title
		 *                  title of the page
		 * @param permalink
		 *                  canonical link for the page
		 * @param extras
		 *                  additional fields to include
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

		/**
		 * Process a sequence of tags up to a certain point. If {@code before} is -1 or
		 * less, all tags up to the first <code>@after</code> tag are output; if
		 * {@code before} is 1 or more, all tags after the first <code>@after</code>
		 * tags are output; if "{@code before}" is 0, all tags are output.
		 *
		 * @param tags
		 *               array of tags
		 * @param before
		 *               flag for treatment of "{@code @after}" tag
		 * @return reference to this object
		 */
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

		/**
		 * Write an array of tags one by one.
		 *
		 * @param tags
		 *             array of tags
		 * @return reference to this object
		 */
		private MdWriter tags(Tag[] tags) {
			return tags(tags, 0);
		}

		/**
		 * Write an A element with the given link and text.
		 *
		 * @param href
		 *             link for the element
		 * @param text
		 *             text for the element
		 * @return reference to this object
		 */
		public MdWriter a(String href, String text) {
			printf("<a href=\"%s\">%s</a>", href(href), text);
			return this;
		}

		/**
		 * Write an A element with the given link, text, and class.
		 *
		 * @param clas
		 *             CSS class of span
		 * @param href
		 *             link for the element
		 * @param text
		 *             text for the element
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
		 * @param clas
		 *             CSS class of span
		 * @param href
		 *             link for the element
		 * @param text
		 *             text for the element
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
		 * @param doc
		 *             document for relative links
		 * @param clas
		 *             CSS class of span
		 * @param href
		 *             link for the element
		 * @param text
		 *             text for the element
		 * @return reference to this object
		 */
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

		/**
		 * Write an HREF attribute for an HTML A element.
		 *
		 * @param href
		 *             link to include in attribute
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
		 * @param data
		 *             text to output
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
		 * @param clas
		 *             CSS class of paragraph
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
		 * @param clas
		 *             CSS class of span
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
		 * @param clas
		 *             CSS class of span
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
		 * @param element
		 *                element name
		 * @return reference to this object
		 */
		private MdWriter generic(String element) {
			return generic(element, true);
		}

		/**
		 * Write the opening tag of an HTML element with a given class.
		 *
		 * @param clas
		 *                CSS class of element
		 * @param element
		 *                element name
		 * @return reference to this object
		 */
		private MdWriter generic(String clas, String element) {
			return generic(clas, element, true);
		}

		/**
		 * Write the closing tag of a literal span of text.
		 *
		 * @param element
		 *                element name
		 * @return reference to this object
		 */
		private MdWriter genericEnd(String element) {
			return genericEnd(element, true);
		}

		/**
		 * Write the opening tag of an HTML element followed by an optional newline.
		 *
		 * @param element
		 *                element name
		 * @param newline
		 *                whether or not to write a newline
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
		 * @param clas
		 *                CSS class of element
		 * @param element
		 *                element name
		 * @param newline
		 *                whether or not to write a newline
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
		 * @param element
		 *                element name
		 * @param newline
		 *                whether or not to write a newline
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
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h1(String heading) {
			return hn("h1", heading);
		}

		/**
		 * Write an H1 tag with the given text and class.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h1(String clas, String heading) {
			return hn("h1", clas, heading);
		}

		/**
		 * Write an H1 tag with the given text, class, and name attribute.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @param name
		 *                name attribute
		 * @return reference to this object
		 * @return
		 */
		public MdWriter h1(String clas, String heading, String name) {
			return hn("h1", clas, heading, name);
		}

		/**
		 * Write an H2 tag with the given text.
		 *
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h2(String heading) {
			return hn("h2", heading);
		}

		/**
		 * Write an H2 tag with the given text and class.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h2(String clas, String heading) {
			return hn("h2", clas, heading);
		}

		/**
		 * Write an H2 tag with the given text, class, and name attribute.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @param name
		 *                name attribute
		 * @return reference to this object
		 * @return
		 */
		public MdWriter h2(String clas, String heading, String name) {
			return hn("h2", clas, heading, name);
		}

		/**
		 * Write an H3 tag with the given text.
		 *
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h3(String heading) {
			return hn("h3", heading);
		}

		/**
		 * Write an H3 tag with the given text and class.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h3(String clas, String heading) {
			return hn("h3", clas, heading);
		}

		/**
		 * Write an H3 tag with the given text, class, and name attribute.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @param name
		 *                name attribute
		 * @return reference to this object
		 * @return
		 */
		public MdWriter h3(String clas, String heading, String name) {
			return hn("h3", clas, heading, name);
		}

		/**
		 * Write an H4 tag with the given text.
		 *
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h4(String heading) {
			return hn("h4", heading);
		}

		/**
		 * Write an H4 tag with the given text and class.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		public MdWriter h4(String clas, String heading) {
			return hn("h4", clas, heading);
		}

		/**
		 * Write an H4 tag with the given text, class, and name attribute.
		 *
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @param name
		 *                name attribute
		 * @return reference to this object
		 * @return
		 */
		public MdWriter h4(String clas, String heading, String name) {
			return hn("h4", clas, heading, name);
		}

		/**
		 * Write an Hx tag with the given text.
		 *
		 * @param hn
		 *                heading element name
		 * @param heading
		 *                text for the heading
		 * @return reference to this object
		 */
		private MdWriter hn(String hn, String heading) {
			printf("<%s>%s</%s>\n", hn, heading, hn);
			return this;
		}

		/**
		 * Write an Hx tag with the given text and class.
		 *
		 * @param hn
		 *                heading element name
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
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
		 * @param hn
		 *                heading element name
		 * @param clas
		 *                CSS class of heading
		 * @param heading
		 *                text for the heading
		 * @param name
		 *                name attribute
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
		 * @param clas
		 *             CSS class of list
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
		 * @param clas
		 *             CSS class of list item
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
		 * @param clas
		 *             CSS class of section
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
		 * @param clas
		 *             CSS class of table
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
		 * @param clas
		 *             CSS class of table heading
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
		 * @param clas
		 *             CSS class of body
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
		 * @param clas
		 *             CSS class of row
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
		 * @param clas
		 *             CSS class of cell
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
		 * @param clas
		 *             CSS class of cell
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

}

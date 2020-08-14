/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.ac.sun.cs.coastal.utility;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.ElementScanner9;
import javax.tools.Diagnostic;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTreeScanner;
import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.Doclet;
import com.sun.source.doctree.DocTree.Kind;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class CoastalDoclet implements Doclet {

	private static final boolean OK = true;
	// private static final boolean FAILED = false;

	/**
	 * Destination directory for generated documentation.
	 */
	private String destination = ".";

	private Reporter reporter;

	private DocTrees treeUtils;

	private Collector collector;

	abstract class Option implements Doclet.Option {
		private final String name;
		private final boolean hasArg;
		private final String description;
		private final String parameters;

		Option(String name, boolean hasArg, String description, String parameters) {
			this.name = name;
			this.hasArg = hasArg;
			this.description = description;
			this.parameters = parameters;
		}

		@Override
		public int getArgumentCount() {
			return hasArg ? 1 : 0;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public Kind getKind() {
			return Kind.STANDARD;
		}

		@Override
		public List<String> getNames() {
			return List.of(name);
		}

		@Override
		public String getParameters() {
			return hasArg ? parameters : "";
		}

		@Override
		public boolean process(String option, List<String> arguments) {
			return OK;
		}
	}

	private final Set<Option> options = Set.of(new Option("-d", true, "destination where files are stored", "<string>") {
		@Override
		public boolean process(String option, List<String> arguments) {
			destination = arguments.get(0);
			new File(destination).mkdirs();
			return OK;
		}
	}, new Option("-noqualifier", true, "prefixes to omit qualifiers for", "<string>") {
//		@Override
//		public boolean process(String option, List<String> arguments) {
//			String[] args = arguments.get(0).split(":");
//			for (String arg : args) {
//				excludedQualifiers.add(arg);
//			}
//			return OK;
//		}
	}, new Option("-doctitle", true, "title used in headers and footers (ignored)", "<string>") {
	}, new Option("-notimestamp", false, "suppress the hidden HTML timestamp (ignored)", "") {
	}, new Option("-windowtitle", true, "title used in HTML title tags (ignored)", "<string>") {
	});

	@Override
	public void init(Locale locale, Reporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public Set<? extends Option> getSupportedOptions() {
		return options;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public boolean run(DocletEnvironment environment) {
		treeUtils = environment.getDocTrees();
		collector = new Collector();
		collector.process(environment.getIncludedElements());
		try {
			MdWriter packageWriter = new MdWriter(destination, "COASTAL", false);
			reporter.print(Diagnostic.Kind.NOTE, "Main index");
			packageWriter.section("sidebar").sectionEnd();
			packageWriter.section("main").h1("{{ page.title | escape }}");
			packageWriter.table("packages").thead().tr();
			packageWriter.th().cdata("Package").thEnd();
			packageWriter.th().cdata("Description").thEnd();
			packageWriter.trEnd().theadEnd().tbody();
			for (Element e : collector.getPackages()) {
				String name = e.toString();
				String desc = FirstSentenceScanner.process(treeUtils, e);
				packageWriter.tr();
				packageWriter.td().a(name, name).tdEnd();
				packageWriter.td().cdata(desc).tdEnd();
				packageWriter.trEnd();
				generatePackage(e, name);
			}
			packageWriter.tbodyEnd().tableEnd();
			packageWriter.sectionEnd();
			packageWriter.close();
		} catch (IOException x) {
			x.printStackTrace();
		}
		return OK;
	}

	/**
	 * Generate a page for a given package.
	 * @param e element that represents the package
	 * @param name name of the package
	 * @throws IOException
	 *                     if file can not be written to
	 */
	private void generatePackage(Element e, String name) throws IOException {
		MdWriter writer = new MdWriter(destination, name, name);
		reporter.print(Diagnostic.Kind.NOTE, "package> " + name);
		generatePackageSidebar(writer);
		writer.section("main package").h1("{{ page.title | escape }}");
		DocumentationScanner.write(writer, treeUtils, e);
		int interfaceCount = 0;
		int classCount = 0;
		for (Element ee : collector.getPackagesFor(e)) {
			switch (ee.getKind()) {
				case INTERFACE:
					interfaceCount++;
					break;
				case CLASS:
					classCount++;
					break;
				default:
					reporter.print(Diagnostic.Kind.NOTE, "??? " + ee.getKind());
					break;
			}
		}
		if (interfaceCount > 0) {
			writer.h2("Interfaces").table("classes").tbody();
			for (Element ee : collector.getPackagesFor(e)) {
				if (ee.getKind() == ElementKind.INTERFACE) {
					String n = ee.getSimpleName().toString();
					String d = FirstSentenceScanner.process(treeUtils, ee);
					writer.tr();
					writer.td().a(n, n).tdEnd();
					writer.td().cdata(d).tdEnd();
					writer.trEnd();
//					generateInterface(ee);
				}
			}
			writer.tbodyEnd().tableEnd();
		}
		if (classCount > 0) {
			writer.h2("Classes").table("classes").tbody();
			for (Element ee : collector.getPackagesFor(e)) {
				if (ee.getKind() == ElementKind.CLASS) {
					String n = ee.getSimpleName().toString();
					String d = FirstSentenceScanner.process(treeUtils, ee);
					writer.tr();
					writer.td().a(n, n).tdEnd();
					writer.td().cdata(d).tdEnd();
					writer.trEnd();
//					generateClass(ee);
				}
			}
			writer.tbodyEnd().tableEnd();
		}
//		if (hasAfterTag(packageDoc.inlineTags())) {
//			writer.h2("Description").tags(packageDoc.inlineTags(), 1);
//		}
		writer.sectionEnd();
		writer.close();
	}

	private void generatePackageSidebar(MdWriter writer) throws IOException {
		writer.section("sidetoc").ul("section-nav");
		writer.li("toc-entry toc-h2").ax("top", "/api/", "API home").liEnd();
		writer.li("toc-entry toc-h2").cdata("COASTAL").ul();
		for (Element e : collector.getPackages()) {
			String name = e.toString();
			writer.li("toc-entry toc-h3").a(name, name).liEnd();
		}
		writer.ulEnd().liEnd().ulEnd().sectionEnd();
	}

	/**
	 * A scanner to display the structure of a series of elements and their
	 * documentation comments.
	 */
	class Collector extends ElementScanner9<Void, Integer> {

		private final SortedSet<Element> packages = new TreeSet<Element>(new Comparator<Element>() {
			@Override
			public int compare(Element e1, Element e2) {
				return e1.toString().compareTo(e2.toString());
			}
		});

		private final Map<Element, SortedSet<Element>> classes = new HashMap<Element, SortedSet<Element>>();

		public SortedSet<Element> getPackages() {
			return packages;
		}

		public SortedSet<Element> getPackagesFor(Element e) {
			return classes.get(e);
		}

		public void process(Set<? extends Element> elements) {
			scan(elements, 0);
		}

		@Override
		public Void scan(Element e, Integer depth) {
			ElementKind k = e.getKind();
			if (k == ElementKind.PACKAGE) {
				packages.add(e);
			} else if ((k == ElementKind.CLASS) || (k == ElementKind.INTERFACE)) {
				classes.computeIfAbsent(e.getEnclosingElement(), key -> new TreeSet<Element>(new Comparator<Element>() {
					@Override
					public int compare(Element e1, Element e2) {
						return e1.toString().compareTo(e2.toString());
					}
				})).add(e);
			}
			return super.scan(e, depth + 1);
		}

	}

	/**
	 * A scanner to display the structure of a documentation comment.
	 */
	static class FirstSentenceScanner extends DocTreeScanner<Void, Integer> {

		private String firstSentence = "";

		private boolean isActive = true;

		public String getFirstSentence() {
			return firstSentence;
		}

		@Override
		public Void scan(DocTree t, Integer depth) {
			if ((t == null) || !isActive) {
				return null;
			}
			Kind k = t.getKind();
			if (k == Kind.DOC_COMMENT) {
				return super.scan(t, depth + 1);
			} else {
				String text = t.toString();
				if (text.contains(".")) {
					int index = text.indexOf('.');
					firstSentence += text.substring(0, index + 1);
				} else {
					firstSentence += text;
				}
				isActive = !firstSentence.endsWith(".");
			}
			return null;
		}

		public static String process(DocTrees treeUtils, Element e) {
			DocCommentTree docTree = treeUtils.getDocCommentTree(e);
			FirstSentenceScanner fss = new FirstSentenceScanner();
			fss.scan(docTree, 0);
			return fss.getFirstSentence();
		}

	}

	/**
	 * A scanner to display the structure of a documentation comment.
	 */
	static class DocumentationScanner extends DocTreeScanner<Void, Integer> {

		private final MdWriter writer;

		DocumentationScanner(MdWriter writer) {
			this.writer = writer;
		}

		@Override
		public Void scan(DocTree t, Integer depth) {
			if (t == null) {
				return null;
			}
			switch (t.getKind()) {
				case DOC_COMMENT:
					super.scan(t, depth + 1);
					break;
				case TEXT:
				case START_ELEMENT:
				case END_ELEMENT:
					writer.cdata(t.toString());
					break;
				case CODE:
					writer.code();
					super.scan(t,  depth + 1);
					writer.codeEnd();
					break;
				case UNKNOWN_INLINE_TAG:
					String text = t.toString();
					if (text.startsWith("{@prejava") && text.endsWith("}")) {
						writer.prejava();
						writer.cdata(text.substring(10, text.length() - 1));
						writer.prejavaEnd();
					}
					break;
				default:
					writer.cdata("TAG(" + t.getKind() + ", ");
					// + tags[i].name() + ", " + tags[i].text() +
					writer.cdata(")");
					break;
			}
			return null;
//		case "@after":
//			if (before < 0) {
//				return this;
//			}
//			break;
//		case "@link":
//			code().az(tags[i].holder(), null, tags[i].text(), tags[i].text()).codeEnd();
//			break;
		}

		public static void write(MdWriter writer, DocTrees treeUtils, Element e) {
			DocCommentTree docTree = treeUtils.getDocCommentTree(e);
			DocumentationScanner ds = new DocumentationScanner(writer);
			ds.scan(docTree, 0);
		}

	}

}

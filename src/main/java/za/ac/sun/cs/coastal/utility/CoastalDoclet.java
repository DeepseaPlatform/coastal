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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class CoastalDoclet implements Doclet {

	private static final boolean OK = true;

	private static final boolean FAILED = false;
	
	private Reporter reporter;

	/**
	 * Destination directory for generated documentation.
	 */
	private String destination = ".";

	/**
	 * Set of qualifiers that are never displayed.
	 */
	private final Set<String> excludedQualifiers = new HashSet<>();

	/**
	 * Set of COASTAL classes to document.
	 */
	private final Set<PackageElement> coastalPackages = new HashSet<>();
	
	/**
	 * The COASTAL packages detected.
	 */
	private PackageElement[] coastalPackages2;
	
	/**
	 * Set of COASTAL classes to document.
	 */
	private final Set<TypeElement> coastalClasses = new HashSet<>();

	@Override
	public void init(Locale locale, Reporter reporter) {
		reporter.print(Kind.NOTE, "Doclet using locale: " + locale);
		this.reporter = reporter;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * A base class for declaring options. Subtypes for specific options should
	 * implement the {@link #process(String,List) process} method to handle
	 * instances of the option found on the command line.
	 */
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

	}

	private final Set<Option> options = Set.of(
			// An option that takes no arguments.
			new Option("-destination", false, "where files are stored", "<string>") {
				@Override
				public boolean process(String option, List<String> arguments) {
					destination = arguments.get(0);
					new File(destination).mkdirs();
					return OK;
				}
			},

			// An option that takes a single string-valued argument.
			new Option("-noqualifier", true, "prefixes to omit qualifiers for", "<string>") {
				@Override
				public boolean process(String option, List<String> arguments) {
					String[] args = arguments.get(0).split(":");
					for (String arg : args) {
						excludedQualifiers.add(arg);
					}
					return OK;
				}
			});

	@Override
	public Set<? extends Option> getSupportedOptions() {
		return options;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_5;
	}

	@Override
	public boolean run(DocletEnvironment environment) {
		try {
			initArrays(environment);
			generatePackageSummary();
		} catch (IOException e) {
			reporter.print(Diagnostic.Kind.ERROR, e.getMessage());
			return FAILED;
		}
		reporter.print(Diagnostic.Kind.NOTE, "Done");
		return OK;
	}

	/**
	 * Pick out the COASTAL classes from a root document.
	 *
	 * @param root
	 *             root document
	 */
	private void initArrays(DocletEnvironment environment) {
		for (Element elem : environment.getIncludedElements()) {
			if (elem instanceof TypeElement) {
				TypeElement type = (TypeElement) elem;
				Element pack = type.getEnclosingElement();
				while (!(pack instanceof PackageElement)) {
					pack = pack.getEnclosingElement();
				}
				PackageElement packag = (PackageElement) pack;
				if (packag.getQualifiedName().toString().startsWith("za.ac.sun.cs.coastal")) {
					coastalPackages.add(packag);
					coastalClasses.add(type);
				}
			}
		}
		coastalPackages2 = sortList(coastalPackages, new PackageElement[] {});
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
		for (PackageElement packageElem : coastalPackages2) {
			String name = packageElem.getSimpleName().toString();
			writer.tr();
			writer.td().a(name, name).tdEnd();
			// TODO writer.td().tags(packageDoc.firstSentenceTags()).tdEnd();
			writer.trEnd();
			// generatePackage(packageElem);
		}
		writer.tbodyEnd().tableEnd();
		writer.sectionEnd();
		writer.close();
	}

	/**
	 * Generate a page for a given package.
	 *
	 * @param packageElem
	 *                   package documentation
	 * @throws IOException
	 *                     if file can not be written to
	 */
//	private void generatePackage(PackageElement packageElem) throws IOException {
//		String name = packageElem.getSimpleName().toString();
//		MdWriter writer = new MdWriter(destination, name, name);
//		generatePackageSidebar(writer);
//		writer.section("main package").h1("{{ page.title | escape }}");
//		writer.tags(packageElem.inlineTags(), -1);
//		int interfaceCount = 0;
//		ClassDoc[] interfaces = sortList(packageElem.interfaces(), new ClassDoc[] {});
//		if (interfaces.length > 0) {
//			writer.h2("Interfaces").table("classes").tbody();
//			for (ClassDoc interfac : interfaces) {
//				writer.tr();
//				writer.td().a(interfac.name(), interfac.name()).tdEnd();
//				writer.td().tags(interfac.firstSentenceTags()).tdEnd();
//				writer.trEnd();
//				generateClass(interfac);
//				interfaceCount++;
//			}
//			writer.tbodyEnd().tableEnd();
//		}
//		if (interfaceCount > 0) {
//			ClassDoc[] classes = sortList(packageElem.allClasses(), new ClassDoc[] {});
//			writer.h2("Classes").table("classes").tbody();
//			for (ClassDoc classDoc : classes) {
//				if (classDoc.isInterface()) {
//					continue;
//				}
//				writer.tr();
//				writer.td().a(classDoc.name(), classDoc.name()).tdEnd();
//				writer.td().tags(classDoc.firstSentenceTags()).tdEnd();
//				writer.trEnd();
//				generateClass(classDoc);
//			}
//			writer.tbodyEnd().tableEnd();
//		}
//		if (hasAfterTag(packageElem.inlineTags())) {
//			writer.h2("Description").tags(packageElem.inlineTags(), 1);
//		}
//		writer.sectionEnd().close();
//	}

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
		for (PackageElement packageElem : coastalPackages2) {
			String name = packageElem.getSimpleName().toString();
			writer.li("toc-entry toc-h3").a(name, name).liEnd();
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

}

//public class Doclet {
//
//	/**
//	 * Generate the documentation.
//	 *
//	 * @param root
//	 *             root document
//	 * @return true on success
//	 */
//	private boolean startDoc(DocletEnvironment root) {
//		try {
//			initArrays(root);
//			generatePackageSummary();
//		} catch (IOException e) {
//			root.printError(e.getMessage());
//			return false;
//		}
//		root.printNotice("Done");
//		root.
//		return true;
//	}
//
//	/**
//	 * Reduce a name by removing any registered qualifiers.
//	 *
//	 * @param name
//	 *             name to process
//	 * @return resulting name possibly with its qualifier excluded
//	 */
//	private String excludeQualifier(String name) {
//		for (String excludedQualifier : excludedQualifiers) {
//			if (excludedQualifier.endsWith("*")) {
//				if (name.startsWith(excludedQualifier.substring(0, excludedQualifier.length() - 1))) {
//					int index = name.lastIndexOf(".", name.length() + 1 - 1);
//					return name.substring(index + 1);
//				}
//			}
//		}
//		int index = name.length() + 1;
//		while ((index = name.lastIndexOf(".", index - 1)) != -1) {
//			String prefix = name.substring(0, index + 1);
//			if (excludedQualifiers.contains(prefix)) {
//				return name.substring(index + 1);
//			}
//		}
//		return name;
//	}
//
//	/**
//	 * Check if a sequence of tags includes an <code>@after</code> tag.
//	 *
//	 * @param inlineTags
//	 *                   sequence of tags
//	 * @return true if and only if the sequence includes
//	 */
//	private boolean hasAfterTag(Tag[] inlineTags) {
//		for (Tag tag : inlineTags) {
//			if (tag.name().equals("@after")) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * Generate a page for a given class.
//	 *
//	 * @param classDoc
//	 *                 class documentation
//	 * @throws IOException
//	 *                     if file can not be written to
//	 */
//	private void generateClass(ClassDoc classDoc) throws IOException {
//		MdWriter writer = new MdWriter(destination, classDoc.name(), classDoc.name(), true);
//		generatePackageSidebar(writer);
//		writer.section("main class").h1("{{ page.title | escape }}");
//		writer.tags(classDoc.inlineTags());
//		// FIELDS
//		FieldDoc[] fields = classDoc.fields();
//		if (fields.length > 0) {
//			int numberOfConstants = 0;
//			for (FieldDoc field : fields) {
//				if (field.isStatic() && field.isFinal()) {
//					numberOfConstants++;
//				}
//			}
//			if (numberOfConstants > 0) {
//				// writer.h2("Constants");
//				for (FieldDoc field : fields) {
//					if (field.isStatic() && field.isFinal()) {
//						writer.h2(null, field.name(), field.name());
//						writer.prejava();
//						writer.cdata(field.modifiers() + " ");
//						writer.cdata(excludeQualifier(field.type().qualifiedTypeName()) + " ");
//						writer.cdata(field.name());
//						String value = field.constantValueExpression();
//						if (value != null) {
//							writer.cdata(" = " + value);
//						}
//						writer.cdata("\n").prejavaEnd();
//						writer.p().tags(field.inlineTags()).pEnd();
//					}
//				}
//			}
//			if (numberOfConstants < fields.length) {
//				// writer.h2("Fields");
//				for (FieldDoc field : fields) {
//					if (!field.isStatic() || !field.isFinal()) {
//						writer.h2(null, field.name(), field.name());
//						writer.prejava();
//						writer.cdata(field.modifiers() + " ");
//						writer.cdata(
//								excludeQualifier(field.type().qualifiedTypeName()) + field.type().dimension() + " ");
//						writer.cdata(field.name());
//						writer.cdata("\n").prejavaEnd();
//						writer.p().tags(field.inlineTags()).pEnd();
//					}
//				}
//			}
//		}
//		// CONSTRUCTORS
//		ConstructorDoc[] constructors = classDoc.constructors();
//		if (constructors.length > 0) {
//			// writer.h2("Constructors");
//			for (ConstructorDoc constructor : constructors) {
//				writer.h2(null, constructor.name(), constructor.name() + constructor.flatSignature());
//				writer.prejava();
//				writer.cdata(constructor.modifiers() + " ");
//				generateMethodHead(writer, constructor);
//				writer.prejavaEnd();
//				generateMethod(writer, constructor);
//			}
//		}
//		// METHODS
//		MethodDoc[] methods = classDoc.methods();
//		if (methods.length > 0) {
//			// writer.h2("Methods");
//			for (MethodDoc method : methods) {
//				writer.h2(null, method.name(), method.name() + method.flatSignature());
//				writer.prejava();
//				writer.cdata(method.modifiers() + " ");
//				writer.cdata(excludeQualifier(method.returnType().qualifiedTypeName()) + " ");
//				generateMethodHead(writer, method);
//				writer.prejavaEnd();
//				generateMethod(writer, method);
//			}
//		}
//		writer.sectionEnd();
//		generateClassSidebar(writer, classDoc);
//		writer.close();
//	}
//
//	/**
//	 * Generate the method head include its name, return type, and parameters.
//	 *
//	 * @param writer
//	 *                   markdown writer
//	 * @param executable
//	 *                   method information
//	 */
//	private void generateMethodHead(MdWriter writer, ExecutableMemberDoc executable) {
//		writer.cdata(executable.name() + "(");
//		Parameter[] params = executable.parameters();
//		if (params.length > 0) {
//			boolean isFirst = true;
//			for (Parameter param : params) {
//				if (isFirst) {
//					isFirst = false;
//				} else {
//					writer.cdata(", ");
//				}
//				writer.cdata(excludeQualifier(param.type().qualifiedTypeName()) + param.type().dimension() + " ");
//				writer.cdata(param.name());
//			}
//		}
//		writer.cdata(")\n");
//	}
//
//	/**
//	 * Generate the description for a method.
//	 *
//	 * @param writer
//	 *                   markdown writer
//	 * @param executable
//	 *                   method information
//	 */
//	private void generateMethod(MdWriter writer, ExecutableMemberDoc executable) {
//		writer.tags(executable.inlineTags());
//		Parameter[] params = executable.parameters();
//		ParamTag[] paramTags = executable.paramTags();
//		if (params.length > 0) {
//			writer.h4("Parameters").table("parameters").tbody();
//			for (int i = 0, n = params.length; i < n; i++) {
//				writer.tr();
//				writer.td().cdata(params[i].name()).cdata("<br/>");
//				writer.span("paramtype").cdata(excludeQualifier(params[i].type().qualifiedTypeName())).spanEnd();
//				writer.tdEnd();
//				if (i < paramTags.length) {
//					writer.td().tags(paramTags[i].inlineTags()).tdEnd();
//				} else {
//					writer.td().tdEnd();
//				}
//				writer.trEnd();
//			}
//			writer.tbodyEnd().tableEnd();
//		}
//		Tag[] returnTags = executable.tags("@return");
//		if (returnTags.length > 0) {
//			writer.h4("Returns");
//			writer.p().tags(returnTags[0].inlineTags()).pEnd();
//		}
//		// writer.h4("Throws");
//	}
//
//	/**
//	 * Generate the right-hand sidebar that lists all methods and fields.
//	 *
//	 * @param writer
//	 *                 markdown writer
//	 * @param classDoc
//	 *                 class to generate the sidebar for
//	 * @throws IOException
//	 *                     if file can not be written to
//	 */
//	private void generateClassSidebar(MdWriter writer, ClassDoc classDoc) throws IOException {
//		writer.section("apitoc").ul("section-nav");
//		FieldDoc[] fields = classDoc.fields();
//		if (fields.length > 0) {
//			fields = sortList(fields, new FieldDoc[] {});
//			int numberOfConstants = 0;
//			for (FieldDoc field : fields) {
//				if (field.isStatic() && field.isFinal()) {
//					numberOfConstants++;
//				}
//			}
//			if (numberOfConstants > 0) {
//				writer.li("toc-entry toc-h2").cdata("Constants").ul();
//				for (FieldDoc field : fields) {
//					if (field.isStatic() && field.isFinal()) {
//						writer.li("toc-entry toc-h3").a(classDoc.name() + "#" + field.name(), field.name()).liEnd();
//					}
//				}
//				writer.ulEnd().liEnd();
//			}
//			if (numberOfConstants < fields.length) {
//				writer.li("toc-entry toc-h2").cdata("Fields").ul();
//				for (FieldDoc field : fields) {
//					if (!field.isStatic() || !field.isFinal()) {
//						writer.li("toc-entry toc-h3").a(classDoc.name() + "#" + field.name(), field.name()).liEnd();
//					}
//				}
//				writer.ulEnd().liEnd();
//			}
//		}
//		ConstructorDoc[] constructors = classDoc.constructors();
//		if (constructors.length > 0) {
//			constructors = sortList(constructors, new ConstructorDoc[] {});
//			writer.li("toc-entry toc-h2").cdata("Constructors").ul();
//			for (ConstructorDoc constructor : constructors) {
//				String text = constructor.name() + constructor.flatSignature();
//				String anchor = classDoc.name() + "#" + text;
//				writer.li("toc-entry toc-h3");
//				writer.a(anchor, text);
//				writer.liEnd();
//			}
//			writer.ulEnd().liEnd();
//		}
//		MethodDoc[] methods = classDoc.methods();
//		if (methods.length > 0) {
//			methods = sortList(methods, new MethodDoc[] {});
//			writer.li("toc-entry toc-h2").cdata("Methods").ul();
//			for (MethodDoc method : methods) {
//				String text = method.name() + method.flatSignature();
//				String anchor = classDoc.name() + "#" + text;
//				writer.li("toc-entry toc-h3");
//				writer.a(anchor, text);
//				writer.liEnd();
//			}
//			writer.ulEnd().liEnd();
//		}
//		writer.cdata("\n").ulEnd().sectionEnd();
//	}
//
//}

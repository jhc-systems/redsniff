/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jhc.redsniff.generation;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.hamcrest.generator.QDox;
import org.hamcrest.generator.QDoxFactoryReader;
import org.hamcrest.generator.QuickReferenceWriter;
import org.hamcrest.generator.SugarConfiguration;
import org.hamcrest.generator.SugarGenerator;

public class PackageScanningGenerator {

	private final SugarConfiguration sugarConfiguration;
	private final ClassLoader classLoader;
	private final QDox qdox;
	private File sourceDir;
	private Class<?> parentClass;

	public PackageScanningGenerator(SugarConfiguration sugarConfiguration,
			ClassLoader classLoader,Class<?> parentClass) {
		this.sugarConfiguration = sugarConfiguration;
		this.classLoader = classLoader;
		this.parentClass = parentClass;
		qdox = new QDox();
		
	}

	public void addSourceDir(File sourceDir) {
		this.sourceDir = sourceDir;
		qdox.addSourceTree(sourceDir);
	}

	public void addClasses(String package_name_prefix) {
		try {
			for (String className : classesInPackage(package_name_prefix))
				addClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private Collection<String> classesInPackage(String package_name_prefix) {
		File packageDir=new File(sourceDir.getAbsolutePath() +"\\"+ package_name_prefix.replaceAll("\\.","\\\\"));
		Set<String> classNames = new HashSet<String>();
		for(File classFile:FileUtils.listFiles(packageDir, FileFilterUtils.suffixFileFilter(".java"),TrueFileFilter.INSTANCE))
			classNames.add(classFile.getAbsolutePath().replace(sourceDir.getAbsolutePath()+"\\","").replace(".java","").replaceAll("\\\\", "."));
	//classNames.remove(o)
		return classNames;
	}

	

	private void addClass(String className) throws ClassNotFoundException {
		Class<?> cls = classLoader.loadClass(className);
		sugarConfiguration.addFactoryMethods(new QDoxFactoryReader(
				new FactoryMethodReader(cls,parentClass), qdox, className));
	}

	public static void main(String[] args) throws Exception {

		if (args.length != 5) {
			System.err
					.println("Args: source-dir package-class-filter parent-class generated-class output-dir");
			System.err.println("");
			System.err
					.println("    source-dir  : Path to Java source containing matchers to generate sugar for.");
			System.err
					.println("                  May contain multiple paths, separated by commas.");
			System.err.println("                  e.g. src/java,src/more-java");
			System.err
					.println("    package-class-filter  : base of package to look for classes with methods, eg jhc.selenium.matchers");

			System.err.println("");
			System.err
			.println("parent-class : Full name of parent class type to examine - eg org.hamcrest.Matcher, jhc.selenium.seeker.Seeker");
			System.err
			.println("                  e.g. org.myproject.MyMatchers");
			System.err
					.println("generated-class : Full name of class to generate.");
			System.err
					.println("                  e.g. org.myproject.MyMatchers");
			System.err.println("");
			System.err
					.println("     output-dir : Where to output generated code (package subdirs will be");
			System.err.println("                  automatically created).");
			System.err.println("                  e.g. build/generated-code");
			System.exit(-1);
		}

		String srcDirs = args[0];
		String package_name_prefix = args[1];
		String parentClassName= args[2];
		String fullClassName = args[3];
		File outputDir = new File(args[4]);

		String fileName = fullClassName.replace('.', File.separatorChar)
				+ ".java";
		int dotIndex = fullClassName.lastIndexOf(".");
		String packageName = dotIndex == -1 ? "" : fullClassName.substring(0,
				dotIndex);
		String shortClassName = fullClassName.substring(dotIndex + 1);

		if (!outputDir.isDirectory()) {
			System.err.println("Output directory not found : "
					+ outputDir.getAbsolutePath());
			System.exit(-1);
		}

		Class<?> parentClass = Class.forName(parentClassName);
		
		File outputFile = new File(outputDir, fileName);
		outputFile.getParentFile().mkdirs();
		File tmpFile = new File(outputDir,fileName + ".tmp");

		SugarGenerator sugarGenerator = new SugarGenerator();
		try {
			sugarGenerator.addWriter(new SeleniumFactoryWriter(packageName,
					shortClassName, new FileWriter(tmpFile)));
			sugarGenerator.addWriter(new QuickReferenceWriter(System.out));

			PackageScanningGenerator pkgScanningGenerator = new PackageScanningGenerator(
					sugarGenerator,
					PackageScanningGenerator.class.getClassLoader(),parentClass );

			if (srcDirs.trim().length() > 0) {
				for (String srcDir : srcDirs.split(",")) {
					pkgScanningGenerator.addSourceDir(new File(srcDir));
				}
			}
			// could add use of xml just to list filter expressions
			// pkgScanningGenerator.load(new InputSource(configFile));
			pkgScanningGenerator.addClasses(package_name_prefix);

			System.out.println("Generating " + fullClassName);
			sugarGenerator.generate();
			sugarGenerator.close();
			outputFile.delete();
			FileUtils.moveFile(tmpFile, outputFile);
			
		} finally {
			tmpFile.delete();
			sugarGenerator.close();
		}
	}
}

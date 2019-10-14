package com.cherokeelessons.builders;

import java.io.PrintWriter;

import com.cherokeelessons.dict.client.BuildInfo;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class BuildInfoGenerator extends Generator {
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		TypeOracle typeOracle = context.getTypeOracle();
		assert typeOracle != null;

		JClassType classType = typeOracle.findType(typeName);
		if (classType == null) {
			logger.log(TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'", null);
			throw new UnableToCompleteException();
		}

		String packageName = classType.getPackage().getName();
		String simpleName = classType.getSimpleSourceName() + "Impl";
		String qualifiedClassName = packageName + "." + simpleName;

		ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
				packageName, simpleName);
		composerFactory.addImport(BuildInfo.class.getCanonicalName());
		composerFactory.addImplementedInterface(BuildInfo.class.getName());

		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
		if (printWriter == null) {
			return qualifiedClassName;
		}

		SourceWriter sourceWriter = composerFactory.createSourceWriter(context,	printWriter);
		if (sourceWriter == null) {
			return qualifiedClassName;
		}

		// write the method body of getBuildTimestamp
		sourceWriter.println("public java.util.Date getBuildTimestamp() {");
		sourceWriter.println("    return new java.util.Date(" + System.currentTimeMillis() + "l);");
		sourceWriter.println("}");
		// method body ends

		sourceWriter.commit(logger);
		return packageName + "." + simpleName;
	}
}

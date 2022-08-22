package jadx.plugins.input.java;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import jadx.api.plugins.JadxPlugin;
import jadx.api.plugins.JadxPluginContext;
import jadx.api.plugins.JadxPluginInfo;
import jadx.api.plugins.input.ICodeLoader;
import jadx.api.plugins.input.JadxCodeInput;
import jadx.api.plugins.input.data.impl.EmptyCodeLoader;

public class JavaInputPlugin implements JadxPlugin, JadxCodeInput {

	public static final JadxPluginInfo PLUGIN_INFO = new JadxPluginInfo(
			"java-input",
			"JavaInput",
			"Load .class and .jar files");

	@Override
	public JadxPluginInfo getPluginInfo() {
		return PLUGIN_INFO;
	}

	@Override
	public void init(JadxPluginContext context) {
		context.addCodeInput(this);
	}

	@Override
	public ICodeLoader loadFiles(List<Path> inputFiles) {
		return loadClassFiles(inputFiles, null);
	}

	public static ICodeLoader loadClassFiles(List<Path> inputFiles, @Nullable Closeable closeable) {
		List<JavaClassReader> readers = new JavaFileLoader().collectFiles(inputFiles);
		if (readers.isEmpty()) {
			return EmptyCodeLoader.INSTANCE;
		}
		return new JavaLoadResult(readers, closeable);
	}
}

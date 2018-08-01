package com.crayon2f.java8.kit;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by feiFan.gou on 2018/2/12 10:43.
 */
public class ScriptingKit {

    private static final String javascript = "javascript";
    private static final String nas_horn = "nashorn";
    private static final String ruby = "jruby";

    public static ScriptEngine javascriptEngine() {

        return generator(javascript);
    }

    public static ScriptEngine nasHornEngine() {

        return generator(nas_horn);
    }

    public static ScriptEngine rubyEngine() {

        return generator(ruby);
    }

    private static ScriptEngine generator(String engineName) {

        return new ScriptEngineManager().getEngineByName(engineName);
    }


    public static Object evalStr(ScriptEngine engine, String script) {

        try {
            return engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object runJsFile(String jsFilePath, ScriptEngine engine, Bindings bindings) {

        try(Reader reader = Files.newBufferedReader(Paths.get(jsFilePath))) {
            if (null == bindings) {
                return engine.eval(reader);
            }
            return engine.eval(reader, bindings);
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object runJsFile(String jsFilePath, ScriptEngine engine) {

        try {
            return engine.eval(String.format("load('%s')", jsFilePath));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(ScriptEngine engine, String key) {

        return Optional.ofNullable(engine).map(ths -> ((T) ths.get(key))).orElse(null);
    }
}




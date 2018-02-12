package com.ivan.java8.scripting;

import com.ivan.java8.kit.ScriptingKit;
import com.ivan.java8.kit.StringKit;
import com.ivan.java8.pojo.Article;
import org.junit.Test;

import javax.script.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static javax.script.ScriptContext.ENGINE_SCOPE;
import static javax.script.ScriptContext.GLOBAL_SCOPE;

/**
 * Created by feiFan.gou on 2018/2/12 10:15.
 */
public class Scripting {

    @Test
    public void helloWorld() throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String script = "print('hello world')";
        engine.eval(script);
    }

    @Test
    public void availableEngines() {

        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> engineFactories = manager.getEngineFactories();
        engineFactories.forEach(factory -> {
            System.out.println("Engine Name:" + factory.getEngineName());
            System.out.println("Engine Version:" + factory.getEngineVersion());
            System.out.println("Language Name:" + factory.getLanguageName());
            System.out.println("Language Version:" + factory.getLanguageVersion());
            System.out.println("Engine Short Names:" + factory.getNames());
            System.out.println("Mime Types:" + factory.getMimeTypes());
            System.out.println(StringKit.divide);
        });
    }

    @Test
    public void FindOutTheSyntax() throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine javascript = manager.getEngineByName("javascript");
        ScriptEngineFactory factory = javascript.getFactory();
        String script = factory.getOutputStatement("\"javascript\"");
        System.out.println("Syntax: " + script);
        javascript.eval(script);
    }

    @Test
    public void output() throws ScriptException {

        ScriptEngine engine = ScriptingKit.javascriptEngine();
        engine.eval("print('hello','world')");
    }

    @Test
    public void write2File() {

        ScriptEngine engine = ScriptingKit.javascriptEngine();
        File file = new File(this.getClass().getResource("/text.txt").getFile());
        System.out.println(file.exists());

        try (FileWriter writer = new FileWriter(file)) {
            ScriptContext context = engine.getContext();
            context.setWriter(writer);
            String script = "print('write to file already !')";
            engine.eval(script);
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void runJavascriptFromJsFile() {

        String jsFile = this.getClass().getResource("/test.js").getFile().substring(1);
        Path path = Paths.get(jsFile);
        ScriptEngine engine = ScriptingKit.nasHornEngine();
        try (Reader reader = Files.newBufferedReader(path)) {
            engine.put("param", "this is a param");
            Object eval = engine.eval(reader);
            System.out.println(eval);
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }
    }

    @Test
    //returns the last value in the script as an Object
    public void eval() throws ScriptException {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        Object result;
        result = engine.eval("1 + 2;");
        System.out.println(result); //3
        result = engine.eval("1 + 2; 3 + 4;");
        System.out.println(result); //7 覆盖 1 + 2
        result = engine.eval("1 + 2; 3 + 4; var v = 5; v = 6;");
        System.out.println(result);
        result = engine.eval("1 + 2; 3 + 4; var v = 5;");
        System.out.println(result);
        result = engine.eval("print(1 + 2)");
        System.out.println(result);

    }

    @Test
    public void transmitParam() throws ScriptException {

        Article article = new Article("标题4", "作者1", 12);
        ScriptEngine engine = ScriptingKit.nasHornEngine();
        engine.put("article", article);
        engine.eval("article.setCount(4)");
        System.out.println(article.getCount());
    }

    @Test
    public void rubyEngine() {

        ScriptEngine engine = ScriptingKit.javascriptEngine();
        engine.put("msg", "this is a ruby script");
        Object result = ScriptingKit.evalStr(engine, "print(msg)");
        System.out.println(result);
    }

    @Test
    public void engineGet() {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        ScriptingKit.runJsFile(this.getClass().getResource("/test.js").getFile().substring(1), engine, null);
        Article article = ScriptingKit.get(engine, "article");
        System.out.println(StringKit.divide);
        System.out.println(article);
    }

    @Test
    public void buildings() {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        Bindings bindings = engine.createBindings();
        bindings.put("key_1", 90);
        Object result = ScriptingKit.runJsFile(this.getClass().getResource("/test.js").getFile().substring(1), engine, bindings);
        System.out.println(result);

    }

    /**
     * @throws ScriptException
     *  ScriptContext.ENGINE_SCOPE
        ScriptContext.GLOBAL_SCOPE
        引擎范围的优先级高于全局范围。
     */
    @Test
    public void context() throws ScriptException{

        ScriptContext context = new SimpleScriptContext();
        context.setAttribute("name", "jack", ENGINE_SCOPE);
        context.setAttribute("age", 35, ENGINE_SCOPE);
        context.setAttribute("address", "北京", ENGINE_SCOPE);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        manager.put("n1", 1);
        String script = "var sum = n1 + n2;print(msg + "
                + "' n1=' + n1 + ', n2=' + n2 + " + "', sum=' + sum);";

        engine.put("n2", 2);
        engine.put("msg", "a string");
        engine.eval(script);

        Bindings bindings = engine.createBindings();
        bindings.put("n2", 3);
        bindings.put("msg", "another string");
        engine.eval(script, bindings);

        ScriptContext ctx = new SimpleScriptContext();
        Bindings ctxGlobalBindings = engine.createBindings();
        ctx.setBindings(ctxGlobalBindings, GLOBAL_SCOPE);
        ctx.setAttribute("n1", 4, GLOBAL_SCOPE);
        ctx.setAttribute("n2", 5, ENGINE_SCOPE);
        ctx.setAttribute("msg", "ScriptContext:", ENGINE_SCOPE);
        engine.eval(script, ctx);

        engine.eval(script);
    }

    @Test
    public void runJsByLoad() throws ScriptException {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        ScriptingKit.runJsFile(this.getClass().getResource("/test.js").getFile().substring(1), engine);
        Object result = engine.eval("add(1,2)");
        System.out.println(result);
    }

    /**
     * 有点像反射
     */
    @Test
    public void callJsFunction() {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        ScriptingKit.runJsFile(this.getClass().getResource("/test.js").getFile().substring(1), engine);
        Invocable inv = (Invocable) engine;

    }
}

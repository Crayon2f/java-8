package com.ivan.java8.scripting;

import com.ivan.java8.kit.ScriptingKit;
import com.ivan.java8.kit.StringKit;
import com.crayon2f.common.pojo.Article;
import org.junit.jupiter.api.Test;

import javax.script.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static javax.script.ScriptContext.ENGINE_SCOPE;
import static javax.script.ScriptContext.GLOBAL_SCOPE;

/**
 * Created by feiFan.gou on 2018/2/12 10:15.
 */
class Scripting {

    @Test
    void helloWorld() throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String script = "print('hello world')";
        engine.eval(script);
    }

    @Test
    void availableEngines() {

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
    void findOutTheSyntax() throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine javascript = manager.getEngineByName("javascript");
        ScriptEngineFactory factory = javascript.getFactory();
        String script = factory.getOutputStatement("\"javascript\"");
        System.out.println("Syntax: " + script);
        javascript.eval(script);
    }

    @Test
    void output() throws ScriptException {

        ScriptEngine engine = ScriptingKit.javascriptEngine();
        engine.eval("print('hello','world')");
    }

    @Test
    void write2File() {

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
    void runJavascriptFromJsFile() {

        String jsFile = this.getClass().getResource("/javascript/test.js").getFile().substring(1);
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
    void eval() throws ScriptException {

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
    void transmitParam() throws ScriptException {

        Article article = new Article("标题4", "作者1", 12);
        ScriptEngine engine = ScriptingKit.nasHornEngine();
        engine.put("article", article);
        engine.eval("article.setCount(4)");
        System.out.println(article.getCount());
    }

    @Test
    void rubyEngine() {

        ScriptEngine engine = ScriptingKit.javascriptEngine();
        engine.put("msg", "this is a ruby script");
        Object result = ScriptingKit.evalStr(engine, "print(msg)");
        System.out.println(result);
    }

    @Test
    void engineGet() {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        ScriptingKit.runJsFile(this.getClass().getResource("/javascript/test.js").getFile().substring(1), engine, null);
        Article article = ScriptingKit.get(engine, "article");
        System.out.println(StringKit.divide);
        System.out.println(article);
    }

    @Test
    void buildings() {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        Bindings bindings = engine.createBindings();
        bindings.put("key_1", 90);
        Object result = ScriptingKit.runJsFile(this.getClass().getResource("/javascript/test.js").getFile().substring(1), engine, bindings);
        System.out.println(result);

    }

    /**
     * @throws ScriptException
     *  ScriptContext.ENGINE_SCOPE
        ScriptContext.GLOBAL_SCOPE
        引擎范围的优先级高于全局范围。
     */
    @Test
    void context() throws ScriptException{

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
    void runJsByLoad() throws ScriptException {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        ScriptingKit.runJsFile(this.getClass().getResource("/javascript/test.js").getFile().substring(1), engine);
        Object result = engine.eval("add(1,2)");
        System.out.println(result);
    }

    /**
     * 有点像反射
     */
    @Test
    void callJsFunction() throws ScriptException, NoSuchMethodException {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        ScriptingKit.runJsFile(this.getClass().getResource("/javascript/test.js").getFile().substring(1), engine);
        Invocable inv = (Invocable) engine;
        String food = "rice, noodles, dumplings", words = "hello script engine !!";
        Object person = engine.get("person");
        inv.invokeMethod(person, "eat", food);
        inv.invokeMethod(person, "speak", words);
        System.out.println(inv.invokeFunction("add", 4, 5));

    }

    @Test
    void interfaces() throws ScriptException {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        engine.eval(String.format("load('%s')", this.getClass().getResource("/javascript/interface.js").getFile().substring(1)));

        Invocable invocable = (Invocable) engine;
        Interface impl = invocable.getInterface(Interface.class);
        //同名函数,取最后定义的一个
        Optional.ofNullable(impl).ifPresent(ths -> System.out.println(ths.add(2, 4, 5)));
    }

    @Test
    void complied() {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        Optional.ofNullable(engine).filter(ths -> ths instanceof Compilable).ifPresent(ths -> {
            Compilable comp = (Compilable) engine;

            try {
                CompiledScript cScript = comp.compile("print(n1 + n2)");

                Bindings scriptParams = engine.createBindings();
                scriptParams.put("n1", 2);
                scriptParams.put("n2", 3);
                cScript.eval(scriptParams);

                scriptParams.put("n1", 9);
                scriptParams.put("n2", 7);
                cScript.eval(scriptParams);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void javaInScript() throws ScriptException {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        engine.eval(String.format("load('%s')",
                this.getClass().getResource("/javascript/java.js").getFile().substring(1)));

    }

    @Test
    void importJavaClass() throws ScriptException {

        ScriptEngine engine = ScriptingKit.nasHornEngine();
        engine.eval(String.format("load('%s')",
                this.getClass().getResource("/javascript/import_java.js").getFile().substring(1)));
        LocalDate date = LocalDate.now();
    }
}

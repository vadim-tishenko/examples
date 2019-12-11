import net.openhft.compiler.CompilerUtils;
import p1.Sum;
//https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes
public class DynamicJavaCompilation {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        // dynamically you can call
        String className = "mypackage.MyClass";
        String javaCode = "package mypackage;\n" +
                "public class MyClass implements Runnable {\n" +
                "    public void run() {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "}\n";

        String className2 = "mypackage.MyClass2";
        String javaCode2 = "package mypackage;\n" +
                "public class MyClass2 implements p1.Sum {\n" +
                "    public int  summa(int op1,int op2) {\n" +
                "return op1+op2;\n"+
                "    }\n" +
                "}\n";
        Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
        Runnable runner = (Runnable) aClass.newInstance();
        runner.run();
        Class aClass2 = CompilerUtils.CACHED_COMPILER.loadFromJava(className2, javaCode2);
        Sum summer = (Sum) aClass2.newInstance();
        System.out.println(summer.summa(3,5));

    }
}

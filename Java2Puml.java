package pumlFromJava;

import java.util.spi.ToolProvider;

public class Java2Puml
{

    public static void main(String[] args)
    {
        ToolProvider toolProvider = ToolProvider.findFirst("javadoc").get();
        System.out.println(toolProvider.name());

        // Variable de Debug
        String[] debugArgs = new String[] {
                "-private",
                "-sourcepath", ".//src//",
                "-doclet", "pumlFromJava.Doclets.PumlDoclet",
                "-docletpath", ".//out//production//Refactorisation_SAE//",
                "-subpackages", "western",
                "-out", "Test2",
                "-d","./src/western/SortieJavaDoc"
        };
        toolProvider.run(System.out, System.err, debugArgs);
    }
}

package pumlFromJava.Processors;

public class FormatProcessor extends ElementProcessor {
    public FormatProcessor(String formatString) {
        super(formatString);
    }
    @Override
    public String processElement() {
        String typeName = this.string;
        return switch (typeName) {
            case "Java.util.List<" -> "[*]" + typeName.substring(15, typeName.toString().length()-1);
            case "java.lang.String" -> " String";
            case "int", "long", "byte", "short" -> "Integer";
            case "void" -> "";
            case "char" -> "Char";
            case "double", "float" -> "Float";
            case "boolean" -> "Boolean";

            default -> " " + typeName.substring(0, 1).toUpperCase() + typeName.substring(1);
        };
    }
}

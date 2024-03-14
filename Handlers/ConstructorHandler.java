package pumlFromJava.Handlers;

import pumlFromJava.Processors.FormatProcessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorHandler extends ElementHandler {
    public ConstructorHandler(Element constructorElement) {
        super(constructorElement);
    }

    @Override
    public String getContent() {
        // Cast to ExecutableElement to handle constructors properly.
        ExecutableElement constructorElement = (ExecutableElement) super.element;

        // Get the name of the class from the parent of the constructor.
        String className = constructorElement.getEnclosingElement().getSimpleName().toString();

        // Get the parameters of the constructor.
        List<? extends VariableElement> parameters = constructorElement.getParameters();
        // Prepare the parameters for display.
        String parametersDisplay = parameters.stream()
                .map(param -> {
                    String typeName = param.asType().toString();
                    FormatProcessor formatProcessor = new FormatProcessor(typeName);
                    String formattedTypeName = formatProcessor.processElement();
                    return formattedTypeName + " " + param.getSimpleName().toString();
                })
                .collect(Collectors.joining(", "));

        // Build the output string.
        String output = "<<Create>> " + className + "(" + parametersDisplay + ")";

        return output + "\n";
    }
}
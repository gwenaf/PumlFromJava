package pumlFromJava.Processors;

import javax.lang.model.element.ExecutableElement;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MethodProcessor extends ElementProcessor {
    public MethodProcessor(ExecutableElement executableElement){
        super(executableElement);
    }
    @Override
    public String processElement() {
        return super.executableElement.getParameters().stream()
                .map(p -> p.asType().toString() + " " + p.getSimpleName())
                .collect(Collectors.joining(", "));
    }
}

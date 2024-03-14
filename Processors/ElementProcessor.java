package pumlFromJava.Processors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public abstract class ElementProcessor {
    protected Element element;
    protected String string;
    protected ExecutableElement executableElement;
    public ElementProcessor(Element element)
    {
        this.element = element;
    }
    public ElementProcessor(String string) {
        this.string = string;
    }
    public ElementProcessor(ExecutableElement executableElement) {
        this.executableElement = executableElement;
    }
    public abstract String processElement();
}

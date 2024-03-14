package pumlFromJava.Handlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;

public abstract class ElementHandler {
    protected Element element;
    protected int selector;
    public ElementHandler(Element element)
    {
        this.element = element;
    }
    public ElementHandler(Element element, int selector) {
        this.element = element;
        this.selector = selector;
    }
    public abstract String getContent();
}

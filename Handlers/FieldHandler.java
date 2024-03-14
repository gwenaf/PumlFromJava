package pumlFromJava.Handlers;

import pumlFromJava.Processors.ModifierProcessor;
import pumlFromJava.Processors.VisibilityProcessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class FieldHandler extends ElementHandler {
    public FieldHandler(Element element) {
        super(element);
    }
    @Override
    public String getContent() {
        VisibilityProcessor visibilityProcessor = new VisibilityProcessor(element);
        ModifierProcessor modifierProcessor = new ModifierProcessor(element);
        if (this.element.getKind() == ElementKind.FIELD) {
            return "  " + visibilityProcessor.processElement() + " " + modifierProcessor.processElement() + " " + this.element.getSimpleName() + "\n";
        }
        return "";
    }
}

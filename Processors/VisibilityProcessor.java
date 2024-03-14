package pumlFromJava.Processors;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Set;

public class VisibilityProcessor extends ElementProcessor {
    public VisibilityProcessor(Element visibilityElement) {
        super(visibilityElement);
    }
    @Override
    public String processElement() {
        Set<Modifier> modifiers = this.element.getModifiers();
        if (modifiers.contains(Modifier.PUBLIC)) {
            return "+";
        } else if (modifiers.contains(Modifier.PRIVATE)) {
            return "-";
        } else if (modifiers.contains(Modifier.PROTECTED)) {
            return "#";
        } else {
            return "~";
        }
    }
}

package pumlFromJava.Processors;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Set;

public class ModifierProcessor extends ElementProcessor {
    public ModifierProcessor(Element modifierElement) {
        super(modifierElement);
    }
    @Override
    public String processElement() {
        Set<javax.lang.model.element.Modifier> modifiers = this.element.getModifiers();
        if (modifiers.contains(javax.lang.model.element.Modifier.ABSTRACT) && modifiers.contains(javax.lang.model.element.Modifier.STATIC)) {
            return "{static} {abstract}";
        }
        if (modifiers.contains(javax.lang.model.element.Modifier.ABSTRACT)) {
            return "{abstract}";
        }
        if (modifiers.contains(javax.lang.model.element.Modifier.STATIC) && modifiers.contains(javax.lang.model.element.Modifier.FINAL)) {
            return "{readOnly} {static}";
        }
        if (modifiers.contains(javax.lang.model.element.Modifier.STATIC)) {
            return "{static}";
        }
        if (modifiers.contains(Modifier.FINAL)) {
            return "{readOnly}";
        }
        else {
            return "";
        }
    }
}

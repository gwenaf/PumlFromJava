package pumlFromJava.Handlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public class EnumHandler extends ElementHandler {
    public EnumHandler(Element enumElement) {
        super(enumElement);
    }
    public EnumHandler(Element enumElement, int selector) {
        super(enumElement, selector);
    }
    @Override
    public String getContent() {
        TypeElement typeElement = (TypeElement) super.element;
        String enumName = typeElement.getQualifiedName().toString();
        StringBuilder enumContent = new StringBuilder("enum " + enumName + " <<enumerate>> {\n");
        for (Element enclosedElement : super.element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.ENUM_CONSTANT) {
                enumContent.append("  " + enclosedElement.getSimpleName() + "\n");
            }
        }
        enumContent.append("}\n");
        return enumContent.toString();
    }
}

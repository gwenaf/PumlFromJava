package pumlFromJava.Handlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public class InterfaceHandler extends ElementHandler {
    public InterfaceHandler(Element interfaceElement) {
        super(interfaceElement);
    }
    public InterfaceHandler(Element interfaceElement, int selector) {
        super(interfaceElement, selector);
    }
    @Override
    public String getContent() {
        TypeElement typeElement = (TypeElement) this.element;
        String interfaceName = typeElement.getQualifiedName().toString();
        StringBuilder interfaceContent = new StringBuilder("interface " + interfaceName + " <<interface>> {\n");
        for (Element enclosedElement : this.element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                MethodHandler methodHandler = new MethodHandler(enclosedElement);
                interfaceContent.append(methodHandler.getContent());
            }
        }
        interfaceContent.append("}\n");
        return interfaceContent.toString();
    }
}

package pumlFromJava.RelationshipHandlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class InterfaceRelationshipHandler implements RelationshipHandler {
    @Override
    public String handleRelationship(TypeElement typeElement, Element field) {
        String elementName = typeElement.getQualifiedName().toString();
        for (TypeMirror anInterface : typeElement.getInterfaces()) {
            String interfaceName = ((TypeElement)((DeclaredType)anInterface).asElement()).getQualifiedName().toString();
            if (!interfaceName.equals("java.lang.Object")) {
                return "\"" + elementName + "\" ..|> \"" + interfaceName + "\"";
            }
        }
        return "";
    }
}

package pumlFromJava.RelationshipHandlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;

public class InheritanceRelationshipHandler implements RelationshipHandler {
    @Override
    public String handleRelationship(TypeElement typeElement, Element field) {
        String className = typeElement.getQualifiedName().toString();
        String parentName = ((TypeElement)field).getQualifiedName().toString();

        return "\"" + className + "\" <|-- \"" + parentName + "\"";
    }
}

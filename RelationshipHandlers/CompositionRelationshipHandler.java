package pumlFromJava.RelationshipHandlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class CompositionRelationshipHandler implements RelationshipHandler {
    @Override
    public String handleRelationship(TypeElement typeElement, Element field) {
        String className = typeElement.getQualifiedName().toString();
        String fieldName = field.getSimpleName().toString();
        TypeMirror fieldType = ((VariableElement) field).asType();
        String fieldTypeName = fieldType.toString();

        String relationship = className + " \"1\" --* \"*\" " + fieldTypeName + " : " + fieldName;
        return relationship;
    }
}

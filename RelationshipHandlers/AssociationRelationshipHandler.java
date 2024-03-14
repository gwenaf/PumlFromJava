package pumlFromJava.RelationshipHandlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class AssociationRelationshipHandler implements RelationshipHandler {
    private static final List<String> ignoredTypes = List.of("boolean", "char", "byte", "short", "int", "long", "float", "double",
            "java.lang.Boolean", "java.lang.Character", "java.lang.Byte", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double",
            "java.lang.String", "java.util.Set", "java.util.List");
    @Override
    public String handleRelationship(TypeElement typeElement, Element field) {
        String className = typeElement.getQualifiedName().toString();
        String fieldName = field.getSimpleName().toString();
        TypeMirror fieldType = ((VariableElement) field).asType();
        String fieldTypeName = fieldType.toString();

        if (ignoredTypes.contains(fieldTypeName)) {
            return "";
        }

        String relationship = className + " -- " + fieldTypeName + " : " + fieldName;
        return relationship;
    }
}

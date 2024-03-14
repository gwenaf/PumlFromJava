package pumlFromJava.Processors;

import pumlFromJava.Enumerations.RelationType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;

public class RelationshipTypeProcessor extends ElementProcessor{
    private final Element targetElement;
    private final Types typeUtils;
    public RelationshipTypeProcessor(Element element, Element targetElement, Types typeUtils){
        super(element);
        this.targetElement = targetElement;
        this.typeUtils = typeUtils;
    }
    @Override
    public String processElement() {
        TypeElement classA = (TypeElement) element;
        TypeElement classB = (TypeElement) targetElement;
        // Check Inheritance
        if (classB.getSuperclass().equals(classA.asType())) {
            return RelationType.INHERITANCE.toString();
        }

        // Check Aggregation
        for (VariableElement field : ElementFilter.fieldsIn(classB.getEnclosedElements())) {
            if (typeUtils.isAssignable(field.asType(), classA.asType())) {
                return RelationType.AGGREGATION.toString();
            }
        }

        // Check Dependency
        for (ExecutableElement method : ElementFilter.methodsIn(classB.getEnclosedElements())) {
            for (VariableElement param : method.getParameters()) {
                if (typeUtils.isAssignable(param.asType(), classA.asType())) {
                    return RelationType.DEPENDENCY.toString();
                }
            }
        }

        // Check Association
        for (VariableElement field : ElementFilter.fieldsIn(classB.getEnclosedElements())) {
            if (!typeUtils.isSameType(field.asType(), classA.asType())) {
                return RelationType.ASSOCIATION.toString();
            }
        }

        // Check Realization
        for (TypeMirror interf : classB.getInterfaces()) {
            if (typeUtils.isSameType(interf, classA.asType())) {
                return RelationType.REALIZATION.toString();
            }
        }

        // Autre type de relation ?
        return RelationType.UNDEFINED.toString();
    }
}

package pumlFromJava.RelationshipHandlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public interface RelationshipHandler {
    String handleRelationship(TypeElement typeElement, Element field);
}

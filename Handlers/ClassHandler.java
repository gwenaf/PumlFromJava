package pumlFromJava.Handlers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public class ClassHandler extends ElementHandler{
    public ClassHandler(Element classElement, int selector) {
        super(classElement, selector);
    }
    @Override
    public String getContent() {
        // On instancie les element requis par le programme

        // On transtype l'Element classElement en TypeElement
        TypeElement typeElement = (TypeElement) super.element;
        // On cherche le nom de la classe crée
        String className = typeElement.getQualifiedName().toString();
        // On initialise un Stringbuilder avec le début de la classe
        StringBuilder classContent = new StringBuilder("class " + className + " {\n");

        // On vérifie la valeur du selecteur
        if (super.selector == 0) {
        // Si le sélecteur est égal à 0 on sort les informations sous la forme d'un DCA
            
        } else if (super.selector == 1)
        // Si le sélecteur est égal à 1 on sort les informations sous la forme d'un DCC

        // On parcourt l'ensemble des elements compris dans la classe
        for (Element enclosedElement : super.element.getEnclosedElements()) {
            // Si l'élément est un champ
            if (enclosedElement.getKind() == ElementKind.CONSTRUCTOR) {
                // Instantiate a ConstructorHandler
                ConstructorHandler constructorHandler = new ConstructorHandler(enclosedElement);
                // Add to the StringBuilder
                classContent.append(constructorHandler.getContent());
            } else if (enclosedElement.getKind() == ElementKind.FIELD) {
                // On instancie un gestionnaire de champs
                FieldHandler fieldHandler = new FieldHandler(enclosedElement);
                // On ajoute au stringbuilder
                classContent.append(fieldHandler.getContent());
            } else if (enclosedElement.getKind() == ElementKind.METHOD) {
                MethodHandler methodHandler = new MethodHandler(enclosedElement);
                classContent.append(methodHandler.getContent());
            }
        }

        classContent.append("}\n");
        return classContent.toString();
    }
}

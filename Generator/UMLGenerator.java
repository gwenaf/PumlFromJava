package pumlFromJava.Generator;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import pumlFromJava.Handlers.*;
import pumlFromJava.Processors.RelationshipTypeProcessor;
import pumlFromJava.RelationshipHandlers.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UMLGenerator {
    private final DocletEnvironment docletEnvironment;
    private final String filename;
    public UMLGenerator(DocletEnvironment docletEnvironment, String fileName) {
        this.docletEnvironment = docletEnvironment;
        this.filename = fileName;
    }

    public void generateUML(int selector) {
        // On déclare le printwriter
        PrintWriter writer = null;
        // On essaie
        try {
            // On initialise
            writer = new PrintWriter(new File(this.filename));
        } catch (FileNotFoundException e) {
            // En cas d'erreur on envoie une exception sur le terminal
            throw new RuntimeException(e + " \n| Erreur à la création du fichier");
        }
        // On rajoute au début du document la balise @startuml ainsi que les paramètres de mise en page
        writer.println("""
                    @startuml
                    skinparam classAttributeIconSize 0
                    skinparam classFontStyle Bold
                    skinparam style strictuml
                    hide empty members
                    """);
        // On initialise la liste des éléments déjà ajoutés qui seront parcourus par les gestionnaires de relation
        List<Element> addedElements = new ArrayList<>();
        // Selection du type de diagramme à renvoyer
        if (selector == 0) {
            /* Si la valeur du sélecteur est à 0 on écrit un DCA
            *
            * Habip et Joanna
            *
            */
        } else if (selector == 1){
            // si la valeur du sélecteur est à 1 on écrit un DCC

            // On parcourt l'ensemble des Elements inclus dans l'environnement.
            for (Element element : docletEnvironment.getIncludedElements()) {
                // On initialise la liste des éléments qui va contenir tous les éléments ci-dessus
                List<Element> elements = new ArrayList<>(element.getEnclosedElements());
                // On parcourt chaque element e dans la liste.
                for (Element e : elements) {
                    // Si cet element est une classe
                    if (e.getKind() == ElementKind.CLASS) {
                        // On instancie un gestionnaire de classe avec l'élément en paramètre
                        ClassHandler classHandler = new ClassHandler(e, selector);
                        // On écrit dans le writer le contenu renvoyé par la méthode de classe getContent()
                        writer.println(classHandler.getContent());
                        // Et on ajoute l'élément traité dans la liste addedElements
                        addedElements.add(e);
                    // Si cet element est une interface
                    } else if (e.getKind() == ElementKind.INTERFACE) {
                        // On instancie un gestionnaire d'interface avec l'élément en paramètre
                        InterfaceHandler interfaceHandler = new InterfaceHandler(e, selector);
                        // On écrit dans le writer le contenu renvoyé par la méthode de classe getContent()
                        writer.println(interfaceHandler.getContent());
                        // Et on ajoute l'élément traité dans la liste addedElements
                        addedElements.add(e);
                    } else if (e.getKind() == ElementKind.ENUM) {
                        EnumHandler enumHandler = new EnumHandler(e, selector);
                        writer.println(enumHandler.getContent());
                        addedElements.add(e);
                    }
                }
                // Ajouter une vérification des relations entre elements ajoutés pour déterminer le type de relation
            }
            AggregationRelationshipHandler aggregationHandler = new AggregationRelationshipHandler();
            AssociationRelationshipHandler associationRelationshipHandler = new AssociationRelationshipHandler();
            DependencyRelationshipHandler dependencyHandler = new DependencyRelationshipHandler();
            InterfaceRelationshipHandler interfaceHandler = new InterfaceRelationshipHandler();
            InheritanceRelationshipHandler inheritanceHandler = new InheritanceRelationshipHandler();
            for (Element element : addedElements) {
                if (element.getKind() == ElementKind.CLASS) {
                    TypeElement classElement = (TypeElement) element;
                    for (Element field : classElement.getEnclosedElements()) {
                        if (field.getKind() == ElementKind.FIELD) {
                            for (Element otherElement : addedElements) {
                                if (!otherElement.equals(element) && otherElement.getKind() == ElementKind.CLASS) {
                                    TypeElement otherClassElement = (TypeElement) otherElement;
                                    RelationshipTypeProcessor processor = new RelationshipTypeProcessor(classElement, otherClassElement, docletEnvironment.getTypeUtils());
                                    String relationshipType = processor.processElement();
                                    String relationship = "";

                                    String fieldType;
                                    String otherElementType;
                                    switch (relationshipType) {
                                        case "INHERITANCE":
                                            fieldType = field.asType().toString();
                                            otherElementType = otherClassElement.getQualifiedName().toString();
                                            if (fieldType.contains(otherElementType)) {
                                                relationship = inheritanceHandler.handleRelationship(classElement, otherClassElement);
                                            }
                                            break;
                                        case "AGGREGATION":
                                            fieldType = field.asType().toString();
                                            otherElementType = otherClassElement.getQualifiedName().toString();
                                            if (fieldType.contains(otherElementType)) {
                                                relationship = aggregationHandler.handleRelationship(classElement, field);
                                            }
                                            break;
                                        case "DEPENDENCY":
                                            fieldType = field.asType().toString();
                                            otherElementType = otherClassElement.getQualifiedName().toString();
                                            if (fieldType.contains(otherElementType)) {
                                                relationship = dependencyHandler.handleRelationship(classElement, field);
                                            }
                                            break;
                                        case "REALIZATION":
                                            fieldType = field.asType().toString();
                                            otherElementType = otherClassElement.getQualifiedName().toString();
                                            if (fieldType.contains(otherElementType)) {
                                                relationship = interfaceHandler.handleRelationship(classElement, field);
                                            }
                                            break;
                                        case "ASSOCIATION":
                                            fieldType = field.asType().toString();
                                            otherElementType = otherClassElement.getQualifiedName().toString();
                                            if (fieldType.contains(otherElementType)) {
                                                relationship = associationRelationshipHandler.handleRelationship(classElement, field);
                                            }
                                            break;
                                        case "UNDEFINED":
                                        default:
                                            break;
                                    }

                                    if (!relationship.isEmpty()) {
                                        writer.println(relationship);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("La valeur du paramètre sélecteur n'est pas conforme");
        }
        // On ajoute à la fin la balise "@enduml"
        writer.println("@enduml");
        // On ferme le writer
        writer.close();
    }
}

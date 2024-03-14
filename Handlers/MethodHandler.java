package pumlFromJava.Handlers;

import pumlFromJava.Processors.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

public class MethodHandler extends ElementHandler {
    private MethodParameterProcessor methodParameterProcessor;
    public MethodHandler(Element methodElement) {
        super(methodElement);
        methodParameterProcessor = new MethodParameterProcessor((ExecutableElement) methodElement);
    }
    @Override
    public String getContent() {
        // On ajoute les Processeurs requis
        VisibilityProcessor visibilityProcessor = new VisibilityProcessor(super.element);
        ModifierProcessor modifierProcessor = new ModifierProcessor(super.element);
        MethodParameterProcessor methodParameterProcessor = new MethodParameterProcessor((ExecutableElement) super.element);
        // On initialise les variables nécessaires
        ExecutableElement executableElement = (ExecutableElement) super.element;
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        if (super.element.getKind() == ElementKind.METHOD) {
            ExecutableElement methodElement = (ExecutableElement) this.element;
            String overrideText = "";
            if (methodElement.getAnnotation(Override.class) != null) {
                TypeElement superClass = (TypeElement) ((DeclaredType) typeElement.getSuperclass()).asElement();
                ExecutableElement overriddenMethod = findOverriddenMethod(methodElement, superClass);
                if (overriddenMethod != null) {
                    overrideText = "\n\t {redefines " + superClass.getSimpleName().toString() + "::" + overriddenMethod.getSimpleName() + "(" + methodParameterProcessor.processElement() + ")}";
                }
            }
            return "  " + visibilityProcessor.processElement() + " " + modifierProcessor.processElement() + " " + methodElement.getSimpleName() + "(" + methodParameterProcessor.processElement() + ")" + overrideText + "\n";
        }
        return "";
    }
    private ExecutableElement findOverriddenMethod(ExecutableElement method, TypeElement superClass) {
        String methodName = method.getSimpleName().toString();
        String methodParameters = methodParameterProcessor.processElement();
        for (Element e : superClass.getEnclosedElements()) {
            if (e.getKind() == ElementKind.METHOD) {
                ExecutableElement superMethod = (ExecutableElement) e;
                if ((superMethod.getSimpleName().toString().equals(methodName)) && methodParameterProcessor.processElement().equals(methodParameters)) {
                    return superMethod;
                }
            }
        }
        return null;
    }
}

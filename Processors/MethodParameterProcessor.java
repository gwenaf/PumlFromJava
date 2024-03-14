package pumlFromJava.Processors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.stream.Collectors;

public class MethodParameterProcessor extends ElementProcessor {
    public MethodParameterProcessor(ExecutableElement executableElement){
        super(executableElement);
    }
    @Override
    public String processElement() {
        return super.executableElement.getParameters().stream()
                .map(p -> {
                    String type = p.asType().toString();
                    if (type.startsWith("java.util.ArrayList")) {
                        type = handleArrayListType(p.asType());
                    } else {
                        type = getSimpleName(type);
                    }
                    return type + " " + p.getSimpleName();
                })
                .collect(Collectors.joining(", "));
    }

    private String handleArrayListType(TypeMirror typeMirror) {
        if (typeMirror instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            if (!declaredType.getTypeArguments().isEmpty()) {
                String genericType = declaredType.getTypeArguments().get(0).toString();
                return getSimpleName(genericType) + "[*]";
            }
        }
        // If there's no generic type defined, fall back to just "Object[*]"
        return "Object[*]";
    }

    private String getSimpleName(String typeName) {
        int lastDotIndex = typeName.lastIndexOf('.');
        return (lastDotIndex == -1) ? typeName : typeName.substring(lastDotIndex + 1);
    }
}

package pumlFromJava.Doclets;
//Gestion des imports des classes du package
import pumlFromJava.Generator.UMLGenerator;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;


import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import java.io.File;
import java.util.*;

@SupportedOptions({"out","d"})
public class PumlDoclet implements Doclet {
    private static final Map<String, String> options = new HashMap<>();
    @Override
    public void init(Locale locale, Reporter reporter) {
    }

    @Override
    public String getName() {
        // For this doclet, the name of the doclet is just the
        // simple name of the class. The name may be used in
        // messages related to this doclet, such as in command-line
        // help when doclet-specific options are provided.
        return "PumlDoclet";
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        // This doclet does not support any options.
        return Set.of(
                new OutputFileNameOption(),
                new OutputDirectoryOption(),
                new DiagramSelectorOption()
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // This doclet supports all source versions.
        // More sophisticated doclets may use a more
        // specific version, to ensure that they do not
        // encounter more recent language features that
        // they may not be able to handle.
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        // Récupération des options

        // -------------------------------
        // Génération du fichier de sortie

        // Par défaut on crée un DCA
        int selector = 1;

        // On récupère la valeur du selecteur si elle existe dans les options
        if (options.get("-type") != null) {
            selector = Integer.parseInt(options.get("-type"));
        }
        // On récupère le nom du fichier à créer
        String filename = options.get("-out");
        // On récupère le chemin de création du fichier
        String outputDirectory = options.get("-d");
        // Message pour l'utilisateur
        System.out.println("Création du fichier \"" + filename + "\" à l'emplacement : \n" + outputDirectory);
        // On regarde si l'option -out est vide
        if (filename == null || filename.equals("")) {
            // Si elle est vide on donne le nom "Fichier.puml" par defaut
            filename = "Fichier.puml";
            // On vérifie si le nom donnée se termine par l'extention .puml
        } else if (!filename.endsWith(".puml")) {
            // Si elle n'est pas vide mais ne se termine pas par l'extention .puml on la rajoute
            filename += ".puml";
        }
        // Ajout du directory au filename afin de générer le fichier au bon endroit.
        if (outputDirectory != null && !outputDirectory.equals("")) {
            filename = outputDirectory + File.separator + filename;
        }

        System.out.println("Création du fichier \"" + filename + "\" à l'emplacement : \n" + outputDirectory);
        //-----------------------------------------------------

        // On instancie le générateur de diagramme
        UMLGenerator umlGenerator = new UMLGenerator(environment, filename);
        // On appelle la procédure de génération avec le selecteur en paramètre.
        umlGenerator.generateUML(selector);
        return true;
    }
    /*
    *
    *  On ajoute les options ici, je n'ai pas trouvé de solution pour les mettre en dehors de la classe PumlDoclet
    *
     */
    public class OutputDirectoryOption implements Doclet.Option {
        private String option;
        @Override
        public int getArgumentCount() {
            return 1;
        }
        @Override
        public String getDescription() {
            return "Répertoire de sortie";
        }
        @Override
        public Doclet.Option.Kind getKind() {
            return Doclet.Option.Kind.EXTENDED;
        }
        @Override
        public List<String> getNames() {
            return List.of("-d");
        }
        @Override
        public String getParameters() {
            return option;
        }
        @Override
        public boolean process(String option, List<String> arguments) {
            this.option = arguments.get(0);
            options.put(option, this.option);
            return true;
        }
    }
    public class OutputFileNameOption implements Doclet.Option {
        private String option;

        @Override
        public int getArgumentCount() {
            return 1;
        }

        @Override
        public String getDescription() {
            return "Nom du fichier en sortie";
        }
        @Override
        public Kind getKind() {
            return Kind.EXTENDED;
        }
        @Override
        public List<String> getNames() {
            return List.of("-out");
        }
        @Override
        public String getParameters() {
            return option;
        }
        @Override
        public boolean process(String option, List<String> arguments) {
            this.option = arguments.get(0);
            options.put(option, this.option);
            return true;
        }
    }
    public static class DiagramSelectorOption implements Doclet.Option {
        private String option;

        @Override
        public int getArgumentCount() {
            return 1;
        }

        @Override
        public String getDescription() {
            return "Type de Diagramme";
        }
        @Override
        public Kind getKind() {
            return Kind.EXTENDED;
        }
        @Override
        public List<String> getNames() {
            return List.of("-type");
        }
        @Override
        public String getParameters() {
            return option;
        }
        @Override
        public boolean process(String option, List<String> arguments) {
            this.option = arguments.get(0);
            options.put(option, this.option);
            return true;
        }
    }
}

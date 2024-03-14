package main.java.com.walhalla.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Generator {
    private static final String TEMPLATE_DIR = "D:\\src\\jks-password-cracker\\templates";
    private static final String OUTPUT_DIR = "D:\\walhalla\\W\\WIMIA\\app\\src\\main\\java\\com\\walhalla\\whatismyipaddress\\features";

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter action: ");
//        String action = scanner.nextLine();
//
//        if (action.equals("makefeature")) {
//            System.out.print("Enter feature name: ");
//            String featureName = scanner.nextLine();
//            createFeature(featureName);
//        } else {
//            System.out.println("Invalid action!");
//        }
//        System.out.println("@@@");

        createFeature("checkhost");
    }

    private static void createFeature(String featureName) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIR));

            // Create the output directory
            File outputDir = new File(OUTPUT_DIR, featureName);
            outputDir.mkdirs();

            // Generate the MVP files
            //generateFile(cfg, "Model.ftl", featureName + "Model.java", outputDir);
            //generateFile(cfg, "View.ftl", featureName + "View.java", outputDir);
            generateFile(cfg, "Presenter.ftl", capitalizeFirstLetter(featureName) + "Presenter.java", outputDir);
            //generateFile(cfg, "Activity.ftl", capitalizeFirstLetter(featureName) + "Activity.java", outputDir);


        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    private static void generateFile(Configuration cfg, String templateName, String outputFileName, File outputDir)
            throws IOException, TemplateException {
        Template template = cfg.getTemplate(templateName);

        Map<String, Object> data = new HashMap<>();
        data.put("featureName", outputFileName.substring(0, outputFileName.indexOf(".")));

        File outputFile = new File(outputDir, outputFileName);
        Writer writer = new FileWriter(outputFile);
        template.process(data, writer);
        writer.close();

        System.out.println("Generated file: " + outputFile.getAbsolutePath() + " " + outputFileName);
    }
}

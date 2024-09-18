package main.java.org.proguard;


import com.company.ApkClonerPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.prefs.Preferences;

public class ApkClonerApp extends Application implements ApkClonerPresenter.OutputCallback {
    boolean ZIPALIGN = false;
    boolean SIGN = false;
    boolean BUILD = false;
    private static final String KEY_APK_PATH = "key0";
    private ApkClonerPresenter ap;
    private TextArea outputArea;
    private Preferences prefs;
    private String mm;
    private TextField pathInput;


    public ApkClonerApp() {
        ap = new ApkClonerPresenter(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("APK Cloner");

        // Initialize preferences
        prefs = Preferences.userNodeForPackage(ApkClonerApp.class);
        boolean buildPref = prefs.getBoolean("BUILD", false);
        boolean signPref = prefs.getBoolean("SIGN", false);
        boolean zipalignPref = prefs.getBoolean("ZIPALIGN", false);
        mm = prefs.get(KEY_APK_PATH, "C:\\");

        ZIPALIGN = zipalignPref;
        SIGN = signPref;
        BUILD = buildPref;

        // Create the grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Path input
        Label pathLabel = new Label("APK Path:");
        GridPane.setConstraints(pathLabel, 0, 0);

        pathInput = new TextField();
        pathInput.setPromptText("Enter the path to the APK folder");
        GridPane.setConstraints(pathInput, 1, 0);
        setCurrentDir();
        // Directory chooser button
        Button chooseDirButton = new Button("Choose Directory");
        chooseDirButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select APK Directory");
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                mm = selectedDirectory.getAbsolutePath();
                prefs.put(KEY_APK_PATH, mm);
                setCurrentDir();
            }
        });
        GridPane.setConstraints(chooseDirButton, 2, 0);

        // Index input
        Label indexLabel = new Label("Index:");
        GridPane.setConstraints(indexLabel, 0, 1);

        TextField indexInput = new TextField("10");
        indexInput.setPromptText("Enter the index");
        GridPane.setConstraints(indexInput, 1, 1);

        // Checkboxes for options
        CheckBox buildCheckBox = new CheckBox("Build");
        buildCheckBox.setSelected(buildPref);
        GridPane.setConstraints(buildCheckBox, 0, 2);

        CheckBox signCheckBox = new CheckBox("Sign");
        signCheckBox.setSelected(signPref);
        GridPane.setConstraints(signCheckBox, 1, 2);

        CheckBox zipalignCheckBox = new CheckBox("Zipalign");
        zipalignCheckBox.setSelected(zipalignPref);
        GridPane.setConstraints(zipalignCheckBox, 0, 3);

        // Output area
        outputArea = new TextArea();
//        String log = ">> Sample passed \n";
//        Text t1 = new Text();
//        t1.setStyle("-fx-fill: #4F8A10;-fx-font-weight:bold;");
//        t1.setText(log);
//        Text t2 = new Text();
//        t2.setStyle("-fx-fill: RED;-fx-font-weight:normal;");
//        t2.setText(log);
//        outputArea.getChildren().addAll(t1, t2);
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        GridPane.setConstraints(this.outputArea, 0, 4, 3, 1);
        buildCheckBox.setOnAction(actionEvent -> {
            BUILD = buildCheckBox.isSelected();
            prefs.putBoolean("BUILD", BUILD);
        });

        signCheckBox.setOnAction(actionEvent -> {
            SIGN = signCheckBox.isSelected();
            prefs.putBoolean("SIGN", SIGN);
        });

        zipalignCheckBox.setOnAction(actionEvent -> {
            // Save preferences
            ZIPALIGN = zipalignCheckBox.isSelected();
            prefs.putBoolean("ZIPALIGN", ZIPALIGN);
        });

        // Clone button
        Button cloneButton = new Button("Clone APK");
        cloneButton.setOnAction(e -> {
            String path = pathInput.getText();
            int index = Integer.parseInt(indexInput.getText());

            new Thread(() -> {
                System.out.println(BUILD+""+SIGN+""+ZIPALIGN);
                ap.patchxmlRequest(path, index, BUILD, SIGN, ZIPALIGN);
            }).run();
        });
        GridPane.setConstraints(cloneButton, 1, 5);

        // Add everything to grid
        grid.getChildren().addAll(pathLabel, pathInput, chooseDirButton, indexLabel, indexInput, buildCheckBox, signCheckBox, zipalignCheckBox, this.outputArea, cloneButton);

        // Set up the scene and stage
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setCurrentDir() {
        if (mm != null) {
            pathInput.setText(mm);
        }
    }

    @Override
    public void appendOutput(String message, boolean isError) {
        Platform.runLater(() -> {
//            Text text = new Text(message + "\n");
//            if (isError) {
//                text.setFill(Color.RED);
//            } else {
//                text.setFill(Color.BLACK);
//            }
//            outputArea.getChildren().add(text);
            if (isError) {
                outputArea.appendText("[ERROR] " + message + "\n");
            } else {
                outputArea.appendText(message + "\n");
            }
        });
    }
}
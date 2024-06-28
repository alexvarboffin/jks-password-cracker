package org.walhalla;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * --module-path C:/Users/combo/Documents/lib/javafx-sdk-17.0.0.1/lib --add-modules javafx.controls,javafx.fxml
 */

public class MultiLanguageTextMerger extends Application {

    private TextArea textInput;
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Multi-Language Text Merger");

        textInput = new TextArea();
        textInput.setPrefHeight(150);
        textInput.setPrefWidth(Double.MAX_VALUE);

        String[] languages = {"es", "fr", "de", "pt", "ru", "ja", "zh-rCN", "zh-rTW", "ko", "ar",
                "az", "uz", "el", "en", "it", "bn", "cs", "vi", "sk", "ca", "uk"};
        String[] languageNames = {"Испанский (es)", "Французский (fr)", "Немецкий (de)", "Португальский (pt)", "Русский (ru)",
                "Японский (ja)", "Китайский (Упрощенный) zh-rCN", "Китайский (Традиционный) zh-rTW",
                "Корейский (ko)", "Арабский (ar)", "Азербайджанский (az)", "Узбекский (uz)",
                "(Greek) el", "(English) en", "(Italian) it", "(Bangla) bn",
                "(Czech) cs", "(VN Vietnamese) vi", "(Slovak) sk", "(Catalan) ca", "Украинский (uk)"};

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));


        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        for (int i = 0; i < languages.length; i++) {

            String language = languages[i];
            String languageName = languageNames[i];
            Button button = new Button(languageName);
            button.setOnAction(e -> mergeText(language));

            buttonBox.getChildren().add(button);

            if ((i % 5) > 0) {

            } else {
                vBox.getChildren().add(buttonBox);
                buttonBox = new HBox(10);
                buttonBox.setPadding(new Insets(10));
            }
        }

        Button uniqueButton = new Button("::: Уникализация текста :::");
        uniqueButton.setOnAction(e -> mergeText2());
        vBox.getChildren().add(uniqueButton);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(textInput, vBox);
        VBox.setVgrow(textInput, Priority.ALWAYS);

        Scene scene = new Scene(vbox, 800, 500); // Увеличенный размер окна
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mergeText(String language) {
        String selectedText = textInput.getText();
        String mergedText = "Переведи xml на " + language
                + ", экранируй символ ' а также заменяй ... на …\n" + selectedText;

        copyToClipboard(mergedText);
    }

    private void mergeText2() {
        String selectedText = textInput.getText();
        String mergedText = "Перепиши текст, поменяй структуру и сделай его более читаемым. Он должен быть оригинальным, избегать повторов и проходить проверку Антиплагиата:\n" + selectedText;

        copyToClipboard(mergedText);
    }

    private void copyToClipboard(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText("Текст успешно скопирован в буфер обмена!");
        alert.showAndWait();
    }
}
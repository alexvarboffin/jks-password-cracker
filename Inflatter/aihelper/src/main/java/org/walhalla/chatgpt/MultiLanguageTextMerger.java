package org.walhalla.chatgpt;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

import java.util.Map;
import java.util.TreeMap;

/**
 * --module-path C:/Users/combo/Documents/lib/javafx-sdk-17.0.0.1/lib --add-modules javafx.controls,javafx.fxml
 */

public class MultiLanguageTextMerger extends Application {

    private static final Map<String, String> LANGUAGE_MAP = new TreeMap<>();

    static {
        LANGUAGE_MAP.put("es", "Испанский (es)");
        LANGUAGE_MAP.put("fr", "Французский (fr)");
        LANGUAGE_MAP.put("de", "Немецкий (de)");
        LANGUAGE_MAP.put("pt", "Португальский (pt)");
        LANGUAGE_MAP.put("ru", "Русский (ru)");
        LANGUAGE_MAP.put("ja", "Японский (ja)");
        LANGUAGE_MAP.put("zh-rCN", "Китайский (Упрощенный) zh-rCN");
        LANGUAGE_MAP.put("zh-rTW", "Китайский (Традиционный) zh-rTW");
        LANGUAGE_MAP.put("ko", "Корейский (ko)");
        LANGUAGE_MAP.put("ar", "Арабский (ar)");
        LANGUAGE_MAP.put("az", "Азербайджанский (az)");
        LANGUAGE_MAP.put("uz", "Узбекский (uz)");
        LANGUAGE_MAP.put("el", "Греческий (el)");
        LANGUAGE_MAP.put("en", "Английский (en)");
        LANGUAGE_MAP.put("it", "Итальянский (it)");
        LANGUAGE_MAP.put("bn", "Бенгальский (bn)");
        LANGUAGE_MAP.put("cs", "Чешский (cs)");
        LANGUAGE_MAP.put("vi", "Вьетнамский (vi)");
        LANGUAGE_MAP.put("sk", "Словацкий (sk)");
        LANGUAGE_MAP.put("ca", "Каталанский (ca)");
        LANGUAGE_MAP.put("uk", "Украинский (uk)");
    }

    String aa = "Ты очень классный программист, создай для меня";
//    String aa="Если чтото не понятно, задай уточняющие вопросы сразу, до выполнения основного задания" +
//            ", если все понятно - выполняй задачу.";

    String s0 = "\"I am an experienced developer, no need to explain basics\" and a generic \"no yapping\"";


    private TextArea textInput;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Multi-Language Text Merger");
        primaryStage.setHeight(700);
        primaryStage.setMaxHeight(700);

        primaryStage.setWidth(1200);
        primaryStage.setMaxWidth(1200);

        textInput = new TextArea();
        textInput.setPrefHeight(50);
        textInput.setMinHeight(50);

        textInput.setPrefWidth(Double.MAX_VALUE);

//        String[] languages = {"es", "fr", "de", "pt", "ru", "ja", "zh-rCN", "zh-rTW", "ko", "ar",
//                "az", "uz", "el", "en", "it", "bn", "cs", "vi", "sk", "ca", "uk"};
//        String[] languageNames = {"Испанский (es)", "Французский (fr)", "Немецкий (de)", "Португальский (pt)", "Русский (ru)",
//                "Японский (ja)", "Китайский (Упрощенный) zh-rCN", "Китайский (Традиционный) zh-rTW",
//                "Корейский (ko)", "Арабский (ar)", "Азербайджанский (az)", "Узбекский (uz)",
//                "(Greek) el", "(English) en", "(Italian) it", "(Bangla) bn",
//                "(Czech) cs", "(VN Vietnamese) vi", "(Slovak) sk", "(Catalan) ca", "Украинский (uk)"};

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
//        HBox buttonBox = null;
//        buttonBox = new HBox(4);
        //buttonBox.setPadding(new Insets(10));
//        int total = languages.length;
//        System.out.println(total);
//        for (int i = 0; i < total; i++) {
//            String language = languages[i];
//            String languageName = languageNames[i];
//            Button button = new Button(i + "." + languageName);
//            button.setOnAction(e -> mergeText(language));
//
//            // Добавляем кнопку в текущий HBox
//            buttonBox.getChildren().add(button);
//
//            // Если добавили 10 кнопок, или это последняя кнопка
//            if ((i + 1) % 7 == 0 || i == total - 1) {
//                vBox.getChildren().add(buttonBox);  // Добавляем HBox в VBox
//                buttonBox = new HBox(4);            // Создаем новый HBox для следующих кнопок
//            }
//
//        }

        createButtons(vBox);
        Button uniqueButton = new Button("::: Перепиши на viewbinding :::");
        uniqueButton.setOnAction(e -> {

            String bb = "Перепиши на Android viewbinding, Замени butterknife на viewbinding, разрешено менять имена переменных на подходяшие по смыслу если на них нет внешних ссылок. " +
                    "Также разрешено выносить строки в строковые ресурсы xml. \n";

            copyToClipboard(mergedText(bb));
        });


        vBox.getChildren().addAll(

                newBtn(
                        "::: Уникализация текста  Антиплагиат :::",
                        "Перепиши текст, поменяй структуру и сделай его более читаемым. Он должен быть оригинальным, избегать повторов и проходить проверку Антиплагиата:\n"
                )
                , uniqueButton,

                newBtn(
                        "Добавь тематические emoji текст оптимизируй под ASO и SEO текст должен быть цепляющим.",
                        "Добавь тематические emoji текст оптимизируй под ASO и SEO текст должен быть цепляющим."),
                newBtn(
                        "Json Обход",
                        "последовательно проверь наличие конкретных ключей в известной структуре JSON и сохрани их значения в HashMap, если они присутствуют.\n" +
                                "сделай для всех ключей, JSONArray проверяй, используя библиотеку org.json.\n" +
                                "  Добавляй так  " +
                                "    private void item0(String key, String value, List<ViewModel> nameValue) {\n" +
                                "        if (!TextUtils.isEmpty(key) || !TextUtils.isEmpty(value)) {\n" +
                                "            nameValue.add(new TwoColItem(key, value));\n" +
                                "        }\n" +
                                "    }\n"),


//                newBtn(
//                        "@"
//                        , "@")
//                ,

                newBtn(
                        "Activity Generator"
                        , "Передаю в startActivity обьект ")


        );

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(textInput, vBox);
        VBox.setVgrow(textInput, Priority.ALWAYS);

        Scene scene = new Scene(vbox, 800, 500); // Увеличенный размер окна
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createButtons(VBox vBox) {
        HBox buttonBox = null;
        buttonBox = new HBox(4);
        Map<String, String> languageMap = getAllLanguages();
        int count = 0;

        for (Map.Entry<String, String> entry : languageMap.entrySet()) {
            String languageCode = entry.getKey();
            String languageName = entry.getValue();
            Button button = new Button(count + "." + languageName);
            button.setOnAction(e -> mergeText(languageCode));

            // Добавляем кнопку в текущий HBox
            buttonBox.getChildren().add(button);
            count++;

            // Если достигли конца строки (7 кнопок) или это последняя кнопка
            if (count % 7 == 0) {
                vBox.getChildren().add(buttonBox);  // Добавляем HBox в VBox
                buttonBox = new HBox(4);            // Создаем новый HBox для следующих кнопок
            }
        }

        // Добавляем последний HBox в VBox, если он не пустой
        if (!buttonBox.getChildren().isEmpty()) {
            vBox.getChildren().add(buttonBox);
        }
    }

    public static String getLanguageName(String code) {
        return LANGUAGE_MAP.get(code);
    }

    // Получение всех языков
    public static Map<String, String> getAllLanguages() {
        return new TreeMap<>(LANGUAGE_MAP); // Возвращает отсортированную копию карты
    }

    private Node newBtn(String title, String s1) {
        Button uniqueButton1 = new Button(title);
        uniqueButton1.setOnAction(e -> {
            copyToClipboard(mergedText(s1));
        });
        return uniqueButton1;
    }

    private String mergedText(String bb) {
        String selectedText = textInput.getText();
        return bb + selectedText + "\n" + s0;
    }

    //@@@@  String m = "Создай Html Разметку"


    private void mergeText(String language) {
        String selectedText = textInput.getText();
        String mergedText = "Переведи xml на " + language
                + ", экранируй символ ' (пример <string name=\"abc_moreApps\">Ko'proq ilovalar</string> <string name=\"abc_moreApps\">Ko\\'proq ilovalar</string>) а также заменяй ... на …\n" + selectedText;

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
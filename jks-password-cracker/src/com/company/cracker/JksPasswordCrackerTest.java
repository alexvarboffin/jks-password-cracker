package com.company.cracker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


import javafx.scene.control.Button;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.stage.FileChooser;

import java.io.File;

public class JksPasswordCrackerTest extends Application {

    private Label statusLabel = new Label("Cracking in progress...");
    private Label jksFileLabel = new Label("JKS File: Not selected");
    private Label pswFileLabel = new Label("Password File: Not selected");
    private File jksFile;
    private File pswFile;
    private final Preferences prefs = Preferences.userNodeForPackage(JksPasswordCrackerTest.class);
    private AtomicInteger errorCount;
    private final int NUM_THREADS = 4;
    private boolean found;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button jksButton = new Button("Select JKS File");
        Button pswButton = new Button("Select Password File");
        Button startButton = new Button("Start Cracking");

        jksButton.setOnAction(e -> selectFile(primaryStage, true));
        pswButton.setOnAction(e -> selectFile(primaryStage, false));
        startButton.setOnAction(e -> startCracking());

        statusLabel.setFont(new javafx.scene.text.Font("Arial", 18));
        statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        statusLabel.setAlignment(Pos.CENTER);

        jksFileLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        pswFileLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        VBox vbox = new VBox(10, jksFileLabel, pswFileLabel, jksButton, pswButton, startButton, statusLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 500, 300);
        primaryStage.setTitle("JKS Password Cracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadPreferences();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    private void selectFile(Stage stage, boolean isJksFile) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (isJksFile) {
                jksFile = file;
                prefs.put("jksFilePath", file.getAbsolutePath());
                jksFileLabel.setText("JKS File: " + file.getAbsolutePath());
            } else {
                pswFile = file;
                prefs.put("pswFilePath", file.getAbsolutePath());
                pswFileLabel.setText("Password File: " + file.getAbsolutePath());
            }
        }
    }

    private void startCracking() {
        if (jksFile == null || pswFile == null) {
            updateLabel("Please select both files.");
            return;
        }
        errorCount = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        try {
            long startTime = System.currentTimeMillis();

            String jksFilePath = jksFile.getAbsolutePath();
            String pswFilePath = pswFile.getAbsolutePath();

            KeyStore ks = KeyStore.getInstance("JKS");
            byte[] keystoreBytes = Files.readAllBytes(Paths.get(jksFilePath));
            ByteArrayInputStream mm = new ByteArrayInputStream(keystoreBytes);

            List<String> allPasswords = Files.readAllLines(Paths.get(pswFilePath));
            List<List<String>> partitions = splitList(allPasswords, NUM_THREADS);

            for (List<String> partition : partitions) {
                executor.submit(() -> {
                    for (String password : partition) {
                        if (found) return;
                        try {
                            ks.load(mm, password.toCharArray());
                            updateLabel("Password found: " + password);
                            found = true;
                            executor.shutdownNow();
                            break;
                        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
                            errorCount.incrementAndGet();
                        }
                    }
                });

            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            System.out.println("Total time: " + duration + " ms, Errors: " + errorCount.get() + "/" + allPasswords.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static List<List<String>> splitList(List<String> list, int parts) {
        List<List<String>> partitions = new ArrayList<>();
        int partitionSize = list.size() / parts;
        for (int i = 0; i < list.size(); i += partitionSize) {
            partitions.add(list.subList(i, Math.min(i + partitionSize, list.size())));
        }
        return partitions;
    }

    private void updateLabel(String text) {
        javafx.application.Platform.runLater(() -> statusLabel.setText(text));
    }

    private void loadPreferences() {
        jksFileLabel.setText("JKS File: " + prefs.get("jksFilePath", "Not selected"));
        pswFileLabel.setText("Password File: " + prefs.get("pswFilePath", "Not selected"));
        File jksFilePath = new File(prefs.get("jksFilePath", ""));
        File pswFilePath = new File(prefs.get("pswFilePath", ""));
        if (jksFilePath.exists()) {
            jksFile = jksFilePath;
        }
        if (pswFilePath.exists()) {
            pswFile = pswFilePath;
        }
    }
}
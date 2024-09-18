package com.company.cracker;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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

    private final Label statusLabel = new Label("Cracking in progress...");
    private final Label jksFileLabel = new Label("JKS File: Not selected");
    private final Label pswFileLabel = new Label("Password File: Not selected");
    private File jksFile;
    private File pswFile;
    private final Preferences prefs = Preferences.userNodeForPackage(JksPasswordCrackerTest.class);
    private AtomicInteger errorCount;
    private final int NUM_THREADS = 1;
    private boolean found;
    private ComboBox<Object> keystoreTypeComboBox;


    public void setSavedKeyStoreType(String savedKeyStoreType) {
        this.savedKeyStoreType = savedKeyStoreType;
        System.out.println("==>" + savedKeyStoreType + "<==");
    }

    public String getSavedKeyStoreType() {
        return savedKeyStoreType;
    }

    private String savedKeyStoreType;


    public static void main(String[] args) {
        JksPasswordCracker c = new JksPasswordCracker();
        c.test1();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) { String version = System.getProperty("java.version");
        Button jksButton = new Button("Select JKS File");
        Button pswButton = new Button("Select Password File");
        Button startButton = new Button("Start Cracking");

        jksButton.setOnAction(e -> selectFile(primaryStage, true));
        pswButton.setOnAction(e -> selectFile(primaryStage, false));
        startButton.setOnAction(e -> startCracking());

        statusLabel.setFont(new javafx.scene.text.Font("Arial", 18));
        statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setWrapText(true);

        jksFileLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        pswFileLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");


        keystoreTypeComboBox = new ComboBox<>();
        keystoreTypeComboBox.getItems().addAll(Const.keyStoreTypes.keySet());


        setSavedKeyStoreType(prefs.get("keystoreType", "JKS"));

        keystoreTypeComboBox.setValue(getSavedKeyStoreType());

        keystoreTypeComboBox.setOnAction(event -> {
            setSavedKeyStoreType(String.valueOf(keystoreTypeComboBox.getValue()));
            prefs.put("keystoreType", getSavedKeyStoreType());
        });


        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(jksFileLabel, pswFileLabel, jksButton, pswButton, keystoreTypeComboBox, startButton, statusLabel);


        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("JKS Password Cracker ["+version+"]");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadPreferences();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    private void selectFile(Stage stage, boolean isJksFile) {

        FileChooser fileChooser = new FileChooser();
        if (isJksFile) {
            if (jksFile != null) {
                setDefault(jksFile, fileChooser);
            }
        } else {
            if (pswFile != null) {
                setDefault(pswFile, fileChooser);
            }
        }
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

    private void setDefault(File pswFile, FileChooser fileChooser) {
        String m = pswFile.getParent();
        File initialDirectory = new File(m);
        if (initialDirectory.isDirectory() && initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }
    }

    String _res;
    Exception error = null;

    private void startCracking() {
        if (jksFile == null || pswFile == null) {
            updateLabel("Please select both files.");
            return;
        }
        error = null;
        found = false;
        _res = null;

        errorCount = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        try {
            long startTime = System.currentTimeMillis();

            String jksFilePath = jksFile.getAbsolutePath();
            String pswFilePath = pswFile.getAbsolutePath();


            final KeyStore[] kss = new KeyStore[NUM_THREADS];
            final byte[] keystoreBytes = Files.readAllBytes(Paths.get(jksFilePath));
            //final ByteArrayInputStream mm = new ByteArrayInputStream(keystoreBytes);

            for (int i = 0; i < NUM_THREADS; i++) {
                kss[i] = KeyStore.getInstance(getSavedKeyStoreType());
            }


            //final ByteArrayInputStream mm = new ByteArrayInputStream(keystoreBytes);

            List<String> allPasswords = Files.readAllLines(Paths.get(pswFilePath));
            List<List<String>> partitions = splitList(allPasswords, NUM_THREADS);


            for (int i = 0; i < partitions.size(); i++) {
                int finalI = i;
                executor.submit(() -> {
                    List<String> partition = partitions.get(finalI);
                    for (String password : partition) {
                        if (found) return;
                        try {
                            byte[] keystoreBytes1 = Files.readAllBytes(Paths.get(jksFilePath));
                            ByteArrayInputStream mmLocal = new ByteArrayInputStream(keystoreBytes1);
                            KeyStore tmp = null;
                            try {
                                tmp = KeyStore.getInstance("PKCS12");
                            } catch (KeyStoreException e) {
                                e.printStackTrace();
                            }
                            //KeyStore tmp = kss[finalI];
                            tmp.load(mmLocal, password.toCharArray());//<--- this method clear ByteArrayInputStream
                            updateLabel("PASSWORD FOUND: " + password);
                            _res = password;
                            System.out.println("-------------------------------------");
                            found = true;
                            executor.shutdownNow();
                            break;

                        }

                        /**
                         * Wtf this s*t not catch??
                         * catch (CertificateException e0) {
                         String message = e0.getMessage();
                         System.out.println("1"); break;
                         } catch (NoSuchAlgorithmException e0) {
                         String message = e0.getMessage();
                         System.out.println("2"); break;
                         } catch (IOException e0) {
                         String message = e0.getMessage();
                         System.out.println("3"); break;
                         }**/

                        //ok
                        catch (CertificateException | NoSuchAlgorithmException | IOException e0) {
                            String message = e0.getMessage();
                            if (message != null) {
                                if (message.contains("keystore password was incorrect")) {
                                    //System.out.println("Incorrect keystore password.");
//                                    if (password.equals("release")) {
//                                        System.out.println(password);
//                                    }
                                    errorCount.incrementAndGet();
                                } else if (message.contains("NoSuchAlgorithmException") || message.contains("Invalid keystore format")) {
                                    System.out.println("Algorithm not found.");
                                    System.out.println(e0.getClass() + ": " + message + ": " + e0.getLocalizedMessage());
                                    error = e0;
                                    executor.shutdownNow();
                                    break;
                                } else if (message.contains("UnrecoverableKeyException")) {
                                    System.out.println("Key recovery failed: Possible bad key used.");
                                    e0.printStackTrace();
                                } else if (message.contains("Integrity check failed")) {
                                    e0.printStackTrace();
                                    System.out.println("Integrity check failed: Possible corrupted or incorrect keystore.");
                                    e0.printStackTrace();
                                } else {
                                    System.out.println("An unexpected error occurred.");
                                    e0.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }


            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);


            StringBuilder sb = new StringBuilder();
            System.out.println("---" + error);

            if (_res != null) {
                if (error != null) {
                    sb.append(error);
                }
                sb.append("PASSWORD FOUND: " + _res + "\n [-] Total time: " + duration + " ms, Errors: " + errorCount.get() + "/" + allPasswords.size());
            } else {
                if (error != null) {
                    sb.append(error);
                }
                sb.append("[-] Total time: " + duration + " ms, Errors: " + errorCount.get() + "/" + allPasswords.size());
            }

            updateLabel(sb.toString());
        } catch (IOException e0) {
            String message = e0.getMessage();
            if (message != null) {
                if (message.contains("keystore password was incorrect")) {
                    //System.out.println("Incorrect keystore password.");
//                                    if (password.equals("release")) {
//                                        System.out.println(password);
//                                    }
                    errorCount.incrementAndGet();
                } else if (message.contains("NoSuchAlgorithmException") || message.contains("Invalid keystore format")) {
                    System.out.println("@@@Algorithm not found.");
                    System.out.println(e0.getClass() + ": " + message + ": " + e0.getLocalizedMessage());
                    error = e0;
                    executor.shutdownNow();
                } else if (message.contains("UnrecoverableKeyException")) {
                    System.out.println("@@@Key recovery failed: Possible bad key used.");
                } else if (message.contains("Integrity check failed")) {
                    System.out.println("@@@Integrity check failed: Possible corrupted or incorrect keystore.");
                } else {
                    System.out.println("@@@An unexpected error occurred.");
                    e0.printStackTrace();
                }
            }
        } catch (KeyStoreException e) {
            updateLabel(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
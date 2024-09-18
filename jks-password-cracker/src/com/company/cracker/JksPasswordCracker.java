package com.company.cracker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

public class JksPasswordCracker {
    static String jksFile = "C:\\abc\\Rudos\\rudos\\keystore\\keystore.jks";
    //static String jksFile = "C:\\Users\\combo\\Desktop\\AndroidWork\\VolgaPosl\\keystore.jks";



    //            if(t instanceof NoSuchAlgorithmException){
//                System.out.println("@@@"+t.getMessage());
//            }else  if(t instanceof CertificateException){
//                System.out.println("@nnnn@"+t.getMessage());
//            }else  if(t instanceof javax.crypto.BadPaddingException){
//                System.out.println("@nnnmmmmmmmmmmmmn@"+t.getMessage());
//            }else  if(t instanceof java.security.UnrecoverableKeyException){
//                System.out.println("@nnnmmmmmmmmmmmmn@"+t.getMessage());
//            }else{
//                System.out.println("@w@"+t.getMessage());
//            }
//    private static void test0(KeyStore ks, byte[] keystoreBytes) throws IOException, NoSuchAlgorithmException {
//        try {
//            ks.load(new ByteArrayInputStream(keystoreBytes), "release".toCharArray());
//        } catch (CertificateException e) {
//            System.out.println("mmmmmmmmmmmmmmmmmmm");
//        }
//    }

//    private static void test0(byte[] keystoreBytes0) throws IOException, NoSuchAlgorithmException {
//        try {
//            for (Map.Entry<String, String> entry : Const.keyStoreTypes.entrySet()) {
//
//                try {
//                    KeyStore ks = KeyStore.getInstance(entry.getKey());
//                    byte[]
//                            keystoreBytes = Files.readAllBytes(Paths.get(jksFile));
//                    ks.load(new ByteArrayInputStream(keystoreBytes), "release".toCharArray());
//                    System.out.println("[valid: ]" + entry.getKey());
//                } catch (KeyStoreException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (CertificateException e) {
//            System.out.println("mmmmmmmmmmmmmmmmmmm");
//        }
//    }

    public void test1() {
        String version = System.getProperty("java.version");
        System.out.println(version);

        long startTime = System.currentTimeMillis();

        //String jksFile = "C:\\Users\\combo\\Downloads\\SalesRecord\\SalesRecord.jks";
        //String jksFile = "C:\\abc\\Rudos\\app\\keystore\\key.jks";


        String psw = "D:\\src\\jks-password-cracker\\src\\main\\resources\\passwords.txt";

//not work
//        KeyStore ks = KeyStore.getInstance("JKS");
//        byte[] keystoreBytes = Files.readAllBytes(Paths.get(jksFile));


//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(psw), StandardCharsets.UTF_8));
//        String password;
//        while ((password = reader.readLine()) != null) { // читаем пароли из файла
//            try {
//                KeyStore ks = KeyStore.getInstance("JKS");
//                byte[] keystoreBytes = Files.readAllBytes(Paths.get(jksFile));
//                ks.load(new ByteArrayInputStream(keystoreBytes), password.toCharArray());
//                System.out.println("SUCCESS: " + password);
//                break;
//            } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
//                System.out.println("Неверный пароль: " + password);
//                if (password.equals("release")) {
//                    System.out.println(password);
//                    break;
//                }
//            }
//        }
//
//        long endTime = System.currentTimeMillis();
//        long duration = (endTime - startTime); // время выполнения в наносекундах
//        System.out.println("{*} Время выполнения: " + duration + " mс");

        //KeyStore ks = KeyStore.getInstance("JKS");
        //KeyStore ks = KeyStore.getInstance("PKCS12");

//        for (Map.Entry<String, String> entry : Const.keyStoreTypes.entrySet()) {
//            try {
//                KeyStore ks = KeyStore.getInstance(entry.getKey());
//                System.out.println("[valid: ]" + entry.getKey());
//            } catch (KeyStoreException e) {
//                e.printStackTrace();
//            }
//        }


        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            byte[] keystoreBytes = Files.readAllBytes(Paths.get(jksFile));
            test0(ks, keystoreBytes);
            //test0(keystoreBytes);

            System.out.println("000");
        } catch (Exception t) {
            String message = t.getMessage();
            System.out.println(t.getClass() + ": " + message + ": " + t.getLocalizedMessage());

            if (message != null) {
                if (message.contains("keystore password was incorrect")) {
                    System.out.println("Incorrect keystore password.");
                } else if (message.contains("NoSuchAlgorithmException")) {
                    System.out.println("Algorithm not found.");
                } else if (message.contains("UnrecoverableKeyException")) {
                    System.out.println("Key recovery failed: Possible bad key used.");
                } else if (message.contains("Integrity check failed")) {
                    System.out.println("Integrity check failed: Possible corrupted or incorrect keystore.");
                } else {
                    System.out.println("An unexpected error occurred.");
                    t.printStackTrace();
                }
            }

        }
    }

    private void test0(KeyStore ks, byte[] keystoreBytes) throws IOException, NoSuchAlgorithmException {
        try {
            ks.load(new ByteArrayInputStream(keystoreBytes), "release".toCharArray());
            System.out.println("[valid: ]" + "release");
        } catch (CertificateException e) {
            System.out.println("mmmmmmmmmmmmmmmmmmm");
        }
    }
}

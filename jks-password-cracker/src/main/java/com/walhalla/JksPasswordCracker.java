package com.walhalla;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class JksPasswordCracker {

    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();

        String jksFile = "C:\\Users\\combo\\Downloads\\SalesRecord\\SalesRecord.jks";
        String psw = "D:\\src\\jks-password-cracker\\src\\main\\resources\\passwords.txt";


        KeyStore ks = KeyStore.getInstance("JKS");
//
//        for (String password : passwords) {
//            try {
//                FileInputStream fis = new FileInputStream(jksFile);
//                ks.load(fis, password.toCharArray());
//                fis.close();
//                System.out.println("Пароль найден: " + password);
//                break;
//            } catch (Exception e) {
//                // Если пароль неверный, продолжаем перебирать
//                //keystore password was incorrect
//                //System.out.println(e.getLocalizedMessage());
//            }
//        }


        byte[] keystoreBytes = Files.readAllBytes(Paths.get(jksFile));


        BufferedReader reader = new BufferedReader(new FileReader(psw));
        String password;
        while ((password = reader.readLine()) != null) { // читаем пароли из файла
            try {
                ks.load(new ByteArrayInputStream(keystoreBytes), password.toCharArray());
                System.out.println("Пароль найден: " + password);
                break;
            } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
                //System.out.println("Неверный пароль: " + password);
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); // время выполнения в наносекундах
        System.out.println("{*} Время выполнения: " + duration + " mс");

    }
}

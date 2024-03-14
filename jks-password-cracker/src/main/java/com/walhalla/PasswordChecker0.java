package com.walhalla;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PasswordChecker0 implements Runnable {

    private static volatile boolean passwordFound = false;

    private final String password;
    //private File jksFile;
    private final KeyStore ks;

    //    public PasswordChecker(String password, File jksFile, KeyStore ks) {
//        this.password = password;
//        this.jksFile = jksFile;
//        this.ks = ks;
//    }
    byte[] keystoreBytes;

    public PasswordChecker0(String password, byte[] keystoreBytes) throws KeyStoreException {
        this.password = password;
        this.keystoreBytes = keystoreBytes;
        this.ks = KeyStore.getInstance("JKS");;
    }

    @Override
    public void run() {
        try {
            //FileInputStream is = new FileInputStream(jksFile);
            //ks.load(is, password.toCharArray());
            //System.out.println(ks.hashCode());
            ks.load(new ByteArrayInputStream(keystoreBytes), password.toCharArray());
            System.out.println("Пароль найден: " + password);
            passwordFound = true;

        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            //System.out.println("Неверный пароль: " + password);
        }
    }

    public static void main(String[] args) throws IOException, KeyStoreException {

        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        String jksFile = "C:\\Users\\combo\\Downloads\\SalesRecord\\SalesRecord.jks";
        String psw = "D:\\src\\jks-password-cracker\\src\\main\\resources\\passwords.txt";

        byte[] keystoreBytes = Files.readAllBytes(Paths.get(jksFile));


        BufferedReader reader = new BufferedReader(new FileReader(psw));
        String password;
        while ((password = reader.readLine()) != null&& !passwordFound) {
            executor.execute(new PasswordChecker0(password, keystoreBytes));
        }
        executor.shutdown();
        try {
            boolean mm = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); // время выполнения в наносекундах
        System.out.println("{*} Время выполнения: " + duration + " mс");
    }
}

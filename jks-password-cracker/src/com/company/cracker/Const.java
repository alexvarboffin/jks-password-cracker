package com.company.cracker;

import java.util.HashMap;
import java.util.Map;

public class Const {

    static Map<String, String> keyStoreTypes = new HashMap<>();

    static {
        keyStoreTypes.put("JKS", "Java KeyStore (default format for Java keystores).");
        keyStoreTypes.put("PKCS12", "Portable Cryptographic format, widely used for storing certificates and private keys.");

        //keyStoreTypes.put("RSA", "");
//        keyStoreTypes.put("SHA", "");
//        keyStoreTypes.put("SHA-1", "");
//        keyStoreTypes.put("SHA-256", "");

        //keyStoreTypes.put("Windows-MY", "Windows-specific keystore for personal keys and certificates.");



        //Принимает любой пароль?? не падает при KeyStore.getInstance("Windows-ROOT");
        //keyStoreTypes.put("Windows-ROOT", "Windows-specific keystore for trusted root certificates.");


//        keyStoreTypes.put("JCEKS", "Java Cryptography Extension KeyStore, supports additional key types (e.g., symmetric keys).");
//        keyStoreTypes.put("BKS", "Bouncy Castle KeyStore, provides enhanced security and flexibility.");
//        keyStoreTypes.put("UBER", "Bouncy Castle's highly secure keystore format.");

    }

}

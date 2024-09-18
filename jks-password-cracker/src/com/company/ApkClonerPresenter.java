package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


//App Cloner ApkCloner

public class ApkClonerPresenter {


    private static final String _APKSIGNER = "C:\\android\\sdk\\build-tools\\34.0.0\\apksigner.bat";
    private final OutputCallback outputCallback;


    public ApkClonerPresenter(OutputCallback outputCallback) {
        this.outputCallback = outputCallback;
    }

    public void patchxmlRequest(String s, int total, boolean BUILD, boolean SIGN, boolean ZIPALIGN) {
        System.out.println("***************************************");
        System.out.println("***************************************");
        System.out.println("********* START *************");
        System.out.println("***************************************");
        System.out.println("***************************************");


        File dist = new File(s, "dist");
        if (dist.exists()) {
            for (File file : Objects.requireNonNull(dist.listFiles())) {
                boolean e = file.delete();
            }
        }

        for (int index = 0; index < total; index++) {
            makeOne(index, s, BUILD, SIGN, ZIPALIGN);
        }

        System.out.println("***************************************");
        System.out.println("***************************************");
        System.out.println("********* END *************");
        System.out.println("***************************************");
        System.out.println("***************************************");
    }

    private void makeOne(int index, String s, boolean BUILD, boolean SIGN, boolean ZIPALIGN) {

        File parent = new File(s);
        if (parent.exists()) {
            File file = new File(parent, "AndroidManifest.xml");
            if (file.exists()) {
                File xml = new File(parent, "res\\xml");
                File values = new File(parent, "res\\values");

                if (!xml.exists()) {
                    boolean b = xml.mkdirs();
                }

                if (!xml.exists()) {
                    boolean b = xml.mkdirs();
                }
                File network_security_config = new File(xml, "network_security_config.xml");
                File strings = new File(values, "strings.xml");
                try {
                    makeList(strings, index);
                    patchxml(file, index);

                    File newFileName = new File(parent, String.format("dist/%04d.apk", index));
                    if (BUILD) {
                        String shell = "apktool b " + s + " -o " + newFileName.getAbsolutePath();

                        System.out.println(shell);
                        buildApp(shell);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    int exitCode = 0;
//                    if (SIGN) {
//                        exitCode = apkSigner0(newFileName);
//                        System.out.println("Apk signing successful.");
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
                    try {
                        if (exitCode == 0) {
                            String absolutePath = newFileName.getAbsolutePath();
                            String fileName = newFileName.getName();
                            int dotIndex = fileName.lastIndexOf('.');

                            // If dot is present and not the first character, get the substring before the dot
                            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                                fileName = fileName.substring(0, dotIndex);
                            }


                            //[ERROR] Input and output can't be same file
                            String zipalignedFileName = absolutePath.replace(fileName, fileName + "-zipaligned");
                            if (ZIPALIGN) {
                                exitCode = zpl(newFileName, zipalignedFileName);
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            if (exitCode == 0) {
                                String f0 = "\"" + _APKSIGNER + "\" sign " + "--ks C:\\tools\\gamename.keystore --ks-key-alias gamename " +
                                        "--ks-pass pass:Q89163241477 --key-pass pass:Q89163241477 --v1-signing-enabled true --v2-signing-enabled true " +
                                        zipalignedFileName;

                                System.out.println("@SIGN-1@ " + f0);

                                exitCode = executeCommand(f0);
                                if (exitCode == 0) {
                                    System.out.println("Apk signing-2 successful.");
                                    System.out.println("===========================================");
                                } else {
                                    System.out.println("Apk sign-2 failed. Exit code: " + exitCode + "@@");
                                }
                            } else {
                                System.out.println("Apk zipalign failed. Exit code: " + exitCode);
                            }

                        } else {
                            System.out.println("Apk signing failed. Exit code: " + exitCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SAXException e) {
                    throw new RuntimeException(e);
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    private int zpl(File newFileName, String signedFileName) {

        String shell2 = "\"C:\\android\\sdk\\build-tools\\34.0.0\\zipalign.exe\" -f -v 4 " +
                newFileName + " " + signedFileName;
        System.out.println(shell2);
        return executeCommand(shell2);
    }

    private int apkSigner0(File newFileName) {
        int exitCode = 0;
        String absolutePath = newFileName.getAbsolutePath();
        String command = _APKSIGNER + " sign " + "--ks C:\\tools\\gamename.keystore --ks-key-alias gamename " +
                "--ks-pass pass:Q89163241477 --key-pass pass:Q89163241477 --v1-signing-enabled true --v2-signing-enabled true "
                + absolutePath;
        System.out.println("@SIGN-0@ " + command);
        exitCode = executeCommand(command);
        return exitCode;
    }


    private static void makeList(File xmlFile, int index) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        // Get <application> element
        NodeList resources = doc.getElementsByTagName("resources");
        Element e = (Element) resources.item(0);
        NodeList strings = e.getElementsByTagName("string");

        int total = strings.getLength();
        for (int i = 0; i < total; i++) {
            Element item = (Element) strings.item(i);
            String name = item.getAttribute("name");
            if (name.equals("app_name")) {
                String newName = String.format("%04d", index);
                item.setTextContent(newName);
                String aa = item.getTextContent();
                System.out.println(name + "-->" + aa);
                break;
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

    private static void patchxml(File xmlFile, int k) {
        try {

            Map<String, String> map = new HashMap<>();

            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Get <application> element
            NodeList applicationList = doc.getElementsByTagName("application");
            Element applicationElement = (Element) applicationList.item(0);


            NodeList mList = doc.getElementsByTagName("manifest");
            Element manifest = (Element) mList.item(0);
            Attr aPackage = manifest.getAttributeNode("package");

            String packageName0 = "";
            String newPackageName = "";
            if (aPackage != null) {
                System.out.println("Package: " + aPackage.getValue());
                packageName0 = aPackage.getValue();
                //newPackageName = packageName0 + String.format("%04d", k);
                newPackageName = "com.example.client" + String.format("%04d", k);

                aPackage.setValue(newPackageName);
            }

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String attributeName = entry.getKey();
                String attributeValue = entry.getValue();

                // Check if the attribute already exists
                Attr attr = applicationElement.getAttributeNode(attributeName);
                if (attr != null) {
                    // If the attribute exists, set its value
                    attr.setValue(attributeValue);
                } else {
                    // If the attribute does not exist, create a new one and add it to the element
                    applicationElement.setAttribute(attributeName, attributeValue);
                }
            }

            NodeList providers = applicationElement.getElementsByTagName("provider");

            int totalP = providers.getLength();


            for (int i = 0; i < totalP; i++) {
                Element elem = (Element) providers.item(i);

                String attributeName = "android:authorities";

                String authorities = elem.getAttribute(attributeName);
                Attr authoritiesNode = elem.getAttributeNode(attributeName);

                if (authorities != null && !authorities.isEmpty()) {
                    String newValue = authorities.replace(packageName0, newPackageName);
                    authoritiesNode.setValue(newValue);
                    System.out.println(authorities + " --> " + newValue + "-->"/*+authoritiesNode.getValue()*/);
                }
            }

            print(applicationElement);


            // Write the changes back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("XML file updated successfully.");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }


    public static void buildApp(String builder1) {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"C:\\Windows\" && " + builder1);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            InputStreamReader streamReader = new InputStreamReader(p.getInputStream());
            BufferedReader r = new BufferedReader(streamReader);

            StringBuilder sb = new StringBuilder();
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                //System.out.println(line);
                sb.append(line).append("\n");
            }
            String fullText = sb.toString();
            if (fullText.contains("Unsupported codec id")) {
                System.out.println("{}" + builder1 + "{}");
                System.out.println(fullText);
                System.out.println("====================================");
            } else {
                System.out.println(fullText);
            }


        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public int executeCommand(String command) {
        int exitCode = 0;
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                outputCallback.appendOutput(line, false);
            }
            while ((line = errorReader.readLine()) != null) {
                outputCallback.appendOutput(line, true);
            }

            exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            outputCallback.appendOutput(e.getMessage(), true);
        }
        return exitCode;
    }

    @FunctionalInterface
    public
    interface OutputCallback {
        void appendOutput(String message, boolean isError);
    }

    private static void print(Element applicationElement) {

        NodeList activities = applicationElement.getElementsByTagName("activity");
        int totalA = activities.getLength();

        for (int i = 0; i < totalA; i++) {
            Element activity = (Element) activities.item(i);
            String attributeName = "android:exported";
            String name = activity.getAttribute("android:name");
            Attr exported = activity.getAttributeNode(attributeName);
            String attributeValue = "true";
            if (exported != null) {
                // If the attribute exists, set its value
                exported.setValue(attributeValue);
            } else {
                // If the attribute does not exist, create a new one and add it to the element
                System.out.println("[add]" + name);
                activity.setAttribute(attributeName, attributeValue);
            }
            //Attr aPackage = manifest.getAttributeNode("package");
            System.out.println("=====================================");
            System.out.println("@@" + name + ", " + exported);
            System.out.println("=====================================");

//                String attributeName2 = "intent-filter";
//                NodeList intentFilters = activity.getElementsByTagName(attributeName2);
//                int size = intentFilters.getLength();
//                for (int i1 = 0; i1 < size; i1++) {
//                    Element intentFilter = (Element) intentFilters.item(i);
//                    if (intentFilter != null) {
//                        System.out.println("@@@@@@@@@@@@@@@@");
//
//                        NodeList actionions = intentFilter.getElementsByTagName("action");
//                        NodeList categories = intentFilter.getElementsByTagName("category");
//
//                        Element action = (Element) actionions.item(0);
//                        Element category = (Element) categories.item(0);
//
//                        String actionName = action.getAttribute("android:name");
//                        String categoryName = category.getAttribute("android:name");
//
//                        String launcherActioName = "android.intent.action.MAIN";
//                        String launcherCategoryName = "android.intent.category.LAUNCHER";
//
//                        if (!launcherCategoryName.equals(categoryName) || !launcherActioName.equals(actionName)) {
//                            Element intentFilterElement = makeLauncherFilter(doc);
//                            activity.appendChild(intentFilterElement);
//                        }
//                        System.out.println(intentFilter + ", " + actionName + ", " + categoryName);
//                        //intentFilter.setNodeValue();
//                    } else {
//                        Element intentFilterElement = makeLauncherFilter(doc);
//                        activity.appendChild(intentFilterElement);
//                    }
//                }
        }

//        Write the changes back to the XML file
//        (You may need to customize this part depending on your specific requirements)
//         ...
//
//        For example, to save the changes back to the same file:
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(xmlFile);
//        transformer.transform(source, result);
    }
}

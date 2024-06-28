package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShadowVortex {
    public static void main(String[] args) {
        findInstrum();
    }
    //adb shell pm list instrumentation
//    adb shell am instrument -w <component_name>

    //adb shell am instrument -w app.source.getcontact/androidx.test.core.app.InstrumentationActivityInvoker$BootstrapActivity
    //adb shell am instrument -w com.instagram.android/androidx.test.core.app.InstrumentationActivityInvoker$EmptyActivity


    private static void findInstrum() {
        try {
            // Выполнение adb shell pm list packages
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "shell", "pm", "list", "packages");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                // Обработка каждой строки вывода, которая начинается с "package:"
                if (line.startsWith("package:")) {
                    String packageName = line.substring("package:".length()).trim();
                    StringBuilder sb=new StringBuilder();
                    sb.append("Package Name: " + packageName).append("\n");

                    // Выполнение adb shell pm dump для текущего пакета
                    ProcessBuilder dumpProcessBuilder = new ProcessBuilder("adb", "shell", "pm", "dump", packageName);
                    Process dumpProcess = dumpProcessBuilder.start();
                    BufferedReader dumpReader = new BufferedReader(new InputStreamReader(dumpProcess.getInputStream()));

                    String dumpLine;
                    boolean foundInstrumentation = false;
                    while ((dumpLine = dumpReader.readLine()) != null) {
                        // Поиск строки с "Instrumentation"
                        if (dumpLine.contains("Instrumentation")) {
                            sb.append("Instrumentation found: ");
                            sb.append(dumpLine);
                            foundInstrumentation = true;
                            break;
                        }
                    }

                    if (!foundInstrumentation) {
                        //System.out.println("No Instrumentation found for package " + packageName);
                    }else {
                        System.out.println(sb.toString());
                    }

                    dumpReader.close();
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

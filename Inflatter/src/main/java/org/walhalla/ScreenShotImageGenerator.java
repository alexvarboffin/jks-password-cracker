package org.walhalla;

import java.io.*;
import java.util.List;
import java.util.Arrays;

public class ScreenShotImageGenerator {

    static int width = 1080;
    static int height = 1920;
    private static final String MAGICK = "\"C:\\Program Files\\ImageMagick-7.0.10-Q16\\magick.exe\"";




    public void mmm8() {
        int doubleWidth = width * 2;
        System.out.println(doubleWidth + "x" + height);

//        String shell = "convert \"C:\\Users\\combo\\Desktop\\scr\\00\\test\\00.png\" -gravity North -background none -splice 0x250 " +
//                "\"C:\\Users\\combo\\Desktop\\scr\\00\\test\\montage_with_padding.png\"";
//        System.out.println(shell);


        System.out.println(MAGICK + " -size " + doubleWidth + "x" + height + " canvas:black background.png");
        System.out.println("convert background.png 123.png -composite -gravity center composite.png");

        System.out.println("=========== Разрезаем двойной скрин на 2 ==============");
        System.out.println("convert.exe -crop " + width + "x c.png cropped/cropped_%d.png");


        System.out.println("magick.exe cropped_0.png -font Arial -pointsize 44 -fill white -gravity north -annotate +0+120 \"666666\" C:\\Users\\combo\\Desktop\\scr\\00\\5w5.png");
        //System.out.println("magick.exe  -size  1080x1920  canvas:transparent  -font  Arial  -pointsize  44  -fill  white  -gravity  north  -annotate  +0+120  \"666666\"  C:\\Users\\combo\\Desktop\\scr\\00\\5w5.png");

        //infoprint();
    }

    public void infoprint() {
        //1920 до 7680
        //1080 до 7680

        //120.0
        System.out.println("-------9999--------" + ((float) 1080 / 9f));
        System.out.println("-------9999--------" + ((float) 7680 / 120f));

        //160.0 640.0
        System.out.println("---------------" + ((float) 1920 / 12f) + " " + (7680 / 12f));
        System.out.println(7680 / 1920f);//4.0

        System.out.println(160 * 1 * 9);//1920
        System.out.println(160 * 1 * 12);

        System.out.println("Landscape:");
        lands(1);
        lands(2);
        lands(3);
        lands(4);

        System.out.println("Port 160:");
        port160(1);
        port160(2);
        port160(3);
        port160(4);


        System.out.println("Port 120:");
        port120(1);
        port120(2);
        port120(3);
        port120(4);

        //Chromebook16x9Port
    }

    private static void port120(int i) {
        int w = 120 * 9 * i;
        int h = 120 * 12 * i;

        System.out.println(h + "x" + w + " []");
    }

    private static void port160(int i) {
        int w = 160 * 9 * i;
        int h = 160 * 12 * i;

        System.out.println(h + "x" + w + " []");
    }

    private static void lands(int i) {
        int h = 160 * 9 * i;
        int w = 160 * 12 * i;

        System.out.println(h + "x" + w + " ==");
    }




    String[] gravity = new String[]{
            "north",
            "north",
            "north",
            "north",
            "north",

    };

    /**
     * // Путь для сохранения изображений
     * String outputPath
     */

    public void generateCoolScreenShots(List<String> slidesText, File backgroundFile) {


        // Путь для сохранения изображений

        File textsFolder = new File(backgroundFile, "texts");
        generateTextCard(slidesText, textsFolder);


        File outFolder = tmpOutFolder(new File(backgroundFile, "GooglePlay").getAbsolutePath());


        File[] m = backgroundFile.listFiles(pathname -> pathname.getAbsolutePath().endsWith(".png"));
        File[] t = textsFolder.listFiles();


        for (int i = 0; i < m.length; i++) {
            File top = m[i];
            File textOverlay = t[i];
            String gravity = ((i == 2) ? "south" : "north");
            File out = new File(outFolder, i + ".png");
            String sh = MAGICK + " composite -gravity " + gravity + " \"" + textOverlay.getAbsolutePath() + "\" \"" + top + "\" \"" + out + "\"";
            System.out.println(sh);

            runShell(sh);
        }

        System.out.println("Background: " + slidesText.size() * width + "x" + height);
    }

    private File tmpOutFolder(String s) {
        File generatedFolder = new File(s);
        if (!generatedFolder.exists()) {
            generatedFolder.mkdirs();
        }
        return generatedFolder;
    }

    private void generateTextCard(List<String> slidesText, File textOutFolder) {

        if (!textOutFolder.exists()) {
            textOutFolder.mkdirs();
        }

        // Шрифт и размер текста
        String font = "C:/Users/combo/Downloads/Bebas/Bebas-Regular.ttf";//Arial
        int fontSize = 44;

        // Параметры изображения

//        String backgroundColor = "transparent";//black transparent
//        String textColor = "white";//white black
//        int height = 1920;


        String backgroundColor = "black";//black transparent
        String textColor = "white";//white black


        // Генерация изображений для каждого слайда
        for (int i = 0; i < slidesText.size(); i++) {


            int height = ((i == 0) ? (450) : 300);
            String gravity = ((i == 2) ? "south" : "north");


            String text = slidesText.get(i);
            String outputFileName = String.format("%s/%d.png", textOutFolder.getAbsolutePath(), i + 1);
            //"C:\Program Files\ImageMagick-7.0.10-Q16\magick.exe" -size 2160x450 -background black -fill white -gravity north pango:"<span font='Bebas 44' foreground='white'>📏 Precise Leveling Tool 📏\nPerfect for DIY & Professional Use 🛠️</span>" D:\walhalla\Tools\Resources\cropped\texts/1.png

            // Команда ImageMagick для генерации изображения с текстом
            String[] command = {
                    MAGICK, // Или "convert" в зависимости от версии ImageMagick
//                    "-background", backgroundColor,
//                    "-fill", textColor,
//                    "-font", font,
//                    "-pointsize", String.valueOf(fontSize),
//                    "-size", width + "x" + height,
//                    "-gravity", "center",
//                    "label:" + text,
//                    outputFileName


                    "-size", ((i == 0) ? (width * 2) : width) + "x" + height,

                    "canvas:" + backgroundColor,
                    //@@@@@"C:\\Users\\combo\\Desktop\\scr\\00\\screenshot0\\c2_0.png",

                    "-font", font,
                    "-pointsize", String.valueOf(fontSize),
                    "-fill", textColor,

//                    "-stroke","black",
//                    "-strokewidth","2",

                    "-gravity", gravity,
                    "-annotate",

                    //Отступы текста от лева и верха
                    "+0+120", //"+100+120",
                    "\""+text+"\"",

                    //"-draw", "rectangle 50,100 1030,180",

                    outputFileName
            };

            System.out.println(Arrays.toString(command).replace(",", " "));
            runShell(command);


        }
    }

    private void runShell(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            // Запуск процесса
            Process process = processBuilder.start();

            // Чтение вывода процесса
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Чтение ошибок процесса
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            // Ожидание завершения процесса
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                //System.out.println("Generated: " + outputFileName);
                System.out.println("Generated: ");

            } else {
                System.err.println("Произошла ошибка при разбиении видео на сегменты.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runShell(String[] command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            // Запуск процесса
            Process process = processBuilder.start();

            // Чтение вывода процесса
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Чтение ошибок процесса
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            // Ожидание завершения процесса
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                //System.out.println("Generated: " + outputFileName);
                System.out.println("Generated: ");

            } else {
                System.err.println("Произошла ошибка при разбиении видео на сегменты.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

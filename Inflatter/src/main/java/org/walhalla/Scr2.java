package org.walhalla;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Scr2 {

//    static final List<String> slidesText = Arrays.asList(
//            "🌟 Ultimate.TV Entertainment Hub 🌟\n🍿 Discover Ultimate Collections with our IPTV Player 🎥\nUltimate.TV is your top choice for IPTV streaming.\n🎉 Live Channels   🎬 Movies   📺 TV Shows\n👥 Create a new account",
//            "🚀 Elevate Your Live TV & IPTV Experience 📡\nwith intuitive features 🔧",
//            "🎨 Import and personalize\nyour favorite IPTV Playlists & Channels 📂",
//            "🌍 Access unlimited IPTV Channels & Playlists 📺",
//            "🎈 Watch your favorite content anytime, anywhere! 🎈",
//            "Add your favorite categories and channels"
//    );

//    static final List<String> slidesText = Arrays.asList(
//            "📏 Precise Leveling Tool 📏\nPerfect for DIY & Professional Use 🛠️",
//            "🎯 Accurate Measurements in Real-Time 🕒\nFor Horizontal, Vertical & 360° Levels",
//            "🚀 Easy-to-Use Interface 🌟\nLevel Any Surface in Seconds!",
//            "🔄 Quick Calibration 🛠️\nEnsure Precise Results Every Time",
//            "🏗️ Essential for Construction & Home Projects 🏠\nFrom Shelves to Frames!",
//            "📱 Optimized for All Device Sizes 📲\nWorks on Phones & Tablets",
//            "🔔 Regular Updates with New Features 🔧\nKeep Your Tools Up to Date!"
//    );


    public static void main(String[] args) {
        Preset p = new Preset(Arrays.asList(
                "Ultimate.TV Entertainment Hub\nDiscover Ultimate Collections with our IPTV Player\nUltimate.TV is your top choice for IPTV streaming.\nLive Channels   Movies   TV Shows",
                "Elevate Your Live TV & IPTV Experience\nwith intuitive features",
                "Import and personalize\nyour favorite IPTV Playlists & Channels",
                "Access unlimited IPTV Channels & Playlists",
                "Watch your favorite content anytime, anywhere!",
                "Add your favorite categories and channels"
        )
                , "C:\\Users\\combo\\Desktop\\scr\\00\\backgrounds");

//        Preset p = new Preset(Arrays.asList(
//                "Precise Leveling Tool\nPerfect for DIY & Professional Use",
//                "Accurate Measurements in Real-Time\nFor Horizontal, Vertical & 360° Levels",
//                "Easy-to-Use Interface\nLevel Any Surface in Seconds!",
//                "Quick Calibration\nEnsure Precise Results Every Time",
//                "Essential for Construction & Home Projects\nFrom Shelves to Frames!",
//                "Optimized for All Device Sizes\nWorks on Phones & Tablets",
//                "Regular Updates with New Features\nKeep Your Tools Up to Date!"
//        )
//                , "D:\\walhalla\\Tools\\Resources\\cropped");


        ScreenShotImageGenerator s = new ScreenShotImageGenerator(

        );
        //Make in Photoshop double screen
        //Split double screen
        //
        //s.mmm8();//convert.exe -crop 1080x 00.png 00_%d.png
        //make screens
        s.generateCoolScreenShots(p.slidesText, p.backgroundFile);
    }


    private static class Preset {

        List<String> slidesText;
        File backgroundFile;

        public Preset(List<String> slidesText, String s) {
            this.slidesText = slidesText;
            backgroundFile = new File(s);
        }
    }
}

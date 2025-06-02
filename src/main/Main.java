package main;

import frame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class Main {

    public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            Font enFont = Font.createFont(Font.TRUETYPE_FONT, new File("./res/fonts/ark-pixel-10px-monospaced-latin.ttf"));
            Font jaFont = Font.createFont(Font.TRUETYPE_FONT, new File("./res/fonts/ark-pixel-10px-monospaced-ja.ttf"));
            System.out.println(enFont.getFontName());
            System.out.println(jaFont.getFontName());
            ge.registerFont(enFont);
            ge.registerFont(jaFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainMenuFrame());

    }


}

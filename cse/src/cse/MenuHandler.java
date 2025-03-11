package cse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;


public class MenuHandler {
    public static JMenuBar createMenuBar(final JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        
        // Game Menü
        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        JMenuItem highScoresMenuItem = new JMenuItem("High Scores");

        restartMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (frame instanceof MemoryGame2) {
                    ((MemoryGame2)frame).restartGame();
                } else if (frame instanceof MemoryGame3) {
                    ((MemoryGame3)frame).restartGame();
                }
                else if (frame instanceof MemoryGame) {
                    ((MemoryGame)frame).restartGame();
                }
                }
        });

        highScoresMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHighScores(frame);
            }
        });

        gameMenu.add(restartMenuItem);
        gameMenu.add(highScoresMenuItem);

        // About Menü
        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutGameMenuItem = new JMenuItem("About the Game");
        JMenuItem aboutDeveloperMenuItem = new JMenuItem("About the Developer");

        aboutGameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutGame(frame);
            }
        });

        aboutDeveloperMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutDeveloper(frame);
            }
        });

        aboutMenu.add(aboutGameMenuItem);
        aboutMenu.add(aboutDeveloperMenuItem);

        // Exit Menü
        JMenu exitMenu = new JMenu("Exit");
        JMenuItem exitToMenuMenuItem = new JMenuItem("Exit to Menu");
        JMenuItem exitGameMenuItem = new JMenuItem("Exit Game");

        exitToMenuMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                frame.dispose(); 
                new Menu(); 
            }
        });

        exitGameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                frame.dispose(); // 
            }
        });

        exitMenu.add(exitToMenuMenuItem);
        exitMenu.add(exitGameMenuItem);

        // Menüleri yatay olarak hizalamak için boşluklar ekle
        //// Burda menüleri orta kısma taşımaya çalıştım
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(gameMenu);
        menuBar.add(Box.createHorizontalStrut(10)); // Boşluk ekle
        menuBar.add(aboutMenu);
        menuBar.add(Box.createHorizontalStrut(10)); // Boşluk ekle
        menuBar.add(exitMenu);
        menuBar.add(Box.createHorizontalGlue()); // Menüyü ortala

        return menuBar;
    }

    private static void showHighScores(JFrame frame) {
        
        ArrayList<String> highScores = HighScoreManager.getHighScores();
        if (highScores.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No high scores found!", "High Scores", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder highScoresString = new StringBuilder();
            for (String score : highScores) {
                highScoresString.append(score).append("\n");
            }
            JOptionPane.showMessageDialog(frame, highScoresString.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void showAboutGame(JFrame frame) {
       
        String aboutGameInfo = "This game is a memory game where you need to match pairs of images.";
        JOptionPane.showMessageDialog(frame, aboutGameInfo, "About the Game", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showAboutDeveloper(JFrame frame) {
       
        String aboutDeveloperInfo = "Developer: Özer Efe Engin \nStudent Number: 20210702033 ";
        JOptionPane.showMessageDialog(frame, aboutDeveloperInfo, "About the Developer", JOptionPane.INFORMATION_MESSAGE);
    }
}

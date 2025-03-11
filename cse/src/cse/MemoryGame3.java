package cse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame3 extends JFrame implements ActionListener, Restartable {
    JButton gameButton;
    JLabel triesLabel;
    JPanel gamePanel;
    HighScoreManager highScoreManager;

    int triesLeft = 12;
    String imageFolderPath = "src/Level3/";
    ArrayList<String> images = new ArrayList<String>();
    ArrayList<JButton> cards = new ArrayList<JButton>();
    JButton lastClicked = null;
    String lastClickedImagePath = null;
    ImageIcon noImageIcon;
    boolean waitingForMatch = false;

    public MemoryGame3() {
        setTitle("Gaming PC Parts");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 650);
        setLayout(new BorderLayout());

        highScoreManager = new HighScoreManager();

        JMenuBar menuBar = MenuHandler.createMenuBar(this);
        setJMenuBar(menuBar);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {
            JButton card = new JButton();
            card.setActionCommand(Integer.toString(i));
            card.addActionListener(this);
            cards.add(card);
            gamePanel.add(card);
        }

        add(gamePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.RED);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel levelLabel = new JLabel("Level 3         ");
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 32));

        bottomPanel.add(levelLabel);

        triesLabel = new JLabel("   Tries Left: " + triesLeft);
        triesLabel.setForeground(Color.WHITE);
        triesLabel.setFont(new Font("Arial", Font.BOLD, 32));

        bottomPanel.add(triesLabel);

        bottomPanel.setPreferredSize(new Dimension(800, 50));

        add(bottomPanel, BorderLayout.NORTH);

        loadImages();
        noImageIcon = new ImageIcon(imageFolderPath + "no_image.png");

        setVisible(true);
        startGame();
    }

    private void loadImages() {
        ArrayList<String> selectedImages = selectRandomImages(8);
        for (String imagePath : selectedImages) {
            images.add(imagePath);
            images.add(imagePath);
        }
        Collections.shuffle(images);
    }

    private ArrayList<String> selectRandomImages(int count) {
        ArrayList<String> allImages = new ArrayList<String>();
        for (int i = 0; i < 9; i++) {
            allImages.add(imageFolderPath + i + ".png");
        }
        Collections.shuffle(allImages);
        ArrayList<String> selectedImages = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            selectedImages.add(allImages.get(i));
        }
        return selectedImages;
    }

    private boolean allCardsMatched() {
        for (JButton card : cards) {
            if (card.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    public void actionPerformed(ActionEvent e) {
        if (waitingForMatch) return;
        JButton clickedButton = (JButton) e.getSource();
        final int index = Integer.parseInt(clickedButton.getActionCommand());
        final String imagePath = images.get(index);

        if (lastClicked == null) {
            lastClicked = clickedButton;
            lastClickedImagePath = imagePath;
            setCardImage(clickedButton, imagePath);
        } else {
            setCardImage(clickedButton, imagePath);

            if (lastClicked != clickedButton ) {
                if (lastClickedImagePath.equals(imagePath)) {
                    lastClicked.setEnabled(false);
                    clickedButton.setEnabled(false);
                    lastClicked = null;
                    GameSession.getInstance().addScore(3); 
                    if (allCardsMatched()) {
                        String playerName = JOptionPane.showInputDialog(this, "You won! Congratulations!\nPlease enter your name:", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                        if (playerName != null && !playerName.trim().isEmpty()) {
                            highScoreManager.saveHighScore(playerName, GameSession.getInstance().getTotalScore());
                        } else {
                            highScoreManager.saveHighScore("Player", GameSession.getInstance().getTotalScore());
                        }
                        JOptionPane.showMessageDialog(this, "Final Score: " + GameSession.getInstance().getTotalScore(), "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new Menu();
                    }
                } else {
                    waitingForMatch = true;
                    triesLeft--;
                    triesLabel.setText("Tries Left: " + triesLeft);
                    GameSession.getInstance().addScore(-3); 
                    if (triesLeft == 0) {
                        JOptionPane.showMessageDialog(this, "You lost. Try again!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        new MemoryGame();
                    } else {
                        final JButton finalClickedButton = clickedButton;
                        final JButton finalLastClicked = lastClicked;
                        new Thread(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            SwingUtilities.invokeLater(() -> {
                                setCardImage(finalLastClicked, imageFolderPath + "no_image.png");
                                setCardImage(finalClickedButton, imageFolderPath + "no_image.png");
                                lastClicked = null;
                                waitingForMatch = false;
                                shuffleCards();
                            });
                        }).start();
                    }
                }
            }
        }
    }

    private void setCardImage(JButton card, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        icon.setDescription(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        card.setIcon(scaledIcon);
    }

    private void startGame() {
        Collections.shuffle(images);
        for (int i = 0; i < cards.size(); i++) {
            JButton card = cards.get(i);
            setCardImage(card, imageFolderPath + "no_image.png");
            card.setEnabled(true);
        }
    }

    @Override
    public void restartGame() {
        dispose();
        GameSession.getInstance().reset(); 
        new MemoryGame();
    }

    private void shuffleCards() {
        ArrayList<String> unmatchedImages = new ArrayList<String>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isEnabled()) {
                unmatchedImages.add(images.get(i));
            }
        }
        Collections.shuffle(unmatchedImages);
        int j = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isEnabled()) {
                images.set(i, unmatchedImages.get(j));
                j++;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MemoryGame3();
            }
        });
    }
}

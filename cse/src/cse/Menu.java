package cse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {
    JButton startButton, levelButton, instructionsButton, exitButton;
    JLabel titleLabel;
    BackgroundPanel backgroundPanel;

    public Menu() {
        setTitle("Memory Card Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(711, 400);
        setLayout(new BorderLayout());
        setResizable(true);

     
        backgroundPanel = new BackgroundPanel("src/images/background.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel, BorderLayout.CENTER);

      
        titleLabel = new JLabel("Memory Card Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.CYAN);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        backgroundPanel.add(titleLabel, gbc);

        
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        gbc.gridy = 1;
        backgroundPanel.add(startButton, gbc);

        levelButton = new JButton("Select Level");
        levelButton.addActionListener(this);
        gbc.gridy = 2;
        backgroundPanel.add(levelButton, gbc);

        instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(this);
        gbc.gridy = 3;
        backgroundPanel.add(instructionsButton, gbc);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        gbc.gridy = 4;
        backgroundPanel.add(exitButton, gbc);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == instructionsButton) {
           
            showInstructions(this);
        } else if (e.getSource() == startButton) {
          
            setVisible(false);
            new MemoryGame(); 
        } else if (e.getSource() == levelButton) {
           
            new Select_Level(this);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private static void showInstructions(JFrame frame) {
        
        String instructionsMessage = "There are 3 levels in the game. It gets gradually harder! Match all pairs of cards to win!";
        JOptionPane.showMessageDialog(frame, instructionsMessage, "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu();
            }
        });
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String filePath) {
        try {
            backgroundImage = new ImageIcon(filePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Arka plan resmini panelin boyutuna göre ölçeklendir
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

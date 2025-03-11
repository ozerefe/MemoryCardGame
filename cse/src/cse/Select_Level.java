package cse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Select_Level extends JFrame implements ActionListener {
    JButton level1Button, level2Button, level3Button;
    JFrame parentFrame;

    public Select_Level(JFrame parent) {
        this.parentFrame = parent;

        setTitle("Select Level");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridLayout(3, 1));

     
        level1Button = new JButton("Level 1");
        level1Button.addActionListener(this);
        add(level1Button);

       
        level2Button = new JButton("Level 2");
        level2Button.addActionListener(this);
        add(level2Button);

       
        level3Button = new JButton("Level 3");
        level3Button.addActionListener(this);
        add(level3Button);

        setLocationRelativeTo(parent); // Pencereyi ana pencerenin ortasına yerleştir
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == level1Button) {
           
            parentFrame.setVisible(false); 
            new MemoryGame(); 
            GameSession.getInstance().reset();
            dispose();
        } else if (e.getSource() == level2Button) {
            // Level 2 seçildiğinde
            parentFrame.setVisible(false);
            new MemoryGame2(); 
            GameSession.getInstance().reset();
            dispose(); 
        } else if (e.getSource() == level3Button) {
          
            parentFrame.setVisible(false); 
            new MemoryGame3(); 
            GameSession.getInstance().reset();
            dispose(); 
        }
    }
}


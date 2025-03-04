/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mino.Block;
import mino.Mino;
import mino.Mino_L1;

/**
 *
 * @author travi
 */
public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame ("Rip-Off Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        // Add gamePanel to the window
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack(); // size of GamePanel is size of window
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
     
        gp.launchGame();
    }
}

class GamePanel extends JPanel implements Runnable{
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager pm;
    
    
    public GamePanel() {
        
        // Panel Settings
        this.setPreferredSize (new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        // Implement KeyListener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        
        pm = new PlayManager();
    }
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
//        System.out.println("run");
        
        // Game Loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
        
    }
    private void update() {
       if(KeyHandler.pausePressed == false && pm.gameOver == false) {
        pm.update(); 
       }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);
    }
}


package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainPanel extends JPanel implements KeyListener, ActionListener {
    SuperFrame superFrame;
    int selectedIndex;
    Timer timer = new Timer(20,this);
    boolean isFirstEnter = false;

    public MainPanel(SuperFrame superFrame) {
        this.superFrame = superFrame;

        this.setBounds(0,0,500,600);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                if(150<x && x<340 && y>300 && y<379){
                    selectedIndex = 0;
                    play();
                    playSelectSound();
                }
                if(150<x && x<340 && y>400 && y<479){
                    selectedIndex = 1;
                    exit();
                    playSelectSound();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0,0,500,600);
        g.setColor(Color.DARK_GRAY);
        switch (selectedIndex){
            case 0:
                g.fillRect(150,300,190,79);
                break;
            case 1:
                g.fillRect(150,400,190,79);
                break;
        }
        Data.hello.paintIcon(this,g,50,50);
        Data.play.paintIcon(this,g,150,300);
        Data.exit.paintIcon(this,g,150,400);
    }

    public void playSelectSound(){
        String musicLocation = new String("src/sample/fire.wav");
        try {
            File musicPath = new File(musicLocation);

            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> {
                selectedIndex = (selectedIndex+1) % 2;
                playSelectSound();
            }
            case KeyEvent.VK_DOWN -> {
                selectedIndex = (selectedIndex+3) % 2;
                playSelectSound();
            }
            case KeyEvent.VK_ENTER -> {
                if(!isFirstEnter){
                    if(selectedIndex == 0){
                        play();
                    } else{
                        exit();
                    }
                } else isFirstEnter = false;
            }
        }
    }

//    public void play(){
//        JFrame frame = new JFrame();
//
//        JMenuBar menuBar = new JMenuBar();
//        JMenu menu1 = new JMenu("menu1");
//        JMenu menu2 = new JMenu("menu2");
//        JMenuItem item1 = new JMenuItem("kaishi");
//        JMenuItem item2 = new JMenuItem("save");
//        JMenuItem item3 = new JMenuItem("caidan");
//
//        menu1.add(item1);
//        menu2.add(item2);
//        menu2.add(item3);
//
//        menuBar.add(menu1);
//        menuBar.add(menu2);
//        menuBar.setBounds(0,0,700,20);
//        frame.setJMenuBar(menuBar);
//
//        var panel = new GamePanel();
//
//        panel.setBounds(0,50,700,500);
//        frame.add(panel);
//
//        frame.setBounds(100,200,700,560);
//        frame.setVisible(true);
//
//        item3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame frame=new JFrame("GreedySnake");
//                frame.setBounds(10,10,900,720);
//                frame.setResizable(false);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.add(new gamepanel());
//
//                frame.setVisible(true);
//            }
//        });
//    }

    public void play(){
        if(superFrame.isMainPanelEnabled){
            isFirstEnter = true;
            superFrame.isMainPanelEnabled = false;
            superFrame.cardLayout.show(superFrame.centerPanel,"sub");
        }
    }

    public void exit(){
        if(superFrame.isMainPanelEnabled){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        timer.start();
    }
}

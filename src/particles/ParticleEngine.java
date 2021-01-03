/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

/**
 *
 * @author Calvin
 */
public class ParticleEngine {
    
    private boolean enabled = false;
    private JFrame f;
    private Graphics g;
    private Graphics offScreen;
    private Image image;
            
    private TimerTask timerTask;
    private Timer timer;
    private Random r;
    
    private int screenWidth;
    private int screenHeight;
    
    private ArrayList<Particle> particles;
    
    public ParticleEngine(Graphics g, JFrame f) {
        this.enabled = false;
        this.g = g;
        this.f = f;
        this.r = new Random();
        
        timerTask = new RepeatingTimer(this);
        timer = new Timer(true);
        
        image = f.createImage(f.getWidth(), f.getHeight());
        if (image != null) {
             offScreen = image.getGraphics();
        }
        
        screenWidth = ParticleEngine.getScreenWidth();
        screenHeight = ParticleEngine.getScreenHeight();
       
        
        particles = new ArrayList<>();
        this.populateParticles();


    }
    
    
    private void populateParticles() {
        //particles.add(new Particle(900, 300, Color.WHITE, 0, 0, 0)); //gravity well
        //particles.get(0).setSize(2);
        for (int i = 0; i < 500; i++) {
            particles.add(getRandomParticle());
        }
    }
    
    
    public void paint() {
        g = f.getGraphics();
        if (g == null) return;
        if (offScreen == null) return;
        
        //clear buffer
        offScreen.setColor(Color.BLACK);
        offScreen.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
        
        for (Particle p : particles) {
            offScreen.setColor(p.getColor());
            //offScreen.drawRect((int) p.getX(), (int) p.getY(), p.getSize(), p.getSize()); // Single pixel
            offScreen.drawOval((int) p.getX(), (int) p.getY(), p.getSize(), p.getSize());   // Circle
        }
        
        
        g.drawImage(image, 0, 100, f);
    }
    
    public void tick() {
        this.updateParticles();
        this.paint();
        this.possiblyAddNewParticle();
        
        //System.out.println(particles.size());
    }
    
    private void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).updateParticle();
            if(particles.get(i).isDead()) {
                particles.remove(i);
            }
        }
        
    }
    
    public Particle getRandomParticle() {
        return new Particle(this.randomXPosition(), this.randomYPosition(), 
                                        this.randomColor(), -0.5, 
                                        this.randomVelocity(), this.randomVelocity() );
    }
    
    private void possiblyAddNewParticle() {
        double n = 200 / particles.size();
        
        if (this.randProbability(n)) {
            particles.add(getRandomParticle());
        }
        
    }
    
    private double randomVelocity() {
        return ( (r.nextDouble() * 2) - 1 );
    }
    
    private double randomXPosition() {
        return (r.nextDouble() * screenWidth);
    }
    
    private double randomYPosition() {
        return (r.nextDouble() * screenHeight);
    }
    
    private Color randomColor() {
        int red = r.nextInt(256);
        int g = r.nextInt(256);
        int b = r.nextInt(256);
        if (red < 100) red = 100;
        if (g < 100) g = 100;
        if (b < 100) b = 100;
        return new Color(red, g, b);   
    }
    
    public void setEnabled(boolean b) {
        this.enabled = b;
        
        if (enabled == true) {
            timer.scheduleAtFixedRate(timerTask, 0, 1);
            
            image = f.createImage(f.getWidth(), f.getHeight());
            if (image != null) {
                offScreen = image.getGraphics();
            }

        } else {
            timer.cancel();
            timerTask = new RepeatingTimer(this);
            timer = new Timer(true);
            
            
        }
    }
    
    public boolean randProbability(double prob) {
        if (prob < 0) System.out.println("Bad probability: " + prob);
        
        Random r = new Random();
        double answer = r.nextInt(101) / 100.0;
        if (answer <= prob) return true;
        return false;
    }
    
    public static int getScreenWidth() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.width;
    }
    
    public static int getScreenHeight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.height;
    }
    
}

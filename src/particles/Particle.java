/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;

/**
 *
 * @author Calvin
 */
public class Particle {
    private double x;
    private double y;
    private double dx;
    private double dy;
    
    private int size; //generally 0
    
    private Color c;
    private double red;
    private double green;
    private double blue;
    private double dc;
    
    private static double gravityWellX = 1000;
    private static double gravityWellY = 300;
    private static final long GRAVITYWELL_MASS = 1000000000;
    
    private static final double AIR_RESISTANCE_ACCELERATION = 0.1;
    
    public Particle() {
        this(0, 0, Color.RED, 0, 0, 0);
    }
    
    public Particle(double x, double y, Color c, double dc, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.red = c.getRed();
        this.green = c.getGreen();
        this.blue = c.getBlue();
        this.dc = dc;
        this.dx = dx;
        this.dy = dy;
        this.size = 4;
    }
    
    public void setSize(int size) {
        if (size < 0) this.size = 0;
        else this.size = size;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public Color getColor() {
        return this.c;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public void setColor(Color c) {
        if (c == null) this.c = Color.RED;
        else this.c = c;
    }
    
    public void updateParticle() {
        this.updateVelocity();
        this.updatePosition();
        //this.updateColor();
    }
    
    public void updateVelocity() {
        
        Point p = MouseInfo.getPointerInfo().getLocation();
        
        double delX = p.x - x;
        double delY = p.y - 100 - y;
        
        
        
        double distanceToWell = Math.sqrt( (delX * delX) + (delY * delY) );
        double acceleration = GRAVITYWELL_MASS * 10 / (distanceToWell * distanceToWell);
        
        double theta = Math.atan2(delY, delX);
        double ay = Math.sin(theta);
        double ax = Math.cos(theta);
        
        dy += ay * 1; //a * time elapsed (should be time between ticks but I want cool effect
        dx += ax * 1;
        
        if (Math.abs(dy) > 2) {
            if (dy > 0) dy -= AIR_RESISTANCE_ACCELERATION;
            else dy += AIR_RESISTANCE_ACCELERATION;
        }
        if (Math.abs(dx) > 2) {
            if (dx > 0) dx -= AIR_RESISTANCE_ACCELERATION;
            else dx += AIR_RESISTANCE_ACCELERATION;
        }
        
    }
    
    public void updatePosition() {
        // Slow down acording to some factor
        x += dx * 0.05;
        y  += dy * 0.05;
        
        
    }
    
    public void updateColor() {
        red += dc;
        green += dc;
        blue += dc;
        
        if (red > 255) red = 255;
        if (green > 255) green = 255;
        if (blue > 255) blue = 255;
        if (red < 0) red = 0;
        if (green < 0) green = 0;
        if (blue < 0) blue = 0;
        
        this.c = new Color( (int) red, 
                            (int) green, 
                            (int) blue);
    }
    
    public boolean isDead() {
        return (c.equals(Color.BLACK));
    }
    
}

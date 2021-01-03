
package particles;

import java.awt.Color;
import java.awt.Container;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Calvin Cramer
 */
public class FullScreen 
    extends JFrame 
    implements ActionListener {
    
    private GraphicsDevice device;
    private DisplayMode originalDM;
    private boolean isFullScreen = false; // initially false
    
    private JButton switchViewModesButton = new JButton("Toggle Views");
    private JButton exitButton = new JButton("Exit");
    private JCheckBox particlesCheckBox = new JCheckBox("Particles", false);
    private Point centerPoint;
    
    private ParticleEngine pe;
    
    public FullScreen(GraphicsDevice device, Point centerPoint) {
        
        super(device.getDefaultConfiguration());
        
        this.device = device;
        this.centerPoint = centerPoint;
        setTitle("Switch Window Modes");
        originalDM = device.getDisplayMode();
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        
        switchViewModesButton.addActionListener(this);
        exitButton.addActionListener(this);
        particlesCheckBox.addActionListener(this);
        
        this.initComponents(this.getContentPane());
        
        pe = new ParticleEngine(this.getGraphics(), this);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ev) {
        Object source = ev.getSource();

        if (source.equals(switchViewModesButton)) {
        
            if (isFullScreen) {
                //set to windowed mode
                this.dispose();
                this.setUndecorated(false);
                device.setFullScreenWindow(null);

                this.setLocation(centerPoint.x - 500, centerPoint.y - 300);

                this.pack();
                this.setSize(900, 400);
                setVisible(true);
                this.setResizable(true);
                isFullScreen = false;

            } else {
                //set full-screen
                device.setFullScreenWindow(this);
                this.validate();
                this.setResizable(false);
                isFullScreen = true;
            }
            
        } else if (source.equals(exitButton)) {
            
            System.exit(0);
        } else if (source.equals(particlesCheckBox)) {
            if (particlesCheckBox.isSelected()){
                //particles
                pe.setEnabled(true);
                
            } else {
                //no particles
                pe.setEnabled(false);
            }
            
        } else {
            System.out.println("undefined source: " + source.toString());
        }
        
    }
    
    @Override
    public void update(Graphics g) {
        this.paint(g);
    }
    
    private void initComponents(Container c) {
        this.setContentPane(c);
        /*
        BorderLayout bl = new BorderLayout();
        c.setLayout(bl);
        //add some stuff
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(500, 100));
        c.add(topPanel, BorderLayout.NORTH);
        
        //add the switchViewModesButton
        switchViewModesButton.setPreferredSize(new Dimension(200, 100));
        c.add(switchViewModesButton, BorderLayout.CENTER);
                */
        
        c.setLayout(null);
        
        c.setBackground(Color.BLACK);
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(new LineBorder(new Color(40, 40, 40), 1, false));
        
        topPanel.setBounds(10, 10, 800, 45);
        
        particlesCheckBox.setBackground(Color.BLACK);
        particlesCheckBox.setForeground(Color.WHITE);
        topPanel.add(particlesCheckBox);
        
        topPanel.add(exitButton);
        topPanel.add(switchViewModesButton);
        
        c.add(topPanel);
        
    }
    
    public void begin() {
        setUndecorated(isFullScreen);
        setResizable(!isFullScreen);
        
        if (isFullScreen) {
            // Full-screen mode
            device.setFullScreenWindow(this);
            validate();
            
        } else {
            // Windowed mode
            this.setLocation(centerPoint);
            pack();
            setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice[] devices = env.getScreenDevices();
        
        FullScreen fs = new FullScreen(devices[0], env.getCenterPoint());
        fs.begin();
        
        
    }
}

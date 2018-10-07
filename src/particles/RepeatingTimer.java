/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particles;

import java.util.TimerTask;
import java.util.Timer;
/**
 *
 * @author Calvin
 */
public class RepeatingTimer extends TimerTask{
    
    private ParticleEngine pe;
    
    public RepeatingTimer(ParticleEngine pe) {
        this.pe = pe;
    }
    @Override
    public void run() {
        //stuff
        pe.tick();
    }
    
}

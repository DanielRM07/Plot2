/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plot1;

/**
 *
 * @author daniel
 */
public class Rio {
    Delta delta;
    Nascente nasc;

    public Rio(PointLocation delta, PointLocation nasc) {
        this.delta = new Delta(delta);
        this.nasc = new Nascente(nasc);
    }
    
    
    
    public double comprimentoReta(){
        double dist = Math.sqrt(
                Math.pow(
                        Math.abs(nasc.xCentral - delta.xCentral),
                        2
                )+
                Math.pow(
                        Math.abs(nasc.yCentral - delta.yCentral),
                        2
                )
        );
        return dist;
    }
}

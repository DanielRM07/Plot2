/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plot1;

/**
 *
 * @author daniel
 */
public class Serra {
    Nascente morroA;
    Nascente morroB;

    public Serra(PointLocation a, PointLocation b) {
        this.morroA = new Nascente(b);
        this.morroB = new Nascente(a);
    }
    
    
    
    public double comprimentoReta(){
        double dist = Math.sqrt(
                Math.pow(
                        Math.abs(morroA.xCentral - morroB.xCentral),
                        2
                )+
                Math.pow(
                        Math.abs(morroB.yCentral - morroA.yCentral),
                        2
                )
        );
        return dist;
    }
    
}

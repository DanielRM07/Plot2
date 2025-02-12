/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plot1;

/**
 *
 * @author daniel
 */
public class Nascente extends PointLocation {

    public Nascente(int x, int y, final int maxX, final int maxY) {
        xCentral = x;
        yCentral = y;
        //limX = maxX-3;
        //limY = maxY-3;
        //padrão
        xPNO = switch (xCentral) {
            case 2 ->
                x - 2;
            case 1 ->
                x - 1;
            case 0 ->
                x;
            default ->
                x - 3;
        };
        if (x > maxX - 4) {
            xCentral = maxX - 4;
            xPSE = x;
        }else{
            xPSE = x + 3;
        }

        yPNO = switch (yCentral) {
            case 2 ->
                y - 2;
            case 1 ->
                y - 1;
            case 0 ->
                y;
            default ->
                y - 3;
        };
        if (y > maxY - 4) {
            yCentral = maxY - 4;
            yPSE = y;
        }else{
            yPSE = y + 3;
        }
    }
    
    public Nascente(PointLocation basico){
        this.xCentral = basico.xCentral;
        this.yCentral = basico.yCentral;
        this.xPNO = basico.xPNO;
        this.xPSE = basico.xPSE;
        this.yPNO = basico.yPNO;
        this.yPSE = basico.yPSE;
    }
}

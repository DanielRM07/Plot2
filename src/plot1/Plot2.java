/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plot1;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author daniel
 */
public class Plot2 extends Plot1{
    
    @Override
    void plotCanvas(Graphics2D g2d){
        g2d.drawImage(donetsk, 0, 0, null);
        placeCities(g2d);
        placeLines(g2d);
    }
    public static void organizarDist(ArrayList<ArrayList> a, int c){      
        int n = a.size(),i,j;
        boolean swapped;
        ArrayList menor;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                Double v1 = Double.valueOf(a.get(j).get(4).toString());
                Double v2 = Double.valueOf(a.get(j+1).get(4).toString());
                if(v1>v2){
                    menor = a.get(j);
                    a.set(j,a.get(j+1));
                    a.set(j+1, menor);
                    swapped = true;
                }
            }
            if (false == swapped) {
                break;
            }
        }
    }    
    @Override
    public void placeLines(Graphics2D g2d){
        int limite = pontos.size();
        int chosenPoint = -1;
        int pop1, pop2=-1, popLim=0, x1, y1, x2, y2;
        double distFinal = -1, distCalc;
        ArrayList<ArrayList> pontosDist = new ArrayList<>();
        for(int i = 0; i < limite; i++){
            pop1 = Integer.parseInt((String) pontos.get(i).get(1));
            x1 = Integer.parseInt((String)pontos.get(i).get(2));
            y1 = Integer.parseInt((String)pontos.get(i).get(3));
            for(int j = 0; j < limite; j++){
                if(j!=i){
                    x2 = Integer.parseInt((String)pontos.get(j).get(2));
                    y2 = Integer.parseInt((String)pontos.get(j).get(3));
                    distCalc = Math.sqrt(
                                Math.abs(x1-x2)*Math.abs(x1-x2) +
                                Math.abs(y1-y2)*Math.abs(y1-y2)
                    );
                    ArrayList d = new ArrayList();
                    d.addAll(pontos.get(j));
                    pontosDist.add(d);
                    pontosDist.getLast().add(distCalc);
                    //System.out.println(pontosDist.getLast().get(0)+" k"+i+"k "+distCalc);
                }
                /*
                if(j!=i){
                    x2 = Integer.parseInt((String)pontos.get(j).get(2));
                    y2 = Integer.parseInt((String)pontos.get(j).get(3));
                    pop2 = Integer.parseInt((String) pontos.get(j).get(1));
                    if(pop1<=pop2 && pop2>popLim){
                        popLim = pop2;
                        chosenPoint = j;
                        System.out.println("CHOSEN");
                        distCalc = Math.sqrt(
                                Math.abs(x1-x2)*Math.abs(x1-x2) +
                                Math.abs(y1-y2)*Math.abs(y1-y2)
                        );                     
                    }else if(pop1>pop2 && popLim!=0){
                        break;
                    }
                }*/
                
            }
            organizarDist(pontosDist, pontosDist.size());
            /*
            System.out.println("cidade: "+pontos.get(i));
            pontosDist.forEach(
                    (e) -> System.out.println(e)
            );
            System.out.println("");
            */
            pop1 = Integer.parseInt(pontos.get(i).get(1).toString());
            x1 = -1;
            y1 = -1;
            for(int j = pontosDist.size()-1; j>=0;j--){
                pop2 = Integer.parseInt(pontosDist.get(j).get(1).toString());
                if(pop2>pop1){
                    x1 = Integer.parseInt(pontosDist.get(j).get(2).toString());
                    y1 = Integer.parseInt(pontosDist.get(j).get(3).toString());
                }
            }
            if(x1 != -1){
                g2d.drawLine(
                        Integer.parseInt((String) pontos.get(i).get(2)),
                        Integer.parseInt((String) pontos.get(i).get(3)),
                        x1,
                        y1
                );
            }
            pontosDist.clear();
        }
    }
}

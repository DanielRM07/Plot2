/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plot1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Plot3 extends Plot1{
    final double anguloRodo = Math.PI/7;
    
    @Override
    void plotCanvas(Graphics2D g2d){
        g2d.drawImage(donetsk, 0, 0, null);
        
        placeLines(g2d);
        placeCities(g2d);
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
    
    public double calcY(double alfa, double n, double x){
        return Math.tan(alfa)*x + n;
    }
    
    @Override
    public void placeLines(Graphics2D g2d){
        int limite = pontos.size();
        int chosenPoint = -1;
        int pop1, pop2=-1, popLim=0, x1, y1, x2, y2;
        int px1,py1;
        boolean drawn = false;
        double distFinal = -1, distCalc;
        ArrayList<ArrayList> pontosDist = new ArrayList<>();
        for(int i = 0; i < limite; i++){
            pop1 = Integer.parseInt((String) pontos.get(i).get(1));
            x1 = Integer.parseInt((String)pontos.get(i).get(2));
            y1 = Integer.parseInt((String)pontos.get(i).get(3));
            /*
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
                }                
            }           
            organizarDist(pontosDist, pontosDist.size());*/
            pontosDist = listarDist(x1, y1, i, limite);
            pop1 = Integer.parseInt(pontos.get(i).get(1).toString());
            x2 = Integer.MIN_VALUE;
            y2 = Integer.MIN_VALUE;
            x1 = Integer.parseInt((String) pontos.get(i).get(2));
            y1 = Integer.parseInt((String) pontos.get(i).get(3));
            int j;
            for(j = 0; j<pontosDist.size();j++){
                pop2 = Integer.parseInt(pontosDist.get(j).get(1).toString());
                if(pop2>pop1){
                    x2 = Integer.parseInt(pontosDist.get(j).get(2).toString());
                    y2 = Integer.parseInt(pontosDist.get(j).get(3).toString());
                    break;
                }
            }            
            if(x2 != Integer.MIN_VALUE){                
                double alfa = -Math.atan2(y1-y2, x2-x1);
                double alfa2l = alfa + Math.PI/8;
                double alfa1l = alfa - Math.PI/8;
                double n1g = 0 - Math.tan(alfa2l) * 0;
                double n1h = 0 - Math.tan(alfa1l) * 0;
                double n2g = y2 - y1 - Math.tan(alfa2l) * (x2-x1);
                double n2h = y2 - y1 - Math.tan(alfa1l) * (x2-x1);
                System.out.println("angle: "+alfa+" city: "+pontos.get(i).get(0));
                if(drawn);
                else{
                    g2d.setStroke(new BasicStroke(3f));
                    g2d.setColor(Color.red);
                    g2d.drawLine(
                            x1,
                            y1,
                            x2,
                            y2
                    );
                     
                }
                drawn=false;
            }
            tracePath(x1, y1, x2, y2, j, pontosDist, g2d);
            pontosDist.clear();
            
        }/*
        for (int i = 0; i < portoes.size(); i++) {
            px1 = Integer.parseInt((String) portoes.get(i).get(1));
            py1 = Integer.parseInt((String) portoes.get(i).get(2));
            pontosDist = listarDist(px1, py1, i, portoes.size());
            g2d.setStroke(new BasicStroke(5f));
            g2d.setColor(Color.GRAY);
            g2d.drawLine(px1, py1, Integer.parseInt((String)pontosDist.get(0).get(2)), Integer.parseInt((String)pontosDist.get(0).get(3)));
        }*/
    }
    
    public ArrayList<ArrayList> listarDist(int x1, int y1, int i, int limite){
        int x2, y2;
        double distCalc;
        ArrayList<ArrayList> pontosDist = new ArrayList<>();
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
            }                
        }
        organizarDist(pontosDist, pontosDist.size());
        return pontosDist;
    }
    
    public void tracePath(int x1, int y1, int x2, int y2, int j, ArrayList<ArrayList> lista,Graphics2D g2d){
                double alfa = -Math.atan2(y1-y2, x2-x1);
                double alfa2l = alfa + anguloRodo;
                double alfa1l = alfa - anguloRodo;
                double n1g = 0 - Math.tan(alfa2l) * 0;
                double n1h = 0 - Math.tan(alfa1l) * 0;
                double n2g = y2 - y1 - Math.tan(alfa2l) * (x2-x1);
                double n2h = y2 - y1 - Math.tan(alfa1l) * (x2-x1);
                boolean drawn = false;
                for (int k = 0; k < j; k++) {
                    int x3 = Integer.parseInt(lista.get(k).get(2).toString());
                    int y3 = Integer.parseInt(lista.get(k).get(3).toString());
                    System.out.println(k+" >>"+lista.get(k).get(0)+">> "+alfa);
                    if(alfa > 0 && alfa < Math.PI/2){
                        System.out.println("case1 = "+(y3-y1)+
                                "\n. & n1g="+calcY(alfa2l, n1g, x3-x1)+
                                "\n. & n1h="+calcY(alfa2l, n1h, x3-x1)+
                                "\n. & n2g="+calcY(alfa2l, n2g, x3-x1)+
                                "\n. & n2h="+calcY(alfa2l, n2h, x3-x1)
                        );
                        if(
                            y3-y1<calcY(alfa2l, n1g, x3-x1)&&
                            y3-y1>calcY(alfa1l, n1h, x3-x1)&&
                            y3-y1>calcY(alfa2l, n2g, x3-x1)&&
                            y3-y1<calcY(alfa1l, n2h, x3-x1)
                        ){
                            
                            g2d.setStroke(new BasicStroke(9f));
                            g2d.setColor(Color.green);
                            g2d.drawLine(
                                x1,
                                y1,
                                x3,
                                y3
                            );
                            g2d.drawLine(
                                x2,
                                y2,
                                x3,
                                y3
                            );
                            tracePath(x3, y3, x2, y2, j, listarDist(x3,y3,k,pontos.size()), g2d);
                            drawn = true;
                        }
                    }else if(alfa < Math.PI && alfa > Math.PI/2){
                        System.out.println("case2 = "+(y3-y1)+
                                "\n. & n1g="+n1g+" x3="+calcY(alfa2l, n1g, x3-x1)+
                                "\n. & n1h="+calcY(alfa2l, n1h, x3-x1)+
                                "\n. & n2g="+calcY(alfa2l, n2g, x3-x1)+
                                "\n. & n2h="+calcY(alfa2l, n2h, x3-x1)
                        );
                        if(
                            y3-y1>calcY(alfa2l, n1g, x3-x1)&&
                            y3-y1<calcY(alfa1l, n1h, x3-x1)&&
                            y3-y1<calcY(alfa2l, n2g, x3-x1)&&
                            y3-y1>calcY(alfa1l, n2h, x3-x1)
                        ){
                             
                            g2d.setStroke(new BasicStroke(9f));
                            g2d.setColor(Color.CYAN);
                            g2d.drawLine(
                                x1,
                                y1,
                                x3,
                                y3
                            );
                            g2d.drawLine(
                                x2,
                                y2,
                                x3,
                                y3
                            );
                            drawn = true;
                        }
                    }else if(alfa > -Math.PI && alfa < -Math.PI/2){
                        System.out.println("case3 = "+(y3-y1)+
                                "\n. & n1g="+calcY(alfa2l, n1g, x3-x1)+
                                "\n. & n1h="+calcY(alfa2l, n1h, x3-x1)+
                                "\n. & n2g="+calcY(alfa2l, n2g, x3-x1)+
                                "\n. & n2h="+calcY(alfa2l, n2h, x3-x1)
                        );
                        if(
                            (y3-y1)>calcY(alfa2l, n1g, x3-x1)&&
                            (y3-y1)<calcY(alfa1l, n1h, x3-x1)&&
                            (y3-y1)<calcY(alfa2l, n2g, x3-x1)&&
                            (y3-y1)>calcY(alfa1l, n2h, x3-x1)
                        ){
                             
                            g2d.setStroke(new BasicStroke(9f));
                            g2d.setColor(Color.YELLOW);
                            g2d.drawLine(
                                x1,
                                y1,
                                x3,
                                y3
                            );
                            g2d.drawLine(
                                x2,
                                y2,
                                x3,
                                y3
                            );
                            drawn = true;
                        }
                    }else if(alfa < 0 && alfa > -Math.PI/2){
                        System.out.println("case4 = "+(y3-y1)+
                                "\n. & n1g="+calcY(alfa2l, n1g, x3-x1)+
                                "\n. & n1h="+calcY(alfa2l, n1h, x3-x1)+
                                "\n. & n2g="+calcY(alfa2l, n2g, x3-x1)+
                                "\n. & n2h="+calcY(alfa2l, n2h, x3-x1)
                        );
                        if(
                            (y3-y1)<calcY(alfa2l, n1g, x3-x1)&&
                            (y3-y1)>calcY(alfa1l, n1h, x3-x1)&&
                            (y3-y1)>calcY(alfa2l, n2g, x3-x1)&&
                            (y3-y1)<calcY(alfa1l, n2h, x3-x1)
                        ){
                             
                            g2d.setStroke(new BasicStroke(9f));
                            g2d.setColor(Color.black);
                            g2d.drawLine(
                                x1,
                                y1,
                                x3,
                                y3
                            );
                            g2d.drawLine(
                                x2,
                                y2,
                                x3,
                                y3
                            );
                            drawn = true;
                        }
                    }
                }
                drawn = true;
                if(drawn);
                else{
                    g2d.setStroke(new BasicStroke(3f));
                    g2d.setColor(Color.red);
                    g2d.drawLine(
                            x1,
                            y1,
                            x2,
                            y2
                    );
                     
                }
                drawn=false;
                
    }
    
    
    }


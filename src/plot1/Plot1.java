/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package plot1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class Plot1 extends JFrame {
    static Image donetsk;
    static ArrayList<ArrayList> pontos = new ArrayList<>();
    static ArrayList<ArrayList> portoes = new ArrayList<>();
    public Plot1(){
        System.out.println("Digite o nome da região:");
        Scanner e = new Scanner(System.in);
        String regiao = e.next();
        System.out.println("Digite a versão da lista:");
        String versao = e.next();
        readList(versao,regiao);        
        //setBounds(0, 0, 1155, 1617);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        donetsk = Toolkit.getDefaultToolkit().createImage("src\\imagens\\"+regiao+".jpg");
        MediaTracker track1 = new MediaTracker(this);
        MediaTracker track2 = new MediaTracker(this);
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        track1.addImage(donetsk, 0);

        
        
        try{
            track1.waitForID(0);
            //System.out.println(track1.isErrorID(0));
        }catch(InterruptedException ae){
            System.out.println("brrrrr");
        }
        int windowH = donetsk.getHeight(this);
        int windowW = donetsk.getWidth(this);
        setSize(windowW, windowH);
    }
    
    void plotCanvas(Graphics2D g2d){
        g2d.drawImage(donetsk, 0, 0, null);        
        //g2d.drawLine(518, 174, 560, 822);
        
        placeLines(g2d);
        placeCities(g2d);
    }
    
    void drawLines(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        plotCanvas(g2d);        
    }
    
    public void placeCities(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(4f));
        for (ArrayList<String> ponto : pontos) {
            g2d.setColor(Color.getHSBColor(colorPopulation(Integer.parseInt(ponto.get(1))), 1f, 0.57f));
            //System.out.println(">>>>"+colorPopulation(Integer.parseInt(ponto.get(1))));
            //System.out.println("["+ponto.get(2)+","+ponto.get(3)+"]");
            int pop = (int)Math.round(Math.pow(Integer.parseInt(ponto.get(1)),(1/2.5d))/3);
            g2d.drawOval(
                    Integer.parseInt(ponto.get(2))-pop/2
                    ,Integer.parseInt(ponto.get(3))-pop/2
                    ,pop
                    ,pop
            );
        }
        for (int i = pontos.size()-1; i >= 0; i--) {
            ArrayList<String> ponto = pontos.get(i);
            //System.out.println("["+ponto.get(2)+","+ponto.get(3)+"]");
            int pop = (int)Math.round(Math.sqrt(Math.sqrt(Integer.parseInt(ponto.get(1)))));
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial",Font.BOLD,11));
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawRect(
                    Integer.parseInt(ponto.get(2))-pop/2,
                    Integer.parseInt(ponto.get(3))-pop/2-6,
                    (int)Math.round(Math.pow(ponto.get(0).length(),1/1.1d)*8),
                    6);
            g2d.fillRect(
                    Integer.parseInt(ponto.get(2))-pop/2,
                    Integer.parseInt(ponto.get(3))-pop/2-6,
                    (int)Math.round(Math.pow(ponto.get(0).length(),1/1.1d)*8),
                    6);
            
            g2d.setColor(Color.getHSBColor(colorPopulation(Integer.parseInt(ponto.get(1))), 1f, 0.57f));
            g2d.drawString(
                    ponto.get(0),
                    Integer.parseInt(ponto.get(2))-pop/2,
                    Integer.parseInt(ponto.get(3))-pop/2
            );
            //g2d.dra            
        }
    }
    
    public void placeLines(Graphics2D g2D){
        int limite = pontos.size();
        int chosenPoint = -1;
        int pop1, pop2;
        for(int i = 0; i < limite; i++){
            while(chosenPoint == -1 || chosenPoint == i){
                chosenPoint =(int) Math.round(Math.random()*limite -1);
            }
            //System.out.println(chosenPoint+" -> "+i);
            pop1 = Integer.parseInt((String) pontos.get(i).get(1));
            pop2 = Integer.parseInt((String) pontos.get(chosenPoint).get(1));
            if(colorPopulation(pop1)>=(240/360f)||colorPopulation(pop2)>=(240/360f)){
                g2D.setStroke(new BasicStroke(6f));
                g2D.setColor(Color.getHSBColor(270/360f, 1f, 0.57f));
            }else{
                g2D.setStroke(new BasicStroke(4f));
                g2D.setColor(Color.getHSBColor(210/360f, 1f, 0.57f));
            }
            g2D.drawLine(
                    Integer.parseInt((String) pontos.get(i).get(2)),
                    Integer.parseInt((String) pontos.get(i).get(3)),
                    Integer.parseInt((String) pontos.get(chosenPoint).get(2)),
                    Integer.parseInt((String) pontos.get(chosenPoint).get(3))
            );
            chosenPoint = -1;
        }
        
               
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        drawLines(g);
    }
    
    /*public void readList(){
        try {
            Scanner roader = new Scanner(new File("src/listas/goias-1.txt"));
            String[] linha;
            while(roader.hasNextLine()){ 
                ArrayList<String> oPonto = new ArrayList<>();
                linha = roader.nextLine().split(",");
                oPonto.addAll(Arrays.asList(linha));
                pontos.add(oPonto);            
            }
            roader = new Scanner(new File("src/listas/exterior.txt"));
            while(roader.hasNextLine()){
                ArrayList<String> oPonto = new ArrayList<>();
                linha = roader.nextLine().split(",");
                oPonto.addAll(Arrays.asList(linha));
                portoes.add(oPonto); 
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("shit");
        }
    }*/
    
    public void readList(String versao, String nome){
        try {
            Scanner roader = new Scanner(new File("src/listas/"+nome+"-"+versao+".txt"));
            String[] linha;
            while(roader.hasNextLine()){ 
                ArrayList<String> oPonto = new ArrayList<>();
                linha = roader.nextLine().split(",");
                oPonto.addAll(Arrays.asList(linha));
                pontos.add(oPonto);            
            }
            roader = new Scanner(new File("src/listas/exterior.txt"));
            while(roader.hasNextLine()){
                ArrayList<String> oPonto = new ArrayList<>();
                linha = roader.nextLine().split(",");
                oPonto.addAll(Arrays.asList(linha));
                portoes.add(oPonto); 
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("shit");
        }
    }
    
    public float colorPopulation(int i){
        if(i>1000000){
            return 270/360f;
        }else if(i>100000){
            return 240/360f;
        }else if(i>10000){
            return 210/360f;
        }else{
            return 180/360f;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                try {
                    new RefPoints();
                } catch (IOException ex) {
                    Logger.getLogger(Plot1.class.getName()).log(Level.SEVERE, null, ex);
                }
                //new Plot4().setVisible(true);
                
            }
        });
    }
    
}

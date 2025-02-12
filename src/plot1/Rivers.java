/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plot1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author daniel
 */
public class Rivers {

    int combinacaoRio = 0;
    ArrayList<ArrayList<Integer>> riosColc = new ArrayList();
    int[][] composicaoMapa;
    int[][] composicaoRuido;
    final int riverLines = 23;

    public Rivers() throws IOException {
        File file = new File("src\\imagens\\krasnodar-2.png");
        BufferedImage image;
        final int imgLimX;
        final int imgLimY;
        image = ImageIO.read(file);
        imgLimX = image.getWidth();
        imgLimY = image.getHeight();
        composicaoMapa = new int[imgLimX][imgLimY];
        composicaoRuido = new int[imgLimX][imgLimY];
        int contXRuido = 0;
        int contYRuido = 0;
        boolean praiaRuido = false;
        //...................................[x][y]...(invertido)
        for (int[] i : composicaoMapa) {
            for (int j : i) {
                j = 0;
            }
        }
        for (int x = 0; x < imgLimX; x++) {
            contXRuido += x;
            for (int y = 0; y < imgLimX; y++) {
                
                int clr = image.getRGB(y, x);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                //digitaliza o mapa:
                if (red == 105 && green == 255 && blue == 51) {
                    composicaoMapa[x][y] = 2;
                } else if (red == 60 && green == 82 && blue == 255) {
                    composicaoMapa[x][y] = 1;
                } else if (red == 0 && green == 0 && blue == 0) {
                    composicaoMapa[x][y] = 3;
                    praiaRuido = true;
                } else {
                    composicaoMapa[x][y] = 0;
                }
                contXRuido += red;
                contYRuido += y+blue+green;

                //cria o ruido:
                long noiseFactor = (!praiaRuido) ? (contXRuido * contYRuido * x * y) / (red + green + blue) : (contXRuido * y + 1) / (y + x + 1);
                if (noiseFactor * 7 % 69 == 0) {

                    for (int i = x - 10; i <= x + 10; i++) {
                        for (int j = y - 10; j <= y + 10; j++) {
                            try {
                                if (composicaoRuido[i][j] == 1) {
                                    composicaoRuido[i][j] = 2;
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    composicaoRuido[x][y] = 1;
                } else {
                    composicaoRuido[x][y] = 0;
                }
                praiaRuido = false;
            }
        }

        ArrayList<ArrayList<Integer>> rios = new ArrayList();
        for (int i = 0; i < riverLines; i++) {
            rios.add(traceRiverLine(imgLimX, imgLimY, i));
        }
        for (int x = 0; x < imgLimX; x++) {
            for (int y = 0; y < imgLimX; y++) {
                for (ArrayList<Integer> rio : rios) {
                    if (composicaoMapa[x][y] == 7) {
                        if (rio.get(x) != null) {
                            if (rio.get(x) == y) {
                                composicaoMapa[x][y] = 9;
                                if (y > 0) {
                                    composicaoMapa[x][y - 1] = 9;
                                }
                                if (y < imgLimY - 1) {
                                    composicaoMapa[x][y + 1] = 9;
                                }
                            }
                        }
                    }
                    if (composicaoMapa[x][y] == 2) {
                        if (rio.get(x) != null) {
                            if (rio.get(x) == y) {

                                composicaoMapa[x][y] = 7;
                                if (y > 0) {
                                    composicaoMapa[x][y - 1] = 7;
                                }
                                if (y < imgLimY - 1) {
                                    composicaoMapa[x][y + 1] = 7;
                                }
                                if (composicaoMapa[x + 1][y] == 2
                                        && composicaoMapa[x - 1][y] == 2 //&&
                                        //composicaoMapa[x][y+1] == 2 //&&
                                        //composicaoMapa[x][y-1] == 2
                                        ) {
                                    //composicaoMapa[x+1][y] = 3; 
                                    //composicaoMapa[x-1][y] = 3;
                                    try {
                                        composicaoMapa[x][y + 1] = 7;
                                        composicaoMapa[x][y - 1] = 7;
                                        //composicaoMapa[x+1][y+1] = 7;
                                        composicaoMapa[x - 1][y + 1] = 7;
                                        //composicaoMapa[x+1][y-1] = 7;
                                        composicaoMapa[x - 1][y - 1] = 7;
                                        if (composicaoMapa[x][y - 2] == 7) {
                                            composicaoMapa[x][y - 2] = 9;
                                        } else {
                                            composicaoMapa[x][y - 2] = 7;
                                        }
                                        if (composicaoMapa[x][y + 2] == 7) {
                                            composicaoMapa[x][y + 2] = 9;
                                        } else {
                                            composicaoMapa[x][y + 2] = 7;
                                        }
                                        if (composicaoMapa[x][y - 3] == 7) {
                                            composicaoMapa[x][y - 3] = 9;
                                        } else {
                                            composicaoMapa[x][y - 3] = 7;
                                        }
                                        if (composicaoMapa[x][y + 3] == 7) {
                                            composicaoMapa[x][y + 3] = 9;
                                        } else {
                                            composicaoMapa[x][y + 3] = 7;
                                        }
                                        if (composicaoMapa[x][y - 4] == 7) {
                                            composicaoMapa[x][y - 4] = 9;
                                        }
                                        if (composicaoMapa[x][y + 4] == 7) {
                                            composicaoMapa[x][y + 4] = 9;
                                        }if (composicaoMapa[x][y + 4] == 7) {
                                            composicaoMapa[x][y + 4] = 9;
                                        }if (composicaoMapa[x-1][y] == 7) {
                                            composicaoMapa[x-1][y] = 9;
                                        }else composicaoMapa[x - 1][y] = 7;
                                        //composicaoMapa[x+1][y] = 7;
                                        
                                        //composicaoMapa[x+1][y+2] = 7;
                                        //composicaoMapa[x+1][y-2] = 7;
                                        composicaoMapa[x - 1][y + 2] = 7;
                                        composicaoMapa[x - 1][y - 2] = 7;
                                    } catch (Exception e) {

                                    }
                                }
                            }
                        }
                    }
                    if (composicaoMapa[x][y] == 3) {
                        if (rio.get(x) != null) {
                            if (rio.get(x) == y) {
                                composicaoMapa[x][y] = 8;
                                if (y > 0) {
                                    composicaoMapa[x][y - 1] = 8;
                                }
                                if (y < imgLimY - 1) {
                                    composicaoMapa[x][y + 1] = 8;
                                }
                            }
                        }
                    }
                }

                if (composicaoMapa[x][y] == 0); else if (composicaoMapa[x][y] == 2 || composicaoMapa[x][y] == 3) {
                    if (x > 10 && y > 10 && x < 89 && y < 89) {

                    }
                    if (x > 80 || x < 20) {
                        //composicaoMapa[x][y] = 6;
                    } else if (!(x < 5 || x > 94) && !(y < 5 || y > 94)) {
                        if (composicaoMapa[x - 5][y - 5] == 1
                                || composicaoMapa[x + 5][y - 5] == 1
                                || composicaoMapa[x - 5][y + 5] == 1
                                || composicaoMapa[x + 5][y + 5] == 1) {
                            composicaoMapa[x][y] = 5;
                        }
                    }
                    if (composicaoMapa[x][y] == 2) {
                        if (x > 20 && y > 20) {
                            if (composicaoMapa[x - 20][y - 20] == 2);
                            //composicaoMapa[x][y] = 4;
                        }
                    }

                }
            }
        }
        riosColc.addAll(rios);
        try {
            BufferedImage imgMapa = new BufferedImage(imgLimX, imgLimX, BufferedImage.TYPE_INT_RGB);
            BufferedImage imgRuido = new BufferedImage(imgLimX, imgLimX, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < imgLimX; x++) {
                for (int y = 0; y < imgLimX; y++) {
                    int pixel = composicaoMapa[x][y];
                    int ruido = composicaoRuido[x][y];
                    Color paleta;

                    //colore mapa:
                    switch (pixel) {
                        case 2 ->
                            paleta = new Color(105, 255, 51);
                        case 1 ->
                            paleta = new Color(7, 24, 147);
                        case 3 ->
                            paleta = new Color(255, 255, 150);
                        case 4 ->
                            paleta = new Color(43, 132, 11);
                        case 5 ->
                            paleta = new Color(167, 161, 32);
                        //gelo
                        case 6 ->
                            paleta = new Color(170, 251, 255);
                        //rio:
                        case 7 ->
                            paleta = new Color(107, 124, 247);
                        //delta:
                        case 8 ->
                            paleta = new Color(207, 124, 207);
                        //nascente:
                        case 9 ->
                            paleta = new Color(0, 0, 0);
                        default ->
                            paleta = new Color(100, 100, 100);
                    }
                    imgMapa.setRGB(y, x, paleta.getRGB());

                    //colore ruido:
                    switch (ruido) {
                        case 0 ->
                            paleta = new Color(105, 255, 51);
                        case 1 ->
                            paleta = new Color(7, 24, 147);
                        case 2 ->
                            paleta = new Color(145, 50, 50);
                        default ->
                            paleta = new Color(100, 100, 100);
                    }
                    imgRuido.setRGB(y, x, paleta.getRGB());
                }
            }
            File novoMapa = new File("src\\imagens\\krasnodar-novo.png");
            File novoRuido = new File("src\\imagens\\krasnodar-ruido.png");
            ImageIO.write(imgMapa, "png", novoMapa);
            ImageIO.write(imgRuido, "png", novoRuido);
        } catch (IOException ex) {
            Logger.getLogger(Rivers.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">>>");
        // Getting pixel color by position x and y 

    }

    public ArrayList<Integer> traceRiverLine(int maxX, int maxY, int counter) {
        int xA = 0;
        int xB = 0;
        int yA = 0;
        int yB = 0;
        double m = 0;
        double n = 0;
        boolean inside = true;

        ArrayList<Integer> reta = new ArrayList();
        switch (combinacaoRio) {
            case 0:
                xA = randomNumber(maxX - 1, composicaoRuido, counter, 0);
                xB = maxX;
                yA = 0;
                yB = randomNumber(maxY - 1, composicaoRuido, counter, 1) + 1;
                m = (double) (yA - yB) / (xA - xB);
                n = (double) (yB * xA - xB * yA) / (xA - xB);
                while (!vefM(m)) {
                    xA = randomNumber(xA);
                    xB = maxX;
                    yA = 0;
                    yB = randomNumber(maxY - 1, composicaoRuido, counter, 3) + 1;
                    m = (double) (yA - yB) / (xA - xB);
                    n = (double) (yB * xA - xB * yA) / (xA - xB);
                }
                break;
            case 1:
                xB = randomNumber(maxX - 1, composicaoRuido, counter, 0);
                xA = maxX;
                yB = maxY;
                yA = randomNumber(maxY - 1, composicaoRuido, counter, 1) + 1;
                m = (double) (yA - yB) / (xA - xB);
                n = (double) (yB * xA - xB * yA) / (xA - xB);
                while (!vefM(m)) {
                    xB = randomNumber(xB);
                    xA = maxX;
                    yB = maxY;
                    yA = randomNumber(maxY - 1, composicaoRuido, counter, 3) + 1;
                    m = (double) (yA - yB) / (xA - xB);
                    n = (double) (yB * xA - xB * yA) / (xA - xB);
                }
                break;
            case 2:
                xB = randomNumber(maxX - 1, composicaoRuido, counter, 0);
                xA = 0;
                yB = 0;
                yA = randomNumber(maxY - 1, composicaoRuido, counter, 1) + 1;
                m = (double) (yA - yB) / (xA - xB);
                n = (double) (yB * xA - xB * yA) / (xA - xB);
                while (!vefM(m)) {
                    xB = randomNumber(xB);
                    xA = 0;
                    yB = 0;
                    yA = randomNumber(maxY - 1, composicaoRuido, counter, 3) + 1;
                    m = (double) (yA - yB) / (xA - xB);
                    n = (double) (yB * xA - xB * yA) / (xA - xB);
                }
                break;
            case 3:
                xB = randomNumber(maxX - 1, composicaoRuido, counter, 0);
                xA = 0;
                yB = maxY;
                yA = randomNumber(maxY - 1, composicaoRuido, counter, 1) + 1;
                m = (double) (yA - yB) / (xA - xB);
                n = (double) (yB * xA - xB * yA) / (xA - xB);
                while (!vefM(m)) {
                    xB = randomNumber(xB);
                    xA = 0;
                    yB = maxY;
                    yA = randomNumber(maxY - 1, composicaoRuido, counter, 3) + 1;
                    m = (double) (yA - yB) / (xA - xB);
                    n = (double) (yB * xA - xB * yA) / (xA - xB);
                }
                break;
            case 4:
                xB = randomNumber(maxX - 1, composicaoRuido, counter, 0);
                xA = randomNumber(maxX - 1, composicaoRuido, counter, 1);
                while (xA == xB) {
                    xA = randomNumber(xA);
                }
                yB = maxY;
                yA = 0;
                m = (double) (yA - yB) / (xA - xB);
                n = (double) (yB * xA - xB * yA) / (xA - xB);
                while (!vefM(m)) {
                    xB = randomNumber(xB);
                    xA = randomNumber(maxX - 1, composicaoRuido, counter, 3);
                    while (xA == xB) {
                        xA = randomNumber(xA);
                    }
                    yB = maxY;
                    yA = 0;
                    m = (double) (yA - yB) / (xA - xB);
                    n = (double) (yB * xA - xB * yA) / (xA - xB);
                }
                break;
            case 5:
                xB = maxX;
                xA = 0;
                yB = randomNumber(maxY - 1, composicaoRuido, counter, 0) + 1;
                yA = randomNumber(maxY - 1, composicaoRuido, counter, 1) + 1;
                m = (double) (yA - yB) / (xA - xB);
                n = (double) (yB * xA - xB * yA) / (xA - xB);
                while (!vefM(m)) {
                    xB = maxX;
                    xA = 0;
                    yB = randomNumber(yB) + 1;
                    yA = randomNumber(maxY - 1, composicaoRuido, counter, 3) + 1;
                    m = (double) (yA - yB) / (xA - xB);
                    n = (double) (yB * xA - xB * yA) / (xA - xB);
                }
                break;
            default:;
        }

        switch (combinacaoRio) {
            case 0:
                for (int x = 0; x < maxX; x++) {
                    if (x >= xA) {
                        int y = (int) Math.round(m * x + n);
                        reta.add(y);
                    } else {
                        reta.add(null);
                    }
                }
                combinacaoRio = 1;
                System.out.println("|||case0");
                break;
            case 1:
                for (int x = 0; x < maxX; x++) {
                    if (x >= xB) {
                        int y = (int) Math.round(m * x + n);
                        reta.add(y);
                    } else {
                        reta.add(null);
                    }
                }
                combinacaoRio = 2;
                System.out.println("|||case1");
                break;
            case 2:
                for (int x = 0; x < maxX; x++) {
                    if (x <= xB) {
                        int y = (int) Math.round(m * x + n);
                        reta.add(y);
                    } else {
                        reta.add(null);
                    }
                }
                combinacaoRio = 3;
                System.out.println("|||case2");
                break;
            case 3:
                for (int x = 0; x < maxX; x++) {
                    if (x <= xB) {
                        int y = (int) Math.round(m * x + n);
                        reta.add(y);
                    } else {
                        reta.add(null);
                    }
                }
                combinacaoRio = 4;
                System.out.println("|||case3");
                break;
            case 4:
                for (int x = 0; x < maxX; x++) {
                    if ((x >= xA && x <= xB) || (x <= xA && x >= xB)) {
                        int y = (int) Math.round(m * x + n);
                        reta.add(y);
                    } else {
                        reta.add(null);
                    }
                }
                combinacaoRio = 5;
                System.out.println("|||case4");
                break;
            case 5:
                for (int x = 0; x < maxX; x++) {
                    int y = (int) Math.round(m * x + n);
                    reta.add(y);
                }
                combinacaoRio = 0;
                System.out.println("|||case4");
                break;
            default:

        }

        for (int i = 0; i < reta.size(); i++) {
            System.out.println("[" + i + "," + reta.get(i) + "]");
        }
        return reta;
    }

    public int randomNumber(int ref, int[][] noiseMatrix, int band, int version) {
        int xInicial = noiseMatrix.length / riverLines * band;
        int xFinal = xInicial + noiseMatrix.length / riverLines;
        int yInicial = 0;
        int yFinal = 200;
        switch (version) {
            case 1:
                yInicial = noiseMatrix[0].length / 4;
                yFinal = yInicial + noiseMatrix[0].length / 4;
                break;
            case 0:
                yInicial = 0;
                yFinal = noiseMatrix[0].length / 4;
                break;
            case 2:
                yInicial = noiseMatrix[0].length / 4 * 2;
                yFinal = noiseMatrix[0].length / 4 * 3;
                break;
            case 3:
                yInicial = noiseMatrix[0].length / 4 * 3;
                yFinal = noiseMatrix[0].length - 1;
                break;
            default:
                System.out.println("erro");
                break;
        }
        double somatorio = 0;
        int ruido1 = 0, ruido2 = 0, diferenca = 0;

        for (int x = xInicial; x < xFinal; x++) {
            for (int y = yInicial; y < yFinal; y++) {
                if (noiseMatrix[x][y] == 2) {
                    ruido2++;
                } else if (noiseMatrix[x][y] == 1) {
                    if (y % 2 == 0) {
                        diferenca += 10;
                    } else {
                        diferenca -= 10;
                    }
                    ruido1++;
                }
            }
        }
        int aux = (-diferenca > ruido2) ? 0 : ruido2 + diferenca;
        somatorio = (double) aux / (ruido2 + ruido1 * 10);
        int value = (int) Math.round(somatorio * ref);
        int retorno = (int) Math.round(Math.random() * ref);
        return value;
    }

    boolean randomNovamente = false;

    public int randomNumber(int antigo) {
        int novo;
        if (antigo > 0 && !randomNovamente) {
            novo = antigo - 1;
            randomNovamente = false;
        } else {
            novo = antigo + 1;
            randomNovamente = true;
        }
        return novo;
    }

    public boolean vefM(double m) {
        return (m <= 5.5d && m >= -5.5d);
    }
}
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
public class RefPoints {

    ArrayList<PointLocation> referencias = new ArrayList();
    ArrayList<Rio> rios = new ArrayList();
    ArrayList<Serra> serras = new ArrayList();

    public RefPoints() throws IOException {
        Rivers base = new Rivers();
        File file = new File("src\\imagens\\krasnodar-novo.png");
        BufferedImage image;

        /*
        agua = 1
        terra = 2
        praia = 3
         */
        final int imgLimX;
        final int imgLimY;
        int[][] composicaoMapa;

        image = ImageIO.read(file);
        imgLimX = image.getWidth();
        imgLimY = image.getHeight();
        composicaoMapa = new int[imgLimX][imgLimX];

        for (int[] i : composicaoMapa) {
            for (int j : i) {
                j = 0;
            }
        }

        for (int x = 0; x < imgLimX; x++) {
            for (int y = 0; y < imgLimX; y++) {
                int clr = image.getRGB(y, x);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                if (composicaoMapa[x][y] == 0) {
                    if (red == 105 && green == 255 && blue == 51) {
                        //interior
                        composicaoMapa[x][y] = 2;
                    } else if (red == 7 && green == 24 && blue == 147) {
                        //mar
                        composicaoMapa[x][y] = 1;
                    } else if (red == 255 && green == 255 && blue == 150) {
                        //praia
                        composicaoMapa[x][y] = 3;
                    } else if (red == 107 && green == 124 && blue == 247) {
                        //bed
                        composicaoMapa[x][y] = 2;
                    } else if (red == 207 && green == 124 && blue == 207) {
                        Delta d = new Delta(x, y, imgLimX, imgLimY);
                        //delta
                        for (int i = d.xPNO; i <= d.xPSE; i++) {
                            for (int j = d.yPNO; j <= d.yPSE; j++) {
                                try {
                                    composicaoMapa[i][j] = -8;
                                    referencias.add(d);
                                } catch (Exception e) {
                                    System.out.println("==>" + e.getMessage());
                                }
                            }
                        }
                        composicaoMapa[x][y] = 8;
                    } else if (red == 0 && green == 0 && blue == 0) {
                        //nascente
                        Nascente d = new Nascente(x, y, imgLimX, imgLimY);
                        for (int i = d.xPNO; i <= d.xPSE; i++) {
                            for (int j = d.yPNO; j <= d.yPSE; j++) {
                                try {
                                    composicaoMapa[i][j] = -9;
                                    referencias.add(d);
                                } catch (Exception e) {
                                    System.out.println("==>" + e.getMessage());
                                }
                            }
                        }
                        composicaoMapa[x][y] = 9;
                    }
                }
            }
        }

        for (ArrayList<Integer> a : base.riosColc) {
            boolean delta = false, monteA = false;
            boolean nasc = false, monteB = false;
            boolean habilitado = false;
            int habPrev = -1;
            for (int i = 0; i < referencias.size() - 1; i++) {
                PointLocation ponto = referencias.get(i);
                for (int j = ponto.xPNO; j <= ponto.xPSE; j++) {
                    if (habilitado) {
                        break;
                    }
                    for (int k = ponto.yPNO; k <= ponto.yPSE; k++) {
                        if (habilitado) {
                            break;
                        }
                        for (int xRio = 0; xRio < a.size(); xRio++) {
                            if (a.get(xRio) != null) {
                                if (xRio == j && a.get(xRio) == k) {
                                    habilitado = true;
                                    break;
                                }
                            }

                        }
                    }
                }
                if (habilitado) {
                    if (delta) {
                        if (referencias.get(i).getClass() == Nascente.class && habPrev != -1) {
                            Rio r = new Rio(referencias.get(habPrev), referencias.get(i));
                            if(r.comprimentoReta()>10)
                                rios.add(r);
                            else;
                        }
                    } else if (nasc) {
                        if (referencias.get(i).getClass() == Delta.class && habPrev != -1) {
                            Rio r = new Rio(referencias.get(i), referencias.get(habPrev));
                            if(r.comprimentoReta()>10)
                                rios.add(r);
                            else;
                        }
                    }
                    if (referencias.get(i).getClass() == Delta.class) {
                        delta = true;
                        nasc = false;
                    } else if (referencias.get(i).getClass() == Nascente.class) {
                        delta = false;
                        nasc = true;
                    }

                    if (monteA) {
                        if (referencias.get(i).getClass() == Nascente.class && habPrev != -1) {
                            Serra r = new Serra(referencias.get(habPrev), referencias.get(i));
                            if(r.comprimentoReta()>10)
                                serras.add(r);
                            else;
                        }
                    } else if (monteB) {
                        if (referencias.get(i).getClass() == Nascente.class && habPrev != -1) {
                            Serra r = new Serra(referencias.get(i), referencias.get(habPrev));
                            if(r.comprimentoReta()>10)
                                serras.add(r);
                            else;
                        }
                    }
                    if (referencias.get(i).getClass() == Delta.class) {
                        monteA = false;
                        monteB = false;
                    } else if (referencias.get(i).getClass() == Nascente.class && monteA) {
                        monteB = true;
                    } else if (referencias.get(i).getClass() == Nascente.class) {
                        monteA = true;
                    }
                }

                if (habilitado) {
                    habilitado = false;
                    habPrev = i;
                } else {
                }
            }
            habPrev = -1;
        }
        /*
        try {
            BufferedImage img = new BufferedImage(imgLimX, imgLimX, BufferedImage.TYPE_INT_RGB);
            for (PointLocation p : referencias) {
                String pixel = p.getClass().toString();
                Color paleta;
                switch (pixel) {
                    case "Delta" ->
                        paleta = new Color(255, 255, 51);
                    case "Nascente" ->
                        paleta = new Color(255, 24, 255);
                    default ->
                        paleta = new Color(100, 100, 100);
                }
                img.setRGB(p.yCentral, p.xCentral, paleta.getRGB());

            }
            File novoMapa = new File("src\\imagens\\krasnodar-referencias.png");
            ImageIO.write(img, "png", novoMapa);
        } catch (IOException ex) {
            Logger.getLogger(Rivers.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        try {
            BufferedImage img = new BufferedImage(imgLimX, imgLimX, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < serras.size(); i++) {
                printSerraInfo(i);
                Color paleta;
                paleta = new Color(255,255 / serras.size() * (i + 1), 125 - (125 / serras.size() * (i + 1)));
                for (int j = serras.get(i).morroA.xPNO; j < serras.get(i).morroA.xPSE; j++) {
                    for (int k = serras.get(i).morroA.yPNO; k < serras.get(i).morroA.yPSE; k++) {
                        img.setRGB(k, j, paleta.getRGB());
                    }
                }
                for (int j = serras.get(i).morroB.xPNO; j < serras.get(i).morroB.xPSE; j++) {
                    for (int k = serras.get(i).morroB.yPNO; k < serras.get(i).morroB.yPSE; k++) {
                        img.setRGB(k, j, paleta.getRGB());
                    }
                }

            }
            for (int i = 0; i < rios.size(); i++) {
                printRioInfo(i);
                Color paleta;
                paleta = new Color(255 / rios.size() * (i + 1), 255 - (255 / rios.size() * (i + 1)), 255);
                for (int j = rios.get(i).delta.xPNO; j < rios.get(i).delta.xPSE; j++) {
                    for (int k = rios.get(i).delta.yPNO; k < rios.get(i).delta.yPSE; k++) {
                        img.setRGB(k, j, paleta.getRGB());
                    }
                }
                for (int j = rios.get(i).nasc.xPNO; j < rios.get(i).nasc.xPSE; j++) {
                    for (int k = rios.get(i).nasc.yPNO; k < rios.get(i).nasc.yPSE; k++) {
                        img.setRGB(k, j, paleta.getRGB());
                    }
                }

            }
            File novoMapa = new File("src\\imagens\\krasnodar-referencias.png");
            ImageIO.write(img, "png", novoMapa);
        } catch (IOException ex) {
            Logger.getLogger(Rivers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printRioInfo(int indice) {
        Rio alvo = rios.get(indice);
        System.out.println("|-INFORMAÇÕES DO RIO " + (indice + 1) + ":-|");
        System.out.println("|Comprimento em linha reta: " + alvo.comprimentoReta());
        System.out.println("|----------|");
    }
    public void printSerraInfo(int indice) {
        Serra alvo = serras.get(indice);
        System.out.println("|-INFORMAÇÕES DA SERRA " + (indice + 1) + ":-|");
        System.out.println("|Comprimento em linha reta: " + alvo.comprimentoReta());
        System.out.println("|----------|");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

/**
 *
 * @author Ilkka Hiltunen
 */
public class Model {

    private int priorityGain[][];  // Arvioi ruudun "arvon"
    private int priorityThreat[][];
    private int control[][];   // Kuka "omistaa" ruudun, 0 = tyhjä, 1 = risti ja 2 = nolla.

    public Model() {
        // Luodaan ruudukko ja täytetään se tyhjyydellä
        priorityGain = new int[10][10];
        priorityThreat = new int[10][10];
        control = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(i == 5 && j == 5){
                    priorityGain[j][i] = 1;
                }else{
                priorityGain[j][i] = 0;
                }
                priorityThreat[j][i] = 0;
                control[j][i] = 0;
            }
        }
    }

    public void calculateGain() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (control[i][j] == 0) {
                    try {
                        boolean l1, l2, l3, r1, r2, r3; // kolme vasenta ja oikeeta ruutua hallussa?
                        l1 = control[i][j - 1] == 2;
                        if (l1) {
                            priorityGain[i][j] += 5;
                            l2 = control[i][j - 2] == 2;
                            if (l2) {
                                priorityGain[i][j] += 20;
                                l3 = control[i][j - 3] == 2;
                                if (l3) {
                                    priorityGain[i][j] += 10000;
                                }
                            }
                        }
                        r1 = control[i][j + 1] == 2;
                        if (r1) {
                            priorityGain[i][j] += 5;
                            r2 = control[i][j + 2] == 2;
                            if (r2) {
                                priorityGain[i][j] += 20;
                                r3 = control[i][j + 3] == 2;
                                if (r3) {
                                    priorityGain[i][j] += 10000;
                                }
                            }
                        }
                        boolean u1, u2, u3, d1, d2, d3; // Kolme ylä ja alapuolella olevaa ruutu hallussa
                        u1 = control[i - 1][j] == 2;
                        if (u1) {
                            priorityGain[i][j] += 5;
                            u2 = control[i - 2][j] == 2;
                            if (u2) {
                                priorityGain[i][j] += 20;
                                u3 = control[i - 3][j] == 2;
                                if (u3) {
                                    priorityGain[i][j] += 10000;
                                }
                            }
                        }
                        d1 = control[i + 1][j] == 2;
                        if (d1) {
                            priorityGain[i][j] += 5;
                            d2 = control[i + 2][j] == 2;
                            if (d2) {
                                priorityGain[i][j] += 20;
                                d3 = control[i + 3][j] == 2;
                                if (d3) {
                                    priorityGain[i][j] += 10000;
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }
    }
    
    public void calculateThreat() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (control[i][j] == 0) {
                    try {
                        boolean l1, l2, l3, r1, r2, r3; // kolme vasenta ja oikeeta ruutua hallussa?
                        l1 = control[i][j - 1] == 1;
                        if (l1) {
                            priorityThreat[i][j] += 5;
                            l2 = control[i][j - 2] == 1;
                            if (l2) {
                                priorityThreat[i][j] += 50;
                                l3 = control[i][j - 3] == 1;
                                if (l3) {
                                    priorityThreat[i][j] += 500;                                   
                                }
                            }
                        }
                        r1 = control[i][j + 1] == 1;
                        if (r1) {
                            priorityThreat[i][j] += 5;
                            r2 = control[i][j + 2] == 1;
                            if (r2) {
                                priorityThreat[i][j] += 50;
                                r3 = control[i][j + 3] == 1;
                                if (r3) {
                                    priorityThreat[i][j] += 500;
                                }
                            }
                        }
                        boolean u1, u2, u3, d1, d2, d3; // Kolme ylä ja alapuolella olevaa ruutu hallussa
                        u1 = control[i - 1][j] == 1;
                        if (u1) {
                            priorityThreat[i][j] += 5;
                            u2 = control[i - 2][j] == 1;
                            if (u2) {
                                priorityThreat[i][j] += 50;
                                u3 = control[i - 3][j] == 1;
                                if (u3) {
                                    priorityThreat[i][j] += 500;
                                }
                            }
                        }
                        d1 = control[i + 1][j] == 1;
                        if (d1) {
                            priorityThreat[i][j] += 5;
                            d2 = control[i + 2][j] == 1;
                            if (d2) {
                                priorityThreat[i][j] += 50;
                                d3 = control[i + 3][j] == 1;
                                if (d3) {
                                    priorityThreat[i][j] += 500;
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }
    }

    public int getBestMove(){
        int bestMove = 0;
        int move = 0;
        int number = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                move = priorityThreat[i][j] + priorityGain[i][j];
                if (move > bestMove) {
                    bestMove = move;
                    number = i * 10 + j;                    
                }
                priorityThreat[j][i] = 0;
                priorityGain[i][j] = 0;
            }
        }
        return number;
    }

    public void setOwner(int slot, int owner) {
        int b = slot % 10;
        int a = (slot - b) / 10;
        System.out.println(owner + " " + a + " " + b);
        control[a][b] = owner;
    }
}

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
    private int control[][];   // Kuka "omistaa" ruudun, 0 = tyhjä, 1 = risti ja 2 = nolla, 3 = out of bounds.

    public Model() {
        // Luodaan ruudukko ja täytetään se tyhjyydellä
        priorityGain = new int[16][16];
        priorityThreat = new int[16][16];
        control = new int[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (i == 7 && j == 7) {
                    priorityGain[j][i] = 1;
                } else {
                    priorityGain[j][i] = 0;
                }
                priorityThreat[j][i] = 0;
                control[j][i] = 0;
                if((i < 3) || (i > 12) || (j < 3) || (j > 12)){
                    control[j][i] = 3;
                }
            }
        }
    }

    public void calculateGain() {
        for (int i = 3; i < 13; i++) {
            for (int j = 3; j < 13; j++) {
                if (control[i][j] == 0) {
                    try {
                        boolean l1, l2, l3, r1, r2, r3; // kolme vasenta ja oikeeta ruutua hallussa?
                        boolean l1p, l2p, l3p, r1p, r2p, r3p; // kolmen vasemman ja oikean ruudun potenttiaalit
                        l1 = control[i][j - 1] == 2;
                        l2 = control[i][j - 2] == 2;
                        l3 = control[i][j - 3] == 2;
                        r1 = control[i][j + 1] == 2;
                        r2 = control[i][j + 2] == 2;
                        r3 = control[i][j + 3] == 2;
                        l1p = control[i][j - 1] == 0;
                        l2p = control[i][j - 2] == 0;
                        l3p = control[i][j - 3] == 0;
                        r1p = control[i][j + 1] == 0;
                        r2p = control[i][j + 2] == 0;
                        r3p = control[i][j + 3] == 0;
                        if (l1) {
                            priorityGain[i][j] += 1;
                            if (l2) {
                                priorityGain[i][j] += 10;
                                if (l3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if (r1) {
                            priorityGain[i][j] += 1;
                            if (r2) {
                                priorityGain[i][j] += 10;
                                if (r3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if ((r1 && l1) && (r2p || l2p)) {
                            priorityGain[i][j] += 1000;
                            if (r2 || l2) {
                                priorityGain[i][j] += 100000;
                            }
                        } else {
                            // saisko jotenki vähennettyy pois väärät kohat ilman erillistä muuttujaa?
                        }

                        boolean u1, u2, u3, d1, d2, d3; // Kolme ylä ja alapuolella olevaa ruutu hallussa
                        boolean u1p, u2p, u3p, d1p, d2p, d3p; // kolme ylä ja alapuolella olevien ruutujen potenttiaalit
                        u1 = control[i - 1][j] == 2;
                        u2 = control[i - 2][j] == 2;
                        u3 = control[i - 3][j] == 2;
                        d1 = control[i + 1][j] == 2;
                        d2 = control[i + 2][j] == 2;
                        d3 = control[i + 3][j] == 2;
                        u1p = control[i - 1][j] == 0;
                        u2p = control[i - 2][j] == 0;
                        u3p = control[i - 3][j] == 0;
                        d1p = control[i + 1][j] == 0;
                        d2p = control[i + 2][j] == 0;
                        d3p = control[i + 3][j] == 0;
                        if (u1) {
                            priorityGain[i][j] += 1;
                            if (u2) {
                                priorityGain[i][j] += 10;
                                if (u3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if (d1) {
                            priorityGain[i][j] += 1;
                            if (d2) {
                                priorityGain[i][j] += 10;
                                if (d3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if ((u1 && d1) && (u2p || d2p)) {
                            priorityGain[i][j] += 1000;
                            if (u2 || d2) {
                                priorityGain[i][j] += 100000;
                            }
                        } else {
                            //Tähän kanssa vähennys?
                        }
                        boolean l1d1, l1u1, l2d2, l2u2, l3d3, l3u3, r1d1, r1u1, r2d2, r2u2, r3d3, r3u3;
                        boolean l1d1p, l1u1p, r1d1p, r1u1p, l2d2p, l2u2p, r2d2p, r2u2p;
                        l1d1 = control[i + 1][j - 1] == 2;
                        l2d2 = control[i + 2][j - 2] == 2;
                        l3d3 = control[i + 3][j - 3] == 2;
                        l1u1 = control[i - 1][j - 1] == 2;
                        l2u2 = control[i - 2][j - 2] == 2;
                        l3u3 = control[i - 3][j - 3] == 2;
                        r1d1 = control[i + 1][j + 1] == 2;
                        r2d2 = control[i + 2][j + 2] == 2;
                        r3d3 = control[i + 3][j + 3] == 2;
                        r1u1 = control[i - 1][j + 1] == 2;
                        r2u2 = control[i - 2][j + 2] == 2;
                        r3u3 = control[i - 3][j + 3] == 2;
                        l1d1p = control[i + 1][j - 1] == 0;
                        l1u1p = control[i - 1][j - 1] == 0;
                        r1d1p = control[i + 1][j + 1] == 0;
                        r1u1p = control[i - 1][j + 1] == 0;
                        l2d2p = control[i + 2][j - 2] == 0;
                        l2u2p = control[i - 2][j - 2] == 0;
                        r2d2p = control[i + 2][j + 2] == 0;
                        r2u2p = control[i - 2][j + 2] == 0;
                        if (l1d1) {
                            priorityGain[i][j] += 1;
                            if (l2d2) {
                                priorityGain[i][j] += 10;
                                if (l3d3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if (l1u1) {
                            priorityGain[i][j] += 1;
                            if (l2u2) {
                                priorityGain[i][j] += 10;
                                if (l3u3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if (r1d1) {
                            priorityGain[i][j] += 1;
                            if (r2d2) {
                                priorityGain[i][j] += 10;
                                if (r3d3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if (r1u1) {
                            priorityGain[i][j] += 1;
                            if (r2u2) {
                                priorityGain[i][j] += 10;
                                if (r3u3) {
                                    priorityGain[i][j] += 100000;
                                }
                            }
                        }
                        if ((l1d1 && r1u1) && (l2d2p || r2u2p)) {
                            priorityGain[i][j] += 1000;
                            if (l2d2 || r2u2) {
                                priorityGain[i][j] += 100000;
                            }
                        }

                        if ((l1u1 && r1d1) && (l2u2p || r2d2p)) {
                            priorityGain[i][j] += 1000;
                            if (l2u2 || r2d2) {
                                priorityGain[i][j] += 100000;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }
    }

    public void calculateThreat() {
        for (int i = 3; i < 13; i++) {
            for (int j = 3; j < 13; j++) {
                if (control[i][j] == 0) {
                    try {
                        boolean l1, l2, l3, r1, r2, r3; // kolme vasenta ja oikeeta ruutua hallussa?
                        boolean l1p, l2p, l3p, r1p, r2p, r3p; // kolmen vasemman ja oikean ruudun potenttiaalit
                        l1 = control[i][j - 1] == 1;
                        l2 = control[i][j - 2] == 1;
                        l3 = control[i][j - 3] == 1;
                        r1 = control[i][j + 1] == 1;
                        r2 = control[i][j + 2] == 1;
                        r3 = control[i][j + 3] == 1;
                        l1p = control[i][j - 1] == 0;
                        l2p = control[i][j - 2] == 0;
                        l3p = control[i][j - 3] == 0;
                        r1p = control[i][j + 1] == 0;
                        r2p = control[i][j + 2] == 0;
                        r3p = control[i][j + 3] == 0;
                        if (l1) {
                            priorityThreat[i][j] += 1;
                            if (l2) {
                                priorityThreat[i][j] += 10;
                                if (l3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if (r1) {
                            priorityThreat[i][j] += 1;
                            if (r2) {
                                priorityThreat[i][j] += 10;
                                if (r3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if ((r1 && l1) && (r2p || l2p)) {
                            priorityThreat[i][j] += 1000;
                            if (r2 || l2) {
                                priorityThreat[i][j] += 10000;
                            }
                        } else {
                            // saisko jotenki vähennettyy pois väärät kohat ilman erillistä muuttujaa?
                        }

                        boolean u1, u2, u3, d1, d2, d3; // Kolme ylä ja alapuolella olevaa ruutu hallussa
                        boolean u1p, u2p, u3p, d1p, d2p, d3p; // kolme ylä ja alapuolella olevien ruutujen potenttiaalit
                        u1 = control[i - 1][j] == 2;
                        u2 = control[i - 2][j] == 2;
                        u3 = control[i - 3][j] == 2;
                        d1 = control[i + 1][j] == 2;
                        d2 = control[i + 2][j] == 2;
                        d3 = control[i + 3][j] == 2;
                        u1p = control[i - 1][j] == 0;
                        u2p = control[i - 2][j] == 0;
                        u3p = control[i - 3][j] == 0;
                        d1p = control[i + 1][j] == 0;
                        d2p = control[i + 2][j] == 0;
                        d3p = control[i + 3][j] == 0;
                        if (u1) {
                            priorityThreat[i][j] += 1;
                            if (u2) {
                                priorityThreat[i][j] += 10;
                                if (u3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if (d1) {
                            priorityThreat[i][j] += 1;
                            if (d2) {
                                priorityThreat[i][j] += 10;
                                if (d3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if ((u1 && d1) && (u2p || d2p)) {
                            priorityThreat[i][j] += 1000;
                            if (u2 || d2) {
                                priorityThreat[i][j] += 10000;
                            }
                        } else {
                            //Tähän kanssa vähennys?
                        }
                        boolean l1d1, l1u1, l2d2, l2u2, l3d3, l3u3, r1d1, r1u1, r2d2, r2u2, r3d3, r3u3;
                        boolean l1d1p, l1u1p, r1d1p, r1u1p, l2d2p, l2u2p, r2d2p, r2u2p;
                        l1d1 = control[i + 1][j - 1] == 2;
                        l2d2 = control[i + 2][j - 2] == 2;
                        l3d3 = control[i + 3][j - 3] == 2;
                        l1u1 = control[i - 1][j - 1] == 2;
                        l2u2 = control[i - 2][j - 2] == 2;
                        l3u3 = control[i - 3][j - 3] == 2;
                        r1d1 = control[i + 1][j + 1] == 2;
                        r2d2 = control[i + 2][j + 2] == 2;
                        r3d3 = control[i + 3][j + 3] == 2;
                        r1u1 = control[i - 1][j + 1] == 2;
                        r2u2 = control[i - 2][j + 2] == 2;
                        r3u3 = control[i - 3][j + 3] == 2;
                        l1d1p = control[i + 1][j - 1] == 0;
                        l1u1p = control[i - 1][j - 1] == 0;
                        r1d1p = control[i + 1][j + 1] == 0;
                        r1u1p = control[i - 1][j + 1] == 0;
                        l2d2p = control[i + 2][j - 2] == 0;
                        l2u2p = control[i - 2][j - 2] == 0;
                        r2d2p = control[i + 2][j + 2] == 0;
                        r2u2p = control[i - 2][j + 2] == 0;
                        if (l1d1) {
                            priorityThreat[i][j] += 1;
                            if (l2d2) {
                                priorityThreat[i][j] += 10;
                                if (l3d3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if (l1u1) {
                            priorityThreat[i][j] += 1;
                            if (l2u2) {
                                priorityThreat[i][j] += 10;
                                if (l3u3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if (r1d1) {
                            priorityThreat[i][j] += 1;
                            if (r2d2) {
                                priorityThreat[i][j] += 10;
                                if (r3d3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if (r1u1) {
                            priorityThreat[i][j] += 1;
                            if (r2u2) {
                                priorityThreat[i][j] += 10;
                                if (r3u3) {
                                    priorityThreat[i][j] += 10000;
                                }
                            }
                        }
                        if ((l1d1 && r1u1) && (l2d2p || r2u2p)) {
                            priorityThreat[i][j] += 1000;
                            if (l2d2 || r2u2) {
                                priorityThreat[i][j] += 10000;
                            }
                        }

                        if ((l1u1 && r1d1) && (l2u2p || r2d2p)) {
                            priorityThreat[i][j] += 1000;
                            if (l2u2 || r2d2) {
                                priorityThreat[i][j] += 10000;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {                       
                    }
                }
            }
        }
    }

    public int getBestMove() {
        int bestMove = 0;
        int move = 0;
        int number = 0;
        for (int i = 3; i < 13; i++) {
            for (int j = 3; j < 13; j++) {
                move = priorityThreat[i][j] + priorityGain[i][j];
                if (move > bestMove) {
                    bestMove = move;
                    number = (i - 3) * 10 + (j - 3);
                }
                priorityThreat[i][j] = 0;
                priorityGain[i][j] = 0;
            }
        }
        System.out.println("ai paras liike:" + number);
        return number;
    }

    public void setOwner(int slot, int owner) {
        int b = slot % 10;
        int a = (slot - b) / 10;
        System.out.println(owner + " " + a + " " + b);
        control[a+3][b+3] = owner;
    }
}

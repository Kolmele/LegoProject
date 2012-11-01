/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

/**
 *
 * @author Ilkka Hiltunen
 */
public class Controller {

    private GUI gui;
    private Model model;

    public Controller(GUI inGUI, Model inModel) {
        gui = inGUI;
        model = inModel;
    }

    public static void main(String args[]) {
        Model m = new Model();
        GUI g = new GUI();
        Controller c = new Controller(g, m);
        g.registerController(c);
    }

    public int getAiMove() {
        model.calculateGain();
        model.calculateThreat();
        return model.getBestMove();
    }

    public void makeMove(int slot, int owner) {
        model.setOwner(slot, owner);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JUEGO;

import javax.swing.JButton;

/**
 *
 * @author Josue Gavidia
 */
public class cartita extends JButton {

    private String takenBy;
    private int team;
    private int row, col;
    private boolean Checked;
    private boolean alreadySequenced;

    public cartita(int row, int col) {
        takenBy = "";
        this.row = row;
        this.col = col;
        Checked = false;
        alreadySequenced = false;
    }

    public void claimCard(String name, int team) {
        this.takenBy = name;
        this.team = team;
        this.Checked = true;

    }

    public String getTakenBy() {
        return takenBy;
    }

    public int getTeam() {
        return team;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean Checked) {
        this.Checked = Checked;
    }

    public boolean isAlreadySequenced() {
        return alreadySequenced;
    }

    public void setAlreadySequenced(boolean alreadySequenced) {
        this.alreadySequenced = alreadySequenced;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JUEGO;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Josue Gavidia
 */
public class boardManager {

    private cartita[][] lol = new cartita[10][10];
    String[][] cartas = {
        {"corner", "10S", "QS", "KS", "AS", "2D", "3D", "4D", "5D", "corner",},
        {"9S", "10H", "9H", "8H", "7H", "6H", "5H", "4H", "3H", "6H",},
        {"8S", "QH", "7D", "8D", "9D", "10D", "QD", "KD", "2H", "7D",},
        {"7S", "KH", "6D", "2C", "AH", "KH", "QH", "AD", "2S", "8D",},
        {"6S", "AH", "5D", "3C", "4H", "3H", "10H", "AC", "3S", "9D",},
        {"5S", "2C", "4D", "4S", "5H", "2H", "9H", "KS", "4S", "10D",},
        {"4S", "3C", "3D", "5C", "6H", "7H", "8H", "QC", "5S", "QD",},
        {"3S", "4C", "2D", "6C", "7C", "8C", "9C", "10C", "6S", "KD",},
        {"2S", "5C", "AS", "KS", "QS", "10S", "9S", "8S", "7S", "AD",},
        {"corner", "6C", "7C", "8C", "9C", "10C", "QC", "KC", "AC", "corner",}};

    public void initBoard(JPanel pan) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                lol[row][col] = new cartita(row, col);

                lol[row][col].setBounds((pan.getWidth() / 10) * col, (pan.getHeight() / 10) * row, (pan.getWidth() / 10), (pan.getHeight() / 10));

                lol[row][col].setText(null);

                try {
                    //RESIZER DE IMAGENES
                    String imagePath = "CARDS/" + cartas[row][col] + ".png";
                    ImageIcon neocard = new ImageIcon(imagePath);
                    Image Scalecard = neocard.getImage().getScaledInstance(lol[row][col].getWidth(), lol[row][col].getHeight(), Image.SCALE_SMOOTH);
                    neocard = new ImageIcon(Scalecard);
                    lol[row][col].setIcon(neocard);

                } catch (Exception e) {
                    System.out.println("Muere aqui" + row + " " + col);
                    System.out.println(e.getMessage());
                }

                //ACTION LISTENER DE LOS BOTONES
                lol[row][col].addActionListener((ActionEvent e) -> {
                    int Fila = ((cartita) e.getSource()).getRow();
                    int Columna = ((cartita) e.getSource()).getCol();

                    this.takeCard(Fila, Columna);

                    //SWITCH USUARIO EN TURNO.GET TEAM, CAMBIA EL COLOR DE LA FICHA
                    ImageIcon neoFicha = new ImageIcon("CARDS/redToken.png");
                    Image Scalecard = neoFicha.getImage().getScaledInstance(lol[Fila][Columna].getWidth(), lol[Fila][Columna].getHeight(), Image.SCALE_SMOOTH);
                    neoFicha = new ImageIcon(Scalecard);
                    lol[Fila][Columna].getFichita().setIcon(neoFicha);
                });

                pan.add(lol[row][col]);

            }
        }
    }

    private void takeCard(int row, int col) {
        if (lol[row][col].getTakenBy().equals("")) {
            lol[row][col].claimCard("a", 1);

            System.out.println(lol[row][col].getTakenBy());

        }

    }

    public boolean SequenceFinder() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (lol[row][col].isChecked()) {

                    if (this.checkXnY(row, col)||this.checkDiagonal(row, col)) {
                        JOptionPane.showMessageDialog(null, "SECUENCIA ENCONTRADA");
                        return true;
                    }

                } 
            }
        }

        System.out.println("Not found");
        return false;
    }

    private boolean checkXnY(int row, int col) {
        int contHorizontal = 0, contVertical = 0;

        for (int i = 0; i < 5; i++) {
            if (validPos(row, col + i) && lol[row][col + i].isChecked()) {

                contHorizontal++;

            } else {
                contHorizontal = 0;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (validPos(row, col - i) && lol[row][col - i].isChecked()) {

                contHorizontal++;

            } else {
                contHorizontal = 0;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (validPos(row + i, col) && lol[row + i][col].isChecked()) {

                contVertical++;

            } else {
                contVertical = 0;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (validPos(row - i, col) && lol[row - i][col].isChecked()) {

                contVertical++;

            } else {
                contVertical = 0;
            }
        }

        return contHorizontal == 5 || contVertical == 5;
    }

    private boolean checkDiagonal(int row, int col) {
        int NE = 0, SW = 0, NW = 0, SE = 0;

        //Diag North East 
        for (int i = 0; i < 5; i++) {
            if (validPos(row + i, col + i) && lol[row + i][col + i].isChecked()) {

                NE++;

            } else {
                NE = 0;
            }
        }
        //Diag South West
        for (int i = 0; i < 5; i++) {
            if (validPos(row - i, col - i) && lol[row - i][col - i].isChecked()) {

                SW++;

            } else {
                SW = 0;
            }
        }

        // Diag North West
        for (int i = 0; i < 5; i++) {
            if (validPos(row + i, col - i) && lol[row + i][col - i].isChecked()) {
                NW++;
            } else {
                NW = 0;
            }
        }

        // Diag South East
        for (int i = 0; i < 5; i++) {
            if (validPos(row - i, col + i) && lol[row - i][col + i].isChecked()) {
                SE++;
            } else {
                SE = 0;
            }
        }

        return NE == 5 || SW == 5 || NW == 5 || SE == 5;

    }

    private boolean validPos(int row, int col) {
        return row >= 0 && row < lol.length && col >= 0 && col < lol.length;
    }

}

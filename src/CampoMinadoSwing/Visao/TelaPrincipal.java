package CampoMinadoSwing.Visao;

import CampoMinadoSwing.Modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        Tabuleiro tabuleiro = new Tabuleiro(16,30 ,40);

        add(new PainelTabuleiro(tabuleiro));

        setLocationRelativeTo(null);
        setSize(690,438);
        setTitle("Campo Minado");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        new TelaPrincipal();

    }
}

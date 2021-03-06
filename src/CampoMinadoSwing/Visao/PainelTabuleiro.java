package CampoMinadoSwing.Visao;

import CampoMinadoSwing.Modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro) {

        setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));

        tabuleiro.paraCada(c -> add(new BotaoCampo(c)));

        tabuleiro.registrarObservador(e -> {

            SwingUtilities.invokeLater(() -> {

                if(e.isGanhou()){
                    JOptionPane.showMessageDialog(this,"GANHOU AMIGAO:)");
                } else {
                    JOptionPane.showMessageDialog(this,"PERDEU AMIGAO:(");
                }
                tabuleiro.reiniciar();
            });
        });

    }
}

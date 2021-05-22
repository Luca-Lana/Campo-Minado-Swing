package CampoMinadoSwing.Visao;

import CampoMinadoSwing.Modelo.Campo;
import CampoMinadoSwing.Modelo.CampoEvento;
import CampoMinadoSwing.Modelo.CampoObservador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObservador , MouseListener {

    private Campo campo;

    private final Color BG_PADRAO = new Color(184,184,184);
    private final Color BG_MARCAR = new Color(88,179,247);
    private final Color BG_EXPLODIR = new Color(189,66,68);
    private final Color TEXTO_VERDE = new Color(0,100,0);

    public BotaoCampo(Campo campo) {
        this.campo = campo;
        setBackground(BG_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));
        campo.registrarObservadores(this);
        addMouseListener(this);
    }


    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento){
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
        }

    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");

    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCAR);
        setForeground(Color.BLACK);
        setText("M");
    }

    private void aplicarEstiloAbrir() {

        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if(campo.isMinado()){
            setBackground(BG_EXPLODIR);
            return;
        }

        setBackground(BG_PADRAO);

       switch (campo.minasNaVizinhaca()){
           case 1:
               setForeground(TEXTO_VERDE);
               break;
           case 2:
               setForeground(Color.BLUE);
               break;
           case 3:
               setForeground(Color.YELLOW);
               break;
           case 4:
           case 5:
           case 6:
               setForeground(Color.RED);
               break;
           default:
               setForeground(Color.PINK);
       }

        String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhaca() + "" : "";
        setText(valor);

    }

// EVENTOS DO MOUSE

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            campo.abrir();
        } else{
            campo.alternarMarcaçao();
        }
    }

    public void mouseClicked(MouseEvent e) {    }
    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) {  }
}

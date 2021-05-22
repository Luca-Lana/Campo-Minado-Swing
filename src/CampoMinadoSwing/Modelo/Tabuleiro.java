package CampoMinadoSwing.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObservador {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<Boolean>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        
        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void registrarObservador(Consumer<Boolean> observador){
        observadores.add(observador);
    }

    private void notificarObservadores(boolean resultado){
        observadores.stream().forEach(o -> o.accept(resultado));
    }

    public void abrir(int linhas , int colunas){
        campos.stream()
                .filter(c -> c.getLinha() == linhas && c.getColuna() == colunas)
                .findFirst()
                .ifPresent(Campo::abrir);
    }

     public void alternarMarcacao(int linhas , int colunas){
        campos.stream()
                .filter(c -> c.getLinha() == linhas && c.getColuna() == colunas)
                .findFirst()
                .ifPresent(Campo::alternarMarcaçao);
    }


    private void gerarCampos() {
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas ; c++) {
                Campo campo = new Campo(l,c);
                campo.registrarObservadores(this);
                campos.add(campo);
            }
        }

    }

    private void associarVizinhos() {
        for(Campo c1 : campos){
            for (Campo c2 : campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        do{
            int aleatorio = (int) (Math.random()*campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(c -> c.isMinado()).count();
        }while(minasArmadas<minas);
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if(evento == CampoEvento.EXPLODIR){
            mostrarMinas();
            notificarObservadores(false);
        } else if (objetivoAlcancado()){
            notificarObservadores(true);
        }
    }

    private void mostrarMinas(){
        campos.stream()
                .filter(Campo::isMinado)
                .forEach(c -> c.setAberto(true));
    }

}

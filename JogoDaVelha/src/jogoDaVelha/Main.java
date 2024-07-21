package jogoDaVelha;

import entradaDados.Console;
import armazenamento.DadosArray;
import java.io.FileWriter;
import java.util.ArrayList;
import armazenamento.GerenciaJogadoresArquivo;
import armazenamento.GerenciaJogadoresArrayList;

/**
 * Classe main que inicia o jogo e o menu inicial de opções.
 * @version 0.8
 * @author Mateus de Oliveira Lopes e Yan Gabriel Reis
 */
public class Main extends Console {
    /**
     * Método principal do programa.
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        GerenciaJogadoresArquivo gerencia = new GerenciaJogadoresArquivo("dadosArmazenados.txt");
        GerenciaJogadoresArrayList gerenciaArray = new GerenciaJogadoresArrayList();
        FileWriter fw = gerencia.criarArquivo(true);
        boolean isJogando = true;
        int modoJogo = 0; //Funcionalidade Extra: essa variavel é usada para definir se o modo de jogo vai ser o tradicional ou o modificado.
        int escolhaMenu;
        ArrayList<DadosArray> array = new ArrayList<>();

        if(isJogando){
            do{
                escolhaMenu = Menu();
                switch (escolhaMenu) {
                    case 1: // jogar
                        modoJogo = escolhaModoJogo(modoJogo); // Aqui é salvo em modoJogo o modo de jogo escolhido.
                        System.out.println("Modo de Jogo Escolhido: " + modoJogo);
                        isJogando = true;
                        break;
                    case 2: // leaderboard
                        array = new ArrayList<>();
                        gerenciaArray.atualizarJogador(array);
                        gerenciaArray.printarPontosGerais(array);
                        isJogando = false;
                        break;
                    case 3: // sair
                        isJogando = false;
                        break;
                    default:
                        isJogando = false;
                        System.out.println("Escolha inválida!\nPor favor, escolha uma das opções abaixo:");
                        break;
                }
                Jogador j1 = new Jogador("X", null);
                Jogador j2 = new Jogador("O", null);

                if(isJogando){
                    j1.setNome(escolherNome('X'));
                    j2.setNome(escolherNome('O'));
                }

                while (isJogando)
                {
                    jogo.jogando(j1, j2, modoJogo);
                    jogo.mostrarPontuacao(j1,j2);
                    char escolha = jogarNovamente();
                    isJogando = continuar(jogo, escolha, j1, j2);
                    if(!isJogando) {
                        gerencia.adicionarJogador(j1.getNome(), j1.getPontos(), fw);
                        gerencia.adicionarJogador(j2.getNome(), j2.getPontos(), fw);
                        array = new ArrayList<>();
                        gerenciaArray.atualizarJogador(array);
                    }
                }
            }while(escolhaMenu != 3);
        }
        System.out.println("Fim do programa");
    }
}

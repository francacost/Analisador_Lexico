package com.lexical;

import java.nio.file.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        try {
            String codigo = Files.readString(Paths.get("src/main/resources/codigo.txt"));

            AnalisadorLexico analisador = new AnalisadorLexico();
            List<Token> tokens = analisador.analisar(codigo);

            System.out.println("Lista de Tokens:");
            for (Token token : tokens) {
                System.out.println(token);
            }

            analisador.getTabelaSimbolos().exibirTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.lexical;

import java.util.*;
import java.util.regex.*;

public class AnalisadorLexico {
    private static final String[] PALAVRAS_CHAVE = {"int", "float", "if", "else", "while", "return"};
    private static final String OPERADORES = "[+\\-*/=<>!&|]";
    private static final String DELIMITADORES = "[;,.(){}\\[\\]]";
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String NUMERO = "\\d+(\\.\\d+)?";

    private TabelaSimbolos tabelaSimbolos;

    public AnalisadorLexico() {
        this.tabelaSimbolos = new TabelaSimbolos();
    }

    public List<Token> analisar(String codigo) {
        List<Token> tokens = new ArrayList<>();
        String[] linhas = codigo.split("\n");

        for (String linha : linhas) {
            linha = linha.trim();

            // Ignorar comentários
            if (linha.startsWith("//")) continue;
            linha = linha.replaceAll("/\\*.*?\\*/", ""); // Remover comentários de bloco

            Matcher matcher;

            while (!linha.isEmpty()) {
                // Palavras-chave
                for (String palavra : PALAVRAS_CHAVE) {
                    if (linha.startsWith(palavra + " ")) {
                        tokens.add(new Token("Palavra-chave", palavra));
                        linha = linha.substring(palavra.length()).trim();
                        continue;
                    }
                }

                // Identificadores
                matcher = Pattern.compile(IDENTIFICADOR).matcher(linha);
                if (matcher.find() && matcher.start() == 0) {
                    String identificador = matcher.group();
                    tokens.add(new Token("Identificador", identificador));
                    tabelaSimbolos.adicionarSimbolo(identificador, "Desconhecido"); // Atualize o tipo no contexto semântico
                    linha = linha.substring(identificador.length()).trim();
                    continue;
                }

                // Números
                matcher = Pattern.compile(NUMERO).matcher(linha);
                if (matcher.find() && matcher.start() == 0) {
                    tokens.add(new Token("Número", matcher.group()));
                    linha = linha.substring(matcher.group().length()).trim();
                    continue;
                }

                // Operadores
                matcher = Pattern.compile(OPERADORES).matcher(linha);
                if (matcher.find() && matcher.start() == 0) {
                    tokens.add(new Token("Operador", matcher.group()));
                    linha = linha.substring(matcher.group().length()).trim();
                    continue;
                }

                // Delimitadores
                matcher = Pattern.compile(DELIMITADORES).matcher(linha);
                if (matcher.find() && matcher.start() == 0) {
                    tokens.add(new Token("Delimitador", matcher.group()));
                    linha = linha.substring(matcher.group().length()).trim();
                    continue;
                }

                // Caso nenhum token seja identificado
                throw new RuntimeException("Token inválido encontrado: " + linha);
            }
        }

        return tokens;
    }

    public TabelaSimbolos getTabelaSimbolos() {
        return tabelaSimbolos;
    }
}


package com.lexical;

import java.util.*;
import java.util.regex.*;

public class AnalisadorLexico {
    private static final Set<String> PALAVRAS_CHAVE = new HashSet<>(Arrays.asList("int", "float", "if", "else", "while", "return"));
    private static final String OPERADORES = "[+\\-*/=<>!&|]";
    private static final String DELIMITADORES = "[;,.(){}\\[\\]]";
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String NUMERO = "\\d+(\\.\\d+)?";
    private static final String STRING_LITERAL = "\"(\\\\.|[^\"\\\\])*\""; // Suporte para strings
    private static final String CHAR_LITERAL = "'(\\\\.|[^'\\\\])'"; // Suporte para caracteres

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
            linha = removerComentarios(linha);

            while (!linha.isEmpty()) {
                Token token = identificarToken(linha);
                if (token != null) {
                    tokens.add(token);
                    linha = linha.substring(token.getValor().length()).trim();
                } else {
                    throw new LexicalException("Token inválido encontrado: " + linha);
                }
            }
        }

        return tokens;
    }

    private String removerComentarios(String linha) {
        if (linha.startsWith("//")) return "";
        return linha.replaceAll("/\\*.*?\\*/", "");
    }

    private Token identificarToken(String linha) {
        // Literais de string
        Matcher matcher = Pattern.compile(STRING_LITERAL).matcher(linha);
        if (matcher.find() && matcher.start() == 0) {
            return new Token("String", matcher.group());
        }

        // Literais de caractere
        matcher = Pattern.compile(CHAR_LITERAL).matcher(linha);
        if (matcher.find() && matcher.start() == 0) {
            return new Token("Char", matcher.group());
        }

        // Palavras-chave
        for (String palavra : PALAVRAS_CHAVE) {
            if (linha.startsWith(palavra + " ")) {
                return new Token("Palavra-chave", palavra);
            }
        }

        // Identificadores
        matcher = Pattern.compile(IDENTIFICADOR).matcher(linha);
        if (matcher.find() && matcher.start() == 0) {
            String identificador = matcher.group();
            tabelaSimbolos.adicionarSimbolo(identificador, "Desconhecido");
            return new Token("Identificador", identificador);
        }

        // Números
        matcher = Pattern.compile(NUMERO).matcher(linha);
        if (matcher.find() && matcher.start() == 0) {
            return new Token("Número", matcher.group());
        }

        // Operadores
        matcher = Pattern.compile(OPERADORES).matcher(linha);
        if (matcher.find() && matcher.start() == 0) {
            return new Token("Operador", matcher.group());
        }

        // Delimitadores
        matcher = Pattern.compile(DELIMITADORES).matcher(linha);
        if (matcher.find() && matcher.start() == 0) {
            return new Token("Delimitador", matcher.group());
        }

        return null;
    }

    public TabelaSimbolos getTabelaSimbolos() {
        return tabelaSimbolos;
    }
}

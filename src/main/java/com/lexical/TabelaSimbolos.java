package com.lexical;

import java.util.LinkedHashMap;
import java.util.Map;

public class TabelaSimbolos {
    private Map<String, Simbolo> tabela;
    private int contadorIdentificadores; // Contador para identificar numericamente

    public TabelaSimbolos() {
        this.tabela = new LinkedHashMap<>(); // Mantém a ordem de inserção
        this.contadorIdentificadores = 1; // Inicia com 1
    }

    public void adicionarSimbolo(String nome, String tipo) {
        if (!tabela.containsKey(nome)) {
            String tipoNumerado = tipo.equals("Desconhecido") ? "Identificador " + contadorIdentificadores++ : tipo;
            tabela.put(nome, new Simbolo(nome, tipoNumerado));
        }
    }

    public Simbolo buscarSimbolo(String nome) {
        return tabela.get(nome);
    }

    public void exibirTabela() {
        System.out.println("Tabela de Símbolos:");
        for (Simbolo simbolo : tabela.values()) {
            System.out.println(simbolo);
        }
    }

    private static class Simbolo {
        private String nome;
        private String tipo;

        public Simbolo(String nome, String tipo) {
            this.nome = nome;
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return "Simbolo{" + "nome='" + nome + '\'' + ", tipo='" + tipo + '\'' + '}';
        }
    }
}

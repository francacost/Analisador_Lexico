package com.lexical;

import java.util.HashMap;
import java.util.Map;

public class TabelaSimbolos {
    private Map<String, Simbolo> tabela;

    public TabelaSimbolos() {
        this.tabela = new HashMap<>();
    }

    public void adicionarSimbolo(String nome, String tipo) {
        if (!tabela.containsKey(nome)) {
            tabela.put(nome, new Simbolo(nome, tipo));
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


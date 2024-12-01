package com.lexical;

public class LexicalException extends RuntimeException {
    public LexicalException(String mensagem) {
        super(mensagem); // Chama o construtor da classe pai (RuntimeException) com a mensagem
    }
}


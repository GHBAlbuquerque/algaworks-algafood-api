package com.algaworks.algafood.domain.enums;

import com.algaworks.algafood.domain.exception.NegocioException;
import groovy.transform.ToString;

import java.util.Arrays;
import java.util.stream.Collectors;

@ToString
public enum StatusPedidoEnum {
    CRIADO,
    CONFIRMADO,
    ENTREGUE,
    CANCELADO;

    public static StatusPedidoEnum getFromName(String name) {
        for (var value : StatusPedidoEnum.values()) {
            if (value.toString().equals(name)) {
                return value;
            }
        }

        throw new NegocioException(String.format("Status do pedido não está de acordo com o valores existentes. " +
                "Use os valores %s.", getAllAsString()));
    }

    public static String getAllAsString() {
        return Arrays.stream(values()).map(Enum::toString).collect(Collectors.joining(", "));
    }
}

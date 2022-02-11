package com.algaworks.algafood.domain.enums;

import com.algaworks.algafood.domain.exception.NegocioException;
import groovy.transform.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public enum StatusPedidoEnum {
    CRIADO,
    CONFIRMADO(CRIADO),
    ENTREGUE(CONFIRMADO),
    CANCELADO(CRIADO, CONFIRMADO);

    private List<StatusPedidoEnum> statusAnteriores;

    StatusPedidoEnum(StatusPedidoEnum... statusAnteriores) {
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public static StatusPedidoEnum traduzir(String name) {
        for (var value : StatusPedidoEnum.values()) {
            if (value.toString().equals(name)) {
                return value;
            }
        }

        throw new NegocioException(String.format("Status do pedido não está de acordo com o valores existentes. " +
                "Use os valores %s.", listarNomes()));
    }

    public static String listarNomes() {
        return Arrays.stream(values()).map(Enum::toString).collect(Collectors.joining(", "));
    }

    public boolean podeSerAlterado(StatusPedidoEnum novoStatus) {
        return novoStatus.statusAnteriores.contains(this);
    }
}

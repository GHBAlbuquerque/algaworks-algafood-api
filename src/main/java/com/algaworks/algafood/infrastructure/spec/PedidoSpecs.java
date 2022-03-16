package com.algaworks.algafood.infrastructure.spec;

import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, criteriaBuilder) -> {
            // evitando múltiplos selects (n+1)
            if (Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();

            if (ObjectUtils.isNotEmpty(filtro.getClienteId())) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if (ObjectUtils.isNotEmpty(filtro.getDataCriacaoInicio())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
            }

            if (ObjectUtils.isNotEmpty(filtro.getDataCriacaoFim())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
            }

            if (ObjectUtils.isNotEmpty(filtro.getRestauranteId())) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if (ObjectUtils.isNotEmpty(filtro.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filtro.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

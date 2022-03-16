package com.algaworks.algafood.infrastructure.impl.report;

import com.algaworks.algafood.domain.enums.StatusPedidoEnum;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.view.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaDiariaQueryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendaDiariaQueryServiceImpl implements VendaDiariaQueryService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        //cria uma funcao a ser executada no banco de dados (no caso, de converter a data para o TimeZone correto)
        //final:  date(convert_tz(data_criacao, '+00:00', '-03:00'))
        var convertTZDataCriacao =
                builder.function("convert_tz", Date.class, root.get("dataCriacao"),
                        builder.literal("+00:00"), builder.literal("-03:00"));

        //cria uma funcao a ser executada no banco de dados (no caso, de truncar a data para aaaa-MM-dd)
        var truncateDataCriacao =
                builder.function("date", Date.class, convertTZDataCriacao);


        var selection = builder.construct(VendaDiaria.class,
                truncateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(truncateDataCriacao);

        var predicates = criarPredicates(builder, root, filtro);
        query.where(predicates);

        return entityManager.createQuery(query).getResultList();
    }

    private Predicate[] criarPredicates(CriteriaBuilder criteriaBuilder, Root root, VendaDiariaFilter filtro) {
        var predicates = new ArrayList<javax.persistence.criteria.Predicate>();

        var statusPredicate = root.get("status").in(StatusPedidoEnum.ENTREGUE, StatusPedidoEnum.CONFIRMADO);
        predicates.add(statusPredicate);

        if (ObjectUtils.isNotEmpty(filtro.getDataCriacaoInicio())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if (ObjectUtils.isNotEmpty(filtro.getDataCriacaoFim())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        if (ObjectUtils.isNotEmpty(filtro.getRestauranteId())) {
            predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
        }

        return predicates.toArray(new javax.persistence.criteria.Predicate[0]);
    }
}

/* SELECT SQL:

    select date(p.data_criacao) as data_criacao,
        count(p.id) as total_vendas,
        sum(p.valor_total) as total_faturado
    from pedido p
    group by date(p.data_criacao);
 */

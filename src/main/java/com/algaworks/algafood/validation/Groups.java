package com.algaworks.algafood.validation;

import javax.validation.GroupSequence;

@GroupSequence({Groups.TaxaFrete1.class, Groups.TaxaFrete2.class})
public interface Groups {

    public interface CadastroRestaurante { }

    public interface CadastroCozinha { }

    public interface CadastroCidade { }

    public interface CadastroEstado { }

    public interface TaxaFrete1 { }

    public interface TaxaFrete2 { }
}

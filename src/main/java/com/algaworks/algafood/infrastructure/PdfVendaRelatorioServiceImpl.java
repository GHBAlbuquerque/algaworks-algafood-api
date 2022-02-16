package com.algaworks.algafood.infrastructure;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.infrastructure.exception.ReportException;
import com.algaworks.algafood.infrastructure.service.PdfVendaRelatorioService;
import com.algaworks.algafood.infrastructure.service.VendaDiariaQueryService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendaRelatorioServiceImpl implements PdfVendaRelatorioService {


    @Autowired
    private VendaDiariaQueryService vendaDiariaQueryService;

    @Override
    public byte[] emitirRelatorioEmPdf(VendaDiariaFilter filtro) {
        try {
            var relatorioStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            var params = new HashMap<String, Object>();
            params.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var resultado = vendaDiariaQueryService.consultarVendasDiarias(filtro);

            var dataSource = new JRBeanCollectionDataSource(resultado);

            var jasperPrint = JasperFillManager.fillReport(relatorioStream, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException ex) {
            throw new ReportException("Não foi possível gerar o pdf do relatório.", ex.getCause());
        }
    }
}

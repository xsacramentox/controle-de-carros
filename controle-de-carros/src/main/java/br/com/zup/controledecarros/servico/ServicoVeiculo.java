
package br.com.zup.controledecarros.servico;

import br.com.zup.controledecarros.entidades.Veiculo;
import br.com.zup.controledecarros.excecao.VeiculoInvalidoException;
import br.com.zup.controledecarros.repositorio.RepositorioVeiculo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicoVeiculo {

    private final RepositorioVeiculo repositorioVeiculo;
    private RestTemplate restTemplate;
    
    public ServicoVeiculo(RepositorioVeiculo repositorioVeiculo) {
        this.repositorioVeiculo = repositorioVeiculo;
        
        // O servico da tabela FIPE é um webservice assim como o nosso projeto
        // porém, agora nos que iremos consumir os servicos deles e por usaremos
        // essa classe RestTemplate que pode fazer requisiçoes http
        restTemplate = new RestTemplate();
    }
    
    public void salvarVeiculo(Veiculo veiculo) throws VeiculoInvalidoException {
        
        if(veiculo.getModelo() == null) {
            throw new VeiculoInvalidoException("É obrigatório informar o modelo do veículo");
        }
        
        if(veiculo.getMarca() == null) {
            throw new VeiculoInvalidoException("É obrigatório informar a marca do veículo");
        }
        
        if(veiculo.getAno() == null) {
            throw new VeiculoInvalidoException("É obrigatório informar o ano do veículo");
        }
        
        
        // A partir daqui vamos pesquisar as informações na api da FIPE
        
        String codigoMarcaFIPE = null;
        String codigoModeloFIPE = null;
        String codigoAnoFIPE = null;
        
        ArrayNode marcas = pesquisarMarcasDaTabelaFIPE();
        for (int i = 0; i < marcas.size(); i++) {
            JsonNode marca = marcas.get(i);
            if(marca.get("nome").asText().equals(veiculo.getMarca())) {
                codigoMarcaFIPE = marca.get("codigo").asText();
                break;
            }
        }
        
        if(codigoMarcaFIPE == null) {
            throw new VeiculoInvalidoException("A marca do carro não consta na tabela FIPE");
        }
        
        ArrayNode modelos = pesquisarModelosDaTabelaFIPE(codigoMarcaFIPE);
        for (int i = 0; i < modelos.size(); i++) {
            JsonNode modelo = modelos.get(i);
            if(modelo.get("nome").asText().equals(veiculo.getModelo())) {
                codigoModeloFIPE = modelo.get("codigo").asText();
                break;
            }
        }
        
        if(codigoModeloFIPE == null) {
            throw new VeiculoInvalidoException("O modelo do carro não consta na tabela FIPE");
        }
        
        ArrayNode anos = pesquisarAnosDaTabelaFIPE(codigoMarcaFIPE, codigoModeloFIPE);
        for (int i = 0; i < anos.size(); i++) {
            JsonNode ano = anos.get(i);
            if(ano.get("nome").asText().contains(veiculo.getAno().toString())) {
                codigoAnoFIPE = ano.get("codigo").asText();
            }
        }
        
        if(codigoAnoFIPE == null) {
            throw new VeiculoInvalidoException("O ano do carro não consta na tabela FIPE");
        }
        
        DecimalFormat format = new DecimalFormat("'R$ '#,##0.00");
        
        JsonNode infoTabelaFIPE = pesquisarInfoTabelaFIPE(codigoMarcaFIPE, codigoModeloFIPE, codigoAnoFIPE);
        String textoValorTabelaFIPE = infoTabelaFIPE.get("Valor").asText();
        Number valorTabelaFIPE;
        try {
            valorTabelaFIPE = format.parse(textoValorTabelaFIPE);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        veiculo.setValor(new BigDecimal(valorTabelaFIPE.doubleValue()));
        
        repositorioVeiculo.save(veiculo);
        
        
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.
        
    }
    
    public Veiculo buscarVeiculoPelaPlaca(String placa) {
        return repositorioVeiculo.findByPlaca(placa);
    }
    
    private ArrayNode pesquisarMarcasDaTabelaFIPE() {
        return restTemplate.getForObject("https://parallelum.com.br/fipe/api/v1/carros/marcas", ArrayNode.class);
    }
    
    private ArrayNode pesquisarModelosDaTabelaFIPE(String codigoDaMarca) {
        JsonNode response = restTemplate.getForObject("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+ codigoDaMarca +"/modelos", JsonNode.class);
        return (ArrayNode) response.get("modelos");
    }
    
    private ArrayNode pesquisarAnosDaTabelaFIPE(String codigoDaMarca, String codigoDoModelo) {
        return restTemplate.getForObject("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+ codigoDaMarca +"/modelos/" + codigoDoModelo + "/anos", ArrayNode.class);
    }
    
    private JsonNode pesquisarInfoTabelaFIPE(String codigoDaMarca, String codigoDoModelo, String codigoDoAno) {
        return restTemplate.getForObject("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+ codigoDaMarca +"/modelos/" + codigoDoModelo + "/anos/" + codigoDoAno, JsonNode.class);
    }
    
}

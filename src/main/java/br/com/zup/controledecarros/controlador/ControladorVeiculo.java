
package br.com.zup.controledecarros.controlador;

import br.com.zup.controledecarros.entidades.Usuario;
import br.com.zup.controledecarros.entidades.Veiculo;
import br.com.zup.controledecarros.excecao.VeiculoInvalidoException;
import br.com.zup.controledecarros.servico.ServicoVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/veiculo")
public class ControladorVeiculo {
    
    @Autowired
    private ServicoVeiculo servicoVeiculo;
    
    @RequestMapping(path = "/cadastrar", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Veiculo veiculo) {
        try {
            servicoVeiculo.salvarVeiculo(veiculo);
            return new ResponseEntity<Veiculo>(veiculo, HttpStatus.CREATED);
        } catch (VeiculoInvalidoException ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}

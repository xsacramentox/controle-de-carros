
package br.com.zup.controledecarros.controlador;

import br.com.zup.controledecarros.entidades.UsuarioEVeiculos;
import br.com.zup.controledecarros.excecao.UsuarioInvalidoException;
import br.com.zup.controledecarros.excecao.VeiculoInvalidoException;
import br.com.zup.controledecarros.servico.ServicoUsuarioVeiculo;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/usuario-veiculo")
public class ControladorUsuarioVeiculo {
    
    @Autowired
    private ServicoUsuarioVeiculo servicoUsuarioVeiculo;
    
    @RequestMapping(path = "/vincular", method = RequestMethod.POST)
    public ResponseEntity<?> vincularVeiculoAoUsuario(@RequestParam("cpf") String cpf, @RequestParam("placa") String placa) {
        
        try {
            servicoUsuarioVeiculo.vincularVeiculoAoUsuario(cpf, placa);
            return new ResponseEntity<String>("Veiculo vinculado ao usuário", HttpStatus.CREATED);
        } catch (UsuarioInvalidoException | VeiculoInvalidoException ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    public ResponseEntity<?> getUsuariosESeusVeiculos(@RequestParam("cpf") String cpf) {
        
        try {        
            UsuarioEVeiculos usuarioEVeiculos = servicoUsuarioVeiculo.buscarVeiculosDoUsuario(cpf);
            return new ResponseEntity<UsuarioEVeiculos>(usuarioEVeiculos, HttpStatus.OK);
        } catch (UsuarioInvalidoException ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
        
    }
}

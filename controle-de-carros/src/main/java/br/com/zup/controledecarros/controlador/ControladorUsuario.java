
package br.com.zup.controledecarros.controlador;

import br.com.zup.controledecarros.entidades.Usuario;
import br.com.zup.controledecarros.servico.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/usuario")
public class ControladorUsuario {
    
    @Autowired
    private ServicoUsuario servicoUsuario;
    
    @RequestMapping(path = "/cadastrar", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            servicoUsuario.salvarUsuario(usuario);
            return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}

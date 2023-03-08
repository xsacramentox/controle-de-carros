
package br.com.zup.controledecarros.repositorio;

import br.com.zup.controledecarros.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioUsuario extends CrudRepository<Usuario, Long>{
    
    Usuario findByCpf(String cpf);
    
    Usuario findByEmail(String cpf);
}

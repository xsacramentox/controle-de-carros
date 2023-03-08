
package br.com.zup.controledecarros.repositorio;

import br.com.zup.controledecarros.entidades.Usuario;
import br.com.zup.controledecarros.entidades.UsuarioVeiculo;
import br.com.zup.controledecarros.entidades.UsuarioVeiculoPK;
import br.com.zup.controledecarros.entidades.Veiculo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioUsuarioVeiculo extends CrudRepository<UsuarioVeiculo, UsuarioVeiculoPK>{
    
    @Query(value = "SELECT uv.pk.veiculo FROM UsuarioVeiculo uv WHERE uv.pk.usuario.cpf = ?1 ")
    public List<Veiculo> buscarVeiculosPorCpfDoUsuario(String cpf);    
    
}

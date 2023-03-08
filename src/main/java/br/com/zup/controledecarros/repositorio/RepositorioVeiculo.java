
package br.com.zup.controledecarros.repositorio;

import br.com.zup.controledecarros.entidades.Veiculo;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioVeiculo extends CrudRepository<Veiculo, String>{
    
    public Veiculo findByPlaca(String placa);
    
}

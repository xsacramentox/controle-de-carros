
package br.com.zup.controledecarros.servico;

import br.com.zup.controledecarros.entidades.Usuario;
import br.com.zup.controledecarros.entidades.UsuarioEVeiculos;
import br.com.zup.controledecarros.entidades.UsuarioVeiculo;
import br.com.zup.controledecarros.entidades.UsuarioVeiculoPK;
import br.com.zup.controledecarros.entidades.Veiculo;
import br.com.zup.controledecarros.excecao.UsuarioInvalidoException;
import br.com.zup.controledecarros.excecao.VeiculoInvalidoException;
import br.com.zup.controledecarros.repositorio.RepositorioUsuarioVeiculo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicoUsuarioVeiculo {
    
    private RepositorioUsuarioVeiculo repositorioUsuarioVeiculo;
    
    @Autowired
    private ServicoUsuario servicoUsuario;
    
    @Autowired
    private ServicoVeiculo servicoVeiculo;

    public ServicoUsuarioVeiculo(RepositorioUsuarioVeiculo repositorioUsuarioVeiculo) {
        this.repositorioUsuarioVeiculo = repositorioUsuarioVeiculo;
    }
    
    public void vincularVeiculoAoUsuario(String cpf, String placa) throws UsuarioInvalidoException, VeiculoInvalidoException {
        
        Usuario usuario = servicoUsuario.buscarUsuarioPorCpf(cpf);
        
        if(usuario == null) {
            throw new UsuarioInvalidoException("O CPF informado não consta na base de dados");
        }
        
        Veiculo veiculo = servicoVeiculo.buscarVeiculoPelaPlaca(placa);
        
        if(veiculo == null) {
            throw new UsuarioInvalidoException("A placa informada não consta na base de dados");
        }
        
        UsuarioVeiculoPK pk = new UsuarioVeiculoPK();
        pk.setUsuario(usuario);
        pk.setVeiculo(veiculo);
        
        Optional<UsuarioVeiculo> optUsuarioVeiculo = repositorioUsuarioVeiculo.findById(pk);
        if(optUsuarioVeiculo.isPresent()) {
            throw new VeiculoInvalidoException("Este veículo já se encotra vinculado ao usuário");
        }
        
        UsuarioVeiculo usuarioVeiculo = new UsuarioVeiculo();
        usuarioVeiculo.setPk(pk);
        
        repositorioUsuarioVeiculo.save(usuarioVeiculo);
        
    }
    
    public UsuarioEVeiculos buscarVeiculosDoUsuario(String cpf) throws UsuarioInvalidoException{
        
        Usuario usuario = servicoUsuario.buscarUsuarioPorCpf(cpf);
        
        if(usuario == null) {
            throw new UsuarioInvalidoException("O CPF do usuário informado não consta na base dados");
        }
        
        List<Veiculo> veiculosDoUsuario = repositorioUsuarioVeiculo.buscarVeiculosPorCpfDoUsuario(cpf);
        
        UsuarioEVeiculos usuarioEVeiculos = new UsuarioEVeiculos();
        usuarioEVeiculos.setUsuario(usuario);
        usuarioEVeiculos.setVeiculos(veiculosDoUsuario);
        
        return usuarioEVeiculos;
        
    }
    
    
}

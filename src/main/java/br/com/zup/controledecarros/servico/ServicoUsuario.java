
package br.com.zup.controledecarros.servico;

import br.com.zup.controledecarros.entidades.Usuario;
import br.com.zup.controledecarros.excecao.UsuarioInvalidoException;
import br.com.zup.controledecarros.repositorio.RepositorioUsuario;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServicoUsuario {
    
    private RepositorioUsuario repositorioUsuario;

    public ServicoUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }
    
    public void salvarUsuario(Usuario usuario) throws Exception {
        
        if(usuario.getNome()== null) {
            throw new UsuarioInvalidoException("É obrigatório informar o nome do usuário");
        }
        
        if(usuario.getCpf() == null) {
            throw new UsuarioInvalidoException("É obrigatório informar o CPF do usuário");
        }
        
        if(usuario.getEmail() == null) {
            throw new UsuarioInvalidoException("É obrigatório informar o email do usuário");
        }
        
        if(usuario.getDataDeNascimento() == null) {
            throw new UsuarioInvalidoException("É obrigatório informar a data de nascimento do usuário");
        }
        
        if(repositorioUsuario.findByCpf(usuario.getCpf()) != null) {
            throw new UsuarioInvalidoException("O CPF deste usuário já se encontra cadastrado no sistema");
        }                
        
        if(repositorioUsuario.findByEmail(usuario.getEmail()) != null) {
            throw new UsuarioInvalidoException("O email deste usuário já se encontra cadastrado no sistema");
        }
        
        repositorioUsuario.save(usuario);
    }
    
    public Usuario buscarUsuarioPorCpf(String cpf) {
        return repositorioUsuario.findByCpf(cpf);
    }
    
}

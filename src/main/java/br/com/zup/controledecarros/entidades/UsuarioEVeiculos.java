
package br.com.zup.controledecarros.entidades;

import java.util.List;

public class UsuarioEVeiculos {
    
    private Usuario usuario;
    
    private List<Veiculo> veiculos;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
    
    
    
}

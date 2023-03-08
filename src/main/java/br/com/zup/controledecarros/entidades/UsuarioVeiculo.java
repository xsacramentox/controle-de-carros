
package br.com.zup.controledecarros.entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_veiculo")
public class UsuarioVeiculo implements Serializable {
    
    @EmbeddedId
    private UsuarioVeiculoPK pk;     

    public UsuarioVeiculoPK getPk() {
        return pk;
    }

    public void setPk(UsuarioVeiculoPK pk) {
        this.pk = pk;
    }        
    
}

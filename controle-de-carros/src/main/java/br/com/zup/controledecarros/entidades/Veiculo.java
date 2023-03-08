
package br.com.zup.controledecarros.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "veiculo")
public class Veiculo implements Serializable {
    
    @Id
    private String placa;
    
    private String marca;
    
    private String modelo;
    
    private Integer ano;
    
    private BigDecimal valor;    

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public String getDiaDoRodizio() {
        Integer numeroDoDia = descobrirNumeroDoDiaDoRodizio();
        switch(numeroDoDia) {
            case Calendar.MONDAY:
                return "Segunda-feira";
            case Calendar.TUESDAY:
                return "Terça-feira";
            case Calendar.WEDNESDAY:
                return "Quarta-feira";
            case Calendar.THURSDAY:
                return "Quinta-feira";
            case Calendar.FRIDAY:
                return "Sexta-feira";
        }
        
        return "Dia não definido";
    }
    
    public boolean getHojeEhDiaDeRodizio() {
        Integer numeroDoDia = descobrirNumeroDoDiaDoRodizio();
        
        Calendar calenderio = Calendar.getInstance(); // retorna o dia atual
        int diaAtual = calenderio.get(Calendar.DAY_OF_WEEK);
        
        if(diaAtual == numeroDoDia) {
            return true;
        } else {
            return false;
        }
    }
    
    private Integer descobrirNumeroDoDiaDoRodizio() {
        String ano = getAno().toString();
        if(ano.endsWith("0") || ano.endsWith("1")) {
            return Calendar.MONDAY;
        } else if(ano.endsWith("2") || ano.endsWith("3")) {
            return Calendar.TUESDAY;
        } else if(ano.endsWith("4") || ano.endsWith("5")) {
            return Calendar.WEDNESDAY;
        } else if(ano.endsWith("6") || ano.endsWith("7")) {
            return Calendar.THURSDAY;
        } else if(ano.endsWith("8") || ano.endsWith("9")) {
            return Calendar.FRIDAY;
        }
        
        return -1; // nenhum dia;
    }
    
    
}

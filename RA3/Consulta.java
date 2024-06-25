package RA3;

import java.io.Serializable;
import java.util.Date;

public class Consulta implements Serializable {
    private static final long serialVersionUID = 1L;
    private Medico medico;
    private Paciente paciente;
    private Date data;

    public Consulta(Medico medico, Paciente paciente, Date data) {
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Date getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Consulta [medico=" + medico + ", paciente=" + paciente + ", data=" + data + "]";
    }
}

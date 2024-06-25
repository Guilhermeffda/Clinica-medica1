package RA3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JanelaConsulta extends JFrame {
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;

    public JanelaConsulta(List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.consultas = consultas;
        initUI();
    }

    private void initUI() {
        setTitle("Opções de Consulta");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton agendarConsultaBtn = new JButton("Agendar Consulta");
        agendarConsultaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agendarConsulta();
            }
        });
        panel.add(agendarConsultaBtn);

        JButton removerConsultaBtn = new JButton("Remover Consulta");
        removerConsultaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerConsulta();
            }
        });
        panel.add(removerConsultaBtn);

        JButton consultasAgendadasBtn = new JButton("Consultas Agendadas");
        consultasAgendadasBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarConsultasAgendadas();
            }
        });
        panel.add(consultasAgendadasBtn);
    }

    private void agendarConsulta() {
        JComboBox<String> medicoBox = new JComboBox<>();
        for (Medico medico : medicos) {
            medicoBox.addItem(medico.getNome());
        }

        JComboBox<String> pacienteBox = new JComboBox<>();
        for (Paciente paciente : pacientes) {
            pacienteBox.addItem(paciente.getNome());
        }

        JTextField dataField = new JTextField();

        Object[] message = {
            "Nome do Medico:", medicoBox,
            "Nome do Paciente:", pacienteBox,
            "Data (dd/MM/yyyy):", dataField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agendar Consulta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nomeMedico = (String) medicoBox.getSelectedItem();
            String nomePaciente = (String) pacienteBox.getSelectedItem();
            String data = dataField.getText();

            try {
                Medico medico = encontrarMedico(nomeMedico);
                Paciente paciente = encontrarPaciente(nomePaciente);

                Date dataConsulta = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                Consulta consulta = new Consulta(medico, paciente, dataConsulta);
                consultas.add(consulta);
                JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso.");
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido.");
            } catch (MedicoNaoEncontradoException | PacienteNaoEncontradoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private Medico encontrarMedico(String nome) throws MedicoNaoEncontradoException {
        for (Medico medico : medicos) {
            if (medico.getNome().equalsIgnoreCase(nome)) {
                return medico;
            }
        }
        throw new MedicoNaoEncontradoException("Medico não encontrado: " + nome);
    }

    private Paciente encontrarPaciente(String nome) throws PacienteNaoEncontradoException {
        for (Paciente paciente : pacientes) {
            if (paciente.getNome().equalsIgnoreCase(nome)) {
                return paciente;
            }
        }
        throw new PacienteNaoEncontradoException("Paciente não encontrado: " + nome);
    }

    private void removerConsulta() {
        JComboBox<String> consultasBox = new JComboBox<>();
        for (int i = 0; i < consultas.size(); i++) {
            Consulta consulta = consultas.get(i);
            consultasBox.addItem("Consulta " + (i + 1) + ": " + consulta.getMedico().getNome() + " - " + consulta.getPaciente().getNome() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(consulta.getData()));
        }

        Object[] message = {
            "Selecione a Consulta para Remover:", consultasBox
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Remover Consulta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int selectedIndex = consultasBox.getSelectedIndex();
            if (selectedIndex != -1) {
                consultas.remove(selectedIndex);
                JOptionPane.showMessageDialog(this, "Consulta removida com sucesso.");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma consulta selecionada.");
            }
        }
    }

    private void mostrarConsultasAgendadas() {
        StringBuilder consultaList = new StringBuilder();
        for (int i = 0; i < consultas.size(); i++) {
            Consulta consulta = consultas.get(i);
            consultaList.append("Consulta ").append(i + 1).append(": ");
            consultaList.append("Medico: ").append(consulta.getMedico().getNome()).append(", ");
            consultaList.append("Paciente: ").append(consulta.getPaciente().getNome()).append(", ");
            consultaList.append("Data: ").append(new SimpleDateFormat("dd/MM/yyyy").format(consulta.getData())).append("\n");
        }
        JOptionPane.showMessageDialog(this, consultaList.toString(), "Consultas Agendadas", JOptionPane.INFORMATION_MESSAGE);
    }
}

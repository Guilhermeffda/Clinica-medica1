package RA3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JanelaPrincipal extends JFrame {
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;

    public JanelaPrincipal(List<Medico> medicos, List<Paciente> pacientes, List<Consulta> consultas) {
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.consultas = consultas;
        initUI();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarDados();
            }
        });
    }

    private void initUI() {
        setTitle("Gerenciamento de Consultas Medicas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton consultaBtn = new JButton("Consulta");
        consultaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaConsulta();
            }
        });
        panel.add(consultaBtn);

        JButton adicionarMedicoBtn = new JButton("Adicionar Medico");
        adicionarMedicoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarMedico();
            }
        });
        panel.add(adicionarMedicoBtn);

        JButton adicionarPacienteBtn = new JButton("Adicionar Paciente");
        adicionarPacienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPaciente();
            }
        });
        panel.add(adicionarPacienteBtn);
        
        JButton removerMedicoBtn = new JButton("Remover Medico");
        removerMedicoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerMedico();
            }
        });
        panel.add(removerMedicoBtn);
        
        JButton removerPacienteBtn = new JButton("Remover Paciente");
        removerPacienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerPaciente();
            }
        });
        panel.add(removerPacienteBtn);
    }

    private void abrirJanelaConsulta() {
        new JanelaConsulta(medicos, pacientes, consultas).setVisible(true);
    }

    private void adicionarMedico() {
        JTextField nomeField = new JTextField();
        JTextField crmField = new JTextField();

        Object[] message = {
                "Nome do Medico:", nomeField,
                "CRM:", crmField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Adicionar Medico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String crm = crmField.getText();

            if (nome.trim().isEmpty() || crm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            medicos.add(new Medico(nome, crm));
            salvarDados(); // Salvando após adicionar médico
            JOptionPane.showMessageDialog(this, "Medico adicionado com sucesso.");
        }
    }

    private void adicionarPaciente() {
        JTextField nomeField = new JTextField();
        JTextField cpfField = new JTextField();

        Object[] message = {
                "Nome do Paciente:", nomeField,
                "CPF:", cpfField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Adicionar Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();

            if (nome.trim().isEmpty() || cpf.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            pacientes.add(new Paciente(nome, cpf));
            salvarDados(); // Salvando após adicionar paciente
            JOptionPane.showMessageDialog(this, "Paciente adicionado com sucesso.");
        }
    }

    private void removerMedico() {
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nao ha medicos para remover.");
            return;
        }

        String[] nomesMedicos = new String[medicos.size()];
        for (int i = 0; i < medicos.size(); i++) {
            nomesMedicos[i] = medicos.get(i).getNome();
        }

        String nomeSelecionado = (String) JOptionPane.showInputDialog(this,
                "Selecione o medico a ser removido:", "Remover Medico",
                JOptionPane.QUESTION_MESSAGE, null, nomesMedicos, nomesMedicos[0]);

        if (nomeSelecionado != null) {
            for (Consulta consulta : consultas) {
                if (consulta.getMedico().getNome().equals(nomeSelecionado)) {
                    JOptionPane.showMessageDialog(this, "Este medico possui consultas agendadas e nao pode ser removido.");
                    return;
                }
            }

            for (int i = 0; i < medicos.size(); i++) {
                if (medicos.get(i).getNome().equals(nomeSelecionado)) {
                    medicos.remove(i);
                    salvarDados(); // Salvando após remover médico
                    JOptionPane.showMessageDialog(this, "Medico removido com sucesso.");
                    return;
                }
            }
        }
    }

    private void removerPaciente() {
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nao ha pacientes para remover.");
            return;
        }

        String[] nomesPacientes = new String[pacientes.size()];
        for (int i = 0; i < pacientes.size(); i++) {
            nomesPacientes[i] = pacientes.get(i).getNome();
        }

        String nomeSelecionado = (String) JOptionPane.showInputDialog(this,
                "Selecione o paciente a ser removido:", "Remover Paciente",
                JOptionPane.QUESTION_MESSAGE, null, nomesPacientes, nomesPacientes[0]);

        if (nomeSelecionado != null) {
            for (Consulta consulta : consultas) {
                if (consulta.getPaciente().getNome().equals(nomeSelecionado)) {
                    JOptionPane.showMessageDialog(this, "Este paciente possui consultas agendadas e nao pode ser removido.");
                    return;
                }
            }

            for (int i = 0; i < pacientes.size(); i++) {
                if (pacientes.get(i).getNome().equals(nomeSelecionado)) {
                    pacientes.remove(i);
                    salvarDados(); // Salvando após remover paciente
                    JOptionPane.showMessageDialog(this, "Paciente removido com sucesso.");
                    return;
                }
            }
        }
    }

    private void salvarDados() {
        try {
            LeitorCSV.salvarMedicos(medicos, "data/medicos.csv");
            LeitorCSV.salvarPacientes(pacientes, "data/pacientes.csv");
            Persistencia.salvarObjeto(consultas, "data/consultas.dat");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Medico> medicos = LeitorCSV.lerMedicos("data/medicos.csv");
                    List<Paciente> pacientes = LeitorCSV.lerPacientes("data/pacientes.csv");
                    List<Consulta> consultas;
                    try {
                        consultas = Persistencia.carregarObjeto("data/consultas.dat");
                    } catch (IOException | ClassNotFoundException ex) {
                        consultas = new ArrayList<>();
                    }
                    JanelaPrincipal ex = new JanelaPrincipal(medicos, pacientes, consultas);
                    ex.setVisible(true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao ler arquivos CSV: " + ex.getMessage());
                }
            }
        });
    }
}

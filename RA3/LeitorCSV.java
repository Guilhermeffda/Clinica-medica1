package RA3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {

    public static List<Medico> lerMedicos(String caminho) throws IOException {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            criarArquivoMedicos(caminho);
        }
        
        List<Medico> medicos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Medico medico = new Medico(dados[0], dados[1]);
                medicos.add(medico);
            }
        }
        return medicos;
    }

    public static List<Paciente> lerPacientes(String caminho) throws IOException {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            criarArquivoPacientes(caminho);
        }

        List<Paciente> pacientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Paciente paciente = new Paciente(dados[0], dados[1]);
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }

    public static void salvarMedicos(List<Medico> medicos, String caminho) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Medico medico : medicos) {
                bw.write(medico.getNome() + "," + medico.getCrm() + "\n");
            }
        }
    }

    public static void salvarPacientes(List<Paciente> pacientes, String caminho) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Paciente paciente : pacientes) {
                bw.write(paciente.getNome() + "," + paciente.getCpf() + "\n");
            }
        }
    }

    private static void criarArquivoMedicos(String caminho) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            // Não adicionando dados de exemplo
        }
    }

    private static void criarArquivoPacientes(String caminho) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            // Não adicionando dados de exemplo
        }
    }
}

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Gpu;
import conexao.Conexao;
import conexao.ConexaoSql;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Solution {
    public static void main(String[] args) {
        Looca looca = new Looca();
        Sistema sistema = new Sistema();
        Conexao conexao = new Conexao();
        ConexaoSql conexao2 = new ConexaoSql();
        Timer timer = new Timer();
        JdbcTemplate sql = conexao.getConexaoDoBanco();
        JdbcTemplate sqlServer = conexao2.getConexaoDoBancoSqlServer();

        Scanner leitor = new Scanner(System.in);
        Components components = JSensors.get.components();
        List<Gpu> gpus = components.gpus;
        Gpu gpuUnique = gpus.get(0);
        Integer opcao;

        System.out.println(gpuUnique.sensors.temperatures.get(0).value);

        System.out.println("""
                Olá, bem vindo!
                """);

        do {
            System.out.println("""
                    -------------------
                    O que deseja fazer?
                    -------------------
                    1 - Registrar dados
                    2 - Verificar histórico
                    3 - Informações do sistema
                    0 - Sair
                    --------------------
                    """);
            opcao = leitor.nextInt();

            if (opcao.equals(1)){
                System.out.println("Inserindo registros...");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Integer usoProcessador = (looca.getProcessador().getUso()).intValue();
                        Long usoMemoria = (looca.getMemoria().getEmUso());
                        Long limiteMemoria = (looca.getMemoria().getTotal());
                        Double temperaturaGPU = Double.valueOf(gpuUnique.sensors.temperatures.get(0).value);

                        Double porcentagemMemoria = Double.valueOf ((usoMemoria * 100) / limiteMemoria);
                        Integer usoDisco = looca.getGrupoDeDiscos().getTamanhoTotal().intValue();

                        sql.update("INSERT INTO Medida (usoProcessador, usoRam, usoDisco, usoGpu) VALUES (? , ? , ?, ?)", usoProcessador, porcentagemMemoria, usoDisco, temperaturaGPU);

                        sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoProcessador, 1, 1);
                        sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", porcentagemMemoria, 2, 1);
                        sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", usoDisco, 3, 1);
                        sqlServer.update("INSERT INTO Medida (medida, fkComponente, fkSetup) VALUES (?, ?, ?)", temperaturaGPU, 6, 1);
                    }
                }, 5000 , 2000);
            } else if (opcao.equals(2)) {
                System.out.println("""
                        ----------------------------------------------
                        De qual componente deseja checar o  histórico:
                        1 - Processador
                        2 - RAM
                        3 - Disco
                        ----------------------------------------------
                        """);
                Integer escolha = leitor.nextInt();
                if (escolha.equals(1)) {
                    List<Medida> medidasProcesador = sql.query("SELECT usoProcessador, dataMedida FROM Medida", new BeanPropertyRowMapper<>(Medida.class));

                    for (Medida processadorMedida : medidasProcesador) {
                        System.out.println("Medida: " + processadorMedida.getUsoProcessador() + "%" + " Data de captura: " + processadorMedida.getDataMedida());
                    }
                }
                if (escolha.equals(2)) {
                    List<Medida> medidasRam = sql.query("SELECT usoRam, dataMedida FROM Medida", new BeanPropertyRowMapper<>(Medida.class));

                    for (Medida ramMedida : medidasRam) {
                        System.out.println("Medida: " + ramMedida.getUsoRam() + "%" + " Data de captura: " + ramMedida.getDataMedida());
                    }
                }
                if (escolha.equals(3)) {
                    List<Medida> medidasDisco = sql.query("SELECT usoDisco, dataMedida FROM Medida", new BeanPropertyRowMapper<>(Medida.class));

                    for (Medida discoMedida : medidasDisco) {
                        System.out.println("Medida: " + discoMedida.getUsoDisco() + " Data de captura: " + discoMedida.getDataMedida());
                    }
                }
            } else if (opcao.equals(3)) {
                System.out.println("Sistema operacional: " + sistema.getSistemaOperacional());
                System.out.println("Arquitetura: " + sistema.getArquitetura() + " bits");
                System.out.println("Fabricante: " + sistema.getFabricante());
                System.out.println("Tempo de atividade: " + sistema.getTempoDeAtividade());
            } else if (opcao.equals(0)){
                System.out.println("Saindo do sistema");
            }
        } while (!opcao.equals(0));
    }
}

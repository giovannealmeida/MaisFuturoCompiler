package br.uesc.maisfuturocompiler;

import static br.uesc.maisfuturocompiler.LogHelper.LogType.REGISTER_RECOVERED;
import static br.uesc.maisfuturocompiler.LogHelper.LogType.REGISTER_REMOVED;
import br.uesc.maisfuturocompiler.SheetHandler.ColumnName;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanne Almeida 20/08/2018
 */
/**
 * ###################################### # Mensagem de valor inválido (null)
 * ###################################### Foi econtrado "NULL" na coluna
 * <coluna>.
 *
 * Nome: <nome>
 * Matrícula: <matricula>
 *
 * Registro removido por não haver resolução
 *
 * ###################################### # Mensagem de valor inválido (0 onde
 * não é permitido) ###################################### Foi econtrado "0" na
 * coluna <coluna>.
 *
 * Nome: <nome>
 * Matrícula: <matricula>
 *
 * Registro removido por não haver resolução
 *
 * ###################################### # Mensagem de valor inválido resolvido
 * ###################################### Foi econtrado <valor> na coluna
 * <coluna>.
 *
 * Nome: <nome>
 * Matrícula: <matricula>
 *
 * O valor foi substituído por <valor_substituido>. Registro reparado.
 *
 * @author gamessias
 */
public class LogHelper {

    public static enum LogType {
        REGISTER_REMOVED,
        REGISTER_RECOVERED
    }

    private List<Log> logs;
    private static LogHelper logHelper;

    public static LogHelper getInstance() {
        if (logHelper == null) {
            logHelper = new LogHelper();
        }

        return logHelper;
    }

    private LogHelper() {
        this.logs = new ArrayList<>();
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void log(LogType logType, String name, String id, int lineNumber, int columnNumber, String foundValue, String newValue) {

        Log log = new Log(logType, name, id, lineNumber, columnNumber, foundValue, newValue);
        logs.add(log);
    }

    public void writeLog(String file) {
        BufferedWriter writer = null;
        try {
            File logFile = new File(file);
            writer = new BufferedWriter(new FileWriter(logFile));

            //Escreve os removidos primeiro
            for (Log log : logs) {
                if (log.logType == REGISTER_REMOVED) {
                    writer.write("Foi encontrado \"" + log.foundValue + "\" na coluna " + SheetHandler.getStringColumnNameFromIndex(log.columnNumber) + ".");
                    writer.newLine();
                    writer.newLine();
                    writer.write("Dados do aluno (linha " + (log.lineNumber + 1) + ") ===");
                    writer.newLine();
                    writer.write("Nome: " + log.name);
                    writer.newLine();
                    writer.write("Matrícula: " + log.id);
                    writer.newLine();
                    writer.newLine();
                    writer.write("Registro removido por não haver resolução.");
                    writer.newLine();
                    writer.write("------------------------------------------------");
                    writer.newLine();
                    writer.newLine();
                }
            }

            //Escreve os removidos primeiro
            for (Log log : logs) {
                if (log.logType == REGISTER_RECOVERED) {
                    writer.write("Foi encontrado \"" + log.foundValue + "\" na coluna " + SheetHandler.getStringColumnNameFromIndex(log.columnNumber) + ".");
                    writer.newLine();
                    writer.newLine();
                    writer.write("Dados do aluno (linha " + (log.lineNumber + 1) + ") ===");
                    writer.newLine();
                    writer.write("Nome: " + log.name);
                    writer.newLine();
                    writer.write("Matrícula: " + log.id);
                    writer.newLine();
                    writer.newLine();
                    writer.write("O valor foi substituído por \"" + log.newValue + "\".");
                    writer.newLine();
                    writer.write("Registro reparado.");
                    writer.newLine();
                    writer.write("------------------------------------------------");
                    writer.newLine();
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Log {

        private LogType logType;
        private String name, id, foundValue, newValue;
        private int lineNumber, columnNumber;

        public Log(LogType logType, String name, String id, int lineNumber, int columnNumber, String foundValue, String newValue) {
            this.logType = logType;
            this.name = name;
            this.id = id;
            this.lineNumber = lineNumber;
            this.columnNumber = columnNumber;
            this.foundValue = foundValue;
            this.newValue = newValue;
        }
    }
}

package br.uesc.maisfuturocompiler;

import static br.uesc.maisfuturocompiler.LogHelper.LogType.REGISTER_RECOVERED;
import static br.uesc.maisfuturocompiler.LogHelper.LogType.REGISTER_REMOVED;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Giovanne Almeida 20/08/2018
 */
public class LogHelper {

    private JTextArea txtLog;
    private boolean showRemoved, showRestored;
    private File logFile;

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

    public void setTxtLog(JTextArea txtLog) {
        txtLog.setText("");
        this.txtLog = txtLog;
    }

    private LogHelper() {
        this.logs = new ArrayList<>();
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setShowRemoved(boolean showRemoved) {
        this.showRemoved = showRemoved;
    }

    public void setShowRestored(boolean showRestored) {
        this.showRestored = showRestored;
    }

    public void log(LogType logType, String name, String id, int lineNumber, int columnNumber, String foundValue, String newValue) {
        Log log = new Log(logType, name, id, lineNumber, columnNumber, foundValue, newValue);
        writeInConsole(log);
        logs.add(log);
    }

    private void writeInConsole(Log log) {
        StringBuilder sb = new StringBuilder();

        sb.append("Foi encontrado \"").append(log.foundValue).append("\" na coluna ").append(SheetHandler.getStringColumnNameFromIndex(log.columnNumber)).append(".\n\n");
        sb.append("Dados do aluno (linha ").append(log.lineNumber + 1).append(") ===\n");
        sb.append("Nome: ").append(log.name).append("\n");
        sb.append("Matrícula: ").append(log.id).append("\n\n");
        if (log.logType == REGISTER_REMOVED) {
            sb.append("Registro removido por não haver resolução.\n");
        } else {
            sb.append("O valor foi substituído por \"").append(log.newValue).append("\".\n");
            sb.append("Registro reparado.\n");
        }
        sb.append("------------------------------------------------\n\n");

        txtLog.append(sb.toString());
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    /**
     * Escreve o texto passado por parâmetro no log de eventos.
     *
     * @param message Texto a ser escrito.
     */
    public void writeInConsole(String message) {
        txtLog.append(message);
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    public void writeLog(String absolutePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath + "\\log.txt"));

        //Escreve os removidos primeiro
        if (showRemoved) {
            writer.write("============================");
            writer.newLine();
            writer.write("=== ARQUIVOS REMOVIDOS ===");
            writer.newLine();
            writer.write("============================");
            writer.newLine();
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
        }

        //Escreve os recuperados depois
        if (showRestored) {
            writer.newLine();
            writer.newLine();
            writer.write("============================");
            writer.newLine();
            writer.write("=== ARQUIVOS RECUPERADOS ===");
            writer.newLine();
            writer.write("============================");
            writer.newLine();
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
        }
        writer.close();
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

    public void openLogFileExternally() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(logFile);

            } else {
                // dunno, up to you to handle this
            }
        } catch (IOException ex) {
            Logger.getLogger(LogHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package br.uesc.maisfuturocompiler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Giovanne Almeida 20/09/2018
 */
public class Compiler {

    private JProgressBar progressBar;
    private final int HEADER_NUM_ROWS = 4;
    private int SAVE_COST = 0; //Custo (para barra de progresso) do salvamento do arquivo arrumado calculado após análise do arquivo de entrada
    private List<Register> registers;
    private String dir;
    private CompileListener listener;

    void run(String file, JProgressBar progressBar, CompileListener listener) {
        this.listener = listener;
        this.progressBar = progressBar;
        this.progressBar.setMinimum(0);
        this.progressBar.setStringPainted(true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    dir = file;
                    LogHelper.getInstance().writeInConsole("Abrindo arquivo...\n");
                    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file)); //Abre/descompila a planilha
                    XSSFSheet sheet = wb.getSheetAt(0); //Pega a primeira pasta
                    //Prepara o arquivo para processamento
                    prepareFile(sheet);
                    //Inicializa a barra de progresso com o número de registros
                    /* O valor máximo da barra de progresso é composto por:
                        - Quantidade de linhas a ser processada (excluindo as HEADER_NUM_ROWS primeiras que são do cabeçalho)
                        - Tempo de geração do arquivo arrumado (2% do valor da quantidade de a ser linhas processada)
                     */
                    int max = sheet.getLastRowNum() + 1 - HEADER_NUM_ROWS; //+1 é preciso pois getLastRowNum() é 0 based
                    SAVE_COST = (int) (max * 0.02);
                    max += SAVE_COST;
                    progressBar.setMaximum(max);

                    //Processa o arquivo
                    processFile(sheet);

                } catch (IOException ex) {
                    Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    /**
     * ToDo: Fazer o método Prepara o arquivo para processamento. Operações: -
     * Remove as linhas de cabeçalho
     *
     * @param ExcelFileToRead
     */
    private void prepareFile(XSSFSheet sheet) {
        LogHelper.getInstance().writeInConsole("Preparando arquivo...\n");
        for (int i = 0; i < HEADER_NUM_ROWS; i++) {
            sheet.removeRow(sheet.getRow(i));
        }
    }

    private void processFile(XSSFSheet sheet) throws IOException {
        LogHelper.getInstance().writeInConsole("Iniciando compilação...\n\n");
        XSSFRow row; //Objeto que armazena as linhas de registro
        Iterator rows = sheet.rowIterator(); //Obtém o iterador das linhas de registro
        registers = new ArrayList<>();

        while (rows.hasNext()) { //Percorre todas as linhas
            row = (XSSFRow) rows.next(); //Pega a linha atual
            //Cria um registro
            Register register = new Register(row, row.getRowNum() + 1);
            registers.add(register);
            //Verifica a integridade deste registro
            checkRegister(register);
            progressBar.setValue(row.getRowNum() + 1);
        }
        //Escreve registros válidos na nova planilha
        writeNewDocument();
        //Escreve o log
        LogHelper.getInstance().writeLog("C:\\Users\\gamessias\\Desktop\\teste_log.txt");
        progressBar.setValue(progressBar.getValue() + SAVE_COST);
        listener.onFinished();
    }

    /**
     * Verifica a integridade do registro passado seguindo as regras: - Se a
     * célula é null e é um dado sensível: > Marca como inválido
     *
     * - Se a célula é null mas não é sensível: - Se a coluna da célula é
     * {@link ColumnName#E_ORGAO_EXPEDIDOR_RG} > Substitui por "SSP" - Se a
     * coluna da célula é {}
     *
     * @param register
     */
    private void checkRegister(Register register) {
        /* Esvazia as células de campos não obrigatórios 
        Essa operação deve ser feita antes da validação dos registros pois
        remove vários campos nulos que não serão usados */
        register.clearNonRequiredCells();
        //Valida as células marcando os registros que não são válidos
        register.validateCells();
        //Se o registro for válido, faz o resto
        if (register.isValid()) {
            register.fixValues();
        }
    }

    private void writeNewDocument() {
        LogHelper.getInstance().writeInConsole("Gerando arquivo arrumado...\n");
        String excelFileName = "C:\\Users\\gamessias\\Desktop\\teste_arrumado.xlsx";//name of excel file

        String sheetName = "Dados Mais Futuro";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        int fileLine = 0;
        for (int registerCount = 0; registerCount < registers.size(); registerCount++) {
            Register register = registers.get(registerCount);

            if (register.isValid()) {
                XSSFRow row = sheet.createRow(fileLine++);
                for (int column = 0; column < register.getAllCells().size(); column++) {
                    XSSFCell cell = row.createCell(column);
                    cell.setCellValue(register.getCellByIndex(column).getStringCellValue());
                }
            }
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(excelFileName);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            System.out.println("O arquivo está aberto ou falta permissão de escrita!");
        } catch (IOException ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Giovanne Almeida 20/09/2018
 */
public class Compiler {

    private List<Register> registers;
    private String dir;

    void run(String file) {
        try {
            dir = file;
            //Abre o arquivo
            InputStream ExcelFileToRead = new FileInputStream(file);
            //Prepara o arquivo para processamento
            prepareFile(ExcelFileToRead);
            //Processa o arquivo
            processFile(ExcelFileToRead);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processFile(InputStream ExcelFileToRead) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead); //Abre/descompila a planilha
        XSSFSheet sheet = wb.getSheetAt(0); //Pega a primeira pasta
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
        }
        //Escreve registros válidos na nova planilha
        writeNewDocument();
        //Escreve o log
        LogHelper.getInstance().writeLog("C:\\Users\\gamessias\\Desktop\\teste_log.txt");
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
        //Esvazia as células de campos não obrigatórios
        register.clearNonRequiredCells();
        //Valida as células marcando os registros que não são válidos
        register.validateCells();
    }

    /**
     * ToDo: Fazer o método Prepara o arquio para processamento. Operações: -
     * Remove as linhas de cabeçalho
     *
     * @param ExcelFileToRead
     */
    private void prepareFile(InputStream ExcelFileToRead) {

    }

    private void writeNewDocument() {
        String excelFileName = "C:\\Users\\gamessias\\Desktop\\teste_arrumado.xlsx";//name of excel file

        String sheetName = "Pasta1";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        for (int line = 0; line < registers.size(); line++) {
            Register register = registers.get(line);

            if (register.isValid()) {
                XSSFRow row = sheet.createRow(line);
                for (int column = 0; column < register.getAllCells().size(); column++) {
                    XSSFCell cell = row.createCell(column);
                    cell.setCellValue(register.getCellByIndex(column).getStringCellValue());
                }
            }
        }
        //iterating r number of rows
//        for (int r = 0; r < 5; r++) {
//            XSSFRow row = sheet.createRow(r);
//
//            //iterating c number of columns
//            for (int c = 0; c < 5; c++) {
//                XSSFCell cell = row.createCell(c);
//
//                cell.setCellValue("Cell " + r + " " + c);
//            }
//        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(excelFileName);

            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

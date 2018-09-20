/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uesc.maisfuturocompiler;

import br.uesc.maisfuturocompiler.SheetHandler.ColumnName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 *
 * @author Giovanne Almeida 20/09/2018
 */
/**
 * Cria uma lista de {@link Cell} a partir de {@link XSSFRow} <b>row</b>.
 *
 */
public class Register {

    //Células deste registro
    private final List<XSSFCell> cells;
    private final int index; //Número do registro/linha (inicia em 1)
    private boolean isValid = true;

    Register(XSSFRow row, int index) {
        this.index = index;

        cells = new ArrayList<>();
        Iterator rowIterator = row.cellIterator();

        while (rowIterator.hasNext()) {
            cells.add((XSSFCell) rowIterator.next());
        }
    }

    /**
     * Pega uma célula pelo nome da coluna ({@link ColumnName}).
     *
     * @param column Nome da columa
     * @return A célula presente na coluna solicitada.
     */
    public XSSFCell getCellByColum(ColumnName column) {
        return cells.get(column.ordinal());
    }
    
    /**
     * Pega uma célula pelo índice da sua coluna.
     *
     * @param index Índice da columa
     * @return A célula presente na coluna indicada.
     */
    public XSSFCell getCellByIndex(int index) {
        return cells.get(index);
    }

    /**
     * Retorna todas as células desse registro/linha.
     *
     * @return Todas as células desse registro/linha.
     */
    public List<XSSFCell> getAllCells() {
        return cells;
    }

    /**
     * Retorna o número do registro/linha. Inicia em 1.
     *
     * @return O número do registro/linha
     */
    public int getIndex() {
        return index;
    }

    public boolean isValid() {
        return isValid;
    }

    /**
     * ToDo: Pensar em estratégias de garantia de integridade de alguns desses
     * dados descartados.
     *
     * Limpa todos as células que não são obrigatórios e que não podemos
     * garantir a integridade:
     * <ul>
     * <li>{@link ColumnName#E_CADUNICO}</li>
     * <li>{@link ColumnName#E_NIS}</li>
     * <li>{@link ColumnName#E_COMPLEMENTO}</li>
     * <li>{@link ColumnName#E_MUNICIPIO_IBGE}</li>
     * <li>{@link ColumnName#E_CEP}</li>
     * <li>{@link ColumnName#E_ZONA}</li>
     * <li>{@link ColumnName#E_TELEFONE}</li>
     * <li>{@link ColumnName#E_CELULAR}</li>
     * <li>{@link ColumnName#R_CPF}</li>
     * <li>{@link ColumnName#R_RG}</li>
     * <li>{@link ColumnName#R_ORGAO_EXPEDIDOR_RG}</li>
     * <li>{@link ColumnName#R_DATA_NASCIMENTO}</li>
     * <li>{@link ColumnName#R_NIS}</li>
     * <li>{@link ColumnName#R_LOGRADOURO}</li>
     * <li>{@link ColumnName#R_NUMERO}</li>
     * <li>{@link ColumnName#R_BAIRRO}</li>
     * <li>{@link ColumnName#R_COMPLEMENTO}</li>
     * <li>{@link ColumnName#R_MUNICIPIO_IBGE}</li>
     * <li>{@link ColumnName#R_CEP}</li>
     * <li>{@link ColumnName#R_ZONA}</li>
     * <li>{@link ColumnName#R_TELEFONE}</li>
     * <li>{@link ColumnName#R_CELULAR}</li>
     * <li>{@link ColumnName#R_EMAIL}</li>
     * </ul>
     *
     */
    public void clearNonRequiredCells() {
        cells.get(ColumnName.E_CADUNICO.ordinal()).setCellValue("");
        cells.get(ColumnName.E_NIS.ordinal()).setCellValue("");
        cells.get(ColumnName.E_COMPLEMENTO.ordinal()).setCellValue("");
        cells.get(ColumnName.E_MUNICIPIO_IBGE.ordinal()).setCellValue("");
        cells.get(ColumnName.E_CEP.ordinal()).setCellValue("");
        cells.get(ColumnName.E_ZONA.ordinal()).setCellValue("");
        cells.get(ColumnName.E_TELEFONE.ordinal()).setCellValue("");
        cells.get(ColumnName.E_CELULAR.ordinal()).setCellValue("");
        cells.get(ColumnName.R_CPF.ordinal()).setCellValue("");
        cells.get(ColumnName.R_RG.ordinal()).setCellValue("");
        cells.get(ColumnName.R_ORGAO_EXPEDIDOR_RG.ordinal()).setCellValue("");
        cells.get(ColumnName.R_DATA_NASCIMENTO.ordinal()).setCellValue("");
        cells.get(ColumnName.R_NIS.ordinal()).setCellValue("");
        cells.get(ColumnName.R_LOGRADOURO.ordinal()).setCellValue("");
        cells.get(ColumnName.R_NUMERO.ordinal()).setCellValue("");
        cells.get(ColumnName.R_BAIRRO.ordinal()).setCellValue("");
        cells.get(ColumnName.R_COMPLEMENTO.ordinal()).setCellValue("");
        cells.get(ColumnName.R_MUNICIPIO_IBGE.ordinal()).setCellValue("");
        cells.get(ColumnName.R_CEP.ordinal()).setCellValue("");
        cells.get(ColumnName.R_ZONA.ordinal()).setCellValue("");
        cells.get(ColumnName.R_TELEFONE.ordinal()).setCellValue("");
        cells.get(ColumnName.R_CELULAR.ordinal()).setCellValue("");
        cells.get(ColumnName.R_EMAIL.ordinal()).setCellValue("");
    }

    /**
     * Verifica a integridade do registro passado seguindo as regras: - Se a
     * célula é null e é um dado sensível: > Marca como inválido
     *
     * - Se a célula é null mas não é sensível: - Se a coluna da célula é
     * {@link ColumnName#E_ORGAO_EXPEDIDOR_RG} > Substitui por "SSP" - Se a
     * coluna da célula é {}
     *
     */
    void validateCells() {
        for (XSSFCell cell : cells) {
            //Verifica se há algo de errado na célula
            if (cell.getStringCellValue().equals("NULL")) {
                //Verifica se dá pra arrumar
                int column = cell.getColumnIndex();
                //Se a coluna for E_ORGAO_EXPEDIDOR_RG, assume o valor como "SSP"
                if (column == ColumnName.E_ORGAO_EXPEDIDOR_RG.ordinal()) {
                    cell.setCellValue("SSP");
                    logRecoveredRegister(cell);
                    //Se a coluna for E_COR_RACA, assume o valor como "6", valor referente a "Não Declarado"
                } else if (column == ColumnName.E_COR_RACA.ordinal()) {
                    cell.setCellValue("6");
                    logRecoveredRegister(cell);
                } else {
                    //Se não dá pra arrumar, marca esse registro como inválido
                    logRemovedRegister(cell);
                    return;
                }
            } else if (cell.getStringCellValue().equals("0")
                    && ((cell.getColumnIndex() == ColumnName.E_TURNO_ATUAL.ordinal())
                    || (cell.getColumnIndex() == ColumnName.E_SEMESTRE_ATUAL.ordinal()))) {
                logRemovedRegister(cell);
                return;
            }
        }
    }

    private void logRemovedRegister(XSSFCell cell) {
        LogHelper.getInstance().log(
                LogHelper.LogType.REGISTER_REMOVED,
                cells.get(ColumnName.E_MATRICULA.ordinal()).getStringCellValue(),
                cells.get(ColumnName.E_NOME.ordinal()).getStringCellValue(),
                cell.getRowIndex(),
                cell.getColumnIndex(),
                "",
                cell.getStringCellValue());
        isValid = false;
    }

    private void logRecoveredRegister(XSSFCell cell) {
        LogHelper.getInstance().log(
                LogHelper.LogType.REGISTER_RECOVERED,
                cells.get(ColumnName.E_MATRICULA.ordinal()).getStringCellValue(),
                cells.get(ColumnName.E_NOME.ordinal()).getStringCellValue(),
                cell.getRowIndex(),
                cell.getColumnIndex(),
                "NULL",
                cell.getStringCellValue());
    }
}

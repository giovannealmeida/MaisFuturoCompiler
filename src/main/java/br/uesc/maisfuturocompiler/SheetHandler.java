package br.uesc.maisfuturocompiler;

/**
 *
 * @author Giovanne Almeida 20/09
 */
public class SheetHandler {
    /**
     * Nome das colunas no arquivo Excel de origem.
     */
    public enum ColumnName {
        E_MATRICULA,
        E_NOME,
        E_CPF,
        E_RG,
        E_ORGAO_EXPEDIDOR_RG,
        E_DATA_NASCIMENTO,
        E_CADUNICO,
        E_NIS,
        E_TURNO_ATUAL,
        E_INGRESSO_ANO_SEMESTRE,
        E_GENERO,
        E_COR_RACA,
        E_UNIVERSIDADE,
        E_CAMPUS,
        E_CURSO,
        E_DURACAO_CURSO,
        E_SEMESTRE_ATUAL,
        E_LOGRADOURO,
        E_NUMERO,
        E_BAIRRO,
        E_COMPLEMENTO,
        E_MUNICIPIO_IBGE,
        E_CEP,
        E_ZONA,
        E_TELEFONE,
        E_CELULAR,
        E_EMAIL,
        R_NOME,
        R_CPF,
        R_RG,
        R_ORGAO_EXPEDIDOR_RG,
        R_DATA_NASCIMENTO,
        R_NIS,
        R_LOGRADOURO,
        R_NUMERO,
        R_BAIRRO,
        R_COMPLEMENTO,
        R_MUNICIPIO_IBGE,
        R_CEP,
        R_ZONA,
        R_TELEFONE,
        R_CELULAR,
        R_EMAIL,
        E_PERCENT_CURSO,
        E_QTD_TRANC_CURSO,
        E_QTD_TRANC_DISC,
        E_QTD_DISC_PERD,
        E_QTD_DISC_ABAN
    }
    /**
     * Retorna o nome da coluna no formato {@link String} a partir do tipo da coluna.
     * 
     * @param column Nome da coluna ({@link ColumnName}).
     * @return Nome da coluna no formato texto ({@link String}) 
     */
    public static String getStringColumnNameFromColumnName(ColumnName column){
        switch(column){
        case E_MATRICULA:
            return "E_MATRICULA";
        case E_NOME:
            return "E_NOME";
        case E_CPF:
            return "E_CPF";
        case E_RG:
            return "E_RG";
        case E_ORGAO_EXPEDIDOR_RG:
            return "E_ORGAO_EXPEDIDOR_RG";
        case E_DATA_NASCIMENTO:
            return "E_DATA_NASCIMENTO";
        case E_CADUNICO:
            return "E_CADUNICO";
        case E_NIS:
            return "E_NIS";
        case E_TURNO_ATUAL:
            return "E_TURNO_ATUAL";
        case E_INGRESSO_ANO_SEMESTRE:
            return "E_INGRESSO_ANO_SEMESTRE";
        case E_GENERO:
            return "E_GENERO";
        case E_COR_RACA:
            return "E_COR_RACA";
        case E_UNIVERSIDADE:
            return "E_UNIVERSIDADE";
        case E_CAMPUS:
            return "E_CAMPUS";
        case E_CURSO:
            return "E_CURSO";
        case E_DURACAO_CURSO:
            return "E_DURACAO_CURSO";
        case E_SEMESTRE_ATUAL:
            return "E_SEMESTRE_ATUAL";
        case E_LOGRADOURO:
            return "E_LOGRADOURO";
        case E_NUMERO:
            return "E_NUMERO";
        case E_BAIRRO:
            return "E_BAIRRO";
        case E_COMPLEMENTO:
            return "E_COMPLEMENTO";
        case E_MUNICIPIO_IBGE:
            return "E_MUNICIPIO_IBGE";
        case E_CEP:
            return "E_CEP";
        case E_ZONA:
            return "E_ZONA";
        case E_TELEFONE:
            return "E_TELEFONE";
        case E_CELULAR:
            return "E_CELULAR";
        case E_EMAIL:
            return "E_EMAIL";
        case R_NOME:
            return "R_NOME";
        case R_CPF:
            return "R_CPF";
        case R_RG:
            return "R_RG";
        case R_ORGAO_EXPEDIDOR_RG:
            return "R_ORGAO_EXPEDIDOR_RG";
        case R_DATA_NASCIMENTO:
            return "R_DATA_NASCIMENTO";
        case R_NIS:
            return "R_NIS";
        case R_LOGRADOURO:
            return "R_LOGRADOURO";
        case R_NUMERO:
            return "R_NUMERO";
        case R_BAIRRO:
            return "R_BAIRRO";
        case R_COMPLEMENTO:
            return "R_COMPLEMENTO";
        case R_MUNICIPIO_IBGE:
            return "R_MUNICIPIO_IBGE";
        case R_CEP:
            return "R_CEP";
        case R_ZONA:
            return "R_ZONA";
        case R_TELEFONE:
            return "R_TELEFONE";
        case R_CELULAR:
            return "R_CELULAR";
        case R_EMAIL:
            return "R_EMAIL";
        case E_PERCENT_CURSO:
            return "E_PERCENT_CURSO";
        case E_QTD_TRANC_CURSO:
            return "E_QTD_TRANC_CURSO";
        case E_QTD_TRANC_DISC:
            return "E_QTD_TRANC_DISC";
        case E_QTD_DISC_PERD:
            return "E_QTD_DISC_PERD";
        case E_QTD_DISC_ABAN:
            return "E_QTD_DISC_ABAN";
        }
        return null;
    }
    
    /**
     * Retorna o nome da coluna no formato {@link String} a partir do índice da coluna.
     * 
     * @param index Índice da coluna
     * @return Nome da coluna no formato texto ({@link String}) 
     */
    public static String getStringColumnNameFromIndex(int index){
        switch(index){
        case 0:
            return "E_MATRICULA";
        case 1:
            return "E_NOME";
        case 2:
            return "E_CPF";
        case 3:
            return "E_RG";
        case 4:
            return "E_ORGAO_EXPEDIDOR_RG";
        case 5:
            return "E_DATA_NASCIMENTO";
        case 6:
            return "E_CADUNICO";
        case 7:
            return "E_NIS";
        case 8:
            return "E_TURNO_ATUAL";
        case 9:
            return "E_INGRESSO_ANO_SEMESTRE";
        case 10:
            return "E_GENERO";
        case 11:
            return "E_COR_RACA";
        case 12:
            return "E_UNIVERSIDADE";
        case 13:
            return "E_CAMPUS";
        case 14:
            return "E_CURSO";
        case 15:
            return "E_DURACAO_CURSO";
        case 16:
            return "E_SEMESTRE_ATUAL";
        case 17:
            return "E_LOGRADOURO";
        case 18:
            return "E_NUMERO";
        case 19:
            return "E_BAIRRO";
        case 20:
            return "E_COMPLEMENTO";
        case 21:
            return "E_MUNICIPIO_IBGE";
        case 22:
            return "E_CEP";
        case 23:
            return "E_ZONA";
        case 24:
            return "E_TELEFONE";
        case 25:
            return "E_CELULAR";
        case 26:
            return "E_EMAIL";
        case 27:
            return "R_NOME";
        case 28:
            return "R_CPF";
        case 29:
            return "R_RG";
        case 30:
            return "R_ORGAO_EXPEDIDOR_RG";
        case 31:
            return "R_DATA_NASCIMENTO";
        case 32:
            return "R_NIS";
        case 33:
            return "R_LOGRADOURO";
        case 34:
            return "R_NUMERO";
        case 35:
            return "R_BAIRRO";
        case 36:
            return "R_COMPLEMENTO";
        case 37:
            return "R_MUNICIPIO_IBGE";
        case 38:
            return "R_CEP";
        case 39:
            return "R_ZONA";
        case 40:
            return "R_TELEFONE";
        case 41:
            return "R_CELULAR";
        case 42:
            return "R_EMAIL";
        case 43:
            return "E_PERCENT_CURSO";
        case 44:
            return "E_QTD_TRANC_CURSO";
        case 45:
            return "E_QTD_TRANC_DISC";
        case 46:
            return "E_QTD_DISC_PERD";
        case 47:
            return "E_QTD_DISC_ABAN";
        }
        return null;
    }
}

/**
 * Este compilador foi criado com o intuito de facilitar o preparo do arquivo
 * de dados recebido (planilha Excel).
 *
 * Vez ou outra o governo solicita dados acadêmicos para atualização de status
 * de bolsas de assistência. Junto com a solicitação vem um arquivo PDF de
 * layout com a especificação da formatação dos dados que devem ser enviados.
 * A UDO solicita os dados à ASSEST (Assessoria à Assistência Estudantil), que
 * os puxa do SAGRES, devolvendo-os numa planilha do Excel.
 * A planilha reflete uma tabela do SAGRES e contém dados pessoais de alunos da
 * UESC. A tabela vem organizada com os mesmo cabeçalhos de coluna que são
 * exigidos no arquivo de layout, porém os dados estão em outro formato.
 *
 * Após os ajustes dos dados, a planilha resultante deve ser submetido no site
 * <http://maisfuturo.educacao.ba.gov.br/auth/>.
 *
 * ==== Regras aplicadas pelo programa (em sequência, separadas em grupos) ====
 *
 * 1. Remover registros que possuem CPF (`E_CPF`) ou RH (`E_RG`) nulos (NULL);
 *
 * 2. Alterar todos os casos nulos de órgão expeditor (`E_ORGAO_EXPEDIDOR_RG`)
 * para "SSP";
 *
 * 3. Limpar todos os registros das colunas `E_CADUNICO` e `E_NIS`, 
 * atribuindo "" (string vazia) nos campos;
 *
 * 4. Fazer a correspondência dos códigos em `E_TURNO_ATUAL` seguindo a legenda:
 *      1 = Matutino 
 *      2 = Vespertino 
 *      3 = Noturno 
 *      4 = Integral/diurno
 *
 * 5. Aplicar máscara em `E_INGRESSO_ANO_SEMESTRE` alterando o formato "99999"
 * para "9999.9";
 *
 * 6. Fazer a correspondência dos códigos em `E_COR_RACA` seguindo a legenda: 
 *      1 = Branca 
 *      2 = Preta 
 *      3 = Amerela (é 'AmErela' mesmo) 
 *      4 = Parda 
 *      5 = Indígena 
 *      6 = Não Declarado / null
 *
 * 7. Alterar todos os registros da coluna `E_UNIVERSIDADE` para "4" (valor
 * correspondente à UESC);
 *
 * 8. Alterar todos os registros da coluna `E_CAMPUS` para "Prof. Soane Nazaré
 * de Andrade";
 *
 * 9. Limpar todos os registros das colunas `E_COMPLEMENTO`, `E_MUNICIPIO_IBGE`,
 * `E_CEP`, `E_ZONA`, `E_TELEFONE` e `E_CELULAR` atribuindo "" (string vazia) 
 * nos campos;
 *
 * 10. Limpar todos os registros das colunas `R_CPF`, `R_RG`,
 * `R_ORGAO_EXPEDIDOR_RG`, `R_DATA_NASCIMENTO`, `R_NIS`, `R_LOGRADOURO`,
 * `R_NUMERO`, `R_BAIRRO`, `R_COMPLEMENTO`, `R_MUNICIPIO_IBGE`, `R_CEP`,
 * `R_ZONA`, `R_TELEFONE`, `R_CELULAR` e `R_EMAIL`;
 *
 * 11. Remover as primeiras linhas (cabeçalhos).
 *
 */
package br.uesc.maisfuturocompiler;

/**
 *
 * @author Giovanne Almeida 19/09/2018
 */
public class Main {    
    public static void main(String[] args) {
        new Compiler().run("C:\\Users\\gamessias\\Desktop\\teste.xlsx");
    }
}

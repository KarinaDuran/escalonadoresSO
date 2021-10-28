package kernel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import operacoes.Operacao;
import operacoes.OperacaoES;

public class SeuSO extends SO {
	private List<Integer> processosTerminados = new ArrayList<Integer>();
	private static List<Integer> processosProntos = new ArrayList<Integer>();
	private Integer processosExecutando;
	private List<Integer> processosEsperando = new ArrayList<Integer>();
	
	private Integer idProcessoNovo = 0; //?
	private Integer trocasDeContexto = 0;
	private Escalonador esquemaEscalonador;
	
	private PCB processoAtual;
	private Operacao operacaoCPU;
	private OperacaoES operacaoES;
	private List<PCB> processosList = new ArrayList<PCB>();

	private List<OperacaoES> filaES0 = new ArrayList<>();
	private List<OperacaoES> filaES1 = new ArrayList<>();
	private List<OperacaoES> filaES2 = new ArrayList<>();
	private List<OperacaoES> filaES3 = new ArrayList<>();
	private List<OperacaoES> filaES4 = new ArrayList<>();

	private HashMap <Integer, Operacao> filaES = new HashMap<Integer, Operacao>();

	@Override
	// ATENCÃO: cria o processo mas o mesmo 
	// só estará "pronto" no próximo ciclo
	protected void criaProcesso(Operacao[] codigo) {
		PCB novoProcesso = new PCB();
		novoProcesso.idProcesso = idProcessoNovo;
		novoProcesso.codigo = codigo;
		novoProcesso.contadorDePrograma = 0;
		processosList.add(novoProcesso);	
	}


	@Override
	protected void trocaContexto(PCB pcbAtual, PCB pcbProximo) {
	 idProcessoNovo = pcbProximo.idProcesso;
		processoAtual = pcbProximo;
		
		trocasDeContexto ++;
	}

	@Override
	protected OperacaoES proximaOperacaoES(int idDispositivo) {
		OperacaoES operacao;
		if(idDispositivo == 0){
			operacao = filaES0.get(0);
		}else		if(idDispositivo == 1){
			operacao = filaES1.get(0);
		}else		if(idDispositivo == 2){
			operacao = filaES2.get(0);
		}else if(idDispositivo == 3){
			operacao = filaES3.get(0);
		}else{
			operacao = (filaES4).get(0);
		}
		return operacao;
	
	}

	@Override
	protected Operacao proximaOperacaoCPU() {
		return operacaoCPU;
	}

	@Override
	protected void executaCicloKernel() {
		if(idProcessoNovo == 0){
			processosProntos.add(idProcessoNovo);
			

		}
		if(processosProntos.size()> 0 || processosExecutando != null){
				if(esquemaEscalonador == Escalonador.FIRST_COME_FIRST_SERVED){
				
			}


		}


			


		
	}

	@Override
	protected boolean temTarefasPendentes(){
		if(processosEsperando.size()==0 && processoAtual.ciclosRestantes==0)
		return false;
		return true;
	}

	@Override
	protected Integer idProcessoNovo() {
		return idProcessoNovo-1;
	}

	@Override
	protected List<Integer> idProcessosProntos() {
		return processosProntos;
	}

	@Override
	protected Integer idProcessoExecutando() {
		return processosExecutando;
	}

	@Override
	protected List<Integer> idProcessosEsperando() {
		return processosEsperando;
	}

	@Override
	protected List<Integer> idProcessosTerminados() {
		return processosTerminados;
	}

	@Override
	protected int tempoEsperaMedio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int tempoRespostaMedio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int tempoRetornoMedio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int trocasContexto() {
		return trocasDeContexto;
	}

	@Override
	public void defineEscalonador(Escalonador e) {
		esquemaEscalonador = e;
	}
}

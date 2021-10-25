package kernel;
import java.util.ArrayList;
import java.util.List;

import operacoes.Operacao;
import operacoes.OperacaoES;

public class SeuSO extends SO {
	private List<Integer> processosTerminados = new ArrayList<Integer>();
	private static List<Integer> processosProntos = new ArrayList<Integer>();
	private Integer processosExecutando;
	private List<Integer> processosEsperando = new ArrayList<Integer>();
	private Integer idAtual = 0; //?
	private Integer trocasDeContexto = 0;
	private Escalonador esquemaEscalonador;
	private PCB processoAtual;
	private Operacao operacaoAtual;

	@Override
	// ATENCÃO: cria o processo mas o mesmo 
	// só estará "pronto" no próximo ciclo
	protected void criaProcesso(Operacao[] codigo) {
		PCB novoProcesso = new PCB();
		novoProcesso.idProcesso = idAtual;
		novoProcesso.codigo = codigo;
		novoProcesso.contadorDePrograma = 0;
		novoProcesso.ciclosRestantes = totalCiclos(codigo);
		processosProntos.add(idAtual);
		

	}

	protected int totalCiclos(Operacao[] codigo){
		int aux = codigo.length;
		for (Operacao o : codigo){
			if (o instanceof OperacaoES){
				OperacaoES x = (OperacaoES)o;
				aux--;
				aux+=x.ciclos;
			}
		}
		return aux;

	}

	@Override
	protected void trocaContexto(PCB pcbAtual, PCB pcbProximo) {
		idAtual = pcbProximo.idProcesso;
		processoAtual = pcbProximo;
		
		trocasDeContexto ++;
	}

	@Override
	protected OperacaoES proximaOperacaoES(int idDispositivo) {
		if(idDispositivo < 0 || idDispositivo > 4)
		return null;
		else return null;
	}

	@Override
	protected Operacao proximaOperacaoCPU() {
		if(esquemaEscalonador == Escalonador.FIRST_COME_FIRST_SERVED){
			if(processoAtual.codigo.length > 0){
				return processoAtual.codigo[processoAtual.contadorDePrograma+1];
				processoAtual.contadorDePrograma = processoAtual.contadorDePrograma++;
			}else{
				processosProntos.add(processoAtual.idProcesso);
				return processosExecutando.get();

			}
		}else if( esquemaEscalonador == Escalonador.SHORTEST_JOB_FIRST){

		}


		
		return null;
	}

	@Override
	protected void executaCicloKernel() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean temTarefasPendentes(){
		if(processosEsperando.size()==0)
		return false;
		return true;
	}

	@Override
	protected Integer idProcessoNovo() {
		// TODO Auto-generated method stub
		return idAtual-1;
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

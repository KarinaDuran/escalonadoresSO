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
	private Integer idAtual;
	private Operacao[] operacaoAtual;

	@Override
	// ATENCÃO: cria o processo mas o mesmo 
	// só estará "pronto" no próximo ciclo
	protected void criaProcesso(Operacao[] codigo) {
		for(int i = 0; i< codigo.length; i++){


		}
		
	}

	@Override
	protected void trocaContexto(PCB pcbAtual, PCB pcbProximo) {
		idAtual = pcbProximo.idProcesso;
		operacaoAtual = pcbProximo.codigo;
	}

	@Override
	protected OperacaoES proximaOperacaoES(int idDispositivo) {
		if(idDispositivo < 0 || idDispositivo > 4)
		return null;
		else return null;
	}

	@Override
	protected Operacao proximaOperacaoCPU() {
		// TODO Auto-generated method stub
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
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void defineEscalonador(Escalonador e) {
		// TODO Auto-generated method stub
	}
}

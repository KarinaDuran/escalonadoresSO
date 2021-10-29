package kernel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import operacoes.Operacao;
import operacoes.OperacaoES;

public class SeuSO extends SO {
	private List<Integer> processosTerminados = new ArrayList<Integer>();
	private static List<Integer> processosProntos = new ArrayList<Integer>();
	private Integer processosExecutando;
	private List<Integer> processosEsperando = new ArrayList<Integer>();
	
	private Integer idProcessoNovo = 0;
	private Integer trocasDeContexto = 0;
	private Escalonador esquemaEscalonador;
	private int contadorCiclos =0; 

	private PCB processoAtual;
	private PCB processoNovo;
	private Operacao operacaoCPU;

	private List<PCB> prontoList = new ArrayList<PCB>();
	private List<PCB> esperandoList = new ArrayList<PCB>();

	private HashMap<Integer, OperacaoES> aa = new HashMap<>();

	private List<PCB> filaES0 = new ArrayList<>();
	private List<PCB> filaES1 = new ArrayList<>();
	private List<PCB> filaES2 = new ArrayList<>();
	private List<PCB> filaES3 = new ArrayList<>();
	private List<PCB> filaES4 = new ArrayList<>();


	@Override
	// ATENCÃO: cria o processo mas o mesmo 
	// só estará "pronto" no próximo ciclo
	protected void criaProcesso(Operacao[] codigo) {
		PCB novoProcesso = new PCB();
		novoProcesso.idProcesso = idProcessoNovo;
		novoProcesso.codigo = codigo;
		novoProcesso.contadorDePrograma = 0;
        	processoNovo = novoProcesso;
		idProcessoNovo ++;
	}


	@Override
	protected void trocaContexto(PCB pcbAtual, PCB pcbProximo) {
	     	processoAtual = pcbProximo;
		trocasDeContexto ++;
	}

	@Override
	protected OperacaoES proximaOperacaoES(int idDispositivo) {
		PCB processos;
		if(idDispositivo == 0){
			processos = filaES0.get(0);
		}else		if(idDispositivo == 1){
			processos = filaES1.get(0);
		}else		if(idDispositivo == 2){
			processos = filaES2.get(0);
		}else if(idDispositivo == 3){
			processos = filaES3.get(0);
		}else{
		processos = filaES4.get(0);
		}
		return (OperacaoES)(processos.codigo[processos.contadorDePrograma]);
	
	}
	
	protected void verificaOperacaoESAcabou() {
		if(filaES0.size()>0){
		if(filaES0.get(0).codigo[filaES0.get(0).contadorDePrograma].ciclos==0){
		PCB aux = filaES0.get(0);
		filaES0.remove(0);
		aux.contadorDePrograma++;
		prontoList.add(aux);
		processosProntos.add(aux.id);
		}
						
			
		}
		if(filaES1.size()>0){
			if(filaES1.get(0).codigo[filaES1.get(0).contadorDePrograma].ciclos==0){
		PCB aux = filaES1.get(0);
				filaES1.remove(0);
		aux.contadorDePrograma++;
		prontoList.add(aux);
		processosProntos.add(aux.id);
		}
		if(filaES2.size()>0){
			if(filaES2.get(0).codigo[filaES2.get(0).contadorDePrograma].ciclos==0){
		PCB aux = filaES2.get(0);
				filaES2.remove(0);
		aux.contadorDePrograma++;
		prontoList.add(aux);
		processosProntos.add(aux.id);
		
		}
		if(filaES3.size()>0){
		if(filaES3.get(0).codigo[filaES3.get(0).contadorDePrograma].ciclos==0){
		PCB aux = filaES3.get(0);
			filaES3.remove(0);
		aux.contadorDePrograma++;
		prontoList.add(aux);
		processosProntos.add(aux.id);
		
		}
			
		if(filaES4.get(0).codigo[filaES4.get(0).contadorDePrograma].ciclos==0){
			PCB aux = filaES4.get(0);
			filaES4.remove(0);
			aux.contadorDePrograma++;
			
			if(aux.codigo[aux.contadorDePrograma] instance of OperacaoES){
				adicionaFilaES(aux.codigo[aux.contadorDePrograma].idDispositivo, aux);
			}else{
				aux.momentoPronto = contadorCiclos;
				prontoList.add(aux);
				processosProntos.add(aux.id);
			}
	}

	@Override
	protected Operacao proximaOperacaoCPU() {
		return operacaoCPU;
	}

	@Override
	protected void executaCicloKernel() {
		PCB processo = processoAtual;
		
		if(filaES0 != null){
			// pegar os valores que estao na fila e colocar na fila de pronto se tiverem concluido			
		}


		if(processo != null){
			if(processoAtual.codigo.length == processoAtual.contadorDePrograma){
				//Termina o processo atual 
				processosTerminados.add(processoAtual.idProcesso);
				trocaContexto(processoAtual, prontoList.get(0));
				prontoList.remove(0);
				operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
				processoAtual.contadorDePrograma ++;

			}else if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES){
				//Troca de contexto pra abrir espaco para operacao ES
				OperacaoES aux =(OperacaoES)processoAtual.codigo[processoAtual.contadorDePrograma];
				processosEsperando.add(processoAtual.idProcesso);
				adicionaFilaES(aux.idDispositivo, processoAtual);
				trocaContexto(processoAtual, prontoList.get(0));
				prontoList.remove(0);			
			
			}else {
				//Vai pra proxima execução
				operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
				processoAtual.contadorDePrograma ++;
			}

		}else{
			if(prontoList.size() > 0){
				processoAtual = prontoList.get(0);
				prontoList.remove(0);
				operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
			}
		}

		if(processoNovo != null){
			processoNovo.momentoPronto = contadorCiclos;
			prontoList.add(processoNovo);
			processosProntos.add(idProcessoNovo);
			processoNovo = null;
		}

		contadorCiclos++; 
		
	}

	protected void adicionaFilaES(int idDispositivo, PCB processo){
		if(idDispositivo == 0){
			filaES0.add(processo);
		}else		if(idDispositivo == 1){
			filaES1.add(processo);

		}else		if(idDispositivo == 2){
			filaES2.add(processo);
			
		}else if(idDispositivo == 3){
			filaES3.add(processo);
		}else{
			filaES4.add(processo);
		}
	}

	@Override
	protected boolean temTarefasPendentes(){
		if(processosEsperando.size()==0 && processoAtual == null)
		return false;
		return true;
	}

	@Override
	protected Integer idProcessoNovo() {
		return processoNovo.idProcesso;
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

package kernel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import operacoes.Operacao;
import operacoes.OperacaoES;

public class SeuSO extends SO{
	private List<Integer> processosTerminados = new ArrayList<Integer>();
	private static List<Integer> processosProntos = new ArrayList<Integer>();
	private Integer processosExecutando;
	private List<Integer> processosEsperando = new ArrayList<Integer>();
	
	private Integer idProcessoNovo;
	private Integer trocasDeContexto = 0;
	private Escalonador esquemaEscalonador;
	private int contadorCiclos =0; 

	private PCB processoAtual;
	private PCB processoNovo;
	private PCB processoEsperaNovo;
	private Operacao operacaoCPU;

	private List<PCB> prontoList = new ArrayList<PCB>();

	private List<PCB> filaES0 = new ArrayList<>();
	private List<PCB> filaES1 = new ArrayList<>();
	private List<PCB> filaES2 = new ArrayList<>();
	private List<PCB> filaES3 = new ArrayList<>();
	private List<PCB> filaES4 = new ArrayList<>();

	private OperacaoES operacaoES0;
	private OperacaoES operacaoES1;
	private OperacaoES operacaoES2;
	private OperacaoES operacaoES3;
	private OperacaoES operacaoES4;


	@Override
	// ATENCÃO: cria o processo mas o mesmo 
	// só estará "pronto" no próximo ciclo
	protected void criaProcesso(Operacao[] codigo) {
		PCB novoProcesso = new PCB();
		novoProcesso.idProcesso = contadorCiclos;
		novoProcesso.codigo = codigo;
		novoProcesso.contadorDePrograma = 0;
        processoEsperaNovo = novoProcesso;
		idProcessoNovo = processoEsperaNovo.idProcesso;
	}


	@Override
	protected void trocaContexto(PCB pcbAtual, PCB pcbProximo) {
	     	processoAtual = pcbProximo;
		trocasDeContexto ++;
	}

	@Override
	protected OperacaoES proximaOperacaoES(int idDispositivo) {
		if(idDispositivo == 0){
			return operacaoES0;
		}else if(idDispositivo == 1){
			return operacaoES1;
		}else	if(idDispositivo == 2){
			return operacaoES2;
		}else if(idDispositivo == 3){
			return operacaoES3;
		}else{
			return operacaoES4;
		}
	}
	
	protected boolean proxOperacaoehES(PCB processo){
		if(processo.codigo[processo.contadorDePrograma] instanceof OperacaoES) return true;
		return false;

	}


	protected void verificaOperacaoESAcabou() {
		if (operacaoES0 != null && operacaoES0.ciclos == 0){
				PCB x = filaES0.get(0);
				x.contadorDePrograma ++;
				filaES0.remove(0);
				int y =processosEsperando.indexOf(x.idProcesso);
				processosEsperando.remove(y);

				if(x.contadorDePrograma == x.codigo.length){
					processosTerminados.add(x.idProcesso);
				}else
			if(x.codigo[x.contadorDePrograma] instanceof OperacaoES){
				OperacaoES aux = (OperacaoES)x.codigo[x.contadorDePrograma];
				adicionaFilaES(aux.idDispositivo, x);
			}else{
				x.momentoPronto = contadorCiclos;
				prontoList.add(x);
			}
         
			if(!filaES0.isEmpty()){
				operacaoES0 = (OperacaoES)filaES0.get(0).codigo[filaES0.get(0).contadorDePrograma];
			}else{
				operacaoES0 = null;
			}
		}

		if (operacaoES1 != null && operacaoES1.ciclos == 0){
			PCB x = filaES1.get(0);
				x.contadorDePrograma = x.contadorDePrograma+1;
				filaES1.remove(0);
				int y =processosEsperando.indexOf(x.idProcesso);
				processosEsperando.remove(y);
				if(x.contadorDePrograma == x.codigo.length){
					processosTerminados.add(x.idProcesso);
				}else
			if(x.codigo[x.contadorDePrograma] instanceof OperacaoES){
				OperacaoES aux = (OperacaoES)x.codigo[x.contadorDePrograma];
				adicionaFilaES(aux.idDispositivo, x);
			}else{
				x.momentoPronto = contadorCiclos;
				prontoList.add(x);
			}
         
			if(!filaES1.isEmpty()){
				operacaoES1 = (OperacaoES)filaES1.get(0).codigo[filaES1.get(0).contadorDePrograma];
			}else{
				operacaoES1 = null;
			}
		}


		if (operacaoES2 != null && operacaoES2.ciclos == 0){
			PCB x = filaES2.get(0);
				x.contadorDePrograma = x.contadorDePrograma +1;
				filaES2.remove(0);
				int y =processosEsperando.indexOf(x.idProcesso);
				processosEsperando.remove(y);
			if(x.contadorDePrograma == x.codigo.length){
				processosTerminados.add(x.idProcesso);
			}else
			if(x.codigo[x.contadorDePrograma] instanceof OperacaoES){
				OperacaoES aux = (OperacaoES)x.codigo[x.contadorDePrograma];
				adicionaFilaES(aux.idDispositivo, x);
			}else{
				x.momentoPronto = contadorCiclos;
				prontoList.add(x);
			}
         
			if(!filaES2.isEmpty()){
				operacaoES2 = (OperacaoES)filaES2.get(0).codigo[filaES2.get(0).contadorDePrograma];
			}else{
				operacaoES2 = null;
			}
		}



		if (operacaoES3 != null && operacaoES3.ciclos == 0){
			PCB x = filaES3.get(0);
				x.contadorDePrograma ++;
				filaES3.remove(0);
				int y =processosEsperando.indexOf(x.idProcesso);
				processosEsperando.remove(y);
				if(x.contadorDePrograma == x.codigo.length){
					processosTerminados.add(x.idProcesso);
				}else
			if(x.codigo[x.contadorDePrograma] instanceof OperacaoES){
				OperacaoES aux = (OperacaoES)x.codigo[x.contadorDePrograma];
				adicionaFilaES(aux.idDispositivo, x);
			}else{
				x.momentoPronto = contadorCiclos;
				prontoList.add(x);
			}
         
			if(!filaES3.isEmpty()){
				operacaoES3 = (OperacaoES)filaES3.get(0).codigo[filaES3.get(0).contadorDePrograma];
			}else{
				operacaoES3 = null;
			}
		}
		

		if (operacaoES4 != null && operacaoES4.ciclos == 0){
			PCB x = filaES4.get(0);
				x.contadorDePrograma ++;
				filaES4.remove(0);
				int y =processosEsperando.indexOf(x.idProcesso);
				processosEsperando.remove(y);

				if(x.contadorDePrograma == x.codigo.length){
					processosTerminados.add(x.idProcesso);
				}else
			if(x.codigo[x.contadorDePrograma] instanceof OperacaoES){
				OperacaoES aux = (OperacaoES)x.codigo[x.contadorDePrograma];
				adicionaFilaES(aux.idDispositivo, x);
			}else{
				x.momentoPronto = contadorCiclos;
				prontoList.add(x);
			}
         
			if(!filaES4.isEmpty()){
				operacaoES4 = (OperacaoES)filaES4.get(0).codigo[filaES4.get(0).contadorDePrograma];
			}else{
				operacaoES4 = null;
			}
		}


		// if(operacaoES0 == null && !filaES0.isEmpty())operacaoES0 = (OperacaoES)filaES0.get(0).codigo[filaES0.get(0).contadorDePrograma];
		if(operacaoES1 == null && !filaES1.isEmpty())operacaoES1 = (OperacaoES)filaES1.get(0).codigo[filaES1.get(0).contadorDePrograma];
		if(operacaoES2 == null && !filaES2.isEmpty())operacaoES2 = (OperacaoES)filaES2.get(0).codigo[filaES2.get(0).contadorDePrograma];
		
		// if(operacaoES3 == null && !filaES3.isEmpty())operacaoES3 = (OperacaoES)filaES3.get(0).codigo[filaES3.get(0).contadorDePrograma];
		
		// if(operacaoES4 == null && !filaES4.isEmpty())operacaoES4 = (OperacaoES)filaES4.get(0).codigo[filaES4.get(0).contadorDePrograma];
			
			

	}


	@Override
	protected Operacao proximaOperacaoCPU() {
		return operacaoCPU;
	}

	protected void verificaDestino(PCB processo){

	}

	@Override
	protected void executaCicloKernel() {
		PCB processo = processoAtual;
		if(processoNovo != null){
			if(processoNovo.codigo[processoNovo.contadorDePrograma] instanceof OperacaoES){
				OperacaoES aux =(OperacaoES)processoNovo.codigo[processoNovo.contadorDePrograma];
				processosEsperando.add(processoNovo.idProcesso);
				adicionaFilaES(aux.idDispositivo, processoNovo);
				processoNovo = null;
			}else{
			processoNovo.momentoPronto = contadorCiclos;
			prontoList.add(processoNovo);
			processosProntos.add(processoNovo.idProcesso);
			processoNovo = null;
			}
		}

		


		if(processo != null){
			if(processoAtual.codigo.length == processoAtual.contadorDePrograma){
				//Termina o processo atual 
				processosTerminados.add(processoAtual.idProcesso);
				if(!prontoList.isEmpty()){
					trocaContexto(processoAtual, prontoList.get(0));
					operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
					prontoList.remove(0);
				}
				else{ processoAtual = null;
				}

			}else{ 
				if (processoAtual.codigo[processoAtual.contadorDePrograma] instanceof OperacaoES){
				//Troca de contexto pra abrir espaco para operacao ES
				OperacaoES aux =(OperacaoES)processoAtual.codigo[processoAtual.contadorDePrograma];
				processosEsperando.add(processoAtual.idProcesso);
				adicionaFilaES(aux.idDispositivo, processoAtual);
				if (!prontoList.isEmpty()){
				trocaContexto(processoAtual, prontoList.get(0));	
				prontoList.remove(0);
				}else{
					processoAtual = null;
				}
			}else {
				//Vai pra proxima execução
				operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
				processoAtual.contadorDePrograma =  processoAtual.contadorDePrograma+1;
			}
		}
		}

		else{
			if(!prontoList.isEmpty()){
				processoAtual = prontoList.get(0);
				prontoList.remove(0);
				operacaoCPU = processoAtual.codigo[processoAtual.contadorDePrograma];
				processoAtual.contadorDePrograma =  processoAtual.contadorDePrograma+1;
			}else{
				processoAtual = null;
			}
		}

		if(processoEsperaNovo !=null){
			processoNovo = processoEsperaNovo;
			processoEsperaNovo = null;
		}else{
			idProcessoNovo = null;
		}
		verificaOperacaoESAcabou();

		

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
		if(processosProntos.isEmpty() && processoAtual == null && processosEsperando.isEmpty() && processoNovo == null){
			return false;
		}
		else{
					return true;
	}
	}

	@Override
	protected Integer idProcessoNovo() {
		return idProcessoNovo;	
	}

	@Override
	protected List<Integer> idProcessosProntos() {
		processosProntos.clear();
		for (PCB x : prontoList){
			processosProntos.add(x.idProcesso);
		}
		
		return processosProntos;
	}

	@Override
	protected Integer idProcessoExecutando() {
		if (processoAtual != null) return processoAtual.idProcesso;
		return null;
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

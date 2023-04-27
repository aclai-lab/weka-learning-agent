/** Librerie */
import java.io.*;
import java.text.DecimalFormat;

import weka.core.*;
import weka.classifiers.*;

public class Agent {
	
	/** Minimo numero di istanze prima di classificare. */
	protected static final int MININSTANCES = 3;
	
	/**
	 * Nome del agente intelligente.
	 */
	protected String m_agentName;
	
	/**
	 * Problema dell'agente.
	 */
	protected Problem m_problem;
	
	/**
	 * Metodo di apprendimento per la classificazione.
	 */
	protected Classifier m_classifier;
	
	/**
	 * Costruttore agente.
	 * 
	 * @param agentName nome del agente
	 * @param problem oggeto problema
	 * @param classifier metodo di classificazione
	 */
	public Agent(String agentName, Problem problem, Classifier classifier) {
		
		m_agentName = agentName;
		m_problem = problem;
		m_classifier = classifier;

		System.out.println("\n" + Colors.blue("sistema") + ": avvio dell'agente " + agentName + " in corso: attendere...");
		System.out.println(name() + ": Ciao! Sono " + m_agentName +
			" e sono qui per imparare tutto su " + Colors.blue(problem.getName()) + "! :)");
	}

	/**
	 * Ritorna il nome dell'agente colorato come stringa.
	 * @return
	 */
	private String name() {
		return Colors.cyan(getAgentName());
	}

	/**
	 * Funzione che fa imparare all'agente.
	 * 
	 * @throws Exception
	 */
	public void learn() throws Exception {
		
		/** Quante nuove istanze? */
		int numNewInstances = howManyNewInstances();
		/** Impara a predire le nuove istanze i cui valori devono essere ancora definiti. */
		learnToPredict(numNewInstances);
	}
	
	/**
	 * Funzione che chiede all'utente quante nuove istanze si vuol inserire. Ci sono due casi: <br>
	 * 1) Se non ci sono abbastanza istanze, allora l'utente deve inserire almeno il numero necessario <br>
	 * per avere il minimo numero di istanze per poter imparare. <br>
	 * 2) Se ci sono abbastanza istanze, allora l'utente deve dire che vuole almeno un'istanza.
	 * 
	 * @return numero di nuove istanze da inserire
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public int howManyNewInstances() throws NumberFormatException, IOException {
		
		/** Numero di nuove istanze. */
		int numNewInstances = 0;
		/** Lettore dalla console. */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		/** La definizione del problema. */
		Instances data = getProblemDefinition();
		
		/** Non ci sono abbastanza istanze */
		if (data.numInstances() < MININSTANCES) {
			
			System.out.println(name() + ": il problema ha " + data.numInstances() + " instanze; " +
					"ma ho bisogno di almeno " + MININSTANCES + " per poter imparare :(");
			
			while (true) {
				System.out.println(name() + ": quante nuove instanze vuoi inserire? " +
					"(almeno " + (MININSTANCES - data.numInstances()) + ")");
				InputManager.prompt();
				try {
					numNewInstances = InputManager.parseInt(reader.readLine());
				} catch (Exception e) {
					InputManager.error(e.getMessage());
					continue;
				}

				if (data.numInstances() + numNewInstances >= MININSTANCES)
					break;
			} 
		}
		/** Ci sono abbastanza istanze. */
		else {
			while (true) {
				System.out.println(name() + ": quante nuove instanze vuoi inserire? (almeno 1)");
				InputManager.prompt();
				try {
					numNewInstances = InputManager.parseInt(reader.readLine());
				} catch (Exception e) {
					InputManager.error(e.getMessage());
					continue;
				}

				if (numNewInstances > 0)
					break;

				InputManager.error("troppe poche istanze");
			} 
		}
		
		return numNewInstances;
	}
	
	/**
	 * Funzione che aggiunge istanze in maniera incrementale, e prova a predire la classe, se ci sono abbastanza istanze.
	 * 
	 * @param numNewInstances numero di istanze da aggiungere
	 * @throws Exception
	 */
	public void learnToPredict(int numNewInstances) throws Exception {
		
		/** Definizione del problema. */
		Instances data = getProblemDefinition();

		System.out.println(name() + ": bene! Aggiungiamo " + Colors.blue("" + numNewInstances) + " nuove istanze!");
		
		/** Ciclo per le nuove istanze. */
		for (; numNewInstances > 0; numNewInstances--) {
			
			/** Indice dell'attributo. */
			int attrIndex = 0;
			/** Valori della nuova instanza. */
			double[] instanceValues = new double[data.numAttributes()];
			
			System.out.println("○ nuova istanza:");
			/** Inserimento dei valori degli attributi, tranne quello della classe. */
			for (attrIndex = 0; attrIndex < data.numAttributes()-1; attrIndex++) 
				insertAttributeValue(instanceValues, attrIndex, attrIndex == data.numAttributes()-2, false);
			
			/** Non ci sono abbastanza istanze per predire. */
			if (data.numInstances() < MININSTANCES) {
				/** Inserimento della classe. */
				System.out.println("    │     └── " + name() + ": non posso ancora predire nulla perché ci sono poche istanze.");
				insertAttributeValue(instanceValues, attrIndex, true, true);
			}
			else {
				/** Prima di inserire la classe vera, proviamo a predire. */
				predict(new DenseInstance(1.0, instanceValues));
				/** Inserimento della classe vera. */
				insertAttributeValue(instanceValues, attrIndex, true, true);
			}
			
			/** Aggiungiamo la nuova istanza al problema. */
			getProblem().addInstance(new DenseInstance(1.0, instanceValues));
		}
	}
	
	/**
	 * Funzione che predice la classe della nuova istanza.
	 * 
	 * @param instanceToPredict istanza incompleta da predire
	 * @throws Exception
	 */
	public void predict(Instance instanceToPredict) throws Exception {
		
		/** Definizione del problema. */
		Instances data = getProblemDefinition();
		/** Costruiamo il modello con le istanze che abbiamo. */
		getClassifier().buildClassifier(data);
		/** Assegnamento dell'istanza al data set corrente (necessario per classificare). */
		instanceToPredict.setDataset(data);
		/** Risultato della classificazione. */
		double result = getClassifier().classifyInstance(instanceToPredict);
		/** Valutiamo l'albero. */
		Evaluation evaluation = new Evaluation(data);
		evaluation.evaluateModel(getClassifier(), data);
		/** Stampiamo il risultato. */
		System.out.println("    │     └── " + name() + ": credo che questa istanza sia " +
			Colors.blue(data.classAttribute().value((int) result)) +
			" e sono sicuro al "+ (new DecimalFormat("###.##")).format((1 - evaluation.errorRate())*100) + "%");
	}
	
	/**
	 * Funzione che aggiunge il valore ad un attributo.
	 * 
	 * @param instanceValues contenitore di valori dell'instanza
	 * @param attrIndex indice del valore da aggiungere
	 * @param isLast specifica se è l'ultimo attributo da inserire
	 * @param isClass specifica se si sta inserendo il valore di una classe
	 * @throws IOException
	 */
	public void insertAttributeValue(double[] instanceValues, int attrIndex, boolean isLast, boolean isClass)
												throws IOException {
		

		String c = "├";
		if (isLast)
			c = "└";

		String cl = "";
		if (isClass)
			cl = " (classe)";
	
		while (true) {
			try {
				/** Definizione del problema. */
				Instances data = getProblemDefinition();
				/** Lettore dalla console. */
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				
				/** Inserimento valore del attributo */
				System.out.print("    "+c+"── Inserisci " + data.attribute(attrIndex).name() + cl + ": ");
				String value = reader.readLine();
				
				/** Attributo numerico. */
				if (data.attribute(attrIndex).isNumeric())
					instanceValues[attrIndex] = InputManager.parseDouble(value);
				/** Attributo nominale. */
				else {
					int i = data.attribute(attrIndex).indexOfValue(value);

					if (i < 0) {
						System.err.println("    │     └── " + Colors.red("errore") + ": il valore \"" + value +
							"\" non appartiene al dominio di questo attributo");
						continue;
					}
					instanceValues[attrIndex] = i;
				}
				break;
			} catch (InputTypeException e) {
				System.err.println("    │     └── " + Colors.red("errore") + ": " + e.getMessage());
				continue;
			}
		}
	}
	
	/**
	 * Funzione che ritorna il nome dell'agente.
	 * 
	 * @return nome dell'agente
	 */
	public String getAgentName() {
		
		return m_agentName;
	}
	
	/**
	 * Funzione che ritorna l'oggeto del problema.
	 * 
	 * @return oggetto del problema
	 */
	public Problem getProblem() {
		
		return m_problem;
	}
	
	/**
	 * Funzione che ritorna il metodo di classificazione usato.
	 * 
	 * @return metodo di classificazione usato
	 */
	public Classifier getClassifier() {
		
		return m_classifier;
	}
	
	/**
	 * Funzione che ritorna la definizione del problema.
	 * 
	 * @return definizione del problema
	 */
	public Instances getProblemDefinition() {
		
		return m_problem.getProblemDefinition();
	}
}

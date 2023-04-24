/** Librerie */
import java.io.*;
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
			
			System.out.println("Il problema ha " + data.numInstances() + " instanze. " +
					"Bisogna aver almeno " + MININSTANCES + " per poter imparare.");
			
			do {
				System.out.println("Quante nuove instanze si vuol inserire? (NB: Devono essere almeno " + (MININSTANCES - data.numInstances()) + ")");
				numNewInstances = Integer.parseInt(reader.readLine());
			} 
			while(data.numInstances() + numNewInstances < MININSTANCES);
		}
		/** Ci sono abbastanza istanze. */
		else {
			do {
				System.out.println("Quante nuove instanze si vuol inserire? (NB: Deve essere almeno 1)");
				numNewInstances = Integer.parseInt(reader.readLine());
			} 
			while(numNewInstances < 1);
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
		
		/** Ciclo per le nuove istanze. */
		for (; numNewInstances > 0; numNewInstances--) {
			
			/** Indice dell'attributo. */
			int attrIndex = 0;
			/** Valori della nuova instanza. */
			double[] instanceValues = new double[data.numAttributes()];
			
			System.out.println("Nuova istanza -->");
			/** Inserimento dei valori degli attributi, tranne quello della classe. */
			for (attrIndex = 0; attrIndex < data.numAttributes()-1; attrIndex++) 
				insertAttributeValue(instanceValues, attrIndex);
			
			/** Non ci sono abbastanza istanze per predire. */
			if (data.numInstances() < MININSTANCES) {
				/** Inserimento della classe. */
				System.out.println("Non posso ancora predire nulla perché ci sono poche istanze.");
				insertAttributeValue(instanceValues, attrIndex);
			}
			else {
				/** Prima di inserire la classe vera, proviamo a predire. */
				predict(new DenseInstance(1.0, instanceValues));
				/** Inserimento della classe vera. */
				insertAttributeValue(instanceValues, attrIndex);
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
		System.out.println("Credo che questa istanza sia "+ data.classAttribute().value((int) result) + " e sono sicuro al "+ (1 - evaluation.errorRate())*100 + " percento");
	}
	
	/**
	 * Funzione che aggiunge il valore ad un attributo.
	 * 
	 * @param instanceValues contenitore di valori dell'instanza
	 * @param attrIndex indice del valore da aggiungere
	 * @throws IOException
	 */
	public void insertAttributeValue(double[] instanceValues, int attrIndex) throws IOException {
		
		/** Definizione del problema. */
		Instances data = getProblemDefinition();
		/** Lettore dalla console. */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		/** Inserimento valore del attributo */
		System.out.print("Inserisci " + data.attribute(attrIndex).name() + ": ");
		String value = reader.readLine();
		
		/** Attributo numerico. */
		if (data.attribute(attrIndex).isNumeric())
			instanceValues[attrIndex] = Double.parseDouble(value);
		/** Attributo nominale. */
		else 
			instanceValues[attrIndex] = data.attribute(attrIndex).indexOfValue(value);
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

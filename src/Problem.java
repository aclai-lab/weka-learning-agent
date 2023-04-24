/** Librerie */
import java.io.*;
import java.util.ArrayList;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;

public class Problem {
	
	/** Nome del problema. */
	protected String m_problemName;
	
	/** Nome del file del problema. */
	protected String m_problemFileName;
	
	/** File del problema. */
	protected File m_problemFile;
	
	/** Definizione del problema. */
	protected Instances m_data;
	
	/**
	 * Costruttore problema.
	 * 
	 * @throws Exception
	 */
	public Problem() throws Exception {
		
		/** Lettore dalla console. */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		/** Inserimento del nome del problema */
		System.out.println("Inserisci il nome del problema:");
		String problemName = reader.readLine();
		
		m_problemName = problemName;
		m_problemFileName = problemName + (problemName.endsWith(".arff") ? "" : ".arff");
		m_problemFile = new File(m_problemFileName);
		
		if (m_problemFile.isFile()) {
			System.out.println("Conosco questo problema!");
			loadProblem();
		}
		else {
			System.out.println("Non conosco questo problema!");
			initializeProblem();
		}
	}
	
	/**
	 * Funzione che recupera il problema (già esistente).
	 * 
	 * @throws Exception
	 */
	public void loadProblem() throws Exception {
		
		DataSource dataSource = new DataSource(m_problemFileName);
		m_data = dataSource.getDataSet();
		m_data.setClassIndex(m_data.numAttributes() - 1); 
	}
	
	/**
	 * Funzione che initializza un nuovo problema. <br>
	 * Viene chiesto il numero degli attributi (che deve essere un intero maggiore di 0). <br>
	 * Poi, per ogni attributo, viene chiesto il suo nome e il suo tipo.
	 * 
	 * @throws IOException
	 */
	public void initializeProblem() throws IOException {
		
		/** Numero degli attributi (all'inizio 0). */
		int numAttributes = 0;
		
		/** Lettore dalla console. */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		/** Lettura del numero degli attributi */		
		do {
			System.out.println("Inserire numero di attributi del problema (deve essere almeno 2): ");
			numAttributes = Integer.parseInt(reader.readLine());
		}
		while (numAttributes < 2);
		
		/** Attributi del problema. */
		ArrayList<Attribute> attributes = new ArrayList<Attribute>(numAttributes);
		
		System.out.println("\nInserire i nomi degli attributi e il loro tipo secondo la seguente sintassi\n" + 
				"\t- <nome> <numeric>, oppure\n" +
				"\t- <nome> <valore1, valore2, ...>\n\n" + 
				"\tAd esempio: \n" +
				"\t\tattr1 numeric\n" +
				"\t\tattr2 cane,gatto,topo\n");
		
		/** Ciclo per la definizione degli attributi. */
		for (int i=0; i<numAttributes; i++) {
			
			/** Linea in input. */
			String inputLine;
			
			/** Definizione dell'attributo. */
			System.out.print("Attributo numero " + (i+1) + ": ");
			
			/** Lettura della riga rimuovendo tutti gli spazi tranne uno tra le parole. */
			inputLine = reader.readLine().replaceAll("\\s{2,}", " ").trim();  
			
			/** Le parole della riga. */
			String[] words;
			words = inputLine.split(" "); 
			
			/** Attributo numerico */
			if (words[1].toLowerCase().equals("numeric")) 
				/** Aggiungiamo l'attributo numerico. */
				attributes.add(new Attribute(words[0]));	
			/** Attributo nominale */
			else {
				/** Contenitore dei valori nominali. */
				ArrayList<String> nominalValues = new ArrayList<String>();
				
				/** Le sottoparole che rappresentano i valori nominali. */
				String[] subWords = words[1].split(",");
				
				/** Ciclo per aggiungere i valori nominali. */
				for (int j=0; j<subWords.length; j++) 
					/** Se la sottoparola (= valore nominale) è non vuota, aggiungi. */
					if (!subWords[j].isEmpty()) nominalValues.add(subWords[j]);
				
				/** Aggiungiamo l'attributo nominale. */
				attributes.add(new Attribute(words[0],nominalValues));
			}
		}
		
		/** Definizione del problema. */
		m_data = new Instances(m_problemName,attributes,0);
		
		/** Supponiamo che la classe rappresentata dall'ultimo attributo. */
    	m_data.setClassIndex(m_data.numAttributes()-1); 
	}
	
	/**
	 * Funzione che salva il problema.
	 * 
	 * @throws IOException
	 */
	public void saveProblem() throws IOException {
		
		/** Scrittore su file. */
		BufferedWriter writer;
		
		if (m_problemFile.isFile()) m_problemFile.delete();
		
		m_problemFile.createNewFile();
		writer = new BufferedWriter(new FileWriter(m_problemFile));
		writer.write(m_data.toString());
		writer.flush();
		writer.close();
	}
	
	/**
	 * Funzione che ritorna la definizione del problema (insieme ai dati).
	 * 
	 * @return definizione del problema
	 */
	public Instances getProblemDefinition() {
		
		return m_data;
	}
	
	/**
	 * Funzione che aggiunge una nuova istanza al problema.
	 * 
	 * @param instance istanza da aggiungere
	 */
	public void addInstance(Instance instance) {
		
		m_data.add(instance);
	}
}

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
		InputManager.systemMessage("inserire il nome del problema:");
		InputManager.prompt();
		String problemName = reader.readLine();
		
		m_problemName = problemName;
		m_problemFileName = problemName + (problemName.endsWith(".arff") ? "" : ".arff");
		m_problemFile = new File(m_problemFileName);

		if (m_problemFile.isFile()) {
			InputManager.systemMessage("il problema è già presente in memoria");
			loadProblem();
		}
		else {
			InputManager.systemMessage("il problema non è presente in memoria");
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
		while (true) {
			try {
				InputManager.systemMessage("inserire il numero di attributi del problema (almeno 1): ");
				InputManager.prompt();
				numAttributes = InputManager.parseInt(reader.readLine()) + 1;

				if ((numAttributes >= 2))
					break;
				
				InputManager.error("troppi pochi attributi");
			} catch (InputTypeException e) {
				InputManager.error(e.getMessage());
				continue;
			}
		}
		
		/** Attributi del problema. */
		ArrayList<Attribute> attributes = new ArrayList<Attribute>(numAttributes);
		
		System.out.println();
		InputManager.systemMessage("inserire i nomi degli attributi e il loro tipo secondo la seguente sintassi\n" + 
				"\t- <nome> <numeric>, oppure\n" +
				"\t- <nome> <valore1, valore2, ...>\n\n" + 
				"\tAd esempio: \n" +
				"\t\tattr1 numeric\n" +
				"\t\tattr2 cane,gatto,topo\n");
		
		/** Ciclo per la definizione degli attributi. */
		for (int i=0; i<numAttributes; i++) {
			
			/** Linea in input. */
			String inputLine;
			InputManager.prompt();
			
			/** Definizione dell'attributo/classe. */
			if (i<numAttributes-1)
				System.out.print("attributo " + (i+1) + ": ");
			else
				System.out.print("classe: ");

			
			/** Lettura della riga rimuovendo tutti gli spazi tranne uno tra le parole. */
			inputLine = reader.readLine().replaceAll("\\s{2,}", " ").trim();  
			
			/** Le parole della riga. */
			String[] words;
			words = inputLine.split(" ");

			if (words.length > 2)
				InputManager.warn("sono stati passati " + words.length +
					" argomenti: quelli dal terzo in poi verranno ignorati");
			else if (words.length < 2) {
				InputManager.error("sono stati passati " + words.length +
					" argomenti; è necessario passarne esattamente 2");
				i--; continue;
			}

			/** Attributo numerico */
			if (words[1].toLowerCase().equals("numeric")) 
				/** Aggiungiamo l'attributo numerico. */
				attributes.add(new Attribute(words[0]));	
			/** Attributo nominale */
			else {
				/** Contenitore dei valori nominali. */
				ArrayList<String> nominalValues = new ArrayList<String>();
				
				try {
					/** Le sottoparole che rappresentano i valori nominali. */
					String[] subWords = InputManager.parseDomain(words[1]).split(",");
					
					/** Ciclo per aggiungere i valori nominali. */
					for (int j=0; j<subWords.length; j++) 
						/** Se la sottoparola (= valore nominale) è non vuota, aggiungi. */
						if (!subWords[j].isEmpty()) nominalValues.add(subWords[j]);
					
					/** Aggiungiamo l'attributo nominale. */
					attributes.add(new Attribute(words[0],nominalValues));
				} catch (InputTypeException e) {
					InputManager.error("\"" + words[1] + "\" non è un dominio valido");
					i--; continue;
				}
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

	/**
	 * Funzione che ritorna il nome del problema.
	 * 
	 * @return nome del problema
	 */
	public String getName() {
		
		return m_problemName;
	}
}

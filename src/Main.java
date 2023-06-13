import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		/** Setta il nome dell'utente. */
		InputManager.setName("luke");
		/** Creazione nuovo problema. */
		Problem problem = new Problem();
		/** Creazione nuovo agente. */
		Agent agent = new Agent("R2-D2", problem, new J48());
		/** Facciamo imparare all'agente. */
		agent.learn();
		/** Salviamo il problema. */
		problem.saveProblem();
	}
}

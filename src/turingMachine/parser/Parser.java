package turingMachine.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import turingMachine.logic.TuringMachine;

public class Parser {

	/**
	 * Parses a Turing machine definition file to a compiled Turing machine object. 
	 * @param turingMachineFile
	 * @return
	 * @throws IOException
	 */
	public static TuringMachine parseTuringMachineFile(File turingMachineFile) throws IOException {
		BufferedReader bufferedReader = null;
		TuringMachine turingMachine = null;
		try{
			bufferedReader = new BufferedReader(new FileReader(turingMachineFile));
			String readLine = bufferedReader.readLine();
			turingMachine = parseFirstTuringLine(readLine);
			while((readLine=bufferedReader.readLine())!=null)
			{
				parseTuringMachineLine(turingMachine,readLine);
			}
			turingMachine.compile();
		}finally{			
			if(bufferedReader != null){
				bufferedReader.close();
			}
		}
		return turingMachine;
	}


	/**
	 * @param machineDefinitionLine
	 * @return
	 */
	private static TuringMachine parseFirstTuringLine(String machineDefinitionLine) {
		String[] definitions = purify(machineDefinitionLine);
		return new TuringMachine(definitions[0],definitions[1],definitions[2],definitions[3],definitions[4],definitions[5],definitions[6],definitions[7]);
	}


	/**
	 * Removes not required braces and splits the definition in its core parts.
	 * @param definitionLine
	 * @return
	 */
	private static String[] purify(String definitionLine) {
		return definitionLine.replaceAll("[{}]", "").split("[=,]");
	}

	/**
	 * Puts the things needed from the definition into a touring machine. The touring machine still needs to be compiled after this.
	 * @param tm
	 * @param readLine
	 */
	private static void parseTuringMachineLine(TuringMachine tm, String readLine) {
		String[] definition = readLine.split("=",2);
		if(definition[0].equals(tm.getComputationsName()))
		{
			tm.getComputionDefinitions().add(definition[1].trim());
		}else if(definition[0].equals(tm.getEndStatesName()))
		{
			for (String endState : purify(definition[1])) {
				tm.getEndingStateDefinitions().add(endState.trim());
			}
		}else if(definition[0].equals(tm.getStatesName()))
		{
			for (String state : purify(definition[1])) {
				tm.getStateDefinitions().add(state.trim());
			}
		}
	}
	
	
}

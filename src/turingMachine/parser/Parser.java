package turingMachine.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import turingMachine.logic.TuringMachine;

public class Parser {

	public static void parseTuringMachineFile(File turingMachineFile) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(turingMachineFile));
		String readLine = bufferedReader.readLine();
		TuringMachine tm = parseFirstTuringLine(readLine);
		while((readLine=bufferedReader.readLine())!=null)
		{
			parseTuringMachineLine(tm,readLine);
		}
		tm.compile();
	}


	private static TuringMachine parseFirstTuringLine(String machineDefinitionLine) {
		String[] definitions = purify(machineDefinitionLine);
		return new TuringMachine(definitions[0],definitions[1],definitions[2],definitions[3],definitions[4],definitions[5],definitions[6],definitions[7]);
	}


	private static String[] purify(String definitionLine) {
		return definitionLine.replace("[\\{\\}]", "").split("[=,]");
	}

	private static void parseTuringMachineLine(TuringMachine tm, String readLine) {
		String[] definition = readLine.split("=",2);
		if(definition[0].equals(tm.getComputationsName()))
		{
			tm.getComputionDefinitions().add(definition[1].trim());
		}else if(definition.equals(tm.getEndStatesName()))
		{
			for (String endState : purify(definition[1])) {
				tm.getEndingStateDefinitions().add(endState.trim());
			}
		}else if(definition.equals(tm.getStatesName()))
		{
			for (String state : purify(definition[1])) {
				tm.getStateDefinitions().add(state.trim());
			}
		}
	}
	
	
}

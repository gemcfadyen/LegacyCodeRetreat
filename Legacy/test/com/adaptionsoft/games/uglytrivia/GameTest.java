package com.adaptionsoft.games.uglytrivia;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;

public class GameTest {

	@Ignore
	@Test
	public void shouldRegressionTestTheGame() throws IOException {
		List<Integer> seeds = generateSeeds();
		
		System.setOut(new PrintStream(goldenMasterFile()));
		for(Integer seed: seeds){
			shouldHaveAWinningOutcome(seed);
		}
		
		System.setOut(new PrintStream(actualTestResultsFile()));
		for(Integer seed: seeds){
			shouldHaveAWinningOutcome(seed);
		}

		String goldenMasterContents = FileUtils.readFileToString(goldenMasterFile());
		String testFileContents = FileUtils.readFileToString(actualTestResultsFile());
        assertEquals(goldenMasterContents, testFileContents);

	}

	@Test
	public void shouldAddAPlayerToTheGame(){
		Game game = new Game();
		game.add("TestUser");
		
		assertThat(game.players.size(), is(1));
		assertThat(game.places.length, is(6));
		assertThat(game.purses.length, is(6));
		assertThat(game.inPenaltyBox.length, is(6));
		Assert.assertEquals(0, game.places[0]);
		Assert.assertEquals(0, game.purses[0]);
		assertThat(game.inPenaltyBox[0], is(false));
	} 
	
	
	@Test
public void should	


	@Test
	public void shouldNotChangeBehaviour() throws IOException{
		List<Integer> seeds = generateSeeds();
		
		System.setOut(new PrintStream(actualTestResultsFile()));
		for(Integer seed: seeds){
			shouldHaveAWinningOutcome(seed);
		}

		String goldenMasterContents = FileUtils.readFileToString(goldenMasterFile());
		String testFileContents = FileUtils.readFileToString(actualTestResultsFile());
        assertEquals(goldenMasterContents, testFileContents);
	}



	private File actualTestResultsFile() {
		return new File("C:\\DevWork\\actualTestResults.txt");
	}



	private File goldenMasterFile() {
		return new File("C:\\DevWork\\goldenTestResults.txt");
	}
	
	

	private List<Integer> generateSeeds() {
		List<Integer> goldenMasterSeeds = new ArrayList<Integer>();
		for(int i=0; i<100;i++){
			System.out.println(i);
			goldenMasterSeeds.add(i);
		}
		
		return goldenMasterSeeds;
	}



	private File generateSeedsToFile() throws IOException {

		File seedFile = new File("C:\\DevWork\\seed.txt");

		List<Random> seedList = new ArrayList<Random>();

		if (!seedFile.exists()) {
			seedFile.createNewFile();

			for (int i = 0; i < 5; i++) {
				seedList.add(getNewRandomSeed(1));
			}

			FileWriter fstream = new FileWriter(seedFile);
			BufferedWriter out = new BufferedWriter(fstream);
			for (Random seed : seedList) {
				int randomSeed = seed.nextInt();
				System.out.println(randomSeed);
				out.write(Integer.toString(randomSeed) + ",");
			}
			out.close();
		}

		return seedFile;

	}

	private Random getNewRandomSeed(Integer seed) {
		return new Random(seed);
	}

	public void shouldHaveAWinningOutcome(Integer seed) {
	
		
		GameRunner.main(new String[]{Integer.toString(seed)});
}
}

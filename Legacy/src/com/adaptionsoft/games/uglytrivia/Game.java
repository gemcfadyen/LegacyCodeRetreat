package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    private LinkedList popQuestions = new LinkedList();
    private LinkedList scienceQuestions = new LinkedList();
    private LinkedList sportsQuestions = new LinkedList();
    private LinkedList rockQuestions = new LinkedList();
    
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;
    
    public Game(){
    	setupGame();
    }

	private void setupGame() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
	}

	private String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public void add(String playerName) {
	    addPlayerToGame(playerName);
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
	}

	private void addPlayerToGame(String playerName) {
		players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	}
	
	private int howManyPlayers() {
		return players.size();
	}
	
	
	private void movePlayerBy(int numberOfPlaces){
		places[currentPlayerIndex] = places[currentPlayerIndex] + numberOfPlaces;
	}

	public void roll(int roll) {
		displayCurrentPlayerAndRoll(roll);
		
		if (isInPenaltyBox()) {
			if (isEvenNumber(roll)) {
				setGettingOutOfPenaltyBoxTo(true);
				
				displayPlayerLeavingPenaltyBox();
				updatePlaceOfPlayerUsing(roll);
				
				displayCurrentPlayersLocationAndCategory();
				askQuestion();
			} else {
				displayPlayerStayingInPenaltyBox();
				setGettingOutOfPenaltyBoxTo(false);
				}
			 
		} else {
		
			updatePlaceOfPlayerUsing(roll);			
			displayCurrentPlayersLocationAndCategory();
			askQuestion();
		}
		
	}

	private void setGettingOutOfPenaltyBoxTo(final boolean value) {
		isGettingOutOfPenaltyBox = value;
	}

	private void updatePlaceOfPlayerUsing(int roll) {
		movePlayerBy(roll);
		if (placeOfPlayerIsHigherThan11()){
			movePlayerBy(-12);
		}
	}

	private void displayPlayerStayingInPenaltyBox() {
		System.out.println(players.get(currentPlayerIndex) + " is not getting out of the penalty box");
	}

	private void displayPlayerLeavingPenaltyBox() {
		System.out.println(players.get(currentPlayerIndex) + " is getting out of the penalty box");
	}

	private void displayCurrentPlayersLocationAndCategory() {
		System.out.println(players.get(currentPlayerIndex) 
				+ "'s new location is " 
				+ places[currentPlayerIndex]);
		System.out.println("The category is " + currentCategory());
	}

	private boolean placeOfPlayerIsHigherThan11() {
		return places[currentPlayerIndex] > 11;
	}

	private boolean isInPenaltyBox() {
		return inPenaltyBox[currentPlayerIndex];
	}

	private boolean isEvenNumber(int roll) {
		return roll % 2 != 0;
	}

	private void displayCurrentPlayerAndRoll(int roll) {
		System.out.println(players.get(currentPlayerIndex) + " is the current player");
		System.out.println("They have rolled a " + roll);
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
		if (places[currentPlayerIndex] == 0) return "Pop";
		if (places[currentPlayerIndex] == 4) return "Pop";
		if (places[currentPlayerIndex] == 8) return "Pop";
		if (places[currentPlayerIndex] == 1) return "Science";
		if (places[currentPlayerIndex] == 5) return "Science";
		if (places[currentPlayerIndex] == 9) return "Science";
		if (places[currentPlayerIndex] == 2) return "Sports";
		if (places[currentPlayerIndex] == 6) return "Sports";
		if (places[currentPlayerIndex] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				addCoinToPlayersPurse();
				displayContentsOfPlayersPurse();
				
				boolean winner = didPlayerWin();
				calculateNextPlayersIndex();
				
				return winner;
			} else {
				calculateNextPlayersIndex();
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			addCoinToPlayersPurse();
			displayContentsOfPlayersPurse();
			
			final boolean isWinner = didPlayerWin();
			calculateNextPlayersIndex();
			
			return isWinner;
		}
	}

	private void calculateNextPlayersIndex() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
	}

	private void displayContentsOfPlayersPurse() {
		System.out.println(players.get(currentPlayerIndex) 
				+ " now has "
				+ purses[currentPlayerIndex]
				+ " Gold Coins.");
	}

	private void addCoinToPlayersPurse() {
		purses[currentPlayerIndex]++;
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayerIndex)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayerIndex] = true;
		
		calculateNextPlayersIndex();
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayerIndex] == 6);
	}
}

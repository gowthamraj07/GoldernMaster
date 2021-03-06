package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private final PrintStream out;
    private ArrayList<String> players = new ArrayList<>();
    private int[] places = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private LinkedList<String> popQuestions = new LinkedList<>();

    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();
    private int currentPlayer = 0;

    private boolean isGettingOutOfPenaltyBox;
    public Game(PrintStream out) {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast("Rock Question " + i);
        }

        this.out = out;
    }

    public void add(String playerName) {
        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        printMessage(playerName + " was added");
        printMessage("They are player number " + players.size());
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        printMessage(players.get(currentPlayer) + " is the current player");
        printMessage("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            handlePenalty(roll);
            return;
        }

        calculateCurrentPlayerPlace(roll);
        printMessage(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
        printMessage("The category is " + currentCategory());
        askQuestion();
    }

    private void handlePenalty(int roll) {
        if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            printMessage(players.get(currentPlayer) + " is getting out of the penalty box");
            calculateCurrentPlayerPlace(roll);

            printMessage(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            printMessage("The category is " + currentCategory());
            askQuestion();
        } else {
            printMessage(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
        }
    }

    private void askQuestion() {
        if ("Pop".equals(currentCategory()))
            printMessage(popQuestions.removeFirst());
        if ("Science".equals(currentCategory()))
            printMessage(scienceQuestions.removeFirst());
        if ("Sports".equals(currentCategory()))
            printMessage(sportsQuestions.removeFirst());
        if ("Rock".equals(currentCategory()))
            printMessage(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                printMessage("Answer was correct!!!!");
                purses[currentPlayer]++;
                printMessage(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                calculateCurrentPlayer();

                return winner;
            } else {
                calculateCurrentPlayer();
                return true;
            }


        } else {

            printMessage("Answer was corrent!!!!");
            purses[currentPlayer]++;
            printMessage(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            calculateCurrentPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        printMessage("Question was incorrectly answered");
        printMessage(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        calculateCurrentPlayer();
        return true;
    }

    public LinkedList<String> getPopQuestions() {
        return popQuestions;
    }

    public LinkedList<String> getScienceQuestions() {
        return scienceQuestions;
    }

    public LinkedList<String> getSportsQuestions() {
        return sportsQuestions;
    }

    public LinkedList<String> getRockQuestions() {
        return rockQuestions;
    }

    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }

    private void printMessage(String message) {
        out.println(message);
    }

    private void calculateCurrentPlayerPlace(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11)
            places[currentPlayer] = places[currentPlayer] - 12;
    }

    private void calculateCurrentPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }
}

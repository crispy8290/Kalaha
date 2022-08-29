public class Player {
    private String playerName;
    private int playerWinCounter;

    // Constructor, pn is an alias for playerName
    Player(String pn) {
        this.playerName = pn;
        this.playerWinCounter = 0;
    }

    // Mutator method
    public String getPlayerName() {
        return playerName;
    }

    // Mutator method
    public int getPlayerWinCounter() {
        return playerWinCounter;
    }

    // Mutator method; pwc is an alias for playerWinCounter
    public void setPlayerWinCounter(int pwc) {
        this.playerWinCounter = pwc;
    }
}
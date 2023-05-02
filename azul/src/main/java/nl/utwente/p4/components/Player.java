package nl.utwente.p4.components;

public class Player {
    private PlayerBoard board;

    public PlayerBoard getBoard() {
        return board;
    }

    public void setBoard(PlayerBoard board) {
        this.board = board;
    }

    public Player() {
        this.board = new PlayerBoard();
    }
}

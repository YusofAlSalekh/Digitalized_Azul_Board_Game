public class EndGameandBonusPoints {
    public int calculateScore(Player player) {
        Tile[][] board = player.getBoard();
        int TotalBonusPoints = 0;
        int fullRowBonus = 0;
        int fullColumnBonus = 0;
        int colorBonus = 0;
    
        // Check for full rows and end the game if a row has exactly 5 tiles
        for(int i=0; i<board.length; i++) {
            int count = 0;
            for(int j=0; j<board[i].length; j++) {
                if(board[i][j] != null) {
                    count++;
                }
            }
            if(count == 5) {
                player.endGame();
                fullRowBonus += 2; // Add 2 points for each full row
            }
        }
    
        // Check for full columns
        for(int j=0; j<board[0].length; j++) {
            int count = 0;
            for(int i=0; i<board.length; i++) {
                if(board[i][j] != null) {
                    count++;
                }
            }
            if(count == 5) {
                fullColumnBonus += 7; // Add 7 points for each full column
            }
        }
    
        // Check for color sets
        for(Color color : Color.values()) {
            int count = 0;
            for(int i=0; i<board.length; i++) {
                for(int j=0; j<board[i].length; j++) {
                    if(board[i][j] != null && board[i][j].getColor() == color) {
                        count++;
                    }
                }
            }
            if(count == 5) {
                colorBonus += 10; // Add 10 points for each full color set
            }
        }
    
        // Add up all the bonuses and score
        TotalBonusPoints = fullRowBonus + fullColumnBonus + colorBonus;
        player.addToScore(TotalBonusPoints);
    
        return TotalBonusPoints;
    }
    
}

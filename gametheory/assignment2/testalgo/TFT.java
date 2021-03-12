package gametheory.assignment2.testalgo;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * Implementation of Player for Moose's game
 * Test section. At start it choose the field
 * randomly. After that it starts to repeat
 * opponent's last move.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
public class TFT implements Player {

    Random randomizer = new Random();

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {

    }

    /**
     * This method returns the move of the player based on
     * the last move of the opponent and X values of all fields.
     * Initially, X for all fields is equal to 1 and last opponent
     * move is equal to 0
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     *         and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0){
            return randomizer.nextInt(3) + 1;
        } else {
            return opponentLastMove;
        }
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    @Override
    public String getEmail() {
        return "m.evgrafov@innopolis.university";
    }
}


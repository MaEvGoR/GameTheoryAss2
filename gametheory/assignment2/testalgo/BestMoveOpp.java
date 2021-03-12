package gametheory.assignment2.testalgo;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementation of Player for Moose's game
 * Test section. It always choose the field
 * with the highest coefficient.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
public class BestMoveOpp implements Player {

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
        List<Integer> xs = new ArrayList<>();
        xs.add(xA);
        xs.add(xB);
        xs.add(xC);

        List<Integer> best = max_index(xs);

        return best.get(randomizer.nextInt(best.size())) + 1;
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

    /**
     * This method returns indexes of items with the biggest
     * value from the list.
     *
     * @param xs list in which the search will be made
     * @return list that contains integers - indexes
     *                   where the biggest numbers from
     *                   the list are
     */
    private List<Integer> max_index(List<Integer> xs) {
        int max_x = Collections.max(xs);
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < xs.size(); i++) {
            if (xs.get(i) == max_x){
                indexes.add(i);
            }
        }
        return indexes;
    }
}

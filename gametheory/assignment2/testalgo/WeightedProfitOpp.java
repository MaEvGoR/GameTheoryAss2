package gametheory.assignment2.testalgo;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * Implementation of Player for Moose's game.
 * It chooses randomly between the fields taking
 * into consideration future payoff.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
public class WeightedProfitOpp implements Player {

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

        double A_payoff = payoff(xA);
        double B_payoff = payoff(xB);
        double C_payoff = payoff(xC);

        double payoffs_sum = A_payoff + B_payoff + C_payoff;

        long A_weight = Math.round((A_payoff/payoffs_sum)*100);
        long B_weight = Math.round((B_payoff/payoffs_sum)*100);
        long C_weight = Math.round((C_payoff/payoffs_sum)*100);


        int random_choice = randomizer.nextInt(100);

        if (random_choice < A_weight){
            return 1;
        }else if (A_weight <= random_choice && A_weight + B_weight > random_choice) {
            return 2;
        } else {
            return 3;
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

    /**
     * This method returns the payoff that will be
     * generated for an Agent from field with specific
     * X value.
     *
     * @param xField X value for the field.
     *
     * @return payoff for field with coefficient X.
     */
    private double payoff(int xField) {
        return f(xField) - f(0);
    }

    /**
     * This method returns the profit that will be
     * generated from field with specific X value.
     *
     * @param x X value for the field.
     *
     * @return profit for field with coefficient X.
     */
    private double f(int x) {
        return ((10 * Math.exp(x))/(1 + Math.exp(x)));
    }
}

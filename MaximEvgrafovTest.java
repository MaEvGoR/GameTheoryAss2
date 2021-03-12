import gametheory.assignment2.Player;
import gametheory.assignment2.students2021.MaximEvgrafovCode;
//import gametheory.assignment2.testalgo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementation of Tournament Simulation
 * for Moose Game.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
public class MaximEvgrafovTest {

    /**
     * This method returns the payoff that will be
     * generated for an Agent from field with specific
     * X value.
     *
     * @param xField X value for the field.
     * @return payoff for field with coefficient X.
     */
    private static double payoff(int xField) {
        return f(xField) - f(0);
    }

    /**
     * This method returns the profit that will be
     * generated from field with specific X value.
     *
     * @param x X value for the field.
     * @return profit for field with coefficient X.
     */
    private static double f(int x) {
        return ((10 * Math.exp(x)) / (1 + Math.exp(x)));
    }

    /**
     * This method returns size of the string from
     * the list with maximum length.
     *
     * @param list List that contains Strings
     *             for analyzing.
     * @return Integer, maximum string length from
     * the list
     */
    private static int MaxStringLength(List<String> list) {
        int maxlength = 0;
        for (String str : list) {
            maxlength = Math.max(maxlength, str.length());
        }
        return maxlength;
    }

    /**
     * This method generate string with provided
     * target length by concatenating it with
     * different number of spaces. Also, it's able to align
     * text.
     *
     * @param raw_text      text that should be
     *                      inside the resulting
     *                      string.
     * @param target_length target length of
     *                      the resulting string.
     * @param align         specify where the raw_text
     *                      should be located inside
     *                      the result. Can be "left", "right"
     *                      or "center".
     * @return String with length target_length.
     */
    private static String FitString(String raw_text, int target_length,
                                    String align) {
        String result = raw_text;

        switch (align) {
            case "left":
                while (result.length() < target_length) {
                    result = result.concat(" ");
                }
                break;
            case "right":
                while (result.length() < target_length) {
                    result = " ".concat(result);
                }
                break;
            case "center":
                float target_add = target_length - raw_text.length();
                result = FitString(raw_text, Math.round(target_add / 2), "right");
                result = FitString(result, target_length - result.length(), "left");
                break;
            default:
                result = FitString("ERROR", target_length, "center");
                break;
        }

        return result;
    }

    /**
     * This finction is used for printing the
     * scoreboard from the Moose game tournament.
     *
     * @param scoreboard two-dimensional list with
     *                   doubles inside (NxN).
     * @param labels     list that contains names of
     *                   the players (size - N)
     */
    private static void PrintScoreboard(List<List<Double>> scoreboard, List<String> labels) {
        int NumPlayers = labels.size();
        int column_width = MaxStringLength(labels);

        List<String> first_row_list = new ArrayList<>();
        first_row_list.add(FitString("", column_width, "left"));
        for (String label : labels) {
            first_row_list.add(FitString(label, column_width, "left"));
        }

        System.out.println(String.join(" | ", first_row_list));

        List<String> row_list;
        for (int i = 0; i < NumPlayers; i++) {
            row_list = new ArrayList<>();
            row_list.add(first_row_list.get(i + 1));

            for (int j = 0; j < NumPlayers; j++) {
                String item = String.format("%.1f", scoreboard.get(i).get(j));
                row_list.add(FitString(item, column_width, "left"));
            }

            System.out.println(String.join(" | ", row_list));
        }
    }

    public static void main(String[] args) {

        List<Player> TournamentPlayers = new ArrayList<>();

        TournamentPlayers.add(new MaximEvgrafovCode());
        TournamentPlayers.add(new MaximEvgrafovCode());
        TournamentPlayers.add(new HolderOpp());
        TournamentPlayers.add(new HolderOpp());
        TournamentPlayers.add(new BestMoveOpp());
        TournamentPlayers.add(new BestMoveOpp());
        TournamentPlayers.add(new WeightedXOpp());
        TournamentPlayers.add(new WeightedXOpp());
        TournamentPlayers.add(new WeightedProfitOpp());
        TournamentPlayers.add(new WeightedProfitOpp());
        TournamentPlayers.add(new RandomOpp());
        TournamentPlayers.add(new RandomOpp());
        TournamentPlayers.add(new SecondBestOpp());
        TournamentPlayers.add(new SecondBestOpp());
        TournamentPlayers.add(new TFT());
        TournamentPlayers.add(new TFT());

        List<String> TournamentLabels = new ArrayList<>();
        TournamentLabels.add("MyAgent1");
        TournamentLabels.add("MyAgent2");
        TournamentLabels.add("Holder1");
        TournamentLabels.add("Holder2");
        TournamentLabels.add("BestMove1");
        TournamentLabels.add("BestMove2");
        TournamentLabels.add("WeightedX1");
        TournamentLabels.add("WeightedX2");
        TournamentLabels.add("WeightedProfit1");
        TournamentLabels.add("WeightedProfit2");
        TournamentLabels.add("Random1");
        TournamentLabels.add("Random2");
        TournamentLabels.add("SecondBest1");
        TournamentLabels.add("SecondBest1");
        TournamentLabels.add("TFT1");
        TournamentLabels.add("TFT2");

        int NumPlayers = TournamentPlayers.size();

        List<List<Double>> scoreboard = new ArrayList<>();
        for (int i = 0; i < NumPlayers; i++) {
            scoreboard.add(new ArrayList<>());
            for (int j = 0; j < NumPlayers; j++) {
                scoreboard.get(i).add(0.0);
            }
        }

        int rounds_num = 100;

        for (int pl1_idx = 0; pl1_idx < NumPlayers; pl1_idx++) {
            for (int pl2_idx = pl1_idx + 1; pl2_idx < NumPlayers; pl2_idx++) {
                Player player1 = TournamentPlayers.get(pl1_idx);
                Player player2 = TournamentPlayers.get(pl2_idx);

                player1.reset();
                player2.reset();

                double player1_score = 0.0;
                double player2_score = 0.0;

                List<Integer> Fields_xs = new ArrayList<>();
                Fields_xs.add(1);
                Fields_xs.add(1);
                Fields_xs.add(1);

                int player1_move;
                int player2_move;

                int player1_prev = 0;
                int player2_prev = 0;

                double player1_payoff;
                double player2_payoff;

                for (int round_idx = 0; round_idx < rounds_num; round_idx++) {
                    player1_move = player1.move(player2_prev,
                            Fields_xs.get(0),
                            Fields_xs.get(1),
                            Fields_xs.get(2));

                    player2_move = player2.move(player1_prev,
                            Fields_xs.get(0),
                            Fields_xs.get(1),
                            Fields_xs.get(2));

                    player1_prev = player1_move;
                    player2_prev = player2_move;

                    if (player1_move == player2_move) {
                        player1_payoff = 0.0;
                        player2_payoff = 0.0;

                        for (int j = 0; j < 3; j++) {
                            if (j != player1_move - 1) {
                                Fields_xs.set(j, Fields_xs.get(j) + 1);
                            } else {
                                if (Fields_xs.get(j) != 0) {
                                    Fields_xs.set(j, Fields_xs.get(j) - 1);
                                }
                            }
                        }

                    } else {

                        player1_payoff = payoff(Fields_xs.get(player1_move - 1));
                        player2_payoff = payoff(Fields_xs.get(player2_move - 1));

                        Fields_xs.set(player1_move - 1, Math.max(Fields_xs.get(player1_move - 1) - 1, 0));
                        Fields_xs.set(player2_move - 1, Math.max(Fields_xs.get(player2_move - 1) - 1, 0));
                        Fields_xs.set(5 - player1_move - player2_move, Fields_xs.get(5 - player1_move - player2_move) + 1);
                    }
                    player1_score += player1_payoff;
                    player2_score += player2_payoff;
                }

                scoreboard.get(pl1_idx).set(pl2_idx, player1_score);
                scoreboard.get(pl2_idx).set(pl1_idx, player2_score);
            }
        }
        PrintScoreboard(scoreboard, TournamentLabels);
    }
}

/**
 * Implementation of Player for Moose's game.
 * It chooses randomly between the fields taking
 * into consideration future payoff.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
class WeightedProfitOpp implements Player {

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
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        double A_payoff = payoff(xA);
        double B_payoff = payoff(xB);
        double C_payoff = payoff(xC);

        double payoffs_sum = A_payoff + B_payoff + C_payoff;

        long A_weight = Math.round((A_payoff / payoffs_sum) * 100);
        long B_weight = Math.round((B_payoff / payoffs_sum) * 100);
        long C_weight = Math.round((C_payoff / payoffs_sum) * 100);


        int random_choice = randomizer.nextInt(100);

        if (random_choice < A_weight) {
            return 1;
        } else if (A_weight <= random_choice && A_weight + B_weight > random_choice) {
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
     * @return profit for field with coefficient X.
     */
    private double f(int x) {
        return ((10 * Math.exp(x)) / (1 + Math.exp(x)));
    }
}

/**
 * Implementation of Player for Moose's game
 * Test section. At start it choose the field
 * randomly. After that it starts to repeat
 * opponent's last move.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
class TFT implements Player {

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
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
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

/**
 * Implementation of Player for Moose's game.
 * It chooses randomly between the fields taking
 * into consideration their coefficients.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
class WeightedXOpp implements Player {

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
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int A_payoff = xA * xA;
        int B_payoff = xB * xB;
        int C_payoff = xC * xC;

        double payoffs_sum = A_payoff + B_payoff + C_payoff;

        long A_weight = Math.round((A_payoff / payoffs_sum) * 100);
        long B_weight = Math.round((B_payoff / payoffs_sum) * 100);
        long C_weight = Math.round((C_payoff / payoffs_sum) * 100);


        int random_choice = randomizer.nextInt(100);

        if (random_choice < A_weight) {
            return 1;
        } else if (A_weight <= random_choice && A_weight + B_weight > random_choice) {
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
}

/**
 * Implementation of Player for Moose's game
 * Test section. It randomly choose between
 * the fields.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
class RandomOpp implements Player {

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
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return randomizer.nextInt(3) + 1;
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

/**
 * Implementation of Player for Moose's game
 * Test section. The strategy is to hold
 * the field with the highest coefficient
 * until it equals to 0.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
class HolderOpp implements Player {

    List<Integer> myMoves = new ArrayList<>();
    int mylastmove = 0;
    Random randomizer = new Random();

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        this.mylastmove = 0;
        this.myMoves.clear();
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
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        if (opponentLastMove == 0) {
            this.mylastmove = 1;
        } else {
            this.mylastmove = this.myMoves.get(this.myMoves.size() - 1);
        }

        List<Integer> xs = new ArrayList<>();
        xs.add(xA);
        xs.add(xB);
        xs.add(xC);

        if (xs.get(this.mylastmove - 1) > 0) {
            this.myMoves.add(this.mylastmove);
            return this.mylastmove;
        }

        List<Integer> best = max_index(xs);

        int chosen_move = best.get(randomizer.nextInt(best.size())) + 1;
        this.myMoves.add(chosen_move);
        return chosen_move;
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
     * where the biggest numbers from
     * the list are
     */
    private List<Integer> max_index(List<Integer> xs) {
        int max_x = Collections.max(xs);
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < xs.size(); i++) {
            if (xs.get(i) == max_x) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}

/**
 * Implementation of Player for Moose's game
 * Test section. It always choose the field
 * with second best coefficient.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
class SecondBestOpp implements Player {

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
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> xs = new ArrayList<>();
        xs.add(xA);
        xs.add(xB);
        xs.add(xC);

        List<Integer> min_idxs = min_index(xs);
        List<Integer> max_idxs = max_index(xs);

        if (min_idxs.size() == 3) {
            return randomizer.nextInt(3) + 1;
        }

        if (min_idxs.size() == 2) {
            return min_idxs.get(randomizer.nextInt(2)) + 1;
        }

        if (max_idxs.size() == 2) {
            return min_idxs.get(0) + 1;
        }

        return 4 - max_idxs.get(0) - min_idxs.get(0);
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
     * where the biggest numbers from
     * the list are
     */
    private List<Integer> max_index(List<Integer> xs) {
        int max_x = Collections.max(xs);
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < xs.size(); i++) {
            if (xs.get(i) == max_x) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    /**
     * This method returns indexes of items with the lowest
     * value from the list.
     *
     * @param xs list in which the search will be produced
     * @return list that contains integers - indexes
     * where the smallest numbers from
     * the list are
     */
    private List<Integer> min_index(List<Integer> xs) {
        int min_x = Collections.min(xs);
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < xs.size(); i++) {
            if (xs.get(i) == min_x) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}

/**
 * Implementation of Player for Moose's game
 * Test section. It always choose the field
 * with the highest coefficient.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
class BestMoveOpp implements Player {

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
     * and 3 for C fields
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
     * where the biggest numbers from
     * the list are
     */
    private List<Integer> max_index(List<Integer> xs) {
        int max_x = Collections.max(xs);
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < xs.size(); i++) {
            if (xs.get(i) == max_x) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}


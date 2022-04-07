package mtsp;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import mtsp.MoveOperations;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws IOException {

        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        AtomicInteger minCost = new AtomicInteger(Integer.MAX_VALUE);
        AtomicReference<mTSP> best = new AtomicReference<>();


        IntStream.range(0, 500_000).boxed().parallel().forEach((t) -> {
            mTSP mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen());

            mTSP.randomSolution();
            //mTSP.validate();
            // mTSP.print(false);

            final int cost = mtsp.mTSP.cost(mTSP.getDepots());

            // System.out.println("Total cost is " + cost);

            if (cost < minCost.get()) {
                best.set(mTSP);
                minCost.set(cost);
            }


        });

        if (best != null) {
            //best.get().print(params.getVerbose());
            System.out.println("**Total cost is before moveOperations " + mTSP.cost(best.get().getDepots()));
        }




        MoveOperations moveOperations = new MoveOperations(best.get().getDepots());

        for (int i = 0; i < 20_000_000; i++) {
            int random = (int) (Math.random() * 6);
            if (random == 0)
                moveOperations.swapNodesInRoute();
            else if (random == 1)
                moveOperations.swapHubWithNodeInRoute();
            else if (random == 2)
                moveOperations.swapNodesBetweenRoutes();
            else if (random == 3)
                moveOperations.insertNodeInRoute();
            else if (random == 4)
                moveOperations.insertNodeBetweenRoutes();

        }


        moveOperations.printSolution();

        //moveOperations.print(true);

        Solution.solutionToJson(moveOperations, params.getNumDepots(), params.getNumSalesmen(),params.getVerbose());


    }


}

package mtsp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Solution {

    ArrayList<Depots> solution = new ArrayList<>();

    public Solution(ArrayList<Depots> solution) {
        this.solution = solution;
    }

    static class Depots {
        String depot;

        ArrayList<String> routes;

        public Depots(String depot, ArrayList<String> routes) {
            this.depot = depot;
            this.routes = routes;
        }

    }

    public static void solutionToJson(MoveOperations moveOperations, int depotNumber, int salesNummber, boolean verbose) throws IOException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();


        ArrayList<Solution.Depots> depots = new ArrayList<Solution.Depots>();

        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> a : moveOperations.getDepots().entrySet()
        ) {
            String depot;
            if (!verbose)
                depot = Integer.toString(a.getKey());
            else
                depot = TurkishNetwork.cities[a.getKey()];
            ArrayList<ArrayList<Integer>> routes = a.getValue();

            ArrayList<String> stringRoutes = new ArrayList<>();
            String route = "";
            for (int i = 0; i < routes.size(); i++) {
                for (int j = 0; j < routes.get(i).size(); j++) {
                    if (j != routes.get(i).size() - 1) {
                        if (!verbose) {
                            route += routes.get(i).get(j) + " ";
                        } else
                            route += TurkishNetwork.cities[routes.get(i).get(j)] + " ";

                    } else {
                        if (!verbose)
                            route += routes.get(i).get(j);
                        else
                            route += TurkishNetwork.cities[routes.get(i).get(j)];
                    }
                }
                stringRoutes.add(route);
                route = "";
            }


            depots.add(new Solution.Depots(depot, stringRoutes));
        }

        Solution solution = new Solution(depots);

        String jsonPath = "solution_" + "d" + depotNumber + "s" + salesNummber + ".json";

        byte[] jsonBytes = gson.toJson(solution).getBytes();

        Files.write(Paths.get(jsonPath), jsonBytes);

        System.out.println(gson.toJson(solution));
    }


}


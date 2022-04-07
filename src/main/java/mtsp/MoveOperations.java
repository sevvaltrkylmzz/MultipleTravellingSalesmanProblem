package mtsp;

import java.util.*;

public class MoveOperations {

    private Map<Integer, ArrayList<ArrayList<Integer>>> depots;

    private int bestCost;

    private int swapNodesInRoutes;
    private int swapHubWithNodeInRoute;
    private int swapNodesBetweenRoutes;
    private int insertNodeInRoute;
    private int insertNodeBetweenRoutes;
    private int howManyTimes;
    private int swapHubBtwNodes;
    int equality;

    public MoveOperations(Map<Integer, ArrayList<ArrayList<Integer>>> depots) {
        this.depots = depots;
        this.bestCost = mTSP.cost(depots);
        this.swapNodesInRoutes = 0;
        this.swapHubWithNodeInRoute = 0;
        this.swapNodesBetweenRoutes = 0;
        this.insertNodeInRoute = 0;
        this.insertNodeBetweenRoutes = 0;
        this.swapHubBtwNodes = 0;
    }

    public void setDepots(Map<Integer, ArrayList<ArrayList<Integer>>> depots) {
        this.depots = depots;
    }

    void swapNodesInRoute() {

        Map<Integer, ArrayList<ArrayList<Integer>>> copyNodes = copyOfNetwork(this.depots);

        ArrayList<Integer> depotCities = new ArrayList<>();

        copyNodes.keySet().iterator().forEachRemaining(depotCities::add);

        int randomKeyForDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));

        int randomRouteIndex = (int) (Math.random() * depots.get(randomKeyForDepot).size());

        if (depots.get(randomKeyForDepot).get(randomRouteIndex).size() == 1) {
            if(depots.get(randomKeyForDepot).size()-1 == randomRouteIndex)
                randomRouteIndex--;
            else
                randomRouteIndex++;
        }

        int firstRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForDepot).get(randomRouteIndex).size());

        int secondRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForDepot).get(randomRouteIndex).size());

        if (firstRandomCityIndex == secondRandomCityIndex) {
            return;

        }

        int firstRandomCityNumber = depots.get(randomKeyForDepot).get(randomRouteIndex).get(firstRandomCityIndex);

        int secondRandomCityNumber = depots.get(randomKeyForDepot).get(randomRouteIndex).get(secondRandomCityIndex);

        howManyTimes++;
        copyNodes.get(randomKeyForDepot).get(randomRouteIndex).set(firstRandomCityIndex, secondRandomCityNumber);
        copyNodes.get(randomKeyForDepot).get(randomRouteIndex).set(secondRandomCityIndex, firstRandomCityNumber);

        int newCost = mTSP.cost(copyNodes);


        if (newCost < bestCost) {

            System.out.println("random hub :" + randomKeyForDepot + " randomRoute: " + randomRouteIndex + " firsRandomIndex: " + firstRandomCityIndex + "secondCity: " + secondRandomCityIndex + " swapNodesInRoute used.. old best cost : " + bestCost + " new cost :" + newCost);
            depots = copyNodes;
            bestCost = mTSP.cost(copyNodes);
            swapNodesInRoutes++;
        }
    }

    void swapHubWithNodeInRoute() {

        //Select random depot city and route
        //change random selected city in route with depot

        Map<Integer, ArrayList<ArrayList<Integer>>> copyNodes = copyOfNetwork(this.depots);

        ArrayList<Integer> depotCities = new ArrayList<>();

        copyNodes.keySet().iterator().forEachRemaining(depotCities::add);

        int randomKeyForDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));

        int randomRouteIndex = (int) (Math.random() * depots.get(randomKeyForDepot).size());

        int firstRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForDepot).get(randomRouteIndex).size());

        int firstRandomCityNumber = depots.get(randomKeyForDepot).get(randomRouteIndex).get(firstRandomCityIndex);

        ArrayList<ArrayList<Integer>> changedList = createNewListWithCities(copyNodes.get(randomKeyForDepot), firstRandomCityIndex, randomRouteIndex, randomKeyForDepot);

        copyNodes.remove(randomKeyForDepot);
        copyNodes.put(firstRandomCityNumber, changedList);

        int newCost = mTSP.cost(copyNodes);


        howManyTimes++;
        if (newCost < bestCost) {

            System.out.println("random hub :" + randomKeyForDepot + " randomRoute: " + randomRouteIndex + " firsRandomIndex: " + firstRandomCityIndex + " swapHupWithNodeInRoute used.. old best cost : " + bestCost + " new cost :" + newCost);
            depots = copyNodes;
            bestCost = newCost;
            swapHubWithNodeInRoute++;
        }
    }

    void swapNodesBetweenRoutes() {
        Map<Integer, ArrayList<ArrayList<Integer>>> copyNodes = copyOfNetwork(this.depots);

        ArrayList<Integer> depotCities = new ArrayList<>();

        copyNodes.keySet().iterator().forEachRemaining(depotCities::add);

        int randomKeyForFirstDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));

        int randomKeyForSecondDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));

        if(depotCities.size() > 1) {
            while (randomKeyForFirstDepot == randomKeyForSecondDepot)
                randomKeyForFirstDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));
        } else
            return;
        int FirstRandomRouteIndex = ((int) (Math.random() * depots.get(randomKeyForFirstDepot).size()));

        int SecondRandomRouteIndex = ((int) (Math.random() * depots.get(randomKeyForSecondDepot).size()));



        int firstRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForFirstDepot).get(FirstRandomRouteIndex).size());

        int firstRandomCityNumber = depots.get(randomKeyForFirstDepot).get(FirstRandomRouteIndex).get(firstRandomCityIndex);


        int secondRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForSecondDepot).get(SecondRandomRouteIndex).size());

        int secondRandomCityNumber = depots.get(randomKeyForSecondDepot).get(SecondRandomRouteIndex).get(secondRandomCityIndex);

        ArrayList<ArrayList<Integer>> firstCopyList = createNewCopyList(copyNodes.get(randomKeyForFirstDepot));
        ArrayList<ArrayList<Integer>> secondCopyList = createNewCopyList(copyNodes.get(randomKeyForSecondDepot));

        firstCopyList.get(FirstRandomRouteIndex).set(firstRandomCityIndex, secondRandomCityNumber);

        secondCopyList.get(SecondRandomRouteIndex).set(secondRandomCityIndex, firstRandomCityNumber);

        copyNodes.remove(randomKeyForFirstDepot);
        copyNodes.put(randomKeyForFirstDepot, firstCopyList);

        copyNodes.remove(randomKeyForSecondDepot);
        copyNodes.put(randomKeyForSecondDepot, secondCopyList);


        int newCost = mTSP.cost(copyNodes);

        howManyTimes++;
        if (newCost < bestCost) {

            System.out.println("random hub :" + randomKeyForFirstDepot + " FirstRandomRoute: " + FirstRandomRouteIndex + " SecondRandomRoute: " + SecondRandomRouteIndex + " firsRandomIndex: " + firstRandomCityIndex + "SecondRandomIndex : " + secondRandomCityIndex + " swapNodesBtwRoutes used.. old best cost : " + bestCost + " new cost :" + newCost);
            this.depots = copyNodes;
            bestCost = newCost;
            swapNodesBetweenRoutes++;
        }
    }

    void insertNodeInRoute() {
        // instead of swapping, we delete the source node, and
        // then insert it to right of the destination node.

        Map<Integer, ArrayList<ArrayList<Integer>>> copyNodes = copyOfNetwork(this.depots);

        ArrayList<Integer> depotCities = new ArrayList<>();

        copyNodes.keySet().iterator().forEachRemaining(depotCities::add);

        int randomKeyForDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));

        int randomRouteIndex = ((int) (Math.random() * depots.get(randomKeyForDepot).size()));

        if (depots.get(randomKeyForDepot).get(randomRouteIndex).size() < 2) {
            if(depots.get(randomKeyForDepot).size()-1 == randomRouteIndex)
                randomRouteIndex--;
            else
                randomRouteIndex++;
        }

        int firstRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForDepot).get(randomRouteIndex).size());

        int firstRandomCityNumber = depots.get(randomKeyForDepot).get(randomRouteIndex).get(firstRandomCityIndex);

        int secondRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForDepot).get(randomRouteIndex).size());

        if (firstRandomCityIndex == secondRandomCityIndex) {
            return;}


        copyNodes.get(randomKeyForDepot).get(randomRouteIndex).remove(firstRandomCityIndex);


        if (firstRandomCityIndex < secondRandomCityIndex) {

            if (secondRandomCityIndex == copyNodes.get(randomKeyForDepot).get(randomRouteIndex).size()) {
                copyNodes.get(randomKeyForDepot).get(randomRouteIndex).add(firstRandomCityNumber);

            } else {
                copyNodes.get(randomKeyForDepot).get(randomRouteIndex).add(secondRandomCityIndex, firstRandomCityNumber);
            }

        } else {
            copyNodes.get(randomKeyForDepot).get(randomRouteIndex).add(secondRandomCityIndex + 1, firstRandomCityNumber);
        }


        int newCost = mTSP.cost(copyNodes);
        howManyTimes++;
        if (newCost < bestCost) {

            System.out.println("random hub :" + randomKeyForDepot + " randomRoute: " + randomRouteIndex + " firsRandomIndex: " + firstRandomCityIndex + " secondRandom: " + secondRandomCityIndex + " insertNodeInRoute used.. old best cost : " + bestCost + " new cost :" + newCost);
            this.depots = copyNodes;
            this.bestCost = newCost;
            insertNodeInRoute++;

        }
    }

    void insertNodeBetweenRoutes() {
        Map<Integer, ArrayList<ArrayList<Integer>>> copyNodes = copyOfNetwork(this.depots);

        ArrayList<Integer> depotCities = new ArrayList<>();

        copyNodes.keySet().iterator().forEachRemaining(depotCities::add);


        int randomKeyForFirstDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));
        int randomKeyForSecondDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));


        int FirstRandomRouteIndex = ((int) (Math.random() * depots.get(randomKeyForFirstDepot).size()));


        if (depots.get(randomKeyForFirstDepot).get(FirstRandomRouteIndex).size() < 2) return;

        int SecondRandomRouteIndex = ((int) (Math.random() * depots.get(randomKeyForSecondDepot).size()));


        int firstRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForFirstDepot).get(FirstRandomRouteIndex).size());

        int firstRandomCityNumber = depots.get(randomKeyForFirstDepot).get(FirstRandomRouteIndex).get(firstRandomCityIndex);

        int secondRandomCityIndex = (int) (Math.random() * depots.get(randomKeyForSecondDepot).get(SecondRandomRouteIndex).size());


        ArrayList<ArrayList<Integer>> firstCopyList = createNewCopyList(copyNodes.get(randomKeyForFirstDepot));
        ArrayList<ArrayList<Integer>> secondCopyList = createNewCopyList(copyNodes.get(randomKeyForSecondDepot));

        firstCopyList.get(FirstRandomRouteIndex).remove(firstRandomCityIndex);

        if (depots.get(randomKeyForSecondDepot).get(SecondRandomRouteIndex).size() - 1 == secondRandomCityIndex)
            secondCopyList.get(SecondRandomRouteIndex).add(firstRandomCityNumber);
        else
            secondCopyList.get(SecondRandomRouteIndex).add(secondRandomCityIndex + 1, firstRandomCityNumber);

        copyNodes.remove(randomKeyForFirstDepot);
        copyNodes.put(randomKeyForFirstDepot,firstCopyList);

        copyNodes.remove(randomKeyForSecondDepot);
        copyNodes.put(randomKeyForSecondDepot, secondCopyList);


        int newCost = mTSP.cost(copyNodes);


        howManyTimes++;
        if (newCost < bestCost) {
            System.out.println("random hub :" + randomKeyForFirstDepot + " FirstrandomRoute " + FirstRandomRouteIndex + "SecondRoute :" + SecondRandomRouteIndex + " +firstRandomCityIndex  " + firstRandomCityIndex + " insertNodeBTWRoute used.. old best cost : " + bestCost + " new cost :" + newCost);
            this.depots = copyNodes;
            this.bestCost = newCost;
            insertNodeBetweenRoutes++;

        }
    }

    void swapHubBtwNodes() {
        Map<Integer, ArrayList<ArrayList<Integer>>> copyNodes = copyOfNetwork(this.depots);

        ArrayList<Integer> depotCities = new ArrayList<>();

        copyNodes.keySet().iterator().forEachRemaining(depotCities::add);

        int firstRandomKeyForDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));
        int secondRandomKeyForDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));

        while(firstRandomKeyForDepot == secondRandomKeyForDepot)
            secondRandomKeyForDepot = depotCities.get((int) (Math.random() * (copyNodes.size())));

        ArrayList<ArrayList<Integer>> firstRoutes = createNewCopyList(copyNodes.get(firstRandomKeyForDepot));
        ArrayList<ArrayList<Integer>> secondRoutes = createNewCopyList(copyNodes.get(secondRandomKeyForDepot));

        copyNodes.remove(firstRandomKeyForDepot);
        copyNodes.remove(secondRandomKeyForDepot);

        copyNodes.put(firstRandomKeyForDepot,secondRoutes);
        copyNodes.put(secondRandomKeyForDepot,firstRoutes);

        int newCost = mTSP.cost(copyNodes);

        if(newCost > bestCost ) {
            System.out.println("random hub :" + firstRandomKeyForDepot + " secondrandomHub " + secondRandomKeyForDepot + " swapHubBtwNodes used.. old best cost : " + bestCost + " new cost :" + newCost);
            this.depots = copyNodes;
            this.bestCost = newCost;
            swapHubBtwNodes++;
        }



    }


    void calculateBtw(){
        Map<Integer, ArrayList<ArrayList<Integer>>> copyNodes = copyOfNetwork(this.depots);



    }

    private ArrayList<ArrayList<Integer>> createNewCopyList(ArrayList<ArrayList<Integer>> arrayLists) {
        ArrayList<ArrayList<Integer>> newList = new ArrayList<>();

        for (ArrayList<Integer> arrayList : arrayLists) {
            ArrayList<Integer> integers = new ArrayList<>(arrayList);
            newList.add(integers);

        }

        return newList;
    }

    private ArrayList<ArrayList<Integer>> createNewListWithCities(ArrayList<ArrayList<Integer>> arrayLists, int firstRandomCityIndex, int randomRouteIndex, int randomKeyForDepot) {
        ArrayList<ArrayList<Integer>> newList = new ArrayList<>();

        for (ArrayList<Integer> arrayList : arrayLists) {
            ArrayList<Integer> integers = new ArrayList<>(arrayList);
            newList.add(integers);
        }

        newList.get(randomRouteIndex).set(firstRandomCityIndex, randomKeyForDepot);

        return newList;
    }


    public Map<Integer, ArrayList<ArrayList<Integer>>> getDepots() {
        return depots;
    }

    public Map<Integer, ArrayList<ArrayList<Integer>>> copyOfNetwork(Map<Integer, ArrayList<ArrayList<Integer>>> realNetwork) {


        HashMap<Integer, ArrayList<ArrayList<Integer>>> copyNetwork = new HashMap<>();


        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> a : realNetwork.entrySet()) {

            ArrayList<ArrayList<Integer>> copyList = new ArrayList<>(10);

            for (int i = 0; i < a.getValue().size(); i++) {
                ArrayList<Integer> route = createCopyList(a.getValue().get(i));
                copyList.add(route);
            }

            copyNetwork.put(a.getKey(), copyList);

        }
        return copyNetwork;
    }


    void printSolution() {
        System.out.println("Total cost after move operations: " + mTSP.cost(depots));
        System.out.println("Number of times used swapNodesInRoutes : " + swapNodesInRoutes);
        System.out.println("Number of times used swapHupWithInRoutes: " + swapHubWithNodeInRoute);
        System.out.println("Number of times used swapNodesBetweenRoutes: " + swapNodesBetweenRoutes);
        System.out.println("Number of times used insertNodeInRoute : " + insertNodeInRoute);
        System.out.println("Number of times used insertNodeBetweenRoutes : " + insertNodeBetweenRoutes);
        System.out.println("Number of times used swapHupBtw : " + swapHubBtwNodes);
        System.out.println("Entered : " + howManyTimes);
        System.out.println("Equality :" + equality);
    }


    ArrayList<Integer> createCopyList(ArrayList<Integer> source) {

        return new ArrayList<>(source);

    }


}





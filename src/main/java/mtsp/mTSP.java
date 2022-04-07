package mtsp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mTSP {

    private int numDepots;
    private int numSalesman;
    private int[] cityNumbers = new int[81];


    private Map<Integer, ArrayList<ArrayList<Integer>>> depots = new HashMap<>();

    public Map<Integer, ArrayList<ArrayList<Integer>>> getDepots() {
        return depots;
    }

    public mTSP(int numDepots, int numSalesman) {
        this.numDepots = numDepots;
        this.numSalesman = numSalesman;
    }


    public void generateRandomArray() {
        for (int i = 0; i < cityNumbers.length; i++) {
            cityNumbers[i] = i;
        }

        for (int i = 0; i < cityNumbers.length; i++) {
            int rand = (int) (81 * Math.random());
            int temp;

            temp = cityNumbers[i];
            cityNumbers[i] = cityNumbers[rand];
            cityNumbers[rand] = temp;
        }
    }


    public void randomSolution() {

        generateRandomArray();

        int number = 0;

        for (int i = 0; i < cityNumbers.length; i++) {
            if (i < numDepots) { //find the depo cities
                depots.put(cityNumbers[i], new ArrayList<>());
                number++;
            } else {
                if (i >= numDepots && i < (numDepots + (numDepots * numSalesman))) { //add at least one city to each depo
                    for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> a : depots.entrySet()) {
                        if (a.getValue().size() != numSalesman) {
                            for (int j = 0; j < numSalesman; j++) {
                                a.getValue().add(new ArrayList<>());
                            }
                        }

                        for (int j = 0; j < a.getValue().size(); j++) {
                            a.getValue().get(j).add(cityNumbers[i]);
                            number++;
                            i++;
                        }
                    }
                    i--;
                } else {
                    for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> a : depots.entrySet()) {
                        for (int j = 0; j < a.getValue().size(); j++) {
                            for (int k = 0; k < (int) (Math.random() * 5); k++) {
                                if (number == 81 || i == 81)
                                    break;
                                a.getValue().get(j).add(cityNumbers[i]);
                                number++;
                                if (i < 81)
                                    i++;
                            }
                        }
                        if (number == 81 || i == 81)
                            break;
                    }
                    if (i != 80 && number < 80)
                        i--;
                }
            }
        }
        if (number != 80 && number != 81) {
            randomSolution();
        }

    }


    public void validate() {
        int i = 0;

        ArrayList<Integer> ints = new ArrayList<>();
        ArrayList<Integer> otherlist = new ArrayList<>();
        for (int j = 0; j < cityNumbers.length; j++) {
            ints.add(cityNumbers[j]);
        }


        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> a : depots.entrySet()
        ) {
            for (int j = 0; j < a.getValue().size(); j++) {
                otherlist.addAll(a.getValue().get(j));
            }
        }

        System.out.println(ints.containsAll(otherlist));
    }

    public static int cost(Map<Integer, ArrayList<ArrayList<Integer>>> depots) {
        int distance = 0;
        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> a : depots.entrySet()) {
            for (int j = 0; j < a.getValue().size(); j++) {
                int depotNumber = a.getKey();

                //find distance btw depot and first number

                distance += TurkishNetwork.distance[depotNumber ][a.getValue().get(j).get(0)];

                for (int i = 0; i < a.getValue().get(j).size() - 1; i++) { // find the distance btw cities in the list
                    distance += TurkishNetwork.distance[a.getValue().get(j).get(i + 1)][a.getValue().get(j).get(i)];

                    if (i == a.getValue().get(j).size() - 2) { //find distance btw depot and last number
                        distance += TurkishNetwork.distance[depotNumber][a.getValue().get(j).get(i+1)];
                    }
                }


            }
        }
        return distance;
    }



    public void print(boolean verbose) {

        int dep = 1;
        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> a : depots.entrySet()) {
            for (int j = 0; j < a.getValue().size(); j++) {

                int depotNumber = a.getKey();
                if (j == 0 && !verbose) {
                    System.out.println("Depot" + dep + ": " + (depotNumber));
                    dep++;
                } else if (j == 0) {
                    System.out.println("Depot" + dep + ": " + TurkishNetwork.cities[depotNumber - 1]);
                    dep++;
                }


                for (int i = 0; i < a.getValue().get(j).size(); i++) {
                    if (i == 0 && !verbose) {
                        System.out.print("  Route" + (j + 1) + ": " + (a.getValue().get(j).get(i)));
                        if (a.getValue().get(j).size() != 1)
                            System.out.print(",");
                    } else if (i == 0) {
                        System.out.print("  Route" + (j + 1) + ": " + TurkishNetwork.cities[a.getValue().get(j).get(i) - 1]);
                        if (a.getValue().get(j).size() != 1)
                            System.out.print(",");

                    }
                    if (i != 0 && i != a.getValue().get(j).size() - 1 && !verbose) {
                        System.out.print(a.getValue().get(j).get(i) + ",");
                    } else if (i != 0 && i != a.getValue().get(j).size() - 1) {
                        System.out.print(TurkishNetwork.cities[a.getValue().get(j).get(i) - 1] + ",");
                    }
                    if (i != 0 && i == a.getValue().get(j).size() - 1 && !verbose)
                        System.out.print(a.getValue().get(j).get(i));
                    else if (i != 0 && i == a.getValue().get(j).size() - 1)
                        System.out.print(TurkishNetwork.cities[a.getValue().get(j).get(i) - 1]);
                }
                System.out.println();
            }
        }
    }

    public void printNumbers() {
        for (int i = 0; i < cityNumbers.length; i++) {
            System.out.print(cityNumbers[i] + " ");
        }
    }

    public void compare(ArrayList<ArrayList<Integer>> routes) {
        for (int i = 0; i < routes.size() - 1; i++) {
            for (int j = 0; j < routes.get(j).size(); j++) {

            }
        }

    }








}

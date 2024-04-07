import java.util.*;
import java.io.*;
import java.lang.*;

public class AirlineSystem implements AirlineInterface {

    private String[] airportNames;
    private AirlineGraph G;


    public static void main(String[] args) throws IOException {
        AirlineSystem airline = new AirlineSystem();
    }

    public Set<String> retrieveAirports(AirlineGraph ag) {
        Set<String> airports = new HashSet<>();
        for (String airport : ag.getAirports()){
            airports.add(airport);
        }
        return airports;
    }

    public Set<Route> retrieveDirectRoutesFrom(AirlineGraph ag, String airport) throws AirportNotFoundException {
        try {
            Set<Route> directRoutes = new HashSet<>();
            for (Route j : ag.adj(airport)){
                directRoutes.add(j);
            }
            return directRoutes;
        } catch (Exception e){
            throw new AirportNotFoundException(airport);
        }
    }

    public Set<ArrayList<String>> fewestStopsItinerary(AirlineGraph ag, String source, String destination) throws AirportNotFoundException {
        try {
            Map<String, String> predecessorMap = new HashMap<>();
            Set<ArrayList<String>> fewestStops = new HashSet<>();
            Queue<String> frontier = new LinkedList<>();
            frontier.add(source);
            Set<String> visitedSet = new HashSet<>();
            while (!frontier.isEmpty()) {
                String curr = frontier.remove();
                if (curr == destination) {
                    break;
                }
                for (Route j : ag.adj(curr)) {
                    if (!visitedSet.contains(j.destination)) {
                        frontier.add(j.destination);
                        visitedSet.add(j.destination);
                        predecessorMap.put(j.destination, curr);
                    }
                }
            }
            if(predecessorMap.containsKey(destination)){
                ArrayList<String> itinerary = new ArrayList<>();
                String current = destination;
                while(!current.equals(source)){

                    itinerary.add(0,current);
                    current = predecessorMap.get(current);
                }
                itinerary.add(0, source);
                fewestStops.add(itinerary);
            }
            return fewestStops;
        } catch (Exception e){
            throw new AirportNotFoundException(source);
        }
    }

    public Set<ArrayList<String>> fewestStopsItinerary(AirlineGraph ag, String source, String transit, String destination) throws AirportNotFoundException {
       try { //LWR FEN XXS

            ArrayList[] arr = new ArrayList[1];
            Set<ArrayList<String>> sourceTransit = fewestStopsItinerary(ag, source, transit);
            Set<ArrayList<String>> transitDestination = fewestStopsItinerary(ag, transit, destination);
            arr = sourceTransit.toArray(arr);
            ArrayList starray = arr[0];

            arr = transitDestination.toArray(arr);
            ArrayList tdarray = arr[0];

            tdarray.remove(0);

            starray.addAll(tdarray);


            Set<ArrayList<String>> fewestStops = Set.of(starray);

            return fewestStops;

        } catch (Exception e) {
            throw new AirportNotFoundException(source);
        }
    }

    public Set<Set<String>> connectedComponents(AirlineGraph ag) { //return the set of the smallest connected components
        Set<Set<String>> connectedComponents = new HashSet<>();
        Set<String> visited = new HashSet<>();

        Queue<String> frontier = new LinkedList<>(); //new Queue for BFS
        int components = 0;

        for (String airport : ag.getAirports()) { //for each vertex v in V
            if (!visited.contains(airport)) { //if v hasnt been visited
                frontier.add(airport); //add v to Q
                visited.add(airport);

                //begin BFS
                Set<String> component = new HashSet<>();
                while (!frontier.isEmpty()) { //while Q is not empty
                    String w = frontier.remove(); //remove head of Q
                    component.add(w);

                    for (Route r : ag.adj(w)) {
                        String neighbor = r.destination;
                        if (!visited.contains(neighbor)) {

                            frontier.add(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }

                connectedComponents.add(component);
            }
        }
        return connectedComponents;
    }

//IOZ FYE WDG

    public Set<ArrayList<Route>> allTrips(AirlineGraph ag, String source, String destination, double budget, int stops) {
        Set<ArrayList<Route>> totalCost = new HashSet<>();
        Set<String> visited = new HashSet<>();
        visited.add(source);
        return totalCost;
    }

    private void solveAllTrips(AirlineGraph ag, Route currentNode, allTripsSolution solution){
        if(currentNode.destination == solution.destination){
            solution.currentPaths.add(currentNode);
            solution.trips.add(solution.currentPaths);
            return;
        }
        for(Route neighbor : ag.adj(currentNode.destination)){
            if(solution.markedVertices.contains(neighbor.destination)){
                continue;
            }
            if(currentNode.price + solution.totalPrice > solution.budget){
                continue;
            }
            if(1 + solution.currentPaths.size() > solution.maxStops){
                continue;
            }
            solution.markedVertices.add(currentNode.destination);
            solution.totalPrice += neighbor.price;
            solution.currentPaths.add(currentNode);

            solveAllTrips(ag, neighbor, solution);
            solution.markedVertices.remove(currentNode.destination); //backtracking
            solution.currentPaths.remove(solution.currentPaths.size() - 1);
            solution.totalPrice -= neighbor.price;
        }
    }

    private final class allTripsSolution  {
        Set<ArrayList<Route>> trips = new HashSet<>();
        ArrayList<Route> currentPaths = new ArrayList<>();
        int totalPrice = 0;
        String destination = "";
        int budget = 0;
        int maxStops = 0;
        Set<String> markedVertices = new HashSet<>();

    }


    public Set<ArrayList<Route>> allRoundTrips(AirlineGraph ag, String source, double budget, int stops) {
        Set<ArrayList<Route>> totalCost = new HashSet<>();
        return totalCost;
    }
}

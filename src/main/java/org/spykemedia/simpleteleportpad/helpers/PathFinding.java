package org.spykemedia.simpleteleportpad.helpers;

import java.util.*;

public class PathFinding {
    // Troep, bespaar tijd en ga hier niet verder mee

    public List<String> findShortestPath(String startPad, String endPad, Map<String, Set<String>> network){
        Map<String, String> previousPad = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> queue = new PriorityQueue<>();

        for (String pad : network.keySet()) {
            distance.put(pad, Integer.MAX_VALUE);
        }
        distance.put(startPad, 0);
        queue.add(startPad);

        while(!queue.isEmpty()){
            String currentPad = queue.poll();

            if (visited.contains(currentPad)) continue;
            visited.add(currentPad);

            if(currentPad.equals(endPad)) break;

            for (String neighbor : network.getOrDefault(currentPad, new HashSet<>())) {
                int newDistance = distance.get(currentPad) + 1;
                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previousPad.put(neighbor, currentPad);
                    queue.add(neighbor);
                }
            }
        }

        List<String> path = new ArrayList<>();

        for (String at = endPad; at != null; at = previousPad.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        if (path.size() == 1 && !path.get(0).equals(startPad)) return Collections.emptyList();

        return path;
    }
}

package org.spykemedia.simpleteleportpad.helpers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class TeleportPadNetwork {

    // Geen idee of dit gaat werken

    private final Map<String, Set<String>> padNetwork = new HashMap<>();

    public void addPad(String padName) {
        padNetwork.putIfAbsent(padName, new HashSet<>());
    }

    public void linkPads(String pad1, String pad2) {
        padNetwork.putIfAbsent(pad1, new HashSet<>());
        padNetwork.putIfAbsent(pad2, new HashSet<>());

        padNetwork.get(pad1).add(pad2);
        padNetwork.get(pad2).add(pad1);
    }

    public Set<String> getLinkedPads(String padName) {
        return padNetwork.getOrDefault(padName, new HashSet<>());
    }

    public Map<String, Set<String>> getNetwork() {
        return padNetwork;
    }


}

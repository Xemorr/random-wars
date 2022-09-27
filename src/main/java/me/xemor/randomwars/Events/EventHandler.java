package me.xemor.randomwars.Events;

import java.util.*;
import java.util.Map.Entry;

//We set the number of votes at 0.25 by default, to ensure some level of randomness for all spectator counts to keep the namesake "RandomWars" true at all times!
public class EventHandler {
   private final HashMap<String, Event> allEvents = new HashMap<>();
   private final HashMap<UUID, Event> playerVotes = new HashMap<>();
   private final HashMap<Event, Double> eventVotes = new HashMap<>();
   private double sumOfVotes = 0;

   public EventHandler(Event[] events) {
      for (Event event : events) {
         addEvent(event);
      }
   }

   public void addEvent(Event event) {
      this.allEvents.put(event.getName().toLowerCase().replace(" ", "_"), event);
      this.eventVotes.put(event, 0.25);
      sumOfVotes += 0.25; //ensure all events have some probability
   }

   public void addVote(UUID uuid, Event event) {
      double votes = eventVotes.get(event);
      playerVotes.put(uuid, event);
      eventVotes.replace(event, votes + 1);
      sumOfVotes++;
   }

   public void removeVote(UUID uuid) {
      Event event = playerVotes.remove(uuid);
      if (event == null) return;
      double votes = this.eventVotes.get(event);
      eventVotes.replace(event, votes - 1);
      sumOfVotes--;
   }

   public void reset() {
      this.sumOfVotes = 0;
      this.eventVotes.clear();
      for (Event event : this.allEvents.values()) {
         this.eventVotes.put(event, 0.25);
         sumOfVotes += 0.25;
      }
   }

   public Event decideEvent() {
      Random random = new Random();
      double rng = random.nextDouble();
      double percentageSum = 0;
      for (Map.Entry<Event, Double> entry : eventVotes.entrySet()) {
         percentageSum += entry.getValue() / sumOfVotes;
         if (percentageSum > rng) {
            return entry.getKey();
         }
      }
      return null; //should never happen if code written correctly
   }

   public Event getEvent(String name) {
      return allEvents.get(name.toLowerCase().replace(" ", "_"));
   }
   public Collection<Event> getAllEvents() {
      return allEvents.values();
   }
}

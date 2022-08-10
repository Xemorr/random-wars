package me.xemor.randomwars.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

public class EventHandler {
   private List<Event> allEvents = new ArrayList();
   private HashMap<Event, Integer> eventVotes = new HashMap();
   int sumOfVotes = 0;

   public void addEvent(Event event) {
      this.allEvents.add(event);
      this.eventVotes.put(event, 0);
   }

   public void addVote(Event event) {
      int votes = this.eventVotes.get(event);
      this.eventVotes.replace(event, votes + 1);
      ++this.sumOfVotes;
   }

   public void reset() {
      this.sumOfVotes = 0;
      this.eventVotes.clear();
      Iterator var1 = this.allEvents.iterator();

      while(var1.hasNext()) {
         Event event = (Event)var1.next();
         this.eventVotes.put(event, 0);
      }

   }

   public Event decideEvent() {
      Random random = new Random();
      int rng;
      if (this.sumOfVotes == 0) {
         rng = random.nextInt(this.allEvents.size() - 1);
         return this.allEvents.get(rng);
      } else {
         rng = random.nextInt(this.sumOfVotes);
         int i = 0;
         Iterator<Entry<Event, Integer>> iterator = this.eventVotes.entrySet().iterator();
         Event event = null;

         while(i <= rng) {
            if (iterator.hasNext()) {
               Entry<Event, Integer> next = iterator.next();
               i += next.getValue();
               event = next.getKey();
            }
         }

         return event;
      }
   }
}

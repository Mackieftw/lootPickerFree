
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.methods.map.Area;

import org.dreambot.api.wrappers.items.GroundItem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


@ScriptManifest(author = "Mackie", category = Category.MONEYMAKING, description = "Picks up items at lummy in pvp worlds", name = "lootPickerFree", version = 1.0)

public class main extends AbstractScript  {

    private long timeBegan;

    private long timeRan;
    @Override
    public void onStart() {
        timeBegan = System.currentTimeMillis();


        log("Starting!");
    }
    @Override
    public int onLoop() {
        Area lootArea = new Area(3221, 3237, 3247, 3197, 0);
        Area lumbridgeBank = new Area(3210, 3218, 3207, 3220, 2);

        if (lootArea.contains(getLocalPlayer()) && !getInventory().isFull() && getSkills().getBoostedLevels(Skill.HITPOINTS) == 10) {

            List<String> itemsToPickup = new ArrayList<String>();
            itemsToPickup.add("Rune axe");
            itemsToPickup.add("Rune dagger");
            itemsToPickup.add("Rune mace");
            itemsToPickup.add("Rune sword");
            itemsToPickup.add("Rune longsword");
            itemsToPickup.add("Rune scimitar");
            itemsToPickup.add("Rune battleaxe");
            itemsToPickup.add("Rune 2h sword");
            itemsToPickup.add("Rune full helm");
            itemsToPickup.add("Rune chainbody");
            itemsToPickup.add("Rune platebody");
            itemsToPickup.add("Rune platelegs");
            itemsToPickup.add("Rune kiteshield");


            itemsToPickup.add("Air rune");
            itemsToPickup.add("Fire rune");
            itemsToPickup.add("Cosmic rune");
            itemsToPickup.add("Chaos rune");
            itemsToPickup.add("Death rune");
            itemsToPickup.add("Mind rune");
            itemsToPickup.add("Law rune");
            itemsToPickup.add("Earth rune");
            itemsToPickup.add("Nature rune");


            itemsToPickup.add("Swordfish");
            itemsToPickup.add("Maple shortbow");
            itemsToPickup.add("Mithril arrow");
            itemsToPickup.add("Steel arrow");
            itemsToPickup.add("Lobster");
            itemsToPickup.add("Adamant arrow");

            List<GroundItem> itemsOnGround = getGroundItems().all();
            outerLoop:
            if (!getLocalPlayer().isInCombat() ){
                for (GroundItem i : itemsOnGround) {
                    if (itemsToPickup.contains(i.getName()) && i != null && lootArea.contains(i)) {
                        /* getCamera().rotateToEntity(i); */
                          i.interact("Take");
                          break outerLoop; //leave the for loop

                      }

                }
             }
             else if (getLocalPlayer().isInCombat()){
                getWalking().walk(lumbridgeBank.getRandomTile());
            }
             else if (getSkills().getRealLevel(Skill.HITPOINTS) < 10){
                getWalking().walk(lumbridgeBank.getRandomTile());
                getBank().openClosest();
                getBank().depositAllItems();//Deposit all Items
                getBank().close();

            }
             else{
                getWalking().walk(lootArea.getRandomTile());

                sleep(3000);
            }
        }
        else if (getInventory().isFull() || getSkills().getBoostedLevels(Skill.HITPOINTS) < 10){
            getWalking().walk(lumbridgeBank.getRandomTile());
            getBank().openClosest();

            getBank().depositAllItems();
            getBank().close();
            if (lumbridgeBank.contains(getLocalPlayer()) && getInventory().isEmpty()) {
                sleepUntil(() -> getSkills().getBoostedLevels(Skill.HITPOINTS) == 10, 60000);
            }
        }
        else {
            getWalking().walk(lootArea.getRandomTile());
        }
        return 1000;

    }



    //setup graphics
    @Override
    public void onPaint(Graphics graphics) {
        timeRan = System.currentTimeMillis() - this.timeBegan;
        graphics.setColor(Color.black);
        graphics.fillRect(10, 30, 200, 50);
        graphics.setColor(Color.WHITE);
        graphics.drawString(ft(timeRan), 10, 50);
    }
    private String ft(long duration)

    {

        String res = "";

        long days = TimeUnit.MILLISECONDS.toDays(duration);

        long hours = TimeUnit.MILLISECONDS.toHours(duration)

                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));

        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)

                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS

                .toHours(duration));

        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)

                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS

                .toMinutes(duration));

        if (days == 0) {

            res = (hours + ":" + minutes + ":" + seconds);

        } else {

            res = (days + ":" + hours + ":" + minutes + ":" + seconds);

        }

        return res;

    }
}

package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {

    private String name;
    private int capacity;

    private List<Collectible> list;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        list = new ArrayList<>();
    }


    @Override
    public @NotNull List<Collectible> getContent() {
        return new ArrayList<>(list);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(list.size() >= capacity)
            throw new IllegalStateException(name + " is full");
        else
            list.add(actor);

    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if(list.contains(actor)){
            list.remove(actor);
        }
    }

    @Override
    public @Nullable Collectible peek() {
        if(list != null && !list.isEmpty())
            return list.get(list.size()-1);
        return null;
    }

    @Override
    public void shift() {
        if(list.size() > 1) {
            List<Collectible> shiftedList = new ArrayList<>(list.subList(0, getSize() - 1));
            Collections.reverse(shiftedList);
            shiftedList.add(list.get(getSize() - 1));
            Collections.reverse(shiftedList);
            list = new ArrayList<>(shiftedList);
        }
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return list.iterator();
    }
}

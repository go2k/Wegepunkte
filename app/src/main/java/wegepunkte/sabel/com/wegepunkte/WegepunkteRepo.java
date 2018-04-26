package wegepunkte.sabel.com.wegepunkte;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WegepunkteRepo {

    private List<Wegepunkt> list;

    public WegepunkteRepo() {
        this.list = new ArrayList<>();
    }

    public void addWegepunkt(Wegepunkt wegepunkt) {
        list.add(wegepunkt);
    }

    public Wegepunkt getWegepunkt(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public int size() {
        return list.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WegepunkteRepo wegepunkteRepo = (WegepunkteRepo) o;
        return Objects.equals(list, wegepunkteRepo.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Wegepunkt wegepunkt : list) {
            ret.append(wegepunkt.toString());
            ret.append(String.format("%n"));
        }
        return ret.toString();
    }
}

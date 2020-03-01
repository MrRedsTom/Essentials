package fr.timkyl2203.enluria.utils.other;

public class BiVal<K, L> {

    private K firstVal;
    private L secondVal;

    public BiVal(K firstKey, L secondVal) {
        this.firstVal = firstKey;
        this.secondVal = secondVal;
    }

    public K getFirstVal() {
        return firstVal;
    }

    public void setFirstVal(K firstKey) {
        this.firstVal = firstKey;
    }

    public L getSecondVal() {
        return secondVal;
    }

    public void setSecondVal(L secondVal) {
        this.secondVal = secondVal;
    }

    @Override
    public int hashCode(){
      return firstVal.hashCode() ^ secondVal.hashCode();
    }
}

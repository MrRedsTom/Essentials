package fr.timkyl2203.enluria.utils.other;

import java.util.HashMap;

public class BiValHashMap<K, V, W> extends HashMap<K, BiVal<V, W>> implements BiValMap<K, V, W> {

    @Override
    public BiVal<V, W> put(K key, V firstVal, W secondVal) {
        return put(key, new BiVal<>(firstVal, secondVal));
    }

    @Override
    public BiVal<V, W> getOrDefault(K key, V defaultFirstVal, W defaultSecondVal) {
        return getOrDefault(key, defaultFirstVal, defaultSecondVal);
    }

}
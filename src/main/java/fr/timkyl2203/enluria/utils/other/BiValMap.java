package fr.timkyl2203.enluria.utils.other;

import java.util.Map;

public interface BiValMap<K, V, W> extends Map<K, BiVal<V, W>> {

    BiVal<V, W> put(K key, V firstVal, W secondVal);

    BiVal<V, W> getOrDefault(K key, V defaultFirstVal, W defaultSecondVal);

}

package graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Kuba Fortunka on 10.10.2016.
 */
@Data
@AllArgsConstructor
public class Vertex<V> {

    private int id;
    private V value;

}

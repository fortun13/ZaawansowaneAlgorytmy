package graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Kuba Fortunka on 10.10.2016.
 */
@Data
@AllArgsConstructor
public class Edge<E> {

    private E value;

    private int fstVertexId;
    private int sndVertexId;

    private Integer weight;

    @Override
    public String toString() {
        return fstVertexId + " --(" + value + ")-->" + sndVertexId;
    }

}

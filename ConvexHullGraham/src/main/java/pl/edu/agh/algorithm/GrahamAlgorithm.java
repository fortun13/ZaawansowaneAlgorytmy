package pl.edu.agh.algorithm;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jakub Fortunka on 27.11.2016.
 */
public class GrahamAlgorithm {

    public Stack<Point2D> getConvexHull(Collection<Point2D> points) {
        List<Point2D> sorted = sortPoints(points);

        if (sorted.size() < 3) {
            throw new IllegalArgumentException("YABAIDE - convex hull can be created for 3 or more points only");
        }

        if (areAllCollinear(sorted)) {
            throw new IllegalArgumentException("YABAIDE - convex hull cannot be created from collinear points");
        }

        Stack<Point2D> hull = new Stack<>();
        hull.push(sorted.get(0));
        hull.push(sorted.get(1));
        for (int i=2;i<sorted.size();i++) {
            Point2D head = sorted.get(i);
            Point2D middle = hull.pop();
            Point2D tail = hull.peek();
            double turn = computePointsTurn(tail, middle, head);
            if (turn > 0) {
                hull.push(middle);
                hull.push(head);
            } else if (turn < 0) {
                i--;
            } else {
                hull.push(head);
            }
        }
        hull.push(sorted.get(0));
        return hull;
    }

    private List<Point2D> sortPoints(Collection<Point2D> points) {
        final Point2D lowest = getLowestPoint(points);
        return points.stream().sorted((a,b) -> {
            if (a.equals(b)) {
                return 0;
            }
            double thetaA = Math.atan2(a.getY()-lowest.getY(), a.getX()-lowest.getX());
            double thetaB = Math.atan2(b.getY()-lowest.getY(), b.getX()-lowest.getX());
            if (thetaA < thetaB) {
                return -1;
            } else if (thetaA > thetaB) {
                return 1;
            } else {
                double distanceA = a.distance(lowest);
                double distanceB = b.distance(lowest);
                return (distanceA < distanceB) ? -1 : 1;
//                if (distanceA < distanceB) {
//                    return -1;
//                } else {
//                    return 1;
//                }
            }
        }).collect(Collectors.toList());
    }

    private Point2D getLowestPoint(Collection<Point2D> points) {
        Iterator<Point2D> it = points.iterator();
        Point2D lowest = it.next();
        while (it.hasNext()) {
            Point2D processed = it.next();
            if (processed.getY() < lowest.getY() || (processed.getY() == lowest.getY() && processed.getX() < lowest.getX())) {
                lowest = processed;
            }
        }
        return lowest;
    }

    private boolean areAllCollinear(List<Point2D> points) {
        if (points.size() < 2) {
            return true;
        }
        Point2D fst = points.get(0);
        Point2D snd = points.get(1);
        for (int i=2;i<points.size();i++) {
            Point2D tmp = points.get(i);
            if (computePointsTurn(fst, snd, tmp) != 0) {
                return false;
            }
        }
        return true;
    }

    private double computePointsTurn(Point2D p0, Point2D p1, Point2D p2) {
        return (p1.getX()-p0.getX())*(p2.getY()-p0.getY()) - (p2.getX()-p0.getX())*(p1.getY()-p0.getY());
    }

}

package ru.stqa.pft.sandbox;

import org.apache.commons.math3.util.Precision;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance() {
        Point p1 = new Point(3.8, 3.2);
        Point p2 = new Point(5.2, 6.4);

        Assert.assertEquals(Precision.round(p1.distance(p1, p2), 2), 3.49);

        Point p3 = new Point(0, 0);
        Point p4 = new Point(0, 0);

        Assert.assertEquals(Precision.round(p3.distance(p3, p4), 2), 0.00);

        Point p5 = new Point(10.6, 15.8);
        Point p6 = new Point(100, 102);

        Assert.assertEquals(Precision.round(p5.distance(p5, p6), 2), 124.19);
    }
}

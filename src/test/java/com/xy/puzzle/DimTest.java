package com.xy.puzzle;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class DimTest {

    @Test
    public void test() {
        try {
            Dim.newDim(Integer.MAX_VALUE, 2, 1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertThat(e, CoreMatchers.instanceOf(IllegalArgumentException.class));
        }
    }

}

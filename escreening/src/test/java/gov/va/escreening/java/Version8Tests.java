package gov.va.escreening.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

/**
 * Test language features to ensure correct java version is being used
 * 
 * @author Robin Carnow
 *
 */
@RunWith(JUnit4.class)
public class Version8Tests {

	@Test
    public void testLambdas() throws Exception{
		Op sum = (a, b) -> a + b;
		assertEquals(4, sum.eval(2, 2));
	}
	
	@FunctionalInterface
	private interface Op {
		public int eval(int x, int y);
	}
}

package gov.va.escreening.xport;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class FormulaExpressionTest {
	Logger logger = LoggerFactory.getLogger(FormulaExpressionTest.class);

	@Test
	public void formula1() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "( ( (true?1:0 + false?1:0 + false?1:0) )  +  ( (true?1:0 + false?1:0 + false?1:0 + false?1:0 + false?1:0) )  + (false?1:0) + (false?1:0))";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("2"));
	}

	@Test
	public void formula0() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "((false?1:0) + (false?1:0))";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("0"));
	}

	@Test
	public void formula2() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "( ( (true?1:0 + false?1:0 + false?1:0) )  +  ( (true?1:0 + false?1:0 + false?1:0 + false?1:0 + false?1:0) ) )";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("2"));
	}

	@Test
	public void formula3() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "( ( (true?1:0 + false?1:0 + false?1:0 + false?1:0 + false?1:0) )  )";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("1"));
	}

	@Test
	public void formula4() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "( ( (true?1:0 + false?1:0 + false?1:0) ) )";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("1"));
	}

	@Test
	public void formula5() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "((true?1:0) + (true?1:0) + (true?1:0) + (true?1:0) + (true?1:0) + (true?1:0))";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("6"));
	}

	@Test
	public void simple() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Spring Expression Language Tutorial'");
		Assert.assertTrue(exp.getValue(String.class).equals("Spring Expression Language Tutorial"));
	}

	@Test
	public void simple1() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("\"Spring Expression Language Tutorial\"");
		Assert.assertTrue(exp.getValue(String.class).equals("Spring Expression Language Tutorial"));
	}

	@Test
	public void formula6() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "((true?1:0) + (true?1:0) + (true?1:0) + (true?1:0) + (true?1:0) + (true?1:0))>1?1:0";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("1"));
	}

	@Test
	public void formula7() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "((true ? 1:0) + (true?1:0) + (true?1:   0) + (true ?1 :0) + (true?1:0) + (true ? 1 :   0))   >=   1?1:0";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("1"));
		// String businessFormula = mathFormula.replaceAll("[$]", "").replaceAll("[?]", "").replaceAll("1\\s*:\\s*0",
		// "").replaceAll(">=\\s*1", "");
		String businessFormula = mathFormula.replaceAll("[$]|[?]|[1\\s*:\\s*0]|[>=\\s*1]", "");
		int y = 0;
	}
	@Test
	public void formula10() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "((9)>=7 && (1)==1 && (2)>=2)?1:0";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("1"));
		String businessFormula = mathFormula.replaceAll("([$])|(\\?\\s*[1]\\s*[:]\\s*[0])", "").replaceAll("(>\\s*=\\s*[1])", " >= 1 then 1 else 0");
		Assert.assertTrue(businessFormula.equals("((9)>=7 && (1)==1 && (2)>=2)"));
	}

	@Test
	public void formula9() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "(3)+(4)+ (((6<7) && (4>3))?2:1)";
		Assert.assertTrue(parser.parseExpression(mathFormula).getValue(String.class).equals("9"));
		// String businessFormula = mathFormula.replaceAll("[$]", "").replaceAll("[?]", "").replaceAll("1\\s*:\\s*0",
		// "").replaceAll(">=\\s*1", "");
		String businessFormula = mathFormula.replaceAll("[$]|[?]|[1\\s*:\\s*0]|[>=\\s*1]", "");
		int y = 0;
	}

	@Test
	public void formula8() {
		ExpressionParser parser = new SpelExpressionParser();
		String mathFormula = "([tbi_q1_score]+[tbi_q2_score]+[tbi_q3_score]+[tbi_q4_score]+[tbi_score])>=1?1:0";
		String businessFormula = mathFormula.replaceAll("([$])|(\\?\\s*[1]\\s*[:]\\s*[0])", "").replaceAll("(>\\s*=\\s*[1])", " >= 1 then 1 else 0");
		int y = 0;
	}


}
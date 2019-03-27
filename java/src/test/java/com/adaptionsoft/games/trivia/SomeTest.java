package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.approvaltests.Approvals;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.Assert.fail;

public class SomeTest {

	@Test
	@Ignore
	public void true_is_true() {
		fail();
	}

	@Test
	public void shouldProduceSameOutput() {
		Integer[] seed = new Integer[]{0,1,2,3,4,5};
		Approvals.verifyAll(seed,this::runGame);
	}

	private String runGame(Integer seed) {
		ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
		GameRunner.runGame(new Random(seed), new PrintStream(outputStream1));
		return outputStream1.toString();
	}
}

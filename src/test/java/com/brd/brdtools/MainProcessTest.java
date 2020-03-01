package com.brd.brdtools;


import com.brd.brdtools.report.Report;
import com.brd.brdtools.report.ReportStatus;
import com.brd.brdtools.util.Util;
import javassist.CannotCompileException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Test : SQL import.
 */
@SpringBootTest
public class MainProcessTest {

	private MainProcess mainProcess = new MainProcess();
	private Util util = new Util();

	@Test
	public void testGetDatabase_nofile() {

		// When
		final String out = mainProcess.process(null);

		// Then
		final Report report = mainProcess.getReport();
		assertEquals(ReportStatus.EMPTY_DATABASE, report.getReportStatus());
		assertNull(out);
	}

	@Test
	public void testGetDatabase_1() throws FileNotFoundException, CannotCompileException {
		// Given
		final File file = util.getFileByClassPath("/1.sql");
		final InputStream in = new FileInputStream(file);
		final String sqlContent = util.read(in);

		// When
		final String out = mainProcess.process(sqlContent);

		// Then
		final Report report = mainProcess.getReport();
		String generatedClass = GgenerateClass.generateClass("Policy", report.getResdef().getEntities().get(0));
		System.out.println(generatedClass);
		assertEquals(ReportStatus.SUCCESS, report.getReportStatus());
		assertNotNull(out);
	}

	@Test
	public void testGetDatabase_2() throws FileNotFoundException {
		// Given
		final File file = util.getFileByClassPath("/2.sql");
		final InputStream in = new FileInputStream(file);
		final String sqlContent = util.read(in);

		final String sql = sqlContent.replaceAll("\\[|\\]", "").replaceAll("\n", "");

		// When
		final String out = mainProcess.process(sql);

		// Then
		final Report report = mainProcess.getReport();
		String generatedClass = GgenerateClass.generateClass("Policy", report.getResdef().getEntities().get(0));
		System.out.println(generatedClass);
		assertEquals(ReportStatus.SUCCESS, report.getReportStatus());
		assertNotNull(out);
	}

}

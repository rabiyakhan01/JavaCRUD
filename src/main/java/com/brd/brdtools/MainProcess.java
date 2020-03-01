package com.brd.brdtools;

import com.brd.brdtools.export.DatabaseToResdef;
import com.brd.brdtools.export.ResdefToJson;
import com.brd.brdtools.model.resdef.Resdef;
import com.brd.brdtools.model.sql.Database;
import com.brd.brdtools.parser.SqlImport;
import com.brd.brdtools.report.Report;
import com.brd.brdtools.report.ReportStatus;
import com.brd.brdtools.type.TypeConverter;
import com.brd.brdtools.validation.DatabaseValidator;
import org.springframework.stereotype.Component;

/**
 * Main manager for SQL import and export in the pivot format file
 */
@Component
public class MainProcess {

	/**
	 * Report.
	 */
	private Report report = new Report();

	/**
	 * Main method
	 */
	public String process(final String sqlContent) {

		// Load SQL file, filter and parse SQL queries
		final SqlImport sqlImport = new SqlImport(report);
		final Database database = sqlImport.getDatabase(sqlContent);

		if((database == null) || database.getTables().isEmpty()) {
			// Empty database
			report.setReportStatus(ReportStatus.EMPTY_DATABASE);
			return null;
		}

		// Convert SQL types to Entity store types
		final TypeConverter typeConverter = new TypeConverter(report);
		typeConverter.convertTypeFromSQLToEntityStore(database);

		// Database schema validator
		final DatabaseValidator databaseValidator = new DatabaseValidator(report);
		databaseValidator.validateDatabase(database);

		// Convert to Resdef bean
		final DatabaseToResdef databaseToResdef = new DatabaseToResdef();
		final Resdef resdef = databaseToResdef.databaseToResdef(database);
		report.setResdef(resdef);

		// Export to JSON
		final ResdefToJson resdefToJson = new ResdefToJson();
		final String json = resdefToJson.resdefToJson(resdef);
		report.setReportStatus(ReportStatus.SUCCESS);

		// Summary
		report.setNbCreatedEntity(database.getTables().size());

		return json;
	}

	/**
	 * Get report.
	 * @return report
	 */
	public Report getReport() {
		return report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

}

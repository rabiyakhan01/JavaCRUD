package com.brd.brdtools.rest.api;

import com.brd.brdtools.CodeGeneratorClass;
import com.brd.brdtools.MainProcess;
import com.brd.brdtools.model.rest.RequestParameter;
import com.brd.brdtools.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/generator")
public class JavaCodeGeneratorController {

    @Autowired
    MainProcess mainProcess = null;

    @RequestMapping(path="/code/", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<String> getSqlGenerator(@RequestBody RequestParameter requestParameter)
    {
        String sqlQuery= requestParameter.getSqlQuery();
        if (sqlQuery == null) {
            return new ResponseEntity<String>("Please Enter Valid Query!!!", HttpStatus.BAD_REQUEST);
        }else {
            boolean isPostgresQueryFormat = sqlQuery.contains("[") ? false : true;
            final String sql = sqlQuery.replaceAll("\\[|\\]", "").replaceAll("\n", "");
            mainProcess.process(sql);
            final Report report = mainProcess.getReport();
            String generatedClass = CodeGeneratorClass.generateClass(requestParameter.getBeanName(), isPostgresQueryFormat, report.getResdef().getEntities().get(0));
            return new ResponseEntity<String>(generatedClass, HttpStatus.OK);
        }
    }
}

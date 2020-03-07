package com.brd.brdtools.rest.api;

import com.brd.brdtools.CodeGeneratorClass;
import com.brd.brdtools.MainProcess;
import com.brd.brdtools.model.rest.RequestParameter;
import com.brd.brdtools.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/generator")
public class JavaCodeGeneratorController {

    @Autowired
    MainProcess mainProcess = null;

    @RequestMapping(value="/code/", method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .headers(headers)
                .build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(path="/code/",produces = "!application/json" , consumes = "application/json" ,method = RequestMethod.POST)
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

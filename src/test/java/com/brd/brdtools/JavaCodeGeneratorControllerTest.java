package com.brd.brdtools;

import com.brd.brdtools.model.rest.RequestParameter;
import com.brd.brdtools.rest.api.JavaCodeGeneratorController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JavaCodeGeneratorController.class)
public class JavaCodeGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private JavaCodeGeneratorController javaCodeGeneratorController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void verifyInvalidGeneratorCodeArgument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/generator/code/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void verifyGeneratorCodeAPI() throws Exception {
        // given
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setBeanName("Policy");
        requestParameter.setSqlQuery("CREATE TABLE public.t_ri_m_policy (policy_no serial NOT NULL,policy_code character varying(100) NOT NULL, versi character varying(20) NOT NULL, policy_name character varying(500) NULL, status_policy character varying(100) NULL, document_indonesia character varying(100) NULL,document_english character varying(100) NULL, effective_date timestamp NOT NULL,revision_date timestamp NOT NULL,overdue_date timestamp NULL, socialization_flag character varying(1) NULL,socialization_method character varying(2500) NULL, parent_no bigint NULL,level_policy character varying(2) NULL,transfer_no bigint NULL,transfer_reason character varying(500) NULL, transfer_date timestamp NULL,status character(1) NOT NULL, cre_dt timestamp NOT NULL, cre_user_no bigint NOT NULL, upd_dt timestamp, upd_user_no bigint,PRIMARY KEY (policy_no) )WITH (OIDS = FALSE)TABLESPACE pg_default;");

        String generatedClass = "public class Policy{\n" +
                "private Integer policyNo;\n" +
                "private String policyCode;\n" +
                "private String versi;\n" +
                "private String policyName;\n" +
                "private String statusPolicy;\n" +
                "private String documentIndonesia;\n" +
                "private String documentEnglish;\n" +
                "private String effectiveDate;\n" +
                "private String revisionDate;\n" +
                "private String overdueDate;\n" +
                "private String socializationFlag;\n" +
                "private String socializationMethod;\n" +
                "private Integer parentNo;\n" +
                "private String levelPolicy;\n" +
                "private Integer transferNo;\n" +
                "private String transferReason;\n" +
                "private String transferDate;\n" +
                "private String status;\n" +
                "private String creDt;\n" +
                "private Integer creUserNo;\n" +
                "private String updDt;\n" +
                "private Integer updUserNo;\n" +
                "\n" +
                "public void addPolicy(Policy policy, int creUserNo) {\n" +
                "Connection conn = null;\n" +
                "PreparedStatement pstmt = null;\n" +
                "ResultSet rs = null;\n" +
                "int i=1;\n" +
                "final String SQL = \"INSERT INTO t_ri_m_policy (policy_code, versi, policy_name, status_policy, document_indonesia, document_english, effective_date, revision_date, overdue_date, socialization_flag, socialization_method, parent_no, level_policy, transfer_no, transfer_reason, transfer_date, status, cre_dt, cre_user_no, upd_dt, upd_user_no ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )\"; \n" +
                "try {\n" +
                "conn = this.ds.getConnection();\n" +
                "pstmt = conn.prepareStatement(SQL);\n" +
                "pstmt.setObject(i++, policy.getPolicyCode());\n" +
                "pstmt.setObject(i++, policy.getVersi());\n" +
                "pstmt.setObject(i++, policy.getPolicyName());\n" +
                "pstmt.setObject(i++, policy.getStatusPolicy());\n" +
                "pstmt.setObject(i++, policy.getDocumentIndonesia());\n" +
                "pstmt.setObject(i++, policy.getDocumentEnglish());\n" +
                "pstmt.setObject(i++, policy.getEffectiveDate());\n" +
                "pstmt.setObject(i++, policy.getRevisionDate());\n" +
                "pstmt.setObject(i++, policy.getOverdueDate());\n" +
                "pstmt.setObject(i++, policy.getSocializationFlag());\n" +
                "pstmt.setObject(i++, policy.getSocializationMethod());\n" +
                "pstmt.setObject(i++, policy.getParentNo());\n" +
                "pstmt.setObject(i++, policy.getLevelPolicy());\n" +
                "pstmt.setObject(i++, policy.getTransferNo());\n" +
                "pstmt.setObject(i++, policy.getTransferReason());\n" +
                "pstmt.setObject(i++, policy.getTransferDate());\n" +
                "pstmt.setObject(i++, policy.getStatus());\n" +
                "pstmt.setObject(i++, policy.getCreDt());\n" +
                "pstmt.setObject(i++, policy.getCreUserNo());\n" +
                "pstmt.setObject(i++, policy.getUpdDt());\n" +
                "pstmt.setObject(i++, policy.getUpdUserNo());\n" +
                "pstmt.executeUpdate();\n" +
                "        } catch (Exception e) {\n" +
                "LOG.error(\"Exception at addPolicy\");\n" +
                "LOG.error(Policy.class, e);\n" +
                "throw e;\n" +
                "        } finally { \n" +
                "try {\n" +
                "DatabaseUtility.clear(conn, pstmt, rs);\n" +
                "            } catch (final Exception ex) { // do nothing\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "public void updatePolicy(Policy policy, int updUserNo) {\n" +
                "Connection conn = null;\n" +
                "PreparedStatement pstmt = null;\n" +
                "ResultSet rs = null;\n" +
                "int i=1;\n" +
                "final String SQL = \"UPDATE t_ri_m_policy SET policy_code = ?, versi = ?, policy_name = ?, status_policy = ?, document_indonesia = ?, document_english = ?, effective_date = ?, revision_date = ?, overdue_date = ?, socialization_flag = ?, socialization_method = ?, parent_no = ?, level_policy = ?, transfer_no = ?, transfer_reason = ?, transfer_date = ?, status = ?, cre_dt = ?, cre_user_no = ?, upd_dt = ?, upd_user_no = ? WHERE policy_no = ? \"; \n" +
                "try {\n" +
                "conn = this.ds.getConnection();\n" +
                "pstmt = conn.prepareStatement(SQL);\n" +
                "pstmt.setObject(i++, policy.getPolicyCode());\n" +
                "pstmt.setObject(i++, policy.getVersi());\n" +
                "pstmt.setObject(i++, policy.getPolicyName());\n" +
                "pstmt.setObject(i++, policy.getStatusPolicy());\n" +
                "pstmt.setObject(i++, policy.getDocumentIndonesia());\n" +
                "pstmt.setObject(i++, policy.getDocumentEnglish());\n" +
                "pstmt.setObject(i++, policy.getEffectiveDate());\n" +
                "pstmt.setObject(i++, policy.getRevisionDate());\n" +
                "pstmt.setObject(i++, policy.getOverdueDate());\n" +
                "pstmt.setObject(i++, policy.getSocializationFlag());\n" +
                "pstmt.setObject(i++, policy.getSocializationMethod());\n" +
                "pstmt.setObject(i++, policy.getParentNo());\n" +
                "pstmt.setObject(i++, policy.getLevelPolicy());\n" +
                "pstmt.setObject(i++, policy.getTransferNo());\n" +
                "pstmt.setObject(i++, policy.getTransferReason());\n" +
                "pstmt.setObject(i++, policy.getTransferDate());\n" +
                "pstmt.setObject(i++, policy.getStatus());\n" +
                "pstmt.setObject(i++, policy.getCreDt());\n" +
                "pstmt.setObject(i++, policy.getCreUserNo());\n" +
                "pstmt.setObject(i++, policy.getUpdDt());\n" +
                "pstmt.setObject(i++, policy.getUpdUserNo());\n" +
                "pstmt.executeUpdate();\n" +
                "        } catch (Exception e) {\n" +
                "LOG.error(\"Exception at updatePolicy\");\n" +
                "LOG.error(Policy.class, e);\n" +
                "throw e;\n" +
                "        } finally { \n" +
                "try {\n" +
                "DatabaseUtility.clear(conn, pstmt, rs);\n" +
                "            } catch (final Exception ex) { // do nothing\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "public void deletePolicy(int policyNo) {\n" +
                "int i=1;\n" +
                "Connection conn = null;\n" +
                "PreparedStatement pstmt = null;\n" +
                "final String SQL = \"delete from t_ri_m_policy WHERE policy_no = ? \"; \n" +
                "try {\n" +
                "conn = this.ds.getConnection();\n" +
                "pstmt = conn.prepareStatement(SQL);\n" +
                "pstmt.setInt(i++, policyNo);\n" +
                "pstmt.executeUpdate();\n" +
                "        } catch (Exception e) {\n" +
                "LOG.error(\"Exception at deletePolicy\");\n" +
                "LOG.error(Policy.class, e);\n" +
                "throw e;\n" +
                "        } finally { \n" +
                "try {\n" +
                "DatabaseUtility.clear(conn, pstmt, rs);\n" +
                "            } catch (final Exception ex) { // do nothing\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "public List<Policy>  getListOfPolicy( ) {\n" +
                "List<Policy> list = null;\n" +
                "Policy policy; \n" +
                "Connection conn = null;\n" +
                "PreparedStatement pstmt = null;\n" +
                "ResultSet rs = null;\n" +
                "final String SQL = \"SELECT policy_no, policy_code, versi, policy_name, status_policy, document_indonesia, document_english, effective_date, revision_date, overdue_date, socialization_flag, socialization_method, parent_no, level_policy, transfer_no, transfer_reason, transfer_date, status, cre_dt, cre_user_no, upd_dt, upd_user_no FROM t_ri_m_policy \"\n" +
                "try {\n" +
                "list = new ArrayList<Policy>();\n" +
                "conn = this.ds.getConnection();\n" +
                "pstmt = conn.prepareStatement(SQL);\n" +
                "rs = pstmt.executeQuery();\n" +
                "while (rs.next()) {\n" +
                "policy = null;\n" +
                "policy = new Policy();\n" +
                "policy.setPolicyNo(rs.getInt(policy_no));\n" +
                "policy.setPolicyCode(rs.getString(policy_code) == null ? \"\": rs.getString(policy_code));\n" +
                "policy.setVersi(rs.getString(versi) == null ? \"\": rs.getString(versi));\n" +
                "policy.setPolicyName(rs.getString(policy_name) == null ? \"\": rs.getString(policy_name));\n" +
                "policy.setStatusPolicy(rs.getString(status_policy) == null ? \"\": rs.getString(status_policy));\n" +
                "policy.setDocumentIndonesia(rs.getString(document_indonesia) == null ? \"\": rs.getString(document_indonesia));\n" +
                "policy.setDocumentEnglish(rs.getString(document_english) == null ? \"\": rs.getString(document_english));\n" +
                "policy.setEffectiveDate(rs.getString(effective_date) == null ? \"\": rs.getString(effective_date));\n" +
                "policy.setRevisionDate(rs.getString(revision_date) == null ? \"\": rs.getString(revision_date));\n" +
                "policy.setOverdueDate(rs.getString(overdue_date) == null ? \"\": rs.getString(overdue_date));\n" +
                "policy.setSocializationFlag(rs.getString(socialization_flag) == null ? \"\": rs.getString(socialization_flag));\n" +
                "policy.setSocializationMethod(rs.getString(socialization_method) == null ? \"\": rs.getString(socialization_method));\n" +
                "policy.setParentNo(rs.getInt(parent_no));\n" +
                "policy.setLevelPolicy(rs.getString(level_policy) == null ? \"\": rs.getString(level_policy));\n" +
                "policy.setTransferNo(rs.getInt(transfer_no));\n" +
                "policy.setTransferReason(rs.getString(transfer_reason) == null ? \"\": rs.getString(transfer_reason));\n" +
                "policy.setTransferDate(rs.getString(transfer_date) == null ? \"\": rs.getString(transfer_date));\n" +
                "policy.setStatus(rs.getString(status) == null ? \"\": rs.getString(status));\n" +
                "policy.setCreDt(rs.getString(cre_dt) == null ? \"\": rs.getString(cre_dt));\n" +
                "policy.setCreUserNo(rs.getInt(cre_user_no));\n" +
                "policy.setUpdDt(rs.getString(upd_dt) == null ? \"\": rs.getString(upd_dt));\n" +
                "policy.setUpdUserNo(rs.getInt(upd_user_no));\n" +
                "if (policy != null) {\n" +
                "list.add(policy);\n" +
                "                }\n" +
                "            }\n" +
                "        } catch (Exception e) {\n" +
                "LOG.error(\"Exception at getListOfPolicy\");\n" +
                "LOG.error(Policy.class, e);\n" +
                "throw e;\n" +
                "        } finally { \n" +
                "try {\n" +
                "DatabaseUtility.clear(conn, pstmt, rs);\n" +
                "            } catch (final Exception ex) { // do nothing\n" +
                "            }\n" +
                "        }\n" +
                "return list;\n" +
                "    }\n" +
                "}";
        ResponseEntity responseEntity = new ResponseEntity<String>(generatedClass, HttpStatus.OK);

        given(javaCodeGeneratorController.getSqlGenerator(requestParameter)).willReturn(responseEntity);

        String requestJson = new ObjectMapper().writeValueAsString(requestParameter);

        // when + then
        this.mockMvc.perform(get("/generator/code/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

}

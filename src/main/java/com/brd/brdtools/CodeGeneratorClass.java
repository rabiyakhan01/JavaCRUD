package com.brd.brdtools;

import com.brd.brdtools.model.resdef.Entity;
import com.brd.brdtools.model.resdef.Field;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeGeneratorClass {

    public static String generateClass(String className, Entity entity) {
        List<Field> fields = entity.getFields();
        StringBuffer classStr = new StringBuffer();

        //Create class from class name
        classStr.append("public class ")
                .append(className)
                .append(" {\n");

        //Create fields for class
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!Arrays.asList("CONSTRAINT").contains(fieldName)) {
                classStr.append("private ")
                        .append(field.getType().equals("Date") ? "String" : field.getType())
                        .append(" " + getFieldName(fieldName))
                        .append(";\n");
            }
        }

        Field primaryField = fields.remove(0);

        //Add Insert Method
        classStr.append(addMethod(className, entity));

        //Add Update Method
        classStr.append(updateMethod(className, entity, primaryField));

        //Add Delete Method
        classStr.append(deleteMethod(className, entity, primaryField));

        //Adding primary key field
        fields.add(0, primaryField);
        //Add get list Method
        classStr.append(getListMethod(className, entity));

        //Class End
        classStr.append("}");
        return classStr.toString();
    }

    private static String getFieldName(String fieldName) {
        Matcher m = Pattern.compile("([_][a-z])").matcher(fieldName);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group().substring(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String addMethod(String className, Entity entity) {
        StringBuffer method = new StringBuffer();
        method.append("\npublic void add")
                .append(className)
                .append("(")
                .append(className + " " + firstLetterLowerCase(className))
                .append(", int creUserNo")
                .append(") {\n")
                .append("Connection conn = null;\n")
                .append("PreparedStatement pstmt = null;\n")
                .append("ResultSet rs = null;\n")
                .append("int i=1;\n")
                .append("final String SQL = \"INSERT INTO ")
                .append(entity.getName() + " (");

        //insert fields into the insert query
        List<Field> fields = entity.getFields();
        addFields(method, fields);

        method.append(") VALUES (");
        //insert values into the insert query
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!Arrays.asList("CONSTRAINT").contains(fieldName)) {
                method.append("?, ");
            }
        }
        method.deleteCharAt(method.lastIndexOf(", "));
        method.append(")\"; \n");
        appendValues(method, className, fields, "add", null);

        return method.toString();
    }

    private static String updateMethod(String className, Entity entity, Field primaryField) {
        StringBuffer method = new StringBuffer();
        method.append("\npublic void update")
                .append(className)
                .append("(")
                .append(className + " " + firstLetterLowerCase(className))
                .append(", int updUserNo")
                .append(") {\n")
                .append("Connection conn = null;\n")
                .append("PreparedStatement pstmt = null;\n")
                .append("ResultSet rs = null;\n")
                .append("int i=1;\n")
                .append("final String SQL = \"UPDATE ")
                .append(entity.getName() + " SET ");

        //insert fields into the insert query
        List<Field> fields = entity.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!Arrays.asList("CONSTRAINT").contains(fieldName)) {
                method.append(fieldName + " = ?, ");
            }
        }
        method.deleteCharAt(method.lastIndexOf(", "));
        method.append("WHERE " + primaryField.getName() + " = ? \"; \n");
        appendValues(method, className, fields, "update", null);

        return method.toString();
    }

    private static String deleteMethod(String className, Entity entity, Field primaryField) {
        StringBuffer method = new StringBuffer();
        String fieldName = getFieldName(primaryField.getName());
        method.append("\npublic void delete")
                .append(className)
                .append("(")
                .append("int " + fieldName)
                .append(") {\n")
                .append("int i=1;\n")
                .append("Connection conn = null;\n")
                .append("PreparedStatement pstmt = null;\n")
                .append("final String SQL = \"delete from ")
                .append(entity.getName() + " ")
                .append("WHERE " + primaryField.getName() + " = ? \"; \n");

        appendValues(method, className, null, "delete", fieldName);

        return method.toString();
    }

    private static String getListMethod(String className, Entity entity) {
        StringBuffer method = new StringBuffer();
        String lowerClassName = firstLetterLowerCase(className);
        method.append("\npublic List<" + className + ">  getListOf")
                .append(className)
                .append("( ) {\n")
                .append("List<" + className + "> list = null;\n")
                .append(className + " " + lowerClassName+"; \n")
                .append("Connection conn = null;\n")
                .append("PreparedStatement pstmt = null;\n")
                .append("ResultSet rs = null;\n")
                .append("final String SQL = \"SELECT ");

        //insert fields into the insert query
        List<Field> fields = entity.getFields();
        addFields(method, fields);

        method.append("FROM " + entity.getName() + " \"\n");

        method.append("try {\n");
        method.append("list = new ArrayList<" + className + ">();\n");
        method.append("conn = this.ds.getConnection();\n");
        method.append("pstmt = conn.prepareStatement(SQL);\n");
        method.append("rs = pstmt.executeQuery();\n");
        method.append("while (rs.next()) {\n");
        method.append(lowerClassName + " = null;\n");
        method.append(lowerClassName + " = new " + className + "();\n");

        for (Field field : fields) {
            String fieldNameStr = field.getName();
            if (!Arrays.asList("CONSTRAINT").contains(fieldNameStr)) {
                if (field.getType().equals("Integer")) {
                    method.append(lowerClassName + ".set")
                            .append(firstLetterUpperCase(getFieldName(fieldNameStr)))
                            .append("(rs.getInt(" + fieldNameStr + "));\n");
                } else {
                    method.append(lowerClassName + ".set")
                            .append(firstLetterUpperCase(getFieldName(fieldNameStr)))
                            .append("(rs.getString(" + fieldNameStr + ") == null ? \"\" : rs.getString(" + fieldNameStr + "));\n");
                }

            }
        }
        method.append("if (" + lowerClassName + " != null) {\n")
                .append("list.add("+lowerClassName+");\n")
                .append("}\n")
                .append("}\n")
                .append("} catch (Exception e) {\n")
                .append("LOG.error(\"Exception at getListOf" + className + "\");\n")
                .append("LOG.error(" + className + ".class, e);\n")
                .append("throw e; \n")
                .append("} finally { \n")
                .append("try {\n")
                .append("DatabaseUtility.clear(conn, pstmt, rs);\n")
                .append("} catch (final Exception ex) { // do nothing\n")
                .append("}\n }\n")
                .append("return list;\n")
                .append("}\n");

        return method.toString();
    }

    private static void appendValues(StringBuffer method, String className, List<Field> fields, String operation, String fieldName) {
        method.append("try {\n")
                .append("conn = this.ds.getConnection();\n")
                .append("pstmt = conn.prepareStatement(SQL);\n");
        if (operation.equalsIgnoreCase("delete")) {
            method.append("pstmt.setInt(i++, " + fieldName + ");\n");
        } else {
            for (Field field : fields) {
                String fieldNameStr = field.getName();
                if (!Arrays.asList("CONSTRAINT").contains(fieldNameStr)) {
                    method.append("pstmt.setObject(i++, ")
                            .append(firstLetterLowerCase(className) + ".get")
                            .append(firstLetterUpperCase(getFieldName(fieldNameStr)))
                            .append("());\n");

                }
            }
        }

        method.append("pstmt.executeUpdate();\n")
                .append("} catch (Exception e) {\n")
                .append("LOG.error(\"Exception at " + operation + className + "\");\n")
                .append("LOG.error(" + className + ".class, e);\n")
                .append("throw e; \n")
                .append("} finally { \n")
                .append("try {\n")
                .append("DatabaseUtility.clear(conn, pstmt, rs);\n")
                .append("} catch (final Exception ex) { // do nothing\n")
                .append("}\n }\n")
                .append("}\n");
    }

    public static void addFields(StringBuffer method, List<Field> fields) {
        //insert fields into the insert query
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!Arrays.asList("CONSTRAINT").contains(fieldName)) {
                method.append(fieldName + ", ");
            }
        }
        method.deleteCharAt(method.lastIndexOf(", "));
    }

    public static String firstLetterUpperCase(String str) {
        if (str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String firstLetterLowerCase(String str) {
        if (str == null) return str;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


}

# Java Crud Tools
# Steps to install:
1. Copy the code to a directory say "brdtools"
2. Run “mvn clean install” from the "brdtools" directory
3. This might take a few minutes to install all the relevant dependenceis
4. run mvn package.
5. This will create a file named "brd-tools-0.0.1-SNAPSHOT.jar" in the target folder
6. change directory to the target folder 
7. run "java -jar "brd-tools-0.0.1-SNAPSHOT.jar"
8. Your spring boot application will start running on port 8080

# Steps to Run using postman:
1. Open postman
2. in the url section type select "POST" and type "localhost:8080/generator/code/" in the address bar
3. In the body section specify the beanName and Query in JSON Format as follows:
e.g.
{
	"beanName": "Policy",
	"sqlQuery": "CREATE TABLE [dbo].[t_ri_m_policy]( [policy_no] [bigint] IDENTITY(1,1) NOT NULL, [policy_code] [varchar](100) NULL,[versi] [varchar](20) NULL,[policy_name] [varchar](500) NULL, [status_policy] [varchar](100) NULL, [document_indonesia] [varchar](100) NULL, [document_english] [varchar](100) NULL, [effective_date] [datetime] NOT NULL, [revision_date] [datetime] NOT NULL, [overdue_date] [datetime] NULL, [socialization_flag] [varchar](1) NULL, [socialization_method] [varchar](2500) NULL, [parent_no] [bigint] NULL,[level_policy] [varchar](2) NULL, [transfer_no] [bigint] NULL, [transfer_reason] [varchar](500) NULL, [transfer_date] [datetime] NULL,[status] [varchar](1) NULL,[cre_dt] [datetime] NOT NULL,[cre_user_no] [bigint] NOT NULL,[upd_dt] [datetime] NULL,[upd_user_no] [bigint] NULL,CONSTRAINT [PK_t_ri_m_policy] PRIMARY KEY CLUSTERED ([policy_no] ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]) ON [PRIMARY]"
	
}
Please ensure that the "JSON" type is selected as the consume type in postman
4. The corresponding CRUD code is generated as an output


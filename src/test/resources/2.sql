CREATE TABLE [dbo].[t_ri_m_policy](
[policy_no] [bigint] IDENTITY(1,1) NOT NULL, [policy_code] [varchar](100) NULL,
[versi] [varchar](20) NULL,
[policy_name] [varchar](500) NULL, [status_policy] [varchar](100) NULL, [document_indonesia] [varchar](100) NULL, [document_english] [varchar](100) NULL, [effective_date] [datetime] NOT NULL, [revision_date] [datetime] NOT NULL, [overdue_date] [datetime] NULL, [socialization_flag] [varchar](1) NULL, [socialization_method] [varchar](2500) NULL, [parent_no] [bigint] NULL,
[level_policy] [varchar](2) NULL, [transfer_no] [bigint] NULL, [transfer_reason] [varchar](500) NULL, [transfer_date] [datetime] NULL,
[status] [varchar](1) NULL,
[cre_dt] [datetime] NOT NULL,
[cre_user_no] [bigint] NOT NULL,
[upd_dt] [datetime] NULL,
[upd_user_no] [bigint] NULL,
CONSTRAINT [PK_t_ri_m_policy] PRIMARY KEY CLUSTERED (
[policy_no] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
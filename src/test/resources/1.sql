CREATE TABLE public.t_ri_m_policy (
policy_no serial NOT NULL,
policy_code character varying(100) NOT NULL, versi character varying(20) NOT NULL, policy_name character varying(500) NULL, status_policy character varying(100) NULL, document_indonesia character varying(100) NULL,
document_english character varying(100) NULL, effective_date timestamp NOT NULL,
revision_date timestamp NOT NULL,
overdue_date timestamp NULL, socialization_flag character varying(1) NULL,
socialization_method character varying(2500) NULL, parent_no bigint NULL,
level_policy character varying(2) NULL,
transfer_no bigint NULL,
transfer_reason character varying(500) NULL, transfer_date timestamp NULL,
status character(1) NOT NULL, cre_dt timestamp NOT NULL, cre_user_no bigint NOT NULL, upd_dt timestamp, upd_user_no bigint,
PRIMARY KEY (policy_no) )
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;
table tb_konsumen

-- public.tb_konsumen definition

-- Drop table

-- DROP TABLE public.tb_konsumen;

CREATE TABLE tb_konsumen (
	id serial4 NOT NULL,
	nama varchar(50) NULL,
	alamat text NULL,
	kota varchar(50) NULL,
	provinsi varchar(50) NULL,
	tgl_registrasi timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	status bpchar(1) NULL,
	CONSTRAINT tb_konsumen_pkey PRIMARY KEY (id)
);


running program: mvn spring-boot:run
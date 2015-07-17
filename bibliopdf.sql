--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.4
-- Started on 2015-06-08 23:18:54

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE bibliopdf;
--
-- TOC entry 1966 (class 1262 OID 31441)
-- Name: bibliopdf; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE bibliopdf WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


ALTER DATABASE bibliopdf OWNER TO postgres;

\connect bibliopdf

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 1967 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 176 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1969 (class 0 OID 0)
-- Dependencies: 176
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 175 (class 1259 OID 31483)
-- Name: comentarios; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comentarios (
    serialnocomentarios integer NOT NULL,
    comentario text,
    patrimonio integer
);


ALTER TABLE public.comentarios OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 31481)
-- Name: comentarios_serialnocomentarios_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comentarios_serialnocomentarios_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comentarios_serialnocomentarios_seq OWNER TO postgres;

--
-- TOC entry 1970 (class 0 OID 0)
-- Dependencies: 174
-- Name: comentarios_serialnocomentarios_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE comentarios_serialnocomentarios_seq OWNED BY comentarios.serialnocomentarios;


--
-- TOC entry 171 (class 1259 OID 31444)
-- Name: dadoscatalogo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dadoscatalogo (
    patrimonio integer NOT NULL,
    titulo text,
    autoria text,
    veiculo text,
    data_publicacao date,
    arquivo text
);


ALTER TABLE public.dadoscatalogo OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 31442)
-- Name: dadoscatalogo_patrimonio_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE dadoscatalogo_patrimonio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dadoscatalogo_patrimonio_seq OWNER TO postgres;

--
-- TOC entry 1971 (class 0 OID 0)
-- Dependencies: 170
-- Name: dadoscatalogo_patrimonio_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE dadoscatalogo_patrimonio_seq OWNED BY dadoscatalogo.patrimonio;


--
-- TOC entry 173 (class 1259 OID 31455)
-- Name: palavras_chave; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE palavras_chave (
    serialno integer NOT NULL,
    palchave text,
    patrimonio integer
);


ALTER TABLE public.palavras_chave OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 31453)
-- Name: palavras_chave_serialno_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE palavras_chave_serialno_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.palavras_chave_serialno_seq OWNER TO postgres;

--
-- TOC entry 1972 (class 0 OID 0)
-- Dependencies: 172
-- Name: palavras_chave_serialno_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE palavras_chave_serialno_seq OWNED BY palavras_chave.serialno;


--
-- TOC entry 1840 (class 2604 OID 31486)
-- Name: serialnocomentarios; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comentarios ALTER COLUMN serialnocomentarios SET DEFAULT nextval('comentarios_serialnocomentarios_seq'::regclass);


--
-- TOC entry 1838 (class 2604 OID 31447)
-- Name: patrimonio; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dadoscatalogo ALTER COLUMN patrimonio SET DEFAULT nextval('dadoscatalogo_patrimonio_seq'::regclass);


--
-- TOC entry 1839 (class 2604 OID 31458)
-- Name: serialno; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavras_chave ALTER COLUMN serialno SET DEFAULT nextval('palavras_chave_serialno_seq'::regclass);


--
-- TOC entry 1961 (class 0 OID 31483)
-- Dependencies: 175
-- Data for Name: comentarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO comentarios VALUES (1, '<span style="color:blue;">Leao</span><br>Este é bom porque a teoria é prática.<br><hr>', 1);
INSERT INTO comentarios VALUES (2, '<span style="color:blue;">Leao</span><br>Este também.<br><hr>', 1);


--
-- TOC entry 1973 (class 0 OID 0)
-- Dependencies: 174
-- Name: comentarios_serialnocomentarios_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comentarios_serialnocomentarios_seq', 2, true);


--
-- TOC entry 1957 (class 0 OID 31444)
-- Dependencies: 171
-- Data for Name: dadoscatalogo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO dadoscatalogo VALUES (2, '50 tons de cinza', 'Amelia', 'manuscrito', '2015-06-06', '000002.pdf');
INSERT INTO dadoscatalogo VALUES (1, 'Running to the water-closet', 'W.Timoshenko', 'Visto em www.deep.web.tv/hackers/point.pdf em 31/02/1970', '2015-06-02', '000001.pdf');


--
-- TOC entry 1974 (class 0 OID 0)
-- Dependencies: 170
-- Name: dadoscatalogo_patrimonio_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('dadoscatalogo_patrimonio_seq', 2, true);


--
-- TOC entry 1959 (class 0 OID 31455)
-- Dependencies: 173
-- Data for Name: palavras_chave; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO palavras_chave VALUES (1, 'programming', 1);
INSERT INTO palavras_chave VALUES (2, 'Java', 1);
INSERT INTO palavras_chave VALUES (3, 'WEB', 1);


--
-- TOC entry 1975 (class 0 OID 0)
-- Dependencies: 172
-- Name: palavras_chave_serialno_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('palavras_chave_serialno_seq', 3, true);


--
-- TOC entry 1842 (class 2606 OID 31452)
-- Name: patrimonio_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dadoscatalogo
    ADD CONSTRAINT patrimonio_pk PRIMARY KEY (patrimonio);


--
-- TOC entry 1844 (class 2606 OID 31463)
-- Name: serialno_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY palavras_chave
    ADD CONSTRAINT serialno_pk PRIMARY KEY (serialno);


--
-- TOC entry 1846 (class 2606 OID 31491)
-- Name: serialnocomentarios_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comentarios
    ADD CONSTRAINT serialnocomentarios_pk PRIMARY KEY (serialnocomentarios);


--
-- TOC entry 1847 (class 2606 OID 31469)
-- Name: patrimonio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY palavras_chave
    ADD CONSTRAINT patrimonio_fk FOREIGN KEY (patrimonio) REFERENCES dadoscatalogo(patrimonio);


--
-- TOC entry 1848 (class 2606 OID 31492)
-- Name: patrimonio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comentarios
    ADD CONSTRAINT patrimonio_fk FOREIGN KEY (patrimonio) REFERENCES dadoscatalogo(patrimonio);


--
-- TOC entry 1968 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-06-08 23:18:56

--
-- PostgreSQL database dump complete
--


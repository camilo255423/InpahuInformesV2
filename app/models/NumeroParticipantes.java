package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import play.db.DB;

public class NumeroParticipantes {
	ArrayList<Facultad> estudiantesEvaluadosPorFacultad;
	ArrayList<Facultad> docentesEvaluadosPorEstudiantesPorFacultad;
	ArrayList<Facultad> docentesConAutoevaluacionPorFacultad;
	ArrayList<Facultad> directivosGestionEvaluadosPorFacultad;
	ArrayList<Facultad> directivosInvestigacionEvaluadosPorFacultad;
	public NumeroParticipantes(
			ArrayList<Facultad> estudiantesEvaluadosPorFacultad,
			ArrayList<Facultad> docentesEvaluadosPorEstudiantesPorFacultad,
			ArrayList<Facultad> docentesConAutoevaluacionPorFacultad,
			ArrayList<Facultad> directivosGestionEvaluadosPorFacultad,
			ArrayList<Facultad> directivosInvestigacionEvaluadosPorFacultad) {
		super();
		this.estudiantesEvaluadosPorFacultad = estudiantesEvaluadosPorFacultad;
		this.docentesEvaluadosPorEstudiantesPorFacultad = docentesEvaluadosPorEstudiantesPorFacultad;
		this.docentesConAutoevaluacionPorFacultad = docentesConAutoevaluacionPorFacultad;
		this.directivosGestionEvaluadosPorFacultad = directivosGestionEvaluadosPorFacultad;
		this.directivosInvestigacionEvaluadosPorFacultad = directivosInvestigacionEvaluadosPorFacultad;
	}
	public static NumeroParticipantes findBySemestre(String semestre)
	{
		ArrayList<Facultad> estudiantesEvaluadosPorFacultad=null;
		ArrayList<Facultad> docentesEvaluadosPorEstudiantesPorFacultad=null;
		ArrayList<Facultad> docentesConAutoevaluacionPorFacultad=null;
		ArrayList<Facultad> directivosGestionEvaluadosPorFacultad=null;
		ArrayList<Facultad> directivosInvestigacionEvaluadosPorFacultad=null;
		Connection con = DB.getConnection();
		PreparedStatement p;
		
		String periodo[] = Periodo.getFecha(semestre);
		String fechaContrato = Periodo.getFechaContrato(semestre);
			try {
				p = con.prepareStatement(NumeroParticipantes.consultaEstudiantesEvaluadosPorFacultad);
				p.setString(1, semestre);
				p.setString(2, semestre);
				p.setString(3, periodo[Periodo.FECHAINICIO]);
				p.setString(4, periodo[Periodo.FECHAFIN]);
				estudiantesEvaluadosPorFacultad = NumeroParticipantes.consultarParticipantes(p); 
				
				p = con.prepareStatement(NumeroParticipantes.consultaDocentesEvaluadosPorEstudiantesPorFacultad);
				p.setString(1, fechaContrato);
				p.setString(2, fechaContrato);
				p.setString(3, fechaContrato);
				p.setString(4, fechaContrato);
				p.setString(5, periodo[Periodo.FECHAINICIO]);
				p.setString(6, periodo[Periodo.FECHAFIN]);
				docentesEvaluadosPorEstudiantesPorFacultad = NumeroParticipantes.consultarParticipantes(p);
				
				p = con.prepareStatement(NumeroParticipantes.consultaDocentesEvaluadosPorEstudiantesPorFacultad);
				p.setString(1, fechaContrato);
				p.setString(2, fechaContrato);
				p.setString(3, fechaContrato);
				p.setString(4, fechaContrato);
				p.setString(5, periodo[Periodo.FECHAINICIO]);
				p.setString(6, periodo[Periodo.FECHAFIN]);
				p.setString(7, semestre);
				p.setString(8, periodo[Periodo.FECHAINICIO]);
				docentesConAutoevaluacionPorFacultad = NumeroParticipantes.consultarParticipantes(p);
				
				p = con.prepareStatement(NumeroParticipantes.consultaDocentesEvaluadosPorEstudiantesPorFacultad);
				p.setString(1, fechaContrato);
				p.setString(2, fechaContrato);		
				p.setString(3, periodo[Periodo.FECHAINICIO]);
				p.setString(4, periodo[Periodo.FECHAFIN]);
				p.setString(5, fechaContrato);
				p.setString(6, fechaContrato);
				p.setString(7, periodo[Periodo.FECHAINICIO]);
				p.setString(8, periodo[Periodo.FECHAFIN]);
				p.setString(9, periodo[Periodo.FECHAINICIO]);
				p.setString(10, periodo[Periodo.FECHAFIN]);
				directivosGestionEvaluadosPorFacultad = NumeroParticipantes.consultarParticipantes(p);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return new NumeroParticipantes(estudiantesEvaluadosPorFacultad, docentesEvaluadosPorEstudiantesPorFacultad, docentesConAutoevaluacionPorFacultad, directivosGestionEvaluadosPorFacultad, directivosInvestigacionEvaluadosPorFacultad);
	}
	private static ArrayList<Facultad> consultarParticipantes(PreparedStatement p)
	{
		
		ArrayList<Facultad> facultades = new ArrayList<Facultad>();
		
		try {
				ResultSet rs=p.executeQuery();
			while (rs.next()) {
				facultades.add(new Facultad(rs.getString("IDFACULTAD"),
						rs.getString("FACULTAD"),rs.getInt("PARTICIPANTES"),
						rs.getInt("TOTAL"),rs.getDouble("PORCENTAJE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Excepcion : "+e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}
		return facultades;
	}
	public ArrayList<Facultad> getEstudiantesEvaluadosPorFacultad() {
		return estudiantesEvaluadosPorFacultad;
	}
	public void setEstudiantesEvaluadosPorFacultad(
			ArrayList<Facultad> estudiantesEvaluadosPorFacultad) {
		this.estudiantesEvaluadosPorFacultad = estudiantesEvaluadosPorFacultad;
	}
	public ArrayList<Facultad> getDocentesEvaluadosPorEstudiantesPorFacultad() {
		return docentesEvaluadosPorEstudiantesPorFacultad;
	}
	public void setDocentesEvaluadosPorEstudiantesPorFacultad(
			ArrayList<Facultad> docentesEvaluadosPorEstudiantesPorFacultad) {
		this.docentesEvaluadosPorEstudiantesPorFacultad = docentesEvaluadosPorEstudiantesPorFacultad;
	}
	public ArrayList<Facultad> getDocentesConAutoevaluacionPorFacultad() {
		return docentesConAutoevaluacionPorFacultad;
	}
	public void setDocentesConAutoevaluacionPorFacultad(
			ArrayList<Facultad> docentesConAutoevaluacionPorFacultad) {
		this.docentesConAutoevaluacionPorFacultad = docentesConAutoevaluacionPorFacultad;
	}
	public ArrayList<Facultad> getDirectivosGestionEvaluadosPorFacultad() {
		return directivosGestionEvaluadosPorFacultad;
	}
	public void setDirectivosGestionEvaluadosPorFacultad(
			ArrayList<Facultad> directivosGestionEvaluadosPorFacultad) {
		this.directivosGestionEvaluadosPorFacultad = directivosGestionEvaluadosPorFacultad;
	}
	public ArrayList<Facultad> getDirectivosInvestigacionEvaluadosPorFacultad() {
		return directivosInvestigacionEvaluadosPorFacultad;
	}
	public void setDirectivosInvestigacionEvaluadosPorFacultad(
			ArrayList<Facultad> directivosInvestigacionEvaluadosPorFacultad) {
		this.directivosInvestigacionEvaluadosPorFacultad = directivosInvestigacionEvaluadosPorFacultad;
	}
	private static final String consultaEstudiantesEvaluadosPorFacultad ="select a.id_facultad as idFacultad, a.facultad as facultad, inscritos as total, encuestados as participantes, 100*encuestados/inscritos as porcentaje from "+
"(select id_facultad,facultad,count(*) as inscritos from ( "+
"SELECT     distinct FAC.ID_DECAN as id_facultad, "+
"           FAC.DESCRIP as  facultad, "+
"           EST.CLI_NUMDCTO "+
"    FROM   sai.ART_ESTUDIANTES EST, "+
"           sai.RCT_CLIENTES CLI, "+
"           sai.ART_PROGRAMAS PGM, "+
"           sai.SCT_DECAN FAC, "+
"           sai.ART_LIQUIDACION_ENC LIQ "+
"   WHERE       CLI.CLI_TIPODCTO = EST.CLI_TIPODCTO "+
"           AND CLI.CLI_NUMDCTO = EST.CLI_NUMDCTO "+
"           AND SUBSTR (EST.EST_CODIGO, 10, 3) = PGM.PRO_CODPROGRAMA "+
"           AND PGM.PRO_ID_DECAN = FAC.ID_DECAN "+
"           AND EST.EST_CODIGO = LIQ.EST_CODIGO "+
"           AND LIQ.PRO_CODIGO = '1' "+
"           AND LIQ.LIQ_SEMESTRE = ? "+
"           AND FAC.ID_DECAN <> 12 "+
"           AND LIQ.LIQ_VALOR > 0 "+
"           AND LIQ.LIQ_ESTADO = 'PAG' "+
") p "+
"group by "+ 
"id_facultad, "+
"facultad) a, "+
"( "+
"select id_facultad,facultad,count(*) as encuestados from ( "+
"SELECT     distinct FAC.ID_DECAN as id_facultad, "+
"           FAC.DESCRIP as  facultad, "+
"           EST.CLI_NUMDCTO "+
"    FROM   sai.ART_ESTUDIANTES EST, "+
"           sai.RCT_CLIENTES CLI, "+
"           sai.ART_PROGRAMAS PGM, "+
"           sai.SCT_DECAN FAC, "+
"           sai.ART_LIQUIDACION_ENC LIQ "+
"   WHERE       CLI.CLI_TIPODCTO = EST.CLI_TIPODCTO "+
"           AND CLI.CLI_NUMDCTO = EST.CLI_NUMDCTO "+
"           AND SUBSTR (EST.EST_CODIGO, 10, 3) = PGM.PRO_CODPROGRAMA "+
"           AND PGM.PRO_ID_DECAN = FAC.ID_DECAN "+
"           AND EST.EST_CODIGO = LIQ.EST_CODIGO "+
"           AND LIQ.PRO_CODIGO = '1' "+
"           AND LIQ.LIQ_SEMESTRE = ? "+
"           AND FAC.ID_DECAN <> 12 "+
"           AND LIQ.LIQ_VALOR > 0 "+
"           AND LIQ.LIQ_ESTADO = 'PAG' "+
"           and  EST.CLI_NUMDCTO in (select distinct cedula "+
" from sai.tbl_encuestado "+
"where fecha> to_date(?, 'yyyy-mm-dd') "+
"and fecha< to_date(?, 'yyyy-mm-dd')) "+
") p "+
"group by "+ 
"id_facultad, "+
"facultad "+
") b "+
"where a.id_facultad=b.id_facultad "+ 
"order by idFacultad"; 
	private static final String consultaDocentesEvaluadosPorEstudiantesPorFacultad=
"select a.ID_DECAN as idFacultad,facultad,evaluados as participantes, total, evaluados*100/inscritos as porcentaje "+
"from "+
"( "+
"SELECT   count(distinct NIT) as inscritos, facultad_centro_costo.ID_DECAN as id_decan, facultad_centro_costo.nombre as facultad "+
"  FROM   ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
" WHERE    E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"  and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
" and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
" group by facultad_centro_costo.ID_DECAN,facultad_centro_costo.nombre "+
" ) a, "+
"(SELECT   count(distinct NIT) as evaluados, facultad_centro_costo.ID_DECAN as id_decan "+
"  FROM   ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
" WHERE    E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"  and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
" and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
" and nit in  "+
" ( "+
"select distinct(SUBSTR (rta.VALOR, 1, INSTR (rta.VALOR, '|') - 1) ) "+
"from sai.tbl_encuestado enc,    sai.tbl_resultados rta, "+
"           sai.tbl_h_cuestionarios cue,  sai.tbl_h_cuestionario_preguntas cpr, "+
"             sai.tbl_h_preguntas pre "+
"           where    enc.idresultados = rta.idresultados "+
"                     AND rta.idpreguntah = cpr.idpreguntah "+
"                     AND rta.idcuestionarioh = cpr.idcuestionarioh "+
"                     AND cue.idcuestionarioh = cpr.idcuestionarioh "+
"                     AND pre.idpreguntah = cpr.idpreguntah "+
"                     AND cue.idcuestionarioh = enc.idcuestionarioh "+
"                     and rta.VALOR like '%||%' "+
"                     and not rta.VALOR like '%||%||%' "+
"                     and   ENC.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+
"where fecha_inicia >  to_date(?,'yyyy-mm-dd')   "+
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
") "+
"group by facultad_centro_costo.ID_DECAN "+
") b "+
"where a.ID_DECAN = b.ID_DECAN "+
"order By IDFACULTAD ";	 
private  static final String consultaDocentesConAutoevaluacionPorFacultad =
"select a.ID_DECAN as idFacultad,facultad,evaluados as participantes, inscritos as total, evaluados*100/inscritos as porcentaje "+
"from "+
"( "+
"SELECT   count(distinct NIT) as inscritos, facultad_centro_costo.ID_DECAN as id_decan, facultad_centro_costo.nombre as facultad "+
"  FROM   ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
" WHERE    E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"  and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
" and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
" group by facultad_centro_costo.ID_DECAN,facultad_centro_costo.nombre "+
" ) a, "+
"( "+
"SELECT   count(distinct NIT) as evaluados, facultad_centro_costo.ID_DECAN as id_decan "+
"  FROM   ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
" WHERE    E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"  and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
" and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
" and nit in ( "+
"  SELECT    DISTINCT CEDULA "+
"    FROM   sai.TBL_RESULTADOS RE, "+
"           sai.TBL_H_PREGUNTAS PRE, "+
"           sai.RCT_CLIENTES CLI, "+
"           sai.TBL_H_CUESTIONARIOS HCU, "+
"           sai.TBL_INSTANCIA_EVADOC TIE, "+
"           sai.TBL_RESULTADOS_EVADOC TRE, "+
"           sai.TBL_H_CUESTIONARIO_PREGUNTAS THCP, "+
"           sai.TBL_ENCUESTADO ENC "+
"   WHERE   HCU.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+ 
"where fecha_inicia >  to_date(?,'yyyy-mm-dd') "+  
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
"AND CEDULA IN (SELECT   DISTINCT (CLI_NUMDCTO) "+
"                            FROM   sai.TBL_DOCENTE_RELACION "+
"                           WHERE   ESTADO = 'ACT') "+
"           AND CLI.CLI_NUMDCTO = CEDULA "+
"           AND RE.IDRESULTADOS = ENC.IDRESULTADOS "+
"           AND HCU.IDCUESTIONARIOH = RE.IDCUESTIONARIOH "+
"           AND ENC.IDCUESTIONARIOH = RE.IDCUESTIONARIOH "+
"           AND ENC.IDCUESTIONARIOH = THCP.IDCUESTIONARIOH "+
"           AND RE.IDPREGUNTAH = PRE.IDPREGUNTAH "+
"           AND PRE.IDPREGUNTAH = THCP.IDPREGUNTAH "+
"           AND TIE.IDINSTANCIA = TRE.IDINSTANCIA "+
"           AND ENC.IDRESULTADOS = TRE.IDRESULTADO "+
"           AND HCU.IDCUESTIONARIOH = ENC.IDCUESTIONARIOH "+
"           AND ENC.IDCUESTIONARIOH = TIE.IDCUESTIONARIOH "+
"           AND RE.IDCUESTIONARIOH = TIE.IDCUESTIONARIOH "+
"           AND HCU.IDCUESTIONARIOH = TIE.IDCUESTIONARIOH "+
"           AND TIE.PERIODO =? "+
"           AND TIE.CODIGO_PROFESOR = CLI.CLI_NUMDCTO "+
"           AND TIE.CODIGO_PROFESOR = CEDULA "+
"           AND ENC.FECHA >= TO_DATE (?, 'yyyy/mm/dd') "+
") "+
" group by facultad_centro_costo.ID_DECAN "+
" ) b "+
" where a.ID_DECAN = b.ID_DECAN "+
" order by idFacultad ";
public static final String consultaDirectivosGestionEvaluadosPorFacultad=
"select a.ID_DECAN as idFacultad,facultad,encuestados as participantes, total, encuestados*100/total as porcentaje "+
"from "+
"(select count(distinct cedula) as total, facultad_centro_costo.ID_DECAN as id_decan, facultad_centro_costo.nombre as facultad "+
"from sai.tbl_encuestado enc,sai.tbl_h_cuestionarios cue,ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
"where  "+
"E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
"and enc.cedula=E.NIT "+
" and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
"and ENC.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+
"where fecha_inicia >  to_date(?,'yyyy-mm-dd')   "+
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
"and enc.IDCUESTIONARIOH=cue.IDCUESTIONARIOH "+
"and cue.titulo like '%GESTION%' "+
"group by facultad_centro_costo.ID_DECAN,facultad_centro_costo.nombre "+
") a, "+
"( "+
"select count(distinct cedula) as encuestados,  facultad_centro_costo.ID_DECAN "+
"from sai.tbl_encuestado enc,sai.tbl_h_cuestionarios cue,ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
"where  "+
"E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
"and enc.cedula=E.NIT "+
" and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
"and ENC.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+
"where fecha_inicia >  to_date(?,'yyyy-mm-dd')   "+
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
"and enc.IDCUESTIONARIOH=cue.IDCUESTIONARIOH "+
"and cue.titulo like '%GESTION%' "+
"and cedula in "+
"( "+
"select distinct(CEDULA) "+
"from sai.tbl_encuestado enc,    sai.tbl_resultados rta, "+
"           sai.tbl_h_cuestionarios cue,  sai.tbl_h_cuestionario_preguntas cpr, "+
"             sai.tbl_h_preguntas pre "+
"           where    enc.idresultados = rta.idresultados "+
"                     AND rta.idpreguntah = cpr.idpreguntah "+
"                     AND rta.idcuestionarioh = cpr.idcuestionarioh "+
"                     AND cue.idcuestionarioh = cpr.idcuestionarioh "+
"                     AND pre.idpreguntah = cpr.idpreguntah "+
"                     AND cue.idcuestionarioh = enc.idcuestionarioh "+
"                     and cue.titulo like '%GESTION%' "+
"                     and   ENC.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+
"where fecha_inicia >  to_date(?,'yyyy-mm-dd')   "+
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
") "+
"group by facultad_centro_costo.ID_DECAN "+
") b "+
" where a.ID_DECAN = b.ID_DECAN "+
"order by idFacultad"; 
public static final String consultaDirectivosInvestigacionEvaluadosPorFacultad=
"select a.ID_DECAN as idFacultad,facultad,encuestados as participantes, total, encuestados*100/total as porcentaje "+
"from "+
"(select count(distinct cedula) as total,  facultad_centro_costo.ID_DECAN as id_decan, facultad_centro_costo.nombre as facultad "+
"from sai.tbl_encuestado enc,sai.tbl_h_cuestionarios cue,ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
"where  "+
"E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
"and enc.cedula=E.NIT "+
" and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
"and ENC.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+
"where fecha_inicia >  to_date(?,'yyyy-mm-dd')   "+
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
"and enc.IDCUESTIONARIOH=cue.IDCUESTIONARIOH "+
"and cue.titulo like '%INVEST%' "+
"group by facultad_centro_costo.ID_DECAN,facultad_centro_costo.nombre "+
") a, "+
"( "+
"select count(distinct cedula) as encuestados,  facultad_centro_costo.ID_DECAN "+
"from sai.tbl_encuestado enc,sai.tbl_h_cuestionarios cue,ICEBERG.EMPLEADO E, ICEBERG.CENTRO_COSTO CC, facultad_centro_costo "+
"where  "+
"E.CENTRO_COSTO = CC.CENTRO_COSTO "+
"and CC.CENTRO_COSTO_PREDECESOR=facultad_centro_costo.centro_costo "+
"and enc.cedula=E.NIT "+
" and to_date(?,'yyyy-mm-dd')>fecha_ingreso and "+ 
" to_date(?,'yyyy-mm-dd')<E.FECHA_FIN_CONTRATO "+
"and ENC.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+
"where fecha_inicia >  to_date(?,'yyyy-mm-dd') "+  
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
"and enc.IDCUESTIONARIOH=cue.IDCUESTIONARIOH "+
"and cue.titulo like '%INVEST%' "+
"and cedula in "+
"( "+
"select distinct(CEDULA) "+
"from sai.tbl_encuestado enc,    sai.tbl_resultados rta, "+
"           sai.tbl_h_cuestionarios cue,  sai.tbl_h_cuestionario_preguntas cpr, "+
"             sai.tbl_h_preguntas pre "+
"           where    enc.idresultados = rta.idresultados "+
"                     AND rta.idpreguntah = cpr.idpreguntah "+
"                     AND rta.idcuestionarioh = cpr.idcuestionarioh "+
"                     AND cue.idcuestionarioh = cpr.idcuestionarioh "+
"                     AND pre.idpreguntah = cpr.idpreguntah "+
"                     AND cue.idcuestionarioh = enc.idcuestionarioh "+
"                     and cue.titulo like '%INVEST%' "+
"                     and   ENC.IDCUESTIONARIOH IN ((SELECT IDCUESTIONARIOH FROM SAI.TBL_H_CUESTIONARIOS "+
"where fecha_inicia >  to_date(?,'yyyy-mm-dd')   "+
"and fecha_inicia < to_date(?,'yyyy-mm-dd'))) "+
") "+
"group by facultad_centro_costo.ID_DECAN "+
") b "+
" where a.ID_DECAN = b.ID_DECAN "+
"order by idFacultad";

}

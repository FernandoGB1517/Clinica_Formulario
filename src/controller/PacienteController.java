package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.PacienteDao;
import excepciones.CampoVacioException;
import excepciones.CodigoBarrasException;
import modelo.Paciente;

public class PacienteController {

	private List<Paciente> pacientes;
	private Connection cn;
	
	public PacienteController(Connection cn) {
		// TODO Auto-generated constructor stub
		this.cn=cn;
	}

	public List<Paciente> getPacientes(String sql) throws SQLException, CampoVacioException, CodigoBarrasException {
		PacienteDao clinicaDao = new PacienteDao(cn);
		pacientes = clinicaDao.getConsultaPacientes(sql);
		
		return pacientes;
	}

	public boolean agregar(String historiaClinica, String nombre, String servicio, String seguroMedico, double importe) 
			throws CampoVacioException, CodigoBarrasException, SQLException{
		boolean agregado= false;
		
		Paciente paciente = new Paciente(historiaClinica, nombre, servicio, seguroMedico, importe);
		
		PacienteDao pacienteDao = new PacienteDao(cn);
		pacienteDao.agregarPacientePst(paciente);
		
		agregado=true;
		
		return agregado;
	}
	
	public int borrar(String sql) throws CampoVacioException, CodigoBarrasException, SQLException {
		int row= 0;
		
		PacienteDao pacienteDao = new PacienteDao(cn);
		row=pacienteDao.borrarPacientePst(sql);
		
		return row;
	}
	
	public int atender(String idconsulta) throws SQLException, CampoVacioException, CodigoBarrasException {
		int row=0;
				
		PacienteDao pacienteDao = new PacienteDao(cn);
		
		String campo="idconsulta";
		String sql="select * from paciente where "+campo+" = '"+idconsulta+"'";
		
		List<Paciente> pacientes=pacienteDao.getConsultaPacientes(sql);
		
		if(pacientes.size()!=0 && !pacientes.get(0).isAtendido()) {
			row=pacienteDao.atenderPacientePst(idconsulta);
		}
		
		if(pacientes.size()!=0 && pacientes.get(0).isAtendido()) {
			row=pacienteDao.DesatenderPacientePst(idconsulta);
		}
		
		return row;
	}

	
	
	

}

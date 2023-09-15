package vista;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.PacienteController;
import dao.DbConnection;
import excepciones.CampoVacioException;
import excepciones.CodigoBarrasException;
import modelo.Paciente;

public class Main {

	public static void main(String[] args) throws SQLException, CampoVacioException, CodigoBarrasException{
		// TODO Auto-generated method stub
		String historiaClinica="9781234567897";
		String nombre="Fernando";
		String servicio="Digestivo";
		String seguroMedico="SANITAS";
		Double importe=200.0;
		
		Paciente paciente;

		paciente = new Paciente(historiaClinica,nombre,servicio,seguroMedico,importe);
		System.out.println(paciente.toString());
		
		frm_Paciente formulario=new frm_Paciente();
		
		/*
		try {
			paciente = new Paciente(historiaClinica,nombre,servicio,seguroMedico,importe);
			System.out.println(paciente.toString());
		
		
		//CONSULTAR
		System.out.println("========== CONSULTAR PACIENTES ==========");
		DbConnection db = new DbConnection();
		Connection cn = db.getConnection();
		PacienteController pacienteController = new PacienteController(cn);
		
		String sql="select * from paciente";
		
		List<Paciente>pacientes=pacienteController.getPacientes(sql);
		for(Paciente p : pacientes) {
			System.out.println(p);
		}
		
		pacienteController=null;
		db.disconect();
		cn=null;
		
		
		//AGREGAR
		System.out.println("========== AGREGAR PACIENTE ==========");
		db = new DbConnection();
		cn = db.getConnection();
		pacienteController = new PacienteController(cn);
		
		historiaClinica="9781234567897";
		nombre="Fernando";
		servicio="Digestivo";
		seguroMedico="SANITAS";
		importe=200.0;
		
		if(pacienteController.agregar(historiaClinica, nombre, servicio, seguroMedico, importe)) {
			System.out.println("EL PACIENTE SE HA AGREGADO CORRECTAMENTE");
		}else {
			System.out.println("NO SE HA PODIDO AGREGAR AL PACIENTE");
		}
					
		pacienteController=null;
		db.disconect();
		cn=null;
		
		
		//BORRAR
		System.out.println("========== BORRAR PACIENTE ==========");
		db = new DbConnection();
		cn = db.getConnection();
		pacienteController = new PacienteController(cn);
		
		String campo="historiaClinica";
		String cadenaBusqueda="9781234567897";
		String sql2="delete from paciente where "+campo+" = '"+cadenaBusqueda+"'";
		
		int row=pacienteController.borrar(sql2);
		if(row>0) {
			System.out.println("Se han borrado: "+row+" pacientes");
		}else {
			System.out.println("No se ha borrado ningun paciente");
		}
		
		pacienteController=null;
		db.disconect();
		cn=null;
		
		
		//ATENDER PACIENTE
		System.out.println("========== ATENDER PACIENTE ==========");
		db = new DbConnection();
		cn = db.getConnection();
		pacienteController = new PacienteController(cn);
		
		String historiaClinicaUpdate="9781234567897";
	
		int row2=pacienteController.atender(historiaClinicaUpdate);
		if(row2>0) {
			System.out.println("Se ha atendido a : "+row2+" pacientes");
		}else {
			System.out.println("No se ha atendido a ningun paciente");
		}
		
		pacienteController=null;
		db.disconect();
		cn=null;
		
		
		} catch (SQLException |  CampoVacioException | CodigoBarrasException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		*/

	}


}

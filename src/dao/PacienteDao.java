package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import excepciones.CampoVacioException;
import excepciones.CodigoBarrasException;
import modelo.Paciente;

public class PacienteDao {

	private Connection cn;
	
	public PacienteDao(Connection cn) {
		// TODO Auto-generated constructor stub
		this.cn=cn;
	}
	
	public List<Paciente> getConsultaPacientes(String sql) throws SQLException, CampoVacioException, CodigoBarrasException{
		List<Paciente>pacientes=new ArrayList<Paciente>();
		PreparedStatement pst = cn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		
		Paciente paciente;
		
		while(rs.next()) {
			int idConsulta=rs.getInt("idConsulta");
			String historiaClinica=rs.getString("historiaClinica");
			String nombre=rs.getString("nombre");
			String servicio=rs.getString("servicio");
			String seguroMedico=rs.getString("seguroMedico");
			Double importe=rs.getDouble("importe");
			Boolean atendido=rs.getBoolean("atendido");
			LocalDate fechaServicio=rs.getDate("fechaServicio").toLocalDate();
			
			paciente= new Paciente(idConsulta,historiaClinica,nombre,servicio,seguroMedico,importe,atendido,fechaServicio);
			pacientes.add(paciente);
			paciente=null;
		}
		
		return pacientes;
		
	}
	
	public boolean agregarPacientePst(Paciente paciente) throws SQLException {
		boolean agregado=false;
		
		String historiaClinica=paciente.getHistoriaClinica();
		String nombre=paciente.getNombre();
		String servicio=paciente.getServicio();
		String seguroMedico=paciente.getSeguroMedico();
		Double importe=paciente.getImporte();
		Boolean atendido=paciente.isAtendido();
		LocalDate fechaServicio=paciente.getFechaServicio();
		
		String sql="insert into paciente values (?,?,?,?,?,?,?,?)";
		PreparedStatement pst = cn.prepareStatement(sql);
		pst.setInt(1, 0);
		pst.setString(2, historiaClinica);
		pst.setString(3, nombre);
		pst.setString(4, servicio);
		pst.setString(5, seguroMedico);
		pst.setDouble(6, importe);
		pst.setBoolean(7, atendido);
		Date fechaServicioPst= Date.valueOf(fechaServicio);
		pst.setDate(8, fechaServicioPst);
		pst.executeUpdate();
		pst=null;
		
		agregado=true;
		
		return agregado;
		
	}
	
	public int borrarPacientePst(String sql) throws SQLException {
		int row=0;
		
		PreparedStatement pst = cn.prepareStatement(sql);
		row=pst.executeUpdate();
		
		return row;
		
	}
	
	public int atenderPacientePst(String idconsulta) throws SQLException {
		int row=0;
		
		String sql="update paciente set atendido=?, fechaServicio=? where idconsulta=?";
		PreparedStatement pst = cn.prepareStatement(sql);
		
		pst.setBoolean(1, true);
		Date fechaServicio=Date.valueOf(LocalDate.now().plusDays(1));
		pst.setDate(2, fechaServicio);
		pst.setString(3, idconsulta);
		
		row=pst.executeUpdate();
		
		return row;
		
	}
	
	public int DesatenderPacientePst(String idconsulta) throws SQLException {
		int row=0;
		
		String sql="update paciente set atendido=?, fechaServicio=? where idconsulta=?";
		PreparedStatement pst = cn.prepareStatement(sql);
		
		pst.setBoolean(1, false);
		Date fechaServicio=Date.valueOf(LocalDate.now().plusDays(1));
		pst.setDate(2, fechaServicio);
		pst.setString(3, idconsulta);
		
		row=pst.executeUpdate();
		
		return row;
		
	}
	


}

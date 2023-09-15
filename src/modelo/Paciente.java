package modelo;

import java.time.LocalDate;

import excepciones.CampoVacioException;
import excepciones.CodigoBarrasException;

public class Paciente {
	
	private int idConsulta;
	private String historiaClinica, nombre, servicio, seguroMedico;
	private Double importe;
	private boolean atendido;
	private LocalDate fechaServicio;
	
	
	public Paciente() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Paciente(int idConsulta, String historiaClinica, String nombre, String servicio, String seguroMedico,
			double importe, boolean atendido, LocalDate fechaServicio) throws CampoVacioException, CodigoBarrasException {
		super();
		this.setIdConsulta(idConsulta);
		this.setHistoriaClinica(historiaClinica);
		this.setNombre(nombre);
		this.setServicio(servicio);
		this.setSeguroMedico(seguroMedico);
		this.setImporte(importe);
		this.setAtendido(atendido);
		this.setFechaServicio(fechaServicio);
	}


	public Paciente(String historiaClinica, String nombre, String servicio, String seguroMedico, double importe) throws CampoVacioException, CodigoBarrasException {
		super();
		this.setIdConsulta(idConsulta);
		this.setHistoriaClinica(historiaClinica);
		this.setNombre(nombre);
		this.setServicio(servicio);
		this.setSeguroMedico(seguroMedico);
		this.setImporte(importe);
		this.setAtendido(false);
		this.setFechaServicio(LocalDate.now());
	}


	public int getIdConsulta() {
		return idConsulta;
	}


	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}


	public String getHistoriaClinica() {
		return historiaClinica;
	}


	public void setHistoriaClinica(String historiaClinica) throws CampoVacioException, CodigoBarrasException {
		if(historiaClinica.length()==0) throw new CampoVacioException();
		if(!compruebaCodigoBarras(historiaClinica))	 throw new CodigoBarrasException();
		this.historiaClinica = historiaClinica;
	}


	private boolean compruebaCodigoBarras(String historiaClinica) throws CodigoBarrasException {
		// TODO Auto-generated method stub
		boolean correcto=true;
		historiaClinica=historiaClinica.replace(" ", "");
		try {
			double codigo=Double.parseDouble(historiaClinica);
			
			if(historiaClinica.length()!=13) {
				correcto=false;
				throw new CodigoBarrasException();
			}

			String dc= historiaClinica.substring(12);
			historiaClinica=historiaClinica.substring(0,12);
			int dControl=Integer.parseInt(dc);
			
			int suma=0;
			int posicion =1;//utilizamos posicion en 1 para que el cero no cuente como par

			for(int x=0; x<historiaClinica.length(); x++) {
				char codigoB= historiaClinica.charAt(x);
				int numero=Character.getNumericValue(codigoB);
				
				if((posicion%2)==0 ) {//sacamos los pares desde posicion
					numero=numero*3;
				}

				suma+=numero;
				posicion++;
			}
			
			int resto= suma%10;
			int resultado=(10-resto);
			
			if(resultado==10) {
				resultado=0;
			}
			
			if(resultado != dControl) {
				correcto=false;
				throw new CodigoBarrasException();
			}
			
	
		} catch (Exception e) {
			// TODO: handle exception
			throw new CodigoBarrasException();
		}
		
		return correcto;
	}
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) throws CampoVacioException {
		if(nombre.length()==0) throw new CampoVacioException();
		this.nombre = nombre;
	}

	


	public String getServicio() {
		return servicio;
	}


	public void setServicio(String servicio) throws CampoVacioException {
		if(servicio.length()==0) throw new CampoVacioException();
		this.servicio = servicio;
	}


	public String getSeguroMedico() {
		return seguroMedico;
	}


	public void setSeguroMedico(String seguroMedico) throws CampoVacioException {
		if(seguroMedico.length()==0) throw new CampoVacioException();
		this.seguroMedico = seguroMedico;
	}


	public Double getImporte() {
		return importe;
	}


	public void setImporte(Double importe) throws CampoVacioException {
		if(importe.equals(null)) {
			importe=0.0;
			throw new CampoVacioException();
		}
		this.importe = importe;
	}


	public boolean isAtendido() {
		return atendido;
	}


	public void setAtendido(boolean atendido) {
		this.atendido = atendido;
	}


	public LocalDate getFechaServicio() {
		return fechaServicio;
	}


	public void setFechaServicio(LocalDate fechaServicio) {
		this.fechaServicio = fechaServicio;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historiaClinica == null) ? 0 : historiaClinica.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		if (historiaClinica == null) {
			if (other.historiaClinica != null)
				return false;
		} else if (!historiaClinica.equals(other.historiaClinica))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Paciente [idConsulta=" + idConsulta + ", historiaClinica=" + historiaClinica + ", nombre=" + nombre
				+ ", servicio=" + servicio + ", seguroMedico=" + seguroMedico + ", importe=" + importe + ", atendido="
				+ atendido + ", fechaServicio=" + fechaServicio + "]";
	}
	
	

}

package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controller.PacienteController;
import dao.DbConnection;
import excepciones.CampoVacioException;
import excepciones.CodigoBarrasException;
import modelo.Paciente;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class frm_Paciente extends JFrame {
	private JPanel panel;
	private JTextField textIdConsultas, textNombre, textServicio,textFechaServicio;
	JCheckBox chckbxAtendido;
	private JButton btnPrimero, btnAtras, btnAdelante, btnUltimo;
	private JButton btnNuevo, btnEditar, btnGuardar, btnDeshacer, btnBorrar;
	private JPanel panelMantenimiento;
	private JPanel panelGrid;
	private JScrollPane scrollPane;
	private JTable tblPacientes;	
	DefaultTableModel dtm;
	private JTextField textHistoriaClinica;
	private JFrame frame;
	JComboBox comboCampo;
	private JTextField textFiltrar;
	JButton btnFiltrar;
	JLabel lblConsulta;	
	private DbConnection db;
	private PacienteController tablaPacientes;
	private List<Paciente>pacientes;
	private int puntero;
	private boolean nuevoRegistro=false;
	private ResultSet rs;
	
	private JRadioButton rdbtnDKV;
	private JRadioButton rdbtnSanitas;
	private JRadioButton rdbtnOtro;
	private boolean editar=false;
	ButtonGroup seguro;
	ArrayList<JRadioButton>listaSeguro;
	private final String[] titulos={"id_consultas","nombre","servicio","seguro_medico","historia_clinica", "importe", "atendido", "fecha_servicio"};
	private JTextField textImporte;
	
	
	public frm_Paciente() {
		setTitle("F O R M U L A R I O   D E   M A N T E N I M I E N T O");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1182, 482);
		puntero=0;
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		definirVentana();
		definirEventos();
		cargarDatos("select * from paciente");
		frame=this;
		this.setVisible(true);
	}
	



	private void cargarDatos(String sql) {
			// TODO Auto-generated method stub
		DbConnection db;
		try {
			db = new DbConnection();
			Connection cn=db.getConnection();	
			System.out.println("DB conect...");
			PacienteController pacienteController=new PacienteController(cn);
			
			pacientes=pacienteController.getPacientes(sql);
			
			pacienteController=null;
			db.disconect();
			db=null;
			
			dtm.setRowCount(0);// Antes de llenar la tabla la vaciamos
			dtm.setColumnCount(0);
			dtm.setColumnIdentifiers(titulos);// pone los titulo en el panel
			
			for(Paciente p: pacientes) {
				
				Object[]fila= {p.getIdConsulta(),p.getNombre(),p.getServicio(),p.getSeguroMedico(),p.getHistoriaClinica(), p.getImporte(),p.isAtendido(), p.getFechaServicio()};
				dtm.addRow(fila);
			}
			
			mostrar(puntero);
			
		} catch (SQLException | CodigoBarrasException | CampoVacioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		}

	private void mostrar(int pp) {
		// TODO Auto-generated method stub
		Paciente p=pacientes.get(pp);
		
		int idConsulta=p.getIdConsulta();
		textIdConsultas.setText(Integer.toString(idConsulta));
		double importe=p.getImporte();
		textImporte.setText(Double.toString(importe));
		
		String seguroMedico=(p.getSeguroMedico());
		
		textServicio.setText(p.getServicio());
		textHistoriaClinica.setText(p.getHistoriaClinica());
		textNombre.setText(p.getNombre());
		
		if(p.getFechaServicio()!=null) {
		textFechaServicio.setText(p.getFechaServicio().toString());
		}else {
			textFechaServicio.setText("");
		}
		
		if(p.isAtendido()) {
			chckbxAtendido.setSelected(true);
		}else {
			chckbxAtendido.setSelected(false);
		}
		
		switch(seguroMedico) {
		case "DKV":
			rdbtnDKV.setSelected(true);
			break;
		case "SANITAS":
			rdbtnSanitas.setSelected(true);
			break;
		case "OTRO":
			rdbtnOtro.setSelected(true);
			break;
		default:
	}
	habilitarPanelLibros(false);

		
	}


//E V E N T O S

	private void definirEventos() {
		// TODO Auto-generated method stub
		//Ir al primer registro
		
		 btnPrimero.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 	puntero=0;
			 							
					mostrar(puntero);
											 		
			 	}
			 });
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// iralanterior();
						if (puntero > 0) {
							puntero--;
							mostrar(puntero);
						}

					} catch (Exception ex) {
						// TODO Bloque catch generado automï¿½ticamente
						ex.printStackTrace();
					}

				}
			});
			btnAdelante.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// iralsiguiente();
						int ultimo_Reg = pacientes.size() - 1;
						if (puntero < ultimo_Reg) {
							puntero++;
							mostrar(puntero);
						}

					} catch (Exception ex) {
						// TODO Bloque catch generado automï¿½ticamente
						ex.printStackTrace();
					}
				}
			});
			btnUltimo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// iralfinal();
						puntero = pacientes.size() - 1;
						mostrar(puntero);

					} catch (Exception ex) {
						// TODO Bloque catch generado automï¿½ticamente
						ex.printStackTrace();
					}
				}
			});
			btnNuevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					habilitarNavegador(false);
					habilitarPanelMantenimiento(false);
					habilitarPanelLibros(true);
					
					textServicio.setText("");
					textImporte.setText("");
					textHistoriaClinica.setText("");
					textFechaServicio.setText("");
					textNombre.setText("");
					
					rdbtnDKV.setSelected(false);
					rdbtnSanitas.setSelected(false);
					rdbtnOtro.setSelected(false);
					
					textFechaServicio.setEditable(false);
					chckbxAtendido.setEnabled(false);
					nuevoRegistro=true;

				}
			});
			btnDeshacer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					habilitarNavegador(true);
					habilitarPanelMantenimiento(true);
					habilitarPanelLibros(false);
					mostrar(puntero);

				}
			});
			// Editar un registro
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					habilitarPanelLibros(false);
					
					chckbxAtendido.setEnabled(true);
					rdbtnDKV.setSelected(false);
					rdbtnSanitas.setSelected(false);
					rdbtnOtro.setSelected(false);
					habilitarNavegador(false);
					
					habilitarPanelMantenimiento(false);

				}
			});
			
			// Guardar registro nuevo o edicion
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(nuevoRegistro==true) {
						try {
							db=new DbConnection();
							Connection cn=db.getConnection();
							PacienteController pacienteController=new PacienteController(cn);
							
							String historiaClinica=textHistoriaClinica.getText();
							String nombre=textNombre.getText();
							String servicio=textServicio.getText();
							String seguroMedico="";
							for(JRadioButton radio:listaSeguro) {
								if(radio.isSelected()) {
									seguroMedico=radio.getText();
								}
							}
							String importeTexto=textImporte.getText();
							Double importe=Double.valueOf(importeTexto);
							
							pacienteController.agregar(historiaClinica, nombre, servicio, seguroMedico, importe);
							String sql="select * from paciente";
							cargarDatos(sql);
							
							nuevoRegistro=false;
							db.disconect();
							db=null;
							pacienteController=null;
							
							puntero=pacientes.size()-1;
							mostrar(puntero);
							
							habilitarNavegador(true);
							habilitarPanelMantenimiento(true);
							habilitarPanelLibros(false);
						} catch (SQLException | CodigoBarrasException | CampoVacioException e1) {
							JOptionPane.showMessageDialog(frame, e1.getMessage());
						}
					}else {
						try {
							boolean atendido=chckbxAtendido.isSelected();
							String idconsulta=textIdConsultas.getText();
							
							db=new DbConnection();
							Connection cn=db.getConnection();
							PacienteController pacienteController=new PacienteController(cn);
							
							pacienteController.atender(idconsulta);
							String sql="select * from paciente";
							cargarDatos(sql);
							
							db.disconect();
							db=null;
							pacienteController=null;
							
							mostrar(puntero);
							
							habilitarNavegador(true);
							habilitarPanelMantenimiento(true);
							habilitarPanelLibros(false);
							
						} catch (SQLException | CodigoBarrasException | CampoVacioException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(frame, e1.getMessage());
						}
					}
				}

				
			});

			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {	
					int confirmado = JOptionPane.showConfirmDialog(frame, "¿Estas seguro que quieres borrar?","Confirmación", JOptionPane.YES_NO_OPTION);
					if (JOptionPane.YES_NO_OPTION==confirmado ){

							String campo="idconsulta";
							String busqueda=textIdConsultas.getText();
							String sql="delete  from paciente where "+campo+" = '"+busqueda+"'";
							try {
								db=new DbConnection();
								Connection cn=db.getConnection();
								PacienteController pacienteController=new PacienteController(cn);
								
								int rows=pacienteController.borrar(sql);
								JOptionPane.showMessageDialog(frame,"Se ha eliminado " +rows+ " registros");
								
								//mostrar ya borrado
								String sql2="select * from paciente";
								
								//comprobar si se borra el ultimo registro
								int ultima=pacientes.size()-1;
								if(ultima==0) {
									listaVacia();
									dtm.setRowCount(0);// Antes de llenar la tabla la vaciamos
									dtm.setColumnCount(0);
									dtm.setColumnIdentifiers(titulos);// pone los titulo en el panel
								}else {
									puntero=0;
									cargarDatos(sql2);
								}
								
								db.disconect();
								db=null;
								pacienteController=null;
								

							} catch (SQLException | CampoVacioException | CodigoBarrasException e) {
								e.printStackTrace();
							}

					}
				}

				private void listaVacia() {
					// TODO Auto-generated method stub
						textIdConsultas.setText("");
						textNombre.setText("");
						textServicio.setText("");
						textImporte.setText("");
						textFechaServicio.setText("");
						textHistoriaClinica.setText("");
						chckbxAtendido.setSelected(false);
						rdbtnSanitas.setSelected(false);
						rdbtnDKV.setSelected(false);
						rdbtnOtro.setSelected(false);
				}		
			});
			

			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				 	String campo=comboCampo.getSelectedItem().toString();
				 	String busqueda=textFiltrar.getText();
				 	String sql="select * from paciente where "+campo+" like '"+busqueda+"%'";
				 		
				 	if(busqueda.equals("")) {
						sql="select * from paciente";
				 	}
				 		
				 	try {
						db=new DbConnection();
						Connection cn=db.getConnection();
						PacienteController pacienteController=new PacienteController(cn);
							
						pacientes=pacienteController.getPacientes(sql);
						cargarDatos(sql);
							
						db.disconect();
						db=null;
						pacienteController=null;
						
						mostrar(puntero);
							
					} catch (SQLException | CodigoBarrasException | CampoVacioException e2) {
						e2.printStackTrace();
					}	
				}
			});
			 
			tblPacientes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JTable click=(JTable)e.getSource();
					puntero=click.getSelectedRow();
					mostrar(puntero);
				}
			});	 
	//end eventos	
	}






	
	

	// D E F I N I R   V E N T A N A
private void definirVentana() {
	// TODO Apï¿½ndice de mï¿½todo generado automï¿½ticamente
	JPanel panelLibros = new JPanel();
	panelLibros.setLayout(null);
	panelLibros.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Pacientes", TitledBorder.LEADING,
			TitledBorder.TOP, null, Color.BLUE));
	panelLibros.setBounds(28, 88, 298, 278);
	panel.add(panelLibros);

	textIdConsultas = new JTextField();
	textIdConsultas.setEditable(false);
	textIdConsultas.setColumns(10);
	textIdConsultas.setBounds(121, 31, 167, 20);
	panelLibros.add(textIdConsultas);

	JLabel label_IdLibros = new JLabel("IdConsultas");
	label_IdLibros.setFont(new Font("Tahoma", Font.BOLD, 11));
	label_IdLibros.setBounds(26, 34, 97, 14);
	panelLibros.add(label_IdLibros);

	JLabel label_Titulo = new JLabel("Nombre");
	label_Titulo.setFont(new Font("Tahoma", Font.BOLD, 11));
	label_Titulo.setBounds(26, 62, 97, 14);
	panelLibros.add(label_Titulo);

	textNombre = new JTextField();
	textNombre.setEditable(false);
	textNombre.setColumns(10);
	textNombre.setBounds(121, 59, 167, 20);
	panelLibros.add(textNombre);

	JLabel label_Autor = new JLabel("Servicio");
	label_Autor.setFont(new Font("Tahoma", Font.BOLD, 11));
	label_Autor.setBounds(26, 90, 97, 14);
	panelLibros.add(label_Autor);

	textServicio = new JTextField();
	textServicio.setEditable(false);
	textServicio.setColumns(10);
	textServicio.setBounds(121, 87, 167, 20);
	panelLibros.add(textServicio);
	
	JLabel lblFecha = new JLabel("Fecha Servicio");
	lblFecha.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblFecha.setBounds(26, 174, 97, 14);
	panelLibros.add(lblFecha);
	
	textFechaServicio = new JTextField();
	textFechaServicio.setBounds(121, 171, 85, 20);
	textFechaServicio.setEditable(false);
	panelLibros.add(textFechaServicio);
	textFechaServicio.setColumns(10);
	
	chckbxAtendido = new JCheckBox("Atendido");
	chckbxAtendido.setFont(new Font("Tahoma", Font.BOLD, 11));
	chckbxAtendido.setBounds(26, 195, 97, 23);
	panelLibros.add(chckbxAtendido);
	
	JLabel lblIsbn = new JLabel("Historia Clinica");
	lblIsbn.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblIsbn.setBounds(26, 118, 97, 14);
	panelLibros.add(lblIsbn);
	
	textHistoriaClinica = new JTextField();
	textHistoriaClinica.setEditable(false);
	textHistoriaClinica.setBounds(121, 115, 167, 20);
	panelLibros.add(textHistoriaClinica);
	textHistoriaClinica.setColumns(10);
	
	JLabel lblAaammdd = new JLabel("aaaa-MM-dd");
	lblAaammdd.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblAaammdd.setBounds(210, 174, 78, 14);
	panelLibros.add(lblAaammdd);
	
	JLabel lblImporte = new JLabel("Importe");
	lblImporte.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblImporte.setBounds(26, 146, 97, 14);
	panelLibros.add(lblImporte);
	
	textImporte = new JTextField();
	textImporte.setEditable(false);
	textImporte.setColumns(10);
	textImporte.setBounds(121, 143, 167, 20);
	panelLibros.add(textImporte);
	
	seguro=new ButtonGroup();
	listaSeguro=new ArrayList<JRadioButton>();
	
	JLabel lblseguro = new JLabel("SEGURO MEDICO");
	lblseguro.setBounds(26, 226, 109, 13);
	panelLibros.add(lblseguro);
	
	rdbtnDKV = new JRadioButton("DKV");
	rdbtnDKV.setBounds(26, 247, 68, 21);
	seguro.add(rdbtnDKV);
	listaSeguro.add(rdbtnDKV);
	panelLibros.add(rdbtnDKV);
	
	rdbtnSanitas = new JRadioButton("SANITAS");
	rdbtnSanitas.setBounds(105, 247, 78, 21);
	seguro.add(rdbtnSanitas);
	listaSeguro.add(rdbtnSanitas);
	panelLibros.add(rdbtnSanitas);
	
	rdbtnOtro = new JRadioButton("OTRO");
	rdbtnOtro.setBounds(197, 247, 68, 21);
	seguro.add(rdbtnOtro);
	listaSeguro.add(rdbtnOtro);
	panelLibros.add(rdbtnOtro);
	

	JPanel panelNavegador = new JPanel();
	panelNavegador.setLayout(null);
	panelNavegador.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Navegador", TitledBorder.LEADING,
			TitledBorder.TOP, null, Color.BLUE));
	panelNavegador.setBounds(28, 366, 218, 70);
	panel.add(panelNavegador);

	// NAVEGADOR
	ImageIcon imaNav = new ImageIcon("imagenes/navPri.jpg");
	btnPrimero = new JButton("", imaNav);

	btnPrimero.setBounds(18, 19, 40, 40);
	panelNavegador.add(btnPrimero);
	imaNav = new ImageIcon("imagenes/navIzq.jpg");
	btnAtras = new JButton("", imaNav);

	btnAtras.setBounds(68, 19, 40, 40);
	panelNavegador.add(btnAtras);
	imaNav = new ImageIcon("imagenes/navDer.jpg");
	btnAdelante = new JButton("", imaNav);
	
	btnAdelante.setBounds(118, 19, 40, 40);
	panelNavegador.add(btnAdelante);
	imaNav = new ImageIcon("imagenes/navUlt.jpg");
	btnUltimo = new JButton("", imaNav);

	btnUltimo.setBounds(168, 19, 40, 40);
	panelNavegador.add(btnUltimo);
	
	panelMantenimiento = new JPanel();
	panelMantenimiento.setLayout(null);
	panelMantenimiento.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Mantenimiento Pacientes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
	panelMantenimiento.setBounds(28, 11, 266, 74);
	panel.add(panelMantenimiento);
	
	imaNav = new ImageIcon("imagenes/botonAgregar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnNuevo = new JButton("", imaNav);
	

	btnNuevo.setToolTipText("Insertar Nuevo Libro");
	btnNuevo.setBounds(10, 23, 40, 40);
	panelMantenimiento.add(btnNuevo);
	
	imaNav = new ImageIcon("imagenes/botonEditar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnEditar = new JButton("", imaNav);
	

	btnEditar.setToolTipText("Editar");
	btnEditar.setBounds(60, 23, 40, 40);
	panelMantenimiento.add(btnEditar);
	
	imaNav = new ImageIcon("imagenes/botonGuardar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnGuardar = new JButton("", imaNav);
	


	btnGuardar.setEnabled(false);
	btnGuardar.setToolTipText("Guardar");
	btnGuardar.setBounds(161, 23, 40, 40);
	panelMantenimiento.add(btnGuardar);
	
	imaNav = new ImageIcon("imagenes/botonDeshacer.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnDeshacer = new JButton("", imaNav);


	btnDeshacer.setEnabled(false);
	btnDeshacer.setToolTipText("Deshacer");
	btnDeshacer.setBounds(211, 23, 40, 40);
	panelMantenimiento.add(btnDeshacer);
	
	imaNav = new ImageIcon("imagenes/borrar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnBorrar = new JButton("", imaNav);
	btnBorrar.setToolTipText("Borrar Registro");
	btnBorrar.setBounds(111, 23, 40, 40);
	panelMantenimiento.add(btnBorrar);
	
	panelGrid = new JPanel();
	panelGrid.setBounds(363, 98, 766, 305);
	panel.add(panelGrid);
	panelGrid.setLayout(new BorderLayout(0, 0));
	
	scrollPane = new JScrollPane();
	panelGrid.add(scrollPane, BorderLayout.CENTER);
	dtm=new DefaultTableModel();
	tblPacientes = new JTable(dtm);

	
	scrollPane.setViewportView(tblPacientes);
	
	comboCampo = new JComboBox();
	comboCampo.setModel(new DefaultComboBoxModel(new String[] {"nombre", "servicio", "seguroMedico"}));
	comboCampo.setBounds(363, 52, 137, 20);
	panel.add(comboCampo);
	
	textFiltrar = new JTextField();
	textFiltrar.setBounds(510, 52, 200, 20);
	panel.add(textFiltrar);
	textFiltrar.setColumns(10);
	
	btnFiltrar = new JButton("FILTRAR");
	
	
	btnFiltrar.setBounds(720, 51, 89, 23);
	panel.add(btnFiltrar);
	
	lblConsulta = new JLabel("Consulta");
	lblConsulta.setBounds(363, 21, 286, 14);
	panel.add(lblConsulta);

}


//HABILITAR DESABILITAR PANELES
private void habilitarPanelLibros(boolean sw){
		
		textServicio.setEditable(sw);
		textNombre.setEditable(sw);
		textImporte.setEditable(sw);
		textFechaServicio.setEditable(sw);
		textHistoriaClinica.setEditable(sw);
		chckbxAtendido.setEnabled(sw);
		
		rdbtnDKV.setEnabled(sw);
		rdbtnSanitas.setEnabled(sw);
		rdbtnOtro.setEnabled(sw);
	}
	
	private void habilitarPanelMantenimiento(boolean sw){
		
		btnNuevo.setEnabled(sw);
		btnEditar.setEnabled(sw);
		btnBorrar.setEnabled(sw);
	    btnGuardar.setEnabled(!sw);
	    btnDeshacer.setEnabled(!sw);
				
		
	}
	private void deshabilitarPanelMantenimiento(){
		boolean sw=false;
		btnNuevo.setEnabled(sw);
		btnEditar.setEnabled(sw);
		btnBorrar.setEnabled(sw);
	    btnGuardar.setEnabled(sw);
	    btnDeshacer.setEnabled(sw);
				
		
	}
	
	
	private void habilitarNavegador(boolean sw){
		
		btnPrimero.setEnabled(sw);
		btnAtras.setEnabled(sw);
		btnAdelante.setEnabled(sw);
		btnUltimo.setEnabled(sw);
		
		
	}
	
	
}


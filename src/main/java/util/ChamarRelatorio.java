package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JScrollBar;

import org.hibernate.jdbc.Work;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.export.HtmlExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class ChamarRelatorio implements Work {

	private String caminhoRelatorio;
	private String nomeRelatorio;
	private HashMap param;

	public ChamarRelatorio(String caminho, HashMap parametros, String nome) {
		this.caminhoRelatorio = caminho;
		this.nomeRelatorio = nome;
		this.param = parametros;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		String absolute_path="C:\\Users/CEDI/Downloads";
		try {

			HashMap parametros = param;

			FacesContext facesContext = FacesContext.getCurrentInstance();

			facesContext.responseComplete();

			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					scontext.getRealPath("/WEB-INF/relatorios/" + caminhoRelatorio), parametros, connection);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

		
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			
		//	 JasperExportManager.exportReportToHtmlFile(jasperPrint, absolute_path+nomeRelatorio+".html");


			 
			 

			byte[] bytes = baos.toByteArray();

			if (bytes != null && bytes.length > 0) {

				HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

				response.setContentType("application/pdf");

				response.setHeader("Content-disposition", "inline; filename=\"" + nomeRelatorio + ".pdf\"");

				response.setContentLength(bytes.length);
				 
				ServletOutputStream outputStream = response.getOutputStream();

				outputStream.write(bytes, 0, bytes.length);

				outputStream.flush();

				outputStream.close();

				baos.close();

			}

		} catch (Exception e) {

			throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);

		}

	}

	// @Override
	// public void execute(Connection connection) throws SQLException {
	//
	// try {
	//
	// HashMap parametros = param;
	//
	// FacesContext facesContext = FacesContext.getCurrentInstance();
	//
	// facesContext.responseComplete();
	//
	// ServletContext scontext = (ServletContext)
	// facesContext.getExternalContext().getContext();
	//
	// JasperPrint jasperPrint = JasperFillManager.fillReport(
	// scontext.getRealPath("/WEB-INF/relatorios/" + caminhoRelatorio),
	// parametros, connection);
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//
	// // JasperRunManager.runReportToHtmlFile(outputStream, params)
	//
	// JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
	//
	// //JasperExportManager.exportReportToHtmlFile(jasperPrint, nome);
	//
	// byte[] bytes = baos.toByteArray();
	//
	// if (bytes != null && bytes.length > 0) {
	//
	// HttpServletResponse response = (HttpServletResponse)
	// facesContext.getExternalContext().getResponse();
	//
	// response.setContentType("application/pdf");
	//
	// response.setHeader("Content-disposition", "attachment; filename=\"" +
	// nomeRelatorio + ".pdf\"");
	//
	// response.setContentLength(bytes.length);
	//
	// ServletOutputStream outputStream = response.getOutputStream();
	//
	// outputStream.write(bytes, 0, bytes.length);
	//
	// outputStream.flush();
	//
	// outputStream.close();
	//
	// baos.close();
	//
	//
	//
	//
	//
	//
	//
	// }
	//
	// } catch (Exception e) {
	//
	// throw new SQLException("Erro ao executar relat�rio " +
	// this.caminhoRelatorio, e);
	//
	// }
	//
	// }
	/*
	 * 
	 * public void imprimeRelatorio(String caminhoRelatorio, HashMap param,
	 * String nomeRelatorio) throws IOException, SQLException {
	 * 
	 * 
	 * Connection con = ConexaoBanco.getConexao().getConnection();
	 * 
	 * 
	 * HashMap parametros = param;
	 * 
	 * try {
	 * 
	 * FacesContext facesContext = FacesContext.getCurrentInstance();
	 * 
	 * facesContext.responseComplete();
	 * 
	 * ServletContext scontext = (ServletContext)
	 * facesContext.getExternalContext().getContext();
	 * 
	 * JasperPrint jasperPrint = JasperFillManager
	 * .fillReport(scontext.getRealPath("/WEB-INF/relatorios/" +
	 * caminhoRelatorio), parametros, con);
	 * 
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * 
	 * JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
	 * 
	 * byte[] bytes = baos.toByteArray();
	 * 
	 * if (bytes != null && bytes.length > 0) {
	 * 
	 * HttpServletResponse response = (HttpServletResponse)
	 * facesContext.getExternalContext().getResponse();
	 * 
	 * response.setContentType("application/pdf");
	 * 
	 * response.setHeader("Content-disposition", "attachment; filename=\"" +
	 * nomeRelatorio + ".pdf\"");
	 * 
	 * response.setContentLength(bytes.length);
	 * 
	 * ServletOutputStream outputStream = response.getOutputStream();
	 * 
	 * outputStream.write(bytes, 0, bytes.length);
	 * 
	 * outputStream.flush();
	 * 
	 * outputStream.close();
	 * 
	 * baos.close(); }
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * }
	 */

}

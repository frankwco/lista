package base.converter;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import dao.GenericDAO;
import base.modelo.Estado; 
import util.Mensagem;

@Named("converterEstado")
public class ConverterEstado implements Converter{

	@Inject
	private GenericDAO<Estado> dao;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				
			 return dao.buscarPorId(Estado.class, Long.parseLong(value));
			 
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Mensagem.ERRO_CONVERTER, ""));
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof Estado)) return null;
		    return  String.valueOf(((Estado) value).getId());
		
		
	}
}

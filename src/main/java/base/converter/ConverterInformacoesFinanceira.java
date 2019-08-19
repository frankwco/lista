package base.converter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import dao.GenericDAO;
import base.modelo.InformacaoFinanceira; 
import util.Mensagem;

@Named("converterInformacoesFinanceiras")
public class ConverterInformacoesFinanceira implements Converter{

	@Inject
	private GenericDAO<InformacaoFinanceira> dao;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				
			 return dao.buscarPorId(InformacaoFinanceira.class, Long.parseLong(value));
			 
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
		if (!(value instanceof InformacaoFinanceira)) return null;
		    return  String.valueOf(((InformacaoFinanceira) value).getId());
		
		
	}
}

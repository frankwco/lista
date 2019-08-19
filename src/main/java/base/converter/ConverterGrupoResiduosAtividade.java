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
import base.modelo.Atividade;
import base.modelo.GrupoResiduo;
import util.Mensagem;

@Named("converterGrupoResiduo")
public class ConverterGrupoResiduosAtividade implements Converter{

	@Inject
	private GenericDAO<GrupoResiduo> dao;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				
			 return dao.buscarPorId(GrupoResiduo.class, Long.parseLong(value));
			 
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
		if (!(value instanceof GrupoResiduo)) return null;
		    return  String.valueOf(((GrupoResiduo) value).getId());
		
		
	}
}

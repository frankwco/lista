package util.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validacaoCNPJ")
public class ValidacaoCNPJ implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String cnpj = (String) arg2;
		if(!ValidaCNPJ.isCNPJ(cnpj)) {
			FacesMessage msg = 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"CNPJ Incorreto!!", "CNPJ Incorreto!!");			
			throw new ValidatorException(msg);
		}
	}

}

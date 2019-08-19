package util.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validacaoCPF")
public class ValidacaoCPF implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String cpf = (String) arg2;
		if(!ValidaCPF.isCPF(cpf)) {
			FacesMessage msg = 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"CPF Incorreto!", "CPF Incorreto!!");			
			throw new ValidatorException(msg);
		}
	}

}

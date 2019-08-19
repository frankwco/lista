package lista.controle;

import java.io.PrintStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import base.modelo.Atividade;
import base.service.AtividadeService;
import dao.GenericDAO;
import lista.modelo.EntidadeItensServicoLista;
import lista.modelo.EntidadeLista;
import lista.modelo.EntidadeServicoLista;
import lista.service.ItensServicoListaService;

@ViewScoped
@Named("listaMB")
public class ListaMB implements Serializable {
	
	private EntidadeLista listaSelecionada;
	private List<EntidadeLista> listaLista;
	private EntidadeServicoLista servicoListaSelecionado;
	private List<EntidadeServicoLista> listaServicoLista;
	private EntidadeItensServicoLista itemServicoLista;
	private List<EntidadeItensServicoLista> listaItensServicoLista;
	private DateFormat formataDataConsulta = new SimpleDateFormat("yyyy-MM-dd");
	
	@Inject
	private GenericDAO<EntidadeItensServicoLista> daoItensServicoLista; // faz as buscas

	@Inject
	private ItensServicoListaService itensServicoService; // inserir no banco

	public void iniciaListagem() {
		this.listaLista = null;
	}

	public void excluirServicoLista() {
		if (getServicoListaSelecionado().getId() != null) {
			List<EntidadeItensServicoLista> li = this.daoItensServicoLista.listar(
					EntidadeItensServicoLista.class, "servicoLista.id=" + this.servicoListaSelecionado.getId());
			for (EntidadeItensServicoLista l : li) {
				l.setStatus("excluido");
				this.itensServicoService.inserirAlterar(l);
			}
			this.servicoListaSelecionado.setStatus("excluido");
			this.service.salvar(this.servicoListaSelecionado);
			this.servicoListaSelecionado = null;
			this.listaServicoLista = null;
		}
	}

	public void novaLista() {
		this.listaSelecionada = null;
		this.itemServicoLista = null;
		this.listaItensServicoLista = null;
		this.listaServicoLista = null;
		this.servicoListaSelecionado = null;
	}

	public void novoServicoLista() {
		this.servicoListaSelecionado = null;
	}

	public void novoItemServico() {
		this.itemServicoLista = null;
	}

	public void setarOrdem() {
		this.servicoListaSelecionado.setOrdem(this.servicoListaSelecionado.getTipoServico().getOrdemAproximada());
	}

	public void inserirItemServico() {
		if (this.itemServicoLista.getId() == null) {
			String cod = RetornaDadosSessao.RetornaCodigoEmpresa();
			this.itemServicoLista.setCodigoCasaOracao(cod);
			this.itemServicoLista.setServicoLista(this.servicoListaSelecionado);
			this.itemServicoLista.setServicoLista(this.servicoListaSelecionado);
			this.service.inserir(this.itemServicoLista);
		} else {
			this.service.salvar(this.itemServicoLista);
		}
		this.listaItensServicoLista = null;
		this.itemServicoLista = null;
	}

	public void excluirItemServico() {
		if (getItemServicoLista().getId() != null) {
			this.itemServicoLista.setStatus("excluido");
			this.service.salvar(this.itemServicoLista);
		}
		this.listaItensServicoLista = null;
		this.itemServicoLista = null;
	}

	public void imprimir() {
		ChamaRelatorio chamaRelatorio;
		if (getListaSelecionada().getId() != null) {
			List<EntidadeServicoLista> sl = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class,
					"lista.id=" + this.listaSelecionada.getId());
			for (EntidadeServicoLista s : sl) {
				if (s.getTipoDescricao().equalsIgnoreCase("Automatico")) {
					List it = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class,
							"servicoLista.id=" + s.getId());
					if (it.size() > 1) {
						s.setDescricao(s.getTipoServico().getNomePlural());
						this.service.salvar(s);
					} else {
						s.setDescricao(s.getTipoServico().getNomeSingular());
						this.service.salvar(s);
					}
				}
			}
			alterarLista();
			HashMap par = new HashMap();
			System.out.println("id: " + this.listaSelecionada.getId());
			par.put("REPORT_LOCALE", new Locale("pt", "BR"));
			par.put("IDLISTA", this.listaSelecionada.getId());
			par.put("SUBREPORT_DIR", "/relatorios/");

			chamaRelatorio = new ChamaRelatorio("/relatorios/listaFolhaA4.jasper", par);
		}
	}

	public void insereTipoServico() {
		if (this.servicoListaSelecionado.getId() == null) {
			String cod = RetornaDadosSessao.RetornaCodigoEmpresa();
			this.servicoListaSelecionado.setCodigoCasaOracao(cod);
			this.servicoListaSelecionado.setLista(this.listaSelecionada);
			this.service.inserir(this.servicoListaSelecionado);
		} else {
			this.service.salvar(this.servicoListaSelecionado);
		}
		this.listaServicoLista = null;
		this.servicoListaSelecionado = null;
	}

	public void insereLista() {
		if (this.listaSelecionada.getId() == null) {
			String cod = RetornaDadosSessao.RetornaCodigoEmpresa();
			this.listaSelecionada.setCodigoCasaOracao(cod);
			this.service.inserir(this.listaSelecionada);

			insereServicoscomum();
			this.listaServicoLista = null;
			this.listaLista = null;
		} else {
			this.service.salvar(this.listaSelecionada);
		}
	}

	public void listarListaServicoComum() {
		this.listaItensServicoLista = null;
	}

	public void alterarLista() {
		this.listaItensServicoLista = null;
		this.listaServicoLista = null;
		this.servicoListaSelecionado = null;
	}

	public void excluirLista() {
		List<EntidadeServicoLista> sl = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class,
				"lista.id=" + this.listaSelecionada.getId());
		for (EntidadeServicoLista s : sl) {
			List<EntidadeItensServicoLista> lit = this.service
					.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "servicoLista.id=" + s.getId());
			for (EntidadeItensServicoLista it : lit) {
				it.setStatus("excluido");
				this.service.salvar(it);
			}
			s.setStatus("excluido");
			this.service.salvar(s);
		}
		this.listaSelecionada.setObservacao("excluido - teste");
		this.service.salvar(this.listaSelecionada);
		novaLista();
	}

	private void insereServicoFixo() {
          String cod = RetornaDadosSessao.RetornaCodigoEmpresa();
          DateFormat dfor = new SimpleDateFormat("dd.MM");
          GregorianCalendar dataInicial = new GregorianCalendar();
          dataInicial.setTime(this.listaSelecionada.getDataLista());
          String ultimo = String.valueOf(dataInicial.getActualMaximum(5));
          String dataFinal = String.valueOf(dataInicial.get(1)) + "-" + String.valueOf(dataInicial.get(2) + 1) + "-" + ultimo;
          System.out.println("data ini: " + dataFinal);
          List<EntidadeServicoFixo> listaFixo = this.service.listaConsultaGenericoStatusCod(EntidadeServicoFixo.class, "dataServico between '" + this.formataDataConsulta.format(this.listaSelecionada.getDataLista()) + "' and '" + dataFinal + "'");
        
          for (EntidadeServicoFixo f : listaFixo) {
              EntidadeTipoServico tipo = f.getTipoServico();
               EntidadeServicoLista servLista = new EntidadeServicoLista();
              List listaServico = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class, "lista.id=" + this.listaSelecionada.getId() + " and tipoServico.id=" + tipo.getId());
                if (listaServico.size() > 0) {
                 servLista = (EntidadeServicoLista) listaServico.get(0);
                            } else {
                servLista.setCodigoCasaOracao(cod);
               servLista.setLista(this.listaSelecionada);
              servLista.setTipoServico(tipo);
             servLista.setOrdem(tipo.getOrdemAproximada());
             this.service.inserir(servLista);
                            }
            
             EntidadeItensServicoLista itemS = new EntidadeItensServicoLista();
             itemS.setDescricao(f.getDescricao());
             dfor = new SimpleDateFormat("dd.MM");
             itemS.setDataServicoDate(f.getDataServico());
             itemS.setDataServicoStr(dfor.format(f.getDataServico()));
             itemS.setHora(f.getHora());
             itemS.setAtendidoPor(f.getAtendidoPor());
             itemS.setServicoLista(servLista);
               itemS.setCodigoCasaOracao(cod);
            
                GregorianCalendar dat = new GregorianCalendar();
                dat.setTime(itemS.getDataServicoDate());
                itemS.setCodigoMesAno(String.valueOf(f.getId()) + String.valueOf(dat.get(2)) + String.valueOf(dat.get(1)));
                List lisl = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "codigoMesAno='" + itemS.getCodigoMesAno() + "' and servicoLista.lista.id=" + this.listaSelecionada.getId());
                if (lisl.size() < 1)     {
                this.service.inserir(itemS);
            }  else {
                 238  System.out.println("ja existe este serviço, comum primeiro..............");
                            }
            
                    }
        
            dataInicial.set(2, 2);
            ultimo = String.valueOf(dataInicial.getActualMaximum(5));
            dataFinal = String.valueOf(dataInicial.get(1)) + "-" + String.valueOf(dataInicial.get(2) + 1) + "-" + ultimo;
            String dataInicialDate = String.valueOf(dataInicial.get(1)) + "-" + String.valueOf(dataInicial.get(2) + 1) + "-1";
            listaFixo = this.service.listaConsultaGenericoStatusCod(EntidadeServicoFixo.class, "dataServico between '" + dataInicialDate + "' and '" + dataFinal + "'");
        
            for (EntidadeServicoFixo f : listaFixo)     {
            if (f.getIncluirMesAnterior().booleanValue() == true) {
                    EntidadeTipoServico tipo = f.getTipoServico();
                    EntidadeServicoLista servLista = new EntidadeServicoLista();
                    List listaServico = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class, "lista.id=" + this.listaSelecionada.getId() + " and tipoServico.id=" + tipo.getId());
                    if (listaServico.size() > 0) {
                        servLista = (EntidadeServicoLista) listaServico.get(0);
                                    } else {
                        servLista.setCodigoCasaOracao(cod);
                        servLista.setLista(this.listaSelecionada);
                        servLista.setTipoServico(tipo);
                        servLista.setOrdem(tipo.getOrdemAproximada());
                        this.service.inserir(servLista);
                                    }
                
                    EntidadeItensServicoLista itemS = new EntidadeItensServicoLista();
                    itemS.setDescricao(f.getDescricao());
                    dfor = new SimpleDateFormat("dd.MM");
                    itemS.setDataServicoDate(f.getDataServico());
                    itemS.setDataServicoStr(dfor.format(f.getDataServico()));
                    itemS.setHora(f.getHora());
                    itemS.setAtendidoPor(f.getAtendidoPor());
                    itemS.setServicoLista(servLista);
                    itemS.setCodigoCasaOracao(cod);
                
                    GregorianCalendar dat = new GregorianCalendar();
                    dat.setTime(itemS.getDataServicoDate());
                    itemS.setCodigoMesAno(String.valueOf(f.getId()) + String.valueOf(dat.get(2)) + String.valueOf(dat.get(1)));
                    List lisl = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "codigoMesAno='" + itemS.getCodigoMesAno() + "' and servicoLista.lista.id=" + this.listaSelecionada.getId());
                    if (lisl.size() < 1)     {
                    this.service.inserir(itemS);
                }  else     {
                    System.out.println("ja existe este serviço, comum primeiro..............");
                }
                            }
        }
            }

	private void insereServicoscomum()  {
            String cod = RetornaDadosSessao.RetornaCodigoEmpresa();
        
            List<EntidadeServicoComum> listaSCIncluir = new ArrayList();
            List<EntidadeServicoComum> listaMesAnterior = new ArrayList();
        
            List<EntidadeServicoComum> listaTodosSC = this.service.listaConsultaGenericoStatusCod(EntidadeServicoComum.class);
            SimpleDateFormat dataFormat = new SimpleDateFormat("MM");
            SimpleDateFormat dataFormatAno = new SimpleDateFormat("yyyy");
            int mes = Integer.parseInt(dataFormat.format(this.listaSelecionada.getDataLista()));
            int ano = Integer.parseInt(dataFormatAno.format(this.listaSelecionada.getDataLista()));
        
            if (mes == 1) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getJaneiro().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getJaneiro().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getFevereiro().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
        
            if (mes == 2) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getFevereiro().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getFevereiro().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMarco().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 3) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getMarco().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getMarco().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getAbril().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 4) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getAbril().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getAbril().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMaio().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 5) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getMaio().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getMaio().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getJunho().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 6) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getJunho().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getJunho().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getJulho().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 7) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getJulho().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getJulho().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getAgosto().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 8) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getAgosto().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getAgosto().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getSetembro().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 9) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getSetembro().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getSetembro().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getOutubro().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 10) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getOutubro().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getOutubro().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getNovembro().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 11) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getNovembro().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getNovembro().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getDezembro().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                            }
                    }
            if (mes == 12) {
                for (int x = 0; x < listaTodosSC.size(); x++) {
                    if (((EntidadeServicoComum) listaTodosSC.get(x)).getDezembro().booleanValue() == true) {
                        listaSCIncluir.add(listaTodosSC.get(x));
                                    }
                
                    if ((!((EntidadeServicoComum) listaTodosSC.get(x)).getDezembro().booleanValue()) && (((EntidadeServicoComum) listaTodosSC.get(x)).getJaneiro().booleanValue() == true) && (((EntidadeServicoComum) listaTodosSC.get(x)).getMesAnterior().booleanValue() == true)) {
                        listaMesAnterior.add(listaTodosSC.get(x));
                                    }
                
                            }
            
                    }
        
            List<EntidadeItensServicoLista> lisServicosAnterior = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "dataServicoDate between '" + this.formataDataConsulta.format(this.listaSelecionada.getDataLista()) + "' and '3000-01-01'");
            for (EntidadeItensServicoLista l : lisServicosAnterior) {
                EntidadeTipoServico tipo = l.getServicoLista().getTipoServico();
                EntidadeServicoLista servLista = new EntidadeServicoLista();
             List listaServico = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class, "lista.id=" + this.listaSelecionada.getId() + " and tipoServico.id=" + tipo.getId());
             if (listaServico.size() > 0) {
             servLista = (EntidadeServicoLista) listaServico.get(0);
                            } else {
                 servLista.setCodigoCasaOracao(cod);
                servLista.setLista(this.listaSelecionada);
                servLista.setTipoServico(tipo);
               servLista.setOrdem(tipo.getOrdemAproximada());
              this.service.inserir(servLista);
                            }
              EntidadeItensServicoLista itemS = new EntidadeItensServicoLista();
              itemS.setDescricao(l.getDescricao());
              itemS.setDataServicoDate(l.getDataServicoDate());
              itemS.setDataServicoStr(l.getDataServicoStr());
              itemS.setHora(l.getHora());
              itemS.setAtendidoPor(l.getAtendidoPor());
              itemS.setServicoLista(servLista);
              itemS.setCodigoCasaOracao(l.getCodigoCasaOracao());
              GregorianCalendar dataI = new GregorianCalendar();
              dataI.setTime(itemS.getDataServicoDate());
              itemS.setCodigoMesAno(l.getCodigoMesAno());
             List lisl = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "codigoMesAno='" + itemS.getCodigoMesAno() + "' and servicoLista.lista.id=" + this.listaSelecionada.getId());
            if (lisl.size() < 1)  465  {
                this.service.inserir(itemS);
            }  else {
           System.out.println("ja existe este serviço incluso mes seguinte..............");
                            }
            
                    }
        
          for (int x = 0; x < listaSCIncluir.size(); x++) {
              EntidadeServicoComum sc = (EntidadeServicoComum) listaSCIncluir.get(x);
             EntidadeTipoServico tipo = sc.getTipoServico();
            
              EntidadeServicoLista servLista = new EntidadeServicoLista();
              List listaServico = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class, "lista.id=" + this.listaSelecionada.getId() + " and tipoServico.id=" + tipo.getId());
              if (listaServico.size() > 0) {
                 servLista = (EntidadeServicoLista) listaServico.get(0);
                            } else {
                servLista.setCodigoCasaOracao(cod);
               servLista.setLista(this.listaSelecionada);
              servLista.setTipoServico(tipo);
             servLista.setOrdem(tipo.getOrdemAproximada());
            this.service.inserir(servLista);
                            }
            EntidadeItensServicoLista item = new EntidadeItensServicoLista();
            
              item.setDescricao(sc.getDescricao());
              Date dataSe = retornaDataReferenteSemana(this.listaSelecionada.getDataLista(), sc.getDia().intValue(), mes - 1, ano, sc.getDiaSemana());
              DateFormat dfor = new SimpleDateFormat("dd.MM");
              DateFormat hfor = new SimpleDateFormat("HH:mm");
              dfor = new SimpleDateFormat("dd.MM");
              item.setDataServicoStr(dfor.format(dataSe));
              item.setDataServicoDate(dataSe);
              item.setHora(sc.getHoraStr());
              item.setAtendidoPor("");
              GregorianCalendar dataInicial = new GregorianCalendar();
              dataInicial.setTime(item.getDataServicoDate());
              item.setCodigoMesAno(String.valueOf(sc.getId()) + String.valueOf(dataInicial.get(2)) + String.valueOf(dataInicial.get(1)));
              item.setCodigoCasaOracao(cod);
              item.setServicoLista(servLista);
            
              new calculaDiferencaAno();
            int dias = calculaDiferencaAno.calculaDiferenca(this.listaSelecionada.getDataLista(), dataSe);
            
              if (dias >= 0) {
                List lisl = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "codigoMesAno='" + item.getCodigoMesAno() + "' and servicoLista.lista.id=" + this.listaSelecionada.getId());
                if (lisl.size() < 1)    {
                    this.service.inserir(item);
                }  else {
               System.out.println("ja existe este serviço incluso, serviço normal..............");
                                    }
                            }
               if (sc.getMesSeguinte().booleanValue() == true) {
                  EntidadeItensServicoLista itemS = new EntidadeItensServicoLista();
                  itemS.setDescricao(sc.getDescricao());
                  dfor = new SimpleDateFormat("dd.MM");
                  itemS.setDataServicoDate(retornaDataReferenteSemana(new Date(), sc.getDia().intValue(), mes, ano, sc.getDiaSemana()));
                  itemS.setDataServicoStr(dfor.format(retornaDataReferenteSemana(new Date(), sc.getDia().intValue(), mes, ano, sc.getDiaSemana())));
                  itemS.setHora(sc.getHoraStr());
                  itemS.setAtendidoPor("");
                  itemS.setServicoLista(servLista);
                 itemS.setCodigoCasaOracao(cod);
                GregorianCalendar dataI = new GregorianCalendar();
               dataI.setTime(itemS.getDataServicoDate());
              itemS.setCodigoMesAno(String.valueOf(sc.getId()) + String.valueOf(dataI.get(2)) + String.valueOf(dataI.get(1)));
             List lisl = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "codigoMesAno='" + itemS.getCodigoMesAno() + "' and servicoLista.lista.id=" + this.listaSelecionada.getId());
            if (lisl.size() < 1)  529  {
                    this.service.inserir(itemS);
                }  else {
           System.out.println("ja existe este serviço incluso mes seguinte..............");
                                    }
                
                            }
            
                    }
        
          System.out.println("lista mes anterior: " + listaMesAnterior.size());
          DateFormat dfor = new SimpleDateFormat("dd.MM");
          for (EntidadeServicoComum c : listaMesAnterior) {
              EntidadeTipoServico tipo = c.getTipoServico();
              EntidadeServicoLista servLista = new EntidadeServicoLista();
              List listaServico = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class, "lista.id=" + this.listaSelecionada.getId() + " and tipoServico.id=" + tipo.getId());
              if (listaServico.size() > 0) {
                  servLista = (EntidadeServicoLista) listaServico.get(0);
                            } else {
                  servLista.setCodigoCasaOracao(cod);
                  servLista.setLista(this.listaSelecionada);
                  servLista.setTipoServico(tipo);
                  servLista.setOrdem(tipo.getOrdemAproximada());
                  this.service.inserir(servLista);
                            }
            
              EntidadeItensServicoLista itemS = new EntidadeItensServicoLista();
              itemS.setDescricao(c.getDescricao());
              dfor = new SimpleDateFormat("dd.MM");
              itemS.setDataServicoDate(retornaDataReferenteSemana(new Date(), c.getDia().intValue(), mes, ano, c.getDiaSemana()));
              itemS.setDataServicoStr(dfor.format(retornaDataReferenteSemana(new Date(), c.getDia().intValue(), mes, ano, c.getDiaSemana())));
              itemS.setHora(c.getHoraStr());
              itemS.setAtendidoPor("");
              itemS.setServicoLista(servLista);
              itemS.setCodigoCasaOracao(cod);
              GregorianCalendar dataI = new GregorianCalendar();
              dataI.setTime(itemS.getDataServicoDate());
             itemS.setCodigoMesAno(String.valueOf(c.getId()) + String.valueOf(dataI.get(2)) + String.valueOf(dataI.get(1)));
            
            List lisl = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "codigoMesAno='" + itemS.getCodigoMesAno() + "' and servicoLista.lista.id=" + this.listaSelecionada.getId());
           if (lisl.size() < 1)    {
                this.service.inserir(itemS);
            }  else {
                 574  System.out.println("ja existe este serviço incluso, mes anterior..............");
                            }
            
                    }
        
          List<EntidadeServicoLista> lsl = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class, "lista.id=" + this.listaSelecionada.getId());
         for (EntidadeServicoLista s : lsl) {
              List is = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class, "servicoLista.id=" + s.getId());
             if (is.size() < 1) {
            s.setStatus("excluido");
           this.service.salvar(s);
                            }
            
                    }
        
          insereServicoFixo();
        
         this.listaServicoLista = null;
            }

	private Date retornaDataReferenteSemana(Date dataLista, int semanaServico, int mes, int anoServico, String dia) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dataLista);
		int quantDia = cal.getActualMaximum(5);

		int diaRef = 0;

		int diaSemSer = semanaServico;

		int ano = anoServico;

		int mesServiso = mes;

		int diaSemanaNum = 1;

		Date dataReturno = null;
		String diaSemana = dia;

		if (diaSemana.equalsIgnoreCase("Domingo")) {
			diaSemanaNum = 1;
		}
		if (diaSemana.equalsIgnoreCase("Segunda")) {
			diaSemanaNum = 2;
		}
		if (diaSemana.equalsIgnoreCase("Terça")) {
			diaSemanaNum = 3;
		}
		if (diaSemana.equalsIgnoreCase("Quarta")) {
			diaSemanaNum = 4;
		}
		if (diaSemana.equalsIgnoreCase("Quinta")) {
			diaSemanaNum = 5;
		}
		if (diaSemana.equalsIgnoreCase("Sexta")) {
			diaSemanaNum = 6;
		}
		if (diaSemana.equalsIgnoreCase("Sabado")) {
			diaSemanaNum = 7;
		}

		for (int x = 1; x <= quantDia; x++) {
			GregorianCalendar dat = new GregorianCalendar();
			dat.set(1, ano);
			dat.set(2, mesServiso);
			dat.set(5, x);
			if (dat.get(7) == diaSemanaNum) {
				diaRef += 1;

				if (diaRef == diaSemSer) {
					dataReturno = dat.getTime();
					break;
				}
			}
		}

		return dataReturno;
	}

	public EntidadeLista getListaSelecionada() {
		if (this.listaSelecionada == null) {
			this.listaSelecionada = new EntidadeLista();
		}
		return this.listaSelecionada;
	}

	public void setListaSelecionada(EntidadeLista listaSelecionada) {
		this.listaSelecionada = listaSelecionada;
	}

	public List<EntidadeLista> getListaLista() {
		if (this.listaLista == null) {
			this.listaLista = new ArrayList();
			this.listaLista = this.service.listaConsultaGenericoStatusCod(EntidadeLista.class);
		}
		return this.listaLista;
	}

	public void setListaLista(List<EntidadeLista> listaLista) {
		this.listaLista = listaLista;
	}

	public EntidadeServicoLista getServicoListaSelecionado() {
		if (this.servicoListaSelecionado == null) {
			this.servicoListaSelecionado = new EntidadeServicoLista();
		}
		return this.servicoListaSelecionado;
	}

	public void setServicoListaSelecionado(EntidadeServicoLista servicoListaSelecionado) {
		this.servicoListaSelecionado = servicoListaSelecionado;
	}

	public List<EntidadeServicoLista> getListaServicoLista() {
		if ((this.listaSelecionada != null) && (this.listaSelecionada.getId() != null)
				&& (this.listaServicoLista == null)) {
			this.listaServicoLista = new ArrayList();
			this.listaServicoLista = this.service.listaConsultaGenericoStatusCod(EntidadeServicoLista.class,
					" lista.id=" + this.listaSelecionada.getId() + " order by ordem");
		}

		return this.listaServicoLista;
	}

	public void setListaServicoLista(List<EntidadeServicoLista> listaServicoLista) {
		this.listaServicoLista = listaServicoLista;
	}

	public EntidadeItensServicoLista getItemServicoLista() {
		if (this.itemServicoLista == null) {
			this.itemServicoLista = new EntidadeItensServicoLista();
		}
		return this.itemServicoLista;
	}

	public void setItemServicoLista(EntidadeItensServicoLista itemServicoLista) {
		this.itemServicoLista = itemServicoLista;
	}

	public List<EntidadeItensServicoLista> getListaItensServicoLista() {
		if ((this.servicoListaSelecionado != null) && (this.servicoListaSelecionado.getId() != null)
				&& (this.listaItensServicoLista == null)) {
			this.listaItensServicoLista = new ArrayList();
			this.listaItensServicoLista = this.service.listaConsultaGenericoStatusCod(EntidadeItensServicoLista.class,
					" servicoLista.id=" + this.servicoListaSelecionado.getId() + " order by dataServicoDate, hora");
		}
		return this.listaItensServicoLista;
	}

	public void setListaItensServicoLista(List<EntidadeItensServicoLista> listaItensServicoLista) {
		this.listaItensServicoLista = listaItensServicoLista;
	}
}
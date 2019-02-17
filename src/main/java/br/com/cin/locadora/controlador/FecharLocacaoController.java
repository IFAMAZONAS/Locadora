package br.com.cin.locadora.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cin.locadora.controlador.util.Navegacao;
import br.com.cin.locadora.model.Cartao;
import br.com.cin.locadora.model.Cheque;
import br.com.cin.locadora.model.Filme;
import br.com.cin.locadora.model.Locacao;
import br.com.cin.locadora.model.Pagamento;
import br.com.cin.locadora.model.TipoMidia;
import br.com.cin.locadora.model.repository.CartaoRepository;
import br.com.cin.locadora.model.repository.ChequeRepository;
import br.com.cin.locadora.model.repository.ClienteRepository;
import br.com.cin.locadora.model.repository.FormaPagamentoRepository;
import br.com.cin.locadora.model.repository.LocacaoRepository;
import br.com.cin.locadora.model.repository.PagamentoRepository;
import br.com.cin.locadora.model.repository.StatusLocacaoRepository;
import gherkin.lexer.Pa;

@Controller
@RequestMapping(value = "fechamento")
public class FecharLocacaoController {
	
	static Integer CARTAO=1;
	static Integer CHEQUE=2;
	static Integer DINHERO=3;
	
	static Integer LOCACAO_ABERTA=1;
	static Integer LOCACAO_FECHADA=2;
	
	@Autowired
	private LocacaoRepository locacaoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private StatusLocacaoRepository statusLocacaoRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private ChequeRepository chequeRepository;
	
	private Double valorLocacao =0.0;
	private Integer formaPagamentoId;
	
	
	private Locacao locacao;
	private Pagamento pagamento;
	
	
	@GetMapping("**/cadastropagamento/{idLocacao}")
	@ResponseBody
	public ModelAndView entrarTelapagamento(@PathVariable("idLocacao") Integer idLocacao) {
		 ModelAndView andView = new ModelAndView(Navegacao.ENTRAR_TELA_PAGAMENTO_LOCACAO);
		 
		 this.locacao = this.locacaoRepository.findById(idLocacao).get();
		 andView.addObject("clienteLocacao", this.locacao.getIdCliente());
		 andView.addObject("itens", this.locacao.getLocacaoFilmeList());
		 andView.addObject("locacao",this.locacao);
		 andView.addObject("formaPagamento", this.formaPagamentoRepository.findAll());
		 
		 return andView;
	}
	@RequestMapping(value = "**/processarpagamento", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processarPagamento(@RequestParam("numerocartao") String numeroCartao,
			@RequestParam("dataautorizacao") String dataautorizacao, 
			@RequestParam("bandeira") String bandeira,
			@RequestParam("formaPagamento") String formaPagamento,
			@RequestParam("numerocheque") String numerocheque,
			@RequestParam("banco") String banco,
			@RequestParam("agencia") String agencia,
			@RequestParam("conta") String conta) throws ParseException {
		ModelAndView andView = new ModelAndView(Navegacao.BUSCAR_LOCACAO_NOVO);
		
		Cartao cartao;
		Cheque cheque;
		
		this.formaPagamentoId = Integer.valueOf(formaPagamento);
		
		if(this.formaPagamentoId == CARTAO) {
			
			
			this.pagamento = new Pagamento();
			this.pagamento.setValor(this.locacao.getValor());
			this.pagamento.setValorTotal(this.locacao.getValor());
			this.pagamento.setLocacao(this.locacao);
			this.locacao.setPagamentoList(new ArrayList<Pagamento>());
			this.locacao.getPagamentoList().add(this.pagamento);
			this.locacao.setStatusLocacao(this.statusLocacaoRepository.findById(LOCACAO_FECHADA).get());
			this.pagamento.setFormaPagamento(this.formaPagamentoRepository.findById(formaPagamentoId).get());
			this.pagamento.setCartaoList(new ArrayList<Cartao>());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formato.parse(dataautorizacao.replace('-', '/'));
			
			this.pagamentoRepository.save(this.pagamento);
			cartao = new Cartao();
			cartao.setNumero(numeroCartao);
			cartao.setOperadora(bandeira);
			cartao.setDataAutorizacao(date);
			
			
		
			cartao.setPagamento(pagamento);
			this.cartaoRepository.save(cartao);
			this.locacaoRepository.save(this.locacao);
		}
		if(this.formaPagamentoId==CHEQUE) {
			this.pagamento = new Pagamento();
			this.pagamento.setValor(this.locacao.getValor());
			this.pagamento.setValorTotal(this.locacao.getValor());
			this.pagamento.setLocacao(this.locacao);
			this.locacao.setPagamentoList(new ArrayList<Pagamento>());
			this.locacao.getPagamentoList().add(this.pagamento);
			this.locacao.setStatusLocacao(this.statusLocacaoRepository.findById(LOCACAO_FECHADA).get());
			this.pagamento.setFormaPagamento(this.formaPagamentoRepository.findById(formaPagamentoId).get());
			this.pagamento.setCartaoList(new ArrayList<Cartao>());
			
			this.pagamentoRepository.save(this.pagamento);
			
			cheque = new Cheque();
			cheque.setPagamento(this.pagamento);
			cheque.setAgencia(agencia);
			cheque.setBanco(banco);
			cheque.setNumeroCheque(numerocheque);
			cheque.setConta(conta);
			
			this.chequeRepository.save(cheque);
			this.locacaoRepository.save(this.locacao);
			
			
			
			
			
			
		}
		
		if(this.formaPagamentoId==DINHERO) {
			this.pagamento = new Pagamento();
			this.pagamento.setValor(this.locacao.getValor());
			this.pagamento.setValorTotal(this.locacao.getValor());
			this.pagamento.setLocacao(this.locacao);
			this.locacao.setPagamentoList(new ArrayList<Pagamento>());
			this.locacao.getPagamentoList().add(this.pagamento);
			this.locacao.setStatusLocacao(this.statusLocacaoRepository.findById(LOCACAO_FECHADA).get());
			this.pagamento.setFormaPagamento(this.formaPagamentoRepository.findById(formaPagamentoId).get());
			this.pagamento.setCartaoList(new ArrayList<Cartao>());
			
			this.pagamentoRepository.save(this.pagamento);
			this.locacaoRepository.save(this.locacao);
		}
		
		this.locacao = new Locacao();
		this.pagamento = new Pagamento();
		cartao = new Cartao();
		cheque = new Cheque();
		
		return andView;
	}
	
	@GetMapping("**/visualizarlocacao/{idLocacao}")
	public ModelAndView modal(@PathVariable("idLocacao") Integer idLocacao) {
		 ModelAndView andView = new ModelAndView(Navegacao.VISUALIZAR_LOCACAO);
		 this.locacao = this.locacaoRepository.findById(idLocacao).get();
		 andView.addObject("locacao",this.locacao);
		 andView.addObject("itens", this.locacao.getLocacaoFilmeList());
		 andView.addObject("clienteLocacao", this.locacao.getIdCliente());
		 andView.addObject("formaPagamento", this.locacao.getPagamentoList().get(0).getFormaPagamento().getDescricao());
		 return andView;
	}
	
	
	
	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}
	
	public Locacao getLocacao() {
		return locacao;
	}

}

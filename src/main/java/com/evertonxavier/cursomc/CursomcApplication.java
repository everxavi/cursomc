package com.evertonxavier.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.evertonxavier.cursomc.domain.Categoria;
import com.evertonxavier.cursomc.domain.Cidade;
import com.evertonxavier.cursomc.domain.Cliente;
import com.evertonxavier.cursomc.domain.Endereco;
import com.evertonxavier.cursomc.domain.Estado;
import com.evertonxavier.cursomc.domain.Pagamento;
import com.evertonxavier.cursomc.domain.PagamentoComBoleto;
import com.evertonxavier.cursomc.domain.PagamentoComCartao;
import com.evertonxavier.cursomc.domain.Pedido;
import com.evertonxavier.cursomc.domain.Produto;
import com.evertonxavier.cursomc.domain.enuns.EstadoPagamento;
import com.evertonxavier.cursomc.domain.enuns.TipoCliente;
import com.evertonxavier.cursomc.repositories.CategoriaRepository;
import com.evertonxavier.cursomc.repositories.CidadeRepository;
import com.evertonxavier.cursomc.repositories.ClienteRepository;
import com.evertonxavier.cursomc.repositories.EnderecoRepository;
import com.evertonxavier.cursomc.repositories.EstadoRepository;
import com.evertonxavier.cursomc.repositories.PagamentoRepository;
import com.evertonxavier.cursomc.repositories.PedidoRepository;
import com.evertonxavier.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;	
	@Autowired
	private ClienteRepository clienteRepository;	
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
				
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cl1 = new Cliente(null, "Maria Silva", "mariasilva@gmail.com", "111.222.333.44", TipoCliente.PESSOAFISICA);
		cl1.getTelefones().addAll(Arrays.asList("84.91111-2222", "84.92222-3333"));
		
		Endereco e1 = new Endereco(null, "R. Flores", "300", "Apto 303", "Jardim", "59000-100", cl1, c1);
		Endereco e2 = new Endereco(null, "Av. Matos", "105", "Sala 800", "Centro", "59000-200", cl1, c2);
		
		cl1.getEndereco().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cl1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("20/06/2023 10:00"), cl1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("05/07/2023 17:50"), cl1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("09/12/2023 00:00"), null);
		ped2.setPagamento(pgto2);
		
		cl1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
	}

}

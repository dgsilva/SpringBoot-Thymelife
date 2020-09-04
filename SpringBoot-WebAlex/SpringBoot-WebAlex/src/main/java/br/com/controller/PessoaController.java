package br.com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.entity.Pessoa;
import br.com.entity.Telefone;
import br.com.repository.PessoaRepository;
import br.com.repository.TelefoneRepository;

@Controller
public class PessoaController {
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private TelefoneRepository telefoneRepository;
	
    //Ele vai direcionar para essa tela
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		return modelAndView;
	}
	//Salvar											//** -> Serve para ingorar antes de salvar pessoa
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	//Valid - > Serve para fazer a validação.
	//BindingResult - > Serve para retorna a validação
	//HasErros - > Esse objeto serve para trata o erro
	public ModelAndView  salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ModelAndView modelandView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelandView.addObject("pessoas", pessoasIt);
			                           //Retorna objeto vazio
			modelandView.addObject("pessoaobj", new Pessoa());
			
			List<String>msg = new ArrayList<>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());//Vem das anotações @NotEmpty
			}
		    modelandView.addObject("msg", msg);
			//Se de erro ele só vai executa somente esse if. e vai retorna na tela
			return modelandView;
		}
		pessoaRepository.save(pessoa);
		
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		                           //Retorna objeto vazio
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("msg", "Dados gravado com sucesso");
	   return andView;
	}
	
	//ele está listado os dados na tabela
	@RequestMapping(method = RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}
	
	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa")Long idpessoa) {
		//Está sendo instanciado porque vai retorna na tela 
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		modelAndView.addObject("pessoaobj",pessoa.get());
		return modelAndView;
	}
	
	//Vai excluir da tabela
	@GetMapping("/removerpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa")Long idpessoa) {
		 
		pessoaRepository.deleteById(idpessoa);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas",pessoaRepository.findAll());
		modelAndView.addObject("pessoaobj",new Pessoa());
		return modelAndView;
	}
	
	@PostMapping("**/pesquisarpessoa")
									//RequestParam -> ele que vai pesquisar a pessoa
	public ModelAndView pesquisar(@RequestParam("nomepesquisar") String nomepesquisar) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisar));
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}
	
	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa")Long idpessoa) {
		//Está sendo instanciado porque vai retorna na tela 
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		modelAndView.addObject("pessoaobj",pessoa.get());
		//Para carregar a lista dos telefones
		modelAndView.addObject("telefones",telefoneRepository.getTelefones(idpessoa));
		return modelAndView;
	}
	
	//Fazendo salvar o telefone da pessoa
	@PostMapping("**/addfonePessoa/{pessoaid}")
	public ModelAndView addFonePessoa( Telefone telefone, 
			@PathVariable("pessoaid")Long pessoaid) {
		//Ele faz a buscar pelo objeto
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		if(telefone !=null && telefone.getNumero().isEmpty()||telefone.getTipo().isEmpty()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj",pessoa);
			modelAndView.addObject("telefones",telefoneRepository.getTelefones(pessoaid));
			List<String>msg = new ArrayList<>();
			if(telefone.getNumero().isEmpty()) {
			msg.add("Numero deve ser informado");
			}
			if(telefone.getTipo().isEmpty()) {
				msg.add("Tipo deve ser informado");
			}
			modelAndView.addObject("msg",msg);
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		//Ele pega o set do objeto pessoa
		telefone.setPessoa(pessoa);
		//Ele está salvando o telefone da pessoa do relacionamento 
		telefoneRepository.save(telefone);
		//Se não coloca esse objeto, vai dar erro na hora de salvar
		modelAndView.addObject("pessoaobj",pessoa);
		//Vai carregar os telefones assim q salvar
		modelAndView.addObject("telefones",telefoneRepository.getTelefones(pessoaid));
		return modelAndView;
		
	}
	//Meotodo de Excluir
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone")Long idtelefone) {
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		
		telefoneRepository.deleteById(idtelefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj",pessoa);
		modelAndView.addObject("telefones",telefoneRepository.getTelefones(pessoa.getId()));
		return modelAndView;
	}
	
}

package com.evertonxavier.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonxavier.cursomc.domain.Cliente;
import com.evertonxavier.cursomc.repositories.ClienteRepository;
import com.evertonxavier.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository cliente;
	public Cliente find(Integer id) {
		Optional<Cliente> obj = cliente.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
}

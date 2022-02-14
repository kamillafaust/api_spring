package com.algaworks.osworks.domain.service;
import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        Cliente clienteVerificado = clienteRepository.findByEmail(cliente.getEmail());

        if(clienteVerificado != null && !clienteVerificado.equals(cliente)) {
            throw new NegocioException("Já existe um cliente cadastrado com esse e-mail.");
        }
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}

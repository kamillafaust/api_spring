package com.algaworks.osworks.api.contoller;

import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.service.CadastroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll());
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Cliente>> buscarPorNome() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findByNome("Maria"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> clienteBusca = clienteRepository.findById(id);
        if (clienteBusca.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteBusca.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String cadastrarCliente(@Valid @RequestBody Cliente cliente)
        cadastroClienteService.salvar(cliente);
        return "Cliente salvo com sucesso!";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@Valid @PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        if(!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteAtualizado.setId(id);
        clienteAtualizado = cadastroClienteService.salvar(clienteAtualizado);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        if(!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cadastroClienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

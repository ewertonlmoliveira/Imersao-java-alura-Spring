package br.com.alura.linguagens.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

public class LinguagemController {

    @Autowired
    private LinguagemRepository repository;

    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens() {
        List<Linguagem> linguagens = repository.findByOrderByRanking();
        return linguagens;
    }
    @PostMapping("/linguagens")
    public ResponseEntity <Linguagem> cadastrarLinguagem (@RequestBody Linguagem linguagem) {
      Linguagem linguagemSalva = repository.save(linguagem);
      return new ResponseEntity<>(linguagemSalva, HttpStatus.CREATED);
    }
    @GetMapping("/linguagens/{id}")
    public Linguagem obterLinguagemPorId(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/linguagens/{id}")
    public Linguagem atualizarLiguagem(@PathVariable String id, @RequestBody Linguagem linguagem) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        linguagem.setId(id);
        Linguagem linguagemSalva = repository.save(linguagem);
        return linguagemSalva;
    }
    @DeleteMapping("/linguagens/{id}")
    public void excluirLinguagem(@PathVariable String id){
        repository.deleteById(id);
    }
}

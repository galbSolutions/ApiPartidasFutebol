package br.com.meli.apifutebol.service;

import br.com.meli.apifutebol.dto.EstadioDto;
import br.com.meli.apifutebol.model.Estadio;
import br.com.meli.apifutebol.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EstadioService{
    private final EstadioRepository estadioRepository;
    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }
    public List<EstadioDto> getEstadio() {
       /* List<Estadio> estadio;
        try {
            estadio = estadioRepository.findAll();
        } catch (Exception ex) {
            // qualquer falha de acesso a dados vira RuntimeException
            throw new RuntimeException("Erro ao acessar base de dados de estádios.", ex);
        }

        if (estadio == null || estadio.isEmpty()) {
            // 404 na sua camada superior (controller)
            throw new NoSuchElementException("Nenhum estádio encontrado.");
        }*/

        // converte entidade → DTO
        return estadioRepository.findAll().stream()
                .map(e -> new EstadioDto(
                        e.getId(),
                        e.getNome(),
                        e.getCidade(),
                        e.getCapacidade(),
                        e.isStatus()))
                .collect(Collectors.toList());
    }

    public EstadioDto cadastrarEstadio(EstadioDto dto) {
        // mapeia DTO → entidade
        Estadio entidade = new Estadio();
        entidade.setNome(dto.getNome());
        entidade.setCapacidade(dto.getCapacidade());
        entidade.setCidade(dto.getCidade());
        // salva (gera o ID)
        Estadio saved;
        try {
            saved = estadioRepository.save(entidade);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar estádio.", e);
        }
        // mapeia entidade → DTO e retorna
        return new EstadioDto(
                saved.getId(),
                saved.getNome(),
                saved.getCidade(),
                saved.getCapacidade(),
                saved.isStatus()
        );
    }
}


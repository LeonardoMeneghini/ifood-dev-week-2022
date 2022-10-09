package mi.dio.sacola.service.impl;

import lombok.RequiredArgsConstructor;
import mi.dio.sacola.enumeration.FormaPagamento;
import mi.dio.sacola.model.Item;
import mi.dio.sacola.model.Sacola;
import mi.dio.sacola.repository.ProdutoRepository;
import mi.dio.sacola.repository.SacolaRepository;
import mi.dio.sacola.resource.dto.ItemDto;
import mi.dio.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola (itemDto.getIdSacola ());

        if(sacola.isFechada()){
            throw new RuntimeException("Esta sacola está fechada.");
        }
        Item itemParaSerInserido = Item.builder ()
                .quantidade (itemDto.getQuantidade ())
                .sacola(sacola)
                .produto (produtoRepository.findById (itemDto.getProdutoId ()).orElseThrow (
                        () -> {
                            throw new RuntimeException ("Essa sacola não existe!");
                        }
                ))
                .build();

        return null;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException ("Essa sacola não existe!");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroformaPagamento) {
        Sacola sacola = verSacola (id);

        if(sacola.getItens().isEmpty()){
            throw new RuntimeException("Inclua itens na sacola!");
        }
        FormaPagamento formaPagamento =
                numeroformaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);

    }
}

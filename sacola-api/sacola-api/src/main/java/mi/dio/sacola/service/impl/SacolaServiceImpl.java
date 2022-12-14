package mi.dio.sacola.service.impl;

import lombok.RequiredArgsConstructor;
import mi.dio.sacola.enumeration.FormaPagamento;
import mi.dio.sacola.model.Item;
import mi.dio.sacola.model.Restaurante;
import mi.dio.sacola.model.Sacola;
import mi.dio.sacola.repository.ItemRepository;
import mi.dio.sacola.repository.ProdutoRepository;
import mi.dio.sacola.repository.SacolaRepository;
import mi.dio.sacola.resource.dto.ItemDto;
import mi.dio.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;

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
                            throw new RuntimeException ("Esse produto não existe!");
                        }
                ))
                .build();

         List<Item> itensDaSacola = sacola.getItens();
        if(itensDaSacola.isEmpty()){
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();
            if(restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaSacola.add (itemParaSerInserido);
            } else {
                throw new RuntimeException ("Não é possível adicionar itens de estabelecimentos distintos. FECHE ou ESVAZIE a sacola!");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();
        for(Item itemDaSacola: itensDaSacola){
            double valorTotalItem =
            itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        double valorTotalSacola = valorDosItens.stream()
                        .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                                .sum();
        sacola.setValorTotal(valorTotalSacola);

        sacolaRepository.save(sacola);
        return itemParaSerInserido;

        //return itemRepository.save(itemParaSerInserido); AQUI FOI RETIRADO PARA NÃO OCORRER DUPLICIDADE DE LINHAS NO DB.

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
